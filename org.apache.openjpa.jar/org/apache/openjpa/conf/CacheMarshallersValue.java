package org.apache.openjpa.conf;

import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.PluginListValue;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class CacheMarshallersValue extends PluginListValue {
   private static final String KEY = "CacheMarshallers";
   private static final CacheMarshaller NO_OP_CACHE_MARSHALLER = new NoOpCacheMarshaller();
   private static final Localizer _loc = Localizer.forPackage(CacheMarshallersValue.class);
   private Configuration _conf;
   private Map _marshallers;
   private boolean _initialized;

   public CacheMarshallersValue(Configuration conf) {
      super("CacheMarshallers");
      this._conf = conf;
      this.setAlias("default", CacheMarshallerImpl.class.getName());
      this.setAlias("none", (String)null);
      this.setDefault("none");
      this.setString("none");
      this.setScope(this.getClass());
   }

   public Object instantiate(Class elemType, Configuration conf, boolean fatal) {
      CacheMarshaller[] ms = (CacheMarshaller[])((CacheMarshaller[])super.instantiate(elemType, conf, fatal));
      if (ms != null) {
         this._marshallers = new HashMap();

         for(int i = 0; i < ms.length; ++i) {
            String mid = ms[i].getId();
            if (mid != null) {
               this._marshallers.put(mid, ms[i]);
            }
         }
      } else {
         this._marshallers = null;
      }

      return ms;
   }

   public CacheMarshaller getMarshallerById(String id) {
      this.initialize();
      CacheMarshaller cm = (CacheMarshaller)this._marshallers.get(id);
      if (cm == null) {
         if (this.getLog().isTraceEnabled()) {
            this.getLog().trace(_loc.get("cache-marshaller-not-found", (Object)id));
         }

         return NO_OP_CACHE_MARSHALLER;
      } else {
         if (this.getLog().isTraceEnabled()) {
            this.getLog().trace(_loc.get("cache-marshaller-found", id, cm.getClass().getName()));
         }

         return cm;
      }
   }

   private Log getLog() {
      return this._conf.getConfigurationLog();
   }

   public static CacheMarshaller getMarshallerById(Configuration c, String id) {
      CacheMarshallersValue v = ((OpenJPAConfigurationImpl)c).cacheMarshallerPlugins;
      return v.getMarshallerById(id);
   }

   public Map getInstancesAsMap() {
      return this._marshallers;
   }

   protected synchronized void initialize() {
      if (!this._initialized) {
         this.instantiate(CacheMarshaller.class, this._conf);
         this._initialized = true;
      }

   }
}
