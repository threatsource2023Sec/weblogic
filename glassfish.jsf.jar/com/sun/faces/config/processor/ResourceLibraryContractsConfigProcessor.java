package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResourceLibraryContractsConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String RESOURCE_LIBRARY_CONTRACTS = "resource-library-contracts";

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      HashMap map = new HashMap();

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing factory elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList resourceLibraryContracts = document.getDocumentElement().getElementsByTagNameNS(namespace, "resource-library-contracts");
         if (resourceLibraryContracts != null && resourceLibraryContracts.getLength() > 0) {
            this.processResourceLibraryContracts(resourceLibraryContracts, map);
         }
      }

      if (!map.isEmpty()) {
         ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
         associate.setResourceLibraryContracts(map);
      }

   }

   private void processResourceLibraryContracts(NodeList resourceLibraryContracts, HashMap map) {
      XPath xpath = XPathFactory.newInstance().newXPath();
      xpath.setNamespaceContext(new FacesConfigNamespaceContext());

      for(int c = 0; c < resourceLibraryContracts.getLength(); ++c) {
         Node node = resourceLibraryContracts.item(c);

         try {
            NodeList mappings = (NodeList)xpath.evaluate(".//ns1:contract-mapping", node, XPathConstants.NODESET);
            if (mappings != null) {
               for(int m = 0; m < mappings.getLength(); ++m) {
                  Node contractMapping = mappings.item(m);
                  NodeList urlPatterns = (NodeList)xpath.evaluate(".//ns1:url-pattern/text()", contractMapping, XPathConstants.NODESET);
                  if (urlPatterns != null) {
                     for(int p = 0; p < urlPatterns.getLength(); ++p) {
                        String urlPattern = urlPatterns.item(p).getNodeValue().trim();
                        if (LOGGER.isLoggable(Level.INFO)) {
                           LOGGER.log(Level.INFO, "Processing resource library contract mapping for url-pattern: {0}", urlPattern);
                        }

                        if (map.containsKey(urlPattern)) {
                           LOGGER.log(Level.INFO, "Duplicate url-patern found: {0}, ignoring it", urlPattern);
                        } else {
                           ArrayList list = new ArrayList();
                           NodeList contracts = (NodeList)xpath.evaluate(".//ns1:contracts/text()", contractMapping, XPathConstants.NODESET);
                           if (contracts != null && contracts.getLength() > 0) {
                              for(int j = 0; j < contracts.getLength(); ++j) {
                                 String[] contractStrings = contracts.item(j).getNodeValue().trim().split(",");

                                 for(int k = 0; k < contractStrings.length; ++k) {
                                    if (!list.contains(contractStrings[k])) {
                                       if (LOGGER.isLoggable(Level.INFO)) {
                                          LOGGER.log(Level.INFO, "Added contract: {0} for url-pattern: {1}", new Object[]{contractStrings[k], urlPattern});
                                       }

                                       list.add(contractStrings[k]);
                                    } else if (LOGGER.isLoggable(Level.INFO)) {
                                       LOGGER.log(Level.INFO, "Duplicate contract: {0} found for url-pattern: {1}", new Object[]{contractStrings[k], urlPattern});
                                    }
                                 }
                              }
                           }

                           if (!list.isEmpty()) {
                              map.put(urlPattern, list);
                           } else {
                              LOGGER.log(Level.INFO, "No contracts found for url-pattern: {0}", urlPattern);
                           }
                        }
                     }
                  }
               }
            }
         } catch (XPathExpressionException var17) {
            LOGGER.log(Level.FINEST, "Unable to parse XPath expression", var17);
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
