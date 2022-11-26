package weblogicx.jsp.tags;

import java.lang.reflect.Array;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;
import weblogic.utils.UnsyncStringBuffer;

public class BeanParamTag implements BodyTag {
   Tag parent;
   BodyContent bc;
   PageContext pc;
   String objectID;
   String lastExpression = "";
   BeanParamInterpreter bpi;
   int iter = 0;
   boolean isArray = false;
   boolean iterate = true;
   boolean relative = true;
   int arrayLen = 1;
   Integer parentIndex = null;
   static final boolean debug = false;
   private String interpreterClass;

   public int getIndex() {
      return this.iter;
   }

   public void setRelative(boolean b) {
      this.p("setRelative(" + b + ")");
      this.relative = b;
   }

   public boolean getRelative() {
      return this.relative;
   }

   public void setIterate(boolean b) {
      this.p("setIterate(" + b + ")");
      this.iterate = b;
   }

   public boolean getIterate() {
      return this.iterate;
   }

   public void setInterpreterClass(String interpreterClass) {
      this.interpreterClass = interpreterClass;
   }

   public String getInterpreterClass() {
      if (this.interpreterClass == null) {
         this.interpreterClass = this.pc.getServletContext().getInitParameter("weblogicx.jsp.tags.InterpreterClass");
      }

      return this.interpreterClass;
   }

   private String removeLeadingDot(String s) {
      return s.length() > 0 && s.charAt(0) == '.' ? s.substring(1) : s;
   }

   private String prependArrayIndexSyntax(String expr) {
      if (expr.equals("[]")) {
         return "[" + this.iter + "]";
      } else if (!expr.equals("-") && !expr.equals("[]-")) {
         if (expr.startsWith("[]")) {
            return expr.endsWith("-delete") ? "[" + this.iter + "]" + expr.substring(2) : "[" + this.iter + "]." + expr.substring(2);
         } else {
            return expr.endsWith("-delete") ? "[" + this.iter + "]" + expr : "[" + this.iter + "]." + expr;
         }
      } else {
         return "[" + this.iter + "]-";
      }
   }

   private void syncIndexVar(int x) {
      this.pc.setAttribute("index", new Integer(x));
      this.pc.setAttribute("arrayLength", new Integer(this.arrayLen));
   }

   void p(String s) {
   }

   public String getObject() {
      return !this.isArray ? this.objectID : this.objectID + "[" + this.iter + "]";
   }

   public void setObject(String id) throws Exception {
      this.p("setObject(\"" + id + "\")");
      if (id.endsWith(".")) {
         if (id.length() == 1) {
            throw new IllegalArgumentException("null object ID string \".\"");
         }

         id = id.substring(0, id.length() - 1);
      }

      if (id.startsWith(".")) {
         if (id.length() == 1) {
            throw new IllegalArgumentException("null object ID string \".\"");
         }

         id = id.substring(1);
      }

      id = id.trim();
      if (id.length() == 0) {
         throw new IllegalArgumentException("empty object ID string");
      } else {
         this.objectID = id;
      }
   }

   public int doStartTag() throws JspException {
      this.p("doStartTag()");

      try {
         String name = this.getInterpreterClass();
         if (name == null) {
            throw new JspException("InterpreterClass is not set in the servlet context init parameter\nweblogic.jsp.tags.InterpreterClass, nor as the interpreterClass attribute of the tag.");
         }

         this.bpi = (BeanParamInterpreter)Class.forName(name).newInstance();
      } catch (Exception var4) {
         throw new JspException("Could not instantiate your interpreterClass: " + this.interpreterClass + ": " + var4);
      }

      if (this.relative && this.parent != null && this.parent instanceof BeanParamTag) {
         BeanParamTag bpt = (BeanParamTag)this.parent;
         String parentObject = bpt.getObject();
         if (parentObject == null) {
            parentObject = "";
         }

         this.objectID = parentObject + "." + this.objectID;
      }

      HttpSession s = this.pc.getSession();
      if (s == null) {
         throw new IllegalArgumentException("no session defined for this page");
      } else {
         try {
            Object o = this.bpi.get(this.objectID, s);
            if (o != null && o.getClass().isArray()) {
               this.isArray = true;
               this.arrayLen = Array.getLength(o);
               this.p("array length is " + this.arrayLen);
            }
         } catch (Exception var3) {
            throw new JspException("Could not get the objectID: " + var3);
         }

         return this.arrayLen == 0 && this.isArray && this.iterate ? 0 : 2;
      }
   }

   public int doEndTag() throws JspException {
      this.p("doEndTag");
      return 6;
   }

   public Tag getParent() {
      return this.parent;
   }

