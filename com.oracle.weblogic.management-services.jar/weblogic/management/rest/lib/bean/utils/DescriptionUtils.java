package weblogic.management.rest.lib.bean.utils;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONArray;

public class DescriptionUtils {
   private static Set usedKeys = new HashSet();

   public static String description(HttpServletRequest request, String key) throws Exception {
      return description(request, "", key, keys());
   }

   public static String description(HttpServletRequest request, String scope, String defaultDescription, String[] customKeys, Object... args) throws Exception {
      return description(request, scope, defaultDescription, false, customKeys, args);
   }

   public static String description(HttpServletRequest request, String scope, String defaultDescription, boolean formatted, String[] customKeys, Object... args) throws Exception {
      return description(request, scope, new DefaultValue(defaultDescription, formatted), customKeys, args);
   }

   public static Object[] args(Object... args) throws Exception {
      return args;
   }

   public static String[] keys(String... keys) throws Exception {
      return keys;
   }

   private static String description(HttpServletRequest request, String scope, DefaultValue defaultDescription, String[] customKeys, Object... args) throws Exception {
      return description(request, scope, (DefaultValue)null, (DefaultValue)null, defaultDescription, customKeys, args);
   }

   private static String description(HttpServletRequest request, String scope, DefaultValue defaultPrefix, DefaultValue defaultSuffix, DefaultValue defaultDescription, String[] customKeys, Object... args) throws Exception {
      String textKey = "/text";
      String prefixKey = "/prefix";
      String suffixKey = "/suffix";
      if (scope != null) {
         textKey = scope + textKey;
         prefixKey = scope + prefixKey;
         suffixKey = scope + suffixKey;
      }

      String prefix = descValue(request, prefixKey, defaultPrefix, customKeys, args);
      String suffix = descValue(request, suffixKey, defaultSuffix, customKeys, args);
      String text = descValue(request, textKey, defaultDescription, customKeys, args);
      StringBuilder sb = new StringBuilder();
      if (MetaDataUtils.isDebugEnabled()) {
         sb.append("<p>Text</p>");
         sb.append(text);
         if (MetaDataUtils.isVerboseEnabled()) {
            sb.append("<p>Prefix</p>");
            sb.append(prefix);
            sb.append("<p>Suffix</p>");
            sb.append(suffix);
            sb.append("<p>Arguments</p>");
            sb.append("<ul>");

            for(int i = 0; args != null && i < args.length; ++i) {
               sb.append("<li>{" + i + "} - " + args[i] + "</li>");
            }

            sb.append("</ul>");
         }
      } else {
         if (prefix != null) {
            sb.append(prefix);
         }

         if (text != null) {
            sb.append(text);
         }

         if (suffix != null) {
            sb.append(suffix);
         }
      }

      String rtn = sb.toString();
      return rtn.length() > 0 ? rtn : null;
   }

   private static String descValue(HttpServletRequest request, String scope, DefaultValue defaultValue, String[] customKeys, Object... args) throws Exception {
      int var7;
      String pattern;
      if (MetaDataUtils.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("<ul>");
         String[] var11 = customKeys;
         var7 = customKeys.length;

         for(int var13 = 0; var13 < var7; ++var13) {
            pattern = var11[var13];
            if (scope != null) {
               pattern = pattern + scope;
            }

            sb.append("<li>" + pattern + " - " + descValue(request, pattern) + "</li>");
         }

         if (defaultValue != null) {
            if (defaultValue.formatted()) {
               sb.append("<li>default - " + defaultValue.value() + "</li>");
            } else {
               String key = defaultValue.value();
               if (key != null) {
                  sb.append("<li>" + key + " - " + descValue(request, key) + "</li>");
               }
            }
         } else {
            sb.append("<li>default - null</li>");
         }

         sb.append("</ul>");
         return sb.toString();
      } else {
         String[] var5 = customKeys;
         int var6 = customKeys.length;

         for(var7 = 0; var7 < var6; ++var7) {
            String key = var5[var7];
            if (scope != null) {
               key = key + scope;
            }

            pattern = descValue(request, key);
            if (pattern != null) {
               return format(pattern, args);
            }
         }

         if (defaultValue != null) {
            return defaultValue.format(request, args);
         } else {
            return null;
         }
      }
   }

   private static String descValue(HttpServletRequest request, String key) throws Exception {
      String val = exactVal(request, key);
      if (val != null) {
         return val;
      } else {
         String ref = exactVal(request, key + "-ref");
         return ref != null ? exactVal(request, ref) : null;
      }
   }

   public static String exactVal(HttpServletRequest request, String key) throws Exception {
      if (key == null) {
         return null;
      } else {
         String val = null;
         Iterator var3 = BeanResourceRegistry.instance().getResourceMetaDataDescriptions().iterator();

         while(var3.hasNext()) {
            ResourceMetaDataDescriptions descs = (ResourceMetaDataDescriptions)var3.next();
            ResourceBundle bundle = descs.bundle(request);
            if (bundle.containsKey(key)) {
               String v = bundle.getString(key);
               if (MetaDataUtils.isDebugEnabled()) {
                  usedKeys.add(key);
               }

               if (val != null) {
                  throw new AssertionError("MetaDataDescription key " + key + " defined in more than one resource extension.");
               }

               val = v;
            }
         }

         return val;
      }
   }

   public static JSONArray unusedKeys(HttpServletRequest request) throws Exception {
      JSONArray rtn = new JSONArray();
      if (MetaDataUtils.isDebugEnabled()) {
         Iterator var2 = BeanResourceRegistry.instance().getResourceMetaDataDescriptions().iterator();

         while(var2.hasNext()) {
            ResourceMetaDataDescriptions descs = (ResourceMetaDataDescriptions)var2.next();
            Iterator var4 = descs.bundle(request).keySet().iterator();

            while(var4.hasNext()) {
               String key = (String)var4.next();
               if (!usedKeys.contains(key)) {
                  rtn.put(key);
               }
            }
         }
      }

      return rtn;
   }

   private static String format(String pattern, Object... args) throws Exception {
      return pattern != null ? MessageFormat.format(pattern, args) : pattern;
   }

   public static class DefaultValue {
      private String value;
      private boolean formatted;

      public DefaultValue(String value, boolean formatted) {
         this.value = value;
         this.formatted = formatted;
      }

      private String value() {
         return this.value;
      }

      private boolean formatted() {
         return this.formatted;
      }

      public String format(HttpServletRequest request, Object... args) throws Exception {
         return this.formatted ? this.value : DescriptionUtils.format(DescriptionUtils.exactVal(request, this.value), args);
      }

      public String toString() {
         return "DefaultValue(" + this.value + "," + this.formatted + ")";
      }
   }
}
