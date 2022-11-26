package weblogic.diagnostics.snmp.agent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SNMPUtil implements SNMPConstants {
   private static final boolean DEBUG = false;
   private static List subSystemPrefixes = new ArrayList();
   private static final String MBEAN_SUFFIX = "MBean";

   public static void initializeTypeNamePrefixes(String[] prefixes) {
      if (prefixes != null) {
         for(int i = 0; i < prefixes.length; ++i) {
            subSystemPrefixes.add(prefixes[i]);
         }
      }

   }

   public static String convertTypeNameToSNMPTableName(String typeName) {
      String tableName = typeName.replace("$", "");
      int index = tableName.lastIndexOf(".");
      if (index >= 0) {
         tableName = tableName.substring(index + 1);
      }

      if (tableName.endsWith("MBean")) {
         index = tableName.lastIndexOf("MBean");
         if (index >= 0) {
            tableName = tableName.substring(0, index);
         }
      }

      Iterator i = subSystemPrefixes.iterator();

      String prefix;
      do {
         if (!i.hasNext()) {
            tableName = makeFirstCharLowerCase(tableName) + "Table";
            return tableName;
         }

         prefix = (String)i.next();
      } while(!tableName.toLowerCase().startsWith(prefix) || tableName.length() < prefix.length());

      tableName = prefix + tableName.substring(prefix.length()) + "Table";
      if (tableName.startsWith("snmp")) {
         tableName = "wls" + tableName;
      }

      return tableName;
   }

   public static String getColumnNameForAttribute(String table, String attr) {
      int index = table.lastIndexOf("Table");
      if (index > 0) {
         table = table.substring(0, index);
      }

      return table + attr;
   }

   private static String makeFirstCharLowerCase(String s) {
      if (s.length() == 0) {
         throw new IllegalArgumentException("Empty string");
      } else {
         char c = s.charAt(0);
         String begin = "" + Character.toLowerCase(c);
         String remainder = "";
         if (s.length() > 1) {
            remainder = s.substring(1);
         }

         return begin + remainder;
      }
   }

   public static String stripHtmlTags(String s) {
      if (s == null) {
         return "";
      } else {
         s = s.replaceAll("<.*?>", "");
         return s.replace('"', '\'');
      }
   }

   public static void main(String[] args) {
      System.out.println(convertTypeNameToSNMPTableName("weblogic.management.runtime.WseeRuntimeMBean"));
   }
}
