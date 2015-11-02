/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.model;

import com.huotu.huobanmallcount.entity.UserShareAmounted;
import com.huotu.huobanmallcount.entity.UserShareRebateSource;
import com.huotu.huobanmallcount.entity.UserShareSource;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by lgh on 2015/10/9.
 */
public class TreeNode implements Serializable {
    private Integer id;
    private Integer parentId = 0;
    protected TreeNode parentNode;
    protected List<TreeNode> childList;

//    private int parentId;
//    protected String nodeName;
//    protected Object obj;

    /**
     * 当前成员的消费值
     */
    private float currentAmount;

    /**
     * 总值
     * 含所有下线和自身
     */
//    private float totalAmount;


    /**
     * 往上贡献的5%值（用于上线5%的计算）
     * <p>
     * 本身的值 + 下级的往上贡献的5%值（upPercentFiveAmount）的总和 - 本身已计算的5%基础值
     */
    private float upPercentFiveAmount;

    /**
     * 是否计算5%
     */
    private boolean countedPercentFive;

    /**
     * 5%的值
     * 对象:单元下线
     * 条件:指定阀值（如5万）
     * 以单元下线的往上贡献的5%值计算（即upPercentFiveAmount）
     * 同时记录满足条件upUserList 放入临时变量countedUserList
     */
    private float percentFiveAmount;

    /**
     * 往上计算的用户
     * 计算前提：计算5%值的时候计算
     * 本身用户 + （不参与当前5%计算的）下级的往上计算的用户总和
     */
    private List<UserShareAmounted> upUserList;


    /**
     * 按5%计算的数量
     */
    private Integer percentFiveNum;

    /**
     * 往上贡献的2%值（用于上线2%的计算）
     * 本身5%的基础值，单元下线（未参与2%计算）的往上贡献的2%值的总和（percentFiveAmount/0.05 + sum(upPercentTwoAmount)-percentTwoAmount/0.02）
     */
    private float upPercentTwoAmount;

    /**
     * 是否计算2%
     */
    private boolean countedPercentTwo;

    /**
     * 2%的值
     * 对象:单元下线
     * 条件:以单元下线的往上贡献的2%值计算（即upPercentTwoAmount）
     * 大于等于2个，如奇数个去除最小的那个
     */
    private float percentTwoAmount;

    /**
     * 按2%计算的数量
     */
    private Integer percentTwoNum;

    /**
     * 5%来源用户
     */
    private List<UserShareRebateSource> sourceFive = new ArrayList<>();

    /**
     * 2%来源用户
     */
    private List<UserShareRebateSource> sourceTwo = new ArrayList<>();

    /**
     * 按5%计算的数量全部
     */
    private Integer percentFiveNumAll;

    /**
     * 按2%计算的数量全部
     */
    private Integer percentTwoNumAll;

    /**
     * 5%来源用户全部
     */
    private List<UserShareSource> sourceFiveAll = new ArrayList<>();

    /**
     * 2%来源用户全部
     */
    private List<UserShareSource> sourceTwoAll = new ArrayList<>();


    public TreeNode() {
        initChildList();
    }

//    public TreeNode(TreeNode parentNode) {
//        this.getParentNode();
//        initChildList();
//    }


    /**
     * 是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        if (childList == null) {
            return true;
        } else {
            if (childList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void initChildList() {
        if (childList == null)
            childList = new ArrayList<TreeNode>();
    }

    /**
     * 插入一个child节点到当前节点中
     *
     * @param treeNode
     */
    public void addChildNode(TreeNode treeNode) {
        initChildList();
        childList.add(treeNode);
    }


    /**
     * 返回当前节点的父辈节点集合
     *
     * @return
     */
//    public List<TreeNode> getParents() {
//        List<TreeNode> result = new ArrayList<TreeNode>();
//        TreeNode parentNode = this.getParentNode();
//        if (parentNode == null) {
//            return result;
//        } else {
//            result.add(parentNode);
//            result.addAll(parentNode.getParents());
//            return result;
//        }
//    }

