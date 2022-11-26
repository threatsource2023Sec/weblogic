package kodo.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import kodo.conf.customizers.QueryCachesBeanCustomizer;
import weblogic.descriptor.BeanAlreadyExistsException;
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

public class QueryCachesBeanImpl extends AbstractDescriptorBean implements QueryCachesBean, Serializable {
   private CustomQueryCacheBean _CustomQueryCache;
   private DefaultQueryCacheBean _DefaultQueryCache;
   private DisabledQueryCacheBean _DisabledQueryCache;
   private GemFireQueryCacheBean _GemFireQueryCache;
   private KodoConcurrentQueryCacheBean _KodoConcurrentQueryCache;
   private LRUQueryCacheBean _LRUQueryCache;
   private TangosolQueryCacheBean _TangosolQueryCache;
   private transient QueryCachesBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public QueryCachesBeanImpl() {
      try {
         this._customizer = new QueryCachesBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public QueryCachesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new QueryCachesBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public QueryCachesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new QueryCachesBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public DefaultQueryCacheBean getDefaultQueryCache() {
      return this._DefaultQueryCache;
   }

   public boolean isDefaultQueryCacheInherited() {
      return false;
   }

   public boolean isDefaultQueryCacheSet() {
      return this._isSet(0);
   }

   public void setDefaultQueryCache(DefaultQueryCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultQueryCache() != null && param0 != this.getDefaultQueryCache()) {
         throw new BeanAlreadyExistsException(this.getDefaultQueryCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultQueryCacheBean _oldVal = this._DefaultQueryCache;
         this._DefaultQueryCache = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public DefaultQueryCacheBean createDefaultQueryCache() {
      DefaultQueryCacheBeanImpl _val = new DefaultQueryCacheBeanImpl(this, -1);

      try {
         this.setDefaultQueryCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultQueryCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultQueryCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultQueryCache((DefaultQueryCacheBean)null);
               this._unSet(0);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoConcurrentQueryCacheBean getKodoConcurrentQueryCache() {
      return this._KodoConcurrentQueryCache;
   }

   public boolean isKodoConcurrentQueryCacheInherited() {
      return false;
   }

   public boolean isKodoConcurrentQueryCacheSet() {
      return this._isSet(1);
   }

   public void setKodoConcurrentQueryCache(KodoConcurrentQueryCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoConcurrentQueryCache() != null && param0 != this.getKodoConcurrentQueryCache()) {
         throw new BeanAlreadyExistsException(this.getKodoConcurrentQueryCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoConcurrentQueryCacheBean _oldVal = this._KodoConcurrentQueryCache;
         this._KodoConcurrentQueryCache = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public KodoConcurrentQueryCacheBean createKodoConcurrentQueryCache() {
      KodoConcurrentQueryCacheBeanImpl _val = new KodoConcurrentQueryCacheBeanImpl(this, -1);

      try {
         this.setKodoConcurrentQueryCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoConcurrentQueryCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoConcurrentQueryCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoConcurrentQueryCache((KodoConcurrentQueryCacheBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public GemFireQueryCacheBean getGemFireQueryCache() {
      return this._GemFireQueryCache;
   }

   public boolean isGemFireQueryCacheInherited() {
      return false;
   }

   public boolean isGemFireQueryCacheSet() {
      return this._isSet(2);
   }

   public void setGemFireQueryCache(GemFireQueryCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getGemFireQueryCache() != null && param0 != this.getGemFireQueryCache()) {
         throw new BeanAlreadyExistsException(this.getGemFireQueryCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         GemFireQueryCacheBean _oldVal = this._GemFireQueryCache;
         this._GemFireQueryCache = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public GemFireQueryCacheBean createGemFireQueryCache() {
      GemFireQueryCacheBeanImpl _val = new GemFireQueryCacheBeanImpl(this, -1);

      try {
         this.setGemFireQueryCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyGemFireQueryCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._GemFireQueryCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setGemFireQueryCache((GemFireQueryCacheBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LRUQueryCacheBean getLRUQueryCache() {
      return this._LRUQueryCache;
   }

   public boolean isLRUQueryCacheInherited() {
      return false;
   }

   public boolean isLRUQueryCacheSet() {
      return this._isSet(3);
   }

   public void setLRUQueryCache(LRUQueryCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLRUQueryCache() != null && param0 != this.getLRUQueryCache()) {
         throw new BeanAlreadyExistsException(this.getLRUQueryCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LRUQueryCacheBean _oldVal = this._LRUQueryCache;
         this._LRUQueryCache = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public LRUQueryCacheBean createLRUQueryCache() {
      LRUQueryCacheBeanImpl _val = new LRUQueryCacheBeanImpl(this, -1);

      try {
         this.setLRUQueryCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLRUQueryCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LRUQueryCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLRUQueryCache((LRUQueryCacheBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TangosolQueryCacheBean getTangosolQueryCache() {
      return this._TangosolQueryCache;
   }

   public boolean isTangosolQueryCacheInherited() {
      return false;
   }

   public boolean isTangosolQueryCacheSet() {
      return this._isSet(4);
   }

   public void setTangosolQueryCache(TangosolQueryCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTangosolQueryCache() != null && param0 != this.getTangosolQueryCache()) {
         throw new BeanAlreadyExistsException(this.getTangosolQueryCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TangosolQueryCacheBean _oldVal = this._TangosolQueryCache;
         this._TangosolQueryCache = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public TangosolQueryCacheBean createTangosolQueryCache() {
      TangosolQueryCacheBeanImpl _val = new TangosolQueryCacheBeanImpl(this, -1);

      try {
         this.setTangosolQueryCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTangosolQueryCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TangosolQueryCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTangosolQueryCache((TangosolQueryCacheBean)null);
               this._unSet(4);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DisabledQueryCacheBean getDisabledQueryCache() {
      return this._DisabledQueryCache;
   }

   public boolean isDisabledQueryCacheInherited() {
      return false;
   }

   public boolean isDisabledQueryCacheSet() {
      return this._isSet(5);
   }

   public void setDisabledQueryCache(DisabledQueryCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDisabledQueryCache() != null && param0 != this.getDisabledQueryCache()) {
         throw new BeanAlreadyExistsException(this.getDisabledQueryCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DisabledQueryCacheBean _oldVal = this._DisabledQueryCache;
         this._DisabledQueryCache = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public DisabledQueryCacheBean createDisabledQueryCache() {
      DisabledQueryCacheBeanImpl _val = new DisabledQueryCacheBeanImpl(this, -1);

      try {
         this.setDisabledQueryCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDisabledQueryCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DisabledQueryCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDisabledQueryCache((DisabledQueryCacheBean)null);
               this._unSet(5);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomQueryCacheBean getCustomQueryCache() {
      return this._CustomQueryCache;
   }

   public boolean isCustomQueryCacheInherited() {
      return false;
   }

   public boolean isCustomQueryCacheSet() {
      return this._isSet(6);
   }

   public void setCustomQueryCache(CustomQueryCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomQueryCache() != null && param0 != this.getCustomQueryCache()) {
         throw new BeanAlreadyExistsException(this.getCustomQueryCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 6)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomQueryCacheBean _oldVal = this._CustomQueryCache;
         this._CustomQueryCache = param0;
         this._postSet(6, _oldVal, param0);
      }
   }

   public CustomQueryCacheBean createCustomQueryCache() {
      CustomQueryCacheBeanImpl _val = new CustomQueryCacheBeanImpl(this, -1);

      try {
         this.setCustomQueryCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomQueryCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomQueryCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomQueryCache((CustomQueryCacheBean)null);
               this._unSet(6);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Class[] getQueryCacheTypes() {
      return this._customizer.getQueryCacheTypes();
   }

   public QueryCacheBean getQueryCache() {
      return this._customizer.getQueryCache();
   }

   public QueryCacheBean createQueryCache(Class param0) {
      return this._customizer.createQueryCache(param0);
   }

   public void destroyQueryCache() {
      this._customizer.destroyQueryCache();
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._CustomQueryCache = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._DefaultQueryCache = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._DisabledQueryCache = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._GemFireQueryCache = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._KodoConcurrentQueryCache = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._LRUQueryCache = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._TangosolQueryCache = null;
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
            case 15:
               if (s.equals("lru-query-cache")) {
                  return 3;
               }
            case 16:
            case 17:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               break;
            case 18:
               if (s.equals("custom-query-cache")) {
                  return 6;
               }
               break;
            case 19:
               if (s.equals("default-query-cache")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("disabled-query-cache")) {
                  return 5;
               }

               if (s.equals("gem-fire-query-cache")) {
                  return 2;
               }

               if (s.equals("tangosol-query-cache")) {
                  return 4;
               }
               break;
            case 27:
               if (s.equals("kodo-concurrent-query-cache")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new DefaultQueryCacheBeanImpl.SchemaHelper2();
            case 1:
               return new KodoConcurrentQueryCacheBeanImpl.SchemaHelper2();
            case 2:
               return new GemFireQueryCacheBeanImpl.SchemaHelper2();
            case 3:
               return new LRUQueryCacheBeanImpl.SchemaHelper2();
            case 4:
               return new TangosolQueryCacheBeanImpl.SchemaHelper2();
            case 5:
               return new DisabledQueryCacheBeanImpl.SchemaHelper2();
            case 6:
               return new CustomQueryCacheBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "default-query-cache";
            case 1:
               return "kodo-concurrent-query-cache";
            case 2:
               return "gem-fire-query-cache";
            case 3:
               return "lru-query-cache";
            case 4:
               return "tangosol-query-cache";
            case 5:
               return "disabled-query-cache";
            case 6:
               return "custom-query-cache";
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
            case 6:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private QueryCachesBeanImpl bean;

      protected Helper(QueryCachesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DefaultQueryCache";
            case 1:
               return "KodoConcurrentQueryCache";
            case 2:
               return "GemFireQueryCache";
            case 3:
               return "LRUQueryCache";
            case 4:
               return "TangosolQueryCache";
            case 5:
               return "DisabledQueryCache";
            case 6:
               return "CustomQueryCache";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CustomQueryCache")) {
            return 6;
         } else if (propName.equals("DefaultQueryCache")) {
            return 0;
         } else if (propName.equals("DisabledQueryCache")) {
            return 5;
         } else if (propName.equals("GemFireQueryCache")) {
            return 2;
         } else if (propName.equals("KodoConcurrentQueryCache")) {
            return 1;
         } else if (propName.equals("LRUQueryCache")) {
            return 3;
         } else {
            return propName.equals("TangosolQueryCache") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCustomQueryCache() != null) {
            iterators.add(new ArrayIterator(new CustomQueryCacheBean[]{this.bean.getCustomQueryCache()}));
         }

         if (this.bean.getDefaultQueryCache() != null) {
            iterators.add(new ArrayIterator(new DefaultQueryCacheBean[]{this.bean.getDefaultQueryCache()}));
         }

         if (this.bean.getDisabledQueryCache() != null) {
            iterators.add(new ArrayIterator(new DisabledQueryCacheBean[]{this.bean.getDisabledQueryCache()}));
         }

         if (this.bean.getGemFireQueryCache() != null) {
            iterators.add(new ArrayIterator(new GemFireQueryCacheBean[]{this.bean.getGemFireQueryCache()}));
         }

         if (this.bean.getKodoConcurrentQueryCache() != null) {
            iterators.add(new ArrayIterator(new KodoConcurrentQueryCacheBean[]{this.bean.getKodoConcurrentQueryCache()}));
         }

         if (this.bean.getLRUQueryCache() != null) {
            iterators.add(new ArrayIterator(new LRUQueryCacheBean[]{this.bean.getLRUQueryCache()}));
         }

         if (this.bean.getTangosolQueryCache() != null) {
            iterators.add(new ArrayIterator(new TangosolQueryCacheBean[]{this.bean.getTangosolQueryCache()}));
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
            childValue = this.computeChildHashValue(this.bean.getCustomQueryCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultQueryCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDisabledQueryCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getGemFireQueryCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoConcurrentQueryCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLRUQueryCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTangosolQueryCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            QueryCachesBeanImpl otherTyped = (QueryCachesBeanImpl)other;
            this.computeChildDiff("CustomQueryCache", this.bean.getCustomQueryCache(), otherTyped.getCustomQueryCache(), false);
            this.computeChildDiff("DefaultQueryCache", this.bean.getDefaultQueryCache(), otherTyped.getDefaultQueryCache(), false);
            this.computeChildDiff("DisabledQueryCache", this.bean.getDisabledQueryCache(), otherTyped.getDisabledQueryCache(), false);
            this.computeChildDiff("GemFireQueryCache", this.bean.getGemFireQueryCache(), otherTyped.getGemFireQueryCache(), false);
            this.computeChildDiff("KodoConcurrentQueryCache", this.bean.getKodoConcurrentQueryCache(), otherTyped.getKodoConcurrentQueryCache(), false);
            this.computeChildDiff("LRUQueryCache", this.bean.getLRUQueryCache(), otherTyped.getLRUQueryCache(), false);
            this.computeChildDiff("TangosolQueryCache", this.bean.getTangosolQueryCache(), otherTyped.getTangosolQueryCache(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            QueryCachesBeanImpl original = (QueryCachesBeanImpl)event.getSourceBean();
            QueryCachesBeanImpl proposed = (QueryCachesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CustomQueryCache")) {
                  if (type == 2) {
                     original.setCustomQueryCache((CustomQueryCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomQueryCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CustomQueryCache", (DescriptorBean)original.getCustomQueryCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("DefaultQueryCache")) {
                  if (type == 2) {
                     original.setDefaultQueryCache((DefaultQueryCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultQueryCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DefaultQueryCache", (DescriptorBean)original.getDefaultQueryCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DisabledQueryCache")) {
                  if (type == 2) {
                     original.setDisabledQueryCache((DisabledQueryCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getDisabledQueryCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DisabledQueryCache", (DescriptorBean)original.getDisabledQueryCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("GemFireQueryCache")) {
                  if (type == 2) {
                     original.setGemFireQueryCache((GemFireQueryCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getGemFireQueryCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("GemFireQueryCache", (DescriptorBean)original.getGemFireQueryCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("KodoConcurrentQueryCache")) {
                  if (type == 2) {
                     original.setKodoConcurrentQueryCache((KodoConcurrentQueryCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoConcurrentQueryCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("KodoConcurrentQueryCache", (DescriptorBean)original.getKodoConcurrentQueryCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("LRUQueryCache")) {
                  if (type == 2) {
                     original.setLRUQueryCache((LRUQueryCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getLRUQueryCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("LRUQueryCache", (DescriptorBean)original.getLRUQueryCache());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TangosolQueryCache")) {
                  if (type == 2) {
                     original.setTangosolQueryCache((TangosolQueryCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getTangosolQueryCache()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TangosolQueryCache", (DescriptorBean)original.getTangosolQueryCache());
                  }

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
            QueryCachesBeanImpl copy = (QueryCachesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CustomQueryCache")) && this.bean.isCustomQueryCacheSet() && !copy._isSet(6)) {
               Object o = this.bean.getCustomQueryCache();
               copy.setCustomQueryCache((CustomQueryCacheBean)null);
               copy.setCustomQueryCache(o == null ? null : (CustomQueryCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultQueryCache")) && this.bean.isDefaultQueryCacheSet() && !copy._isSet(0)) {
               Object o = this.bean.getDefaultQueryCache();
               copy.setDefaultQueryCache((DefaultQueryCacheBean)null);
               copy.setDefaultQueryCache(o == null ? null : (DefaultQueryCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DisabledQueryCache")) && this.bean.isDisabledQueryCacheSet() && !copy._isSet(5)) {
               Object o = this.bean.getDisabledQueryCache();
               copy.setDisabledQueryCache((DisabledQueryCacheBean)null);
               copy.setDisabledQueryCache(o == null ? null : (DisabledQueryCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("GemFireQueryCache")) && this.bean.isGemFireQueryCacheSet() && !copy._isSet(2)) {
               Object o = this.bean.getGemFireQueryCache();
               copy.setGemFireQueryCache((GemFireQueryCacheBean)null);
               copy.setGemFireQueryCache(o == null ? null : (GemFireQueryCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoConcurrentQueryCache")) && this.bean.isKodoConcurrentQueryCacheSet() && !copy._isSet(1)) {
               Object o = this.bean.getKodoConcurrentQueryCache();
               copy.setKodoConcurrentQueryCache((KodoConcurrentQueryCacheBean)null);
               copy.setKodoConcurrentQueryCache(o == null ? null : (KodoConcurrentQueryCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LRUQueryCache")) && this.bean.isLRUQueryCacheSet() && !copy._isSet(3)) {
               Object o = this.bean.getLRUQueryCache();
               copy.setLRUQueryCache((LRUQueryCacheBean)null);
               copy.setLRUQueryCache(o == null ? null : (LRUQueryCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TangosolQueryCache")) && this.bean.isTangosolQueryCacheSet() && !copy._isSet(4)) {
               Object o = this.bean.getTangosolQueryCache();
               copy.setTangosolQueryCache((TangosolQueryCacheBean)null);
               copy.setTangosolQueryCache(o == null ? null : (TangosolQueryCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getCustomQueryCache(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultQueryCache(), clazz, annotation);
         this.inferSubTree(this.bean.getDisabledQueryCache(), clazz, annotation);
         this.inferSubTree(this.bean.getGemFireQueryCache(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoConcurrentQueryCache(), clazz, annotation);
         this.inferSubTree(this.bean.getLRUQueryCache(), clazz, annotation);
         this.inferSubTree(this.bean.getTangosolQueryCache(), clazz, annotation);
      }
   }
}
