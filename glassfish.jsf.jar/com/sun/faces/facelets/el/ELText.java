package com.sun.faces.facelets.el;

import com.sun.faces.el.ELUtils;
import com.sun.faces.util.HtmlUtils;
import com.sun.faces.util.MessageUtils;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.view.Location;

public class ELText {
   protected final String literal;

   public ELText(String literal) {
      this.literal = literal;
   }

   public boolean isLiteral() {
      return true;
   }

   public ELText apply(ExpressionFactory factory, ELContext ctx) {
      return this;
   }

   public void write(Writer out, ELContext ctx) throws ELException, IOException {
      out.write(this.literal);
   }

   public void writeText(ResponseWriter out, ELContext ctx) throws ELException, IOException {
      out.writeText(this.literal, (String)null);
   }

   public String toString(ELContext ctx) throws ELException {
      return this.literal;
   }

   public String toString() {
      return this.literal;
   }

   public static boolean isLiteral(String in) {
      ELText txt = parse(in);
      return txt == null || txt.isLiteral();
   }

   public static ELText parse(String in) throws ELException {
      return parse((ExpressionFactory)null, (ELContext)null, in);
   }

   public static ELText parse(String in, String alias) throws ELException {
      return parse((ExpressionFactory)null, (ELContext)null, in, alias);
   }

   public static ELText parse(ExpressionFactory fact, ELContext ctx, String in) throws ELException {
      return parse((ExpressionFactory)null, (ELContext)null, in, (String)null);
   }

   public static ELText parse(ExpressionFactory fact, ELContext ctx, String in, String alias) throws ELException {
      char[] ca = in.toCharArray();
      int i = 0;
      char c = false;
      int len = ca.length;
      int end = len - 1;
      boolean esc = false;
      int vlen = false;
      StringBuffer buff = new StringBuffer(128);
      List text = new ArrayList();
      ELText t = null;
      ValueExpression ve = null;

      while(true) {
         while(i < len) {
            char c = ca[i];
            if ('\\' == c) {
               esc = !esc;
               if (esc && i < end && (ca[i + 1] == '$' || ca[i + 1] == '#')) {
                  ++i;
                  continue;
               }
            } else if (!esc && ('$' == c || '#' == c) && i < end && '{' == ca[i + 1]) {
               if (buff.length() > 0) {
                  text.add(new ELText(buff.toString()));
                  buff.setLength(0);
               }

               int vlen = findVarLength(ca, i);
               if (ctx != null && fact != null) {
                  ValueExpression ve = fact.createValueExpression(ctx, new String(ca, i, vlen), String.class);
                  t = new ELTextVariable(ve);
               } else {
                  String expr = new String(ca, i, vlen);
                  if (null != alias && ELUtils.isCompositeComponentExpr(expr)) {
                     if (ELUtils.isCompositeComponentLookupWithArgs(expr)) {
                        String message = MessageUtils.getExceptionMessageString("com.sun.faces.ARGUMENTS_NOT_LEGAL_WITH_CC_ATTRS_EXPR");
                        throw new ELException(message);
                     }

                     FacesContext context = FacesContext.getCurrentInstance();
                     ELContext elContext = context.getELContext();
                     ValueExpression delegate = context.getApplication().getExpressionFactory().createValueExpression(elContext, expr, Object.class);
                     Location location = new Location(alias, -1, -1);
                     ve = new ContextualCompositeValueExpression(location, delegate);
                  } else {
                     ve = new LiteralValueExpression(expr);
                  }

                  t = new ELTextVariable((ValueExpression)ve);
               }

               text.add(t);
               i += vlen;
               continue;
            }

            esc = false;
            buff.append(c);
            ++i;
         }

         if (buff.length() > 0) {
            text.add(new ELText(buff.toString()));
            buff.setLength(0);
         }

         if (text.isEmpty()) {
            return new ELText("");
         }

         if (text.size() == 1) {
            return (ELText)text.get(0);
         }

         ELText[] ta = (ELText[])((ELText[])text.toArray(new ELText[text.size()]));
         return new ELTextComposite(ta);
      }
   }

