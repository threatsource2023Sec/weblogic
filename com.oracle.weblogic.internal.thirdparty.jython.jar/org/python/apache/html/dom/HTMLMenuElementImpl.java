package org.python.apache.html.dom;

import org.w3c.dom.html.HTMLMenuElement;

public class HTMLMenuElementImpl extends HTMLElementImpl implements HTMLMenuElement {
   private static final long serialVersionUID = -1489696654903916901L;

   public boolean getCompact() {
      return this.getBinary("compact");
   }

   public void setCompact(boolean var1) {
      this.setAttribute("compact", var1);
   }

   public HTMLMenuElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
