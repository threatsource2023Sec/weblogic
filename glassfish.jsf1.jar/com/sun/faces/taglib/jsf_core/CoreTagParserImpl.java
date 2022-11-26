package com.sun.faces.taglib.jsf_core;

import com.sun.faces.taglib.TagParser;
import com.sun.faces.taglib.ValidatorInfo;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import org.xml.sax.Attributes;

public class CoreTagParserImpl implements TagParser {
   private StringBuffer failureMessages = new StringBuffer();
   private boolean failed = false;
   private ValidatorInfo validatorInfo;

   public void setValidatorInfo(ValidatorInfo validatorInfo) {
      this.validatorInfo = validatorInfo;
   }

   public String getMessage() {
      return this.failureMessages.toString();
   }

   public boolean hasFailed() {
      return this.failed;
   }

   public void parseStartElement() {
      String ns = this.validatorInfo.getNameSpace();
      String ln = this.validatorInfo.getLocalName();
      if (ns.equals("http://java.sun.com/jsf/core")) {
         if (ln.equals("valueChangeListener")) {
            this.handleListener();
         } else if (ln.equals("actionListener")) {
            this.handleListener();
         } else if (ln.equals("converter")) {
            this.handleConverter();
         } else if (ln.equals("validator")) {
            this.handleValidator();
         }
      }

   }

   public void parseEndElement() {
   }

   private void handleListener() {
      Attributes attrs = this.validatorInfo.getAttributes();
      String ln = this.validatorInfo.getLocalName();
      boolean hasType = false;
      boolean hasBinding = false;

      for(int i = 0; i < attrs.getLength(); ++i) {
         if (attrs.getLocalName(i).equals("type")) {
            hasType = true;
         }

         if (attrs.getLocalName(i).equals("binding")) {
            hasBinding = true;
         }
      }

      if (this.failed = !hasBinding && !hasType) {
         Object[] obj = new Object[]{ln};
         ResourceBundle rb = ResourceBundle.getBundle("com.sun.faces.resources.Resources");
         this.failureMessages.append(MessageFormat.format(rb.getString("TLV_LISTENER_ERROR"), obj));
         this.failureMessages.append("\n");
      }

   }

   private void handleValidator() {
      Attributes attrs = this.validatorInfo.getAttributes();
      String ln = this.validatorInfo.getLocalName();
      boolean hasValidatorId = false;
      boolean hasBinding = false;

      for(int i = 0; i < attrs.getLength(); ++i) {
         if (attrs.getLocalName(i).equals("validatorId")) {
            hasValidatorId = true;
         }

         if (attrs.getLocalName(i).equals("binding")) {
            hasBinding = true;
         }
      }

      if (this.failed = !hasBinding && !hasValidatorId) {
         Object[] obj = new Object[]{ln};
         ResourceBundle rb = ResourceBundle.getBundle("com.sun.faces.resources.Resources");
         this.failureMessages.append(MessageFormat.format(rb.getString("TLV_VALIDATOR_ERROR"), obj));
         this.failureMessages.append("\n");
      }

   }

   private void handleConverter() {
      Attributes attrs = this.validatorInfo.getAttributes();
      String ln = this.validatorInfo.getLocalName();
      boolean hasConverterId = false;
      boolean hasBinding = false;

      for(int i = 0; i < attrs.getLength(); ++i) {
         if (attrs.getLocalName(i).equals("converterId")) {
            hasConverterId = true;
         }

         if (attrs.getLocalName(i).equals("binding")) {
            hasBinding = true;
         }
      }

      if (this.failed = !hasBinding && !hasConverterId) {
         Object[] obj = new Object[]{ln};
         ResourceBundle rb = ResourceBundle.getBundle("com.sun.faces.resources.Resources");
         this.failureMessages.append(MessageFormat.format(rb.getString("TLV_CONVERTER_ERROR"), obj));
         this.failureMessages.append("\n");
      }

   }
}
