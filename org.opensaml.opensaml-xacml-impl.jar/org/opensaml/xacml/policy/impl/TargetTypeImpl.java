package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ActionsType;
import org.opensaml.xacml.policy.EnvironmentsType;
import org.opensaml.xacml.policy.ResourcesType;
import org.opensaml.xacml.policy.SubjectsType;
import org.opensaml.xacml.policy.TargetType;

public class TargetTypeImpl extends AbstractXACMLObject implements TargetType {
   private ActionsType actions;
   private EnvironmentsType environments;
   private SubjectsType subjects;
   private ResourcesType resources;

   protected TargetTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.subjects != null) {
         children.add(this.subjects);
      }

      if (this.resources != null) {
         children.add(this.resources);
      }

      if (this.actions != null) {
         children.add(this.actions);
      }

      if (this.environments != null) {
         children.add(this.environments);
      }

      return Collections.unmodifiableList(children);
   }

   public SubjectsType getSubjects() {
      return this.subjects;
   }

   public ResourcesType getResources() {
      return this.resources;
   }

   public ActionsType getActions() {
      return this.actions;
   }

   public EnvironmentsType getEnvironments() {
      return this.environments;
   }

   public void setActions(ActionsType newActions) {
      this.actions = (ActionsType)this.prepareForAssignment(this.actions, newActions);
   }

   public void setEnvironments(EnvironmentsType newEnvironments) {
      this.environments = (EnvironmentsType)this.prepareForAssignment(this.environments, newEnvironments);
   }

   public void setResources(ResourcesType newResources) {
      this.resources = (ResourcesType)this.prepareForAssignment(this.resources, newResources);
   }

   public void setSubjects(SubjectsType newSubjects) {
      this.subjects = (SubjectsType)this.prepareForAssignment(this.subjects, newSubjects);
   }
}
