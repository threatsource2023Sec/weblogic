package weblogic.transaction.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.transaction.TransactionSystemException;
import weblogic.utils.io.FilteringObjectInputStream;

public final class PropagationContext implements Externalizable {
   static final long serialVersionUID = -8597561089762241351L;
   private transient TransactionImpl tx = null;
   private transient Object peerInfo = null;
   private transient boolean infectCoordinatorFirstTime = false;
   private transient boolean readOrWriteRollbackReasonInBytes = true;
   static final int VERSION1 = 1;
   static final int VERSION2 = 2;
   static final int VERSION3 = 3;
   static final int VERSION4 = 4;
   private int inVersion;
   private XidImpl xid;
   private int timeToLiveSec;
   private int timeoutSec;
   private boolean isCancelled;
   private Throwable rollbackReason;
   private byte[] rollbackReasonInBytes = null;
   private String coordinatorURL;
   private String txName;
   private String[] scURLs;
   private byte[] scStates;
   private byte[] scSyncRegs;
   private String[] resNames;
   private String[][] resSCURLs;
   private HashMap txProps;
   private static final boolean m_isDebugCoURL = Boolean.getBoolean("weblogic.transaction.internal.debugcourl");

   static boolean isDebugCoURL() {
      return m_isDebugCoURL;
   }

   public PropagationContext() {
   }

   PropagationContext(TransactionImpl atx) {
      this.tx = atx;
   }

