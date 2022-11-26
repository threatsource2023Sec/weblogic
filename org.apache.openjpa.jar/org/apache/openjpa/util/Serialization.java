package org.apache.openjpa.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.BitSet;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.MultiClassLoader;

public class Serialization {
   private static final Localizer _loc = Localizer.forPackage(Serialization.class);

   public static byte[] serialize(Object val, StoreContext ctx) {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();

      try {
         ObjectOutputStream objs = new PersistentObjectOutputStream(bytes, ctx);
         objs.writeObject(val);
         objs.flush();
         return bytes.toByteArray();
      } catch (Exception var4) {
         throw new StoreException(var4);
      }
   }

   public static Object deserialize(byte[] bytes, StoreContext ctx) {
      ByteArrayInputStream in = new ByteArrayInputStream(bytes);
      return deserialize((InputStream)in, ctx);
   }

   public static Object deserialize(InputStream in, StoreContext ctx) {
      try {
         return ctx == null ? (new ClassResolvingObjectInputStream(in)).readObject() : (new PersistentObjectInputStream(in, ctx)).readObject();
      } catch (Exception var3) {
         throw new StoreException(var3);
      }
   }

   private static class ObjectIdMarker implements Serializable {
      public Object oid;

      public ObjectIdMarker(Object oid) {
         this.oid = oid;
      }
   }

   public static class PersistentObjectInputStream extends ClassResolvingObjectInputStream {
      private final StoreContext _ctx;

      public PersistentObjectInputStream(InputStream delegate, StoreContext ctx) throws IOException {
         super(delegate);
         this._ctx = ctx;
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               PersistentObjectInputStream.this.enableResolveObject(true);
               return null;
            }
         });
      }

      protected void addContextClassLoaders(MultiClassLoader loader) {
         super.addContextClassLoaders(loader);
         loader.addClassLoader(this._ctx.getClassLoader());
      }

      protected Object resolveObject(Object obj) {
         if (!(obj instanceof ObjectIdMarker)) {
            return obj;
         } else {
            Object oid = ((ObjectIdMarker)obj).oid;
            if (oid == null) {
               return null;
            } else {
               Object pc = this._ctx.find(oid, (FetchConfiguration)null, (BitSet)null, (Object)null, 0);
               if (pc == null) {
                  Log log = this._ctx.getConfiguration().getLog("openjpa.Runtime");
                  if (log.isWarnEnabled()) {
                     log.warn(Serialization._loc.get("bad-ser-oid", oid));
                  }

                  if (log.isTraceEnabled()) {
                     log.trace(new ObjectNotFoundException(oid));
                  }
               }

               return pc;
            }
         }
      }
   }

   public static class ClassResolvingObjectInputStream extends ObjectInputStream {
      public ClassResolvingObjectInputStream(InputStream delegate) throws IOException {
         super(delegate);
      }

      protected Class resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
         MultiClassLoader loader = (MultiClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newMultiClassLoaderAction());
         this.addContextClassLoaders(loader);
         loader.addClassLoader(this.getClass().getClassLoader());
         loader.addClassLoader(MultiClassLoader.SYSTEM_LOADER);
         return Class.forName(desc.getName(), true, loader);
      }

      protected void addContextClassLoaders(MultiClassLoader loader) {
         loader.addClassLoader((ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction()));
      }
   }

   public static class PersistentObjectOutputStream extends ObjectOutputStream {
      private StoreContext _ctx;

      public PersistentObjectOutputStream(OutputStream delegate, StoreContext ctx) throws IOException {
         super(delegate);
         this._ctx = ctx;
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               PersistentObjectOutputStream.this.enableReplaceObject(true);
               return null;
            }
         });
      }

      protected Object replaceObject(Object obj) {
         Object oid = this._ctx.getObjectId(obj);
         return oid == null ? obj : new ObjectIdMarker(oid);
      }
   }
}
