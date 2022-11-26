package javax.jdo.metadata;

public interface ColumnMetadata extends Metadata {
   ColumnMetadata setName(String var1);

   String getName();

   ColumnMetadata setTarget(String var1);

   String getTarget();

   ColumnMetadata setTargetField(String var1);

   String getTargetField();

   ColumnMetadata setJDBCType(String var1);

   String getJDBCType();

   ColumnMetadata setSQLType(String var1);

   String getSQLType();

   ColumnMetadata setLength(int var1);

   Integer getLength();

   ColumnMetadata setScale(int var1);

   Integer getScale();

   ColumnMetadata setAllowsNull(boolean var1);

   Boolean getAllowsNull();

   ColumnMetadata setDefaultValue(String var1);

   String getDefaultValue();

   ColumnMetadata setInsertValue(String var1);

   String getInsertValue();

   ColumnMetadata setPosition(int var1);

   Integer getPosition();
}
