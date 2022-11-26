package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.CombinerParametersType;

public class CombinerParametersTypeImpl extends AbstractXACMLObject implements CombinerParametersType {
   private XMLObjectChildrenList combinerParameters = new XMLObjectChildrenList(this);

   protected CombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getCombinerParameters() {
      return this.combinerParameters;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.combinerParameters);
      return Collections.unmodifiableList(children);
   }
}
