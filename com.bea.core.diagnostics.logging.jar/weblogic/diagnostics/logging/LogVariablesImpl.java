package weblogic.diagnostics.logging;

import com.bea.logging.BaseLogEntry;
import com.bea.logging.LoggingSupplementalAttribute;
import com.bea.logging.ThrowableWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableDeclarator;
import weblogic.diagnostics.query.VariableIndexResolver;
import weblogic.diagnostics.query.VariableResolver;
import weblogic.i18n.logging.SeverityI18N;
import weblogic.utils.PlatformConstants;

public class LogVariablesImpl implements VariableDeclarator, VariableIndexResolver, LogVariablesConstants {
   private static final boolean DEBUG = false;
   private Map indexMap;

   public static LogVariablesImpl getInstance() {
      return LogVariablesImpl.LogVariablesImplFactory.SINGLETON;
   }

   private LogVariablesImpl() {
      this.indexMap = new HashMap();
      this.indexMap.put("DATE", 1);
      this.indexMap.put("MSGID", 11);
      this.indexMap.put("MACHINE", 4);
      this.indexMap.put("SERVER", 5);
      this.indexMap.put("THREAD", 6);
      this.indexMap.put("USERID", 7);
      this.indexMap.put("TXID", 8);
      this.indexMap.put("SEVERITY", 2);
      this.indexMap.put("SUBSYSTEM", 3);
      this.indexMap.put("TIMESTAMP", 10);
      this.indexMap.put("MESSAGE", 12);
      this.indexMap.put("SUPP_ATTRS", 13);
      this.indexMap.put("SEVERITY_VALUE", 14);
      this.indexMap.put("PARTITION_ID", 15);
      this.indexMap.put("PARTITION_NAME", 16);
      this.indexMap.put("RID", 17);
      this.indexMap.put("APP_NAME", 18);
      this.indexMap.put("MODULE_NAME", 19);
   }

   public int getVariableType(String varName) throws UnknownVariableException {
      if ("TIMESTAMP".equals(varName)) {
         return 2;
      } else if ("SEVERITY_VALUE".equals(varName)) {
         return 1;
      } else {
         Integer mapIdx = (Integer)this.indexMap.get(varName);
         if (mapIdx == null) {
            throw new UnknownVariableException(varName);
         } else {
            return 0;
         }
      }
   }

   public int getVariableIndex(String varName) throws UnknownVariableException {
      Integer mapIdx = (Integer)this.indexMap.get(varName);
      if (mapIdx == null) {
         throw new UnknownVariableException(varName);
      } else {
         return mapIdx;
      }
   }

   public LogVariablesResolver getLogVariablesResolver(BaseLogEntry le) {
      return new LogVariablesResolver(le);
   }

   // $FF: synthetic method
   LogVariablesImpl(Object x0) {
      this();
   }

   public static class LogVariablesResolver implements VariableResolver {
      private BaseLogEntry logEntry;

      private LogVariablesResolver(BaseLogEntry le) {
         this.logEntry = le;
      }

      public Properties getVariableData() {
         Properties props = new Properties();
         props.setProperty("DATE", this.getVariable(1));
         props.setProperty("MSGID", this.getVariable(11));
         props.setProperty("MACHINE", this.getVariable(4));
         props.setProperty("SERVER", this.getVariable(5));
         props.setProperty("THREAD", this.getVariable(6));
         props.setProperty("USERID", this.getVariable(7));
         props.setProperty("TXID", this.getVariable(8));
         props.setProperty("SEVERITY", this.getVariable(2));
         props.setProperty("SUBSYSTEM", this.getVariable(3));
         props.setProperty("TIMESTAMP", this.getVariable(10));
         props.setProperty("MESSAGE", this.getVariable(12));
         props.setProperty("CONTEXTID", this.getVariable(9));
         props.setProperty("SUPP_ATTRS", this.getVariable(13));
         return props;
      }

      private String getVariable(int idx) {
         try {
            return "" + this.resolveVariable(idx);
         } catch (UnknownVariableException var3) {
            throw new AssertionError("Unknown variable index " + idx + ":" + var3);
         }
      }

