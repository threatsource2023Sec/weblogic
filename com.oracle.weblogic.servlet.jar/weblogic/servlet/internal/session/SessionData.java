package weblogic.servlet.internal.session;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.i18n.logging.Loggable;
import weblogic.management.runtime.ServletSessionRuntimeMBean;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManager;
import weblogic.rmi.extensions.RemoteSystemException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.AttributeWrapper;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.MembershipControllerImpl;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.servlet.internal.WebAppConfigManager;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.ContainerSupportProvider;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.utils.Base64;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.concurrent.Latch;

public abstract class SessionData implements HttpSession, SessionInternal, Externalizable, SessionConstants {
   static final long serialVersionUID = -4398986144473197373L;
   private static WebServerRegistry registry = WebServerRegistry.getInstance();
   private transient boolean needToSerializeAttributes;
   private static final transient int AVALABLE_PROCESSORS;
   protected transient SessionContext sessionContext;
   private transient ServletSessionRuntimeMBean runtime;
   protected String id;
   protected String oldId;
   protected String versionId;
   protected long creationTime;
   protected long accessTime;
   protected int maxInactiveInterval;
   protected boolean isNew;
   protected boolean isValid;
   protected boolean needToSyncNewSessionId;
   protected transient volatile boolean expiring;
   private ThreadLocal invalidating;
   protected Map attributes;
   protected transient Hashtable transientAttributes;
   protected Map internalAttributes;
   protected transient Hashtable internalTransientAttributes;
   private transient AtomicInteger activeRequestCount;
   private static final boolean WIN_32;
   private transient Object removeLock;
   private Latch invalidationLatch;
   private Object internalLock;
   protected static final String VERSION_ID = "weblogic.versionId";
   protected static final DebugCategory DEBUG_APP_VERSION;
   public static final DebugLogger DEBUG_SESSIONS;
   protected static final String DEBUG_SESSION_INDICATOR = "wl_debug_session";
   private int invalidatingThreadID;

   public SessionData() {
      this.needToSerializeAttributes = false;
      this.sessionContext = null;
      this.runtime = null;
      this.id = null;
      this.oldId = null;
      this.versionId = null;
      this.creationTime = 0L;
      this.accessTime = 0L;
      this.maxInactiveInterval = -1;
      this.isNew = true;
      this.isValid = true;
      this.needToSyncNewSessionId = false;
      this.expiring = false;
      this.invalidating = null;
      this.attributes = null;
      this.transientAttributes = null;
      this.internalAttributes = null;
      this.internalTransientAttributes = null;
      this.activeRequestCount = new AtomicInteger(0);
      this.removeLock = new Object();
      this.invalidationLatch = new Latch();
      this.internalLock = new Object();
      this.attributes = createDefaultConCurrentHashMap();
      this.internalAttributes = createDefaultConCurrentHashMap();
      this.setModified(false);
   }

   public SessionData(String sessionid, SessionContext context, boolean newSession) {
      this();
      this.id = sessionid;
      this.sessionContext = context;
      this.isNew = newSession;
      if (this.isNew) {
         if (this.id != null) {
            if (!this.reuseSessionId(this.id)) {
               this.id = null;
            } else {
               this.id = getID(this.id);
            }
         }

         if (this.id == null) {
            this.id = this.generateSessionId();
         }

         if (this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logCreateNewSessionForPathLoggable(this.id, this.getContextPath());
            DEBUG_SESSIONS.debug(l.getMessage());
         }

         this.creationTime = System.currentTimeMillis();
         this.accessTime = this.creationTime;
         this.maxInactiveInterval = this.sessionContext.getConfigMgr().getSessionTimeoutSecs();
         this.isValid = true;
         if (this.sessionContext.getConfigMgr().isMonitoringEnabled()) {
            this.runtime = ServletSessionRuntimeManager.getInstance().findOrCreate(this);
         }
      }

      ServletWorkContext ctx = this.getWebAppServletContext();
      this.versionId = ctx.getVersionId();
   }

   protected static ConcurrentHashMap createDefaultConCurrentHashMap() {
      return new ConcurrentHashMap(16, 0.75F, AVALABLE_PROCESSORS);
   }

   protected boolean isDebugEnabled() {
      return DEBUG_SESSIONS.isDebugEnabled() || this.sessionContext != null && this.sessionContext.getConfigMgr() != null && this.sessionContext.getConfigMgr().isDebugEnabled();
   }

   private String generateSessionId() {
      if (WIN_32) {
         while(true) {
            this.id = this.getNextId();
            if (!FileSessionContext.containsReservedKeywords(this.id)) {
               break;
            }

            if (this.isDebugEnabled()) {
               HTTPSessionLogger.logSessionIDContainsReservedKeyword(this.id);
            }
         }
      } else {
         this.id = this.getNextId();
      }

      return this.id;
   }

   public String changeSessionId(String newId) {
      if (!this.needToSyncNewSessionId) {
         this.oldId = this.id;
      }

      if (newId == null) {
         this.generateSessionId();
      } else {
         this.id = newId;
      }

      if (this.oldId != null && this.id != null) {
         ServletSessionRuntimeManager.getInstance().removeAndCreate(this);
      }

      this.needToSyncNewSessionId = true;
      this.sessionContext.registerIdChangedSession(this);
      return this.id;
   }

   boolean needToSyncNewSessionId() {
      return this.needToSyncNewSessionId;
   }

   void setNeedToSyncNewSessionId(boolean needToSyncNewSessionId) {
      this.needToSyncNewSessionId = needToSyncNewSessionId;
   }

