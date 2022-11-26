package com.sun.faces.config.processor;

import com.sun.faces.config.Verifier;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BehaviorConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String BEHAVIOR = "behavior";
   private static final String BEHAVIOR_ID = "behavior-id";
   private static final String BEHAVIOR_CLASS = "behavior-class";

   public void process(ServletContext sc, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      this.processAnnotations(facesContext, FacesBehavior.class);

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing behavior elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList behaviors = document.getDocumentElement().getElementsByTagNameNS(namespace, "behavior");
         if (behaviors != null && behaviors.getLength() > 0) {
            this.addBehaviors(behaviors, namespace);
         }
      }

   }

   private void addBehaviors(NodeList behaviors, String namespace) throws XPathExpressionException {
      Application app = this.getApplication();
      Verifier verifier = Verifier.getCurrentInstance();
      int i = 0;

      for(int size = behaviors.getLength(); i < size; ++i) {
         Node behavior = behaviors.item(i);
         NodeList children = ((Element)behavior).getElementsByTagNameNS(namespace, "*");
         String behaviorId = null;
         String behaviorClass = null;
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            if (n.getNodeType() == 1) {
               switch (n.getLocalName()) {
                  case "behavior-id":
                     behaviorId = this.getNodeText(n);
                     break;
                  case "behavior-class":
                     behaviorClass = this.getNodeText(n);
               }
            }
         }

         if (behaviorId != null && behaviorClass != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.addBehavior({0},{1})", behaviorId, behaviorClass));
            }

            if (verifier != null) {
               verifier.validateObject(Verifier.ObjectType.BEHAVIOR, behaviorClass, Behavior.class);
            }

            app.addBehavior(behaviorId, behaviorClass);
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
