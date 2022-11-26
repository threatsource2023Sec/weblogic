package org.apache.taglibs.standard.tlv;

import java.util.Set;
import java.util.Stack;
import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.ValidationMessage;
import org.apache.taglibs.standard.resources.Resources;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class JstlSqlTLV extends JstlBaseTLV {
   private final String SETDATASOURCE = "setDataSource";
   private final String QUERY = "query";
   private final String UPDATE = "update";
   private final String TRANSACTION = "transaction";
   private final String PARAM = "param";
   private final String DATEPARAM = "dateParam";
   private final String JSP_TEXT = "jsp:text";
   private final String SQL = "sql";
   private final String DATASOURCE = "dataSource";

   public ValidationMessage[] validate(String prefix, String uri, PageData page) {
      return super.validate(3, prefix, uri, page);
   }

   protected DefaultHandler getHandler() {
      return new Handler();
   }

   private class Handler extends DefaultHandler {
      private int depth;
      private Stack queryDepths;
      private Stack updateDepths;
      private Stack transactionDepths;
      private String lastElementName;
      private boolean bodyNecessary;
      private boolean bodyIllegal;

      private Handler() {
         this.depth = 0;
         this.queryDepths = new Stack();
         this.updateDepths = new Stack();
         this.transactionDepths = new Stack();
         this.lastElementName = null;
         this.bodyNecessary = false;
         this.bodyIllegal = false;
      }

      public void startElement(String ns, String ln, String qn, Attributes a) {
         if (ln == null) {
            ln = JstlSqlTLV.this.getLocalPart(qn);
         }

         if (!qn.equals("jsp:text")) {
            if (this.bodyIllegal) {
               JstlSqlTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_BODY", (Object)this.lastElementName));
            }

            Set expAtts;
            if (qn.startsWith(JstlSqlTLV.this.prefix + ":") && (expAtts = (Set)JstlSqlTLV.this.config.get(ln)) != null) {
               for(int i = 0; i < a.getLength(); ++i) {
                  String attName = a.getLocalName(i);
                  if (expAtts.contains(attName)) {
                     String vMsg = JstlSqlTLV.this.validateExpression(ln, attName, a.getValue(i));
                     if (vMsg != null) {
                        JstlSqlTLV.this.fail(vMsg);
                     }
                  }
               }
            }

            if (qn.startsWith(JstlSqlTLV.this.prefix + ":") && !JstlSqlTLV.this.hasNoInvalidScope(a)) {
               JstlSqlTLV.this.fail(Resources.getMessage("TLV_INVALID_ATTRIBUTE", "scope", qn, a.getValue("scope")));
            }

            if (qn.startsWith(JstlSqlTLV.this.prefix + ":") && JstlSqlTLV.this.hasEmptyVar(a)) {
               JstlSqlTLV.this.fail(Resources.getMessage("TLV_EMPTY_VAR", (Object)qn));
            }

            if (qn.startsWith(JstlSqlTLV.this.prefix + ":") && JstlSqlTLV.this.hasDanglingScope(a) && !qn.startsWith(JstlSqlTLV.this.prefix + ":" + "setDataSource")) {
               JstlSqlTLV.this.fail(Resources.getMessage("TLV_DANGLING_SCOPE", (Object)qn));
            }

            if ((JstlSqlTLV.this.isSqlTag(ns, ln, "param") || JstlSqlTLV.this.isSqlTag(ns, ln, "dateParam")) && this.queryDepths.empty() && this.updateDepths.empty()) {
               JstlSqlTLV.this.fail(Resources.getMessage("SQL_PARAM_OUTSIDE_PARENT"));
            }

            if (JstlSqlTLV.this.isSqlTag(ns, ln, "query")) {
               this.queryDepths.push(this.depth);
            }

            if (JstlSqlTLV.this.isSqlTag(ns, ln, "update")) {
               this.updateDepths.push(this.depth);
            }

            if (JstlSqlTLV.this.isSqlTag(ns, ln, "transaction")) {
               this.transactionDepths.push(this.depth);
            }

            this.bodyIllegal = false;
            this.bodyNecessary = false;
            if (JstlSqlTLV.this.isSqlTag(ns, ln, "query") || JstlSqlTLV.this.isSqlTag(ns, ln, "update")) {
               if (!JstlSqlTLV.this.hasAttribute(a, "sql")) {
                  this.bodyNecessary = true;
               }

               if (JstlSqlTLV.this.hasAttribute(a, "dataSource") && !this.transactionDepths.empty()) {
                  JstlSqlTLV.this.fail(Resources.getMessage("ERROR_NESTED_DATASOURCE"));
               }
            }

            if (JstlSqlTLV.this.isSqlTag(ns, ln, "dateParam")) {
               this.bodyIllegal = true;
            }

            this.lastElementName = qn;
            JstlSqlTLV.this.lastElementId = a.getValue("http://java.sun.com/JSP/Page", "id");
            ++this.depth;
         }
      }

      public void characters(char[] ch, int start, int length) {
         this.bodyNecessary = false;
         String s = (new String(ch, start, length)).trim();
         if (!s.equals("")) {
            if (this.bodyIllegal) {
               JstlSqlTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_BODY", (Object)this.lastElementName));
            }

         }
      }

      public void endElement(String ns, String ln, String qn) {
         if (!qn.equals("jsp:text")) {
            if (this.bodyNecessary) {
               JstlSqlTLV.this.fail(Resources.getMessage("TLV_MISSING_BODY", (Object)this.lastElementName));
            }

            this.bodyIllegal = false;
            if (JstlSqlTLV.this.isSqlTag(ns, ln, "query")) {
               this.queryDepths.pop();
            }

            if (JstlSqlTLV.this.isSqlTag(ns, ln, "update")) {
               this.updateDepths.pop();
            }

            if (JstlSqlTLV.this.isSqlTag(ns, ln, "transaction")) {
               this.transactionDepths.pop();
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