   private boolean reuseSessionId(String sessionid) {
      if (!this.getWebAppServletContext().getServer().getMBean().isSingleSignonDisabled() && this.sessionContext.getConfigMgr().isDefaultCookiePath()) {
         WebAppServletContext[] ctxs = this.getWebAppServletContext().getServer().getServletContextManager().getAllContexts();
         if (ctxs != null && ctxs.length >= 2) {
            if (this.isExpiringSessionId(getID(sessionid))) {
               return false;
            } else {
               ArrayList replicatedSessionContexts = new ArrayList();
               if (this.isSessionAvailableInOtherLocalContexts(sessionid, ctxs, replicatedSessionContexts)) {
                  return true;
               } else {
                  this.DEBUGSAY("reuseSessionId replicatedSessionContexts size: " + replicatedSessionContexts.size() + " replicatedSessionContexts: " + replicatedSessionContexts);
                  return !replicatedSessionContexts.isEmpty() ? this.isSessionAvailableInOtherRemoteReplicatedCtxs(sessionid) : false;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isSessionAvailableInOtherLocalContexts(String sessionid, WebAppServletContext[] ctxs, ArrayList replicatedSessionContexts) {
      String cookieName = this.sessionContext.getConfigMgr().getCookieName();

      for(int i = 0; i < ctxs.length; ++i) {
         if (ctxs[i] != this.getWebAppServletContext() && ctxs[i].getSessionContext() != null && ctxs[i].getSessionContext().getConfigMgr().getCookieName().equals(cookieName) && ctxs[i].getSessionContext().getConfigMgr().isDefaultCookiePath()) {
            if (ctxs[i].getSessionContext() instanceof ReplicatedSessionContext) {
               ReplicatedSessionContext replicatedSessionContext = (ReplicatedSessionContext)ctxs[i].getSessionContext();
               if (replicatedSessionContext.getSessionInternal(sessionid, (ServletRequestImpl)null, (ServletResponseImpl)null) != null || replicatedSessionContext.getSecondarySessionInternal(sessionid) != null) {
                  this.DEBUGSAY("isSessionAvailableInOtherLocalContexts found primary/secondary in local other context " + ctxs[i].getContextPath() + " for " + sessionid);
                  return true;
               }

               replicatedSessionContexts.add(replicatedSessionContext);
            } else if (ctxs[i].getSessionContext().hasSession(sessionid)) {
               this.DEBUGSAY("isSessionAvailableInOtherLocalContexts found in local other context " + ctxs[i].getContextPath() + " for " + sessionid);
               return true;
            }
         }
      }

      return false;
   }

   private boolean isSessionAvailableInOtherRemoteReplicatedCtxs(String incomingid) {
      MembershipControllerImpl msi = (MembershipControllerImpl)WebServerRegistry.getInstance().getClusterProvider();
      RSID rsid = new RSID(incomingid, msi.getClusterMembers());
      if (rsid.id == null) {
         return false;
      } else {
         ServerIdentity currentServer = LocalServerIdentity.getIdentity();
         String primaryURL = null;
         String secondaryURL = null;
         this.DEBUGSAY("isSessionAvailableInOtherRemoteReplicatedCtxs currentServer: " + currentServer + ",\nprimary: " + rsid.getPrimary() + ",\nsecondary: " + rsid.getSecondary());
         if (rsid.getPrimary() != null && !currentServer.equals(rsid.getPrimary())) {
            primaryURL = URLManager.findURL(rsid.getPrimary(), this.getHttpServer().getReplicationChannel());
         }

         if (rsid.getSecondary() != null && !currentServer.equals(rsid.getSecondary())) {
            secondaryURL = URLManager.findURL(rsid.getSecondary(), this.getHttpServer().getReplicationChannel());
         }

         boolean result = false;
         RemoteException roidLookupFailure = null;
         if (primaryURL != null) {
            try {
               result = this.getHttpServer().getReplicator().isAvailableInOtherCtx(rsid.id, primaryURL, this.getServletContext().getContextPath(), this.sessionContext.getConfigMgr().getCookieName(), this.sessionContext.getConfigMgr().getCookiePath());
               this.DEBUGSAY("isSessionAvailableInOtherRemoteReplicatedCtxs result: " + result + " from primary: " + primaryURL);
            } catch (RemoteException var11) {
               if (var11 instanceof RemoteSystemException) {
                  this.DEBUGSAY("isSessionAvailableInOtherRemoteReplicatedCtxs Requested timed out, trying to fetch ROID from primary server");
               }

               roidLookupFailure = var11;
            }
         }

         if (!result && secondaryURL != null) {
            try {
               result = this.getHttpServer().getReplicator().isAvailableInOtherCtx(rsid.id, secondaryURL, this.getServletContext().getContextPath(), this.sessionContext.getConfigMgr().getCookieName(), this.sessionContext.getConfigMgr().getCookiePath());
               this.DEBUGSAY("isSessionAvailableInOtherRemoteReplicatedCtxs result: " + result + " from secondary: " + secondaryURL);
            } catch (RemoteException var10) {
               if (var10 instanceof RequestTimeoutException) {
                  this.DEBUGSAY("isSessionAvailableInOtherRemoteReplicatedCtxs Request timed out, trying to fetch ROID from secondary ");
               }

               roidLookupFailure = var10;
            }
         }

         if (result) {
            roidLookupFailure = null;
         }

         if (roidLookupFailure != null) {
            HTTPSessionLogger.logErrorGettingReplicatedSession(incomingid, rsid.getPrimaryServerName(), rsid.getSecondaryServerName(), roidLookupFailure);
         }

         return result;
      }
   }

   private void DEBUGSAY(String msg) {
      if (ReplicatedSessionContext.DEBUG_SESSIONS_CONCISE.isDebugEnabled()) {
         ReplicatedSessionContext.DEBUG_SESSIONS_CONCISE.debug(this.getClass().getName() + "@" + this.hashCode() + " " + msg);
      }

   }

   private HttpServer getHttpServer() {
      return this.getWebAppServletContext().getHttpServer();
   }

   private boolean isExpiringSessionId(String sessionid) {
      SessionData session = (SessionData)this.sessionContext.getOpenSessions().get(sessionid);
      return session != null && session.isExpiring();
   }

   final void reinitRuntimeMBean() {
      if (this.sessionContext.getConfigMgr().isMonitoringEnabled()) {
         if (this.runtime == null) {
            this.runtime = ServletSessionRuntimeManager.getInstance().findOrCreate(this);
         }
      }
   }

   protected void unregisterRuntimeMBean() {
      if (this.sessionContext.getConfigMgr().isMonitoringEnabled()) {
         ServletSessionRuntimeManager.getInstance().destroy(this);
      }
   }

   ServletSessionRuntimeMBean getServletSessionRuntimeMBean() {
      return this.runtime;
   }

   public final boolean isModified() {
      return this.needToSerializeAttributes;
   }

   public final void setModified(boolean flag) {
      this.needToSerializeAttributes = flag;
   }

   final String getContextPath() {
      WebAppServletContext ctx = this.getWebAppServletContext();
      return ctx == null ? null : ctx.getContextPath();
   }

   final String getContextName() {
      WebAppServletContext ctx = this.getWebAppServletContext();
      return ctx == null ? null : ctx.getName();
   }

   public final ServletContext getServletContext() {
      return this.sessionContext.getServletContext();
   }

   public final WebAppServletContext getWebAppServletContext() {
      return this.sessionContext.getServletContext();
   }

   public final long getCreationTime() throws IllegalStateException {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else {
         return this.creationTime;
      }
   }

   public final String getInternalId() {
      return this.id;
   }

   public final String getId() {
      return this.getIdWithServerInfo() + "!" + this.creationTime;
   }

   public String getIdWithServerInfo() {
      if (this.getContext() != null && !this.getWebAppServletContext().getServer().isWAPEnabled()) {
         StringBuffer sb = new StringBuffer(this.sessionContext.getConfigMgr().getIDLength() + 12);
         sb.append(this.id);
         sb.append("!");
         if (WebAppConfigManager.useExtendedSessionFormat()) {
            sb.append(registry.getContainerSupportProvider().getServerHashForExtendedSessionID("!"));
         } else {
            sb.append(this.getWebAppServletContext().getServer().getServerHash());
         }

         return sb.toString();
      } else {
         return this.id;
      }
   }

   public final String getVersionId() {
      return this.versionId;
   }

   public final void setVersionId(String versionId) {
      this.versionId = versionId;
   }

   public long getLastAccessedTime() {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else {
         return this.accessTime;
      }
   }

   public final long getLAT() {
      return this.accessTime;
   }

   public void setLastAccessedTime(long t) {
      this.accessTime = t;
   }

   public final int getMaxInactiveInterval() {
      return this.maxInactiveInterval;
   }

   public void setMaxInactiveInterval(int interval) {
      this.maxInactiveInterval = interval;
   }

   public final HttpSessionContext getSessionContext() {
      return this.sessionContext;
   }

   final void setSessionContext(SessionContext ctx) {
      this.sessionContext = ctx;
   }

   public final SessionContext getContext(String webServerName, String contextName) {
      if (this.sessionContext != null) {
         return this.sessionContext;
      } else {
         WebAppServletContext wasc = getServletContext(webServerName, contextName, this.versionId);
         if (wasc == null) {
            return null;
         } else {
            this.sessionContext = wasc.getSessionContext();
            return this.sessionContext;
         }
      }
   }

   protected static final WebAppServletContext getServletContext(String webServerName, String contextName, String vId) {
      HttpServerManager httpSrvManager = registry.getHttpServerManager();
      HttpServer httper = httpSrvManager.getHttpServer(webServerName);
      if (httper == null) {
         throw new AssertionError("WebService.getHttpServer(" + webServerName + ") returns null");
      } else {
         return httper.getServletContextManager().getContextForContextPath(contextName, vId);
      }
   }

   public final SessionContext getContext() {
      return this.sessionContext;
   }

   public final Object getValue(String name) throws IllegalStateException {
      return this.getAttribute(name);
   }

   public final void putValue(String name, Object value) throws IllegalStateException {
      this.setAttribute(name, value);
   }

   public final String[] getValueNames() throws IllegalStateException {
      ArrayList list = new ArrayList();
      Enumeration e = this.getAttributeNames();

      while(e.hasMoreElements()) {
         list.add(e.nextElement());
      }

      String[] ret = new String[list.size()];
      return (String[])((String[])list.toArray(ret));
   }

   public final void removeValue(String name) {
      this.removeAttribute(name);
   }

   public Object getAttribute(String name) throws IllegalStateException {
      this.check(name);
      Object securityAttribute = this.getSecurityModuleAttribute(name);
      return securityAttribute != null ? securityAttribute : this.getAttributeInternal(name);
   }

   protected Object getSecurityModuleAttribute(String name) {
      if (name.equals("weblogic.formauth.targeturl")) {
         return this.getContext().getServletContext().getConfigManager().isServletAuthFromURL() ? this.getInternalAttribute("weblogic.formauth.targeturl") : this.getInternalAttribute("weblogic.formauth.targeturi");
      } else {
         return null;
      }
   }

   protected Object getAttributeInternal(String name) {
      if (this.attributes != null) {
         Object o = this.attributes.get(name);
         if (o != null) {
            try {
               return AttributeWrapperUtils.unwrapObject(name, (AttributeWrapper)o, this.isDebugEnabled() ? DEBUG_SESSIONS : null);
            } catch (Exception var4) {
               HTTPSessionLogger.logUnableToDeserializeSessionData(var4);
               this.attributes.remove(name);
               return null;
            }
         }
      }

      return this.transientAttributes != null ? this.transientAttributes.get(name) : null;
   }

   protected void check(String name) {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else if (name == null) {
         throw new IllegalArgumentException("Key for session.getAttribute() is null");
      }
   }

   public Enumeration getAttributeNames() {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else {
         Vector v = new Vector();
         if (this.attributes != null) {
            Iterator var2 = this.attributes.keySet().iterator();

            while(var2.hasNext()) {
               String key = (String)var2.next();
               v.addElement(key);
            }
         }

         if (this.transientAttributes != null) {
            Enumeration e = this.transientAttributes.keys();

            while(e.hasMoreElements()) {
               v.addElement(e.nextElement());
            }
         }

         return v.elements();
      }
   }

   public void setAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      if (name == null) {
         throw new IllegalArgumentException("Key for session.setAttribute() is null");
      } else if (value == null) {
         this.removeAttribute(name);
      } else if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else {
         Object oldval = this.getAttribute(name);
         boolean sameValue = this.checkIfSameObjectExists(name, value, oldval);
         if (!sameValue) {
            Object wrapper = this.wrapSessionObjectIfNecessary(name, value, true);
            if (wrapper instanceof AttributeWrapper) {
               this.setModified(true);
               oldval = this.attributes.put(name, wrapper);
            } else {
               if (this.transientAttributes == null) {
                  this.transientAttributes = new Hashtable();
                  if (!registry.isProductionMode()) {
                     this.logTransientAttributeError(name);
                  }
               }

               oldval = this.transientAttributes.put(name, value);
            }

            if (wrapper instanceof AttributeWrapper) {
               if (this.transientAttributes != null && this.transientAttributes.containsKey(name)) {
                  oldval = this.transientAttributes.remove(name);
               }
            } else if (this.attributes.containsKey(name)) {
               oldval = this.attributes.remove(name);
            }

            if (oldval instanceof AttributeWrapper) {
               try {
                  oldval = ((AttributeWrapper)oldval).getObject();
               } catch (IOException | RuntimeException | ClassNotFoundException var7) {
                  HTTPSessionLogger.logUnableToDeserializeSessionData(var7);
               }
            }

            if (oldval instanceof HttpSessionBindingListener) {
               ((HttpSessionBindingListener)oldval).valueUnbound(new HttpSessionBindingEvent(this, name, oldval));
            }

            this.sessionContext.notifySessionAttributeChange(this, name, oldval, value);
         }
      }
   }

   private boolean checkIfSameObjectExists(String name, Object value, Object oldValue) {
      boolean sameInstance = false;
      if (oldValue != null) {
         sameInstance = oldValue == value;
      }

      if (!sameInstance && value instanceof HttpSessionBindingListener) {
         ((HttpSessionBindingListener)value).valueBound(new HttpSessionBindingEvent(this, name, value));
      }

      return sameInstance;
   }

   protected Object wrapSessionObjectIfNecessary(String name, Object value, boolean testSerializability) {
      AttributeWrapper wrapper = AttributeWrapperUtils.wrapObject(name, value, this.isDebugEnabled() ? DEBUG_SESSIONS : null);
      if (wrapper != null) {
         this.validateAttributeValue(name, wrapper, testSerializability);
         return wrapper;
      } else {
         return value;
      }
   }

   private void validateAttributeValue(String name, AttributeWrapper value, boolean testSerializability) {
      if (testSerializability && this.isDebugEnabled() && value != null) {
         try {
            Object o = this.attributes.get(name);
            AttributeWrapper oldValue = o instanceof AttributeWrapper ? (AttributeWrapper)o : null;
            value.testSerializability(oldValue);
            Loggable l = HTTPSessionLogger.logSessionObjectSizeLoggable(name, value.getSerializedObjectLength());
            DEBUG_SESSIONS.debug(l.getMessage());
            long oldChecksum = value.getPreviousChecksum();
            long checksum = value.getCheckSum();
            if (oldChecksum >= 0L) {
               String result = "Attribute value for " + name + " has";
               result = result + (oldChecksum == checksum ? " NOT " : " ");
               result = result + "changed. old value checksum: " + oldChecksum + ", new value checksum: " + checksum;
               DEBUG_SESSIONS.debug(result);
            } else {
               DEBUG_SESSIONS.debug("Checksum for attribute '" + name + "', value: " + checksum);
            }
         } catch (Exception var12) {
            Loggable l = HTTPSessionLogger.logObjectNotSerializableLoggable(name, var12);
            DEBUG_SESSIONS.debug(l.getMessage());
         }

      }
   }

   protected abstract void logTransientAttributeError(String var1);

   public void removeAttribute(String name) throws IllegalStateException {
      this.removeAttribute(name, false);
   }

   protected void removeAttribute(String name, boolean isChange) throws IllegalStateException {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else if (name == null) {
         throw new IllegalArgumentException("Key for session.removeAttribute() is null");
      } else {
         Object result = null;
         Object o;
         if (this.attributes != null) {
            o = this.attributes.remove(name);
            if (o != null) {
               this.setModified(true);
               AttributeWrapper w = (AttributeWrapper)o;

               try {
                  result = o = w.getObject();
                  if (o instanceof HttpSessionBindingListener) {
                     ((HttpSessionBindingListener)o).valueUnbound(new HttpSessionBindingEvent(this, name, o));
                  }
               } catch (ClassNotFoundException var7) {
                  HTTPSessionLogger.logUnableToDeserializeSessionData(var7);
               } catch (IOException var8) {
                  HTTPSessionLogger.logUnableToDeserializeSessionData(var8);
               } catch (RuntimeException var9) {
                  HTTPSessionLogger.logUnableToDeserializeSessionData(var9);
               }
            }
         }

         if (this.transientAttributes != null && this.transientAttributes.get(name) != null) {
            o = this.transientAttributes.remove(name);
            if (o != null) {
               result = o;
               if (o instanceof HttpSessionBindingListener) {
                  ((HttpSessionBindingListener)o).valueUnbound(new HttpSessionBindingEvent(this, name, o));
               }
            }
         }

         if (!isChange && result != null) {
            this.sessionContext.notifySessionAttributeChange(this, name, result, (Object)null);
         }

      }
   }

   public Object getInternalAttribute(String name) {
      return this.getInternalAttribute(name, true);
   }

   private Object getInternalAttribute(String name, boolean checkValidation) throws IllegalStateException {
      if (!this.isValid && checkValidation) {
         throw new IllegalStateException("HttpSession is invalid");
      } else if (name == null) {
         return null;
      } else {
         if (this.internalAttributes != null) {
            Object o = this.internalAttributes.get(name);
            if (o != null) {
               AttributeWrapper w = (AttributeWrapper)o;

               try {
                  return w.getObject();
               } catch (ClassNotFoundException var6) {
                  HTTPSessionLogger.logUnableToDeserializeSessionData(var6);
               } catch (IOException var7) {
                  HTTPSessionLogger.logUnableToDeserializeSessionData(var7);
               } catch (RuntimeException var8) {
                  HTTPSessionLogger.logUnableToDeserializeSessionData(var8);
               }

               this.internalAttributes.remove(name);
               return null;
            }
         }

         return this.internalTransientAttributes != null ? this.internalTransientAttributes.get(name) : null;
      }
   }

   public void setInternalAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else if (name == null) {
         throw new IllegalArgumentException("Key for session.setInternalAttribute() is null");
      } else if (value == null) {
         this.removeInternalAttribute(name);
      } else {
         if (!(value instanceof Serializable) && !(value instanceof Remote)) {
            if (this.internalTransientAttributes == null) {
               this.internalTransientAttributes = new Hashtable();
            }

            this.internalTransientAttributes.put(name, value);
            if (this.internalAttributes != null) {
               this.internalAttributes.remove(name);
            }
         } else {
            this.setModified(true);
            if (this.internalAttributes != null) {
               this.internalAttributes.put(name, new AttributeWrapper(value));
            }

            if (this.internalTransientAttributes != null) {
               this.internalTransientAttributes.remove(name);
            }
         }

      }
   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else if (name == null) {
         throw new IllegalArgumentException("Key for session.removeInternalAttribute() is null");
      } else {
         if (this.internalAttributes != null) {
            Object o = this.internalAttributes.remove(name);
            if (o != null) {
               this.setModified(true);
               return;
            }
         }

         if (this.internalTransientAttributes != null) {
            this.internalTransientAttributes.remove(name);
         }

      }
   }

   public Enumeration getInternalAttributeNames() {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else {
         Vector v = new Vector();
         if (this.internalAttributes != null) {
            Iterator var2 = this.internalAttributes.keySet().iterator();

            while(var2.hasNext()) {
               String key = (String)var2.next();
               v.addElement(key);
            }
         }

         if (this.internalTransientAttributes != null) {
            Enumeration e = this.internalTransientAttributes.keys();

            while(e.hasMoreElements()) {
               v.addElement(e.nextElement());
            }
         }

         return v.elements();
      }
   }

   protected boolean acquireInvalidationLock() {
      if (this.invalidationLatch.tryLock()) {
         this.invalidatingThreadID = System.identityHashCode(Thread.currentThread());
         return true;
      } else {
         return false;
      }
   }

   public void invalidate(boolean ignore) throws IllegalStateException {
      if (!this.isValid) {
         throw new IllegalStateException("Session already invalidated");
      } else {
         this.expiring = true;
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Trying to invalidate http session from api call: " + this.id);
         }

         if (!this.isRefByOthers()) {
            if (this.acquireInvalidationLock()) {
               if (this.sessionContext != null) {
                  try {
                     this.invalidating = this.invalidating != null ? this.invalidating : new ThreadLocal();
                     this.invalidating.set(Boolean.TRUE);
                     this.sessionContext.invalidateSession(this, false);
                  } finally {
                     this.invalidating.remove();
                  }
               }

               this.setValid(false);
               if (DEBUG_SESSIONS.isDebugEnabled()) {
                  DEBUG_SESSIONS.debug("Invalidated http session from api call: " + this.id);
               }

            } else if (System.identityHashCode(Thread.currentThread()) != this.invalidatingThreadID && !ignore) {
               throw new IllegalStateException("Session invalidation is in progress with different thread");
            }
         }
      }
   }

   protected boolean isRefByOthers() {
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("Session ID=" + this.id + ", maxInactiveInterval=" + this.maxInactiveInterval + ", activeRequestCount=" + this.activeRequestCount.get() + ", isRefByOthers=" + (this.activeRequestCount.get() > 1));
      }

      return this.getActiveRequestCount() > 1;
   }

   public void invalidate() throws IllegalStateException {
      this.invalidate(false);
   }

   public boolean isInvalidating() {
      if (this.invalidating == null) {
         return false;
      } else {
         return Boolean.TRUE == this.invalidating.get();
      }
   }

   public boolean isExpiring() {
      return this.expiring;
   }

   public boolean isNew() throws IllegalStateException {
      if (!this.isValid) {
         throw new IllegalStateException("HttpSession is invalid");
      } else {
         return this.isNew;
      }
   }

   public final void setNew(boolean n) {
      if (n != this.isNew) {
         this.isNew = n;
      }

   }

   public boolean isValid() {
      return this.isValid;
   }

   public int getConcurrentRequestCount() {
      return this.getActiveRequestCount();
   }

   final int getActiveRequestCount() {
      return this.activeRequestCount.get();
   }

   final void incrementActiveRequestCount() {
      this.activeRequestCount.incrementAndGet();
   }

   final void decrementActiveRequestCount() {
      this.activeRequestCount.decrementAndGet();
   }

   final boolean sessionInUse() {
      if (DEBUG_SESSIONS.isDebugEnabled()) {
         DEBUG_SESSIONS.debug("Session ID=" + this.id + ", maxInactiveInterval=" + this.maxInactiveInterval + ", activeRequestCount=" + this.activeRequestCount.get() + ", sessionInUse=" + (this.activeRequestCount.get() > 0));
      }

      return this.activeRequestCount.get() > 0;
   }

   final boolean isValidForceCheck() {
      if (this.isValid && !this.expiring) {
         if (this.sessionInUse()) {
            return true;
         } else {
            long currentTime = System.currentTimeMillis();
            if (this.hasSavedPostData() && this.sessionContext.shouldStartSavePostInvalidator()) {
               if (DEBUG_SESSIONS.isDebugEnabled()) {
                  DEBUG_SESSIONS.debug("Invalidating session by SavePostTimeoutSecs, SavePostTimeoutSecs=" + this.sessionContext.getConfigMgr().getSavePostTimeoutSecs());
               }

               return this.isValidForceCheckInternal(currentTime, this.sessionContext.getConfigMgr().getSavePostTimeoutSecs());
            } else {
               return this.maxInactiveInterval < 0 || this.isValidForceCheckInternal(currentTime, this.maxInactiveInterval);
            }
         }
      } else {
         return false;
      }
   }

   boolean hasSavedPostData() {
      return "POST".equals(this.getInternalAttribute("weblogic.formauth.method"));
   }

   private boolean isValidForceCheckInternal(long currentTime, int maxInactiveInterval) {
      if (currentTime - this.accessTime <= (long)maxInactiveInterval * 1000L) {
         return true;
      } else {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Invalidating session ID=" + this.id + ", maxInactiveInterval=" + maxInactiveInterval + ", currentTime=" + currentTime + ", lastAccessTime=" + this.accessTime + ", activeRequestCount=" + this.activeRequestCount.get());
         }

         ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
         boolean needRestoreEnv = false;

         try {
            if (threadCL != this.getWebAppServletContext().getServletClassLoader()) {
               threadCL = this.getWebAppServletContext().pushEnvironment(Thread.currentThread());
               needRestoreEnv = true;
            }

            this.invalidate();
            this.isValid = false;
         } catch (IllegalStateException var10) {
         } finally {
            if (needRestoreEnv) {
               WebAppServletContext.popEnvironment(Thread.currentThread(), threadCL);
            }

         }

         return false;
      }
   }

