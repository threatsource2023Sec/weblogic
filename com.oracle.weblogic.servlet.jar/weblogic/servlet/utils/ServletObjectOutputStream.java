package weblogic.servlet.utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import weblogic.servlet.internal.session.SessionObjectReplacer;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.SlabPool;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class ServletObjectOutputStream extends ObjectOutputStream {
   private static final SlabPool pool = new SlabPool(15, 3);
   private static final SessionObjectReplacer replacer = SessionObjectReplacer.getInstance();
   private final UnsyncByteArrayOutputStream baos;

   private ServletObjectOutputStream() throws IOException {
      this(new UnsyncByteArrayOutputStream());
   }

   private ServletObjectOutputStream(UnsyncByteArrayOutputStream out) throws IOException {
      super(out);
      this.baos = out;
      this.enableReplaceObject(true);
   }

   public Object replaceObject(Object object) throws IOException {
      return replacer.replaceObject(object);
   }

   public void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
      ClassLoader classLoader = desc.forClass().getClassLoader();
      this.writeUTF(desc.getName());
      this.writeLong(desc.getSerialVersionUID());
      String annotation = "";
      if (classLoader instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)classLoader;
         annotation = gcl.getAnnotation().getAnnotationString();
      }

      this.writeUTF(annotation);
   }

   public void annotateProxyClass(Class clazz) throws IOException {
      String annotation = "";
      Class[] interfaces = clazz.getInterfaces();

      for(int i = 0; i < interfaces.length; ++i) {
         if (interfaces[i].getClassLoader() instanceof GenericClassLoader) {
            GenericClassLoader gcl = (GenericClassLoader)interfaces[i].getClassLoader();
            annotation = gcl.getAnnotation().getAnnotationString();
            break;
         }
      }

      this.writeUTF(annotation);
   }

   public void reset() throws IOException {
      this.baos.reset();
   }

   public byte[] toByteArray() {
      return this.baos.toRawBytes();
   }

   private UnsyncByteArrayOutputStream getUnderlyingByteStream() {
      return this.baos;
   }

   public static ServletObjectOutputStream getOutputStream() throws IOException {
      UnsyncByteArrayOutputStream baos = (UnsyncByteArrayOutputStream)pool.remove();
      if (baos == null) {
         baos = new UnsyncByteArrayOutputStream();
      }

      return new ServletObjectOutputStream(baos);
   }

   public static void releaseOutputStream(ServletObjectOutputStream out) throws IOException {
      out.reset();
      pool.add(out.getUnderlyingByteStream());
   }
}
