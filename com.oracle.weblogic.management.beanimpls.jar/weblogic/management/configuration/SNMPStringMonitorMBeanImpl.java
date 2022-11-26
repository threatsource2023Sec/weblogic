package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SNMPStringMonitorMBeanImpl extends SNMPJMXMonitorMBeanImpl implements SNMPStringMonitorMBean, Serializable {
   private boolean _NotifyDiffer;
   private boolean _NotifyMatch;
   private String _StringToCompare;
   private static SchemaHelper2 _schemaHelper;

   public SNMPStringMonitorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPStringMonitorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPStringMonitorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getStringToCompare() {
      return this._StringToCompare;
   }

   public boolean isStringToCompareInherited() {
      return false;
   }

   public boolean isStringToCompareSet() {
      return this._isSet(15);
   }

   public void setStringToCompare(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("StringToCompare", param0);
      String _oldVal = this._StringToCompare;
      this._StringToCompare = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isNotifyDiffer() {
      return this._NotifyDiffer;
   }

   public boolean isNotifyDifferInherited() {
      return false;
   }

   public boolean isNotifyDifferSet() {
      return this._isSet(16);
   }

   public void setNotifyDiffer(boolean param0) {
      boolean _oldVal = this._NotifyDiffer;
      this._NotifyDiffer = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isNotifyMatch() {
      return this._NotifyMatch;
   }

   public boolean isNotifyMatchInherited() {
      return false;
   }

   public boolean isNotifyMatchSet() {
      return this._isSet(17);
   }

   public void setNotifyMatch(boolean param0) {
      boolean _oldVal = this._NotifyMatch;
      this._NotifyMatch = param0;
      this._postSet(17, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("StringToCompare", this.isStringToCompareSet());
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._StringToCompare = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._NotifyDiffer = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._NotifyMatch = false;
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "SNMPStringMonitor";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("NotifyDiffer")) {
         oldVal = this._NotifyDiffer;
         this._NotifyDiffer = (Boolean)v;
         this._postSet(16, oldVal, this._NotifyDiffer);
      } else if (name.equals("NotifyMatch")) {
         oldVal = this._NotifyMatch;
         this._NotifyMatch = (Boolean)v;
         this._postSet(17, oldVal, this._NotifyMatch);
      } else if (name.equals("StringToCompare")) {
         String oldVal = this._StringToCompare;
         this._StringToCompare = (String)v;
         this._postSet(15, oldVal, this._StringToCompare);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("NotifyDiffer")) {
         return new Boolean(this._NotifyDiffer);
      } else if (name.equals("NotifyMatch")) {
         return new Boolean(this._NotifyMatch);
      } else {
         return name.equals("StringToCompare") ? this._StringToCompare : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SNMPJMXMonitorMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("notify-match")) {
                  return 17;
               }
               break;
            case 13:
               if (s.equals("notify-differ")) {
                  return 16;
               }
               break;
            case 17:
               if (s.equals("string-to-compare")) {
                  return 15;
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
            case 15:
               return "string-to-compare";
            case 16:
               return "notify-differ";
            case 17:
               return "notify-match";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
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

   protected static class Helper extends SNMPJMXMonitorMBeanImpl.Helper {
      private SNMPStringMonitorMBeanImpl bean;

      protected Helper(SNMPStringMonitorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 15:
               return "StringToCompare";
            case 16:
               return "NotifyDiffer";
            case 17:
               return "NotifyMatch";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("StringToCompare")) {
            return 15;
         } else if (propName.equals("NotifyDiffer")) {
            return 16;
         } else {
            return propName.equals("NotifyMatch") ? 17 : super.getPropertyIndex(propName);
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
            if (this.bean.isStringToCompareSet()) {
               buf.append("StringToCompare");
               buf.append(String.valueOf(this.bean.getStringToCompare()));
            }

            if (this.bean.isNotifyDifferSet()) {
               buf.append("NotifyDiffer");
               buf.append(String.valueOf(this.bean.isNotifyDiffer()));
            }

            if (this.bean.isNotifyMatchSet()) {
               buf.append("NotifyMatch");
               buf.append(String.valueOf(this.bean.isNotifyMatch()));
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
            SNMPStringMonitorMBeanImpl otherTyped = (SNMPStringMonitorMBeanImpl)other;
            this.computeDiff("StringToCompare", this.bean.getStringToCompare(), otherTyped.getStringToCompare(), true);
            this.computeDiff("NotifyDiffer", this.bean.isNotifyDiffer(), otherTyped.isNotifyDiffer(), true);
            this.computeDiff("NotifyMatch", this.bean.isNotifyMatch(), otherTyped.isNotifyMatch(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPStringMonitorMBeanImpl original = (SNMPStringMonitorMBeanImpl)event.getSourceBean();
            SNMPStringMonitorMBeanImpl proposed = (SNMPStringMonitorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("StringToCompare")) {
                  original.setStringToCompare(proposed.getStringToCompare());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("NotifyDiffer")) {
                  original.setNotifyDiffer(proposed.isNotifyDiffer());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("NotifyMatch")) {
                  original.setNotifyMatch(proposed.isNotifyMatch());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
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
            SNMPStringMonitorMBeanImpl copy = (SNMPStringMonitorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("StringToCompare")) && this.bean.isStringToCompareSet()) {
               copy.setStringToCompare(this.bean.getStringToCompare());
            }

            if ((excludeProps == null || !excludeProps.contains("NotifyDiffer")) && this.bean.isNotifyDifferSet()) {
               copy.setNotifyDiffer(this.bean.isNotifyDiffer());
            }

            if ((excludeProps == null || !excludeProps.contains("NotifyMatch")) && this.bean.isNotifyMatchSet()) {
               copy.setNotifyMatch(this.bean.isNotifyMatch());
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
