package org.opensaml.saml.saml2.metadata.impl;

import com.google.common.base.Strings;
import java.util.List;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.saml.saml2.metadata.LocalizedName;

public class LocalizedNameImpl extends XSStringImpl implements LocalizedName {
   private String language;

   protected LocalizedNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getXMLLang() {
      return this.language;
   }

   public void setXMLLang(String newLang) {
      boolean hasValue = newLang != null && !Strings.isNullOrEmpty(newLang);
      this.language = this.prepareForAssignment(this.language, newLang);
      this.manageQualifiedAttributeNamespace(LangBearing.XML_LANG_ATTR_NAME, hasValue);
   }

   public List getOrderedChildren() {
      return null;
   }

   public int hashCode() {
      int hash = super.hashCode();
      hash = hash * 31 + this.language.hashCode();
      return hash;
   }
}
