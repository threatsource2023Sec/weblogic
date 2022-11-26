package weblogic.management.patching.agent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum ZdtPluginType {
   UPDATE_ORACLE_HOME("UpdateOracleHome"),
   UPDATE_APPLICATION("UpdateApplication");

   String displayString;
   private static final Map lookup = new HashMap();

   private ZdtPluginType(String displayString) {
      this.displayString = displayString;
   }

   public static ZdtPluginType get(String zdtAction) {
      return (ZdtPluginType)lookup.get(zdtAction);
   }

   static {
      Iterator var0 = EnumSet.allOf(ZdtPluginType.class).iterator();

      while(var0.hasNext()) {
         ZdtPluginType s = (ZdtPluginType)var0.next();
         lookup.put(s.displayString, s);
      }

   }
}
