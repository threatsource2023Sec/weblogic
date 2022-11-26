package com.sun.faces.config.processor;

import com.sun.faces.application.InjectionApplicationFactory;
import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.InitFacesContext;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.context.InjectionFacesContextFactory;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FactoryConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String FACTORY = "factory";
   private static final String APPLICATION_FACTORY = "application-factory";
   private static final String EXCEPTION_HANDLER_FACTORY = "exception-handler-factory";
   private static final String FLASH_FACTORY = "flash-factory";
   private static final String VISIT_CONTEXT_FACTORY = "visit-context-factory";
   private static final String VIEW_DECLARATION_LANGUAGE_FACTORY = "view-declaration-language-factory";
   private static final String TAG_HANDLER_DELEGATE_FACTORY = "tag-handler-delegate-factory";
   private static final String FACELET_CACHE_FACTORY = "facelet-cache-factory";
   private static final String FACES_CONTEXT_FACTORY = "faces-context-factory";
   private static final String CLIENT_WINDOW_FACTORY = "client-window-factory";
   private static final String PARTIAL_VIEW_CONTEXT_FACTORY = "partial-view-context-factory";
   private static final String LIFECYCLE_FACTORY = "lifecycle-factory";
   private static final String RENDER_KIT_FACTORY = "render-kit-factory";
   private static final String EXTERNAL_CONTEXT_FACTORY = "external-context-factory";
   private static final String FLOW_HANDLER_FACTORY = "flow-handler-factory";
   private static final String SEARCH_EXPRESSION_CONTEXT_FACTORY = "search-expression-context-factory";
   private final List factoryNames = Arrays.asList("javax.faces.application.ApplicationFactory", "javax.faces.lifecycle.ClientWindowFactory", "javax.faces.context.ExceptionHandlerFactory", "javax.faces.context.ExternalContextFactory", "javax.faces.context.FacesContextFactory", "javax.faces.context.FlashFactory", "javax.faces.lifecycle.LifecycleFactory", "javax.faces.view.ViewDeclarationLanguageFactory", "javax.faces.context.PartialViewContextFactory", "javax.faces.render.RenderKitFactory", "javax.faces.component.visit.VisitContextFactory", "javax.faces.view.facelets.FaceletCacheFactory", "javax.faces.view.facelets.TagHandlerDelegateFactory", "javax.faces.flow.FlowHandlerFactory", "javax.faces.component.search.SearchExpressionContextFactory");
   private boolean validateFactories = true;

   public FactoryConfigProcessor() {
   }

   public FactoryConfigProcessor(boolean validateFactories) {
      this.validateFactories = validateFactories;
   }

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      AtomicInteger facesContextFactoryCount = new AtomicInteger(0);
      AtomicInteger applicationFactoryCount = new AtomicInteger(0);

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing factory elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList factories = document.getDocumentElement().getElementsByTagNameNS(namespace, "factory");
         if (factories != null && factories.getLength() > 0) {
            this.processFactories(factories, namespace, facesContextFactoryCount, applicationFactoryCount);
         }
      }

      this.wrapFactories(applicationFactoryCount.get(), facesContextFactoryCount.get());
      this.verifyFactoriesExist(servletContext);
   }

   private void processFactories(NodeList factories, String namespace, AtomicInteger fcCount, AtomicInteger appCount) {
      int i = 0;

      for(int size = factories.getLength(); i < size; ++i) {
         Node factory = factories.item(i);
         NodeList children = ((Element)factory).getElementsByTagNameNS(namespace, "*");
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node childNode = children.item(c);
            switch (childNode.getLocalName()) {
               case "application-factory":
                  int cnt = appCount.incrementAndGet();
                  setFactory("javax.faces.application.ApplicationFactory", this.getNodeText(childNode));
                  break;
               case "exception-handler-factory":
                  setFactory("javax.faces.context.ExceptionHandlerFactory", this.getNodeText(childNode));
                  break;
               case "visit-context-factory":
                  setFactory("javax.faces.component.visit.VisitContextFactory", this.getNodeText(childNode));
                  break;
               case "lifecycle-factory":
                  setFactory("javax.faces.lifecycle.LifecycleFactory", this.getNodeText(childNode));
                  break;
               case "flash-factory":
                  setFactory("javax.faces.context.FlashFactory", this.getNodeText(childNode));
                  break;
               case "client-window-factory":
                  setFactory("javax.faces.lifecycle.ClientWindowFactory", this.getNodeText(childNode));
                  break;
               case "faces-context-factory":
                  fcCount.incrementAndGet();
                  setFactory("javax.faces.context.FacesContextFactory", this.getNodeText(childNode));
                  break;
               case "render-kit-factory":
                  setFactory("javax.faces.render.RenderKitFactory", this.getNodeText(childNode));
                  break;
               case "view-declaration-language-factory":
                  setFactory("javax.faces.view.ViewDeclarationLanguageFactory", this.getNodeText(childNode));
                  break;
               case "tag-handler-delegate-factory":
                  setFactory("javax.faces.view.facelets.TagHandlerDelegateFactory", this.getNodeText(childNode));
                  break;
               case "facelet-cache-factory":
                  setFactory("javax.faces.view.facelets.FaceletCacheFactory", this.getNodeText(childNode));
                  break;
               case "external-context-factory":
                  setFactory("javax.faces.context.ExternalContextFactory", this.getNodeText(childNode));
                  break;
               case "partial-view-context-factory":
                  setFactory("javax.faces.context.PartialViewContextFactory", this.getNodeText(childNode));
                  break;
               case "flow-handler-factory":
                  setFactory("javax.faces.flow.FlowHandlerFactory", this.getNodeText(childNode));
                  break;
               case "search-expression-context-factory":
                  setFactory("javax.faces.component.search.SearchExpressionContextFactory", this.getNodeText(childNode));
            }
         }
      }

   }

   private static void setFactory(String factoryName, String factoryImpl) {
      if (factoryName != null && factoryImpl != null) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Calling FactoryFinder.setFactory({0}, {1})", factoryName, factoryImpl));
         }

         FactoryFinder.setFactory(factoryName, factoryImpl);
      }

   }

   private void verifyFactoriesExist(ServletContext servletContext) {
      if (this.validateFactories) {
         Deque exceptions = new ConcurrentLinkedDeque();
         ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

         try {
            this.factoryNames.stream().forEach((e) -> {
               Thread.currentThread().setContextClassLoader(contextClassLoader);
               InitFacesContext.getInstance(servletContext);

               try {
                  FactoryFinder.getFactory(e);
               } catch (Exception var8) {
                  var8.printStackTrace();
                  exceptions.add(new ConfigurationException(MessageFormat.format("Factory ''{0}'' was not configured properly.", e), var8));
               } finally {
                  Thread.currentThread().setContextClassLoader((ClassLoader)null);
               }

            });
         } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
         }
      }

   }

   private void wrapFactories(int appCount, int fcCount) {
      if (appCount > 1) {
         this.addInjectionApplicationFactory();
      }

      if (fcCount > 1) {
         this.addInjectionFacesContextFactory();
      }

   }

   private void addInjectionApplicationFactory() {
      FactoryFinder.setFactory("javax.faces.application.ApplicationFactory", InjectionApplicationFactory.class.getName());
   }

   private void addInjectionFacesContextFactory() {
      FactoryFinder.setFactory("javax.faces.context.FacesContextFactory", InjectionFacesContextFactory.class.getName());
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
