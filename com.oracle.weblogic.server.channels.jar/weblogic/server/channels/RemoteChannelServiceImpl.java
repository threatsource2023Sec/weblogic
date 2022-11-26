package weblogic.server.channels;

import java.lang.annotation.Annotation;
import java.net.UnknownHostException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.ConfigurationException;
import javax.naming.NamingException;
import javax.naming.ServiceUnavailableException;
import org.jvnet.hk2.annotations.Service;
import weblogic.health.HealthMonitorService;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.kernel.KernelStatus;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ChannelList;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManager;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.protocol.configuration.ChannelHelperService;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.rmi.extensions.ConnectMonitor;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.extensions.ServerDisconnectEvent;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.NotYetInitializedException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.RemoteLifeCycleOperations;
import weblogic.server.RemoteLifeCycleOperationsImpl;
import weblogic.server.ServerLogger;
import weblogic.server.channels.api.ChannelRegistrationService;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

@Service
public final class RemoteChannelServiceImpl implements RemoteChannelService, ConnectMonitor, ChannelRegistrationService {
   private static HashSet connectListeners = new HashSet();
   private static RemoteChannelService domainGateway;
   private static boolean shutdown = false;
   private static boolean jobSubmitted = false;
   private static volatile int registeringNewServer = 0;
   private static Map connectedServers = new HashMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int MAX_FATAL_COUNT = 100;
   private static volatile int fatalCount;
   private static final DebugCategory debug = Debug.getCategory("weblogic.server.channels");

   private RemoteChannelServiceImpl() {
   }

   public static synchronized RemoteChannelService getDomainGateway() {
      return domainGateway;
   }

   /** @deprecated */
   @Deprecated
   public static ConnectMonitor getInstance() {
      if (!KernelStatus.isServer()) {
         throw new UnsupportedOperationException("ConnectMonitor not supported in a client");
      } else {
         return RemoteChannelServiceImpl.ConnectSingleton.SINGLETON;
      }
   }

   /** @deprecated */
   @Deprecated
   public static synchronized void unregister() {
      if (debug.isEnabled()) {
         Debug.say("registerForever turns on shutdown flag");
      }

      shutdown = true;
   }

   static synchronized boolean isShutdown() {
      return shutdown;
   }

   static synchronized boolean shouldDoShutdown() {
      if (isShutdown()) {
         if (debug.isEnabled()) {
            Debug.say("disabling RECONNECT");
         }

         jobSubmitted = false;
         return true;
      } else {
         return false;
      }
   }

   static synchronized void fatalError() {
      if (debug.isEnabled()) {
         Debug.say("faced fatal error. status should be reset.");
      }

      shutdown = true;
      jobSubmitted = false;
   }

