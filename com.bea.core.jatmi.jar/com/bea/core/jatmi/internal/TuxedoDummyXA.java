package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.wtc.gwt.OatmialServices;

public final class TuxedoDummyXA implements XAResource {
   private static final int DEFAULT_TIMEOUT = 600;
   private Xid myXid;
   private int myTimeout = 600;
   private OatmialServices tos = ConfigHelper.getTuxedoServices();
   private String myRegistryName = null;

   public void commit(Xid xid, boolean onePhase) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/commit/" + xid + "/" + onePhase);
      }

   }

   public void recoveryCommit(Xid xid, boolean onePhase) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/recoveryCommit/" + xid + "/" + onePhase);
      }

   }

   public void end(Xid xid, int flags) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/end/" + xid + "/" + flags);
      }

      this.myXid = null;
   }

   public void forget(Xid xid) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/forget/" + xid);
      }

   }

   public int getRealTransactionTimeout() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      int ret = -1;
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/getRealTransactionTimeout/");
      }

      if (this.myXid != null) {
         ret = TCTransactionHelper.getRealTransactionTimeout();
      }

      if (ret == -1) {
         ret = this.myTimeout;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoDummyXA/getRealTransactionTimeout/10/" + ret);
      }

      return ret;
   }

   public int getTransactionTimeout() throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/getTransactionTimeout/");
         ntrace.doTrace("]/TuxedoDummyXA/getTransactionTimeout/10/" + this.myTimeout);
      }

      return this.myTimeout;
   }

   public boolean isSameRM(XAResource xares) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/isSameRM/" + xares);
      }

      if (this == xares) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoDummyXA/isSameRM/10/true");
         }

         return true;
      } else {
         if (xares instanceof TuxedoDummyXA) {
            if (traceEnabled) {
               ntrace.doTrace("/TuxedoDummyXA/isSameRM/incoming res name: " + ((TuxedoDummyXA)xares).getRegistryName() + "/current res name: " + this.getRegistryName());
            }

            if (this.getRegistryName().equals(((TuxedoDummyXA)xares).getRegistryName())) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoDummyXA/isSameRM/20/true");
               }

               return true;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoDummyXA/isSameRM/30/false");
         }

         return false;
      }
   }

   public int prepare(Xid xid) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/prepare/" + xid);
      }

      return 0;
   }

   public Xid[] recover(int flag) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/recover/" + flag);
      }

      return new Xid[0];
   }

   public void rollback(Xid xid) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/rollback/" + xid);
      }

   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/setTransactionTimeout/" + seconds);
      }

      boolean ret;
      if (seconds <= 0) {
         this.myTimeout = 600;
         ret = false;
      } else {
         this.myTimeout = seconds;
         ret = true;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoDummyXA/setTransactionTimeout/10/" + ret);
      }

      return ret;
   }

   public void start(Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/start/" + xid);
      }

      this.myXid = xid;
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoDummyXA/start/10");
      }

   }

   public void start(Xid xid, int flags) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoDummyXA/start/" + xid + "/" + flags);
      }

      this.myXid = xid;
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoDummyXA/start/10");
      }

   }

   public Xid getXid() {
      return this.myXid;
   }

   public String getRegistryName() {
      if (this.myRegistryName == null) {
         this.myRegistryName = "OatmialDummyResource";
         if (this.tos.getRMNameSuffix() != null) {
            this.myRegistryName = this.myRegistryName + this.tos.getRMNameSuffix();
         }
      }

      return this.myRegistryName;
   }
}
