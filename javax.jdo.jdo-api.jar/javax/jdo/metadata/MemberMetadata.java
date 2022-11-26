package javax.jdo.metadata;

import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceModifier;

public interface MemberMetadata extends Metadata {
   MemberMetadata setName(String var1);

   String getName();

   MemberMetadata setTable(String var1);

   String getTable();

   MemberMetadata setColumn(String var1);

   String getColumn();

   MemberMetadata setFieldType(String var1);

   String getFieldType();

   MemberMetadata setDeleteAction(ForeignKeyAction var1);

   ForeignKeyAction getDeleteAction();

   MemberMetadata setPersistenceModifier(PersistenceModifier var1);

   PersistenceModifier getPersistenceModifier();

   MemberMetadata setNullValue(NullValue var1);

   NullValue getNullValue();

   MemberMetadata setDefaultFetchGroup(boolean var1);

   Boolean getDefaultFetchGroup();

   MemberMetadata setDependent(boolean var1);

   Boolean getDependent();

   MemberMetadata setEmbedded(boolean var1);

   Boolean getEmbedded();

   MemberMetadata setSerialized(boolean var1);

   Boolean getSerialized();

   MemberMetadata setPrimaryKey(boolean var1);

   boolean getPrimaryKey();

   MemberMetadata setIndexed(boolean var1);

   Boolean getIndexed();

   MemberMetadata setUnique(boolean var1);

   Boolean getUnique();

   MemberMetadata setCacheable(boolean var1);

   boolean getCacheable();

   MemberMetadata setRecursionDepth(int var1);

   int getRecursionDepth();

   MemberMetadata setLoadFetchGroup(String var1);

   String getLoadFetchGroup();

   MemberMetadata setValueStrategy(IdGeneratorStrategy var1);

   IdGeneratorStrategy getValueStrategy();

   MemberMetadata setCustomStrategy(String var1);

   String getCustomStrategy();

   MemberMetadata setSequence(String var1);

   String getSequence();

   MemberMetadata setMappedBy(String var1);

   String getMappedBy();

   ArrayMetadata newArrayMetadata();

   ArrayMetadata getArrayMetadata();

   CollectionMetadata newCollectionMetadata();

   CollectionMetadata getCollectionMetadata();

   MapMetadata newMapMetadata();

   MapMetadata getMapMetadata();

   JoinMetadata newJoinMetadata();

   JoinMetadata getJoinMetadata();

   EmbeddedMetadata newEmbeddedMetadata();

   EmbeddedMetadata getEmbeddedMetadata();

   ElementMetadata newElementMetadata();

   ElementMetadata getElementMetadata();

   KeyMetadata newKeyMetadata();

   KeyMetadata getKeyMetadata();

   ValueMetadata newValueMetadata();

   ValueMetadata getValueMetadata();

   IndexMetadata newIndexMetadata();

   IndexMetadata getIndexMetadata();

   UniqueMetadata newUniqueMetadata();

   UniqueMetadata getUniqueMetadata();

   ForeignKeyMetadata newForeignKeyMetadata();

   ForeignKeyMetadata getForeignKeyMetadata();

   OrderMetadata newOrderMetadata();

   OrderMetadata getOrderMetadata();

   ColumnMetadata[] getColumns();

   ColumnMetadata newColumnMetadata();

   int getNumberOfColumns();
}
