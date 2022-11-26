package weblogic.wtc.tbridge;

import com.bea.core.jatmi.common.ntrace;
import java.util.StringTokenizer;
import weblogic.logging.Loggable;
import weblogic.management.configuration.WTCServerMBean;
import weblogic.management.configuration.WTCtBridgeGlobalMBean;
import weblogic.management.configuration.WTCtBridgeRedirectMBean;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.TPException;

public final class tBparseArgs {
   public static final tBstartArgs sArgs = new tBstartArgs();
   private String tempstr;
   private tBpvalueMap pv = new tBpvalueMap();
   private int state = 0;
   public static final int TUX2JMS = 1;
   public static final int JMS2TUX = 2;
   private static final int TUXQ2JMSQ = 1;
   private static final int JMSQ2TUXQ = 2;
   private static final int JMSQ2TUXS = 3;
   private static final int JMSQ2JMSQ = 4;

   public tBstartArgs parseMBeans(WTCServerMBean srvrmb) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(17);
      if (traceEnabled) {
         ntrace.doTrace("[/tBparseArgs/parseMBeans");
      }

      WTCtBridgeRedirectMBean[] redirmbeans = srvrmb.gettBridgeRedirects();
      int redircnt = redirmbeans.length;
      if (redircnt == 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]tBparseArgs/parseMBeans/10/no tbridge redirect info defined");
         }

         Loggable lnrd = WTCLogger.logtBNOredirectsLoggable();
         lnrd.log();
         throw new TPException(4, lnrd.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("/tBparseArgs/parseMBeans/redirect=" + redircnt);
         }

         sArgs.redirect = redircnt;

         Loggable le;
         for(int rdi = 0; rdi < redircnt; ++rdi) {
            if (!this.setupTBRedirInfo(redirmbeans[rdi], rdi)) {
               le = WTCLogger.logErrorExecMBeanDefLoggable(redirmbeans[rdi].getName());
               le.log();
               throw new TPException(4, le.getMessage());
            }

            if (traceEnabled) {
               ntrace.doTrace("/tBparseArgs/parseMBeans/20/setup tbridge redirect:" + redirmbeans[rdi].getName());
            }
         }

         WTCtBridgeGlobalMBean tbgmb = srvrmb.getWTCtBridgeGlobal();
         if (tbgmb == null) {
            le = WTCLogger.logUndefinedMBeanLoggable("tBridgeGlobal");
            le.log();
            throw new TPException(4, le.getMessage());
         } else if (!this.setupTBGlobalInfo(tbgmb)) {
            le = WTCLogger.logErrorExecMBeanDefLoggable(tbgmb.getName());
            le.log();
            throw new TPException(4, le.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("/tBparseArgs/parseMBeans/30/setup tbridge global:" + tbgmb.getName());
            }

            for(int rdi = 0; rdi < redircnt; ++rdi) {
               sArgs.operational[rdi] = true;
            }

            sArgs.print_tBstartArgs();
            if (traceEnabled) {
               ntrace.doTrace("]tBparseArgs/parseMBeans/40/sArgs");
            }

            return sArgs;
         }
      }
   }

   public tBstartArgs parseGlobal(WTCtBridgeGlobalMBean tbgmb) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(17);
      if (traceEnabled) {
         ntrace.doTrace("[/tBparseArgs/parseGlobal(tBridgeGlobal)");
      }

      Loggable le;
      if (tbgmb != null) {
         if (!this.setupTBGlobalInfo(tbgmb)) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tBparseArgs/parseGlobal(10) failed to setup tbridge global: " + tbgmb.getName());
            }

            le = WTCLogger.logErrorExecMBeanDefLoggable(tbgmb.getName());
            le.log();
            throw new TPException(4, le.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("setup tbridge global:" + tbgmb.getName());
            }

            if (traceEnabled) {
               ntrace.doTrace("]/tBparseArgs/parseGlobal(30) returns sArgs = " + sArgs);
            }

            return sArgs;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/tBparseArgs/parseGlobal(20) null WTCtBridgeGlobalMBean");
         }

         le = WTCLogger.logUndefinedMBeanLoggable("tBridgeGlobal");
         le.log();
         throw new TPException(4, le.getMessage());
      }
   }

   public tBstartArgs parseRedirect(WTCtBridgeRedirectMBean redir) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(17);
      if (traceEnabled) {
         ntrace.doTrace("[/tBparseArgs/parseRedirect(WTCtBridgeRedirect=" + redir + ")");
      }

      if (redir == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tBparseArgs/parseRedirect(10) null WTCtBridgeRedirectMBean");
         }

         Loggable lnrd = WTCLogger.logtBNOredirectsLoggable();
         lnrd.log();
         throw new TPException(4, lnrd.getMessage());
      } else {
         int rdi;
         if (sArgs.redirect == -1) {
            rdi = 0;
            sArgs.redirect = 1;
         } else {
            rdi = sArgs.redirect++;
         }

         if (!this.setupTBRedirInfo(redir, rdi)) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tBparseArgs/parseRedirect(20) failed to parse WTCtBridgeRedirectMBean");
            }

            Loggable le = WTCLogger.logErrorExecMBeanDefLoggable(redir.getName());
            le.log();
            throw new TPException(4, le.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("setup tbridge redirect:" + redir.getName());
            }

            sArgs.operational[rdi] = true;
            if (traceEnabled) {
               ntrace.doTrace("]/tBparseArgs/parseRedirect(30) parse WTCtBridgeRedirectMBean successful");
            }

            return sArgs;
         }
      }
   }

   public tBstartArgs getParsedMBeans() {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(17);
      if (traceEnabled) {
         ntrace.doTrace("[/tBparseArgs/getParsedMBean()");
      }

      sArgs.print_tBstartArgs();
      if (traceEnabled) {
         ntrace.doTrace("]/tBparseArgs/getParsedMBean(10) returns sArgs = " + sArgs);
      }

      return sArgs;
   }

   private boolean setupTBRedirInfo(WTCtBridgeRedirectMBean mb, int rindex) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(17);
      if (traceEnabled) {
         ntrace.doTrace("[/tBparseArgs/setupTBRedirInfo/rindex " + rindex);
      }

      String tmpstr = mb.getDirection();
      if (tmpstr.equals("JmsQ2TuxQ")) {
         sArgs.direction[rindex] = 2;
      } else if (tmpstr.equals("TuxQ2JmsQ")) {
         sArgs.direction[rindex] = 1;
      } else if (tmpstr.equals("JmsQ2TuxS")) {
         sArgs.direction[rindex] = 3;
      } else if (tmpstr.equals("JmsQ2JmsQ")) {
         sArgs.direction[rindex] = 4;
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/Direction=" + tmpstr);
      }

      String tmpsa = mb.getSourceAccessPoint();
      String tmpta = mb.getTargetAccessPoint();
      if (tmpsa != null && tmpsa.length() != 0 || tmpta != null && tmpta.length() != 0) {
         if (tmpsa != null && tmpsa.length() != 0) {
            if (tmpta != null && tmpta.length() != 0) {
               if (sArgs.direction[rindex] == 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/20/WARNING: source and target Access Point both set. Using source AccessPoint");
                  }

                  sArgs.AccessPoint[rindex] = tmpsa;
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/25/WARNING: source and target Access Point both set. Using target AccessPoint");
                  }

                  sArgs.AccessPoint[rindex] = tmpta;
               }
            } else {
               sArgs.AccessPoint[rindex] = tmpsa;
            }
         } else {
            sArgs.AccessPoint[rindex] = tmpta;
         }
      } else if (sArgs.direction[rindex] != 4) {
         if (traceEnabled) {
            ntrace.doTrace("]/tBparseArgs/setupTBRedirInfo/10/false Source or target AccessPoint must be set.");
         }

         return false;
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/AccessPoint=" + sArgs.AccessPoint[rindex]);
      }

      sArgs.sourceName[rindex] = mb.getSourceName();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/SourceName=" + sArgs.sourceName[rindex]);
      }

      sArgs.targetName[rindex] = mb.getTargetName();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/TargetName=" + sArgs.targetName[rindex]);
      }

      sArgs.sourceQspace[rindex] = mb.getSourceQspace();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/SourceQspace=" + sArgs.sourceQspace[rindex]);
      }

      sArgs.targetQspace[rindex] = mb.getTargetQspace();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/TargetQspace=" + sArgs.targetQspace[rindex]);
      }

      sArgs.translateFML[rindex] = mb.getTranslateFML();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/TranslateFML=" + sArgs.translateFML[rindex]);
      }

      sArgs.metadataFile[rindex] = mb.getMetaDataFile();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/MetaDataFile=" + sArgs.metadataFile[rindex]);
      }

      sArgs.replyQ[rindex] = mb.getReplyQ();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBRedirInfo/ReplyQ=" + sArgs.replyQ[rindex]);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBparseArgs/setupTBRedirInfo/35/true");
      }

      return true;
   }

   private boolean setupTBGlobalInfo(WTCtBridgeGlobalMBean mb) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(17);
      if (traceEnabled) {
         ntrace.doTrace("[/tBparseArgs/setupTBGlobalInfo/");
      }

      sArgs.transactional = mb.getTransactional();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/Transactional=" + sArgs.transactional);
      }

      sArgs.timeout = mb.getTimeout();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/Timeout=" + sArgs.timeout);
      }

      sArgs.retries = mb.getRetries();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/Retries=" + sArgs.retries);
      }

      sArgs.retryDelay = mb.getRetryDelay();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/RetryDelay=" + sArgs.retryDelay);
      }

      sArgs.wlsErrorDestination = mb.getWlsErrorDestination();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/WlsErrorDestination=" + sArgs.wlsErrorDestination);
      }

      sArgs.tuxErrorQueue = mb.getTuxErrorQueue();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/TuxErrorQueue=" + sArgs.tuxErrorQueue);
      }

      sArgs.deliveryModeOverride = mb.getDeliveryModeOverride();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/DeliveryModeOverride=" + sArgs.deliveryModeOverride);
      }

      sArgs.defaultReplyDeliveryMode = mb.getDefaultReplyDeliveryMode();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/DefaultReplyDeliveryMode=" + sArgs.defaultReplyDeliveryMode);
      }

      sArgs.userID = mb.getUserId();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/UserId=" + sArgs.userID);
      }

      sArgs.allowNonStandardTypes = mb.getAllowNonStandardTypes();
      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/AllowNonStandardTypes=" + sArgs.allowNonStandardTypes);
      }

      sArgs.jndiFactory = mb.getJndiFactory();
      if (sArgs.jndiFactory == null || sArgs.jndiFactory.length() == 0) {
         sArgs.jndiFactory = "weblogic.jndi.WLInitialContextFactory";
         if (traceEnabled) {
            ntrace.doTrace("jndiFactory set to default value");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/JndiFactory=" + sArgs.jndiFactory);
      }

      sArgs.jmsFactory = mb.getJmsFactory();
      if (sArgs.jmsFactory == null || sArgs.jmsFactory.length() == 0) {
         if (sArgs.transactional.equals("Yes")) {
            sArgs.jmsFactory = "weblogic.jms.XAConnectionFactory";
            if (traceEnabled) {
               ntrace.doTrace("jmsFactory set to default transactional value");
            }
         } else {
            sArgs.jmsFactory = "weblogic.jms.ConnectionFactory";
            if (traceEnabled) {
               ntrace.doTrace("jmsFactory set to default non transactional value");
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/JmsFactory=" + sArgs.jmsFactory);
      }

      sArgs.tuxFactory = mb.getTuxFactory();
      if (sArgs.tuxFactory == null || sArgs.tuxFactory.length() == 0) {
         sArgs.tuxFactory = "tuxedo.services.TuxedoConnection";
         if (traceEnabled) {
            ntrace.doTrace("tuxFactory set to default value");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/TuxFactory=" + sArgs.tuxFactory);
      }

      boolean retval = true;
      boolean goodset = true;
      tBsetPriority j2tsp = new tBsetPriority();
      tBsetPriority t2jsp = new tBsetPriority();
      int ci = true;

      for(int pway = 1; pway <= 2; ++pway) {
         String pmapstr;
         if (pway == 2) {
            pmapstr = mb.getJmsToTuxPriorityMap();
         } else {
            pmapstr = mb.getTuxToJmsPriorityMap();
         }

         if (pmapstr != null && pmapstr.length() != 0) {
            if (traceEnabled) {
               ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/Pway=" + pway + " PriorityMap=" + pmapstr);
            }

            ++sArgs.priorityMapping;
            this.pv.Pway = pway;
            String pvs;
            int ci;
            if (pmapstr.indexOf(124) == -1) {
               ci = pmapstr.indexOf(58);
               if (ci == -1) {
                  if (traceEnabled) {
                     ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/10/Error value:range pair missing separator=>" + pmapstr);
                  }

                  retval = false;
               } else {
                  pvs = pmapstr.substring(0, ci);
                  if (pvs.indexOf(45) != -1) {
                     if (traceEnabled) {
                        ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/15/Error range defined instead of value in value:range pair=>" + pvs);
                     }

                     retval = false;
                  } else {
                     this.pv.Pvalue = Integer.parseInt(pvs, 10);
                     this.pv.Prange = pmapstr.substring(ci + 1, pmapstr.length());
                     if (pway == 2) {
                        goodset = j2tsp.setPVmap(this.pv, sArgs);
                     } else {
                        goodset = t2jsp.setPVmap(this.pv, sArgs);
                     }

                     if (!goodset && traceEnabled) {
                        ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/20/WARNING:problem setting priority map");
                     }
                  }
               }
            } else {
               StringTokenizer st = new StringTokenizer(pmapstr, "|");

               while(st.hasMoreTokens()) {
                  String tmpvr = st.nextToken();
                  ci = tmpvr.indexOf(58);
                  if (ci == -1) {
                     if (traceEnabled) {
                        ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/20/Error value:range pair missing separator=>" + tmpvr);
                     }

                     retval = false;
                  } else {
                     pvs = tmpvr.substring(0, ci);
                     if (pvs.indexOf(45) != -1) {
                        if (traceEnabled) {
                           ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/25/Error range defined instead of value in value:range pair=>" + pvs);
                        }

                        retval = false;
                     } else {
                        this.pv.Pvalue = Integer.parseInt(pvs, 10);
                        this.pv.Prange = tmpvr.substring(ci + 1, tmpvr.length());
                        if (pway == 2) {
                           goodset = j2tsp.setPVmap(this.pv, sArgs);
                        } else {
                           goodset = t2jsp.setPVmap(this.pv, sArgs);
                        }

                        if (!goodset && traceEnabled) {
                           ntrace.doTrace("/tBparseArgs/setupTBGlobalInfo/45/WARNING:problem setting priority map");
                        }
                     }
                  }
               }
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tBparseArgs/setupTBGlobalInfo/50/" + retval);
      }

      return retval;
   }
}
