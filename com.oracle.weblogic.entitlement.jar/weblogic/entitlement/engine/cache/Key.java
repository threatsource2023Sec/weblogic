package weblogic.entitlement.engine.cache;

import javax.security.auth.Subject;
import weblogic.security.spi.Resource;
import weblogic.utils.HashCodeUtil;

public class Key {
   private Resource resource;
   private Subject subject;

   public Key(Resource resource, Subject subject) {
      this.resource = resource;
      this.subject = subject;
   }

   public Resource getResource() {
      return this.resource;
   }

   void setResource(Resource resource) {
      this.resource = resource;
   }

   public Subject getSubject() {
      return this.subject;
   }

   void setSubject(Subject subject) {
      this.subject = subject;
   }

   void setValues(Resource resource, Subject subject) {
      this.resource = resource;
      this.subject = subject;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Key)) {
         return false;
      } else {
         Key o = (Key)other;
         return (this.resource == o.resource || this.resource != null && this.resource.equals(o.resource)) && this.subject == o.subject;
      }
   }

   public int hashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.resource);
      result = HashCodeUtil.hash(result, System.identityHashCode(this.subject));
      return result;
   }
}