      public Object resolveVariable(String varName) throws UnknownVariableException {
         int idx = LogVariablesImpl.getInstance().getVariableIndex(varName);
         return this.logEntry == null ? null : this.resolveVariable(idx);
      }

      public Object resolveVariable(int index) throws UnknownVariableException {
         Object ret = null;
         switch (index) {
            case 1:
               ret = this.logEntry.getFormattedDate();
               break;
            case 2:
               ret = SeverityI18N.severityNumToString(this.logEntry.getSeverity());
               break;
            case 3:
               ret = this.logEntry.getSubsystem();
               break;
            case 4:
               ret = this.logEntry.getMachineName();
               break;
            case 5:
               ret = this.logEntry.getServerName();
               break;
            case 6:
               ret = this.logEntry.getThreadName();
               break;
            case 7:
               ret = this.logEntry.getUserId();
               break;
            case 8:
               ret = this.logEntry.getTransactionId();
               break;
            case 9:
               ret = this.logEntry.getDiagnosticContextId();
               break;
            case 10:
               ret = this.logEntry.getTimestamp();
               break;
            case 11:
               ret = this.logEntry.getId();
               break;
            case 12:
               String retStr = this.logEntry.getLogMessage();
               ThrowableWrapper wrapper = this.logEntry.getThrowableWrapper();
               if (wrapper != null) {
                  retStr = retStr + PlatformConstants.EOL + wrapper;
               }

               ret = retStr;
               break;
            case 13:
               ret = this.logEntry.getSupplementalAttributes().toString();
               break;
            case 14:
               ret = this.logEntry.getSupplementalAttributes().get(LoggingSupplementalAttribute.SUPP_ATTR_SEVERITY_VALUE.getAttributeName());
               break;
            case 15:
               ret = this.logEntry.getSupplementalAttributes().get(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_ID.getAttributeName());
               break;
            case 16:
               ret = this.logEntry.getSupplementalAttributes().get(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_NAME.getAttributeName());
               break;
            case 17:
               ret = this.logEntry.getSupplementalAttributes().get(LoggingSupplementalAttribute.SUPP_ATTR_RID.getAttributeName());
               break;
            case 18:
               ret = this.logEntry.getSupplementalAttributes().get(LoggingSupplementalAttribute.SUPP_ATTR_APPLICATION_NAME.getAttributeName());
               break;
            case 19:
               ret = this.logEntry.getSupplementalAttributes().get(LoggingSupplementalAttribute.SUPP_ATTR_MODULE_NAME.getAttributeName());
               break;
            default:
               throw new AssertionError("Unknown variable index " + index);
         }

         return ret;
      }

      public int resolveInteger(int index) throws UnknownVariableException {
         return ((Number)this.resolveVariable(index)).intValue();
      }

      public long resolveLong(int index) throws UnknownVariableException {
         return ((Number)this.resolveVariable(index)).longValue();
      }

      public float resolveFloat(int index) throws UnknownVariableException {
         return ((Number)this.resolveVariable(index)).floatValue();
      }

      public double resolveDouble(int index) throws UnknownVariableException {
         return ((Number)this.resolveVariable(index)).doubleValue();
      }

      public String resolveString(int index) throws UnknownVariableException {
         return this.resolveVariable(index).toString();
      }

      public int resolveInteger(String varName) throws UnknownVariableException {
         return ((Number)this.resolveVariable(varName)).intValue();
      }

      public long resolveLong(String varName) throws UnknownVariableException {
         return ((Number)this.resolveVariable(varName)).longValue();
      }

      public float resolveFloat(String varName) throws UnknownVariableException {
         return ((Number)this.resolveVariable(varName)).floatValue();
      }

      public double resolveDouble(String varName) throws UnknownVariableException {
         return ((Number)this.resolveVariable(varName)).doubleValue();
      }

      public String resolveString(String varName) throws UnknownVariableException {
         return this.resolveVariable(varName).toString();
      }

      // $FF: synthetic method
      LogVariablesResolver(BaseLogEntry x0, Object x1) {
         this(x0);
      }
   }

   private static final class LogVariablesImplFactory {
      private static LogVariablesImpl SINGLETON = new LogVariablesImpl();
   }
}
