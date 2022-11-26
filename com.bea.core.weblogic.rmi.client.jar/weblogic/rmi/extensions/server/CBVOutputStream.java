package weblogic.rmi.extensions.server;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.rmi.internal.CBVOutput;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.collections.Pool;
import weblogic.utils.collections.StackPool;
import weblogic.utils.io.ChunkedDataOutputStream;

public final class CBVOutputStream implements ColocatedStream {
   private static final int STREAM_POOL_SIZE = 5;
   private static final Pool POOL = new StackPool(5);
   private final ChunkedDataOutputStream cdos = new ChunkedDataOutputStream();
   private final CBVOutput oos;
   private CBVHolder head;
   private CBVHolder tail;
   private int pos = 0;

   private CBVOutput getOutputStream(OutputStream os) {
      CBVOutput wlos = (CBVOutput)POOL.remove();

      try {
         if (wlos != null) {
            wlos.setDelegate(this, os);
         } else {
            wlos = RMIEnvironment.getEnvironment().getCBVOutput(this, os);
            wlos.setReplacer(RemoteObjectReplacer.getReplacer());
         }

         wlos.reset();
         return wlos;
      } catch (IOException var4) {
         throw new AssertionError(var4);
      }
   }

   private static void releaseOutputStream(CBVOutput os) {
      POOL.add(os);
   }

   public CBVOutputStream() {
      this.oos = this.getOutputStream(this.cdos);
   }

   public void writeObjectSpecial(Object obj) throws IOException {
      final CBVHolder sholder = new CBVHolder(obj, this.pos++);
      if (this.tail == null) {
         this.head = this.tail = sholder;
      } else {
         this.tail.next = sholder;
         this.tail = this.tail.next;
      }

      final CBVOutput localoos = this.oos;
      PrivilegedExceptionAction action = new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            localoos.writeInt(sholder.pos);
            return null;
         }
      };
      this.doPriviligedExceptionAction(action);
   }

   public void writeObject(final Object obj) throws IOException {
      final CBVOutput localoos = this.oos;
      PrivilegedExceptionAction action = new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            localoos.writeObject(obj);
            return null;
         }
      };
      this.doPriviligedExceptionAction(action);
   }

   private void doPriviligedExceptionAction(PrivilegedExceptionAction action) throws IOException {
      try {
         AccessController.doPrivileged(action);
      } catch (PrivilegedActionException var4) {
         Exception ex = var4.getException();
         if (ex instanceof IOException) {
            throw (IOException)ex;
         }

         if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
         }
      }

   }

   public CBVInputStream makeCBVInputStream() {
      return new CBVInputStream(this.cdos.makeChunkedDataInputStream(), this.head);
   }

   public void flush() throws IOException {
      this.oos.flush();
   }

   public void close() throws IOException {
      this.head = null;
      this.tail = null;
      this.oos.close();
      this.cdos.close();
      releaseOutputStream(this.oos);
   }
}
