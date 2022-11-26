package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class XMLEntitySpecRegistryEntryMBeanImpl extends ConfigurationMBeanImpl implements XMLEntitySpecRegistryEntryMBean, Serializable {
   private int _CacheTimeoutInterval;
   private String _EntityURI;
   private String _HandleEntityInvalidation;
   private String _PublicId;
   private String _SystemId;
   private String _WhenToCache;
   private static SchemaHelper2 _schemaHelper;

   public XMLEntitySpecRegistryEntryMBeanImpl() {
      this._initializeProperty(-1);
   }

   public XMLEntitySpecRegistryEntryMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public XMLEntitySpecRegistryEntryMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getPublicId() {
      return this._PublicId;
   }

   public boolean isPublicIdInherited() {
      return false;
   }

   public boolean isPublicIdSet() {
      return this._isSet(10);
   }

   public void setPublicId(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PublicId;
      this._PublicId = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getSystemId() {
      return this._SystemId;
   }

   public boolean isSystemIdInherited() {
      return false;
   }

   public boolean isSystemIdSet() {
      return this._isSet(11);
   }

   public void setSystemId(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SystemId;
      this._SystemId = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getEntityURI() {
      return this._EntityURI;
   }

   public boolean isEntityURIInherited() {
      return false;
   }

   public boolean isEntityURISet() {
      return this._isSet(12);
   }

   public void setEntityURI(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EntityURI;
      this._EntityURI = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getWhenToCache() {
      return this._WhenToCache;
   }

   public boolean isWhenToCacheInherited() {
      return false;
   }

   public boolean isWhenToCacheSet() {
      return this._isSet(13);
   }

   public void setWhenToCache(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"cache-on-reference", "cache-at-initialization", "cache-never", "defer-to-registry-setting"};
      param0 = LegalChecks.checkInEnum("WhenToCache", param0, _set);
      String _oldVal = this._WhenToCache;
      this._WhenToCache = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getCacheTimeoutInterval() {
      return this._CacheTimeoutInterval;
   }

   public boolean isCacheTimeoutIntervalInherited() {
      return false;
   }

   public boolean isCacheTimeoutIntervalSet() {
      return this._isSet(14);
   }

   public void setCacheTimeoutInterval(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheTimeoutInterval", param0, -1);
      int _oldVal = this._CacheTimeoutInterval;
      this._CacheTimeoutInterval = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getHandleEntityInvalidation() {
      return this._HandleEntityInvalidation;
   }

   public boolean isHandleEntityInvalidationInherited() {
      return false;
   }

   public boolean isHandleEntityInvalidationSet() {
      return this._isSet(15);
   }

   public void setHandleEntityInvalidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"true", "false", "defer-to-registry-setting"};
      param0 = LegalChecks.checkInEnum("HandleEntityInvalidation", param0, _set);
      String _oldVal = this._HandleEntityInvalidation;
      this._HandleEntityInvalidation = param0;
      this._postSet(15, _oldVal, param0);
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._CacheTimeoutInterval = -1;
               if (initOne) {
                  break;
               }
            case 12:
               this._EntityURI = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._HandleEntityInvalidation = "defer-to-registry-setting";
               if (initOne) {
                  break;
               }
            case 10:
               this._PublicId = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._SystemId = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._WhenToCache = "defer-to-registry-setting";
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
      return "XMLEntitySpecRegistryEntry";
   }

   public void putValue(String name, Object v) {
      if (name.equals("CacheTimeoutInterval")) {
         int oldVal = this._CacheTimeoutInterval;
         this._CacheTimeoutInterval = (Integer)v;
         this._postSet(14, oldVal, this._CacheTimeoutInterval);
      } else {
         String oldVal;
         if (name.equals("EntityURI")) {
            oldVal = this._EntityURI;
            this._EntityURI = (String)v;
            this._postSet(12, oldVal, this._EntityURI);
         } else if (name.equals("HandleEntityInvalidation")) {
            oldVal = this._HandleEntityInvalidation;
            this._HandleEntityInvalidation = (String)v;
            this._postSet(15, oldVal, this._HandleEntityInvalidation);
         } else if (name.equals("PublicId")) {
            oldVal = this._PublicId;
            this._PublicId = (String)v;
            this._postSet(10, oldVal, this._PublicId);
         } else if (name.equals("SystemId")) {
            oldVal = this._SystemId;
            this._SystemId = (String)v;
            this._postSet(11, oldVal, this._SystemId);
         } else if (name.equals("WhenToCache")) {
            oldVal = this._WhenToCache;
            this._WhenToCache = (String)v;
            this._postSet(13, oldVal, this._WhenToCache);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CacheTimeoutInterval")) {
         return new Integer(this._CacheTimeoutInterval);
      } else if (name.equals("EntityURI")) {
         return this._EntityURI;
      } else if (name.equals("HandleEntityInvalidation")) {
         return this._HandleEntityInvalidation;
      } else if (name.equals("PublicId")) {
         return this._PublicId;
      } else if (name.equals("SystemId")) {
         return this._SystemId;
      } else {
         return name.equals("WhenToCache") ? this._WhenToCache : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("public-id")) {
                  return 10;
               }

               if (s.equals("system-id")) {
                  return 11;
               }
               break;
            case 10:
               if (s.equals("entity-uri")) {
                  return 12;
               }
               break;
            case 13:
               if (s.equals("when-to-cache")) {
                  return 13;
               }
               break;
            case 22:
               if (s.equals("cache-timeout-interval")) {
                  return 14;
               }
               break;
            case 26:
               if (s.equals("handle-entity-invalidation")) {
                  return 15;
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
               return "public-id";
            case 11:
               return "system-id";
            case 12:
               return "entity-uri";
            case 13:
               return "when-to-cache";
            case 14:
               return "cache-timeout-interval";
            case 15:
               return "handle-entity-invalidation";
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
      private XMLEntitySpecRegistryEntryMBeanImpl bean;

      protected Helper(XMLEntitySpecRegistryEntryMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "PublicId";
            case 11:
               return "SystemId";
            case 12:
               return "EntityURI";
            case 13:
               return "WhenToCache";
            case 14:
               return "CacheTimeoutInterval";
            case 15:
               return "HandleEntityInvalidation";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CacheTimeoutInterval")) {
            return 14;
         } else if (propName.equals("EntityURI")) {
            return 12;
         } else if (propName.equals("HandleEntityInvalidation")) {
            return 15;
         } else if (propName.equals("PublicId")) {
            return 10;
         } else if (propName.equals("SystemId")) {
            return 11;
         } else {
            return propName.equals("WhenToCache") ? 13 : super.getPropertyIndex(propName);
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

            if (this.bean.isEntityURISet()) {
               buf.append("EntityURI");
               buf.append(String.valueOf(this.bean.getEntityURI()));
            }

            if (this.bean.isHandleEntityInvalidationSet()) {
               buf.append("HandleEntityInvalidation");
               buf.append(String.valueOf(this.bean.getHandleEntityInvalidation()));
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
            XMLEntitySpecRegistryEntryMBeanImpl otherTyped = (XMLEntitySpecRegistryEntryMBeanImpl)other;
            this.computeDiff("CacheTimeoutInterval", this.bean.getCacheTimeoutInterval(), otherTyped.getCacheTimeoutInterval(), true);
            this.computeDiff("EntityURI", this.bean.getEntityURI(), otherTyped.getEntityURI(), true);
            this.computeDiff("HandleEntityInvalidation", this.bean.getHandleEntityInvalidation(), otherTyped.getHandleEntityInvalidation(), true);
            this.computeDiff("PublicId", this.bean.getPublicId(), otherTyped.getPublicId(), true);
            this.computeDiff("SystemId", this.bean.getSystemId(), otherTyped.getSystemId(), true);
            this.computeDiff("WhenToCache", this.bean.getWhenToCache(), otherTyped.getWhenToCache(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            XMLEntitySpecRegistryEntryMBeanImpl original = (XMLEntitySpecRegistryEntryMBeanImpl)event.getSourceBean();
            XMLEntitySpecRegistryEntryMBeanImpl proposed = (XMLEntitySpecRegistryEntryMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CacheTimeoutInterval")) {
                  original.setCacheTimeoutInterval(proposed.getCacheTimeoutInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("EntityURI")) {
                  original.setEntityURI(proposed.getEntityURI());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("HandleEntityInvalidation")) {
                  original.setHandleEntityInvalidation(proposed.getHandleEntityInvalidation());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PublicId")) {
                  original.setPublicId(proposed.getPublicId());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("SystemId")) {
                  original.setSystemId(proposed.getSystemId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("WhenToCache")) {
                  original.setWhenToCache(proposed.getWhenToCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            XMLEntitySpecRegistryEntryMBeanImpl copy = (XMLEntitySpecRegistryEntryMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CacheTimeoutInterval")) && this.bean.isCacheTimeoutIntervalSet()) {
               copy.setCacheTimeoutInterval(this.bean.getCacheTimeoutInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("EntityURI")) && this.bean.isEntityURISet()) {
               copy.setEntityURI(this.bean.getEntityURI());
            }

            if ((excludeProps == null || !excludeProps.contains("HandleEntityInvalidation")) && this.bean.isHandleEntityInvalidationSet()) {
               copy.setHandleEntityInvalidation(this.bean.getHandleEntityInvalidation());
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
