package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.DefaultsType;

public class DefaultsTypeImpl extends AbstractXACMLObject implements DefaultsType {
   private XSString xPathVersion;

   protected DefaultsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public XSString getXPathVersion() {
      return this.xPathVersion;
   }

   public void setXPathVersion(XSString version) {
      this.xPathVersion = (XSString)this.prepareForAssignment(this.xPathVersion, version);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.xPathVersion != null) {
         children.add(this.xPathVersion);
      }

      return Collections.unmodifiableList(children);
   }
}
