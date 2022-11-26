package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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

public class StatefulSessionDescriptorBeanImpl extends AbstractDescriptorBean implements StatefulSessionDescriptorBean, Serializable {
   private boolean _AllowRemoveDuringTransaction;
   private BusinessInterfaceJndiNameMapBean[] _BusinessInterfaceJndiNameMaps;
   private String _Id;
   private String _PersistentStoreDir;
   private StatefulSessionCacheBean _StatefulSessionCache;
   private StatefulSessionClusteringBean _StatefulSessionClustering;
   private static SchemaHelper2 _schemaHelper;

   public StatefulSessionDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public StatefulSessionDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StatefulSessionDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public StatefulSessionCacheBean getStatefulSessionCache() {
      return this._StatefulSessionCache;
   }

   public boolean isStatefulSessionCacheInherited() {
      return false;
   }

   public boolean isStatefulSessionCacheSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getStatefulSessionCache());
   }

   public void setStatefulSessionCache(StatefulSessionCacheBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      StatefulSessionCacheBean _oldVal = this._StatefulSessionCache;
      this._StatefulSessionCache = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPersistentStoreDir() {
      return this._PersistentStoreDir;
   }

   public boolean isPersistentStoreDirInherited() {
      return false;
   }

   public boolean isPersistentStoreDirSet() {
      return this._isSet(1);
   }

   public void setPersistentStoreDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistentStoreDir;
      this._PersistentStoreDir = param0;
      this._postSet(1, _oldVal, param0);
   }

   public StatefulSessionClusteringBean getStatefulSessionClustering() {
      return this._StatefulSessionClustering;
   }

   public boolean isStatefulSessionClusteringInherited() {
      return false;
   }

   public boolean isStatefulSessionClusteringSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getStatefulSessionClustering());
   }

   public void setStatefulSessionClustering(StatefulSessionClusteringBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      StatefulSessionClusteringBean _oldVal = this._StatefulSessionClustering;
      this._StatefulSessionClustering = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isAllowRemoveDuringTransaction() {
      return this._AllowRemoveDuringTransaction;
   }

   public boolean isAllowRemoveDuringTransactionInherited() {
      return false;
   }

   public boolean isAllowRemoveDuringTransactionSet() {
      return this._isSet(3);
   }

   public void setAllowRemoveDuringTransaction(boolean param0) {
      boolean _oldVal = this._AllowRemoveDuringTransaction;
      this._AllowRemoveDuringTransaction = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addBusinessInterfaceJndiNameMap(BusinessInterfaceJndiNameMapBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         BusinessInterfaceJndiNameMapBean[] _new;
         if (this._isSet(4)) {
            _new = (BusinessInterfaceJndiNameMapBean[])((BusinessInterfaceJndiNameMapBean[])this._getHelper()._extendArray(this.getBusinessInterfaceJndiNameMaps(), BusinessInterfaceJndiNameMapBean.class, param0));
         } else {
            _new = new BusinessInterfaceJndiNameMapBean[]{param0};
         }

         try {
            this.setBusinessInterfaceJndiNameMaps(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public BusinessInterfaceJndiNameMapBean[] getBusinessInterfaceJndiNameMaps() {
      return this._BusinessInterfaceJndiNameMaps;
   }

   public boolean isBusinessInterfaceJndiNameMapsInherited() {
      return false;
   }

   public boolean isBusinessInterfaceJndiNameMapsSet() {
      return this._isSet(4);
   }

   public void removeBusinessInterfaceJndiNameMap(BusinessInterfaceJndiNameMapBean param0) {
      this.destroyBusinessInterfaceJndiNameMap(param0);
   }

   public void setBusinessInterfaceJndiNameMaps(BusinessInterfaceJndiNameMapBean[] param0) throws InvalidAttributeValueException {
      BusinessInterfaceJndiNameMapBean[] param0 = param0 == null ? new BusinessInterfaceJndiNameMapBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      BusinessInterfaceJndiNameMapBean[] _oldVal = this._BusinessInterfaceJndiNameMaps;
      this._BusinessInterfaceJndiNameMaps = (BusinessInterfaceJndiNameMapBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public BusinessInterfaceJndiNameMapBean createBusinessInterfaceJndiNameMap() {
      BusinessInterfaceJndiNameMapBeanImpl _val = new BusinessInterfaceJndiNameMapBeanImpl(this, -1);

      try {
         this.addBusinessInterfaceJndiNameMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyBusinessInterfaceJndiNameMap(BusinessInterfaceJndiNameMapBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         BusinessInterfaceJndiNameMapBean[] _old = this.getBusinessInterfaceJndiNameMaps();
         BusinessInterfaceJndiNameMapBean[] _new = (BusinessInterfaceJndiNameMapBean[])((BusinessInterfaceJndiNameMapBean[])this._getHelper()._removeElement(_old, BusinessInterfaceJndiNameMapBean.class, param0));
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
               this.setBusinessInterfaceJndiNameMaps(_new);
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

   public BusinessInterfaceJndiNameMapBean lookupBusinessInterfaceJndiNameMap(String param0) {
      Object[] aary = (Object[])this._BusinessInterfaceJndiNameMaps;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      BusinessInterfaceJndiNameMapBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (BusinessInterfaceJndiNameMapBeanImpl)it.previous();
      } while(!bean.getBusinessRemote().equals(param0));

      return bean;
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
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
      return super._isAnythingSet() || this.isStatefulSessionCacheSet() || this.isStatefulSessionClusteringSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._BusinessInterfaceJndiNameMaps = new BusinessInterfaceJndiNameMapBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._PersistentStoreDir = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._StatefulSessionCache = new StatefulSessionCacheBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._StatefulSessionCache);
               if (initOne) {
                  break;
               }
            case 2:
               this._StatefulSessionClustering = new StatefulSessionClusteringBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._StatefulSessionClustering);
               if (initOne) {
                  break;
               }
            case 3:
               this._AllowRemoveDuringTransaction = false;
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
                  return 5;
               }
               break;
            case 20:
               if (s.equals("persistent-store-dir")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("stateful-session-cache")) {
                  return 0;
               }
               break;
            case 27:
               if (s.equals("stateful-session-clustering")) {
                  return 2;
               }
               break;
            case 31:
               if (s.equals("allow-remove-during-transaction")) {
                  return 3;
               }
               break;
            case 32:
               if (s.equals("business-interface-jndi-name-map")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new StatefulSessionCacheBeanImpl.SchemaHelper2();
            case 1:
            case 3:
            default:
               return super.getSchemaHelper(propIndex);
            case 2:
               return new StatefulSessionClusteringBeanImpl.SchemaHelper2();
            case 4:
               return new BusinessInterfaceJndiNameMapBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "stateful-session-cache";
            case 1:
               return "persistent-store-dir";
            case 2:
               return "stateful-session-clustering";
            case 3:
               return "allow-remove-during-transaction";
            case 4:
               return "business-interface-jndi-name-map";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
            case 3:
            default:
               return super.isBean(propIndex);
            case 2:
               return true;
            case 4:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private StatefulSessionDescriptorBeanImpl bean;

      protected Helper(StatefulSessionDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "StatefulSessionCache";
            case 1:
               return "PersistentStoreDir";
            case 2:
               return "StatefulSessionClustering";
            case 3:
               return "AllowRemoveDuringTransaction";
            case 4:
               return "BusinessInterfaceJndiNameMaps";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BusinessInterfaceJndiNameMaps")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("PersistentStoreDir")) {
            return 1;
         } else if (propName.equals("StatefulSessionCache")) {
            return 0;
         } else if (propName.equals("StatefulSessionClustering")) {
            return 2;
         } else {
            return propName.equals("AllowRemoveDuringTransaction") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getBusinessInterfaceJndiNameMaps()));
         if (this.bean.getStatefulSessionCache() != null) {
            iterators.add(new ArrayIterator(new StatefulSessionCacheBean[]{this.bean.getStatefulSessionCache()}));
         }

         if (this.bean.getStatefulSessionClustering() != null) {
            iterators.add(new ArrayIterator(new StatefulSessionClusteringBean[]{this.bean.getStatefulSessionClustering()}));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getBusinessInterfaceJndiNameMaps().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getBusinessInterfaceJndiNameMaps()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isPersistentStoreDirSet()) {
               buf.append("PersistentStoreDir");
               buf.append(String.valueOf(this.bean.getPersistentStoreDir()));
            }

            childValue = this.computeChildHashValue(this.bean.getStatefulSessionCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getStatefulSessionClustering());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAllowRemoveDuringTransactionSet()) {
               buf.append("AllowRemoveDuringTransaction");
               buf.append(String.valueOf(this.bean.isAllowRemoveDuringTransaction()));
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
            StatefulSessionDescriptorBeanImpl otherTyped = (StatefulSessionDescriptorBeanImpl)other;
            this.computeChildDiff("BusinessInterfaceJndiNameMaps", this.bean.getBusinessInterfaceJndiNameMaps(), otherTyped.getBusinessInterfaceJndiNameMaps(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("PersistentStoreDir", this.bean.getPersistentStoreDir(), otherTyped.getPersistentStoreDir(), false);
            this.computeSubDiff("StatefulSessionCache", this.bean.getStatefulSessionCache(), otherTyped.getStatefulSessionCache());
            this.computeSubDiff("StatefulSessionClustering", this.bean.getStatefulSessionClustering(), otherTyped.getStatefulSessionClustering());
            this.computeDiff("AllowRemoveDuringTransaction", this.bean.isAllowRemoveDuringTransaction(), otherTyped.isAllowRemoveDuringTransaction(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StatefulSessionDescriptorBeanImpl original = (StatefulSessionDescriptorBeanImpl)event.getSourceBean();
            StatefulSessionDescriptorBeanImpl proposed = (StatefulSessionDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BusinessInterfaceJndiNameMaps")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addBusinessInterfaceJndiNameMap((BusinessInterfaceJndiNameMapBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeBusinessInterfaceJndiNameMap((BusinessInterfaceJndiNameMapBean)update.getRemovedObject());
                  }

                  if (original.getBusinessInterfaceJndiNameMaps() == null || original.getBusinessInterfaceJndiNameMaps().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("PersistentStoreDir")) {
                  original.setPersistentStoreDir(proposed.getPersistentStoreDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("StatefulSessionCache")) {
                  if (type == 2) {
                     original.setStatefulSessionCache((StatefulSessionCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getStatefulSessionCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("StatefulSessionCache", (DescriptorBean)original.getStatefulSessionCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("StatefulSessionClustering")) {
                  if (type == 2) {
                     original.setStatefulSessionClustering((StatefulSessionClusteringBean)this.createCopy((AbstractDescriptorBean)proposed.getStatefulSessionClustering()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("StatefulSessionClustering", (DescriptorBean)original.getStatefulSessionClustering());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("AllowRemoveDuringTransaction")) {
                  original.setAllowRemoveDuringTransaction(proposed.isAllowRemoveDuringTransaction());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            StatefulSessionDescriptorBeanImpl copy = (StatefulSessionDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BusinessInterfaceJndiNameMaps")) && this.bean.isBusinessInterfaceJndiNameMapsSet() && !copy._isSet(4)) {
               BusinessInterfaceJndiNameMapBean[] oldBusinessInterfaceJndiNameMaps = this.bean.getBusinessInterfaceJndiNameMaps();
               BusinessInterfaceJndiNameMapBean[] newBusinessInterfaceJndiNameMaps = new BusinessInterfaceJndiNameMapBean[oldBusinessInterfaceJndiNameMaps.length];

               for(int i = 0; i < newBusinessInterfaceJndiNameMaps.length; ++i) {
                  newBusinessInterfaceJndiNameMaps[i] = (BusinessInterfaceJndiNameMapBean)((BusinessInterfaceJndiNameMapBean)this.createCopy((AbstractDescriptorBean)oldBusinessInterfaceJndiNameMaps[i], includeObsolete));
               }

               copy.setBusinessInterfaceJndiNameMaps(newBusinessInterfaceJndiNameMaps);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStoreDir")) && this.bean.isPersistentStoreDirSet()) {
               copy.setPersistentStoreDir(this.bean.getPersistentStoreDir());
            }

            if ((excludeProps == null || !excludeProps.contains("StatefulSessionCache")) && this.bean.isStatefulSessionCacheSet() && !copy._isSet(0)) {
               Object o = this.bean.getStatefulSessionCache();
               copy.setStatefulSessionCache((StatefulSessionCacheBean)null);
               copy.setStatefulSessionCache(o == null ? null : (StatefulSessionCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StatefulSessionClustering")) && this.bean.isStatefulSessionClusteringSet() && !copy._isSet(2)) {
               Object o = this.bean.getStatefulSessionClustering();
               copy.setStatefulSessionClustering((StatefulSessionClusteringBean)null);
               copy.setStatefulSessionClustering(o == null ? null : (StatefulSessionClusteringBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AllowRemoveDuringTransaction")) && this.bean.isAllowRemoveDuringTransactionSet()) {
               copy.setAllowRemoveDuringTransaction(this.bean.isAllowRemoveDuringTransaction());
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
         this.inferSubTree(this.bean.getBusinessInterfaceJndiNameMaps(), clazz, annotation);
         this.inferSubTree(this.bean.getStatefulSessionCache(), clazz, annotation);
         this.inferSubTree(this.bean.getStatefulSessionClustering(), clazz, annotation);
      }
   }
}
