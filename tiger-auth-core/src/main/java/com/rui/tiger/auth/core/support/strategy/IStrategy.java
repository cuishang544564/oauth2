package com.rui.tiger.auth.core.support.strategy;

/**
 * 蔡锐  2015年10月18日
 */

public interface IStrategy<C> {

    /**
     * 获得策略条件
     *
     * @param
     * @return 用来注册的策略处理条件
     */
    C getCondition();
}
