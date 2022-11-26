package org.opensaml.saml.ext.saml2alg.impl;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2alg.SigningMethod;

public class SigningMethodImpl extends AbstractSAMLObject implements SigningMethod {
   private final IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);
   private String algorithm;
   private Integer minKeySize;
   private Integer maxKeySize;

   public SigningMethodImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   @Nullable
   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(@Nullable String newValue) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newValue);
   }

   @Nullable
   public Integer getMinKeySize() {
      return this.minKeySize;
   }

   public void setMinKeySize(@Nullable Integer newValue) {
      this.minKeySize = (Integer)this.prepareForAssignment(this.minKeySize, newValue);
   }

   @Nullable
   public Integer getMaxKeySize() {
      return this.maxKeySize;
   }

   public void setMaxKeySize(@Nullable Integer newValue) {
      this.maxKeySize = (Integer)this.prepareForAssignment(this.maxKeySize, newValue);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.unknownChildren);
   }
}
