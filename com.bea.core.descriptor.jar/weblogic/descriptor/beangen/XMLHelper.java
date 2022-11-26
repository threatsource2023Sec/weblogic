package weblogic.descriptor.beangen;

import java.util.StringTokenizer;

public class XMLHelper {
   public static String toElementName(String propName) {
      return toXMLName(propName);
   }

   public static final String toTypeName(String className) {
      String base = className;
      int idx;
      if (className.endsWith("MBean")) {
         idx = className.lastIndexOf("MBean");
         base = className.substring(0, idx);
      } else if (className.endsWith("Bean")) {
         idx = className.lastIndexOf("Bean");
         base = className.substring(0, idx);
      }

      return toXMLName(base) + "Type";
   }

   private static String toXMLName(String beanName) {
      StringBuffer buf = new StringBuffer();
      buf.append(Character.toLowerCase(beanName.charAt(0)));

      for(int i = 1; i < beanName.length() - 1; ++i) {
         char cur = beanName.charAt(i);
         if (Character.isUpperCase(cur)) {
            char prev = beanName.charAt(i - 1);
            char next = beanName.charAt(i + 1);
            if (Character.isLowerCase(prev) || Character.isLowerCase(next)) {
               buf.append('-');
            }
         }

         buf.append(Character.toLowerCase(cur));
      }

      buf.append(Character.toLowerCase(beanName.charAt(beanName.length() - 1)));
      return buf.toString();
   }

   public static String toPropName(String elementName) {
      StringTokenizer st = new StringTokenizer(elementName, "-");
      StringBuffer sb = new StringBuffer();

      while(st.hasMoreTokens()) {
         String s = st.nextToken();
         char c = Character.toUpperCase(s.charAt(0));
         sb.append(c);
         sb.append(s.substring(1));
      }

      return sb.toString();
   }
}
