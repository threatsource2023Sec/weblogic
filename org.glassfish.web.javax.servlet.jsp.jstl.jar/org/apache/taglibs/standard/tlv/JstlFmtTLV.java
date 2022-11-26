package org.apache.taglibs.standard.tlv;

import java.util.Set;
import java.util.Stack;
import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.ValidationMessage;
import org.apache.taglibs.standard.resources.Resources;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class JstlFmtTLV extends JstlBaseTLV {
   private final String SETLOCALE = "setLocale";
   private final String SETBUNDLE = "setBundle";
   private final String SETTIMEZONE = "setTimeZone";
   private final String BUNDLE = "bundle";
   private final String MESSAGE = "message";
   private final String MESSAGE_PARAM = "param";
   private final String FORMAT_NUMBER = "formatNumber";
   private final String PARSE_NUMBER = "parseNumber";
   private final String PARSE_DATE = "parseDate";
   private final String JSP_TEXT = "jsp:text";
   private final String EVAL = "evaluator";
   private final String MESSAGE_KEY = "key";
   private final String BUNDLE_PREFIX = "prefix";
   private final String VALUE = "value";

   public ValidationMessage[] validate(String prefix, String uri, PageData page) {
      return super.validate(2, prefix, uri, page);
   }

   protected DefaultHandler getHandler() {
      return new Handler();
   }

   private class Handler extends DefaultHandler {
      private int depth;
      private Stack messageDepths;
      private String lastElementName;
      private boolean bodyNecessary;
      private boolean bodyIllegal;

      private Handler() {
         this.depth = 0;
         this.messageDepths = new Stack();
         this.lastElementName = null;
         this.bodyNecessary = false;
         this.bodyIllegal = false;
      }

      public void startElement(String ns, String ln, String qn, Attributes a) {
         if (ln == null) {
            ln = JstlFmtTLV.this.getLocalPart(qn);
         }

         if (!qn.equals("jsp:text")) {
            if (this.bodyIllegal) {
               JstlFmtTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_BODY", (Object)this.lastElementName));
            }

            Set expAtts;
            if (qn.startsWith(JstlFmtTLV.this.prefix + ":") && (expAtts = (Set)JstlFmtTLV.this.config.get(ln)) != null) {
               for(int i = 0; i < a.getLength(); ++i) {
                  String attName = a.getLocalName(i);
                  if (expAtts.contains(attName)) {
                     String vMsg = JstlFmtTLV.this.validateExpression(ln, attName, a.getValue(i));
                     if (vMsg != null) {
                        JstlFmtTLV.this.fail(vMsg);
                     }
                  }
               }
            }

            if (qn.startsWith(JstlFmtTLV.this.prefix + ":") && !JstlFmtTLV.this.hasNoInvalidScope(a)) {
               JstlFmtTLV.this.fail(Resources.getMessage("TLV_INVALID_ATTRIBUTE", "scope", qn, a.getValue("scope")));
            }

            if (qn.startsWith(JstlFmtTLV.this.prefix + ":") && JstlFmtTLV.this.hasEmptyVar(a)) {
               JstlFmtTLV.this.fail(Resources.getMessage("TLV_EMPTY_VAR", (Object)qn));
            }

            if (qn.startsWith(JstlFmtTLV.this.prefix + ":") && !JstlFmtTLV.this.isFmtTag(ns, ln, "setLocale") && !JstlFmtTLV.this.isFmtTag(ns, ln, "setBundle") && !JstlFmtTLV.this.isFmtTag(ns, ln, "setTimeZone") && JstlFmtTLV.this.hasDanglingScope(a)) {
               JstlFmtTLV.this.fail(Resources.getMessage("TLV_DANGLING_SCOPE", (Object)qn));
            }

            if (JstlFmtTLV.this.isFmtTag(ns, ln, "param") && this.messageDepths.empty()) {
               JstlFmtTLV.this.fail(Resources.getMessage("PARAM_OUTSIDE_MESSAGE"));
            }

            if (JstlFmtTLV.this.isFmtTag(ns, ln, "message")) {
               this.messageDepths.push(this.depth);
            }

            this.bodyIllegal = false;
            this.bodyNecessary = false;
            if (!JstlFmtTLV.this.isFmtTag(ns, ln, "param") && !JstlFmtTLV.this.isFmtTag(ns, ln, "formatNumber") && !JstlFmtTLV.this.isFmtTag(ns, ln, "parseNumber") && !JstlFmtTLV.this.isFmtTag(ns, ln, "parseDate")) {
               if (JstlFmtTLV.this.isFmtTag(ns, ln, "message") && !JstlFmtTLV.this.hasAttribute(a, "key")) {
                  this.bodyNecessary = true;
               } else if (JstlFmtTLV.this.isFmtTag(ns, ln, "bundle") && JstlFmtTLV.this.hasAttribute(a, "prefix")) {
                  this.bodyNecessary = true;
               }
            } else if (JstlFmtTLV.this.hasAttribute(a, "value")) {
               this.bodyIllegal = true;
            } else {
               this.bodyNecessary = true;
            }

            this.lastElementName = qn;
            JstlFmtTLV.this.lastElementId = a.getValue("http://java.sun.com/JSP/Page", "id");
            ++this.depth;
         }
      }

      public void characters(char[] ch, int start, int length) {
         this.bodyNecessary = false;
         String s = (new String(ch, start, length)).trim();
         if (!s.equals("")) {
            if (this.bodyIllegal) {
               JstlFmtTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_BODY", (Object)this.lastElementName));
            }

         }
      }

      public void endElement(String ns, String ln, String qn) {
         if (!qn.equals("jsp:text")) {
            if (this.bodyNecessary) {
               JstlFmtTLV.this.fail(Resources.getMessage("TLV_MISSING_BODY", (Object)this.lastElementName));
            }

            this.bodyIllegal = false;
            if (JstlFmtTLV.this.isFmtTag(ns, ln, "message")) {
               this.messageDepths.pop();
            }

            --this.depth;
         }
      }

      // $FF: synthetic method
      Handler(Object x1) {
         this();
      }
   }
}
