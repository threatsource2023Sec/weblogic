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
import java.util.Enumeration;
import javax.naming.NamingException;
import weblogic.utils.StackTraceUtils;

public class TxIndexedFileStub implements Externalizable, TxIndexedFile {
   static final long serialVersionUID = -1563396532888782603L;
   private TxIndexedFileRemote ph;
   private ByteArrayOutputStream baos;

   public TxIndexedFileStub() {
   }

   public TxIndexedFileStub(String name, String actualDir, String tmpDir) throws NamingException {
      try {
         this.ph = new TxIndexedFileImpl(name, actualDir, tmpDir);
      } catch (IOException var6) {
         NamingException ne = new NamingException("Can't create impl.");
         ne.setRootCause(var6);
         throw ne;
      }
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.ph = (TxIndexedFileRemote)oi.readObject();
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeObject(this.ph);
   }

   public String getName() {
      return this.ph.getName();
   }

   public void store(String key, Object object) throws IOException {
      if (this.ph instanceof TxIndexedFileImpl) {
         if (this.baos == null) {
            this.baos = new ByteArrayOutputStream() {
               public void close() {
               }
            };
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
         } catch (Exception var9) {
            throw new IOException("Could not make clone of object: " + StackTraceUtils.throwable2StackTrace(var9));
         }
      }

      this.ph.store(key, object);
   }

   public Object retrieve(String key) {
      return this.ph.retrieve(key);
   }

   public Enumeration keys() {
      return this.ph.keys();
   }

   public void shutdown() {
      this.ph.shutdown();
   }
}
