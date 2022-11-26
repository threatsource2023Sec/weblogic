package org.jboss.weld.bean;

import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public class ManagedBeanIdentifier implements BeanIdentifier {
   private static final long serialVersionUID = -2549776947566879012L;
   private final AnnotatedTypeIdentifier typeIdentifier;
   private final int hashCode;

   public ManagedBeanIdentifier(AnnotatedTypeIdentifier typeIdentifier) {
      this.typeIdentifier = typeIdentifier;
      this.hashCode = this.asString().hashCode();
   }

   public String asString() {
      return BeanIdentifiers.forManagedBean(this.typeIdentifier);
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof BeanIdentifier) {
         if (this.hashCode != obj.hashCode()) {
            return false;
         } else if (obj instanceof ManagedBeanIdentifier) {
            ManagedBeanIdentifier that = (ManagedBeanIdentifier)obj;
            return this.typeIdentifier.equals(that.typeIdentifier);
         } else {
            BeanIdentifier that = (BeanIdentifier)obj;
            return this.asString().equals(that.asString());
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return this.asString();
   }
}
