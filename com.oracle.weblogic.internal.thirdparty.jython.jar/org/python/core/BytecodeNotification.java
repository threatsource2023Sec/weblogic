package org.python.core;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BytecodeNotification {
   private static List callbacks = new CopyOnWriteArrayList();

   public static void register(Callback n) {
      callbacks.add(n);
   }

   public static boolean unregister(Callback n) {
      return callbacks.remove(n);
   }

   public static void clear() {
      callbacks.clear();
   }

   public static void notify(String name, byte[] data, Class klass) {
      Iterator var3 = callbacks.iterator();

      while(var3.hasNext()) {
         Callback c = (Callback)var3.next();

         try {
            c.notify(name, data, klass);
         } catch (Exception var6) {
            Py.writeWarning("BytecodeNotification", "Exception from callback:" + var6);
         }
      }

   }

   static {
      register(new Callback() {
         public void notify(String name, byte[] bytes, Class c) {
            if (Options.proxyDebugDirectory != null && (name.startsWith("org.python.pycode.") || name.startsWith("org.python.proxies."))) {
               ByteArrayOutputStream ostream = new ByteArrayOutputStream(bytes.length);
               ostream.write(bytes, 0, bytes.length);
               Py.saveClassFile(name, ostream);
            }
         }
      });
   }

   public interface Callback {
      void notify(String var1, byte[] var2, Class var3);
   }
}
