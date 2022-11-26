package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;
import weblogic.work.WorkManagerValidator;

public class MaxThreadsConstraintBeanImpl extends SettableBeanImpl implements MaxThreadsConstraintBean, Serializable {
   private int _Count;
   private String _Id;
   private String _Name;
   private String _PoolName;
   private int _QueueSize;
   private static SchemaHelper2 _schemaHelper;

   public MaxThreadsConstraintBeanImpl() {
      this._initializeProperty(-1);
   }

   public MaxThreadsConstraintBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MaxThreadsConstraintBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getCount() {
      return this._Count;
   }

   public boolean isCountInherited() {
      return false;
   }

   public boolean isCountSet() {
      return this._isSet(1);
   }

   public void setCount(int param0) {
      WorkManagerValidator.validateMaxThreadsConstraintCount(param0);
      int _oldVal = this._Count;
      this._Count = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getPoolName() {
      return this._PoolName;
   }

   public boolean isPoolNameInherited() {
      return false;
   }

   public boolean isPoolNameSet() {
      return this._isSet(2);
   }

   public void setPoolName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PoolName;
      this._PoolName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void setQueueSize(int param0) {
      LegalChecks.checkInRange("QueueSize", (long)param0, 256L, 1073741824L);
      int _oldVal = this._QueueSize;
      this._QueueSize = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getQueueSize() {
      return this._QueueSize;
   }

   public boolean isQueueSizeInherited() {
      return false;
   }

   public boolean isQueueSizeSet() {
      return this._isSet(4);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
               this._Count = -1;
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._PoolName = null;
               if (initOne) {
                  break;
               }
            case 4:
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 3;
               }
            case 3:
            case 6:
            case 7:
            case 8:
            default:
               break;
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 5:
               if (s.equals("count")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("pool-name")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("queue-size")) {
                  return 4;
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
               return "name";
            case 1:
               return "count";
            case 2:
               return "pool-name";
            case 3:
               return "id";
            case 4:
               return "queue-size";
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
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private MaxThreadsConstraintBeanImpl bean;

      protected Helper(MaxThreadsConstraintBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "Count";
            case 2:
               return "PoolName";
            case 3:
               return "Id";
            case 4:
               return "QueueSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Count")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("PoolName")) {
            return 2;
         } else {
            return propName.equals("QueueSize") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isCountSet()) {
               buf.append("Count");
               buf.append(String.valueOf(this.bean.getCount()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPoolNameSet()) {
               buf.append("PoolName");
               buf.append(String.valueOf(this.bean.getPoolName()));
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
            MaxThreadsConstraintBeanImpl otherTyped = (MaxThreadsConstraintBeanImpl)other;
            this.computeDiff("Count", this.bean.getCount(), otherTyped.getCount(), true);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PoolName", this.bean.getPoolName(), otherTyped.getPoolName(), false);
            this.computeDiff("QueueSize", this.bean.getQueueSize(), otherTyped.getQueueSize(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MaxThreadsConstraintBeanImpl original = (MaxThreadsConstraintBeanImpl)event.getSourceBean();
            MaxThreadsConstraintBeanImpl proposed = (MaxThreadsConstraintBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Count")) {
                  original.setCount(proposed.getCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("PoolName")) {
                  original.setPoolName(proposed.getPoolName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("QueueSize")) {
                  original.setQueueSize(proposed.getQueueSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            MaxThreadsConstraintBeanImpl copy = (MaxThreadsConstraintBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Count")) && this.bean.isCountSet()) {
               copy.setCount(this.bean.getCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PoolName")) && this.bean.isPoolNameSet()) {
               copy.setPoolName(this.bean.getPoolName());
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