   static synchronized boolean shouldDoInitialze() {
      if (shutdown) {
         if (debug.isEnabled()) {
            Debug.say("shutdown flag is reverted on registerForever.");
         }

         shutdown = false;
      }

      if (jobSubmitted) {
         if (debug.isEnabled()) {
            Debug.say("disconnect lister or timer are already submitted. registerForever body process is skipped.");
         }

         return false;
      } else {
         jobSubmitted = true;
         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   public static void registerForever(ServerEnvironment env) throws NamingException {
      List envList = new ArrayList();
      envList.add(env);
      registerForever((List)envList);
   }

   /** @deprecated */
   @Deprecated
   public static void registerForever(final List envList) throws NamingException {
      if (shouldDoInitialze()) {
         registerInternal(envList, new DisconnectListener() {
            public void onDisconnect(DisconnectEvent de) {
               if (de instanceof ServerDisconnectEvent) {
                  ServerLogger.logServerDisconnect(((ServerDisconnectEvent)de).getServerName());
                  if (RemoteChannelServiceImpl.shouldDoShutdown()) {
                     return;
                  }
               }

               if (RemoteChannelServiceImpl.debug.isEnabled()) {
                  Debug.say("scheduled RECONNECT with DisconnectEvent:" + de);
               }

               try {
                  TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new TimerListenerImpl(envList, this), (long)(ManagementService.getRuntimeAccess(RemoteChannelServiceImpl.kernelId).getServer().getAdminReconnectIntervalSeconds() * 1000));
               } catch (IllegalStateException var3) {
                  RemoteChannelServiceImpl.fatalError();
                  ServerLogger.logScheduleReconnectFailed();
               }

            }
         });
      }
   }

   private static synchronized void registerInternal(List envList, DisconnectListener listener) throws NamingException {
      Throwable fatal = null;
      Iterator var3 = envList.iterator();

      while(var3.hasNext()) {
         final ServerEnvironment env = (ServerEnvironment)var3.next();

         try {
            final RemoteChannelService remote = (RemoteChannelService)PortableRemoteObject.narrow(env.getInitialReference(RemoteChannelServiceImpl.class), RemoteChannelService.class);
            if (remote == RemoteChannelServiceImpl.ConnectSingleton.SINGLETON) {
               throw new ConfigurationException("Cannot register for disconnect events on local server");
            }

            String state = null;

            try {
               state = (String)SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
                  public String run() throws RemoteException, NamingException {
                     return RemoteChannelServiceImpl.getServerState(env);
                  }
               });
            } catch (PrivilegedActionException var10) {
               if (var10.getCause() instanceof RemoteException) {
                  throw (RemoteException)var10.getCause();
               }

               if (var10.getCause() instanceof NamingException) {
                  throw (NamingException)var10.getCause();
               }

               throw new RuntimeException(var10);
            }

            if (!"STANDBY".equals(state) && !"STARTING".equals(state)) {
               String remoteServerName = null;

               try {
                  remoteServerName = (String)SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
                     public String run() throws RemoteException {
                        return remote.registerServer(LocalServerIdentity.getIdentity().getServerName(), ServerChannelManager.getLocalChannelsForExport());
                     }
                  });
               } catch (PrivilegedActionException var11) {
                  if (var11.getCause() instanceof RemoteException) {
                     throw (RemoteException)var11.getCause();
                  }

                  throw new RuntimeException(var11);
               }

               RemoteChannelServiceImpl.ConnectSingleton.SINGLETON.invokeListeners(remoteServerName, (ChannelList)null);
               ServerLogger.logServerConnect(remoteServerName);
               domainGateway = remote;

               try {
                  DisconnectMonitorListImpl.getDisconnectMonitor().addDisconnectListener(remote, listener);
               } catch (Exception var9) {
                  throw new AssertionError(var9);
               }

               if (debug.isEnabled()) {
                  Debug.say("RECONNECT successful");
               }

               return;
            }

