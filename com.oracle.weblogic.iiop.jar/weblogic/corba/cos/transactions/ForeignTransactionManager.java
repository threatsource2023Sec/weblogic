package weblogic.corba.cos.transactions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.security.AccessController;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.UNKNOWN;
import org.omg.CosTransactions.HeuristicCommit;
import org.omg.CosTransactions.HeuristicHazard;
import org.omg.CosTransactions.HeuristicMixed;
import org.omg.CosTransactions.HeuristicRollback;
import org.omg.CosTransactions.NotPrepared;
import org.omg.CosTransactions.Resource;
import org.omg.CosTransactions.Vote;
import weblogic.corba.utils.RemoteInfo;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.ior.IOR;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionLoggable;
import weblogic.transaction.TransactionLogger;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;
import weblogic.utils.AssertionError;
import weblogic.utils.collections.ConcurrentHashMap;

public final class ForeignTransactionManager implements XAResource, TransactionLoggable, Serializable {
   protected static final boolean DEBUG = false;
   private String name;
   private int id;
   private int txTimeout = 0;
   private transient boolean isSynced = false;
   private transient boolean isFailed = false;
   private static final int FTM_POOL_SIZE = 1024;
   private static final ForeignTransactionManager[] ftmPool = new ForeignTransactionManager[1024];
   private static int ftmCounter = 0;
   private static String FTM_NAME;

   public static ForeignTransactionManager registerResource(Resource res, Xid xid) throws SystemException {
      Transaction tx = getTx(xid);
      ForeignTransactionManager ftm;
      synchronized(ftmPool) {
         ResourceMap h = (ResourceMap)tx.getProperty("weblogic.transaction.ots.resources");
         if (h == null) {
            h = new ResourceMap();
            tx.setProperty("weblogic.transaction.ots.resources", h);
         } else if ((ftm = (ForeignTransactionManager)h.get(res)) != null) {
            return ftm;
         }

         Integer current = (Integer)tx.getProperty("weblogic.transaction.ots.ftmCounter");
         int cpos = current == null ? 0 : current;
         tx.setProperty("weblogic.transaction.ots.ftmCounter", new Integer(cpos + 1));
         TransactionManager tm = TxHelper.getTransactionManager();
         ftm = ftmPool[cpos];
         if (ftm == null) {
            if (ftmCounter == 1024) {
               throw new AssertionError("OTS Foreign TM pool exhausted");
            }

            if (cpos >= ftmCounter) {
               ++ftmCounter;
            }

            ftm = ftmPool[cpos] = new ForeignTransactionManager(getFtmName() + cpos, cpos);
            ((ServerTransactionManager)tm).getTransactionLogger().store(ftm);
            if (OTSHelper.isDebugEnabled()) {
               IIOPLogger.logDebugOTS("registerResource(" + ftm.getName() + "): registering with JTA");
            }

            tm.registerDynamicResource(ftm.getName(), ftm);
         }

         if (ftm.isFailed) {
            tm.unregisterResource(ftm.getName());
            tm.registerDynamicResource(ftm.getName(), ftm);
            ftm.isFailed = false;
            if (OTSHelper.isDebugEnabled()) {
               IIOPLogger.logDebugOTS("registerResource(" + ftm.getName() + "): re-registering with JTA");
            }
         }

         h.put(res, ftm);
         h.put(ftm, res);
      }

      if (OTSHelper.isDebugEnabled()) {
         IIOPLogger.logDebugOTS("registerResource(" + ftm.getName() + "): enlisted " + ftm + " for " + xid);
      }

      return ftm;
   }

   private static final String getFtmName() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (FTM_NAME == null) {
         Class var1 = ForeignTransactionManager.class;
         synchronized(ForeignTransactionManager.class) {
            FTM_NAME = ManagementService.getRuntimeAccess(kernelId).getServer().getName() + "_OTS_ForeignTM_";
         }
      }

