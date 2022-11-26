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

public class SNMPJMXMonitorMBeanImpl extends SNMPTrapSourceMBeanImpl implements SNMPJMXMonitorMBean, Serializable {
   private String _MonitoredAttributeName;
   private String _MonitoredMBeanName;
   private String _MonitoredMBeanType;
   private int _PollingInterval;
   private static SchemaHelper2 _schemaHelper;

   public SNMPJMXMonitorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPJMXMonitorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPJMXMonitorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMonitoredMBeanType() {
      return this._MonitoredMBeanType;
   }

   public boolean isMonitoredMBeanTypeInherited() {
      return false;
   }

   public boolean isMonitoredMBeanTypeSet() {
      return this._isSet(11);
   }

   public void setMonitoredMBeanType(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("MonitoredMBeanType", param0);
      String _oldVal = this._MonitoredMBeanType;
      this._MonitoredMBeanType = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getMonitoredMBeanName() {
      return this._MonitoredMBeanName;
   }

   public boolean isMonitoredMBeanNameInherited() {
      return false;
   }

   public boolean isMonitoredMBeanNameSet() {
      return this._isSet(12);
   }

   public void setMonitoredMBeanName(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MonitoredMBeanName;
      this._MonitoredMBeanName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getMonitoredAttributeName() {
      return this._MonitoredAttributeName;
   }

   public boolean isMonitoredAttributeNameInherited() {
      return false;
   }

   public boolean isMonitoredAttributeNameSet() {
      return this._isSet(13);
   }

   public void setMonitoredAttributeName(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("MonitoredAttributeName", param0);
      String _oldVal = this._MonitoredAttributeName;
      this._MonitoredAttributeName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getPollingInterval() {
      return this._PollingInterval;
   }

   public boolean isPollingIntervalInherited() {
      return false;
   }

   public boolean isPollingIntervalSet() {
      return this._isSet(14);
   }

   public void setPollingInterval(int param0) throws InvalidAttributeValueException, ConfigurationException {
      LegalChecks.checkInRange("PollingInterval", (long)param0, 1L, 65535L);
      int _oldVal = this._PollingInterval;
      this._PollingInterval = param0;
      this._postSet(14, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      SNMPValidator.validateJMXMonitorMBean(this);
      LegalChecks.checkIsSet("MonitoredAttributeName", this.isMonitoredAttributeNameSet());
      LegalChecks.checkIsSet("MonitoredMBeanType", this.isMonitoredMBeanTypeSet());
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._MonitoredAttributeName = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._MonitoredMBeanName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._MonitoredMBeanType = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._PollingInterval = 10;
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
      return "SNMPJMXMonitor";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("MonitoredAttributeName")) {
         oldVal = this._MonitoredAttributeName;
         this._MonitoredAttributeName = (String)v;
         this._postSet(13, oldVal, this._MonitoredAttributeName);
      } else if (name.equals("MonitoredMBeanName")) {
         oldVal = this._MonitoredMBeanName;
         this._MonitoredMBeanName = (String)v;
         this._postSet(12, oldVal, this._MonitoredMBeanName);
      } else if (name.equals("MonitoredMBeanType")) {
         oldVal = this._MonitoredMBeanType;
         this._MonitoredMBeanType = (String)v;
         this._postSet(11, oldVal, this._MonitoredMBeanType);
      } else if (name.equals("PollingInterval")) {
         int oldVal = this._PollingInterval;
         this._PollingInterval = (Integer)v;
         this._postSet(14, oldVal, this._PollingInterval);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("MonitoredAttributeName")) {
         return this._MonitoredAttributeName;
      } else if (name.equals("MonitoredMBeanName")) {
         return this._MonitoredMBeanName;
      } else if (name.equals("MonitoredMBeanType")) {
         return this._MonitoredMBeanType;
      } else {
         return name.equals("PollingInterval") ? new Integer(this._PollingInterval) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SNMPTrapSourceMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 16:
               if (s.equals("polling-interval")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("monitoredm-bean-name")) {
                  return 12;
               }

               if (s.equals("monitoredm-bean-type")) {
                  return 11;
               }
               break;
            case 24:
               if (s.equals("monitored-attribute-name")) {
                  return 13;
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
            case 11:
               return "monitoredm-bean-type";
            case 12:
               return "monitoredm-bean-name";
            case 13:
               return "monitored-attribute-name";
            case 14:
               return "polling-interval";
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

   protected static class Helper extends SNMPTrapSourceMBeanImpl.Helper {
      private SNMPJMXMonitorMBeanImpl bean;

      protected Helper(SNMPJMXMonitorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 11:
               return "MonitoredMBeanType";
            case 12:
               return "MonitoredMBeanName";
            case 13:
               return "MonitoredAttributeName";
            case 14:
               return "PollingInterval";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MonitoredAttributeName")) {
            return 13;
         } else if (propName.equals("MonitoredMBeanName")) {
            return 12;
         } else if (propName.equals("MonitoredMBeanType")) {
            return 11;
         } else {
            return propName.equals("PollingInterval") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isMonitoredAttributeNameSet()) {
               buf.append("MonitoredAttributeName");
               buf.append(String.valueOf(this.bean.getMonitoredAttributeName()));
            }

            if (this.bean.isMonitoredMBeanNameSet()) {
               buf.append("MonitoredMBeanName");
               buf.append(String.valueOf(this.bean.getMonitoredMBeanName()));
            }

            if (this.bean.isMonitoredMBeanTypeSet()) {
               buf.append("MonitoredMBeanType");
               buf.append(String.valueOf(this.bean.getMonitoredMBeanType()));
            }

            if (this.bean.isPollingIntervalSet()) {
               buf.append("PollingInterval");
               buf.append(String.valueOf(this.bean.getPollingInterval()));
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
            SNMPJMXMonitorMBeanImpl otherTyped = (SNMPJMXMonitorMBeanImpl)other;
            this.computeDiff("MonitoredAttributeName", this.bean.getMonitoredAttributeName(), otherTyped.getMonitoredAttributeName(), true);
            this.computeDiff("MonitoredMBeanName", this.bean.getMonitoredMBeanName(), otherTyped.getMonitoredMBeanName(), true);
            this.computeDiff("MonitoredMBeanType", this.bean.getMonitoredMBeanType(), otherTyped.getMonitoredMBeanType(), true);
            this.computeDiff("PollingInterval", this.bean.getPollingInterval(), otherTyped.getPollingInterval(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPJMXMonitorMBeanImpl original = (SNMPJMXMonitorMBeanImpl)event.getSourceBean();
            SNMPJMXMonitorMBeanImpl proposed = (SNMPJMXMonitorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MonitoredAttributeName")) {
                  original.setMonitoredAttributeName(proposed.getMonitoredAttributeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("MonitoredMBeanName")) {
                  original.setMonitoredMBeanName(proposed.getMonitoredMBeanName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MonitoredMBeanType")) {
                  original.setMonitoredMBeanType(proposed.getMonitoredMBeanType());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("PollingInterval")) {
                  original.setPollingInterval(proposed.getPollingInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            SNMPJMXMonitorMBeanImpl copy = (SNMPJMXMonitorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MonitoredAttributeName")) && this.bean.isMonitoredAttributeNameSet()) {
               copy.setMonitoredAttributeName(this.bean.getMonitoredAttributeName());
            }

            if ((excludeProps == null || !excludeProps.contains("MonitoredMBeanName")) && this.bean.isMonitoredMBeanNameSet()) {
               copy.setMonitoredMBeanName(this.bean.getMonitoredMBeanName());
            }

            if ((excludeProps == null || !excludeProps.contains("MonitoredMBeanType")) && this.bean.isMonitoredMBeanTypeSet()) {
               copy.setMonitoredMBeanType(this.bean.getMonitoredMBeanType());
            }

            if ((excludeProps == null || !excludeProps.contains("PollingInterval")) && this.bean.isPollingIntervalSet()) {
               copy.setPollingInterval(this.bean.getPollingInterval());
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
