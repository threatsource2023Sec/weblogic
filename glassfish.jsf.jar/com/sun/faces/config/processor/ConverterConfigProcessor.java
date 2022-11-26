package com.sun.faces.config.processor;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.Verifier;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConverterConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String CONVERTER = "converter";
   private static final String CONVERTER_ID = "converter-id";
   private static final String CONVERTER_FOR_CLASS = "converter-for-class";
   private static final String CONVERTER_CLASS = "converter-class";

   public void process(ServletContext sc, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      this.processAnnotations(facesContext, FacesConverter.class);

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing converter elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList nodes = document.getDocumentElement().getElementsByTagNameNS(namespace, "converter");
         if (nodes != null && nodes.getLength() > 0) {
            this.addConverters(nodes, namespace);
         }
      }

   }

   private void addConverters(NodeList converters, String namespace) {
      Application application = this.getApplication();
      Verifier verifier = Verifier.getCurrentInstance();
      int i = 0;

      for(int size = converters.getLength(); i < size; ++i) {
         Node converter = converters.item(i);
         NodeList children = ((Element)converter).getElementsByTagNameNS(namespace, "*");
         String converterId = null;
         String converterClass = null;
         String converterForClass = null;
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            switch (n.getLocalName()) {
               case "converter-id":
                  converterId = this.getNodeText(n);
                  break;
               case "converter-class":
                  converterClass = this.getNodeText(n);
                  break;
               case "converter-for-class":
                  converterForClass = this.getNodeText(n);
            }
         }

         if (converterId != null && converterClass != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("[Converter by ID] Calling Application.addConverter({0}, {1}", converterId, converterClass));
            }

            if (verifier != null) {
               verifier.validateObject(Verifier.ObjectType.CONVERTER, converterClass, Converter.class);
            }

            application.addConverter(converterId, converterClass);
         } else if (converterClass != null && converterForClass != null) {
            try {
               Class cfcClass = Util.loadClass(converterForClass, this.getClass());
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("[Converter for Class] Calling Application.addConverter({0}, {1}", converterForClass, converterClass));
               }

               if (verifier != null) {
                  verifier.validateObject(Verifier.ObjectType.CONVERTER, converterClass, Converter.class);
               }

               application.addConverter(cfcClass, converterClass);
            } catch (ClassNotFoundException var17) {
               throw new ConfigurationException(var17);
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
