package weblogic.wtc.tbridge;

import com.bea.core.jatmi.common.ntrace;
import java.util.Arrays;
import java.util.Date;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageEOFException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.logging.Loggable;
import weblogic.management.configuration.WTCServerMBean;
import weblogic.management.configuration.WTCtBridgeGlobalMBean;
import weblogic.management.configuration.WTCtBridgeRedirectMBean;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.gwt.TuxedoConnectionFactory;
import weblogic.wtc.gwt.WTCService;
import weblogic.wtc.gwt.XmlFmlCnv;
import weblogic.wtc.jatmi.DequeueReply;
import weblogic.wtc.jatmi.EnqueueRequest;
import weblogic.wtc.jatmi.FldTbl;
import weblogic.wtc.jatmi.QueueTimeField;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TypedCArray;
import weblogic.wtc.jatmi.TypedFML;
import weblogic.wtc.jatmi.TypedFML32;
import weblogic.wtc.jatmi.TypedString;
import weblogic.wtc.jatmi.TypedXML;

public final class tBexec extends Thread {
   private QueueConnectionFactory qconFactory;
   private QueueConnection qcon;
   private QueueSession sendSession;
   private QueueSession recvSession;
   private QueueSession errorSession;
   private QueueReceiver qreceiver;
   private QueueSender qsender;
   private QueueSender qerror;
   private Queue queue;
   private TextMessage jmsSendMsg;
   private BytesMessage jmsSendBytes;
   private Message jmsRecvMsg;
   private Message jmsErrorMsg;
   private static int threadCount = 0;
   private int threadNumber;
   private static tBstartArgs sArgs;
   private static tBparseArgs x = null;

   public tBexec() {
      this.threadNumber = ++threadCount;
   }

   public static void tBmain(WTCServerMBean servermbean) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[tBexec/");
      }

      if (x == null) {
         x = new tBparseArgs();
      }

      try {
         sArgs = x.parseMBeans(servermbean);
      } catch (TPException var6) {
         WTCLogger.logtBparsefailed(var6.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("*]/tBexec/05/TPException: could not parse MBeans");
         }

         throw var6;
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBexec/configuration parse complete");
      }

