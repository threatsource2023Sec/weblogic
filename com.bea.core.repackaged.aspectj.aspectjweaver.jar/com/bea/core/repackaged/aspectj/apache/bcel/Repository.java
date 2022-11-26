package com.bea.core.repackaged.aspectj.apache.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.util.ClassPath;
import com.bea.core.repackaged.aspectj.apache.bcel.util.SyntheticRepository;
import java.io.IOException;

public abstract class Repository {
   private static com.bea.core.repackaged.aspectj.apache.bcel.util.Repository _repository = null;

   public static com.bea.core.repackaged.aspectj.apache.bcel.util.Repository getRepository() {
      if (_repository == null) {
         _repository = SyntheticRepository.getInstance();
      }

      return _repository;
   }

   public static void setRepository(com.bea.core.repackaged.aspectj.apache.bcel.util.Repository rep) {
      _repository = rep;
   }

   public static JavaClass lookupClass(String class_name) {
      try {
         JavaClass clazz = getRepository().findClass(class_name);
         return clazz != null ? clazz : getRepository().loadClass(class_name);
      } catch (ClassNotFoundException var2) {
         return null;
      }
   }

   public static ClassPath.ClassFile lookupClassFile(String class_name) {
      try {
         return ClassPath.getSystemClassPath().getClassFile(class_name);
      } catch (IOException var2) {
         return null;
      }
   }

   public static void clearCache() {
      getRepository().clear();
   }

   public static JavaClass addClass(JavaClass clazz) {
      JavaClass old = getRepository().findClass(clazz.getClassName());
      getRepository().storeClass(clazz);
      return old;
   }

   public static void removeClass(String clazz) {
      getRepository().removeClass(getRepository().findClass(clazz));
   }

   public static boolean instanceOf(JavaClass clazz, JavaClass super_class) {
      return clazz.instanceOf(super_class);
   }

   public static boolean instanceOf(String clazz, String super_class) {
      return instanceOf(lookupClass(clazz), lookupClass(super_class));
   }

   public static boolean implementationOf(JavaClass clazz, JavaClass inter) {
      return clazz.implementationOf(inter);
   }

   public static boolean implementationOf(String clazz, String inter) {
      return implementationOf(lookupClass(clazz), lookupClass(inter));
   }
}
