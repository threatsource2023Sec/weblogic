package org.apache.openjpa.lib.conf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class MapConfigurationProvider implements ConfigurationProvider {
   private static final Localizer _loc = Localizer.forPackage(MapConfigurationProvider.class);
   private Map _props = null;

   public MapConfigurationProvider() {
   }

   public MapConfigurationProvider(Map props) {
      this.addProperties(props);
   }

   public Map getProperties() {
      return this._props == null ? Collections.EMPTY_MAP : this._props;
   }

   public void addProperties(Map props) {
      if (props != null && !props.isEmpty()) {
         if (this._props == null) {
            this._props = new HashMap();
         }

         this._props.putAll(props);
      }
   }

   public Object addProperty(String key, Object value) {
      if (this._props == null) {
         this._props = new HashMap();
      }

      return this._props.put(key, value);
   }

   public void setInto(Configuration conf) {
      this.setInto(conf, conf.getConfigurationLog());
   }

   protected void setInto(Configuration conf, Log log) {
      if (log != null && log.isTraceEnabled()) {
         log.trace(_loc.get("conf-load", (Object)this.getProperties()));
      }

      if (this._props != null) {
         conf.fromProperties(this._props);
      }

   }
}
