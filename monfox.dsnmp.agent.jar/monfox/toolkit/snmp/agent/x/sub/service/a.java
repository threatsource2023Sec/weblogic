package monfox.toolkit.snmp.agent.x.sub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.SnmpResponder;
import monfox.toolkit.snmp.agent.impl.SetTask;
import monfox.toolkit.snmp.agent.impl.Task;
import monfox.toolkit.snmp.agent.x.connection.AgentXConnection;
import monfox.toolkit.snmp.agent.x.pdu.AgentXPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetBulkPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetNextPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetPDU;
import monfox.toolkit.snmp.agent.x.pdu.ResponsePDU;
import monfox.toolkit.snmp.agent.x.pdu.SearchRange;
import monfox.toolkit.snmp.agent.x.pdu.TestSetPDU;
import monfox.toolkit.snmp.engine.SnmpBulkPDU;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.TransportEntity;

class a implements AgentXConnection.RequestListener {
   private SnmpResponder a;
   private SnmpAgent b;
   private SnmpEngine c;
   private SnmpAccessPolicy d;
   private static Logger e;
   private Map f = new HashMap();

   public a(SnmpAgent var1, SnmpAccessPolicy var2) {
      e = Logger.getInstance(a("9q\\US"), a("<eWVWPz"), a("<Ewvw%pwks\u0012Lv}q"));
      this.b = var1;
      this.c = var1.getEngine();
      this.a = var1.getResponder();
      this.d = var2;
   }

   public void handleGet(AgentXConnection.PendingIndication var1) {
      GetPDU var2 = (GetPDU)var1.getRequest();
      SnmpMessage var3 = new SnmpMessage();
      if (var2.getContext() != null) {
         var3.setContext(new SnmpContext(new SnmpEngineID(), var2.getContext()));
      }

      var3.setVersion(1);
      SnmpRequestPDU var4 = new SnmpRequestPDU(160, 1, (byte[])null, -1, 0, 0);
      SnmpVarBindList var5 = this.a(var2.getSearchRangeList(), false);
      var4.setVarBindList(var5);
      var3.setData(var4);
      monfox.toolkit.snmp.agent.x.sub.service.a.a var6 = new monfox.toolkit.snmp.agent.x.sub.service.a.a(this, var3, var1);
      this.handleMessage(var6, this.d);
   }

   public void handleGetNext(AgentXConnection.PendingIndication var1) {
      GetNextPDU var2 = (GetNextPDU)var1.getRequest();
      SnmpMessage var3 = new SnmpMessage();
      if (var2.getContext() != null) {
         var3.setContext(new SnmpContext(new SnmpEngineID(), var2.getContext()));
      }

      var3.setVersion(1);
      SnmpRequestPDU var4 = new SnmpRequestPDU(161, 1, (byte[])null, -1, 0, 0);
      SnmpVarBindList var5 = this.a(var2.getSearchRangeList(), true);
      var4.setVarBindList(var5);
      var3.setData(var4);
      monfox.toolkit.snmp.agent.x.sub.service.a.a var6 = new monfox.toolkit.snmp.agent.x.sub.service.a.a(this, var3, var1);
      this.handleMessage(var6, this.d);
   }

   public void handleGetBulk(AgentXConnection.PendingIndication var1) {
      GetBulkPDU var2 = (GetBulkPDU)var1.getRequest();
      SnmpMessage var3 = new SnmpMessage();
      if (var2.getContext() != null) {
         var3.setContext(new SnmpContext(new SnmpEngineID(), var2.getContext()));
      }

      var3.setVersion(1);
      SnmpBulkPDU var4 = new SnmpBulkPDU();
      var4.setType(165);
      var4.setVersion(1);
      SnmpVarBindList var5 = this.a(var2.getSearchRangeList(), true);
      var4.setNonRepeaters(var2.getNonRepeaters());
      var4.setMaxRepetitions(var2.getMaxRepetitions());
      var4.setVarBindList(var5);
      var3.setData(var4);
      monfox.toolkit.snmp.agent.x.sub.service.a.a var6 = new monfox.toolkit.snmp.agent.x.sub.service.a.a(this, var3, var1);
      this.handleMessage(var6, this.d);
   }

