package javax.jdo.metadata;

public interface UniqueMetadata extends Metadata {
   UniqueMetadata setName(String var1);

   String getName();

   UniqueMetadata setTable(String var1);

   String getTable();

   UniqueMetadata setDeferred(boolean var1);

   Boolean getDeferred();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();

   MemberMetadata[] getMembers();

   int getNumberOfMembers();

   FieldMetadata newFieldMetadata(String var1);

   PropertyMetadata newPropertyMetadata(String var1);
}
