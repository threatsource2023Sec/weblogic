package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.AlgorithmIdentifierType;

public abstract class AlgorithmIdentifierTypeImpl extends AbstractXMLObject implements AlgorithmIdentifierType {
   private String algorithm;
   private XMLObject parameters;

   protected AlgorithmIdentifierTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   @Nullable
   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(@Nullable String newAlgorithm) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
   }

   @Nullable
   public XMLObject getParameters() {
      return this.parameters;
   }

   public void setParameters(@Nullable XMLObject newParameters) {
      this.parameters = this.prepareForAssignment(this.parameters, newParameters);
   }

   @Nullable
   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.parameters != null) {
         children.add(this.parameters);
      }

      return Collections.unmodifiableList(children);
   }
}
