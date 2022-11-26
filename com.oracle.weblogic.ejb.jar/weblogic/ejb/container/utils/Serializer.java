package weblogic.ejb.container.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class Serializer {
   private final Class clazz;
   private final List fieldsCache;
   private static final boolean IS_SECURITY_MANAGER_ENABLED = System.getSecurityManager() != null;

   public Serializer(Class clazz) {
      this.clazz = clazz;
      this.fieldsCache = this.getSerializableFields(clazz);
   }

   private List getSerializableFields(Class toSerialize) {
      List fieldsList = new LinkedList();

      for(Class c = toSerialize; c != Object.class; c = c.getSuperclass()) {
         Field[] fields = this.getDeclaredFields(c);
         Arrays.sort(fields, new Comparator() {
            public int compare(Field f1, Field f2) {
               return f1.getName().compareTo(f2.getName());
            }
         });
         Field[] var5 = fields;
         int var6 = fields.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Field f = var5[var7];
            if (!Modifier.isTransient(f.getModifiers())) {
               fieldsList.add(f);
            }
         }
      }

      this.setAccessible(fieldsList);
      return Collections.unmodifiableList(fieldsList);
   }

   private Field[] getDeclaredFields(final Class c) {
      return !IS_SECURITY_MANAGER_ENABLED ? c.getDeclaredFields() : (Field[])AccessController.doPrivileged(new PrivilegedAction() {
         public Field[] run() {
            return c.getDeclaredFields();
         }
      });
   }

   private void setAccessible(final List fields) {
      if (IS_SECURITY_MANAGER_ENABLED) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Void run() {
               Iterator var1 = fields.iterator();

               while(var1.hasNext()) {
                  Field f = (Field)var1.next();
                  if (!f.isAccessible()) {
                     f.setAccessible(true);
                  }
               }

               return null;
            }
         });
      } else {
         Iterator var2 = fields.iterator();

         while(var2.hasNext()) {
            Field f = (Field)var2.next();
            if (!f.isAccessible()) {
               f.setAccessible(true);
            }
         }
      }

   }

   public void writeObject(ObjectOutputStream out, Object obj) throws IOException, IllegalAccessException {
      assert obj.getClass() == this.clazz;

      Iterator var3 = this.fieldsCache.iterator();

      while(var3.hasNext()) {
         Field f = (Field)var3.next();
         out.writeObject(f.get(obj));
      }

   }

   public void readObject(ObjectInputStream in, Object obj) throws IOException, ClassNotFoundException, IllegalAccessException {
      assert obj.getClass() == this.clazz;

      Iterator var3 = this.fieldsCache.iterator();

      while(var3.hasNext()) {
         Field f = (Field)var3.next();
         f.set(obj, in.readObject());
      }

   }
}
