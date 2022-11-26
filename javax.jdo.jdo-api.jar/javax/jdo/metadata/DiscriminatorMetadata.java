package javax.jdo.metadata;

import javax.jdo.annotations.DiscriminatorStrategy;

public interface DiscriminatorMetadata extends Metadata {
   DiscriminatorMetadata setColumn(String var1);

   String getColumn();

   DiscriminatorMetadata setValue(String var1);

   String getValue();

   DiscriminatorMetadata setStrategy(DiscriminatorStrategy var1);

   DiscriminatorStrategy getStrategy();

   DiscriminatorMetadata setIndexed(Indexed var1);

   Indexed getIndexed();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();

   IndexMetadata newIndexMetadata();

   IndexMetadata getIndexMetadata();
}