    /**
     * 返回当前节点的晚辈集合
     *
     * @return
     */
    public List<TreeNode> getChildrens() {
        List<TreeNode> result = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildList();
        if (childList == null) {
            return result;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode treeNode = childList.get(i);
                result.add(treeNode);
                result.addAll(treeNode.getChildrens());
            }
            return result;
        }
    }


    /**
     * 删除节点和它下面的晚辈
     */
    public void deleteNode() {
        TreeNode parentNode = this.getParentNode();
        int id = this.getId();

        if (parentNode != null) {
            parentNode.deleteChildNode(id);
        }
    }

    /**
     * 删除当前节点的某个子节点
     *
     * @param childId
     */
    public void deleteChildNode(int childId) {
        List<TreeNode> childList = this.getChildList();
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            if (child.getId() == childId) {
                childList.remove(i);
                return;
            }
        }
    }

    /**
     * 动态的插入一个新的节点到当前树中
     * 会根据父节点动态的找到位置
     *
     * @param treeNode
     * @return
     */
    public boolean insertNode(TreeNode treeNode) {
        if (this.parentNode != null && this.parentNode.getId() == treeNode.parentNode.getId()) {
            addChildNode(treeNode);
            return true;
        } else {
            List<TreeNode> childList = this.getChildList();
            int childNumber = childList.size();
            boolean insertFlag;

            for (int i = 0; i < childNumber; i++) {
                TreeNode childNode = childList.get(i);
                insertFlag = childNode.insertNode(treeNode);
                if (insertFlag == true)
                    return true;
            }
            return false;
        }
    }

    /**
     * 找到一颗树中某个节点
     *
     * @param id
     * @return
     */
    public TreeNode findTreeNodeById(int id) {
        if (this.id == id)
            return this;
        if (childList.isEmpty() || childList == null) {
            return null;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode child = childList.get(i);
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }

    /**
     * 获取所有的叶子节点
     *
     * @return
     */
    public List<TreeNode> getLeaves() {
        List<TreeNode> result = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildList();
        if (childList == null) {
            result.add(this);
            return result;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode treeNode = childList.get(i);
                if (treeNode.getChildList() != null && treeNode.getChildList().size() > 0)
                    result.addAll(treeNode.getLeaves());
                else
                    result.add(treeNode);
            }
            return result;
        }
    }


    /**
     * 遍历一棵树，层次遍历
     */
    public void traverse() {
        if (id < 0) return;

        print(id + " " + currentAmount + " " + percentFiveAmount + " " + percentTwoAmount);
//        print(id + " " + currentAmount + " " + percentFiveAmount + " " + percentTwoAmount);
//        print("upfive " + upPercentFiveAmount + " uptwo " + upPercentTwoAmount);
//        for (UserShareRebateSource userShareSource : sourceFive) {
//            print(" five " + userShareSource.getSourceUserId() + " " + userShareSource.getAmount());
//        }
//
//        for (UserShareRebateSource userShareSource : sourceTwo) {
//            print(" two " + userShareSource.getSourceUserId() + " " + userShareSource.getAmount());
//        }


        if (childList == null || childList.isEmpty())
            return;
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            child.traverse();
        }
    }

    public void print(String content) {
        System.out.println(content);
    }

    public void print(int content) {
        System.out.println(String.valueOf(content));
    }

    /**
     * 返回当前节点的孩子集合
     *
     * @return
     */
    public List<TreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }

//    public int getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(int parentId) {
//        this.parentId = parentId;
//    }


    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(float currentAmount) {
        this.currentAmount = currentAmount;
    }

//    public float getTotalAmount() {
//        return totalAmount;
//    }

//    public void setTotalAmount(float totalAmount) {
//        this.totalAmount = totalAmount;
//    }

    public boolean isCountedPercentFive() {
        return countedPercentFive;
    }

    public void setCountedPercentFive(boolean countedPercentFive) {
        this.countedPercentFive = countedPercentFive;
    }

    public float getUpPercentFiveAmount() {
        return upPercentFiveAmount;
    }

    public void setUpPercentFiveAmount(float upPercentFiveAmount) {
        this.upPercentFiveAmount = upPercentFiveAmount;
    }

    public float getPercentFiveAmount() {
        return percentFiveAmount;
    }

    public void setPercentFiveAmount(float percentFiveAmount) {
        this.percentFiveAmount = percentFiveAmount;
    }

    public boolean isCountedPercentTwo() {
        return countedPercentTwo;
    }

    public void setCountedPercentTwo(boolean countedPercentTwo) {
        this.countedPercentTwo = countedPercentTwo;
    }

    public float getPercentTwoAmount() {
        return percentTwoAmount;
    }

    public void setPercentTwoAmount(float percentTwoAmount) {
        this.percentTwoAmount = percentTwoAmount;
    }

    public Integer getPercentFiveNum() {
        return percentFiveNum;
    }

    public void setPercentFiveNum(Integer percentFiveNum) {
        this.percentFiveNum = percentFiveNum;
    }

    public float getUpPercentTwoAmount() {
        return upPercentTwoAmount;
    }

    public void setUpPercentTwoAmount(float upPercentTwoAmount) {
        this.upPercentTwoAmount = upPercentTwoAmount;
    }

    public Integer getPercentTwoNum() {
        return percentTwoNum;
    }

    public void setPercentTwoNum(Integer percentTwoNum) {
        this.percentTwoNum = percentTwoNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<UserShareRebateSource> getSourceFive() {
        return sourceFive;
    }

    public void setSourceFive(List<UserShareRebateSource> sourceFive) {
        this.sourceFive = sourceFive;
    }

    public List<UserShareRebateSource> getSourceTwo() {
        return sourceTwo;
    }

    public void setSourceTwo(List<UserShareRebateSource> sourceTwo) {
        this.sourceTwo = sourceTwo;
    }

    public List<UserShareAmounted> getUpUserList() {
        return upUserList;
    }

    public void setUpUserList(List<UserShareAmounted> upUserList) {
        this.upUserList = upUserList;
    }

    public List<UserShareSource> getSourceFiveAll() {
        return sourceFiveAll;
    }

    public void setSourceFiveAll(List<UserShareSource> sourceFiveAll) {
        this.sourceFiveAll = sourceFiveAll;
    }

    public List<UserShareSource> getSourceTwoAll() {
        return sourceTwoAll;
    }

    public void setSourceTwoAll(List<UserShareSource> sourceTwoAll) {
        this.sourceTwoAll = sourceTwoAll;
    }

    public Integer getPercentFiveNumAll() {
        return percentFiveNumAll;
    }

    public void setPercentFiveNumAll(Integer percentFiveNumAll) {
        this.percentFiveNumAll = percentFiveNumAll;
    }

    public Integer getPercentTwoNumAll() {
        return percentTwoNumAll;
    }

    public void setPercentTwoNumAll(Integer percentTwoNumAll) {
        this.percentTwoNumAll = percentTwoNumAll;
    }


//    public String getNodeName() {
//        return nodeName;
//    }
//
//    public void setNodeName(String nodeName) {
//        this.nodeName = nodeName;
//    }
//
//    public Object getObj() {
//        return obj;
//    }
//
//    public void setObj(Object obj) {
//        this.obj = obj;
//    }
}

