package weblogic.apache.wml.dom;

import weblogic.apache.wml.WMLRefreshElement;

public class WMLRefreshElementImpl extends WMLElementImpl implements WMLRefreshElement {
   private static final long serialVersionUID = 8781837880806459398L;

   public WMLRefreshElementImpl(WMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }

   public void setClassName(String var1) {
      this.setAttribute("class", var1);
   }

   public String getClassName() {
      return this.getAttribute("class");
   }

   public void setId(String var1) {
      this.setAttribute("id", var1);
   }

   public String getId() {
      return this.getAttribute("id");
   }
}
