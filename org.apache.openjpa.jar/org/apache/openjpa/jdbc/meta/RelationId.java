package org.apache.openjpa.jdbc.meta;

import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface RelationId {
   Object toRelationDataStoreValue(OpenJPAStateManager var1, Column var2);
}
