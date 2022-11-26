package weblogic.websocket.tyrus;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.AsyncContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerContainer;
import org.glassfish.tyrus.core.RequestContext;
import org.glassfish.tyrus.core.TyrusUpgradeResponse;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.RequestContext.Builder;
import org.glassfish.tyrus.server.TyrusServerContainer;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.WebSocketEngine;
import weblogic.servlet.internal.AuthenticationListener;
import weblogic.servlet.internal.MuxableSocketHTTP;
import weblogic.servlet.internal.ServletInvocationContext;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.websocket.internal.WebSocketDebugLogger;

public class TyrusServletFilter implements Filter, HttpSessionListener, TyrusServletWriter.CloseListener, AuthenticationListener {
   private static final String DEFAULT_SESSION_MAX_IDLE_TIMEOUT = "weblogic.websocket.tyrus.session-max-idle-timeout";
   private static final long DEFAULT_IDLE_TIMEOUT = 30000L;
   private final WebSocketEngine engine;
   private final CoherenceServletFilterService coherenceService;
   private TyrusServerContainer serverContainer = null;
   private final Map websocketConnections = new ConcurrentHashMap();
   private final Map websocketConnectionsPerServletResponseImpl = new ConcurrentHashMap();

   TyrusServletFilter(WebSocketEngine engine, CoherenceServletFilterService coherenceService) {
      this.engine = engine;
      this.coherenceService = coherenceService;
   }

   public void init(FilterConfig filterConfig) throws ServletException {
      Thread thread = Thread.currentThread();
      ClassLoader contextClassLoader = null;
      if (this.coherenceService != null) {
         contextClassLoader = this.coherenceService.setContextClassLoader(thread);
      }

      try {
         ServletContext context = filterConfig.getServletContext();
         this.serverContainer = (TyrusServerContainer)context.getAttribute(ServerContainer.class.getName());
         String sessionMaxIdleTimeout = context.getInitParameter("weblogic.websocket.tyrus.session-max-idle-timeout");
         if (sessionMaxIdleTimeout != null) {
            this.serverContainer.setDefaultMaxSessionIdleTimeout(Long.parseLong(sessionMaxIdleTimeout));
         } else {
            this.serverContainer.setDefaultMaxSessionIdleTimeout(30000L);
         }

         try {
            this.serverContainer.start(filterConfig.getServletContext().getContextPath(), 0);
         } catch (Exception var15) {
            throw new ServletException("Web socket server initialization failed.", var15);
         } finally {
            this.serverContainer.doneDeployment();
         }
      } finally {
         if (contextClassLoader != null) {
            thread.setContextClassLoader(contextClassLoader);
         }

      }

   }

   public void onLogout(HttpSessionEvent se) {
      this.closeOnLogout(se);
   }

   public void sessionCreated(HttpSessionEvent se) {
   }

   public void sessionDestroyed(HttpSessionEvent se) {
      this.closeOnLogout(se);
   }

   private void closeOnLogout(HttpSessionEvent se) {
      Thread thread = Thread.currentThread();
      ClassLoader contextClassLoader = null;
      if (this.coherenceService != null) {
         contextClassLoader = this.coherenceService.setContextClassLoader(thread);
      }

      try {
         Iterator var4 = this.websocketConnections.keySet().iterator();

         while(var4.hasNext()) {
            Connection connection = (Connection)var4.next();
            if (se.getSession().getId().equals(this.websocketConnections.get(connection)) && ((TyrusServletWriter)connection.getWriter()).isProtected()) {
               this.websocketConnections.remove(connection);
               this.syncHttpSessionIfAny((ServletResponseImpl)this.websocketConnectionsPerServletResponseImpl.remove(connection));
               connection.close(new CloseReason(CloseCodes.VIOLATED_POLICY, "No reason given."));
            }
         }
      } finally {
         if (contextClassLoader != null) {
            thread.setContextClassLoader(contextClassLoader);
         }

      }

   }

