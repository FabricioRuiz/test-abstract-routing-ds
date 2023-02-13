package com.example.testards;

public class MultitenantContext {
	private static ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

	public static String getCurrentTenant() {
		if(CURRENT_TENANT.get() == null){
			return Schema.user.name();
		}
		return CURRENT_TENANT.get();
	}

	public static void setCurrentTenant(String tenant) {
		CURRENT_TENANT.set(tenant);
	}

	public static void clear() {
		CURRENT_TENANT.set(null);
	}
}