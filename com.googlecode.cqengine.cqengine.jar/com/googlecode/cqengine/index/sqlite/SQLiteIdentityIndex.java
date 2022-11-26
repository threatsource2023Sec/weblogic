package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.CloseableIterable;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.NonHeapTypeIndex;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.persistence.support.serialization.PersistenceConfig;
import com.googlecode.cqengine.persistence.support.serialization.PojoSerializer;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.lang.reflect.Constructor;

public class SQLiteIdentityIndex implements IdentityAttributeIndex, SortedKeyStatisticsAttributeIndex, NonHeapTypeIndex {
   final SQLiteIndex sqLiteIndex;
   final Class objectType;
   final SimpleAttribute primaryKeyAttribute;
   final SimpleAttribute foreignKeyAttribute;
   final PojoSerializer pojoSerializer;

   public SQLiteIdentityIndex(SimpleAttribute primaryKeyAttribute) {
      this.sqLiteIndex = new SQLiteIndex(primaryKeyAttribute, new SerializingAttribute(primaryKeyAttribute.getObjectType(), byte[].class), new DeserializingAttribute(byte[].class, primaryKeyAttribute.getObjectType()), "") {
         public Index getEffectiveIndex() {
            return SQLiteIdentityIndex.this.getEffectiveIndex();
         }
      };
      this.objectType = primaryKeyAttribute.getObjectType();
      this.primaryKeyAttribute = primaryKeyAttribute;
      this.foreignKeyAttribute = new ForeignKeyAttribute();
      this.pojoSerializer = createSerializer(this.objectType);
   }

   public SimpleAttribute getForeignKeyAttribute() {
      return this.foreignKeyAttribute;
   }

   public Attribute getAttribute() {
      return this.sqLiteIndex.getAttribute();
   }

   public boolean isMutable() {
      return this.sqLiteIndex.isMutable();
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      return this.sqLiteIndex.supportsQuery(query, queryOptions);
   }

   public boolean isQuantized() {
      return this.sqLiteIndex.isQuantized();
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      return this.sqLiteIndex.retrieve(query, queryOptions);
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      return this.sqLiteIndex.addAll(objectSet, queryOptions);
   }

   public boolean removeAll(ObjectSet objectSet, QueryOptions queryOptions) {
      return this.sqLiteIndex.removeAll(objectSet, queryOptions);
   }

   public void clear(QueryOptions queryOptions) {
      this.sqLiteIndex.clear(queryOptions);
   }

   public void init(ObjectStore objectStore, QueryOptions queryOptions) {
      this.sqLiteIndex.init(objectStore, queryOptions);
   }

   public CloseableIterable getDistinctKeys(QueryOptions queryOptions) {
      return this.sqLiteIndex.getDistinctKeys(queryOptions);
   }

   public Integer getCountForKey(Comparable key, QueryOptions queryOptions) {
      return this.sqLiteIndex.getCountForKey(key, queryOptions);
   }

   public CloseableIterable getDistinctKeys(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.sqLiteIndex.getDistinctKeys(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(QueryOptions queryOptions) {
      return this.sqLiteIndex.getDistinctKeysDescending(queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.sqLiteIndex.getDistinctKeysDescending(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeysDescending(QueryOptions queryOptions) {
      return this.sqLiteIndex.getStatisticsForDistinctKeysDescending(queryOptions);
   }

   public Integer getCountOfDistinctKeys(QueryOptions queryOptions) {
      return this.sqLiteIndex.getCountOfDistinctKeys(queryOptions);
   }

   public CloseableIterable getStatisticsForDistinctKeys(QueryOptions queryOptions) {
      return this.sqLiteIndex.getStatisticsForDistinctKeys(queryOptions);
   }

   public CloseableIterable getKeysAndValues(QueryOptions queryOptions) {
      return this.sqLiteIndex.getKeysAndValues(queryOptions);
   }

   public CloseableIterable getKeysAndValues(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.sqLiteIndex.getKeysAndValues(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(QueryOptions queryOptions) {
      return this.sqLiteIndex.getKeysAndValuesDescending(queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.sqLiteIndex.getKeysAndValuesDescending(lowerBound, lowerInclusive, upperBound, upperInclusive, queryOptions);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SQLiteIdentityIndex that = (SQLiteIdentityIndex)o;
         return this.primaryKeyAttribute.equals(that.primaryKeyAttribute);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.getClass().hashCode();
      result = 31 * result + this.primaryKeyAttribute.hashCode();
      return result;
   }

   static PojoSerializer createSerializer(Class objectType) {
      Class serializerClass = null;

      try {
         PersistenceConfig persistenceConfig = (PersistenceConfig)objectType.getAnnotation(PersistenceConfig.class);
         if (persistenceConfig == null) {
            persistenceConfig = PersistenceConfig.DEFAULT_CONFIG;
         }

         serializerClass = persistenceConfig.serializer();
         Constructor constructor = serializerClass.getConstructor(Class.class, PersistenceConfig.class);
         Object serializerInstance = constructor.newInstance(objectType, persistenceConfig);
         return (PojoSerializer)serializerInstance;
      } catch (Exception var5) {
         throw new IllegalStateException("Failed to instantiate PojoSerializer: " + serializerClass, var5);
      }
   }

   public static SQLiteIdentityIndex onAttribute(SimpleAttribute primaryKeyAttribute) {
      return new SQLiteIdentityIndex(primaryKeyAttribute);
   }

   class ForeignKeyAttribute extends SimpleAttribute {
      public ForeignKeyAttribute() {
         super(SQLiteIdentityIndex.this.primaryKeyAttribute.getAttributeType(), SQLiteIdentityIndex.this.objectType, ForeignKeyAttribute.class.getSimpleName() + "_" + SQLiteIdentityIndex.this.primaryKeyAttribute.getAttributeName());
      }

      public Object getValue(Comparable foreignKey, QueryOptions queryOptions) {
         return SQLiteIdentityIndex.this.retrieve(QueryFactory.equal(SQLiteIdentityIndex.this.primaryKeyAttribute, foreignKey), QueryFactory.noQueryOptions()).uniqueResult();
      }
   }

   class DeserializingAttribute extends SimpleAttribute {
      public DeserializingAttribute(Class objectType, Class attributeType) {
         super(objectType, attributeType);
      }

      public Object getValue(byte[] bytes, QueryOptions queryOptions) {
         return SQLiteIdentityIndex.this.pojoSerializer.deserialize(bytes);
      }
   }

   class SerializingAttribute extends SimpleAttribute {
      public SerializingAttribute(Class objectType, Class attributeType) {
         super(objectType, attributeType);
      }

      public byte[] getValue(Object object, QueryOptions queryOptions) {
         if (object == null) {
            throw new NullPointerException("Object was null");
         } else {
            try {
               return SQLiteIdentityIndex.this.pojoSerializer.serialize(object);
            } catch (Exception var4) {
               throw new IllegalStateException("Failed to serialize object, object type: " + SQLiteIdentityIndex.this.objectType, var4);
            }
         }
      }
   }
}
