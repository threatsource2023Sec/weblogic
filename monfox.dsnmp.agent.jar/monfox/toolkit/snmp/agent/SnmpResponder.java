package monfox.toolkit.snmp.agent;

import java.util.Enumeration;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.ext.audit.SnmpAuditTrailLogger;
import monfox.toolkit.snmp.agent.impl.Task;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpErrorListener;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageListener;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.util.ExecManager;
import monfox.toolkit.snmp.util.Queue;
import monfox.toolkit.snmp.util.WorkItem;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;

public class SnmpResponder implements SnmpMessageListener, SnmpErrorListener {
   private Queue a = null;
   private ExecManager b = null;
   private SnmpAgent c;
   private SnmpEngine d;
   private SnmpRequestProcessor e;
   private SnmpRequestProcessor f;
   private SnmpRequestProcessor g;
   private SnmpAccessControlModel h;
   private SnmpSecurityModel i;
   private SnmpContext j;
   private boolean k = true;
   private Logger l = Logger.getInstance(a("\u001c\u00186K\u007f\u0000\u0019 I"));

   protected SnmpResponder(SnmpAgent var1) {
      this.c = var1;
      this.d = this.c.getEngine();
      this.h = this.c.getAccessControlModel();
      this.i = this.c.getSecurityModel();
      this.d.addRequestListener(this);
      if (this.c.isV3()) {
         this.j = new SnmpContext(this.d.getEngineID());
         this.d.addErrorListener(this);
      }

   }

   public boolean isValidatingContextEngineID() {
      return this.k;
   }

   public void isValidatingContextEngineID(boolean var1) {
      this.k = var1;
   }

   void a(ExecManager var1) {
      this.b = var1;
   }

   ExecManager a() {
      return this.b;
   }

   public void shutdown() {
      this.d.removeRequestListener(this);
      this.d.removeErrorListener(this);
      Queue var1 = this.a;
      if (var1 != null) {
         var1.shutdown();
         this.a = null;
      }

   }

   int b() {
      Queue var1 = this.a;
      return var1 == null ? 0 : var1.getNumWorkers();
   }

   void a(int var1) {
      Queue var2 = this.a;
      if (var2 != null) {
         var2.shutdown();
         this.a = null;
      }

      if (var1 <= 0) {
         this.a = null;
         if (!SnmpMibNode.b) {
            return;
         }
      }

      this.a = new Queue(a("\u001d3\bkb+.\u0015t^*8\u0017JE+(\u00006"), var1, 5);
   }

   public void setRequestProcessor(SnmpRequestProcessor var1) {
      this.e = var1;
   }

   public SnmpRequestProcessor getRequestProcessor() {
      return this.e;
   }

   public void addPreProcessor(SnmpRequestProcessor var1) {
      this.f = monfox.toolkit.snmp.agent.i.add(this.f, var1);
   }

   public void removePreProcessor(SnmpRequestProcessor var1) {
      this.f = monfox.toolkit.snmp.agent.i.remove(this.f, var1);
   }

   public void addPostProcessor(SnmpRequestProcessor var1) {
      this.g = monfox.toolkit.snmp.agent.i.add(this.g, var1);
   }

   public void removePostProcessor(SnmpRequestProcessor var1) {
      this.g = monfox.toolkit.snmp.agent.i.remove(this.g, var1);
   }

   public void handleMessage(SnmpMessage var1, TransportEntity var2) {
      boolean var3 = SnmpMibNode.b;
      if (this.b != null) {
         this.b.schedule(new MessageContext(var1, var2));
         if (!var3) {
            return;
         }
      }

      if (this.a == null) {
         this.a(var1, var2);
         if (!var3) {
            return;
         }
      }

      this.a.put(new MessageContext(var1, var2));
   }

