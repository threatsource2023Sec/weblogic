package com.sun.faces.config.manager;

import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FacesConfigInfo {
   private static final Logger LOGGER;
   private static final String ABSOLUTE_ORDERING = "absolute-ordering";
   private static final String ORDERING = "ordering";
   private static final String NAME = "name";
   private static final String OTHERS = "others";
   private double version = 2.0;
   private boolean isWebInfFacesConfig;
   private boolean metadataComplete;
   private List absoluteOrdering;

   public FacesConfigInfo(DocumentInfo documentInfo) {
      Document document = documentInfo.getDocument();
      this.isWebInfFacesConfig = this.isWebinfFacesConfig(document);
      this.version = this.getVersion(document);
      if (this.isWebInfFacesConfig && this.isVersionGreaterOrEqual(2.0)) {
         this.extractOrdering(document);
      }

      this.metadataComplete = this.isMetadataComplete(document);
   }

   public boolean isVersionGreaterOrEqual(double version) {
      return this.version >= version;
   }

   public boolean isWebInfFacesConfig() {
      return this.isWebInfFacesConfig;
   }

   public boolean isMetadataComplete() {
      return this.metadataComplete;
   }

   public List getAbsoluteOrdering() {
      return this.absoluteOrdering != null ? Collections.unmodifiableList(this.absoluteOrdering) : null;
   }

   private double getVersion(Document document) {
      String version = document.getDocumentElement().getAttributeNS(document.getNamespaceURI(), "version");
      return version != null && version.length() > 0 ? Double.parseDouble(version) : 1.1;
   }

   private boolean isWebinfFacesConfig(Document document) {
      return !Util.isEmpty(document.getDocumentElement().getAttribute("com.sun.faces.webinf"));
   }

   private boolean isMetadataComplete(Document document) {
      if (this.isVersionGreaterOrEqual(2.0)) {
         String metadataComplete = document.getDocumentElement().getAttributeNS(document.getNamespaceURI(), "metadata-complete");
         return metadataComplete != null ? Boolean.valueOf(metadataComplete) : false;
      } else {
         return true;
      }
   }

   private void extractOrdering(Document document) {
      Element documentElement = document.getDocumentElement();
      String namespace = documentElement.getNamespaceURI();
      NodeList orderingElements = documentElement.getElementsByTagNameNS(namespace, "ordering");
      if (orderingElements.getLength() > 0) {
         LOGGER.warning("jsf.configuration.web.faces.config.contains.ordering");
      }

      NodeList absoluteOrderingElements = documentElement.getElementsByTagNameNS(namespace, "absolute-ordering");
      if (absoluteOrderingElements.getLength() > 0) {
         if (absoluteOrderingElements.getLength() > 1) {
            throw new IllegalStateException("Multiple 'absolute-ordering' elements found within WEB-INF/faces-config.xml");
         }

         Node absoluteOrderingNode = absoluteOrderingElements.item(0);
         NodeList children = absoluteOrderingNode.getChildNodes();
         this.absoluteOrdering = new ArrayList(children.getLength());
         int i = 0;

         for(int len = children.getLength(); i < len; ++i) {
            Node n = children.item(i);
            if (null != n.getLocalName()) {
               switch (n.getLocalName()) {
                  case "name":
                     this.absoluteOrdering.add(this.getNodeText(n));
                     break;
                  case "others":
                     if (this.absoluteOrdering.contains("others")) {
                        throw new IllegalStateException("'absolute-ordering' element defined with multiple 'others' child elements found within WEB-INF/faces-config.xml");
                     }

                     this.absoluteOrdering.add("others");
               }
            }
         }
      }

   }

   private String getNodeText(Node node) {
      String nodeText = null;
      if (node != null) {
         nodeText = node.getTextContent();
         if (nodeText != null) {
            nodeText = nodeText.trim();
         }
      }

      return !Util.isEmpty(nodeText) ? nodeText : null;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
