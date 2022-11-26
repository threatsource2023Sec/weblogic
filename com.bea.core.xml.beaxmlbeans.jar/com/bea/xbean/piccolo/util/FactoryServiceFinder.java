package com.bea.xbean.piccolo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

public class FactoryServiceFinder {
   static final String SERVICE = "META-INF/services/";

   public static String findService(String name) throws IOException {
      InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("META-INF/services/" + name);
      BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      return r.readLine();
   }

   public static Enumeration findServices(String name) throws IOException {
      return new FactoryEnumeration(ClassLoader.getSystemClassLoader().getResources(name));
   }

   private static class FactoryEnumeration implements Enumeration {
      Enumeration enumValue;
      Object next = null;

      FactoryEnumeration(Enumeration enumValue) {
         this.enumValue = enumValue;
         this.nextElement();
      }

      public boolean hasMoreElements() {
         return this.next != null;
      }

      public Object nextElement() {
         Object current = this.next;

         while(true) {
            try {
               if (this.enumValue.hasMoreElements()) {
                  BufferedReader r = new BufferedReader(new InputStreamReader(((URL)this.enumValue.nextElement()).openStream()));
                  this.next = r.readLine();
               } else {
                  this.next = null;
               }

               return current;
            } catch (IOException var3) {
            }
         }
      }
   }
}
