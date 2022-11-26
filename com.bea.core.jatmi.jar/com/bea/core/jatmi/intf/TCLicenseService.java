package com.bea.core.jatmi.intf;

public interface TCLicenseService {
   void shutdown(int var1);

   boolean isTCLicensed();

   boolean updateLicenseInformation();

   int updateInstalledEncryptionInfo();

   int getInstalledEncryption();

   int decideEncryptionLevel(int var1, int var2, int var3);

   int acceptEncryptionLevel(int var1, int var2, int var3, int var4);
}