   public final void setValid(boolean valid) {
      if (valid != this.isValid) {
         this.isValid = valid;
      }

   }

   void remove() {
      synchronized(this.removeLock) {
         this.getWebAppServletContext().getEventsManager().notifySessionLifetimeEvent(this, false);
         String s;
         if (this.attributes != null) {
            Iterator var2 = this.attributes.keySet().iterator();

            while(var2.hasNext()) {
               s = (String)var2.next();

               try {
                  this.removeAttribute(s);
               } catch (Exception var7) {
                  HTTPSessionLogger.logAttributeRemovalFailure(this.getWebAppServletContext().getLogContext(), this.getId(), var7);
               }
            }
         }

         if (this.transientAttributes != null) {
            Enumeration e = this.transientAttributes.keys();

            while(e.hasMoreElements()) {
               s = (String)e.nextElement();

               try {
                  this.removeAttribute(s);
               } catch (Exception var6) {
                  HTTPSessionLogger.logAttributeRemovalFailure(this.getWebAppServletContext().getLogContext(), this.getId(), var6);
               }
            }
         }

         this.internalAttributes = null;
         this.removeSessionLogin();
         this.unregisterRuntimeMBean();
         this.isValid = false;
      }
   }

   private void removeSessionLogin() {
      if (this.sessionContext != null) {
         WebAppServletContext servletContext = this.sessionContext.getServletContext();
         SessionConfigManager configManager = this.sessionContext.getConfigMgr();
         HttpServer.SessionLogin sessionLogin = servletContext.getServer().getSessionLogin();
         if (configManager.isSessionSharingEnabled()) {
            sessionLogin.unregister(this.id);
         } else {
            sessionLogin.unregister(this.id, servletContext.getContextPath());
         }

      }
   }

