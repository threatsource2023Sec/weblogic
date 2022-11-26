package weblogic.apache.html.dom;

import org.w3c.dom.html.HTMLModElement;

public class HTMLModElementImpl extends HTMLElementImpl implements HTMLModElement {
   private static final long serialVersionUID = 6424581972706750120L;

   public String getCite() {
      return this.getAttribute("cite");
   }

   public void setCite(String var1) {
      this.setAttribute("cite", var1);
   }

   public String getDateTime() {
      return this.getAttribute("datetime");
   }

   public void setDateTime(String var1) {
      this.setAttribute("datetime", var1);
   }

   public HTMLModElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
