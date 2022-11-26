package weblogic.management.rest.lib.bean.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import org.glassfish.admin.rest.utils.JsonFilter;
import org.glassfish.admin.rest.utils.JsonUtil;
import org.glassfish.admin.rest.utils.StringUtil;

public class QueryUtils {
   private static JSONObject standardChildrenQuery = new JSONObject();
   private static JSONArray standardChildrenLinks = new JSONArray();

   public static List getIdentities(HttpServletRequest request, String identityPropertyName, JSONObject query) throws Exception {
      return getStringArray(request, query, identityPropertyName);
   }

   public static JSONObject getIncludeExcludeQuery(String includeFields, String excludeFields, String includeLinks, String excludeLinks) throws Exception {
      if (includeFields == null && excludeFields == null && includeLinks == null && excludeLinks == null) {
         return null;
      } else {
         JSONObject query = new JSONObject();
         if (includeFields != null) {
            query.put("fields", commaSeparatedStringListToJSONArray(includeFields));
         }

         if (excludeFields != null) {
            query.put("excludeFields", commaSeparatedStringListToJSONArray(excludeFields));
         }

         if (includeLinks != null) {
            query.put("links", commaSeparatedStringListToJSONArray(includeLinks));
         }

         if (excludeLinks != null) {
            query.put("excludeLinks", commaSeparatedStringListToJSONArray(excludeLinks));
         }

         return query;
      }
   }

   public static JSONObject getAllPropertiesQuery(JSONObject originalQuery) throws Exception {
      JSONObject allPropertiesQuery = null;
      if (originalQuery != null && (originalQuery.has("links") || originalQuery.has("excludeLinks"))) {
         allPropertiesQuery = JsonUtil.cloneJSONObject(originalQuery);
         allPropertiesQuery.remove("fields");
         allPropertiesQuery.remove("excludeFields");
      }

      return allPropertiesQuery;
   }

   public static JsonFilter getPropertiesFilter(HttpServletRequest request, JSONObject query) throws Exception {
      return getFilter(request, query, "fields", "excludeFields");
   }

   public static JsonFilter getLinksFilter(HttpServletRequest request, JSONObject query) throws Exception {
      return getFilter(request, query, "links", "excludeLinks");
   }

   public static JSONObject getChildrenQuery(JSONObject collectionQuery) throws Exception {
      if (collectionQuery == null) {
         return standardChildrenQuery;
      } else if (collectionQuery.optString("links", (String)null) == null && collectionQuery.optString("excludeLinks", (String)null) == null) {
         JSONObject childrenQuery = JsonUtil.cloneJSONObject(collectionQuery);
         childrenQuery.put("links", standardChildrenLinks);
         return childrenQuery;
      } else {
         return collectionQuery;
      }
   }

   private static JsonFilter getFilter(HttpServletRequest request, JSONObject query, String includePropertyName, String excludePropertyName) throws Exception {
      return new JsonFilter(request.getLocale(), getNames(request, query, includePropertyName), getNames(request, query, excludePropertyName), (String)null, includePropertyName, excludePropertyName);
   }

   public static Map getChildren(HttpServletRequest request, JSONObject query) throws Exception {
      String property = "children";
      if (query != null && query.has(property)) {
         JSONObject jo = null;

         try {
            jo = query.getJSONObject(property);
         } catch (Exception var9) {
            ExceptionUtil.badRequest(MessageUtils.beanFormatter(request).msgInvalidQueryPropertyNotAnObject(property));
            return null;
         }

         Map vals = new HashMap();
         Iterator i = jo.keys();

         while(i.hasNext()) {
            String childName = (String)i.next();

            try {
               vals.put(childName, jo.getJSONObject(childName));
            } catch (Exception var8) {
               ExceptionUtil.badRequest(MessageUtils.beanFormatter(request).msgInvalidQueryPropertyContainsNonObjects(property));
               return null;
            }
         }

         return vals;
      } else {
         return null;
      }
   }

   private static String getNames(HttpServletRequest request, JSONObject query, String property) throws Exception {
      List names = getStringArray(request, query, property);
      if (names == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < names.size(); ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append((String)names.get(i));
         }

         return sb.toString();
      }
   }

   private static List getStringArray(HttpServletRequest request, JSONObject query, String property) throws Exception {
      if (query != null && property != null && query.has(property)) {
         JSONArray ja = null;

         try {
            ja = query.getJSONArray(property);
         } catch (Exception var8) {
            ExceptionUtil.badRequest(MessageUtils.beanFormatter(request).msgInvalidQueryPropertyNotAnArray(property));
            return null;
         }

         List vals = new ArrayList();

         for(int i = 0; i < ja.length(); ++i) {
            try {
               vals.add(ja.getString(i));
            } catch (Exception var7) {
               ExceptionUtil.badRequest(MessageUtils.beanFormatter(request).msgInvalidQueryPropertyNotAStringArray(property));
               return null;
            }
         }

         return vals;
      } else {
         return null;
      }
   }

   private static JSONArray commaSeparatedStringListToJSONArray(String stringList) throws Exception {
      JSONArray a = new JSONArray();
      Iterator var2 = StringUtil.parseCommaSeparatedStringList(stringList).iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         a.put(s);
      }

      return a;
   }

   static {
      standardChildrenLinks.put("self");
      standardChildrenLinks.put("canonical");

      try {
         standardChildrenQuery.put("links", standardChildrenLinks);
      } catch (Throwable var1) {
      }

   }
}
