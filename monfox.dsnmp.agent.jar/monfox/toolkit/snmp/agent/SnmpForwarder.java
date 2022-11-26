package monfox.toolkit.snmp.agent;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.proxy.SnmpProxyForwarder;
import monfox.toolkit.snmp.agent.proxy.SnmpProxyTable;
import monfox.toolkit.snmp.agent.target.SnmpTargetAddrTable;
import monfox.toolkit.snmp.agent.vacm.SnmpCommunityTable;
import monfox.toolkit.snmp.agent.vacm.Vacm;
import monfox.toolkit.snmp.engine.SnmpBulkPDU;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;

class SnmpForwarder {
   private SnmpAgent a;
   private SnmpSession b;
   private Logger c = Logger.getInstance(a("3<,rP'7;w"));

   SnmpForwarder(SnmpAgent var1) {
      this.a = var1;
   }

   private boolean a(SnmpMessage var1, TransportEntity var2) {
      boolean var16 = SnmpMibNode.b;
      if (var1.isDiscovery()) {
         this.c.debug(a("\u0011\u001a\rF~\u0003\u0016\f\\=U\u001d\u0011Q1\u0013\u001c\fRp\u0007\u0017\u0017Kv"));
         return false;
      } else if (this.a.getProxyForwarder().getProxyTable().getNumOfRows() == 0) {
         this.c.debug(a("\u001b\u001c^Uc\u001a\u000b\u0007\u0005w\u001a\u0001\tDc\u0011\u001a\u0010B1\u0010\u001d\nWx\u0010\u0000"));
         return false;
      } else {
         SnmpContext var3 = null;
         int var4 = var1.getVersion();
         String var5 = new String(var1.getSecurityParameters().getSecurityName());
         int var6 = var1.getSecurityParameters().getSecurityModel();
         int var7 = 0;
         String var12;
         if (var4 == 3) {
            var3 = var1.getContext();
            V3SnmpMessageParameters var8 = (V3SnmpMessageParameters)var1.getMessageParameters();
            var7 = var8.getFlags() & 3;
         } else {
            SnmpAccessControlModel var19 = this.a.getAccessControlModel();
            if (var19 instanceof Vacm) {
               Vacm var9 = (Vacm)var19;
               SnmpCommunityTable var10 = var9.getCommunityTable();
               String var11 = new String(var1.getData().getCommunity());
               var12 = var10.getContextName(var11);
               SnmpEngineID var13 = var10.getContextEngineID(var11);
               if (var13 != null || var12 != null) {
                  if (var13 == null) {
                     var13 = this.a.getEngine().getEngineID();
                  }

                  var3 = new SnmpContext(var13, var12);
               }
            }
         }

         if (var3 == null) {
            this.c.debug(a("\u001b\u001c^F~\u001b\u0007\u001b]eU\u001a\u0010\u0005|\u0010\u0000\rDv\u0010"));
            return false;
         } else {
            byte var20 = -1;
            switch (var1.getData().getType()) {
               case 160:
               case 161:
               case 165:
                  var20 = 1;
                  if (!var16) {
                     break;
                  }
               case 163:
                  var20 = 2;
               case 162:
               case 164:
            }

            if (var20 == -1) {
               this.c.debug(a("\u001c\u001d\bD}\u001c\u0017^Wt\u0004\u0006\u001bVeU\u0007\u0007UtOS") + var1.getData().getType());
               return false;
            } else {
               SnmpProxyForwarder var21 = this.a.getProxyForwarder();
               SnmpProxyTable var22 = var21.getProxyTable();
               SnmpProxyTable.Row var23 = var22.getProxyRow(var3, var20, var4, var5, var6, var7);
               if (var23 == null) {
                  this.c.debug(a("\u001b\u001c^Uc\u001a\u000b\u0007\u0005t\u001b\u0007\fLt\u0006S\u001fSp\u001c\u001f\u001fG}\u0010"));
                  return false;
               } else {
                  this.c.debug(a("\u0013\u001c\u000bKuU\u0003\fJi\fS\fJfOS") + var23);
                  var12 = var23.getSingleTargetOut();
                  if (var12 == null) {
                     this.c.debug(a("\u001b\u001c^Jd\u0001\u0003\u000bQ1\u0001\u0012\fBt\u0001"));
                     return false;
                  } else {
                     try {
                        SnmpTargetAddrTable.Row var24 = this.a.getTarget().getAddrTable().getRowByName(var12);
                        if (var24 == null) {
                           this.c.error(a("\u001b\u001c^Jd\u0001\u0003\u000bQ1\u0001\u0012\fBt\u0001S\fJfU\u0015\u0011W1\u0001\u0012\fBt\u0001S\u0010D|\u0010I") + var12);
                           return false;
                        } else {
                           SnmpPeer var14 = var24.getPeer();
                           if (var14 == null) {
                              this.c.error(a("\u0016\u0012\u0010K~\u0001S\u001dWt\u0014\u0007\u001b\u0005a\u0010\u0016\f\u0005w\u001a\u0001^Qp\u0007\u0014\u001bQ+U") + var12);
                           }

                           SnmpPeer var15 = var14;
                           if (var14.isPassThrough()) {
                              var15 = var14.createFromPassThrough(var1);
                           }

                           label101: {
                              if (var3.getContextEngineID() == null || var3.getContextEngineID().equals(SnmpContext.ANY_ENGINE_ID.getValue()) || var3.getContextEngineID().equals(this.a.getEngine().getEngineID().getValue())) {
                                 this.c.debug(a("\u0018\u0012\u000eUx\u001b\u0014^I~\u0016\u0012\u0012\u0005r\u001a\u001d\n@i\u0001S\nJ1\u0007\u0016\u0013Je\u0010S\u001fBt\u001b\u0007R\u0005\u007f\u001a\u0007^Vt\u0001\u0007\u0017KvU\u0010\u0011Ke\u0010\u000b\n\bt\u001b\u0014\u0017KtX\u001a\u001a\nr\u001a\u001d\n@i\u0001^\u0010D|\u0010"));
                                 if (!var16) {
                                    break label101;
                                 }
                              }

                              var15.setDefaultContext(var3);
                           }

                           this.a(var1, var2, var15);
                           return true;
                        }
                     } catch (SnmpException var17) {
                        this.c.debug(a("\u0016\u0012\u0010K~\u0001S\u0019@eU\u0012\u001aAc!\u0012\u001cIt'\u001c\t\u001f1") + var12, var17);
                        return false;
                     } catch (Exception var18) {
                        this.c.debug(a("0\u000b\u001d@a\u0001\u001a\u0011K1\u001c\u001d^C~\u0007\u0004\u001fWu\u0010\u0001^Uc\u001a\u0010\u001bVb\u001c\u001d\u0019\u001f1") + var12, var18);
                        return false;
                     }
                  }
               }
            }
         }
      }
   }

