/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.concurrency.impl;

import com.huotu.huobanmallcount.common.DateHelper;
import com.huotu.huobanmallcount.common.MathHelper;
import com.huotu.huobanmallcount.concurrency.SystemCounting;
import com.huotu.huobanmallcount.entity.*;
import com.huotu.huobanmallcount.model.TreeNode;
import com.huotu.huobanmallcount.model.UserConsumeModel;
import com.huotu.huobanmallcount.repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


/**
 * 计算演练过程
 * 1.原始数据转为多叉树
 * 2.叶子开始计算 往上贡献的5%值 完成后触发父节点计算
 * 3.父节点计算 在子节点全部计算完成后，按照 5%的值->2%的值->往上贡献的5%值->往上贡献的2%值 计算完，再次触发父节点计算，循环循环，如此往复
 * 4.计算的数据存入数据库
 * Created by lgh on 2015/10/9.
 */

@Service("SystemCountingImpl")
public class SystemCountingImpl implements SystemCounting {

    private static Log log = LogFactory.getLog(SystemCountingImpl.class);

    //todo 上线替换
    //5%计算起始值
    private float startAmount = 100;//50000
    //金额打折费率
    private float rate = 1;//0.94f
    //每月结算日 如1，代表1日
    private int monthCountedDay = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserShareRepository userShareRepository;

    @Autowired
    private UserShareSourceRepository userShareSourceRepository;


    @Autowired
    private UserShareAmountedRepository userShareAmountedRepository;

    @Autowired
    private UserShareRebateRepository userShareRebateRepository;

    @Autowired
    private UserShareRebateSourceRepository userShareRebateSourceRepository;

    @Autowired
    private UserBalanceLogRepository userBalanceLogRepository;

    public void countAll() {
        Calendar calendar = Calendar.getInstance();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 10, 1, 0, 0, 0);
        beginTime.set(Calendar.MILLISECOND, 0);

