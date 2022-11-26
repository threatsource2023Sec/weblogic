package kodo.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import kodo.conf.customizers.DataCachesBeanCustomizer;
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

public class DataCachesBeanImpl extends AbstractDescriptorBean implements DataCachesBean, Serializable {
   private CustomDataCacheBean[] _CustomDataCache;
   private DefaultDataCacheBean[] _DefaultDataCache;
   private GemFireDataCacheBean[] _GemFireDataCache;
   private KodoConcurrentDataCacheBean[] _KodoConcurrentDataCache;
   private LRUDataCacheBean[] _LRUDataCache;
   private TangosolDataCacheBean[] _TangosolDataCache;
   private transient DataCachesBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public DataCachesBeanImpl() {
      try {
         this._customizer = new DataCachesBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public DataCachesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new DataCachesBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public DataCachesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new DataCachesBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public void addDefaultDataCache(DefaultDataCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         DefaultDataCacheBean[] _new;
         if (this._isSet(0)) {
            _new = (DefaultDataCacheBean[])((DefaultDataCacheBean[])this._getHelper()._extendArray(this.getDefaultDataCache(), DefaultDataCacheBean.class, param0));
         } else {
            _new = new DefaultDataCacheBean[]{param0};
         }

         try {
            this.setDefaultDataCache(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DefaultDataCacheBean[] getDefaultDataCache() {
      return this._DefaultDataCache;
   }

   public boolean isDefaultDataCacheInherited() {
      return false;
   }

   public boolean isDefaultDataCacheSet() {
      return this._isSet(0);
   }

   public void removeDefaultDataCache(DefaultDataCacheBean param0) {
      this.destroyDefaultDataCache(param0);
   }

   public void setDefaultDataCache(DefaultDataCacheBean[] param0) throws InvalidAttributeValueException {
      DefaultDataCacheBean[] param0 = param0 == null ? new DefaultDataCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DefaultDataCacheBean[] _oldVal = this._DefaultDataCache;
      this._DefaultDataCache = (DefaultDataCacheBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public DefaultDataCacheBean createDefaultDataCache(String param0) {
      DefaultDataCacheBeanImpl _val = new DefaultDataCacheBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addDefaultDataCache(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyDefaultDataCache(DefaultDataCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         DefaultDataCacheBean[] _old = this.getDefaultDataCache();
         DefaultDataCacheBean[] _new = (DefaultDataCacheBean[])((DefaultDataCacheBean[])this._getHelper()._removeElement(_old, DefaultDataCacheBean.class, param0));
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
               this.setDefaultDataCache(_new);
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

   public void addKodoConcurrentDataCache(KodoConcurrentDataCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         KodoConcurrentDataCacheBean[] _new;
         if (this._isSet(1)) {
            _new = (KodoConcurrentDataCacheBean[])((KodoConcurrentDataCacheBean[])this._getHelper()._extendArray(this.getKodoConcurrentDataCache(), KodoConcurrentDataCacheBean.class, param0));
         } else {
            _new = new KodoConcurrentDataCacheBean[]{param0};
         }

         try {
            this.setKodoConcurrentDataCache(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public KodoConcurrentDataCacheBean[] getKodoConcurrentDataCache() {
      return this._KodoConcurrentDataCache;
   }

   public boolean isKodoConcurrentDataCacheInherited() {
      return false;
   }

   public boolean isKodoConcurrentDataCacheSet() {
      return this._isSet(1);
   }

   public void removeKodoConcurrentDataCache(KodoConcurrentDataCacheBean param0) {
      this.destroyKodoConcurrentDataCache(param0);
   }

   public void setKodoConcurrentDataCache(KodoConcurrentDataCacheBean[] param0) throws InvalidAttributeValueException {
      KodoConcurrentDataCacheBean[] param0 = param0 == null ? new KodoConcurrentDataCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      KodoConcurrentDataCacheBean[] _oldVal = this._KodoConcurrentDataCache;
      this._KodoConcurrentDataCache = (KodoConcurrentDataCacheBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public KodoConcurrentDataCacheBean createKodoConcurrentDataCache(String param0) {
      KodoConcurrentDataCacheBeanImpl _val = new KodoConcurrentDataCacheBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addKodoConcurrentDataCache(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyKodoConcurrentDataCache(KodoConcurrentDataCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         KodoConcurrentDataCacheBean[] _old = this.getKodoConcurrentDataCache();
         KodoConcurrentDataCacheBean[] _new = (KodoConcurrentDataCacheBean[])((KodoConcurrentDataCacheBean[])this._getHelper()._removeElement(_old, KodoConcurrentDataCacheBean.class, param0));
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
               this.setKodoConcurrentDataCache(_new);
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

   public void addGemFireDataCache(GemFireDataCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         GemFireDataCacheBean[] _new;
         if (this._isSet(2)) {
            _new = (GemFireDataCacheBean[])((GemFireDataCacheBean[])this._getHelper()._extendArray(this.getGemFireDataCache(), GemFireDataCacheBean.class, param0));
         } else {
            _new = new GemFireDataCacheBean[]{param0};
         }

         try {
            this.setGemFireDataCache(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public GemFireDataCacheBean[] getGemFireDataCache() {
      return this._GemFireDataCache;
   }

   public boolean isGemFireDataCacheInherited() {
      return false;
   }

   public boolean isGemFireDataCacheSet() {
      return this._isSet(2);
   }

   public void removeGemFireDataCache(GemFireDataCacheBean param0) {
      this.destroyGemFireDataCache(param0);
   }

   public void setGemFireDataCache(GemFireDataCacheBean[] param0) throws InvalidAttributeValueException {
      GemFireDataCacheBean[] param0 = param0 == null ? new GemFireDataCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      GemFireDataCacheBean[] _oldVal = this._GemFireDataCache;
      this._GemFireDataCache = (GemFireDataCacheBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public GemFireDataCacheBean createGemFireDataCache(String param0) {
      GemFireDataCacheBeanImpl _val = new GemFireDataCacheBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addGemFireDataCache(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyGemFireDataCache(GemFireDataCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         GemFireDataCacheBean[] _old = this.getGemFireDataCache();
         GemFireDataCacheBean[] _new = (GemFireDataCacheBean[])((GemFireDataCacheBean[])this._getHelper()._removeElement(_old, GemFireDataCacheBean.class, param0));
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
               this.setGemFireDataCache(_new);
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

   public void addLRUDataCache(LRUDataCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         LRUDataCacheBean[] _new;
         if (this._isSet(3)) {
            _new = (LRUDataCacheBean[])((LRUDataCacheBean[])this._getHelper()._extendArray(this.getLRUDataCache(), LRUDataCacheBean.class, param0));
         } else {
            _new = new LRUDataCacheBean[]{param0};
         }

         try {
            this.setLRUDataCache(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LRUDataCacheBean[] getLRUDataCache() {
      return this._LRUDataCache;
   }

   public boolean isLRUDataCacheInherited() {
      return false;
   }

   public boolean isLRUDataCacheSet() {
      return this._isSet(3);
   }

   public void removeLRUDataCache(LRUDataCacheBean param0) {
      this.destroyLRUDataCache(param0);
   }

   public void setLRUDataCache(LRUDataCacheBean[] param0) throws InvalidAttributeValueException {
      LRUDataCacheBean[] param0 = param0 == null ? new LRUDataCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LRUDataCacheBean[] _oldVal = this._LRUDataCache;
      this._LRUDataCache = (LRUDataCacheBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public LRUDataCacheBean createLRUDataCache(String param0) {
      LRUDataCacheBeanImpl _val = new LRUDataCacheBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addLRUDataCache(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyLRUDataCache(LRUDataCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         LRUDataCacheBean[] _old = this.getLRUDataCache();
         LRUDataCacheBean[] _new = (LRUDataCacheBean[])((LRUDataCacheBean[])this._getHelper()._removeElement(_old, LRUDataCacheBean.class, param0));
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
               this.setLRUDataCache(_new);
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

   public void addTangosolDataCache(TangosolDataCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         TangosolDataCacheBean[] _new;
         if (this._isSet(4)) {
            _new = (TangosolDataCacheBean[])((TangosolDataCacheBean[])this._getHelper()._extendArray(this.getTangosolDataCache(), TangosolDataCacheBean.class, param0));
         } else {
            _new = new TangosolDataCacheBean[]{param0};
         }

         try {
            this.setTangosolDataCache(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TangosolDataCacheBean[] getTangosolDataCache() {
      return this._TangosolDataCache;
   }

   public boolean isTangosolDataCacheInherited() {
      return false;
   }

   public boolean isTangosolDataCacheSet() {
      return this._isSet(4);
   }

   public void removeTangosolDataCache(TangosolDataCacheBean param0) {
      this.destroyTangosolDataCache(param0);
   }

   public void setTangosolDataCache(TangosolDataCacheBean[] param0) throws InvalidAttributeValueException {
      TangosolDataCacheBean[] param0 = param0 == null ? new TangosolDataCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      TangosolDataCacheBean[] _oldVal = this._TangosolDataCache;
      this._TangosolDataCache = (TangosolDataCacheBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public TangosolDataCacheBean createTangosolDataCache(String param0) {
      TangosolDataCacheBeanImpl _val = new TangosolDataCacheBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addTangosolDataCache(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyTangosolDataCache(TangosolDataCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         TangosolDataCacheBean[] _old = this.getTangosolDataCache();
         TangosolDataCacheBean[] _new = (TangosolDataCacheBean[])((TangosolDataCacheBean[])this._getHelper()._removeElement(_old, TangosolDataCacheBean.class, param0));
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
               this.setTangosolDataCache(_new);
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

   public void addCustomDataCache(CustomDataCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         CustomDataCacheBean[] _new;
         if (this._isSet(5)) {
            _new = (CustomDataCacheBean[])((CustomDataCacheBean[])this._getHelper()._extendArray(this.getCustomDataCache(), CustomDataCacheBean.class, param0));
         } else {
            _new = new CustomDataCacheBean[]{param0};
         }

         try {
            this.setCustomDataCache(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CustomDataCacheBean[] getCustomDataCache() {
      return this._CustomDataCache;
   }

   public boolean isCustomDataCacheInherited() {
      return false;
   }

   public boolean isCustomDataCacheSet() {
      return this._isSet(5);
   }

   public void removeCustomDataCache(CustomDataCacheBean param0) {
      this.destroyCustomDataCache(param0);
   }

   public void setCustomDataCache(CustomDataCacheBean[] param0) throws InvalidAttributeValueException {
      CustomDataCacheBean[] param0 = param0 == null ? new CustomDataCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CustomDataCacheBean[] _oldVal = this._CustomDataCache;
      this._CustomDataCache = (CustomDataCacheBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public CustomDataCacheBean createCustomDataCache(String param0) {
      CustomDataCacheBeanImpl _val = new CustomDataCacheBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addCustomDataCache(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyCustomDataCache(CustomDataCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         CustomDataCacheBean[] _old = this.getCustomDataCache();
         CustomDataCacheBean[] _new = (CustomDataCacheBean[])((CustomDataCacheBean[])this._getHelper()._removeElement(_old, CustomDataCacheBean.class, param0));
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
               this.setCustomDataCache(_new);
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

   public Class[] getDataCacheTypes() {
      return this._customizer.getDataCacheTypes();
   }

   public DataCacheBean[] getDataCaches() {
      return this._customizer.getDataCaches();
   }

   public DataCacheBean createDataCache(Class param0, String param1) {
      return this._customizer.createDataCache(param0, param1);
   }

   public DataCacheBean lookupDataCache(String param0) {
      return this._customizer.lookupDataCache(param0);
   }

   public void destroyDataCache(DataCacheBean param0) {
      this._customizer.destroyDataCache(param0);
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._CustomDataCache = new CustomDataCacheBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._DefaultDataCache = new DefaultDataCacheBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._GemFireDataCache = new GemFireDataCacheBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._KodoConcurrentDataCache = new KodoConcurrentDataCacheBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._LRUDataCache = new LRUDataCacheBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._TangosolDataCache = new TangosolDataCacheBean[0];
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
            case 14:
               if (s.equals("lru-data-cache")) {
                  return 3;
               }
            case 15:
            case 16:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               break;
            case 17:
               if (s.equals("custom-data-cache")) {
                  return 5;
               }
               break;
            case 18:
               if (s.equals("default-data-cache")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("gem-fire-data-cache")) {
                  return 2;
               }

               if (s.equals("tangosol-data-cache")) {
                  return 4;
               }
               break;
            case 26:
               if (s.equals("kodo-concurrent-data-cache")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new DefaultDataCacheBeanImpl.SchemaHelper2();
            case 1:
               return new KodoConcurrentDataCacheBeanImpl.SchemaHelper2();
            case 2:
               return new GemFireDataCacheBeanImpl.SchemaHelper2();
            case 3:
               return new LRUDataCacheBeanImpl.SchemaHelper2();
            case 4:
               return new TangosolDataCacheBeanImpl.SchemaHelper2();
            case 5:
               return new CustomDataCacheBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "default-data-cache";
            case 1:
               return "kodo-concurrent-data-cache";
            case 2:
               return "gem-fire-data-cache";
            case 3:
               return "lru-data-cache";
            case 4:
               return "tangosol-data-cache";
            case 5:
               return "custom-data-cache";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
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
            default:
               return super.isArray(propIndex);
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
            default:
               return super.isBean(propIndex);
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
            case 5:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private DataCachesBeanImpl bean;

      protected Helper(DataCachesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DefaultDataCache";
            case 1:
               return "KodoConcurrentDataCache";
            case 2:
               return "GemFireDataCache";
            case 3:
               return "LRUDataCache";
            case 4:
               return "TangosolDataCache";
            case 5:
               return "CustomDataCache";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CustomDataCache")) {
            return 5;
         } else if (propName.equals("DefaultDataCache")) {
            return 0;
         } else if (propName.equals("GemFireDataCache")) {
            return 2;
         } else if (propName.equals("KodoConcurrentDataCache")) {
            return 1;
         } else if (propName.equals("LRUDataCache")) {
            return 3;
         } else {
            return propName.equals("TangosolDataCache") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCustomDataCache()));
         iterators.add(new ArrayIterator(this.bean.getDefaultDataCache()));
         iterators.add(new ArrayIterator(this.bean.getGemFireDataCache()));
         iterators.add(new ArrayIterator(this.bean.getKodoConcurrentDataCache()));
         iterators.add(new ArrayIterator(this.bean.getLRUDataCache()));
         iterators.add(new ArrayIterator(this.bean.getTangosolDataCache()));
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

            int i;
            for(i = 0; i < this.bean.getCustomDataCache().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCustomDataCache()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDefaultDataCache().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDefaultDataCache()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getGemFireDataCache().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getGemFireDataCache()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getKodoConcurrentDataCache().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getKodoConcurrentDataCache()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLRUDataCache().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLRUDataCache()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTangosolDataCache().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTangosolDataCache()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            DataCachesBeanImpl otherTyped = (DataCachesBeanImpl)other;
            this.computeChildDiff("CustomDataCache", this.bean.getCustomDataCache(), otherTyped.getCustomDataCache(), false);
            this.computeChildDiff("DefaultDataCache", this.bean.getDefaultDataCache(), otherTyped.getDefaultDataCache(), false);
            this.computeChildDiff("GemFireDataCache", this.bean.getGemFireDataCache(), otherTyped.getGemFireDataCache(), false);
            this.computeChildDiff("KodoConcurrentDataCache", this.bean.getKodoConcurrentDataCache(), otherTyped.getKodoConcurrentDataCache(), false);
            this.computeChildDiff("LRUDataCache", this.bean.getLRUDataCache(), otherTyped.getLRUDataCache(), false);
            this.computeChildDiff("TangosolDataCache", this.bean.getTangosolDataCache(), otherTyped.getTangosolDataCache(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DataCachesBeanImpl original = (DataCachesBeanImpl)event.getSourceBean();
            DataCachesBeanImpl proposed = (DataCachesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CustomDataCache")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCustomDataCache((CustomDataCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCustomDataCache((CustomDataCacheBean)update.getRemovedObject());
                  }

                  if (original.getCustomDataCache() == null || original.getCustomDataCache().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("DefaultDataCache")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDefaultDataCache((DefaultDataCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDefaultDataCache((DefaultDataCacheBean)update.getRemovedObject());
                  }

                  if (original.getDefaultDataCache() == null || original.getDefaultDataCache().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("GemFireDataCache")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addGemFireDataCache((GemFireDataCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeGemFireDataCache((GemFireDataCacheBean)update.getRemovedObject());
                  }

                  if (original.getGemFireDataCache() == null || original.getGemFireDataCache().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("KodoConcurrentDataCache")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addKodoConcurrentDataCache((KodoConcurrentDataCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeKodoConcurrentDataCache((KodoConcurrentDataCacheBean)update.getRemovedObject());
                  }

                  if (original.getKodoConcurrentDataCache() == null || original.getKodoConcurrentDataCache().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("LRUDataCache")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLRUDataCache((LRUDataCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLRUDataCache((LRUDataCacheBean)update.getRemovedObject());
                  }

                  if (original.getLRUDataCache() == null || original.getLRUDataCache().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("TangosolDataCache")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTangosolDataCache((TangosolDataCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTangosolDataCache((TangosolDataCacheBean)update.getRemovedObject());
                  }

                  if (original.getTangosolDataCache() == null || original.getTangosolDataCache().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
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
            DataCachesBeanImpl copy = (DataCachesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("CustomDataCache")) && this.bean.isCustomDataCacheSet() && !copy._isSet(5)) {
               CustomDataCacheBean[] oldCustomDataCache = this.bean.getCustomDataCache();
               CustomDataCacheBean[] newCustomDataCache = new CustomDataCacheBean[oldCustomDataCache.length];

               for(i = 0; i < newCustomDataCache.length; ++i) {
                  newCustomDataCache[i] = (CustomDataCacheBean)((CustomDataCacheBean)this.createCopy((AbstractDescriptorBean)oldCustomDataCache[i], includeObsolete));
               }

               copy.setCustomDataCache(newCustomDataCache);
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDataCache")) && this.bean.isDefaultDataCacheSet() && !copy._isSet(0)) {
               DefaultDataCacheBean[] oldDefaultDataCache = this.bean.getDefaultDataCache();
               DefaultDataCacheBean[] newDefaultDataCache = new DefaultDataCacheBean[oldDefaultDataCache.length];

               for(i = 0; i < newDefaultDataCache.length; ++i) {
                  newDefaultDataCache[i] = (DefaultDataCacheBean)((DefaultDataCacheBean)this.createCopy((AbstractDescriptorBean)oldDefaultDataCache[i], includeObsolete));
               }

               copy.setDefaultDataCache(newDefaultDataCache);
            }

            if ((excludeProps == null || !excludeProps.contains("GemFireDataCache")) && this.bean.isGemFireDataCacheSet() && !copy._isSet(2)) {
               GemFireDataCacheBean[] oldGemFireDataCache = this.bean.getGemFireDataCache();
               GemFireDataCacheBean[] newGemFireDataCache = new GemFireDataCacheBean[oldGemFireDataCache.length];

               for(i = 0; i < newGemFireDataCache.length; ++i) {
                  newGemFireDataCache[i] = (GemFireDataCacheBean)((GemFireDataCacheBean)this.createCopy((AbstractDescriptorBean)oldGemFireDataCache[i], includeObsolete));
               }

               copy.setGemFireDataCache(newGemFireDataCache);
            }

            if ((excludeProps == null || !excludeProps.contains("KodoConcurrentDataCache")) && this.bean.isKodoConcurrentDataCacheSet() && !copy._isSet(1)) {
               KodoConcurrentDataCacheBean[] oldKodoConcurrentDataCache = this.bean.getKodoConcurrentDataCache();
               KodoConcurrentDataCacheBean[] newKodoConcurrentDataCache = new KodoConcurrentDataCacheBean[oldKodoConcurrentDataCache.length];

               for(i = 0; i < newKodoConcurrentDataCache.length; ++i) {
                  newKodoConcurrentDataCache[i] = (KodoConcurrentDataCacheBean)((KodoConcurrentDataCacheBean)this.createCopy((AbstractDescriptorBean)oldKodoConcurrentDataCache[i], includeObsolete));
               }

               copy.setKodoConcurrentDataCache(newKodoConcurrentDataCache);
            }

            if ((excludeProps == null || !excludeProps.contains("LRUDataCache")) && this.bean.isLRUDataCacheSet() && !copy._isSet(3)) {
               LRUDataCacheBean[] oldLRUDataCache = this.bean.getLRUDataCache();
               LRUDataCacheBean[] newLRUDataCache = new LRUDataCacheBean[oldLRUDataCache.length];

               for(i = 0; i < newLRUDataCache.length; ++i) {
                  newLRUDataCache[i] = (LRUDataCacheBean)((LRUDataCacheBean)this.createCopy((AbstractDescriptorBean)oldLRUDataCache[i], includeObsolete));
               }

               copy.setLRUDataCache(newLRUDataCache);
            }

            if ((excludeProps == null || !excludeProps.contains("TangosolDataCache")) && this.bean.isTangosolDataCacheSet() && !copy._isSet(4)) {
               TangosolDataCacheBean[] oldTangosolDataCache = this.bean.getTangosolDataCache();
               TangosolDataCacheBean[] newTangosolDataCache = new TangosolDataCacheBean[oldTangosolDataCache.length];

               for(i = 0; i < newTangosolDataCache.length; ++i) {
                  newTangosolDataCache[i] = (TangosolDataCacheBean)((TangosolDataCacheBean)this.createCopy((AbstractDescriptorBean)oldTangosolDataCache[i], includeObsolete));
               }

               copy.setTangosolDataCache(newTangosolDataCache);
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
         this.inferSubTree(this.bean.getCustomDataCache(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultDataCache(), clazz, annotation);
         this.inferSubTree(this.bean.getGemFireDataCache(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoConcurrentDataCache(), clazz, annotation);
         this.inferSubTree(this.bean.getLRUDataCache(), clazz, annotation);
         this.inferSubTree(this.bean.getTangosolDataCache(), clazz, annotation);
      }
   }
}
