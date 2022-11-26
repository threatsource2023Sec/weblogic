package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.Description;
import org.opensaml.saml.ext.saml2mdui.DisplayName;
import org.opensaml.saml.ext.saml2mdui.InformationURL;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.opensaml.saml.ext.saml2mdui.Logo;
import org.opensaml.saml.ext.saml2mdui.PrivacyStatementURL;
import org.opensaml.saml.ext.saml2mdui.UIInfo;

public class UIInfoImpl extends AbstractSAMLObject implements UIInfo {
   private final IndexedXMLObjectChildrenList uiInfoChildren = new IndexedXMLObjectChildrenList(this);

   protected UIInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getXMLObjects() {
      return this.uiInfoChildren;
   }

   public List getXMLObjects(QName typeOrName) {
      return this.uiInfoChildren.subList(typeOrName);
   }

   public List getDescriptions() {
      return this.uiInfoChildren.subList(Description.DEFAULT_ELEMENT_NAME);
   }

   public List getDisplayNames() {
      return this.uiInfoChildren.subList(DisplayName.DEFAULT_ELEMENT_NAME);
   }

   public List getKeywords() {
      return this.uiInfoChildren.subList(Keywords.DEFAULT_ELEMENT_NAME);
   }

   public List getInformationURLs() {
      return this.uiInfoChildren.subList(InformationURL.DEFAULT_ELEMENT_NAME);
   }

   public List getLogos() {
      return this.uiInfoChildren.subList(Logo.DEFAULT_ELEMENT_NAME);
   }

   public List getPrivacyStatementURLs() {
      return this.uiInfoChildren.subList(PrivacyStatementURL.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.uiInfoChildren);
      return children;
   }
}
