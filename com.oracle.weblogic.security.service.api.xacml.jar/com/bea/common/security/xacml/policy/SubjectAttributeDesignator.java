package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SubjectAttributeDesignator extends AttributeDesignator {
   private static final long serialVersionUID = 3582840085823353659L;
   private static URI ACCESS_SUBJECT = null;
   private URI subjectCategory;

   public SubjectAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent) {
      this(attributeId, dataType, mustBePresent, (URI)null);
   }

   public SubjectAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent, URI subjectCategory) {
      super(attributeId, dataType, mustBePresent);
      this.subjectCategory = subjectCategory;
   }

   public SubjectAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent, String issuer) {
      super(attributeId, dataType, mustBePresent, issuer);
   }

   public SubjectAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent, String issuer, URI subjectCategory) {
      super(attributeId, dataType, mustBePresent, issuer);
      this.subjectCategory = subjectCategory;
   }

   public SubjectAttributeDesignator(Node root) throws URISyntaxException {
      super(root);
      NamedNodeMap attrs = root.getAttributes();
      Node scNode = attrs.getNamedItem("SubjectCategory");

      try {
         this.subjectCategory = scNode != null ? new URI(scNode.getNodeValue()) : ACCESS_SUBJECT;
      } catch (java.net.URISyntaxException var5) {
         throw new URISyntaxException(var5);
      }
   }

   public void encodeAttributes(PrintStream ps) {
      super.encodeAttributes(ps);
      if (this.subjectCategory != null && !ACCESS_SUBJECT.equals(this.subjectCategory)) {
         ps.print(" SubjectCategory=\"");
         ps.print(this.subjectCategory);
         ps.print("\"");
      }

   }

   public boolean equals(Object other) {
      if (super.equals(other) && other instanceof SubjectAttributeDesignator) {
         SubjectAttributeDesignator o = (SubjectAttributeDesignator)other;
         return this.subjectCategory == o.subjectCategory || this.subjectCategory != null && this.subjectCategory.equals(o.subjectCategory) || this.subjectCategory == null && ACCESS_SUBJECT.equals(o.subjectCategory) || o.subjectCategory == null && ACCESS_SUBJECT.equals(this.subjectCategory);
      } else {
         return false;
      }
   }

   public int internalHashCode() {
      int result = super.internalHashCode();
      result = HashCodeUtil.hash(result, this.subjectCategory != null ? this.subjectCategory : ACCESS_SUBJECT);
      return result;
   }

   public URI getSubjectCategory() {
      return this.subjectCategory;
   }

   protected String getDesignatorType() {
      return "Subject";
   }

   static {
      try {
         ACCESS_SUBJECT = new URI("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
      } catch (java.net.URISyntaxException var1) {
         var1.printStackTrace();
      }

   }
}
