package org.python.apache.wml.dom;

import org.python.apache.wml.WMLAnchorElement;

public class WMLAnchorElementImpl extends WMLElementImpl implements WMLAnchorElement {
   private static final long serialVersionUID = 5720492496046133176L;

   public WMLAnchorElementImpl(WMLDocumentImpl var1, String var2) {
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

   public void setTitle(String var1) {
      this.setAttribute("title", var1);
   }

   public String getTitle() {
      return this.getAttribute("title");
   }

   public void setId(String var1) {
      this.setAttribute("id", var1);
   }

   public String getId() {
      return this.getAttribute("id");
   }
}