   Object getInternalLock() {
      return this.internalLock;
   }

   void syncSession() {
      if (this.isDebugEnabled() && this.isValid) {
         int size = calculateSessionSize(new ConcurrentHashMap(this.attributes));
         if (size > -1) {
            Loggable l = HTTPSessionLogger.logSessionSizeLoggable(this.id, size);
            DEBUG_SESSIONS.debug(l.getMessage());
         }
      }

   }

   void postInvalidate() {
      if (this.expiring && this.isValid) {
         this.invalidate(true);
      }

   }

   static final int calculateSessionSize(Object object) {
      try {
         return registry.getContainerSupportProvider().sizeOf(object);
      } catch (IOException var2) {
      } catch (ConcurrentModificationException var3) {
      }

      return -1;
   }

   void storeAttributesInBytes() {
      Iterator var1;
      Object attr;
      AttributeWrapper w;
      if (this.attributes != null) {
         var1 = this.attributes.values().iterator();

         while(var1.hasNext()) {
            attr = var1.next();
            w = (AttributeWrapper)attr;

            try {
               w.convertToBytes();
            } catch (IOException var6) {
               HTTPSessionLogger.logExceptionSerializingAttributeWrapper(var6);
            }
         }
      }

      if (this.internalAttributes != null) {
         var1 = this.internalAttributes.values().iterator();

         while(var1.hasNext()) {
            attr = var1.next();
            w = (AttributeWrapper)attr;

            try {
               w.convertToBytes();
            } catch (IOException var5) {
               HTTPSessionLogger.logExceptionSerializingAttributeWrapper(var5);
            }
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.creationTime = in.readLong();
      this.accessTime = in.readLong();
      this.maxInactiveInterval = in.readInt();
      this.isNew = in.readBoolean();
      this.isValid = in.readBoolean();
      this.attributes = convertToConcurrentHashMap(in.readObject());
      this.id = (String)in.readObject();

      try {
         this.internalAttributes = convertToConcurrentHashMap(in.readObject());
         this.setupVersionIdFromAttrs();
      } catch (OptionalDataException var3) {
         this.internalAttributes = null;
         if (this.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Ignoring the OptionalDataException " + var3.getMessage(), var3);
         }
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      this.initVersionAttrsIfNeeded();
      out.writeLong(this.creationTime);
      out.writeLong(this.accessTime);
      out.writeInt(this.maxInactiveInterval);
      out.writeBoolean(this.isNew);
      out.writeBoolean(this.isValid);
      out.writeObject(convertToHashtable(this.attributes));
      out.writeObject(this.id);
      out.writeObject(convertToHashtable(this.internalAttributes));
   }

   final String getMainAttributeValue() {
      String mainAttribute = this.sessionContext.getConfigMgr().getMonitoringAttributeName();
      if (mainAttribute == null) {
         return null;
      } else {
         Object o = this.getAttribute(mainAttribute);
         if (o == null) {
            return null;
         } else {
            return o instanceof String ? (String)o : o.toString();
         }
      }
   }

   private String getNextId() {
      int sessionLength = this.sessionContext.getConfigMgr().getIDLength();
      byte[] genBytes = new byte[this.calcLength(sessionLength)];
      SecureRandom random = registry.getSecurityProvider().getSecureRandom();
      random.nextBytes(genBytes);
      this.insertTimeSec(genBytes);
      return Base64.urlSafeEncode(genBytes).substring(0, sessionLength);
   }

   private int calcLength(int sessionLength) {
      int mod = sessionLength % 4;
      return mod == 0 ? sessionLength / 4 * 3 : (sessionLength / 4 + 1) * 3;
   }

   private void insertTimeSec(byte[] bytes) {
      long timeMill = System.currentTimeMillis();

      for(int i = 0; i < 4; ++i) {
         bytes[5 - i] = (byte)((int)timeMill);
         timeMill >>= 8;
      }

   }

   public final void notifyAboutToPassivate(HttpSessionEvent evt) {
      notifyAboutToPassivate(this.attributes, evt);
      notifyAboutToPassivate(this.transientAttributes, evt);
   }

   protected Object deserialize(byte[] objBytes) throws ClassNotFoundException, IOException {
      ContainerSupportProvider provider = registry.getContainerSupportProvider();
      return provider.toObject(objBytes);
   }

   protected byte[] serialize(Object newValue) throws IOException {
      ContainerSupportProvider provider = registry.getContainerSupportProvider();
      byte[] objBytes = provider.toByteArray(newValue);
      return objBytes;
   }

   private static void notifyAboutToPassivate(Map attr, HttpSessionEvent evt) {
      if (attr != null) {
         Iterator var2 = attr.values().iterator();

         while(true) {
            Object o;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               o = var2.next();
               if (!(o instanceof AttributeWrapper)) {
                  break;
               }

               try {
                  AttributeWrapper aw = (AttributeWrapper)o;
                  o = aw.getObject(false);
               } catch (Exception var5) {
                  o = null;
               }
            } while(o == null);

            if (o instanceof HttpSessionActivationListener) {
               ((HttpSessionActivationListener)o).sessionWillPassivate(evt);
            }
         }
      }
   }

   public final void notifyActivated(HttpSessionEvent evt) {
      notifyHttpSessionActivationListeners(this.attributes, evt);
      notifyHttpSessionActivationListeners(this.transientAttributes, evt);
   }

   private static void notifyHttpSessionActivationListeners(Map attr, HttpSessionEvent evt) {
      if (attr != null) {
         Iterator var2 = attr.values().iterator();

         while(true) {
            Object o;
            while(true) {
               if (!var2.hasNext()) {
                  return;
               }

               o = var2.next();
               if (!(o instanceof AttributeWrapper)) {
                  break;
               }

               try {
                  AttributeWrapper aw = (AttributeWrapper)o;
                  if (!aw.isHttpSessionActivationListenerWrapped()) {
                     continue;
                  }

                  o = aw.getObject();
               } catch (Exception var5) {
                  o = null;
               }

               if (o != null) {
                  break;
               }
            }

            if (o instanceof HttpSessionActivationListener) {
               ((HttpSessionActivationListener)o).sessionDidActivate(evt);
            }
         }
      }
   }

   static final ConcurrentHashMap convertToConcurrentHashMap(Object iro) {
      ConcurrentHashMap attrs = null;
      if (iro instanceof Hashtable) {
         Map map = (Map)iro;
         attrs = createDefaultConCurrentHashMap();
         attrs.putAll(map);
      } else if (iro instanceof Dictionary) {
         Dictionary diro = (Dictionary)iro;
         attrs = createDefaultConCurrentHashMap();
         Enumeration keys = diro.keys();

         while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            attrs.put(key, diro.get(key));
         }
      } else if (iro instanceof ConcurrentHashMap) {
         attrs = (ConcurrentHashMap)iro;
      } else if (iro != null) {
         throw new IllegalArgumentException("unkonwn attribute map, and its type is " + iro.getClass());
      }

      return attrs;
   }

   static final Hashtable convertToHashtable(Map attrs) {
      return attrs != null && !(attrs instanceof Hashtable) ? new Hashtable(attrs) : (Hashtable)attrs;
   }

   protected void initVersionAttrsIfNeeded() {
      if (this.versionId != null && this.getInternalAttribute("weblogic.versionId") == null) {
         this.setInternalAttribute("weblogic.versionId", this.versionId);
      }

   }

   protected void setupVersionIdFromAttrs() {
      if (this.internalAttributes != null) {
         Object v = this.getInternalAttribute("weblogic.versionId", false);
         if (this.versionId == null && v instanceof String) {
            this.versionId = (String)v;
         }

      }
   }

   protected final void updateVersionIfNeeded(SessionContext oldSC) {
      String inVersionId = (String)this.getInternalAttribute("weblogic.versionId");
      if (inVersionId != null) {
         if (oldSC == null) {
            if (DEBUG_APP_VERSION.isEnabled()) {
               HTTPLogger.logDebug("Cannot find SessionContext of version=" + inVersionId + ", using existing version=" + this.versionId + " for id=" + this.id + ", sessionContext is null");
            }

         } else {
            WebAppServletContext ctx = oldSC.getServletContext();
            if (ctx == null) {
               if (DEBUG_APP_VERSION.isEnabled()) {
                  HTTPLogger.logDebug("Cannot find SessionContext of version=" + inVersionId + ", using existing version=" + this.versionId + " for id=" + this.id + ", servletContext is null");
               }

            } else if (ctx.getVersionId() != null && !ctx.getVersionId().equals(inVersionId)) {
               String webServerName = ctx.getServer().getName();
               String contextPath = ctx.getContextPath();
               if (webServerName != null && contextPath != null) {
                  if (DEBUG_APP_VERSION.isEnabled()) {
                     HTTPLogger.logDebug("SessionData with id=" + this.id + " of version=" + this.versionId + " is being rewired to version=" + inVersionId);
                  }

                  String oldVersionId = this.versionId;
                  this.sessionContext = null;
                  this.versionId = inVersionId;

                  try {
                     this.getContext(webServerName, contextPath);
                  } finally {
                     if (this.sessionContext == null) {
                        this.sessionContext = oldSC;
                        this.versionId = oldVersionId;
                        if (DEBUG_APP_VERSION.isEnabled()) {
                           HTTPLogger.logDebug("Cannot find SessionContext of version=" + inVersionId + ", using existing version=" + this.versionId);
                        }
                     }

                  }

               } else {
                  if (DEBUG_APP_VERSION.isEnabled()) {
                     HTTPLogger.logDebug("Cannot find SessionContext of version=" + inVersionId + ", using existing version=" + this.versionId + " for id=" + this.id + ", webServerName or contextPath is null");
                  }

               }
            }
         }
      }
   }

   public String getMonitoringId() {
      String name = this.sessionContext.getConfigMgr().getMonitoringAttributeName();
      if (name == null) {
         return this.lazyCreateMonitoringId();
      } else {
         Object value = this.getAttribute(name);
         return value == null ? this.lazyCreateMonitoringId() : value.toString();
      }
   }

   private String lazyCreateMonitoringId() {
      String monitoringId = (String)this.getInternalAttribute("weblogic.servlet.monitoringId");
      if (monitoringId == null) {
         monitoringId = this.getNextId();
         this.setInternalAttribute("weblogic.servlet.monitoringId", monitoringId);
      }

      return monitoringId;
   }

   public boolean hasStateAttributes() {
      return this.attributes.size() > 0 || this.internalAttributes != null && (this.internalAttributes.size() > 1 || this.internalAttributes.get("weblogic.workContexts") == null);
   }

   public final void setDebugFlag(boolean debug) {
      if (debug) {
         this.setAttribute("wl_debug_session", Boolean.TRUE);
      } else {
         this.removeAttribute("wl_debug_session");
      }

   }

   public final boolean isDebuggingSession() {
      if (registry.isProductionMode()) {
         return false;
      } else {
         return this.getAttribute("wl_debug_session") != null;
      }
   }

   public String toString() {
      if (!this.isValid()) {
         return "HttpSession is invalid: " + this.getId();
      } else {
         return !this.isDebuggingSession() ? super.toString() : this.dump(true);
      }
   }

   private String dump(boolean externalOnly) {
      StringBuilder buf = new StringBuilder(512);
      buf.append(super.toString()).append(" - \n").append(" session-id: ").append(this.getId()).append('\n').append(" create-time: ").append(new Date(this.creationTime)).append('\n').append(" access-time: ").append(new Date(this.accessTime)).append('\n').append(" max-inactive-interval: ").append(this.maxInactiveInterval).append("\n -");
      this.addUpAttributesTo(this.attributes, buf);
      this.addUpAttributesTo(this.transientAttributes, buf);
      if (externalOnly) {
         return buf.toString();
      } else {
         this.addUpAttributesTo(this.internalAttributes, buf);
         this.addUpAttributesTo(this.internalTransientAttributes, buf);
         return buf.toString();
      }
   }

   protected final void logSessionAttributeChanged(String action, String attrName, Object prev, Object current) {
      prev = prev == null ? "null" : prev;
      current = current == null ? "null" : current;
      HTTPSessionLogger.logAttributeChanged(action, this.getServletContext().getContextPath(), this.getId(), attrName, prev.toString(), current.toString());
   }

   public static final void dumpSessionToLog(HttpSession sess) {
      if (sess != null) {
         HTTPSessionLogger.logDumpSession(sess instanceof SessionData ? ((SessionData)sess).dump(true) : sess.toString());
      }
   }

   private final void addUpAttributesTo(Map attributes, StringBuilder buf) {
      if (attributes != null && attributes.size() != 0) {
         Iterator var3 = attributes.keySet().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            buf.append("\n").append(key).append(": ").append(this.getAttribute(key));
         }

      }
   }