   public void handleTestSet(AgentXConnection.PendingIndication var1) {
      TestSetPDU var2 = (TestSetPDU)var1.getRequest();
      SnmpMessage var3 = new SnmpMessage();
      if (var2.getContext() != null) {
         var3.setContext(new SnmpContext(new SnmpEngineID(), var2.getContext()));
      }

      var3.setVersion(1);
      SnmpRequestPDU var4 = new SnmpRequestPDU(163, 1, (byte[])null, -1, 0, 0);
      SnmpVarBindList var5 = var2.getVarBindList();
      var4.setVarBindList(var5);
      var3.setData(var4);
      monfox.toolkit.snmp.agent.x.sub.service.a.a var6 = new monfox.toolkit.snmp.agent.x.sub.service.a.a(this, var3, var1);
      this.handleMessage(var6, this.d);
   }

   public void handleOtherSet(AgentXConnection.PendingIndication var1) {
      AgentXPDU var2 = var1.getRequest();
      SnmpMessage var3 = new SnmpMessage();
      if (var2.getContext() != null) {
         var3.setContext(new SnmpContext(new SnmpEngineID(), var2.getContext()));
      }

      var3.setVersion(1);
      SnmpRequestPDU var4 = new SnmpRequestPDU(163, 1, (byte[])null, -1, 0, 0);
      var4.setVarBindList(new SnmpVarBindList());
      var3.setData(var4);
      monfox.toolkit.snmp.agent.x.sub.service.a.a var5 = new monfox.toolkit.snmp.agent.x.sub.service.a.a(this, var3, var1);
      this.handleMessage(var5, this.d);
   }

   private SnmpVarBindList a(List var1, boolean var2) {
      boolean var9 = SubAgentXApi.r;
      SnmpVarBindList var3 = new SnmpVarBindList();
      int var4 = 0;

      while(var4 < var1.size()) {
         label39: {
            SearchRange var5 = (SearchRange)var1.get(var4);
            if (!var2) {
               var3.add(var5.getStart());
               if (!var9) {
                  break label39;
               }
            }

            if (var5.isInclude()) {
               SnmpOid var6;
               label24: {
                  var6 = (SnmpOid)var5.getStart().clone();
                  long var7 = var6.getLast();
                  if (var7 == 0L) {
                     var6 = var6.getParent(1);
                     if (!var9) {
                        break label24;
                     }
                  }

                  --var7;
                  var6 = var6.getParent();
                  var6.append(var7 - 1L);
                  var6.append(Long.MAX_VALUE);
               }

               var3.add(var6);
               if (!var9) {
                  break label39;
               }
            }

            var3.add(var5.getStart());
         }

         ++var4;
         if (var9) {
            break;
         }
      }

      return var3;
   }

   public void handleRequest(AgentXConnection.PendingIndication var1, AgentXPDU var2) {
      boolean var3 = SubAgentXApi.r;
      switch (var2.getType()) {
         case 5:
            this.handleGet(var1);
            if (!var3) {
               break;
            }
         case 6:
            this.handleGetNext(var1);
            if (!var3) {
               break;
            }
         case 7:
            this.handleGetBulk(var1);
            if (!var3) {
               break;
            }
         case 8:
            this.handleTestSet(var1);
            if (!var3) {
               break;
            }
         case 9:
            this.handleOtherSet(var1);
            if (!var3) {
               break;
            }
         case 10:
            this.handleOtherSet(var1);
            if (!var3) {
               break;
            }
         case 11:
            this.handleOtherSet(var1);
            if (!var3) {
               break;
            }
         default:
            System.out.println(a("w\u000228)W\b2MM.wBHL/vW\\#/gCMF.v(8\t") + var1.getRequest());
      }

   }

