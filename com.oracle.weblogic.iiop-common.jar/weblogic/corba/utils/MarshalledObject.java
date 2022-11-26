package weblogic.corba.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.Arrays;
import weblogic.utils.io.FilteringObjectInputStream;

public final class MarshalledObject implements Serializable {
   private static final long serialVersionUID = 6422522115153583030L;
   private static Filter filter = new Filter();
   private byte[] objBytes = null;
   private int hash;

   public static void setFilter(Filter filter) {
      MarshalledObject.filter = filter;
   }

   public MarshalledObject(Object obj) throws IOException {
      if (obj == null) {
         this.hash = 13;
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream out = new MarshalledObjectOutputStream(baos);
         out.writeObject(obj);
         out.flush();
         this.objBytes = baos.toByteArray();
         int h = 0;

         for(int i = 0; i < this.objBytes.length; ++i) {
            h = 31 * h + this.objBytes[i];
         }

         this.hash = h;
      }
   }

   public Object readResolve() throws IOException, ClassNotFoundException {
      if (this.objBytes == null) {
         return null;
      } else {
         ByteArrayInputStream bin = new ByteArrayInputStream(this.objBytes);
         FilteringObjectInputStream in = new FilteringObjectInputStream(bin) {
            protected Class resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
               MarshalledObject.filter.check(desc.getName());
               return super.resolveClass(desc);
            }
         };
         Object obj = in.readObject();
         in.close();
         return obj;
      }
   }

   public int hashCode() {
      return this.hash;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         if (obj != null && obj instanceof MarshalledObject) {
            MarshalledObject other = (MarshalledObject)obj;
            if (this.objBytes == null || other.objBytes == null) {
               return this.objBytes == other.objBytes;
            }

            if (Arrays.equals(this.objBytes, other.objBytes)) {
               return true;
            }
         }

         return false;
      }
   }

   private static class MarshalledObjectOutputStream extends ObjectOutputStream {
      MarshalledObjectOutputStream(OutputStream os) throws IOException {
         super(os);
         this.enableReplaceObject(true);
      }

      public Object replaceObject(Object o) throws IOException {
         if (o instanceof Remote) {
            throw new NotSerializableException("marshal remote object - " + o.getClass().getName());
         } else {
            return o;
         }
      }
   }

   public static class Filter {
      public void check(String className) throws InvalidClassException {
      }
   }
}
