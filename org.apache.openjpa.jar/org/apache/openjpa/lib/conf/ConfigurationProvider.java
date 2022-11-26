package org.apache.openjpa.lib.conf;

import java.util.Map;

public interface ConfigurationProvider {
   Map getProperties();

   void addProperties(Map var1);

   Object addProperty(String var1, Object var2);

   void setInto(Configuration var1);
}