   public void release() {
      this.p("release");
      if (this.parent != null && this.parent instanceof BeanParamTag) {
         BeanParamTag bpt = (BeanParamTag)this.parent;
         int x = bpt.getIndex();
         this.syncIndexVar(x);
      }

      this.parent = null;
      this.bc = null;
      this.pc = null;
      this.objectID = "";
      this.iter = 0;
      this.arrayLen = 1;
      this.isArray = false;
      this.iterate = true;
      this.relative = true;
      this.lastExpression = "";
   }

   public void setPageContext(PageContext pc) {
      this.p("setPageContext");
      this.pc = pc;
   }

   public void setParent(Tag p) {
      this.parent = p;
      if (p == null) {
         this.p("setParent(null)");
      } else {
         this.p("setParent(" + p.getClass().getName() + ")");
      }

   }

   private boolean isLegalIdChar(char c) {
      if (c >= 'A' && c <= 'Z') {
         return true;
      } else if (c >= 'a' && c <= 'z') {
         return true;
      } else if (c >= '0' && c <= '9') {
         return true;
      } else if (c == '.') {
         return true;
      } else if (c == '[') {
         return true;
      } else if (c == ']') {
         return true;
      } else {
         return c == '-';
      }
   }

   private String parseAndReplace(String body) throws Exception {
      UnsyncStringBuffer sb = new UnsyncStringBuffer();
      int len = body.length();

      for(int i = 0; i < len; ++i) {
         char c = body.charAt(i);
         if (c == '$' && i != len - 1) {
            c = body.charAt(i + 1);
            if (c == '$') {
               ++i;
               sb.append(c);
            } else {
               String exprString = null;
               boolean appendC = false;
               int elen;
               if (c == '{') {
                  elen = body.indexOf(125, i);
                  if (elen < 0) {
                     throw new IllegalArgumentException("unterminated brace expression in body: remaining=\"" + body.substring(i) + "\"");
                  }

                  exprString = body.substring(i + 2, elen);
                  exprString = exprString.trim();
                  int exprLen = elen - i;
                  i += exprLen;
               } else {
                  UnsyncStringBuffer expr = new UnsyncStringBuffer();

                  while(true) {
                     ++i;
                     if (i >= len || !this.isLegalIdChar(c = body.charAt(i))) {
                        if (c == '-') {
                           expr.append(c);
                        } else {
                           appendC = true;
                        }

                        exprString = expr.toString();
                        break;
                     }

                     expr.append(c);
                  }
               }

               exprString = this.removeLeadingDot(exprString.trim());
               if (exprString.equals("-last")) {
                  exprString = this.lastExpression;
               } else if (exprString.equals("-last-")) {
                  exprString = this.lastExpression + "-";
               } else {
                  if (this.isArray && this.iterate) {
                     exprString = this.prependArrayIndexSyntax(exprString);
                  }

                  this.p("starting with relative expression '" + exprString + "'");
                  if (exprString.startsWith("[")) {
                     exprString = this.objectID + exprString;
                  } else {
                     exprString = this.objectID + "." + exprString;
                  }
               }

               this.p("got full expression: '" + exprString + "'");
               if (exprString.indexOf(45) < 0) {
                  this.lastExpression = exprString;
                  Object o = this.bpi.get(exprString, this.pc.getSession());
                  this.p("for expression '" + exprString + "' got value '" + o + "'");
                  if (o != null) {
                     sb.append(o.toString());
                  }
               } else if (exprString.endsWith("-")) {
                  elen = exprString.length();
                  sb.append(this.lastExpression = exprString.substring(0, elen - 1));
               } else {
                  sb.append(this.lastExpression = exprString);
               }

               if (appendC) {
                  sb.append(c);
               }
            }
         } else {
            sb.append(c);
         }
      }

      return sb.toString();
   }

   public int doAfterBody() throws JspException {
      this.p("doAfterBody iter=" + this.iter);
      String body = this.bc.getString();

      try {
         body = this.parseAndReplace(body);
         this.bc.getEnclosingWriter().write(body);
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new JspException("BeanParamTag: cannot write body: " + var5.toString());
      }

      ++this.iter;
      if (this.iterate && this.iter >= this.arrayLen) {
         if (this.parent != null && this.parent instanceof BeanParamTag) {
            BeanParamTag bpt = (BeanParamTag)this.parent;
            int x = bpt.getIndex();
            this.syncIndexVar(x);
         }

         return 0;
      } else {
         this.bc.clearBody();
         this.syncIndexVar(this.iter);
         return !this.iterate ? 0 : 2;
      }
   }

   public void doInitBody() throws JspException {
      this.syncIndexVar(this.iter);
      this.p("doInitBody");
   }

   public void setBodyContent(BodyContent bc) {
      this.p("setBodyContent");
      this.bc = bc;
   }
}
