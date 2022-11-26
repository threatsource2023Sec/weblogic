package org.glassfish.hk2.extras.operation.internal;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.extras.operation.OperationIdentifier;

public class OperationIdentifierImpl implements OperationIdentifier {
   private final String identifier;
   private final Annotation scope;
   private final int hashCode;

   OperationIdentifierImpl(String identifier, Annotation scope) {
      this.identifier = identifier;
      this.scope = scope;
      this.hashCode = identifier.hashCode();
   }

   public String getOperationIdentifier() {
      return this.identifier;
   }

   public Annotation getOperationScope() {
      return this.scope;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         return !(o instanceof OperationIdentifierImpl) ? false : this.identifier.equals(((OperationIdentifierImpl)o).identifier);
      }
   }

   public String toString() {
      return this.identifier;
   }
}
