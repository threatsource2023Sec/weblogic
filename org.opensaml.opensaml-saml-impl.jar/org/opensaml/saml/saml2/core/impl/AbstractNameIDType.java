package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.NameIDType;

public class AbstractNameIDType extends AbstractSAMLObject implements NameIDType {
   private String name;
   private String nameQualifier;
   private String spNameQualifier;
   private String format;
   private String spProvidedID;

   protected AbstractNameIDType(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return this.name;
   }

   public void setValue(String newName) {
      this.name = this.prepareForAssignment(this.name, newName);
   }

   public String getNameQualifier() {
      return this.nameQualifier;
   }

   public void setNameQualifier(String newNameQualifier) {
      this.nameQualifier = this.prepareForAssignment(this.nameQualifier, newNameQualifier);
   }

   public String getSPNameQualifier() {
      return this.spNameQualifier;
   }

   public void setSPNameQualifier(String newSPNameQualifier) {
      this.spNameQualifier = this.prepareForAssignment(this.spNameQualifier, newSPNameQualifier);
   }

   public String getFormat() {
      return this.format;
   }

   public void setFormat(String newFormat) {
      this.format = this.prepareForAssignment(this.format, newFormat);
   }

   public String getSPProvidedID() {
      return this.spProvidedID;
   }

   public void setSPProvidedID(String newSPProvidedID) {
      this.spProvidedID = this.prepareForAssignment(this.spProvidedID, newSPProvidedID);
   }

   public List getOrderedChildren() {
      return null;
   }
}
