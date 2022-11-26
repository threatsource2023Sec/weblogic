package weblogic.xml.process;

import java.util.Locale;

public class XPathFunctionRef extends FunctionRef {
   private String expr;

   public XPathFunctionRef(String name, String exprVal) {
      super(name);
      this.expr = exprVal;
   }

   public String getExpr() {
      return this.expr;
   }

   public boolean equals(Object obj) {
      XPathFunctionRef other = null;

      try {
         other = (XPathFunctionRef)obj;
      } catch (ClassCastException var4) {
         return false;
      }

      return this.name.equalsIgnoreCase(other.name) && this.expr.equals(other.expr);
   }

   public int hashCode() {
      return this.name.toUpperCase(Locale.ENGLISH).hashCode() ^ this.expr.hashCode();
   }

   public String toString() {
      return "@" + this.name + "{" + this.expr + "}";
   }
}
