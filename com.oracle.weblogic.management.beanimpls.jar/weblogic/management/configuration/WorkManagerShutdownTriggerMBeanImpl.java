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

public class WorkManagerShutdownTriggerMBeanImpl extends ConfigurationMBeanImpl implements WorkManagerShutdownTriggerMBean, Serializable {
   private int _MaxStuckThreadTime;
   private boolean _ResumeWhenUnstuck;
   private int _StuckThreadCount;
   private static SchemaHelper2 _schemaHelper;

   public WorkManagerShutdownTriggerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WorkManagerShutdownTriggerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WorkManagerShutdownTriggerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxStuckThreadTime() {
      return this._MaxStuckThreadTime;
   }

   public boolean isMaxStuckThreadTimeInherited() {
      return false;
   }

   public boolean isMaxStuckThreadTimeSet() {
      return this._isSet(10);
   }

   public void setMaxStuckThreadTime(int param0) {
      int _oldVal = this._MaxStuckThreadTime;
      this._MaxStuckThreadTime = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getStuckThreadCount() {
      return this._StuckThreadCount;
   }

   public boolean isStuckThreadCountInherited() {
      return false;
   }

   public boolean isStuckThreadCountSet() {
      return this._isSet(11);
   }

   public void setStuckThreadCount(int param0) {
      int _oldVal = this._StuckThreadCount;
      this._StuckThreadCount = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isResumeWhenUnstuck() {
      return this._ResumeWhenUnstuck;
   }

   public boolean isResumeWhenUnstuckInherited() {
      return false;
   }

   public boolean isResumeWhenUnstuckSet() {
      return this._isSet(12);
   }

   public void setResumeWhenUnstuck(boolean param0) {
      boolean _oldVal = this._ResumeWhenUnstuck;
      this._ResumeWhenUnstuck = param0;
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._MaxStuckThreadTime = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._StuckThreadCount = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._ResumeWhenUnstuck = true;
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
      return "WorkManagerShutdownTrigger";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("MaxStuckThreadTime")) {
         oldVal = this._MaxStuckThreadTime;
         this._MaxStuckThreadTime = (Integer)v;
         this._postSet(10, oldVal, this._MaxStuckThreadTime);
      } else if (name.equals("ResumeWhenUnstuck")) {
         boolean oldVal = this._ResumeWhenUnstuck;
         this._ResumeWhenUnstuck = (Boolean)v;
         this._postSet(12, oldVal, this._ResumeWhenUnstuck);
      } else if (name.equals("StuckThreadCount")) {
         oldVal = this._StuckThreadCount;
         this._StuckThreadCount = (Integer)v;
         this._postSet(11, oldVal, this._StuckThreadCount);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("MaxStuckThreadTime")) {
         return new Integer(this._MaxStuckThreadTime);
      } else if (name.equals("ResumeWhenUnstuck")) {
         return new Boolean(this._ResumeWhenUnstuck);
      } else {
         return name.equals("StuckThreadCount") ? new Integer(this._StuckThreadCount) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 18:
               if (s.equals("stuck-thread-count")) {
                  return 11;
               }
               break;
            case 19:
               if (s.equals("resume-when-unstuck")) {
                  return 12;
               }
            case 20:
            default:
               break;
            case 21:
               if (s.equals("max-stuck-thread-time")) {
                  return 10;
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
               return "max-stuck-thread-time";
            case 11:
               return "stuck-thread-count";
            case 12:
               return "resume-when-unstuck";
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
      private WorkManagerShutdownTriggerMBeanImpl bean;

      protected Helper(WorkManagerShutdownTriggerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "MaxStuckThreadTime";
            case 11:
               return "StuckThreadCount";
            case 12:
               return "ResumeWhenUnstuck";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MaxStuckThreadTime")) {
            return 10;
         } else if (propName.equals("StuckThreadCount")) {
            return 11;
         } else {
            return propName.equals("ResumeWhenUnstuck") ? 12 : super.getPropertyIndex(propName);
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
            if (this.bean.isMaxStuckThreadTimeSet()) {
               buf.append("MaxStuckThreadTime");
               buf.append(String.valueOf(this.bean.getMaxStuckThreadTime()));
            }

            if (this.bean.isStuckThreadCountSet()) {
               buf.append("StuckThreadCount");
               buf.append(String.valueOf(this.bean.getStuckThreadCount()));
            }

            if (this.bean.isResumeWhenUnstuckSet()) {
               buf.append("ResumeWhenUnstuck");
               buf.append(String.valueOf(this.bean.isResumeWhenUnstuck()));
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
            WorkManagerShutdownTriggerMBeanImpl otherTyped = (WorkManagerShutdownTriggerMBeanImpl)other;
            this.computeDiff("MaxStuckThreadTime", this.bean.getMaxStuckThreadTime(), otherTyped.getMaxStuckThreadTime(), false);
            this.computeDiff("StuckThreadCount", this.bean.getStuckThreadCount(), otherTyped.getStuckThreadCount(), false);
            this.computeDiff("ResumeWhenUnstuck", this.bean.isResumeWhenUnstuck(), otherTyped.isResumeWhenUnstuck(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WorkManagerShutdownTriggerMBeanImpl original = (WorkManagerShutdownTriggerMBeanImpl)event.getSourceBean();
            WorkManagerShutdownTriggerMBeanImpl proposed = (WorkManagerShutdownTriggerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MaxStuckThreadTime")) {
                  original.setMaxStuckThreadTime(proposed.getMaxStuckThreadTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("StuckThreadCount")) {
                  original.setStuckThreadCount(proposed.getStuckThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ResumeWhenUnstuck")) {
                  original.setResumeWhenUnstuck(proposed.isResumeWhenUnstuck());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            WorkManagerShutdownTriggerMBeanImpl copy = (WorkManagerShutdownTriggerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MaxStuckThreadTime")) && this.bean.isMaxStuckThreadTimeSet()) {
               copy.setMaxStuckThreadTime(this.bean.getMaxStuckThreadTime());
            }

            if ((excludeProps == null || !excludeProps.contains("StuckThreadCount")) && this.bean.isStuckThreadCountSet()) {
               copy.setStuckThreadCount(this.bean.getStuckThreadCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ResumeWhenUnstuck")) && this.bean.isResumeWhenUnstuckSet()) {
               copy.setResumeWhenUnstuck(this.bean.isResumeWhenUnstuck());
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
