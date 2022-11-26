package com.sun.faces.config.processor;

import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
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
   private static final String LIFECYCLE = "lifecycle";
   private static final String PHASE_LISTENER = "phase-listener";
   private List appPhaseListeners = new CopyOnWriteArrayList();

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      LifecycleFactory factory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing lifecycle elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList lifecycles = document.getElementsByTagNameNS(namespace, "lifecycle");
         if (lifecycles != null) {
            int c = 0;

            for(int csize = lifecycles.getLength(); c < csize; ++c) {
               Node lifecyleNode = lifecycles.item(c);
               if (lifecyleNode.getNodeType() == 1) {
                  NodeList listeners = ((Element)lifecyleNode).getElementsByTagNameNS(namespace, "phase-listener");
                  this.addPhaseListeners(servletContext, facesContext, factory, listeners);
               }
            }
         }
      }

   }

   public void destroy(ServletContext sc, FacesContext facesContext) {
      this.destroyInstances(sc, facesContext, this.appPhaseListeners);
   }

   private void addPhaseListeners(ServletContext sc, FacesContext facesContext, LifecycleFactory factory, NodeList phaseListeners) {
      if (phaseListeners != null && phaseListeners.getLength() > 0) {
         int i = 0;

         for(int size = phaseListeners.getLength(); i < size; ++i) {
            Node phaseListenerNode = phaseListeners.item(i);
            String phaseListenerClassName = this.getNodeText(phaseListenerNode);
            if (phaseListenerClassName != null) {
               boolean[] didPerformInjection = new boolean[]{false};
               PhaseListener phaseListener = (PhaseListener)this.createInstance(sc, facesContext, phaseListenerClassName, PhaseListener.class, (Object)null, phaseListenerNode, true, didPerformInjection);
               if (phaseListener != null) {
                  if (didPerformInjection[0]) {
                     this.appPhaseListeners.add(phaseListener);
                  }

                  Lifecycle lifecycle;
                  for(Iterator t = factory.getLifecycleIds(); t.hasNext(); lifecycle.addPhaseListener(phaseListener)) {
                     String lfId = (String)t.next();
                     lifecycle = factory.getLifecycle(lfId);
                     if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, MessageFormat.format("Adding PhaseListener ''{0}'' to lifecycle ''{0}}", phaseListenerClassName, lfId));
                     }
                  }
               }
            }
         }
      }

   }

   private void destroyInstances(ServletContext sc, FacesContext facesContext, List instances) {
      Iterator var4 = instances.iterator();

      while(var4.hasNext()) {
         Object instance = var4.next();
         this.destroyInstance(sc, facesContext, instance.getClass().getName(), instance);
      }

      instances.clear();
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
