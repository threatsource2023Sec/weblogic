package weblogic.iiop;

import java.io.EOFException;
import java.io.IOException;
import java.rmi.ConnectException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import weblogic.iiop.ior.CodeSetsComponent;
import weblogic.iiop.ior.IOPProfile;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.rmi.spi.EndPointFinder;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;

public class EndPointManager implements EndPointFinder {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final EndPointManagerDelegate LIVE_DELEGATE = new LiveEndPointManagerDelegate();
   private static EndPointManagerDelegate delegate;
   private static final IdentityHashMap connectionMap;
   private static final HashMap listenPointMap;
   private static final List connectionFactories;
   private static final boolean DEBUG = false;
   private static final WeakHashMap outboundConnectionLockTable;

   static void p(String s) {
      System.err.println("<EndPointManager>: " + s);
   }

   public static EndPoint findOrCreateEndPoint(Connection connection) throws IOException {
      EndPoint endPoint = findEndPoint(connection);
      if (endPoint == null) {
         Class var2 = Connection.class;
         synchronized(Connection.class) {
            if ((endPoint = findEndPoint(connection)) == null) {
               endPoint = createEndPoint(connection);
            }
         }
      }

      return endPoint;
   }

   static EndPoint findEndPoint(Connection connection) {
      return (EndPoint)connectionMap.get(connection);
   }

   private static synchronized EndPoint createEndPoint(Connection connection) throws IOException {
      EndPoint endPoint = connection.createEndPoint();
      setBiDirContextAlreadySent(connection);
      if (connection.isStateful()) {
         addMapping(connection, endPoint);
      }

      return endPoint;
   }

   private static void setBiDirContextAlreadySent(Connection connection) {
      connection.setFlag(8);
   }

   public static void updateConnection(Connection connection, ListenPoint listenPoint) {
      if (!connectionUnknown(connection) && !alreadyMappedForOutput(listenPoint)) {
         listenPointMap.put(listenPoint, findEndPoint(connection));
         connection.setListenPoint(listenPoint);
      }
   }

   private static boolean connectionUnknown(Connection c) {
      return findEndPoint(c) == null;
   }

   private static boolean alreadyMappedForOutput(ListenPoint newkey) {
      return listenPointMap.containsKey(newkey);
   }

   public static EndPoint findOrCreateEndPoint(IOR ior, String channelName, boolean force) throws IOException {
      return delegate.findOrCreateEndPoint(ior, channelName, force);
   }

   public static EndPoint findOrCreateEndPoint(IOR ior) throws IOException {
      return findOrCreateEndPoint(ior, (String)null, false);
   }

   public static EndPoint findOrCreateEndPoint(IOR ior, String channelName) throws IOException {
      return findOrCreateEndPoint(ior, channelName, false);
   }

   static EndPoint findEndPoint(IOR ior) {
      return (EndPoint)listenPointMap.get(getListenPoint(ior));
   }

   private static EndPoint createEndPoint(IOR ior, String channelName, boolean force) throws IOException {
      synchronized(getOutboundConnectionLock(getListenPoint(ior))) {
         EndPoint e = getActiveEndPoint(ior, force);
         if (e == null) {
            e = createEndPoint(ior, channelName);
         }

         negotiateConnection(e, ior);
         return e;
      }
   }

   private static synchronized EndPoint getActiveEndPoint(IOR ior, boolean killExistingConnection) {
      EndPoint endPoint = (EndPoint)listenPointMap.get(getListenPoint(ior));
      if (shouldKillConnection(endPoint, killExistingConnection)) {
         forceConnectionShutdown(endPoint.getConnection(), new EOFException("Forceful shutdown"));
         endPoint = null;
      }

      return endPoint;
   }

   static synchronized void forceConnectionShutdown(Connection connection, Throwable throwable) {
      EndPoint endPoint = removeConnection(connection);
      if (endPoint != null) {
         endPoint.cleanupPendingResponses(throwable);
      }

      connection.close();
   }

   private static boolean shouldKillConnection(EndPoint e, boolean force) {
      return e != null && (force || e.getConnection().isDead());
   }

   private static ListenPoint getListenPoint(IOR ior) {
      return ior.getListenPoint();
   }

   private static EndPoint createEndPoint(IOR ior, String channelName) throws IOException {
      Connection connection = createOutboundConnection(ior, channelName);
      EndPoint endPoint = connection.createEndPoint();
      if (connection.isStateful()) {
         Class var4 = EndPointManager.class;
         synchronized(EndPointManager.class) {
            listenPointMap.put(ior.getListenPoint(), endPoint);
            addMapping(connection, endPoint);
         }
      }

      return endPoint;
   }

   static void addMapping(Connection connection, EndPoint endPoint) {
      connectionMap.put(connection, endPoint);
   }

