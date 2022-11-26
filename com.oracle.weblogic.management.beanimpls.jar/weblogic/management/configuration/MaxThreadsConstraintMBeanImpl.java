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

public class MaxThreadsConstraintMBeanImpl extends DeploymentMBeanImpl implements MaxThreadsConstraintMBean, Serializable {
   private String _ConnectionPoolName;
   private int _Count;
   private int _QueueSize;
   private static SchemaHelper2 _schemaHelper;

   public MaxThreadsConstraintMBeanImpl() {
      this._initializeProperty(-1);
   }

   public MaxThreadsConstraintMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MaxThreadsConstraintMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getCount() {
      return this._Count;
   }

   public boolean isCountInherited() {
      return false;
   }

   public boolean isCountSet() {
      return this._isSet(12);
   }

   public void setCount(int param0) {
      WorkManagerValidator.validateMaxThreadsConstraintCount(param0);
      int _oldVal = this._Count;
      this._Count = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getConnectionPoolName() {
      return this._ConnectionPoolName;
   }

   public boolean isConnectionPoolNameInherited() {
      return false;
   }

   public boolean isConnectionPoolNameSet() {
      return this._isSet(13);
   }

   public void setConnectionPoolName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionPoolName;
      this._ConnectionPoolName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public void setQueueSize(int param0) {
      LegalChecks.checkInRange("QueueSize", (long)param0, 256L, 1073741824L);
      int _oldVal = this._QueueSize;
      this._QueueSize = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getQueueSize() {
      return this._QueueSize;
   }

   public boolean isQueueSizeInherited() {
      return false;
   }

   public boolean isQueueSizeSet() {
      return this._isSet(14);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WorkManagerLegalHelper.validateMaxThreadsConstraint(this);
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
               this._ConnectionPoolName = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._Count = -1;
               if (initOne) {
                  break;
               }
            case 14:
               this._QueueSize = 8192;
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
      return "MaxThreadsConstraint";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ConnectionPoolName")) {
         String oldVal = this._ConnectionPoolName;
         this._ConnectionPoolName = (String)v;
         this._postSet(13, oldVal, this._ConnectionPoolName);
      } else {
         int oldVal;
         if (name.equals("Count")) {
            oldVal = this._Count;
            this._Count = (Integer)v;
            this._postSet(12, oldVal, this._Count);
         } else if (name.equals("QueueSize")) {
            oldVal = this._QueueSize;
            this._QueueSize = (Integer)v;
            this._postSet(14, oldVal, this._QueueSize);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionPoolName")) {
         return this._ConnectionPoolName;
      } else if (name.equals("Count")) {
         return new Integer(this._Count);
      } else {
         return name.equals("QueueSize") ? new Integer(this._QueueSize) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("count")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("queue-size")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("connection-pool-name")) {
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
            case 12:
               return "count";
            case 13:
               return "connection-pool-name";
            case 14:
               return "queue-size";
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private MaxThreadsConstraintMBeanImpl bean;

      protected Helper(MaxThreadsConstraintMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "Count";
            case 13:
               return "ConnectionPoolName";
            case 14:
               return "QueueSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionPoolName")) {
            return 13;
         } else if (propName.equals("Count")) {
            return 12;
         } else {
            return propName.equals("QueueSize") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionPoolNameSet()) {
               buf.append("ConnectionPoolName");
               buf.append(String.valueOf(this.bean.getConnectionPoolName()));
            }

            if (this.bean.isCountSet()) {
               buf.append("Count");
               buf.append(String.valueOf(this.bean.getCount()));
            }

            if (this.bean.isQueueSizeSet()) {
               buf.append("QueueSize");
               buf.append(String.valueOf(this.bean.getQueueSize()));
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
            MaxThreadsConstraintMBeanImpl otherTyped = (MaxThreadsConstraintMBeanImpl)other;
            this.computeDiff("ConnectionPoolName", this.bean.getConnectionPoolName(), otherTyped.getConnectionPoolName(), false);
            this.computeDiff("Count", this.bean.getCount(), otherTyped.getCount(), true);
            this.computeDiff("QueueSize", this.bean.getQueueSize(), otherTyped.getQueueSize(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MaxThreadsConstraintMBeanImpl original = (MaxThreadsConstraintMBeanImpl)event.getSourceBean();
            MaxThreadsConstraintMBeanImpl proposed = (MaxThreadsConstraintMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionPoolName")) {
                  original.setConnectionPoolName(proposed.getConnectionPoolName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Count")) {
                  original.setCount(proposed.getCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("QueueSize")) {
                  original.setQueueSize(proposed.getQueueSize());
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
            MaxThreadsConstraintMBeanImpl copy = (MaxThreadsConstraintMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionPoolName")) && this.bean.isConnectionPoolNameSet()) {
               copy.setConnectionPoolName(this.bean.getConnectionPoolName());
            }

            if ((excludeProps == null || !excludeProps.contains("Count")) && this.bean.isCountSet()) {
               copy.setCount(this.bean.getCount());
            }

            if ((excludeProps == null || !excludeProps.contains("QueueSize")) && this.bean.isQueueSizeSet()) {
               copy.setQueueSize(this.bean.getQueueSize());
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