   public String toString() {
      return "PropagationContext[" + (this.tx == null ? "" : this.tx.toString()) + "]";
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      int streamVersion = this.getVersion(oo);
      this.readOrWriteRollbackReasonInBytes = this.useNewMethod(oo);
      boolean isCancelled;
      String txname;
      List scList;
      int len;
      int len;
      SCInfo sci;
      ResourceInfo ri;
      ArrayList resourceSCList;
      int sclen;
      int j;
      SCInfo sci;
      ArrayList resourceList;
      int i;
      if (streamVersion != 2 && streamVersion != 3 && streamVersion != 4) {
         if (streamVersion == 1) {
            IOHelper.writeCompressedInt(oo, 1);
            ((XidImpl)this.tx.getXID()).writeExternal(oo);
            IOHelper.writeCompressedInt(oo, this.tx.getTimeToLiveSeconds());
            IOHelper.writeCompressedInt(oo, this.tx.getTimeoutSeconds());
            isCancelled = this.tx.isCancelled();
            oo.writeBoolean(isCancelled);
            if (isCancelled) {
               this.writeRollbackReason(oo);
            }

            oo.writeUTF(this.tx.getCoordinatorURL() == null ? "" : this.tx.getCoordinatorURL());
            txname = this.tx.getName();
            oo.writeUTF(txname == null ? "" : txname);
            scList = this.tx.getSCInfoList();
            if (scList == null) {
               IOHelper.writeCompressedInt(oo, 0);
            } else {
               len = scList.size();
               IOHelper.writeCompressedInt(oo, len);

               for(len = 0; len < len; ++len) {
                  sci = (SCInfo)scList.get(len);
                  oo.writeUTF(sci.getScUrl(this.tx));
                  if (isDebugCoURL()) {
                     (new Throwable("PropagationContext writeExternal UTF scInfo getScUrl:" + sci.getScUrl(this.tx))).printStackTrace();
                  }

                  oo.writeByte(sci.getState());
               }
            }

            resourceList = this.tx.getResourceInfoList();
            if (resourceList == null) {
               IOHelper.writeCompressedInt(oo, 0);
            } else {
               len = resourceList.size();
               IOHelper.writeCompressedInt(oo, len);

               for(i = 0; i < len; ++i) {
                  ri = (ResourceInfo)resourceList.get(i);
                  oo.writeUTF(ri.getName());
                  resourceSCList = ri.getSCInfoList();
                  if (resourceSCList == null) {
                     IOHelper.writeCompressedInt(oo, 0);
                  } else {
                     sclen = resourceSCList.size();
                     IOHelper.writeCompressedInt(oo, sclen);

                     for(j = 0; j < sclen; ++j) {
                        sci = (SCInfo)resourceSCList.get(j);
                        oo.writeUTF(sci.getScUrl(this.tx));
                        if (isDebugCoURL()) {
                           (new Throwable("PropagationContext writeExternal writeUTF getScUrl:" + sci.getScUrl(this.tx))).printStackTrace();
                        }
                     }
                  }
               }
            }

            Map txprops = this.filterOTSAndWSATProperties(this.tx.getProperties());
            if (txprops != null && txprops.size() != 0) {
               IOHelper.writeCompressedInt(oo, txprops.size());
               oo.writeObject(txprops);
            } else {
               IOHelper.writeCompressedInt(oo, 0);
            }
         }
      } else {
         IOHelper.writeCompressedInt(oo, streamVersion);
         ((XidImpl)this.tx.getXID()).writeExternal(oo);
         IOHelper.writeCompressedInt(oo, this.tx.getTimeToLiveSeconds());
         IOHelper.writeCompressedInt(oo, this.tx.getTimeoutSeconds());
         isCancelled = this.tx.isCancelled();
         oo.writeBoolean(isCancelled);
         if (isCancelled) {
            this.writeRollbackReason(oo);
         }

         this.writeAbbrevString(oo, this.tx.getCoordinatorURL() == null ? "" : this.tx.getCoordinatorURL());
         txname = this.tx.getName();
         this.writeAbbrevString(oo, txname == null ? "" : txname);
         scList = this.tx.getSCInfoList();
         if (scList == null) {
            IOHelper.writeCompressedInt(oo, 0);
         } else {
            len = scList.size();
            IOHelper.writeCompressedInt(oo, len);

            for(len = 0; len < len; ++len) {
               sci = (SCInfo)scList.get(len);
               this.writeAbbrevString(oo, sci.getScUrl(this.tx));
               if (isDebugCoURL()) {
                  (new Throwable("PropagationContext writeExternal abbrev getScUrl:" + sci.getScUrl(this.tx))).printStackTrace();
               }

               oo.writeByte(sci.getState());
               if (sci.isSyncRegistered()) {
                  oo.writeByte(1);
               } else {
                  oo.writeByte(0);
               }
            }
         }

         resourceList = this.tx.getResourceInfoList();
         if (resourceList == null) {
            IOHelper.writeCompressedInt(oo, 0);
         } else if (streamVersion == 2) {
            len = resourceList.size();
            IOHelper.writeCompressedInt(oo, len);

            for(i = 0; i < len; ++i) {
               ri = (ResourceInfo)resourceList.get(i);
               this.writeAbbrevString(oo, ri.getName());
            }
         } else {
            len = resourceList.size();
            IOHelper.writeCompressedInt(oo, len);

            for(i = 0; i < len; ++i) {
               ri = (ResourceInfo)resourceList.get(i);
               this.writeAbbrevString(oo, ri.getName());
               resourceSCList = ri.getSCInfoList();
               if (resourceSCList == null) {
                  IOHelper.writeCompressedInt(oo, 0);
               } else {
                  sclen = resourceSCList.size();
                  IOHelper.writeCompressedInt(oo, sclen);

                  for(j = 0; j < sclen; ++j) {
                     sci = (SCInfo)resourceSCList.get(j);
                     this.writeAbbrevString(oo, sci.getScUrl(this.tx));
                     if (isDebugCoURL()) {
                        (new Throwable("PropagationContext writeExternal V3 getScUrl:" + sci.getScUrl(this.tx))).printStackTrace();
                     }
                  }
               }
            }
         }

         Map props = this.filterOTSAndWSATProperties(this.tx.getProperties());
         if (streamVersion == 4 && this.infectCoordinatorFirstTime) {
            if (props == null) {
               props = new HashMap();
            }

            ((Map)props).put("weblogic.transaction.infectCoordinatorFirstTime", "true");
         }

         i = props == null ? 0 : ((Map)props).size();
         IOHelper.writeCompressedInt(oo, i);
         if (i > 0) {
            Iterator iter = ((Map)props).entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               this.writeAbbrevString(oo, (String)entry.getKey());
               oo.writeObject(entry.getValue());
            }
         }
      }

   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.readOrWriteRollbackReasonInBytes = this.useNewMethod(oi);
      this.setPeerInfo(oi);
      this.inVersion = IOHelper.readCompressedInt(oi);
      int len;
      int i;
      int sclen;
      String[] curResSCURLs;
      int j;
      if (this.inVersion != 2 && this.inVersion != 3 && this.inVersion != 4) {
         if (this.inVersion != 1) {
            throw new IOException("Unsupported JTA PropagationContext version " + this.inVersion);
         }

         this.xid = new XidImpl();
         this.xid.readExternal(oi);
         this.timeToLiveSec = IOHelper.readCompressedInt(oi);
         this.timeoutSec = IOHelper.readCompressedInt(oi);
         this.isCancelled = oi.readBoolean();
         if (this.isCancelled) {
            this.readRollbackReason(oi);
         }

         this.coordinatorURL = oi.readUTF();
         this.txName = oi.readUTF();
         len = IOHelper.readCompressedInt(oi);
         if (len > 0) {
            this.scURLs = new String[len];
            this.scStates = new byte[len];
            this.scSyncRegs = new byte[len];

            for(i = 0; i < len; ++i) {
               this.scURLs[i] = oi.readUTF();
               this.scStates[i] = oi.readByte();
               this.scSyncRegs[i] = 1;
               if (isDebugCoURL()) {
                  (new Throwable("PropagationCOntext scInfo scURLs" + this.scURLs[i])).printStackTrace();
               }
            }
         }

         len = IOHelper.readCompressedInt(oi);
         if (len > 0) {
            this.resNames = new String[len];
            this.resSCURLs = new String[len][];

            for(i = 0; i < len; ++i) {
               this.resNames[i] = oi.readUTF();
               sclen = IOHelper.readCompressedInt(oi);
               if (sclen > 0) {
                  curResSCURLs = this.resSCURLs[i] = new String[sclen];

                  for(j = 0; j < sclen; ++j) {
                     curResSCURLs[j] = oi.readUTF();
                  }
               }
            }
         }

         i = IOHelper.readCompressedInt(oi);
         if (i > 0) {
            this.txProps = (HashMap)oi.readObject();
         }
      } else {
         this.xid = new XidImpl();
         this.xid.readExternal(oi);
         this.timeToLiveSec = IOHelper.readCompressedInt(oi);
         this.timeoutSec = IOHelper.readCompressedInt(oi);
         this.isCancelled = oi.readBoolean();
         if (this.isCancelled) {
            this.readRollbackReason(oi);
         }

         this.coordinatorURL = this.readAbbrevString(oi);
         this.txName = this.readAbbrevString(oi);
         len = IOHelper.readCompressedInt(oi);
         if (len > 0) {
            this.scURLs = new String[len];
            this.scStates = new byte[len];
            this.scSyncRegs = new byte[len];

            for(i = 0; i < len; ++i) {
               this.scURLs[i] = this.readAbbrevString(oi);
               this.scStates[i] = oi.readByte();
               this.scSyncRegs[i] = oi.readByte();
               if (isDebugCoURL()) {
                  (new Throwable("PropagationContext scURLs:" + this.scURLs[i])).printStackTrace();
               }
            }
         }

         len = IOHelper.readCompressedInt(oi);
         if (len > 0) {
            if (this.inVersion == 2) {
               this.resNames = new String[len];

               for(i = 0; i < len; ++i) {
                  this.resNames[i] = this.readAbbrevString(oi);
               }
            } else {
               this.resNames = new String[len];
               this.resSCURLs = new String[len][];

               for(i = 0; i < len; ++i) {
                  this.resNames[i] = this.readAbbrevString(oi);
                  sclen = IOHelper.readCompressedInt(oi);
                  if (sclen > 0) {
                     curResSCURLs = this.resSCURLs[i] = new String[sclen];

                     for(j = 0; j < sclen; ++j) {
                        curResSCURLs[j] = this.readAbbrevString(oi);
                     }
                  }
               }
            }
         }

         len = IOHelper.readCompressedInt(oi);
         if (len > 0) {
            this.txProps = new HashMap(len);

            for(i = 0; i < len; ++i) {
               Object key = this.readAbbrevString(oi);
               Object val = oi.readObject();
               this.txProps.put(key, val);
            }

            if (this.inVersion == 4 && this.txProps.remove("weblogic.transaction.infectCoordinatorFirstTime") != null) {
               this.infectCoordinatorFirstTime = true;
            }
         }
      }

   }

   public TransactionImpl getTransaction() throws TransactionSystemException {
      if (TxDebug.JTAPropagate.isDebugEnabled()) {
         TxDebug.JTAPropagate.debug("PropagationContext.getTransaction: tx=" + this.tx);
      }

      if (this.tx != null) {
         return this.xid == null ? this.tx : (TransactionImpl)getTM().getTransaction(this.xid);
      } else if (this.inVersion >= 1 && this.inVersion <= 4) {
         TransactionManagerImpl tm = getTM();
         boolean isCoordinator = tm.isLocalCoordinator(this.coordinatorURL);
         Xid foreignXid = null;
         if (isCoordinator && this.txProps != null) {
            foreignXid = (Xid)this.txProps.get("weblogic.transaction.foreignXid");
         }

         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.JTAPropagate.debug("PropagationContext.getTransaction: xid=" + this.xid + ", isCoordinator=" + isCoordinator + ", infectCoordinatorFirstTime=" + this.infectCoordinatorFirstTime + ", txProps=" + this.txProps);
         }

         synchronized(tm) {
            this.tx = (TransactionImpl)tm.getTransactionUnsync(this.xid);
            if (this.tx == null) {
               label151: {
                  Object var10000;
                  try {
                     if (!isCoordinator || this.inVersion < 4 || foreignXid != null || this.infectCoordinatorFirstTime) {
                        if (isCoordinator && foreignXid != null) {
                           this.tx = tm.createImportedTransaction(this.xid, foreignXid, this.timeoutSec, this.timeToLiveSec);
                           this.tx.setDelayRemoveAfterRollback(true);
                           break label151;
                        }

                        boolean isSSLURL = PlatformHelper.getPlatformHelper().isSSLURL(this.coordinatorURL);
                        this.tx = tm.createTransaction(this.xid, this.timeoutSec, this.timeToLiveSec, isSSLURL, true);
                        if (isCoordinator && this.inVersion < 4) {
                           this.tx.setDelayRemoveAfterRollback(true);
                        }
                        break label151;
                     }

                     var10000 = null;
                  } catch (SystemException var8) {
                     throw new TransactionSystemException("Unable to create transaction: " + this.xid + ".  " + var8);
                  }

                  return (TransactionImpl)var10000;
               }
            }
         }

         if (this.isCancelled && !this.tx.isOver() && !this.tx.isCancelled()) {
            if (this.rollbackReasonInBytes != null) {
               this.rollbackReason = this.convertBytesToRollbackReason(this.rollbackReasonInBytes);
            }

            this.tx.setRollbackOnly(this.rollbackReason);
         }

         this.tx.setCoordinatorURL(this.coordinatorURL);
         this.tx.setName(this.txName);
         int i;
         if (this.scURLs != null) {
            for(i = 0; i < this.scURLs.length; ++i) {
               String scURL = this.scURLs[i];
               if (!tm.isLocalCoordinator(scURL)) {
                  SCInfo sci = this.tx.getOrCreateSCInfo(scURL);
                  sci.setState(this.scStates[i]);
                  if (this.scSyncRegs[i] == 1) {
                     sci.setSyncRegistered(true);
                  }
               }
            }
         }

         if (this.txProps != null) {
            this.tx.addProperties(this.txProps);
         }

         if (this.resNames != null) {
            for(i = 0; i < this.resNames.length; ++i) {
               ResourceInfo ri = this.tx.getOrCreateResourceInfo(this.resNames[i], true);
               if (this.resSCURLs != null) {
                  String[] curSCURLs = this.resSCURLs[i];
                  if (curSCURLs != null) {
                     for(int j = 0; j < curSCURLs.length; ++j) {
                        ri.addSC(this.tx.getOrCreateSCInfo(curSCURLs[j]));
                     }
                  }
               }
            }
         }

         return this.tx;
      } else {
         throw new TransactionSystemException("Tx context version mismatch. This version =" + getVersion() + ", incoming version = " + this.inVersion + ", supported versions = " + 1 + ", " + 2 + ", " + 3 + ", " + 4 + ".");
      }
   }

   Object getPeerInfo() {
      return this.peerInfo;
   }

   private int getVersion(ObjectOutput oo) throws IOException {
      return PlatformHelper.getPlatformHelper().getVersion(oo);
   }

   private void setPeerInfo(ObjectInput oi) throws IOException {
      Object object = PlatformHelper.getPlatformHelper().getPeerInfo(oi);
      if (object != null) {
         this.peerInfo = object;
      }

   }

   private static TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }

   private void writeAbbrevString(ObjectOutput oo, String s) throws IOException {
      PlatformHelper.getPlatformHelper().writeAbbrevString0(oo, s);
   }

   private String readAbbrevString(ObjectInput oi) throws IOException {
      return PlatformHelper.getPlatformHelper().readAbbrevString(oi);
   }

   static int getVersion() {
      return 4;
   }

   public void infectCoordinatorFirstTime() {
      this.infectCoordinatorFirstTime = true;
   }

   private byte[] convertRollbackReasonToBytes(Throwable reason) throws IOException {
      ByteArrayOutputStream baos = null;
      ObjectOutputStream oos = null;

      byte[] var20;
      try {
         baos = new ByteArrayOutputStream();
         oos = new ObjectOutputStream(baos);
         oos.writeObject(reason);
         oos.flush();
         byte[] rollbackReasonBytes = baos.toByteArray();
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            String rollbackReasonString = "";
            if (rollbackReasonBytes != null) {
               byte[] var6 = rollbackReasonBytes;
               int var7 = rollbackReasonBytes.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  byte rollbackReasonByte = var6[var8];
                  rollbackReasonString = rollbackReasonString + rollbackReasonByte;
               }
            }

            TxDebug.JTAPropagate.debug(" +++ converted rollback reason ('" + reason + "') to bytes : " + rollbackReasonString);
         }

         var20 = rollbackReasonBytes;
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException var18) {
            }
         }

         if (baos != null) {
            try {
               baos.close();
            } catch (IOException var17) {
            }
         }

      }

      return var20;
   }

   private Throwable convertBytesToRollbackReason(byte[] bytes) {
      if (bytes == null) {
         return null;
      } else {
         ByteArrayInputStream bais = null;
         ObjectInputStream ois = null;

         try {
            bais = new ByteArrayInputStream(bytes);
            ois = new TxObjectInputStream(bais);
            Throwable reason = (Throwable)ois.readObject();
            if (TxDebug.JTAPropagate.isDebugEnabled()) {
               TxDebug.JTAPropagate.debug(" +++ converted bytes to rollback reason : " + reason);
            }

            Throwable var5 = reason;
            return var5;
         } catch (IOException var22) {
            System.err.println(" Error while reading rollback reason : " + PlatformHelper.getPlatformHelper().throwable2StackTrace(var22));
         } catch (ClassNotFoundException var23) {
            System.err.println(" Error while reading rollback reason : " + PlatformHelper.getPlatformHelper().throwable2StackTrace(var23));
         } finally {
            if (ois != null) {
               try {
                  ois.close();
               } catch (IOException var21) {
               }
            }

            if (bais != null) {
               try {
                  bais.close();
               } catch (IOException var20) {
               }
            }

         }

         return null;
      }
   }

   private void readRollbackReason(ObjectInput oi) throws IOException, ClassNotFoundException {
      if (this.readOrWriteRollbackReasonInBytes) {
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.JTAPropagate.debug(" +++ Using new Method for reading rollback reason....");
         }

         this.rollbackReasonInBytes = (byte[])((byte[])oi.readObject());
      } else {
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.JTAPropagate.debug(" +++ Using old Method for reading rollback reason....");
         }

         Object o = oi.readObject();
         if (o instanceof byte[]) {
            if (TxDebug.JTAPropagate.isDebugEnabled()) {
               TxDebug.JTAPropagate.debug(" +++ Using old Method for reading rollback reason but object was of type byte[]....");
            }

            this.rollbackReasonInBytes = (byte[])((byte[])o);
         } else {
            this.rollbackReason = (Throwable)o;
         }
      }

   }

   private void writeRollbackReason(ObjectOutput oo) throws IOException {
      if (this.readOrWriteRollbackReasonInBytes) {
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.JTAPropagate.debug(" +++ Using new Method for writing rollback reason....");
         }

         oo.writeObject(this.convertRollbackReasonToBytes(this.tx.getRollbackReason()));
      } else {
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.JTAPropagate.debug(" +++ Using old Method for writing rollback reason....");
         }

         oo.writeObject(this.tx.getRollbackReason());
      }

   }

   private boolean useNewMethod(Object obj) {
      return PlatformHelper.getPlatformHelper().useNewMethod(obj);
   }

   private Map filterOTSAndWSATProperties(Map props) {
      if (props != null) {
         props.remove("weblogic.transaction.ots.resources");
         props.remove("weblogic.transaction.ots.ftmCounter");
         props.remove("weblogic.wsee.wstx.foreignContext");
      }

      return props;
   }

   public int getTimeoutSecs() {
      return this.timeoutSec;
   }

   private class TxObjectInputStream extends FilteringObjectInputStream {
      private TxObjectInputStream(InputStream is) throws IOException {
         super(is);
      }

      protected Class resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
         ClassLoader loader = PlatformHelper.getPlatformHelper().getContextClassLoader();
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.JTAPropagate.debug(" +++ looking up class : " + v.getName() + " ::  in classloader : " + loader);
         }

         return Class.forName(v.getName(), false, loader);
      }

      // $FF: synthetic method
      TxObjectInputStream(InputStream x1, Object x2) throws IOException {
         this(x1);
      }
   }
}