   boolean b(SnmpMessage var1, TransportEntity var2) {
      boolean var12 = SnmpMibNode.b;
      if (this.a(var1, var2)) {
         return true;
      } else {
         if (this.a.getProxyForwarder().isCommunityBasedForwardingEnabled()) {
            try {
               SnmpPDU var3 = var1.getData();
               if (var3 != null && var3.getCommunity() != null) {
                  int var4 = -1;
                  byte[] var5 = var3.getCommunity();
                  int var6 = 0;

                  label89: {
                     int var10000;
                     int var10001;
                     while(true) {
                        if (var6 < var5.length) {
                           label102: {
                              var10000 = var5[var6];
                              var10001 = 64;
                              if (var12) {
                                 break;
                              }

                              if (var10000 == 64) {
                                 var4 = var6;
                                 if (!var12) {
                                    break label102;
                                 }
                              }

                              ++var6;
                              if (!var12) {
                                 continue;
                              }
                           }
                        }

                        if (var4 < 0) {
                           break label89;
                        }

                        var10000 = var4;
                        var10001 = var5.length;
                        break;
                     }

                     if (var10000 < var10001) {
                        String var14 = new String(var5, 0, var4);
                        String var7 = "@" + new String(var5, var4 + 1, var5.length - var4 - 1);
                        boolean var8 = false;
                        SnmpPeer var9 = this.a.getTarget().getPeer(var14 + var7);
                        if (var9 == null) {
                           var8 = true;
                           var9 = this.a.getTarget().getPeer(var7);
                        }

                        if (var9 == null) {
                           this.c.comms(a("\u001b\u001c^qp\u0007\u0014\u001bQP\u0011\u0017\fqp\u0017\u001f\u001b\u0005t\u001b\u0007\f\\1\u0013\u001c\f\u001f1") + var7);
                           return false;
                        }

                        if (var9.isPassThrough()) {
                           var8 = true;
                        }

                        SnmpPeer var10 = var9;
                        int var11 = var9.getParameters().getDefaultProfile().getSnmpVersion();
                        this.c.comms(a("\u0013\u001c\fRp\u0007\u0017\u0017KvU 0hAU\u0001\u001bTd\u0010\u0000\n\u001f1\u0016\u001c\u0013H,R") + var14 + a("R_^Qp\u0007\u0014\u001bQ,R") + var7 + a("R_^St\u0007\u0000\u0017J\u007fH") + var11 + a("YS\u000bVt6\u001c\u0013Hd\u001b\u001a\n\\,") + var8);
                        this.c.comms(a("\u0005\u0016\u001bW+U") + var9);
                        if (var8 && var11 == 3) {
                           this.c.comms(a("\u0016\u0012\u0010K~\u0001S\u0013DaU\u0010\u0011H|\u0000\u001d\u0017QhU\u0015\u0011Wf\u0014\u0001\u001aL\u007f\u0012S\nJ1#@"));
                           return false;
                        }

                        if (var8 || var11 != 3 && (var9.getReadName() == null || var9.getReadName().equals(""))) {
                           label64: {
                              this.c.debug(a("\u0016\u0001\u001bDe\u001c\u001d\u0019\u0005\u007f\u0010\u0004^Ut\u0010\u0001^Rx\u0001\u001b^F~\u0018\u001e\u000bKx\u0001\nD\u0005") + var14);
                              var10 = new SnmpPeer(var9.getAddress(), var9.getPort(), var9.getTransportDomain());
                              if (var9.isPassThrough()) {
                                 var10.setParameters(var1.getVersion(), var14);
                                 if (!var12) {
                                    break label64;
                                 }
                              }

                              var10.setParameters(var9.getParameters().getDefaultProfile().getSnmpVersion(), var14);
                           }

                           var10.setTimeout(var9.getTimeout());
                           var10.setMaxRetries(var9.getMaxRetries());
                        }

                        this.a(var1, var2, var10);
                        return true;
                     }
                  }

                  this.c.debug(a("\u001b\u001c\n\u0005w\u001a\u0001\tDc\u0011\u001a\u0010B"));
                  return false;
               }

               this.c.debug(a("\u001b\u001c\n\u0005w\u001a\u0001\tDc\u0011\u001a\u0010B=U\u001d\u0011\u0005A1&QF~\u0018\u001e\u000bKx\u0001\n^L\u007f\u0016\u001f\u000bAt\u0011"));
            } catch (Exception var13) {
               this.c.error(a("\u0010\u000b\u001d@a\u0001\u001a\u0011K1\u001c\u001d^C~\u0007\u0004\u001fWu\u001c\u001d\u0019\u0005a\u0007\u001c\u001d@b\u0006"), var13);
            }
         }

         return false;
      }
   }

