package weblogic.management.patching.agent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum ZdtAgentAction {
   CHECK_PREREQUISITES("checkreq"),
   EXTRACT("extract"),
   PREPARE_FOR_UPDATE("prepare"),
   UPDATE("update"),
   VALIDATE_UPDATE("validate"),
   CLEANUP("cleanup");

   String displayString;
   private static final Map lookup = new HashMap();

   private ZdtAgentAction(String displayString) {
      this.displayString = displayString;
   }

   public static ZdtAgentAction get(String zdtAction) {
      return (ZdtAgentAction)lookup.get(zdtAction);
   }

   static {
      Iterator var0 = EnumSet.allOf(ZdtAgentAction.class).iterator();

      while(var0.hasNext()) {
         ZdtAgentAction s = (ZdtAgentAction)var0.next();
         lookup.put(s.displayString, s);
      }

   }
}
