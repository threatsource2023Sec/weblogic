package weblogic.connector.inbound;

import java.util.Map;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.connector.common.Debug;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.transaction.InterposedTransactionManager;
import weblogic.transaction.TxHelper;

public class XATerminator implements javax.resource.spi.XATerminator {
   private static XATerminator oneOfMe;
   private XAResource myXAResource;
   static final long serialVersionUID = 6447778778131583302L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.inbound.XATerminator");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Inbound_Transaction_Start_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Inbound_Transaction_Commmit_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Inbound_Transaction_Rollback_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   private XATerminator() {
      InterposedTransactionManager itm = TxHelper.getServerInterposedTransactionManager();
      this.myXAResource = itm.getXAResource();
   }

   public synchronized void commit(Xid xid, boolean onePhase) throws XAException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = InstrumentationSupport.toSensitive(3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.preProcess(var3);
         }

         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      try {
         Debug.xaIn("XATerminator.commit() called; calling myXAResource.commit()");
         this.myXAResource.commit(xid, onePhase);
         Debug.xaIn("XATerminator.commit() returned from myXAResource.commit() & exiting");
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            if (var3.monitorHolder[3] != null) {
               var3.monitorIndex = 3;
               InstrumentationSupport.process(var3);
            }

            if (var3.monitorHolder[1] != null) {
               var3.monitorIndex = 1;
               InstrumentationSupport.postProcess(var3);
            }
         }

         throw var5;
      }

      if (var3 != null) {
         if (var3.monitorHolder[3] != null) {
            var3.monitorIndex = 3;
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.postProcess(var3);
         }
      }

   }

   public synchronized void forget(Xid xid) throws XAException {
      Debug.xaIn("XATerminator.forget() called; calling myXAResource.commit()");
      this.myXAResource.forget(xid);
      Debug.xaIn("XATerminator.forget() returned from myXAResource.forget() & exiting");
   }

   public synchronized Xid[] recover(int flag) throws XAException {
      return this.myXAResource.recover(flag);
   }

   public synchronized void rollback(Xid xid) throws XAException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.myXAResource.rollback(xid);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[3] != null) {
               var2.monitorIndex = 3;
               InstrumentationSupport.process(var2);
            }

            if (var2.monitorHolder[2] != null) {
               var2.monitorIndex = 2;
               InstrumentationSupport.postProcess(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[3] != null) {
            var2.monitorIndex = 3;
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.postProcess(var2);
         }
      }

   }

   public synchronized int prepare(Xid xid) throws XAException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      int var10000;
      try {
         var10000 = this.myXAResource.prepare(xid);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = InstrumentationSupport.convertToObject(0);
            if (var2.monitorHolder[3] != null) {
               var2.monitorIndex = 3;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }

            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.postProcess(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = InstrumentationSupport.convertToObject(var10000);
         if (var2.monitorHolder[3] != null) {
            var2.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.postProcess(var2);
         }
      }

      return var10000;
   }

   public static synchronized XATerminator getXATerminator() {
      if (oneOfMe == null) {
         oneOfMe = new XATerminator();
      }

      return oneOfMe;
   }

   static {
      _WLDF$INST_FLD_Connector_Before_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Inbound");
      _WLDF$INST_FLD_Connector_Around_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Inbound");
      _WLDF$INST_FLD_Connector_Inbound_Transaction_Start_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Inbound_Transaction_Start_Medium");
      _WLDF$INST_FLD_Connector_After_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Inbound");
      _WLDF$INST_FLD_Connector_Inbound_Transaction_Commmit_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Inbound_Transaction_Commmit_Medium");
      _WLDF$INST_FLD_Connector_Inbound_Transaction_Rollback_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Inbound_Transaction_Rollback_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XATerminator.java", "weblogic.connector.inbound.XATerminator", "commit", "(Ljavax/transaction/xa/Xid;Z)V", 48, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Before_Inbound, _WLDF$INST_FLD_Connector_Around_Inbound, _WLDF$INST_FLD_Connector_Inbound_Transaction_Commmit_Medium, _WLDF$INST_FLD_Connector_After_Inbound};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XATerminator.java", "weblogic.connector.inbound.XATerminator", "rollback", "(Ljavax/transaction/xa/Xid;)V", 89, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Before_Inbound, _WLDF$INST_FLD_Connector_Inbound_Transaction_Rollback_Low, _WLDF$INST_FLD_Connector_Around_Inbound, _WLDF$INST_FLD_Connector_After_Inbound};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XATerminator.java", "weblogic.connector.inbound.XATerminator", "prepare", "(Ljavax/transaction/xa/Xid;)I", 102, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Before_Inbound, _WLDF$INST_FLD_Connector_Around_Inbound, _WLDF$INST_FLD_Connector_Inbound_Transaction_Start_Medium, _WLDF$INST_FLD_Connector_After_Inbound};
   }
}