      if (sArgs.redirect == -1) {
         Loggable lnrd = WTCLogger.logtBNOredirectsLoggable();
         lnrd.log();
         throw new TPException(4, lnrd.getMessage());
      } else {
         try {
            sleep(1000L);
         } catch (InterruptedException var5) {
         }

         for(int j = 1; j <= sArgs.redirect; ++j) {
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/t#" + j + " starting...");
            }

            (new tBexec()).start();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/10/");
         }

      }
   }

   public static void tBupdateGlobal(WTCtBridgeGlobalMBean tglobal) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/tBupdateGlobal(WTCtBridgeGlobal " + tglobal + ")");
      }

      if (x == null) {
         x = new tBparseArgs();
      }

      try {
         sArgs = x.parseGlobal(tglobal);
      } catch (TPException var5) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tBexec/tBupdateGlobal(10) TPException: could not parse MBeans");
         }

         WTCLogger.logtBparsefailed(var5.getMessage());
         throw var5;
      }

      if (traceEnabled) {
         ntrace.doTrace("global configuration parse complete");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/tBupdateGlobal(20) return success");
      }

   }

   public static void tBupdateRedirect(WTCtBridgeRedirectMBean redir) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/tBupdateRedirect(redirect " + redir + ")");
      }

      if (x == null) {
         x = new tBparseArgs();
      }

      try {
         sArgs = x.parseRedirect(redir);
      } catch (TPException var5) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tBexec/tBupdateRedirect(10) TPException: could not parse WTCtBridgeRedirectMBeans");
         }

         WTCLogger.logtBparsefailed(var5.getMessage());
         throw var5;
      }

      if (traceEnabled) {
         ntrace.doTrace("redirect configuration parse complete");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/tBupdateRedirect(20) return success");
      }

   }

   public static boolean tBactivate() {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/tBactivate()");
      }

      if (x == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/tBactivate(10) returns false");
         }

         return false;
      } else {
         sArgs = x.getParsedMBeans();
         if (sArgs.redirect == -1) {
            Loggable lnrd = WTCLogger.logtBNOredirectsLoggable();
            lnrd.log();
            if (traceEnabled) {
               ntrace.doTrace("]/tBexec/tBactivate(20) returns false");
            }

            return false;
         } else {
            try {
               sleep(1000L);
            } catch (InterruptedException var4) {
            }

            for(int j = 1; j <= sArgs.redirect; ++j) {
               if (traceEnabled) {
                  ntrace.doTrace("tBactivate: t#" + j + " starting...");
               }

               (new tBexec()).start();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/tBexec/tBactivate(30) return true");
            }

            return true;
         }
      }
   }

   public void run() {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (sArgs.direction[this.threadNumber - 1] == 1) {
         if (traceEnabled) {
            ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": TuxQ2JmsQ");
         }

         try {
            this.tuxQ2jmsQ(this.threadNumber - 1);
         } catch (Exception var6) {
            var6.printStackTrace();
            if (traceEnabled) {
               ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": TuxQ2JmsQ " + var6);
            }
         }
      } else if (sArgs.direction[this.threadNumber - 1] == 2) {
         if (traceEnabled) {
            ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": JmsQ2TuxQ");
         }

         try {
            this.jmsQ2tuxQ(this.threadNumber - 1);
         } catch (Exception var5) {
            var5.printStackTrace();
            if (traceEnabled) {
               ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": JmsQ2TuxQ " + var5);
            }
         }
      } else if (sArgs.direction[this.threadNumber - 1] == 3) {
         if (traceEnabled) {
            ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": JmsQ2TuxS");
         }

         try {
            this.jmsQ2tuxS(this.threadNumber - 1);
         } catch (Exception var4) {
            var4.printStackTrace();
            if (traceEnabled) {
               ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": JmsQ2TuxS " + var4);
            }
         }
      } else if (sArgs.direction[this.threadNumber - 1] == 4) {
         if (traceEnabled) {
            ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": JmsQ2JmsQ");
         }

         try {
            this.jmsQ2jmsQ(this.threadNumber - 1);
         } catch (Exception var3) {
            var3.printStackTrace();
            if (traceEnabled) {
               ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": JmsQ2JmsQ " + var3);
            }
         }
      } else if (traceEnabled) {
         ntrace.doTrace("/tBexec>run/t#" + this.threadNumber + ": BadLogic/10/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]tBexec<run/t#" + this.threadNumber + ": exited.");
      }

   }

   private void tuxQ2jmsQ(int redirectIndex) throws Exception, NamingException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[tBexec/tuxQ2jmsQ/");
      }

      TypedString tuxString = null;
      TypedFML32 tuxFML32 = null;
      TypedFML tuxFML = null;
      TypedCArray tuxCArray = null;
      TypedXML tuxXML = null;
      String jmsString = null;
      String tuxType = null;
      int myThreadNumber = redirectIndex + 1;
      TuxedoConnection myTux = null;
      DequeueReply deq_outctl = null;
      byte[] msgid = null;
      byte[] corrid = null;
      boolean sendOK = true;
      boolean doWait = true;
      boolean doPeek = false;
      boolean tBerrorQueue = false;
      byte[] zeroCorrid = new byte[32];

      for(int i = 0; i < 32; ++i) {
         zeroCorrid[i] = 0;
      }

      Context iSc = new InitialContext();

      try {
         this.jmsSendInit(iSc, sArgs.targetName[redirectIndex], sArgs.jmsFactory);
      } catch (JMSException var54) {
         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/tuxQ2jmsQ/JMS targetName failed: " + sArgs.targetName[redirectIndex]);
         }

         WTCLogger.logtBJMStargetNamefailed();
         sArgs.operational[redirectIndex] = false;
      }

      Context iEc = new InitialContext();

      try {
         this.jmsErrorInit(iEc, sArgs.wlsErrorDestination, sArgs.jmsFactory);
         tBerrorQueue = true;
      } catch (Exception var53) {
         WTCLogger.logtBJMSerrorDestinationfailed();
         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/tuxQ2jmsQ/JMS Error Destination failed: " + sArgs.wlsErrorDestination);
         }
      }

      Context iTc = new InitialContext();

      try {
         TuxedoConnectionFactory tcf = (TuxedoConnectionFactory)iTc.lookup(sArgs.tuxFactory);
         myTux = tcf.getTuxedoConnection();
      } catch (NamingException var42) {
         WTCLogger.logtBNOTuxedoConnectionFactory();
         throw new TPException(6, ">tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " Could not get " + sArgs.tuxFactory + ": " + var42);
      }

      if (sArgs.operational[redirectIndex] && myTux != null) {
         this.qcon.start();
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " ready.");
         }
      } else {
         sArgs.operational[redirectIndex] = false;
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " SHUTDOWN THREAD.");
         }
      }

      while(true) {
         while(true) {
            TransactionManager myTm;
            while(true) {
               if (!sArgs.operational[redirectIndex]) {
                  if (myTux != null) {
                     myTux.tpterm();
                  }

                  try {
                     this.jmsSendClose();
                  } catch (JMSException var33) {
                  }

                  try {
                     this.jmsErrorClose();
                  } catch (JMSException var32) {
                  }

                  if (this.qcon != null) {
                     this.qcon.close();
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("]/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " shutdown.");
                  }

                  return;
               }

               myTm = TxHelper.getTransactionManager();
               myTm.setTransactionTimeout(sArgs.timeout);
               myTm.begin("WTC");
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " transaction started.");
               }

               int flags = 32;

               try {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " about to try tpdequeue");
                  }

                  deq_outctl = myTux.tpdequeue(sArgs.sourceQspace[redirectIndex], sArgs.sourceName[redirectIndex], (byte[])msgid, (byte[])corrid, doWait, doPeek, flags);
                  if (deq_outctl == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " Invalid NULL tpdequeue...retry");
                     }

                     myTm.rollback();
                  } else {
                     if (deq_outctl.getReplyBuffer() != null) {
                        tuxType = deq_outctl.getReplyBuffer().getType();
                     } else {
                        tuxType = "STRING";
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " tuxType:" + tuxType);
                     }

                     if (tuxType.equalsIgnoreCase("STRING")) {
                        tuxString = (TypedString)deq_outctl.getReplyBuffer();
                        break;
                     }

                     if (tuxType.equalsIgnoreCase("FML")) {
                        tuxFML = (TypedFML)deq_outctl.getReplyBuffer();
                        break;
                     }

                     if (tuxType.equalsIgnoreCase("FML32")) {
                        tuxFML32 = (TypedFML32)deq_outctl.getReplyBuffer();
                        break;
                     }

                     if (tuxType.equalsIgnoreCase("CARRAY")) {
                        tuxCArray = (TypedCArray)deq_outctl.getReplyBuffer();
                        break;
                     }

                     if (tuxType.equalsIgnoreCase("XML")) {
                        tuxXML = (TypedXML)deq_outctl.getReplyBuffer();
                        break;
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " Unknown buffer type ingnored: " + tuxType);
                     }

                     myTm.rollback();

                     try {
                        sleep((long)sArgs.retryDelay);
                     } catch (InterruptedException var41) {
                     }
                  }
               } catch (TPException var45) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/tuxQ2jmsQ/TPException " + var45);
                  }

                  if (var45.gettperrno() == 13) {
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/timeout on: " + sArgs.sourceQspace[redirectIndex]);
                     }
                  } else if (traceEnabled) {
                     ntrace.doTrace("/tBexec/tuxQ2jmsQ/failed: " + sArgs.sourceQspace[redirectIndex]);
                  }

                  try {
                     myTm.rollback();
                  } catch (Exception var43) {
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/TPException Rollback" + var43);
                     }
                  }

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var40) {
                  }
               } catch (Exception var46) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/tuxQ2jmsQ/Not TPException" + var46);
                  }

                  try {
                     myTm.rollback();
                  } catch (Exception var44) {
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/Exception Rollback" + var44);
                     }
                  }

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var39) {
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " Tux Msg received.");
            }

            this.showTuxMsg(deq_outctl);
            int tuxPriority;
            Integer p;
            if ((p = deq_outctl.getpriority()) == null) {
               tuxPriority = 1;
            } else {
               tuxPriority = p;
            }

            int jmsPriority = sArgs.pMapTuxToJms[tuxPriority];
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " TPriority:" + tuxPriority + " JPriority:" + jmsPriority);
            }

            if (deq_outctl.getcorrid() != null) {
               this.jmsSendMsg.setJMSCorrelationIDAsBytes(deq_outctl.getcorrid());
            } else {
               this.jmsSendMsg.setJMSCorrelationIDAsBytes(zeroCorrid);
            }

            if (tuxType.equalsIgnoreCase("STRING")) {
               if (deq_outctl.getReplyBuffer() != null) {
                  this.jmsSendMsg.setText(tuxString.toString());
               } else {
                  this.jmsSendMsg.setText((String)null);
               }
            } else {
               TextMessage tmpMsg;
               if (tuxType.equalsIgnoreCase("FML")) {
                  tmpMsg = this.FML2jms(tuxFML, this.jmsSendMsg);
                  if (tmpMsg == null) {
                     WTCLogger.logtBSlashQFML2XMLFailed();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " translation to FML failed");
                     }

                     try {
                        myTm.rollback();
                     } catch (Exception var52) {
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/tuxQ2jmsQ/FML Rollback" + var52);
                        }
                     }

                     try {
                        sleep((long)sArgs.retryDelay);
                     } catch (InterruptedException var38) {
                     }
                     continue;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace(">tBexec/tuxQ2jmsQ/t#" + myThreadNumber + "translation from FML complete: " + this.jmsSendMsg.getText());
                  }
               } else if (tuxType.equalsIgnoreCase("FML32")) {
                  tmpMsg = this.FML322jms(tuxFML32, this.jmsSendMsg);
                  if (tmpMsg == null) {
                     WTCLogger.logtBSlashQFML322XMLFailed();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + " translation to FML32 failed");
                     }

                     try {
                        myTm.rollback();
                     } catch (Exception var51) {
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/tuxQ2jmsQ/FML32 Rollback" + var51);
                        }
                     }

                     try {
                        sleep((long)sArgs.retryDelay);
                     } catch (InterruptedException var37) {
                     }
                     continue;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/tuxQ2jmsQ/t#" + myThreadNumber + "translation from FML32 complete: " + this.jmsSendMsg.getText());
                  }
               } else if (tuxType.equalsIgnoreCase("CARRAY")) {
                  this.jmsSendBytes.clearBody();
                  if (deq_outctl.getcorrid() != null) {
                     this.jmsSendBytes.setJMSCorrelationIDAsBytes(deq_outctl.getcorrid());
                  } else {
                     this.jmsSendBytes.setJMSCorrelationIDAsBytes(zeroCorrid);
                  }

                  this.jmsSendBytes.writeBytes(tuxCArray.carray);
               } else if (tuxType.equalsIgnoreCase("XML")) {
                  this.jmsSendMsg.setText(tuxXML.toString());
               }
            }

            try {
               if (tuxType.equalsIgnoreCase("CARRAY")) {
                  sendOK = this.jmsSend(this.jmsSendBytes, jmsPriority);
               } else {
                  sendOK = this.jmsSend(this.jmsSendMsg, jmsPriority);
               }

               if (sendOK) {
                  myTm.commit();
               } else {
                  myTm.rollback();

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var36) {
                  }
               }
            } catch (JMSException var49) {
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/tuxQ2jmsQ/JMS send failed: " + sArgs.targetName[redirectIndex]);
               }

               try {
                  myTm.rollback();
               } catch (Exception var47) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/tuxQ2jmsQ/Rollback failed in JMSException " + var47);
                  }
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var35) {
               }
            } catch (Exception var50) {
               var50.printStackTrace();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/tuxQ2jmsQ/JMS send exception: " + sArgs.targetName[redirectIndex]);
               }

               try {
                  myTm.rollback();
               } catch (Exception var48) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/tuxQ2jmsQ/Rollback failed Exception of JMSException " + var48);
                  }
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var34) {
               }
            }
         }
      }
   }

   private void jmsQ2tuxQ(int redirectIndex) throws Exception, NamingException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsQ2tuxQ/");
      }

      TypedString tuxString = null;
      TypedFML32 tuxFML32 = null;
      TypedCArray tuxCArray = null;
      int myThreadNumber = redirectIndex + 1;
      boolean tBerrorQueue = false;
      Context iRc = new InitialContext();

      try {
         this.jmsRecvInit(iRc, sArgs.sourceName[redirectIndex], sArgs.jmsFactory);
      } catch (Exception var42) {
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxQ/JMS recv initialization failed: " + sArgs.sourceName[redirectIndex]);
         }

         WTCLogger.logtBJMSsourceNamefailed();
         sArgs.operational[redirectIndex] = false;
      }

      Context iEc = new InitialContext();

      try {
         this.jmsErrorInit(iEc, sArgs.wlsErrorDestination, sArgs.jmsFactory);
         tBerrorQueue = true;
      } catch (Exception var41) {
         WTCLogger.logtBJMSerrorDestinationfailed();
         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/jmsQ2tuxQ/JMS Error Destination failed: " + sArgs.wlsErrorDestination);
         }
      }

      Context iTc = new InitialContext();

      TuxedoConnection myTux;
      try {
         TuxedoConnectionFactory tcf = (TuxedoConnectionFactory)iTc.lookup(sArgs.tuxFactory);
         myTux = tcf.getTuxedoConnection();
      } catch (NamingException var37) {
         WTCLogger.logtBNOTuxedoConnectionFactory();
         throw new TPException(6, ">tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Could not get " + sArgs.tuxFactory + ": " + var37);
      }

      if (sArgs.operational[redirectIndex] && myTux != null) {
         this.qcon.start();
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " ready.");
         }
      } else {
         sArgs.operational[redirectIndex] = false;
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " SHUTDOWN THREAD.");
         }
      }

      while(sArgs.operational[redirectIndex]) {
         try {
            this.jmsRecvMsg = this.jmsReceive();
         } catch (Exception var38) {
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxQ/JMS recv failed: " + sArgs.sourceName[redirectIndex]);
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var36) {
            }
            continue;
         }

         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Got JmsRecvMsg: " + this.jmsRecvMsg);
         }

         if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("FLAT")) {
            tuxFML32 = this.jms2FML32(this.jmsRecvMsg);
            if (tuxFML32 == null) {
               WTCLogger.logtBInternalTranslationFailed();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " translation to FML32 failed");
               }

               if (tBerrorQueue) {
                  if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                     this.jmsRecvMsg.acknowledge();
                     WTCLogger.logtBsent2errorDestination();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Msg sent to error queue.");
                     }
                  } else {
                     WTCLogger.logtBsent2errorDestinationfailed();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Failed to sent to error queue.");
                     }
                  }
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var35) {
               }
               continue;
            }

            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " translation to FML32 complete");
            }
         } else {
            if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("WLXT")) {
               WTCLogger.logtBNOWLXToptionAvailable();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2jmsQ/WLXT option unavailable.");
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var34) {
               }
               continue;
            }

            if (this.jmsRecvMsg instanceof TextMessage) {
               tuxString = this.jms2tuxString(this.jmsRecvMsg);
            } else {
               if (!(this.jmsRecvMsg instanceof BytesMessage)) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxQ/Unsupported JMS Message Type.");
                  }

                  WTCLogger.logtBunsupportedJMSmsgtype();

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var33) {
                  }
                  continue;
               }

               tuxCArray = this.jms2tuxCArray(this.jmsRecvMsg);
            }
         }

         int jmsPriority = this.jmsRecvMsg.getJMSPriority();
         int tuxPriority = sArgs.pMapJmsToTux[jmsPriority];
         Integer priority = new Integer(tuxPriority);
         QueueTimeField deq_time = null;
         QueueTimeField exp_time = null;
         int delivery_qos = 1;
         int reply_qos = 1;
         int urcode = 0;
         byte[] msgid = null;
         byte[] corrid = this.jmsRecvMsg.getJMSCorrelationIDAsBytes();
         String replyqueue = null;
         replyqueue = sArgs.replyQ[redirectIndex];
         EnqueueRequest inctl = new EnqueueRequest((QueueTimeField)deq_time, priority, (QueueTimeField)exp_time, delivery_qos, reply_qos, (byte[])msgid, corrid, replyqueue, sArgs.tuxErrorQueue, false, false, urcode);
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " about to try tpenqueue");
         }

         try {
            if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("NO")) {
               if (this.jmsRecvMsg instanceof TextMessage) {
                  myTux.tpenqueue(sArgs.targetQspace[redirectIndex], sArgs.targetName[redirectIndex], inctl, tuxString, 0);
               } else {
                  myTux.tpenqueue(sArgs.targetQspace[redirectIndex], sArgs.targetName[redirectIndex], inctl, tuxCArray, 0);
               }
            } else {
               myTux.tpenqueue(sArgs.targetQspace[redirectIndex], sArgs.targetName[redirectIndex], inctl, tuxFML32, 0);
            }

            this.jmsRecvMsg.acknowledge();
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Tux Msg queued.");
            }
         } catch (TPException var39) {
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " TPException explanation: " + var39);
            }

            if (tBerrorQueue) {
               if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                  this.jmsRecvMsg.acknowledge();
                  if (var39.gettperrno() == 6) {
                     WTCLogger.logErrorFail2FindImportedQSpace(sArgs.targetQspace[redirectIndex]);
                  } else {
                     WTCLogger.logtBsent2errorDestination();
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Msg sent to error queue.");
                  }
               } else {
                  WTCLogger.logtBsent2errorDestinationfailed();
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Failed to sent to error queue.");
                  }
               }
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var32) {
            }

            this.recvSession.recover();
         } catch (Exception var40) {
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " Exception " + var40);
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var31) {
            }

            this.recvSession.recover();
         }
      }

      if (myTux != null) {
         myTux.tpterm();
      }

      try {
         this.jmsRecvClose();
      } catch (JMSException var30) {
      }

      try {
         this.jmsErrorClose();
      } catch (JMSException var29) {
      }

      if (this.qcon != null) {
         this.qcon.close();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsQ2tuxQ/t#" + myThreadNumber + " shutdown.");
      }

   }

   private void jmsQ2tuxS(int redirectIndex) throws Exception, NamingException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsQ2tuxS/redirectIndex = " + redirectIndex);
      }

      int myThreadNumber = redirectIndex + 1;
      int jmsPriority = true;
      String tuxType = null;
      TypedString tuxString = null;
      TypedFML32 tuxFML32 = null;
      TypedFML tuxFML = null;
      TypedCArray tuxCArray = null;
      Reply myRtn = null;
      boolean tBerrorQueue = false;
      boolean sendOK = true;
      Context iRc = new InitialContext();

      try {
         this.jmsRecvInit(iRc, sArgs.sourceName[redirectIndex], sArgs.jmsFactory);
      } catch (JMSException var44) {
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxS/sourceName failed: " + sArgs.sourceName[redirectIndex]);
         }

         WTCLogger.logtBJMStargetNamefailed();
         sArgs.operational[redirectIndex] = false;
      }

      Context iSc = new InitialContext();

      try {
         this.jmsSendInit(iSc, sArgs.replyQ[redirectIndex], sArgs.jmsFactory);
      } catch (JMSException var43) {
         var43.printStackTrace();
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxS/replyQ failed: " + sArgs.replyQ[redirectIndex]);
         }

         WTCLogger.logtBJMStargetNamefailed();
         sArgs.operational[redirectIndex] = false;
      }

      Context iEc = new InitialContext();

      try {
         this.jmsErrorInit(iEc, sArgs.wlsErrorDestination, sArgs.jmsFactory);
         tBerrorQueue = true;
      } catch (Exception var42) {
         WTCLogger.logtBJMSerrorDestinationfailed();
         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/jmsQ2tuxS/JMS Error Destination failed: " + sArgs.wlsErrorDestination);
         }
      }

      Context iTc = new InitialContext();

      TuxedoConnection myTux;
      try {
         TuxedoConnectionFactory tcf = (TuxedoConnectionFactory)iTc.lookup(sArgs.tuxFactory);
         myTux = tcf.getTuxedoConnection();
      } catch (NamingException var38) {
         WTCLogger.logtBNOTuxedoConnectionFactory();
         throw new TPException(6, ">tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Could not get " + sArgs.tuxFactory + ": " + var38);
      }

      if (sArgs.operational[redirectIndex] && myTux != null) {
         this.qcon.start();
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " ready.");
         }
      } else {
         sArgs.operational[redirectIndex] = false;
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " SHUTDOWN THREAD.");
         }
      }

      while(sArgs.operational[redirectIndex]) {
         int reason = 0;

         try {
            this.jmsRecvMsg = this.jmsReceive();
         } catch (Exception var39) {
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxS/JMS receive failed: " + sArgs.sourceName[redirectIndex]);
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var37) {
            }
            continue;
         }

         int jmsPriority = this.jmsRecvMsg.getJMSPriority();
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Got JmsRecvMsg: " + this.jmsRecvMsg);
         }

         if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("FLAT")) {
            tuxFML32 = this.jms2FML32(this.jmsRecvMsg);
            if (tuxFML32 == null) {
               WTCLogger.logtBInternalTranslationFailed();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " translation to FML32 failed.");
               }

               if (tBerrorQueue) {
                  if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                     this.jmsRecvMsg.acknowledge();
                     WTCLogger.logtBsent2errorDestination();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Msg sent to error queue.");
                     }
                  } else {
                     WTCLogger.logtBsent2errorDestinationfailed();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Failed to sent to error queue.");
                     }
                  }
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var36) {
               }
               continue;
            }

            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " translation to FML32 complete.");
            }
         } else {
            if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("WLXT")) {
               WTCLogger.logtBNOWLXToptionAvailable();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2tuxS/WLXT option unavailable ");
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var35) {
               }
               continue;
            }

            if (this.jmsRecvMsg instanceof TextMessage) {
               tuxString = this.jms2tuxString(this.jmsRecvMsg);
            } else {
               if (!(this.jmsRecvMsg instanceof BytesMessage)) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2jmsQ/Unsupported JMS Message Type.");
                  }

                  WTCLogger.logtBunsupportedJMSmsgtype();

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var34) {
                  }
                  continue;
               }

               tuxCArray = this.jms2tuxCArray(this.jmsRecvMsg);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " About to call tpcall[" + sArgs.targetName[redirectIndex] + "]");
         }

         int myRetryCnt = 0;

         do {
            try {
               if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("NO")) {
                  if (this.jmsRecvMsg instanceof TextMessage) {
                     myRtn = myTux.tpcall(sArgs.targetName[redirectIndex], tuxString, 0);
                  } else {
                     myRtn = myTux.tpcall(sArgs.targetName[redirectIndex], tuxCArray, 0);
                  }
               } else {
                  myRtn = myTux.tpcall(sArgs.targetName[redirectIndex], tuxFML32, 0);
               }
               break;
            } catch (TPException var40) {
               ++myRetryCnt;
               reason = var40.gettperrno();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " tpcall threw TPException " + var40);
                  ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " retry#: " + myRetryCnt);
               }
            } catch (Exception var41) {
               ++myRetryCnt;
               if (traceEnabled) {
                  ntrace.doTrace("*]/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " tpcall threw Exception " + var41);
               }
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var33) {
            }
         } while(myRetryCnt <= sArgs.retries);

         if (myRetryCnt > sArgs.retries) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " retry count exhausted.");
            }

            if (tBerrorQueue) {
               if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                  this.jmsRecvMsg.acknowledge();
                  if (reason != 6) {
                     WTCLogger.logtBsent2errorDestination();
                  } else {
                     WTCLogger.logErrorTbNoSuchImport(sArgs.targetName[redirectIndex]);
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Msg sent to error queue.");
                  }
               } else {
                  WTCLogger.logtBsent2errorDestinationfailed();
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Failed to sent to error queue.");
                  }
               }
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var32) {
            }
         } else {
            if (myRtn.getReplyBuffer() != null) {
               tuxType = myRtn.getReplyBuffer().getType();
            } else {
               tuxType = "STRING";
            }

            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " tuxType:" + tuxType);
            }

            if (tuxType.equalsIgnoreCase("STRING")) {
               tuxString = (TypedString)myRtn.getReplyBuffer();
            } else if (tuxType.equalsIgnoreCase("FML")) {
               tuxFML = (TypedFML)myRtn.getReplyBuffer();
            } else if (tuxType.equalsIgnoreCase("FML32")) {
               tuxFML32 = (TypedFML32)myRtn.getReplyBuffer();
            } else {
               if (!tuxType.equalsIgnoreCase("CARRAY")) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Tux returned unknown buffer type: " + tuxType);
                  }

                  if (tBerrorQueue) {
                     if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                        this.jmsRecvMsg.acknowledge();
                        WTCLogger.logErrorTbUnsupportedBufferType(tuxType);
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Msg sent to error queue.");
                        }
                     } else {
                        WTCLogger.logtBsent2errorDestinationfailed();
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Failed to sent to error queue.");
                        }
                     }
                  }

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var31) {
                  }
                  continue;
               }

               tuxCArray = (TypedCArray)myRtn.getReplyBuffer();
            }

            this.jmsSendMsg.setJMSCorrelationIDAsBytes(this.jmsRecvMsg.getJMSCorrelationIDAsBytes());
            if (tuxType.equalsIgnoreCase("STRING")) {
               if (myRtn.getReplyBuffer() != null) {
                  this.jmsSendMsg.setText(tuxString.toString());
               } else {
                  this.jmsSendMsg.setText((String)null);
               }
            } else if (tuxType.equalsIgnoreCase("FML")) {
               this.jmsSendMsg = this.FML2jms(tuxFML, this.jmsSendMsg);
               if (this.jmsSendMsg == null) {
                  WTCLogger.logtBInternalFML2XMLFailed();
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " translation to FML failed");
                  }

                  if (tBerrorQueue) {
                     if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                        this.jmsRecvMsg.acknowledge();
                        WTCLogger.logtBsent2errorDestination();
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Msg sent to error queue.");
                        }
                     } else {
                        WTCLogger.logtBsent2errorDestinationfailed();
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Failed to sent to error queue.");
                        }
                     }
                  }

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var30) {
                  }
                  continue;
               }

               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " translation from FML complete: " + this.jmsSendMsg.getText());
               }
            } else if (tuxType.equalsIgnoreCase("FML32")) {
               this.jmsSendMsg = this.FML322jms(tuxFML32, this.jmsSendMsg);
               if (this.jmsSendMsg == null) {
                  WTCLogger.logtBInternalFML322XMLFailed();
                  if (traceEnabled) {
                     ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " translation to FML32 failed");
                  }

                  if (tBerrorQueue) {
                     if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                        this.jmsRecvMsg.acknowledge();
                        WTCLogger.logtBsent2errorDestination();
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Msg sent to error queue.");
                        }
                     } else {
                        WTCLogger.logtBsent2errorDestinationfailed();
                        if (traceEnabled) {
                           ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Failed to sent to error queue.");
                        }
                     }
                  }

                  try {
                     sleep((long)sArgs.retryDelay);
                  } catch (InterruptedException var29) {
                  }
                  continue;
               }

               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + "translation from FML32 complete: " + this.jmsSendMsg.getText());
               }
            } else if (tuxType.equalsIgnoreCase("CARRAY")) {
               this.jmsSendBytes.clearBody();
               this.jmsSendBytes.writeBytes(tuxCArray.carray);
            }

            if (tuxType.equalsIgnoreCase("CARRAY")) {
               sendOK = this.jmsSend(this.jmsSendBytes, jmsPriority);
            } else {
               sendOK = this.jmsSend(this.jmsSendMsg, jmsPriority);
            }

            if (sendOK) {
               this.jmsRecvMsg.acknowledge();
            } else {
               if (tBerrorQueue) {
                  if (this.jmsError(this.jmsRecvMsg, this.jmsRecvMsg.getJMSPriority())) {
                     this.jmsRecvMsg.acknowledge();
                     WTCLogger.logErrorTbJmsSendFailure();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Msg sent to error queue.");
                     }
                  } else {
                     WTCLogger.logtBsent2errorDestinationfailed();
                     if (traceEnabled) {
                        ntrace.doTrace("/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " Failed to sent to error queue.");
                     }
                  }
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var28) {
               }
            }
         }
      }

      if (myTux != null) {
         myTux.tpterm();
      }

      try {
         this.jmsErrorClose();
      } catch (JMSException var27) {
      }

      try {
         this.jmsSendClose();
      } catch (JMSException var26) {
      }

      try {
         this.jmsRecvClose();
      } catch (JMSException var25) {
      }

      if (this.qcon != null) {
         this.qcon.close();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsQ2tuxS/t#" + myThreadNumber + " shutdown.");
      }

   }

   private void jmsQ2jmsQ(int redirectIndex) throws Exception, NamingException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsQ2jmsQ/");
      }

      int myThreadNumber = redirectIndex + 1;
      int jmsPriority = 1;
      Context iRc = new InitialContext();

      try {
         this.jmsRecvInit(iRc, sArgs.sourceName[redirectIndex], sArgs.jmsFactory);
      } catch (JMSException var20) {
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2jmsQ/sourceName failed: " + sArgs.sourceName[redirectIndex]);
         }

         WTCLogger.logtBJMStargetNamefailed();
         sArgs.operational[redirectIndex] = false;
      }

      Context iSc = new InitialContext();

      try {
         this.jmsSendInit(iSc, sArgs.replyQ[redirectIndex], sArgs.jmsFactory);
      } catch (JMSException var19) {
         var19.printStackTrace();
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsQ2jmsQ/replyQ failed: " + sArgs.replyQ[redirectIndex]);
         }

         WTCLogger.logtBJMStargetNamefailed();
         sArgs.operational[redirectIndex] = false;
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBexec/jmsQ2jmsQ/t#" + myThreadNumber + " ready.");
      }

      if (sArgs.operational[redirectIndex]) {
         this.qcon.start();
      }

      while(sArgs.operational[redirectIndex]) {
         try {
            this.jmsRecvMsg = this.jmsReceive();
         } catch (Exception var18) {
            var18.printStackTrace();
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2jmsQ/t#" + myThreadNumber + " Exception " + var18);
            }
         }

         if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("FLAT")) {
            TypedFML32 inData = this.jms2FML32(this.jmsRecvMsg);
            if (inData == null) {
               WTCLogger.logtBInternalTranslationFailed();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2jmsQ/t#" + myThreadNumber + " translation to FML32 failed");
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var17) {
               }
               continue;
            }

            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2jmsQ/t#" + myThreadNumber + " translation to FML32 complete");
            }

            this.jmsSendMsg = this.FML322jms(inData, (TextMessage)this.jmsRecvMsg);
            if (this.jmsSendMsg == null) {
               WTCLogger.logtBInternalTranslationFailed();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2jmsQ/t#" + myThreadNumber + " translation to FML32 failed");
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var16) {
               }
               continue;
            }

            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsQ2jmsQ/t#" + myThreadNumber + " translation from FML32 complete: " + inData.toString());
            }
         } else {
            if (sArgs.translateFML[redirectIndex].equalsIgnoreCase("WLXT")) {
               WTCLogger.logtBNOWLXToptionAvailable();
               if (traceEnabled) {
                  ntrace.doTrace("/tBexec/jmsQ2jmsQ/WLXT option unavailable ");
               }

               try {
                  sleep((long)sArgs.retryDelay);
               } catch (InterruptedException var15) {
               }
               continue;
            }

            this.jmsSendMsg = (TextMessage)this.jmsRecvMsg;
         }

         if (this.jmsSend(this.jmsSendMsg, jmsPriority)) {
            this.jmsRecvMsg.acknowledge();
         } else {
            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var14) {
            }
         }
      }

      try {
         this.jmsSendClose();
      } catch (JMSException var13) {
      }

      try {
         this.jmsRecvClose();
      } catch (JMSException var12) {
      }

      if (this.qcon != null) {
         this.qcon.close();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsQ2jmsQ/t#" + myThreadNumber + " shutdown.");
      }

   }

   public void jmsSendInit(Context ctx, String queueName, String jmsFac) throws NamingException, JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsSendInit/");
      }

      if (this.sendSession == null) {
         if (this.qcon == null) {
            this.qconFactory = (QueueConnectionFactory)ctx.lookup(jmsFac);
            this.qcon = this.qconFactory.createQueueConnection();
         }

         this.sendSession = this.qcon.createQueueSession(false, 1);
      }

      try {
         this.queue = (Queue)ctx.lookup(queueName);
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsSendInit/queue (" + queueName + ") located");
         }
      } catch (NamingException var6) {
         this.queue = this.sendSession.createQueue(queueName);
         ctx.bind(queueName, this.queue);
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsSendInit/queue (" + queueName + ") created");
         }
      }

      this.qsender = this.sendSession.createSender(this.queue);
      this.jmsSendMsg = this.sendSession.createTextMessage();
      this.jmsSendBytes = this.sendSession.createBytesMessage();
      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsSendInit/10/");
      }

   }

   public boolean jmsSend(Message jmsMsg, int jmsPriority) throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsSend/");
      }

      if (traceEnabled) {
         if (jmsMsg instanceof TextMessage) {
            ntrace.doTrace("/tBexec/jmsSend/Jms Msg: " + ((TextMessage)jmsMsg).getText());
         } else {
            ntrace.doTrace("/tBexec/jmsSend/BytesMessage: ");
         }
      }

      int myRetryCnt = 0;

      while(true) {
         try {
            this.qsender.setPriority(jmsPriority);
            this.qsender.send(jmsMsg);
            break;
         } catch (Exception var8) {
            ++myRetryCnt;
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsSend/Error - retry#: " + myRetryCnt);
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var7) {
            }

            if (myRetryCnt > sArgs.retries) {
               break;
            }
         }
      }

      if (myRetryCnt > sArgs.retries) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tBexec/jmsSend/JMS retry count exhausted.");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/jmsSend/10/");
         }

         return true;
      }
   }

   public void jmsSendClose() throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsSendClose/");
      }

      if (this.qsender != null) {
         this.qsender.close();
      }

      if (this.sendSession != null) {
         this.sendSession.close();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsSendClose/10/");
      }

   }

   public void jmsRecvInit(Context ctx, String queueName, String jmsFac) throws NamingException, JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsRecvInit/queueName = " + queueName + ", jmsFac = " + jmsFac);
      }

      if (this.recvSession == null) {
         if (this.qcon == null) {
            this.qconFactory = (QueueConnectionFactory)ctx.lookup(jmsFac);
            this.qcon = this.qconFactory.createQueueConnection();
         }

         this.recvSession = this.qcon.createQueueSession(false, 2);
      }

      int i;
      for(i = 0; i < 10; ++i) {
         if (traceEnabled) {
            ntrace.doTrace("thread(" + this.threadNumber + "), thread id(" + this.getId() + "), lookup #" + i);
         }

         try {
            this.queue = (Queue)ctx.lookup(queueName);
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsRecvInit/queue (" + queueName + ") located");
            }
            break;
         } catch (NamingException var8) {
            if (traceEnabled) {
               ntrace.doTrace("NamingException: " + var8);
            }
         } catch (Exception var9) {
            if (traceEnabled) {
               ntrace.doTrace("Exception: " + var9);
            }
         }

         try {
            sleep(1000L);
         } catch (InterruptedException var7) {
            if (traceEnabled) {
               ntrace.doTrace("InterruptedException: " + var7);
            }
         }
      }

      if (i >= 10) {
         this.queue = this.recvSession.createQueue(queueName);
         ctx.bind(queueName, this.queue);
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsRecvInit/queue (" + queueName + ") created");
         }
      }

      this.qreceiver = this.recvSession.createReceiver(this.queue);
      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsRecvInit/10/");
      }

   }

   public Message jmsReceive() throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsReceive/");
      }

      Message message;
      try {
         message = this.qreceiver.receive();
         this.showJmsMsg(message);
         synchronized(this) {
            this.notifyAll();
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         message = null;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsReceive/10/");
      }

      return message;
   }

   public void jmsRecvClose() throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsRecvClose/");
      }

      if (this.qreceiver != null) {
         this.qreceiver.close();
      }

      if (this.recvSession != null) {
         this.recvSession.close();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsRecvClose/10/");
      }

   }

   public void jmsErrorInit(Context ctx, String queueName, String jmsFac) throws NamingException, JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsErrorInit/");
      }

      if (this.errorSession == null) {
         if (this.qcon == null) {
            this.qconFactory = (QueueConnectionFactory)ctx.lookup(jmsFac);
            this.qcon = this.qconFactory.createQueueConnection();
         }

         this.errorSession = this.qcon.createQueueSession(false, 1);
      }

      try {
         this.queue = (Queue)ctx.lookup(queueName);
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsErrorInit/queue (" + queueName + ") located");
         }
      } catch (NamingException var6) {
         this.queue = this.errorSession.createQueue(queueName);
         ctx.bind(queueName, this.queue);
         if (traceEnabled) {
            ntrace.doTrace("/tBexec/jmsErrorInit/queue (" + queueName + ") created");
         }
      }

      this.qerror = this.errorSession.createSender(this.queue);
      this.jmsErrorMsg = this.errorSession.createMessage();
      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsErrorInit/10/");
      }

   }

   public boolean jmsError(Message jmsMsg, int jmsPriority) throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsError/");
      }

      int myRetryCnt = 0;

      while(true) {
         try {
            this.qerror.setPriority(jmsPriority);
            this.qerror.send(jmsMsg);
            break;
         } catch (Exception var8) {
            ++myRetryCnt;
            if (traceEnabled) {
               ntrace.doTrace("/tBexec/jmsError/Error - retry#: " + myRetryCnt);
            }

            try {
               sleep((long)sArgs.retryDelay);
            } catch (InterruptedException var7) {
            }

            if (myRetryCnt > sArgs.retries) {
               break;
            }
         }
      }

      if (myRetryCnt > sArgs.retries) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tBexec/jmsError/JMS retry count exhausted.");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/tBexec/jmsError/10/");
         }

         return true;
      }
   }

   public void jmsErrorClose() throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jmsErrorClose/");
      }

      if (this.qerror != null) {
         this.qerror.close();
      }

      if (this.errorSession != null) {
         this.errorSession.close();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jmsErrorClose/10/");
      }

   }

   public void showJmsMsg(Message m) throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/showJmsMsg/");
      }

      if (traceEnabled) {
         ntrace.doTrace("    Message ID:" + m.getJMSMessageID());
         ntrace.doTrace(" Delivery Time:" + new Date(m.getJMSTimestamp()));
         ntrace.doTrace("            To:" + m.getJMSDestination());
         if (m.getJMSExpiration() > 0L) {
            ntrace.doTrace("       Expires:" + new Date(m.getJMSExpiration()));
         } else {
            ntrace.doTrace("       Expires:Never");
         }

         ntrace.doTrace("      Priority:" + m.getJMSPriority());
         ntrace.doTrace("          Mode:" + (m.getJMSDeliveryMode() == 2 ? "PERSISTENT" : "NON_PERSISTENT"));
         ntrace.doTrace("Correlation ID:" + m.getJMSCorrelationID());
         ntrace.doTrace("      Reply to:" + m.getJMSReplyTo());
         ntrace.doTrace("  Message type:" + m.getJMSType());
         if (m instanceof TextMessage) {
            ntrace.doTrace("   TextMessage:" + ((TextMessage)m).getText());
         } else if (m instanceof BytesMessage) {
            ntrace.doTrace("  BytesMessage:");
         } else {
            ntrace.doTrace("  NotSupported:");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/showJmsMsg/10/");
      }

   }

   public void showTuxMsg(DequeueReply m) {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/showTuxMsg/");
      }

      if (traceEnabled) {
         Integer i;
         if ((i = m.getpriority()) != null) {
            ntrace.doTrace("      Priority:" + i);
         } else {
            ntrace.doTrace("      Priority:null");
         }

         ntrace.doTrace("Correlation ID:" + Arrays.toString(m.getcorrid()));
         ntrace.doTrace("     Failure Q:" + m.getfailurequeue());
         ntrace.doTrace("      Reply to:" + m.getreplyqueue());
         String tuxType;
         if (m.getReplyBuffer() != null) {
            tuxType = m.getReplyBuffer().getType();
         } else {
            tuxType = "STRING";
         }

         ntrace.doTrace("   MessageType:" + tuxType);
         if (tuxType.equals("STRING")) {
            TypedString tuxData = (TypedString)m.getReplyBuffer();
            if (tuxData != null) {
               ntrace.doTrace("   TextMessage:" + tuxData.toString());
            } else {
               ntrace.doTrace("   TextMessage:null");
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/showTuxMsg/10/");
      }

   }

   public static void stop(int threadNumber) {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec>stop/t#" + threadNumber);
      }

      String direction = null;
      if (sArgs.direction[threadNumber - 1] == 1) {
         direction = "TuxQ2JmsQ";
      } else if (sArgs.direction[threadNumber - 1] == 2) {
         direction = "JmsQ2TuxQ";
      } else if (sArgs.direction[threadNumber - 1] == 3) {
         direction = "JmsQ2TuxS";
      } else if (sArgs.direction[threadNumber - 1] == 4) {
         direction = "JmsQ2JmsQ";
      } else {
         direction = "BadLogic/10/";
      }

      sArgs.operational[threadNumber - 1] = false;
      if (traceEnabled) {
         ntrace.doTrace("/tBexec>stop/direction:" + direction);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec<stop/t#" + threadNumber + ": shutdown in progress");
      }

   }

   public static void tBcancel() {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[tBcancel/");
      }

      for(int jx = 1; jx <= sArgs.redirect; ++jx) {
         if (traceEnabled) {
            ntrace.doTrace("/tBcancel/t#" + jx + " stoping...");
         }

         stop(jx);
      }

      threadCount = 0;
      if (traceEnabled) {
         ntrace.doTrace("]/tBcancel/10/");
      }

   }

   public TypedString jms2tuxString(Message fromJms) throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jms2tuxString/");
      }

      TypedString tuxMsg = null;
      if (((TextMessage)fromJms).getText() != null) {
         tuxMsg = new TypedString(((TextMessage)fromJms).getText());
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/jms2tuxString/10");
      }

      return tuxMsg;
   }

   public TypedCArray jms2tuxCArray(Message fromJms) throws JMSException {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jms2tuxCArray/");
      }

      TypedCArray tuxMsg = new TypedCArray(100000);
      int i = 0;

      while(true) {
         try {
            tuxMsg.carray[i] = ((BytesMessage)fromJms).readByte();
         } catch (MessageEOFException var6) {
            tuxMsg.setSendSize(i);
            if (traceEnabled) {
               ntrace.doTrace("]/tBexec/jms2tuxCArray/moved " + i + " bytes.");
               ntrace.doTrace("]/tBexec/jms2tuxCArray/10");
            }

            return tuxMsg;
         }

         ++i;
      }
   }

   public TypedFML32 jms2FML32(Message fromJms) throws Exception {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/jms2FML32/");
      }

      XmlFmlCnv omy = new XmlFmlCnv();
      TypedFML32 tuxMsg = null;
      WTCService.getWTCService();
      FldTbl[] fieldtables = WTCService.getFldTbls("fml32");
      String XMLin = ((TextMessage)fromJms).getText();

      try {
         tuxMsg = omy.XMLtoFML32(XMLin, fieldtables);
      } catch (RuntimeException var8) {
         tuxMsg = null;
      }

      if (traceEnabled) {
         if (tuxMsg == null) {
            ntrace.doTrace("]/tBexec/jms2FML32/failed");
         } else {
            ntrace.doTrace("]/tBexec/jms2FML32/10");
         }
      }

      return tuxMsg;
   }

   public TextMessage FML2jms(TypedFML fromTux, TextMessage jmsSendMsg) {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/FML2jms/");
      }

      WTCService.getWTCService();
      fromTux.setFieldTables(WTCService.getFldTbls("fml16"));
      XmlFmlCnv ome = new XmlFmlCnv();
      String XMLout = ome.FMLtoXML(fromTux);
      if (XMLout != null) {
         try {
            jmsSendMsg.setText(XMLout);
         } catch (JMSException var8) {
            jmsSendMsg = null;
         }
      } else {
         jmsSendMsg = null;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/FML2jms/10");
      }

      return jmsSendMsg;
   }

   public TextMessage FML322jms(TypedFML32 fromTux, TextMessage jmsSendMsg) {
      boolean traceEnabled = ntrace.isTraceEnabled(1);
      if (traceEnabled) {
         ntrace.doTrace("[/tBexec/FML322jms/");
      }

      WTCService.getWTCService();
      fromTux.setFieldTables(WTCService.getFldTbls("fml32"));
      XmlFmlCnv ome = new XmlFmlCnv();
      String XMLout = ome.FML32toXML(fromTux);
      if (XMLout != null) {
         try {
            jmsSendMsg.setText(XMLout);
         } catch (JMSException var8) {
            jmsSendMsg = null;
         }
      } else {
         jmsSendMsg = null;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBexec/FML322jms/10");
      }

      return jmsSendMsg;
   }
}
