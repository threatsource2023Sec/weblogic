package org.apache.openjpa.jdbc.meta;

import java.io.Serializable;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.MetaDataContext;
import org.apache.openjpa.meta.ValueMetaData;

public interface ValueMapping extends ValueMetaData, MetaDataContext, Serializable {
   int JOIN_FORWARD = 0;
   int JOIN_INVERSE = 1;
   int JOIN_EXPECTED_INVERSE = 2;
   int POLY_TRUE = 0;
   int POLY_FALSE = 1;
   int POLY_JOINABLE = 2;

   ValueMappingInfo getValueInfo();

   ValueHandler getHandler();

   void setHandler(ValueHandler var1);

   MappingRepository getMappingRepository();

   FieldMapping getFieldMapping();

   ClassMapping getTypeMapping();

   ClassMapping getDeclaredTypeMapping();

   ClassMapping getEmbeddedMapping();

   FieldMapping getValueMappedByMapping();

   Column[] getColumns();

   void setColumns(Column[] var1);

   ColumnIO getColumnIO();

   void setColumnIO(ColumnIO var1);

   ForeignKey getForeignKey();

   ForeignKey getForeignKey(ClassMapping var1);

   void setForeignKey(ForeignKey var1);

   int getJoinDirection();

   void setJoinDirection(int var1);

   void setForeignKey(Row var1, OpenJPAStateManager var2) throws SQLException;

   void whereForeignKey(Row var1, OpenJPAStateManager var2) throws SQLException;

   ClassMapping[] getIndependentTypeMappings();

   int getSelectSubclasses();

   Unique getValueUnique();

   void setValueUnique(Unique var1);

   Index getValueIndex();

   void setValueIndex(Index var1);

   boolean getUseClassCriteria();

   void setUseClassCriteria(boolean var1);

   int getPolymorphic();

   void setPolymorphic(int var1);

   void refSchemaComponents();

   void mapConstraints(String var1, boolean var2);

   void clearMapping();

   void syncMappingInfo();

   void copyMappingInfo(ValueMapping var1);
}
