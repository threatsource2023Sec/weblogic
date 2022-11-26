package com.sun.faces.util.copier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class SerializationCopier implements Copier {
   private static final String SERIALIZATION_COPIER_ERROR = "SerializationCopier cannot be used in this case. Please try other copier (e.g. MultiStrategyCopier, NewInstanceCopier, CopyCtorCopier, CloneCopier).";

   public Object copy(Object object) {
      if (!(object instanceof Serializable)) {
         throw new IllegalStateException("Can't copy object of type " + object.getClass() + " since it doesn't implement Serializable");
      } else {
         try {
            return copyOutIn(object);
         } catch (ClassNotFoundException | IOException var3) {
            throw new IllegalArgumentException("SerializationCopier cannot be used in this case. Please try other copier (e.g. MultiStrategyCopier, NewInstanceCopier, CopyCtorCopier, CloneCopier).");
         }
      }
   }

   private static Object copyOutIn(Object object) throws ClassNotFoundException, IOException {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      Out out = new Out(byteArrayOutputStream);
      out.writeObject(object);
      byte[] bytes = byteArrayOutputStream.toByteArray();
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
      In in = new In(byteArrayInputStream, out);
      Object cloned = in.readObject();
      return cloned;
   }

   private static class Out extends ObjectOutputStream {
      Queue queue = new LinkedList();

      Out(OutputStream out) throws IOException {
         super(out);
      }

      protected void annotateClass(Class c) {
         this.queue.add(c);
      }

      protected void annotateProxyClass(Class c) {
         this.queue.add(c);
      }
   }

   private static class In extends ObjectInputStream {
      private final Out out;

      In(InputStream inputStream, Out out) throws IOException {
         super(inputStream);
         this.out = out;
      }

      protected Class resolveProxyClass(String[] interfaceNames) throws IOException, ClassNotFoundException {
         return (Class)this.out.queue.poll();
      }

      protected Class resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
         String actuallyfound = null;
         Class pollclass = (Class)this.out.queue.poll();
         if (pollclass != null) {
            actuallyfound = pollclass.getName();
         }

         if (!objectStreamClass.getName().equals(actuallyfound)) {
            throw new IllegalArgumentException("SerializationCopier cannot be used in this case. Please try other copier (e.g. MultiStrategyCopier, NewInstanceCopier, CopyCtorCopier, CloneCopier).");
         } else {
            return pollclass;
         }
      }
   }
}
