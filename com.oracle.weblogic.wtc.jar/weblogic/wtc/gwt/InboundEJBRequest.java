package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.internal.TuxedoXid;
import com.bea.core.jatmi.intf.TCTask;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.application.AppClassLoaderManager;
import weblogic.jdbc.common.internal.AffinityContextHelper;
import weblogic.jdbc.common.internal.AffinityContextHelperFactory;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.server.GlobalServiceLocator;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.DmsReflect;
import weblogic.wtc.jatmi.InvokeInfo;
import weblogic.wtc.jatmi.MetaTcb;
import weblogic.wtc.jatmi.MetaTcmHelper;
import weblogic.wtc.jatmi.Objrecv;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.SessionAcallDescriptor;
import weblogic.wtc.jatmi.TGIOPUtil;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TPRequestAsyncReply;
import weblogic.wtc.jatmi.TPServiceInformation;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TdomTranTcb;
import weblogic.wtc.jatmi.TuxedoService;
import weblogic.wtc.jatmi.TuxedoServiceHome;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.UserTcb;
import weblogic.wtc.jatmi.dsession;
import weblogic.wtc.jatmi.rdsession;
import weblogic.wtc.jatmi.tcm;
import weblogic.wtc.jatmi.tfmh;

public class InboundEJBRequest implements TCTask {
   private ServiceParameters myParam;
   private OatmialServices myServices;
   private TDMLocal myLocalDomain;
   private TDMRemote myRemoteDomain;
   private TuxedoXid myXid;
   private SessionAcallDescriptor myConvDesc;
   private XAResource wlsXaResource;
   private String myName;
   private WTCStatisticsRuntimeMBeanImpl myWTCStat;

   InboundEJBRequest(ServiceParameters aParam, TDMLocal localDomain, TDMRemote remoteDomain) {
      this.myParam = aParam;
      this.myServices = WTCService.getOatmialServices();
      this.myLocalDomain = localDomain;
      this.myRemoteDomain = remoteDomain;
      this.myWTCStat = (WTCStatisticsRuntimeMBeanImpl)((WTCStatisticsRuntimeMBeanImpl)WTCService.getWTCService().getWTCStatisticsRuntimeMBean());
      this.myXid = null;
      this.myConvDesc = null;
      this.wlsXaResource = null;
   }

