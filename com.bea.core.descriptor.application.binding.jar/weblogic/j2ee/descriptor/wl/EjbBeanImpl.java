package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class EjbBeanImpl extends AbstractDescriptorBean implements EjbBean, Serializable {
   private ApplicationEntityCacheBean[] _EntityCaches;
   private boolean _StartMdbsWithApplication;
   private static SchemaHelper2 _schemaHelper;

   public EjbBeanImpl() {
      this._initializeProperty(-1);
   }

   public EjbBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EjbBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addEntityCache(ApplicationEntityCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         ApplicationEntityCacheBean[] _new;
         if (this._isSet(0)) {
            _new = (ApplicationEntityCacheBean[])((ApplicationEntityCacheBean[])this._getHelper()._extendArray(this.getEntityCaches(), ApplicationEntityCacheBean.class, param0));
         } else {
            _new = new ApplicationEntityCacheBean[]{param0};
         }

         try {
            this.setEntityCaches(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ApplicationEntityCacheBean[] getEntityCaches() {
      return this._EntityCaches;
   }

   public boolean isEntityCachesInherited() {
      return false;
   }

   public boolean isEntityCachesSet() {
      return this._isSet(0);
   }

   public void removeEntityCache(ApplicationEntityCacheBean param0) {
      this.destroyEntityCache(param0);
   }

   public void setEntityCaches(ApplicationEntityCacheBean[] param0) throws InvalidAttributeValueException {
      ApplicationEntityCacheBean[] param0 = param0 == null ? new ApplicationEntityCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ApplicationEntityCacheBean[] _oldVal = this._EntityCaches;
      this._EntityCaches = (ApplicationEntityCacheBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public ApplicationEntityCacheBean createEntityCache() {
      ApplicationEntityCacheBeanImpl _val = new ApplicationEntityCacheBeanImpl(this, -1);

      try {
         this.addEntityCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEntityCache(ApplicationEntityCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         ApplicationEntityCacheBean[] _old = this.getEntityCaches();
         ApplicationEntityCacheBean[] _new = (ApplicationEntityCacheBean[])((ApplicationEntityCacheBean[])this._getHelper()._removeElement(_old, ApplicationEntityCacheBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setEntityCaches(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public boolean isStartMdbsWithApplication() {
      return this._StartMdbsWithApplication;
   }

   public boolean isStartMdbsWithApplicationInherited() {
      return false;
   }

   public boolean isStartMdbsWithApplicationSet() {
      return this._isSet(1);
   }

   public void setStartMdbsWithApplication(boolean param0) {
      boolean _oldVal = this._StartMdbsWithApplication;
      this._StartMdbsWithApplication = param0;
      this._postSet(1, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._EntityCaches = new ApplicationEntityCacheBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._StartMdbsWithApplication = false;
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
            case 12:
               if (s.equals("entity-cache")) {
                  return 0;
               }
               break;
            case 27:
               if (s.equals("start-mdbs-with-application")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ApplicationEntityCacheBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "entity-cache";
            case 1:
               return "start-mdbs-with-application";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EjbBeanImpl bean;

      protected Helper(EjbBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EntityCaches";
            case 1:
               return "StartMdbsWithApplication";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EntityCaches")) {
            return 0;
         } else {
            return propName.equals("StartMdbsWithApplication") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getEntityCaches()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getEntityCaches().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEntityCaches()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isStartMdbsWithApplicationSet()) {
               buf.append("StartMdbsWithApplication");
               buf.append(String.valueOf(this.bean.isStartMdbsWithApplication()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            EjbBeanImpl otherTyped = (EjbBeanImpl)other;
            this.computeChildDiff("EntityCaches", this.bean.getEntityCaches(), otherTyped.getEntityCaches(), false);
            this.computeDiff("StartMdbsWithApplication", this.bean.isStartMdbsWithApplication(), otherTyped.isStartMdbsWithApplication(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EjbBeanImpl original = (EjbBeanImpl)event.getSourceBean();
            EjbBeanImpl proposed = (EjbBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EntityCaches")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEntityCache((ApplicationEntityCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEntityCache((ApplicationEntityCacheBean)update.getRemovedObject());
                  }

                  if (original.getEntityCaches() == null || original.getEntityCaches().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("StartMdbsWithApplication")) {
                  original.setStartMdbsWithApplication(proposed.isStartMdbsWithApplication());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            EjbBeanImpl copy = (EjbBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EntityCaches")) && this.bean.isEntityCachesSet() && !copy._isSet(0)) {
               ApplicationEntityCacheBean[] oldEntityCaches = this.bean.getEntityCaches();
               ApplicationEntityCacheBean[] newEntityCaches = new ApplicationEntityCacheBean[oldEntityCaches.length];

               for(int i = 0; i < newEntityCaches.length; ++i) {
                  newEntityCaches[i] = (ApplicationEntityCacheBean)((ApplicationEntityCacheBean)this.createCopy((AbstractDescriptorBean)oldEntityCaches[i], includeObsolete));
               }

               copy.setEntityCaches(newEntityCaches);
            }

            if ((excludeProps == null || !excludeProps.contains("StartMdbsWithApplication")) && this.bean.isStartMdbsWithApplicationSet()) {
               copy.setStartMdbsWithApplication(this.bean.isStartMdbsWithApplication());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getEntityCaches(), clazz, annotation);
      }
   }
}
