package weblogic.store.admintool;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.store.ByteBufferObjectHandler;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.RuntimeHandler;
import weblogic.store.StoreWritePolicy;
import weblogic.store.internal.PersistentStoreConnectionImpl;
import weblogic.store.io.file.FileStoreIO;
import weblogic.store.io.file.ReplicatedStoreAdminImpl;
import weblogic.store.io.jdbc.JDBCStoreIO;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.store.xa.internal.PersistentStoreXAImpl;
import weblogic.xml.stax.util.XMLPrettyPrinter;

public class StoreAdminIFImpl implements StoreAdminIF {
   private static final String MAP_CONNTYPE_PREFIX = "MAP!:";
   private static final String DEFAULT_CONNTYPE_PREFIX = "DEF!:";
   private static final int DEFAULT_BATCH_SIZE = 16000;
   private static final String DISABLE_SCHEDULER_PROPERTY = "weblogic.store.DisableDiskScheduler";
   private static final PersistentStoreManager psMgr = PersistentStoreManager.getManager();
   private boolean DEBUG = false;

   public StoreAdminIFImpl() {
      try {
         System.setProperty("weblogic.store.DisableDiskScheduler", "true");
      } catch (Exception var2) {
      }

   }

   public ReplicatedStoreAdminImpl getRsAdminImpl() throws IOException {
      return ReplicatedStoreAdminImpl.getReplicatedStoreAdminImplSingleton();
   }

   public PersistentStoreXA openFileStore(String storeName, String dirName, StoreWritePolicy writePolicy, boolean autoCreate) throws PersistentStoreException {
      this.validateStoreName(storeName);
      if (dirName == null) {
         this.throwValEx("Invalid dir name:" + dirName);
      }

      if (writePolicy == null) {
         this.throwValEx("Invalid writePolicy :" + writePolicy);
      }

      FileStoreIO fileIO = new FileStoreIO(storeName, dirName, autoCreate);
      PersistentStoreXA ps = new PersistentStoreXAImpl(storeName, fileIO, (String)null, (RuntimeHandler)null);
      ps.open(writePolicy);
      this.initAllConnections(ps);
      psMgr.addStore(storeName, ps);
      return ps;
   }

   public PersistentStoreXA openJDBCStore(String storeName, String dbTableName, String createTableDDLFile, String dbUrl, String userName, String password, String driverClass, Properties driverProps) throws PersistentStoreException {
      this.validateStoreName(storeName);
      if (dbTableName == null) {
         dbTableName = "WLStore";
      } else if (!dbTableName.endsWith("WLStore")) {
         dbTableName = dbTableName.concat("WLStore");
      }

      BasicDataSource bds = null;

      try {
         bds = new BasicDataSource(dbUrl, driverClass, driverProps, password);
      } catch (SQLException var12) {
         throw new PersistentStoreException(var12);
      }

      JDBCStoreIO jdbcIO = new JDBCStoreIO(storeName, bds, dbTableName, createTableDDLFile, 20, 20, 20);
      PersistentStoreXA ps = new PersistentStoreXAImpl(storeName, jdbcIO, (String)null, (RuntimeHandler)null);
      ps.open(StoreWritePolicy.CACHE_FLUSH);
      this.initAllConnections(ps);
      psMgr.addStore(storeName, ps);
      return ps;
   }

   public void closeStore(PersistentStoreXA store) throws PersistentStoreException {
      if (store == null) {
         this.throwValEx("Invalid store instance:" + store);
      }

      psMgr.removeStore(store.getName());
      store.close();
   }

   public String[] getAllConnections(PersistentStoreXA fromStore) throws PersistentStoreException {
      this.validateStore(fromStore);
      LinkedList ll = new LinkedList();
      Iterator it = null;
      it = fromStore.getConnectionNames();

      while(it.hasNext()) {
         ll.add(this.addDefaultPrefix((String)it.next()));
      }

      it = fromStore.getMapConnectionNames();

      while(it.hasNext()) {
         ll.add(this.addMapPrefix((String)it.next()));
      }

      String[] retStrArr = (String[])((String[])ll.toArray(new String[ll.size()]));
      return retStrArr;
   }

   public void copyConnections(PersistentStoreXA fromStore, PersistentStoreXA toStore, boolean allowOverWrite, String[] toBeCopiedConnections) throws PersistentStoreException {
      this.validateInternal(fromStore, toBeCopiedConnections);
      this.validateStore(toStore);
      Arrays.sort(toBeCopiedConnections);

      for(int i = 0; i < toBeCopiedConnections.length; ++i) {
         String connName = toBeCopiedConnections[i];
         if (this.DEBUG) {
            System.out.println("copyConnections() " + connName);
         }

         this.copyOneConnection(fromStore, toStore, connName, allowOverWrite);
      }

   }

