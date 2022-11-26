package weblogic.transaction.internal;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Map;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.transaction.TransactionLoggable;
import weblogic.transaction.TransactionLogger;

final class HeuristicsLog extends XAResourceHelper implements TransactionLoggable {
   private static final long serialVersionUID = -2310672563418331533L;
   private ServerTransactionImpl importedTx;
   private boolean onDisk = false;

   HeuristicsLog() {
   }

   HeuristicsLog(ServerTransactionImpl tx) {
      this.importedTx = tx;
   }

   public void writeExternal(DataOutput out) throws IOException {
      LogDataOutput encoder = (LogDataOutput)out;
      encoder.writeNonNegativeInt(1);
      encoder.writeProperties(this.importedTx.getProperties());
      encoder.writeByteArray(this.importedTx.getXID().getGlobalTransactionId());
      encoder.writeLong(this.importedTx.getBeginTimeMillis());
      encoder.writeByte(this.importedTx.getState());
      encoder.writeInt(this.importedTx.getHeuristicErrorCode());
      encoder.writeString(this.importedTx.getHeuristicErrorMessage());
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug("writeExternal to heuristic log: " + this);
      }

   }

   public void readExternal(DataInput in) throws IOException {
      LogDataInput decoder = (LogDataInput)in;
      int version = decoder.readNonNegativeInt();
      if (version != 1) {
         throw new InvalidObjectException("Heuristic log record: unrecognized version number " + version);
      } else {
         Map props = decoder.readProperties();
         byte[] gtrid = decoder.readByteArray();
         if (gtrid == null) {
            throw new InvalidObjectException("heuristic log record: null gtrid");
         } else if (gtrid.length > 64) {
            throw new InvalidObjectException("heuristic log record: bad gtrid length " + gtrid.length);
         } else {
            XidImpl xid = XidImpl.create(gtrid);
            Xid foreignXid = (Xid)props.get("weblogic.transaction.foreignXid");
            if (foreignXid == null) {
               throw new InvalidObjectException("heuristic log record: no foreignXid property for: " + xidToString(xid));
            } else {
               this.importedTx = (ServerTransactionImpl)getTM().getTransaction(foreignXid);
               if (this.importedTx == null) {
                  try {
                     this.importedTx = (ServerTransactionImpl)getTM().createImportedTransaction(xid, foreignXid, getTM().getTransactionTimeout(), getTM().getTransactionTimeout());
                  } catch (SystemException var9) {
                     throw new IOException("Cannot create imported transaction for: " + xidToString(xid));
                  }
               } else {
                  this.importedTx.releaseLog();
               }

               this.importedTx.setHeuristicsLog(this);
               this.importedTx.addProperties(props);
               this.importedTx.setBeginTimeMillis(decoder.readLong());
               this.importedTx.setState(decoder.readByte());
               this.importedTx.setHeuristicErrorCode(decoder.readInt());
               this.importedTx.addHeuristicErrorMessage(decoder.readString());
               if (TxDebug.JTAGateway.isDebugEnabled()) {
                  TxDebug.JTAGateway.debug("readExternal from heuristic log: " + this);
               }

            }
         }
      }
   }

   public void onDisk(TransactionLogger hlog) {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug("onDisk() callback: " + this);
      }

      synchronized(this) {
         this.onDisk = true;
         this.notify();
      }
   }

   boolean store() {
      getTM().getHeuristicLogger().store(this);
      synchronized(this) {
         if (this.onDisk) {
            return true;
         }

         try {
            this.wait(1000L);
         } catch (InterruptedException var4) {
         }
      }

      return this.onDisk;
   }

   public void onError(TransactionLogger hlog) {
      hlog.release(this);
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug("onError() callback: " + this);
      }

   }

   public void onRecovery(TransactionLogger hlog) {
      if (TxDebug.JTAGateway.isDebugEnabled()) {
         TxDebug.JTAGateway.debug("onRecovery() callback: " + this);
      }

   }

   public String toString() {
      return (new StringBuffer(100)).append("HeuristicsLog={").append(this.importedTx.getXid().toString()).append("}").toString();
   }

   private static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }
}
