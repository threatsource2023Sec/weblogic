package com.sun.faces.config;

import com.sun.faces.config.configprovider.ConfigurationResourceProvider;
import com.sun.faces.config.configprovider.MetaInfResourceProvider;
import com.sun.faces.config.configprovider.RIConfigResourceProvider;
import com.sun.faces.config.configprovider.WebResourceProvider;
import com.sun.faces.config.processor.ApplicationConfigProcessor;
import com.sun.faces.config.processor.ComponentConfigProcessor;
import com.sun.faces.config.processor.ConfigProcessor;
import com.sun.faces.config.processor.ConverterConfigProcessor;
import com.sun.faces.config.processor.FactoryConfigProcessor;
import com.sun.faces.config.processor.LifecycleConfigProcessor;
import com.sun.faces.config.processor.ManagedBeanConfigProcessor;
import com.sun.faces.config.processor.NavigationConfigProcessor;
import com.sun.faces.config.processor.RenderKitConfigProcessor;
import com.sun.faces.config.processor.ValidatorConfigProcessor;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Timer;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ConfigManager {
   private static final Logger LOGGER;
   private static final List RESOURCE_PROVIDERS;
   private static final int NUMBER_OF_TASK_THREADS = 5;
   private static final ConfigManager CONFIG_MANAGER;
   private List initializedContexts = new CopyOnWriteArrayList();
   private static final ConfigProcessor CONFIG_PROCESSOR_CHAIN;
   private static final String XSL = "/com/sun/faces/jsf1_0-1_1toSchema.xsl";

   public static ConfigManager getInstance() {
      return CONFIG_MANAGER;
   }

   public void initialize(ServletContext sc) {
      if (!this.hasBeenInitialized(sc)) {
         this.initializedContexts.add(sc);

         try {
            CONFIG_PROCESSOR_CHAIN.process(sc, getConfigDocuments(sc));
         } catch (Exception var4) {
            this.releaseFactories();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Unsanitized stacktrace from failed start...", var4);
            }

            Throwable t = this.unwind(var4);
            throw new ConfigurationException("CONFIGURATION FAILED! " + t.getMessage(), t);
         }
      }

   }

   public void destory(ServletContext sc) {
      this.releaseFactories();
      this.initializedContexts.remove(sc);
   }

   public boolean hasBeenInitialized(ServletContext sc) {
      return this.initializedContexts.contains(sc);
   }

   private static Document[] getConfigDocuments(ServletContext sc) {
      ExecutorService executor = null;
      if (useThreads(sc)) {
         executor = Executors.newFixedThreadPool(5);
      }

      List urlTasks = new ArrayList(RESOURCE_PROVIDERS.size());
      Iterator i$ = RESOURCE_PROVIDERS.iterator();

      while(i$.hasNext()) {
         ConfigurationResourceProvider p = (ConfigurationResourceProvider)i$.next();
         FutureTask t = new FutureTask(new URLTask(p, sc));
         urlTasks.add(t);
         if (executor != null) {
            executor.execute(t);
         } else {
            t.run();
         }
      }

      List docTasks = new ArrayList(RESOURCE_PROVIDERS.size() << 1);
      boolean validating = WebConfiguration.getInstance(sc).isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ValidateFacesConfigFiles);
      Iterator i$ = urlTasks.iterator();

      while(i$.hasNext()) {
         FutureTask t = (FutureTask)i$.next();

         try {
            Collection l = (Collection)t.get();
            Iterator i$ = l.iterator();

            while(i$.hasNext()) {
               URL u = (URL)i$.next();
               FutureTask d = new FutureTask(new ParseTask(validating, u));
               docTasks.add(d);
               if (executor != null) {
                  executor.execute(d);
               } else {
                  d.run();
               }
            }
         } catch (InterruptedException var13) {
         } catch (Exception var14) {
            throw new ConfigurationException(var14);
         }
      }

      List docs = new ArrayList(docTasks.size());
      Iterator i$ = docTasks.iterator();

      while(i$.hasNext()) {
         FutureTask t = (FutureTask)i$.next();

         try {
            docs.add(t.get());
         } catch (ExecutionException var11) {
            throw new ConfigurationException(var11);
         } catch (InterruptedException var12) {
         }
      }

      if (executor != null) {
         executor.shutdown();
      }

      return (Document[])docs.toArray(new Document[docs.size()]);
   }

   private static boolean useThreads(ServletContext ctx) {
      WebConfiguration config = WebConfiguration.getInstance(ctx);
      return config.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableThreading);
   }

   private Throwable unwind(Throwable throwable) {
      Throwable t = null;
      if (throwable != null) {
         t = this.unwind(throwable.getCause());
         if (t == null) {
            t = throwable;
         }
      }

      return t;
   }

   private void releaseFactories() {
      try {
         FactoryFinder.releaseFactories();
      } catch (FacesException var2) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Exception thrown from FactoryFinder.releaseFactories()", var2);
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      CONFIG_MANAGER = new ConfigManager();
      List l = new ArrayList(3);
      l.add(new RIConfigResourceProvider());
      l.add(new MetaInfResourceProvider());
      l.add(new WebResourceProvider());
      RESOURCE_PROVIDERS = Collections.unmodifiableList(l);
      ConfigProcessor[] configProcessors = new ConfigProcessor[]{new FactoryConfigProcessor(), new LifecycleConfigProcessor(), new ApplicationConfigProcessor(), new ComponentConfigProcessor(), new ConverterConfigProcessor(), new ValidatorConfigProcessor(), new ManagedBeanConfigProcessor(), new RenderKitConfigProcessor(), new NavigationConfigProcessor()};

      for(int i = 0; i < configProcessors.length; ++i) {
         ConfigProcessor p = configProcessors[i];
         if (i + 1 < configProcessors.length) {
            p.setNext(configProcessors[i + 1]);
         }
      }

      CONFIG_PROCESSOR_CHAIN = configProcessors[0];
   }

   private static class URLTask implements Callable {
      private ConfigurationResourceProvider provider;
      private ServletContext sc;

      public URLTask(ConfigurationResourceProvider provider, ServletContext sc) {
         this.provider = provider;
         this.sc = sc;
      }

      public Collection call() throws Exception {
         return this.provider.getResources(this.sc);
      }
   }

   private static class ParseTask implements Callable {
      private static final String FACES_SCHEMA_DEFAULT_NS = "http://java.sun.com/xml/ns/javaee";
      private URL documentURL;
      private DocumentBuilderFactory factory;
      private boolean validating;

      public ParseTask(boolean validating, URL documentURL) throws Exception {
         this.documentURL = documentURL;
         this.factory = DbfFactory.getFactory();
         this.validating = validating;
      }

      public Document call() throws Exception {
         try {
            Timer timer = Timer.getInstance();
            if (timer != null) {
               timer.startTiming();
            }

            Document d = this.getDocument();
            if (timer != null) {
               timer.stopTiming();
               timer.logResult("Parse " + this.documentURL.toExternalForm());
            }

            return d;
         } catch (Exception var3) {
            throw new ConfigurationException(MessageFormat.format("Unable to parse document ''{0}'': {1}", this.documentURL.toExternalForm(), var3.getMessage()), var3);
         }
      }

      private Document getDocument() throws Exception {
         DocumentBuilder db = this.getNonValidatingBuilder();
         InputSource is = new InputSource(getInputStream(this.documentURL));
         is.setSystemId(this.documentURL.toExternalForm());
         Document doc = db.parse(is);
         String documentNS = doc.getDocumentElement().getNamespaceURI();
         if (this.validating && documentNS != null) {
            DOMSource domSource = new DOMSource(doc, this.documentURL.toExternalForm());
            if ("http://java.sun.com/xml/ns/javaee".equals(documentNS)) {
               DocumentBuilder builder = this.getBuilderForSchema(DbfFactory.FacesSchema.FACES_12);
               if (builder.isValidating()) {
                  builder.getSchema().newValidator().validate(domSource);
                  return (Document)domSource.getNode();
               } else {
                  return (Document)domSource.getNode();
               }
            } else {
               DOMResult domResult = new DOMResult();
               Transformer transformer = getTransformer();
               transformer.transform(domSource, domResult);
               DocumentBuilder builder = this.getBuilderForSchema(DbfFactory.FacesSchema.FACES_11);
               if (builder.isValidating()) {
                  builder.getSchema().newValidator().validate(domSource);
                  return (Document)domSource.getNode();
               } else {
                  return (Document)domSource.getNode();
               }
            }
         } else {
            return doc;
         }
      }

      private static Transformer getTransformer() throws Exception {
         TransformerFactory factory = TransformerFactory.newInstance();
         return factory.newTransformer(new StreamSource(getInputStream(ConfigManager.class.getResource("/com/sun/faces/jsf1_0-1_1toSchema.xsl"))));
      }

      private static InputStream getInputStream(URL url) throws IOException {
         URLConnection conn = url.openConnection();
         conn.setUseCaches(false);
         return new BufferedInputStream(conn.getInputStream());
      }

      private DocumentBuilder getNonValidatingBuilder() throws Exception {
         DocumentBuilderFactory tFactory = DbfFactory.getFactory();
         tFactory.setValidating(false);
         DocumentBuilder tBuilder = tFactory.newDocumentBuilder();
         tBuilder.setEntityResolver(DbfFactory.FACES_ENTITY_RESOLVER);
         tBuilder.setErrorHandler(DbfFactory.FACES_ERROR_HANDLER);
         return tBuilder;
      }

      private DocumentBuilder getBuilderForSchema(DbfFactory.FacesSchema schema) throws Exception {
         try {
            this.factory.setSchema(schema.getSchema());
         } catch (UnsupportedOperationException var3) {
            return this.getNonValidatingBuilder();
         }

         DocumentBuilder builder = this.factory.newDocumentBuilder();
         builder.setEntityResolver(DbfFactory.FACES_ENTITY_RESOLVER);
         builder.setErrorHandler(DbfFactory.FACES_ERROR_HANDLER);
         return builder;
      }
   }
}
