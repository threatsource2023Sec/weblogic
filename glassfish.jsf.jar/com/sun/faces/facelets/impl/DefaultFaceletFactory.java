package com.sun.faces.facelets.impl;

import com.sun.faces.context.FacesFileNotFoundException;
import com.sun.faces.facelets.compiler.Compiler;
import com.sun.faces.util.Cache;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.Facelet;
import javax.faces.view.facelets.FaceletCache;
import javax.faces.view.facelets.FaceletCacheFactory;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.ResourceResolver;

public class DefaultFaceletFactory {
   protected static final Logger log;
   private Compiler compiler;
   private ResourceResolver resolver;
   private URL baseUrl;
   private long refreshPeriod;
   private FaceletCache cache;
   private ConcurrentMap cachePerContract;
   Cache idMappers;

   public DefaultFaceletFactory() {
      this.compiler = null;
      this.resolver = null;
      this.refreshPeriod = -1L;
      this.cache = null;
      this.baseUrl = null;
   }

   public DefaultFaceletFactory(Compiler compiler, ResourceResolver resolver) throws IOException {
      this(compiler, resolver, -1L, (FaceletCache)null);
   }

   public DefaultFaceletFactory(Compiler compiler, ResourceResolver resolver, long refreshPeriod) {
      this(compiler, resolver, refreshPeriod, (FaceletCache)null);
   }

   public DefaultFaceletFactory(Compiler compiler, ResourceResolver resolver, long refreshPeriod, FaceletCache cache) {
      this.init(compiler, resolver, refreshPeriod, cache);
   }

   public final void init(Compiler compiler, ResourceResolver resolver, long refreshPeriod, FaceletCache cache) {
      Util.notNull("compiler", compiler);
      Util.notNull("resolver", resolver);
      this.compiler = compiler;
      this.cachePerContract = new ConcurrentHashMap();
      this.resolver = resolver;
      this.baseUrl = resolver.resolveUrl("/");
      this.idMappers = new Cache(new IdMapperFactory());
      refreshPeriod = refreshPeriod >= 0L ? refreshPeriod * 1000L : -1L;
      this.refreshPeriod = refreshPeriod;
      if (log.isLoggable(Level.FINE)) {
         log.log(Level.FINE, "Using ResourceResolver: {0}", resolver);
         log.log(Level.FINE, "Using Refresh Period: {0}", refreshPeriod);
      }

      this.cache = this.initCache(cache);
   }

   private FaceletCache initCache(FaceletCache cache) {
      if (cache == null) {
         FaceletCacheFactory cacheFactory = (FaceletCacheFactory)FactoryFinder.getFactory("javax.faces.view.facelets.FaceletCacheFactory");
         cache = cacheFactory.getFaceletCache();
      }

      FaceletCache.MemberFactory faceletFactory = new FaceletCache.MemberFactory() {
         public DefaultFacelet newInstance(URL key) throws IOException {
            return DefaultFaceletFactory.this.createFacelet(key);
         }
      };
      FaceletCache.MemberFactory metadataFaceletFactory = new FaceletCache.MemberFactory() {
         public DefaultFacelet newInstance(URL key) throws IOException {
            return DefaultFaceletFactory.this.createMetadataFacelet(key);
         }
      };
      cache.setCacheFactories(faceletFactory, metadataFaceletFactory);
      return cache;
   }

   public ResourceResolver getResourceResolver() {
      return this.resolver;
   }

   public Facelet getFacelet(FacesContext context, String uri) throws IOException {
      return this.getFacelet(context, this.resolveURL(uri));
   }

   public Facelet getMetadataFacelet(FacesContext context, String uri) throws IOException {
      return this.getMetadataFacelet(context, this.resolveURL(uri));
   }

   public URL resolveURL(URL source, String path) throws IOException {
      if (path.startsWith("/")) {
         URL url = this.resolver.resolveUrl(path);
         if (url == null) {
            throw new FacesFileNotFoundException(path + " Not Found in ExternalContext as a Resource");
         } else {
            return url;
         }
      } else {
         return new URL(source, path);
      }
   }

   public Facelet getFacelet(FacesContext context, URL url) throws IOException {
      Facelet result = (Facelet)this.getCache(context).getFacelet(url);
      DefaultFacelet _facelet = null;
      if (result instanceof DefaultFacelet) {
         _facelet = (DefaultFacelet)result;
         String docType = _facelet.getSavedDoctype();
         if (null != docType) {
            Util.saveDOCTYPEToFacesContextAttributes(docType);
         }

         String xmlDecl = _facelet.getSavedXMLDecl();
         if (null != xmlDecl) {
            Util.saveXMLDECLToFacesContextAttributes(xmlDecl);
         }
      }

      return result;
   }

