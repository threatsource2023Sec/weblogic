package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class EntityDescriptorBeanImpl extends AbstractDescriptorBean implements EntityDescriptorBean, Serializable {
   private boolean _EnableDynamicQueries;
   private EntityCacheBean _EntityCache;
   private EntityCacheRefBean _EntityCacheRef;
   private EntityClusteringBean _EntityClustering;
   private String _Id;
   private InvalidationTargetBean _InvalidationTarget;
   private PersistenceBean _Persistence;
   private PoolBean _Pool;
   private TimerDescriptorBean _TimerDescriptor;
   private static SchemaHelper2 _schemaHelper;

   public EntityDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public EntityDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EntityDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public PoolBean getPool() {
      return this._Pool;
   }

   public boolean isPoolInherited() {
      return false;
   }

   public boolean isPoolSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getPool());
   }

   public void setPool(PoolBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      PoolBean _oldVal = this._Pool;
      this._Pool = param0;
      this._postSet(0, _oldVal, param0);
   }

   public TimerDescriptorBean getTimerDescriptor() {
      return this._TimerDescriptor;
   }

   public boolean isTimerDescriptorInherited() {
      return false;
   }

   public boolean isTimerDescriptorSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getTimerDescriptor());
   }

   public void setTimerDescriptor(TimerDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      TimerDescriptorBean _oldVal = this._TimerDescriptor;
      this._TimerDescriptor = param0;
      this._postSet(1, _oldVal, param0);
   }

   public EntityCacheBean getEntityCache() {
      return this._EntityCache;
   }

   public boolean isEntityCacheInherited() {
      return false;
   }

   public boolean isEntityCacheSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getEntityCache());
   }

   public void setEntityCache(EntityCacheBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      EntityCacheBean _oldVal = this._EntityCache;
      this._EntityCache = param0;
      this._postSet(2, _oldVal, param0);
   }

   public EntityCacheRefBean getEntityCacheRef() {
      return this._EntityCacheRef;
   }

   public boolean isEntityCacheRefInherited() {
      return false;
   }

   public boolean isEntityCacheRefSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getEntityCacheRef());
   }

   public void setEntityCacheRef(EntityCacheRefBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      EntityCacheRefBean _oldVal = this._EntityCacheRef;
      this._EntityCacheRef = param0;
      this._postSet(3, _oldVal, param0);
   }

   public PersistenceBean getPersistence() {
      return this._Persistence;
   }

   public boolean isPersistenceInherited() {
      return false;
   }

   public boolean isPersistenceSet() {
      return this._isSet(4) || this._isAnythingSet((AbstractDescriptorBean)this.getPersistence());
   }

   public void setPersistence(PersistenceBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 4)) {
         this._postCreate(_child);
      }

      PersistenceBean _oldVal = this._Persistence;
      this._Persistence = param0;
      this._postSet(4, _oldVal, param0);
   }

   public EntityClusteringBean getEntityClustering() {
      return this._EntityClustering;
   }

   public boolean isEntityClusteringInherited() {
      return false;
   }

   public boolean isEntityClusteringSet() {
      return this._isSet(5) || this._isAnythingSet((AbstractDescriptorBean)this.getEntityClustering());
   }

   public void setEntityClustering(EntityClusteringBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 5)) {
         this._postCreate(_child);
      }

      EntityClusteringBean _oldVal = this._EntityClustering;
      this._EntityClustering = param0;
      this._postSet(5, _oldVal, param0);
   }

   public InvalidationTargetBean getInvalidationTarget() {
      return this._InvalidationTarget;
   }

   public boolean isInvalidationTargetInherited() {
      return false;
   }

   public boolean isInvalidationTargetSet() {
      return this._isSet(6) || this._isAnythingSet((AbstractDescriptorBean)this.getInvalidationTarget());
   }

   public void setInvalidationTarget(InvalidationTargetBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 6)) {
         this._postCreate(_child);
      }

      InvalidationTargetBean _oldVal = this._InvalidationTarget;
      this._InvalidationTarget = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean isEnableDynamicQueries() {
      return this._EnableDynamicQueries;
   }

   public boolean isEnableDynamicQueriesInherited() {
      return false;
   }

   public boolean isEnableDynamicQueriesSet() {
      return this._isSet(7);
   }

   public void setEnableDynamicQueries(boolean param0) {
      boolean _oldVal = this._EnableDynamicQueries;
      this._EnableDynamicQueries = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(8);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(8, _oldVal, param0);
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
      return super._isAnythingSet() || this.isEntityCacheSet() || this.isEntityCacheRefSet() || this.isEntityClusteringSet() || this.isInvalidationTargetSet() || this.isPersistenceSet() || this.isPoolSet() || this.isTimerDescriptorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._EntityCache = new EntityCacheBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._EntityCache);
               if (initOne) {
                  break;
               }
            case 3:
               this._EntityCacheRef = new EntityCacheRefBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._EntityCacheRef);
               if (initOne) {
                  break;
               }
            case 5:
               this._EntityClustering = new EntityClusteringBeanImpl(this, 5);
               this._postCreate((AbstractDescriptorBean)this._EntityClustering);
               if (initOne) {
                  break;
               }
            case 8:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._InvalidationTarget = new InvalidationTargetBeanImpl(this, 6);
               this._postCreate((AbstractDescriptorBean)this._InvalidationTarget);
               if (initOne) {
                  break;
               }
            case 4:
               this._Persistence = new PersistenceBeanImpl(this, 4);
               this._postCreate((AbstractDescriptorBean)this._Persistence);
               if (initOne) {
                  break;
               }
            case 0:
               this._Pool = new PoolBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._Pool);
               if (initOne) {
                  break;
               }
            case 1:
               this._TimerDescriptor = new TimerDescriptorBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._TimerDescriptor);
               if (initOne) {
                  break;
               }
            case 7:
               this._EnableDynamicQueries = false;
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 8;
               }
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 18:
            case 20:
            case 21:
            default:
               break;
            case 4:
               if (s.equals("pool")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("persistence")) {
                  return 4;
               }
               break;
            case 12:
               if (s.equals("entity-cache")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("entity-cache-ref")) {
                  return 3;
               }

               if (s.equals("timer-descriptor")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("entity-clustering")) {
                  return 5;
               }
               break;
            case 19:
               if (s.equals("invalidation-target")) {
                  return 6;
               }
               break;
            case 22:
               if (s.equals("enable-dynamic-queries")) {
                  return 7;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new PoolBeanImpl.SchemaHelper2();
            case 1:
               return new TimerDescriptorBeanImpl.SchemaHelper2();
            case 2:
               return new EntityCacheBeanImpl.SchemaHelper2();
            case 3:
               return new EntityCacheRefBeanImpl.SchemaHelper2();
            case 4:
               return new PersistenceBeanImpl.SchemaHelper2();
            case 5:
               return new EntityClusteringBeanImpl.SchemaHelper2();
            case 6:
               return new InvalidationTargetBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "pool";
            case 1:
               return "timer-descriptor";
            case 2:
               return "entity-cache";
            case 3:
               return "entity-cache-ref";
            case 4:
               return "persistence";
            case 5:
               return "entity-clustering";
            case 6:
               return "invalidation-target";
            case 7:
               return "enable-dynamic-queries";
            case 8:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
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
            case 5:
               return true;
            case 6:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EntityDescriptorBeanImpl bean;

      protected Helper(EntityDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Pool";
            case 1:
               return "TimerDescriptor";
            case 2:
               return "EntityCache";
            case 3:
               return "EntityCacheRef";
            case 4:
               return "Persistence";
            case 5:
               return "EntityClustering";
            case 6:
               return "InvalidationTarget";
            case 7:
               return "EnableDynamicQueries";
            case 8:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EntityCache")) {
            return 2;
         } else if (propName.equals("EntityCacheRef")) {
            return 3;
         } else if (propName.equals("EntityClustering")) {
            return 5;
         } else if (propName.equals("Id")) {
            return 8;
         } else if (propName.equals("InvalidationTarget")) {
            return 6;
         } else if (propName.equals("Persistence")) {
            return 4;
         } else if (propName.equals("Pool")) {
            return 0;
         } else if (propName.equals("TimerDescriptor")) {
            return 1;
         } else {
            return propName.equals("EnableDynamicQueries") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getEntityCache() != null) {
            iterators.add(new ArrayIterator(new EntityCacheBean[]{this.bean.getEntityCache()}));
         }

         if (this.bean.getEntityCacheRef() != null) {
            iterators.add(new ArrayIterator(new EntityCacheRefBean[]{this.bean.getEntityCacheRef()}));
         }

         if (this.bean.getEntityClustering() != null) {
            iterators.add(new ArrayIterator(new EntityClusteringBean[]{this.bean.getEntityClustering()}));
         }

         if (this.bean.getInvalidationTarget() != null) {
            iterators.add(new ArrayIterator(new InvalidationTargetBean[]{this.bean.getInvalidationTarget()}));
         }

         if (this.bean.getPersistence() != null) {
            iterators.add(new ArrayIterator(new PersistenceBean[]{this.bean.getPersistence()}));
         }

         if (this.bean.getPool() != null) {
            iterators.add(new ArrayIterator(new PoolBean[]{this.bean.getPool()}));
         }

         if (this.bean.getTimerDescriptor() != null) {
            iterators.add(new ArrayIterator(new TimerDescriptorBean[]{this.bean.getTimerDescriptor()}));
         }

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
            childValue = this.computeChildHashValue(this.bean.getEntityCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getEntityCacheRef());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getEntityClustering());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getInvalidationTarget());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPersistence());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPool());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTimerDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnableDynamicQueriesSet()) {
               buf.append("EnableDynamicQueries");
               buf.append(String.valueOf(this.bean.isEnableDynamicQueries()));
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
            EntityDescriptorBeanImpl otherTyped = (EntityDescriptorBeanImpl)other;
            this.computeSubDiff("EntityCache", this.bean.getEntityCache(), otherTyped.getEntityCache());
            this.computeSubDiff("EntityCacheRef", this.bean.getEntityCacheRef(), otherTyped.getEntityCacheRef());
            this.computeSubDiff("EntityClustering", this.bean.getEntityClustering(), otherTyped.getEntityClustering());
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeSubDiff("InvalidationTarget", this.bean.getInvalidationTarget(), otherTyped.getInvalidationTarget());
            this.computeSubDiff("Persistence", this.bean.getPersistence(), otherTyped.getPersistence());
            this.computeSubDiff("Pool", this.bean.getPool(), otherTyped.getPool());
            this.computeSubDiff("TimerDescriptor", this.bean.getTimerDescriptor(), otherTyped.getTimerDescriptor());
            this.computeDiff("EnableDynamicQueries", this.bean.isEnableDynamicQueries(), otherTyped.isEnableDynamicQueries(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EntityDescriptorBeanImpl original = (EntityDescriptorBeanImpl)event.getSourceBean();
            EntityDescriptorBeanImpl proposed = (EntityDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EntityCache")) {
                  if (type == 2) {
                     original.setEntityCache((EntityCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getEntityCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("EntityCache", (DescriptorBean)original.getEntityCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("EntityCacheRef")) {
                  if (type == 2) {
                     original.setEntityCacheRef((EntityCacheRefBean)this.createCopy((AbstractDescriptorBean)proposed.getEntityCacheRef()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("EntityCacheRef", (DescriptorBean)original.getEntityCacheRef());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("EntityClustering")) {
                  if (type == 2) {
                     original.setEntityClustering((EntityClusteringBean)this.createCopy((AbstractDescriptorBean)proposed.getEntityClustering()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("EntityClustering", (DescriptorBean)original.getEntityClustering());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("InvalidationTarget")) {
                  if (type == 2) {
                     original.setInvalidationTarget((InvalidationTargetBean)this.createCopy((AbstractDescriptorBean)proposed.getInvalidationTarget()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("InvalidationTarget", (DescriptorBean)original.getInvalidationTarget());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Persistence")) {
                  if (type == 2) {
                     original.setPersistence((PersistenceBean)this.createCopy((AbstractDescriptorBean)proposed.getPersistence()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Persistence", (DescriptorBean)original.getPersistence());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Pool")) {
                  if (type == 2) {
                     original.setPool((PoolBean)this.createCopy((AbstractDescriptorBean)proposed.getPool()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Pool", (DescriptorBean)original.getPool());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TimerDescriptor")) {
                  if (type == 2) {
                     original.setTimerDescriptor((TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getTimerDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TimerDescriptor", (DescriptorBean)original.getTimerDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EnableDynamicQueries")) {
                  original.setEnableDynamicQueries(proposed.isEnableDynamicQueries());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
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
            EntityDescriptorBeanImpl copy = (EntityDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EntityCache")) && this.bean.isEntityCacheSet() && !copy._isSet(2)) {
               Object o = this.bean.getEntityCache();
               copy.setEntityCache((EntityCacheBean)null);
               copy.setEntityCache(o == null ? null : (EntityCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EntityCacheRef")) && this.bean.isEntityCacheRefSet() && !copy._isSet(3)) {
               Object o = this.bean.getEntityCacheRef();
               copy.setEntityCacheRef((EntityCacheRefBean)null);
               copy.setEntityCacheRef(o == null ? null : (EntityCacheRefBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EntityClustering")) && this.bean.isEntityClusteringSet() && !copy._isSet(5)) {
               Object o = this.bean.getEntityClustering();
               copy.setEntityClustering((EntityClusteringBean)null);
               copy.setEntityClustering(o == null ? null : (EntityClusteringBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InvalidationTarget")) && this.bean.isInvalidationTargetSet() && !copy._isSet(6)) {
               Object o = this.bean.getInvalidationTarget();
               copy.setInvalidationTarget((InvalidationTargetBean)null);
               copy.setInvalidationTarget(o == null ? null : (InvalidationTargetBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Persistence")) && this.bean.isPersistenceSet() && !copy._isSet(4)) {
               Object o = this.bean.getPersistence();
               copy.setPersistence((PersistenceBean)null);
               copy.setPersistence(o == null ? null : (PersistenceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Pool")) && this.bean.isPoolSet() && !copy._isSet(0)) {
               Object o = this.bean.getPool();
               copy.setPool((PoolBean)null);
               copy.setPool(o == null ? null : (PoolBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TimerDescriptor")) && this.bean.isTimerDescriptorSet() && !copy._isSet(1)) {
               Object o = this.bean.getTimerDescriptor();
               copy.setTimerDescriptor((TimerDescriptorBean)null);
               copy.setTimerDescriptor(o == null ? null : (TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EnableDynamicQueries")) && this.bean.isEnableDynamicQueriesSet()) {
               copy.setEnableDynamicQueries(this.bean.isEnableDynamicQueries());
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
         this.inferSubTree(this.bean.getEntityCache(), clazz, annotation);
         this.inferSubTree(this.bean.getEntityCacheRef(), clazz, annotation);
         this.inferSubTree(this.bean.getEntityClustering(), clazz, annotation);
         this.inferSubTree(this.bean.getInvalidationTarget(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistence(), clazz, annotation);
         this.inferSubTree(this.bean.getPool(), clazz, annotation);
         this.inferSubTree(this.bean.getTimerDescriptor(), clazz, annotation);
      }
   }
}
