package org.python.apache.wml.dom;

import org.python.apache.wml.WMLPrevElement;

public class WMLPrevElementImpl extends WMLElementImpl implements WMLPrevElement {
   private static final long serialVersionUID = -1545713716925433554L;

   public WMLPrevElementImpl(WMLDocumentImpl var1, String var2) {
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