            throw new RemoteException("Admin Port may be listening, But internal apps may not be activated yet");
         } catch (ConfigurationException var12) {
            if (debug.isEnabled()) {
               Debug.say("RECONNECT failed fatally : " + var12);
            }

            fatal = var12;
         } catch (ServiceUnavailableException var13) {
            if (debug.isEnabled()) {
               Debug.say("RECONNECT failed fatally : " + var13);
            }

            fatal = var13;
         } catch (NamingException var14) {
            if (debug.isEnabled()) {
               Debug.say("RECONNECT failed : " + var14);
            }
         } catch (RemoteException var15) {
            if (debug.isEnabled()) {
               Debug.say("RECONNECT failed : " + var15);
            }
         } catch (NotYetInitializedException var16) {
            if (debug.isEnabled()) {
               Debug.say("RECONNECT failed : " + var16);
            }
         } catch (RemoteRuntimeException var17) {
            if (debug.isEnabled()) {
               Debug.say("RECONNECT failed : " + var17);
            }
         } catch (Throwable var18) {
            if (debug.isEnabled()) {
               Debug.say("RECONNECT failed fatally : " + var18);
            }

            fatal = var18;
         }
      }

      if (fatal != null) {
         Throwable cause = ((Throwable)fatal).getCause();
         if (!(cause instanceof UnknownHostException) && !(cause instanceof NoSuchObjectException) && !(cause instanceof java.rmi.UnknownHostException) && !(cause instanceof RequestTimeoutException)) {
            ServerLogger.logRemoteChannelService("RemoteChannelServiceImpl.registerInternal() failed.", (Throwable)fatal);
            ++fatalCount;
            if (fatalCount > 100) {
               HealthMonitorService.subsystemFailed("RemoteChannelServiceImpl", "too many fatal errors happened on RemoteChannelServiceImpl.");
               return;
            }
         }
      }

      listener.onDisconnect((DisconnectEvent)null);
   }

   public static void retrieveRemoteChannels(ServerIdentity id) throws RemoteException {
      if (getDomainGateway() != null && id.getDomainName().equals(LocalServerIdentity.getIdentity().getDomainName())) {
         getDomainGateway().getChannelList(id);
      }

   }

   public ServerChannel getServerChannel(String protocol) {
      Protocol p = ProtocolManager.getProtocolByName(protocol);
      return ChannelService.findLocalServerChannel(p);
   }

   public String getDefaultURL() {
      return ChannelHelper.getDefaultURL();
   }

   public String getAdministrationURL() {
      return ChannelHelper.getLocalAdministrationURL();
   }

   private ChannelHelperService getChannelHelperService() {
      return (ChannelHelperService)GlobalServiceLocator.getServiceLocator().getService(ChannelHelperService.class, new Annotation[0]);
   }

   public String getURL(String protocol) {
      Protocol p = ProtocolManager.getProtocolByName(protocol);
      return this.getChannelHelperService().getURL(p);
   }

   public ServerIdentity getServerIdentity() {
      return LocalServerIdentity.getIdentity();
   }

   public synchronized String registerServer(String serverName, ChannelList channels) {
      ++registeringNewServer;
      ServerLogger.logServerConnect(serverName);
      this.invokeListeners(serverName, channels);
      connectedServers.put(serverName, channels);
      return this.getServerIdentity().getServerName();
   }

   public void updateServer(String serverName, ChannelList channels) {
      if (debug.isEnabled()) {
         Debug.say("updateServer(" + serverName + ")");
      }

   }

   private void invokeListeners(final String remoteServerName, final ChannelList channels) {
      WorkManagerFactory.getInstance().getSystem().schedule(new WorkAdapter() {
         private final long time = System.currentTimeMillis();
         private final String serverName = channels != null ? channels.getIdentity().getServerName() : remoteServerName;

         public void run() {
            try {
               HashSet p = null;
               synchronized(RemoteChannelServiceImpl.connectListeners) {
                  p = (HashSet)RemoteChannelServiceImpl.connectListeners.clone();
               }

               if (p != null) {
                  Iterator var2 = p.iterator();

                  while(var2.hasNext()) {
                     ConnectListener cl = (ConnectListener)var2.next();
                     cl.onConnect(new ConnectEvent() {
                        public String getServerName() {
                           return serverName;
                        }

                        public long getTime() {
                           return time;
                        }
                     });
                     if (channels != null && cl instanceof DisconnectListener) {
                        try {
                           DisconnectMonitorListImpl.getDisconnectMonitor().addDisconnectListener(((ChannelListImpl)channels).getChannelService(), (DisconnectListener)cl);
                        } catch (Exception var11) {
                           ServerLogger.logDisconnectRegistrationFailed(var11);
                        }
                     }
                  }

                  if (channels != null) {
                     try {
                        DisconnectMonitorListImpl.getDisconnectMonitor().addDisconnectListener(((ChannelListImpl)channels).getChannelService(), new DisconnectListener() {
                           public void onDisconnect(DisconnectEvent de) {
                              if (de instanceof ServerDisconnectEvent) {
                                 ServerLogger.logServerDisconnect(((ServerDisconnectEvent)de).getServerName());
                              } else {
                                 ServerLogger.logServerDisconnect("<unknown>");
                              }

                           }
                        });
                     } catch (Exception var10) {
                        ServerLogger.logDisconnectRegistrationFailed(var10);
                     }
                  }
               }
            } finally {
               RemoteChannelServiceImpl.registeringNewServer--;
            }

         }
      });
   }

   public ChannelList getChannelList(ServerIdentity id) {
      return new ChannelListImpl(id);
   }

   public void removeChannelList(ServerIdentity id) {
      ChannelService.removeChannelEntries(id);
   }

   public String[] getConnectedServers() {
      return URLManager.getConnectedServers();
   }

   public void addConnectListener(ConnectListener listener) {
      synchronized(connectListeners) {
         connectListeners.add(listener);
      }
   }

   public void removeConnectListener(ConnectListener listener) {
      synchronized(connectListeners) {
         connectListeners.remove(listener);
      }
   }

   public synchronized void addConnectDisconnectListener(ConnectListener conlistener, DisconnectListener dislistener) {
      while(registeringNewServer != 0) {
         try {
            this.wait(1000L);
         } catch (InterruptedException var8) {
         }
      }

      this.addConnectListener(new ConnectDisconnectListener(conlistener, dislistener));
      Iterator var3 = connectedServers.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         String serverName = (String)entry.getKey();
         ChannelListImpl cl = (ChannelListImpl)entry.getValue();
         if (URLManager.isConnected(serverName)) {
            try {
               DisconnectMonitorListImpl.getDisconnectMonitor().addDisconnectListener(cl.getChannelService(), dislistener);
            } catch (Exception var9) {
               ServerLogger.logDisconnectRegistrationFailed(var9);
            }
         }
      }

   }

   private static String getServerState(ServerEnvironment env) throws RemoteException, NamingException {
      RemoteLifeCycleOperations remote = (RemoteLifeCycleOperations)PortableRemoteObject.narrow(env.getInitialReference(RemoteLifeCycleOperationsImpl.class), RemoteLifeCycleOperations.class);
      return remote.getState();
   }

   public void registerEnvironmentForever(ServerEnvironment env) throws NamingException {
      registerForever(env);
   }

   public void registerEnvironmentForever(List envList) throws NamingException {
      registerForever(envList);
   }

   public void setShutdownFlag() {
      unregister();
   }

   private static class ConnectDisconnectListener implements ConnectListener, DisconnectListener {
      private ConnectListener conlisten;
      private DisconnectListener dislisten;

      private ConnectDisconnectListener(ConnectListener conlisten, DisconnectListener dislisten) {
         this.conlisten = conlisten;
         this.dislisten = dislisten;
      }

      public int hashCode() {
         return this.conlisten.hashCode();
      }

      public boolean equals(Object obj) {
         try {
            return ((ConnectDisconnectListener)obj).conlisten == this.conlisten && ((ConnectDisconnectListener)obj).dislisten == this.dislisten;
         } catch (ClassCastException var3) {
            return false;
         }
      }

      public void onConnect(ConnectEvent event) {
         String serverName = event.getServerName();
         if (RemoteChannelServiceImpl.debug.isEnabled()) {
            Debug.say("onConnect(" + serverName + ")");
         }

         this.conlisten.onConnect(event);
      }

      public void onDisconnect(DisconnectEvent event) {
         if (RemoteChannelServiceImpl.debug.isEnabled()) {
            if (event instanceof ServerDisconnectEvent) {
               Debug.say("onDisconnect(" + ((ServerDisconnectEvent)event).getServerName() + ")");
            } else {
               Debug.say("onDisconnect(not_a_server)");
            }
         }

         RemoteChannelServiceImpl.getInstance().addConnectListener(this);
         this.dislisten.onDisconnect(event);
      }

      // $FF: synthetic method
      ConnectDisconnectListener(ConnectListener x0, DisconnectListener x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class TimerListenerImpl implements TimerListener {
      private DisconnectListener listener;
      private List envList;

      private TimerListenerImpl(List envList, DisconnectListener listener) {
         this.listener = listener;
         this.envList = envList;
      }

      public void timerExpired(Timer timer) {
         if (!RemoteChannelServiceImpl.shouldDoShutdown()) {
            if (RemoteChannelServiceImpl.debug.isEnabled()) {
               Debug.say("actioning RECONNECT");
            }

            try {
               RemoteChannelServiceImpl.registerInternal(this.envList, this.listener);
            } catch (NamingException var3) {
               RemoteChannelServiceImpl.fatalError();
               throw new AssertionError(var3);
            }
         }
      }

      // $FF: synthetic method
      TimerListenerImpl(List x0, DisconnectListener x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class ConnectSingleton {
      private static final RemoteChannelServiceImpl SINGLETON = (RemoteChannelServiceImpl)GlobalServiceLocator.getServiceLocator().getService(RemoteChannelServiceImpl.class, new Annotation[0]);
   }
}