   private void a(SnmpMessage var1, TransportEntity var2, SnmpPeer var3) {
      boolean var8 = SnmpMibNode.b;
      SnmpPendingIndication var4 = new SnmpPendingIndication(this.a.getEngine(), var2, var1, this.a);
      synchronized(this) {
         if (this.b == null) {
            try {
               this.b = this.a.getSession();
            } catch (Exception var9) {
               this.c.error(a("\u0016\u0012\u0010K~\u0001S\u001dWt\u0014\u0007\u001b\u0005B\u001b\u001e\u000evt\u0006\u0000\u0017J\u007f"), var9);
               var4.setError(5, 0);
               this.a(var4);
               return;
            }
         }
      }

      SnmpVarBindList var5 = var1.getData().getVarBindList();
      if (var5 == null) {
         var4.setError(5, 0);
         this.a(var4);
      } else {
         label79: {
            if (var1.getData().getType() == 160) {
               try {
                  this.b.startGet(var3, new ForwardedResponseListener(var4), var5);
                  return;
               } catch (Exception var13) {
                  this.c.error(a("\u0010\u0001\fJcU\u001a\u0010\u0005a\u0010\u0001\u0018Jc\u0018\u001a\u0010B1\u0012\u0016\n\u0005c\u0010\u0002\u000b@b\u0001"), var13);
                  var4.setError(5, 0);
                  if (!var8) {
                     break label79;
                  }
               }
            }

            if (var1.getData().getType() == 163) {
               try {
                  this.b.startSet(var3, new ForwardedResponseListener(var4), var5);
                  return;
               } catch (Exception var12) {
                  this.c.error(a("\u0010\u0001\fJcU\u001a\u0010\u0005a\u0010\u0001\u0018Jc\u0018\u001a\u0010B1\u0006\u0016\n\u0005c\u0010\u0002\u000b@b\u0001"), var12);
                  var4.setError(5, 0);
                  if (!var8) {
                     break label79;
                  }
               }
            }

            if (var1.getData().getType() == 161) {
               try {
                  this.b.startGetNext(var3, new ForwardedResponseListener(var4), var5);
                  return;
               } catch (Exception var11) {
                  this.c.error(a("\u0010\u0001\fJcU\u001a\u0010\u0005a\u0010\u0001\u0018Jc\u0018\u001a\u0010B1\u0012\u0016\n\b\u007f\u0010\u000b\n\u0005c\u0010\u0002\u000b@b\u0001"), var11);
                  var4.setError(5, 0);
                  if (!var8) {
                     break label79;
                  }
               }
            }

            if (var1.getData().getType() == 165) {
               try {
                  SnmpBulkPDU var6 = (SnmpBulkPDU)var1.getData();
                  this.b.startGetBulk(var3, new ForwardedResponseListener(var4), var5, var6.getNonRepeaters(), var6.getMaxRepetitions());
                  return;
               } catch (Exception var10) {
                  this.c.error(a("\u0010\u0001\fJcU\u001a\u0010\u0005a\u0010\u0001\u0018Jc\u0018\u001a\u0010B1\u0012\u0016\n\bs\u0000\u001f\u0015\u0005c\u0010\u0002\u000b@b\u0001"), var10);
                  var4.setError(5, 0);
                  if (!var8) {
                     break label79;
                  }
               }
            }

            var4.setError(5, 0);
         }

         this.a(var4);
      }
   }