   public void handleMessage(SnmpPendingIndication var1, SnmpAccessPolicy var2) {
      boolean var8 = SubAgentXApi.r;
      SnmpMib var3 = this.a.getMibForRequest(var1);
      if (this.a.getRequestProcessor() != null) {
         this.a.getRequestProcessor().handleRequest(var1, var2, var3);
      }

      monfox.toolkit.snmp.agent.x.sub.service.a.a var4 = (monfox.toolkit.snmp.agent.x.sub.service.a.a)var1;
      AgentXConnection.PendingIndication var5 = var4.getAgentXPendingIndication();
      SetTask var6;
      if (var5.getRequest().getType() == 8) {
         label86: {
            var6 = new SetTask(var4, var3, var2);
            this.f.put(new Integer(var5.getTransactionId()), var6);
            var6.process(1);
            if (var1.getErrorStatus() == 0) {
               this.a(var5, 0, 0, (SnmpVarBindList)null);
               if (!var8) {
                  break label86;
               }
            }

            this.a(var5, var1.getErrorStatus(), var1.getErrorIndex(), (SnmpVarBindList)null);
         }

         if (!var8) {
            return;
         }
      }

      if (var5.getRequest().getType() == 9) {
         var6 = (SetTask)this.f.get(new Integer(var5.getTransactionId()));
         if (var6 != null) {
            label95: {
               var6.process(2);
               if (var1.getErrorStatus() == 14) {
                  this.a(var5, 14, var1.getErrorIndex(), (SnmpVarBindList)null);
                  if (!var8) {
                     break label95;
                  }
               }

               this.a(var5, 0, 0, (SnmpVarBindList)null);
            }
         }

         if (!var8) {
            return;
         }
      }

      if (var5.getRequest().getType() == 10) {
         label66: {
            e.debug(a("-P}{f\u000eQ{vdG\u0002GVG2qWL\\-fG"));
            var6 = (SetTask)this.f.get(new Integer(var5.getTransactionId()));
            if (var6 != null) {
               var6.process(3);
               var6.process(5);
               if (var1.getErrorStatus() == 15) {
                  this.a(var5, 15, var1.getErrorIndex(), (SnmpVarBindList)null);
                  if (!var8) {
                     break label66;
                  }
               }

               this.a(var5, 0, 0, (SnmpVarBindList)null);
               if (!var8) {
                  break label66;
               }
            }

            e.error(a("\u0013M2lb\u000eI2jf\u001aKalf\u000fGv\"#") + var5.getTransactionId());
         }

         if (!var8) {
            return;
         }
      }

      if (var5.getRequest().getType() == 11) {
         label54: {
            e.debug(a("-P}{f\u000eQ{vdG\u0002QTF<lGHP8vMHG("));
            var6 = (SetTask)this.f.get(new Integer(var5.getTransactionId()));
            if (var6 != null) {
               var6.process(4);
               var6.process(5);
               this.f.remove(new Integer(var5.getTransactionId()));
               this.a(var5, 0, 0, (SnmpVarBindList)null);
               if (!var8) {
                  break label54;
               }
            }

            e.error(a("\u0013M2lb\u000eI2jf\u001aKalf\u000fGv\"#") + var5.getTransactionId());
         }

         if (!var8) {
            return;
         }
      }

      if (var1.done()) {
         this.a(var5, var1.getErrorStatus(), var1.getErrorIndex(), var1.getResponseVBL());
      } else {
         Task var10 = Task.getInstance(var1, var3, var2);

         try {
            var10.run();
         } catch (Throwable var9) {
            e.error(a("\u000fW|lj\u0010G2}q\u000fM`8s\u000fMq}p\u000eK|\u007f#\u0010Gakb\u001aG"), var9);
         }

         if (var1.done()) {
            this.a(var5, var1.getErrorStatus(), var1.getErrorIndex(), var1.getResponseVBL());
         } else {
            e.error(a("\u0014Lqwn\rGf}#\u000fGcmf\u000eV2hq\u0012Awkp\u0014Lu8") + var1);
         }
      }
   }