   private static Connection createOutboundConnection(IOR ior, String channelName) throws IOException {
      Connection c = null;
      Exception cause = null;
      Iterator var4 = connectionFactories.iterator();

      while(var4.hasNext()) {
         ConnectionFactory connectionFactory = (ConnectionFactory)var4.next();
         cause = null;
         if (c != null) {
            return c;
         }

         try {
            if (connectionFactory.claimsIOR(ior)) {
               c = createConnection(connectionFactory, ior, channelName);
            }
         } catch (Exception var7) {
            cause = var7;
         } catch (Throwable var8) {
            cause = new RuntimeException(var8);
         }
      }

      if (c == null) {
         throw new ConnectException("Connection refused. Unable to create connection for IOR " + ior, (Exception)cause);
      } else {
         return c;
      }
   }

   private static Connection createConnection(ConnectionFactory connectionFactory, IOR ior, String channelName) throws IOException {
      return connectionFactory.createConnection(ior, IiopConfigurationFacade.getServerChannel(kernelId, connectionFactory.getProtocol(), channelName));
   }

   private static synchronized void negotiateConnection(EndPoint e, IOR ior) throws IOException {
      if (!e.getFlag(16)) {
         IOPProfile profile = ior.getProfile();
         Connection c = e.getConnection();
         c.setMinorVersion(profile.getMinorVersion());
         if (ior.isRemote()) {
            c.setFlag(16);
            CodeSetsComponent csc = (CodeSetsComponent)profile.getComponent(1);
            if (csc != null && (!receivedCodeSetFromPeer(c) || !c.getFlag(64) || !csc.supportedCharCodeSet(c.getCharCodeSet()) || !csc.supportedWcharCodeSet(c.getWcharCodeSet()))) {
               c.setCodeSets(csc.negotiatedCharCodeSet(), csc.negotiatedWcharCodeSet());
            }
         }
      }

   }

   private static boolean receivedCodeSetFromPeer(Connection c) {
      return c.getFlag(32);
   }

   static synchronized EndPoint removeConnection(Connection c) {
      ListenPoint k = c.getListenPoint();
      Object e = connectionMap.remove(c);
      if (e != null) {
         Object e2 = listenPointMap.get(k);
         if (e2 == e) {
            listenPointMap.remove(k);
         }
      }

      return (EndPoint)e;
   }

   private static synchronized Object getOutboundConnectionLock(ListenPoint key) {
      Object lock = outboundConnectionLockTable.get(key);
      if (lock == null) {
         lock = new Object();
      }

      outboundConnectionLockTable.put(key, lock);
      return lock;
   }

   public boolean claimHostID(HostID hostID) {
      return hostID instanceof HostIDImpl;
   }

   public boolean claimServerURL(String url) {
      return false;
   }

   public weblogic.rmi.spi.EndPoint findOrCreateEndPoint(HostID hostID) {
      return hostID instanceof HostIDImpl ? (weblogic.rmi.spi.EndPoint)listenPointMap.get(((HostIDImpl)hostID).getConnectionKey()) : null;
   }

   public weblogic.rmi.spi.EndPoint findEndPoint(HostID hostID) {
      return hostID instanceof HostIDImpl ? (weblogic.rmi.spi.EndPoint)listenPointMap.get(((HostIDImpl)hostID).getConnectionKey()) : null;
   }

   public weblogic.rmi.spi.EndPoint findOrCreateEndPoint(String url) {
      return null;
   }

   public weblogic.rmi.spi.EndPoint findEndPoint(String url) throws IOException {
      return null;
   }

   static boolean hasPendingResponses(Connection connection) {
      return hasPendingResponses(findEndPoint(connection));
   }

   private static boolean hasPendingResponses(EndPoint endPoint) {
      return endPoint != null && endPoint.hasPendingResponses();
   }

   static {
      delegate = LIVE_DELEGATE;
      connectionMap = new IdentityHashMap();
      listenPointMap = new HashMap();
      connectionFactories = LocatorUtilities.getAllServices(ConnectionFactory.class);
      outboundConnectionLockTable = new WeakHashMap();
   }

   private static class LiveEndPointManagerDelegate implements EndPointManagerDelegate {
      private LiveEndPointManagerDelegate() {
      }

      public EndPoint findOrCreateEndPoint(IOR ior, String channelName, boolean force) throws IOException {
         EndPoint e = (EndPoint)EndPointManager.listenPointMap.get(EndPointManager.getListenPoint(ior));
         if (e != null && !force && !e.getConnection().isDead()) {
            if (!e.getFlag(16) && ior.isRemote()) {
               EndPointManager.negotiateConnection(e, ior);
            }
         } else {
            e = EndPointManager.createEndPoint(ior, channelName, force);
         }

         return e;
      }

      // $FF: synthetic method
      LiveEndPointManagerDelegate(Object x0) {
         this();
      }
   }

   public interface EndPointManagerDelegate {
      EndPoint findOrCreateEndPoint(IOR var1, String var2, boolean var3) throws IOException;
   }
}
