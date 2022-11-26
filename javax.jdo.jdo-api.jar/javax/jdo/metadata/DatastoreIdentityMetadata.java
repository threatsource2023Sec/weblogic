package javax.jdo.metadata;

import javax.jdo.annotations.IdGeneratorStrategy;

public interface DatastoreIdentityMetadata extends Metadata {
   DatastoreIdentityMetadata setColumn(String var1);

   String getColumn();

   DatastoreIdentityMetadata setStrategy(IdGeneratorStrategy var1);

   IdGeneratorStrategy getStrategy();

   DatastoreIdentityMetadata setCustomStrategy(String var1);

   String getCustomStrategy();

   DatastoreIdentityMetadata setSequence(String var1);

   String getSequence();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();
}
