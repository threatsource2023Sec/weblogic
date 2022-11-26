package weblogic.descriptor.beangen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.utils.StringUtils;

public class StringHelper {
   public static Properties stringToProperties(String o) {
      if (o != null && o.length() != 0) {
         String[] list = o.split("[;]");
         Properties props = new Properties();

         for(int i = 0; i < list.length; ++i) {
            String[] pair = StringUtils.split(list[i], '=');
            props.setProperty(pair[0], pair[1]);
         }

         return props;
      } else {
         return null;
      }
   }

   public static Hashtable stringToHashtable(String o) {
      if (o == null) {
         return null;
      } else {
         String[] list = o.split("[;]");
         Hashtable h = new Hashtable();

         for(int i = 0; i < list.length; ++i) {
            String[] pair = StringUtils.split(list[i], '=');
            h.put(pair[0], pair[1]);
         }

         return h;
      }
   }

   public static Map stringToMap(String o) {
      if (o == null) {
         return new HashMap();
      } else {
         String[] list = o.split("[;]");
         HashMap h = new HashMap();

         for(int i = 0; i < list.length; ++i) {
            String[] pair = StringUtils.split(list[i], '=');
            h.put(pair[0], pair[1]);
         }

         return h;
      }
   }

   public static String mapToString(Map map) {
      StringBuffer sb = new StringBuffer();
      Iterator it = map.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         sb.append(entry.getKey() + "=" + entry.getValue());
         if (it.hasNext()) {
            sb.append(";");
         }
      }

      return sb.toString();
   }

   public static HashSet stringToHashSet(String o) {
      if (o == null) {
         return null;
      } else {
         String[] list = o.split(",");
         HashSet h = new HashSet();

         for(int i = 0; i < list.length; ++i) {
            h.add(list[i]);
         }

         return h;
      }
   }

   public static String setToString(Set set) {
      StringBuffer sb = new StringBuffer();
      Iterator it = set.iterator();

      while(it.hasNext()) {
         Object entry = it.next();
         sb.append(entry);
         if (it.hasNext()) {
            sb.append(",");
         }
      }

      return sb.toString();
   }

   public static String objectToString(Object o) {
      if (o == null) {
         return null;
      } else if (o instanceof Map) {
         return mapToString((Map)o);
      } else if (o instanceof Set) {
         return setToString((Set)o);
      } else {
         return o.getClass().isArray() ? Arrays.asList((Object[])((Object[])o)).toString() : o.toString();
      }
   }

   public static String[] split(String s) {
      return s.split(",");
   }

   public static String[] split(String[] s) {
      return s;
   }

   public static String replaceExpression(String expr, String variable, String replacement) {
      return expr.replaceAll("([^\\\\])" + variable + "|^" + variable, "$1" + replacement).replaceAll("\\\\" + variable, variable);
   }
}
