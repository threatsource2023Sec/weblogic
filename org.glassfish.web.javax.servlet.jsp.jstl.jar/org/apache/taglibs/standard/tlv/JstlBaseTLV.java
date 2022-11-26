package org.apache.taglibs.standard.tlv;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagLibraryValidator;
import javax.servlet.jsp.tagext.ValidationMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluator;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.resources.Resources;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class JstlBaseTLV extends TagLibraryValidator {
   private final String EXP_ATT_PARAM = "expressionAttributes";
   protected static final String VAR = "var";
   protected static final String SCOPE = "scope";
   protected static final String PAGE_SCOPE = "page";
   protected static final String REQUEST_SCOPE = "request";
   protected static final String SESSION_SCOPE = "session";
   protected static final String APPLICATION_SCOPE = "application";
   protected final String JSP = "http://java.sun.com/JSP/Page";
   private static final int TYPE_UNDEFINED = 0;
   protected static final int TYPE_CORE = 1;
   protected static final int TYPE_FMT = 2;
   protected static final int TYPE_SQL = 3;
   protected static final int TYPE_XML = 4;
   private int tlvType = 0;
   protected String uri;
   protected String prefix;
   protected Vector messageVector;
   protected Map config;
   protected boolean failed;
   protected String lastElementId;

   protected abstract DefaultHandler getHandler();

   public JstlBaseTLV() {
      this.init();
   }

   private synchronized void init() {
      this.messageVector = null;
      this.prefix = null;
      this.config = null;
   }

   public void release() {
      super.release();
      this.init();
   }

   public synchronized ValidationMessage[] validate(int type, String prefix, String uri, PageData page) {
      try {
         this.tlvType = type;
         this.uri = uri;
         this.messageVector = new Vector();
         this.prefix = prefix;

         try {
            if (this.config == null) {
               this.configure((String)this.getInitParameters().get("expressionAttributes"));
            }
         } catch (NoSuchElementException var8) {
            return vmFromString(Resources.getMessage("TLV_PARAMETER_ERROR", (Object)"expressionAttributes"));
         }

         DefaultHandler h = this.getHandler();
         SAXParserFactory f = SAXParserFactory.newInstance();
         f.setValidating(false);
         f.setNamespaceAware(true);
         SAXParser p = f.newSAXParser();
         p.parse(page.getInputStream(), h);
         return this.messageVector.size() == 0 ? null : vmFromVector(this.messageVector);
      } catch (SAXException var9) {
         return vmFromString(var9.toString());
      } catch (ParserConfigurationException var10) {
         return vmFromString(var10.toString());
      } catch (IOException var11) {
         return vmFromString(var11.toString());
      }
   }

   protected String validateExpression(String elem, String att, String expr) {
      ExpressionEvaluator current;
      try {
         current = ExpressionEvaluatorManager.getEvaluatorByName("org.apache.taglibs.standard.lang.jstl.Evaluator");
      } catch (JspException var6) {
         return var6.getMessage();
      }

      String response = current.validate(att, expr);
      return response == null ? response : "tag = '" + elem + "' / attribute = '" + att + "': " + response;
   }

   protected boolean isTag(String tagUri, String tagLn, String matchUri, String matchLn) {
      if (tagUri != null && tagUri.length() != 0 && tagLn != null && matchUri != null && matchLn != null) {
         if (tagUri.length() > matchUri.length()) {
            return tagUri.startsWith(matchUri) && tagLn.equals(matchLn);
         } else {
            return matchUri.startsWith(tagUri) && tagLn.equals(matchLn);
         }
      } else {
         return false;
      }
   }

   protected boolean isJspTag(String tagUri, String tagLn, String target) {
      return this.isTag(tagUri, tagLn, "http://java.sun.com/JSP/Page", target);
   }

   private boolean isTag(int type, String tagUri, String tagLn, String target) {
      return this.tlvType == type && this.isTag(tagUri, tagLn, this.uri, target);
   }

   protected boolean isCoreTag(String tagUri, String tagLn, String target) {
      return this.isTag(1, tagUri, tagLn, target);
   }

   protected boolean isFmtTag(String tagUri, String tagLn, String target) {
      return this.isTag(2, tagUri, tagLn, target);
   }

   protected boolean isSqlTag(String tagUri, String tagLn, String target) {
      return this.isTag(3, tagUri, tagLn, target);
   }

   protected boolean isXmlTag(String tagUri, String tagLn, String target) {
      return this.isTag(4, tagUri, tagLn, target);
   }

   protected boolean hasAttribute(Attributes a, String att) {
      return a.getValue(att) != null;
   }

   protected synchronized void fail(String message) {
      this.failed = true;
      this.messageVector.add(new ValidationMessage(this.lastElementId, message));
   }

   protected boolean isSpecified(TagData data, String attributeName) {
      return data.getAttribute(attributeName) != null;
   }

   protected boolean hasNoInvalidScope(Attributes a) {
      String scope = a.getValue("scope");
      return scope == null || scope.equals("page") || scope.equals("request") || scope.equals("session") || scope.equals("application");
   }

   protected boolean hasEmptyVar(Attributes a) {
      return "".equals(a.getValue("var"));
   }

   protected boolean hasDanglingScope(Attributes a) {
      return a.getValue("scope") != null && a.getValue("var") == null;
   }

   protected String getLocalPart(String qname) {
      int colon = qname.indexOf(":");
      return colon == -1 ? qname : qname.substring(colon + 1);
   }

   private void configure(String info) {
      this.config = new HashMap();
      if (info != null) {
         String attribute;
         Object atts;
         for(StringTokenizer st = new StringTokenizer(info); st.hasMoreTokens(); ((Set)atts).add(attribute)) {
            String pair = st.nextToken();
            StringTokenizer pairTokens = new StringTokenizer(pair, ":");
            String element = pairTokens.nextToken();
            attribute = pairTokens.nextToken();
            atts = this.config.get(element);
            if (atts == null) {
               atts = new HashSet();
               this.config.put(element, atts);
            }
         }

      }
   }

   static ValidationMessage[] vmFromString(String message) {
      return new ValidationMessage[]{new ValidationMessage((String)null, message)};
   }

   static ValidationMessage[] vmFromVector(Vector v) {
      ValidationMessage[] vm = new ValidationMessage[v.size()];

      for(int i = 0; i < vm.length; ++i) {
         vm[i] = (ValidationMessage)v.get(i);
      }

      return vm;
   }
}
