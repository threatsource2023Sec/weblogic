package weblogic.common;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Vector;
import weblogic.common.internal.BootServices;
import weblogic.common.internal.BootServicesStub;
import weblogic.common.internal.T3ClientParams;
import weblogic.kernel.KernelTypeService;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelperService;
import weblogic.rjvm.ClientServerURL;
import weblogic.rjvm.PeerGoneEvent;
import weblogic.rjvm.PeerGoneListener;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.rjvm.RemoteInvokable;
import weblogic.rjvm.RequestStream;
import weblogic.rjvm.Response;
import weblogic.rmi.ConnectIOException;
import weblogic.rmi.MarshalException;
import weblogic.rmi.RemoteException;
import weblogic.rmi.UnmarshalException;
import weblogic.rmi.utils.Utilities;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.AssertionError;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.URLClassFinder;

public final class T3Client implements PeerGoneListener, ClientCallback {
   public static final int DISCONNECT_TIMEOUT_DEFAULT = -2;
   public static final int DISCONNECT_TIMEOUT_NEVER = -1;
   private T3Connection connection;
   private RJVM rjvm;
   private T3ClientParams cm;
   private int idleCallbackID;
   private Thread loginThread;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public T3ServicesDef services;
   private String workspace;
   private Vector disconnectListeners;
   private boolean disconnectEventSent;
   public static final int INVALID_CALLBACK_ID = -1;
   private ClassLoader loader;

   public T3ServicesDef getT3Services() {
      return this.services;
   }

   public RJVM private_getRJVM() {
      return this.rjvm;
   }

   public boolean isConnected() {
      return this.cm != null;
   }

   public T3Connection getT3Connection() {
      return this.connection;
   }

   public boolean timeTraceEnabled() {
      return false;
   }

   public boolean timeTraceEnable(PrintStream ps) {
      return false;
   }

   public T3Client(T3Connection conn, String workspace) {
      this.loginThread = null;
      this.disconnectListeners = new Vector();
      this.disconnectEventSent = false;
      ((KernelTypeService)LocatorUtilities.getService(KernelTypeService.class)).ensureInitialized();
      this.connection = conn;
      this.workspace = workspace;
      this.services = new T3ClientServices(this);
   }

   public T3Client(T3Connection conn) {
      this((T3Connection)conn, (String)null);
   }

   public T3Client(String url, String workspace, UserInfo t3u) throws IOException {
      this(new T3Connection(url, t3u), workspace);
   }

   public T3Client(String url, String workspace) throws IOException {
      this(url, workspace, (UserInfo)null);
   }

   public T3Client(String url) throws IOException {
      this(url, (String)null, (UserInfo)null);
   }

   public T3Client(String url, UserInfo t3u) throws IOException {
      this(url, (String)null, t3u);
   }

   public synchronized T3Client connect() throws IOException, T3ExecuteException, SecurityException {
      if (this.connection == null) {
         throw new T3Exception("Improperly initialized.");
      } else {
         Protocol protocol = ProtocolManager.getProtocolByName(this.connection.getProtocol());
         if (protocol.isUnknown()) {
            throw new MalformedURLException("Unknown protocol: '" + this.connection.getProtocol() + '\'');
         } else {
            this.rjvm = (new ClientServerURL(this.connection.getURL())).findOrCreateRJVM();
            this.rjvm.addPeerGoneListener(this);
            this.idleCallbackID = this.registerCallback(this);
            this.cm = null;

            try {
               BootServices bs = new BootServicesStub(this.rjvm, protocol);
               this.cm = bs.findOrCreateClientContext(this.workspace, this.connection.getUser(), this.idleCallbackID);
               if (this.connection.getUser() instanceof AuthenticatedUser && SecurityServiceManager.isKernelIdentity(SecurityServiceManager.getASFromAU((AuthenticatedUser)((AuthenticatedUser)this.connection.getUser())))) {
                  this.cm.user = (AuthenticatedUser)((AuthenticatedUser)this.connection.getUser());
               }

               this.loginThread = Thread.currentThread();
               AuthenticatedSubject as = SecurityServiceManager.getASFromAU(this.cm.user);
               SecurityServiceManager.pushSubject(kernelId, as);
            } finally {
               if (this.cm == null) {
                  this.shutdown(true);
               }

            }

            if (this.cm == null) {
               throw new T3Exception("Error attempting to initialize.");
            } else {
               if (this.cm.verbose) {
                  System.out.println("New WSID: " + this.cm.wsID);
                  System.out.println("Connected as user=" + this.cm.user);
               }

               return this;
            }
         }
      }
   }

