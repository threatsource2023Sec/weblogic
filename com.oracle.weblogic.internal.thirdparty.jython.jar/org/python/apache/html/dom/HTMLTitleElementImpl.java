package org.python.apache.html.dom;

import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.html.HTMLTitleElement;

public class HTMLTitleElementImpl extends HTMLElementImpl implements HTMLTitleElement {
   private static final long serialVersionUID = 879646303512367875L;

   public String getText() {
      StringBuffer var1 = new StringBuffer();

      for(Node var2 = this.getFirstChild(); var2 != null; var2 = var2.getNextSibling()) {
         if (var2 instanceof Text) {
            var1.append(((Text)var2).getData());
         }
      }

      return var1.toString();
   }

   public void setText(String var1) {
      Node var3;
      for(Node var2 = this.getFirstChild(); var2 != null; var2 = var3) {
         var3 = var2.getNextSibling();
         this.removeChild(var2);
      }

      this.insertBefore(this.getOwnerDocument().createTextNode(var1), this.getFirstChild());
   }

   public HTMLTitleElementImpl(HTMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }
}
