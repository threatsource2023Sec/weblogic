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
import weblogic.work.WorkManagerValidator;

public class PartitionWorkManagerMBeanImpl extends ConfigurationMBeanImpl implements PartitionWorkManagerMBean, Serializable {
   private int _FairShare;
   private int _MaxThreadsConstraint;
   private int _MaxThreadsConstraintQueueSize;
   private int _MinThreadsConstraintCap;
   private int _SharedCapacityPercent;
   private static SchemaHelper2 _schemaHelper;

   public PartitionWorkManagerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public PartitionWorkManagerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PartitionWorkManagerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getSharedCapacityPercent() {
      return this._SharedCapacityPercent;
   }

   public boolean isSharedCapacityPercentInherited() {
      return false;
   }

   public boolean isSharedCapacityPercentSet() {
      return this._isSet(10);
   }

   public void setSharedCapacityPercent(int param0) {
      LegalChecks.checkInRange("SharedCapacityPercent", (long)param0, 1L, 100L);
      int _oldVal = this._SharedCapacityPercent;
      this._SharedCapacityPercent = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getFairShare() {
      return this._FairShare;
   }

   public boolean isFairShareInherited() {
      return false;
   }

   public boolean isFairShareSet() {
      return this._isSet(11);
   }

   public void setFairShare(int param0) {
      LegalChecks.checkInRange("FairShare", (long)param0, 1L, 99L);
      int _oldVal = this._FairShare;
      this._FairShare = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getMinThreadsConstraintCap() {
      return this._MinThreadsConstraintCap;
   }

   public boolean isMinThreadsConstraintCapInherited() {
      return false;
   }

   public boolean isMinThreadsConstraintCapSet() {
      return this._isSet(12);
   }

   public void setMinThreadsConstraintCap(int param0) {
      LegalChecks.checkInRange("MinThreadsConstraintCap", (long)param0, 0L, 65534L);
      int _oldVal = this._MinThreadsConstraintCap;
      this._MinThreadsConstraintCap = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getMaxThreadsConstraint() {
      return this._MaxThreadsConstraint;
   }

   public boolean isMaxThreadsConstraintInherited() {
      return false;
   }

   public boolean isMaxThreadsConstraintSet() {
      return this._isSet(13);
   }

   public void setMaxThreadsConstraint(int param0) {
      WorkManagerValidator.validateMaxThreadsConstraintCount(param0);
      int _oldVal = this._MaxThreadsConstraint;
      this._MaxThreadsConstraint = param0;
      this._postSet(13, _oldVal, param0);
   }

   public void setMaxThreadsConstraintQueueSize(int param0) {
      LegalChecks.checkInRange("MaxThreadsConstraintQueueSize", (long)param0, 256L, 1073741824L);
      int _oldVal = this._MaxThreadsConstraintQueueSize;
      this._MaxThreadsConstraintQueueSize = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMaxThreadsConstraintQueueSize() {
      return this._MaxThreadsConstraintQueueSize;
   }

   public boolean isMaxThreadsConstraintQueueSizeInherited() {
      return false;
   }

   public boolean isMaxThreadsConstraintQueueSizeSet() {
      return this._isSet(14);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._FairShare = 50;
               if (initOne) {
                  break;
               }
            case 13:
               this._MaxThreadsConstraint = -1;
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxThreadsConstraintQueueSize = 8192;
               if (initOne) {
                  break;
               }
            case 12:
               this._MinThreadsConstraintCap = 0;
               if (initOne) {
                  break;
               }
            case 10:
               this._SharedCapacityPercent = 100;
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
      return "PartitionWorkManager";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("FairShare")) {
         oldVal = this._FairShare;
         this._FairShare = (Integer)v;
         this._postSet(11, oldVal, this._FairShare);
      } else if (name.equals("MaxThreadsConstraint")) {
         oldVal = this._MaxThreadsConstraint;
         this._MaxThreadsConstraint = (Integer)v;
         this._postSet(13, oldVal, this._MaxThreadsConstraint);
      } else if (name.equals("MaxThreadsConstraintQueueSize")) {
         oldVal = this._MaxThreadsConstraintQueueSize;
         this._MaxThreadsConstraintQueueSize = (Integer)v;
         this._postSet(14, oldVal, this._MaxThreadsConstraintQueueSize);
      } else if (name.equals("MinThreadsConstraintCap")) {
         oldVal = this._MinThreadsConstraintCap;
         this._MinThreadsConstraintCap = (Integer)v;
         this._postSet(12, oldVal, this._MinThreadsConstraintCap);
      } else if (name.equals("SharedCapacityPercent")) {
         oldVal = this._SharedCapacityPercent;
         this._SharedCapacityPercent = (Integer)v;
         this._postSet(10, oldVal, this._SharedCapacityPercent);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("FairShare")) {
         return new Integer(this._FairShare);
      } else if (name.equals("MaxThreadsConstraint")) {
         return new Integer(this._MaxThreadsConstraint);
      } else if (name.equals("MaxThreadsConstraintQueueSize")) {
         return new Integer(this._MaxThreadsConstraintQueueSize);
      } else if (name.equals("MinThreadsConstraintCap")) {
         return new Integer(this._MinThreadsConstraintCap);
      } else {
         return name.equals("SharedCapacityPercent") ? new Integer(this._SharedCapacityPercent) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("fair-share")) {
                  return 11;
               }
               break;
            case 22:
               if (s.equals("max-threads-constraint")) {
                  return 13;
               }
               break;
            case 23:
               if (s.equals("shared-capacity-percent")) {
                  return 10;
               }
               break;
            case 26:
               if (s.equals("min-threads-constraint-cap")) {
                  return 12;
               }
               break;
            case 33:
               if (s.equals("max-threads-constraint-queue-size")) {
                  return 14;
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
            case 10:
               return "shared-capacity-percent";
            case 11:
               return "fair-share";
            case 12:
               return "min-threads-constraint-cap";
            case 13:
               return "max-threads-constraint";
            case 14:
               return "max-threads-constraint-queue-size";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private PartitionWorkManagerMBeanImpl bean;

      protected Helper(PartitionWorkManagerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "SharedCapacityPercent";
            case 11:
               return "FairShare";
            case 12:
               return "MinThreadsConstraintCap";
            case 13:
               return "MaxThreadsConstraint";
            case 14:
               return "MaxThreadsConstraintQueueSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FairShare")) {
            return 11;
         } else if (propName.equals("MaxThreadsConstraint")) {
            return 13;
         } else if (propName.equals("MaxThreadsConstraintQueueSize")) {
            return 14;
         } else if (propName.equals("MinThreadsConstraintCap")) {
            return 12;
         } else {
            return propName.equals("SharedCapacityPercent") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isFairShareSet()) {
               buf.append("FairShare");
               buf.append(String.valueOf(this.bean.getFairShare()));
            }

            if (this.bean.isMaxThreadsConstraintSet()) {
               buf.append("MaxThreadsConstraint");
               buf.append(String.valueOf(this.bean.getMaxThreadsConstraint()));
            }

            if (this.bean.isMaxThreadsConstraintQueueSizeSet()) {
               buf.append("MaxThreadsConstraintQueueSize");
               buf.append(String.valueOf(this.bean.getMaxThreadsConstraintQueueSize()));
            }

            if (this.bean.isMinThreadsConstraintCapSet()) {
               buf.append("MinThreadsConstraintCap");
               buf.append(String.valueOf(this.bean.getMinThreadsConstraintCap()));
            }

            if (this.bean.isSharedCapacityPercentSet()) {
               buf.append("SharedCapacityPercent");
               buf.append(String.valueOf(this.bean.getSharedCapacityPercent()));
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
            PartitionWorkManagerMBeanImpl otherTyped = (PartitionWorkManagerMBeanImpl)other;
            this.computeDiff("FairShare", this.bean.getFairShare(), otherTyped.getFairShare(), true);
            this.computeDiff("MaxThreadsConstraint", this.bean.getMaxThreadsConstraint(), otherTyped.getMaxThreadsConstraint(), true);
            this.computeDiff("MaxThreadsConstraintQueueSize", this.bean.getMaxThreadsConstraintQueueSize(), otherTyped.getMaxThreadsConstraintQueueSize(), false);
            this.computeDiff("MinThreadsConstraintCap", this.bean.getMinThreadsConstraintCap(), otherTyped.getMinThreadsConstraintCap(), true);
            this.computeDiff("SharedCapacityPercent", this.bean.getSharedCapacityPercent(), otherTyped.getSharedCapacityPercent(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PartitionWorkManagerMBeanImpl original = (PartitionWorkManagerMBeanImpl)event.getSourceBean();
            PartitionWorkManagerMBeanImpl proposed = (PartitionWorkManagerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FairShare")) {
                  original.setFairShare(proposed.getFairShare());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MaxThreadsConstraint")) {
                  original.setMaxThreadsConstraint(proposed.getMaxThreadsConstraint());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("MaxThreadsConstraintQueueSize")) {
                  original.setMaxThreadsConstraintQueueSize(proposed.getMaxThreadsConstraintQueueSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MinThreadsConstraintCap")) {
                  original.setMinThreadsConstraintCap(proposed.getMinThreadsConstraintCap());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("SharedCapacityPercent")) {
                  original.setSharedCapacityPercent(proposed.getSharedCapacityPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            PartitionWorkManagerMBeanImpl copy = (PartitionWorkManagerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FairShare")) && this.bean.isFairShareSet()) {
               copy.setFairShare(this.bean.getFairShare());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadsConstraint")) && this.bean.isMaxThreadsConstraintSet()) {
               copy.setMaxThreadsConstraint(this.bean.getMaxThreadsConstraint());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadsConstraintQueueSize")) && this.bean.isMaxThreadsConstraintQueueSizeSet()) {
               copy.setMaxThreadsConstraintQueueSize(this.bean.getMaxThreadsConstraintQueueSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MinThreadsConstraintCap")) && this.bean.isMinThreadsConstraintCapSet()) {
               copy.setMinThreadsConstraintCap(this.bean.getMinThreadsConstraintCap());
            }

            if ((excludeProps == null || !excludeProps.contains("SharedCapacityPercent")) && this.bean.isSharedCapacityPercentSet()) {
               copy.setSharedCapacityPercent(this.bean.getSharedCapacityPercent());
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