   public void addDisconnectListener(DisconnectListener dl) {
      this.disconnectListeners.addElement(dl);
   }

   public void removeDisconnectListener(DisconnectListener dl) {
      this.disconnectListeners.removeElement(dl);
   }

   private synchronized void disconnectOccurred(DisconnectEvent de) {
      if (!this.disconnectEventSent) {
         this.disconnectEventSent = true;
         Enumeration e = this.disconnectListeners.elements();

         while(e.hasMoreElements()) {
            ((DisconnectListener)e.nextElement()).disconnectOccurred(de);
         }
      }

   }

   private void shutdown(boolean rjvmExists) {
      this.cm = null;
      if (rjvmExists && this.rjvm != null) {
         this.rjvm.removePeerGoneListener(this);
      }

      this.unregisterCallback(this.idleCallbackID);
      this.rjvm = null;
   }

   public synchronized T3Client disconnect() throws IOException, T3Exception {
      if (!this.isConnected()) {
         System.out.println("Ignoring request to disconnect client that is already disconnected.");
         return this;
      } else {
         this.sendOneWay("XZZdisconnectZZX", (Object)null);
         this.shutdown(true);
         this.disconnectOccurred(new DisconnectEvent(this, "clean disconnect"));
         if (Thread.currentThread() != this.loginThread) {
            System.out.println("Warning: T3Client.disconnect() called in a different thread than the one connect was called in");
            return this;
         } else {
            SecurityServiceManager.popSubject(kernelId);
            return this;
         }
      }
   }

   public void peerGone(PeerGoneEvent pge) {
      this.shutdown(false);
      this.disconnectOccurred(new DisconnectEvent(this, "connection to peer went down"));
   }

   public void dispatch(Throwable problem, Object msg) {
      this.shutdown(true);
      this.disconnectOccurred(new DisconnectEvent(this, "idle time out from Server"));
   }

   public int getHardDisconnectTimeoutMins() {
      return !this.isConnected() ? 0 : this.cm.hardDisconnectTimeoutMins;
   }

   public synchronized T3Client setHardDisconnectTimeoutMins(int minutes) throws RemoteException, T3Exception {
      if (!this.isConnected()) {
         throw new T3Exception("T3Client not connected");
      } else {
         T3ClientParams var10001 = this.cm;
         if (minutes < -2) {
            throw new T3Exception("Invalid timeout value: " + minutes);
         } else {
            this.cm.rcc.setHardDisconnectTimeoutMins(minutes);
            this.cm.hardDisconnectTimeoutMins = minutes;
            return this;
         }
      }
   }

   public int getSoftDisconnectTimeoutMins() {
      return !this.isConnected() ? 0 : this.cm.softDisconnectTimeoutMins;
   }

   public synchronized T3Client setSoftDisconnectTimeoutMins(int minutes) throws RemoteException, T3Exception {
      if (!this.isConnected()) {
         throw new T3Exception("T3Client not connected");
      } else {
         T3ClientParams var10001 = this.cm;
         if (minutes < -2) {
            throw new T3Exception("Invalid timeout value: " + minutes);
         } else {
            this.cm.rcc.setSoftDisconnectTimeoutMins(minutes);
            this.cm.softDisconnectTimeoutMins = minutes;
            return this;
         }
      }
   }

   public int getIdleDisconnectTimeoutMins() {
      return !this.isConnected() ? 0 : this.cm.idleSoftDisconnectTimeoutMins;
   }

   public synchronized T3Client setIdleDisconnectTimeoutMins(int minutes) throws T3Exception {
      if (!this.isConnected()) {
         throw new T3Exception("T3Client not connected");
      } else {
         T3ClientParams var10001 = this.cm;
         if (minutes < -2) {
            throw new T3Exception("Invalid timeout value: " + minutes);
         } else {
            this.cm.rcc.setIdleDisconnectTimeoutMins(minutes);
            this.cm.idleSoftDisconnectTimeoutMins = minutes;
            return this;
         }
      }
   }

