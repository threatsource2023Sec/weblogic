package weblogic.apache.html.dom;

import org.w3c.dom.html.HTMLDivElement;

public class HTMLDivElementImpl extends HTMLElementImpl implements HTMLDivElement {
   private static final long serialVersionUID = 2327098984177358833L;

   public String getAlign() {
      return this.capitalize(this.getAttribute("align"));
   }

   public void setAlign(String var1) {
      this.setAttribute("align", var1);
   }

   public HTMLDivElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
