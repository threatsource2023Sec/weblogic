package com.sun.faces.config.processor;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProtectedViewsConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String PROTECTED_VIEWS = "protected-views";
   private static final String URL_PATTERN = "url-pattern";

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing protected-views element for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList protectedViews = document.getDocumentElement().getElementsByTagNameNS(namespace, "protected-views");
         if (protectedViews != null && protectedViews.getLength() > 0) {
            this.processProtectedViews(facesContext, protectedViews, namespace, documentInfos[i]);
         }
      }

   }

   private void processProtectedViews(FacesContext context, NodeList protectedViews, String namespace, DocumentInfo info) {
      WebConfiguration config = null;
      ViewHandler viewHandler = null;
      int i = 0;

      for(int size = protectedViews.getLength(); i < size; ++i) {
         Node urlPatterns = protectedViews.item(i);
         NodeList children = ((Element)urlPatterns).getElementsByTagNameNS(namespace, "*");
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            String urlPattern = null;
            if ("url-pattern".equals(n.getLocalName())) {
               urlPattern = this.getNodeText(n);
            } else if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, MessageFormat.format("Processing protected-views elements for document: ''{0}'', encountered unexpected configuration ''{1}'', ignoring and continuing", info.getSourceURI(), this.getNodeText(n)));
            }

            if (urlPattern != null) {
               if (config == null) {
                  config = WebConfiguration.getInstance();
               }

               if (viewHandler == null) {
                  viewHandler = context.getApplication().getViewHandler();
               }

               viewHandler.addProtectedView(urlPattern);
            } else if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, MessageFormat.format("Processing protected-views elements for document: ''{0}'', encountered <url-pattern> element without expected children", info.getSourceURI()));
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
