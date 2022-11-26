package weblogic.management.rest.lib.bean.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class RequestBodyHelper {
   private Set confidentialPropertyNamePatterns = new HashSet();
   private Pattern pattern;

   public boolean isConfidentialProperty(String property) throws Exception {
      return this.getPattern().matcher(property).matches();
   }

   public void registerConfidentialPropertyNamePattern(String p) {
      if (this.pattern != null) {
         throw new AssertionError("Trying to register a confidential property name pattern after the pattern has been compiled");
      } else {
         this.confidentialPropertyNamePatterns.add(p);
      }
   }

   public JSONObject hideConfidentialProperties(JSONObject raw) throws Exception {
      JSONObject sanitized = new JSONObject();

      String k;
      Object v;
      for(Iterator iter = raw.keys(); iter.hasNext(); sanitized.put(k, v)) {
         k = (String)iter.next();
         v = raw.get(k);
         if (v instanceof String) {
            if (this.isConfidentialProperty(k)) {
               v = "%1arbitraryvalue1%";
            }
         } else if (v instanceof JSONObject) {
            v = this.hideConfidentialProperties((JSONObject)v);
         } else if (v instanceof JSONArray) {
            v = this.hideConfidentialProperties((JSONArray)v);
         }
      }

      return sanitized;
   }

   public Set getConfidentialPropertyNamePatterns() {
      return this.confidentialPropertyNamePatterns;
   }

   private JSONArray hideConfidentialProperties(JSONArray raw) throws Exception {
      JSONArray sanitized = new JSONArray();

      for(int i = 0; i < raw.length(); ++i) {
         Object o = raw.get(i);
         if (o instanceof JSONObject) {
            o = this.hideConfidentialProperties((JSONObject)o);
         }

         sanitized.put(o);
      }

      return sanitized;
   }

   private Pattern getPattern() throws Exception {
      if (this.pattern == null) {
         StringBuilder sb = new StringBuilder();
         boolean first = false;
         Iterator var3 = this.getConfidentialPropertyNamePatterns().iterator();

         while(var3.hasNext()) {
            String p = (String)var3.next();
            if (!first) {
               sb.append("|");
            } else {
               first = false;
            }

            sb.append(".*");
            sb.append(p);
            sb.append(".*");
         }

         String expression = sb.toString();
         this.pattern = Pattern.compile(expression, 2);
      }

      return this.pattern;
   }
}
