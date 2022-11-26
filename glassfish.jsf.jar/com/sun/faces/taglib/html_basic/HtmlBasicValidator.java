package com.sun.faces.taglib.html_basic;

import com.sun.faces.taglib.FacesValidator;
import com.sun.faces.taglib.ValidatorInfo;
import java.beans.Beans;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class HtmlBasicValidator extends FacesValidator {
   private ValidatorInfo validatorInfo;
   private CommandTagParserImpl commandTagParser;

   public HtmlBasicValidator() {
      this.init();
   }

   protected void init() {
      super.init();
      this.failed = false;
      this.validatorInfo = new ValidatorInfo();
      this.commandTagParser = new CommandTagParserImpl();
      this.commandTagParser.setValidatorInfo(this.validatorInfo);
   }

   public void release() {
      super.release();
      this.init();
   }

   protected DefaultHandler getSAXHandler() {
      return Beans.isDesignTime() ? null : new HtmlBasicValidatorHandler();
   }

   protected String getFailureMessage(String prefix, String uri) {
      StringBuffer result = new StringBuffer();
      if (this.commandTagParser.getMessage() != null) {
         result.append(this.commandTagParser.getMessage());
      }

      return result.toString();
   }

   private class HtmlBasicValidatorHandler extends DefaultHandler {
      private HtmlBasicValidatorHandler() {
      }

      public void startElement(String ns, String ln, String qn, Attributes attrs) {
         HtmlBasicValidator.this.maybeSnagTLPrefixes(qn, attrs);
         HtmlBasicValidator.this.validatorInfo.setNameSpace(ns);
         HtmlBasicValidator.this.validatorInfo.setLocalName(ln);
         HtmlBasicValidator.this.validatorInfo.setQName(qn);
         HtmlBasicValidator.this.validatorInfo.setAttributes(attrs);
         HtmlBasicValidator.this.commandTagParser.parseStartElement();
         if (HtmlBasicValidator.this.commandTagParser.hasFailed()) {
            HtmlBasicValidator.this.failed = true;
         }

      }

      public void endElement(String ns, String ln, String qn) {
      }

      // $FF: synthetic method
      HtmlBasicValidatorHandler(Object x1) {
         this();
      }
   }
}
