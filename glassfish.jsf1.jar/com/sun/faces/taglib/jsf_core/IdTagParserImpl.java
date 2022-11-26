package com.sun.faces.taglib.jsf_core;

import com.sun.faces.taglib.FacesValidator;
import com.sun.faces.taglib.TagParser;
import com.sun.faces.taglib.ValidatorInfo;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import org.xml.sax.Attributes;

public class IdTagParserImpl implements TagParser {
   private boolean siblingSatisfied = true;
   private int requiresIdCount = 0;
   private StringBuffer requiresIdList = new StringBuffer();
   private boolean failed = false;
   private ValidatorInfo validatorInfo;
   private boolean nestedInNamingContainer;

   public void setValidatorInfo(ValidatorInfo validatorInfo) {
      this.validatorInfo = validatorInfo;
   }

   public String getMessage() {
      Object[] obj = new Object[]{this.requiresIdList};
      ResourceBundle rb = ResourceBundle.getBundle("com.sun.faces.resources.Resources");
      return MessageFormat.format(rb.getString("TLV_ID_ERROR"), obj);
   }

   public boolean hasFailed() {
      return this.failed;
   }

   public void parseStartElement() {
      String ns = this.validatorInfo.getNameSpace();
      String ln = this.validatorInfo.getLocalName();
      String qn = this.validatorInfo.getQName();
      Attributes a = this.validatorInfo.getAttributes();
      FacesValidator validator = this.validatorInfo.getValidator();
      if (this.isNamingContainerTag(validator, ns, ln)) {
         this.nestedInNamingContainer = true;
      } else if (ns.equals("http://java.sun.com/jsf/html") && this.requiresIdCount > 0) {
         if (!this.nestedInNamingContainer && !this.hasIdAttribute(a)) {
            this.failed = true;
            this.requiresIdList.append(qn).append(' ');
         }
      } else if (this.requiresIdCount == 0 && !this.siblingSatisfied) {
         if ((ns.equals("http://java.sun.com/jsf/html") || ns.equals("http://java.sun.com/jsf/core")) && !this.hasIdAttribute(a) && !this.nestedInNamingContainer) {
            this.failed = true;
            this.requiresIdList.append(qn).append(' ');
         }

         this.siblingSatisfied = true;
      } else if (this.requiresIdCount == 0) {
         this.siblingSatisfied = true;
      }

   }

   public void parseEndElement() {
      String ns = this.validatorInfo.getNameSpace();
      String ln = this.validatorInfo.getLocalName();
      FacesValidator validator = this.validatorInfo.getValidator();
      if (this.isNamingContainerTag(validator, ns, ln)) {
         this.nestedInNamingContainer = false;
      }

   }

   private boolean hasIdAttribute(Attributes a) {
      for(int i = 0; i < a.getLength(); ++i) {
         if (a.getQName(i).equals("id")) {
            return true;
         }
      }

      return false;
   }

   private boolean isNamingContainerTag(FacesValidator validator, String ns, String ln) {
      if (ns.equals("http://java.sun.com/jsf/html") && ln.equals(validator.getJSF_FORM_LN())) {
         return true;
      } else {
         return ns.equals("http://java.sun.com/jsf/core") && ln.equals(validator.getJSF_SUBVIEW_LN());
      }
   }
}
