package com.sun.faces.taglib;

import java.io.IOException;
import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.TagLibraryValidator;
import javax.servlet.jsp.tagext.ValidationMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class FacesValidator extends TagLibraryValidator {
   protected boolean failed;
   private static final String JSF_CORE_URI = "http://java.sun.com/jsf/core";
   private static final String JSF_HTML_URI = "http://java.sun.com/jsf/html";
   private static final String JSTL_OLD_CORE_URI = "http://java.sun.com/jstl/core";
   private static final String JSTL_NEW_CORE_URI = "http://java.sun.com/jsp/jstl/core";
   protected String JSF_HTML_PRE = null;
   protected String JSF_CORE_PRE = null;
   protected String JSTL_CORE_PRE = null;
   protected String JSTL_IF_QN = ":if";
   protected String JSTL_IF_LN = "if";
   protected String JSTL_CHOOSE_QN = ":choose";
   protected String JSTL_CHOOSE_LN = "choose";
   protected String JSTL_FOREACH_QN = ":forEach";
   protected String JSTL_FOREACH_LN = "forEach";
   protected String JSTL_FORTOKENS_QN = ":forTokens";
   protected String JSTL_FORTOKENS_LN = "forTokens";
   protected String JSF_FORM_QN = ":form";
   protected String JSF_FORM_LN = "form";
   protected String JSF_SUBVIEW_QN = ":subview";
   protected String JSF_SUBVIEW_LN = "subview";

   public String getJSF_HTML_PRE() {
      return this.JSF_HTML_PRE;
   }

   public String getJSF_CORE_PRE() {
      return this.JSF_CORE_PRE;
   }

   public String getJSTL_CORE_PRE() {
      return this.JSTL_CORE_PRE;
   }

   public String getJSTL_IF_QN() {
      return this.JSTL_IF_QN;
   }

   public String getJSTL_IF_LN() {
      return this.JSTL_IF_LN;
   }

   public String getJSTL_CHOOSE_QN() {
      return this.JSTL_CHOOSE_QN;
   }

   public String getJSTL_CHOOSE_LN() {
      return this.JSTL_CHOOSE_LN;
   }

   public String getJSTL_FOREACH_QN() {
      return this.JSTL_FOREACH_QN;
   }

   public String getJSTL_FOREACH_LN() {
      return this.JSTL_FOREACH_LN;
   }

   public String getJSTL_FORTOKENS_QN() {
      return this.JSTL_FORTOKENS_QN;
   }

   public String getJSTL_FORTOKENS_LN() {
      return this.JSTL_FORTOKENS_LN;
   }

   public String getJSF_FORM_QN() {
      return this.JSF_FORM_QN;
   }

   public String getJSF_FORM_LN() {
      return this.JSF_FORM_LN;
   }

   public String getJSF_SUBVIEW_QN() {
      return this.JSF_SUBVIEW_QN;
   }

   public String getJSF_SUBVIEW_LN() {
      return this.JSF_SUBVIEW_LN;
   }

   public FacesValidator() {
      this.init();
   }

   protected void init() {
      this.failed = false;
   }

   public void release() {
      super.release();
      this.init();
   }

   protected abstract DefaultHandler getSAXHandler();

   protected abstract String getFailureMessage(String var1, String var2);

   public synchronized ValidationMessage[] validate(String prefix, String uri, PageData page) {
      ValidationMessage[] result = null;

      try {
         DefaultHandler h = this.getSAXHandler();
         if (null == h) {
            return result;
         }

         SAXParserFactory f = SAXParserFactory.newInstance();
         f.setNamespaceAware(true);
         f.setValidating(true);
         SAXParser p = f.newSAXParser();
         p.parse(page.getInputStream(), h);
         if (this.failed) {
            result = this.vmFromString(this.getFailureMessage(prefix, uri));
         } else {
            result = null;
         }
      } catch (SAXException var8) {
         result = this.vmFromString(var8.toString());
      } catch (ParserConfigurationException var9) {
         result = this.vmFromString(var9.toString());
      } catch (IOException var10) {
         result = this.vmFromString(var10.toString());
      }

      this.release();
      return result;
   }

   private ValidationMessage[] vmFromString(String message) {
      return new ValidationMessage[]{new ValidationMessage((String)null, message)};
   }

   protected void debugPrintTagData(String ns, String ln, String qn, Attributes attrs) {
      int i = false;
      int len = attrs.getLength();
      System.out.println("nameSpace: " + ns + " localName: " + ln + " QName: " + qn);

      for(int i = 0; i < len; ++i) {
         System.out.println("\tlocalName: " + attrs.getLocalName(i));
         System.out.println("\tQName: " + attrs.getQName(i));
         System.out.println("\tvalue: " + attrs.getValue(i) + "\n");
      }

   }

   protected void maybeSnagTLPrefixes(String qName, Attributes attrs) {
      if (qName.equals("jsp:root")) {
         int i = false;
         int len = attrs.getLength();
         String prefix = null;
         String value = null;

         for(int i = 0; i < len; ++i) {
            if (null != (value = attrs.getValue(i)) && null != (qName = attrs.getQName(i)) && qName.startsWith("xmlns:") && 7 <= qName.length()) {
               prefix = qName.substring(6);
               if (value.equals("http://java.sun.com/jsf/core")) {
                  this.JSF_CORE_PRE = prefix;
                  this.JSF_SUBVIEW_QN = this.JSF_CORE_PRE + this.JSF_SUBVIEW_QN;
               } else if (value.equals("http://java.sun.com/jsf/html")) {
                  this.JSF_HTML_PRE = prefix;
                  this.JSF_FORM_QN = this.JSF_HTML_PRE + this.JSF_FORM_QN;
               } else if (value.equals("http://java.sun.com/jstl/core") || value.equals("http://java.sun.com/jsp/jstl/core")) {
                  this.JSTL_CORE_PRE = prefix;
                  this.JSTL_IF_QN = this.JSTL_CORE_PRE + this.JSTL_IF_QN;
                  this.JSTL_CHOOSE_QN = this.JSTL_CORE_PRE + this.JSTL_CHOOSE_QN;
                  this.JSTL_FOREACH_QN = this.JSTL_CORE_PRE + this.JSTL_FOREACH_QN;
                  this.JSTL_FORTOKENS_QN = this.JSTL_CORE_PRE + this.JSTL_FORTOKENS_QN;
               }
            }
         }

      }
   }
}
