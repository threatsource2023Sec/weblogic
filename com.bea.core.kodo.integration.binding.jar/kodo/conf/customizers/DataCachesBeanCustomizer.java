package kodo.conf.customizers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kodo.conf.descriptor.CustomDataCacheBean;
import kodo.conf.descriptor.DataCacheBean;
import kodo.conf.descriptor.DataCachesBean;
import kodo.conf.descriptor.DefaultDataCacheBean;
import kodo.conf.descriptor.GemFireDataCacheBean;
import kodo.conf.descriptor.KodoConcurrentDataCacheBean;
import kodo.conf.descriptor.LRUDataCacheBean;
import kodo.conf.descriptor.TangosolDataCacheBean;

public class DataCachesBeanCustomizer {
   private final DataCachesBean customized;

   public DataCachesBeanCustomizer(DataCachesBean custom) {
      this.customized = custom;
   }

   public Class[] getDataCacheTypes() {
      return new Class[]{DefaultDataCacheBean.class, KodoConcurrentDataCacheBean.class, GemFireDataCacheBean.class, LRUDataCacheBean.class, TangosolDataCacheBean.class, CustomDataCacheBean.class};
   }

   public DataCacheBean[] getDataCaches() {
      List all = new ArrayList(7);
      all.addAll(Arrays.asList(this.customized.getDefaultDataCache()));
      all.addAll(Arrays.asList(this.customized.getKodoConcurrentDataCache()));
      all.addAll(Arrays.asList(this.customized.getGemFireDataCache()));
      all.addAll(Arrays.asList(this.customized.getLRUDataCache()));
      all.addAll(Arrays.asList(this.customized.getTangosolDataCache()));
      all.addAll(Arrays.asList(this.customized.getCustomDataCache()));
      return (DataCacheBean[])((DataCacheBean[])all.toArray(new DataCacheBean[all.size()]));
   }

   public DataCacheBean createDataCache(Class type, String name) {
      DataCacheBean dcb = null;
      if (DefaultDataCacheBean.class.getName().equals(type.getName())) {
         dcb = this.customized.createDefaultDataCache(name);
      } else if (KodoConcurrentDataCacheBean.class.getName().equals(type.getName())) {
         dcb = this.customized.createKodoConcurrentDataCache(name);
      } else if (GemFireDataCacheBean.class.getName().equals(type.getName())) {
         dcb = this.customized.createGemFireDataCache(name);
      } else if (LRUDataCacheBean.class.getName().equals(type.getName())) {
         dcb = this.customized.createLRUDataCache(name);
      } else if (TangosolDataCacheBean.class.getName().equals(type.getName())) {
         dcb = this.customized.createTangosolDataCache(name);
      } else if (CustomDataCacheBean.class.getName().equals(type.getName())) {
         dcb = this.customized.createCustomDataCache(name);
      }

      return (DataCacheBean)dcb;
   }

   public DataCacheBean lookupDataCache(String name) {
      DataCacheBean[] dataCaches = this.getDataCaches();

      for(int i = 0; i < dataCaches.length; ++i) {
         if (dataCaches[i].getName().equals(name)) {
            return dataCaches[i];
         }
      }

      return null;
   }

   public void destroyDataCache(DataCacheBean toDestroy) {
      if (toDestroy != null) {
         if (toDestroy instanceof DefaultDataCacheBean) {
            this.customized.destroyDefaultDataCache((DefaultDataCacheBean)toDestroy);
         } else if (toDestroy instanceof KodoConcurrentDataCacheBean) {
            this.customized.destroyKodoConcurrentDataCache((KodoConcurrentDataCacheBean)toDestroy);
         } else if (toDestroy instanceof GemFireDataCacheBean) {
            this.customized.destroyGemFireDataCache((GemFireDataCacheBean)toDestroy);
         } else if (toDestroy instanceof LRUDataCacheBean) {
            this.customized.destroyLRUDataCache((LRUDataCacheBean)toDestroy);
         } else if (toDestroy instanceof TangosolDataCacheBean) {
            this.customized.destroyTangosolDataCache((TangosolDataCacheBean)toDestroy);
         } else {
            this.customized.destroyCustomDataCache((CustomDataCacheBean)toDestroy);
         }

      }
   }
}