        if (calendar.getTime().getTime() > beginTime.getTime().getTime())
            countMerchant(3677);
        else
            log.info("还没有到11月1日呢");
    }

    public void countMerchant(int merchantId) {


        log.info("开始处理父节点");
        Map<Integer, List<TreeNode>> arrMap = queryListToMap(merchantId);

        log.info("开始生成多叉树");
        List<TreeNode> treeNodes = MapForTree(arrMap);

        List<UserShareAmounted> userShareAmounteds = userShareAmountedRepository.findAll();
        log.info("获取消费数据");
        List<UserConsumeModel> userConsumeModels = getConsumeList(merchantId, userShareAmounteds);

        log.info("消费数据计入多叉树");
        consumeToTree(treeNodes, userConsumeModels);

        log.info("开始计算多叉树");
        List<TreeNode> saveTreeNodes = new ArrayList<>();
        //已参与分摊的用户
        List<UserShareAmounted> countedUserList = new ArrayList<>();

        //创建TreeNode
        for (TreeNode treeNode : treeNodes) {
            countedUserList.addAll(countFromLeaf(treeNode));
//            log.info("计算了 " + treeNode.getId() + " 多叉树");
            saveTreeNodes.add(treeNode);

        }

        log.info("一共有" + saveTreeNodes.size() + "树");

        //保存数据
        log.info("开始保存数据到数据库");
        saveTree(saveTreeNodes, merchantId, countedUserList, userShareAmounteds);
    }

    /**
     * 消费数据并入多叉树
     *
     * @param treeNodes
     * @param userConsumeModels
     */
    private void consumeToTree(List<TreeNode> treeNodes, List<UserConsumeModel> userConsumeModels) {
        for (TreeNode treeNode : treeNodes) {
            treeNode.setCurrentAmount(getUserMoney(userConsumeModels, treeNode.getId()));
            consumeToTreeNext(treeNode, userConsumeModels);
        }
    }

    /**
     * 消费数据并入多叉树 (循环查找)
     *
     * @param treeNode
     * @param userConsumeModels
     */
    private void consumeToTreeNext(TreeNode treeNode, List<UserConsumeModel> userConsumeModels) {
        if (treeNode.getChildList() != null) {
//            if (treeNode.getParentNode() != null)
//                log.info("parentNode:" + treeNode.getParentNode().getId() + " parentId:" + treeNode.getParentId());
            for (TreeNode treeNode1 : treeNode.getChildList()) {
                treeNode1.setCurrentAmount(getUserMoney(userConsumeModels, treeNode1.getId()));
                consumeToTreeNext(treeNode1, userConsumeModels);
            }
        }
    }

    @Transactional
    private void saveTree(List<TreeNode> treeNodes, Integer merchantId
            , List<UserShareAmounted> countedUserList
            , List<UserShareAmounted> userShareAmounteds) {
        Date time = DateHelper.getThisDayBegin();

        List<UserShare> userShares = new ArrayList<>();
        List<UserShareSource> userShareSources = new ArrayList<>();
        List<UserShareRebate> userShareRebates = new ArrayList<>();
        List<UserShareRebateSource> userShareRebateSources = new ArrayList<>();


        for (TreeNode treeNode : treeNodes) {
            if (treeNode.getPercentFiveAmount() > 0 || treeNode.getPercentTwoAmount() > 0) {
                treeNode.getSourceFive().forEach(x -> {
                    x.setTime(time);
                });
                treeNode.getSourceTwo().forEach(x -> {
                    x.setTime(time);
                });

                userShareRebates.add(new UserShareRebate(merchantId
                        , treeNode.getId()
                        , time
                        , treeNode.getPercentFiveAmount()
                        , treeNode.getPercentTwoAmount()
                        , treeNode.getPercentFiveNum()
                        , treeNode.getPercentTwoNum()));
                userShareRebateSources.addAll(treeNode.getSourceFive());
                userShareRebateSources.addAll(treeNode.getSourceTwo());
            }

            userShares.add(new UserShare(merchantId
                    , treeNode.getId()
                    , treeNode.getPercentFiveAmount()
                    , treeNode.getPercentTwoAmount()
                    , treeNode.getPercentFiveNumAll()
                    , treeNode.getPercentTwoNumAll()));
            userShareSources.addAll(treeNode.getSourceFiveAll());
            userShareSources.addAll(treeNode.getSourceTwoAll());


            for (TreeNode treeNode1 : treeNode.getChildrens()) {
                if (treeNode1.getPercentFiveAmount() > 0 || treeNode1.getPercentTwoAmount() > 0) {
                    treeNode1.getSourceFive().forEach(x -> {
                        x.setTime(time);
                    });
                    treeNode1.getSourceTwo().forEach(x -> {
                        x.setTime(time);
                    });

                    userShareRebates.add(new UserShareRebate(merchantId
                            , treeNode1.getId()
                            , time
                            , treeNode1.getPercentFiveAmount()
                            , treeNode1.getPercentTwoAmount()
                            , treeNode1.getPercentFiveNum()
                            , treeNode1.getPercentTwoNum()));
                    userShareRebateSources.addAll(treeNode1.getSourceFive());
                    userShareRebateSources.addAll(treeNode1.getSourceTwo());
                }

                userShares.add(new UserShare(merchantId
                        , treeNode1.getId()
                        , treeNode1.getPercentFiveAmount()
                        , treeNode1.getPercentTwoAmount()
                        , treeNode1.getPercentFiveNumAll()
                        , treeNode1.getPercentTwoNumAll()));
                userShareSources.addAll(treeNode1.getSourceFiveAll());
                userShareSources.addAll(treeNode1.getSourceTwoAll());
            }

        }

        //存入当天计算数据
        userShareRepository.deleteAll();
        userShareSourceRepository.deleteAll();
        userShareRepository.save(userShares);
        userShareSourceRepository.save(userShareSources);


        /**
         * 以下数据只有结算日才计算
         */
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DATE) == monthCountedDay) {
            //存入正式返利数据
            userShareRebateRepository.save(userShareRebates);
            userShareRebateSourceRepository.save(userShareRebateSources);

            //计算的用户列表countedUserList存入数据库
            for (UserShareAmounted userShareAmounted : countedUserList) {
                mergeUserShareAmounted(userShareAmounteds, userShareAmounted);
            }
            userShareAmountedRepository.save(userShareAmounteds);


            Date date = new Date();
            //给用户返利
            for (UserShareRebate userShareRebate : userShareRebates) {

                Float money = userShareRebate.getPercentFiveAmount() + userShareRebate.getPercentTwoAmount();

                //记录流水
                UserBalanceLog userBalanceLog = new UserBalanceLog();
                userBalanceLog.setDisabled(0);
                userBalanceLog.setExplodeMoney(0);
                userBalanceLog.setMemberAdvance(0);
                userBalanceLog.setMemo("梦想基金分红");
                userBalanceLog.setMerchantId(merchantId);
                userBalanceLog.setMoney(money);
                userBalanceLog.setPayType("梦想基金分红");
                userBalanceLog.setRemain(money);
                userBalanceLog.setShopAdvance(0);
                userBalanceLog.setTime(date);
                userBalanceLog.setUserId(userShareRebate.getUserId());

                userBalanceLogRepository.save(userBalanceLog);

                //记录金额
                StringBuilder hql = new StringBuilder();
                hql.append("update User user set user.balance=user.balance+:balance where user.id=:userId ");
                userRepository.executeHql(hql.toString(), query -> {
                    query.setParameter("userId", userShareRebate.getUserId());
                    query.setParameter("balance", money);
                });


            }
        }
        log.info("保存" + userShares.size() + "个分摊数据，来源数据" + userShareSources.size() + "个"
                + " 本次返利个数为 " + userShareRebates.size() + " 来源 " + userShareRebateSources.size());
    }

    /**
     * 存在则累计 不存在则添加
     *
     * @param userShareAmounteds
     * @param userShareAmounted
     */
    private void mergeUserShareAmounted(List<UserShareAmounted> userShareAmounteds, UserShareAmounted userShareAmounted) {
        boolean exist = false;
        for (UserShareAmounted userShareAmounted1 : userShareAmounteds) {
            if (userShareAmounted.getUserId().equals(userShareAmounted1.getUserId())) {
                userShareAmounted1.setAmount(userShareAmounted1.getAmount() + userShareAmounted.getAmount());
                exist = true;
                break;
            }
        }
        if (!exist) userShareAmounteds.add(userShareAmounted);
    }

    /**
     * 获取所有的父，并以父为key，子集为value放入Map中
     *
     * @return
     */
    public Map<Integer, List<TreeNode>> queryListToMap(int merchantId) {
        //用父Id分组查询,找到所有的父ID然后循环这个List查询
        List<Integer> sqlGroupList = userRepository.findParents(merchantId);

        //查询出所有的节点
        Map<Integer, List<TreeNode>> arrMap = new HashMap<Integer, List<TreeNode>>(sqlGroupList.size());
        List<User> users = userRepository.findByMerchantId(merchantId);
        /*
         * 通过 父ID 和 所有的节点 比对
		 */
        for (int k = 0; k < sqlGroupList.size(); k++) {
            Integer pid = sqlGroupList.get(k);

            List<TreeNode> tempTreeList = new ArrayList<TreeNode>();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);

				/*
                 * 将同一父ID的tree添加到同一个List中,最后将List放入Map..   arrMap.put(pid, tempTreeList);
				 * 这点虽然不复杂,但这是整个思索的中心,
				 */
                if (pid.equals(0) && (user.getParentId() == null || user.getParentId().equals(0)))
                    tempTreeList.add(createNode(user.getId()));
                else if (!pid.equals(0) && user.getParentId() != null && pid.equals(user.getParentId())) {
                    tempTreeList.add(createNode(user.getId()));
                }
            }

            // 最后将List放入Map..  key就是这组List<TreeNode>父ID, value就是这组List
            arrMap.put(pid, tempTreeList);
        }

        return arrMap;
    }


    /**
     * 让节点与子节点之间彼此关联,并返回全有的根.(没有父节点的都为根)
     * 数据库格式并没有换,只是建立了关联
     *
     * @param arrMap
     */
    public List<TreeNode> MapForTree(Map<Integer, List<TreeNode>> arrMap) {

        //所以pid的集成
        Set<Integer> pidSet = arrMap.keySet();
        //遍历所有的父ID,然后与所以的节点比对,父id与id相同的
        // 找出对应的tree节点,然后将该节点的
        for (Iterator<Integer> it = pidSet.iterator(); it.hasNext(); ) {
            Integer pid = it.next();
            /*
             * 按分组的方式与pid比对.
    		 * 如果找到,那么将该pid分组的List,做为子节点 赋值给该找到的节点的 setChildrenList(list),同时也将找到节点赋值List内所有子节点的parentNode
    		 * 
    		 */
            for (Iterator<Integer> it2 = pidSet.iterator(); it2.hasNext(); ) {
                Integer key = it2.next();
                //不查找自己的分组
//                if (pid.equals(key)) {
//                    //	break;
//                }
                //当前父Id下的所有子集
                List<TreeNode> list = arrMap.get(key);
                //找出对应的tree父节点对象
                TreeNode parentTree = indexOfList(list, pid);
                if (parentTree != null) {
                    //通过pid在Map里找出节点的子节点.
                    List<TreeNode> childrenHereList = arrMap.get(pid);
                    // 把子节点List赋值给Tree节点的Children
                    parentTree.setChildList(childrenHereList);

                    // 与上面相反,这是 把父节点对象赋值给Tree节点的parentNode
                    for (int i = 0; i < childrenHereList.size(); i++) {
                        TreeNode childrenHereTree = childrenHereList.get(i);
                        childrenHereTree.setParentNode(parentTree);
//                        childrenHereTree.setParentId(parentTree.getId());
                    }
                }
            }
        }

        // 找到 childrenHereTree.getParentNode(); 为null的就是根  return rootTreeList
        List<TreeNode> rootTreeList = new ArrayList<TreeNode>();
        for (Iterator<Integer> it2 = pidSet.iterator(); it2.hasNext(); ) {
            Integer key = it2.next();
            List<TreeNode> list = arrMap.get(key);
            for (int i = 0; i < list.size(); i++) {
                TreeNode tree = list.get(i);
                if (null == tree.getParentNode()) {
                    rootTreeList.add(tree);
                }
            }
        }
        return rootTreeList;
    }


    /**
     * No.3:
     * 找出 list 中元素的id与pid相同的节点 的并返回.对应关系为: id与父id相同
     *
     * @param list
     * @param pid
     * @return
     */
    public TreeNode indexOfList(List<TreeNode> list, Integer pid) {
        for (int i = 0; i < list.size(); i++) {
            TreeNode tree = list.get(i);
            if (pid.equals(tree.getId())) {
                return tree;
            }
        }
        return null;
    }


    public void count() {
//        int merchantId = 3677;
//        log.info("获取商家所有用户");
//
//
//        //查询所有用户
//        List<User> users = userRepository.findByMerchantId(merchantId);
//        log.info(users.size());
//
//        log.info("获取顶层的用户数据");
//        //查找顶层用户
//        List<User> top = userRepository.findTop(merchantId);
//
//        log.info("获取上个月的消费数据");
//        List<UserConsumeModel> userConsumeModels = getConsumeList(merchantId);
//
//
//        log.info("创建多叉树，并计算数据");
//        List<TreeNode> treeNodes = new ArrayList<>();
//        //创建TreeNode
//        for (User user : top) {
//            TreeNode treeNode = createNode(user.getId(), getUserMoney(userConsumeModels, user.getId()), null);
//            createNextNode(users, userConsumeModels, treeNode);
//
//            log.info("创建了 " + user.getId() + " 多叉树");
//            countFromLeaf(treeNode);
//            log.info("计算了 " + user.getId() + " 多叉树");
//            treeNodes.add(treeNode);
//        }
//
//        //保存数据
//        log.info("保存数据");
//        treeNodes.forEach(treeNode -> {
//            saveUserShare(treeNode, merchantId);
//        });
    }


    /**
     * 获取消费列表
     *
     * @param merchantId
     * @return
     */
    private List<UserConsumeModel> getConsumeList(int merchantId, List<UserShareAmounted> userShareAmounteds) {
        //查询前一天的消费额
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(2015, 8, 30, 0, 0, 0);
//        beginTime.set(Calendar.MILLISECOND, 0);
//
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(2015, 9, 1, 0, 0, 0);
//        endTime.set(Calendar.MILLISECOND, 0);

//        Date beginTime = DateHelper.getYesterdayBegin();
//        Date endTime = DateHelper.getThisDayBegin();

        //todo 计算起始时间
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 10, 1, 0, 0, 0);
        beginTime.set(Calendar.MILLISECOND, 0);

        log.info("begintime " + beginTime);

        StringBuilder hql = new StringBuilder();
        hql.append("select order.userId,sum(order.price * " + String.valueOf(rate) + ") as amount from Order order " +
                " where order.merchant.id=:merchantId and order.payTime>=:beginTime  and order.payStatus=1 and order.status<>-1 " +
                " group by order.userId");
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchantId);
            query.setParameter("beginTime", beginTime.getTime());
        });

        List<UserConsumeModel> userConsumeModels = new ArrayList<>();
        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            Integer userId = Integer.parseInt(objects[0].toString());
            Float money = MathHelper.doFloat(Float.parseFloat(objects[1].toString()));

            //负数直接赋值0 扣除已经计算的
            float userShareAmounted = getUserShareAmounted(userShareAmounteds, userId);
            if (money - userShareAmounted <= 0)
                money = 0f;
            else
                money = money - userShareAmounted;

            userConsumeModels.add(new UserConsumeModel(userId, money));
        });


        return userConsumeModels;
    }

    private float getUserShareAmounted(List<UserShareAmounted> userShareAmounteds, Integer userId) {
        for (UserShareAmounted userShareAmounted : userShareAmounteds) {
            if (userShareAmounted.getUserId().equals(userId)) {
                return userShareAmounted.getAmount();
            }
        }
        return 0;
    }

    /**
     * 获得指定用户金额
     *
     * @param userConsumeModels
     * @param userId
     * @return
     */
    private float getUserMoney(List<UserConsumeModel> userConsumeModels, Integer userId) {
        float result = 0f;
        for (UserConsumeModel userConsumeModel : userConsumeModels) {
            if (userConsumeModel.getUserId().equals(userId)) {
                result = userConsumeModel.getMoney();
//                log.info(userId + " " + result);
                break;
            }
        }
        return result;
    }


    /**
     * 获取用户的下线
     *
     * @param users
     * @param userId
     * @return
     */
    private List<User> getUserChildren(List<User> users, Integer userId) {
        List<User> result = new ArrayList<>();
        users.forEach(user -> {
            if (user.getParentId() != null && user.getParentId().equals(userId)) {
                result.add(user);
            }
        });
        return result;
    }

    /**
     * 创建下一个节点
     *
     * @param users
     * @param userConsumeModels
     * @param parentNode
     */