   public void deleteConnections(PersistentStoreXA fromStore, String[] toBeDeletedConnections) throws PersistentStoreException {
      this.validateInternal(fromStore, toBeDeletedConnections);
      PersistentStoreConnection psConn = null;

      for(int i = 0; i < toBeDeletedConnections.length; ++i) {
         String connName = toBeDeletedConnections[i];
         if (connName != null) {
            psConn = this.getExistingConnection(fromStore, this.removePrefix(connName), this.isMapConnection(connName));
            if (psConn != null) {
               psConn.delete();
               psConn = null;
            } else {
               this.throwValEx("Delete failed: connection " + connName + " does not exist");
            }
         }
      }

   }

   public void dumpConnections(PersistentStoreXA fromStore, String dumpFileNamePrefix, String[] connectionNames, boolean dumpRecordContents) throws PersistentStoreException, IOException {
      this.validateInternal(fromStore, connectionNames);
      if (dumpFileNamePrefix == null) {
         this.throwValEx("Invalid dump file name:" + dumpFileNamePrefix);
      }

      XMLStreamWriter xsw = null;
      PrintWriter pwr = null;
      PersistentStoreXAImpl psImpl = (PersistentStoreXAImpl)fromStore;

      try {
         pwr = new PrintWriter(new FileOutputStream(dumpFileNamePrefix));
         xsw = new XMLPrettyPrinter(pwr, 2);
         this.dumpInternal(xsw, psImpl, connectionNames, dumpRecordContents);
      } catch (FileNotFoundException var13) {
         throw new IOException("Unable to create dump output file: " + dumpFileNamePrefix);
      } catch (XMLStreamException var14) {
         pwr.println("Error writing XMLStream");
         if (!pwr.checkError()) {
            var14.printStackTrace(pwr);
         }

         throw new IOException("Error writing XML to dump output file: " + dumpFileNamePrefix);
      } finally {
         if (pwr != null) {
            pwr.close();
            pwr = null;
         }

      }

   }

