package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
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
   private static final String IF = "if";
   private static final String TO_VIEW_ID = "to-view-id";
   private static final String TO_FLOW_DOCUMENT_ID = "to-flow-document-id";
   private static final String REDIRECT = "redirect";
   private static final String VIEW_PARAM = "view-param";
   private static final String VIEW_PARAM_NAME = "name";
   private static final String VIEW_PARAM_VALUE = "value";
   private static final String REDIRECT_PARAM = "redirect-param";
   private static final String REDIRECT_PARAM_NAME = "name";
   private static final String REDIRECT_PARAM_VALUE = "value";
   private static final String INCLUDE_VIEW_PARAMS_ATTRIBUTE = "include-view-params";
   private static final String FROM_VIEW_ID_DEFAULT = "*";

   public void process(ServletContext sc, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      NavigationHandler handler = this.getApplication().getNavigationHandler();
      DocumentInfo[] var5 = documentInfos;
      int var6 = documentInfos.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         DocumentInfo documentInfo = var5[var7];
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing navigation-rule elements for document: ''{0}''", documentInfo.getSourceURI()));
         }

         Document document = documentInfo.getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList navigationRules = document.getDocumentElement().getElementsByTagNameNS(namespace, "navigation-rule");
         if (navigationRules != null && navigationRules.getLength() > 0) {
            this.addNavigationRules(navigationRules, handler, sc);
         }
      }

   }

   private void addNavigationRules(NodeList navigationRules, NavigationHandler navHandler, ServletContext sc) throws XPathExpressionException {
      int i = 0;

      for(int size = navigationRules.getLength(); i < size; ++i) {
         Node navigationRule = navigationRules.item(i);
         if (!"flow-definition".equals(navigationRule.getParentNode().getLocalName()) && navigationRule.getNodeType() == 1) {
            NodeList children = navigationRule.getChildNodes();
            String fromViewId = "*";
            List navigationCases = null;
            int c = 0;

            for(int csize = children.getLength(); c < csize; ++c) {
               Node n = children.item(c);
               if (n.getNodeType() == 1) {
                  switch (n.getLocalName()) {
                     case "from-view-id":
                        String t = this.getNodeText(n);
                        fromViewId = t == null ? "*" : t;
                        if (!fromViewId.equals("*") && fromViewId.charAt(0) != '/') {
                           if (LOGGER.isLoggable(Level.WARNING)) {
                              LOGGER.log(Level.WARNING, "jsf.config.navigation.from_view_id_leading_slash", new String[]{fromViewId});
                           }

                           fromViewId = '/' + fromViewId;
                        }
                        break;
                     case "navigation-case":
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

            this.addNavigationCasesForRule(fromViewId, navigationCases, navHandler, sc);
         }
      }

   }

   private void addNavigationCasesForRule(String fromViewId, List navigationCases, NavigationHandler navHandler, ServletContext sc) {
      if (navigationCases != null && !navigationCases.isEmpty()) {
         ApplicationAssociate associate = ApplicationAssociate.getInstance(sc);
         Iterator var6 = navigationCases.iterator();

         while(true) {
            Node navigationCase;
            do {
               if (!var6.hasNext()) {
                  return;
               }

               navigationCase = (Node)var6.next();
            } while(navigationCase.getNodeType() != 1);

            NodeList children = navigationCase.getChildNodes();
            String outcome = null;
            String action = null;
            String condition = null;
            String toViewId = null;
            String toFlowDocumentId = null;
            Map parameters = null;
            boolean redirect = false;
            boolean includeViewParams = false;
            int i = 0;

            for(int size = children.getLength(); i < size; ++i) {
               Node n = children.item(i);
               if (n.getNodeType() == 1) {
                  switch (n.getLocalName()) {
                     case "from-outcome":
                        outcome = this.getNodeText(n);
                        break;
                     case "from-action":
                        action = this.getNodeText(n);
                        break;
                     case "if":
                        String expression = this.getNodeText(n);
                        if (SharedUtils.isExpression(expression) && !SharedUtils.isMixedExpression(expression)) {
                           condition = expression;
                        } else if (LOGGER.isLoggable(Level.WARNING)) {
                           LOGGER.log(Level.WARNING, "jsf.config.navigation.if_invalid_expression", new String[]{expression, fromViewId});
                        }
                        break;
                     case "to-view-id":
                        String toViewIdString = this.getNodeText(n);
                        if (toViewIdString.charAt(0) != '/' && toViewIdString.charAt(0) != '#') {
                           if (LOGGER.isLoggable(Level.WARNING)) {
                              LOGGER.log(Level.WARNING, "jsf.config.navigation.to_view_id_leading_slash", new String[]{toViewIdString, fromViewId});
                           }

                           toViewId = '/' + toViewIdString;
                           break;
                        }

                        toViewId = toViewIdString;
                        break;
                     case "to-flow-document-id":
                        toFlowDocumentId = this.getNodeText(n);
                        break;
                     case "redirect":
                        parameters = this.processParameters(n.getChildNodes());
                        includeViewParams = this.isIncludeViewParams(n);
                        redirect = true;
                  }
               }
            }

            NavigationCase cnc = new NavigationCase(fromViewId, action, outcome, condition, toViewId, toFlowDocumentId, parameters, redirect, includeViewParams);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Adding NavigationCase: {0}", cnc.toString()));
            }

            if (navHandler instanceof ConfigurableNavigationHandler) {
               ConfigurableNavigationHandler cnav = (ConfigurableNavigationHandler)navHandler;
               Set cases = (Set)cnav.getNavigationCases().get(fromViewId);
               if (cases == null) {
                  cases = new LinkedHashSet();
                  cnav.getNavigationCases().put(fromViewId, cases);
               }

               ((Set)cases).add(cnc);
            }

            associate.addNavigationCase(cnc);
         }
      }
   }

   private Map processParameters(NodeList children) {
      Map parameters = null;
      if (children.getLength() > 0) {
         parameters = new LinkedHashMap(4, 1.0F);
         int i = 0;

         for(int size = children.getLength(); i < size; ++i) {
            Node n = children.item(i);
            if (n.getNodeType() == 1) {
               String name;
               String value;
               NodeList params;
               int j;
               int jsize;
               Node pn;
               Object values;
               if ("view-param".equals(n.getLocalName())) {
                  name = null;
                  value = null;
                  params = n.getChildNodes();
                  j = 0;

                  for(jsize = params.getLength(); j < jsize; ++j) {
                     pn = params.item(j);
                     if (pn.getNodeType() == 1) {
                        if ("name".equals(pn.getLocalName())) {
                           name = this.getNodeText(pn);
                        }

                        if ("value".equals(pn.getLocalName())) {
                           value = this.getNodeText(pn);
                        }
                     }
                  }

                  if (name != null) {
                     values = (List)parameters.get(name);
                     if (values == null && value != null) {
                        values = new ArrayList(2);
                        parameters.put(name, values);
                     }

                     if (values != null) {
                        ((List)values).add(value);
                     }
                  }
               }

               if ("redirect-param".equals(n.getLocalName())) {
                  name = null;
                  value = null;
                  params = n.getChildNodes();
                  j = 0;

                  for(jsize = params.getLength(); j < jsize; ++j) {
                     pn = params.item(j);
                     if (pn.getNodeType() == 1) {
                        if ("name".equals(pn.getLocalName())) {
                           name = this.getNodeText(pn);
                        }

                        if ("value".equals(pn.getLocalName())) {
                           value = this.getNodeText(pn);
                        }
                     }
                  }

                  if (name != null) {
                     values = (List)parameters.get(name);
                     if (values == null && value != null) {
                        values = new ArrayList(2);
                        parameters.put(name, values);
                     }

                     if (values != null) {
                        ((List)values).add(value);
                     }
                  }
               }
            }
         }
      }

      return parameters;
   }

   private boolean isIncludeViewParams(Node n) {
      return Boolean.valueOf(this.getNodeText(n.getAttributes().getNamedItem("include-view-params")));
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