   public static String getID(String id) {
      if (id == null) {
         return null;
      } else {
         int end = id.indexOf("!");
         return end == -1 ? id : id.substring(0, end);
      }
   }

   protected void forceToConvertAttributes() {
      if (this.attributes != null) {
         Iterator it = this.attributes.entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String attrName = (String)entry.getKey();
            Object attrValue = entry.getValue();
            if (attrValue instanceof AttributeWrapper) {
               try {
                  ((AttributeWrapper)attrValue).convertToBytes();
               } catch (IOException var8) {
                  try {
                     HTTPLogger.logRemoveNonSerializableAttributeForReload("session", attrName, ((AttributeWrapper)attrValue).getObject(false).getClass().getName());
                  } catch (Throwable var7) {
                  }

                  it.remove();
               }
            }
         }

      }
   }

   protected WebServerRegistry getWebServerRegistry() {
      return registry;
   }

   static {
      int num = Runtime.getRuntime().availableProcessors();
      if (num > 16) {
         num = 16;
      }

      AVALABLE_PROCESSORS = num;
      WIN_32 = System.getProperty("os.name", "unknown").toLowerCase(Locale.ENGLISH).indexOf("windows") >= 0;
      DEBUG_APP_VERSION = Debug.getCategory("weblogic.AppVersion");
      DEBUG_SESSIONS = DebugLogger.getDebugLogger("DebugHttpSessions");
   }
}
