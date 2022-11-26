package org.opensaml.saml.ext.saml2mdui.impl;

import com.google.common.base.Strings;
import java.util.List;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.Logo;

public class LogoImpl extends AbstractSAMLObject implements Logo {
   private String url;
   private String lang;
   private Integer width;
   private Integer height;

   protected LogoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Integer getHeight() {
      return this.height;
   }

   public void setHeight(Integer newHeight) {
      this.height = (Integer)this.prepareForAssignment(this.height, newHeight);
   }

   public Integer getWidth() {
      return this.width;
   }

   public void setWidth(Integer newWidth) {
      this.width = (Integer)this.prepareForAssignment(this.width, newWidth);
   }

   public String getURL() {
      return this.url;
   }

   public void setURL(String newURL) {
      this.url = this.prepareForAssignment(this.url, newURL);
   }

   public String getXMLLang() {
      return this.lang;
   }

   public void setXMLLang(String newLang) {
      boolean hasValue = newLang != null && !Strings.isNullOrEmpty(newLang);
      this.lang = this.prepareForAssignment(this.lang, newLang);
      this.manageQualifiedAttributeNamespace(LangBearing.XML_LANG_ATTR_NAME, hasValue);
   }

   public List getOrderedChildren() {
      return null;
   }

   public int hashCode() {
      int hash = this.url.hashCode();
      hash = hash * 31 + this.lang.hashCode();
      hash = hash * 31 + this.height;
      hash = hash * 31 + this.width;
      return hash;
   }
}
