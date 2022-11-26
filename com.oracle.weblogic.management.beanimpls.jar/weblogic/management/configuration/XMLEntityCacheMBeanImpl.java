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

public class XMLEntityCacheMBeanImpl extends ConfigurationMBeanImpl implements XMLEntityCacheMBean, Serializable {
   private int _CacheDiskSize;
   private String _CacheLocation;
   private int _CacheMemorySize;
   private int _CacheTimeoutInterval;
   private int _MaxSize;
   private static SchemaHelper2 _schemaHelper;

   public XMLEntityCacheMBeanImpl() {
      this._initializeProperty(-1);
   }

   public XMLEntityCacheMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public XMLEntityCacheMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCacheLocation() {
      return this._CacheLocation;
   }

   public boolean isCacheLocationInherited() {
      return false;
   }

   public boolean isCacheLocationSet() {
      return this._isSet(10);
   }

   public void setCacheLocation(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CacheLocation;
      this._CacheLocation = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getCacheMemorySize() {
      return this._CacheMemorySize;
   }

   public boolean isCacheMemorySizeInherited() {
      return false;
   }

   public boolean isCacheMemorySizeSet() {
      return this._isSet(11);
   }

   public void setCacheMemorySize(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheMemorySize", param0, 0);
      int _oldVal = this._CacheMemorySize;
      this._CacheMemorySize = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getCacheDiskSize() {
      return this._CacheDiskSize;
   }

   public boolean isCacheDiskSizeInherited() {
      return false;
   }

   public boolean isCacheDiskSizeSet() {
      return this._isSet(12);
   }

   public void setCacheDiskSize(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheDiskSize", param0, 0);
      int _oldVal = this._CacheDiskSize;
      this._CacheDiskSize = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getCacheTimeoutInterval() {
      return this._CacheTimeoutInterval;
   }

   public boolean isCacheTimeoutIntervalInherited() {
      return false;
   }

   public boolean isCacheTimeoutIntervalSet() {
      return this._isSet(13);
   }

   public void setCacheTimeoutInterval(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheTimeoutInterval", param0, 0);
      int _oldVal = this._CacheTimeoutInterval;
      this._CacheTimeoutInterval = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getMaxSize() {
      return this._MaxSize;
   }

   public boolean isMaxSizeInherited() {
      return false;
   }

   public boolean isMaxSizeSet() {
      return this._isSet(14);
   }

   public void setMaxSize(int param0) {
      int _oldVal = this._MaxSize;
      this._MaxSize = param0;
      this._postSet(14, _oldVal, param0);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._CacheDiskSize = 5;
               if (initOne) {
                  break;
               }
            case 10:
               this._CacheLocation = "xmlcache";
               if (initOne) {
                  break;
               }
            case 11:
               this._CacheMemorySize = 500;
               if (initOne) {
                  break;
               }
            case 13:
               this._CacheTimeoutInterval = 120;
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxSize = 0;
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
      return "XMLEntityCache";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("CacheDiskSize")) {
         oldVal = this._CacheDiskSize;
         this._CacheDiskSize = (Integer)v;
         this._postSet(12, oldVal, this._CacheDiskSize);
      } else if (name.equals("CacheLocation")) {
         String oldVal = this._CacheLocation;
         this._CacheLocation = (String)v;
         this._postSet(10, oldVal, this._CacheLocation);
      } else if (name.equals("CacheMemorySize")) {
         oldVal = this._CacheMemorySize;
         this._CacheMemorySize = (Integer)v;
         this._postSet(11, oldVal, this._CacheMemorySize);
      } else if (name.equals("CacheTimeoutInterval")) {
         oldVal = this._CacheTimeoutInterval;
         this._CacheTimeoutInterval = (Integer)v;
         this._postSet(13, oldVal, this._CacheTimeoutInterval);
      } else if (name.equals("MaxSize")) {
         oldVal = this._MaxSize;
         this._MaxSize = (Integer)v;
         this._postSet(14, oldVal, this._MaxSize);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CacheDiskSize")) {
         return new Integer(this._CacheDiskSize);
      } else if (name.equals("CacheLocation")) {
         return this._CacheLocation;
      } else if (name.equals("CacheMemorySize")) {
         return new Integer(this._CacheMemorySize);
      } else if (name.equals("CacheTimeoutInterval")) {
         return new Integer(this._CacheTimeoutInterval);
      } else {
         return name.equals("MaxSize") ? new Integer(this._MaxSize) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("max-size")) {
                  return 14;
               }
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            default:
               break;
            case 14:
               if (s.equals("cache-location")) {
                  return 10;
               }
               break;
            case 15:
               if (s.equals("cache-disk-size")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("cache-memory-size")) {
                  return 11;
               }
               break;
            case 22:
               if (s.equals("cache-timeout-interval")) {
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
            case 10:
               return "cache-location";
            case 11:
               return "cache-memory-size";
            case 12:
               return "cache-disk-size";
            case 13:
               return "cache-timeout-interval";
            case 14:
               return "max-size";
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
      private XMLEntityCacheMBeanImpl bean;

      protected Helper(XMLEntityCacheMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "CacheLocation";
            case 11:
               return "CacheMemorySize";
            case 12:
               return "CacheDiskSize";
            case 13:
               return "CacheTimeoutInterval";
            case 14:
               return "MaxSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CacheDiskSize")) {
            return 12;
         } else if (propName.equals("CacheLocation")) {
            return 10;
         } else if (propName.equals("CacheMemorySize")) {
            return 11;
         } else if (propName.equals("CacheTimeoutInterval")) {
            return 13;
         } else {
            return propName.equals("MaxSize") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isCacheDiskSizeSet()) {
               buf.append("CacheDiskSize");
               buf.append(String.valueOf(this.bean.getCacheDiskSize()));
            }

            if (this.bean.isCacheLocationSet()) {
               buf.append("CacheLocation");
               buf.append(String.valueOf(this.bean.getCacheLocation()));
            }

            if (this.bean.isCacheMemorySizeSet()) {
               buf.append("CacheMemorySize");
               buf.append(String.valueOf(this.bean.getCacheMemorySize()));
            }

            if (this.bean.isCacheTimeoutIntervalSet()) {
               buf.append("CacheTimeoutInterval");
               buf.append(String.valueOf(this.bean.getCacheTimeoutInterval()));
            }

            if (this.bean.isMaxSizeSet()) {
               buf.append("MaxSize");
               buf.append(String.valueOf(this.bean.getMaxSize()));
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
            XMLEntityCacheMBeanImpl otherTyped = (XMLEntityCacheMBeanImpl)other;
            this.computeDiff("CacheDiskSize", this.bean.getCacheDiskSize(), otherTyped.getCacheDiskSize(), true);
            this.computeDiff("CacheLocation", this.bean.getCacheLocation(), otherTyped.getCacheLocation(), true);
            this.computeDiff("CacheMemorySize", this.bean.getCacheMemorySize(), otherTyped.getCacheMemorySize(), true);
            this.computeDiff("CacheTimeoutInterval", this.bean.getCacheTimeoutInterval(), otherTyped.getCacheTimeoutInterval(), true);
            this.computeDiff("MaxSize", this.bean.getMaxSize(), otherTyped.getMaxSize(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            XMLEntityCacheMBeanImpl original = (XMLEntityCacheMBeanImpl)event.getSourceBean();
            XMLEntityCacheMBeanImpl proposed = (XMLEntityCacheMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CacheDiskSize")) {
                  original.setCacheDiskSize(proposed.getCacheDiskSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CacheLocation")) {
                  original.setCacheLocation(proposed.getCacheLocation());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("CacheMemorySize")) {
                  original.setCacheMemorySize(proposed.getCacheMemorySize());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("CacheTimeoutInterval")) {
                  original.setCacheTimeoutInterval(proposed.getCacheTimeoutInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("MaxSize")) {
                  original.setMaxSize(proposed.getMaxSize());
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
            XMLEntityCacheMBeanImpl copy = (XMLEntityCacheMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CacheDiskSize")) && this.bean.isCacheDiskSizeSet()) {
               copy.setCacheDiskSize(this.bean.getCacheDiskSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheLocation")) && this.bean.isCacheLocationSet()) {
               copy.setCacheLocation(this.bean.getCacheLocation());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheMemorySize")) && this.bean.isCacheMemorySizeSet()) {
               copy.setCacheMemorySize(this.bean.getCacheMemorySize());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheTimeoutInterval")) && this.bean.isCacheTimeoutIntervalSet()) {
               copy.setCacheTimeoutInterval(this.bean.getCacheTimeoutInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxSize")) && this.bean.isMaxSizeSet()) {
               copy.setMaxSize(this.bean.getMaxSize());
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
