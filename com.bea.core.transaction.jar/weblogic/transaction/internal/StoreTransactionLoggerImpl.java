package weblogic.transaction.internal;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.transaction.TransactionLoggable;
import weblogic.transaction.TransactionLogger;
import weblogic.transaction.utils.PrintBox;

public class StoreTransactionLoggerImpl implements TransactionLogger {
   private static final String STORE_CONNECTION_NAME = "weblogic.transaction.";
   private static final int NO_FLAGS = 0;
   private PersistentStore store;
   private PersistentStoreConnection storeConn;
   private final Map handleMap;
   private PersistentStoreTransaction ptx;
   private int numTransactions;
   private PrintBox dumpster;
   private String migratedCoordinatorURL;
   private int initialRecoveredTransactionTotalCount;
   private int recoveredTransactionCompletionCount;
   private JTAHealthListener healthListener;
   private boolean healthy;
   boolean jdbcStoreHealthy;
   Object jdbcStoreRecoveryLock;
   Object jdbcStoreRecoveryRunLock;
   volatile boolean isJDBCRecoveryScheduledOrRunning;
   private int currentSequenceNumber;
   private Set outstandingRequests;
   private final Object outstandingRequestLock;
   private final Object ptxLock;
   static final String PING = "~ping";
   public String serverNameForCrossSiteRecovery;

   public StoreTransactionLoggerImpl(boolean hlog, JTAHealthListener healthListener) throws IOException {
      this.handleMap = Collections.synchronizedMap(new IdentityHashMap());
      this.dumpster = null;
      this.initialRecoveredTransactionTotalCount = 0;
      this.recoveredTransactionCompletionCount = 0;
      this.healthy = true;
      this.jdbcStoreHealthy = true;
      this.jdbcStoreRecoveryLock = new Object();
      this.jdbcStoreRecoveryRunLock = new Object();
      this.isJDBCRecoveryScheduledOrRunning = false;
      this.outstandingRequests = new HashSet();
      this.outstandingRequestLock = new Object();
      this.ptxLock = new Object();
      this.init(hlog, healthListener);
   }

