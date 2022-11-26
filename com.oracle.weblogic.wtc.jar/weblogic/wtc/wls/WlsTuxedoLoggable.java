package weblogic.wtc.wls;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.internal.EXid;
import com.bea.core.jatmi.internal.TuxedoXid;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import javax.transaction.xa.Xid;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.TransactionLoggable;
import weblogic.transaction.TransactionLogger;
import weblogic.transaction.TxHelper;
import weblogic.transaction.internal.LogDataInput;
import weblogic.transaction.internal.LogDataOutput;
import weblogic.wtc.gwt.OatmialServices;
import weblogic.wtc.gwt.WTCService;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.Txid;

public class WlsTuxedoLoggable implements TransactionLoggable, TuxedoLoggable {
   private static final int VERSION = 1;
   private static final int VERSION2 = 2;
   private Txid myTxid;
   private Xid myXid;
   private int myType;
   private Object onDiskReply;
   private boolean gotDiskReply = false;
   private String[] remoteDomains;
   private TransactionLogger tlg = null;

   public WlsTuxedoLoggable() {
      this.myType = 0;
      this.onDiskReply = new Object();
   }

   public WlsTuxedoLoggable(Xid xid, int type) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("WlsTuxedoLoggable constructor xid " + xid + ", type " + type);
      }

      if (type >= 0 && type <= 4) {
         this.myType = type;
         if (type != 1 && type != 2) {
            if (type != 3 && type != 4) {
               this.myXid = xid;
            } else {
               this.myXid = xid;
            }
         } else {
            OatmialServices tos = WTCService.getOatmialServices();
            Xid logicXid = tos.getOutboundXidAssociatedWithFXid(xid);
            if (logicXid == null) {
               this.myXid = new EXid(xid, (Xid)null);
            } else {
               this.myXid = new EXid(logicXid, xid);
            }
         }
      } else {
         this.myType = 0;
         this.myXid = xid;
      }

      this.myTxid = new Txid(this.myXid.getGlobalTransactionId());
      this.onDiskReply = new Object();
   }

   public int getType() {
      return this.myType;
   }

   public void readExternal(DataInput in) throws IOException {
      LogDataInput decoder = (LogDataInput)in;
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WlsTuxedoLoggable/readExternal/");
      }

      byte[] gxidStuff = null;
      byte[] bxidStuff = null;
      int realformatID = 0;
      byte[] realgxidStuff = null;
      byte[] realbxidStuff = null;
      int hasforeignXid = 0;
      int version = decoder.readNonNegativeInt();
      if (version != 2 && version != 1) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WlsTuxedoLoggable/readExternal/10");
         }

         throw new InvalidObjectException("WTC log record: unrecognized versionnumber " + version);
      } else {
         this.myType = decoder.readNonNegativeInt();
         byte[] gxidStuff = decoder.readByteArray();
         byte[] bxidStuff = decoder.readByteArray();
         if (version == 2) {
            hasforeignXid = decoder.readNonNegativeInt();
            if (hasforeignXid == 1) {
               realformatID = decoder.readNonNegativeInt();
               realgxidStuff = decoder.readByteArray();
               realbxidStuff = decoder.readByteArray();
            }
         }

         switch (this.myType) {
            case 1:
            case 2:
               if (hasforeignXid == 0) {
                  this.myXid = new EXid(TxHelper.createXid(gxidStuff, bxidStuff), (Xid)null);
                  this.myTxid = new Txid(this.myXid.getGlobalTransactionId());
               } else {
                  this.myXid = new EXid(TxHelper.createXid(gxidStuff, bxidStuff), TxHelper.createXid(realformatID, realgxidStuff, realbxidStuff));
                  this.myTxid = new Txid(this.myXid.getGlobalTransactionId());
               }
               break;
            case 3:
            case 4:
               try {
                  this.myXid = new TuxedoXid(gxidStuff, bxidStuff);
                  if (hasforeignXid == 1) {
                     ((TuxedoXid)this.myXid).setImportedXid(TxHelper.createXid(realformatID, realgxidStuff, realbxidStuff));
                  }
               } catch (TPException var14) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WlsTuxedoLoggable/readExternal/20");
                  }

                  throw new InvalidObjectException("WTC log record: Invalid Tuxeod Xid " + var14);
               }

               this.myTxid = new Txid(this.myXid.getGlobalTransactionId());
               break;
            default:
               if (traceEnabled) {
                  ntrace.doTrace("*]/WlsTuxedoLoggable/readExternal/30");
               }

               throw new InvalidObjectException("WTC log record: unrecognized type " + this.myType);
         }

         if (traceEnabled) {
            ntrace.doTrace(" /WlsTuxedoLoggable/readExternal/xid/" + this.myXid);
         }

         int numRdoms;
         if ((numRdoms = decoder.readNonNegativeInt()) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("]/WlsTuxedoLoggable/readExternal/40");
            }

         } else {
            this.remoteDomains = new String[numRdoms];

            for(int lcv = 0; lcv < numRdoms; ++lcv) {
               this.remoteDomains[lcv] = decoder.readAbbrevString();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WlsTuxedoLoggable/readExternal/50");
            }

         }
      }
   }

   public void writeExternal(DataOutput out) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WlsTuxedoLoggable/writeExternal/");
      }

      LogDataOutput encoder = (LogDataOutput)out;
      encoder.writeNonNegativeInt(2);
      encoder.writeNonNegativeInt(this.myType);
      if (this.myXid == null) {
         encoder.writeNonNegativeInt(0);
         encoder.writeNonNegativeInt(0);
         encoder.writeNonNegativeInt(0);
         encoder.writeNonNegativeInt(0);
         if (traceEnabled) {
            ntrace.doTrace("]/WlsTuxedoLoggable/writeExternal/10");
         }

      } else {
         byte[] xidStuff = this.myXid.getGlobalTransactionId();
         encoder.writeByteArray(xidStuff);
         xidStuff = this.myXid.getBranchQualifier();
         encoder.writeByteArray(xidStuff);
         OatmialServices tos = WTCService.getOatmialServices();
         Xid foreignXid = null;
         TuxedoConnectorRAP[] rdoms;
         switch (this.myType) {
            case 1:
            case 2:
               if (this.myXid instanceof EXid) {
                  foreignXid = ((EXid)this.myXid).getForeignXid();
               }

               if (foreignXid == null) {
                  if (this.myXid instanceof EXid) {
                     rdoms = tos.getOutboundRdomsAssociatedWithXid(((EXid)this.myXid).getXid());
                  } else {
                     rdoms = tos.getOutboundRdomsAssociatedWithXid(this.myXid);
                  }

                  encoder.writeNonNegativeInt(0);
               } else {
                  rdoms = tos.getOutboundRdomsAssociatedWithXid(foreignXid);
                  encoder.writeNonNegativeInt(1);
                  encoder.writeNonNegativeInt(foreignXid.getFormatId());
                  encoder.writeByteArray(foreignXid.getGlobalTransactionId());
                  encoder.writeByteArray(foreignXid.getBranchQualifier());
               }
               break;
            case 3:
            case 4:
               rdoms = tos.getInboundRdomsAssociatedWithXid(this.myXid);
               if (this.myXid instanceof TuxedoXid) {
                  foreignXid = ((TuxedoXid)this.myXid).getImportedXid();
               }

               if (foreignXid == null) {
                  encoder.writeNonNegativeInt(0);
               } else {
                  encoder.writeNonNegativeInt(1);
                  encoder.writeNonNegativeInt(foreignXid.getFormatId());
                  encoder.writeByteArray(foreignXid.getGlobalTransactionId());
                  encoder.writeByteArray(foreignXid.getBranchQualifier());
               }
               break;
            default:
               rdoms = tos.getOutboundRdomsAssociatedWithXid(this.myXid);
               encoder.writeNonNegativeInt(0);
         }

         if (rdoms != null && rdoms.length != 0) {
            encoder.writeNonNegativeInt(rdoms.length);

            for(int lcv = 0; lcv < rdoms.length; ++lcv) {
               encoder.writeAbbrevString(rdoms[lcv].getAccessPoint());
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("no rdom");
            }

            encoder.writeNonNegativeInt(0);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WlsTuxedoLoggable/writeExternal/20");
         }

      }
   }

   public void onDisk(TransactionLogger tlog) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WlsTuxedoLoggable/onDisk/");
      }

      if (this.myType == 4) {
         if (this.tlg == null) {
            this.tlg = ((ServerTransactionManager)((ServerTransactionManager)TxHelper.getTransactionManager())).getTransactionLogger();
         }

         TuxedoLoggable tl = ConfigHelper.removeXidTLogMap(this.myXid, 3);
         if (ntrace.getTraceLevel() == 1000372) {
            if (traceEnabled) {
               ntrace.doTrace("Committing on disk, prepared not removed, sleep 30 seconds");
            }

            try {
               Thread.sleep(30000L);
            } catch (InterruptedException var7) {
            }

            if (traceEnabled) {
               ntrace.doTrace("Finished sleeping");
            }
         }

         if (tl != null) {
            tl.forget();
         }
      }

      synchronized(this.onDiskReply) {
         this.gotDiskReply = true;
         this.onDiskReply.notify();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WlsTuxedoLoggable/onDisk/10");
      }

   }

   public void waitForDisk() {
      synchronized(this.onDiskReply) {
         while(!this.gotDiskReply) {
            try {
               this.onDiskReply.wait();
            } catch (InterruptedException var4) {
               this.gotDiskReply = false;
               return;
            }
         }

         this.gotDiskReply = false;
      }
   }

   public void onError(TransactionLogger tlog) {
   }

   public void onRecovery(TransactionLogger tlog) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WlsTuxedoLoggable/onRecovery/" + tlog);
      }

      switch (this.myType) {
         case 0:
            if (traceEnabled) {
               ntrace.doTrace("IS_NONE");
            }
            break;
         case 1:
            if (traceEnabled) {
               ntrace.doTrace("IS_READY");
            }

            if (this.myXid != null && this.remoteDomains != null && this.remoteDomains.length != 0) {
               WTCService.addRecoveredXid(this.myXid, this.remoteDomains);
               WTCService.AddXidTLogMap(this.myXid, this);
            }
            break;
         case 2:
            if (traceEnabled) {
               ntrace.doTrace("IS_COMMIT");
            }

            if (this.myXid != null && this.remoteDomains != null && this.remoteDomains.length != 0) {
               WTCService.addCommittedXid(this.myXid, this.remoteDomains);
            }
            break;
         case 3:
            if (traceEnabled) {
               ntrace.doTrace("IS_PREPARED");
            }

            if (this.myXid != null && this.remoteDomains != null && this.remoteDomains.length != 0) {
               WTCService.addPreparedXid(this.myXid, this.remoteDomains[0], this);
            }
            break;
         case 4:
            if (traceEnabled) {
               ntrace.doTrace("IS_COMMITTING");
            }

            if (this.myXid != null && this.remoteDomains != null && this.remoteDomains.length != 0) {
               WTCService.addCommittingXid(this.myXid, this.remoteDomains[0], this);
            }
            break;
         default:
            if (traceEnabled) {
               ntrace.doTrace("Unknown type: " + this.myType);
            }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WlsTuxedoLoggable/onRecovery/10");
      }

   }

   public Txid getTxid() {
      return this.myTxid;
   }

   public boolean equals(Object compareTo) {
      if (this.myTxid == null && compareTo != null) {
         return false;
      } else if (this.myTxid != null && compareTo == null) {
         return false;
      } else if (compareTo == null) {
         return this.myTxid == null && this.myType == 0;
      } else if (!(compareTo instanceof WlsTuxedoLoggable)) {
         return false;
      } else {
         WlsTuxedoLoggable myCompareTo = (WlsTuxedoLoggable)compareTo;
         int compareType = myCompareTo.getType();
         if (compareType != this.myType) {
            return false;
         } else {
            Txid cTxid = myCompareTo.getTxid();
            return this.myTxid.equals(cTxid);
         }
      }
   }

   public int hashCode() {
      return this.myTxid == null ? this.myType : this.myTxid.hashCode() + this.myType;
   }

   public void write() {
      if (this.tlg == null) {
         this.tlg = ((ServerTransactionManager)((ServerTransactionManager)TxHelper.getTransactionManager())).getTransactionLogger();
      }

      this.tlg.store(this);
   }

   public void forget() {
      if (this.tlg == null) {
         this.tlg = ((ServerTransactionManager)((ServerTransactionManager)TxHelper.getTransactionManager())).getTransactionLogger();
      }

      this.tlg.release(this);
   }
}
