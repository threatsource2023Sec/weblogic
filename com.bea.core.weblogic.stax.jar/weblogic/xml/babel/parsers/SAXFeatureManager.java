package weblogic.xml.babel.parsers;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

class SAXFeatureManager {
   private static Map features = new HashMap();

   static boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      Boolean value = (Boolean)features.get(name);
      if (value == null) {
         throw new SAXNotRecognizedException("Unable to find feature:" + name);
      } else {
         return value;
      }
   }

   static void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
      boolean currentValue = getFeature(name);
      if (currentValue != value) {
         throw new SAXNotSupportedException("Currently the setting " + value + " of feature:" + name + " is not supported by the base parser");
      }
   }

   static {
      features.put("http://xml.org/sax/features/validation", Boolean.FALSE);
      features.put("http://xml.org/sax/features/external-general-entities", Boolean.FALSE);
      features.put("http://xml.org/sax/features/external-parameter-entities", Boolean.FALSE);
      features.put("http://xml.org/sax/features/namespaces", Boolean.TRUE);
      features.put("http://xml.org/sax/features/namespace-prefixes", Boolean.TRUE);
      features.put("http://xml.org/sax/features/string-interning", Boolean.FALSE);
   }
}
