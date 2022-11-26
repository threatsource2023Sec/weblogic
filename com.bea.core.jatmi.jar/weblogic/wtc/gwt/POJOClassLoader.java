package weblogic.wtc.gwt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class POJOClassLoader extends ClassLoader {
   private Map _entries;
   private byte[] BUF = new byte[4096];
   private ByteArrayOutputStream BAOS = new ByteArrayOutputStream(4096);

   public POJOClassLoader(String jarFileName, ClassLoader parent) throws IOException {
      super(parent);
      FileInputStream fis = new FileInputStream(jarFileName);
      byte[] bytes = this.readBytes(fis);
      fis.close();
      this._entries = this.readJar(bytes);
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      int arrayDim;
      for(arrayDim = 0; name.charAt(arrayDim) == '['; ++arrayDim) {
      }

      Class clazz;
      if (arrayDim > 0) {
         String basename = name.substring(arrayDim + 1, name.length() - 1);
         clazz = this.loadClass(basename);
         clazz = Array.newInstance(clazz, new int[arrayDim]).getClass();
      } else {
         byte[] bytes = (byte[])((byte[])this._entries.get(name));
         if (bytes == null) {
            throw new ClassNotFoundException(name);
         }

         clazz = this.defineClass(name, bytes, 0, bytes.length);
      }

      return clazz;
   }

   private Map readJar(byte[] bytes) throws IOException {
      Map entries = new HashMap();
      JarInputStream jarInputStream = new JarInputStream(new ByteArrayInputStream(bytes));

      while(true) {
         JarEntry entry = jarInputStream.getNextJarEntry();
         if (entry == null) {
            jarInputStream.close();
            return entries;
         }

         String name = entry.getName();
         if (!entry.isDirectory() && name.endsWith(".class")) {
            byte[] content = this.readBytes(jarInputStream);
            name = name.substring(0, name.length() - 6).replace('/', '.');
            entries.put(name, content);
         }
      }
   }

   private byte[] readBytes(InputStream is) throws IOException {
      this.BAOS.reset();

      int n;
      while((n = is.read(this.BUF, 0, this.BUF.length)) != -1) {
         this.BAOS.write(this.BUF, 0, n);
      }

      return this.BAOS.toByteArray();
   }
}
