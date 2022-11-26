package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.internal.TCResourceHelper;
import java.util.HashSet;
import java.util.Map;
import java.util.Timer;
import javax.naming.Context;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.Txid;

public final class OatmialServices {
   private Context myNameService;
   private Timer myTimeService;
   private Map myOutboundXidMap;
   private Map myInboundXidMap;
   private Map myXidRetryMap;
   private Map myXidReadyMap;
   private Map myOutboundFXidMap;
   private Map myOutboundTxidMap;
   private String myRMNameSuffix;

   public OatmialServices(Context nameService, Timer timeService, Map anOutboundXidMap, Map anInboundXidMap, Map anXidRetryMap, Map anXidReadyMap, Map anOutboundFXidMap) {
      this.myNameService = nameService;
      this.myTimeService = timeService;
      this.myOutboundXidMap = anOutboundXidMap;
      this.myInboundXidMap = anInboundXidMap;
      this.myXidRetryMap = anXidRetryMap;
      this.myXidReadyMap = anXidReadyMap;
      this.myOutboundFXidMap = anOutboundFXidMap;
   }

   public OatmialServices(Context nameService, Timer timeService, Map anOutboundXidMap, Map anInboundXidMap, Map anXidRetryMap, Map anXidReadyMap, Map anOutboundFXidMap, Map anOutboundTxidMap) {
      this.myNameService = nameService;
      this.myTimeService = timeService;
      this.myOutboundXidMap = anOutboundXidMap;
      this.myInboundXidMap = anInboundXidMap;
      this.myXidRetryMap = anXidRetryMap;
      this.myXidReadyMap = anXidReadyMap;
      this.myOutboundFXidMap = anOutboundFXidMap;
      this.myOutboundTxidMap = anOutboundTxidMap;
   }

   public OatmialServices(Context nameService, Timer timeService, Map anOutboundXidMap, Map anInboundXidMap, Map anXidRetryMap, Map anXidReadyMap, Map anOutboundFXidMap, Map anOutboundTxidMap, String anRMNameSuffix) {
      this.myNameService = nameService;
      this.myTimeService = timeService;
      this.myOutboundXidMap = anOutboundXidMap;
      this.myInboundXidMap = anInboundXidMap;
      this.myXidRetryMap = anXidRetryMap;
      this.myXidReadyMap = anXidReadyMap;
      this.myOutboundFXidMap = anOutboundFXidMap;
      this.myOutboundTxidMap = anOutboundTxidMap;
      this.myRMNameSuffix = anRMNameSuffix;
   }

   public Context getNameService() {
      return this.myNameService;
   }

   public Timer getTimeService() {
      return this.myTimeService;
   }

   public void setCommitRetry(Xid xid, boolean retry) {
      synchronized(this.myXidRetryMap) {
         if ((Boolean)this.myXidRetryMap.get(xid) != null) {
            this.myXidRetryMap.remove(xid);
         }

         this.myXidRetryMap.put(xid, retry);
      }
   }

   public boolean getCommitRetry(Xid xid) {
      synchronized(this.myXidRetryMap) {
         Boolean value;
         return (value = (Boolean)this.myXidRetryMap.get(xid)) != null ? value : false;
      }
   }

   public void addOutboundRdomToXid(Xid xid, TuxedoConnectorRAP rdom) {
      Xid mapXid = null;
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/addOutboundRdomtoXid/" + rdom + "/" + xid);
      }

