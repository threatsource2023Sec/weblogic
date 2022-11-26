package weblogic.apache.html.dom;

import org.w3c.dom.html.HTMLBRElement;

public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
   private static final long serialVersionUID = 311960206282154750L;

   public String getClear() {
      return this.capitalize(this.getAttribute("clear"));
   }

   public void setClear(String var1) {
      this.setAttribute("clear", var1);
   }

   public HTMLBRElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
