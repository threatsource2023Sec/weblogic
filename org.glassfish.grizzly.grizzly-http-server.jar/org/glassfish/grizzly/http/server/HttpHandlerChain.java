package org.glassfish.grizzly.http.server;

import java.io.CharConversionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.jmxbase.JmxEventListener;
import org.glassfish.grizzly.http.server.jmxbase.Monitorable;
import org.glassfish.grizzly.http.server.naming.NamingContext;
import org.glassfish.grizzly.http.server.util.DispatcherHelper;
import org.glassfish.grizzly.http.server.util.Mapper;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.RequestURIRef;
import org.glassfish.grizzly.localization.LogMessages;

public class HttpHandlerChain extends HttpHandler implements JmxEventListener {
   private static final Logger LOGGER = Grizzly.logger(HttpHandlerChain.class);
   private static final Map ROOT_URLS = new HashMap(3);
   private final FullUrlPathResolver fullUrlPathResolver = new FullUrlPathResolver(this);
   private final ConcurrentMap handlersByName = new ConcurrentHashMap();
   private final ReentrantReadWriteLock mapperUpdateLock = new ReentrantReadWriteLock();
   private final ConcurrentMap handlers = new ConcurrentHashMap();
   private final ConcurrentMap monitors = new ConcurrentHashMap();
   private int handlersCount;
   private volatile RootHttpHandler rootHttpHandler;
   private final Mapper mapper;
   private final DispatcherHelper dispatchHelper;
   private static final String LOCAL_HOST = "localhost";
   private boolean started;
   private final HttpServer httpServer;
   private boolean isRootConfigured = false;

   public HttpHandlerChain(HttpServer httpServer) {
      this.httpServer = httpServer;
      this.mapper = new Mapper();
      this.mapper.setDefaultHostName("localhost");
      this.dispatchHelper = new DispatchHelperImpl();
      this.setDecodeUrl(false);
   }

   public void jmxEnabled() {
      this.mapperUpdateLock.readLock().lock();

      try {
         Iterator var1 = this.handlers.keySet().iterator();

         while(var1.hasNext()) {
            HttpHandler httpHandler = (HttpHandler)var1.next();
            if (httpHandler instanceof Monitorable) {
               this.registerJmxForHandler(httpHandler);
            }
         }
      } finally {
         this.mapperUpdateLock.readLock().unlock();
      }

   }

   public void jmxDisabled() {
      this.mapperUpdateLock.readLock().lock();

      try {
         Iterator var1 = this.handlers.keySet().iterator();

         while(var1.hasNext()) {
            HttpHandler httpHandler = (HttpHandler)var1.next();
            if (httpHandler instanceof Monitorable) {
               this.deregisterJmxForHandler(httpHandler);
            }
         }
      } finally {
         this.mapperUpdateLock.readLock().unlock();
      }

   }

