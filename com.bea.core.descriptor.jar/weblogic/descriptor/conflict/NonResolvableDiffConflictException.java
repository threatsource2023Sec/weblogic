package weblogic.descriptor.conflict;

import java.util.List;

public class NonResolvableDiffConflictException extends Exception {
   private static final long serialVersionUID = 6948470697958806661L;
   private final List nonResolvableConflicts;

   public NonResolvableDiffConflictException(String message, List nonResolvableConflicts) {
      super(message);
      this.nonResolvableConflicts = nonResolvableConflicts;
   }

   public List getNonResolvableConflicts() {
      return this.nonResolvableConflicts;
   }
}