   public void onClose(Connection connection) {
      this.websocketConnections.remove(connection);
      this.syncHttpSessionIfAny((ServletResponseImpl)this.websocketConnectionsPerServletResponseImpl.remove(connection));
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
      final HttpServletRequest httpRequest = (HttpServletRequest)request;
      HttpServletResponse httpResponse = (HttpServletResponse)response;
      String header = httpRequest.getHeader("Sec-WebSocket-Key");
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      ServletResponseImpl rspi = reqi.getResponse();
      if (header != null) {
         debug("Setting up WebSocket protocol handler");
         SubjectHandle subject = SecurityModule.getCurrentUser(reqi.getContext().getSecurityContext(), httpRequest);
         if (subject == null) {
            subject = WebAppSecurity.getProvider().getAnonymousSubject();
         }

         MuxableSocketHTTP httpSocket = (MuxableSocketHTTP)reqi.getConnection().getConnectionHandler().getRawConnection();
         TyrusMuxableWebSocket webSocket = new TyrusMuxableWebSocket(httpSocket, this.coherenceService, subject);
         boolean isProtected = ((ServletInvocationContext)request.getServletContext()).getSecurityManager().getConstraint(httpRequest) != null;
         TyrusServletWriter webSocketWriter = new TyrusServletWriter(webSocket, this, isProtected);
         RequestContext requestContext = Builder.create().requestURI(URI.create(httpRequest.getRequestURI())).queryString(httpRequest.getQueryString()).httpSession(httpRequest.getSession(false)).secure(httpRequest.isSecure()).userPrincipal(httpRequest.getUserPrincipal()).isUserInRoleDelegate(new RequestContext.Builder.IsUserInRoleDelegate() {
            public boolean isUserInRole(String role) {
               return httpRequest.isUserInRole(role);
            }
         }).parameterMap(httpRequest.getParameterMap()).build();
         Enumeration headerNames = httpRequest.getHeaderNames();

         while(headerNames.hasMoreElements()) {
            String name = (String)headerNames.nextElement();
            Enumeration headerValues = httpRequest.getHeaders(name);

            while(headerValues.hasMoreElements()) {
               List values = (List)requestContext.getHeaders().get(name);
               if (values == null) {
                  requestContext.getHeaders().put(name, Utils.parseHeaderValue(((String)headerValues.nextElement()).trim()));
               } else {
                  values.addAll(Utils.parseHeaderValue(((String)headerValues.nextElement()).trim()));
               }
            }
         }

         Thread currentThread = Thread.currentThread();
         ClassLoader contextClassLoader = currentThread.getContextClassLoader();
         if (this.coherenceService != null) {
            this.coherenceService.setContextClassLoader(currentThread);
            String clusterConnectionId = this.getHeaderValue(httpRequest, "tyrus-cluster-connection-id");
            if (clusterConnectionId != null && requestContext.getHeaders().get("tyrus-cluster-connection-id") == null) {
               requestContext.getHeaders().put("tyrus-cluster-connection-id", Collections.singletonList(clusterConnectionId));
            }
         }

         try {
            TyrusUpgradeResponse webSocketResponse = new TyrusUpgradeResponse();
            WebSocketEngine.UpgradeInfo upgradeInfo = this.engine.upgrade(requestContext, webSocketResponse);
            switch (upgradeInfo.getStatus()) {
               case HANDSHAKE_FAILED:
                  httpResponse.sendError(webSocketResponse.getStatus());
                  break;
               case NOT_APPLICABLE:
                  filterChain.doFilter(request, response);
                  break;
               case SUCCESS:
                  debug("Upgrading Servlet request");

                  try {
                     httpResponse.setStatus(webSocketResponse.getStatus());
                     Iterator var20 = webSocketResponse.getHeaders().entrySet().iterator();

                     while(true) {
                        if (!var20.hasNext()) {
                           AsyncContext asyncContext = httpRequest.startAsync();
                           asyncContext.setTimeout(-1L);
                           webSocket.upgrade(httpSocket, httpRequest.getServletContext());
                           httpResponse.flushBuffer();
                           debug(rspi.toString());
                           break;
                        }

                        Map.Entry entry = (Map.Entry)var20.next();
                        httpResponse.addHeader((String)entry.getKey(), Utils.getHeaderFromList((List)entry.getValue()));
                     }
                  } catch (IOException var25) {
                     debug(rspi.toString());
                     currentThread.setContextClassLoader(contextClassLoader);
                     throw new ServletException(var25.getMessage(), var25);
                  }

                  Connection connection = upgradeInfo.createConnection(webSocketWriter, new Connection.CloseListener() {
                     public void close(CloseReason reason) {
                     }
                  });
                  HttpSession session = httpRequest.getSession(false);
                  this.websocketConnections.put(connection, session != null ? session.getId() : "");
                  this.websocketConnectionsPerServletResponseImpl.put(connection, rspi);
                  webSocket.setConnection(connection);
                  webSocketWriter.setConnection(connection);
                  webSocket.registerForReadEvent();
                  debug("Handshake Complete");
            }
         } finally {
            if (this.coherenceService != null) {
               currentThread.setContextClassLoader(contextClassLoader);
            }

         }
      } else {
         filterChain.doFilter(request, response);
      }

   }

   private void syncHttpSessionIfAny(ServletResponseImpl aRspi) {
      if (aRspi != null) {
         try {
            aRspi.syncSession();
         } catch (IOException var3) {
            if (WebSocketDebugLogger.isEnabled()) {
               WebSocketDebugLogger.debug("Got IOException for http syncSession() when closing the websocket connection.", var3);
            }
         }

      }
   }

   public void destroy() {
      Thread thread = Thread.currentThread();
      ClassLoader contextClassLoader = null;
      if (this.coherenceService != null) {
         contextClassLoader = this.coherenceService.setContextClassLoader(thread);
      }

      try {
         Iterator var3 = this.websocketConnections.keySet().iterator();

         while(var3.hasNext()) {
            Connection connection = (Connection)var3.next();
            this.websocketConnections.remove(connection);
            this.syncHttpSessionIfAny((ServletResponseImpl)this.websocketConnectionsPerServletResponseImpl.remove(connection));
            connection.close(new CloseReason(CloseCodes.GOING_AWAY, "Websocket endpoints get undeployed."));
         }

         this.serverContainer.stop();
      } finally {
         if (this.coherenceService != null) {
            thread.setContextClassLoader(contextClassLoader);
            this.coherenceService.deactivate();
         }

      }
   }

   private String getHeaderValue(HttpServletRequest req, String name) {
      String value = req.getHeader(name);
      if (value == null) {
         value = req.getParameter(name);
      }

      return value;
   }

   private static void debug(String message) {
      if (WebSocketDebugLogger.isEnabled()) {
         WebSocketDebugLogger.debug(message);
      }

   }
}
