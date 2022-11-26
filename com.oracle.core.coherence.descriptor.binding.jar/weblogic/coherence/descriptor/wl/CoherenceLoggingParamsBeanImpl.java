package weblogic.coherence.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceLoggingParamsBeanImpl extends SettableBeanImpl implements CoherenceLoggingParamsBean, Serializable {
   private boolean _Enabled;
   private String _LoggerName;
   private String _MessageFormat;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceLoggingParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceLoggingParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceLoggingParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(0);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getLoggerName() {
      return this._LoggerName;
   }

   public boolean isLoggerNameInherited() {
      return false;
   }

   public boolean isLoggerNameSet() {
      return this._isSet(1);
   }

   public void setLoggerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LoggerName", param0);
      String _oldVal = this._LoggerName;
      this._LoggerName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getMessageFormat() {
      return this._MessageFormat;
   }

   public boolean isMessageFormatInherited() {
      return false;
   }

   public boolean isMessageFormatSet() {
      return this._isSet(2);
   }

   public void setMessageFormat(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MessageFormat;
      this._MessageFormat = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._LoggerName = "com.oracle.coherence";
               if (initOne) {
                  break;
               }
            case 2:
               this._MessageFormat = "{date}/{uptime} {product} {version} <{level}> (thread={thread}, member={member}): {text}";
               if (initOne) {
                  break;
               }
            case 0:
               this._Enabled = true;
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("LoggerName", "com.oracle.coherence");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LoggerName in CoherenceLoggingParamsBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("enabled")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("logger-name")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("message-format")) {
                  return 2;
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
               return "enabled";
            case 1:
               return "logger-name";
            case 2:
               return "message-format";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceLoggingParamsBeanImpl bean;

      protected Helper(CoherenceLoggingParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Enabled";
            case 1:
               return "LoggerName";
            case 2:
               return "MessageFormat";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LoggerName")) {
            return 1;
         } else if (propName.equals("MessageFormat")) {
            return 2;
         } else {
            return propName.equals("Enabled") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isLoggerNameSet()) {
               buf.append("LoggerName");
               buf.append(String.valueOf(this.bean.getLoggerName()));
            }

            if (this.bean.isMessageFormatSet()) {
               buf.append("MessageFormat");
               buf.append(String.valueOf(this.bean.getMessageFormat()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            CoherenceLoggingParamsBeanImpl otherTyped = (CoherenceLoggingParamsBeanImpl)other;
            this.computeDiff("LoggerName", this.bean.getLoggerName(), otherTyped.getLoggerName(), false);
            this.computeDiff("MessageFormat", this.bean.getMessageFormat(), otherTyped.getMessageFormat(), false);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceLoggingParamsBeanImpl original = (CoherenceLoggingParamsBeanImpl)event.getSourceBean();
            CoherenceLoggingParamsBeanImpl proposed = (CoherenceLoggingParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LoggerName")) {
                  original.setLoggerName(proposed.getLoggerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MessageFormat")) {
                  original.setMessageFormat(proposed.getMessageFormat());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            CoherenceLoggingParamsBeanImpl copy = (CoherenceLoggingParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LoggerName")) && this.bean.isLoggerNameSet()) {
               copy.setLoggerName(this.bean.getLoggerName());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageFormat")) && this.bean.isMessageFormatSet()) {
               copy.setMessageFormat(this.bean.getMessageFormat());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
