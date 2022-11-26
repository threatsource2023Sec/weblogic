package org.stringtemplate.v4.misc;

import java.util.Map;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ModelAdaptor;
import org.stringtemplate.v4.ST;

public class MapModelAdaptor implements ModelAdaptor {
   public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName) throws STNoSuchPropertyException {
      Map map = (Map)o;
      Object value;
      if (property == null) {
         value = map.get("default");
      } else if (property.equals("keys")) {
         value = map.keySet();
      } else if (property.equals("values")) {
         value = map.values();
      } else if (map.containsKey(property)) {
         value = map.get(property);
      } else if (map.containsKey(propertyName)) {
         value = map.get(propertyName);
      } else {
         value = map.get("default");
      }

      if (value == "key") {
         value = property;
      }

      return value;
   }
}
