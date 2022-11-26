package weblogic.eclipselink.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.MessageDispatcher;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.utils.Ternary;

public class WLSLogAdapter extends AbstractSessionLog implements MessageLoggerRegistryListener {
   private static final String MAPPING_TO_BE_DETERMINED = "DebugJpaRuntime";
   private static final String SUBSYSTEM_NAME = "EclipseLink";
   private static final String ECLIPSELINK_BASE_LOG_ID = "2005000";
   private static final String WLS_LOG_TAG = "BEA";
   private static final String WLS_IGNORE_EL_LOG_PROPERTY = "weblogic.persistence.IgnoreELLogProperty";
   private boolean isIgnoreELLogProperty = Boolean.getBoolean("weblogic.persistence.IgnoreELLogProperty");
   private WLSDebugLevelHook debugLevelHook;
   private MessageDispatcher md;
   private MessageLogger ml;
   private Map categoryMap;
   private Map categoryLevelConfig = new HashMap();

   public WLSLogAdapter() {
      this.initCategoryMap();
      this.messageLoggerRegistryUpdated();
      MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
   }

   public void log(SessionLogEntry sessionLogEntry) {
      if (this.shouldLog(sessionLogEntry.getLevel(), sessionLogEntry.getNameSpace())) {
         this.md.log(this.toLogMessage(sessionLogEntry));
      }
   }

   public void messageLoggerRegistryUpdated() {
      this.ml = MessageLoggerRegistry.findMessageLogger("");
      this.md = this.ml.getMessageDispatcher("EclipseLink");
      if (!this.isIgnoreELLogProperty) {
         this.md.setSeverity(256);
      }

   }

   public boolean shouldLog(int msgLevel, String elCategory) {
      if (msgLevel >= 8) {
         return false;
      } else {
         int curLevel = this.getLevel(elCategory);
         return msgLevel >= curLevel;
      }
   }

   public int getLevel(String elCategory) {
      String jpaCategory = this.toJPACategory(elCategory);
      Ternary isDebug = this.isDebugEnabled(jpaCategory);
      if (!this.isIgnoreELLogProperty) {
         if (Ternary.TRUE.equals(isDebug)) {
            return 1;
         } else {
            return this.categoryLevelConfig.containsKey(elCategory) ? (Integer)this.categoryLevelConfig.get(elCategory) : this.level;
         }
      } else {
         int wlsSeverity;
         if (Ternary.INDETERMINATE.equals(isDebug) && this.ml.isSeverityEnabled("EclipseLink", 128)) {
            wlsSeverity = 128;
         } else if (Ternary.TRUE.equals(isDebug)) {
            wlsSeverity = 128;
         } else {
            wlsSeverity = this.getWLSSubsystemSeverity();
         }

         return this.toELLevel(wlsSeverity);
      }
   }

   public int getLevel() {
      return this.isIgnoreELLogProperty ? this.getLevel((String)null) : this.level;
   }

   public void setLevel(int level) {
      if (this.isIgnoreELLogProperty) {
         this.setLevel(level, (String)null);
      } else {
         this.level = level;
      }

   }

   public void setLevel(int level, String category) {
      if (!this.isIgnoreELLogProperty) {
         if (category != null) {
            this.categoryLevelConfig.put(category, level);
         }
      } else {
         super.setLevel(level, category);
         int wlsSeverity = this.toWLSLevel(level);
         this.md.setSeverity(wlsSeverity);
      }

   }

   public WLSDebugLevelHook getDebugLevelHook() {
      return this.debugLevelHook;
   }

   public void setDebugLevelHook(WLSDebugLevelHook debugLevelHook) {
      this.debugLevelHook = debugLevelHook;
   }

   private String toJPACategory(String elCategory) {
      return elCategory != null && elCategory.length() > 0 ? (String)this.categoryMap.get(elCategory) : null;
   }