   private static int findVarLength(char[] ca, int s) throws ELException {
      int i = s;
      int len = ca.length;
      char c = false;
      int str = 0;
      int nested = 0;

      for(boolean insideString = false; i < len; ++i) {
         char c = ca[i];
         if ('\\' == c && i < len - 1) {
            ++i;
         } else if ('\'' != c && '"' != c) {
            if ('{' == c && !insideString) {
               ++nested;
            } else if (str == 0 && '}' == c) {
               if (nested <= 1) {
                  return i - s + 1;
               }

               --nested;
            } else if ('}' == c && !insideString) {
               --nested;
            }
         } else if (str == c) {
            insideString = false;
            str = 0;
         } else {
            insideString = true;
            str = c;
         }
      }

      throw new ELException("EL Expression Unbalanced: ... " + new String(ca, s, i - s));
   }

   private static final class ELTextVariable extends ELText {
      private final ValueExpression ve;

      public ELTextVariable(ValueExpression ve) {
         super(ve.getExpressionString());
         this.ve = ve;
      }

      public boolean isLiteral() {
         return false;
      }

      public ELText apply(ExpressionFactory factory, ELContext ctx) {
         ELText result = null;
         if (this.ve instanceof ContextualCompositeValueExpression) {
            result = new ELTextVariable(this.ve);
         } else {
            result = new ELTextVariable(factory.createValueExpression(ctx, this.ve.getExpressionString(), String.class));
         }

         return result;
      }

      public void write(Writer out, ELContext ctx) throws ELException, IOException {
         Object v = this.ve.getValue(ctx);
         if (v != null) {
            char[] buffer = new char[1028];
            HtmlUtils.writeTextForXML(out, v.toString(), buffer);
         }

      }

      public String toString(ELContext ctx) throws ELException {
         Object v = this.ve.getValue(ctx);
         return v != null ? v.toString() : null;
      }

      public void writeText(ResponseWriter out, ELContext ctx) throws ELException, IOException {
         Object v = this.ve.getValue(ctx);
         if (v != null) {
            out.writeText(v.toString(), (String)null);
         }

      }
   }

   private static final class ELTextComposite extends ELText {
      private final ELText[] txt;

      public ELTextComposite(ELText[] txt) {
         super((String)null);
         this.txt = txt;
      }

      public void write(Writer out, ELContext ctx) throws ELException, IOException {
         for(int i = 0; i < this.txt.length; ++i) {
            this.txt[i].write(out, ctx);
         }

      }

      public void writeText(ResponseWriter out, ELContext ctx) throws ELException, IOException {
         for(int i = 0; i < this.txt.length; ++i) {
            this.txt[i].writeText(out, ctx);
         }

      }

      public String toString(ELContext ctx) {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < this.txt.length; ++i) {
            sb.append(this.txt[i].toString(ctx));
         }

         return sb.toString();
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < this.txt.length; ++i) {
            sb.append(this.txt[i].toString());
         }

         return sb.toString();
      }

      public boolean isLiteral() {
         return false;
      }

      public ELText apply(ExpressionFactory factory, ELContext ctx) {
         int len = this.txt.length;
         ELText[] nt = new ELText[len];

         for(int i = 0; i < len; ++i) {
            nt[i] = this.txt[i].apply(factory, ctx);
         }

         return new ELTextComposite(nt);
      }
   }

   private static final class LiteralValueExpression extends ValueExpression {
      private static final long serialVersionUID = 1L;
      private final String text;

      public LiteralValueExpression(String text) {
         this.text = text;
      }

      public boolean isLiteralText() {
         return false;
      }

      public int hashCode() {
         return 0;
      }

      public String getExpressionString() {
         return this.text;
      }

      public boolean equals(Object obj) {
         return false;
      }

      public void setValue(ELContext context, Object value) {
      }

      public boolean isReadOnly(ELContext context) {
         return false;
      }

      public Object getValue(ELContext context) {
         return null;
      }

      public Class getType(ELContext context) {
         return null;
      }

      public Class getExpectedType() {
         return null;
      }
   }
}
