package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ConfigNavigationCase;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NavigationConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String NAVIGATION_RULE = "navigation-rule";
   private static final String FROM_VIEW_ID = "from-view-id";
   private static final String NAVIGATION_CASE = "navigation-case";
   private static final String FROM_ACTION = "from-action";
   private static final String FROM_OUTCOME = "from-outcome";
   private static final String TO_VIEW_ID = "to-view-id";
   private static final String REDIRECT = "redirect";
   private static final String FROM_VIEW_ID_DEFAULT = "*";

   public void process(ServletContext sc, Document[] documents) throws Exception {
      Document[] arr$ = documents;
      int len$ = documents.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Document document = arr$[i$];
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing navigation-rule elements for document: ''{0}''", document.getDocumentURI()));
         }

         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList navigationRules = document.getDocumentElement().getElementsByTagNameNS(namespace, "navigation-rule");
         if (navigationRules != null && navigationRules.getLength() > 0) {
            this.addNavigationRules(navigationRules, sc);
         }
      }

      this.invokeNext(sc, documents);
   }

   private void addNavigationRules(NodeList navigationRules, ServletContext sc) throws XPathExpressionException {
      int i = 0;

      for(int size = navigationRules.getLength(); i < size; ++i) {
         Node navigationRule = navigationRules.item(i);
         if (navigationRule.getNodeType() == 1) {
            NodeList children = navigationRule.getChildNodes();
            String fromViewId = "*";
            List navigationCases = null;
            int c = 0;

            for(int csize = children.getLength(); c < csize; ++c) {
               Node n = children.item(c);
               if (n.getNodeType() == 1) {
                  if ("from-view-id".equals(n.getLocalName())) {
                     String t = this.getNodeText(n);
                     fromViewId = t == null ? "*" : t;
                     if (!fromViewId.equals("*") && fromViewId.charAt(0) != '/') {
                        if (LOGGER.isLoggable(Level.WARNING)) {
                           LOGGER.log(Level.WARNING, "jsf.config.navigation.from_view_id_leading_slash", new String[]{fromViewId});
                        }

                        fromViewId = '/' + fromViewId;
                     }
                  } else if ("navigation-case".equals(n.getLocalName())) {
                     if (navigationCases == null) {
                        navigationCases = new ArrayList(csize);
                     }

                     navigationCases.add(n);
                  }
               }
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Processing navigation rule with 'from-view-id' of ''{0}''", fromViewId));
            }

            this.addNavigationCasesForRule(fromViewId, navigationCases, sc);
         }
      }

   }

   private void addNavigationCasesForRule(String fromViewId, List navigationCases, ServletContext sc) {
      if (navigationCases != null && !navigationCases.isEmpty()) {
         ApplicationAssociate associate = ApplicationAssociate.getInstance(sc);
         if (associate != null) {
            Iterator i$ = navigationCases.iterator();

            while(true) {
               Node navigationCase;
               do {
                  if (!i$.hasNext()) {
                     return;
                  }

                  navigationCase = (Node)i$.next();
               } while(navigationCase.getNodeType() != 1);

               NodeList children = navigationCase.getChildNodes();
               String outcome = null;
               String action = null;
               String toViewId = null;
               boolean redirect = false;
               int i = 0;

               for(int size = children.getLength(); i < size; ++i) {
                  Node n = children.item(i);
                  if (n.getNodeType() == 1) {
                     if ("from-outcome".equals(n.getLocalName())) {
                        outcome = this.getNodeText(n);
                     } else if ("from-action".equals(n.getLocalName())) {
                        action = this.getNodeText(n);
                     } else if ("to-view-id".equals(n.getLocalName())) {
                        toViewId = this.getNodeText(n);
                        if (toViewId.charAt(0) != '/') {
                           if (LOGGER.isLoggable(Level.WARNING)) {
                              LOGGER.log(Level.WARNING, "jsf.config.navigation.to_view_id_leading_slash", new String[]{toViewId, fromViewId});
                           }

                           toViewId = '/' + toViewId;
                        }
                     } else if ("redirect".equals(n.getLocalName())) {
                        redirect = true;
                     }
                  }
               }

               ConfigNavigationCase cnc = new ConfigNavigationCase(fromViewId, action, outcome, toViewId, redirect);
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Adding NavigationCase: {0}", cnc.toString()));
               }

               associate.addNavigationCase(cnc);
            }
         }
      }
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
