package weblogic.management.jmx;

import java.util.Properties;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import weblogic.utils.StringUtils;

public class CompositeTypeProperties {
   public static final CompositeType PROPERTIES;
   public static final String TYPE_NAME = CompositeType.class.getName() + ".PROPERTIES";
   public static final String PROPERTIES_VALUE = "PROPERTYSTRING";

   public static final CompositeData newPropertiesInstance(Properties value) throws OpenDataException {
      return value == null ? null : new CompositeDataSupport(PROPERTIES, new String[]{"PROPERTYSTRING"}, new Object[]{value.toString()});
   }

   public static final Properties reconstitute(CompositeData composite) {
      String prop = (String)composite.get("PROPERTYSTRING");
      return stringToProperties(prop);
   }

   private static Properties stringToProperties(String o) {
      if (o == null) {
         return null;
      } else {
         Properties props = new Properties();
         if (o.length() < 2) {
            return props;
         } else {
            o = o.substring(1, o.length() - 1);
            String[] list = o.split("[,]");

            for(int i = 0; i < list.length; ++i) {
               String[] pair = StringUtils.split(list[i], '=');
               props.setProperty(pair[0], pair[1]);
            }

            return props;
         }
      }
   }

   static {
      try {
         PROPERTIES = new CompositeType(TYPE_NAME, "Represents a value for java.utils.Properties", new String[]{"PROPERTYSTRING"}, new String[]{"PROPERIES STRING VALUE"}, new OpenType[]{SimpleType.STRING});
      } catch (OpenDataException var1) {
         throw new RuntimeException(var1);
      }
   }
}
