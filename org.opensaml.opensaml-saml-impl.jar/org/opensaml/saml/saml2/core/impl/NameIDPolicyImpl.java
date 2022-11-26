package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.NameIDPolicy;

public class NameIDPolicyImpl extends AbstractSAMLObject implements NameIDPolicy {
   private String format;
   private String spNameQualifier;
   private XSBooleanValue allowCreate;

   protected NameIDPolicyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getFormat() {
      return this.format;
   }

   public void setFormat(String newFormat) {
      this.format = this.prepareForAssignment(this.format, newFormat);
   }

   public String getSPNameQualifier() {
      return this.spNameQualifier;
   }

   public void setSPNameQualifier(String newSPNameQualifier) {
      this.spNameQualifier = this.prepareForAssignment(this.spNameQualifier, newSPNameQualifier);
   }

   public Boolean getAllowCreate() {
      return this.allowCreate != null ? this.allowCreate.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue getAllowCreateXSBoolean() {
      return this.allowCreate;
   }

   public void setAllowCreate(Boolean newAllowCreate) {
      if (newAllowCreate != null) {
         this.allowCreate = (XSBooleanValue)this.prepareForAssignment(this.allowCreate, new XSBooleanValue(newAllowCreate, false));
      } else {
         this.allowCreate = (XSBooleanValue)this.prepareForAssignment(this.allowCreate, (Object)null);
      }

   }

   public void setAllowCreate(XSBooleanValue newAllowCreate) {
      this.allowCreate = (XSBooleanValue)this.prepareForAssignment(this.allowCreate, newAllowCreate);
   }

   public List getOrderedChildren() {
      return null;
   }
}
