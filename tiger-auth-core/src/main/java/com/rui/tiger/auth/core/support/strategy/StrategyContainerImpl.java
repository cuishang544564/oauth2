package com.rui.tiger.auth.core.support.strategy;

import com.rui.tiger.auth.core.support.clazz.GenericExtractor;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 抽象策略上下文对象
 * 蔡锐  2015年10月18日
 *
 * @param <C> 注册条件
 * @param <S> 策略类型
 */

public abstract class StrategyContainerImpl<C, S extends IStrategy<C>> implements IStrategyContainer<C, S> {

	public static <D, T extends IStrategy<D>> T getStrategy(final Class<T> strategyClass, final D condition) {
		final Collection<T> strategys = ManageSpringBeans.getBeans(strategyClass).values();
		for (final T t : strategys) {
			if (t.getCondition() == condition) {
				return t;
			}
		}
		return null;
	}

	private final Map<C, S> strategyMap = new HashMap<C, S>();//策略map

	/**
	 * 获取策略实现类
	 */
	@Override
	public S getStrategy(C condition) {
		if (!strategyMap.containsKey(condition)) {
			initStrategyBean();//初始化具体的策略实现类
		}
		return strategyMap.get(condition);

	}

	/**
	 * 从spring容器中直接获取策略处理器
	 */
	protected synchronized void initStrategyBean() {
		if (strategyMap.size() > 0) {
			//log error
			return;
		}
		//
		Assert.isTrue(this.getClass() != StrategyContainerImpl.class, "该方法无法直接应用于StrategyContainerImpl对象");

		@SuppressWarnings("unchecked")
		//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。getGenericSuperclass
		//Type 是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
		final Class<S> strategyClass = (Class<S>) GenericExtractor.getClass(this.getClass().getGenericSuperclass(), 1);//
		/*
		 * 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。如果是则返回 true，否则返回 false。
		 * IStrategy.class.isAssignableFrom(strategyClass)  前面是后面的父类或接口 或相同
		 */
		Assert.isTrue(IStrategy.class.isAssignableFrom(strategyClass));
		@SuppressWarnings("unchecked")        final Class<C> conditionClass = (Class<C>) GenericExtractor.getClass(this.getClass().getGenericSuperclass(), 0);
		final Collection<S> strategys = ManageSpringBeans.getBeans(strategyClass).values();
		//log
		for (final S s : strategys) {
			//log debug
			Assert.isTrue(!strategyMap.containsKey(s.getCondition()),
					"该类型已经被注册过[" + s.getCondition() + "][" + s + "]");
			strategyMap.put(s.getCondition(), s);
		}
	}

	protected Map<C, S> getStrategyMap() {
		return strategyMap;
	}


}
