package org.apache.velocity.app;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class FieldMethodizer {
   private HashMap fieldHash = new HashMap();
   private HashMap classHash = new HashMap();

   public FieldMethodizer() {
   }

   public FieldMethodizer(String s) {
      try {
         this.addObject(s);
      } catch (Exception var3) {
         System.out.println(var3);
      }

   }

   public FieldMethodizer(Object o) {
      try {
         this.addObject(o);
      } catch (Exception var3) {
         System.out.println(var3);
      }

   }

   public void addObject(String s) throws Exception {
      this.inspect(Class.forName(s));
   }

   public void addObject(Object o) throws Exception {
      this.inspect(o.getClass());
   }

   public Object get(String fieldName) {
      try {
         Field f = (Field)this.fieldHash.get(fieldName);
         if (f != null) {
            return f.get((Class)this.classHash.get(fieldName));
         }
      } catch (Exception var3) {
      }

      return null;
   }

   private void inspect(Class clas) {
      Field[] fields = clas.getFields();

      for(int i = 0; i < fields.length; ++i) {
         int mod = fields[i].getModifiers();
         if (Modifier.isStatic(mod) && Modifier.isPublic(mod)) {
            this.fieldHash.put(fields[i].getName(), fields[i]);
            this.classHash.put(fields[i].getName(), clas);
         }
      }

   }
}
