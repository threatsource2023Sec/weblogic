package weblogic.apache.html.dom;

import org.w3c.dom.html.HTMLDirectoryElement;

public class HTMLDirectoryElementImpl extends HTMLElementImpl implements HTMLDirectoryElement {
   private static final long serialVersionUID = -1010376135190194454L;

   public boolean getCompact() {
      return this.getBinary("compact");
   }

   public void setCompact(boolean var1) {
      this.setAttribute("compact", var1);
   }

   public HTMLDirectoryElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
