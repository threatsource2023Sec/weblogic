package com.sun.faces.config.processor;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FactoryConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String FACTORY = "factory";
   private static final String APPLICATION_FACTORY = "application-factory";
   private static final String FACES_CONTEXT_FACTORY = "faces-context-factory";
   private static final String LIFECYCLE_FACTORY = "lifecycle-factory";
   private static final String RENDER_KIT_FACTORY = "render-kit-factory";
   private static final String[] FACTORY_NAMES;

   public void process(ServletContext sc, Document[] documents) throws Exception {
      for(int i = 0; i < documents.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing factory elements for document: ''{0}''", documents[i].getDocumentURI()));
         }

         String namespace = documents[i].getDocumentElement().getNamespaceURI();
         NodeList factories = documents[i].getDocumentElement().getElementsByTagNameNS(namespace, "factory");
         if (factories != null && factories.getLength() > 0) {
            this.processFactories(factories, namespace);
         }
      }

      this.verifyFactoriesExist();
      this.invokeNext(sc, documents);
   }

   private void processFactories(NodeList factories, String namespace) {
      int i = 0;

      for(int size = factories.getLength(); i < size; ++i) {
         Node factory = factories.item(i);
         NodeList children = ((Element)factory).getElementsByTagNameNS(namespace, "*");
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            if ("application-factory".equals(n.getLocalName())) {
               setFactory("javax.faces.application.ApplicationFactory", this.getNodeText(n));
            } else if ("lifecycle-factory".equals(n.getLocalName())) {
               setFactory("javax.faces.lifecycle.LifecycleFactory", this.getNodeText(n));
            } else if ("faces-context-factory".equals(n.getLocalName())) {
               setFactory("javax.faces.context.FacesContextFactory", this.getNodeText(n));
            } else if ("render-kit-factory".equals(n.getLocalName())) {
               setFactory("javax.faces.render.RenderKitFactory", this.getNodeText(n));
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

   private void verifyFactoriesExist() {
      int i = 0;

      for(int len = FACTORY_NAMES.length; i < len; ++i) {
         try {
            FactoryFinder.getFactory(FACTORY_NAMES[i]);
         } catch (Exception var4) {
            throw new ConfigurationException(MessageFormat.format("Factory ''{0}'' was not configured properly.", FACTORY_NAMES[i]), var4);
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      FACTORY_NAMES = new String[]{"javax.faces.application.ApplicationFactory", "javax.faces.context.FacesContextFactory", "javax.faces.lifecycle.LifecycleFactory", "javax.faces.render.RenderKitFactory"};
   }
}
