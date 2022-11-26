package com.sun.faces.config.processor;

import com.sun.faces.config.Verifier;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String COMPONENT = "component";
   private static final String COMPONENT_TYPE = "component-type";
   private static final String COMPONENT_CLASS = "component-class";

   public void process(ServletContext sc, Document[] documents) throws Exception {
      for(int i = 0; i < documents.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing component elements for document: ''{0}''", documents[i].getDocumentURI()));
         }

         String namespace = documents[i].getDocumentElement().getNamespaceURI();
         NodeList components = documents[i].getDocumentElement().getElementsByTagNameNS(namespace, "component");
         if (components != null && components.getLength() > 0) {
            this.addComponents(components, namespace);
         }
      }

      this.invokeNext(sc, documents);
   }

   private void addComponents(NodeList components, String namespace) throws XPathExpressionException {
      Application app = this.getApplication();
      Verifier verifier = Verifier.getCurrentInstance();
      int i = 0;

      for(int size = components.getLength(); i < size; ++i) {
         Node componentNode = components.item(i);
         NodeList children = ((Element)componentNode).getElementsByTagNameNS(namespace, "*");
         String componentType = null;
         String componentClass = null;
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            if ("component-type".equals(n.getLocalName())) {
               componentType = this.getNodeText(n);
            } else if ("component-class".equals(n.getLocalName())) {
               componentClass = this.getNodeText(n);
            }
         }

         if (componentType != null && componentClass != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.addComponent({0},{1})", componentType, componentClass));
            }

            if (verifier != null) {
               verifier.validateObject(Verifier.ObjectType.COMPONENT, componentClass, UIComponent.class);
            }

            app.addComponent(componentType, componentClass);
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
