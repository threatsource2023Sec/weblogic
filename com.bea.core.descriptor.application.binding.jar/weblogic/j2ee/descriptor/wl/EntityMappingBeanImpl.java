package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class EntityMappingBeanImpl extends AbstractDescriptorBean implements EntityMappingBean, Serializable {
   private int _CacheTimeoutInterval;
   private String _EntityMappingName;
   private String _EntityUri;
   private String _PublicId;
   private String _SystemId;
   private String _WhenToCache;
   private static SchemaHelper2 _schemaHelper;

   public EntityMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public EntityMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EntityMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEntityMappingName() {
      return this._EntityMappingName;
   }

   public boolean isEntityMappingNameInherited() {
      return false;
   }

   public boolean isEntityMappingNameSet() {
      return this._isSet(0);
   }

   public void setEntityMappingName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EntityMappingName;
      this._EntityMappingName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPublicId() {
      return this._PublicId;
   }

   public boolean isPublicIdInherited() {
      return false;
   }

   public boolean isPublicIdSet() {
      return this._isSet(1);
   }

   public void setPublicId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PublicId;
      this._PublicId = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getSystemId() {
      return this._SystemId;
   }

   public boolean isSystemIdInherited() {
      return false;
   }

   public boolean isSystemIdSet() {
      return this._isSet(2);
   }

   public void setSystemId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SystemId;
      this._SystemId = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getEntityUri() {
      return this._EntityUri;
   }

   public boolean isEntityUriInherited() {
      return false;
   }

   public boolean isEntityUriSet() {
      return this._isSet(3);
   }

   public void setEntityUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EntityUri;
      this._EntityUri = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getWhenToCache() {
      return this._WhenToCache;
   }

   public boolean isWhenToCacheInherited() {
      return false;
   }

   public boolean isWhenToCacheSet() {
      return this._isSet(4);
   }

   public void setWhenToCache(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WhenToCache;
      this._WhenToCache = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getCacheTimeoutInterval() {
      return this._CacheTimeoutInterval;
   }

   public boolean isCacheTimeoutIntervalInherited() {
      return false;
   }

   public boolean isCacheTimeoutIntervalSet() {
      return this._isSet(5);
   }

   public void setCacheTimeoutInterval(int param0) {
      int _oldVal = this._CacheTimeoutInterval;
      this._CacheTimeoutInterval = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEntityMappingName();
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
         case 19:
            if (s.equals("entity-mapping-name")) {
               return info.compareXpaths(this._getPropertyXpath("entity-mapping-name"));
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._CacheTimeoutInterval = 0;
               if (initOne) {
                  break;
               }
            case 0:
               this._EntityMappingName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._EntityUri = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._PublicId = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._SystemId = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._WhenToCache = null;
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
            case 9:
               if (s.equals("public-id")) {
                  return 1;
               }

               if (s.equals("system-id")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("entity-uri")) {
                  return 3;
               }
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            default:
               break;
            case 13:
               if (s.equals("when-to-cache")) {
                  return 4;
               }
               break;
            case 19:
               if (s.equals("entity-mapping-name")) {
                  return 0;
               }
               break;
            case 22:
               if (s.equals("cache-timeout-interval")) {
                  return 5;
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
               return "entity-mapping-name";
            case 1:
               return "public-id";
            case 2:
               return "system-id";
            case 3:
               return "entity-uri";
            case 4:
               return "when-to-cache";
            case 5:
               return "cache-timeout-interval";
            default:
               return super.getElementName(propIndex);
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
         indices.add("entity-mapping-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EntityMappingBeanImpl bean;

      protected Helper(EntityMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EntityMappingName";
            case 1:
               return "PublicId";
            case 2:
               return "SystemId";
            case 3:
               return "EntityUri";
            case 4:
               return "WhenToCache";
            case 5:
               return "CacheTimeoutInterval";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CacheTimeoutInterval")) {
            return 5;
         } else if (propName.equals("EntityMappingName")) {
            return 0;
         } else if (propName.equals("EntityUri")) {
            return 3;
         } else if (propName.equals("PublicId")) {
            return 1;
         } else if (propName.equals("SystemId")) {
            return 2;
         } else {
            return propName.equals("WhenToCache") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isCacheTimeoutIntervalSet()) {
               buf.append("CacheTimeoutInterval");
               buf.append(String.valueOf(this.bean.getCacheTimeoutInterval()));
            }

            if (this.bean.isEntityMappingNameSet()) {
               buf.append("EntityMappingName");
               buf.append(String.valueOf(this.bean.getEntityMappingName()));
            }

            if (this.bean.isEntityUriSet()) {
               buf.append("EntityUri");
               buf.append(String.valueOf(this.bean.getEntityUri()));
            }

            if (this.bean.isPublicIdSet()) {
               buf.append("PublicId");
               buf.append(String.valueOf(this.bean.getPublicId()));
            }

            if (this.bean.isSystemIdSet()) {
               buf.append("SystemId");
               buf.append(String.valueOf(this.bean.getSystemId()));
            }

            if (this.bean.isWhenToCacheSet()) {
               buf.append("WhenToCache");
               buf.append(String.valueOf(this.bean.getWhenToCache()));
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
            EntityMappingBeanImpl otherTyped = (EntityMappingBeanImpl)other;
            this.computeDiff("CacheTimeoutInterval", this.bean.getCacheTimeoutInterval(), otherTyped.getCacheTimeoutInterval(), false);
            this.computeDiff("EntityMappingName", this.bean.getEntityMappingName(), otherTyped.getEntityMappingName(), false);
            this.computeDiff("EntityUri", this.bean.getEntityUri(), otherTyped.getEntityUri(), false);
            this.computeDiff("PublicId", this.bean.getPublicId(), otherTyped.getPublicId(), false);
            this.computeDiff("SystemId", this.bean.getSystemId(), otherTyped.getSystemId(), false);
            this.computeDiff("WhenToCache", this.bean.getWhenToCache(), otherTyped.getWhenToCache(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EntityMappingBeanImpl original = (EntityMappingBeanImpl)event.getSourceBean();
            EntityMappingBeanImpl proposed = (EntityMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CacheTimeoutInterval")) {
                  original.setCacheTimeoutInterval(proposed.getCacheTimeoutInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("EntityMappingName")) {
                  original.setEntityMappingName(proposed.getEntityMappingName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EntityUri")) {
                  original.setEntityUri(proposed.getEntityUri());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("PublicId")) {
                  original.setPublicId(proposed.getPublicId());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SystemId")) {
                  original.setSystemId(proposed.getSystemId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("WhenToCache")) {
                  original.setWhenToCache(proposed.getWhenToCache());
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
            EntityMappingBeanImpl copy = (EntityMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CacheTimeoutInterval")) && this.bean.isCacheTimeoutIntervalSet()) {
               copy.setCacheTimeoutInterval(this.bean.getCacheTimeoutInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("EntityMappingName")) && this.bean.isEntityMappingNameSet()) {
               copy.setEntityMappingName(this.bean.getEntityMappingName());
            }

            if ((excludeProps == null || !excludeProps.contains("EntityUri")) && this.bean.isEntityUriSet()) {
               copy.setEntityUri(this.bean.getEntityUri());
            }

            if ((excludeProps == null || !excludeProps.contains("PublicId")) && this.bean.isPublicIdSet()) {
               copy.setPublicId(this.bean.getPublicId());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemId")) && this.bean.isSystemIdSet()) {
               copy.setSystemId(this.bean.getSystemId());
            }

            if ((excludeProps == null || !excludeProps.contains("WhenToCache")) && this.bean.isWhenToCacheSet()) {
               copy.setWhenToCache(this.bean.getWhenToCache());
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
