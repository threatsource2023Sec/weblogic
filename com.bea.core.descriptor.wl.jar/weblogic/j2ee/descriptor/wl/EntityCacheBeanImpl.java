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

public class EntityCacheBeanImpl extends AbstractDescriptorBean implements EntityCacheBean, Serializable {
   private boolean _CacheBetweenTransactions;
   private String _ConcurrencyStrategy;
   private boolean _DisableReadyInstances;
   private String _Id;
   private int _IdleTimeoutSeconds;
   private int _MaxBeansInCache;
   private int _MaxQueriesInCache;
   private int _ReadTimeoutSeconds;
   private static SchemaHelper2 _schemaHelper;

   public EntityCacheBeanImpl() {
      this._initializeProperty(-1);
   }

   public EntityCacheBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EntityCacheBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
      LegalChecks.checkMin("MaxBeansInCache", param0, 1);
      int _oldVal = this._MaxBeansInCache;
      this._MaxBeansInCache = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void setMaxQueriesInCache(int param0) {
      LegalChecks.checkMin("MaxQueriesInCache", param0, 0);
      int _oldVal = this._MaxQueriesInCache;
      this._MaxQueriesInCache = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getMaxQueriesInCache() {
      return this._MaxQueriesInCache;
   }

   public boolean isMaxQueriesInCacheInherited() {
      return false;
   }

   public boolean isMaxQueriesInCacheSet() {
      return this._isSet(1);
   }

   public int getIdleTimeoutSeconds() {
      return this._IdleTimeoutSeconds;
   }

   public boolean isIdleTimeoutSecondsInherited() {
      return false;
   }

   public boolean isIdleTimeoutSecondsSet() {
      return this._isSet(2);
   }

   public void setIdleTimeoutSeconds(int param0) {
      LegalChecks.checkMin("IdleTimeoutSeconds", param0, 0);
      int _oldVal = this._IdleTimeoutSeconds;
      this._IdleTimeoutSeconds = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getReadTimeoutSeconds() {
      return this._ReadTimeoutSeconds;
   }

   public boolean isReadTimeoutSecondsInherited() {
      return false;
   }

   public boolean isReadTimeoutSecondsSet() {
      return this._isSet(3);
   }

   public void setReadTimeoutSeconds(int param0) {
      LegalChecks.checkMin("ReadTimeoutSeconds", param0, 0);
      int _oldVal = this._ReadTimeoutSeconds;
      this._ReadTimeoutSeconds = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getConcurrencyStrategy() {
      return this._ConcurrencyStrategy;
   }

   public boolean isConcurrencyStrategyInherited() {
      return false;
   }

   public boolean isConcurrencyStrategySet() {
      return this._isSet(4);
   }

   public void setConcurrencyStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Exclusive", "Database", "ReadOnly", "Optimistic", "ReadOnlyExclusive"};
      param0 = LegalChecks.checkInEnum("ConcurrencyStrategy", param0, _set);
      String _oldVal = this._ConcurrencyStrategy;
      this._ConcurrencyStrategy = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isCacheBetweenTransactions() {
      return this._CacheBetweenTransactions;
   }

   public boolean isCacheBetweenTransactionsInherited() {
      return false;
   }

   public boolean isCacheBetweenTransactionsSet() {
      return this._isSet(5);
   }

   public void setCacheBetweenTransactions(boolean param0) {
      boolean _oldVal = this._CacheBetweenTransactions;
      this._CacheBetweenTransactions = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isDisableReadyInstances() {
      return this._DisableReadyInstances;
   }

   public boolean isDisableReadyInstancesInherited() {
      return false;
   }

   public boolean isDisableReadyInstancesSet() {
      return this._isSet(6);
   }

   public void setDisableReadyInstances(boolean param0) {
      boolean _oldVal = this._DisableReadyInstances;
      this._DisableReadyInstances = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._ConcurrencyStrategy = "Database";
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._IdleTimeoutSeconds = 600;
               if (initOne) {
                  break;
               }
            case 0:
               this._MaxBeansInCache = 1000;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxQueriesInCache = 100;
               if (initOne) {
                  break;
               }
            case 3:
               this._ReadTimeoutSeconds = 600;
               if (initOne) {
                  break;
               }
            case 5:
               this._CacheBetweenTransactions = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._DisableReadyInstances = false;
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
                  return 7;
               }
               break;
            case 18:
               if (s.equals("max-beans-in-cache")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("concurrency-strategy")) {
                  return 4;
               }

               if (s.equals("idle-timeout-seconds")) {
                  return 2;
               }

               if (s.equals("max-queries-in-cache")) {
                  return 1;
               }

               if (s.equals("read-timeout-seconds")) {
                  return 3;
               }
               break;
            case 23:
               if (s.equals("disable-ready-instances")) {
                  return 6;
               }
               break;
            case 26:
               if (s.equals("cache-between-transactions")) {
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
               return "max-beans-in-cache";
            case 1:
               return "max-queries-in-cache";
            case 2:
               return "idle-timeout-seconds";
            case 3:
               return "read-timeout-seconds";
            case 4:
               return "concurrency-strategy";
            case 5:
               return "cache-between-transactions";
            case 6:
               return "disable-ready-instances";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
            default:
               return super.isConfigurable(propIndex);
            case 2:
               return true;
            case 3:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EntityCacheBeanImpl bean;

      protected Helper(EntityCacheBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MaxBeansInCache";
            case 1:
               return "MaxQueriesInCache";
            case 2:
               return "IdleTimeoutSeconds";
            case 3:
               return "ReadTimeoutSeconds";
            case 4:
               return "ConcurrencyStrategy";
            case 5:
               return "CacheBetweenTransactions";
            case 6:
               return "DisableReadyInstances";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConcurrencyStrategy")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("IdleTimeoutSeconds")) {
            return 2;
         } else if (propName.equals("MaxBeansInCache")) {
            return 0;
         } else if (propName.equals("MaxQueriesInCache")) {
            return 1;
         } else if (propName.equals("ReadTimeoutSeconds")) {
            return 3;
         } else if (propName.equals("CacheBetweenTransactions")) {
            return 5;
         } else {
            return propName.equals("DisableReadyInstances") ? 6 : super.getPropertyIndex(propName);
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
            if (this.bean.isConcurrencyStrategySet()) {
               buf.append("ConcurrencyStrategy");
               buf.append(String.valueOf(this.bean.getConcurrencyStrategy()));
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

            if (this.bean.isMaxQueriesInCacheSet()) {
               buf.append("MaxQueriesInCache");
               buf.append(String.valueOf(this.bean.getMaxQueriesInCache()));
            }

            if (this.bean.isReadTimeoutSecondsSet()) {
               buf.append("ReadTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getReadTimeoutSeconds()));
            }

            if (this.bean.isCacheBetweenTransactionsSet()) {
               buf.append("CacheBetweenTransactions");
               buf.append(String.valueOf(this.bean.isCacheBetweenTransactions()));
            }

            if (this.bean.isDisableReadyInstancesSet()) {
               buf.append("DisableReadyInstances");
               buf.append(String.valueOf(this.bean.isDisableReadyInstances()));
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
            EntityCacheBeanImpl otherTyped = (EntityCacheBeanImpl)other;
            this.computeDiff("ConcurrencyStrategy", this.bean.getConcurrencyStrategy(), otherTyped.getConcurrencyStrategy(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IdleTimeoutSeconds", this.bean.getIdleTimeoutSeconds(), otherTyped.getIdleTimeoutSeconds(), true);
            this.computeDiff("MaxBeansInCache", this.bean.getMaxBeansInCache(), otherTyped.getMaxBeansInCache(), true);
            this.computeDiff("MaxQueriesInCache", this.bean.getMaxQueriesInCache(), otherTyped.getMaxQueriesInCache(), false);
            this.computeDiff("ReadTimeoutSeconds", this.bean.getReadTimeoutSeconds(), otherTyped.getReadTimeoutSeconds(), true);
            this.computeDiff("CacheBetweenTransactions", this.bean.isCacheBetweenTransactions(), otherTyped.isCacheBetweenTransactions(), false);
            this.computeDiff("DisableReadyInstances", this.bean.isDisableReadyInstances(), otherTyped.isDisableReadyInstances(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EntityCacheBeanImpl original = (EntityCacheBeanImpl)event.getSourceBean();
            EntityCacheBeanImpl proposed = (EntityCacheBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConcurrencyStrategy")) {
                  original.setConcurrencyStrategy(proposed.getConcurrencyStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("IdleTimeoutSeconds")) {
                  original.setIdleTimeoutSeconds(proposed.getIdleTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MaxBeansInCache")) {
                  original.setMaxBeansInCache(proposed.getMaxBeansInCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MaxQueriesInCache")) {
                  original.setMaxQueriesInCache(proposed.getMaxQueriesInCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ReadTimeoutSeconds")) {
                  original.setReadTimeoutSeconds(proposed.getReadTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("CacheBetweenTransactions")) {
                  original.setCacheBetweenTransactions(proposed.isCacheBetweenTransactions());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("DisableReadyInstances")) {
                  original.setDisableReadyInstances(proposed.isDisableReadyInstances());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            EntityCacheBeanImpl copy = (EntityCacheBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConcurrencyStrategy")) && this.bean.isConcurrencyStrategySet()) {
               copy.setConcurrencyStrategy(this.bean.getConcurrencyStrategy());
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

            if ((excludeProps == null || !excludeProps.contains("MaxQueriesInCache")) && this.bean.isMaxQueriesInCacheSet()) {
               copy.setMaxQueriesInCache(this.bean.getMaxQueriesInCache());
            }

            if ((excludeProps == null || !excludeProps.contains("ReadTimeoutSeconds")) && this.bean.isReadTimeoutSecondsSet()) {
               copy.setReadTimeoutSeconds(this.bean.getReadTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheBetweenTransactions")) && this.bean.isCacheBetweenTransactionsSet()) {
               copy.setCacheBetweenTransactions(this.bean.isCacheBetweenTransactions());
            }

            if ((excludeProps == null || !excludeProps.contains("DisableReadyInstances")) && this.bean.isDisableReadyInstancesSet()) {
               copy.setDisableReadyInstances(this.bean.isDisableReadyInstances());
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
