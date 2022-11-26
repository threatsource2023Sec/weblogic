package kodo.conf.customizers;

import kodo.conf.descriptor.CustomQueryCacheBean;
import kodo.conf.descriptor.DefaultQueryCacheBean;
import kodo.conf.descriptor.DisabledQueryCacheBean;
import kodo.conf.descriptor.GemFireQueryCacheBean;
import kodo.conf.descriptor.KodoConcurrentQueryCacheBean;
import kodo.conf.descriptor.LRUQueryCacheBean;
import kodo.conf.descriptor.QueryCacheBean;
import kodo.conf.descriptor.QueryCachesBean;
import kodo.conf.descriptor.TangosolQueryCacheBean;

public class QueryCachesBeanCustomizer {
   private final QueryCachesBean customized;

   public QueryCachesBeanCustomizer(QueryCachesBean custom) {
      this.customized = custom;
   }

   public Class[] getQueryCacheTypes() {
      return new Class[]{DefaultQueryCacheBean.class, KodoConcurrentQueryCacheBean.class, GemFireQueryCacheBean.class, LRUQueryCacheBean.class, TangosolQueryCacheBean.class, DisabledQueryCacheBean.class, CustomQueryCacheBean.class};
   }

   public QueryCacheBean getQueryCache() {
      QueryCacheBean provider = null;
      provider = this.customized.getDefaultQueryCache();
      if (provider != null) {
         return provider;
      } else {
         QueryCacheBean provider = this.customized.getKodoConcurrentQueryCache();
         if (provider != null) {
            return provider;
         } else {
            QueryCacheBean provider = this.customized.getGemFireQueryCache();
            if (provider != null) {
               return provider;
            } else {
               QueryCacheBean provider = this.customized.getLRUQueryCache();
               if (provider != null) {
                  return provider;
               } else {
                  QueryCacheBean provider = this.customized.getTangosolQueryCache();
                  if (provider != null) {
                     return provider;
                  } else {
                     QueryCacheBean provider = this.customized.getDisabledQueryCache();
                     return (QueryCacheBean)(provider != null ? provider : this.customized.getCustomQueryCache());
                  }
               }
            }
         }
      }
   }

   public QueryCacheBean createQueryCache(Class type) {
      this.destroyQueryCache();
      QueryCacheBean provider = null;
      if (DefaultQueryCacheBean.class.getName().equals(type.getName())) {
         provider = this.customized.createDefaultQueryCache();
      } else if (KodoConcurrentQueryCacheBean.class.getName().equals(type.getName())) {
         provider = this.customized.createKodoConcurrentQueryCache();
      } else if (GemFireQueryCacheBean.class.getName().equals(type.getName())) {
         provider = this.customized.createGemFireQueryCache();
      } else if (LRUQueryCacheBean.class.getName().equals(type.getName())) {
         provider = this.customized.createLRUQueryCache();
      } else if (TangosolQueryCacheBean.class.getName().equals(type.getName())) {
         provider = this.customized.createTangosolQueryCache();
      } else if (DisabledQueryCacheBean.class.getName().equals(type.getName())) {
         provider = this.customized.createDisabledQueryCache();
      } else if (CustomQueryCacheBean.class.getName().equals(type.getName())) {
         provider = this.customized.createCustomQueryCache();
      }

      if (provider != null) {
         ((QueryCacheBean)provider).setName("QueryCache");
      }

      return (QueryCacheBean)provider;
   }

   public void destroyQueryCache() {
      QueryCacheBean provider = null;
      provider = this.customized.getDefaultQueryCache();
      if (provider != null) {
         this.customized.destroyDefaultQueryCache();
      } else {
         QueryCacheBean provider = this.customized.getKodoConcurrentQueryCache();
         if (provider != null) {
            this.customized.destroyKodoConcurrentQueryCache();
         } else {
            QueryCacheBean provider = this.customized.getGemFireQueryCache();
            if (provider != null) {
               this.customized.destroyGemFireQueryCache();
            } else {
               QueryCacheBean provider = this.customized.getLRUQueryCache();
               if (provider != null) {
                  this.customized.destroyLRUQueryCache();
               } else {
                  QueryCacheBean provider = this.customized.getTangosolQueryCache();
                  if (provider != null) {
                     this.customized.destroyTangosolQueryCache();
                  } else {
                     QueryCacheBean provider = this.customized.getDisabledQueryCache();
                     if (provider != null) {
                        this.customized.destroyDisabledQueryCache();
                     } else {
                        QueryCacheBean provider = this.customized.getCustomQueryCache();
                        if (provider != null) {
                           this.customized.destroyCustomQueryCache();
                        }

                     }
                  }
               }
            }
         }
      }
   }
}
