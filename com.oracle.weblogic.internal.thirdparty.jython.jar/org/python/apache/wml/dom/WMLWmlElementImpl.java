package org.python.apache.wml.dom;

import org.python.apache.wml.WMLWmlElement;

public class WMLWmlElementImpl extends WMLElementImpl implements WMLWmlElement {
   private static final long serialVersionUID = -7008023851895920651L;

   public WMLWmlElementImpl(WMLDocumentImpl var1, String var2) {
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
