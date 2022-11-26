package monfox.toolkit.snmp.v3.usm.ext;

import monfox.toolkit.snmp.engine.SnmpEngineID;

public interface UsmUserSecurityExtension {
   UserInfo getUserInfo(String var1, SnmpEngineID var2);

   public interface UserInfo {
      SnmpEngineID getEngineID();

      String getUserName();

      byte[] getLocalizedPrivKey();

      byte[] getLocalizedAuthKey();

      int getAuthProtocol();

      int getPrivProtocol();

      int getSecLevel();
   }
}
