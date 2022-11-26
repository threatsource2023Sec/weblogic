package weblogic.security.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import weblogic.security.spi.Resource;

public final class MBeanResource extends ResourceBase {
   static ActionType getActionType(String name) {
      return (ActionType)MBeanResource.ActionType.actionTypeByName.get(name);
   }

   public MBeanResource() {
   }

   public MBeanResource(String applicationName, String moduleName, String domainName, Hashtable keyPropertyList, ActionType actionType, String actionTarget) {
   }

   public String[] getKeys() {
      return new String[0];
   }

   protected Resource makeParent() {
      return null;
   }

   public String getType() {
      return "<mbean>";
   }

   public String getMBeanName() {
      return null;
   }

   public String getMBeanType() {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public static class ActionType {
      static Map actionTypeByName = new HashMap();
      private String name;
      public static final ActionType EXECUTE = new ActionType("execute");
      public static final ActionType FIND = new ActionType("find");
      public static final ActionType READ = new ActionType("read");
      public static final ActionType REGISTER = new ActionType("register");
      public static final ActionType UNKNOWN = new ActionType("unknown");
      public static final ActionType UNREGISTER = new ActionType("unregister");
      public static final ActionType WRITE = new ActionType("write");

      private ActionType(String name) {
         this.name = name;
         actionTypeByName.put(name, this);
      }

      public String toString() {
         return this.name;
      }
   }
}
