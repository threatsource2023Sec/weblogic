package com.sun.faces.taglib.jsf_core;

import com.sun.faces.taglib.FacesValidator;
import com.sun.faces.taglib.ValidatorInfo;
import com.sun.faces.util.Util;
import java.beans.Beans;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class CoreValidator extends FacesValidator {
   private ValidatorInfo validatorInfo;
   private IdTagParserImpl idTagParser;
   private CoreTagParserImpl coreTagParser;

   public CoreValidator() {
      this.init();
   }

   protected void init() {
      super.init();
      this.failed = false;
      this.validatorInfo = new ValidatorInfo();
      this.idTagParser = new IdTagParserImpl();
      this.idTagParser.setValidatorInfo(this.validatorInfo);
      this.coreTagParser = new CoreTagParserImpl();
      this.coreTagParser.setValidatorInfo(this.validatorInfo);
   }

   public void release() {
      super.release();
      this.init();
   }

   protected DefaultHandler getSAXHandler() {
      return !Beans.isDesignTime() && Util.isCoreTLVActive() ? new CoreValidatorHandler() : null;
   }

   protected String getFailureMessage(String prefix, String uri) {
      StringBuffer result = new StringBuffer();
      if (this.idTagParser.getMessage() != null) {
         result.append(this.idTagParser.getMessage());
      }

      if (this.coreTagParser.getMessage() != null) {
         result.append(this.coreTagParser.getMessage());
      }

      return result.toString();
   }

   private class CoreValidatorHandler extends DefaultHandler {
      private CoreValidatorHandler() {
      }

      public void startElement(String ns, String ln, String qn, Attributes attrs) {
         CoreValidator.this.maybeSnagTLPrefixes(qn, attrs);
         CoreValidator.this.validatorInfo.setNameSpace(ns);
         CoreValidator.this.validatorInfo.setLocalName(ln);
         CoreValidator.this.validatorInfo.setQName(qn);
         CoreValidator.this.validatorInfo.setAttributes(attrs);
         CoreValidator.this.validatorInfo.setValidator(CoreValidator.this);
         CoreValidator.this.idTagParser.parseStartElement();
         if (CoreValidator.this.idTagParser.hasFailed()) {
            CoreValidator.this.failed = true;
         }

         CoreValidator.this.coreTagParser.parseStartElement();
         if (CoreValidator.this.coreTagParser.hasFailed()) {
            CoreValidator.this.failed = true;
         }

      }

      public void endElement(String ns, String ln, String qn) {
         CoreValidator.this.validatorInfo.setNameSpace(ns);
         CoreValidator.this.validatorInfo.setLocalName(ln);
         CoreValidator.this.validatorInfo.setQName(qn);
         CoreValidator.this.idTagParser.parseEndElement();
         CoreValidator.this.coreTagParser.parseEndElement();
      }

      // $FF: synthetic method
      CoreValidatorHandler(Object x1) {
         this();
      }
   }
}
