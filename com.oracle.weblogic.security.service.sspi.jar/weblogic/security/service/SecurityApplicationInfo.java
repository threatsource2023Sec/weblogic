package weblogic.security.service;

import weblogic.security.spi.ApplicationInfo;

public interface SecurityApplicationInfo extends ApplicationInfo {
   String getSecurityDDModel();

   boolean isValidateDDSecurityData();
}
