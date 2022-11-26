package com.sun.faces.taglib.html_basic;

import com.sun.faces.taglib.TagParser;
import com.sun.faces.taglib.ValidatorInfo;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import org.xml.sax.Attributes;

public class CommandTagParserImpl implements TagParser {
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
      if (ns.equals("http://java.sun.com/jsf/html") && ln.equals("commandButton")) {
         this.handleCommandButton();
      }

   }

   public void parseEndElement() {
   }

   private void handleCommandButton() {
      Attributes attrs = this.validatorInfo.getAttributes();
      String ln = this.validatorInfo.getLocalName();
      boolean hasValue = false;
      boolean hasImage = false;
      boolean hasBinding = false;

      for(int i = 0; i < attrs.getLength(); ++i) {
         if (attrs.getLocalName(i).equals("value")) {
            hasValue = true;
         }

         if (attrs.getLocalName(i).equals("image")) {
            hasImage = true;
         }

         if (attrs.getLocalName(i).equals("binding")) {
            hasBinding = true;
         }
      }

      if (this.failed = !hasBinding && !hasValue && !hasImage) {
         Object[] obj = new Object[]{ln};
         ResourceBundle rb = ResourceBundle.getBundle("com.sun.faces.resources.Resources");
         this.failureMessages.append(MessageFormat.format(rb.getString("TLV_COMMAND_ERROR"), obj));
         this.failureMessages.append("\n");
      }

   }
}
