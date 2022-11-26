package org.apache.openjpa.event;

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ObjectNotFoundException;

public class ExceptionOrphanedKeyAction implements OrphanedKeyAction {
   private static final Localizer _loc = Localizer.forPackage(ExceptionOrphanedKeyAction.class);

   public Object orphan(Object oid, OpenJPAStateManager sm, ValueMetaData vmd) {
      throw new ObjectNotFoundException(_loc.get("orphaned-key", oid, vmd));
   }
}
