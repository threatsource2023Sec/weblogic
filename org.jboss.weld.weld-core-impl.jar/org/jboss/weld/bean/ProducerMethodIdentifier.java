package org.jboss.weld.bean;

import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.reflection.DeclaredMemberIndexer;

public class ProducerMethodIdentifier implements BeanIdentifier {
   private static final long serialVersionUID = 1L;
   private final AnnotatedTypeIdentifier typeIdentifier;
   private final int memberIndex;
   private final int hashCode;

   public ProducerMethodIdentifier(EnhancedAnnotatedMethod method, AbstractClassBean declaringBean) {
      this((AnnotatedTypeIdentifier)declaringBean.getAnnotated().getIdentifier(), DeclaredMemberIndexer.getIndexForMethod(method.getJavaMember()));
   }

   public ProducerMethodIdentifier(AnnotatedTypeIdentifier typeIdentifier, int memberIndex) {
      this.typeIdentifier = typeIdentifier;
      this.memberIndex = memberIndex;
      this.hashCode = this.asString().hashCode();
   }

   public String asString() {
      return BeanIdentifiers.forProducerMethod(this.typeIdentifier, this.memberIndex);
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      return this.asString();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof BeanIdentifier)) {
         return false;
      } else if (this.hashCode != obj.hashCode()) {
         return false;
      } else if (!(obj instanceof ProducerMethodIdentifier)) {
         BeanIdentifier that = (BeanIdentifier)obj;
         return this.asString().equals(that.asString());
      } else {
         ProducerMethodIdentifier that = (ProducerMethodIdentifier)obj;
         return this.typeIdentifier.equals(that.typeIdentifier) && this.memberIndex == that.memberIndex;
      }
   }
}
