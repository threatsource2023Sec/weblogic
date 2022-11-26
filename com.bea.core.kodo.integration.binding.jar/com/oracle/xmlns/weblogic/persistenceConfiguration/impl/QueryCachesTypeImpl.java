package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomQueryCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultQueryCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DisabledQueryCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GemFireQueryCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoConcurrentQueryCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LruQueryCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.QueryCachesType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TangosolQueryCacheType;
import javax.xml.namespace.QName;

public class QueryCachesTypeImpl extends XmlComplexContentImpl implements QueryCachesType {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTQUERYCACHE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-query-cache");
   private static final QName KODOCONCURRENTQUERYCACHE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-concurrent-query-cache");
   private static final QName GEMFIREQUERYCACHE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "gem-fire-query-cache");
   private static final QName LRUQUERYCACHE$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "lru-query-cache");
   private static final QName TANGOSOLQUERYCACHE$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "tangosol-query-cache");
   private static final QName DISABLEDQUERYCACHE$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "disabled-query-cache");
   private static final QName CUSTOMQUERYCACHE$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-query-cache");

   public QueryCachesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DefaultQueryCacheType getDefaultQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCacheType target = null;
         target = (DefaultQueryCacheType)this.get_store().find_element_user(DEFAULTQUERYCACHE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCacheType target = null;
         target = (DefaultQueryCacheType)this.get_store().find_element_user(DEFAULTQUERYCACHE$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTQUERYCACHE$0) != 0;
      }
   }

   public void setDefaultQueryCache(DefaultQueryCacheType defaultQueryCache) {
      this.generatedSetterHelperImpl(defaultQueryCache, DEFAULTQUERYCACHE$0, 0, (short)1);
   }

   public DefaultQueryCacheType addNewDefaultQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCacheType target = null;
         target = (DefaultQueryCacheType)this.get_store().add_element_user(DEFAULTQUERYCACHE$0);
         return target;
      }
   }

   public void setNilDefaultQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCacheType target = null;
         target = (DefaultQueryCacheType)this.get_store().find_element_user(DEFAULTQUERYCACHE$0, 0);
         if (target == null) {
            target = (DefaultQueryCacheType)this.get_store().add_element_user(DEFAULTQUERYCACHE$0);
         }

         target.setNil();
      }
   }

   public void unsetDefaultQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTQUERYCACHE$0, 0);
      }
   }

   public KodoConcurrentQueryCacheType getKodoConcurrentQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentQueryCacheType target = null;
         target = (KodoConcurrentQueryCacheType)this.get_store().find_element_user(KODOCONCURRENTQUERYCACHE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoConcurrentQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentQueryCacheType target = null;
         target = (KodoConcurrentQueryCacheType)this.get_store().find_element_user(KODOCONCURRENTQUERYCACHE$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoConcurrentQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOCONCURRENTQUERYCACHE$2) != 0;
      }
   }

   public void setKodoConcurrentQueryCache(KodoConcurrentQueryCacheType kodoConcurrentQueryCache) {
      this.generatedSetterHelperImpl(kodoConcurrentQueryCache, KODOCONCURRENTQUERYCACHE$2, 0, (short)1);
   }

   public KodoConcurrentQueryCacheType addNewKodoConcurrentQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentQueryCacheType target = null;
         target = (KodoConcurrentQueryCacheType)this.get_store().add_element_user(KODOCONCURRENTQUERYCACHE$2);
         return target;
      }
   }

   public void setNilKodoConcurrentQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoConcurrentQueryCacheType target = null;
         target = (KodoConcurrentQueryCacheType)this.get_store().find_element_user(KODOCONCURRENTQUERYCACHE$2, 0);
         if (target == null) {
            target = (KodoConcurrentQueryCacheType)this.get_store().add_element_user(KODOCONCURRENTQUERYCACHE$2);
         }

         target.setNil();
      }
   }

   public void unsetKodoConcurrentQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOCONCURRENTQUERYCACHE$2, 0);
      }
   }

   public GemFireQueryCacheType getGemFireQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireQueryCacheType target = null;
         target = (GemFireQueryCacheType)this.get_store().find_element_user(GEMFIREQUERYCACHE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilGemFireQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireQueryCacheType target = null;
         target = (GemFireQueryCacheType)this.get_store().find_element_user(GEMFIREQUERYCACHE$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetGemFireQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GEMFIREQUERYCACHE$4) != 0;
      }
   }

   public void setGemFireQueryCache(GemFireQueryCacheType gemFireQueryCache) {
      this.generatedSetterHelperImpl(gemFireQueryCache, GEMFIREQUERYCACHE$4, 0, (short)1);
   }

   public GemFireQueryCacheType addNewGemFireQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireQueryCacheType target = null;
         target = (GemFireQueryCacheType)this.get_store().add_element_user(GEMFIREQUERYCACHE$4);
         return target;
      }
   }

   public void setNilGemFireQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GemFireQueryCacheType target = null;
         target = (GemFireQueryCacheType)this.get_store().find_element_user(GEMFIREQUERYCACHE$4, 0);
         if (target == null) {
            target = (GemFireQueryCacheType)this.get_store().add_element_user(GEMFIREQUERYCACHE$4);
         }

         target.setNil();
      }
   }

   public void unsetGemFireQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GEMFIREQUERYCACHE$4, 0);
      }
   }

   public LruQueryCacheType getLruQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruQueryCacheType target = null;
         target = (LruQueryCacheType)this.get_store().find_element_user(LRUQUERYCACHE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLruQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruQueryCacheType target = null;
         target = (LruQueryCacheType)this.get_store().find_element_user(LRUQUERYCACHE$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLruQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LRUQUERYCACHE$6) != 0;
      }
   }

   public void setLruQueryCache(LruQueryCacheType lruQueryCache) {
      this.generatedSetterHelperImpl(lruQueryCache, LRUQUERYCACHE$6, 0, (short)1);
   }

   public LruQueryCacheType addNewLruQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruQueryCacheType target = null;
         target = (LruQueryCacheType)this.get_store().add_element_user(LRUQUERYCACHE$6);
         return target;
      }
   }

   public void setNilLruQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LruQueryCacheType target = null;
         target = (LruQueryCacheType)this.get_store().find_element_user(LRUQUERYCACHE$6, 0);
         if (target == null) {
            target = (LruQueryCacheType)this.get_store().add_element_user(LRUQUERYCACHE$6);
         }

         target.setNil();
      }
   }

   public void unsetLruQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LRUQUERYCACHE$6, 0);
      }
   }

   public TangosolQueryCacheType getTangosolQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolQueryCacheType target = null;
         target = (TangosolQueryCacheType)this.get_store().find_element_user(TANGOSOLQUERYCACHE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTangosolQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolQueryCacheType target = null;
         target = (TangosolQueryCacheType)this.get_store().find_element_user(TANGOSOLQUERYCACHE$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTangosolQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TANGOSOLQUERYCACHE$8) != 0;
      }
   }

   public void setTangosolQueryCache(TangosolQueryCacheType tangosolQueryCache) {
      this.generatedSetterHelperImpl(tangosolQueryCache, TANGOSOLQUERYCACHE$8, 0, (short)1);
   }

   public TangosolQueryCacheType addNewTangosolQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolQueryCacheType target = null;
         target = (TangosolQueryCacheType)this.get_store().add_element_user(TANGOSOLQUERYCACHE$8);
         return target;
      }
   }

   public void setNilTangosolQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TangosolQueryCacheType target = null;
         target = (TangosolQueryCacheType)this.get_store().find_element_user(TANGOSOLQUERYCACHE$8, 0);
         if (target == null) {
            target = (TangosolQueryCacheType)this.get_store().add_element_user(TANGOSOLQUERYCACHE$8);
         }

         target.setNil();
      }
   }

   public void unsetTangosolQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TANGOSOLQUERYCACHE$8, 0);
      }
   }

   public DisabledQueryCacheType getDisabledQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisabledQueryCacheType target = null;
         target = (DisabledQueryCacheType)this.get_store().find_element_user(DISABLEDQUERYCACHE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDisabledQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisabledQueryCacheType target = null;
         target = (DisabledQueryCacheType)this.get_store().find_element_user(DISABLEDQUERYCACHE$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDisabledQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISABLEDQUERYCACHE$10) != 0;
      }
   }

   public void setDisabledQueryCache(DisabledQueryCacheType disabledQueryCache) {
      this.generatedSetterHelperImpl(disabledQueryCache, DISABLEDQUERYCACHE$10, 0, (short)1);
   }

   public DisabledQueryCacheType addNewDisabledQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisabledQueryCacheType target = null;
         target = (DisabledQueryCacheType)this.get_store().add_element_user(DISABLEDQUERYCACHE$10);
         return target;
      }
   }

   public void setNilDisabledQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisabledQueryCacheType target = null;
         target = (DisabledQueryCacheType)this.get_store().find_element_user(DISABLEDQUERYCACHE$10, 0);
         if (target == null) {
            target = (DisabledQueryCacheType)this.get_store().add_element_user(DISABLEDQUERYCACHE$10);
         }

         target.setNil();
      }
   }

   public void unsetDisabledQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISABLEDQUERYCACHE$10, 0);
      }
   }

   public CustomQueryCacheType getCustomQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCacheType target = null;
         target = (CustomQueryCacheType)this.get_store().find_element_user(CUSTOMQUERYCACHE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCacheType target = null;
         target = (CustomQueryCacheType)this.get_store().find_element_user(CUSTOMQUERYCACHE$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMQUERYCACHE$12) != 0;
      }
   }

   public void setCustomQueryCache(CustomQueryCacheType customQueryCache) {
      this.generatedSetterHelperImpl(customQueryCache, CUSTOMQUERYCACHE$12, 0, (short)1);
   }

   public CustomQueryCacheType addNewCustomQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCacheType target = null;
         target = (CustomQueryCacheType)this.get_store().add_element_user(CUSTOMQUERYCACHE$12);
         return target;
      }
   }

   public void setNilCustomQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCacheType target = null;
         target = (CustomQueryCacheType)this.get_store().find_element_user(CUSTOMQUERYCACHE$12, 0);
         if (target == null) {
            target = (CustomQueryCacheType)this.get_store().add_element_user(CUSTOMQUERYCACHE$12);
         }

         target.setNil();
      }
   }

   public void unsetCustomQueryCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMQUERYCACHE$12, 0);
      }
   }
}