   public String getServerName() {
      return !this.isConnected() ? "" : this.cm.serverName;
   }

   public boolean getVerbose() {
      return !this.isConnected() ? false : this.cm.verbose;
   }

   public synchronized T3Client setVerbose(boolean verbose) throws RemoteException {
      if (!this.isConnected()) {
         throw new ConnectIOException("T3Client not connected");
      } else {
         this.cm.rcc.setVerbose(verbose);
         this.cm.verbose = verbose;
         return this;
      }
   }

   public String toString() {
      return super.toString() + " - connection: '" + this.getT3Connection() + "', wsid: '" + (this.cm != null ? this.cm.wsID : "unconnected") + '\'';
   }

   public void sendOneWay(String clss, Object o) throws RemoteException {
      if (this.rjvm == null) {
         throw new ConnectIOException("T3Client not connected");
      } else {
         RequestStream req = null;

         try {
            req = this.rjvm.getRequestStream((ServerChannel)null);
            req.writeAbbrevString(clss);
            req.writeObjectWL(o);
         } catch (IOException var6) {
            throw new MarshalException("Failed to marshal arguments", var6);
         }

         try {
            req.sendOneWay(this.cm.ccID);
         } catch (java.rmi.RemoteException var5) {
            throw (RemoteException)Utilities.theOtherException(var5);
         }
      }
   }

   public Response sendRecvAsync(String clss, Object o) throws RemoteException {
      if (this.rjvm == null) {
         throw new ConnectIOException("T3Client not connected");
      } else {
         RequestStream req = null;

         try {
            req = this.rjvm.getRequestStream((ServerChannel)null);
            req.writeAbbrevString(clss);
            req.writeObjectWL(o);
         } catch (IOException var6) {
            throw new MarshalException("Failed to marshal arguments", var6);
         }

         try {
            return req.sendRecv(this.cm.ccID);
         } catch (java.rmi.RemoteException var5) {
            throw (RemoteException)Utilities.theOtherException(var5);
         }
      }
   }

   public Object sendRecv(String clss, Object o) throws T3Exception {
      Response resp = this.sendRecvAsync(clss, o);
      Throwable error = resp.getThrowable();
      if (error != null) {
         if (error instanceof RemoteException) {
            throw (RemoteException)error;
         }

         if (error instanceof T3Exception) {
            throw (T3Exception)error;
         }

         if (error instanceof RuntimeException) {
            throw (RuntimeException)error;
         }

         if (error instanceof Error) {
            throw (Error)error;
         }

         if (error instanceof Exception) {
            throw new T3Exception("Exception in sendRecv", error);
         }
      }

      WLObjectInput in = null;

      Object var6;
      try {
         in = resp.getMsg();
         var6 = in.readObjectWL();
      } catch (IOException var16) {
         throw new UnmarshalException("reading payload failed: ", var16);
      } catch (ClassNotFoundException var17) {
         throw new UnmarshalException("reading payload failed: ", var17);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var15) {
               throw new AssertionError(var15);
            }
         }

      }

      return var6;
   }

   private int registerCallback(ClientCallback c) {
      if (this.rjvm == null) {
         return -1;
      } else {
         RemoteInvokable e = new CallbackDispatcher(c);
         int callbackID = System.identityHashCode(e);
         RJVMManager.getLocalRJVM().getFinder().put(callbackID, e);
         return callbackID;
      }
   }

   private void unregisterCallback(int callbackID) {
      RJVMManager.getLocalRJVM().getFinder().remove(callbackID);
   }

   public Class loadClass(String className) throws ClassNotFoundException {
      if (this.rjvm == null) {
         try {
            this.connect();
         } catch (Exception var4) {
            throw new ClassNotFoundException("Connection failure while trying to load class: '" + className + '\'');
         }
      }

      if (this.loader == null) {
         String url = getChannelHelperService().createCodeBaseURL(this.rjvm.getID());
         URLClassFinder finder = new URLClassFinder(url);
         this.loader = new GenericClassLoader(finder);
      }

      return this.loader.loadClass(className);
   }

   private static ChannelHelperService getChannelHelperService() {
      return (ChannelHelperService)GlobalServiceLocator.getServiceLocator().getService(ChannelHelperService.class, new Annotation[0]);
   }
}
