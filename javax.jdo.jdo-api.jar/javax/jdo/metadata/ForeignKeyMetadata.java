package javax.jdo.metadata;

import javax.jdo.annotations.ForeignKeyAction;

public interface ForeignKeyMetadata extends Metadata {
   ForeignKeyMetadata setName(String var1);

   String getName();

   ForeignKeyMetadata setTable(String var1);

   String getTable();

   ForeignKeyMetadata setUnique(boolean var1);

   Boolean getUnique();

   ForeignKeyMetadata setDeferred(boolean var1);

   Boolean getDeferred();

   ForeignKeyMetadata setDeleteAction(ForeignKeyAction var1);

   ForeignKeyAction getDeleteAction();

   ForeignKeyMetadata setUpdateAction(ForeignKeyAction var1);

   ForeignKeyAction getUpdateAction();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();

   MemberMetadata[] getMembers();

   int getNumberOfMembers();

   FieldMetadata newFieldMetadata(String var1);

   PropertyMetadata newPropertyMetadata(String var1);
}
