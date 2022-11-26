package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCTaskHelper;
import java.util.TimerTask;
import weblogic.logging.Loggable;
import weblogic.management.configuration.WTCRemoteTuxDomMBean;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.TPException;

class ScheduledReconnect extends TimerTask {
   private TDMRemoteTDomain rdom;
   private long retryLeft;
   private ConnectingWork conn_work = null;
   private int state;
   private boolean connected = false;
   private static int CONN_ST_INIT = 0;
   private static int CONN_ST_ACTIVE = 1;
   private static int CONN_ST_IDLE = 2;
   private static int CONN_ST_SHUTDOWN = 3;

   ScheduledReconnect(TDMRemoteTDomain tdm, long retries) {
      this.rdom = tdm;
      this.retryLeft = retries;
   }

   public void connectingFailure() {
      synchronized(this) {
         this.conn_work = null;
      }

      --this.retryLeft;
      if (this.retryLeft <= 0L) {
         this.rdom.removeConnectingTaskReference();
         this.cancel();
      }

   }

   public void connectingSuccess() {
      synchronized(this) {
         this.conn_work = null;
         this.connected = true;
      }

      this.rdom.removeConnectingTaskReference();
      this.cancel();
   }

   public void connectingTerminate() {
      synchronized(this) {
         this.conn_work = null;
         this.connected = false;
      }

      this.rdom.removeConnectingTaskReference();
      this.cancel();
   }

