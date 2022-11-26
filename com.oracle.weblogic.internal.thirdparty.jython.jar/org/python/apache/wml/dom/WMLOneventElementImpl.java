package org.python.apache.wml.dom;

import org.python.apache.wml.WMLOneventElement;

public class WMLOneventElementImpl extends WMLElementImpl implements WMLOneventElement {
   private static final long serialVersionUID = -4279215241146570871L;

   public WMLOneventElementImpl(WMLDocumentImpl var1, String var2) {
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

   public void setType(String var1) {
      this.setAttribute("type", var1);
   }

   public String getType() {
      return this.getAttribute("type");
   }
}
