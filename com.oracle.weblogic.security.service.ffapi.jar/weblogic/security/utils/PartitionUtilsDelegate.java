package weblogic.security.utils;

import weblogic.management.configuration.ConfigurationMBean;

public interface PartitionUtilsDelegate {
   String getPartitionName();

   String getRealmName(String var1, ConfigurationMBean var2);

   String getPrimaryIdentityDomain(String var1);

   String getAdminIdentityDomain();

   String getCurrentIdentityDomain();
}