   private void a(SnmpMessage var1, TransportEntity var2) {
      if (this.c.a() != null && this.c.a().b(var1, var2)) {
         this.l.debug(a("#8\u0016hQ)8ElQ=}\u0003tB9<\u0017\u007fU*"));
      } else if (!this.h.supportsVersion(var1.getVersion())) {
         this.d.incSnmpInBadVersions();
      } else if (var1.getVersion() == 3 && var1.isDiscovery()) {
         this.b(var1, var2);
      } else {
         SnmpPendingIndication var3 = new SnmpPendingIndication(this.d, var2, var1, this.c);
         if (this.l.isDebugEnabled()) {
            this.l.debug(a("<8\u0006rU88\u0001;") + var3);
         }

         if (this.isValidatingContextEngineID()) {
            SnmpContext var4 = var3.getRequest().getContext();
            if (var4 != null && this.d.getEngineID() != null) {
               SnmpEngineID var5 = var4.getContextEngineID();
               if (var5 != null && !var5.equals(SnmpContext.ANY_ENGINE_ID.getValue()) && !var5.equals(this.d.getEngineID().getValue())) {
                  this.l.comms(a(";3\u000eu_93Ex_ )\u0000cD\u000b3\u0002r^+\u0014!!\u0010") + var5 + a("f.\u0000uT'3\u0002;U</\ni\u0019"));
                  var3.setError(16, 0);
                  var3.sendResponse();
                  return;
               }
            }
         }

         SnmpAccessPolicy var6 = this.h.getAccessPolicy(var3);
         if (this.l.isDebugEnabled()) {
            this.l.debug(a("/>\u0006~C=\r\nwY-$_;") + var6);
         }

         if (var6 == null) {
            if (this.c.isV3()) {
               var3.setError(16, 0);
               var3.sendResponse();
            }

         } else if (!var6.checkAccess(var3)) {
            this.l.comms(a("#8\u0016hQ)8EwU88\t;Q;)\rtB''\u0004oY!3E}Q'1\u0000\u007f\u0010(2\u0017!\u0010") + var6);
            if (this.c.isV3()) {
               var3.setError(16, 0);
               var3.sendResponse();
            }

         } else {
            this.handleMessage(var3, var6);
         }
      }
   }

   public void handleMessage(SnmpPendingIndication var1, SnmpAccessPolicy var2) {
      SnmpMib var3 = this.getMibForRequest(var1);
      if (this.f != null) {
         try {
            this.f.handleRequest(var1, var2, var3);
         } catch (Exception var7) {
            this.l.error(a("+%\u0006~@:4\nu\u0010'3EKb\u000b}6u]>\u000f\u0000jE+.\u0011KB!>\u0000hC!/"), var7);
         }
      }

      if (var1.done()) {
         if (this.g != null) {
            this.g.handleRequest(var1, var2, var3);
         }

         var1.sendResponse();
      } else {
         if (this.e != null) {
            this.e.handleRequest(var1, var2, var3);
         }

         if (var1.done()) {
            if (this.g != null) {
               this.g.handleRequest(var1, var2, var3);
            }

            var1.sendResponse();
         } else {
            Task var4 = Task.getInstance(var1, var3, var2);

            try {
               var4.run();
            } catch (Throwable var6) {
               this.l.error(a("<(\u000boY#8E~B<2\u0017;@<2\u0006~C=4\u000b|\u0010#8\u0016hQ)8"), var6);
            }

            if (var1.done()) {
               if (this.g != null) {
                  this.g.handleRequest(var1, var2, var3);
               }

               var1.sendResponse();
            } else {
               this.l.error(a("'3\u0006t]>8\u0011~\u0010<8\u0014nU=)EkB!>\u0000hC'3\u0002;") + var1);
            }
         }
      }
   }

