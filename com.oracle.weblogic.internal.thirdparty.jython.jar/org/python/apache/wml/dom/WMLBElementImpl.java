package org.python.apache.wml.dom;

import org.python.apache.wml.WMLBElement;

public class WMLBElementImpl extends WMLElementImpl implements WMLBElement {
   private static final long serialVersionUID = -758504371498228671L;

   public WMLBElementImpl(WMLDocumentImpl var1, String var2) {
      super(var1, var2);
   }

   public void setClassName(String var1) {
      this.setAttribute("class", var1);
   }

   public String getClassName() {
      return this.getAttribute("class");
   }

   public void setXmlLang(String var1) {
      this.setAttribute("xml:lang", var1);
   }

   public String getXmlLang() {
      return this.getAttribute("xml:lang");
   }

   public void setId(String var1) {
      this.setAttribute("id", var1);
   }

   public String getId() {
      return this.getAttribute("id");
   }
}
