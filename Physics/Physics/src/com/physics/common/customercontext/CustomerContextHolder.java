package com.physics.common.customercontext;

import org.springframework.util.Assert;

public class CustomerContextHolder {
	private static final ThreadLocal contextHolder = new ThreadLocal();

	/**
	 * 在DAOImpl\MetaDaoImpl中设置isClearCache,
	 * 参数设置值：为 1的时候， 更新操作清空相关联的缓存区；
	 * 为0的时候， 更新操作不更新缓存区，此种情况时针对没有实体对应的实体表的情况
	 * @param isClearCache
	 */
	public static void setCustomerType(String isClearCache) {
	   Assert.notNull(isClearCache, "customerType cannot be null");
	   contextHolder.set(isClearCache);
	}

	public static String getCustomerType() {
	   return (String) contextHolder.get();
	}

	public static void clearCustomerType() {
	   contextHolder.remove();
	}
}