   public SnmpMib getMibForRequest(SnmpPendingIndication var1) {
      SnmpMib var2 = this.c.getMib();
      SnmpContext var3 = null;
      if (var1.getRequest().getVersion() == 3) {
         var3 = var1.getRequest().getContext();
      } else {
         var3 = var1.getContext();
      }

      if (var3 != null && this.d.getEngineID() != null && (var3.getContextEngineID() == null || var3.getContextEngineID().equals(SnmpContext.ANY_ENGINE_ID.getValue()) || var3.getContextEngineID().equals(this.d.getEngineID().getValue()))) {
         String var4 = var3.getContextName();
         if (var4 != null && var4.length() > 0) {
            var2 = this.c.getContextMib(var4);
            if (var2 == null) {
               var2 = this.c.getMib();
            }
         }
      }

      return var2;
   }

   void a(SnmpPendingIndication var1) {
      boolean var16 = SnmpMibNode.b;
      if (var1.getSource() != null) {
         if (this.l.isDebugEnabled()) {
            this.l.debug(a("=8\u000b\u007fY :E") + var1);
         }

         SnmpMessage var2;
         SnmpVarBindList var3;
         label102: {
            var2 = var1.getRequest();
            if (var1.getErrorStatus() != 0 || var1.getRequestType() == 163) {
               var3 = var1.getRequestVBL().cloneWithValue();
               if (!var16) {
                  break label102;
               }
            }

            var3 = var1.getResponseVBL();
         }

         SnmpRequestPDU var4 = new SnmpRequestPDU();
         var4.setRequestId(var2.getData().getRequestId());
         var4.setType(162);
         var4.setVarBindList(var3);
         var4.setVersion(var2.getVersion());
         var4.setErrorStatus(var1.getErrorStatus());
         var4.setErrorIndex(var1.getErrorIndex());
         SnmpMessage var5 = new SnmpMessage();
         var5.setVersion(var2.getVersion());
         var5.setMsgID(var2.getMsgID());
         var5.setData(var4);
         String var6 = null;
         int var8 = var2.getVersion();
         SnmpMessageProfile var7;
         String var9;
         if (var8 == 0) {
            var9 = new String(var2.getData().getCommunity());
            var6 = var9;
            var7 = new SnmpMessageProfile(0, 1, 0, var9);
         } else if (var2.getVersion() == 1) {
            var9 = new String(var2.getData().getCommunity());
            var6 = var9;
            var7 = new SnmpMessageProfile(1, 2, 0, var9);
         } else {
            if (var2.getVersion() != 3) {
               return;
            }

            var5.setSnmpEngineID(this.d.getEngineID());
            var5.setContext(var2.getContext());
            V3SnmpMessageParameters var21 = (V3SnmpMessageParameters)var2.getMessageParameters();
            byte var10 = (byte)(var21.getFlags() & 3);
            byte[] var11 = var2.getSecurityParameters().getSecurityName();
            var6 = new String(var11);
            var7 = new SnmpMessageProfile(3, 3, var10, new String(var11));
         }

         var5.setMessageProfile(var7);
         SnmpAuditTrailLogger var22 = this.c.getAuditTrailLogger();
         if (var22 != null) {
            try {
               label82: {
                  if (var1.getErrorStatus() == 0) {
                     if (var3 == null) {
                        break label82;
                     }

                     Enumeration var23 = var3.getVarBinds();

                     while(var23.hasMoreElements()) {
                        SnmpVarBind var24 = (SnmpVarBind)var23.nextElement();
                        SnmpOid var12 = var24.getOid();
                        String var13 = null;
                        SnmpOidInfo var14 = var12.getOidInfo();
                        if (var16) {
                           break label82;
                        }

                        if (var14 != null) {
                           var13 = var14.getName();
                        }

                        String var15 = var24.getValueString();
                        var22.logObjectAccess(var6, var8, var1.getRequestType(), var13, var12, var15);
                        if (var16) {
                           break;
                        }
                     }

                     if (!var16) {
                        break label82;
                     }
                  }

                  var22.logErrorResponse(var6, var8, var1.getRequestType(), var3, var1.getErrorStatus(), var1.getErrorIndex());
               }
            } catch (Throwable var20) {
               this.l.error(a("+/\u0017tBn4\u000b;Q;9\fod<<\fw\u0010>/\nxU=.\fuW"), var20);
            }
         }

         try {
            var5.setOidMap(var1.getRequest().getOidMap());
         } catch (Exception var17) {
            this.l.error(a("+%\u0006~@:4\nu\u0010'3EhU:)\fuWn\u0012\f\u007f}/-"), var17);
         }

         try {
            this.d.send(var5, var1.getSource());
         } catch (SnmpException var19) {
            if (this.l.isDebugEnabled()) {
               this.l.debug(a("+/\u0017tBn.\u0000uT'3\u0002;B+.\u0015t^=8"), var19);
            }
         }

         try {
            var1.performPostProcessing();
         } catch (Exception var18) {
            if (this.l.isDebugEnabled()) {
               this.l.debug(a("+/\u0017tBn4\u000b;@!.\u0011;@<2\u0006~C=4\u000b|"), var18);
            }
         }

      }
   }