   private void dumpInternal(XMLStreamWriter xsw, PersistentStoreXAImpl store, String[] connectionNames, boolean dumpRecordContents) throws XMLStreamException {
      store.dump(xsw, false);
      boolean isMapConn = false;

      for(int i = 0; i < connectionNames.length; ++i) {
         String connName = connectionNames[i];
         if (connName != null) {
            isMapConn = this.isMapConnection(connName);
            connName = this.removePrefix(connName);
            if (isMapConn) {
               store.dumpPersistentMap(xsw, connName, dumpRecordContents);
            } else {
               store.dumpConnection(xsw, connName, dumpRecordContents);
            }
         }
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   private void copyOneConnection(PersistentStoreXA fromStore, PersistentStoreXA toStore, String inConnName, boolean allowOverWrite) throws PersistentStoreException {
      boolean isMapConn = this.isMapConnection(inConnName);
      String connName = this.removePrefix(inConnName);
      PersistentStoreConnection pscFrom = this.getExistingConnection(fromStore, connName, isMapConn);
      if (pscFrom == null) {
         this.throwValEx("Copy source connection " + inConnName + " unknown or not open");
      }

      PersistentStoreConnection pscTo = this.getExistingConnection(toStore, connName, isMapConn);
      if (pscTo != null && (!isMapConn || isMapConn && connName.indexOf("values") == -1)) {
         if (allowOverWrite) {
            pscTo.delete();
         } else {
            this.throwValEx("Copy destination connection " + inConnName + " already exists and over-write not set");
         }
      }

      pscTo = this.initConnection(toStore, connName, isMapConn);
      ByteBuffer databuf = null;
      PersistentStoreRecord psr = null;
      PersistentStoreTransaction ptx = null;
      int batchsize = 0;
      int batchcount = 0;
      int numRecords = 0;
      PersistentStoreConnection.Cursor cursor = pscFrom.createCursor(0);

      for(ptx = toStore.begin(); (psr = cursor.next()) != null; ++numRecords) {
         databuf = (ByteBuffer)psr.getData();
         batchsize += databuf.limit();
         ((PersistentStoreConnectionImpl)pscTo).create(ptx, psr.getHandle(), databuf, 0);

         try {
            if (batchsize >= 16000) {
               batchsize = 0;
               ++batchcount;
               ptx.commit();
               ptx = toStore.begin();
            }
         } catch (PersistentStoreException var17) {
            throw var17;
         }
      }

      ptx.commit();
   }

   private void initAllConnections(PersistentStoreXA ps) throws PersistentStoreException {
      String connName = null;
      Iterator it = ps.getConnectionNames();

      while(it.hasNext()) {
         connName = (String)it.next();
         this.initConnection(ps, connName, false);
      }

      it = ps.getMapConnectionNames();

      while(it.hasNext()) {
         connName = (String)it.next();
         this.initConnection(ps, connName, true);
      }

   }

   private PersistentStoreConnection initConnection(PersistentStoreXA ps, String conn, boolean isMapConn) throws PersistentStoreException {
      PersistentStoreConnection psc = this.getExistingConnection(ps, conn, isMapConn);
      if (psc != null) {
         return psc;
      } else {
         if (isMapConn) {
            ps.createPersistentMap(conn);
            psc = this.getExistingConnection(ps, conn, isMapConn);
         } else {
            psc = ps.createConnection(conn);
         }

         PersistentStoreConnectionImpl pscImpl = (PersistentStoreConnectionImpl)psc;
         pscImpl.setObjectHandler(new ByteBufferObjectHandler());
         return psc;
      }
   }

   private PersistentStoreConnection getExistingConnection(PersistentStoreXA ps, String connName, boolean isMapConn) {
      PersistentStoreXAImpl psImpl = (PersistentStoreXAImpl)ps;
      PersistentStoreConnection psc = isMapConn ? psImpl.getMapConnection(connName) : psImpl.getConnection(connName);
      if (psc != null) {
         ((PersistentStoreConnectionImpl)psc).setObjectHandler(new ByteBufferObjectHandler());
      }

      if (this.DEBUG) {
         System.out.println("getExistingConnection " + connName + " returning " + psc);
      }

      return psc;
   }

   private void validateInternal(PersistentStoreXA ps, String[] inConnList) throws PersistentStoreException {
      this.validateStore(ps);
      if (inConnList == null) {
         this.throwValEx("Invalid input list of connection names");
      }

   }

   private void validateStoreName(String storeName) throws PersistentStoreException {
      if (storeName == null) {
         this.throwValEx("Invalid store name:" + storeName);
      }

      if (psMgr.getStore(storeName) != null) {
         this.throwValEx("Pre-existing store:" + storeName);
      }

   }

   private void validateStore(PersistentStoreXA ps) throws PersistentStoreException {
      if (ps == null) {
         this.throwValEx("Invalid input store instance:" + ps);
      }

      if (psMgr.getStore(ps.getName()) == null) {
         this.throwValEx("Unknown store:" + ps);
      }

      if (!(ps instanceof PersistentStoreXAImpl)) {
         this.throwValEx("Unknown implementation of store:" + ps);
      }

   }

   private void throwValEx(String errmsg) throws PersistentStoreException {
      throw new PersistentStoreException(new IllegalArgumentException(errmsg));
   }

   private String addMapPrefix(String inName) {
      return this.addPrefix(inName, "MAP!:");
   }

   private String addDefaultPrefix(String inName) {
      return this.addPrefix(inName, "DEF!:");
   }

   private String addPrefix(String inName, String prefix) {
      return inName.startsWith(prefix) ? inName : prefix + inName;
   }

   private boolean isMapConnection(String inName) {
      return inName.startsWith("MAP!:");
   }

   private String removePrefix(String inName) {
      if (inName.startsWith("DEF!:")) {
         return inName.substring("DEF!:".length());
      } else {
         return inName.startsWith("MAP!:") ? inName.substring("MAP!:".length()) : inName;
      }
   }

   public int rsAttach(String dirName, String cfgFileName, int localindex) throws IOException {
      return this.getRsAdminImpl().attach(dirName, cfgFileName, localindex);
   }

   public int rsAttachToDaemon(int dindex) throws IOException {
      return this.getRsAdminImpl().attachToDaemon(dindex);
   }

   public void rsDetach() throws IOException {
      this.getRsAdminImpl().detach();
   }

   public boolean isAttached() throws IOException {
      return this.getRsAdminImpl().isAttached();
   }

   public int rsGetAttachedDaemonIndex() throws IOException {
      return this.getRsAdminImpl().getAttachedDaemonIndex();
   }

   public HashMap rsListGlobalRegions() throws IOException {
      return this.getRsAdminImpl().listGlobalRegions();
   }

   public HashMap rsListLocalRegions() throws IOException {
      return this.getRsAdminImpl().listLocalRegions();
   }

   public HashMap rsListRegion(String name) throws IOException {
      return this.getRsAdminImpl().listRegion(name);
   }

   public int rsDeleteRegion(String name, boolean force) throws IOException {
      return this.getRsAdminImpl().deleteRegion(name, force);
   }

   public HashMap rsListDaemons() throws IOException {
      return this.getRsAdminImpl().listDaemons();
   }

   public int rsShutdownDaemon(int index, boolean force, boolean safe) throws IOException {
      return this.getRsAdminImpl().shutdownDaemon(index, force, safe);
   }

   public void rsClean() throws IOException {
   }
}
