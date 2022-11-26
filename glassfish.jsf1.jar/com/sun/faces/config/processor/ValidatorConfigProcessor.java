package com.sun.faces.config.processor;

import com.sun.faces.config.Verifier;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.validator.Validator;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ValidatorConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String VALIDATOR = "validator";
   private static final String VALIDATOR_ID = "validator-id";
   private static final String VALIDATOR_CLASS = "validator-class";

   public void process(ServletContext sc, Document[] documents) throws Exception {
      for(int i = 0; i < documents.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing validator elements for document: ''{0}''", documents[i].getDocumentURI()));
         }

         String namespace = documents[i].getDocumentElement().getNamespaceURI();
         NodeList validators = documents[i].getDocumentElement().getElementsByTagNameNS(namespace, "validator");
         if (validators != null && validators.getLength() > 0) {
            this.addValidators(validators, namespace);
         }
      }

      this.invokeNext(sc, documents);
   }

   private void addValidators(NodeList validators, String namespace) throws XPathExpressionException {
      Application app = this.getApplication();
      Verifier verifier = Verifier.getCurrentInstance();
      int i = 0;

      for(int size = validators.getLength(); i < size; ++i) {
         Node validator = validators.item(i);
         NodeList children = ((Element)validator).getElementsByTagNameNS(namespace, "*");
         String validatorId = null;
         String validatorClass = null;
         int c = 0;

         for(int csize = children.getLength(); c < csize; ++c) {
            Node n = children.item(c);
            if (n.getNodeType() == 1) {
               if ("validator-id".equals(n.getLocalName())) {
                  validatorId = this.getNodeText(n);
               } else if ("validator-class".equals(n.getLocalName())) {
                  validatorClass = this.getNodeText(n);
               }
            }
         }

         if (validatorId != null && validatorClass != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.addValidator({0},{1})", validatorId, validatorClass));
            }

            if (verifier != null) {
               verifier.validateObject(Verifier.ObjectType.VALIDATOR, validatorClass, Validator.class);
            }

            app.addValidator(validatorId, validatorClass);
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
