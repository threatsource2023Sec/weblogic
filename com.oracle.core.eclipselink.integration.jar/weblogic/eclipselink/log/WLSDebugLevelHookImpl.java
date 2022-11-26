package weblogic.eclipselink.log;

import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.Ternary;

public class WLSDebugLevelHookImpl implements WLSDebugLevelHook {
   private Map debugLoggers = new HashMap(20);

   public WLSDebugLevelHookImpl() {
      this.debugLoggers.put("DebugJpaDataCache", DebugLogger.getDebugLogger("DebugJpaDataCache"));
      this.debugLoggers.put("DebugJpaEnhance", DebugLogger.getDebugLogger("DebugJpaEnhance"));
      this.debugLoggers.put("DebugJpaManage", DebugLogger.getDebugLogger("DebugJpaManage"));
      this.debugLoggers.put("DebugJpaMetaData", DebugLogger.getDebugLogger("DebugJpaMetaData"));
      this.debugLoggers.put("DebugJpaProfile", DebugLogger.getDebugLogger("DebugJpaProfile"));
      this.debugLoggers.put("DebugJpaQuery", DebugLogger.getDebugLogger("DebugJpaQuery"));
      this.debugLoggers.put("DebugJpaRuntime", DebugLogger.getDebugLogger("DebugJpaRuntime"));
      this.debugLoggers.put("DebugJpaTool", DebugLogger.getDebugLogger("DebugJpaTool"));
      this.debugLoggers.put("DebugJpaJdbcJdbc", DebugLogger.getDebugLogger("DebugJpaJdbcJdbc"));
      this.debugLoggers.put("DebugJpaJdbcSchema", DebugLogger.getDebugLogger("DebugJpaJdbcSchema"));
      this.debugLoggers.put("DebugJpaJdbcSql", DebugLogger.getDebugLogger("DebugJpaJdbcSql"));
   }

   public Ternary isDebugEnabled(String category) {
      if (category != null && category.length() > 0) {
         DebugLogger logger = (DebugLogger)this.debugLoggers.get(category);
         if (logger == null) {
            return Ternary.INDETERMINATE;
         } else {
            return logger.isDebugEnabled() ? Ternary.TRUE : Ternary.FALSE;
         }
      } else {
         return Ternary.INDETERMINATE;
      }
   }
}
