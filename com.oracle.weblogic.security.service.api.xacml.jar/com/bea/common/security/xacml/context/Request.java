package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Request extends ContextSchemaObject {
   private static final long serialVersionUID = 8637822746503172604L;
   private List subjects;
   private List resources;
   private Action action;
   private Environment environment;

   public Request(List subjects, List resources, Action action, Environment environment) {
      this.subjects = subjects != null ? Collections.unmodifiableList(subjects) : null;
      this.resources = resources != null ? Collections.unmodifiableList(resources) : null;
      this.action = action;
      this.environment = environment;
   }

   public Request(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      this.subjects = new ArrayList();
      this.resources = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         String cname = this.getLocalName(node);
         if (cname.equals("Subject")) {
            this.subjects.add(new Subject(registry, node));
         } else if (cname.equals("Resource")) {
            this.resources.add(new Resource(registry, node));
         } else if (cname.equals("Action")) {
            this.action = new Action(registry, node);
         } else if (cname.equals("Environment")) {
            this.environment = new Environment(registry, node);
         }
      }

      this.subjects = this.subjects.isEmpty() ? null : Collections.unmodifiableList(this.subjects);
      this.resources = this.resources.isEmpty() ? null : Collections.unmodifiableList(this.resources);
   }

   public String getElementName() {
      return "Request";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      Iterator it;
      if (this.subjects != null) {
         it = this.subjects.iterator();

         while(it.hasNext()) {
            Subject s = (Subject)it.next();
            s.encode(nsMap, ps);
         }
      }

      if (this.resources != null) {
         it = this.resources.iterator();

         while(it.hasNext()) {
            Resource r = (Resource)it.next();
            r.encode(nsMap, ps);
         }
      }

      if (this.action != null) {
         this.action.encode(nsMap, ps);
      }

      if (this.environment != null) {
         this.environment.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Request)) {
         return false;
      } else {
         Request o = (Request)other;
         return CollectionUtil.equals(this.subjects, o.subjects) && CollectionUtil.equals(this.resources, o.resources) && (this.action == o.action || this.action != null && this.action.equals(o.action)) && (this.environment == o.environment || this.environment != null && this.environment.equals(o.environment));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.subjects);
      result = HashCodeUtil.hash(result, this.resources);
      result = HashCodeUtil.hash(result, this.action);
      result = HashCodeUtil.hash(result, this.environment);
      return result;
   }

   public List getSubjects() {
      return this.subjects;
   }

   public List getResources() {
      return this.resources;
   }

   public Action getAction() {
      return this.action;
   }

   public Environment getEnvironment() {
      return this.environment;
   }
}
