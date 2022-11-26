package weblogic.servlet.internal.session;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.rmi.ConnectException;
import java.rmi.Remote;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpSessionEvent;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationAccess;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.replication.ApplicationUnavailableException;
import weblogic.cluster.replication.NotFoundException;
import weblogic.cluster.replication.ROID;
import weblogic.cluster.replication.Replicatable;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationSizeDebugLogger;
import weblogic.common.internal.PassivationUtils;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.common.internal.ProxyClassResolvableChunkedObjectInputStream;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.logging.Loggable;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.AttributeWrapper;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.IncompatibleSessionSerializationException;
import weblogic.servlet.internal.ServerHelper;
import weblogic.servlet.internal.WebAppConfigManager;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.ClusterProvider;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.NestedError;
import weblogic.utils.PlatformConstants;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkInputStreamAccess;
import weblogic.utils.io.ChunkOutput;
import weblogic.utils.io.ChunkedObjectInputStream;
import weblogic.utils.io.ChunkedObjectOutputStream;
import weblogic.utils.io.StringInput;
import weblogic.utils.io.StringOutput;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public class ReplicatedSessionData extends SessionData implements Replicatable {
   private static final long serialVersionUID = -1080155161838278576L;
   protected ROID roid;
   protected String contextName;
   private String webserverName;
   private HttpServer srvr;
   private transient ReplicatedSessionChange change;
   private static final DebugCategory DEBUG_APP_VERSION = Debug.getCategory("weblogic.AppVersion");
   private static final Set IMMUTABLE_CLASSES = new HashSet(Arrays.asList(String.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class));
   private static final boolean IS_REPLICATION_ON_GET = Boolean.getBoolean("weblogic.servlet.session.replicateOnGet");
   private String secondaryJVMID;

   public ReplicatedSessionData() {
      this.secondaryJVMID = null;
      this.initializeChange();
   }

   public ReplicatedSessionData(String sessionid, SessionContext httpSessionContext) {
      this(sessionid, httpSessionContext, true);
   }

   protected ReplicatedSessionData(String sessionID, SessionContext httpSessionContext, boolean isNew) {
      super(sessionID, httpSessionContext, isNew);
      this.secondaryJVMID = null;
      this.initializeChange();
   }

   protected void initialize() {
      this.srvr = this.getWebAppServletContext().getServer();
      this.contextName = this.getContextPath();
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      if (httpSrvManager.isDefaultHttpServer(this.srvr)) {
         this.webserverName = httpSrvManager.fakeDefaultServerName();
      } else {
         this.webserverName = this.srvr.getName();
      }

      this.roid = this.registerOrAdd(this.srvr, this.id, this.contextName);
      if (DEBUG_APP_VERSION.isEnabled()) {
         HTTPLogger.logDebug("registerOrAdd returns " + this.roid);
      }

      if (!this.isValid) {
         HTTPSessionLogger.logSessionGotInvalidatedBeforeCreationCouldComplete(this.getWebAppServletContext().getLogContext(), this.id);
      } else if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("sessionId:" + this.id + " associated with roid:" + this.roid);
      }

      this.reinitializeSecondary();
      this.getWebAppServletContext().getEventsManager().notifySessionLifetimeEvent(this, true);
      this.incrementActiveRequestCount();
      ((SessionContext)this.getSessionContext()).addSession(this.id, this);
      this.srvr.getReplicator().putPrimary(this.id, this.getROID(), this.contextName);
      ((SessionContext)this.getSessionContext()).incrementOpenSessionsCount();
   }

   protected void initializeChange() {
      this.change = new ReplicatedSessionChange();
   }

   protected ReplicatedSessionChange getSessionChange() {
      return this.change;
   }

   protected ROID registerOrAdd(HttpServer srvr, String sessionID, String contextName) {
      ROID roid = (ROID)srvr.getReplicator().getPrimary(sessionID);
      if (roid != null) {
         this.getReplicationServices().add(roid, this);
         return roid;
      } else {
         roid = (ROID)srvr.getReplicator().getSecondary(sessionID);
         if (roid != null) {
            srvr.getReplicator().putPrimary(sessionID, roid, contextName);
            this.getReplicationServices().add(roid, this);
            return roid;
         } else {
            return this.getReplicationServices().register(this).getROID();
         }
      }
   }

   ROID getROID() {
      return this.roid;
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      ClassLoader loader = null;
      String annotation = (String)oi.readObject();
      ClusterProvider clusterProvider = this.getWebServerRegistry().getClusterProvider();
      AppClassLoaderManager manager = clusterProvider.getApplicationClassLoaderManager();
      Annotation ann = new Annotation(annotation);
      loader = manager.findLoader(ann);
      Thread thread = Thread.currentThread();
      ClassLoader origCL = thread.getContextClassLoader();
      if (loader != null) {
         thread.setContextClassLoader(loader);
      }

      boolean useBackwardCompatibilityMode = false;
      if (oi instanceof PeerInfoable) {
         ReplicatedSessionChange replicatedSessionChange = this.getSessionChange();
         PeerInfo peerInfo = ((PeerInfoable)oi).getPeerInfo();
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         String partitionName = null;
         if (cic != null) {
            partitionName = cic.getPartitionName();
         }

         if (peerInfo == null) {
            throw new ConnectException("Couldn't read Replicated Session Data for " + partitionName + " - it is likely that the connection has already been shut down");
         }

         ClusterServices clusterServices = clusterProvider.getClusterService();
         boolean isSessionLazyDeserialization = partitionName != null ? clusterServices.isSessionLazyDeserializationEnabled(partitionName) : clusterServices.isSessionLazyDeserializationEnabled();
         useBackwardCompatibilityMode = replicatedSessionChange.checkCompatibility(peerInfo, isSessionLazyDeserialization);
      }

      boolean useLazyDeserialization = false;

      try {
         ManagedInvocationContext mic = clusterProvider.getClusterServicesInvocationContext().setInvocationContext(ann.getApplicationName(), ann.getModuleName());
         Throwable var37 = null;

         try {
            if (!useBackwardCompatibilityMode && oi instanceof ChunkInputStreamAccess) {
               useLazyDeserialization = oi.readBoolean();
               if (!useLazyDeserialization) {
                  this.attributes = convertToConcurrentHashMap(oi.readObject());
               } else {
                  int size = oi.readInt();
                  this.attributes = createDefaultConCurrentHashMap();

                  for(int i = 0; i < size; ++i) {
                     String key = this.readString(oi);
                     int len = ((ChunkInputStreamAccess)oi).readChunkLength();
                     byte[] val = new byte[len];
                     ((ChunkInputStreamAccess)oi).readByteArray(val, 0, len);
                     if (val.length == 1 && val[0] == 112) {
                        this.attributes.put(key, (Object)null);
                     } else {
                        this.attributes.put(key, val);
                     }
                  }
               }
            } else {
               this.attributes = convertToConcurrentHashMap(oi.readObject());
            }

            this.setNew(false);
            this.isValid = oi.readBoolean();
            this.creationTime = oi.readLong();
            this.maxInactiveInterval = oi.readInt();
            this.id = (String)oi.readObject();
            this.roid = (ROID)oi.readObject();
            this.contextName = (String)oi.readObject();
            this.webserverName = (String)oi.readObject();
            this.internalAttributes = convertToConcurrentHashMap(oi.readObject());
            this.accessTime = oi.readLong();
            long timeOnPrimary = oi.readLong();
            long diff = System.currentTimeMillis() - timeOnPrimary;
            this.accessTime += diff;
            this.setupVersionIdFromAttrs();
         } catch (Throwable var32) {
            var37 = var32;
            throw var32;
         } finally {
            if (mic != null) {
               if (var37 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var31) {
                     var37.addSuppressed(var31);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } finally {
         if (loader != null) {
            thread.setContextClassLoader(origCL);
         }

      }

      if (DEBUG_APP_VERSION.isEnabled()) {
         HTTPLogger.logDebug("ReplicatedSessionData.readExternal: versionId=" + this.versionId + ", contextName=" + this.contextName + ", id=" + this.id + ", workCtxs=" + new String(this.getWorkContexts()));
      }

   }

   private String readString(ObjectInput in) throws IOException {
      if (in instanceof StringInput) {
         StringInput strin = (StringInput)in;
         return strin.readUTF8();
      } else {
         return in.readUTF();
      }
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeObject(this.getWebAppAnnotation());
      this.initVersionAttrsIfNeeded();
      boolean useBackwardCompatibilityMode = false;
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = null;
      if (cic != null) {
         partitionName = cic.getPartitionName();
      }

      ClusterServices clusterServices = this.getWebServerRegistry().getClusterProvider().getClusterService();
      boolean useLazyDeserialization = partitionName != null ? clusterServices.isSessionLazyDeserializationEnabled(partitionName) : clusterServices.isSessionLazyDeserializationEnabled();
      if (oo instanceof PeerInfoable) {
         ReplicatedSessionChange replicatedSessionChange = this.getSessionChange();
         PeerInfo peerInfo = ((PeerInfoable)oo).getPeerInfo();
         useBackwardCompatibilityMode = replicatedSessionChange.checkCompatibility(peerInfo, useLazyDeserialization);
      }

      if (!useBackwardCompatibilityMode && oo instanceof ChunkOutput) {
         oo.writeBoolean(useLazyDeserialization);
         if (!useLazyDeserialization) {
            oo.writeObject(convertToHashtable(this.attributes));
         } else {
            int size = this.attributes.size();
            oo.writeInt(size);
            if (size > 0) {
               Iterator i = this.attributes.keySet().iterator();
               ChunkedObjectOutputStream costrm = new ChunkedObjectOutputStream();
               costrm.setReplacer(RemoteObjectReplacer.getReplacer());

               while(i.hasNext()) {
                  try {
                     String key = (String)i.next();
                     this.writeString(oo, key);
                     Object val = this.attributes.get(key);
                     Chunk toWrite = null;
                     if (val instanceof byte[]) {
                        toWrite = Chunk.getChunk();
                        Chunk.chunkFully(toWrite, new UnsyncByteArrayInputStream((byte[])((byte[])val)));
                     } else {
                        costrm.writeObject(this.attributes.get(key));
                        toWrite = costrm.getChunks();
                     }

                     ((ChunkOutput)oo).writeChunks(toWrite);
                  } finally {
                     costrm.reset();
                  }
               }
            }
         }
      } else {
         oo.writeObject(convertToHashtable(this.attributes));
      }

      oo.writeBoolean(this.isValid);
      oo.writeLong(this.creationTime);
      oo.writeInt(this.maxInactiveInterval);
      oo.writeObject(this.id);
      oo.writeObject(this.roid);
      oo.writeObject(this.contextName);
      oo.writeObject(this.webserverName);
      oo.writeObject(convertToHashtable(this.internalAttributes));
      oo.writeLong(this.accessTime);
      oo.writeLong(System.currentTimeMillis());
      if (DEBUG_APP_VERSION.isEnabled()) {
         HTTPLogger.logDebug("ReplicatedSessionData.writeExternal: versionId=" + this.versionId + ", contextName=" + this.contextName + ", id=" + this.id + ", workCtxs=" + new String(this.getWorkContexts()));
      }

   }

   private void writeString(ObjectOutput out, String str) throws IOException {
      if (out instanceof StringOutput) {
         StringOutput strout = (StringOutput)out;
         strout.writeUTF8(str);
      } else {
         out.writeUTF(str);
      }

   }

   public void becomePrimary(ROID roid) {
      if (roid != null) {
         this.roid = roid;
      }

      SessionContext sessionContext = this.getContext(this.webserverName, this.contextName);
      if (sessionContext == null) {
         HTTPSessionLogger.logContextNotFound(this.contextName, "becomePrimary");
         throw new RuntimeException("WebApp with contextPath: " + this.contextName + " not found in the secondary server");
      } else if (!(sessionContext instanceof ReplicatedSessionContext)) {
         HTTPSessionLogger.logPersistentStoreTypeNotReplicated(this.contextName, "becomePrimary");
         throw new RuntimeException("WebApp with contextPath: " + this.contextName + " is not replicatable in the secondary server");
      } else {
         ReplicatedSessionContext ctx = (ReplicatedSessionContext)sessionContext;
         this.setSessionContext(ctx);
         this.setLastAccessedTime(System.currentTimeMillis());
         this.reinitRuntimeMBean();
         this.getWebAppServletContext().getServer().getSessionLogin().register(this.id, this.getContextPath());
         if (!this.isNew()) {
            this.setForceToConvertAttributes(true);
         }

         ClassLoader oldcl = Thread.currentThread().getContextClassLoader();

         try {
            Thread.currentThread().setContextClassLoader(this.getWebAppServletContext().getServletClassLoader());
            this.notifyActivated(new HttpSessionEvent(this));
         } finally {
            Thread.currentThread().setContextClassLoader(oldcl);
         }

         if (!this.isNew()) {
            ctx.incrementOpenSessionsCount();
         }

         this.getHttpServer().getReplicator().putPrimary(this.id, roid, this.contextName);
         ctx.addSession(this.id, this);
         if (this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logBecomePrimaryLoggable(this.id);
            DEBUG_SESSIONS.debug(l.getMessage());
         }

      }
   }

   private void notifyActivated() {
      Thread currThread = Thread.currentThread();
      ClassLoader origCL = currThread.getContextClassLoader();
      String appName = ApplicationAccess.getApplicationAccess().getCurrentApplicationName();
      if (appName == null) {
         this.getWebAppServletContext().pushEnvironment(currThread);
      }

      try {
         this.notifyActivated(new HttpSessionEvent(this));
      } finally {
         if (appName == null) {
            this.getWebAppServletContext();
            WebAppServletContext.popEnvironment(currThread, origCL);
         }

      }

   }

   public Object becomeSecondary(ROID roid) {
      this.roid = roid;
      SessionContext sessionContext = this.getContext(this.webserverName, this.contextName);
      if (sessionContext == null) {
         HTTPSessionLogger.logContextNotFound(this.contextName, "becomeSecondary");
         throw new ApplicationUnavailableException("WebApp with contextPath: " + this.contextName + " not found in the secondary server");
      } else if (!(sessionContext instanceof ReplicatedSessionContext)) {
         HTTPSessionLogger.logPersistentStoreTypeNotReplicated(this.contextName, "becomeSecondary");
         throw new ApplicationUnavailableException("WebApp with contextPath: " + this.contextName + " is not replicatable in the secondary server");
      } else {
         ReplicatedSessionContext ctx = (ReplicatedSessionContext)sessionContext;
         this.setSessionContext(ctx);
         if (ctx.getOpenSession(this.id) != null) {
            ctx.decrementOpenSessionsCount();
         }

         this.getHttpServer().getReplicator().putSecondary(this.id, roid, this.contextName);
         this.getHttpServer().getSessionLogin().unregister(this.id, this.getContextPath());
         this.unregisterRuntimeMBean();
         if (this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logBecomeSecondaryLoggable(this.id);
            DEBUG_SESSIONS.debug(l.getMessage());
         }

         return LocalServerIdentity.getIdentity();
      }
   }

   public void becomeUnregistered(ROID roid) {
      SessionContext sCtx = this.getContext(this.webserverName, this.contextName);
      if (sCtx != null) {
         ReplicatedSessionContext ctx = (ReplicatedSessionContext)sCtx;
         if (ctx.getOpenSession(this.id) != null) {
            ctx.decrementOpenSessionsCount();
         }

         this.getHttpServer().getReplicator().removeSecondary(this.id, this.contextName);
         this.getHttpServer().getSessionLogin().unregister(this.id, this.getContextPath());
      }

      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logUnregisterLoggable(this.id);
         DEBUG_SESSIONS.debug(l.getMessage());
      }

   }

   public void update(ROID id, Serializable obj) {
      if (this.isDebugEnabled()) {
         Debug.assertion(obj instanceof ReplicatedSessionChange);
      }

      if (obj != null) {
         ReplicatedSessionChange chg = null;

         try {
            chg = (ReplicatedSessionChange)obj;
         } catch (ClassCastException var5) {
            if (this.isDebugEnabled()) {
               DEBUG_SESSIONS.debug("Failed to update the secondary: ", var5);
            }

            return;
         }

         this.applySessionChange(chg);
         if (this.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Received Change = " + chg.toString() + ", workCtxs=" + this.getWorkContexts());
         }

      }
   }

   protected void applySessionChange(ReplicatedSessionChange chg) {
      this.maxInactiveInterval = chg.getMaxInActiveInterval();
      this.accessTime = chg.getLastAccessTime();
      long diff = System.currentTimeMillis() - chg.getTimeOnPrimaryAtUpdate();
      this.accessTime += diff;
      Iterator iter;
      Object key;
      Object v;
      if (!chg.getAttributeChanges().isEmpty()) {
         iter = chg.getAttributeChanges().keySet().iterator();

         while(iter.hasNext()) {
            key = iter.next();
            v = chg.getAttributeChanges().get(key);
            this.justPutValue((String)key, v, chg.isUseLazyDeserialization());
         }
      }

      if (!chg.getInternalAttributeChanges().isEmpty()) {
         iter = chg.getInternalAttributeChanges().keySet().iterator();

         while(iter.hasNext()) {
            key = iter.next();
            v = chg.getInternalAttributeChanges().get(key);
            if (v != null) {
               super.setInternalAttribute((String)key, v);
               if (key.equals("weblogic.workContexts")) {
                  this.updateWorkContextsIfNeeded();
               }
            } else {
               super.removeInternalAttribute((String)key);
            }
         }
      }

      String newId = (String)this.getInternalAttribute("weblogic.servlet.session.newId");
      if (newId != null) {
         this.updateSessionId(newId);
         this.removeInternalAttribute("weblogic.servlet.session.newId");
      }

   }

   public String changeSessionId(String newId) {
      super.changeSessionId(newId);
      this.getSessionChange().addInternalAttributeChange("weblogic.servlet.session.newId", this.id, this.maxInactiveInterval, this.accessTime);
      this.srvr.getReplicator().removePrimary(this.oldId, this.contextName);
      this.srvr.getReplicator().putPrimary(this.id, this.roid, this.contextName);
      return this.id;
   }

   private String updateSessionId(String newId) {
      this.oldId = this.id;
      this.id = newId;
      this.srvr.getReplicator().removeSecondary(this.oldId, this.contextName);
      this.srvr.getReplicator().putSecondary(this.id, this.roid, this.contextName);
      return this.id;
   }

   private void justPutValue(String name, Object value, boolean useLazyDeserialization) {
      if (value != null) {
         if (useLazyDeserialization) {
            this.attributes.put(name, value);
         } else {
            AttributeWrapper wrapper = (AttributeWrapper)this.wrapSessionObjectIfNecessary(name, value, false);
            this.attributes.put(name, wrapper);
         }
      } else {
         this.attributes.remove(name);
      }

   }

   private HttpServer getHttpServer() {
      if (this.srvr != null) {
         return this.srvr;
      } else {
         HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
         this.srvr = httpSrvManager.getHttpServer(this.webserverName);
         return this.srvr;
      }
   }

   private String getSecondaryJVMID() {
      String result = this.secondaryJVMID;
      if (result != null) {
         return result;
      } else {
         Loggable l;
         try {
            ServerIdentity secondary = (ServerIdentity)this.getReplicationServices().getSecondaryInfo(this.roid);
            if (secondary != null) {
               if (WebAppConfigManager.useExtendedSessionFormat()) {
                  result = ServerHelper.createServerEntry(ServerHelper.getNetworkChannelName(), secondary, "!");
               } else {
                  result = Integer.toString(secondary.hashCode());
               }
            } else {
               result = "NONE";
            }
         } catch (NotFoundException var4) {
            if (this.isDebugEnabled()) {
               l = HTTPSessionLogger.logSecondaryIDNotFoundLoggable(this.getWebAppServletContext().getLogContext(), this.id, var4);
               DEBUG_SESSIONS.debug(l.getMessage());
            }

            result = "NONE";
         } catch (RuntimeException var5) {
            if (this.isDebugEnabled()) {
               l = HTTPSessionLogger.logFailedToFindSecondaryInfoLoggable(this.getWebAppServletContext().getLogContext(), this.id, var5);
               DEBUG_SESSIONS.debug(l.getMessage());
            }

            result = "NONE";
         }

         this.secondaryJVMID = result;
         return result;
      }
   }

   void reinitializeSecondary() {
      this.secondaryJVMID = null;
   }

   public String getIdWithServerInfo() {
      SessionContext ctx = this.getContext();
      if (ctx == null) {
         return this.id;
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append(this.id);
         sb.append("!");
         if (WebAppConfigManager.useExtendedSessionFormat()) {
            sb.append(ServerHelper.createServerEntry(ServerHelper.getNetworkChannelName(), LocalServerIdentity.getIdentity(), "!"));
         } else {
            sb.append(this.getWebAppServletContext().getServer().getServerHash());
         }

         sb.append("!");
         sb.append(this.getSecondaryJVMID());
         return sb.toString();
      }
   }

   void remove(boolean trigger) {
      ((ReplicatedSessionContext)this.getContext()).getTimer().unregisterLAT(this.roid);
      if (!trigger) {
         this.getReplicationServices().unregister(this.roid, this.contextName);
      }

      super.remove();
   }

   void unregisterSecondary() {
      ReplicationServices replicationServices = this.getReplicationServices();
      if (replicationServices == null) {
         HTTPSessionLogger.logReplicationServicesNotFound(this.contextName, this.getROID() == null ? "" : this.getROID().toString());
      } else {
         replicationServices.removeOrphanedSecondary(this.roid, this.contextName);
      }
   }

   public void setMaxInactiveInterval(int interval) {
      super.setMaxInactiveInterval(interval);
      this.getSessionChange().init(this.maxInactiveInterval, this.accessTime);
   }

   private void setForceToConvertAttributes(boolean b) {
      if (this.attributes != null) {
         Iterator var2 = this.attributes.values().iterator();

         while(var2.hasNext()) {
            Object attr = var2.next();
            if (attr instanceof AttributeWrapper) {
               AttributeWrapper eachAttr = (AttributeWrapper)attr;
               eachAttr.setForceToConvert(b);
            }
         }
      }

   }

   public void setInternalAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      if (value == null) {
         this.removeInternalAttribute(name);
      } else {
         super.setInternalAttribute(name, value);
         if (value instanceof Serializable || value instanceof Remote) {
            this.getSessionChange().addInternalAttributeChange(name, value, this.maxInactiveInterval, this.accessTime);
         }

      }
   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      super.removeInternalAttribute(name);
      this.getSessionChange().addInternalAttributeChange(name, (Object)null, this.maxInactiveInterval, this.accessTime);
   }

   public void setAttribute(String name, Object value) throws IllegalStateException {
      if (value == null) {
         this.removeAttribute(name);
      } else {
         super.setAttribute(name, value);
         this.debugReplicateSameValue(name, value);
         if (value instanceof Serializable || value instanceof Remote) {
            this.getSessionChange().addAttributeChange(name, value, this.maxInactiveInterval, this.accessTime);
         }

      }
   }

   private void debugReplicateSameValue(String name, Object value) {
      if (this.isDebugEnabled()) {
         if (!(value instanceof Serializable) && !(value instanceof Remote)) {
            DEBUG_SESSIONS.debug("Session attribute with name:" + name + " class:" + value.getClass().getName() + " is not serializable ane will  not be replicated or persisted");
         } else {
            Object oldVal = this.attributes.get(name);
            if (oldVal != null && oldVal instanceof AttributeWrapper) {
               AttributeWrapper wrapper = (AttributeWrapper)oldVal;
               if (wrapper.getPreviousChecksum() == wrapper.getCheckSum()) {
                  String result = "Attribute value for '" + name + "' is being replicated although it has not changed. previous value checksum: " + wrapper.getPreviousChecksum() + ", new value checksum: " + wrapper.getCheckSum();
                  DEBUG_SESSIONS.debug(result);
               }
            }
         }

      }
   }

   protected void removeAttribute(String name, boolean isChange) throws IllegalStateException {
      try {
         this.getAttribute(name);
      } catch (IncompatibleSessionSerializationException var4) {
         this.attributes.remove(name);
      }

      super.removeAttribute(name, isChange);
      this.getSessionChange().addAttributeChange(name, (Object)null, this.maxInactiveInterval, this.accessTime);
   }

   void syncSession() {
      try {
         this._syncSession();
      } finally {
         this.postInvalidate();
      }

   }

   private void _syncSession() {
      super.syncSession();
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("The change associated with this SessionData(" + this.getROID() + " or " + this + ") is: " + this.getSessionChange().hashCode());
      }

      if (this.isValid) {
         if (this.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("SessionData.syncSession() the change is modified: " + this.getSessionChange().isModified() + " and the active request count is: " + this.getActiveRequestCount() + " for " + this.getROID() + " and this is: " + this);
         }

         if (this.getSessionChange().isModified()) {
            if (this.isDebugEnabled()) {
               DEBUG_SESSIONS.debug("Replicating session : " + this.getROID() + " and " + this);
            }

            ((ReplicatedSessionContext)this.getContext()).getTimer().unregisterLAT(this.roid);

            Loggable l;
            try {
               this.getSessionChange().setTimeOnPrimaryAtUpdate(System.currentTimeMillis());
               this.getReplicationServices().updateSecondary(this.roid, this.getUpdateObject(), this.contextName);
            } catch (RemoteRuntimeException var5) {
               if (this.isDebugEnabled()) {
                  l = HTTPSessionLogger.logFailedToUpdateSecondaryLoggable(this.id, var5);
                  DEBUG_SESSIONS.debug(l.getMessage());
               }
            } catch (NotFoundException var6) {
               if (this.isDebugEnabled()) {
                  l = HTTPSessionLogger.logSecondaryNotFoundLoggable(this.id, var6);
                  DEBUG_SESSIONS.debug(l.getMessage());
               }

               throw new NestedError("Could not find secondary on remote server", var6);
            }

            synchronized(this.getSessionChange()) {
               if (!this.sessionInUse()) {
                  this.getSessionChange().clear();
               }
            }

            this.setNeedToSyncNewSessionId(false);
         } else {
            ((ReplicatedSessionContext)this.getContext()).getTimer().registerLAT(this.roid, this.accessTime, this.maxInactiveInterval);
         }

      }
   }

   protected Serializable getUpdateObject() {
      return this.change;
   }

   protected void logTransientAttributeError(String name) {
      HTTPSessionLogger.logTransientReplicatedAttributeError(this.getWebAppServletContext().getLogContext(), name, this.getId());
   }

   public Object getKey() {
      return this.contextName;
   }

   protected ReplicationServices getReplicationServices() {
      ReplicatedSessionContext repContext = (ReplicatedSessionContext)this.getSessionContext();
      return repContext != null ? repContext.getReplicationServices() : null;
   }

   protected void initVersionAttrsIfNeeded() {
      if (this.versionId != null && this.getInternalAttribute("weblogic.versionId") == null) {
         this.setInternalAttribute("weblogic.versionId", this.versionId);
      }

   }

   private byte[] getWorkContexts() {
      return (byte[])((byte[])this.getInternalAttribute("weblogic.workContexts"));
   }

   private void updateWorkContextsIfNeeded() {
      byte[] workCtxs = this.getWorkContexts();
      if (workCtxs != null) {
         SessionContext ctx = this.getContext(this.webserverName, this.contextName);
         if (ctx != null) {
            ctx.getServletContext().getServer().getWorkContextManager().updateWorkContexts(this.id, workCtxs);
         }
      }

   }

   static String getAnnotation() {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      return getAnnotation(loader);
   }

   private static String getAnnotation(ClassLoader loader) {
      return loader instanceof GenericClassLoader ? ((GenericClassLoader)loader).getAnnotation().getAnnotationString() : null;
   }

   private String getWebAppAnnotation() throws IOException {
      WebAppServletContext wasc = getServletContext(this.webserverName, this.contextName, this.versionId);
      if (wasc != null) {
         ClassLoader loader = wasc.getServletClassLoader();
         if (loader != null && loader instanceof GenericClassLoader) {
            return ((GenericClassLoader)loader).getAnnotation().getAnnotationString();
         }
      }

      return getAnnotation();
   }

   public Object getAttribute(String name) throws IllegalStateException {
      this.check(name);
      Object securityAttribute = this.getSecurityModuleAttribute(name);
      if (securityAttribute != null) {
         return securityAttribute;
      } else {
         Object value;
         if (this.attributes != null) {
            value = this.attributes.get(name);
            if (value != null && value instanceof byte[]) {
               this.deserializeAttribute(name, (byte[])((byte[])value));
            }
         }

         value = this.getAttributeInternal(name);
         if (this.replicateOnGet(value)) {
            this.getSessionChange().addAttributeChange(name, value, this.maxInactiveInterval, this.accessTime);
         }

         return value;
      }
   }

   private boolean replicateOnGet(Object o) {
      return IS_REPLICATION_ON_GET && (o instanceof Serializable || o instanceof Remote) && !IMMUTABLE_CLASSES.contains(o.getClass());
   }

   private void deserializeAttribute(String name, byte[] buf) {
      Chunk head = null;
      ChunkedObjectInputStream cistrm = null;

      try {
         head = Chunk.createSharedChunk(buf, buf.length);
         cistrm = new ProxyClassResolvableChunkedObjectInputStream(head, 0);
         cistrm.setReplacer(RemoteObjectReplacer.getReplacer());
         Object value = cistrm.readObject();
         AttributeWrapper wrapper = (AttributeWrapper)this.wrapSessionObjectIfNecessary(name, value, true);
         this.attributes.put(name, wrapper);
      } catch (Exception var10) {
         ClusterProvider clusterProvider = WebServerRegistry.getInstance().getClusterProvider();
         if (this.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Unable to deserialize attribute value for key: " + name, var10);
         }

         if (clusterProvider != null && clusterProvider.shouldDetectSessionCompatiblity()) {
            throw new IncompatibleSessionSerializationException();
         }
      } finally {
         if (cistrm != null) {
            cistrm.close();
         } else if (head != null) {
            Chunk.releaseChunks(head);
         }

      }

   }

   public String toString() {
      String str = super.toString();
      return !ReplicationSizeDebugLogger.isDebugEnabled() ? str : this.appendSizeDetails(str);
   }

   private String appendSizeDetails(String data) {
      StringBuilder sb = (new StringBuilder(data)).append(PlatformConstants.EOL);
      sb.append("  Size of ReplicatedSessionData is  : ");

      try {
         sb.append(PassivationUtils.toByteArray(this).length);
      } catch (Exception var6) {
      }

      sb.append(" Attributes are as below ").append(PlatformConstants.EOL);
      Iterator iterator = this.attributes.keySet().iterator();

      while(iterator.hasNext()) {
         try {
            Object obj = iterator.next();
            sb.append(" Attribute Name  : ").append(obj).append(" Attribute Size : ").append(PassivationUtils.toByteArray(this.attributes.get(obj)).length).append(PlatformConstants.EOL);
         } catch (Exception var5) {
         }
      }

      return sb.toString();
   }
}
