package com.sun.faces.renderkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.HashMap;
import java.util.Map;

public class ApplicationObjectInputStream extends ObjectInputStream {
   private static final Map PRIMITIVE_CLASSES = new HashMap(9, 1.0F);

   public ApplicationObjectInputStream() throws IOException, SecurityException {
   }

   public ApplicationObjectInputStream(InputStream in) throws IOException {
      super(in);
   }

   protected Class resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
      String name = desc.getName();

      try {
         return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException var5) {
         Class c = (Class)PRIMITIVE_CLASSES.get(name);
         if (c != null) {
            return c;
         } else {
            throw var5;
         }
      }
   }

   static {
      PRIMITIVE_CLASSES.put("boolean", Boolean.TYPE);
      PRIMITIVE_CLASSES.put("byte", Byte.TYPE);
      PRIMITIVE_CLASSES.put("char", Character.TYPE);
      PRIMITIVE_CLASSES.put("short", Short.TYPE);
      PRIMITIVE_CLASSES.put("int", Integer.TYPE);
      PRIMITIVE_CLASSES.put("long", Long.TYPE);
      PRIMITIVE_CLASSES.put("float", Float.TYPE);
      PRIMITIVE_CLASSES.put("double", Double.TYPE);
      PRIMITIVE_CLASSES.put("void", Void.TYPE);
   }
}
