package weblogic.persist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.naming.NamingException;
import javax.transaction.TransactionRolledbackException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;

public class TxQueueFileStub implements TxQueueFile, Externalizable {
   private TxQueueFileRemote queue;
   private ByteArrayOutputStream baos;

   public TxQueueFileStub() {
   }

   public TxQueueFileStub(String name, String actualDir, String tmpDir) throws NamingException {
      try {
         this.queue = new TxQueueFileImpl(name, actualDir, tmpDir);
      } catch (IOException var6) {
         NamingException ne = new NamingException("Can't create impl.");
         ne.setRootCause(var6);
         throw ne;
      }
   }

   public void readExternal(ObjectInput wloi) throws IOException, ClassNotFoundException {
      this.queue = (TxQueueFileRemote)((WLObjectInput)wloi).readObjectWL();
   }

   public void writeExternal(ObjectOutput wloo) throws IOException {
      ((WLObjectOutput)wloo).writeObjectWL(this.queue);
   }

   public String getName() {
      return this.queue.getName();
   }

   public void put(Object object) throws IOException {
      if (this.queue instanceof TxQueueFileImpl) {
         if (this.baos == null) {
            this.baos = new ByteArrayOutputStream();
         }

         try {
            synchronized(this.baos) {
               this.baos.reset();
               ObjectOutputStream oos = new ObjectOutputStream(this.baos);
               oos.writeObject(object);
               oos.flush();
               oos.close();
               byte[] bytes = this.baos.toByteArray();
               ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
               object = (Serializable)ois.readObject();
               ois.close();
            }
         } catch (Exception var8) {
            throw new IllegalArgumentException("Could not make clone of object: " + var8);
         }
      }

      this.queue.put(object);
   }

   public Object get() throws TransactionRolledbackException {
      return this.queue.get();
   }

   public Object getW() throws DeadlockException, TransactionRolledbackException {
      return this.queue.getW();
   }

   public Object getW(long millis) throws QueueTimeoutException, TransactionRolledbackException {
      return this.queue.getW(millis);
   }

   public void shutdown() {
      this.queue.shutdown();
   }
}