   public void handleError(SnmpBuffer var1, TransportEntity var2, int var3, int var4, SnmpCoderException var5) {
      if (var4 != 0) {
         SnmpVarBindList var6 = this.i.getReportVarBindList(var3);
         int var7 = 0;
         String var8 = null;
         if (var5 instanceof SnmpSecurityCoderException) {
            SnmpSecurityCoderException var9 = (SnmpSecurityCoderException)var5;
            if (var9.getUserName() != null) {
               var8 = new String(var9.getUserName());
            }

            var7 = var9.getSecurityLevel();
         }

         SnmpAuditTrailLogger var12 = this.c.getAuditTrailLogger();
         if (var12 != null) {
            try {
               var12.logErrorReport(var8, var6);
            } catch (Throwable var11) {
               this.l.error(a("+/\u0017tBn4\u000b;Q;9\fod<<\fw\u0010<8\u0015tB:}\u0015i_-8\u0016hY :"), var11);
            }
         }

         this.a(var2, var4, var6, var7, var8);
      }
   }

   private void b(SnmpMessage var1, TransportEntity var2) {
      this.a(var2, var1.getMsgID(), this.i.getReportVarBindList(3), 0, (String)null);
   }

   private void a(TransportEntity var1, int var2, SnmpVarBindList var3, int var4, String var5) {
      if (var3 != null) {
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         var6.setRequestId(var2);
         var6.setType(168);
         var6.setVarBindList(var3);
         SnmpMessage var7 = new SnmpMessage();
         var7.setVersion(3);
         var7.setMsgID(var2);
         var7.setSnmpEngineID(this.d.getEngineID());
         var7.setData(var6);
         var7.setContext(this.j);
         SnmpMessageProfile var8 = new SnmpMessageProfile(3, 3, var4, var5);
         var7.setMessageProfile(var8);

         try {
            this.d.send(var7, var1);
         } catch (SnmpException var10) {
            if (this.l.isDebugEnabled()) {
               this.l.debug(a("+/\u0017tBn.\u0000uT'3\u0002;B+-\niD"), var10);
            }
         }

      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 78;
               break;
            case 1:
               var10003 = 93;
               break;
            case 2:
               var10003 = 101;
               break;
            case 3:
               var10003 = 27;
               break;
            default:
               var10003 = 48;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class MessageContext extends WorkItem implements Runnable {
      private SnmpMessage a;
      private TransportEntity b;

      MessageContext(SnmpMessage var2, TransportEntity var3) {
         this.a = var2;
         this.b = var3;
      }

      public void run() {
         this.perform();
      }

      public void perform() {
         try {
            SnmpResponder.this.a(this.a, this.b);
         } catch (Exception var2) {
            SnmpResponder.this.l.error(a("\u000fbq\u001aE%b\"\u0019V-dg\u001aW+ieIA0um\u001b"), var2);
         }

      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 66;
                  break;
               case 1:
                  var10003 = 7;
                  break;
               case 2:
                  var10003 = 2;
                  break;
               case 3:
                  var10003 = 105;
                  break;
               default:
                  var10003 = 36;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