   StoreTransactionLoggerImpl(PrintStream dumpStream, boolean output) {
      this.handleMap = Collections.synchronizedMap(new IdentityHashMap());
      this.dumpster = null;
      this.initialRecoveredTransactionTotalCount = 0;
      this.recoveredTransactionCompletionCount = 0;
      this.healthy = true;
      this.jdbcStoreHealthy = true;
      this.jdbcStoreRecoveryLock = new Object();
      this.jdbcStoreRecoveryRunLock = new Object();
      this.isJDBCRecoveryScheduledOrRunning = false;
      this.outstandingRequests = new HashSet();
      this.outstandingRequestLock = new Object();
      this.ptxLock = new Object();
      if (output) {
         this.dumpster = new PrintBox(dumpStream);
         this.dumpster.setTitle("Transaction Log Dump");
         this.dumpster.add("Current time", "" + new Date());
         this.dumpster.add("Current directory", "\"" + (new File(".")).getAbsolutePath() + "\"");
         this.dumpster.print();
      }

      try {
         this.init(false, this.healthListener);
         if (output) {
            this.dumpster.add("Store connection", this.storeConn.toString());
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         if (output) {
            this.dumpster.setTitle("Exception Report");
            this.dumpster.add("Exception", var4.toString());
            this.dumpster.print();
         }
      }

   }

   public StoreTransactionLoggerImpl(String serverName, String coordinatorURL) throws IOException, Exception {
      this(serverName, coordinatorURL, true);
   }

   public StoreTransactionLoggerImpl(String serverName, String coordinatorURL, boolean isRecovery) throws Exception {
      this.handleMap = Collections.synchronizedMap(new IdentityHashMap());
      this.dumpster = null;
      this.initialRecoveredTransactionTotalCount = 0;
      this.recoveredTransactionCompletionCount = 0;
      this.healthy = true;
      this.jdbcStoreHealthy = true;
      this.jdbcStoreRecoveryLock = new Object();
      this.jdbcStoreRecoveryRunLock = new Object();
      this.isJDBCRecoveryScheduledOrRunning = false;
      this.outstandingRequests = new HashSet();
      this.outstandingRequestLock = new Object();
      this.ptxLock = new Object();
      if (!isRecovery) {
         PlatformHelper.getPlatformHelper().getStore(serverName, "~ping");
      } else {
         this.migratedCoordinatorURL = coordinatorURL;
         PersistentStore store = (PersistentStore)PlatformHelper.getPlatformHelper().getStore(serverName, coordinatorURL);
         this.init(false, store, (JTAHealthListener)null, serverName);
      }
   }

   public StoreTransactionLoggerImpl(PersistentStore store) throws IOException {
      this.handleMap = Collections.synchronizedMap(new IdentityHashMap());
      this.dumpster = null;
      this.initialRecoveredTransactionTotalCount = 0;
      this.recoveredTransactionCompletionCount = 0;
      this.healthy = true;
      this.jdbcStoreHealthy = true;
      this.jdbcStoreRecoveryLock = new Object();
      this.jdbcStoreRecoveryRunLock = new Object();
      this.isJDBCRecoveryScheduledOrRunning = false;
      this.outstandingRequests = new HashSet();
      this.outstandingRequestLock = new Object();
      this.ptxLock = new Object();
      this.init(store, false);
   }

   private void init(boolean hlog, JTAHealthListener healthListener) throws IOException {
      PersistentStore store = (PersistentStore)PlatformHelper.getPlatformHelper().getPrimaryStore();
      this.init(hlog, store, healthListener, (String)null);
   }

   private void init(boolean hlog, PersistentStore store, JTAHealthListener healthListener, String serverNameForCrossSiteRecovery) throws IOException {
      this.healthListener = healthListener;
      this.store = store;
      this.ptx = store.begin();
      this.init(store, hlog);
      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug(this.getURL() + "TLOG using store: " + store + " serverNameForCrossSiteRecovery:" + serverNameForCrossSiteRecovery);
      }

      try {
         this.recover(serverNameForCrossSiteRecovery);
      } catch (Exception var7) {
         IOException ioe = new IOException(var7.getMessage());
         ioe.initCause(var7);
         throw ioe;
      }
   }

   private void init(PersistentStore store, boolean hlog) throws IOException {
      this.store = store;
      String connName = "weblogic.transaction." + (hlog ? "hlog" : "tlog");

      try {
         this.storeConn = store.createConnection(connName, new JTAObjectHandler());
      } catch (Exception var6) {
         IOException ioe = new IOException(var6.getMessage());
         ioe.initCause(var6);
         throw ioe;
      }
   }

   public void close() throws Exception {
      PlatformHelper.getPlatformHelper().closeStore(this.store);
   }

   public String getMigratedCoordinatorURL() {
      return this.migratedCoordinatorURL;
   }

   public boolean hasTransactionLogRecords() {
      return this.numTransactions > 0;
   }

   public int getInitialRecoveredTransactionTotalCount() {
      return this.initialRecoveredTransactionTotalCount;
   }

   public int getRecoveredTransactionCompletionCount() {
      return this.recoveredTransactionCompletionCount;
   }

   public synchronized void store(TransactionLoggable obj) {
      this.waitForPossibleJDBCStoreRecovery(obj);
      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug(this.getURL() + "TLOG storing object=" + obj);
      }