   public Facelet getMetadataFacelet(FacesContext context, URL url) throws IOException {
      return (Facelet)this.getCache(context).getViewMetadataFacelet(url);
   }

   public boolean needsToBeRefreshed(URL url) {
      if (!this.cache.isFaceletCached(url)) {
         return true;
      } else if (this.cachePerContract == null) {
         return false;
      } else {
         Iterator var2 = this.cachePerContract.values().iterator();

         FaceletCache faceletCache;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            faceletCache = (FaceletCache)var2.next();
         } while(faceletCache.isFaceletCached(url));

         return true;
      }
   }

   private FaceletCache getCache(FacesContext context) {
      List contracts = context.getResourceLibraryContracts();
      if (!contracts.isEmpty()) {
         StringBuilder builder = new StringBuilder();

         for(int i = 0; i < contracts.size(); ++i) {
            builder.append((String)contracts.get(i));
            if (i + 1 != contracts.size()) {
               builder.append(",");
            }
         }

         String contractsKey = builder.toString();
         FaceletCache faceletCache = (FaceletCache)this.cachePerContract.get(contractsKey);
         if (faceletCache == null) {
            faceletCache = this.initCache((FaceletCache)null);
            this.cachePerContract.putIfAbsent(contractsKey, faceletCache);
            faceletCache = (FaceletCache)this.cachePerContract.get(contractsKey);
         }

         return faceletCache;
      } else {
         return this.cache;
      }
   }

   private URL resolveURL(String uri) throws IOException {
      URL url = this.resolveURL(this.baseUrl, uri);
      if (url == null) {
         throw new IOException("'" + uri + "' not found.");
      } else {
         return url;
      }
   }

   public UIComponent _createComponent(FacesContext context, String taglibURI, String tagName, Map attributes) {
      UIComponent result = null;
      Application app = context.getApplication();
      ExternalContext extContext = context.getExternalContext();
      File tmpDir = (File)extContext.getApplicationMap().get("javax.servlet.context.tempdir");
      File tempFile = null;
      OutputStreamWriter osw = null;
      boolean var26 = false;

      label320: {
         boolean successful;
         label321: {
            label322: {
               try {
                  var26 = true;
                  tempFile = File.createTempFile("mojarra", ".tmp", tmpDir);
                  osw = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");
                  osw.append("<?xml version='1.0' encoding='");
                  osw.append("UTF-8");
                  osw.append("' ?>");
                  osw.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
                  osw.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"\n");
                  osw.append("      xmlns:j=\"").append(taglibURI).append("\">");
                  osw.append("  <j:").append(tagName).append(" ");
                  if (null != attributes && !attributes.isEmpty()) {
                     Iterator var11 = attributes.entrySet().iterator();

                     while(var11.hasNext()) {
                        Map.Entry attr = (Map.Entry)var11.next();
                        osw.append((CharSequence)attr.getKey()).append("=\"").append(attr.getValue().toString()).append("\"").append(" ");
                     }
                  }

                  String tempId = context.getViewRoot().createUniqueId(context, tagName);
                  osw.append(" id=\"").append(tempId).append("\" />");
                  osw.append("</html>");

                  try {
                     osw.flush();
                     osw.close();
                  } catch (IOException var32) {
                     if (log.isLoggable(Level.FINEST)) {
                        log.log(Level.FINEST, "Flushing and closing stream", var32);
                     }
                  }

                  URL fabricatedFaceletPage = tempFile.toURI().toURL();
                  Facelet f = this.createFacelet(fabricatedFaceletPage);
                  UIComponent tmp = app.createComponent("javax.faces.NamingContainer");
                  tmp.setId(context.getViewRoot().createUniqueId());
                  f.apply(context, tmp);
                  result = tmp.findComponent(tempId);
                  tmp.getChildren().clear();
                  osw = null;
                  var26 = false;
                  break label321;
               } catch (MalformedURLException var33) {
                  if (log.isLoggable(Level.FINEST)) {
                     log.log(Level.FINEST, "Invalid URL", var33);
                     var26 = false;
                  } else {
                     var26 = false;
                  }
               } catch (IOException var34) {
                  if (log.isLoggable(Level.FINEST)) {
                     log.log(Level.FINEST, "I/O error", var34);
                     var26 = false;
                     break label322;
                  }

                  var26 = false;
                  break label322;
               } finally {
                  if (var26) {
                     if (null != osw) {
                        try {
                           osw.close();
                        } catch (IOException var27) {
                           if (log.isLoggable(Level.FINEST)) {
                              log.log(Level.FINEST, "Closing stream", var27);
                           }
                        }
                     }

                     if (null != tempFile) {
                        boolean successful = tempFile.delete();
                        if (!successful && log.isLoggable(Level.FINEST)) {
                           log.log(Level.FINEST, "Unable to delete temporary file.");
                        }
                     }

                  }
               }

               if (null != osw) {
                  try {
                     osw.close();
                  } catch (IOException var30) {
                     if (log.isLoggable(Level.FINEST)) {
                        log.log(Level.FINEST, "Closing stream", var30);
                     }
                  }
               }

               if (null != tempFile) {
                  successful = tempFile.delete();
                  if (!successful && log.isLoggable(Level.FINEST)) {
                     log.log(Level.FINEST, "Unable to delete temporary file.");
                  }
               }
               break label320;
            }

            if (null != osw) {
               try {
                  osw.close();
               } catch (IOException var31) {
                  if (log.isLoggable(Level.FINEST)) {
                     log.log(Level.FINEST, "Closing stream", var31);
                  }
               }
            }

            if (null != tempFile) {
               successful = tempFile.delete();
               if (!successful && log.isLoggable(Level.FINEST)) {
                  log.log(Level.FINEST, "Unable to delete temporary file.");
               }
            }
            break label320;
         }

         if (null != osw) {
            try {
               osw.close();
            } catch (IOException var29) {
               if (log.isLoggable(Level.FINEST)) {
                  log.log(Level.FINEST, "Closing stream", var29);
               }
            }
         }

         if (null != tempFile) {
            successful = tempFile.delete();
            if (!successful && log.isLoggable(Level.FINEST)) {
               log.log(Level.FINEST, "Unable to delete temporary file.");
            }
         }
      }

      try {
         byte[] faceletPage = "facelet".getBytes("UTF-8");
         new ByteArrayInputStream(faceletPage);
      } catch (UnsupportedEncodingException var28) {
         if (log.isLoggable(Level.SEVERE)) {
            log.log(Level.SEVERE, "Unsupported encoding when creating component for " + tagName + " in " + taglibURI, var28);
         }
      }

      if (null != result) {
         result.setId((String)null);
      }

      return result;
   }

   private DefaultFacelet createFacelet(URL url) throws IOException {
      if (log.isLoggable(Level.FINE)) {
         log.fine("Creating Facelet for: " + url);
      }

      String escapedBaseURL = Pattern.quote(this.baseUrl.getFile());
      String alias = '/' + url.getFile().replaceFirst(escapedBaseURL, "");

      try {
         FaceletHandler h = this.compiler.compile(url, alias);
         return new DefaultFacelet(this, this.compiler.createExpressionFactory(), url, alias, h);
      } catch (FileNotFoundException var5) {
         throw new FileNotFoundException("Facelet " + alias + " not found at: " + url.toExternalForm());
      }
   }

   private DefaultFacelet createMetadataFacelet(URL url) throws IOException {
      if (log.isLoggable(Level.FINE)) {
         log.fine("Creating Metadata Facelet for: " + url);
      }

      String escapedBaseURL = Pattern.quote(this.baseUrl.getFile());
      String alias = '/' + url.getFile().replaceFirst(escapedBaseURL, "");

      try {
         FaceletHandler h = this.compiler.metadataCompile(url, alias);
         return new DefaultFacelet(this, this.compiler.createExpressionFactory(), url, alias, h);
      } catch (FileNotFoundException var5) {
         throw new FileNotFoundException("Facelet " + alias + " not found at: " + url.toExternalForm());
      }
   }

   public long getRefreshPeriod() {
      return this.refreshPeriod;
   }

   static {
      log = FacesLogger.FACELETS_FACTORY.getLogger();
   }

   private static final class IdMapperFactory implements Cache.Factory {
      private IdMapperFactory() {
      }

      public IdMapper newInstance(String arg) throws InterruptedException {
         return new IdMapper();
      }

      // $FF: synthetic method
      IdMapperFactory(Object x0) {
         this();
      }
   }
}
