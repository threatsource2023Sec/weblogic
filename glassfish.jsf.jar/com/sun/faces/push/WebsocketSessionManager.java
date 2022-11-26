package com.sun.faces.push;

import com.sun.faces.cdi.CdiUtils;
import com.sun.faces.util.Json;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.context.FacesContext;
import javax.faces.event.WebsocketEvent;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;

@ApplicationScoped
public class WebsocketSessionManager {
   private static final Logger logger = Logger.getLogger(WebsocketSessionManager.class.getName());
   private static final CloseReason REASON_EXPIRED;
   private static final AnnotationLiteral SESSION_OPENED;
   private static final AnnotationLiteral SESSION_CLOSED;
   private static final long TOMCAT_WEB_SOCKET_RETRY_TIMEOUT = 10L;
   private static final long TOMCAT_WEB_SOCKET_MAX_RETRIES = 100L;
   private static final String WARNING_TOMCAT_WEB_SOCKET_BOMBED = "Tomcat cannot handle concurrent push messages. A push message has been sent only after %s retries of 10ms apart. Consider rate limiting sending push messages. For example, once every 500ms.";
   private static final String ERROR_TOMCAT_WEB_SOCKET_BOMBED = "Tomcat cannot handle concurrent push messages. A push message could NOT be sent after %s retries of 10ms apart. Consider rate limiting sending push messages. For example, once every 500ms.";
   private final ConcurrentMap socketSessions = new ConcurrentHashMap();
   @Inject
   private WebsocketUserManager socketUsers;
   private static volatile WebsocketSessionManager instance;

   protected void register(String channelId) {
      if (!this.socketSessions.containsKey(channelId)) {
         this.socketSessions.putIfAbsent(channelId, new ConcurrentLinkedQueue());
      }

   }

   protected void register(Iterable channelIds) {
      Iterator var2 = channelIds.iterator();

      while(var2.hasNext()) {
         String channelId = (String)var2.next();
         this.register(channelId);
      }

   }

   protected boolean add(Session session) {
      String channelId = getChannelId(session);
      Collection sessions = (Collection)this.socketSessions.get(channelId);
      if (sessions != null && sessions.add(session)) {
         Serializable user = this.socketUsers.getUser(getChannel(session), channelId);
         if (user != null) {
            session.getUserProperties().put("user", user);
         }

         fireEvent(session, (CloseReason)null, SESSION_OPENED);
         return true;
      } else {
         return false;
      }
   }

   protected Set send(String channelId, Object message) {
      Collection sessions = channelId != null ? (Collection)this.socketSessions.get(channelId) : null;
      if (sessions != null && !sessions.isEmpty()) {
         Set results = new HashSet(sessions.size());
         String json = Json.encode(message);
         Iterator var6 = sessions.iterator();

         while(var6.hasNext()) {
            Session session = (Session)var6.next();
            if (session.isOpen()) {
               results.add(this.send(session, json, true));
            }
         }

         return results;
      } else {
         return Collections.emptySet();
      }
   }

   private Future send(Session session, String text, boolean retrySendTomcatWebSocket) {
      try {
         return session.getAsyncRemote().sendText(text);
      } catch (IllegalStateException var5) {
         if (session.getClass().getName().startsWith("org.apache.tomcat.websocket.") && var5.getMessage().contains("[TEXT_FULL_WRITING]")) {
            return retrySendTomcatWebSocket ? CompletableFuture.supplyAsync(() -> {
               return this.retrySendTomcatWebSocket(session, text);
            }) : null;
         } else {
            throw var5;
         }
      }
   }

   private Void retrySendTomcatWebSocket(Session session, String text) {
      int retries = 0;
      Exception cause = null;

      while(true) {
         ++retries;
         if ((long)retries >= 100L) {
            break;
         }

         try {
            Thread.sleep(10L);
            if (!session.isOpen()) {
               cause = new IllegalStateException("Too bad, session is now closed");
               break;
            }

            Future result = this.send(session, text, false);
            if (result != null) {
               if (logger.isLoggable(Level.WARNING)) {
                  logger.log(Level.WARNING, String.format("Tomcat cannot handle concurrent push messages. A push message has been sent only after %s retries of 10ms apart. Consider rate limiting sending push messages. For example, once every 500ms.", retries));
               }

               return (Void)result.get();
            }
         } catch (ExecutionException | InterruptedException var6) {
            Thread.currentThread().interrupt();
            cause = var6;
            break;
         }
      }

      throw new UnsupportedOperationException(String.format("Tomcat cannot handle concurrent push messages. A push message could NOT be sent after %s retries of 10ms apart. Consider rate limiting sending push messages. For example, once every 500ms.", retries), (Throwable)cause);
   }

   protected void remove(Session session, CloseReason reason) {
      Collection sessions = (Collection)this.socketSessions.get(getChannelId(session));
      if (sessions != null && sessions.remove(session)) {
         fireEvent(session, reason, SESSION_CLOSED);
      }

   }

   protected void deregister(Iterable channelIds) {
      Iterator var2 = channelIds.iterator();

      while(true) {
         Collection sessions;
         do {
            if (!var2.hasNext()) {
               return;
            }

            String channelId = (String)var2.next();
            sessions = (Collection)this.socketSessions.remove(channelId);
         } while(sessions == null);

         Iterator var5 = sessions.iterator();

         while(var5.hasNext()) {
            Session session = (Session)var5.next();
            if (session.isOpen()) {
               try {
                  session.close(REASON_EXPIRED);
               } catch (IOException var8) {
               }
            }
         }
      }
   }

   static WebsocketSessionManager getInstance() {
      if (instance == null) {
         instance = (WebsocketSessionManager)CdiUtils.getBeanReference(WebsocketSessionManager.class);
      }

      return instance;
   }

   private static String getChannel(Session session) {
      return (String)session.getPathParameters().get("channel");
   }

   private static String getChannelId(Session session) {
      return session.getQueryString();
   }

   private static void fireEvent(Session session, CloseReason reason, AnnotationLiteral qualifier) {
      Serializable user = (Serializable)session.getUserProperties().get("user");
      Util.getCdiBeanManager(FacesContext.getCurrentInstance()).fireEvent(new WebsocketEvent(getChannel(session), user, reason != null ? reason.getCloseCode() : null), new Annotation[]{qualifier});
   }

   static {
      REASON_EXPIRED = new CloseReason(CloseCodes.NORMAL_CLOSURE, "Expired");
      SESSION_OPENED = new AnnotationLiteral() {
         private static final long serialVersionUID = 1L;
      };
      SESSION_CLOSED = new AnnotationLiteral() {
         private static final long serialVersionUID = 1L;
      };
   }
}