   private void a(SnmpPendingIndication var1) {
      var1.sendResponse();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 117;
               break;
            case 1:
               var10003 = 115;
               break;
            case 2:
               var10003 = 126;
               break;
            case 3:
               var10003 = 37;
               break;
            default:
               var10003 = 17;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class ForwardedResponseListener implements SnmpResponseListener {
      private SnmpPendingIndication a;

      ForwardedResponseListener(SnmpPendingIndication var2) {
         this.a = var2;
      }

      public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
         label11: {
            SnmpForwarder.this.c.debug(a("\b\u0003@M7\u001c\bW^v\u001c\tAJ9\u0000\u001fW\u001a$\u000b\u000fWS \u000b\b\b\u001a") + var4);
            if (var2 == 0) {
               this.a.setResponseVBL(var4);
               if (!SnmpMibNode.b) {
                  break label11;
               }
            }

            this.a.setError(var2, var3);
         }

         SnmpForwarder.this.a(this.a);
      }

      public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
         SnmpForwarder.this.c.debug(a("\b\u0003@M7\u001c\bW^v\u001c\tBU$\u001aL@_5\u000b\u0005D_2TL") + var4);
         this.a.setError(5, 0);
         SnmpForwarder.this.a(this.a);
      }

      public void handleTimeout(SnmpPendingRequest var1) {
         SnmpForwarder.this.c.debug(a("\b\u0003@M7\u001c\bW^v\u001c\tCO3\u001d\u0018\u0012N?\u0003\t]O\"") + var1);
         this.a.setError(13, 0);
         SnmpForwarder.this.a(this.a);
      }

      public void handleException(SnmpPendingRequest var1, Exception var2) {
         SnmpForwarder.this.c.debug(a("\b\u0003@M7\u001c\bW^v\u001c\tCO3\u001d\u0018\u0012_.\r\tBN?\u0001\u0002"), var2);
         this.a.setError(5, 0);
         SnmpForwarder.this.a(this.a);
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 110;
                  break;
               case 1:
                  var10003 = 108;
                  break;
               case 2:
                  var10003 = 50;
                  break;
               case 3:
                  var10003 = 58;
                  break;
               default:
                  var10003 = 86;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
