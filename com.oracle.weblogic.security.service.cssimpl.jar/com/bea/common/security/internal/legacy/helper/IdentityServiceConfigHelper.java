package com.bea.common.security.internal.legacy.helper;

import weblogic.management.security.RealmMBean;

class IdentityServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return IdentityServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }
}
