package com.sun.faces.config.processor;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LifecycleConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String JS_PHASE_LISTENER = "com.sun.faces.lifecycle.JsfJsResourcePhaseListener";
   private static final String LIFECYCLE = "lifecycle";
   private static final String PHASE_LISTENER = "phase-listener";

   public void process(ServletContext sc, Document[] documents) throws Exception {
      LifecycleFactory factory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");

      for(int i = 0; i < documents.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing lifecycle elements for document: ''{0}''", documents[i].getDocumentURI()));
         }

         String namespace = documents[i].getDocumentElement().getNamespaceURI();
         NodeList lifecycles = documents[i].getDocumentElement().getElementsByTagNameNS(namespace, "lifecycle");
         if (lifecycles != null) {
            int c = 0;

            for(int csize = lifecycles.getLength(); c < csize; ++c) {
               Node n = lifecycles.item(c);
               if (n.getNodeType() == 1) {
                  NodeList listeners = ((Element)n).getElementsByTagNameNS(namespace, "phase-listener");
                  this.addPhaseListeners(factory, listeners);
               }
            }
         }
      }

      this.invokeNext(sc, documents);
   }

   private void addPhaseListeners(LifecycleFactory factory, NodeList phaseListeners) {
      WebConfiguration webConfig = WebConfiguration.getInstance();
      boolean jsPLEnabled = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ExternalizeJavaScript);
      if (phaseListeners != null && phaseListeners.getLength() > 0) {
         int i = 0;

         for(int size = phaseListeners.getLength(); i < size; ++i) {
            Node plNode = phaseListeners.item(i);
            String pl = this.getNodeText(plNode);
            if ((jsPLEnabled || !"com.sun.faces.lifecycle.JsfJsResourcePhaseListener".equals(pl)) && pl != null) {
               Object plInstance = this.createInstance(pl, PhaseListener.class, (Object)null, plNode);
               Lifecycle lifecycle;
               if (plInstance != null) {
                  for(Iterator t = factory.getLifecycleIds(); t.hasNext(); lifecycle.addPhaseListener((PhaseListener)plInstance)) {
                     String lfId = (String)t.next();
                     lifecycle = factory.getLifecycle(lfId);
                     if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, MessageFormat.format("Adding PhaseListener ''{0}'' to lifecycle ''{0}}", pl, lfId));
                     }
                  }
               }
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
