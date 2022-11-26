package com.sun.faces.config.processor;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.Verifier;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
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

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      this.processAnnotations(facesContext, FacesValidator.class);

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing validator elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList validators = document.getDocumentElement().getElementsByTagNameNS(namespace, "validator");
         if (validators != null && validators.getLength() > 0) {
            this.addValidators(facesContext, validators, namespace);
         }
      }

      this.processDefaultValidatorIds();
   }

   private void processDefaultValidatorIds() {
      Application app = this.getApplication();
      Map defaultValidatorInfo = app.getDefaultValidatorInfo();
      Iterator var3 = defaultValidatorInfo.entrySet().iterator();

      String defaultValidatorId;
      boolean found;
      do {
         if (!var3.hasNext()) {
            return;
         }

         Map.Entry info = (Map.Entry)var3.next();
         defaultValidatorId = (String)info.getKey();
         found = false;
         Iterator registered = app.getValidatorIds();

         while(registered.hasNext()) {
            if (defaultValidatorId.equals(registered.next())) {
               found = true;
               break;
            }
         }
      } while(found);

      throw new ConfigurationException(MessageFormat.format("Default validator ''{0}'' does not reference a registered validator.", defaultValidatorId));
   }

   private void addValidators(FacesContext facesContext, NodeList validators, String namespace) throws XPathExpressionException {
      Application application = this.getApplication();
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
               switch (n.getLocalName()) {
                  case "validator-id":
                     validatorId = this.getNodeText(n);
                     break;
                  case "validator-class":
                     validatorClass = this.getNodeText(n);
               }
            }
         }

         if (validatorId != null && validatorClass != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.addValidator({0},{1})", validatorId, validatorClass));
            }

            boolean doAdd = true;
            if (validatorId.equals("javax.faces.Bean")) {
               doAdd = ApplicationConfigProcessor.isBeanValidatorAvailable(facesContext);
            }

            if (doAdd) {
               if (verifier != null) {
                  verifier.validateObject(Verifier.ObjectType.VALIDATOR, validatorClass, Validator.class);
               }

               application.addValidator(validatorId, validatorClass);
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
