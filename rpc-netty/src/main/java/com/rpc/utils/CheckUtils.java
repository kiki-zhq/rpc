package com.rpc.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 集合工具类
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
public class CheckUtils {


    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     * @author zhq
     * @since 2021/6/7 4:53 下午
     */
    public static <T> boolean isCollectionEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断列表是否为空
     *
     * @param list 列表
     * @return 是否为空
     * @author zhq
     * @since 2021/6/7 4:53 下午
     */
    public static <T> boolean isListEmpty(List<T> list) {
        return isCollectionEmpty(list);
    }

    /**
     * 判断set是否为空
     *
     * @param set set
     * @return 是否为空
     * @author zhq
     * @since 2021/6/7 4:56 下午
     */
    public static <T> boolean isSetEmpty(Set<T> set) {
        return isCollectionEmpty(set);
    }

    /**
     * 判断是否为空
     *
     * @param element 元素
     * @return 是否为空
     * @author zhq
     * @since 2021/6/7 4:58 下午
     */
    public static <E> boolean isEmpty(E element) {
        return element == null;
    }

}
