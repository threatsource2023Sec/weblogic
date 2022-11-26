package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class LogEntryBeanImpl extends AbstractDescriptorBean implements LogEntryBean, Serializable {
   private String _DiagnosticContextId;
   private String _FormattedDate;
   private String _LogMessage;
   private String _MachineName;
   private String _MessageId;
   private String _ServerName;
   private int _Severity;
   private String _StackTrace;
   private String _Subsystem;
   private String _ThreadName;
   private long _Timestamp;
   private String _TransactionId;
   private String _UserId;
   private static SchemaHelper2 _schemaHelper;

   public LogEntryBeanImpl() {
      this._initializeProperty(-1);
   }

   public LogEntryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LogEntryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFormattedDate() {
      return this._FormattedDate;
   }

   public boolean isFormattedDateInherited() {
      return false;
   }

   public boolean isFormattedDateSet() {
      return this._isSet(0);
   }

   public void setFormattedDate(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FormattedDate;
      this._FormattedDate = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getMessageId() {
      return this._MessageId;
   }

   public boolean isMessageIdInherited() {
      return false;
   }

   public boolean isMessageIdSet() {
      return this._isSet(1);
   }

   public void setMessageId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MessageId;
      this._MessageId = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getMachineName() {
      return this._MachineName;
   }

   public boolean isMachineNameInherited() {
      return false;
   }

   public boolean isMachineNameSet() {
      return this._isSet(2);
   }

   public void setMachineName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MachineName;
      this._MachineName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getServerName() {
      return this._ServerName;
   }

   public boolean isServerNameInherited() {
      return false;
   }

   public boolean isServerNameSet() {
      return this._isSet(3);
   }

   public void setServerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServerName;
      this._ServerName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getThreadName() {
      return this._ThreadName;
   }

   public boolean isThreadNameInherited() {
      return false;
   }

   public boolean isThreadNameSet() {
      return this._isSet(4);
   }

   public void setThreadName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ThreadName;
      this._ThreadName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getUserId() {
      return this._UserId;
   }

   public boolean isUserIdInherited() {
      return false;
   }

   public boolean isUserIdSet() {
      return this._isSet(5);
   }

   public void setUserId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserId;
      this._UserId = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getTransactionId() {
      return this._TransactionId;
   }

   public boolean isTransactionIdInherited() {
      return false;
   }

   public boolean isTransactionIdSet() {
      return this._isSet(6);
   }

   public void setTransactionId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TransactionId;
      this._TransactionId = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getSeverity() {
      return this._Severity;
   }

   public boolean isSeverityInherited() {
      return false;
   }

   public boolean isSeveritySet() {
      return this._isSet(7);
   }

   public void setSeverity(int param0) {
      int _oldVal = this._Severity;
      this._Severity = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getSubsystem() {
      return this._Subsystem;
   }

   public boolean isSubsystemInherited() {
      return false;
   }

   public boolean isSubsystemSet() {
      return this._isSet(8);
   }

   public void setSubsystem(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Subsystem;
      this._Subsystem = param0;
      this._postSet(8, _oldVal, param0);
   }

   public long getTimestamp() {
      return this._Timestamp;
   }

   public boolean isTimestampInherited() {
      return false;
   }

   public boolean isTimestampSet() {
      return this._isSet(9);
   }

   public void setTimestamp(long param0) {
      long _oldVal = this._Timestamp;
      this._Timestamp = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getLogMessage() {
      return this._LogMessage;
   }

   public boolean isLogMessageInherited() {
      return false;
   }

   public boolean isLogMessageSet() {
      return this._isSet(10);
   }

   public void setLogMessage(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LogMessage;
      this._LogMessage = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getStackTrace() {
      return this._StackTrace;
   }

   public boolean isStackTraceInherited() {
      return false;
   }

   public boolean isStackTraceSet() {
      return this._isSet(11);
   }

   public void setStackTrace(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StackTrace;
      this._StackTrace = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getDiagnosticContextId() {
      return this._DiagnosticContextId;
   }

   public boolean isDiagnosticContextIdInherited() {
      return false;
   }

   public boolean isDiagnosticContextIdSet() {
      return this._isSet(12);
   }

   public void setDiagnosticContextId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DiagnosticContextId;
      this._DiagnosticContextId = param0;
      this._postSet(12, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._DiagnosticContextId = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._FormattedDate = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._LogMessage = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._MachineName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MessageId = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ServerName = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Severity = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._StackTrace = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._Subsystem = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ThreadName = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._Timestamp = 0L;
               if (initOne) {
                  break;
               }
            case 6:
               this._TransactionId = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._UserId = null;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("user-id")) {
                  return 5;
               }
               break;
            case 8:
               if (s.equals("severity")) {
                  return 7;
               }
               break;
            case 9:
               if (s.equals("subsystem")) {
                  return 8;
               }

               if (s.equals("timestamp")) {
                  return 9;
               }
               break;
            case 10:
               if (s.equals("message-id")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("log-message")) {
                  return 10;
               }

               if (s.equals("server-name")) {
                  return 3;
               }

               if (s.equals("stack-trace")) {
                  return 11;
               }

               if (s.equals("thread-name")) {
                  return 4;
               }
               break;
            case 12:
               if (s.equals("machine-name")) {
                  return 2;
               }
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            default:
               break;
            case 14:
               if (s.equals("formatted-date")) {
                  return 0;
               }

               if (s.equals("transaction-id")) {
                  return 6;
               }
               break;
            case 21:
               if (s.equals("diagnostic-context-id")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "formatted-date";
            case 1:
               return "message-id";
            case 2:
               return "machine-name";
            case 3:
               return "server-name";
            case 4:
               return "thread-name";
            case 5:
               return "user-id";
            case 6:
               return "transaction-id";
            case 7:
               return "severity";
            case 8:
               return "subsystem";
            case 9:
               return "timestamp";
            case 10:
               return "log-message";
            case 11:
               return "stack-trace";
            case 12:
               return "diagnostic-context-id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private LogEntryBeanImpl bean;

      protected Helper(LogEntryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FormattedDate";
            case 1:
               return "MessageId";
            case 2:
               return "MachineName";
            case 3:
               return "ServerName";
            case 4:
               return "ThreadName";
            case 5:
               return "UserId";
            case 6:
               return "TransactionId";
            case 7:
               return "Severity";
            case 8:
               return "Subsystem";
            case 9:
               return "Timestamp";
            case 10:
               return "LogMessage";
            case 11:
               return "StackTrace";
            case 12:
               return "DiagnosticContextId";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DiagnosticContextId")) {
            return 12;
         } else if (propName.equals("FormattedDate")) {
            return 0;
         } else if (propName.equals("LogMessage")) {
            return 10;
         } else if (propName.equals("MachineName")) {
            return 2;
         } else if (propName.equals("MessageId")) {
            return 1;
         } else if (propName.equals("ServerName")) {
            return 3;
         } else if (propName.equals("Severity")) {
            return 7;
         } else if (propName.equals("StackTrace")) {
            return 11;
         } else if (propName.equals("Subsystem")) {
            return 8;
         } else if (propName.equals("ThreadName")) {
            return 4;
         } else if (propName.equals("Timestamp")) {
            return 9;
         } else if (propName.equals("TransactionId")) {
            return 6;
         } else {
            return propName.equals("UserId") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isDiagnosticContextIdSet()) {
               buf.append("DiagnosticContextId");
               buf.append(String.valueOf(this.bean.getDiagnosticContextId()));
            }

            if (this.bean.isFormattedDateSet()) {
               buf.append("FormattedDate");
               buf.append(String.valueOf(this.bean.getFormattedDate()));
            }

            if (this.bean.isLogMessageSet()) {
               buf.append("LogMessage");
               buf.append(String.valueOf(this.bean.getLogMessage()));
            }

            if (this.bean.isMachineNameSet()) {
               buf.append("MachineName");
               buf.append(String.valueOf(this.bean.getMachineName()));
            }

            if (this.bean.isMessageIdSet()) {
               buf.append("MessageId");
               buf.append(String.valueOf(this.bean.getMessageId()));
            }

            if (this.bean.isServerNameSet()) {
               buf.append("ServerName");
               buf.append(String.valueOf(this.bean.getServerName()));
            }

            if (this.bean.isSeveritySet()) {
               buf.append("Severity");
               buf.append(String.valueOf(this.bean.getSeverity()));
            }

            if (this.bean.isStackTraceSet()) {
               buf.append("StackTrace");
               buf.append(String.valueOf(this.bean.getStackTrace()));
            }

            if (this.bean.isSubsystemSet()) {
               buf.append("Subsystem");
               buf.append(String.valueOf(this.bean.getSubsystem()));
            }

            if (this.bean.isThreadNameSet()) {
               buf.append("ThreadName");
               buf.append(String.valueOf(this.bean.getThreadName()));
            }

            if (this.bean.isTimestampSet()) {
               buf.append("Timestamp");
               buf.append(String.valueOf(this.bean.getTimestamp()));
            }

            if (this.bean.isTransactionIdSet()) {
               buf.append("TransactionId");
               buf.append(String.valueOf(this.bean.getTransactionId()));
            }

            if (this.bean.isUserIdSet()) {
               buf.append("UserId");
               buf.append(String.valueOf(this.bean.getUserId()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            LogEntryBeanImpl otherTyped = (LogEntryBeanImpl)other;
            this.computeDiff("DiagnosticContextId", this.bean.getDiagnosticContextId(), otherTyped.getDiagnosticContextId(), false);
            this.computeDiff("FormattedDate", this.bean.getFormattedDate(), otherTyped.getFormattedDate(), false);
            this.computeDiff("LogMessage", this.bean.getLogMessage(), otherTyped.getLogMessage(), false);
            this.computeDiff("MachineName", this.bean.getMachineName(), otherTyped.getMachineName(), false);
            this.computeDiff("MessageId", this.bean.getMessageId(), otherTyped.getMessageId(), false);
            this.computeDiff("ServerName", this.bean.getServerName(), otherTyped.getServerName(), false);
            this.computeDiff("Severity", this.bean.getSeverity(), otherTyped.getSeverity(), false);
            this.computeDiff("StackTrace", this.bean.getStackTrace(), otherTyped.getStackTrace(), false);
            this.computeDiff("Subsystem", this.bean.getSubsystem(), otherTyped.getSubsystem(), false);
            this.computeDiff("ThreadName", this.bean.getThreadName(), otherTyped.getThreadName(), false);
            this.computeDiff("Timestamp", this.bean.getTimestamp(), otherTyped.getTimestamp(), false);
            this.computeDiff("TransactionId", this.bean.getTransactionId(), otherTyped.getTransactionId(), false);
            this.computeDiff("UserId", this.bean.getUserId(), otherTyped.getUserId(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LogEntryBeanImpl original = (LogEntryBeanImpl)event.getSourceBean();
            LogEntryBeanImpl proposed = (LogEntryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DiagnosticContextId")) {
                  original.setDiagnosticContextId(proposed.getDiagnosticContextId());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("FormattedDate")) {
                  original.setFormattedDate(proposed.getFormattedDate());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("LogMessage")) {
                  original.setLogMessage(proposed.getLogMessage());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MachineName")) {
                  original.setMachineName(proposed.getMachineName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MessageId")) {
                  original.setMessageId(proposed.getMessageId());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ServerName")) {
                  original.setServerName(proposed.getServerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Severity")) {
                  original.setSeverity(proposed.getSeverity());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("StackTrace")) {
                  original.setStackTrace(proposed.getStackTrace());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Subsystem")) {
                  original.setSubsystem(proposed.getSubsystem());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ThreadName")) {
                  original.setThreadName(proposed.getThreadName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Timestamp")) {
                  original.setTimestamp(proposed.getTimestamp());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("TransactionId")) {
                  original.setTransactionId(proposed.getTransactionId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("UserId")) {
                  original.setUserId(proposed.getUserId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            LogEntryBeanImpl copy = (LogEntryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DiagnosticContextId")) && this.bean.isDiagnosticContextIdSet()) {
               copy.setDiagnosticContextId(this.bean.getDiagnosticContextId());
            }

            if ((excludeProps == null || !excludeProps.contains("FormattedDate")) && this.bean.isFormattedDateSet()) {
               copy.setFormattedDate(this.bean.getFormattedDate());
            }

            if ((excludeProps == null || !excludeProps.contains("LogMessage")) && this.bean.isLogMessageSet()) {
               copy.setLogMessage(this.bean.getLogMessage());
            }

            if ((excludeProps == null || !excludeProps.contains("MachineName")) && this.bean.isMachineNameSet()) {
               copy.setMachineName(this.bean.getMachineName());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageId")) && this.bean.isMessageIdSet()) {
               copy.setMessageId(this.bean.getMessageId());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerName")) && this.bean.isServerNameSet()) {
               copy.setServerName(this.bean.getServerName());
            }

            if ((excludeProps == null || !excludeProps.contains("Severity")) && this.bean.isSeveritySet()) {
               copy.setSeverity(this.bean.getSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("StackTrace")) && this.bean.isStackTraceSet()) {
               copy.setStackTrace(this.bean.getStackTrace());
            }

            if ((excludeProps == null || !excludeProps.contains("Subsystem")) && this.bean.isSubsystemSet()) {
               copy.setSubsystem(this.bean.getSubsystem());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadName")) && this.bean.isThreadNameSet()) {
               copy.setThreadName(this.bean.getThreadName());
            }

            if ((excludeProps == null || !excludeProps.contains("Timestamp")) && this.bean.isTimestampSet()) {
               copy.setTimestamp(this.bean.getTimestamp());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionId")) && this.bean.isTransactionIdSet()) {
               copy.setTransactionId(this.bean.getTransactionId());
            }

            if ((excludeProps == null || !excludeProps.contains("UserId")) && this.bean.isUserIdSet()) {
               copy.setUserId(this.bean.getUserId());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
