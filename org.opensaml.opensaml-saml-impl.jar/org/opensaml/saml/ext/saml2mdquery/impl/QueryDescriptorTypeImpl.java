package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.ext.saml2mdquery.QueryDescriptorType;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorImpl;

public abstract class QueryDescriptorTypeImpl extends RoleDescriptorImpl implements QueryDescriptorType {
   private XSBooleanValue wantAssertionsSigned;
   private XMLObjectChildrenList nameIDFormats = new XMLObjectChildrenList(this);

   protected QueryDescriptorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Boolean getWantAssertionsSigned() {
      return this.wantAssertionsSigned != null ? this.wantAssertionsSigned.getValue() : Boolean.FALSE;
   }

   public void setWantAssertionsSigned(Boolean newWantAssertionsSigned) {
      if (newWantAssertionsSigned != null) {
         this.wantAssertionsSigned = (XSBooleanValue)this.prepareForAssignment(this.wantAssertionsSigned, new XSBooleanValue(newWantAssertionsSigned, false));
      } else {
         this.wantAssertionsSigned = (XSBooleanValue)this.prepareForAssignment(this.wantAssertionsSigned, (Object)null);
      }

   }

   public XSBooleanValue getWantAssertionsSignedXSBoolean() {
      return this.wantAssertionsSigned;
   }

   public void setWantAssertionsSigned(XSBooleanValue wantAssertionSigned) {
      this.wantAssertionsSigned = (XSBooleanValue)this.prepareForAssignment(this.wantAssertionsSigned, wantAssertionSigned);
   }

   public List getNameIDFormat() {
      return this.nameIDFormats;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.nameIDFormats);
      return Collections.unmodifiableList(children);
   }
}
