package weblogic.apache.wml.dom;

import weblogic.apache.wml.WMLNoopElement;

public class WMLNoopElementImpl extends WMLElementImpl implements WMLNoopElement {
   private static final long serialVersionUID = -1581314434256075931L;

   public WMLNoopElementImpl(WMLDocumentImpl var1, String var2) {
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
