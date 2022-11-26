package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class StatefulSessionCacheBeanImpl extends AbstractDescriptorBean implements StatefulSessionCacheBean, Serializable {
   private String _CacheType;
   private String _Id;
   private int _IdleTimeoutSeconds;
   private int _MaxBeansInCache;
   private int _SessionTimeoutSeconds;
   private static SchemaHelper2 _schemaHelper;

   public StatefulSessionCacheBeanImpl() {
      this._initializeProperty(-1);
   }

   public StatefulSessionCacheBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StatefulSessionCacheBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxBeansInCache() {
      return this._MaxBeansInCache;
   }

   public boolean isMaxBeansInCacheInherited() {
      return false;
   }

   public boolean isMaxBeansInCacheSet() {
      return this._isSet(0);
   }

   public void setMaxBeansInCache(int param0) {
      LegalChecks.checkMin("MaxBeansInCache", param0, 0);
      int _oldVal = this._MaxBeansInCache;
      this._MaxBeansInCache = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getIdleTimeoutSeconds() {
      return this._IdleTimeoutSeconds;
   }

   public boolean isIdleTimeoutSecondsInherited() {
      return false;
   }

   public boolean isIdleTimeoutSecondsSet() {
      return this._isSet(1);
   }

   public void setIdleTimeoutSeconds(int param0) {
      LegalChecks.checkMin("IdleTimeoutSeconds", param0, 0);
      int _oldVal = this._IdleTimeoutSeconds;
      this._IdleTimeoutSeconds = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getSessionTimeoutSeconds() {
      return this._SessionTimeoutSeconds;
   }

   public boolean isSessionTimeoutSecondsInherited() {
      return false;
   }

   public boolean isSessionTimeoutSecondsSet() {
      return this._isSet(2);
   }

   public void setSessionTimeoutSeconds(int param0) {
      LegalChecks.checkMin("SessionTimeoutSeconds", param0, 0);
      int _oldVal = this._SessionTimeoutSeconds;
      this._SessionTimeoutSeconds = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getCacheType() {
      return this._CacheType;
   }

   public boolean isCacheTypeInherited() {
      return false;
   }

   public boolean isCacheTypeSet() {
      return this._isSet(3);
   }

   public void setCacheType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NRU", "LRU"};
      param0 = LegalChecks.checkInEnum("CacheType", param0, _set);
      String _oldVal = this._CacheType;
      this._CacheType = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._CacheType = "NRU";
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._IdleTimeoutSeconds = 600;
               if (initOne) {
                  break;
               }
            case 0:
               this._MaxBeansInCache = 1000;
               if (initOne) {
                  break;
               }
            case 2:
               this._SessionTimeoutSeconds = 600;
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
                  return 4;
               }
               break;
            case 10:
               if (s.equals("cache-type")) {
                  return 3;
               }
               break;
            case 18:
               if (s.equals("max-beans-in-cache")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("idle-timeout-seconds")) {
                  return 1;
               }
               break;
            case 23:
               if (s.equals("session-timeout-seconds")) {
                  return 2;
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
               return "max-beans-in-cache";
            case 1:
               return "idle-timeout-seconds";
            case 2:
               return "session-timeout-seconds";
            case 3:
               return "cache-type";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private StatefulSessionCacheBeanImpl bean;

      protected Helper(StatefulSessionCacheBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MaxBeansInCache";
            case 1:
               return "IdleTimeoutSeconds";
            case 2:
               return "SessionTimeoutSeconds";
            case 3:
               return "CacheType";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CacheType")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("IdleTimeoutSeconds")) {
            return 1;
         } else if (propName.equals("MaxBeansInCache")) {
            return 0;
         } else {
            return propName.equals("SessionTimeoutSeconds") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isCacheTypeSet()) {
               buf.append("CacheType");
               buf.append(String.valueOf(this.bean.getCacheType()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIdleTimeoutSecondsSet()) {
               buf.append("IdleTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getIdleTimeoutSeconds()));
            }

            if (this.bean.isMaxBeansInCacheSet()) {
               buf.append("MaxBeansInCache");
               buf.append(String.valueOf(this.bean.getMaxBeansInCache()));
            }

            if (this.bean.isSessionTimeoutSecondsSet()) {
               buf.append("SessionTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getSessionTimeoutSeconds()));
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
            StatefulSessionCacheBeanImpl otherTyped = (StatefulSessionCacheBeanImpl)other;
            this.computeDiff("CacheType", this.bean.getCacheType(), otherTyped.getCacheType(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IdleTimeoutSeconds", this.bean.getIdleTimeoutSeconds(), otherTyped.getIdleTimeoutSeconds(), true);
            this.computeDiff("MaxBeansInCache", this.bean.getMaxBeansInCache(), otherTyped.getMaxBeansInCache(), true);
            this.computeDiff("SessionTimeoutSeconds", this.bean.getSessionTimeoutSeconds(), otherTyped.getSessionTimeoutSeconds(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StatefulSessionCacheBeanImpl original = (StatefulSessionCacheBeanImpl)event.getSourceBean();
            StatefulSessionCacheBeanImpl proposed = (StatefulSessionCacheBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CacheType")) {
                  original.setCacheType(proposed.getCacheType());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("IdleTimeoutSeconds")) {
                  original.setIdleTimeoutSeconds(proposed.getIdleTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MaxBeansInCache")) {
                  original.setMaxBeansInCache(proposed.getMaxBeansInCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SessionTimeoutSeconds")) {
                  original.setSessionTimeoutSeconds(proposed.getSessionTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            StatefulSessionCacheBeanImpl copy = (StatefulSessionCacheBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CacheType")) && this.bean.isCacheTypeSet()) {
               copy.setCacheType(this.bean.getCacheType());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleTimeoutSeconds")) && this.bean.isIdleTimeoutSecondsSet()) {
               copy.setIdleTimeoutSeconds(this.bean.getIdleTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxBeansInCache")) && this.bean.isMaxBeansInCacheSet()) {
               copy.setMaxBeansInCache(this.bean.getMaxBeansInCache());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionTimeoutSeconds")) && this.bean.isSessionTimeoutSecondsSet()) {
               copy.setSessionTimeoutSeconds(this.bean.getSessionTimeoutSeconds());
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
