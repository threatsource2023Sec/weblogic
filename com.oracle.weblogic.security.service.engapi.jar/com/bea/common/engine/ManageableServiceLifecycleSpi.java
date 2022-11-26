package com.bea.common.engine;

public interface ManageableServiceLifecycleSpi extends ServiceLifecycleSpi {
   Object getManagementObject();
}
