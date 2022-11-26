package org.python.apache.html.dom;

import org.w3c.dom.html.HTMLPreElement;

public class HTMLPreElementImpl extends HTMLElementImpl implements HTMLPreElement {
   private static final long serialVersionUID = -4195360849946217644L;

   public int getWidth() {
      return this.getInteger(this.getAttribute("width"));
   }

   public void setWidth(int var1) {
      this.setAttribute("width", String.valueOf(var1));
   }

   public HTMLPreElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