      if (rdom != null && xid != null) {
         if (xid.getFormatId() != TCResourceHelper.getWLSFormatID()) {
            mapXid = TCResourceHelper.createWLSXid(xid.getFormatId(), xid.getGlobalTransactionId(), (byte[])null);
         } else {
            mapXid = xid;
         }

         synchronized(this.myOutboundXidMap) {
            HashSet myRdomSet;
            if ((myRdomSet = (HashSet)this.myOutboundXidMap.get(mapXid)) == null) {
               myRdomSet = new HashSet();
               this.myOutboundXidMap.put(mapXid, myRdomSet);
            }

            myRdomSet.add(rdom);
         }

         synchronized(this.myOutboundTxidMap) {
            Txid txid = new Txid(mapXid.getGlobalTransactionId());
            if (traceEnabled) {
               ntrace.doTrace("/OatmialServices/addOutboundRdomtoXid/19/Txid:" + txid);
            }

            if (this.myOutboundTxidMap.get(txid) == null) {
               this.myOutboundTxidMap.put(txid, xid);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addOutboundRdomtoXid/20/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addOutboundRdomtoXid/10/");
         }

      }
   }

   public void removeOutboundRdomFromXid(TuxedoConnectorRAP rdom, Xid xid) {
      Xid mapXid = null;
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/removeOutboundRdomFromXid/" + rdom + "/" + xid);
      }

      if (rdom != null && xid != null) {
         if (xid.getFormatId() != TCResourceHelper.getWLSFormatID()) {
            mapXid = TCResourceHelper.createWLSXid(xid.getFormatId(), xid.getGlobalTransactionId(), (byte[])null);
         } else {
            mapXid = xid;
         }

         synchronized(this.myOutboundXidMap) {
            HashSet myRdomSet;
            if ((myRdomSet = (HashSet)this.myOutboundXidMap.get(mapXid)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/removeOutboundRdomFromXid/20/");
               }

               return;
            }

            myRdomSet.remove(rdom);
            if (myRdomSet.size() == 0) {
               this.deleteOutboundRdomsAssociatedWithXid(mapXid);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/removeOutboundRdomFromXid/30/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/removeOutboundRdomFromXid/10/");
         }

      }
   }

   public void removeOutboundRdomFromXid(TuxedoConnectorRAP rdom, Txid txid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/removeOutboundRdomFromXid/" + rdom + "/" + txid);
      }