//    private void createNextNode(List<User> users, List<UserConsumeModel> userConsumeModels, TreeNode parentNode) {
//        List<User> children = getUserChildren(users, parentNode.getId());
////        List<User> children = userRepository.findChildren(parentNode.getId());
////        List<User> children = new ArrayList<>();
//        for (User user : children) {
//            TreeNode treeNode = createNode(user.getId(), getUserMoney(userConsumeModels, user.getId()), parentNode);
//            parentNode.addChildNode(treeNode);
////            log.info(treeNode.getId());
//            createNextNode(users, userConsumeModels, treeNode);
//        }
//    }

    /**
     * 分摊数据存入数据库
     *
     * @param treeNode
     */
//    private void saveUserShare(TreeNode treeNode, Integer merchantId) {
//        Date time = DateHelper.getThisDayBegin();
//        time.setDate(-1);
//        List<UserShare> result = new ArrayList<>();
//
//        result.add(new UserShare(merchantId
//                , treeNode.getId()
//                , time
//                , treeNode.getPercentFiveAmount()
//                , treeNode.getPercentTwoAmount()
//                , treeNode.getPercentFiveNum()
//                , treeNode.getPercentTwoNum()));
//
//        treeNode.getChildrens().forEach(treeNode1 -> {
//            result.add(new UserShare(merchantId
//                    , treeNode1.getId()
//                    , time
//                    , treeNode1.getPercentFiveAmount()
//                    , treeNode1.getPercentTwoAmount()
//                    , treeNode1.getPercentFiveNum()
//                    , treeNode1.getPercentTwoNum()));
//        });
//
//        userShareRepository.save(result);
//    }


    /**
     * 从叶子节点进行计算
     *
     * @param node
     */
    public List<UserShareAmounted> countFromLeaf(TreeNode node) {
        List<UserShareAmounted> countedUserList = new ArrayList<>();
        //获得叶子节点
        List<TreeNode> leaves = node.getLeaves();
        for (TreeNode treeNode : leaves) {
            treeNode.setCountedPercentFive(true);
            treeNode.setCountedPercentTwo(true);
            treeNode.setPercentFiveAmount(0);
            treeNode.setPercentTwoAmount(0);
            treeNode.setUpPercentFiveAmount(treeNode.getCurrentAmount());
            treeNode.setUpPercentTwoAmount(0);
            treeNode.setPercentFiveNum(0);
            treeNode.setPercentTwoNum(0);
            treeNode.setPercentFiveNumAll(0);
            treeNode.setPercentTwoNumAll(0);

            List<UserShareAmounted> upUserList = new ArrayList<>();
            upUserList.add(new UserShareAmounted(treeNode.getId(), treeNode.getCurrentAmount()));
            treeNode.setUpUserList(upUserList);

            if (treeNode.getParentNode() != null)
                eventCountParentNode(treeNode.getParentNode(), countedUserList);
        }
        return countedUserList;
    }

    /**
     * 触发计算父节点
     */
    private void eventCountParentNode(TreeNode parentNode, List<UserShareAmounted> countedUserList) {
        //子节点是否已经都计算5%
        boolean isChildAllCountFive = true;
        for (TreeNode treeNode : parentNode.getChildList()) {
            if (!treeNode.isCountedPercentFive()) {
                isChildAllCountFive = false;
                break;
            }
        }

        if (isChildAllCountFive) {
            countCurrentNode(parentNode, countedUserList);
        }
    }

    /**
     * 计算当前节点
     * 条件是 子节点都已经完成5%计算
     *
     * @param treeNode
     */
    private void countCurrentNode(TreeNode treeNode, List<UserShareAmounted> countedUserList) {

        float percentFiveBaseAmount = countPercentFiveAmount(treeNode, countedUserList);
        float percentTwoBaseAmount = countPercentTwoAmount(treeNode);
        float upPercentFiveAmount = countUpPercentFiveAmount(treeNode, percentFiveBaseAmount);
        float upPercentTwoAmount = countUpPercentTwoAmount(treeNode, percentFiveBaseAmount, percentTwoBaseAmount);

        treeNode.setCountedPercentFive(true);
        treeNode.setCountedPercentTwo(true);
        treeNode.setPercentFiveAmount(MathHelper.doFloat(percentFiveBaseAmount * 0.05F));
        treeNode.setPercentTwoAmount(MathHelper.doFloat(percentTwoBaseAmount * 0.02F));
        treeNode.setUpPercentFiveAmount(upPercentFiveAmount);
        treeNode.setUpPercentTwoAmount(upPercentTwoAmount);

        if (treeNode.getParentNode() != null)
            eventCountParentNode(treeNode.getParentNode(), countedUserList);

//        treeNode.setTotalAmount(((Double) treeNode.getChildList().stream().mapToDouble(x -> x.getCurrentAmount()).summaryStatistics().getSum()).floatValue()
//                + treeNode.getCurrentAmount());

//        float childTotalAmount = 0f;
//        for (TreeNode x : treeNode.getChildList()) {
//            childTotalAmount += x.getCurrentAmount();
//        }
//        treeNode.setTotalAmount(childTotalAmount + treeNode.getCurrentAmount());
    }

    /**
     * 计算5%的量
     *
     * @param treeNode
     * @return
     */
    private float countPercentFiveAmount(TreeNode treeNode, List<UserShareAmounted> countedUserList) {
        if (treeNode.getId() == 761510) {
            int a = 0;
        }

        float percentFiveBaseAmount = 0;
        List<UserShareRebateSource> userShareRebateSources = new ArrayList<>();
        List<UserShareSource> userShareSources = new ArrayList<>();

        List<UserShareAmounted> upUserList = new ArrayList<>();
        upUserList.add(new UserShareAmounted(treeNode.getId(), treeNode.getCurrentAmount()));
        treeNode.setUpUserList(upUserList);

        for (TreeNode treeNode1 : treeNode.getChildList()) {
            //子项未参与5%计算的总量
            if (treeNode1.getUpPercentFiveAmount() >= startAmount) {
                percentFiveBaseAmount += treeNode1.getUpPercentFiveAmount();

                UserShareRebateSource userShareRebateSource = new UserShareRebateSource();
                userShareRebateSource.setUserId(treeNode.getId());
                userShareRebateSource.setType(1);
                userShareRebateSource.setSourceUserId(treeNode1.getId());
                userShareRebateSource.setAmount(treeNode1.getUpPercentFiveAmount());
                userShareRebateSources.add(userShareRebateSource);

                //记录当前计算
                countedUserList.addAll(treeNode1.getUpUserList());
            } else {
                treeNode.getUpUserList().addAll(treeNode1.getUpUserList());
            }

            UserShareSource userShareSource = new UserShareSource();
            userShareSource.setUserId(treeNode.getId());
            userShareSource.setType(1);
            userShareSource.setSourceUserId(treeNode1.getId());
            userShareSource.setAmount(treeNode1.getUpPercentFiveAmount());
            userShareSources.add(userShareSource);
        }

        treeNode.setSourceFive(userShareRebateSources);
        treeNode.setPercentFiveNum(userShareRebateSources.size());
        treeNode.setSourceFiveAll(userShareSources);
        treeNode.setPercentFiveNumAll(userShareSources.size());

        return percentFiveBaseAmount;
    }


    /**
     * 计算往上贡献的5%的量
     *
     * @param treeNode
     * @param percentFiveBaseAmount
     * @return
     */
    private float countUpPercentFiveAmount(TreeNode treeNode, float percentFiveBaseAmount) {
        float result = treeNode.getCurrentAmount() - percentFiveBaseAmount;
        for (TreeNode treeNode1 : treeNode.getChildList()) {
            result += treeNode1.getUpPercentFiveAmount();
        }
        return result;
    }

    /**
     * 计算2%的量
     *
     * @param treeNode
     * @return
     */
    private float countPercentTwoAmount(TreeNode treeNode) {
        List<UserShareRebateSource> userShareRebateSources = new ArrayList<>();
        List<UserShareSource> userShareSources = new ArrayList<>();

        for (TreeNode treeNode1 : treeNode.getChildList()) {
            if (treeNode1.getUpPercentTwoAmount() > 0) {
                UserShareRebateSource userShareRebateSource = new UserShareRebateSource();
                userShareRebateSource.setUserId(treeNode.getId());
                userShareRebateSource.setType(2);
                userShareRebateSource.setSourceUserId(treeNode1.getId());
                userShareRebateSource.setAmount(treeNode1.getUpPercentTwoAmount());
                userShareRebateSources.add(userShareRebateSource);
            }

            UserShareSource userShareSource = new UserShareSource();
            userShareSource.setUserId(treeNode.getId());
            userShareSource.setType(2);
            userShareSource.setSourceUserId(treeNode1.getId());
            userShareSource.setAmount(treeNode1.getUpPercentTwoAmount());
            userShareSources.add(userShareSource);
        }

        // 奇数个去除最小那个
        if (userShareRebateSources.size() % 2 == 1) {
            Collections.sort(userShareRebateSources, new Comparator<UserShareRebateSource>() {
                @Override
                public int compare(UserShareRebateSource o1, UserShareRebateSource o2) {
                    return o1.getAmount().compareTo(o2.getAmount());
                }
            });
            userShareRebateSources.remove(0);
        }

        treeNode.setSourceTwo(userShareRebateSources);
        treeNode.setPercentTwoNum(userShareRebateSources.size());

        treeNode.setSourceTwoAll(userShareSources);
        treeNode.setPercentTwoNumAll(userShareSources.size());

        return ((Number) userShareRebateSources.stream().mapToDouble(x -> x.getAmount()).summaryStatistics().getSum()).floatValue();
    }

    /**
     * 计算往上贡献2%的量
     *
     * @param treeNode
     * @param percentFiveBaseAmount
     * @param percentTwoBaseAmount
     * @return
     */
    private float countUpPercentTwoAmount(TreeNode treeNode, float percentFiveBaseAmount, float percentTwoBaseAmount) {
        float result = percentFiveBaseAmount - percentTwoBaseAmount;
        for (TreeNode treeNode1 : treeNode.getChildList()) {
            result += treeNode1.getUpPercentTwoAmount();
        }
        return result;
    }

    private TreeNode createNode(Integer id) {
        return createNode(id, 0);
    }

    private TreeNode createNode(Integer id, float amount) {
        return createNode(id, amount, null);
    }

    private TreeNode createNode(Integer id, float amount, TreeNode parentNode) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(id);
        if (parentNode != null) treeNode.setParentId(parentNode.getId());
        treeNode.setParentNode(parentNode);
        treeNode.setChildList(null);
        treeNode.setCurrentAmount(amount);

        treeNode.setCountedPercentFive(false);
        treeNode.setCountedPercentTwo(false);
        treeNode.setPercentFiveAmount(0);
        treeNode.setPercentTwoAmount(0);
        treeNode.setUpPercentFiveAmount(0);
        treeNode.setUpPercentTwoAmount(0);
        treeNode.setPercentFiveNum(0);
        treeNode.setPercentTwoNum(0);

        return treeNode;
    }


    public void outNode() {
//
//        Date time = DateHelper.getThisDayBegin();
//
//        List<UserShare> userShares = userShareRepository.findAll();
//        List<UserShareSource> userShareSources = userShareSourceRepository.findAll();
//
//        StringBuilder hql = new StringBuilder();
//        hql.append("select share.userId from UserShare share left join User user on user.id=share.userId" +
//                "  where share.time=:time and (user.parentId=0 or user.parentId is null)");
//        List list = userRepository.queryHql(hql.toString(), query -> {
//            query.setParameter("time", time);
//        });
//
//        list.forEach(data -> {
//            int userId = Integer.parseInt(data.toString());
//
//
//        });
    }
}
