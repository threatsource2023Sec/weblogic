package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Target extends PolicySchemaObject {
   private static final long serialVersionUID = -4513336699249999858L;
   private Subjects subjects;
   private Resources resources;
   private Actions actions;
   private Environments environments;

   public Target(Subjects subjects) {
      this(subjects, (Resources)null, (Actions)null, (Environments)null);
   }

   public Target(Resources resources) {
      this((Subjects)null, resources, (Actions)null, (Environments)null);
   }

   public Target(Actions actions) {
      this((Subjects)null, (Resources)null, actions, (Environments)null);
   }

   public Target(Environments environments) {
      this((Subjects)null, (Resources)null, (Actions)null, environments);
   }

   public Target(Subjects subjects, Resources resources) {
      this(subjects, resources, (Actions)null, (Environments)null);
   }

   public Target(Resources resources, Actions actions) {
      this((Subjects)null, resources, actions, (Environments)null);
   }

   public Target(Subjects subjects, Resources resources, Actions actions) {
      this(subjects, resources, actions, (Environments)null);
   }

   public Target(Subjects subjects, Resources resources, Actions actions, Environments environments) {
      this.subjects = subjects;
      this.resources = resources;
      this.actions = actions;
      this.environments = environments;
   }

   public Target(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         String cname = this.getLocalName(node);
         if (cname.equals("Subjects")) {
            this.subjects = new Subjects(registry, node);
         } else if (cname.equals("Resources")) {
            this.resources = new Resources(registry, node);
         } else if (cname.equals("Actions")) {
            this.actions = new Actions(registry, node);
         } else if (cname.equals("Environments")) {
            this.environments = new Environments(registry, node);
         }
      }

   }

   public String getElementName() {
      return "Target";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.subjects != null) {
         this.subjects.encode(nsMap, ps);
      }

      if (this.resources != null) {
         this.resources.encode(nsMap, ps);
      }

      if (this.actions != null) {
         this.actions.encode(nsMap, ps);
      }

      if (this.environments != null) {
         this.environments.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Target)) {
         return false;
      } else {
         Target o = (Target)other;
         return (this.subjects == o.subjects || this.subjects != null && this.subjects.equals(o.subjects)) && (this.resources == o.resources || this.resources != null && this.resources.equals(o.resources)) && (this.actions == o.actions || this.actions != null && this.actions.equals(o.actions)) && (this.environments == o.environments || this.environments != null && this.environments.equals(o.environments));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.subjects);
      result = HashCodeUtil.hash(result, this.resources);
      result = HashCodeUtil.hash(result, this.actions);
      result = HashCodeUtil.hash(result, this.environments);
      return result;
   }

   public Subjects getSubjects() {
      return this.subjects;
   }

   public Resources getResources() {
      return this.resources;
   }

   public Actions getActions() {
      return this.actions;
   }

   public Environments getEnvironments() {
      return this.environments;
   }

   public boolean isEmpty() {
      return (this.actions == null || this.isEmptyList(this.actions.getActions())) && (this.environments == null || this.isEmptyList(this.environments.getEnvironments())) && (this.resources == null || this.isEmptyList(this.resources.getResources())) && (this.subjects == null || this.isEmptyList(this.subjects.getSubjects()));
   }

   private boolean isEmptyList(List list) {
      return list == null || list.size() == 0;
   }
}
