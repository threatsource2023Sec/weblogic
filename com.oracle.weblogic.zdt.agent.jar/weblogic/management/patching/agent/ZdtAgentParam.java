package weblogic.management.patching.agent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum ZdtAgentParam {
   ACTION_PARAM("ACTION"),
   CURRENT_PARAM("CURRENT"),
   RESUME_PARAM("RESUME"),
   PATCHED_PARAM("PATCHED"),
   BACKUP_DIR_PARAM("BACKUP_DIR"),
   REVERT_PARAM("REVERT_FROM_ERROR"),
   NEW_JAVA_HOME_PARAM("NEW_JAVA_HOME"),
   VERBOSE_PARAM("VERBOSE"),
   ZDT_AGENT_JAR_PATH("ZDT_AGENT_JAR_PATH"),
   ZDT_AGENT_JAR_NAME("ZDT_AGENT_JAR_NAME"),
   DOMAIN_DIR_PARAM("DOMAIN_DIR"),
   SCRIPT_OUTFILE_PARAM("SCRIPT_OUTFILE"),
   JAVA_BACKUP_DIR_PARAM("JAVA_BACKUP_DIR"),
   WORKFLOW_ID_PARAM("WORKFLOW_ID"),
   EXTENSION_JARS_PARAM("EXTENSION_JARS"),
   BEFORE_UPDATE_EXTENSIONS_PARAM("BEFORE_UPDATE_EXTENSIONS"),
   AFTER_UPDATE_EXTENSIONS_PARAM("AFTER_UPDATE_EXTENSIONS");

   String displayString;
   private static final String COMMAND_LINE_PREFIX = "-";
   private static final Map lookup = new HashMap();

   private ZdtAgentParam(String displayString) {
      this.displayString = displayString;
   }

   public String getDisplayString() {
      return this.displayString;
   }

   public static ZdtAgentParam get(String zdtParam) {
      if (zdtParam.startsWith("-") && !lookup.containsKey(zdtParam)) {
         zdtParam = zdtParam.substring("-".length());
      }

      return (ZdtAgentParam)lookup.get(zdtParam);
   }

   static {
      Iterator var0 = EnumSet.allOf(ZdtAgentParam.class).iterator();

      while(var0.hasNext()) {
         ZdtAgentParam s = (ZdtAgentParam)var0.next();
         lookup.put(s.displayString, s);
      }

   }
}
