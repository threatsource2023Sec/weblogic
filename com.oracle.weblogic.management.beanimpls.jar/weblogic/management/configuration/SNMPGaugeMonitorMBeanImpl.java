package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SNMPGaugeMonitorMBeanImpl extends SNMPJMXMonitorMBeanImpl implements SNMPGaugeMonitorMBean, Serializable {
   private double _ThresholdHigh;
   private double _ThresholdLow;
   private static SchemaHelper2 _schemaHelper;

   public SNMPGaugeMonitorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPGaugeMonitorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPGaugeMonitorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public double getThresholdHigh() {
      return this._ThresholdHigh;
   }

   public boolean isThresholdHighInherited() {
      return false;
   }

   public boolean isThresholdHighSet() {
      return this._isSet(15);
   }

   public void setThresholdHigh(double param0) {
      double _oldVal = this._ThresholdHigh;
      this._ThresholdHigh = param0;
      this._postSet(15, _oldVal, param0);
   }

   public double getThresholdLow() {
      return this._ThresholdLow;
   }

   public boolean isThresholdLowInherited() {
      return false;
   }

   public boolean isThresholdLowSet() {
      return this._isSet(16);
   }

   public void setThresholdLow(double param0) {
      double _oldVal = this._ThresholdLow;
      this._ThresholdLow = param0;
      this._postSet(16, _oldVal, param0);
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._ThresholdHigh = 0.0;
               if (initOne) {
                  break;
               }
            case 16:
               this._ThresholdLow = 0.0;
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
      return "SNMPGaugeMonitor";
   }

   public void putValue(String name, Object v) {
      double oldVal;
      if (name.equals("ThresholdHigh")) {
         oldVal = this._ThresholdHigh;
         this._ThresholdHigh = (Double)v;
         this._postSet(15, oldVal, this._ThresholdHigh);
      } else if (name.equals("ThresholdLow")) {
         oldVal = this._ThresholdLow;
         this._ThresholdLow = (Double)v;
         this._postSet(16, oldVal, this._ThresholdLow);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ThresholdHigh")) {
         return new Double(this._ThresholdHigh);
      } else {
         return name.equals("ThresholdLow") ? new Double(this._ThresholdLow) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SNMPJMXMonitorMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("threshold-low")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("threshold-high")) {
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
               return "threshold-high";
            case 16:
               return "threshold-low";
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
      private SNMPGaugeMonitorMBeanImpl bean;

      protected Helper(SNMPGaugeMonitorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 15:
               return "ThresholdHigh";
            case 16:
               return "ThresholdLow";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ThresholdHigh")) {
            return 15;
         } else {
            return propName.equals("ThresholdLow") ? 16 : super.getPropertyIndex(propName);
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
            if (this.bean.isThresholdHighSet()) {
               buf.append("ThresholdHigh");
               buf.append(String.valueOf(this.bean.getThresholdHigh()));
            }

            if (this.bean.isThresholdLowSet()) {
               buf.append("ThresholdLow");
               buf.append(String.valueOf(this.bean.getThresholdLow()));
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
            SNMPGaugeMonitorMBeanImpl otherTyped = (SNMPGaugeMonitorMBeanImpl)other;
            this.computeDiff("ThresholdHigh", this.bean.getThresholdHigh(), otherTyped.getThresholdHigh(), true);
            this.computeDiff("ThresholdLow", this.bean.getThresholdLow(), otherTyped.getThresholdLow(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPGaugeMonitorMBeanImpl original = (SNMPGaugeMonitorMBeanImpl)event.getSourceBean();
            SNMPGaugeMonitorMBeanImpl proposed = (SNMPGaugeMonitorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ThresholdHigh")) {
                  original.setThresholdHigh(proposed.getThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("ThresholdLow")) {
                  original.setThresholdLow(proposed.getThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            SNMPGaugeMonitorMBeanImpl copy = (SNMPGaugeMonitorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ThresholdHigh")) && this.bean.isThresholdHighSet()) {
               copy.setThresholdHigh(this.bean.getThresholdHigh());
            }

            if ((excludeProps == null || !excludeProps.contains("ThresholdLow")) && this.bean.isThresholdLowSet()) {
               copy.setThresholdLow(this.bean.getThresholdLow());
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
