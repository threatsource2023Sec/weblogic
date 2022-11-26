package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class RequestTypeImpl extends AbstractXACMLObject implements RequestType {
   private XMLObjectChildrenList subjects = new XMLObjectChildrenList(this);
   private XMLObjectChildrenList resources = new XMLObjectChildrenList(this);
   private EnvironmentType environment;
   private ActionType action;

   protected RequestTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getSubjects() {
      return this.subjects;
   }

   public List getResources() {
      return this.resources;
   }

   public EnvironmentType getEnvironment() {
      return this.environment;
   }

   public void setEnvironment(EnvironmentType env) {
      this.environment = (EnvironmentType)this.prepareForAssignment(this.environment, env);
   }

   public ActionType getAction() {
      return this.action;
   }

   public void setAction(ActionType act) {
      this.action = (ActionType)this.prepareForAssignment(this.action, act);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.subjects);
      children.addAll(this.resources);
      if (this.action != null) {
         children.add(this.action);
      }

      if (this.environment != null) {
         children.add(this.environment);
      }

      return Collections.unmodifiableList(children);
   }
}
