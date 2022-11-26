package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.Timer;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.MBeanKeyStoreConfiguration;
import weblogic.socket.ServerSocketMuxer;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.DomainRegistry;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.TuxedoSSLSocketFactory;
import weblogic.wtc.jatmi.atn;

public final class OatmialListener extends Thread {
   private TDMLocalTDomain myLocalDomain;
   private boolean goon = true;
   private WTCService myMommy;
   private Timer myTimeService;
   private TuxXidRply myXidRply;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServerRuntimeMBean runtime;

   public OatmialListener(Timer tsd, TDMLocalTDomain local_tdomain, WTCService mother, TuxXidRply anXidRply) {
      this.myLocalDomain = local_tdomain;
      this.myMommy = mother;
      this.myTimeService = tsd;
      this.myXidRply = anXidRply;
      this.runtime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
   }

   public void shutdown() {
      this.goon = false;
   }

   public void run() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/OatmialListener/run/");
      }

      ServerSocket myServerSocket = null;
      boolean sslEnabled = false;
      String[] ipaddress;
      if ((ipaddress = this.myLocalDomain.get_ipaddress()) != null && ipaddress.length != 0) {
         boolean[] useSDP;
         if ((useSDP = this.myLocalDomain.get_useSDP()) != null && useSDP.length != 0) {
            int[] port = this.myLocalDomain.get_port();
            int count = ipaddress.length;
            InetAddress[] myAddr = new InetAddress[count];
            if (this.myLocalDomain.getMBean().getUseSSL().equals("TwoWay") || this.myLocalDomain.getMBean().getUseSSL().equals("OneWay")) {
               if (traceEnabled) {
                  ntrace.doTrace(this.myLocalDomain.getMBean().getUseSSL() + " SSL turned on for access point " + this.myLocalDomain.getMBean().getAccessPoint());
               }

               sslEnabled = true;
            }

            int j = 0;

            int i;
            for(i = 0; i < count; ++i) {
               try {
                  myAddr[i] = InetAddress.getByName(ipaddress[i]);
               } catch (UnknownHostException var38) {
                  myAddr[i] = null;
                  if (traceEnabled) {
                     ntrace.doTrace("unknown host(" + ipaddress[i] + ") skip it.");
                  }

                  ++j;
               }
            }

            if (j == count) {
               WTCLogger.logWarnNoValidListeningAddress(this.myLocalDomain.getAccessPointId());
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/OatmialListener/run/08");
               }

            } else {
               Integer waitDelay = Integer.getInteger("weblogic.wtc.WaitDelay");
               if (waitDelay == null) {
                  waitDelay = Integer.MAX_VALUE;
               }

               int k = 0;

               while(true) {
                  String principalName = this.runtime.getState();
                  if ("RUNNING".equals(principalName)) {
                     int j = false;

                     for(i = 0; i < count && !j; ++i) {
                        if (myAddr[i] == null) {
                           if (i >= count - 1) {
                              WTCLogger.logWarnNoMoreValidListeningAddress(this.myLocalDomain.getAccessPointId());
                              break;
                           }
                        } else {
                           try {
                              if (useSDP[i] && sslEnabled) {
                                 sslEnabled = false;
                                 WTCLogger.logWarnIgnoreSSLwithSDP(this.myLocalDomain.getAccessPointId());
                              }

                              if (sslEnabled) {
                                 principalName = null;
                                 TuxedoSSLSocketFactory factory;
                                 if (this.myLocalDomain.getMBean().getKeyStoresLocation().equals("WLS Stores")) {
                                    MBeanKeyStoreConfiguration ksConfig = MBeanKeyStoreConfiguration.getInstance();
                                    String keyStore = ksConfig.getKeyStores();
                                    if (traceEnabled) {
                                       ntrace.doTrace("key store info = " + keyStore);
                                    }

                                    String trustKSType = null;
                                    String trustKSLoc = null;
                                    String trustKSPwd = null;
                                    String principalPwd = ksConfig.getCustomIdentityPrivateKeyPassPhrase();
                                    if ((principalName = ksConfig.getCustomIdentityAlias()) == null && (principalName = this.myLocalDomain.getConnPrincipalName()) == null) {
                                       principalName = this.myLocalDomain.getAccessPointId();
                                    }

                                    String idKSType = ksConfig.getCustomIdentityKeyStoreType();
                                    String idKSLoc = ksConfig.getCustomIdentityKeyStoreFileName();
                                    String idKSPwd = ksConfig.getCustomIdentityKeyStorePassPhrase();
                                    if (traceEnabled) {
                                       ntrace.doTrace("idKSType = " + idKSType + ", idKSLoc = " + idKSLoc + ", idKSPwd = " + idKSPwd);
                                    }

                                    if ("CustomIdentityAndCustomTrust".equals(keyStore)) {
                                       trustKSType = ksConfig.getCustomTrustKeyStoreType();
                                       trustKSLoc = ksConfig.getCustomTrustKeyStoreFileName();
                                       trustKSPwd = ksConfig.getCustomTrustKeyStorePassPhrase();
                                       if (traceEnabled) {
                                          ntrace.doTrace("trustKSType = " + trustKSType + ", trustKSLoc = " + trustKSLoc + ", trustKSPwd = " + trustKSPwd);
                                       }
                                    }

                                    factory = new TuxedoSSLSocketFactory(idKSType, idKSLoc, idKSPwd, principalName, principalPwd, trustKSType, trustKSLoc, trustKSPwd);
                                 } else {
                                    if (this.myLocalDomain.getMBean().getPrivateKeyAlias() == null && this.myLocalDomain.getConnPrincipalName() == null) {
                                       principalName = this.myLocalDomain.getAccessPointId();
                                    }

                                    factory = new TuxedoSSLSocketFactory("jks", this.myLocalDomain.getMBean().getIdentityKeyStoreFileName(), this.myLocalDomain.getMBean().getIdentityKeyStorePassPhrase(), this.myLocalDomain.getMBean().getPrivateKeyAlias(), this.myLocalDomain.getMBean().getPrivateKeyPassPhrase(), "jks", this.myLocalDomain.getMBean().getTrustKeyStoreFileName(), this.myLocalDomain.getMBean().getTrustKeyStorePassPhrase());
                                 }

                                 myServerSocket = factory.createServerSocket(port[i], 50, myAddr[i]);
                                 if (this.myLocalDomain.getMBean().getUseSSL().equals("TwoWay")) {
                                    if (traceEnabled) {
                                       ntrace.doTrace("Server set to TwoWay SSL");
                                    }

                                    ((SSLServerSocket)myServerSocket).setNeedClientAuth(true);
                                 } else if (traceEnabled) {
                                    ntrace.doTrace("Server set to OneWay SSL");
                                 }
                              } else if (!useSDP[i]) {
                                 myServerSocket = ServerSocketMuxer.getMuxer().newServerSocket(myAddr[i], port[i], 50);
                              } else {
                                 myServerSocket = ServerSocketMuxer.getMuxer().newSDPServerSocket(myAddr[i], port[i], 50);
                              }

                              j = true;
                           } catch (IOException var37) {
                              if (i < count - 1) {
                                 WTCLogger.logInfoTryNextListeningAddress(this.myLocalDomain.getAccessPointId(), ipaddress[i], port[i]);
                              } else {
                                 WTCLogger.logWarnNoMoreListeningAddressToTry(this.myLocalDomain.getAccessPointId(), ipaddress[i], port[i]);
                              }
                           }
                        }
                     }

                     if (!j) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/OatmialListener/run/20");
                        }

                        return;
                     } else {
                        try {
                           myServerSocket.setSoTimeout(1000);
                        } catch (SocketException var31) {
                           ntrace.doTrace("SocketException: " + var31);
                        }

                        while(this.goon) {
                           WLSInvoke myInvoker = null;
                           gwdsession mySession = null;
                           Socket mySocket = null;
                           TDMRemote found_rdom = null;
                           TDMRemote[] rdoms = null;

                           try {
                              mySocket = myServerSocket.accept();
                              if (sslEnabled) {
                                 ((SSLSocket)mySocket).addHandshakeCompletedListener(new MyListener());
                                 String[] supportedProtocols = new String[]{"TLSv1"};
                                 ((SSLSocket)mySocket).setEnabledProtocols(supportedProtocols);
                                 ((SSLSocket)mySocket).setEnabledCipherSuites(TuxedoSSLSocketFactory.getCiphers(this.myLocalDomain.getMinEncryptBits(), this.myLocalDomain.getMaxEncryptBits()));
                              }

                              myInvoker = new WLSInvoke(this.myLocalDomain);
                              mySession = new gwdsession(this.myTimeService, mySocket, (atn)null, myInvoker, WTCService.getUniqueGwdsessionId(), sslEnabled, this.myXidRply);
                              gwdsession old_session = (gwdsession)DomainRegistry.addDomainSession(mySession);
                              if (old_session == null) {
                                 mySession.set_BlockTime(this.myLocalDomain.getBlockTime());
                                 mySession.set_compression_threshold(this.myLocalDomain.getCmpLimit());
                                 mySession.set_sess_sec(this.myLocalDomain.getSecurity());
                                 mySession.setDesiredName(this.myLocalDomain.getConnPrincipalName());
                                 mySession.set_local_domain_name(this.myLocalDomain.getAccessPointId());
                                 rdoms = this.myLocalDomain.get_remote_domains();
                                 found_rdom = mySession.tpinit(rdoms);
                                 myInvoker.setRemoteDomain(found_rdom);
                                 mySession.setTerminationHandler((TDMRemoteTDomain)found_rdom);
                              }
                           } catch (SocketTimeoutException var33) {
                           } catch (TPException var34) {
                              if (mySession != null) {
                                 mySession._dom_drop();
                              } else {
                                 try {
                                    if (mySocket != null) {
                                       mySocket.close();
                                    }
                                 } catch (IOException var30) {
                                 }
                              }

                              if (traceEnabled) {
                                 ntrace.doTrace("/WTCService/OatmialListener/run/30/" + var34);
                              }
                           } catch (IOException var35) {
                              if (mySession != null) {
                                 mySession._dom_drop();
                              } else {
                                 try {
                                    if (mySocket != null) {
                                       mySocket.close();
                                    }
                                 } catch (IOException var29) {
                                 }
                              }

                              if (traceEnabled) {
                                 ntrace.doTrace("/WTCService/OatmialListener/run/40/" + var35);
                              }
                           } catch (Exception var36) {
                              if (traceEnabled) {
                                 ntrace.doTrace("/WTCService/OatmialListener/run/50/" + var36);
                              }
                           }
                        }

                        if (myServerSocket != null) {
                           try {
                              if (traceEnabled) {
                                 ntrace.doTrace("/WTCService/OatmialListener/close server socket");
                              }

                              myServerSocket.close();
                           } catch (IOException var28) {
                           }
                        }

                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/OatmialListener/run/60/thread end");
                        }

                        return;
                     }
                  }

                  if (k == waitDelay) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCService/OatmialListener/run/WebLogicServer is not in RUNNING mode");
                     }

                     return;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCService/OatmialListener/run/wait WebLogicServer to be RUNNING mode");
                  }

                  try {
                     Thread.sleep(1000L);
                  } catch (InterruptedException var32) {
                  }

                  ++k;
               }
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/OatmialListener/run/15");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/OatmialListener/run/05");
         }

      }
   }

   private class MyListener implements HandshakeCompletedListener {
      private MyListener() {
      }

      public void handshakeCompleted(HandshakeCompletedEvent event) {
         boolean traceEnabled = ntrace.isTraceEnabled(2);
         if (traceEnabled) {
            ntrace.doTrace("/WTCService/OatmialListener/Server handshake done. Cipher used: " + event.getCipherSuite());
         }

      }

      // $FF: synthetic method
      MyListener(Object x1) {
         this();
      }
   }
}
