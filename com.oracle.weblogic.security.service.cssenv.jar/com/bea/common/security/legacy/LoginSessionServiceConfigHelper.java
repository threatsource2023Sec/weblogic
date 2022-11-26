package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;
import weblogic.management.security.RealmMBean;

public interface LoginSessionServiceConfigHelper {
   String getLoginSessionServiceName(RealmMBean var1);

   void addToConfig(ServiceEngineConfig var1, String var2, RealmMBean var3);
}