   private void initCategoryMap() {
      this.categoryMap = new HashMap(25);
      this.categoryMap.put("cache", "DebugJpaDataCache");
      this.categoryMap.put("connection", "DebugJpaJdbcJdbc");
      this.categoryMap.put("dms", "DebugJpaProfile");
      this.categoryMap.put("ejb", "DebugJpaRuntime");
      this.categoryMap.put("metadata", "DebugJpaMetaData");
      this.categoryMap.put("event", "DebugJpaRuntime");
      this.categoryMap.put("metamodel", "DebugJpaMetaData");
      this.categoryMap.put("propagation", "DebugJpaDataCache");
      this.categoryMap.put("properties", "DebugJpaRuntime");
      this.categoryMap.put("query", "DebugJpaQuery");
      this.categoryMap.put("sequencing", "DebugJpaRuntime");
      this.categoryMap.put("server", "DebugJpaRuntime");
      this.categoryMap.put("sql", "DebugJpaJdbcSql");
      this.categoryMap.put("transaction", "DebugJpaJdbcJdbc");
      this.categoryMap.put("weaver", "DebugJpaEnhance");
   }

   private Ternary isDebugEnabled(String jpaCategory) {
      Ternary retv = Ternary.INDETERMINATE;
      if (this.debugLevelHook != null) {
         retv = this.debugLevelHook.isDebugEnabled(jpaCategory);
      }

      return retv;
   }

   private int getWLSSubsystemSeverity() {
      int retv = 0;
      if (this.ml.isSeverityEnabled("EclipseLink", 64)) {
         retv = 64;
      } else if (this.ml.isSeverityEnabled("EclipseLink", 32)) {
         retv = 32;
      } else if (this.ml.isSeverityEnabled("EclipseLink", 16)) {
         retv = 16;
      } else if (this.ml.isSeverityEnabled("EclipseLink", 8)) {
         retv = 8;
      } else if (this.ml.isSeverityEnabled("EclipseLink", 4)) {
         retv = 4;
      } else if (this.ml.isSeverityEnabled("EclipseLink", 2)) {
         retv = 2;
      } else if (this.ml.isSeverityEnabled("EclipseLink", 1)) {
         retv = 1;
      }

      return retv;
   }

   private int toELLevel(int wlsSeverity) {
      byte elLevel;
      switch (wlsSeverity) {
         case 0:
         case 1:
         default:
            elLevel = 8;
            break;
         case 2:
         case 4:
         case 8:
            elLevel = 7;
            break;
         case 16:
            elLevel = 6;
            break;
         case 32:
            elLevel = 5;
            break;
         case 64:
            elLevel = 4;
            break;
         case 128:
         case 256:
            elLevel = 1;
      }

      return elLevel;
   }

   private int toWLSLevel(int elLevel) {
      short wlsSeverity;
      switch (elLevel) {
         case 0:
         case 1:
         case 2:
         case 3:
            wlsSeverity = 128;
            break;
         case 4:
            wlsSeverity = 64;
            break;
         case 5:
            wlsSeverity = 32;
            break;
         case 6:
            wlsSeverity = 16;
            break;
         case 7:
            wlsSeverity = 2;
            break;
         case 8:
         default:
            wlsSeverity = 0;
      }

      return wlsSeverity;
   }

   private LogMessage toLogMessage(SessionLogEntry elEntry) {
      StringBuffer msgBuf = new StringBuffer(200);
      String jpaCategory = this.toJPACategory(elEntry.getNameSpace());
      String catString = null;
      if (jpaCategory != null) {
         catString = "[" + jpaCategory + "] ";
      }

      switch (elEntry.getLevel()) {
         case 1:
         case 2:
         case 3:
            if (catString != null) {
               msgBuf.append(catString);
            }
            break;
         default:
            Ternary isDebug = this.isDebugEnabled(jpaCategory);
            if (catString != null && Ternary.TRUE.equals(isDebug)) {
               msgBuf.append(catString);
            }
      }

      msgBuf.append(this.getSupplementDetailString(elEntry));
      if (elEntry.hasException()) {
         StringWriter writer;
         if (elEntry.getLevel() == 7) {
            writer = new StringWriter();
            elEntry.getException().printStackTrace(new PrintWriter(writer));
            msgBuf.append(writer.toString());
         } else if (elEntry.getLevel() <= 6) {
            if (this.shouldLogExceptionStackTrace()) {
               writer = new StringWriter();
               elEntry.getException().printStackTrace(new PrintWriter(writer));
               msgBuf.append(writer.toString());
            } else {
               msgBuf.append(elEntry.getException().toString());
            }
         }
      } else {
         msgBuf.append(this.formatMessage(elEntry));
      }

      LogMessage retv = new LogMessage("2005000", "BEA", "EclipseLink", this.toWLSLevel(elEntry.getLevel()), msgBuf.toString());
      return retv;
   }
}