   private void a(AgentXConnection.PendingIndication var1, int var2, int var3, SnmpVarBindList var4) {
      boolean var10 = SubAgentXApi.r;
      e.debug(a(".g\\\\J3e2JF.r]VP8\u0002TWQ]pWIV8qF\"#") + var1.getRequest());
      if (var1.getRequest() instanceof GetNextPDU) {
         GetNextPDU var5 = (GetNextPDU)var1.getRequest();
         List var6 = var5.getSearchRangeList();
         int var7 = 0;

         while(var7 < var6.size()) {
            SearchRange var8 = (SearchRange)var6.get(var7);
            if (var7 < var4.size()) {
               label51: {
                  SnmpVarBind var9 = var4.get(var7);
                  e.debug(a("\u001eJw{h\u0014Lu8q\u0018Qbwm\u000eG2nb\u0011Ww\"#") + var9);
                  e.debug(a("]\u0002?8p\u0018C`{k]Psvd\u0018\u00182") + var8.getStart() + a("]\f<8") + var8.getEnd());
                  if (var8.getEnd() != null) {
                     if (var8.getEnd().compareTo(var9.getOid()) >= 0 || var8.getEnd().contains(var9.getOid())) {
                        break label51;
                     }

                     e.debug(a("\rCal#\u0018Lv8l\u001b\u0002a}b\u000fAz8q\u001cLu}9G\u0002") + var9);
                     var9.setToEndOfMibView();
                     if (!var10) {
                        break label51;
                     }
                  }

                  if (!var8.getStart().contains(var9.getOid())) {
                     e.debug(a("\u0013Mf8`\u0012Lfyj\u0013Gv8a\u0004\u0002alb\u000fV{vd]m[\\9G\u0002") + var9);
                     var9.setToEndOfMibView();
                  }
               }
            }

            ++var7;
            if (var10) {
               break;
            }
         }
      }

      ResponsePDU var12 = new ResponsePDU();
      var12.setError(var2);
      var12.setIndex(var3);
      var12.setSysUpTime(0L);
      if (var4 != null) {
         var12.setVarBindList(var4);
      }

      try {
         var1.sendResponse(var12);
      } catch (Exception var11) {
         e.error(a("\u0018P`wq]K|8p\u0018Lvqm\u001a\u0002amaPCu}m\t\u0002`}p\rM|kfG\u0002") + var12);
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
               var10003 = 125;
               break;
            case 1:
               var10003 = 34;
               break;
            case 2:
               var10003 = 18;
               break;
            case 3:
               var10003 = 24;
               break;
            default:
               var10003 = 3;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   static class a extends SnmpPendingIndication {
      private AgentXConnection.PendingIndication a;

      public a(monfox.toolkit.snmp.agent.x.sub.service.a var1, SnmpMessage var2, AgentXConnection.PendingIndication var3) {
         super(var1.c, (TransportEntity)null, var2, var1.b);
         this.a = var3;
      }

      public AgentXConnection.PendingIndication getAgentXPendingIndication() {
         return this.a;
      }

      protected void sendResponse() {
         ResponsePDU var1;
         label20: {
            var1 = new ResponsePDU();
            if (this.getErrorStatus() == 0) {
               var1.setVarBindList(this.getResponseVBL());
               if (!SubAgentXApi.r) {
                  break label20;
               }
            }

            var1.setError(this.getErrorStatus());
            var1.setIndex(this.getErrorIndex());
            var1.setVarBindList(this.getRequestVBL());
         }

         var1.setSysUpTime(0L);

         try {
            this.a.sendResponse(var1);
         } catch (Exception var3) {
            monfox.toolkit.snmp.agent.x.sub.service.a.e.error(a("-\u000eF1u:O[:t*Oi8\u007f \u001bp\u007fh+\u001cX0t=\n\b\u000f^\u001b"), var3);
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
                  var10003 = 111;
                  break;
               case 2:
                  var10003 = 40;
                  break;
               case 3:
                  var10003 = 95;
                  break;
               default:
                  var10003 = 26;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
