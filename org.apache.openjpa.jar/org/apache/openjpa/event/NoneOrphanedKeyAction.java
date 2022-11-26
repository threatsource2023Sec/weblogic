package org.apache.openjpa.event;

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ValueMetaData;

public class NoneOrphanedKeyAction implements OrphanedKeyAction {
   public Object orphan(Object oid, OpenJPAStateManager sm, ValueMetaData vmd) {
      return null;
   }
}
