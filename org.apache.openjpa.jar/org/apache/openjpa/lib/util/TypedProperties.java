package org.apache.openjpa.lib.util;

import java.util.Properties;

public class TypedProperties extends Properties {
   public TypedProperties() {
   }

   public TypedProperties(Properties defaults) {
      super(defaults);
   }

   public boolean getBooleanProperty(String key) {
      return this.getBooleanProperty(key, false);
   }

   public boolean getBooleanProperty(String key, boolean def) {
      String val = this.getProperty(key);
      if (val == null) {
         return def;
      } else {
         return "t".equalsIgnoreCase(val) || "true".equalsIgnoreCase(val);
      }
   }

   public float getFloatProperty(String key) {
      return this.getFloatProperty(key, 0.0F);
   }

   public float getFloatProperty(String key, float def) {
      String val = this.getProperty(key);
      return val == null ? def : Float.parseFloat(val);
   }

   public double getDoubleProperty(String key) {
      return this.getDoubleProperty(key, 0.0);
   }

   public double getDoubleProperty(String key, double def) {
      String val = this.getProperty(key);
      return val == null ? def : Double.parseDouble(val);
   }

   public long getLongProperty(String key) {
      return this.getLongProperty(key, 0L);
   }

   public long getLongProperty(String key, long def) {
      String val = this.getProperty(key);
      return val == null ? def : Long.parseLong(val);
   }

   public int getIntProperty(String key) {
      return this.getIntProperty(key, 0);
   }

   public int getIntProperty(String key, int def) {
      String val = this.getProperty(key);
      return val == null ? def : Integer.parseInt(val);
   }

   public Object setProperty(String key, String val) {
      return val == null ? this.remove(key) : super.setProperty(key, val);
   }

   public void setProperty(String key, boolean val) {
      this.setProperty(key, String.valueOf(val));
   }

   public void setProperty(String key, double val) {
      this.setProperty(key, String.valueOf(val));
   }

   public void setProperty(String key, float val) {
      this.setProperty(key, String.valueOf(val));
   }

   public void setProperty(String key, int val) {
      this.setProperty(key, String.valueOf(val));
   }

   public void setProperty(String key, long val) {
      this.setProperty(key, String.valueOf(val));
   }

   public String removeProperty(String key) {
      Object val = this.remove(key);
      return val == null ? null : val.toString();
   }

   public String removeProperty(String key, String def) {
      return !this.containsKey(key) ? def : this.removeProperty(key);
   }

   public boolean removeBooleanProperty(String key) {
      String val = this.removeProperty(key);
      return "t".equalsIgnoreCase(val) || "true".equalsIgnoreCase(val);
   }

   public boolean removeBooleanProperty(String key, boolean def) {
      return !this.containsKey(key) ? def : this.removeBooleanProperty(key);
   }

   public double removeDoubleProperty(String key) {
      String val = this.removeProperty(key);
      return val == null ? 0.0 : Double.parseDouble(val);
   }

   public double removeDoubleProperty(String key, double def) {
      return !this.containsKey(key) ? def : this.removeDoubleProperty(key);
   }

   public float removeFloatProperty(String key) {
      String val = this.removeProperty(key);
      return val == null ? 0.0F : Float.parseFloat(val);
   }

   public float removeFloatProperty(String key, float def) {
      return !this.containsKey(key) ? def : this.removeFloatProperty(key);
   }

   public int removeIntProperty(String key) {
      String val = this.removeProperty(key);
      return val == null ? 0 : Integer.parseInt(val);
   }

   public int removeIntProperty(String key, int def) {
      return !this.containsKey(key) ? def : this.removeIntProperty(key);
   }

   public long removeLongProperty(String key) {
      String val = this.removeProperty(key);
      return val == null ? 0L : Long.parseLong(val);
   }

   public long removeLongProperty(String key, long def) {
      return !this.containsKey(key) ? def : this.removeLongProperty(key);
   }
}
