package com.bea.common.security.internal.legacy.helper;

import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import weblogic.management.security.RealmMBean;

class BaseServicesConfigImpl {
   private RealmMBean realmMBean;
   private SecurityProviderConfigHelper providerHelper;

   BaseServicesConfigImpl(RealmMBean realmMBean) {
      this.realmMBean = realmMBean;
      this.providerHelper = null;
   }

   BaseServicesConfigImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      this.realmMBean = realmMBean;
      this.providerHelper = providerHelper;
   }

   protected RealmMBean getRealmMBean() {
      return this.realmMBean;
   }

   protected SecurityProviderConfigHelper getSecurityProviderConfigHelper() {
      return this.providerHelper;
   }
}