   public int execute() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/InboundEJBRequest/execute/" + Thread.currentThread());
      }

      TDMExport found_export = null;
      TypedBuffer data = null;
      int mytpurcode = 0;
      int myTPException = 0;
      int convid = -1;
      rdsession receivePlace = null;
      TdomTranTcb tdomtran = null;
      Object[] txInfo = null;
      Xid importedXid = null;
      AffinityContextHelper tranaffinityctxhelper = null;
      boolean ret_success = true;
      if (this.myParam == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/InboundEJBRequest/execute/10");
         }

         return 0;
      } else {
         InvokeInfo invokeInfo;
         if ((invokeInfo = this.myParam.get_invokeInfo()) == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/InboundEJBRequest/execute/20");
            }

            return 0;
         } else {
            dsession rplyObj;
            if ((rplyObj = (dsession)this.myParam.get_gwatmi()) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/InboundEJBRequest/execute/30");
               }

               return 0;
            } else {
               Serializable rd;
               if ((rd = invokeInfo.getReqid()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/InboundEJBRequest/execute/40");
                  }

                  return 0;
               } else {
                  tfmh service_tmmsg;
                  if ((service_tmmsg = invokeInfo.getServiceMessage()) == null) {
                     rplyObj.send_failure_return(rd, new TPException(4), convid);
                     if (traceEnabled) {
                        ntrace.doTrace("]/InboundEJBRequest/execute/50");
                     }

                     return 0;
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("*/InboundEJBRequest/execute/going to handle ECID");
                     }

                     if (service_tmmsg.meta == null && traceEnabled) {
                        ntrace.doTrace("*/InboundEJBRequest/execute/got no META_TCM");
                     }

                     MetaTcb meta;
                     if (service_tmmsg.meta != null && (meta = (MetaTcb)service_tmmsg.meta.body) != null) {
                        String ecid = meta.getECID();
                        if (traceEnabled) {
                           ntrace.doTrace("*/InboundEJBRequest/execute/got ECID: " + ecid);
                        }

                        if (ecid != null && ecid.length() != 0) {
                           DmsReflect dms = null;

                           try {
                              dms = DmsReflect.getInstance();
                           } catch (Exception var41) {
                              rplyObj.send_failure_return(rd, new TPException(12), convid);
                              if (traceEnabled) {
                                 ntrace.doTrace("]/InboundEJBRequest/execute/55/cannot get DMS instance for ECID");
                              }

                              return 0;
                           }

                           dms.setECID(ecid);
                        }
                     }

                     TdomTcb tdom;
                     if (service_tmmsg.tdom != null && (tdom = (TdomTcb)service_tmmsg.tdom.body) != null) {
                        if ((convid = tdom.get_convid()) != -1) {
                           this.myConvDesc = new SessionAcallDescriptor(convid, true);
                           receivePlace = rplyObj.get_rcv_place();
                        }

                        String service;
                        if ((service = invokeInfo.getServiceName()) != null && !service.equals("")) {
                           if (this.myWTCStat != null) {
                              this.myWTCStat.updInboundMessageTotalCount(rplyObj, 1L);
                              this.myWTCStat.updInboundNWMessageTotalSize(rplyObj, (long)service_tmmsg.getUserDataSize());
                              this.myWTCStat.updOutstandingNWReqCount(rplyObj, 1L);
                              TDMExport exp = WTCService.getWTCService().getExportedService(service, this.myLocalDomain.getAccessPoint());
                              if (exp != null) {
                                 this.myWTCStat.updInboundMessageTotalCount(exp, 1L);
                                 this.myWTCStat.updInboundNWMessageTotalSize(exp, (long)service_tmmsg.getUserDataSize());
                                 this.myWTCStat.updOutstandingNWReqCount(exp, 1L);
                              }
                           }

                           TPException te;
                           if (service_tmmsg.route != null) {
                              if (traceEnabled) {
                                 ntrace.doTrace("]/InboundEJBRequest/execute/75");
                              }

                              if (service_tmmsg.user == null) {
                                 if (convid == -1) {
                                    rplyObj.send_failure_return(rd, new TPException(12), convid);
                                 } else {
                                    receivePlace.remove_rplyObj(this.myConvDesc);
                                 }

                                 this.endOutstandingReq(rplyObj, service, false);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/80");
                                 }
                              } else {
                                 if (service_tmmsg.tdomtran != null) {
                                    tdomtran = (TdomTranTcb)service_tmmsg.tdomtran.body;
                                    if (tdomtran == null) {
                                       te = new TPException(4, "NULL transaction");
                                       rplyObj.send_failure_return(rd, te, convid);
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("*]/InboundEJBRequest/execute/81");
                                       }

                                       return 0;
                                    }

                                    try {
                                       this.myXid = new TuxedoXid(tdomtran);
                                    } catch (TPException var46) {
                                       rplyObj.send_failure_return(rd, var46, convid);
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("*]/InboundEJBRequest/execute/81.5");
                                       }

                                       return 0;
                                    }

                                    if ((this.wlsXaResource = TCTransactionHelper.getXAResource()) == null) {
                                       rplyObj.send_failure_return(rd, new TPException(14), convid);
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("]/InboundEJBRequest/execute/82");
                                       }

                                       return 0;
                                    }

                                    try {
                                       this.wlsXaResource.start(this.myXid, 0);
                                    } catch (XAException var45) {
                                       rplyObj.send_failure_return(rd, new TPException(14), convid);
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("]/InboundEJBRequest/execute/83/XAException" + var45);
                                       }

                                       return 0;
                                    }

                                    if (traceEnabled) {
                                       ntrace.doTrace("]/InboundEJBRequest/transaction started/");
                                    }

                                    this.myServices.addInboundRdomToXid(this.myXid, this.myRemoteDomain);
                                    txInfo = new Object[]{this.myXid, this.wlsXaResource, this.myRemoteDomain};
                                 }

                                 Objrecv currObjrecv = new Objrecv(service_tmmsg);
                                 MethodParameters currMethodParms = new MethodParameters(this.myParam, currObjrecv, txInfo, rplyObj);

                                 try {
                                    TGIOPUtil.injectMsgIntoRMI(service_tmmsg, currMethodParms);
                                 } catch (IOException var40) {
                                    this.endOutstandingReq(rplyObj, service, false);
                                    throw new RuntimeException(var40);
                                 }

                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/85");
                                 }
                              }

                              this.endOutstandingReq(rplyObj, service, true);
                              return 0;
                           } else if ((found_export = WTCService.getWTCService().getExportedService(service, this.myLocalDomain.getAccessPoint())) == null) {
                              if (convid == -1) {
                                 rplyObj.send_failure_return(rd, new TPException(6), convid);
                              } else {
                                 receivePlace.remove_rplyObj(this.myConvDesc);
                              }

                              this.endOutstandingReq(rplyObj, service, false);
                              if (traceEnabled) {
                                 ntrace.doTrace("]/InboundEJBRequest/execute/90");
                              }

                              return 0;
                           } else {
                              this.myParam.setUser();
                              if (service_tmmsg.tdomtran != null) {
                                 tdomtran = (TdomTranTcb)service_tmmsg.tdomtran.body;
                                 if (tdomtran == null) {
                                    te = new TPException(4, "NULL transaction");
                                    this.myParam.removeUser();
                                    rplyObj.send_failure_return(rd, te, convid);
                                    this.endOutstandingReq(rplyObj, service, false);
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/InboundEJBRequest/execute/150");
                                    }

                                    return 0;
                                 }

                                 tranaffinityctxhelper = AffinityContextHelperFactory.createXAAffinityContextHelper();
                                 if ((rplyObj.getSessionFeatures() & 4096) != 0 && service_tmmsg.meta != null) {
                                    MetaTcb tcb = (MetaTcb)service_tmmsg.meta.body;
                                    if (tcb != null) {
                                       byte[] bqual = null;
                                       String transactionParent;
                                       if ((transactionParent = tdomtran.getNwtranidparent()) != null) {
                                          bqual = transactionParent.getBytes();
                                       }

                                       importedXid = MetaTcmHelper.getImportedXid(tcb, bqual);
                                       if (traceEnabled && importedXid != null) {
                                          ntrace.doTrace("]/InboundEJBRequest/execute/getImportedXid/" + importedXid.toString());
                                       }
                                    }
                                 }

                                 try {
                                    this.myXid = new TuxedoXid(tdomtran);
                                 } catch (TPException var47) {
                                    this.myParam.removeUser();
                                    rplyObj.send_failure_return(rd, var47, convid);
                                    this.endOutstandingReq(rplyObj, service, false);
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/InboundEJBRequest/execute/150.5");
                                    }

                                    return 0;
                                 }

                                 if (importedXid != null) {
                                    this.myXid.setImportedXid(importedXid);
                                 }

                                 if ((this.wlsXaResource = TCTransactionHelper.getXAResource()) == null) {
                                    rplyObj.send_failure_return(rd, new TPException(14), convid);
                                    this.myParam.removeUser();
                                    this.endOutstandingReq(rplyObj, service, false);
                                    if (traceEnabled) {
                                       ntrace.doTrace("]/InboundEJBRequest/execute/160");
                                    }

                                    return 0;
                                 }

                                 try {
                                    if (tdomtran != null) {
                                       int dom_timeout = tdomtran.getTransactionTimeout();
                                       this.wlsXaResource.setTransactionTimeout(dom_timeout);
                                    }

                                    this.wlsXaResource.start(this.myXid, 0);
                                 } catch (XAException var64) {
                                    rplyObj.send_failure_return(rd, new TPException(14), convid);
                                    this.myParam.removeUser();
                                    this.endOutstandingReq(rplyObj, service, false);
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/InboundEJBRequest/execute/180/" + var64);
                                    }

                                    return 0;
                                 }

                                 if (traceEnabled) {
                                    ntrace.doTrace("transaction started");
                                 }

                                 this.myServices.addInboundRdomToXid(this.myXid, this.myRemoteDomain);
                                 if (tranaffinityctxhelper != null) {
                                    if (traceEnabled) {
                                       ntrace.doTrace("]/InboundEJBRequest/execute/getInboundAffinityCtxFromMetaTCM:" + tranaffinityctxhelper.isApplicationContextAvailable());
                                    }

                                    GwtUtil.getInboundAffinityCtxFromMetaTCM(service_tmmsg, tranaffinityctxhelper, true);
                                 }
                              }

                              String jndi_name = found_export.getEJBName();
                              if (jndi_name == null || "".equals(jndi_name)) {
                                 String pojoName;
                                 if ((pojoName = found_export.getTargetClass()) != null) {
                                    TPRequestAsyncReplyImpl aReply = new TPRequestAsyncReplyImpl(this.myParam, this.myRemoteDomain, this.myXid);
                                    if (traceEnabled) {
                                       ntrace.doTrace("/InboundEJBRequest/execute/looking up POJO " + pojoName);
                                    }

                                    ClassLoader cl = this.getClass().getClassLoader();
                                    String targetJarName;
                                    AppClassLoaderManager manager;
                                    if ((targetJarName = found_export.getTargetJar()) != null) {
                                       if (targetJarName.endsWith(".jar")) {
                                          try {
                                             cl = new POJOClassLoader(targetJarName, (ClassLoader)cl);
                                          } catch (IOException var54) {
                                             if (traceEnabled) {
                                                ntrace.doTrace("]/InboundEJBRequest/execute/problem loading jar " + targetJarName + ": " + var54);
                                             }

                                             cl = this.getClass().getClassLoader();
                                          }
                                       } else {
                                          manager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
                                          cl = manager.findModuleLoader(targetJarName, (String)null);
                                          if (traceEnabled) {
                                             ntrace.doTrace("/InboundEJBRequest/execute/using Application Class Loader for " + targetJarName + " " + cl);
                                          }

                                          if (cl == null) {
                                             cl = this.getClass().getClassLoader();
                                          }
                                       }
                                    }

                                    manager = null;
                                    Method m = null;
                                    Class[] formalParams = new Class[]{TPServiceInformation.class, TPRequestAsyncReply.class};

                                    Class c;
                                    try {
                                       c = Class.forName(pojoName, true, (ClassLoader)cl);
                                    } catch (ClassNotFoundException var49) {
                                       this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(6, "Could not find " + pojoName + " : " + var49));
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("]/InboundEJBRequest/execute/190/" + var49);
                                       }

                                       return 0;
                                    }

                                    try {
                                       m = c.getMethod(found_export.getResourceName(), formalParams);
                                    } catch (NoSuchMethodException var48) {
                                       this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(6, "Method " + found_export.getResourceName() + " not found:" + var48));
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("]/InboundEJBRequest/execute/200/" + var48);
                                       }

                                       return 0;
                                    }

                                    long blocktime = this.myLocalDomain.getBlockTime();
                                    if (tdomtran != null) {
                                       blocktime = (long)(tdomtran.getTransactionTimeout() * 1000);
                                    }

                                    InboundTimer task = null;
                                    if (blocktime > 0L) {
                                       Timer timer = this.myServices.getTimeService();
                                       task = new InboundTimer(rplyObj, rd, convid, aReply);
                                       aReply.setTimeoutTask(task);

                                       try {
                                          if (traceEnabled) {
                                             ntrace.doTrace("/InboundEJBRequest/execute/Set up timer: " + blocktime + " milliseconds");
                                          }

                                          timer.schedule(task, blocktime);
                                       } catch (IllegalArgumentException var42) {
                                          this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(12, "Cannot schedule timer for " + pojoName + ": " + var42));
                                          this.endOutstandingReq(rplyObj, service, false);
                                          if (traceEnabled) {
                                             ntrace.doTrace("]/InboundEJBRequest/execute/204/" + var42);
                                          }

                                          return 0;
                                       } catch (IllegalStateException var43) {
                                          this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(12, "Cannot schedule timer for " + pojoName + ": " + var43));
                                          this.endOutstandingReq(rplyObj, service, false);
                                          if (traceEnabled) {
                                             ntrace.doTrace("]/InboundEJBRequest/execute/205/" + var43);
                                          }

                                          return 0;
                                       }
                                    }

                                    try {
                                       Object[] actualParams = new Object[]{invokeInfo, aReply};
                                       m.invoke(c.newInstance(), actualParams);
                                    } catch (InstantiationException var50) {
                                       this.cleanUp(convid, rplyObj, rd, task, new TPException(12, "Exception in POJO service " + pojoName + ": " + var50));
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("]/InboundEJBRequest/execute/210/" + var50);
                                       }

                                       return 0;
                                    } catch (IllegalAccessException var51) {
                                       this.cleanUp(convid, rplyObj, rd, task, new TPException(12, "Exception in POJO service " + pojoName + ": " + var51));
                                       this.endOutstandingReq(rplyObj, service, false);
                                       if (traceEnabled) {
                                          ntrace.doTrace("]/InboundEJBRequest/execute/220/" + var51);
                                       }

                                       return 0;
                                    } catch (InvocationTargetException var52) {
                                       Throwable t = var52.getTargetException();
                                       if (t instanceof TPException) {
                                          this.cleanUp(convid, rplyObj, rd, task, (TPException)t);
                                          this.endOutstandingReq(rplyObj, service, false);
                                          if (traceEnabled) {
                                             ntrace.doTrace("]/InboundEJBRequest/execute/230/" + t);
                                          }
                                       } else {
                                          this.cleanUp(convid, rplyObj, rd, task, new TPException(12, "Exception in POJO service " + pojoName + ": " + (Exception)t));
                                          this.endOutstandingReq(rplyObj, service, false);
                                          if (traceEnabled) {
                                             ntrace.doTrace("]/InboundEJBRequest/execute/240/" + t);
                                          }
                                       }

                                       return 0;
                                    }

                                    if (this.wlsXaResource != null) {
                                       try {
                                          this.wlsXaResource.end(this.myXid, 67108864);
                                       } catch (XAException var53) {
                                          XAException e = var53;
                                          WTCLogger.logErrorXAEnd(var53);
                                          this.myServices.removeInboundRdomFromXid(this.myRemoteDomain, this.myXid);
                                          if (convid == -1) {
                                             if (aReply != null) {
                                                synchronized(aReply) {
                                                   if (!aReply.getCalled()) {
                                                      rplyObj.send_failure_return(rd, new TPException(10, "Exception " + e + " in service: " + invokeInfo.getServiceName()), convid);
                                                      aReply.setCalled(true);
                                                   }
                                                }
                                             }
                                          } else {
                                             receivePlace.remove_rplyObj(this.myConvDesc);
                                          }

                                          this.myParam.removeUser();
                                          this.endOutstandingReq(rplyObj, service, false);
                                          if (traceEnabled) {
                                             ntrace.doTrace("]/InboundEJBRequest/execute/255/" + var53);
                                          }

                                          return 0;
                                       }
                                    }

                                    this.endOutstandingReq(rplyObj, service, true);
                                    if (traceEnabled) {
                                       ntrace.doTrace("/InboundEJBRequest/run/success POJO " + pojoName);
                                    }

                                    return 0;
                                 }

                                 jndi_name = new String("tuxedo.services." + found_export.getResourceName() + "Home");
                              }

                              Reply myRtnObj;
                              try {
                                 if (traceEnabled) {
                                    ntrace.doTrace("/InboundEJBRequest/execute/looking up " + jndi_name);
                                 }

                                 Object home = this.myServices.getNameService().lookup(jndi_name);
                                 TuxedoServiceHome myServiceHome = (TuxedoServiceHome)PortableRemoteObject.narrow(home, TuxedoServiceHome.class);
                                 TuxedoService myService = (TuxedoService)PortableRemoteObject.narrow(myServiceHome.create(), TuxedoService.class);
                                 if (traceEnabled) {
                                    ntrace.doTrace("/InboundEJBRequest/execute/invoking EJB " + jndi_name);
                                 }

                                 myRtnObj = myService.service(invokeInfo);
                                 if (traceEnabled) {
                                    ntrace.doTrace("/InboundEJBRequest/execute/success EJB " + jndi_name);
                                 }

                                 if (myRtnObj != null) {
                                    data = myRtnObj.getReplyBuffer();
                                    mytpurcode = myRtnObj.gettpurcode();
                                 }
                              } catch (NamingException var57) {
                                 this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(6));
                                 this.endOutstandingReq(rplyObj, service, false);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/250");
                                 }

                                 return 0;
                              } catch (CreateException var58) {
                                 this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(6, "Could not create " + jndi_name + ": " + var58));
                                 this.endOutstandingReq(rplyObj, service, false);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/280");
                                 }

                                 return 0;
                              } catch (AccessException var59) {
                                 this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(6, jndi_name + " is not accessible: " + var59));
                                 this.endOutstandingReq(rplyObj, service, false);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/310");
                                 }

                                 return 0;
                              } catch (RemoteException var60) {
                                 this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(6, jndi_name + " is not available: " + var60));
                                 this.endOutstandingReq(rplyObj, service, false);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/340");
                                 }

                                 return 0;
                              } catch (TPReplyException var61) {
                                 if (this.wlsXaResource != null) {
                                    try {
                                       this.wlsXaResource.end(this.myXid, 536870912);
                                       this.myServices.removeInboundRdomFromXid(this.myRemoteDomain, this.myXid);
                                       this.wlsXaResource.rollback(this.myXid);
                                    } catch (XAException var56) {
                                       if (traceEnabled) {
                                          ntrace.doTrace("/InboundEJBRequest/error ending transaction/" + var56);
                                       }
                                    }

                                    this.wlsXaResource = null;
                                 }

                                 if (traceEnabled) {
                                    ntrace.doTrace("/InboundEJBRequest/execute/tpReplyerro " + var61);
                                 }

                                 myTPException = var61.gettperrno();
                                 myRtnObj = var61.getExceptionReply();
                                 if (myRtnObj != null) {
                                    data = myRtnObj.getReplyBuffer();
                                    mytpurcode = myRtnObj.gettpurcode();
                                 }
                              } catch (TPException var62) {
                                 this.cleanUp(convid, rplyObj, rd, (TimerTask)null, var62);
                                 this.endOutstandingReq(rplyObj, service, false);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/390/" + var62);
                                 }

                                 return 0;
                              } catch (Exception var63) {
                                 this.cleanUp(convid, rplyObj, rd, (TimerTask)null, new TPException(12, "Exception in service: " + var63));
                                 this.endOutstandingReq(rplyObj, service, false);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/420/" + var63);
                                 }

                                 return 0;
                              }

                              if (this.wlsXaResource != null) {
                                 try {
                                    this.wlsXaResource.end(this.myXid, 67108864);
                                 } catch (XAException var44) {
                                    WTCLogger.logErrorXAEnd(var44);
                                    this.myServices.removeInboundRdomFromXid(this.myRemoteDomain, this.myXid);
                                    if (convid == -1) {
                                       rplyObj.send_failure_return(rd, new TPException(10, "Exception " + var44 + " in service: " + service), convid);
                                    } else {
                                       receivePlace.remove_rplyObj(this.myConvDesc);
                                    }

                                    this.myParam.removeUser();
                                    this.endOutstandingReq(rplyObj, service, false);
                                    if (traceEnabled) {
                                       ntrace.doTrace("]/InboundEJBRequest/execute/430/" + var44);
                                    }

                                    return 0;
                                 }
                              }

                              if ((tdom.get_flag() & 4) != 0) {
                                 this.endOutstandingReq(rplyObj, service, true);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/TPNOREPLY set");
                                 }

                                 return 0;
                              } else {
                                 tfmh tmmsg;
                                 if (data == null) {
                                    tmmsg = new tfmh(1);
                                 } else {
                                    tcm user = new tcm((short)0, new UserTcb(data));
                                    tmmsg = new tfmh(data.getHintIndex(), user, 1);
                                 }

                                 try {
                                    if (traceEnabled) {
                                       ntrace.doTrace("]/InboundEJBRequest/execute/sending success " + rd);
                                    }

                                    rplyObj.send_success_return(rd, tmmsg, myTPException, mytpurcode, convid);
                                 } catch (TPException var55) {
                                    if (convid == -1) {
                                       rplyObj.send_failure_return(invokeInfo.getReqid(), var55, convid);
                                    } else {
                                       receivePlace.remove_rplyObj(this.myConvDesc);
                                    }

                                    this.myParam.removeUser();
                                    this.endOutstandingReq(rplyObj, service, false);
                                    if (traceEnabled) {
                                       ntrace.doTrace("]/InboundEJBRequest/execute/460/" + var55);
                                    }

                                    return 0;
                                 }

                                 if (convid != -1) {
                                    receivePlace.remove_rplyObj(this.myConvDesc);
                                 }

                                 this.myParam.removeUser();
                                 this.endOutstandingReq(rplyObj, service, true);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/InboundEJBRequest/execute/480");
                                 }

                                 return 0;
                              }
                           }
                        } else {
                           if (convid == -1) {
                              rplyObj.send_failure_return(rd, new TPException(4), convid);
                           } else {
                              receivePlace.remove_rplyObj(this.myConvDesc);
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("]/InboundEJBRequest/execute/70");
                           }

                           return 0;
                        }
                     } else {
                        rplyObj.send_failure_return(rd, new TPException(4), convid);
                        if (traceEnabled) {
                           ntrace.doTrace("]/InboundEJBRequest/execute/60");
                        }

                        return 0;
                     }
                  }
               }
            }
         }
      }
   }

   private void cleanUp(int convid, dsession rplyObj, Serializable rd, TimerTask tt, TPException tpe) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (tt != null) {
         tt.cancel();
      }

      if (this.wlsXaResource != null) {
         try {
            this.wlsXaResource.end(this.myXid, 536870912);
            this.myServices.removeInboundRdomFromXid(this.myRemoteDomain, this.myXid);
            this.wlsXaResource.rollback(this.myXid);
         } catch (XAException var11) {
            if (traceEnabled) {
               ntrace.doTrace("/InboundEJBRequest/cleanUp/error ending transaction/" + var11);
            }
         } finally {
            this.wlsXaResource = null;
         }
      }

      if (convid == -1) {
         rplyObj.send_failure_return(rd, tpe, convid);
      } else {
         rplyObj.get_rcv_place().remove_rplyObj(this.myConvDesc);
      }

      this.myParam.removeUser();
   }

   private void endOutstandingReq(dsession ss, String service, boolean rtn_success) {
      if (this.myWTCStat != null) {
         this.myWTCStat.updOutstandingNWReqCount(ss, -1L);
         TDMExport exp = WTCService.getWTCService().getExportedService(service, this.myLocalDomain.getAccessPoint());
         if (exp != null) {
            this.myWTCStat.updOutstandingNWReqCount(exp, -1L);
            if (rtn_success) {
               this.myWTCStat.updInboundSuccessReqTotalCount(exp, 1L);
            } else {
               this.myWTCStat.updInboundFailReqTotalCount(exp, 1L);
            }
         }
      }

   }

   public void setTaskName(String tname) {
      this.myName = new String("InboundEJBRequest$" + tname);
   }

   public String getTaskName() {
      return this.myName == null ? "InboundEJBRequest$unknown" : this.myName;
   }

   private class InboundTimer extends TimerTask {
      dsession _rplyObj;
      Serializable _rd;
      int _convid = -1;
      TPRequestAsyncReplyImpl _aReply;

      InboundTimer(dsession rplyObj, Serializable rd, int convid, TPRequestAsyncReplyImpl aReply) {
         this._rplyObj = rplyObj;
         this._rd = rd;
         this._convid = convid;
         this._aReply = aReply;
      }

      public void run() {
         boolean traceEnabled = ntrace.isTraceEnabled(2);
         if (traceEnabled) {
            ntrace.doTrace("/InboundEJBRequest$InboundTimer/send timeout");
         }

         if (this._aReply != null && !this._aReply.isDone()) {
            synchronized(this._aReply) {
               if (!this._aReply.getCalled()) {
                  InboundEJBRequest.this.cleanUp(this._convid, this._rplyObj, this._rd, (TimerTask)null, new TPException(13));
                  this._aReply.setCalled(true);
               }
            }
         } else {
            InboundEJBRequest.this.cleanUp(this._convid, this._rplyObj, this._rd, (TimerTask)null, new TPException(13));
         }

      }
   }
}