   public void run() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/ScheduledReconnect/run/" + this.rdom.getAccessPoint());
      }

      boolean create;
      synchronized(this) {
         create = this.conn_work == null;
      }

      if (create) {
         this.conn_work = new ConnectingWork(this, this.rdom);
         TCTaskHelper.schedule(this.conn_work);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ScheduledReconnect/run/10");
      }

   }

   public static TDMRemoteTDomain create(WTCRemoteTuxDomMBean mb) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/TDMRemoteTDomain/create/");
      }

      WTCService myWTC = WTCService.getWTCService();
      String accpnt = mb.getAccessPoint();
      if (accpnt == null) {
         Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("AccessPoint", mb.getName());
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/TDMRemoteTDomain/create/10/no AP");
         }

         throw new TPException(4, lua.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("AccessPoint: " + accpnt);
         }

         String accpntId = mb.getAccessPointId();
         if (accpntId == null) {
            Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("AccessPointId", mb.getName());
            lua.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/TDMRemoteTDomain/create/20/no APId");
            }

            throw new TPException(4, lua.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("AccessPointId:" + accpntId);
            }

            String lapnm = mb.getLocalAccessPoint();
            if (lapnm == null) {
               Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("LocalAccessPoint", mb.getName());
               lua.log();
               if (traceEnabled) {
                  ntrace.doTrace("*]/TDMRemoteTDomain/create/30/no LAP");
               }

               throw new TPException(4, lua.getMessage());
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("LocalAccessPoint:" + lapnm);
               }

               String nwaddr = mb.getNWAddr();
               if (nwaddr == null) {
                  Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("NWAddr", mb.getName());
                  lua.log();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TDMRemoteTDomain/create/40/no NWAddr");
                  }

                  throw new TPException(4, lua.getMessage());
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("NWAddr:" + nwaddr);
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("create rtd from " + accpnt);
                  }

                  TDMRemoteTDomain rtd;
                  Loggable lie;
                  try {
                     rtd = new TDMRemoteTDomain(accpnt, myWTC.getUnknownTxidRply(), WTCService.getTimerService());
                  } catch (Exception var15) {
                     lie = WTCLogger.logUEconstructTDMRemoteTDLoggable(var15.getMessage());
                     lie.log();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TDMRemoteTDomain/create/50/create failed");
                     }

                     throw new TPException(4, lie.getMessage());
                  }

                  TDMLocalTDomain lap = myWTC.getLocalDomain(lapnm);
                  if (null == lap) {
                     lie = WTCLogger.logErrorBadTDMRemoteLTDLoggable(lapnm);
                     lie.log();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TDMRemoteTDomain/create/60/no LDOM");
                     }

                     throw new TPException(4, lie.getMessage());
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("valid LocalAccessPoint");
                     }

                     rtd.setLocalAccessPoint(lapnm);
                     rtd.setAccessPointId(accpntId);
                     rtd.setAclPolicy(mb.getAclPolicy());
                     rtd.setCredentialPolicy(mb.getCredentialPolicy());
                     rtd.setTpUsrFile(mb.getTpUsrFile());

                     Loggable lia;
                     try {
                        rtd.setNWAddr(nwaddr);
                     } catch (TPException var16) {
                        lia = WTCLogger.logInvalidMBeanAttrLoggable("NWAddr", mb.getName());
                        lia.log();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/TDMRemoteTDomain/create/70/" + var16.getMessage());
                        }

                        throw new TPException(4, lia.getMessage());
                     }

                     rtd.setFederationURL(mb.getFederationURL());
                     rtd.setFederationName(mb.getFederationName());

                     try {
                        rtd.setCmpLimit(mb.getCmpLimit());
                     } catch (TPException var14) {
                        lia = WTCLogger.logInvalidMBeanAttrLoggable("CmpLimit", mb.getName());
                        lia.log();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/TDMRemoteTDomain/create/80/" + var14.getMessage());
                        }

                        throw new TPException(4, lia.getMessage());
                     }

                     String tmps = mb.getMinEncryptBits();
                     if (tmps != null) {
                        rtd.setMinEncryptBits(Integer.parseInt(tmps, 10));
                     }

                     tmps = mb.getMaxEncryptBits();
                     if (tmps != null) {
                        rtd.setMaxEncryptBits(Integer.parseInt(tmps, 10));
                     }

                     rtd.setConnectionPolicy(mb.getConnectionPolicy());
                     rtd.setRetryInterval(mb.getRetryInterval());
                     rtd.setMaxRetries(mb.getMaxRetries());
                     rtd.setKeepAlive(mb.getKeepAlive());
                     rtd.setKeepAliveWait(mb.getKeepAliveWait());
                     String sel = mb.getAppKey();
                     if (sel == null && mb.getTpUsrFile() != null) {
                        sel = new String("TpUsrFile");
                        if (traceEnabled) {
                           ntrace.doTrace("Use dflt AppKey Generator");
                        }
                     }

                     rtd.setAppKey(sel);
                     rtd.setAllowAnonymous(mb.getAllowAnonymous());
                     rtd.setDefaultAppKey(mb.getDefaultAppKey());
                     if (sel != null) {
                        if (sel.equals("LDAP")) {
                           rtd.setTuxedoUidKw(mb.getTuxedoUidKw());
                           rtd.setTuxedoGidKw(mb.getTuxedoGidKw());
                           if (traceEnabled) {
                              ntrace.doTrace("LDAP, allow=" + mb.getAllowAnonymous() + ",Dflt AppKey=" + mb.getDefaultAppKey() + ",UID KW=" + mb.getTuxedoUidKw() + ", GID KW=" + mb.getTuxedoGidKw());
                           }
                        } else if (sel.equals("Custom")) {
                           String cls = mb.getCustomAppKeyClass();
                           String clsp = mb.getCustomAppKeyClassParam();
                           if (cls == null) {
                              Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("CustomAppKeyClass", mb.getName());
                              lua.log();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/TDMRemoteTDomain/create/90/no custom class defined");
                              }

                              throw new TPException(4, lua.getMessage());
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("Custom, allow=" + mb.getAllowAnonymous() + ",Dflt AppKey=" + mb.getDefaultAppKey() + ",Class=" + cls + ", Parm =" + clsp);
                           }

                           rtd.setCustomAppKeyClass(cls);
                           rtd.setCustomAppKeyClassParam(clsp);
                        } else {
                           if (!sel.equals("TpUsrFile")) {
                              Loggable lia = WTCLogger.logInvalidMBeanAttrLoggable("AppKey", mb.getName());
                              lia.log();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/TDMRemoteTDomain/create/100/unsupported appkey");
                              }

                              throw new TPException(4, lia.getMessage());
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("TpUsrFile, allow=" + mb.getAllowAnonymous() + ",Dflt AppKey=" + mb.getDefaultAppKey() + ",File=" + mb.getTpUsrFile());
                           }
                        }
                     }

                     rtd.setMBean(mb);
                     if (traceEnabled) {
                        ntrace.doTrace("]/TDMRemoteTDomain/create/140/success");
                     }

                     return rtd;
                  }
               }
            }
         }
      }
   }
}
