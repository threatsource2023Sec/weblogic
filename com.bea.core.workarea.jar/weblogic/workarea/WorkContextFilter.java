package weblogic.workarea;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import weblogic.diagnostics.debug.DebugLogger;

public class WorkContextFilter {
   static final String DISABLE_PROPAGATION_MODES_PROPERTY = "weblogic.workarea.disablePropagationModes";
   static final String DISABLE_CONTEXT_NAMES_PROPERTY = "weblogic.workarea.disableContextNames";
   static final List DISABLE_CONTEXT_NAMES = new ArrayList();
   static int propagationModeFilterMask = -1;
   private static final DebugLogger debugWorkContext = DebugLogger.getDebugLogger("DebugWorkContext");

   public static void initialize() {
      String disableContextNames = initProperty("weblogic.workarea.disableContextNames", (String)null);
      String disablePropagationModes = initProperty("weblogic.workarea.disablePropagationModes", (String)null);
      if (disableContextNames != null && disablePropagationModes != null) {
         updateDisableContextNamesList(disableContextNames);
         updatePropagationModeFilterMask(disablePropagationModes);
      }

   }

   private static String initProperty(String name, String defaultValue) {
      try {
         return System.getProperty(name);
      } catch (SecurityException var3) {
         return defaultValue;
      }
   }

   private static void updateDisableContextNamesList(String disableContextNames) {
      if (disableContextNames != null) {
         disableContextNames = stripEnclosingQuotes(disableContextNames.trim());

         String token;
         for(StringTokenizer st = new StringTokenizer(disableContextNames, ","); st.hasMoreTokens(); DISABLE_CONTEXT_NAMES.add(token)) {
            token = st.nextToken().trim();
            if (debugWorkContext.isDebugEnabled()) {
               debugWorkContext.debug("Disabling propagation of contexts with name " + token);
            }
         }
      }

   }

   private static void updatePropagationModeFilterMask(String disablePropagationModes) {
      if (disablePropagationModes != null) {
         disablePropagationModes = stripEnclosingQuotes(disablePropagationModes.trim());
         StringTokenizer st = new StringTokenizer(disablePropagationModes, ",");

         while(st.hasMoreTokens()) {
            String modeToDisable = st.nextToken().trim();
            int propagationModeToFilter = getPropagationModeFromName(modeToDisable);
            if (propagationModeToFilter != 0) {
               if (debugWorkContext.isDebugEnabled()) {
                  debugWorkContext.debug("Disabling propagation of contexts with propagation mode " + modeToDisable);
               }

               propagationModeFilterMask &= ~propagationModeToFilter;
            }
         }
      }

   }

   private static String stripEnclosingQuotes(String string) {
      return string.replaceAll("^([\"'])(.*)\\1$", "$2");
   }

   static int getPropagationModeFromName(String propagationModeName) {
      if ("LOCAL".equals(propagationModeName)) {
         return 1;
      } else if ("WORK".equals(propagationModeName)) {
         return 2;
      } else if ("RMI".equals(propagationModeName)) {
         return 4;
      } else if ("TRANSACTION".equals(propagationModeName)) {
         return 8;
      } else if ("JMS_QUEUE".equals(propagationModeName)) {
         return 16;
      } else if ("JMS_TOPIC".equals(propagationModeName)) {
         return 32;
      } else if ("SOAP".equals(propagationModeName)) {
         return 64;
      } else if ("MIME_HEADER".equals(propagationModeName)) {
         return 128;
      } else if ("ONEWAY".equals(propagationModeName)) {
         return 256;
      } else if ("GLOBAL".equals(propagationModeName)) {
         return 212;
      } else {
         return "DEFAULT".equals(propagationModeName) ? 212 : 0;
      }
   }

   static int getFilteredPropagationMode(String contextName, int propagationMode) {
      return !DISABLE_CONTEXT_NAMES.isEmpty() && DISABLE_CONTEXT_NAMES.contains(contextName) ? propagationMode & propagationModeFilterMask : propagationMode;
   }
}
