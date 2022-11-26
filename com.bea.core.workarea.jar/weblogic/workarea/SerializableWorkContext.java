package weblogic.workarea;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.io.FilteringObjectInputStream;

public class SerializableWorkContext implements PrimitiveWorkContext, Serializable {
   private static final DebugLogger debugWorkContext = DebugLogger.getDebugLogger("DebugWorkContext");
   private static final long serialVersionUID = 3485637846065994552L;
   private byte[] data;
   private transient Serializable object;
   private transient boolean mutable;

   public SerializableWorkContext() {
      this.mutable = false;
   }

   SerializableWorkContext(Serializable s) throws IOException {
      this(s, false);
   }

   SerializableWorkContext(Serializable s, boolean mutable) throws IOException {
      this.mutable = false;
      this.object = s;
      this.mutable = mutable;
      if (!mutable) {
         this.data = this.serialize(this.object);
      }

   }

   public String toString() {
      return "Serializable";
   }

   public Object get() {
      try {
         return this.getSerializable();
      } catch (IOException var2) {
         return this.data;
      } catch (ClassNotFoundException var3) {
         return this.data;
      }
   }

   public Serializable getSerializable() throws IOException, ClassNotFoundException {
      if (this.object != null) {
         return this.object;
      } else {
         ByteArrayInputStream bin = new ByteArrayInputStream(this.data);
         ObjectInputStream in = new FilteringObjectInputStream(bin) {
            protected Class resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
               String className = desc.getName();
               if (className != null && className.length() > 0) {
                  try {
                     this.checkLegacyBlacklistIfNeeded(className);
                     return Class.forName(className, false, Thread.currentThread().getContextClassLoader());
                  } catch (InvalidClassException var4) {
                     if (SerializableWorkContext.debugWorkContext.isDebugEnabled()) {
                        SerializableWorkContext.debugWorkContext.debug("Unauthorized deserialization attempt: " + className);
                     }

                     throw var4;
                  } catch (ClassNotFoundException var5) {
                     return super.resolveClass(desc);
                  }
               } else {
                  throw new ClassNotFoundException(className);
               }
            }

            protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
               ClassLoader ctxLoader = Thread.currentThread().getContextClassLoader();
               ClassLoader nonPublicLoader = null;
               boolean hasNonPublicInterface = false;
               Class[] classObjs = new Class[interfaces.length];

               for(int i = 0; i < interfaces.length; ++i) {
                  Class cl = Class.forName(interfaces[i], false, ctxLoader);
                  if ((cl.getModifiers() & 1) == 0) {
                     if (hasNonPublicInterface) {
                        if (nonPublicLoader != cl.getClassLoader()) {
                           throw new IllegalAccessError("conflicting non-public interface class loaders");
                        }
                     } else {
                        nonPublicLoader = cl.getClassLoader();
                        hasNonPublicInterface = true;
                     }
                  }

                  classObjs[i] = cl;
               }

               try {
                  return Proxy.getProxyClass(hasNonPublicInterface ? nonPublicLoader : ctxLoader, classObjs);
               } catch (IllegalArgumentException var8) {
                  throw new ClassNotFoundException((String)null, var8);
               }
            }
         };
         Serializable obj = (Serializable)in.readObject();
         in.close();
         if (obj instanceof Carrier) {
            this.object = ((Carrier)obj).getSerializable();
            this.mutable = ((Carrier)obj).isMutable();
         } else {
            this.object = obj;
         }

         return this.object;
      }
   }

   public int hashCode() {
      if (!this.mutable) {
         return Arrays.hashCode(this.data);
      } else {
         Object gotten = this.get();
         return gotten == null ? 0 : gotten.hashCode();
      }
   }

   public boolean equals(Object obj) {
      if (obj instanceof SerializableWorkContext) {
         return !this.mutable && !((SerializableWorkContext)obj).mutable ? Arrays.equals(((SerializableWorkContext)obj).data, this.data) : this.get().equals(((SerializableWorkContext)obj).get());
      } else {
         return false;
      }
   }

   public void writeContext(WorkContextOutput out) throws IOException {
      if (this.mutable) {
         Carrier carrier = new Carrier(this.object);
         carrier.setMutable();
         this.data = this.serialize(carrier);
      }

      out.writeInt(this.data.length);
      out.write(this.data);
   }

   public void readContext(WorkContextInput in) throws IOException {
      this.data = new byte[in.readInt()];
      in.readFully(this.data);
   }

   private byte[] serialize(Serializable s) throws IOException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(bout);
      out.writeObject(s);
      out.flush();
      out.close();
      return bout.toByteArray();
   }

   private static class Carrier implements Serializable {
      private static final int VERSION = 1;
      private static final long serialVersionUID = -197593099539117489L;
      private Serializable serializable;
      private boolean mutable;

      public Carrier() {
      }

      Carrier(Serializable object) {
         this.serializable = object;
      }

      Serializable getSerializable() {
         return this.serializable;
      }

      void setMutable() {
         this.mutable = true;
      }

      boolean isMutable() {
         return this.mutable;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.writeInt(1);
         out.writeObject(this.serializable);
         out.writeBoolean(this.mutable);
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         int version = in.readInt();
         this.serializable = (Serializable)in.readObject();
         this.mutable = in.readBoolean();
      }
   }
}
