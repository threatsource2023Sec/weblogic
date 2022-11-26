package weblogic.servlet.internal.session;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.i18n.logging.Loggable;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.runtime.ServletSessionRuntimeMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.SessionCreationException;
import weblogic.servlet.internal.ContextVersionManager;
import weblogic.servlet.internal.ServletContextManager;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.WorkManagerFactory;

public abstract class SessionContext implements HttpSessionContext, SessionConstants {
   private static final boolean DEBUG = false;
   protected final WebAppServletContext servletContext;
   private final Hashtable openSessions = new Hashtable();
   protected final Hashtable transientData = new Hashtable();
   protected final SessionConfigManager configMgr;
   private AtomicInteger curOpenSessions = new AtomicInteger(0);
   private AtomicInteger totalOpenSessions = new AtomicInteger(0);
   private AtomicInteger maxOpenSessions = new AtomicInteger(0);
   private SessionInvalidator invalidator = null;
   private SavePostSessionInvalidator savePostInvalidator = null;
   private static final DebugCategory DEBUG_APP_VERSION = Debug.getCategory("weblogic.AppVersion");
   protected static final DebugLogger DEBUG_SESSIONS = DebugLogger.getDebugLogger("DebugHttpSessions");
   protected static final DebugLogger DEBUG_SESSIONS_CONCISE = DebugLogger.getDebugLogger("DebugHttpSessionsConcise");

