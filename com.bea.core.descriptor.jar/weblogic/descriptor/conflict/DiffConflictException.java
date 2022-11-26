package weblogic.descriptor.conflict;

import java.util.Collection;

public class DiffConflictException extends Exception {
   public DiffConflictException(String conflictsFullMessage) {
      super(conflictsFullMessage);
   }

   public DiffConflictException(Collection conflicts) {
      this(DiffConflict.constructMessage(conflicts));
   }
}
