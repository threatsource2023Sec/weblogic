package org.apache.openjpa.conf;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.util.ParseException;
import org.apache.openjpa.lib.util.concurrent.ConcurrentMap;
import org.apache.openjpa.util.CacheMap;

public class QueryCompilationCacheValue extends PluginValue {
   public static final String[] ALIASES = new String[]{"true", CacheMap.class.getName(), "all", ConcurrentHashMap.class.getName(), "false", null};

   public QueryCompilationCacheValue(String prop) {
      super(prop, true);
      this.setAliases(ALIASES);
      this.setDefault(ALIASES[0]);
      this.setClassName(ALIASES[1]);
   }

   public Object newInstance(String clsName, Class type, Configuration conf, boolean fatal) {
      Map map;
      try {
         map = (Map)super.newInstance(clsName, type, conf, fatal);
      } catch (ParseException var7) {
         map = (Map)super.newInstance(clsName, QueryCompilationCacheValue.class, conf, fatal);
      } catch (IllegalArgumentException var8) {
         map = (Map)super.newInstance(clsName, QueryCompilationCacheValue.class, conf, fatal);
      }

      if (map != null && !(map instanceof Hashtable) && !(map instanceof CacheMap) && !(map instanceof ConcurrentMap) && !(map instanceof java.util.concurrent.ConcurrentMap)) {
         map = Collections.synchronizedMap(map);
      }

      return map;
   }
}
