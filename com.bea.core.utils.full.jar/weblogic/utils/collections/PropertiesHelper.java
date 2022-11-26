package weblogic.utils.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesHelper {
   public static Map toMap(Properties changeMe) {
      HashMap retVal = new HashMap();
      Iterator var2 = changeMe.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         retVal.put((String)entry.getKey(), (String)entry.getValue());
      }

      return retVal;
   }

   public static Properties parse(String propsString) {
      Properties p = new Properties();
      String[] pairs = propsString.split(";");
      if (pairs != null) {
         for(int i = 0; i < pairs.length; ++i) {
            String[] keyValuePair = pairs[i].split("=");
            if (keyValuePair.length == 2) {
               String key = keyValuePair[0];
               String value = keyValuePair[1];
               p.put(key, value);
            }
         }
      }

      return p;
   }

   public static void main(String[] args) {
      System.out.println("Parsing " + args[0]);
      Properties p = parse(args[0]);
      System.out.println(p);
   }
}
