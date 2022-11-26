package com.bea.core.repackaged.springframework.cglib.core;

public class ClassesKey {
   private static final Key FACTORY = (Key)KeyFactory.create(Key.class);

   private ClassesKey() {
   }

   public static Object create(Object[] array) {
      return FACTORY.newInstance(classNames(array));
   }

   private static String[] classNames(Object[] objects) {
      if (objects == null) {
         return null;
      } else {
         String[] classNames = new String[objects.length];

         for(int i = 0; i < objects.length; ++i) {
            Object object = objects[i];
            if (object != null) {
               Class aClass = object.getClass();
               classNames[i] = aClass == null ? null : aClass.getName();
            }
         }

         return classNames;
      }
   }

   interface Key {
      Object newInstance(Object[] var1);
   }
}
