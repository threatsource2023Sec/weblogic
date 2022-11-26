package weblogic.management.patching;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum ExtensionPoint {
   EXTENSION_POINT_ONLINE_BEFORE_UPDATE("ep_OnlineBeforeUpdate", "ONLINE"),
   EXTENSION_POINT_EACH_NODE("ep_EachNode", "ONLINE"),
   EXTENSION_POINT_OFFLINE_BEFORE_UPDATE("ep_OfflineBeforeUpdate", "OFFLINE"),
   EXTENSION_POINT_OFFLINE_AFTER_UPDATE("ep_OfflineAfterUpdate", "OFFLINE"),
   EXTENSION_POINT_ONLINE_AFTER_SERVER_START("ep_OnlineAfterServerStart", "ONLINE"),
   EXTENSION_POINT_ONLINE_AFTER_UPDATE("ep_OnlineAfterUpdate", "ONLINE"),
   EXTENSION_POINT_ROLLOUT_SUCCESS("ep_RolloutSuccess", "ONLINE");

   private final String extensionPointName;
   private final String extensionType;
   private static final Map lookup = new HashMap();

   private ExtensionPoint(String extensionPointName, String extensionType) {
      this.extensionPointName = extensionPointName;
      this.extensionType = extensionType;
   }

   public String getExtensionPointName() {
      return this.extensionPointName;
   }

   public String getExtensionType() {
      return this.extensionType;
   }

   public static ExtensionPoint get(String extensionPointName) {
      return (ExtensionPoint)lookup.get(extensionPointName);
   }

   static {
      Iterator var0 = EnumSet.allOf(ExtensionPoint.class).iterator();

      while(var0.hasNext()) {
         ExtensionPoint e = (ExtensionPoint)var0.next();
         lookup.put(e.extensionPointName, e);
      }

   }
}
