package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class WLDFLogActionBeanImpl extends WLDFNotificationBeanImpl implements WLDFLogActionBean, Serializable {
   private String _Message;
   private String _Severity;
   private String _SubsystemName;
   private static SchemaHelper2 _schemaHelper;

   public WLDFLogActionBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFLogActionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFLogActionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSeverity() {
      return this._Severity;
   }

   public boolean isSeverityInherited() {
      return false;
   }

   public boolean isSeveritySet() {
      return this._isSet(4);
   }

   public void setSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"};
      param0 = LegalChecks.checkInEnum("Severity", param0, _set);
      String _oldVal = this._Severity;
      this._Severity = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getMessage() {
      return this._Message;
   }

   public boolean isMessageInherited() {
      return false;
   }

   public boolean isMessageSet() {
      return this._isSet(5);
   }

   public void setMessage(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Message", param0);
      LegalChecks.checkNonNull("Message", param0);
      String _oldVal = this._Message;
      this._Message = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getSubsystemName() {
      return this._SubsystemName;
   }

   public boolean isSubsystemNameInherited() {
      return false;
   }

   public boolean isSubsystemNameSet() {
      return this._isSet(6);
   }

   public void setSubsystemName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("SubsystemName", param0);
      LegalChecks.checkNonNull("SubsystemName", param0);
      String _oldVal = this._SubsystemName;
      this._SubsystemName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Message", this.isMessageSet());
      LegalChecks.checkIsSet("SubsystemName", this.isSubsystemNameSet());
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._Message = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Severity = "Notice";
               if (initOne) {
                  break;
               }
            case 6:
               this._SubsystemName = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static void validateGeneration() {
      try {
         String[] _set = new String[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"};
         LegalChecks.checkInEnum("Severity", "Notice", _set);
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property Severity in WLDFLogActionBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("message")) {
                  return 5;
               }
               break;
            case 8:
               if (s.equals("severity")) {
                  return 4;
               }
               break;
            case 14:
               if (s.equals("subsystem-name")) {
                  return 6;
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
            case 4:
               return "severity";
            case 5:
               return "message";
            case 6:
               return "subsystem-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFLogActionBeanImpl bean;

      protected Helper(WLDFLogActionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "Severity";
            case 5:
               return "Message";
            case 6:
               return "SubsystemName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Message")) {
            return 5;
         } else if (propName.equals("Severity")) {
            return 4;
         } else {
            return propName.equals("SubsystemName") ? 6 : super.getPropertyIndex(propName);
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
            if (this.bean.isMessageSet()) {
               buf.append("Message");
               buf.append(String.valueOf(this.bean.getMessage()));
            }

            if (this.bean.isSeveritySet()) {
               buf.append("Severity");
               buf.append(String.valueOf(this.bean.getSeverity()));
            }

            if (this.bean.isSubsystemNameSet()) {
               buf.append("SubsystemName");
               buf.append(String.valueOf(this.bean.getSubsystemName()));
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
            WLDFLogActionBeanImpl otherTyped = (WLDFLogActionBeanImpl)other;
            this.computeDiff("Message", this.bean.getMessage(), otherTyped.getMessage(), true);
            this.computeDiff("Severity", this.bean.getSeverity(), otherTyped.getSeverity(), true);
            this.computeDiff("SubsystemName", this.bean.getSubsystemName(), otherTyped.getSubsystemName(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFLogActionBeanImpl original = (WLDFLogActionBeanImpl)event.getSourceBean();
            WLDFLogActionBeanImpl proposed = (WLDFLogActionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Message")) {
                  original.setMessage(proposed.getMessage());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Severity")) {
                  original.setSeverity(proposed.getSeverity());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("SubsystemName")) {
                  original.setSubsystemName(proposed.getSubsystemName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            WLDFLogActionBeanImpl copy = (WLDFLogActionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Message")) && this.bean.isMessageSet()) {
               copy.setMessage(this.bean.getMessage());
            }

            if ((excludeProps == null || !excludeProps.contains("Severity")) && this.bean.isSeveritySet()) {
               copy.setSeverity(this.bean.getSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("SubsystemName")) && this.bean.isSubsystemNameSet()) {
               copy.setSubsystemName(this.bean.getSubsystemName());
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
