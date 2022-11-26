package weblogic.diagnostics.snmp.agent.monfox;

import java.security.NoSuchAlgorithmException;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.v3.usm.USMUtil;
import monfox.toolkit.snmp.v3.usm.ext.UsmUserSecurityExtension;
import weblogic.diagnostics.debug.DebugLogger;

public class WLSUserInfo implements UsmUserSecurityExtension.UserInfo {
   private SnmpEngineID engineID;
   private String userName;
   private int secLevel;
   private int authProtocol;
   private int privProtocol;
   private byte[] localizedAuthKey;
   private byte[] localizedPrivKey;
   private boolean nonExistentUser;
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");

   public WLSUserInfo(SnmpEngineID engine_id, String user_name, int sec_level, int auth_protocol, int priv_protocol, byte[] auth_password, byte[] priv_password, boolean nonExistentUser) throws NoSuchAlgorithmException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Creating WLSUserInfo: user_name=" + user_name + " sec_level=" + sec_level + " auth_protocol=" + auth_protocol + " priv_protocol=" + priv_protocol);
      }

      this.engineID = engine_id;
      this.userName = user_name;
      this.secLevel = sec_level;
      this.authProtocol = auth_protocol;
      this.privProtocol = priv_protocol;
      this.nonExistentUser = nonExistentUser;
      byte[] priv_key;
      if (auth_password != null && auth_password.length > 0) {
         priv_key = USMUtil.generateKey(auth_password, auth_protocol);
         this.localizedAuthKey = USMUtil.localizeKey(priv_key, engine_id.toByteArray(), auth_protocol);
      }

      if (priv_password != null && priv_password.length > 0) {
         priv_key = USMUtil.generateKey(priv_password, auth_protocol);
         this.localizedPrivKey = USMUtil.localizeKey(priv_key, engine_id.toByteArray(), auth_protocol);
      }

   }

   public String toString() {
      return this.userName;
   }

   public SnmpEngineID getEngineID() {
      return this.engineID;
   }

   public String getUserName() {
      return this.userName;
   }

   public byte[] getLocalizedAuthKey() {
      return this.localizedAuthKey;
   }

   public byte[] getLocalizedPrivKey() {
      return this.localizedPrivKey;
   }

   public int getAuthProtocol() {
      return this.authProtocol;
   }

   public int getPrivProtocol() {
      return this.privProtocol;
   }

   public int getSecLevel() {
      return this.secLevel;
   }

   public boolean isNonExistentUser() {
      return this.nonExistentUser;
   }
}
