package org.apache.openjpa.event;

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ValueMetaData;

public interface OrphanedKeyAction {
   Object orphan(Object var1, OpenJPAStateManager var2, ValueMetaData var3);
}