   boolean doHandle(Request request, Response response) throws Exception {
      response.setErrorPageGenerator(this.getErrorPageGenerator(request));

      try {
         RootHttpHandler rootHttpHandlerLocal = this.rootHttpHandler;
         if (rootHttpHandlerLocal != null) {
            HttpHandler rh = rootHttpHandlerLocal.httpHandler;
            rootHttpHandlerLocal.pathUpdater.update(this, rh, request);
            return rh.doHandle(request, response);
         }

         RequestURIRef uriRef = request.getRequest().getRequestURIRef();
         uriRef.setDefaultURIEncoding(this.getRequestURIEncoding());
         DataChunk decodedURI = uriRef.getDecodedRequestURIBC(this.isAllowEncodedSlash());
         MappingData mappingData = request.obtainMappingData();
         this.mapper.mapUriWithSemicolon((HttpRequestPacket)request.getRequest(), decodedURI, mappingData, 0);
         if (mappingData.context != null && mappingData.context instanceof HttpHandler) {
            HttpHandler httpHandler;
            if (mappingData.wrapper != null) {
               httpHandler = (HttpHandler)mappingData.wrapper;
            } else {
               httpHandler = (HttpHandler)mappingData.context;
            }

            updatePaths(request, mappingData);
            return httpHandler.doHandle(request, response);
         }

         response.sendError(404);
      } catch (Exception var9) {
         Exception t = var9;

         try {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Internal server error", t);
            }
         } catch (Exception var8) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLERCHAIN_ERRORPAGE(), var8);
            }
         }
      }

      return true;
   }

   public void service(Request request, Response response) throws Exception {
      throw new IllegalStateException("Method doesn't have to be called");
   }

   public void addHandler(HttpHandler httpHandler, String[] mappings) {
   }

   public void addHandler(HttpHandler httpHandler, HttpHandlerRegistration[] mappings) {
      this.mapperUpdateLock.writeLock().lock();

      try {
         if (mappings.length == 0) {
            this.addHandler(httpHandler, new String[]{""});
         } else {
            if (this.started) {
               httpHandler.start();
               if (httpHandler instanceof Monitorable) {
                  this.registerJmxForHandler(httpHandler);
               }
            }

            if (this.handlers.put(httpHandler, mappings) == null) {
               ++this.handlersCount;
            }

            String name = httpHandler.getName();
            if (name != null) {
               this.handlersByName.put(name, httpHandler);
            }

            httpHandler.setDispatcherHelper(this.dispatchHelper);
            HttpHandlerRegistration[] var4 = mappings;
            int var5 = mappings.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               HttpHandlerRegistration reg = var4[var6];
               String ctx = reg.getContextPath();
               String wrapper = reg.getUrlPattern();
               if (ctx.length() != 0) {
                  this.mapper.addContext("localhost", ctx, httpHandler, new String[]{"index.html", "index.htm"}, (NamingContext)null);
               } else if (!this.isRootConfigured && wrapper.startsWith("*.")) {
                  this.isRootConfigured = true;
                  HttpHandler a = new HttpHandler() {
                     public void service(Request request, Response response) throws IOException {
                        response.sendError(404);
                     }
                  };
                  this.mapper.addContext("localhost", ctx, a, new String[]{"index.html", "index.htm"}, (NamingContext)null);
               } else {
                  this.mapper.addContext("localhost", ctx, httpHandler, new String[]{"index.html", "index.htm"}, (NamingContext)null);
               }

               this.mapper.addWrapper("localhost", ctx, wrapper, httpHandler);
            }

            if (this.handlersCount == 1 && mappings.length == 1 && ROOT_URLS.containsKey(mappings[0])) {
               this.rootHttpHandler = new RootHttpHandler(httpHandler, (PathUpdater)ROOT_URLS.get(mappings[0]));
            } else {
               this.rootHttpHandler = null;
            }
         }
      } finally {
         this.mapperUpdateLock.writeLock().unlock();
      }

   }

   public boolean removeHttpHandler(HttpHandler httpHandler) {
      if (httpHandler == null) {
         throw new IllegalStateException();
      } else {
         this.mapperUpdateLock.writeLock().lock();

         boolean var13;
         try {
            String name = httpHandler.getName();
            if (name != null) {
               this.handlersByName.remove(name);
            }

            HttpHandlerRegistration[] mappings = (HttpHandlerRegistration[])this.handlers.remove(httpHandler);
            if (mappings != null) {
               HttpHandlerRegistration[] var4 = mappings;
               int var5 = mappings.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  HttpHandlerRegistration mapping = var4[var6];
                  String contextPath = mapping.getContextPath();
                  this.mapper.removeWrapper("localhost", contextPath, mapping.getUrlPattern());
                  if (this.mapper.getWrapperNames("localhost", name).length == 0) {
                     this.mapper.removeContext("localhost", contextPath);
                  }
               }

               this.deregisterJmxForHandler(httpHandler);
               httpHandler.destroy();
               --this.handlersCount;
               if (this.handlersCount == 1) {
                  Map.Entry entry = (Map.Entry)this.handlers.entrySet().iterator().next();
                  HttpHandlerRegistration[] lastHttpHandlerMappings = (HttpHandlerRegistration[])entry.getValue();
                  if (lastHttpHandlerMappings.length == 1 && ROOT_URLS.containsKey(lastHttpHandlerMappings[0])) {
                     this.rootHttpHandler = new RootHttpHandler(httpHandler, (PathUpdater)ROOT_URLS.get(lastHttpHandlerMappings[0]));
                  } else {
                     this.rootHttpHandler = null;
                  }
               } else {
                  this.rootHttpHandler = null;
               }
            }

            var13 = mappings != null;
         } finally {
            this.mapperUpdateLock.writeLock().unlock();
         }

         return var13;
      }
   }

   public void removeAllHttpHandlers() {
      this.mapperUpdateLock.writeLock().lock();

      try {
         Iterator var1 = this.handlers.keySet().iterator();

         while(var1.hasNext()) {
            HttpHandler handler = (HttpHandler)var1.next();
            this.removeHttpHandler(handler);
         }
      } finally {
         this.mapperUpdateLock.writeLock().unlock();
      }

   }

   public synchronized void start() {
      this.mapperUpdateLock.readLock().lock();

      try {
         Iterator var1 = this.handlers.keySet().iterator();

         while(var1.hasNext()) {
            HttpHandler httpHandler = (HttpHandler)var1.next();
            httpHandler.start();
         }
      } finally {
         this.mapperUpdateLock.readLock().unlock();
      }

      this.started = true;
   }

   public synchronized void destroy() {
      this.mapperUpdateLock.writeLock().lock();

      try {
         Iterator var1 = this.handlers.keySet().iterator();

         while(var1.hasNext()) {
            HttpHandler httpHandler = (HttpHandler)var1.next();
            httpHandler.destroy();
         }
      } finally {
         this.mapperUpdateLock.writeLock().unlock();
      }

      this.started = false;
   }

   private void registerJmxForHandler(HttpHandler httpHandler) {
      Monitorable monitorable = (Monitorable)httpHandler;
      Object jmx = monitorable.createManagementObject();
      if (this.monitors.putIfAbsent(httpHandler, jmx) == null) {
         this.httpServer.jmxManager.register(this.httpServer.managementObject, jmx);
      }

   }

   private void deregisterJmxForHandler(HttpHandler httpHandler) {
      Object jmx = this.monitors.remove(httpHandler);
      if (jmx != null) {
         this.httpServer.jmxManager.deregister(jmx);
      }

   }

   static {
      ROOT_URLS.put(HttpHandlerRegistration.fromString(""), new EmptyPathUpdater());
      ROOT_URLS.put(HttpHandlerRegistration.fromString("/"), new SlashPathUpdater());
      ROOT_URLS.put(HttpHandlerRegistration.fromString("/*"), new SlashStarPathUpdater());
   }

   private static class FullUrlPathResolver implements Request.PathResolver {
      private final HttpHandler httpHandler;

      public FullUrlPathResolver(HttpHandler httpHandler) {
         this.httpHandler = httpHandler;
      }

      public String resolve(Request request) {
         try {
            RequestURIRef uriRef = request.getRequest().getRequestURIRef();
            uriRef.setDefaultURIEncoding(this.httpHandler.getRequestURIEncoding());
            DataChunk decodedURI = uriRef.getDecodedRequestURIBC(this.httpHandler.isAllowEncodedSlash());
            int pos = decodedURI.indexOf(';', 0);
            return pos < 0 ? decodedURI.toString() : decodedURI.toString(0, pos);
         } catch (CharConversionException var5) {
            throw new IllegalStateException(var5);
         }
      }
   }

   private static class EmptyPathUpdater implements PathUpdater {
      private EmptyPathUpdater() {
      }

      public void update(HttpHandlerChain handlerChain, HttpHandler httpHandler, Request request) {
         request.setContextPath("");
         request.setPathInfo((String)null);
         request.setHttpHandlerPath((String)null);
      }

      // $FF: synthetic method
      EmptyPathUpdater(Object x0) {
         this();
      }
   }

   private static class SlashStarPathUpdater implements PathUpdater {
      private SlashStarPathUpdater() {
      }

      public void update(HttpHandlerChain handlerChain, HttpHandler httpHandler, Request request) {
         request.setContextPath("");
         request.setPathInfo((Request.PathResolver)handlerChain.fullUrlPathResolver);
         request.setHttpHandlerPath("");
      }

      // $FF: synthetic method
      SlashStarPathUpdater(Object x0) {
         this();
      }
   }

   private static class SlashPathUpdater implements PathUpdater {
      private SlashPathUpdater() {
      }

      public void update(HttpHandlerChain handlerChain, HttpHandler httpHandler, Request request) {
         request.setContextPath("");
         request.setPathInfo((String)null);
         request.setHttpHandlerPath((Request.PathResolver)handlerChain.fullUrlPathResolver);
      }

      // $FF: synthetic method
      SlashPathUpdater(Object x0) {
         this();
      }
   }

   private interface PathUpdater {
      void update(HttpHandlerChain var1, HttpHandler var2, Request var3);
   }

   private static final class RootHttpHandler {
      private final HttpHandler httpHandler;
      private final PathUpdater pathUpdater;

      public RootHttpHandler(HttpHandler httpHandler, PathUpdater pathUpdater) {
         this.httpHandler = httpHandler;
         this.pathUpdater = pathUpdater;
      }
   }

   private final class DispatchHelperImpl implements DispatcherHelper {
      private DispatchHelperImpl() {
      }

      public void mapPath(HttpRequestPacket requestPacket, DataChunk path, MappingData mappingData) throws Exception {
         HttpHandlerChain.this.mapper.map(requestPacket, path, mappingData);
      }

      public void mapName(DataChunk name, MappingData mappingData) {
         String nameStr = name.toString();
         HttpHandler handler = (HttpHandler)HttpHandlerChain.this.handlersByName.get(nameStr);
         if (handler != null) {
            mappingData.wrapper = handler;
            mappingData.servletName = nameStr;
         }

      }

      // $FF: synthetic method
      DispatchHelperImpl(Object x1) {
         this();
      }
   }
}
