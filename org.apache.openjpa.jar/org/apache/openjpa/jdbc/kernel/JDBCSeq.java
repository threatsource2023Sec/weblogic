package org.apache.openjpa.jdbc.kernel;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.kernel.Seq;

public interface JDBCSeq extends Seq {
   void addSchema(ClassMapping var1, SchemaGroup var2);
}
