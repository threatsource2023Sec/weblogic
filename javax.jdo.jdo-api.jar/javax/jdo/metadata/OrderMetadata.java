package javax.jdo.metadata;

public interface OrderMetadata extends Metadata {
   OrderMetadata setColumn(String var1);

   String getColumn();

   OrderMetadata setMappedBy(String var1);

   String getMappedBy();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();

   IndexMetadata newIndexMetadata();

   IndexMetadata getIndexMetadata();
}
