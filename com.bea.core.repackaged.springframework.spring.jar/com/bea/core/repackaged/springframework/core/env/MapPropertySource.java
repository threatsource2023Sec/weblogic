package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Collection;
import java.util.Map;

public class MapPropertySource extends EnumerablePropertySource {
   public MapPropertySource(String name, Map source) {
      super(name, source);
   }

   @Nullable
   public Object getProperty(String name) {
      return ((Map)this.source).get(name);
   }

   public boolean containsProperty(String name) {
      return ((Map)this.source).containsKey(name);
   }

   public String[] getPropertyNames() {
      return StringUtils.toStringArray((Collection)((Map)this.source).keySet());
   }
}