   protected SessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      this.configMgr = scm;
      this.servletContext = sci;
      if (!sci.getHttpServer().isWAPEnabled()) {
         this.configMgr.validateSessionIdLength();
      }

   }

   public void startTimers() {
      this.initializeInvalidator();
   }

   protected void checkSessionCount() {
      if (WebServerRegistry.getInstance().getManagementProvider().isMemoryLow()) {
         throw new SessionCreationException("The server is running low on memory, cannot create new sessions");
      } else if (this.configMgr.getMaxInMemorySessions() > -1 && this.getOpenSessions().size() >= this.configMgr.getMaxInMemorySessions()) {
         throw new SessionCreationException("Cannot create new sessions as the MaxInMemorySessions limit (" + this.configMgr.getMaxInMemorySessions() + ") has been exceeded");
      }
   }

   protected boolean isDebugEnabled() {
      return DEBUG_SESSIONS.isDebugEnabled() || this.getConfigMgr() != null && this.getConfigMgr().isDebugEnabled();
   }

   protected void initializeInvalidator() {
      this.invalidator = new SessionInvalidator();
      if (this.shouldStartSavePostInvalidator()) {
         this.savePostInvalidator = new SavePostSessionInvalidator();
      }

   }

   boolean shouldStartSavePostInvalidator() {
      WebAppSecurity sm = (WebAppSecurity)AccessController.doPrivileged(new PrivilegedAction() {
         public WebAppSecurity run() {
            return SessionContext.this.servletContext.getSecurityManager();
         }
      });
      return sm != null && "FORM".equals(sm.getAuthMethod()) && this.configMgr.getSavePostTimeoutSecs() > 0 && this.configMgr.getInvalidationIntervalSecs() > 0 && this.configMgr.getSavePostTimeoutSecs() < this.configMgr.getSessionTimeoutSecs();
   }

   public void initialize(ServletWorkContext sci) {
   }

   public Hashtable getSessionsMap() {
      return this.openSessions;
   }

   public void setSessionsMap(Hashtable backup) {
      if (backup != null) {
         this.openSessions.putAll(backup);
      }
   }

   public void sync(SessionData session) {
      synchronized(session.getInternalLock()) {
         session.decrementActiveRequestCount();
         if (!session.sessionInUse()) {
            if (session.isValid()) {
               if (this.needToPassivate()) {
                  session.notifyAboutToPassivate(new HttpSessionEvent(session));
               }

               this.beforeSync(session);
               session.syncSession();
               this.afterSync(session);
            }
         }
      }
   }

   protected boolean needToPassivate() {
      return false;
   }

   protected void beforeSync(SessionData session) {
   }

   protected void afterSync(SessionData session) {
   }

   public abstract String getPersistentStoreType();

   public abstract HttpSession getNewSession(String var1, ServletRequestImpl var2, ServletResponseImpl var3);

   public abstract SessionData getSessionInternal(String var1, ServletRequestImpl var2, ServletResponseImpl var3);

   abstract boolean invalidateSession(SessionData var1, boolean var2);

   abstract void unregisterExpiredSessions(ArrayList var1);

   void registerIdChangedSession(SessionData session) {
      this.addSession(session.id, session);
      this.removeSession(session.oldId);
   }

   public void detectSessionCompatiblity(HttpSession session, HttpServletRequest req) {
   }

   public String[] getIdsInternal() {
      Hashtable sessions = this.getOpenSessions();
      return sessions == null ? new String[0] : (String[])((String[])sessions.keySet().toArray(new String[0]));
   }

   public SessionData getSessionInternalForAuthentication(String id, ServletRequestImpl req, ServletResponseImpl res) {
      return this.getSessionInternal(id, req, res);
   }

   public boolean hasSession(String incomingid) {
      return this.getSessionInternal(incomingid, (ServletRequestImpl)null, (ServletResponseImpl)null) != null;
   }

   public HttpSession getSharedSession(String id, ServletRequestImpl req, ServletResponseImpl res) {
      if (!this.getConfigMgr().isSessionSharingEnabled()) {
         return null;
      } else {
         WebAppServletContext curCtx = this.getServletContext();
         if (curCtx == null) {
            return null;
         } else {
            ServletContextManager scm = curCtx.getServer().getServletContextManager();
            WebAppServletContext[] wasc = scm.getAllContexts();

            for(int i = 0; i < wasc.length; ++i) {
               if (curCtx.getApplicationContext() == wasc[i].getApplicationContext() && wasc[i] != curCtx) {
                  SessionData session = wasc[i].getSessionContext().getSessionInternal(id, req, res);
                  if (session != null) {
                     return new SharedSessionData(session, curCtx);
                  }
               }
            }

            return null;
         }
      }
   }

   public SessionData getSessionFromOtherContexts(String sessionId, ServletRequestImpl req, ServletResponseImpl resp) {
      WebAppServletContext curCtx = this.getServletContext();
      if (curCtx != null && curCtx.getVersionId() != null) {
         ContextVersionManager ctxManager = curCtx.getContextManager();
         if (ctxManager == null) {
            return null;
         } else {
            Iterator i = ctxManager.getServletContexts(req.isAdminChannelRequest());

            while(i.hasNext()) {
               WebAppServletContext ctx = (WebAppServletContext)i.next();
               if (ctx != curCtx) {
                  if (DEBUG_APP_VERSION.isEnabled()) {
                     HTTPLogger.logDebug("Trying to getSessionInternal from another  context=" + ctx + " for id=" + sessionId);
                  }

                  SessionData session = ctx.getSessionContext().getSessionInternal(sessionId, req, resp);
                  if (session != null) {
                     return session;
                  }
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public abstract int getNonPersistedSessionCount();

   public void storeAttributesInBytes() {
      String[] allIds = this.getIdsInternal();
      if (allIds != null) {
         for(int i = 0; i < allIds.length && allIds[i] != null; ++i) {
            SessionData session = this.getSessionInternal(allIds[i], (ServletRequestImpl)null, (ServletResponseImpl)null);
            if (session != null) {
               session.storeAttributesInBytes();
            }
         }

      }
   }

   public void destroy(boolean serverShutdown) {
      this.cancelTrigger();
   }

   public void cancelTrigger() {
      if (this.invalidator != null) {
         this.invalidator.stop();
      }

      this.invalidator = null;
      if (this.savePostInvalidator != null) {
         this.savePostInvalidator.stop();
      }

      this.savePostInvalidator = null;
   }

   public WebAppServletContext getServletContext() {
      return this.servletContext;
   }

   public abstract int getCurrOpenSessionsCount();

   public int getTotalOpenSessionsCount() {
      return this.totalOpenSessions.get();
   }

   public int getMaxOpenSessionsCount() {
      return this.maxOpenSessions.get();
   }

   public void setCurrOpenSessionsCount(int count) {
      this.curOpenSessions.set(count);
   }

   public void setTotalOpenSessionsCount(int count) {
      this.totalOpenSessions.set(count);
   }

   public void setMaxOpenSessionsCount(int count) {
      this.maxOpenSessions.set(count);
   }

   public void incrementOpenSessionsCount() {
      this.curOpenSessions.incrementAndGet();
      this.totalOpenSessions.incrementAndGet();

      int current;
      int max;
      do {
         current = this.curOpenSessions.get();
         max = this.maxOpenSessions.get();
      } while(current > max && !this.maxOpenSessions.compareAndSet(max, current));

   }

   public void decrementOpenSessionsCount() {
      this.curOpenSessions.decrementAndGet();

      int current;
      do {
         current = this.curOpenSessions.get();
      } while(current < 0 && !this.curOpenSessions.compareAndSet(current, 0));

   }

   public void addSession(String sessionId, Object data) {
      this.openSessions.put(sessionId, data);
      this.servletContext.addSession(sessionId);
   }

   public void removeSession(String sessionId) {
      this.openSessions.remove(sessionId);
      this.servletContext.removeSession(sessionId);
   }

   public void exit(ServletRequestImpl req, ServletResponseImpl res, HttpSession session) {
   }

   public void enter(ServletRequestImpl req, ServletResponseImpl res, HttpSession session) {
   }

   public Object getOpenSession(String sessionId) {
      return this.openSessions.get(sessionId);
   }

   public Hashtable getOpenSessions() {
      return this.openSessions;
   }

   public synchronized ServletSessionRuntimeMBean getServletSessionRuntimeMBean(String id) {
      SessionData session = this.getSessionInternal(id, (ServletRequestImpl)null, (ServletResponseImpl)null);
      return session != null && session.isValid() ? session.getServletSessionRuntimeMBean() : null;
   }

   public synchronized ServletSessionRuntimeMBean[] getServletSessionRuntimeMBeans() {
      if (!this.configMgr.isMonitoringEnabled()) {
         return new ServletSessionRuntimeMBean[0];
      } else {
         HashSet beans = new HashSet();
         String[] ids = this.getIdsInternal();
         if (ids.length < 1) {
            return new ServletSessionRuntimeMBean[0];
         } else {
            for(int i = 0; i < ids.length && ids[i] != null; ++i) {
               ServletSessionRuntimeMBean mbean = this.getServletSessionRuntimeMBean(ids[i]);
               if (mbean != null) {
                  beans.add(mbean);
               }
            }

            if (beans.isEmpty()) {
               return new ServletSessionRuntimeMBean[0];
            } else {
               ServletSessionRuntimeMBean[] result = new ServletSessionRuntimeMBean[beans.size()];
               beans.toArray(result);
               return result;
            }
         }
      }
   }

   public synchronized Set getAllServletSessions() {
      String[] ids = this.getIdsInternal();
      if (ids.length < 1) {
         return new HashSet();
      } else {
         HashSet set = new HashSet(ids.length);

         for(int i = 0; i < ids.length && ids[i] != null; ++i) {
            SessionData session = this.getSessionInternal(ids[i], (ServletRequestImpl)null, (ServletResponseImpl)null);
            if (session != null && session.isValid()) {
               SessionInfo info = new SessionInfo(session);
               if (info.getMonitoringId() != null) {
                  set.add(info);
               }
            }
         }

         return set;
      }
   }

   public String[] getServletSessionsMonitoringIds() {
      String[] ids = this.getIdsInternal();
      if (ids.length < 1) {
         return new String[0];
      } else {
         HashSet monitoringIds = new HashSet();

         for(int i = 0; i < ids.length && ids[i] != null; ++i) {
            SessionData session = this.getSessionInternal(ids[i], (ServletRequestImpl)null, (ServletResponseImpl)null);
            if (session != null && session.isValid()) {
               String id = session.getMonitoringId();
               if (id != null) {
                  monitoringIds.add(id);
               }
            }
         }

         if (monitoringIds.isEmpty()) {
            return new String[0];
         } else {
            String[] result = new String[monitoringIds.size()];
            monitoringIds.toArray(result);
            return result;
         }
      }
   }

   public void invalidateServletSession(String monitoringId) throws IllegalStateException {
      SessionData data = this.getSessionWithMonitoringId(monitoringId);
      if (data != null && data.isValid()) {
         data.invalidate();
      } else {
         throw new IllegalStateException("Session has been invalidated already");
      }
   }

   private SessionData getSessionWithMonitoringId(String monitoringId) {
      String[] ids = this.getIdsInternal();
      if (ids.length < 1) {
         return null;
      } else {
         for(int i = 0; i < ids.length && ids[i] != null; ++i) {
            SessionData session = this.getSessionInternal(ids[i], (ServletRequestImpl)null, (ServletResponseImpl)null);
            if (session != null && session.isValid() && monitoringId.equals(session.getMonitoringId())) {
               return session;
            }
         }

         return null;
      }
   }

   public long getSessionLastAccessedTime(String monitoringId) throws IllegalStateException {
      SessionData data = this.getSessionWithMonitoringId(monitoringId);
      if (data != null && data.isValid()) {
         return data.getLastAccessedTime();
      } else {
         throw new IllegalStateException("Session has been invalidated already");
      }
   }

   public long getSessionMaxInactiveInterval(String monitoringId) throws IllegalStateException {
      SessionData data = this.getSessionWithMonitoringId(monitoringId);
      if (data != null && data.isValid()) {
         return (long)data.getMaxInactiveInterval();
      } else {
         throw new IllegalStateException("Session has been invalidated already");
      }
   }

   public String getMonitoringId(String sessionId) {
      SessionData data = this.getSessionInternal(sessionId, (ServletRequestImpl)null, (ServletResponseImpl)null);
      if (data != null && data.isValid()) {
         return data.getMonitoringId();
      } else {
         throw new IllegalStateException("Session has been invalidated already");
      }
   }

   public void notifySessionAttributeChange(HttpSession sess, String name, Object prev, Object attr) {
      this.servletContext.getEventsManager().notifySessionAttributeChange(sess, name, prev, attr);
   }

   public static void declareProperties() {
   }

   public void setInvalidationIntervalSecs(int interval) {
      if (this.configMgr.getInvalidationIntervalSecs() != interval) {
         if (this.invalidator != null) {
            this.invalidator.bounce();
         }

      }
   }

   public void setSavePostTimeoutIntervalSecs(int interval) {
      if (this.configMgr.getSavePostTimeoutIntervalSecs() != interval) {
         if (this.savePostInvalidator != null) {
            this.savePostInvalidator.bounce();
         }

      }
   }

   public synchronized HttpSession getSession(String id) {
      HTTPSessionLogger.logDeprecatedCall("getSession(String id)");
      return null;
   }

   public Enumeration getIds() {
      HTTPSessionLogger.logDeprecatedCall("getIds()");
      return (new Vector()).elements();
   }

   public void deleteInvalidSessions() {
      if (this.invalidator != null) {
         this.invalidator.cleanupExpiredSessions();
      }

   }

   private boolean hasSessionExpired(SessionData session, long currentTime, int timeout) {
      long expiration = Long.MAX_VALUE;
      expiration = currentTime - (long)timeout * 1000L;
      return timeout >= 0 && session.getLAT() < expiration;
   }

   public SessionConfigManager getConfigMgr() {
      return this.configMgr;
   }

   protected void invalidateOrphanedSessions() {
   }

   public String lookupAppVersionIdForSession(String requestedSID, ServletRequestImpl req, ServletResponseImpl res) {
      return null;
   }

   public void forceToConvertAllSessionAttributes() {
      Iterator var1 = this.openSessions.values().iterator();

      while(var1.hasNext()) {
         Object obj = var1.next();
         if (obj instanceof SessionData) {
            ((SessionData)obj).forceToConvertAttributes();
         }
      }

   }

   private boolean invalidateSession(SessionData session) {
      InvalidationAction action = new InvalidationAction(this, session);
      Object obj = session.getInternalAttribute("weblogic.authuser");
      SubjectHandle subject = null;
      if (obj == null) {
         subject = WebAppSecurity.getProvider().getAnonymousSubject();
      } else {
         subject = WebAppSecurity.getProvider().wrapSubject(obj);
      }

      Throwable excp = (Throwable)subject.run((PrivilegedAction)action);
      if (excp != null) {
         HTTPSessionLogger.logUnexpectedTimeoutError(excp);
      }

      return excp == null && action.isInvalidated();
   }

   private static class InvalidationAction implements PrivilegedAction {
      private SessionData sess;
      private SessionContext ctx;
      private boolean invalidated = false;

      InvalidationAction(SessionContext sc, SessionData s) {
         this.ctx = sc;
         this.sess = s;
      }

      public Object run() {
         try {
            this.invalidated = this.ctx.invalidateSession(this.sess, true);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }

      public boolean isInvalidated() {
         return this.invalidated;
      }
   }

   private class SavePostSessionInvalidator extends SessionInvalidator {
      private SavePostSessionInvalidator() {
         super();
      }

      protected int getIntervalSecs() {
         return SessionContext.this.configMgr.getSavePostTimeoutIntervalSecs();
      }

      protected boolean shouldIgnoreChecking(SessionData session) {
         try {
            return !session.hasSavedPostData();
         } catch (IllegalStateException var3) {
            return !session.isValid();
         }
      }

      protected boolean sessionExpired(SessionData session, long currentTime) {
         return SessionContext.this.hasSessionExpired(session, currentTime, SessionContext.this.configMgr.getSavePostTimeoutSecs());
      }

      // $FF: synthetic method
      SavePostSessionInvalidator(Object x1) {
         this();
      }
   }

   private class SessionInvalidator implements NakedTimerListener {
      private final TimerManager timerManager;
      private Timer timer;
      private int invalCount = 0;

      SessionInvalidator() {
         String appId = SessionContext.this.servletContext.getApplicationId();
         String hostName = SessionContext.this.servletContext.getServer().getName();
         String timerManagerStr = "ServletSessionTimer-Host='" + hostName + "',appId='" + appId + "',contextPath='" + SessionContext.this.servletContext.getContextPath() + "',name='" + this.getClass().getName() + "'";
         this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager(timerManagerStr, WorkManagerFactory.getInstance().getSystem());
         this.start();
      }

      void start() {
         int iis = this.getIntervalSecs();
         if (SessionContext.this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logInvalidationIntervalLoggable(iis);
            SessionContext.DEBUG_SESSIONS.debug(l.getMessage());
         }

         this.timer = this.timerManager.schedule(this, 0L, (long)(iis * 1000));
      }

      void stop() {
         this.timer.cancel();
         this.timerManager.stop();
      }

      void bounce() {
         this.timer.cancel();
         this.start();
      }

      public void timerExpired(Timer timer) {
         if (SessionContext.this.getServletContext().isStarted()) {
            ComponentInvocationContextManager cicManager = WebServerRegistry.getInstance().getContainerSupportProvider().getComponentInvocationContextManager();
            ClassLoader oldcl = null;
            Thread thread = null;
            ComponentInvocationContext cic = SessionContext.this.getServletContext().getComponentInvocationContext();

            try {
               ManagedInvocationContext mic = cicManager.setCurrentComponentInvocationContext(cic);
               Throwable var7 = null;

               try {
                  thread = Thread.currentThread();
                  oldcl = thread.getContextClassLoader();

                  try {
                     WebServerRegistry.getInstance().getJNDIProvider().pushContext(SessionContext.this.getServletContext().getEnvironmentContext());
                  } catch (Exception var31) {
                     HTTPLogger.logNoJNDIContext(SessionContext.this.servletContext.getContextPath(), var31.toString());
                  }

                  ClassLoader cl = SessionContext.this.getServletContext().getServletClassLoader();
                  thread.setContextClassLoader(cl);
                  Throwable var9 = (Throwable)WebAppSecurity.getProvider().getAnonymousSubject().run(new PrivilegedAction() {
                     public Object run() {
                        try {
                           SessionInvalidator.this.cleanupExpiredSessions();
                        } catch (Throwable var2) {
                           HTTPSessionLogger.logUnexpectedTimeoutError(var2);
                        }

                        return null;
                     }
                  });
               } catch (Throwable var32) {
                  var7 = var32;
                  throw var32;
               } finally {
                  if (mic != null) {
                     if (var7 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var30) {
                           var7.addSuppressed(var30);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }
            } finally {
               try {
                  WebServerRegistry.getInstance().getJNDIProvider().popContext();
               } catch (Exception var29) {
                  HTTPLogger.logNoJNDIContext(SessionContext.this.servletContext.getContextPath(), var29.toString());
               }

               thread.setContextClassLoader(oldcl);
            }

         }
      }

      public void cleanupExpiredSessions() {
         try {
            long currentTime = System.currentTimeMillis();
            String[] ids = SessionContext.this.getIdsInternal();
            int total = ids.length;
            int invalidated = false;
            int removed = false;
            ArrayList expired = new ArrayList();

            for(int i = 0; i < total; ++i) {
               try {
                  if (ids[i] == null) {
                     break;
                  }

                  SessionData session = SessionContext.this.getSessionInternal(ids[i], (ServletRequestImpl)null, (ServletResponseImpl)null);
                  if (session != null) {
                     if (session.isValid() && !session.sessionInUse() && !this.shouldIgnoreChecking(session)) {
                        synchronized(session.getInternalLock()) {
                           if (session.isValid() && !session.sessionInUse() && !this.shouldIgnoreChecking(session) && this.sessionExpired(session, currentTime)) {
                              if (session.acquireInvalidationLock()) {
                                 if (SessionContext.this.invalidateSession(session)) {
                                    expired.add(session);
                                    if (SessionContext.this.isDebugEnabled()) {
                                       Loggable l = HTTPSessionLogger.logTimerInvalidatedSessionLoggable(ids[i], SessionContext.this.getServletContext().getContextPath());
                                       SessionContext.DEBUG_SESSIONS.debug(l.getMessage());
                                    }
                                 }
                              }
                           }
                        }
                     }
                  } else {
                     session = (SessionData)SessionContext.this.openSessions.get(ids[i]);
                     if (session != null && session.isValid() && !session.sessionInUse() && !this.shouldIgnoreChecking(session)) {
                        synchronized(session.getInternalLock()) {
                           if (session.isValid() && !session.sessionInUse() && !this.shouldIgnoreChecking(session) && this.sessionExpired(session, currentTime)) {
                              if (session.acquireInvalidationLock()) {
                                 if (SessionContext.this.invalidateSession(session)) {
                                    expired.add(session);
                                 }
                              }
                           }
                        }
                     }
                  }
               } catch (ThreadDeath var16) {
                  throw var16;
               } catch (Throwable var17) {
                  HTTPSessionLogger.logUnexpectedTimeoutError(var17);
               }
            }

            SessionContext.this.unregisterExpiredSessions(expired);
            ++this.invalCount;
            this.invalCount %= 10;
            if (this.invalCount == 9) {
               SessionContext.this.invalidateOrphanedSessions();
            }
         } catch (ThreadDeath var18) {
            throw var18;
         } catch (Throwable var19) {
            HTTPSessionLogger.logUnexpectedTimeoutErrorRaised(var19);
         }

      }

      protected int getIntervalSecs() {
         return SessionContext.this.configMgr.getInvalidationIntervalSecs();
      }

      protected boolean shouldIgnoreChecking(SessionData session) {
         return false;
      }

      protected boolean sessionExpired(SessionData session, long currentTime) {
         return SessionContext.this.hasSessionExpired(session, currentTime, session.getMaxInactiveInterval());
      }
   }
}
