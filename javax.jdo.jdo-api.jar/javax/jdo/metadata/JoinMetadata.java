package javax.jdo.metadata;

import javax.jdo.annotations.ForeignKeyAction;

public interface JoinMetadata extends Metadata {
   JoinMetadata setColumn(String var1);

   String getColumn();

   JoinMetadata setTable(String var1);

   String getTable();

   JoinMetadata setOuter(boolean var1);

   boolean getOuter();

   JoinMetadata setDeleteAction(ForeignKeyAction var1);

   ForeignKeyAction getDeleteAction();

   JoinMetadata setIndexed(Indexed var1);

   Indexed getIndexed();

   JoinMetadata setUnique(boolean var1);

   Boolean getUnique();

   IndexMetadata newIndexMetadata();

   IndexMetadata getIndexMetadata();

   UniqueMetadata newUniqueMetadata();

   UniqueMetadata getUniqueMetadata();

   ForeignKeyMetadata newForeignKeyMetadata();

   ForeignKeyMetadata getForeignKeyMetadata();

   PrimaryKeyMetadata newPrimaryKeyMetadata();

   PrimaryKeyMetadata getPrimaryKeyMetadata();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();
}
