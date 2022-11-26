package weblogic.persist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.TransactionRolledbackException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.jndi.Environment;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.utils.enumerations.BatchingEnumerationWrapper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.T3Srvr;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionManager;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.UnsyncHashtable;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class TxIndexedFileImpl implements TxIndexedFileRemote, XAResource, PlatformConstants {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean VERBOSE = false;
   int fullWriteInterval = Integer.getInteger("txindexedfile.fullwriteinterval", 100);
   static PrintStream ps;
   private String name;
   private String commitFilename;
   private String prepareFilename;
   private UnsyncHashtable database;
   private CommitThread commitThread;
   private PrepareThread prepareThread;
   private UnsyncHashtable bytes = new UnsyncHashtable();
   private ByteArrayOutputStream baos;
   private UnsyncHashtable enrolled = new UnsyncHashtable();
   private static Object exists = new Object();
   private UnsyncHashtable writeLocks = new UnsyncHashtable();
   private UnsyncHashtable changes = new UnsyncHashtable();
   private static Object removeObject = "\u00000\u00000\u00000\u00000";
   private int numWrites = 0;
   protected Object preparationMutex = new Object();
   protected Object commitMutex = new Object();
   protected UnsyncHashtable preparationChanges;
   protected IOException preparationIOE;
   protected UnsyncHashtable commitChanges;
   protected IOException commitIOE;
   protected File commitFile;
   protected File prepareFile;
   private static final int MIN_MILLIS_BETWEEN_WRITES = Integer.getInteger("txindexedfile.writemillis", 50);
   private Object prepareWriteMutex = new Object();
   private Object commitWriteMutex = new Object();
   protected boolean isShutdown = false;
   private static TransactionManager tms = null;

   public static void main(String[] args) {
      try {
         String name = args.length >= 1 ? args[0] : "DefaultStore";
         String actualDir = args.length >= 2 ? args[1] : ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         String tmpDir = args.length >= 3 ? args[2] : ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         TxIndexedFile phi = new TxIndexedFileStub(name, actualDir, tmpDir);

         try {
            Hashtable props = new Hashtable();
            props.put("weblogic.jndi.createIntermediateContexts", "true");
            Context ctx = new InitialContext(props);
            ctx.rebind(name, phi);
         } catch (NamingException var7) {
            T3Srvr.getT3Srvr().getLog().error("There was a communication problem -- this Impl must be in the server", var7);
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   private UnsyncHashtable readDatabase(File file) throws IOException, ClassNotFoundException {
      FileInputStream fis = null;
      MyObjectInputStream ois = null;
      UnsyncHashtable database = null;

      try {
         fis = new FileInputStream(file);
         ois = new MyObjectInputStream(fis);
         database = (UnsyncHashtable)ois.readObject();

         while(true) {
            try {
               ois = new MyObjectInputStream(fis);
               ois.readBoolean();
            } catch (IOException var11) {
               return database;
            }

            try {
               UnsyncHashtable changes = (UnsyncHashtable)ois.readObject();
               this.fillInDatabase(database, changes);
            } catch (IOException var10) {
               throw var10;
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

   private void loadDatabase(File actualDir, File tmpDir) throws IOException {
      this.database = new UnsyncHashtable();
      this.ensureDirectories(actualDir, tmpDir);
      this.commitFile = new File(this.commitFilename);
      this.prepareFile = new File(this.prepareFilename);
      if (this.commitFile.exists()) {
         if (this.prepareFile.exists() && this.prepareFile.lastModified() > this.commitFile.lastModified()) {
            try {
               this.database = this.readDatabase(this.prepareFile);
               return;
            } catch (Exception var7) {
            }
         }

         try {
            this.database = this.readDatabase(this.commitFile);
            return;
         } catch (Exception var6) {
            try {
               if (this.prepareFile.exists() && this.prepareFile.lastModified() < this.commitFile.lastModified()) {
                  this.database = this.readDatabase(this.prepareFile);
                  return;
               }
            } catch (Exception var5) {
               return;
            }
         }
      } else {
         try {
            if (this.prepareFile.exists()) {
               this.database = this.readDatabase(this.prepareFile);
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

   protected TxIndexedFileImpl(String name, String actualDir, String tmpDir) throws IOException {
      this.name = name;
      this.commitFilename = actualDir + File.separator + name + ".dat";
      this.prepareFilename = tmpDir + File.separator + name + "Tmp.dat";
      this.loadDatabase(new File(actualDir), new File(tmpDir));
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

   public void store(String key, Object object) throws IOException, TransactionRolledbackException {
      Transaction txc = null;

      try {
         txc = getTransaction();
      } catch (SystemException var7) {
         throw new TransactionRolledbackException("Could not obtain transaction:\n " + var7.getMessage());
      }

      if (txc == null) {
         try {
            getTransactionManager().begin("persist internal");
            txc = getTransaction();
            txc.enlistResource(this);
            this.write(txc.getXID(), key, object);
            txc.commit();
         } catch (Exception var6) {
            var6.printStackTrace();
            throw new TransactionRolledbackException("Could not complete transaction . . . Rolled back: " + var6.getMessage());
         }
      } else {
         try {
            synchronized(this) {
               if (this.enrolled.put(txc.getXID(), exists) == null) {
                  txc.enlistResource(this);
               }
            }
         } catch (Exception var9) {
            throw new TransactionRolledbackException("Could not enroll resource: " + var9.getMessage());
         }

         this.write(txc.getXID(), key, object);
      }

   }

   public Object retrieve(String key) {
      Transaction txc = null;

      try {
         txc = getTransaction();
      } catch (SystemException var4) {
         var4.printStackTrace();
         return null;
      }

      return this.read(txc == null ? null : txc.getXID(), key);
   }

   public Enumeration keys() {
      synchronized(this) {
         if (this.baos == null) {
            this.baos = new ByteArrayOutputStream();
         } else {
            this.baos.reset();
         }

         UnsyncHashtable frozenDatabase;
         try {
            ObjectOutputStream oos = new ObjectOutputStream(this.baos);
            oos.writeObject(this.database);
            oos.flush();
            oos.close();
            byte[] bytes = this.baos.toByteArray();
            MyObjectInputStream ois = new MyObjectInputStream(new ByteArrayInputStream(bytes));
            frozenDatabase = (UnsyncHashtable)ois.readObject();
            ois.close();
         } catch (Exception var7) {
            throw new IllegalArgumentException("Could not make clone of object: " + var7);
         }

         return new BatchingEnumerationWrapper(frozenDatabase.keys(), 10);
      }
   }

   private void write(Xid xid, String key, Object value) {
      synchronized(this.writeLocks) {
         Transaction txclock = (Transaction)this.writeLocks.get(key);

         while(true) {
            if (txclock == null || txclock.equals(xid)) {
               this.writeLocks.put(key, xid);
               break;
            }

            synchronized(txclock) {
               try {
                  txclock.wait(100L);
               } catch (InterruptedException var12) {
               }

               txclock = (Transaction)this.writeLocks.get(key);
            }
         }
      }

      synchronized(this.changes) {
         UnsyncHashtable keyValues = (UnsyncHashtable)this.changes.get(xid);
         if (keyValues == null) {
            keyValues = new UnsyncHashtable();
            this.changes.put(xid, keyValues);
         }

         if (value == null) {
            value = removeObject;
         }

         keyValues.put(key, value);
      }
   }

   private Object read(Xid xid, String key) {
      Xid txclock;
      synchronized(this.writeLocks) {
         txclock = (Xid)this.writeLocks.get(key);

         while(txclock != null && !txclock.equals(xid)) {
            synchronized(txclock) {
               try {
                  txclock.wait();
               } catch (InterruptedException var11) {
               }

               txclock = (Xid)this.writeLocks.get(key);
            }
         }
      }

      synchronized(this) {
         if (xid != null && xid.equals(txclock)) {
            UnsyncHashtable keyValues = (UnsyncHashtable)this.changes.get(xid);
            if (keyValues != null) {
               Object value = keyValues.get(key);
               if (value == removeObject) {
                  return null;
               }

               if (value != null) {
                  return value;
               }
            }
         }

         return this.database.get(key);
      }
   }

   private synchronized void releaseLocks(Xid xid) {
      this.enrolled.remove(xid);
      this.bytes.remove(xid);
      UnsyncHashtable keyValues = (UnsyncHashtable)this.changes.remove(xid);
      Enumeration e = keyValues.keys();

      while(e.hasMoreElements()) {
         String key = (String)e.nextElement();
         Object txclock = this.writeLocks.remove(key);
         if (txclock != null && !xid.equals(txclock)) {
            synchronized(txclock) {
               txclock.notifyAll();
            }
         }
      }

      synchronized(xid) {
         xid.notifyAll();
      }
   }

   protected void write(UnsyncHashtable changes, File file) throws IOException {
      int halfWrites = this.numWrites++ >> 2;
      boolean append = halfWrites % this.fullWriteInterval != 0;
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      if (append) {
         oos.writeBoolean(true);
         oos.writeObject(changes);
      } else {
         UnsyncHashtable newDatabase;
         synchronized(this) {
            newDatabase = (UnsyncHashtable)this.database.clone();
         }

         this.fillInDatabase(newDatabase, changes);
         oos.writeObject(newDatabase);
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
      try {
         synchronized(this.preparationMutex) {
            synchronized(this.prepareWriteMutex) {
               if (this.preparationChanges == null) {
                  this.preparationChanges = new UnsyncHashtable();
               }

               this.copy(this.preparationChanges, (UnsyncHashtable)this.changes.get(xid));
            }
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      this.prepareThread.write();
      if (this.preparationIOE != null) {
         throw new XAException(StackTraceUtils.throwable2StackTrace(this.preparationIOE));
      } else {
         return 0;
      }
   }

   public void rollback(Xid xid) throws XAException {
      this.commitThread.write();
      this.releaseLocks(xid);
   }

   public void commit(Xid xid, boolean onePhase) throws XAException {
      if (onePhase) {
         try {
            int status = this.prepare(xid);
            if (status == 0) {
               this.commit(xid);
            }
         } catch (XAException var4) {
            this.decipherFailure("prepare", this.prepareFilename, (IOException)null);
         }

      } else {
         this.commit(xid);
      }
   }

   public void commit(Xid xid) throws XAException {
      synchronized(this.commitMutex) {
         synchronized(this.commitWriteMutex) {
            if (this.commitChanges == null) {
               this.commitChanges = new UnsyncHashtable();
            }

            this.copy(this.commitChanges, (UnsyncHashtable)this.changes.get(xid));
         }
      }

      this.commitThread.write();
      if (this.commitIOE != null) {
         this.decipherFailure("commit", this.commitFilename, this.commitIOE);
      }

      this.fillInDatabase(this.database, xid);
      this.releaseLocks(xid);
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
         throw new XAException(prefix + "Possible problems: " + EOL + "  Disk full" + EOL + "  No available file descriptors (Unix)" + EOL + "  File owned by different process (NT)" + EOL + "  Hardware error");
      }
   }

   private void fillInDatabase(UnsyncHashtable ht, Xid xid) {
      this.fillInDatabase(ht, (UnsyncHashtable)null, xid);
   }

   private void fillInDatabase(UnsyncHashtable ht, UnsyncHashtable newchanges, Xid xid) {
      synchronized(this) {
         UnsyncHashtable keyValues = (UnsyncHashtable)this.changes.get(xid);
         Enumeration e = keyValues.keys();

         while(e.hasMoreElements()) {
            String key = (String)e.nextElement();
            Object value = keyValues.get(key);
            if (newchanges != null) {
               newchanges.put(key, value);
            }

            if (value.equals(removeObject)) {
               ht.remove(key);
            } else {
               ht.put(key, value);
            }
         }

      }
   }

   private void fillInDatabase(UnsyncHashtable ht, UnsyncHashtable changes) {
      Enumeration e = changes.keys();

      while(e.hasMoreElements()) {
         String key = (String)e.nextElement();
         Object value = changes.get(key);
         if (value.equals(removeObject)) {
            ht.remove(key);
         } else {
            ht.put(key, value);
         }
      }

   }

   private void copy(UnsyncHashtable ht, UnsyncHashtable changes) {
      Enumeration e = changes.keys();

      while(e.hasMoreElements()) {
         String key = (String)e.nextElement();
         Object value = changes.get(key);
         ht.put(key, value);
      }

   }

   private static Transaction getTransaction() throws SystemException {
      return (Transaction)getTransactionManager().getTransaction();
   }

   private static TransactionManager getTransactionManager() throws SystemException {
      if (tms == null) {
         Class var0 = TxIndexedFileImpl.class;
         synchronized(TxIndexedFileImpl.class) {
            if (tms == null) {
               try {
                  Environment env = new Environment();
                  env.setCreateIntermediateContexts(true);
                  Context context = env.getInitialContext();
                  tms = (TransactionManager)context.lookup("weblogic.transaction.TransactionManager");
               } catch (NamingException var4) {
                  throw new SystemException("Naming lookup problem: " + StackTraceUtils.throwable2StackTrace(var4));
               }
            }
         }
      }

      return tms;
   }

   private class CommitThread extends Thread {
      private int writeCalled = 0;
      private int writeDone = 0;

      public CommitThread(String name) {
         super(name);
      }

      public void run() {
         while(true) {
            synchronized(TxIndexedFileImpl.this.commitMutex) {
               long diff = (long)TxIndexedFileImpl.MIN_MILLIS_BETWEEN_WRITES;

               while(true) {
                  if (TxIndexedFileImpl.this.commitChanges == null) {
                     diff = (long)TxIndexedFileImpl.MIN_MILLIS_BETWEEN_WRITES;
                  } else if (diff <= 0L) {
                     break;
                  }

                  long starttime = System.currentTimeMillis();

                  try {
                     TxIndexedFileImpl.this.commitMutex.wait(diff);
                  } catch (InterruptedException var11) {
                  }

                  if (TxIndexedFileImpl.this.isShutdown) {
                     break;
                  }

                  diff -= System.currentTimeMillis() - starttime;
               }

               if (TxIndexedFileImpl.this.commitChanges != null) {
                  try {
                     TxIndexedFileImpl.this.write(TxIndexedFileImpl.this.commitChanges, TxIndexedFileImpl.this.commitFile);
                     TxIndexedFileImpl.this.commitIOE = null;
                  } catch (IOException var10) {
                     TxIndexedFileImpl.this.commitIOE = var10;
                  }
               }

               synchronized(TxIndexedFileImpl.this.commitWriteMutex) {
                  TxIndexedFileImpl.this.commitChanges = null;
                  TxIndexedFileImpl.this.commitWriteMutex.notifyAll();
               }

               if (TxIndexedFileImpl.this.isShutdown) {
                  return;
               }
            }
         }
      }

      public void write() {
         if (TxIndexedFileImpl.this.isShutdown) {
            throw new RuntimeException("Database has been shutdown");
         } else {
            synchronized(TxIndexedFileImpl.this.commitWriteMutex) {
               while(TxIndexedFileImpl.this.commitChanges != null) {
                  try {
                     TxIndexedFileImpl.this.commitWriteMutex.wait();
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
            synchronized(TxIndexedFileImpl.this.preparationMutex) {
               long diff = (long)TxIndexedFileImpl.MIN_MILLIS_BETWEEN_WRITES;

               while(true) {
                  if (TxIndexedFileImpl.this.preparationChanges == null) {
                     diff = (long)TxIndexedFileImpl.MIN_MILLIS_BETWEEN_WRITES;
                  } else if (diff <= 0L) {
                     break;
                  }

                  long starttime = System.currentTimeMillis();

                  try {
                     TxIndexedFileImpl.this.preparationMutex.wait(diff);
                  } catch (InterruptedException var11) {
                  }

                  if (TxIndexedFileImpl.this.isShutdown) {
                     break;
                  }

                  diff -= System.currentTimeMillis() - starttime;
               }

               if (TxIndexedFileImpl.this.preparationChanges != null) {
                  try {
                     TxIndexedFileImpl.this.write(TxIndexedFileImpl.this.preparationChanges, TxIndexedFileImpl.this.prepareFile);
                     TxIndexedFileImpl.this.preparationIOE = null;
                  } catch (IOException var10) {
                     TxIndexedFileImpl.this.preparationIOE = var10;
                  }
               }

               synchronized(TxIndexedFileImpl.this.prepareWriteMutex) {
                  TxIndexedFileImpl.this.preparationChanges = null;
                  TxIndexedFileImpl.this.prepareWriteMutex.notifyAll();
               }

               if (TxIndexedFileImpl.this.isShutdown) {
                  return;
               }
            }
         }
      }

      public void write() {
         if (TxIndexedFileImpl.this.isShutdown) {
            throw new RuntimeException("Database has been shutdown");
         } else {
            synchronized(TxIndexedFileImpl.this.prepareWriteMutex) {
               while(TxIndexedFileImpl.this.preparationChanges != null) {
                  try {
                     TxIndexedFileImpl.this.prepareWriteMutex.wait();
                  } catch (InterruptedException var4) {
                  }
               }

            }
         }
      }
   }

   private static class MyObjectInputStream extends ObjectInputStream {
      public MyObjectInputStream(InputStream is) throws IOException, StreamCorruptedException {
         super(is);
      }

      protected Class resolveClass(ObjectStreamClass clazz) throws IOException, ClassNotFoundException {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         return cl.loadClass(clazz.getName());
      }
   }
}
