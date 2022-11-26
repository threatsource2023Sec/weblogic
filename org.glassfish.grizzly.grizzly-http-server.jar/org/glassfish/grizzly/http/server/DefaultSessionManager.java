package org.glassfish.grizzly.http.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.http.Cookie;

public class DefaultSessionManager implements SessionManager {
   private final ConcurrentMap sessions;
   private final Random rnd;
   private String sessionCookieName;
   private final ScheduledThreadPoolExecutor sessionExpirer;

   public static SessionManager instance() {
      return DefaultSessionManager.LazyHolder.INSTANCE;
   }

   private DefaultSessionManager() {
      this.sessions = new ConcurrentHashMap();
      this.rnd = new Random();
      this.sessionCookieName = "JSESSIONID";
      this.sessionExpirer = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
         public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Grizzly-HttpSession-Expirer");
            t.setDaemon(true);
            return t;
         }
      });
      this.sessionExpirer.scheduleAtFixedRate(new Runnable() {
         public void run() {
            long currentTime = System.currentTimeMillis();
            Iterator iterator = DefaultSessionManager.this.sessions.entrySet().iterator();

            while(true) {
               Session session;
               do {
                  if (!iterator.hasNext()) {
                     return;
                  }

                  Map.Entry entry = (Map.Entry)iterator.next();
                  session = (Session)entry.getValue();
               } while(session.isValid() && (session.getSessionTimeout() <= 0L || currentTime - session.getTimestamp() <= session.getSessionTimeout()));

               session.setValid(false);
               iterator.remove();
            }
         }
      }, 5L, 5L, TimeUnit.SECONDS);
   }

   public Session getSession(Request request, String requestedSessionId) {
      if (requestedSessionId != null) {
         Session session = (Session)this.sessions.get(requestedSessionId);
         if (session != null && session.isValid()) {
            return session;
         }
      }

      return null;
   }

   public Session createSession(Request request) {
      Session session = new Session();

      String requestedSessionId;
      do {
         requestedSessionId = String.valueOf(this.generateRandomLong());
         session.setIdInternal(requestedSessionId);
      } while(this.sessions.putIfAbsent(requestedSessionId, session) != null);

      return session;
   }

   public String changeSessionId(Request request, Session session) {
      String oldSessionId = session.getIdInternal();
      String newSessionId = String.valueOf(this.generateRandomLong());
      session.setIdInternal(newSessionId);
      this.sessions.remove(oldSessionId);
      this.sessions.put(newSessionId, session);
      return oldSessionId;
   }

   public void configureSessionCookie(Request request, Cookie cookie) {
   }

   public void setSessionCookieName(String name) {
      if (name != null && !name.isEmpty()) {
         this.sessionCookieName = name;
      }

   }

   public String getSessionCookieName() {
      return this.sessionCookieName;
   }

   private long generateRandomLong() {
      return this.rnd.nextLong() & Long.MAX_VALUE;
   }

   // $FF: synthetic method
   DefaultSessionManager(Object x0) {
      this();
   }

   private static class LazyHolder {
      private static final DefaultSessionManager INSTANCE = new DefaultSessionManager();
   }
}
