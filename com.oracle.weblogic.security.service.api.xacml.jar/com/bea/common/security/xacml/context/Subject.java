package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Subject extends ContextSchemaObject {
   private static final long serialVersionUID = -3272502530852421544L;
   private static final String ACCESS_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
   private URI subjectCategory;
   private List attributes;

   public Subject() {
      this((List)((List)null), (URI)null);
   }

   public Subject(URI subjectCategory) {
      this((List)null, (URI)subjectCategory);
   }

   public Subject(List attributes) {
      this((List)attributes, (URI)null);
   }

   public Subject(List attributes, URI subjectCategory) {
      this.attributes = attributes != null ? Collections.unmodifiableList(attributes) : null;
      this.subjectCategory = subjectCategory;
   }

   public Subject(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();
      Node scNode = attrs.getNamedItem("SubjectCategory");

      try {
         this.subjectCategory = new URI(scNode != null ? scNode.getNodeValue() : "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
      } catch (java.net.URISyntaxException var8) {
         throw new URISyntaxException(var8);
      }

      this.attributes = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("Attribute")) {
            this.attributes.add(new Attribute(registry, node));
         }
      }

      this.attributes = this.attributes.isEmpty() ? null : Collections.unmodifiableList(this.attributes);
   }

   public String getElementName() {
      return "Subject";
   }

   public void encodeAttributes(PrintStream ps) {
      super.encodeAttributes(ps);
      if (this.subjectCategory != null && !"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject".equals(this.subjectCategory.toString())) {
         ps.print(" SubjectCategory=\"");
         ps.print(this.subjectCategory);
         ps.print("\"");
      }

   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.attributes != null) {
         Iterator it = this.attributes.iterator();

         while(it.hasNext()) {
            Attribute a = (Attribute)it.next();
            a.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Subject)) {
         return false;
      } else {
         Subject o = (Subject)other;
         return CollectionUtil.equals(this.attributes, o.attributes) && (this.subjectCategory == o.subjectCategory || this.subjectCategory != null && this.subjectCategory.equals(o.subjectCategory) || this.subjectCategory == null && "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject".equals(o.subjectCategory.toString()) || o.subjectCategory == null && "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject".equals(this.subjectCategory.toString()));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.attributes);
      result = HashCodeUtil.hash(result, this.subjectCategory != null ? this.subjectCategory : "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
      return result;
   }

   public URI getSubjectCategory() {
      return this.subjectCategory;
   }

   public List getAttributes() {
      return this.attributes;
   }
}
