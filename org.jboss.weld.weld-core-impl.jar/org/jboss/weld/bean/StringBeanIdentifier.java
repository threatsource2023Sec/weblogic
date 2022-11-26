package org.jboss.weld.bean;

import org.jboss.weld.serialization.spi.BeanIdentifier;

public class StringBeanIdentifier implements BeanIdentifier {
   private static final long serialVersionUID = -3389031898783605246L;
   private final String value;

   public StringBeanIdentifier(String value) {
      this.value = value;
   }

   public String asString() {
      return this.value;
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof BeanIdentifier) {
         if (this.hashCode() != obj.hashCode()) {
            return false;
         } else if (obj instanceof StringBeanIdentifier) {
            StringBeanIdentifier that = (StringBeanIdentifier)obj;
            return this.value.equals(that.value);
         } else {
            BeanIdentifier that = (BeanIdentifier)obj;
            return this.value.equals(that.asString());
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return this.asString();
   }
}
