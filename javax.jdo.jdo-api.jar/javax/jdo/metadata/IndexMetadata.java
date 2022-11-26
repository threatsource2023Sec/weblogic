package javax.jdo.metadata;

public interface IndexMetadata extends Metadata {
   IndexMetadata setName(String var1);

   String getName();

   IndexMetadata setTable(String var1);

   String getTable();

   IndexMetadata setUnique(boolean var1);

   boolean getUnique();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumn();

   int getNumberOfColumns();

   MemberMetadata[] getMembers();

   int getNumberOfMembers();

   FieldMetadata newFieldMetadata(String var1);

   PropertyMetadata newPropertyMetadata(String var1);
}
