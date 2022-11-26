package org.apache.openjpa.conf;

import java.util.Map;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.meta.MetaDataRepository;

public class MetaDataRepositoryValue extends PluginValue {
   private static final String KEY = "MetaDataRepository";

   public MetaDataRepositoryValue() {
      super("MetaDataRepository", false);
      String[] aliases = new String[]{"default", MetaDataRepository.class.getName()};
      this.setAliases(aliases);
      this.setDefault(aliases[0]);
      this.setString(aliases[0]);
   }

   public Object instantiate(Class type, Configuration c, boolean fatal) {
      MetaDataRepository repos = null;
      OpenJPAConfiguration conf = (OpenJPAConfiguration)c;
      Object[] os = (Object[])((Object[])CacheMarshallersValue.getMarshallerById(conf, MetaDataCacheMaintenance.class.getName()).load());
      if (os != null) {
         repos = (MetaDataRepository)os[0];
         if (os[1] != null) {
            conf.getQueryCompilationCacheInstance().putAll((Map)os[1]);
         }
      }

      return repos == null ? super.instantiate(type, c, fatal) : repos;
   }
}
