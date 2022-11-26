package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ApplicationEntityCacheBeanImpl extends AbstractDescriptorBean implements ApplicationEntityCacheBean, Serializable {
   private String _CachingStrategy;
   private String _EntityCacheName;
   private int _MaxBeansInCache;
   private MaxCacheSizeBean _MaxCacheSize;
   private int _MaxQueriesInCache;
   private static SchemaHelper2 _schemaHelper;

   public ApplicationEntityCacheBeanImpl() {
      this._initializeProperty(-1);
   }

   public ApplicationEntityCacheBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ApplicationEntityCacheBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEntityCacheName() {
      return this._EntityCacheName;
   }

   public boolean isEntityCacheNameInherited() {
      return false;
   }

   public boolean isEntityCacheNameSet() {
      return this._isSet(0);
   }

   public void setEntityCacheName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EntityCacheName;
      this._EntityCacheName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getMaxBeansInCache() {
      return this._MaxBeansInCache;
   }

   public boolean isMaxBeansInCacheInherited() {
      return false;
   }

   public boolean isMaxBeansInCacheSet() {
      return this._isSet(1);
   }

   public void setMaxBeansInCache(int param0) {
      int _oldVal = this._MaxBeansInCache;
      this._MaxBeansInCache = param0;
      this._postSet(1, _oldVal, param0);
   }

   public MaxCacheSizeBean getMaxCacheSize() {
      return this._MaxCacheSize;
   }

   public boolean isMaxCacheSizeInherited() {
      return false;
   }

   public boolean isMaxCacheSizeSet() {
      return this._isSet(2);
   }

   public void setMaxCacheSize(MaxCacheSizeBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMaxCacheSize() != null && param0 != this.getMaxCacheSize()) {
         throw new BeanAlreadyExistsException(this.getMaxCacheSize() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MaxCacheSizeBean _oldVal = this._MaxCacheSize;
         this._MaxCacheSize = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public MaxCacheSizeBean createMaxCacheSize() {
      MaxCacheSizeBeanImpl _val = new MaxCacheSizeBeanImpl(this, -1);

      try {
         this.setMaxCacheSize(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMaxCacheSize(MaxCacheSizeBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MaxCacheSize;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMaxCacheSize((MaxCacheSizeBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public int getMaxQueriesInCache() {
      return this._MaxQueriesInCache;
   }

   public boolean isMaxQueriesInCacheInherited() {
      return false;
   }

   public boolean isMaxQueriesInCacheSet() {
      return this._isSet(3);
   }

   public void setMaxQueriesInCache(int param0) {
      int _oldVal = this._MaxQueriesInCache;
      this._MaxQueriesInCache = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getCachingStrategy() {
      return this._CachingStrategy;
   }

   public boolean isCachingStrategyInherited() {
      return false;
   }

   public boolean isCachingStrategySet() {
      return this._isSet(4);
   }

   public void setCachingStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Exclusive", "MultiVersion"};
      param0 = LegalChecks.checkInEnum("CachingStrategy", param0, _set);
      String _oldVal = this._CachingStrategy;
      this._CachingStrategy = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEntityCacheName();
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
         case 17:
            if (s.equals("entity-cache-name")) {
               return info.compareXpaths(this._getPropertyXpath("entity-cache-name"));
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._CachingStrategy = "MultiVersion";
               if (initOne) {
                  break;
               }
            case 0:
               this._EntityCacheName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxBeansInCache = 1000;
               if (initOne) {
                  break;
               }
            case 2:
               this._MaxCacheSize = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._MaxQueriesInCache = 100;
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
               if (s.equals("max-cache-size")) {
                  return 2;
               }
            case 15:
            case 19:
            default:
               break;
            case 16:
               if (s.equals("caching-strategy")) {
                  return 4;
               }
               break;
            case 17:
               if (s.equals("entity-cache-name")) {
                  return 0;
               }
               break;
            case 18:
               if (s.equals("max-beans-in-cache")) {
                  return 1;
               }
               break;
            case 20:
               if (s.equals("max-queries-in-cache")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new MaxCacheSizeBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "entity-cache-name";
            case 1:
               return "max-beans-in-cache";
            case 2:
               return "max-cache-size";
            case 3:
               return "max-queries-in-cache";
            case 4:
               return "caching-strategy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
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
         indices.add("entity-cache-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ApplicationEntityCacheBeanImpl bean;

      protected Helper(ApplicationEntityCacheBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EntityCacheName";
            case 1:
               return "MaxBeansInCache";
            case 2:
               return "MaxCacheSize";
            case 3:
               return "MaxQueriesInCache";
            case 4:
               return "CachingStrategy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CachingStrategy")) {
            return 4;
         } else if (propName.equals("EntityCacheName")) {
            return 0;
         } else if (propName.equals("MaxBeansInCache")) {
            return 1;
         } else if (propName.equals("MaxCacheSize")) {
            return 2;
         } else {
            return propName.equals("MaxQueriesInCache") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getMaxCacheSize() != null) {
            iterators.add(new ArrayIterator(new MaxCacheSizeBean[]{this.bean.getMaxCacheSize()}));
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
            if (this.bean.isCachingStrategySet()) {
               buf.append("CachingStrategy");
               buf.append(String.valueOf(this.bean.getCachingStrategy()));
            }

            if (this.bean.isEntityCacheNameSet()) {
               buf.append("EntityCacheName");
               buf.append(String.valueOf(this.bean.getEntityCacheName()));
            }

            if (this.bean.isMaxBeansInCacheSet()) {
               buf.append("MaxBeansInCache");
               buf.append(String.valueOf(this.bean.getMaxBeansInCache()));
            }

            childValue = this.computeChildHashValue(this.bean.getMaxCacheSize());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMaxQueriesInCacheSet()) {
               buf.append("MaxQueriesInCache");
               buf.append(String.valueOf(this.bean.getMaxQueriesInCache()));
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
            ApplicationEntityCacheBeanImpl otherTyped = (ApplicationEntityCacheBeanImpl)other;
            this.computeDiff("CachingStrategy", this.bean.getCachingStrategy(), otherTyped.getCachingStrategy(), false);
            this.computeDiff("EntityCacheName", this.bean.getEntityCacheName(), otherTyped.getEntityCacheName(), false);
            this.computeDiff("MaxBeansInCache", this.bean.getMaxBeansInCache(), otherTyped.getMaxBeansInCache(), true);
            this.computeChildDiff("MaxCacheSize", this.bean.getMaxCacheSize(), otherTyped.getMaxCacheSize(), false);
            this.computeDiff("MaxQueriesInCache", this.bean.getMaxQueriesInCache(), otherTyped.getMaxQueriesInCache(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ApplicationEntityCacheBeanImpl original = (ApplicationEntityCacheBeanImpl)event.getSourceBean();
            ApplicationEntityCacheBeanImpl proposed = (ApplicationEntityCacheBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CachingStrategy")) {
                  original.setCachingStrategy(proposed.getCachingStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("EntityCacheName")) {
                  original.setEntityCacheName(proposed.getEntityCacheName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MaxBeansInCache")) {
                  original.setMaxBeansInCache(proposed.getMaxBeansInCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MaxCacheSize")) {
                  if (type == 2) {
                     original.setMaxCacheSize((MaxCacheSizeBean)this.createCopy((AbstractDescriptorBean)proposed.getMaxCacheSize()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MaxCacheSize", (DescriptorBean)original.getMaxCacheSize());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MaxQueriesInCache")) {
                  original.setMaxQueriesInCache(proposed.getMaxQueriesInCache());
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
            ApplicationEntityCacheBeanImpl copy = (ApplicationEntityCacheBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CachingStrategy")) && this.bean.isCachingStrategySet()) {
               copy.setCachingStrategy(this.bean.getCachingStrategy());
            }

            if ((excludeProps == null || !excludeProps.contains("EntityCacheName")) && this.bean.isEntityCacheNameSet()) {
               copy.setEntityCacheName(this.bean.getEntityCacheName());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxBeansInCache")) && this.bean.isMaxBeansInCacheSet()) {
               copy.setMaxBeansInCache(this.bean.getMaxBeansInCache());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCacheSize")) && this.bean.isMaxCacheSizeSet() && !copy._isSet(2)) {
               Object o = this.bean.getMaxCacheSize();
               copy.setMaxCacheSize((MaxCacheSizeBean)null);
               copy.setMaxCacheSize(o == null ? null : (MaxCacheSizeBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MaxQueriesInCache")) && this.bean.isMaxQueriesInCacheSet()) {
               copy.setMaxQueriesInCache(this.bean.getMaxQueriesInCache());
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
         this.inferSubTree(this.bean.getMaxCacheSize(), clazz, annotation);
      }
   }
}
