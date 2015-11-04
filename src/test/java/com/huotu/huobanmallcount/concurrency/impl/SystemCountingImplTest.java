/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.concurrency.impl;

import com.huotu.huobanmallcount.bootconfig.MvcConfig;
import com.huotu.huobanmallcount.bootconfig.RootConfig;
import com.huotu.huobanmallcount.common.DateHelper;
import com.huotu.huobanmallcount.concurrency.SystemCounting;
import com.huotu.huobanmallcount.entity.Merchant;
import com.huotu.huobanmallcount.entity.Order;
import com.huotu.huobanmallcount.entity.User;
import com.huotu.huobanmallcount.entity.UserShareSource;
import com.huotu.huobanmallcount.model.TreeNode;
import com.huotu.huobanmallcount.repository.MerchantRepository;
import com.huotu.huobanmallcount.repository.OrderRepository;
import com.huotu.huobanmallcount.repository.UserRepository;
import com.huotu.huobanmallcount.repository.UserShareAmountedRepository;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by lgh on 2015/10/10.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class SystemCountingImplTest extends TestCase {

    @Qualifier(value = "SystemMonthCountingImpl")
    @Autowired
    private SystemCounting systemCounting;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserShareAmountedRepository userShareAmountedRepository;

    /**
     * 测试关系情况，是否有死循环
     *
     * @throws Exception
     */
    @Test
    public void testRelation() throws Exception {
        checkRelation();
    }

    public void checkRelation() {
        List<User> users = userRepository.findAll();

        List<User> top = userRepository.findTop();
//        users.stream().filter(x -> x.getParentId() == 0).forEach(top::add);
        List<Integer> errors = new ArrayList<>();

        for (User user : top) {
            List<Integer> fliter = new ArrayList<>();
            fliter.add(user.getId());
            checkRelationNext(users, user, fliter, errors);
            System.out.println(user.getId() + " finished");
        }

        System.out.println("错误总数：" + errors.size());
    }

    private void checkRelationNext(List<User> users, User user, List<Integer> fliter, List<Integer> errors) {
        List<User> list = new ArrayList<>();
        for (User user2 : users) {
            if (user2.getParentId() == user.getId()) list.add(user2);
        }

        for (User user1 : list) {
            if (fliter.contains(user1.getId())) {
                System.out.println("错误用户:" + user1.getId());
                errors.add(user1.getId());
            } else {
                fliter.add(user1.getId());
                checkRelationNext(users, user1, fliter, errors);
            }
        }
    }


    private TreeNode generateTreeNode1() {
        TreeNode A1 = createNode(1, 20f*500, null);
        TreeNode A2 = createNode(2, 10f*500, A1);
        TreeNode A3 = createNode(3, 100f*500, A1);
        A1.addChildNode(A2);
        A1.addChildNode(A3);

        TreeNode A4 = createNode(4, 50f*500, A2);
        TreeNode A5 = createNode(5, 80f*500, A2);
        TreeNode A6 = createNode(6, 120f*500, A2);
        TreeNode A11 = createNode(11, 30f*500, A2);
        A2.addChildNode(A4);
        A2.addChildNode(A5);
        A2.addChildNode(A6);
        A2.addChildNode(A11);

        TreeNode A7 = createNode(7, 100f*500, A4);
        TreeNode A8 = createNode(8, 20f*500, A4);
        A4.addChildNode(A7);
        A4.addChildNode(A8);

        TreeNode A9 = createNode(9, 100f*500, A5);
        TreeNode A10 = createNode(10, 30f*500, A5);
        A5.addChildNode(A9);
        A5.addChildNode(A10);

        TreeNode A12 = createNode(12, 100f*500, A6);
        A6.addChildNode(A12);

        TreeNode A13 = createNode(13, 100f*500, A3);
        A3.addChildNode(A13);
        return A1;

    }

    /**
     * 模拟数据测试
     *
     * @throws Exception
     */
    @Test
    public void testSimulate() throws Exception {
        System.out.println(new Date());
        TreeNode node = generateTreeNode1();
        System.out.println(new Date());


        System.out.println("数据创建完毕,开始计算");
        systemCounting.countFromLeaf(node);
        System.out.println(new Date());
        node.traverse();
    }

    /**
     * 模拟二次计算过程
     *
     * @throws Exception
     */
    @Test
    public void testSimulate2() throws Exception {
        System.out.println(new Date());
        /**
         * 数据翻倍
         */
        TreeNode root = generateTreeNode1();
        root.setCurrentAmount(root.getCurrentAmount() * 2);
        for (TreeNode treeNode : root.getChildrens()) {
            treeNode.setCurrentAmount(treeNode.getCurrentAmount() * 2);
        }

        System.out.println(new Date());


        System.out.println("数据创建完毕,开始计算");
        systemCounting.countFromLeaf(root);
        System.out.println(new Date());
        root.traverse();
    }

//    private String generateMobile()
//    {
//        Random random = new Random();
//        String number = "" + random.nextLong(111111111L);
//        number = number.substring(number.length() - 11);
//    }


    /**
     * 创建模拟数据进行处理
     */
    @Test
    @Rollback(value = false)
    public void testSimulate3() {
        //创建商家
//        Merchant merchant = new Merchant();
//        merchant.setName(UUID.randomUUID().toString());
//        merchant = merchantRepository.saveAndFlush(merchant);
//
//        String userName = "00011111111";
//        //创建用户
//        User user1 = new User("1_" + userName, 0, merchant, "", "", "", "", "", 0);
//        user1 = userRepository.saveAndFlush(user1);
//
//        User user2 = new User("2_" + userName, user1.getId(), merchant, "", "", "", "", "", 0);
//        user2 = userRepository.saveAndFlush(user2);
//
//        User user3 = new User("3_" + userName, user1.getId(), merchant, "", "", "", "", "", 0);
//        user3 = userRepository.saveAndFlush(user3);
//
//
//        User user4 = new User("4_" + userName, user2.getId(), merchant, "", "", "", "", "", 0);
//        user4 = userRepository.saveAndFlush(user4);
//
//        User user5 = new User("5_" + userName, user2.getId(), merchant, "", "", "", "", "", 0);
//        user5 = userRepository.saveAndFlush(user5);
//
//        User user6 = new User("6_" + userName, user2.getId(), merchant, "", "", "", "", "", 0);
//        user6 = userRepository.saveAndFlush(user6);
//
//        User user11 = new User("11_" + userName, user2.getId(), merchant, "", "", "", "", "", 0);
//        user11 = userRepository.saveAndFlush(user11);
//
//
//        User user7 = new User("7_" + userName, user4.getId(), merchant, "", "", "", "", "", 0);
//        user7 = userRepository.saveAndFlush(user7);
//
//        User user8 = new User("8_" + userName, user4.getId(), merchant, "", "", "", "", "", 0);
//        user8 = userRepository.saveAndFlush(user8);
//
//
//        User user9 = new User("9_" + userName, user5.getId(), merchant, "", "", "", "", "", 0);
//        user9 = userRepository.saveAndFlush(user9);
//
//        User user10 = new User("10_" + userName, user5.getId(), merchant, "", "", "", "", "", 0);
//        user10 = userRepository.saveAndFlush(user10);
//
//        User user12 = new User("12_" + userName, user6.getId(), merchant, "", "", "", "", "", 0);
//        user12 = userRepository.saveAndFlush(user12);
//
//
//        User user13 = new User("13_" + userName, user3.getId(), merchant, "", "", "", "", "", 0);
//        user13 = userRepository.saveAndFlush(user13);


        int merchantId = 5691;
        Merchant merchant = merchantRepository.findOne(merchantId);

        Date payTime = new Date();

        List<Order> orders = new ArrayList<>();
        //创建订单
        orders.add(createOrder(merchant, 1, 20, payTime));
        orders.add(createOrder(merchant, 2, 0, payTime));
        orders.add(createOrder(merchant, 3, 100, payTime));
        orders.add(createOrder(merchant, 4, 50, payTime));
        orders.add(createOrder(merchant, 5, 80, payTime));
        orders.add(createOrder(merchant, 6, 120, payTime));
        orders.add(createOrder(merchant, 7, 100, payTime));
        orders.add(createOrder(merchant, 8, 20, payTime));
        orders.add(createOrder(merchant, 9, 100, payTime));
        orders.add(createOrder(merchant, 10, 30, payTime));
        orders.add(createOrder(merchant, 11, 30, payTime));
        orders.add(createOrder(merchant, 12, 100, payTime));
        orders.add(createOrder(merchant, 13, 100, payTime));

        orderRepository.save(orders);

        systemCounting.countMerchant(merchantId);
    }

    private Order createOrder(Merchant merchant, Integer userId, float price, Date payTime) {
        String userName = "00011111111";
        User user = userRepository.findByUsername(userId.toString() + "_" + userName);
        Random random = new Random();
        Order order = new Order();
        order.setId(createOrderNo(random));
        order.setUserId(user.getId());
        order.setMerchant(merchant);
        order.setIsTax(1);
        order.setTime(new Date());
        order.setAmount(1);
        order.setDeliverStatus(1);
        order.setPayStatus(1);
        order.setStatus(1);
        order.setIsProtect(1);
        order.setPrice(price);
        order.setPayTime(payTime);
        order.setUserType(1);
        order.setTitle("");
        order.setReceivestatus(1);
        order.setReceiver("");
        return order;
    }

    private String createOrderNo(Random random) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime()) + random.nextInt(1000000);
    }

    private TreeNode generateTreeNode() {
        //模拟node
        TreeNode parentNode = createNode(1, 10, null);
        generateTreeNodeNext(parentNode, 1, 1);
        return parentNode;
    }

    private void generateTreeNodeNext(TreeNode treeNode, Integer level, Integer id) {
        if (level <= 4) {
            Random random = new Random();
            for (int m = 1; m <= 10; m++) {
                id += 1;
                TreeNode treeNode1 = createNode(id, m + level + 0.2545F, treeNode);
                treeNode.addChildNode(treeNode1);
                generateTreeNodeNext(treeNode1, level + 1, id);

            }
        }
    }

    private TreeNode createNode(Integer id, float amount, TreeNode parentNode) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(id);
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



    @Test
    public void t1() {
        List<UserShareSource> userShareSources = new ArrayList<>();
        UserShareSource userShareSource = new UserShareSource();
        userShareSource.setUserId(1);
        userShareSource.setType(2);
        userShareSource.setSourceUserId(6);
        userShareSource.setAmount(110f);
        userShareSources.add(userShareSource);

        userShareSource = new UserShareSource();
        userShareSource.setUserId(1);
        userShareSource.setType(2);
        userShareSource.setSourceUserId(7);
        userShareSource.setAmount(120f);
        userShareSources.add(userShareSource);

        userShareSource = new UserShareSource();
        userShareSource.setUserId(1);
        userShareSource.setType(2);
        userShareSource.setSourceUserId(8);
        userShareSource.setAmount(100f);
        userShareSources.add(userShareSource);

        // 奇数个去除最小那个
        if (userShareSources.size() % 2 == 1) {
            Collections.sort(userShareSources, new Comparator<UserShareSource>() {
                @Override
                public int compare(UserShareSource o1, UserShareSource o2) {
                    return o1.getAmount().compareTo(o2.getAmount());
                }
            });
            userShareSources.remove(0);
        }

        System.out.println(userShareSources.size());
    }

    @Test
    public void t3()
    {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DATE));


        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 0, 1, 0, 0, 0);
        beginTime.set(Calendar.MILLISECOND, 0);
        System.out.println("begintime " + beginTime.getTime());

        Date day = DateHelper.getThisDayBegin();
        System.out.println(day.getDay());
    }
    /**
     * 计算真实数据 优化后
     *
     * @throws Exception
     */
    @Test
    @Rollback(value = false)
    public void testCount2() throws Exception {

//        Date endTime = DateHelper.getThisMonthBegin();
//        System.out.println(endTime);
//        Date beginTime = DateHelper.getThisMonthBegin();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(beginTime);
//        calendar.add(Calendar.MONTH,-1);
//        System.out.println(calendar.getTime());

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2015, 8, 30, 0, 0, 0);
//        calendar1.set(Calendar.DAY_OF_MONTH, 30);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2015, 9, 1, 0, 0, 0);


        System.out.println(calendar1.getTime());
        System.out.println(calendar2.getTime());
        systemCounting.countAll();
    }


    @Repeat(3)
    @Test
    public void x1()
    {


    }
}