package org.opensaml.saml.ext.saml2mdui.impl;

import com.google.common.base.Strings;
import java.util.Iterator;
import java.util.List;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.Keywords;

public class KeywordsImpl extends AbstractSAMLObject implements Keywords {
   private String lang;
   private List data;

   protected KeywordsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return null;
   }

   public String getXMLLang() {
      return this.lang;
   }

   public void setXMLLang(String newLang) {
      boolean hasValue = newLang != null && !Strings.isNullOrEmpty(newLang);
      this.lang = this.prepareForAssignment(this.lang, newLang);
      this.manageQualifiedAttributeNamespace(LangBearing.XML_LANG_ATTR_NAME, hasValue);
   }

   public List getKeywords() {
      return this.data;
   }

   public void setKeywords(List val) {
      this.data = (List)this.prepareForAssignment(this.data, val);
   }

   public int hashCode() {
      int hash = this.lang.hashCode();

      String s;
      for(Iterator var2 = this.data.iterator(); var2.hasNext(); hash = hash * 31 + s.hashCode()) {
         s = (String)var2.next();
      }

      return hash;
   }
}
