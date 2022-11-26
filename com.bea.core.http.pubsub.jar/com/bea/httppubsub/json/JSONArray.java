package com.bea.httppubsub.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JSONArray {
   private ArrayList myArrayList;

   public JSONArray() {
      this.myArrayList = new ArrayList();
   }

   public JSONArray(JSONTokener x) throws JSONException {
      this();
      if (x.nextClean() != '[') {
         throw x.syntaxError("A JSONArray text must start with '['");
      } else if (x.nextClean() != ']') {
         x.back();

         while(true) {
            if (x.nextClean() == ',') {
               x.back();
               this.myArrayList.add((Object)null);
            } else {
               x.back();
               this.myArrayList.add(x.nextValue());
            }

            switch (x.nextClean()) {
               case ',':
               case ';':
                  if (x.nextClean() == ']') {
                     return;
                  }

                  x.back();
                  break;
               case ']':
                  return;
               default:
                  throw x.syntaxError("Expected a ',' or ']'");
            }
         }
      }
   }

   public JSONArray(String string) throws JSONException {
      this(new JSONTokener(string));
   }

   public JSONArray(Collection collection) {
      this.myArrayList = collection == null ? new ArrayList() : new ArrayList(collection);
   }

   public Object get(int index) throws JSONException {
      Object o = this.opt(index);
      if (o == null) {
         throw new JSONException("JSONArray[" + index + "] not found.");
      } else {
         return o;
      }
   }

   public boolean getBoolean(int index) throws JSONException {
      Object o = this.get(index);
      if (!o.equals(Boolean.FALSE) && (!(o instanceof String) || !((String)o).equalsIgnoreCase("false"))) {
         if (!o.equals(Boolean.TRUE) && (!(o instanceof String) || !((String)o).equalsIgnoreCase("true"))) {
            throw new JSONException("JSONArray[" + index + "] is not a Boolean.");
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public double getDouble(int index) throws JSONException {
      Object o = this.get(index);

      try {
         return o instanceof Number ? ((Number)o).doubleValue() : Double.valueOf((String)o);
      } catch (Exception var4) {
         throw new JSONException("JSONArray[" + index + "] is not a number.");
      }
   }

   public int getInt(int index) throws JSONException {
      Object o = this.get(index);
      return o instanceof Number ? ((Number)o).intValue() : (int)this.getDouble(index);
   }

   public JSONArray getJSONArray(int index) throws JSONException {
      Object o = this.get(index);
      if (o instanceof JSONArray) {
         return (JSONArray)o;
      } else {
         throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
      }
   }

   public JSONObject getJSONObject(int index) throws JSONException {
      Object o = this.get(index);
      if (o instanceof JSONObject) {
         return (JSONObject)o;
      } else {
         throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
      }
   }

   public long getLong(int index) throws JSONException {
      Object o = this.get(index);
      return o instanceof Number ? ((Number)o).longValue() : (long)this.getDouble(index);
   }

   public String getString(int index) throws JSONException {
      return this.get(index).toString();
   }

   public boolean isNull(int index) {
      return JSONObject.NULL.equals(this.opt(index));
   }

   public String join(String separator) throws JSONException {
      int len = this.length();
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < len; ++i) {
         if (i > 0) {
            sb.append(separator);
         }

         sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
      }

      return sb.toString();
   }

   public int length() {
      return this.myArrayList.size();
   }

   public Object opt(int index) {
      return index >= 0 && index < this.length() ? this.myArrayList.get(index) : null;
   }

   public boolean optBoolean(int index) {
      return this.optBoolean(index, false);
   }

   public boolean optBoolean(int index, boolean defaultValue) {
      try {
         return this.getBoolean(index);
      } catch (Exception var4) {
         return defaultValue;
      }
   }

   public double optDouble(int index) {
      return this.optDouble(index, Double.NaN);
   }

   public double optDouble(int index, double defaultValue) {
      try {
         return this.getDouble(index);
      } catch (Exception var5) {
         return defaultValue;
      }
   }

   public int optInt(int index) {
      return this.optInt(index, 0);
   }

   public int optInt(int index, int defaultValue) {
      try {
         return this.getInt(index);
      } catch (Exception var4) {
         return defaultValue;
      }
   }

   public JSONArray optJSONArray(int index) {
      Object o = this.opt(index);
      return o instanceof JSONArray ? (JSONArray)o : null;
   }

   public JSONObject optJSONObject(int index) {
      Object o = this.opt(index);
      return o instanceof JSONObject ? (JSONObject)o : null;
   }

   public long optLong(int index) {
      return this.optLong(index, 0L);
   }

   public long optLong(int index, long defaultValue) {
      try {
         return this.getLong(index);
      } catch (Exception var5) {
         return defaultValue;
      }
   }

   public String optString(int index) {
      return this.optString(index, "");
   }

   public String optString(int index, String defaultValue) {
      Object o = this.opt(index);
      return o != null ? o.toString() : defaultValue;
   }

   public JSONArray put(boolean value) {
      this.put((Object)(value ? Boolean.TRUE : Boolean.FALSE));
      return this;
   }

   public JSONArray put(Collection value) {
      this.put((Object)(new JSONArray(value)));
      return this;
   }

   public JSONArray put(double value) throws JSONException {
      Double d = new Double(value);
      JSONObject.testValidity(d);
      this.put((Object)d);
      return this;
   }

   public JSONArray put(int value) {
      this.put((Object)(new Integer(value)));
      return this;
   }

   public JSONArray put(long value) {
      this.put((Object)(new Long(value)));
      return this;
   }

   public JSONArray put(Map value) {
      this.put((Object)(new JSONObject(value)));
      return this;
   }

   public JSONArray put(Object value) {
      this.myArrayList.add(value);
      return this;
   }

   public JSONArray put(int index, boolean value) throws JSONException {
      this.put(index, (Object)(value ? Boolean.TRUE : Boolean.FALSE));
      return this;
   }

   public JSONArray put(int index, Collection value) throws JSONException {
      this.put(index, (Object)(new JSONArray(value)));
      return this;
   }

   public JSONArray put(int index, double value) throws JSONException {
      this.put(index, (Object)(new Double(value)));
      return this;
   }

   public JSONArray put(int index, int value) throws JSONException {
      this.put(index, (Object)(new Integer(value)));
      return this;
   }

   public JSONArray put(int index, long value) throws JSONException {
      this.put(index, (Object)(new Long(value)));
      return this;
   }

   public JSONArray put(int index, Map value) throws JSONException {
      this.put(index, (Object)(new JSONObject(value)));
      return this;
   }

   public JSONArray put(int index, Object value) throws JSONException {
      JSONObject.testValidity(value);
      if (index < 0) {
         throw new JSONException("JSONArray[" + index + "] not found.");
      } else {
         if (index < this.length()) {
            this.myArrayList.set(index, value);
         } else {
            while(index != this.length()) {
               this.put(JSONObject.NULL);
            }

            this.put(value);
         }

         return this;
      }
   }

   public JSONObject toJSONObject(JSONArray names) throws JSONException {
      if (names != null && names.length() != 0 && this.length() != 0) {
         JSONObject jo = new JSONObject();

         for(int i = 0; i < names.length(); ++i) {
            jo.put(names.getString(i), this.opt(i));
         }

         return jo;
      } else {
         return null;
      }
   }

   public String toString() {
      try {
         return '[' + this.join(",") + ']';
      } catch (Exception var2) {
         return null;
      }
   }

   public String toString(int indentFactor) throws JSONException {
      return this.toString(indentFactor, 0);
   }

   String toString(int indentFactor, int indent) throws JSONException {
      int len = this.length();
      if (len == 0) {
         return "[]";
      } else {
         StringBuffer sb = new StringBuffer("[");
         if (len == 1) {
            sb.append(JSONObject.valueToString(this.myArrayList.get(0), indentFactor, indent));
         } else {
            int newindent = indent + indentFactor;
            sb.append('\n');

            int i;
            for(i = 0; i < len; ++i) {
               if (i > 0) {
                  sb.append(",\n");
               }

               for(int j = 0; j < newindent; ++j) {
                  sb.append(' ');
               }

               sb.append(JSONObject.valueToString(this.myArrayList.get(i), indentFactor, newindent));
            }

            sb.append('\n');

            for(i = 0; i < indent; ++i) {
               sb.append(' ');
            }
         }

         sb.append(']');
         return sb.toString();
      }
   }

   public Writer write(Writer writer) throws JSONException {
      try {
         boolean b = false;
         int len = this.length();
         writer.write(91);

         for(int i = 0; i < len; ++i) {
            if (b) {
               writer.write(44);
            }

            Object v = this.myArrayList.get(i);
            if (v instanceof JSONObject) {
               ((JSONObject)v).write(writer);
            } else if (v instanceof JSONArray) {
               ((JSONArray)v).write(writer);
            } else {
               writer.write(JSONObject.valueToString(v));
            }

            b = true;
         }

         writer.write(93);
         return writer;
      } catch (IOException var6) {
         throw new JSONException(var6);
      }
   }
}
