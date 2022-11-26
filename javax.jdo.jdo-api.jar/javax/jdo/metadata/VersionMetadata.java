package javax.jdo.metadata;

import javax.jdo.annotations.VersionStrategy;

public interface VersionMetadata extends Metadata {
   VersionMetadata setStrategy(VersionStrategy var1);

   VersionStrategy getStrategy();

   VersionMetadata setColumn(String var1);

   String getColumn();

   VersionMetadata setIndexed(Indexed var1);

   Indexed getIndexed();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();

   IndexMetadata newIndexMetadata();

   IndexMetadata getIndexMetadata();
}
