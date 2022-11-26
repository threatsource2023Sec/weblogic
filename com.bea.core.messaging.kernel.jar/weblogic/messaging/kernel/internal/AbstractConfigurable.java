package weblogic.messaging.kernel.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.messaging.kernel.Configurable;
import weblogic.messaging.kernel.KernelException;

public abstract class AbstractConfigurable implements Configurable {
   private HashMap properties = new HashMap();
   protected String name;

   protected AbstractConfigurable(String name) {
      this.properties.put("Name", this.name = name);
   }

   protected AbstractConfigurable() {
   }

   public String getName() {
      return (String)this.getProperty("Name");
   }

   public void setProperties(Map properties) throws KernelException {
      Iterator iterator = properties.entrySet().iterator();

      while(iterator.hasNext()) {
         Map.Entry entry = (Map.Entry)iterator.next();
         this.setProperty((String)entry.getKey(), entry.getValue());
      }

   }

   public Map getProperties() {
      return (Map)this.properties.clone();
   }

   public Object getProperty(String name) {
      return this.properties.get(name);
   }

   public int getIntProperty(String name, int defaultValue) {
      Object value = this.properties.get(name);
      if (value == null) {
         return defaultValue;
      } else {
         try {
            return (Integer)value;
         } catch (ClassCastException var5) {
            return defaultValue;
         }
      }
   }

   public long getLongProperty(String name, long defaultValue) {
      Object value = this.properties.get(name);
      if (value == null) {
         return defaultValue;
      } else {
         try {
            return (Long)value;
         } catch (ClassCastException var6) {
            return defaultValue;
         }
      }
   }

   public boolean getBooleanProperty(String name, boolean defaultValue) {
      Object value = this.properties.get(name);
      if (value == null) {
         return defaultValue;
      } else {
         try {
            return (Boolean)value;
         } catch (ClassCastException var5) {
            return defaultValue;
         }
      }
   }

   public String getStringProperty(String name, String defaultValue) {
      Object value = this.properties.get(name);
      if (value == null) {
         return defaultValue;
      } else {
         try {
            return (String)value;
         } catch (ClassCastException var5) {
            return defaultValue;
         }
      }
   }

   public void setProperty(String name, Object value) throws KernelException {
      if (name.equals("Name")) {
         this.name = (String)value;
      }

      this.properties.put(name, value);
   }
}
