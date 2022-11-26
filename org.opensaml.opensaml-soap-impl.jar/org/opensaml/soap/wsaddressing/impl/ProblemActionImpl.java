package org.opensaml.soap.wsaddressing.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wsaddressing.Action;
import org.opensaml.soap.wsaddressing.ProblemAction;
import org.opensaml.soap.wsaddressing.SoapAction;

public class ProblemActionImpl extends AbstractWSAddressingObject implements ProblemAction {
   private Action action;
   private SoapAction soapAction;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public ProblemActionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Action getAction() {
      return this.action;
   }

   public SoapAction getSoapAction() {
      return this.soapAction;
   }

   public void setAction(Action newAction) {
      this.action = (Action)this.prepareForAssignment(this.action, newAction);
   }

   public void setSoapAction(SoapAction newSoapAction) {
      this.soapAction = (SoapAction)this.prepareForAssignment(this.soapAction, newSoapAction);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.action != null) {
         children.add(this.action);
      }

      if (this.soapAction != null) {
         children.add(this.soapAction);
      }

      return Collections.unmodifiableList(children);
   }
}
