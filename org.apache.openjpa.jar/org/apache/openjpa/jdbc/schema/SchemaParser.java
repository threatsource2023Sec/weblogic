package org.apache.openjpa.jdbc.schema;

import org.apache.openjpa.lib.meta.MetaDataParser;

public interface SchemaParser extends MetaDataParser {
   boolean getDelayConstraintResolve();

   void setDelayConstraintResolve(boolean var1);

   SchemaGroup getSchemaGroup();

   void setSchemaGroup(SchemaGroup var1);

   void resolveConstraints();
}