      try {
         this.doStore(obj);
      } catch (RuntimeException var3) {
         this.reportFailure(obj, var3);
         throw var3;
      } catch (Error var4) {
         this.reportFailure(obj, var4);
         throw var4;
      }
   }

   void doStore(TransactionLoggable obj) {
      PersistentStoreTransaction ptxCopy;
      synchronized(this.ptxLock) {
         ptxCopy = this.ptx;
         this.ptx = this.store.begin();
      }

      LogEntry logEntry = new LogEntry(this.nextSequenceNumber(), obj);
      PersistentHandle handle = this.storeConn.create(ptxCopy, logEntry, 0);
      ++this.numTransactions;
      JTACompletionRequest cr = new JTACompletionRequest(obj, handle);
      synchronized(this.outstandingRequestLock) {
         cr.runListenersInSetResult(true);
         this.outstandingRequests.add(cr);
         cr.setOutstandingRequests(this.outstandingRequests);
      }

      ptxCopy.commit(cr);
   }

   public void store(List transactionLoggables) throws Exception {
      PersistentStoreTransaction ptx = this.store.begin();
      Iterator i = transactionLoggables.iterator();

      while(i.hasNext()) {
         TransactionLoggable obj = (TransactionLoggable)i.next();
         if (TxDebug.JTATLOG.isDebugEnabled()) {
            TxDebug.JTATLOG.debug(this.getURL() + "TLOG storing object=" + obj);
         }

         LogEntry logEntry = new LogEntry(this.nextSequenceNumber(), obj);
         PersistentHandle handle = this.storeConn.create(ptx, logEntry, 0);
         logEntry.setHandle(handle);
      }

      ptx.commit();
   }

   public void release(TransactionLoggable obj) {
      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug(this.getURL() + "TLOG releasing object=" + obj);
      }

      PersistentHandle handle = (PersistentHandle)this.handleMap.remove(obj);
      if (handle == null) {
         if (TxDebug.JTATLOG.isDebugEnabled()) {
            TxDebug.JTATLOG.debug(this.getURL() + "No mapping found for: " + obj);
         }

      } else {
         try {
            synchronized(this.ptxLock) {
               this.storeConn.delete(this.ptx, handle, 0);
            }
         } catch (RuntimeException var6) {
            this.reportFailure(obj, var6);
            throw var6;
         } catch (Error var7) {
            this.reportFailure(obj, var7);
            throw var7;
         }

         if (obj instanceof ServerTransactionImpl) {
            --this.numTransactions;
            if (((ServerTransactionImpl)obj).isRecoveredTransaction()) {
               ++this.recoveredTransactionCompletionCount;
            }
         }

      }
   }

   public synchronized void checkpoint() {
      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug(this.getURL() + "TLOG checkpoint starting");
      }

      Set currentOutstandingRequests;
      synchronized(this.outstandingRequestLock) {
         currentOutstandingRequests = this.outstandingRequests;
         this.outstandingRequests = new HashSet();
      }

      try {
         PersistentStoreTransaction ptxCopy;
         synchronized(this.ptxLock) {
            ptxCopy = this.ptx;
            this.ptx = this.store.begin();
         }

         try {
            ptxCopy.commit();
         } catch (Exception var9) {
            if (TxDebug.JTATLOG.isDebugEnabled()) {
               TxDebug.JTATLOG.debug(this.getURL() + "Exception checkpointing disk: " + var9);
            }

            this.reportFailure(var9);
         }

         synchronized(this.outstandingRequestLock) {
            while(currentOutstandingRequests.size() > 0) {
               try {
                  this.outstandingRequestLock.wait();
               } catch (InterruptedException var6) {
               }
            }
         }
      } catch (RuntimeException var11) {
         this.reportFailure(var11);
         throw var11;
      } catch (Error var12) {
         this.reportFailure(var12);
         throw var12;
      }

      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug(this.getURL() + "TLOG checkpoint ending");
      }

   }

   private synchronized int nextSequenceNumber() {
      return this.currentSequenceNumber++;
   }

   private void recover(String serverNameForCrossSiteRecovery) throws Exception {
      TxDebug.JTATLOG.debug("StoreTransactionLoggerImpl.recover serverNameForCrossSiteRecovery:" + serverNameForCrossSiteRecovery);
      this.serverNameForCrossSiteRecovery = serverNameForCrossSiteRecovery;
      PersistentStoreConnection.Cursor cursor = this.storeConn.createCursor(0);
      ArrayList records = new ArrayList();

      PersistentStoreRecord rec;
      while((rec = cursor.next()) != null) {
         LogEntry logEntry = (LogEntry)rec.getData();
         if (logEntry.getSequenceNumber() >= this.currentSequenceNumber) {
            this.currentSequenceNumber = logEntry.getSequenceNumber() + 1;
         }

         logEntry.setHandle(rec.getHandle());
         records.add(logEntry);
      }

      if (this.dumpster == null) {
         Collections.sort(records);
         Iterator var11 = records.iterator();

         while(var11.hasNext()) {
            Object record = var11.next();
            LogEntry logEntry = (LogEntry)record;
            TransactionLoggable obj = logEntry.getObj();
            this.handleMap.put(obj, logEntry.getHandle());

            try {
               boolean isTx = obj instanceof ServerTransactionImpl;
               if (this.migratedCoordinatorURL == null || isTx || obj instanceof ResourceCheckpoint || obj instanceof ServerCheckpoint) {
                  TxDebug.JTATLOG.debug("StoreTransactionLoggerImpl.recover obj migratedCoordinatorURL:" + this.migratedCoordinatorURL + " isTx:" + isTx + " (obj instanceof ResourceCheckpoint):" + (obj instanceof ResourceCheckpoint));
                  obj.onRecovery(this);
               }

               if (isTx) {
                  ++this.numTransactions;
                  ++this.initialRecoveredTransactionTotalCount;
               }
            } catch (Exception var10) {
               TXLogger.logTLOGOnRecoveryException(var10);
            }
         }

      }
   }

   private void reportFailure(TransactionLoggable obj, Throwable t) {
      TXLogger.logTLOGWriteError(t);
      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug(this.getURL() + "TLOG error processing log record, obj=" + obj, t);
      }

      try {
         obj.onError(this);
      } catch (Exception var4) {
         TXLogger.logTLOGOnErrorException(var4);
      }

      this.recoverStore(obj, t);
   }

   void waitForPossibleJDBCStoreRecovery(TransactionLoggable transactionLoggable) {
      if (!this.jdbcStoreHealthy) {
         TransactionManagerImpl transactionManager = TransactionManagerImpl.getTransactionManager();
         synchronized(this.jdbcStoreRecoveryLock) {
            if (!this.jdbcStoreHealthy) {
               try {
                  this.jdbcStoreRecoveryLock.wait((long)(transactionManager.getJdbcTLogMaxRetrySecondsBeforeTXException() * 1000));
               } catch (InterruptedException var6) {
                  var6.printStackTrace();
               }

            }
         }
      }
   }

   void recoverStore(TransactionLoggable obj, Throwable t) {
      TransactionManagerImpl transactionManager = TransactionManagerImpl.getTransactionManager();
      if (!transactionManager.getJdbcTLogEnabled()) {
         if (this.healthListener != null && this.healthy) {
            this.healthListener.healthEvent(new HealthEvent(1, (String)null, "Unable to access transaction log. Error is " + t.getMessage()));
            this.healthy = false;
         }
      } else {
         this.recoverJDBCStore(obj, t, transactionManager);
      }

   }

   private void recoverJDBCStore(final TransactionLoggable obj, final Throwable t, final TransactionManagerImpl transactionManager) {
      if (!this.isJDBCRecoveryScheduledOrRunning) {
         synchronized(this.jdbcStoreRecoveryRunLock) {
            if (!this.isJDBCRecoveryScheduledOrRunning) {
               Runnable work = new Runnable() {
                  public void run() {
                     try {
                        this.doRun();
                     } finally {
                        StoreTransactionLoggerImpl.this.isJDBCRecoveryScheduledOrRunning = false;
                     }

                  }

                  public void doRun() {
                     StoreTransactionLoggerImpl.this.healthListener.healthEvent(new HealthEvent(1, "JDBCSTORE_FAILURE", "JDBC store transaction log failed, attempting recovery while processing record " + obj + " of type " + (obj == null ? "null" : obj.getClass().getName()) + ". Error is " + t.getMessage()));
                     StoreTransactionLoggerImpl.this.jdbcStoreHealthy = false;
                     long recoveryStartTime = System.currentTimeMillis();
                     synchronized(StoreTransactionLoggerImpl.this.jdbcStoreRecoveryLock) {
                        int jdbcTLogMaxRetrySecondsBeforeTLOGFailInMillis = this.getJdbcTLogMaxRetrySecondsBeforeTLOGFailInMillis();

                        do {
                           StoreTransactionLoggerImpl.this.jdbcStoreHealthy = PlatformHelper.getPlatformHelper().openPrimaryStore(true);

                           try {
                              if (!StoreTransactionLoggerImpl.this.jdbcStoreHealthy) {
                                 StoreTransactionLoggerImpl.this.jdbcStoreRecoveryLock.wait((long)(transactionManager.getJdbcTLogRetryIntervalSeconds() * 1000));
                              }
                           } catch (InterruptedException var9) {
                           }
                        } while(!StoreTransactionLoggerImpl.this.jdbcStoreHealthy && System.currentTimeMillis() <= recoveryStartTime + (long)jdbcTLogMaxRetrySecondsBeforeTLOGFailInMillis);

                        if (StoreTransactionLoggerImpl.this.jdbcStoreHealthy) {
                           StoreTransactionLoggerImpl.this.healthListener.healthEvent(new HealthEvent(2, "JDBCSTORE_RECOVERED", "JDBC store transaction log recovered while processing record of type " + obj.getClass().getName() + ". Error is " + t.getMessage()));

                           try {
                              List rdList = ResourceDescriptor.getAllResources();
                              Iterator it = rdList.iterator();

                              while(it.hasNext()) {
                                 ResourceDescriptor rd = (ResourceDescriptor)it.next();
                                 rd.setIsResourceCheckpointNeeded(true);
                              }

                              ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).setServerCheckpointNeeded(true);
                              ResourceDescriptor.setLatestResourceCheckpoint((ResourceCheckpoint)null);
                              ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).setLatestServerCheckpoint((ServerCheckpoint)null);
                              StoreTransactionLoggerImpl.this.init(false, StoreTransactionLoggerImpl.this.healthListener);
                              StoreTransactionLoggerImpl.this.jdbcStoreRecoveryLock.notifyAll();
                              PlatformHelper.getPlatformHelper().setServerResume();
                           } catch (Exception var10) {
                              TXLogger.logJDBCStoreRecoveryInitException(var10);
                           }
                        } else if (StoreTransactionLoggerImpl.this.healthListener != null && StoreTransactionLoggerImpl.this.healthy) {
                           StoreTransactionLoggerImpl.this.healthListener.healthEvent(new HealthEvent(1, (String)null, "Unable to recover JDBC store for transaction log after processing record of type " + obj.getClass().getName() + ". Error is " + t.getMessage()));
                        }

                     }
                  }

                  private int getJdbcTLogMaxRetrySecondsBeforeTLOGFailInMillis() {
                     int jdbcTLogMaxRetrySecondsBeforeTLOGFail = transactionManager.getJdbcTLogMaxRetrySecondsBeforeTLOGFail();
                     if (jdbcTLogMaxRetrySecondsBeforeTLOGFail > 2147483) {
                        jdbcTLogMaxRetrySecondsBeforeTLOGFail = Integer.MAX_VALUE;
                     } else {
                        jdbcTLogMaxRetrySecondsBeforeTLOGFail *= 1000;
                     }

                     return jdbcTLogMaxRetrySecondsBeforeTLOGFail;
                  }

                  public String toString() {
                     return "Attempting recovery of JDBC TLog store";
                  }
               };
               PlatformHelper.getPlatformHelper().scheduleWork(work);
               this.isJDBCRecoveryScheduledOrRunning = true;
            }
         }
      }
   }

   private void reportFailure(Throwable t) {
      TXLogger.logTLOGWriteError(t);
      this.recoverStore((TransactionLoggable)null, t);
   }

   private void reportSuccess(TransactionLoggable obj) {
      if (this.healthListener != null && !this.healthy) {
         this.healthListener.healthEvent(new HealthEvent(2, (String)null, "Successed to write transaction log while processing record of type " + obj.getClass().getName()));
         this.healthy = true;
      }

   }

   private String getURL() {
      String url = "";
      if (this.getMigratedCoordinatorURL() != null) {
         url = "[" + this.getMigratedCoordinatorURL() + "] ";
      }

      return url;
   }

   long delete() throws Exception {
      long before = this.storeConn.getStatistics().getObjectCount();
      this.storeConn.delete();
      long after = this.storeConn.getStatistics().getObjectCount();
      return before - after;
   }

   public static void main(String[] args) throws Exception {
      boolean delete = false;
      String tlogPath = "";
      String serverName = "";
      String rootPath = "";
      String tableRef = "";
      String dataSourceName = "";
      String tlogStore = "";
      if (args.length != 2 && args.length != 3 && args.length != 5) {
         System.out.println("Usage:\njava weblogic.transaction.internal.StoreTransactionLoggerImpl [-delete] <server_tlog_path> <server_name>\n  or:\njava weblogic.transaction.internal.StoreTransactionLoggerImpl -JDBCTLOG <server_name> <domainRoot_path> <dataSource_name> <table_reference>\n\n  -delete - remove all entries from the transaction log\n  -JDBCTLOG - dump JDBC transaction log\n\n  <server_tlog_path> - path of transaction log store\n    e.g. /weblogic/config/mydomain/myserver/myserver\n  <server_name> - name of server instance\n    e.g. myserver\n  <domainRoot_path> - path of domain\n    e.g. /home/base_domain\n  <dataSource_name> - pool name of JDBC TLOG store\n    e.g. OracleDS\n  <table_reference> - name of JDBC TLOG store table\n    e.g. MYSERVERWLSTORE\nExamples:\n    java weblogic.transaction.internal.StoreTransactionLoggerImpl /weblogic/mydomain/servers/myserver/data/store/default myserver\n    java weblogic.transaction.internal.StoreTransactionLoggerImpl -delete /weblogic/mydomain/servers/myserver/data/store/default myserver\n    java weblogic.transaction.internal.StoreTransactionLoggerImpl -JDBCTLOG myserver /weblogic/mydomain OracleDS MYSERVERWLSTORE\n");
         System.exit(1);
      }

      switch (args.length) {
         case 2:
            tlogPath = args[0];
            serverName = args[1];
            break;
         case 3:
            if (!args[0].equalsIgnoreCase("-delete")) {
               throw new IllegalArgumentException("Invalid number of arguments: " + args.length + ", or unknown option: " + args[0]);
            }

            delete = true;
            tlogPath = args[1];
            serverName = args[2];
            break;
         case 4:
         default:
            throw new IllegalArgumentException("Invalid number of arguments: " + args.length + ", or unknown option: " + args[0]);
         case 5:
            if (!args[0].equalsIgnoreCase("-JDBCTLOG")) {
               throw new IllegalArgumentException("Invalid number of arguments: " + args.length + ", or unknown option: " + args[0]);
            }

            serverName = args[1];
            rootPath = args[2];
            dataSourceName = args[3];
            tableRef = args[4];
      }

      if (args.length != 5) {
         tlogStore = tlogPath + "/_WLS_" + serverName.toUpperCase() + "000000.DAT";
         if (!(new File(tlogStore)).exists()) {
            throw new IllegalArgumentException("TLOG store not found: " + tlogStore);
         }
      }

      if (args[0].equalsIgnoreCase("-JDBCTLOG")) {
         PlatformHelper.getPlatformHelper().dumpJDBCTLOG(serverName, rootPath, dataSourceName, tableRef);
      } else {
         PlatformHelper.getPlatformHelper().dumpTLOG(tlogPath, serverName, delete);
      }

      System.exit(0);
   }

   PersistentStore getStore() {
      return this.store;
   }

   private final class JTAObjectHandler implements ObjectHandler {
      private JTAObjectHandler() {
      }

      public void writeObject(ObjectOutput out, Object o) throws IOException {
         if (!(o instanceof LogEntry)) {
            throw new IOException("Cannot serialize class of type: " + o == null ? null : o.getClass().toString());
         } else {
            StoreLogDataOutputImpl encoder = new StoreLogDataOutputImpl(out);
            LogEntry logEntry = (LogEntry)o;
            TransactionLoggable obj = logEntry.getObj();
            String className = obj.getClass().getName();

            try {
               encoder.writeInt(logEntry.getSequenceNumber());
            } catch (IOException var10) {
               TXLogger.logTLOGRecordEncodingError(className, var10);
               throw var10;
            }

            try {
               encoder.writeUTF(className);
            } catch (IOException var9) {
               TXLogger.logTLOGRecordClassNameError(className, var9);
               throw var9;
            }

            try {
               obj.writeExternal(encoder);
            } catch (IOException var8) {
               TXLogger.logTLOGRecordEncodingError(className, var8);
               throw var8;
            }

            if (TxDebug.JTATLOG.isDebugEnabled()) {
               TxDebug.JTATLOG.debug(StoreTransactionLoggerImpl.this.getURL() + "TLOG writing log record, class=" + className + ", obj=" + obj);
            }

         }
      }

      public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
         StoreLogDataInputImpl decoder = new StoreLogDataInputImpl(in);
         int sequenceNumber = decoder.readInt();
         String className = decoder.readUTF();
         if (StoreTransactionLoggerImpl.this.dumpster != null) {
            StoreTransactionLoggerImpl.this.dumpster.add("Class Name", className);
         }

         TransactionLoggable obj;
         try {
            obj = (TransactionLoggable)Class.forName(className).newInstance();
         } catch (Exception var7) {
            TXLogger.logTLOGRecordClassInstantiationException(className, var7);
            if (StoreTransactionLoggerImpl.this.dumpster != null) {
               StoreTransactionLoggerImpl.this.dumpster.add("Exception", var7.toString());
               StoreTransactionLoggerImpl.this.dumpster.print();
               return null;
            }

            throw new RuntimeException(var7);
         }

         obj.readExternal(decoder);
         LogEntry logEntry = new LogEntry(sequenceNumber, obj);
         if (TxDebug.JTATLOG.isDebugEnabled()) {
            TxDebug.JTATLOG.debug(StoreTransactionLoggerImpl.this.getURL() + "TLOG read log record, obj=" + obj);
         }

         if (StoreTransactionLoggerImpl.this.dumpster != null) {
            StoreTransactionLoggerImpl.this.dumpster.add("Object", obj.toString());
            StoreTransactionLoggerImpl.this.dumpster.print();
         }

         return logEntry;
      }

      // $FF: synthetic method
      JTAObjectHandler(Object x1) {
         this();
      }
   }

   private final class JTACompletionRequest extends CompletionRequest implements CompletionListener {
      private final TransactionLoggable tl;
      private Set outstandingRequests;
      private final PersistentHandle handle;

      private JTACompletionRequest(TransactionLoggable tl, PersistentHandle handle) {
         this.tl = tl;
         this.handle = handle;
         this.addListener(this);
      }

      private void setOutstandingRequests(Set outstandingRequests) {
         this.outstandingRequests = outstandingRequests;
      }

      private void removeFromOutstandingRequests() {
         synchronized(StoreTransactionLoggerImpl.this.outstandingRequestLock) {
            this.outstandingRequests.remove(this);
            StoreTransactionLoggerImpl.this.outstandingRequestLock.notify();
         }
      }

      public void onCompletion(CompletionRequest request, Object result) {
         StoreTransactionLoggerImpl.this.handleMap.put(this.tl, this.handle);
         this.tl.onDisk(StoreTransactionLoggerImpl.this);
         this.removeFromOutstandingRequests();
         StoreTransactionLoggerImpl.this.reportSuccess(this.tl);
      }

      public void onException(CompletionRequest request, Throwable reason) {
         this.removeFromOutstandingRequests();
         StoreTransactionLoggerImpl.this.reportFailure(this.tl, reason);
      }

      // $FF: synthetic method
      JTACompletionRequest(TransactionLoggable x1, PersistentHandle x2, Object x3) {
         this(x1, x2);
      }
   }

   private static final class LogEntry implements Comparable {
      private final int sequenceNumber;
      private final TransactionLoggable obj;
      private PersistentHandle handle;

      private LogEntry(int sequenceNumber, TransactionLoggable obj) {
         this.sequenceNumber = sequenceNumber;
         this.obj = obj;
      }

      private int getSequenceNumber() {
         return this.sequenceNumber;
      }

      private TransactionLoggable getObj() {
         return this.obj;
      }

      private void setHandle(PersistentHandle handle) {
         this.handle = handle;
      }

      private PersistentHandle getHandle() {
         return this.handle;
      }

      public int compareTo(Object o) {
         int otherSeq = ((LogEntry)o).getSequenceNumber();
         if (this.sequenceNumber < otherSeq) {
            return -1;
         } else {
            return this.sequenceNumber == otherSeq ? 0 : 1;
         }
      }

      // $FF: synthetic method
      LogEntry(int x0, TransactionLoggable x1, Object x2) {
         this(x0, x1);
      }
   }
}