      if (rdom != null && txid != null) {
         Xid xid;
         synchronized(this.myOutboundTxidMap) {
            if (traceEnabled) {
               ntrace.doTrace("/OatmialServices/removeOutboundRdomFromXid/18/Txid:" + txid);
            }

            if ((xid = (Xid)this.myOutboundTxidMap.get(txid)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/removeOutboundRdomFromXid/19/");
               }

               return;
            }
         }

         this.removeOutboundRdomFromXid(rdom, xid);
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/removeOutboundRdomFromXid/30/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/removeOutboundRdomFromXid/10/");
         }

      }
   }

   public TuxedoConnectorRAP[] getOutboundRdomsAssociatedWithXid(Xid xid) {
      Xid mapXid = null;
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/getOutboundRdomsAssociatedWithXid/" + xid);
      }

      TuxedoConnectorRAP[] rClass = new TuxedoConnectorRAP[0];
      if (xid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getOutboundRdomsAssociatedWithXid/10/null");
         }

         return null;
      } else {
         if (xid.getFormatId() != TCResourceHelper.getWLSFormatID()) {
            mapXid = TCResourceHelper.createWLSXid(xid.getFormatId(), xid.getGlobalTransactionId(), (byte[])null);
         } else {
            mapXid = xid;
         }

         synchronized(this.myOutboundXidMap) {
            HashSet myRdomSet;
            if ((myRdomSet = (HashSet)this.myOutboundXidMap.get(mapXid)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/getOutboundRdomsAssociatedWithXid/20/null");
               }

               return null;
            }

            if (myRdomSet.size() == 0) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/getOutboundRdomsAssociatedWithXid/30/null");
               }

               return null;
            }

            rClass = (TuxedoConnectorRAP[])((TuxedoConnectorRAP[])myRdomSet.toArray(rClass));
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getOutboundRdomsAssociatedWithXid/40/" + rClass.length);
         }

         return rClass;
      }
   }

   public TuxedoConnectorRAP[] getOutboundRdomsAssociatedWithXid(Txid txid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/getOutboundRdomsAssociatedWithXid/" + txid);
      }

      TuxedoConnectorRAP[] rClass = new TuxedoConnectorRAP[0];
      if (txid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getOutboundRdomsAssociatedWithXid/10/null");
         }

         return null;
      } else {
         Xid xid;
         synchronized(this.myOutboundTxidMap) {
            if (traceEnabled) {
               ntrace.doTrace("/OatmialServices/getOutboundRdomsAssociatedWithXid/20/Txid:" + txid);
            }

            if ((xid = (Xid)this.myOutboundTxidMap.get(txid)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/getOutboundRdomsAssociatedWithXid/19/");
               }

               return null;
            }
         }

         rClass = this.getOutboundRdomsAssociatedWithXid(xid);
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getOutboundRdomsAssociatedWithXid/40/" + rClass.length);
         }

         return rClass;
      }
   }

   public void deleteOutboundRdomsAssociatedWithXid(Xid xid) {
      Xid mapXid = null;
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/deleteOutboundRdomsAssociatedWithXid/" + xid);
      }

      this.deleteOutboundXidAssociatedWithFXid(xid);
      if (xid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteOutboundRdomsAssociatedWithXid/10/null");
         }

      } else {
         if (xid.getFormatId() != TCResourceHelper.getWLSFormatID()) {
            mapXid = TCResourceHelper.createWLSXid(xid.getFormatId(), xid.getGlobalTransactionId(), (byte[])null);
         } else {
            mapXid = xid;
         }

         synchronized(this.myOutboundXidMap) {
            this.myOutboundXidMap.remove(mapXid);
            ConfigHelper.forgetRecoveredXid(mapXid);
         }

         synchronized(this.myOutboundTxidMap) {
            Txid txid = new Txid(mapXid.getGlobalTransactionId());
            if (traceEnabled) {
               ntrace.doTrace("/OatmialServices/deleteOutboundRdomsAssociatedWithXid/18/Txid:" + txid);
            }

            this.myOutboundTxidMap.remove(txid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteOutboundRdomsAssociatedWithXid/20");
         }

      }
   }

   public void addInboundRdomToXid(Xid xid, TuxedoConnectorRAP rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/addInboundRdomtoXid/" + rdom + "/" + xid);
      }

      if (rdom != null && xid != null) {
         synchronized(this.myInboundXidMap) {
            HashSet myRdomSet;
            if ((myRdomSet = (HashSet)this.myInboundXidMap.get(xid)) == null) {
               myRdomSet = new HashSet();
               this.myInboundXidMap.put(xid, myRdomSet);
            }

            myRdomSet.add(rdom);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addInboundRdomtoXid/20/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addInboundRdomtoXid/10/");
         }

      }
   }

   public void removeInboundRdomFromXid(TuxedoConnectorRAP rdom, Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/removeInboundRdomFromXid/" + rdom + "/" + xid);
      }

      if (rdom != null && xid != null) {
         synchronized(this.myInboundXidMap) {
            HashSet myRdomSet;
            if ((myRdomSet = (HashSet)this.myInboundXidMap.get(xid)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/removeInboundRdomFromXid/20/");
               }

               return;
            }

            myRdomSet.remove(rdom);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/removeInboundRdomFromXid/30/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/removeInboundRdomFromXid/10/");
         }

      }
   }

   public TuxedoConnectorRAP[] getInboundRdomsAssociatedWithXid(Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/getInboundRdomsAssociatedWithXid/" + xid);
      }

      TuxedoConnectorRAP[] rClass = new TuxedoConnectorRAP[0];
      if (xid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getInboundRdomsAssociatedWithXid/10/null");
         }

         return null;
      } else {
         synchronized(this.myInboundXidMap) {
            HashSet myRdomSet;
            if ((myRdomSet = (HashSet)this.myInboundXidMap.get(xid)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/getInboundRdomsAssociatedWithXid/20/null");
               }

               return null;
            }

            if (myRdomSet.size() == 0) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialServices/getInboundRdomsAssociatedWithXid/30/null");
               }

               return null;
            }

            rClass = (TuxedoConnectorRAP[])((TuxedoConnectorRAP[])myRdomSet.toArray(rClass));
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getInboundRdomsAssociatedWithXid/40/" + rClass.length);
         }

         return rClass;
      }
   }

   public void deleteInboundRdomsAssociatedWithXid(Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/deleteInboundRdomsAssociatedWithXid/" + xid);
      }

      if (xid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteInboundRdomsAssociatedWithXid/10/null");
         }

      } else {
         synchronized(this.myInboundXidMap) {
            this.myInboundXidMap.remove(xid);
            ConfigHelper.forgetRecoveredXid(xid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteInboundRdomsAssociatedWithXid/20");
         }

      }
   }

   public void addXidToReadyMap(Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/addXidToReadyMap/" + xid);
      }

      if (xid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addXidToReadyMap/10/");
         }

      } else {
         synchronized(this.myXidReadyMap) {
            this.myXidReadyMap.put(xid, xid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addXidToReadyMap/20/");
         }

      }
   }

   public void deleteXidFromReadyMap(Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/deleteXidFromReadyMap/" + xid);
      }

      if (xid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteXidFromReadyMap/10/null");
         }

      } else {
         synchronized(this.myXidReadyMap) {
            this.myXidReadyMap.remove(xid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteXidFromReadyMap/20/DONE");
         }

      }
   }

   public boolean isXidInReadyMap(Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/isXidInReadyMap/" + xid);
      }

      if (xid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/isXidInReadyMap/10/false");
         }

         return false;
      } else {
         boolean rc;
         synchronized(this.myXidReadyMap) {
            rc = this.myXidReadyMap.containsKey(xid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/isXidInReadyMap/20/" + rc);
         }

         return rc;
      }
   }

   public void addOutboundXidToFXid(Xid fxid, Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/addOutboundXidToFXid/" + xid);
      }

      if (fxid != null && xid != null) {
         Xid mapXid = TCResourceHelper.createWLSXid(fxid.getFormatId(), fxid.getGlobalTransactionId(), (byte[])null);
         synchronized(this.myOutboundFXidMap) {
            this.myOutboundFXidMap.put(mapXid, xid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addOutboundXidToFXid/20/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/addOutboundXidToFXid/10/");
         }

      }
   }

   public Xid getOutboundXidAssociatedWithFXid(Xid fxid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/getOutboundXidAssociatedWithFXid/" + fxid);
      }

      if (fxid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getOutboundXidAssociatedWithFXid/10/null");
         }

         return null;
      } else {
         Xid mapXid = TCResourceHelper.createWLSXid(fxid.getFormatId(), fxid.getGlobalTransactionId(), (byte[])null);
         Xid xid;
         synchronized(this.myOutboundFXidMap) {
            xid = (Xid)this.myOutboundFXidMap.get(mapXid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/getOutboundXidAssociatedWithFXid/20/" + xid);
         }

         return xid;
      }
   }

   public void deleteOutboundXidAssociatedWithFXid(Xid fxid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialServices/deleteOutboundXidAssociatedWithFXid/" + fxid);
      }

      if (fxid == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteOutboundXidAssociatedWithFXid/10/null");
         }

      } else {
         Xid mapXid = TCResourceHelper.createWLSXid(fxid.getFormatId(), fxid.getGlobalTransactionId(), (byte[])null);
         synchronized(this.myOutboundFXidMap) {
            this.myOutboundFXidMap.remove(mapXid);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialServices/deleteOutboundXidAssociatedWithFXid/20/");
         }

      }
   }

   public String getRMNameSuffix() {
      return this.myRMNameSuffix;
   }
}
