package javax.jdo.metadata;

import java.lang.reflect.Method;
import javax.jdo.annotations.IdentityType;

public interface TypeMetadata extends Metadata {
   String getName();

   TypeMetadata setIdentityType(IdentityType var1);

   IdentityType getIdentityType();

   TypeMetadata setObjectIdClass(String var1);

   String getObjectIdClass();

   TypeMetadata setRequiresExtent(boolean var1);

   boolean getRequiresExtent();

   TypeMetadata setDetachable(boolean var1);

   boolean getDetachable();

   TypeMetadata setCacheable(boolean var1);

   boolean getCacheable();

   TypeMetadata setSerializeRead(boolean var1);

   boolean getSerializeRead();

   TypeMetadata setEmbeddedOnly(boolean var1);

   Boolean getEmbeddedOnly();

   TypeMetadata setCatalog(String var1);

   String getCatalog();

   TypeMetadata setSchema(String var1);

   String getSchema();

   TypeMetadata setTable(String var1);

   String getTable();

   InheritanceMetadata newInheritanceMetadata();

   InheritanceMetadata getInheritanceMetadata();

   VersionMetadata newVersionMetadata();

   VersionMetadata getVersionMetadata();

   DatastoreIdentityMetadata newDatastoreIdentityMetadata();

   DatastoreIdentityMetadata getDatastoreIdentityMetadata();

   PrimaryKeyMetadata newPrimaryKeyMetadata();

   PrimaryKeyMetadata getPrimaryKeyMetadata();

   JoinMetadata[] getJoins();

   JoinMetadata newJoinMetadata();

   int getNumberOfJoins();

   ForeignKeyMetadata[] getForeignKeys();

   ForeignKeyMetadata newForeignKeyMetadata();

   int getNumberOfForeignKeys();

   IndexMetadata[] getIndices();

   IndexMetadata newIndexMetadata();

   int getNumberOfIndices();

   UniqueMetadata[] getUniques();

   UniqueMetadata newUniqueMetadata();

   int getNumberOfUniques();

   MemberMetadata[] getMembers();

   int getNumberOfMembers();

   PropertyMetadata newPropertyMetadata(String var1);

   PropertyMetadata newPropertyMetadata(Method var1);

   QueryMetadata[] getQueries();

   QueryMetadata newQueryMetadata(String var1);

   int getNumberOfQueries();

   FetchGroupMetadata[] getFetchGroups();

   FetchGroupMetadata newFetchGroupMetadata(String var1);

   int getNumberOfFetchGroups();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();
}
