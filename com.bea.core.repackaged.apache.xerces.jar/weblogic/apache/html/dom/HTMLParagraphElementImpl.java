package weblogic.apache.html.dom;

import org.w3c.dom.html.HTMLParagraphElement;

public class HTMLParagraphElementImpl extends HTMLElementImpl implements HTMLParagraphElement {
   private static final long serialVersionUID = 8075287150683866287L;

   public String getAlign() {
      return this.getAttribute("align");
   }

   public void setAlign(String var1) {
      this.setAttribute("align", var1);
   }

   public HTMLParagraphElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
