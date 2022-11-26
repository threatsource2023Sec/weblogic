package com.bea.common.security.legacy;

import com.bea.common.security.utils.LegacyEncryptorKey;

public interface LegacyDomainInfo {
   LegacyEncryptorKey getLegacyEncryptorKey();

   String getDomainName();

   String getServerName();

   String getRootDirectory();

   boolean getProductionModeEnabled();

   boolean getSecureModeEnabled();

   boolean getWebAppFilesCaseInsensitive();

   byte[] getDomainCredential();

   boolean getManagementModificationsSupported();
}
