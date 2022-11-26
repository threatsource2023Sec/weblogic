package javax.jdo.metadata;

public interface PrimaryKeyMetadata extends Metadata {
   PrimaryKeyMetadata setName(String var1);

   String getName();

   PrimaryKeyMetadata setColumn(String var1);

   String getColumn();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();
}
