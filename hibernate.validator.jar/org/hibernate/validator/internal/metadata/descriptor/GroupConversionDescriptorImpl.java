package org.hibernate.validator.internal.metadata.descriptor;

import javax.validation.metadata.GroupConversionDescriptor;

public class GroupConversionDescriptorImpl implements GroupConversionDescriptor {
   private final Class from;
   private final Class to;

   public GroupConversionDescriptorImpl(Class from, Class to) {
      this.from = from;
      this.to = to;
   }

   public Class getFrom() {
      return this.from;
   }

   public Class getTo() {
      return this.to;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.from == null ? 0 : this.from.hashCode());
      result = 31 * result + (this.to == null ? 0 : this.to.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         GroupConversionDescriptorImpl other = (GroupConversionDescriptorImpl)obj;
         if (this.from == null) {
            if (other.from != null) {
               return false;
            }
         } else if (!this.from.equals(other.from)) {
            return false;
         }

         if (this.to == null) {
            if (other.to != null) {
               return false;
            }
         } else if (!this.to.equals(other.to)) {
            return false;
         }

         return true;
      }
   }

   public String toString() {
      return "GroupConversionDescriptorImpl [from=" + this.from + ", to=" + this.to + "]";
   }
}