      return FTM_NAME;
   }

   public static void releaseResource(ForeignTransactionManager ftm) {
   }

   private ForeignTransactionManager(String name, int id) {
      this.name = name;
      this.id = id;
   }

   private void deenlist(Xid xid) {
   }

   public final String getName() {
      return this.name;
   }

   public final String toString() {
      return this.name;
   }

   public void commit(Xid xid, boolean onephase) throws XAException {
      if (OTSHelper.isDebugEnabled()) {
         IIOPLogger.logDebugOTS("commit(" + xid + ", " + onephase + ")");
      }

      try {
         Resource res = this.findResource(xid);
         if (onephase) {
            res.commit_one_phase();
         } else {
            res.commit();
         }

         releaseResource(this);
      } catch (HeuristicMixed var15) {
         throw (XAException)(new XAException(5)).initCause(var15);
      } catch (HeuristicRollback var16) {
         throw (XAException)(new XAException(6)).initCause(var16);
      } catch (HeuristicHazard var17) {
         throw (XAException)(new XAException(8)).initCause(var17);
      } catch (NotPrepared var18) {
         throw (XAException)(new XAException(6)).initCause(var18);
      } catch (TRANSACTION_ROLLEDBACK var19) {
         throw (XAException)(new XAException(100)).initCause(var19);
      } catch (UNKNOWN var20) {
         if (var20.minor >= 1111818304 && var20.minor <= 1111818368) {
            throw (XAException)(new XAException(var20.minor - 1111818304)).initCause(var20);
         }

         throw (XAException)(new XAException(-3)).initCause(var20);
      } catch (INVALID_TRANSACTION var21) {
         throw (XAException)(new XAException(-4)).initCause(var21);
      } catch (OBJECT_NOT_EXIST var22) {
         throw new XAException(-4);
      } catch (org.omg.CORBA.SystemException var23) {
         IIOPLogger.logOTSError("commit() failed unexpectedly", var23);
         this.isFailed = true;
         throw (XAException)(new XAException(-3)).initCause(var23);
      } finally {
         this.deenlist(xid);
      }

   }

   public void end(Xid xid, int flags) throws XAException {
      switch (flags) {
         case 33554432:
         case 67108864:
         case 536870912:
            return;
         default:
            throw new XAException(-5);
      }
   }

   public void forget(Xid xid) throws XAException {
      try {
         Resource res = this.findResource(xid);
         res.forget();
         releaseResource(this);
      } catch (org.omg.CORBA.SystemException var6) {
         IIOPLogger.logOTSError("forget() failed unexpectedly", var6);
         this.isFailed = true;
         throw (XAException)(new XAException(-3)).initCause(var6);
      } finally {
         this.deenlist(xid);
      }

   }

   public int getTransactionTimeout() throws XAException {
      return this.txTimeout;
   }

   public boolean isSameRM(XAResource xar) throws XAException {
      if (xar instanceof ForeignTransactionManager) {
         ForeignTransactionManager other = (ForeignTransactionManager)xar;
         if (other.equals(this)) {
            return true;
         }
      }

      return false;
   }

   public boolean equals(Object o) {
      try {
         ForeignTransactionManager other = (ForeignTransactionManager)o;
         return other != null && this.name.equals(other.name) && this.id == other.id;
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode() ^ this.id;
   }

   public int prepare(Xid xid) throws XAException {
      try {
         Resource res = this.findResource(xid);
         Vote vote = res.prepare();
         switch (vote.value()) {
            case 0:
               return 0;
            case 2:
               return 3;
            default:
               throw new XAException(100);
         }
      } catch (HeuristicMixed var4) {
         throw (XAException)(new XAException(5)).initCause(var4);
      } catch (HeuristicHazard var5) {
         throw (XAException)(new XAException(8)).initCause(var5);
      } catch (TRANSACTION_ROLLEDBACK var6) {
         throw (XAException)(new XAException(100)).initCause(var6);
      } catch (INVALID_TRANSACTION var7) {
         throw (XAException)(new XAException(-4)).initCause(var7);
      } catch (org.omg.CORBA.SystemException var8) {
         IIOPLogger.logOTSError("prepare() failed unexpectedly", var8);
         this.isFailed = true;
         throw (XAException)(new XAException(-3)).initCause(var8);
      }
   }

   public Xid[] recover(int flags) throws XAException {
      switch (flags) {
         case 0:
         case 8388608:
         default:
            return null;
         case 16777216:
            return null;
      }
   }

   public void rollback(Xid xid) throws XAException {
      if (OTSHelper.isDebugEnabled()) {
         IIOPLogger.logDebugOTS("rollback(" + xid + ")");
      }

      try {
         Resource res = this.findResource(xid);
         res.rollback();
         releaseResource(this);
      } catch (HeuristicMixed var11) {
         throw (XAException)(new XAException(5)).initCause(var11);
      } catch (HeuristicCommit var12) {
         throw (XAException)(new XAException(7)).initCause(var12);
      } catch (HeuristicHazard var13) {
         throw (XAException)(new XAException(8)).initCause(var13);
      } catch (INVALID_TRANSACTION var14) {
         throw (XAException)(new XAException(-4)).initCause(var14);
      } catch (OBJECT_NOT_EXIST var15) {
         throw new XAException(-4);
      } catch (org.omg.CORBA.SystemException var16) {
         IIOPLogger.logOTSError("rollback() failed unexpectedly", var16);
         this.isFailed = true;
         throw (XAException)(new XAException(-3)).initCause(var16);
      } finally {
         this.deenlist(xid);
      }

   }

   public boolean setTransactionTimeout(int timeout) throws XAException {
      this.txTimeout = timeout;
      return false;
   }

   public void start(Xid xid, int flags) throws XAException {
      if (this.findResource(xid) == null) {
         throw new XAException(-4);
      }
   }

   private Resource findResource(Xid xid) throws XAException {
      Transaction tx = getTx(xid);
      if (tx == null) {
         if (OTSHelper.isDebugEnabled()) {
            IIOPLogger.logDebugOTS("findResource(" + xid + ") failed, no transaction");
         }

         throw new XAException(-4);
      } else {
         ResourceMap h = (ResourceMap)tx.getProperty("weblogic.transaction.ots.resources");
         Resource res = null;
         if (h != null && (res = (Resource)h.get(this)) != null) {
            return res;
         } else {
            if (OTSHelper.isDebugEnabled()) {
               IIOPLogger.logDebugOTS("findResource(" + xid + ") failed because " + h == null ? "no resources are registered" : "no mapping exists for this resource");
            }

            throw new XAException(-4);
         }
      }
   }

   Resource findMissingResource(ForeignTransactionManager ftm, Transaction tx) {
      LinkedList omr = (LinkedList)tx.getLocalProperty("weblogic.transaction.ots.failedResources");
      if (omr != null) {
         ResourceMap h = (ResourceMap)tx.getProperty("weblogic.transaction.ots.resources");
         if (h == null) {
            h = new ResourceMap();
            tx.setProperty("weblogic.transaction.ots.resources", h);
         }

         Resource res = (Resource)omr.removeFirst();
         h.put(ftm, res);
         h.put(res, ftm);
         return res;
      } else {
         return null;
      }
   }

   private static final Transaction getTx(Xid xid) {
      return (Transaction)TxHelper.getTransactionManager().getTransaction(xid);
   }

   public ForeignTransactionManager() {
   }

   public void readExternal(DataInput decoder) throws IOException {
      this.name = decoder.readUTF();
      this.txTimeout = decoder.readInt();
      this.id = decoder.readInt();
      synchronized(ftmPool) {
         ftmPool[this.id] = this;
         if (this.id >= ftmCounter) {
            ftmCounter = this.id + 1;
         }

      }
   }

   public void writeExternal(DataOutput encoder) throws IOException {
      encoder.writeUTF(this.name);
      encoder.writeInt(this.txTimeout);
      encoder.writeInt(this.id);
   }

   public synchronized void onDisk(TransactionLogger tlog) {
      this.isSynced = true;
      this.notify();
   }

   private synchronized void waitForSync() {
      while(!this.isSynced) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      this.isSynced = false;
   }

   public void onError(TransactionLogger tlog) {
   }

   public void onRecovery(TransactionLogger tlog) {
      try {
         if (OTSHelper.isDebugEnabled()) {
            IIOPLogger.logDebugOTS("recovering " + this);
         }

         TransactionManager tm = TxHelper.getTransactionManager();
         tm.registerDynamicResource(this.getName(), this);
      } catch (Exception var3) {
         releaseResource(this);
      }

   }

   protected static void p(String s) {
      System.err.println("<ForeignTransactionManager> " + s);
   }

   public static class ResourceMap extends ConcurrentHashMap implements Externalizable {
      public synchronized void writeExternal(ObjectOutput out) throws IOException {
         Set s = this.entrySet();
         out.writeInt(s.size() / 2);
         Iterator iter = s.iterator();

         while(iter.hasNext()) {
            Map.Entry e = (Map.Entry)iter.next();
            Object key = e.getKey();
            Object value = e.getValue();
            if (key instanceof ForeignTransactionManager) {
               out.writeUTF(((ForeignTransactionManager)key).getName());
               out.writeObject(IIOPReplacer.getIIOPReplacer().replaceObject(value));
            }
         }

      }

      public synchronized void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         int size = in.readInt();

         for(int i = 0; i < size; ++i) {
            String ftmName = in.readUTF();
            ForeignTransactionManager ftm = null;
            int j = 0;
            if (j < ForeignTransactionManager.ftmCounter && ForeignTransactionManager.ftmPool[j] != null && ForeignTransactionManager.ftmPool[j].getName().equals(ftmName)) {
               ftm = ForeignTransactionManager.ftmPool[j];
            }

            IOR resource = (IOR)in.readObject();
            if (ftm != null) {
               Resource res = (Resource)IIOPReplacer.resolveObject(resource, RemoteInfo.findRemoteInfo(Resource.class));
               this.put(ftm, res);
               this.put(res, ftm);
            }
         }

      }
   }
}
