package weblogic.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.AccessController;
import java.util.Hashtable;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionRolledbackException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.T3Srvr;
import weblogic.transaction.TxHelper;
import weblogic.utils.NestedRuntimeException;
import weblogic.utils.PlatformConstants;
import weblogic.utils.UnsyncHashtable;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class TxQueueFileImpl implements TxQueueFileRemote, XAResource, PlatformConstants {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean verbose = false;
   private String name;
   private String commitFilename;
   private String prepareFilename;
   private Vector queue;
   private Object headMutex = new Object() {
   };
   private Object tailMutex = new Object() {
   };
   private Object headLocker;
   private Object tailLocker;
   private UnsyncHashtable enrolled = new UnsyncHashtable();
   private static Object exists = new Object() {
   };
   private Vector inserts = new Vector();
   private int numRemoved = 0;
   private int numWrites = 0;
   private int fullWriteInterval = 100;
   private int minMillisBetweenWrites = 20;
   private boolean isShutdown;
   private Object preparationMutex = new Object() {
   };
   private Vector preparationInserts;
   private int preparationNumRemoved;
   private Object prepareWriteMutex = new Object() {
   };
   private Vector preparationQueue;
   private File prepareFile;
   private IOException preparationIOE;
   private PrepareThread prepareThread;
   private Object commitMutex = new Object() {
   };
   private Object commitCompleteMutex = new Object() {
   };
   private Object commitWriteMutex = new Object() {
   };
   private Vector commitInserts;
   private int commitNumRemoved;
   private Vector commitQueue;
   private File commitFile;
   private IOException commitIOE;
   private CommitThread commitThread;

   public static void main(String[] args) {
      try {
         String name = args.length >= 1 ? args[0] : "DefaultStore";
         String actualDir = args.length >= 2 ? args[1] : ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         String tmpDir = args.length >= 3 ? args[2] : ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         TxQueueFile phi = new TxQueueFileStub(name, actualDir, tmpDir);

         try {
            Hashtable p = new Hashtable();
            p.put("weblogic.jndi.createIntermediateContexts", "true");
            Context ctx = new InitialContext(p);
            ctx.rebind(name, phi);
         } catch (NamingException var7) {
            T3Srvr.getT3Srvr().getLog().error("There was a communication problem -- this Impl must be in the server", var7);
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   protected TxQueueFileImpl(String name, String actualDir, String tmpDir) throws IOException {
      this.name = name;
      this.commitFilename = actualDir + File.separator + name + ".dat";
      this.prepareFilename = tmpDir + File.separator + name + "Tmp.dat";
      this.loadQueue(new File(actualDir), new File(tmpDir));
      this.commitThread = new CommitThread(name + "-CommitThread");
      this.commitThread.start();
      this.prepareThread = new PrepareThread(name + "-PrepareThread");
      this.prepareThread.start();
   }

   public void shutdown() {
      this.isShutdown = true;
      synchronized(this.preparationMutex) {
         this.preparationMutex.notifyAll();
      }

      synchronized(this.commitMutex) {
         this.commitMutex.notifyAll();
      }
   }

   public String getName() {
      return this.name;
   }

   public void put(Object object) throws TransactionRolledbackException {
      Transaction txc = null;
      txc = TxHelper.getTransaction();
      if (txc == null) {
         try {
            TransactionManager tm = TxHelper.getTransactionManager();
            tm.begin();
            Transaction txc = tm.getTransaction();
            txc.enlistResource(this);
            this.put(txc, object);
            txc.commit();
         } catch (Exception var5) {
            var5.printStackTrace();
            throw new TransactionRolledbackException("Could not complete transaction . . . Rolled back: " + var5.getMessage());
         }
      } else {
         try {
            synchronized(this) {
               if (this.enrolled.put(txc, exists) == null) {
                  txc.enlistResource(this);
               }
            }
         } catch (Exception var7) {
            throw new TransactionRolledbackException("Could not enroll resource: " + var7.getMessage());
         }

         this.put(txc, object);
      }

   }

   public Object get() throws TransactionRolledbackException {
      Transaction txc = null;
      txc = TxHelper.getTransaction();
      Object object;
      if (txc == null) {
         try {
            TransactionManager tm = TxHelper.getTransactionManager();
            tm.begin();
            Transaction txc = tm.getTransaction();
            txc.enlistResource(this);
            object = this.get(txc);
            txc.commit();
         } catch (Exception var5) {
            var5.printStackTrace();
            throw new TransactionRolledbackException("Could not complete transaction . . . Rolled back: " + var5.getMessage());
         }
      } else {
         try {
            synchronized(this) {
               if (this.enrolled.put(txc, exists) == null) {
                  txc.enlistResource(this);
               }
            }
         } catch (Exception var7) {
            throw new TransactionRolledbackException("Could not enroll resource: " + var7.getMessage());
         }

         object = this.get(txc);
      }

      return object;
   }

   public Object getW() throws DeadlockException, TransactionRolledbackException {
      Transaction txc = null;
      txc = TxHelper.getTransaction();
      int total = this.queue.size();
      if (txc != null && txc.equals(this.tailLocker)) {
         total += this.inserts.size();
      }

      if (txc != null && txc.equals(this.headLocker)) {
         total -= this.numRemoved;
      }

      if (total == 0 && txc != null && txc.equals(this.tailLocker)) {
         throw new DeadlockException("Attempting to do a get in transaction that will never succeed\nInserts: " + this.inserts.size() + " Original Size: " + this.queue.size() + " Removes: " + this.numRemoved + "\ntailLocker: " + this.tailLocker + " Tx: " + txc);
      } else {
         synchronized(this.commitCompleteMutex) {
            Object object;
            while((object = this.get()) == null) {
               try {
                  this.commitCompleteMutex.wait();
               } catch (InterruptedException var7) {
               }
            }

            return object;
         }
      }
   }

   public Object getW(long millis) throws QueueTimeoutException, TransactionRolledbackException {
      Transaction txc = TxHelper.getTransaction();

      Object object;
      long currentTime;
      for(long startTime = System.currentTimeMillis(); (object = this.get()) == null && millis > 0L; startTime = currentTime) {
         try {
            Thread.sleep(50L);
         } catch (InterruptedException var9) {
         }

         currentTime = System.currentTimeMillis();
         millis -= currentTime - startTime;
      }

      if (object == null) {
         throw new QueueTimeoutException("GetW() timed out");
      } else {
         return object;
      }
   }

   private void put(Transaction txc, Object object) {
      synchronized(this.tailMutex) {
         while(this.tailLocker != null && !txc.equals(this.tailLocker)) {
            try {
               this.tailMutex.wait();
            } catch (InterruptedException var6) {
            }
         }

         if (this.tailLocker == null) {
            this.inserts = new Vector();
         }

         this.tailLocker = txc;
      }

      this.inserts.addElement(object);
   }

   private Object get(Transaction txc) {
      synchronized(this.headMutex) {
         while(this.headLocker != null && !txc.equals(this.headLocker)) {
            try {
               this.headMutex.wait();
            } catch (InterruptedException var5) {
            }
         }

         if (this.headLocker == null) {
            this.numRemoved = 0;
         }

         this.headLocker = txc;
      }

      int numOverflow = this.numRemoved - this.queue.size();
      Object object;
      if (numOverflow >= 0) {
         if (!txc.equals(this.tailLocker) || this.inserts.size() <= numOverflow) {
            return null;
         }

         object = this.inserts.elementAt(numOverflow);
      } else {
         object = this.queue.elementAt(this.numRemoved);
      }

      ++this.numRemoved;
      return object;
   }

   private void decipherFailure(String method, String filename, IOException ioe) throws XAException {
      String d = filename.substring(0, filename.lastIndexOf(FILE_SEP));
      File dir = new File(d);
      File file = new File(filename);
      String prefix = "Could not " + method + ": ";
      String excepMsg = ioe == null ? "" : EOL + "Caught " + ioe;
      if (!dir.exists()) {
         throw new XAException(prefix + "Directory '" + dir + "' does not exist." + excepMsg);
      } else if (!dir.isDirectory()) {
         throw new XAException(prefix + "File '" + dir + "' exists but is not a directory." + excepMsg);
      } else if (!file.canWrite()) {
         throw new XAException(prefix + "Can't write to file '" + file + "'." + excepMsg);
      } else if (ioe != null) {
         throw new XAException(prefix + excepMsg);
      } else {
         throw new XAException(prefix + "Possible problems: " + EOL + "  Disk full" + EOL + "  File owned by different process (NT)" + EOL + "  Hardware error");
      }
   }

   public void end(Xid xid, int flags) throws XAException {
   }

   public void forget(Xid xid) throws XAException {
   }

   public int getTransactionTimeout() throws XAException {
      return 0;
   }

   public boolean isSameRM(XAResource xar) throws XAException {
      return this == xar;
   }

   public Xid[] recover(int flags) throws XAException {
      return null;
   }

   public boolean setTransactionTimeout(int t) throws XAException {
      return true;
   }

   public void start(Xid xid, int flags) throws XAException {
   }

   public int prepare(Xid xid) throws XAException {
      Transaction txc = TxHelper.getTransaction();
      if (txc == null) {
         throw new XAException("Transaction unexpectedly null");
      } else {
         synchronized(this.preparationMutex) {
            synchronized(this.prepareWriteMutex) {
               if (this.preparationQueue == null) {
                  this.preparationQueue = (Vector)this.queue.clone();
               }
            }

            if (txc.equals(this.headLocker)) {
               this.preparationNumRemoved = this.numRemoved;
            } else {
               this.preparationNumRemoved = 0;
            }

            if (txc.equals(this.tailLocker)) {
               this.preparationInserts = this.inserts;
            } else {
               this.preparationInserts = null;
            }

            this.fillInQueue(this.preparationQueue, this.preparationInserts, this.preparationNumRemoved);
         }

         this.prepareThread.write();
         if (this.preparationIOE != null) {
            throw new XAException(this.preparationIOE.getMessage());
         } else {
            return 0;
         }
      }
   }

   public void rollback(Xid xid) throws XAException {
      this.commitThread.write();
      this.releaseLocks();
   }

   public void commit(Xid xid, boolean onePhase) throws XAException {
      if (onePhase) {
         this.commitOnePhase(xid);
      } else {
         this.commit(xid);
      }

   }

   private void commitOnePhase(Xid xid) throws XAException {
      int status = this.prepare(xid);
      if (status == 0) {
         this.commit(xid);
      }

   }

   private void commit(Xid xid) throws XAException {
      Transaction txc = null;
      txc = TxHelper.getTransaction();
      if (txc == null) {
         throw new XAException("No transaction");
      } else {
         Vector localCommitInserts;
         int localCommitNumRemoved;
         synchronized(this.commitMutex) {
            synchronized(this.commitWriteMutex) {
               if (this.commitQueue == null) {
                  this.commitQueue = (Vector)this.queue.clone();
               }
            }

            if (txc.equals(this.headLocker)) {
               this.commitNumRemoved = this.numRemoved;
            } else {
               this.commitNumRemoved = 0;
            }

            if (txc.equals(this.tailLocker)) {
               this.commitInserts = this.inserts;
            } else {
               this.commitInserts = null;
            }

            this.fillInQueue(this.commitQueue, localCommitInserts = this.commitInserts, localCommitNumRemoved = this.commitNumRemoved);
         }

         this.commitThread.write();
         if (this.commitIOE != null) {
            this.decipherFailure("commit", this.commitFilename, this.commitIOE);
         }

         this.fillInQueue(this.queue, localCommitInserts, localCommitNumRemoved);
         this.releaseLocks();
         synchronized(this.commitCompleteMutex) {
            this.commitCompleteMutex.notifyAll();
         }
      }
   }

   protected void write(Vector queue, Vector inserts, int numRemoved, File file) throws IOException {
      int halfWrites = this.numWrites++ >> 2;
      boolean append = halfWrites % this.fullWriteInterval != 0;
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      if (append) {
         oos.writeBoolean(true);
         oos.writeObject(inserts);
         oos.writeInt(numRemoved);
      } else {
         oos.writeObject(queue);
      }

      oos.flush();
      oos.close();
      baos.close();
      FileOutputStream fos = new FileOutputStream(file.getAbsolutePath(), append);
      fos.write(baos.toByteArray());
      fos.flush();
      fos.getFD().sync();
      fos.close();
   }

   private Vector readQueue(File file) throws IOException, ClassNotFoundException {
      FileInputStream fis = null;
      ObjectInputStream ois = null;
      Vector queue = null;

      try {
         fis = new FileInputStream(file);
         ois = new ObjectInputStream(fis);
         queue = (Vector)ois.readObject();

         while(true) {
            try {
               ois = new ObjectInputStream(fis);
               ois.readBoolean();
            } catch (IOException var12) {
               return queue;
            }

            try {
               Vector inserts = (Vector)ois.readObject();
               int numRemoved = ois.readInt();
               this.fillInQueue(queue, inserts, numRemoved);
            } catch (IOException var11) {
               throw var11;
            }
         }
      } finally {
         if (ois != null) {
            ois.close();
         }

         if (fis != null) {
            fis.close();
         }

      }
   }

   private void loadQueue(File actualDir, File tmpDir) throws IOException {
      this.queue = new Vector();
      this.ensureDirectories(actualDir, tmpDir);
      this.commitFile = new File(this.commitFilename);
      this.prepareFile = new File(this.prepareFilename);
      if (this.commitFile.exists()) {
         if (this.prepareFile.exists() && this.prepareFile.lastModified() > this.commitFile.lastModified()) {
            try {
               this.queue = this.readQueue(this.prepareFile);
               return;
            } catch (Exception var7) {
            }
         }

         try {
            this.queue = this.readQueue(this.commitFile);
            return;
         } catch (Exception var6) {
            try {
               if (this.prepareFile.exists() && this.prepareFile.lastModified() < this.commitFile.lastModified()) {
                  this.queue = this.readQueue(this.prepareFile);
                  return;
               }
            } catch (Exception var5) {
               return;
            }
         }
      } else {
         try {
            if (this.prepareFile.exists()) {
               this.queue = this.readQueue(this.prepareFile);
               return;
            }
         } catch (Exception var4) {
            return;
         }
      }

   }

   private void ensureDirectories(File actualDir, File tmpDir) throws IOException {
      if (!actualDir.exists() && !actualDir.mkdirs()) {
         throw new IOException("Couldn't create " + actualDir);
      } else if (!tmpDir.exists() && !tmpDir.mkdirs()) {
         throw new IOException("Couldn't create " + tmpDir);
      }
   }

   private void fillInQueue(Vector v, Vector inserts, int numRemoved) {
      synchronized(this) {
         if (inserts != null) {
            int size = inserts.size();

            for(int insertNum = 0; insertNum < size; ++insertNum) {
               v.addElement(inserts.elementAt(insertNum));
            }
         }

         while(numRemoved > 0) {
            v.removeElementAt(0);
            --numRemoved;
         }

      }
   }

   private synchronized void releaseLocks() {
      Transaction txc = null;
      txc = TxHelper.getTransaction();
      if (txc == null) {
         throw new NestedRuntimeException("Transaction is unexpectedly null");
      } else {
         this.enrolled.remove(txc);
         synchronized(this.tailMutex) {
            if (txc.equals(this.tailLocker)) {
               this.tailLocker = null;
               this.inserts = null;
            }

            this.tailMutex.notifyAll();
         }

         synchronized(this.headMutex) {
            if (txc.equals(this.headLocker)) {
               this.headLocker = null;
               this.numRemoved = 0;
            }

            this.headMutex.notifyAll();
         }
      }
   }

   private class CommitThread extends Thread {
      private int writeCalled = 0;
      private int writeDone = 0;

      public CommitThread(String name) {
         super(name);
      }

      public void run() {
         while(true) {
            synchronized(TxQueueFileImpl.this.commitMutex) {
               long diff = (long)TxQueueFileImpl.this.minMillisBetweenWrites;

               while(true) {
                  if (TxQueueFileImpl.this.commitQueue == null) {
                     diff = (long)TxQueueFileImpl.this.minMillisBetweenWrites;
                  } else if (diff <= 0L) {
                     break;
                  }

                  long starttime = System.currentTimeMillis();

                  try {
                     TxQueueFileImpl.this.commitMutex.wait(diff);
                  } catch (InterruptedException var11) {
                  }

                  if (TxQueueFileImpl.this.isShutdown) {
                     break;
                  }

                  diff -= System.currentTimeMillis() - starttime;
               }

               if (TxQueueFileImpl.this.commitQueue != null) {
                  try {
                     TxQueueFileImpl.this.write(TxQueueFileImpl.this.commitQueue, TxQueueFileImpl.this.commitInserts, TxQueueFileImpl.this.commitNumRemoved, TxQueueFileImpl.this.commitFile);
                     TxQueueFileImpl.this.commitIOE = null;
                  } catch (IOException var10) {
                     TxQueueFileImpl.this.commitIOE = var10;
                  }
               }

               synchronized(TxQueueFileImpl.this.commitWriteMutex) {
                  TxQueueFileImpl.this.commitQueue = null;
                  TxQueueFileImpl.this.commitWriteMutex.notifyAll();
               }

               if (TxQueueFileImpl.this.isShutdown) {
                  return;
               }
            }
         }
      }

      public void write() {
         if (TxQueueFileImpl.this.isShutdown) {
            throw new RuntimeException("Queue has been shutdown");
         } else {
            synchronized(TxQueueFileImpl.this.commitWriteMutex) {
               while(TxQueueFileImpl.this.commitQueue != null) {
                  try {
                     TxQueueFileImpl.this.commitWriteMutex.wait();
                  } catch (InterruptedException var4) {
                  }
               }

            }
         }
      }
   }

   private class PrepareThread extends Thread {
      private int writeCalled = 0;
      private int writeDone = 0;

      public PrepareThread(String name) {
         super(name);
      }

      public void run() {
         while(true) {
            synchronized(TxQueueFileImpl.this.preparationMutex) {
               long diff = (long)TxQueueFileImpl.this.minMillisBetweenWrites;

               while(true) {
                  if (TxQueueFileImpl.this.preparationQueue == null) {
                     diff = (long)TxQueueFileImpl.this.minMillisBetweenWrites;
                  } else if (diff <= 0L) {
                     break;
                  }

                  long starttime = System.currentTimeMillis();

                  try {
                     TxQueueFileImpl.this.preparationMutex.wait(diff);
                  } catch (InterruptedException var11) {
                  }

                  if (TxQueueFileImpl.this.isShutdown) {
                     break;
                  }

                  diff -= System.currentTimeMillis() - starttime;
               }

               if (TxQueueFileImpl.this.preparationQueue != null) {
                  try {
                     TxQueueFileImpl.this.write(TxQueueFileImpl.this.preparationQueue, TxQueueFileImpl.this.preparationInserts, TxQueueFileImpl.this.preparationNumRemoved, TxQueueFileImpl.this.prepareFile);
                     TxQueueFileImpl.this.preparationIOE = null;
                  } catch (IOException var10) {
                     TxQueueFileImpl.this.preparationIOE = var10;
                  }
               }

               synchronized(TxQueueFileImpl.this.prepareWriteMutex) {
                  TxQueueFileImpl.this.preparationQueue = null;
                  TxQueueFileImpl.this.prepareWriteMutex.notifyAll();
               }

               if (TxQueueFileImpl.this.isShutdown) {
                  return;
               }
            }
         }
      }

      public void write() {
         if (TxQueueFileImpl.this.isShutdown) {
            throw new RuntimeException("Queue has been shutdown");
         } else {
            synchronized(TxQueueFileImpl.this.prepareWriteMutex) {
               while(TxQueueFileImpl.this.preparationQueue != null) {
                  try {
                     TxQueueFileImpl.this.prepareWriteMutex.wait();
                  } catch (InterruptedException var4) {
                  }
               }

            }
         }
      }
   }
}
