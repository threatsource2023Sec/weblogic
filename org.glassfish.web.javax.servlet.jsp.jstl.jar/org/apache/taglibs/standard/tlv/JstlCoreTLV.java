package org.apache.taglibs.standard.tlv;

import java.util.Set;
import java.util.Stack;
import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.ValidationMessage;
import org.apache.taglibs.standard.resources.Resources;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class JstlCoreTLV extends JstlBaseTLV {
   private final String CHOOSE = "choose";
   private final String WHEN = "when";
   private final String OTHERWISE = "otherwise";
   private final String EXPR = "out";
   private final String SET = "set";
   private final String IMPORT = "import";
   private final String URL = "url";
   private final String REDIRECT = "redirect";
   private final String PARAM = "param";
   private final String TEXT = "text";
   private final String VALUE = "value";
   private final String DEFAULT = "default";
   private final String VAR_READER = "varReader";
   private final String IMPORT_WITH_READER = "import varReader=''";
   private final String IMPORT_WITHOUT_READER = "import var=''";

   public ValidationMessage[] validate(String prefix, String uri, PageData page) {
      return super.validate(1, prefix, uri, page);
   }

   protected DefaultHandler getHandler() {
      return new Handler();
   }

   private class Handler extends DefaultHandler {
      private int depth;
      private Stack chooseDepths;
      private Stack chooseHasOtherwise;
      private Stack chooseHasWhen;
      private Stack urlTags;
      private String lastElementName;
      private boolean bodyNecessary;
      private boolean bodyIllegal;

      private Handler() {
         this.depth = 0;
         this.chooseDepths = new Stack();
         this.chooseHasOtherwise = new Stack();
         this.chooseHasWhen = new Stack();
         this.urlTags = new Stack();
         this.lastElementName = null;
         this.bodyNecessary = false;
         this.bodyIllegal = false;
      }

      public void startElement(String ns, String ln, String qn, Attributes a) {
         if (ln == null) {
            ln = JstlCoreTLV.this.getLocalPart(qn);
         }

         if (!JstlCoreTLV.this.isJspTag(ns, ln, "text")) {
            if (this.bodyIllegal) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_BODY", (Object)this.lastElementName));
            }

            Set expAtts;
            if (qn.startsWith(JstlCoreTLV.this.prefix + ":") && (expAtts = (Set)JstlCoreTLV.this.config.get(ln)) != null) {
               for(int i = 0; i < a.getLength(); ++i) {
                  String attName = a.getLocalName(i);
                  if (expAtts.contains(attName)) {
                     String vMsg = JstlCoreTLV.this.validateExpression(ln, attName, a.getValue(i));
                     if (vMsg != null) {
                        JstlCoreTLV.this.fail(vMsg);
                     }
                  }
               }
            }

            if (qn.startsWith(JstlCoreTLV.this.prefix + ":") && !JstlCoreTLV.this.hasNoInvalidScope(a)) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_INVALID_ATTRIBUTE", "scope", qn, a.getValue("scope")));
            }

            if (qn.startsWith(JstlCoreTLV.this.prefix + ":") && JstlCoreTLV.this.hasEmptyVar(a)) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_EMPTY_VAR", (Object)qn));
            }

            if (qn.startsWith(JstlCoreTLV.this.prefix + ":") && JstlCoreTLV.this.hasDanglingScope(a)) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_DANGLING_SCOPE", (Object)qn));
            }

            if (this.chooseChild()) {
               if (JstlCoreTLV.this.isCoreTag(ns, ln, "when")) {
                  this.chooseHasWhen.pop();
                  this.chooseHasWhen.push(Boolean.TRUE);
               }

               if (!JstlCoreTLV.this.isCoreTag(ns, ln, "when") && !JstlCoreTLV.this.isCoreTag(ns, ln, "otherwise")) {
                  JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_CHILD_TAG", JstlCoreTLV.this.prefix, "choose", qn));
               }

               if ((Boolean)this.chooseHasOtherwise.peek()) {
                  JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_ORDER", qn, JstlCoreTLV.this.prefix, "otherwise", "choose"));
               }

               if (JstlCoreTLV.this.isCoreTag(ns, ln, "otherwise")) {
                  this.chooseHasOtherwise.pop();
                  this.chooseHasOtherwise.push(Boolean.TRUE);
               }
            }

            if (JstlCoreTLV.this.isCoreTag(ns, ln, "param")) {
               if (this.urlTags.empty() || this.urlTags.peek().equals("param")) {
                  JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_ORPHAN", (Object)"param"));
               }

               if (!this.urlTags.empty() && this.urlTags.peek().equals("import varReader=''")) {
                  JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_PARAM", JstlCoreTLV.this.prefix, "param", "import", "varReader"));
               }
            } else if (!this.urlTags.empty() && this.urlTags.peek().equals("import var=''")) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_CHILD_TAG", JstlCoreTLV.this.prefix, "import", qn));
            }

            if (JstlCoreTLV.this.isCoreTag(ns, ln, "choose")) {
               this.chooseDepths.push(this.depth);
               this.chooseHasWhen.push(Boolean.FALSE);
               this.chooseHasOtherwise.push(Boolean.FALSE);
            }

            if (JstlCoreTLV.this.isCoreTag(ns, ln, "import")) {
               if (JstlCoreTLV.this.hasAttribute(a, "varReader")) {
                  this.urlTags.push("import varReader=''");
               } else {
                  this.urlTags.push("import var=''");
               }
            } else if (JstlCoreTLV.this.isCoreTag(ns, ln, "param")) {
               this.urlTags.push("param");
            } else if (JstlCoreTLV.this.isCoreTag(ns, ln, "redirect")) {
               this.urlTags.push("redirect");
            } else if (JstlCoreTLV.this.isCoreTag(ns, ln, "url")) {
               this.urlTags.push("url");
            }

            this.bodyIllegal = false;
            this.bodyNecessary = false;
            if (JstlCoreTLV.this.isCoreTag(ns, ln, "out")) {
               if (JstlCoreTLV.this.hasAttribute(a, "default")) {
                  this.bodyIllegal = true;
               }
            } else if (JstlCoreTLV.this.isCoreTag(ns, ln, "set") && JstlCoreTLV.this.hasAttribute(a, "value")) {
               this.bodyIllegal = true;
            }

            this.lastElementName = qn;
            JstlCoreTLV.this.lastElementId = a.getValue("http://java.sun.com/JSP/Page", "id");
            ++this.depth;
         }
      }

      public void characters(char[] ch, int start, int length) {
         this.bodyNecessary = false;
         String s = (new String(ch, start, length)).trim();
         if (!s.equals("")) {
            if (this.bodyIllegal) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_BODY", (Object)this.lastElementName));
            }

            if (!this.urlTags.empty() && this.urlTags.peek().equals("import var=''")) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_ILLEGAL_BODY", (Object)(JstlCoreTLV.this.prefix + ":" + "import")));
            }

            if (this.chooseChild()) {
               String msg = Resources.getMessage("TLV_ILLEGAL_TEXT_BODY", JstlCoreTLV.this.prefix, "choose", s.length() < 7 ? s : s.substring(0, 7));
               JstlCoreTLV.this.fail(msg);
            }

         }
      }

      public void endElement(String ns, String ln, String qn) {
         if (!JstlCoreTLV.this.isJspTag(ns, ln, "text")) {
            if (this.bodyNecessary) {
               JstlCoreTLV.this.fail(Resources.getMessage("TLV_MISSING_BODY", (Object)this.lastElementName));
            }

            this.bodyIllegal = false;
            if (JstlCoreTLV.this.isCoreTag(ns, ln, "choose")) {
               Boolean b = (Boolean)this.chooseHasWhen.pop();
               if (!b) {
                  JstlCoreTLV.this.fail(Resources.getMessage("TLV_PARENT_WITHOUT_SUBTAG", "choose", "when"));
               }

               this.chooseDepths.pop();
               this.chooseHasOtherwise.pop();
            }

            if (JstlCoreTLV.this.isCoreTag(ns, ln, "import") || JstlCoreTLV.this.isCoreTag(ns, ln, "param") || JstlCoreTLV.this.isCoreTag(ns, ln, "redirect") || JstlCoreTLV.this.isCoreTag(ns, ln, "url")) {
               this.urlTags.pop();
            }

            --this.depth;
         }
      }

      private boolean chooseChild() {
         return !this.chooseDepths.empty() && this.depth - 1 == (Integer)this.chooseDepths.peek();
      }

      // $FF: synthetic method
      Handler(Object x1) {
         this();
      }
   }
}
