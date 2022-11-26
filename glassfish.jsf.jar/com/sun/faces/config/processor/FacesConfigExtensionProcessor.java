package com.sun.faces.config.processor;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FacesConfigExtensionProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String FACES_CONFIG_EXTENSION = "faces-config-extension";
   private static final String FACELETS_PROCESSING = "facelets-processing";
   private static final String FILE_EXTENSION = "file-extension";
   private static final String PROCESS_AS = "process-as";

   public void process(ServletContext sc, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing faces-config-extension elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList facesConfigExtensions = document.getDocumentElement().getElementsByTagNameNS(namespace, "faces-config-extension");
         if (facesConfigExtensions != null && facesConfigExtensions.getLength() > 0) {
            this.processFacesConfigExtensions(facesConfigExtensions, namespace, documentInfos[i]);
         }
      }

   }

   private void processFacesConfigExtensions(NodeList facesConfigExtensions, String namespace, DocumentInfo info) {
      WebConfiguration config = null;
      int i = 0;

      for(int size = facesConfigExtensions.getLength(); i < size; ++i) {
         Node facesConfigExtension = facesConfigExtensions.item(i);
         NodeList children = ((Element)facesConfigExtension).getElementsByTagNameNS(namespace, "*");
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            if ("facelets-processing".equals(n.getLocalName())) {
               NodeList faceletsProcessingChildren = ((Element)n).getElementsByTagNameNS(namespace, "*");
               String fileExtension = null;
               String processAs = null;
               int fp = 0;

               for(int fpsize = faceletsProcessingChildren.getLength(); fp < fpsize; ++fp) {
                  Node childOfInterset = faceletsProcessingChildren.item(fp);
                  if (null == fileExtension && "file-extension".equals(childOfInterset.getLocalName())) {
                     fileExtension = this.getNodeText(childOfInterset);
                  } else if (null == processAs && "process-as".equals(childOfInterset.getLocalName())) {
                     processAs = this.getNodeText(childOfInterset);
                  } else if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, MessageFormat.format("Processing faces-config-extension elements for document: ''{0}'', encountered unexpected configuration ''{1}'', ignoring and continuing", info.getSourceURI(), this.getNodeText(childOfInterset)));
                  }
               }

               if (null != fileExtension && null != processAs) {
                  if (null == config) {
                     config = WebConfiguration.getInstance();
                  }

                  Map faceletsProcessingMappings = config.getFacesConfigOptionValue(WebConfiguration.WebContextInitParameter.FaceletsProcessingFileExtensionProcessAs, true);
                  faceletsProcessingMappings.put(fileExtension, processAs);
               } else if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, MessageFormat.format("Processing faces-config-extension elements for document: ''{0}'', encountered <facelets-processing> elemnet without expected children", info.getSourceURI()));
               }
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
