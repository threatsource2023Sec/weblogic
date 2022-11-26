package weblogic.rmi.extensions.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.lang.ref.SoftReference;
import weblogic.rmi.internal.CBVInput;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.collections.Pool;
import weblogic.utils.collections.StackPool;
import weblogic.utils.io.ChunkedDataInputStream;

public final class CBVInputStream implements ColocatedStream {
   private static final int STREAM_POOL_SIZE = 5;
   private static final Pool POOL = new StackPool(5);
   private final ChunkedDataInputStream cdis;
   private final CBVInput ois;
   private CBVHolder head;

   CBVInputStream(ChunkedDataInputStream cdis, CBVHolder head) {
      this.cdis = cdis;
      this.head = head;
      this.ois = this.getInputStream(cdis);
   }

   private CBVInput getInputStream(InputStream in) {
      CBVInput wlis = acquireInputStream();

      try {
         if (wlis != null) {
            wlis.setDelegate(this, in);
         } else {
            wlis = RMIEnvironment.getEnvironment().getCBVInput(this, in);
            wlis.setReplacer(RemoteObjectReplacer.getReplacer());
         }

         return wlis;
      } catch (IOException var5) {
         AssertionError e = new AssertionError("Failed to create input stream");
         e.initCause(var5);
         throw e;
      }
   }

   private static CBVInput acquireInputStream() {
      for(SoftReference ref = (SoftReference)POOL.remove(); ref != null; ref = (SoftReference)POOL.remove()) {
         CBVInput wlis = (CBVInput)ref.get();
         if (wlis != null) {
            return wlis;
         }
      }

      return null;
   }

   private static void releaseInputStream(CBVInput is) {
      POOL.add(new SoftReference(is));
   }

   public Object readObject() throws ClassNotFoundException, IOException {
      return this.ois.readObject();
   }

   public Object readObjectSpecial() throws IOException {
      int pos = this.ois.readInt();
      if (this.head != null && this.head.pos <= pos) {
         do {
            if (this.head.pos >= pos) {
               Object o = this.head.obj;
               this.head = this.head.next;
               return o;
            }

            this.head = this.head.next;
         } while(this.head != null);

         throw new StreamCorruptedException("Unexpected CBVHolder index " + pos);
      } else {
         throw new StreamCorruptedException("Unexpected CBVHolder index " + pos);
      }
   }

   public void close() throws IOException {
      this.cdis.close();
      releaseInputStream(this.ois);
   }
}
