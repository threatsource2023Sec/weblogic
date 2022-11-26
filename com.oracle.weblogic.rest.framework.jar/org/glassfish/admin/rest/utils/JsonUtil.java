package org.glassfish.admin.rest.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.composite.metadata.Confidential;
import org.glassfish.admin.rest.model.ResponseBody;

public class JsonUtil {
   public static final String CONFIDENTIAL_PROPERTY_SET = "@_Oracle_Confidential_Property_Set_V1.1_#";
   public static final String CONFIDENTIAL_PROPERTY_UNSET = null;

   public static Object getJsonObject(Object object) throws JSONException {
      return getJsonObject(object, true);
   }

   public static Object getJsonObject(Object object, boolean hideConfidentialProperties) throws JSONException {
      Object result;
      if (object instanceof Collection) {
         result = processCollection((Collection)object, hideConfidentialProperties);
      } else if (object instanceof Map) {
         result = processMap((Map)object, hideConfidentialProperties);
      } else if (object == null) {
         result = JSONObject.NULL;
      } else if (RestModel.class.isAssignableFrom(object.getClass())) {
         result = getJsonForRestModel((RestModel)object, hideConfidentialProperties);
      } else if (object instanceof ResponseBody) {
         result = ((ResponseBody)object).toJson();
      } else {
         Class clazz = object.getClass();
         if (clazz.isArray()) {
            JSONArray array = new JSONArray();
            int lenth = Array.getLength(object);

            for(int i = 0; i < lenth; ++i) {
               array.put(getJsonObject(Array.get(object, i)));
            }

            result = array;
         } else {
            result = object;
         }
      }

      return result;
   }

   public static JSONObject getJsonForRestModel(RestModel model, boolean hideConfidentialProperties) {
      JSONObject result = new JSONObject();
      Method[] var3 = model.getClass().getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (m.getName().startsWith("get")) {
            String propName = m.getName().substring(3);
            propName = propName.substring(0, 1).toLowerCase(Locale.getDefault()) + propName.substring(1);
            if (model.isSet(propName)) {
               try {
                  result.put(propName, getJsonObject(getRestModelProperty(model, m, hideConfidentialProperties), hideConfidentialProperties));
               } catch (Exception var9) {
               }
            }
         }
      }

      return result;
   }

   private static Object getRestModelProperty(RestModel model, Method method, boolean hideConfidentialProperties) throws Exception {
      Object object = method.invoke(model);
      if (hideConfidentialProperties && isConfidentialString(model, method)) {
         String str = (String)object;
         return StringUtil.notEmpty(str) ? "@_Oracle_Confidential_Property_Set_V1.1_#" : CONFIDENTIAL_PROPERTY_UNSET;
      } else {
         return object;
      }
   }

   private static boolean isConfidentialString(RestModel model, Method method) {
      return !String.class.equals(method.getReturnType()) ? false : isConfidentialProperty(model.getClass(), method.getName());
   }

   public static boolean isConfidentialProperty(Class clazz, String getterMethodName) {
      if (getConfidentialAnnotation(clazz, getterMethodName) != null) {
         return true;
      } else {
         Class[] var2 = clazz.getInterfaces();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class iface = var2[var4];
            if (getConfidentialAnnotation(iface, getterMethodName) != null) {
               return true;
            }
         }

         return false;
      }
   }

   private static Confidential getConfidentialAnnotation(Class clazz, String getterMethodName) {
      try {
         Method m = clazz.getDeclaredMethod(getterMethodName);
         return (Confidential)m.getAnnotation(Confidential.class);
      } catch (Exception var3) {
         return null;
      }
   }

   public static JSONArray processCollection(Collection c) throws JSONException {
      return processCollection(c, true);
   }

   public static JSONArray processCollection(Collection c, boolean hideConfidentialProperties) throws JSONException {
      JSONArray result = new JSONArray();
      Iterator i = c.iterator();

      while(i.hasNext()) {
         Object item = getJsonObject(i.next(), hideConfidentialProperties);
         result.put(item);
      }

      return result;
   }

   public static JSONObject processMap(Map map) throws JSONException {
      return processMap(map, true);
   }

   public static JSONObject processMap(Map map, boolean hideConfidentialProperties) throws JSONException {
      JSONObject result = new JSONObject();
      Iterator var3 = map.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         result.put(entry.getKey().toString(), getJsonObject(entry.getValue(), hideConfidentialProperties));
      }

      return result;
   }

   public static String getString(JSONObject jsonObject, String key) throws JSONException {
      return jsonObject.has(key) && !jsonObject.isNull(key) ? jsonObject.getString(key) : null;
   }

   public static String getString(JSONObject jsonObject, String key, String dflt) {
      try {
         return jsonObject.isNull(key) ? null : jsonObject.getString(key);
      } catch (JSONException var4) {
         return dflt;
      }
   }

   public static int getInt(JSONObject jsonObject, String key, int dflt) {
      try {
         return jsonObject.getInt(key);
      } catch (JSONException var4) {
         return dflt;
      }
   }

   public static void put(JSONObject jsonObject, String key, Object value) {
      try {
         synchronized(jsonObject) {
            jsonObject.put(key, value != null ? value : JSONObject.NULL);
         }
      } catch (JSONException var6) {
      }

   }

   public static void put(JSONArray jsonArray, JSONObject item) {
      synchronized(jsonArray) {
         jsonArray.put(item);
      }
   }

   public static void put(JSONObject jsonObject, JsonFilter.Scope filter, String key, Object value) throws Exception {
      if (filter.include(key)) {
         jsonObject.put(key, value != null ? value : JSONObject.NULL);
      }

   }

   public static boolean optBoolean(JSONObject j, String field) throws JSONException {
      return optBoolean(j, field, false);
   }

   public static boolean optBoolean(JSONObject j, String field, boolean defaultValue) throws JSONException {
      return j.has(field) ? j.optBoolean(field, defaultValue) : defaultValue;
   }

   public static double optDouble(JSONObject j, String field) throws JSONException {
      return optDouble(j, field, 0.0);
   }

   public static double optDouble(JSONObject j, String field, double defaultValue) throws JSONException {
      return j.has(field) ? j.optDouble(field, defaultValue) : defaultValue;
   }

   public static long optLong(JSONObject j, String field) throws JSONException {
      return optLong(j, field, 0L);
   }

   public static long optLong(JSONObject j, String field, long defaultValue) throws JSONException {
      return j.has(field) ? j.optLong(field, defaultValue) : defaultValue;
   }

   public static int optInt(JSONObject j, String field) throws JSONException {
      return optInt(j, field, 0);
   }

   public static int optInt(JSONObject j, String field, int defaultValue) throws JSONException {
      return j.has(field) ? j.optInt(field, defaultValue) : defaultValue;
   }

   public static JSONObject cloneJSONObject(JSONObject j1) throws JSONException {
      JSONObject j2 = new JSONObject();
      Iterator i = j1.keys();

      while(i.hasNext()) {
         String key = (String)i.next();
         j2.put(key, cloneJSONValue(j1.get(key)));
      }

      return j2;
   }

   public static JSONArray cloneJSONArray(JSONArray a1) throws JSONException {
      JSONArray a2 = new JSONArray();

      for(int i = 0; i < a1.length(); ++i) {
         a2.put(cloneJSONValue(a1.get(i)));
      }

      return a2;
   }

   private static Object cloneJSONValue(Object value) throws JSONException {
      if (value instanceof JSONObject) {
         return cloneJSONObject((JSONObject)value);
      } else {
         return value instanceof JSONArray ? cloneJSONArray((JSONArray)value) : value;
      }
   }
}
