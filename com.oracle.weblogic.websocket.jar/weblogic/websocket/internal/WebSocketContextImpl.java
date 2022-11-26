package weblogic.websocket.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketContext;
import weblogic.websocket.WebSocketListener;
import weblogic.websocket.annotation.WebSocket;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class WebSocketContextImpl implements WebSocketContext {
   private static Set versions;
   private WebSocketListener listener;
   private WebAppServletContext context;
   private String[] path;
   private int timeoutSecs = 30;
   private int maxMessageSize = 4000;
   private int maxConnection = -1;
   private WorkManager workManager;
   private ConcurrentHashMap connections;

   public WebSocketContextImpl(ServletConfig config) throws ServletException {
      this.context = (WebAppServletContext)config.getServletContext();
      String listenerName = config.getInitParameter(WebSocketListener.class.getName());
      if (listenerName != null) {
         try {
            Class listenerClass = Thread.currentThread().getContextClassLoader().loadClass(listenerName);
            WebSocket wsAnno = (WebSocket)listenerClass.getAnnotation(WebSocket.class);
            this.timeoutSecs = wsAnno.timeout();
            this.maxMessageSize = wsAnno.maxMessageSize();
            this.maxConnection = wsAnno.maxConnections();
            this.path = wsAnno.pathPatterns();
            this.workManager = WorkManagerFactory.getInstance().find(wsAnno.dispatchPolicy(), this.context.getApplicationId(), this.context.getModuleId());
            this.listener = (WebSocketListener)this.context.createInstance(listenerClass);
            this.listener.init(this);
            this.connections = new ConcurrentHashMap();
         } catch (Exception var5) {
            throw new ServletException("Error loading WebSocketListener class", var5);
         }
      }

   }

   public WebSocketContextImpl(WebAppServletContext context, WebSocketListener listener) {
      this.context = context;
      this.listener = listener;
      this.listener.init(this);
      this.workManager = WorkManagerFactory.getInstance().find("", context.getApplicationId(), context.getModuleId());
      this.connections = new ConcurrentHashMap();
   }

   public void destroy() {
      try {
         this.listener.destroy();
      } catch (Exception var2) {
      }

   }

   public WebSocketListener getWebSocketListener() {
      return this.listener;
   }

   public WorkManager getWorkManager() {
      return this.workManager;
   }

   public ServletContext getServletContext() {
      return this.context;
   }

   public String[] getPathPatterns() {
      return this.path;
   }

   public void setPathPatterns(String[] patterns) {
      this.path = patterns;
   }

   public int getTimeoutSecs() {
      return this.timeoutSecs;
   }

   public void setTimeoutSecs(int timeout) {
      this.timeoutSecs = timeout;
   }

   public int getMaxMessageSize() {
      return this.maxMessageSize;
   }

   public void setMaxMessageSize(int maxMessageSize) {
      this.maxMessageSize = maxMessageSize;
   }

   public int getMaxConnections() {
      return this.maxConnection;
   }

   public void setMaxConnections(int maxConnections) {
      this.maxConnection = maxConnections;
   }

   public Set getWebSocketConnections() {
      return this.connections.keySet();
   }

   public Set getSupportedVersions() {
      return versions;
   }

   void addConnection(WebSocketConnection connection) {
      this.connections.put(connection, Boolean.TRUE);
   }

   void removeConnection(WebSocketConnection connection) {
      this.connections.remove(connection);
   }

   boolean hasMaxConnections() {
      if (this.maxConnection == -1) {
         return false;
      } else {
         return this.connections.size() >= this.maxConnection;
      }
   }

   SubjectHandle getAuthenticatedUser(HttpServletRequest req) {
      SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), req);
      if (subject == null) {
         subject = WebAppSecurity.getProvider().getAnonymousSubject();
      }

      return subject;
   }

   void dispatch(final WebSocketConnection connection, final AbstractWebSocketMessage message) {
      Runnable runnable = new Runnable() {
         public void run() {
            Thread thread = Thread.currentThread();
            ClassLoader cl = WebSocketContextImpl.this.context.pushEnvironment(thread);

            try {
               PrivilegedCallbacks.onMessageProcess(WebSocketContextImpl.this.listener, connection, message);
            } catch (Throwable var7) {
            } finally {
               if (connection.isOpen()) {
                  ((MuxableWebSocket)connection).requeue();
               }

               WebSocketContextImpl.this.context;
               WebAppServletContext.popEnvironment(thread, cl);
            }

         }
      };
      this.workManager.schedule(runnable);
   }

   static {
      HashSet wsVersions = new HashSet(5);
      wsVersions.add(13);
      versions = Collections.unmodifiableSet(wsVersions);
   }
}
