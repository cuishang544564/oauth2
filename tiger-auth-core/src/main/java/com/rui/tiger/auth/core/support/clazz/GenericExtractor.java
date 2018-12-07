package com.rui.tiger.auth.core.support.clazz;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


/**
 * 泛型工具类
 * 蔡锐  2015年10月18日
 */
public final class GenericExtractor {
    /**
     * 抽取Class上使用的第i个泛型类
     *
     * @param type
     * @param i
     * @return Class<?>
     */
    public static Class<?> getClass(final Type type, final int i) {//IStrategyContainer<DataPermission,IQueryCustmoeerStragtegy>
        if (type instanceof ParameterizedType) {//是参数化类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            final TypeVariable<?> typeVariable = (TypeVariable<?>) type;
            return getClass(((TypeVariable) type).getBounds()[0], 0);
        } else {
            return (Class<?>) type;
        }
    }

    /**
     * ParameterizedType 表示参数化类型，如 Collection<String>。
     *
     * @param parameterizedType
     * @param i
     * @return Class<?>
     */
    private static Class<?> getGenericClass(final ParameterizedType parameterizedType, final int i) {
        //getActualTypeArguments 返回表示此类型实际类型参数的 Type 对象的数组。
        final Type genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) {
            // getRawType() 返回 Type 对象，表示声明此类型的类或接口。
            return (Class<?>) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) {//表示一种数组类型，其组件类型为参数化类型或类型变量。
            return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();// 返回表示此数组的组件类型的 Type 对象。
        } else if (genericClass instanceof TypeVariable) {//TypeVariable是各种类型变量的公共高级接口
            final TypeVariable<?> typeVariable = (TypeVariable<?>) genericClass;
            final Type bound = typeVariable.getBounds()[0];//返回表示此类型变量上边界的 Type 对象的数组
            return getClass(bound, 0);
        } else {
            return (Class<?>) genericClass;
        }
    }
}
