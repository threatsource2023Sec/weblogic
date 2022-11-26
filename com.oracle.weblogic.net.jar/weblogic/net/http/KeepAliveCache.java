package weblogic.net.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import weblogic.utils.AssertionError;

public final class KeepAliveCache {
   private static int LIFETIME = 15000;
   private static int PROXY_LIFETIME = 20000;
   private static int INTERVAL_RATE = 2;
   private static int HEALTH_CHECK_TIMOUT = 0;
   private Timer timer = new Timer(true);
   private final ConcurrentMap cache = new ConcurrentHashMap();
   private Random random = new Random();

   public void put(HttpClient http) {
      if (this.isClientTimeout(http)) {
         http.closeServer();
      } else {
         final KeepAliveKey key = new KeepAliveKey(http.getURL(), http.getClientInfo(), http.instProxy);
         List clients = (List)this.cache.get(key);
         if (HttpURLConnection.debug) {
            HttpURLConnection.p("cache connection: " + http);
         }

         if (clients == null) {
            List clients = new LinkedList();
            List oldClients = (List)this.cache.putIfAbsent(key, clients);
            if (oldClients != null) {
               synchronized(oldClients) {
                  oldClients.add(http);
                  return;
               }
            }

            synchronized(clients) {
               clients.add(http);
            }

            int life = http.usingProxy ? PROXY_LIFETIME : LIFETIME;
            TimerTask task = new TimerTask() {
               public void run() {
                  List clients = (List)KeepAliveCache.this.cache.get(key);
                  if (clients == null) {
                     this.cancel();
                  } else {
                     synchronized(clients) {
                        if (clients.isEmpty()) {
                           if (HttpURLConnection.debug) {
                              HttpURLConnection.p("cancel cleanup timer task: " + this);
                           }

                           KeepAliveCache.this.cache.remove(key);
                           this.cancel();
                        } else {
                           int i;
                           for(i = 0; i < clients.size(); ++i) {
                              HttpClient client = (HttpClient)clients.get(i);
                              if (!KeepAliveCache.this.isClientTimeout(client)) {
                                 break;
                              }

                              if (HttpURLConnection.debug) {
                                 HttpURLConnection.p("cleanup invalid client: " + client);
                              }

                              client.closeServer();
                           }

                           clients.subList(0, i).clear();
                        }
                     }
                  }
               }
            };

            try {
               if (HttpURLConnection.debug) {
                  HttpURLConnection.p("init timer task: " + task);
               }

               this.timer.schedule(task, (long)life, (long)(life / INTERVAL_RATE));
            } catch (IllegalStateException var11) {
               throw new AssertionError("Keep-Alive timer task cancelled: " + task);
            }
         } else {
            synchronized(clients) {
               clients.add(http);
            }
         }

      }
   }

   public HttpClient get(URL url, Object info) {
      return this.get(url, info, (Proxy)null);
   }

   public HttpClient get(URL url, Object info, Proxy proxy) {
      KeepAliveKey key = new KeepAliveKey(url, info, proxy);
      List clients = (List)this.cache.get(key);
      if (clients != null && !clients.isEmpty()) {
         HttpClient client = null;
         synchronized(clients) {
            if (clients.size() == 0) {
               return null;
            }

            client = (HttpClient)clients.remove(this.random.nextInt(clients.size()));
         }

         if (client != null && !this.isClientTimeout(client)) {
            if (HEALTH_CHECK_TIMOUT > 0 && !this.isSocketHealthy(client)) {
               client.closeServer();
               return null;
            } else {
               if (HttpURLConnection.debug) {
                  HttpURLConnection.p("reuse connection from cache: " + client);
               }

               return client;
            }
         } else {
            if (client != null) {
               client.closeServer();
            }

            return null;
         }
      } else {
         if (HttpURLConnection.debug) {
            HttpURLConnection.p("doesn't hit for: " + key);
         }

         return null;
      }
   }

   private boolean isClientTimeout(HttpClient http) {
      long sinceLastUsed = System.currentTimeMillis() - http.lastUsed;
      return http.usingProxy ? sinceLastUsed > (long)PROXY_LIFETIME : sinceLastUsed > (long)LIFETIME;
   }

   private boolean isSocketHealthy(HttpClient http) {
      try {
         http.serverSocket.setSoTimeout(HEALTH_CHECK_TIMOUT);
         http.serverSocket.getInputStream().read();
      } catch (SocketTimeoutException var3) {
         return true;
      } catch (SocketException var4) {
      } catch (IOException var5) {
      }

      return false;
   }

   static {
      try {
         LIFETIME = SecurityHelper.getInteger("http.keepAliveCache.lifeTime", LIFETIME);
         PROXY_LIFETIME = SecurityHelper.getInteger("http.keepAliveCache.proxyLifeTime", PROXY_LIFETIME);
         HEALTH_CHECK_TIMOUT = SecurityHelper.getInteger("http.keepAliveCache.socketHealthCheckTimeout", HEALTH_CHECK_TIMOUT);
      } catch (SecurityException var1) {
      }

   }
}
