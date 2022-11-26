package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SNMPCounterMonitorMBeanImpl extends SNMPJMXMonitorMBeanImpl implements SNMPCounterMonitorMBean, Serializable {
   private long _Modulus;
   private long _Offset;
   private long _Threshold;
   private static SchemaHelper2 _schemaHelper;

   public SNMPCounterMonitorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPCounterMonitorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPCounterMonitorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public long getThreshold() {
      return this._Threshold;
   }

   public boolean isThresholdInherited() {
      return false;
   }

   public boolean isThresholdSet() {
      return this._isSet(15);
   }

   public void setThreshold(long param0) {
      LegalChecks.checkMin("Threshold", param0, 0L);
      long _oldVal = this._Threshold;
      this._Threshold = param0;
      this._postSet(15, _oldVal, param0);
   }

   public long getOffset() {
      return this._Offset;
   }

   public boolean isOffsetInherited() {
      return false;
   }

   public boolean isOffsetSet() {
      return this._isSet(16);
   }

   public void setOffset(long param0) {
      LegalChecks.checkMin("Offset", param0, 0L);
      long _oldVal = this._Offset;
      this._Offset = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getModulus() {
      return this._Modulus;
   }

   public boolean isModulusInherited() {
      return false;
   }

   public boolean isModulusSet() {
      return this._isSet(17);
   }

   public void setModulus(long param0) {
      LegalChecks.checkMin("Modulus", param0, 0L);
      long _oldVal = this._Modulus;
      this._Modulus = param0;
      this._postSet(17, _oldVal, param0);
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._Modulus = 0L;
               if (initOne) {
                  break;
               }
            case 16:
               this._Offset = 0L;
               if (initOne) {
                  break;
               }
            case 15:
               this._Threshold = 0L;
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
      return "SNMPCounterMonitor";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("Modulus")) {
         oldVal = this._Modulus;
         this._Modulus = (Long)v;
         this._postSet(17, oldVal, this._Modulus);
      } else if (name.equals("Offset")) {
         oldVal = this._Offset;
         this._Offset = (Long)v;
         this._postSet(16, oldVal, this._Offset);
      } else if (name.equals("Threshold")) {
         oldVal = this._Threshold;
         this._Threshold = (Long)v;
         this._postSet(15, oldVal, this._Threshold);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Modulus")) {
         return new Long(this._Modulus);
      } else if (name.equals("Offset")) {
         return new Long(this._Offset);
      } else {
         return name.equals("Threshold") ? new Long(this._Threshold) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SNMPJMXMonitorMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("offset")) {
                  return 16;
               }
               break;
            case 7:
               if (s.equals("modulus")) {
                  return 17;
               }
            case 8:
            default:
               break;
            case 9:
               if (s.equals("threshold")) {
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
               return "threshold";
            case 16:
               return "offset";
            case 17:
               return "modulus";
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
      private SNMPCounterMonitorMBeanImpl bean;

      protected Helper(SNMPCounterMonitorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 15:
               return "Threshold";
            case 16:
               return "Offset";
            case 17:
               return "Modulus";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Modulus")) {
            return 17;
         } else if (propName.equals("Offset")) {
            return 16;
         } else {
            return propName.equals("Threshold") ? 15 : super.getPropertyIndex(propName);
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
            if (this.bean.isModulusSet()) {
               buf.append("Modulus");
               buf.append(String.valueOf(this.bean.getModulus()));
            }

            if (this.bean.isOffsetSet()) {
               buf.append("Offset");
               buf.append(String.valueOf(this.bean.getOffset()));
            }

            if (this.bean.isThresholdSet()) {
               buf.append("Threshold");
               buf.append(String.valueOf(this.bean.getThreshold()));
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
            SNMPCounterMonitorMBeanImpl otherTyped = (SNMPCounterMonitorMBeanImpl)other;
            this.computeDiff("Modulus", this.bean.getModulus(), otherTyped.getModulus(), true);
            this.computeDiff("Offset", this.bean.getOffset(), otherTyped.getOffset(), true);
            this.computeDiff("Threshold", this.bean.getThreshold(), otherTyped.getThreshold(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPCounterMonitorMBeanImpl original = (SNMPCounterMonitorMBeanImpl)event.getSourceBean();
            SNMPCounterMonitorMBeanImpl proposed = (SNMPCounterMonitorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Modulus")) {
                  original.setModulus(proposed.getModulus());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("Offset")) {
                  original.setOffset(proposed.getOffset());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Threshold")) {
                  original.setThreshold(proposed.getThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
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
            SNMPCounterMonitorMBeanImpl copy = (SNMPCounterMonitorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Modulus")) && this.bean.isModulusSet()) {
               copy.setModulus(this.bean.getModulus());
            }

            if ((excludeProps == null || !excludeProps.contains("Offset")) && this.bean.isOffsetSet()) {
               copy.setOffset(this.bean.getOffset());
            }

            if ((excludeProps == null || !excludeProps.contains("Threshold")) && this.bean.isThresholdSet()) {
               copy.setThreshold(this.bean.getThreshold());
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
