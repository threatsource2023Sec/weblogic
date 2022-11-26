package com.googlecode.cqengine.index.sqlite;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.support.DBQueries;
import com.googlecode.cqengine.index.sqlite.support.DBUtils;
import com.googlecode.cqengine.index.sqlite.support.SQLiteIndexFlags;
import com.googlecode.cqengine.index.support.AbstractAttributeIndex;
import com.googlecode.cqengine.index.support.CloseableIterable;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.CloseableRequestResources;
import com.googlecode.cqengine.index.support.KeyStatistics;
import com.googlecode.cqengine.index.support.KeyValue;
import com.googlecode.cqengine.index.support.KeyValueMaterialized;
import com.googlecode.cqengine.index.support.LazyCloseableIterator;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.NonHeapTypeIndex;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.FlagsEnabled;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Between;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.FilterQuery;
import com.googlecode.cqengine.query.simple.GreaterThan;
import com.googlecode.cqengine.query.simple.Has;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.query.simple.LessThan;
import com.googlecode.cqengine.query.simple.StringStartsWith;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Iterator;
import org.sqlite.SQLiteConfig;

public class SQLiteIndex extends AbstractAttributeIndex implements SortedKeyStatisticsAttributeIndex, NonHeapTypeIndex {
   static final int INDEX_RETRIEVAL_COST = 80;
   static final int INDEX_RETRIEVAL_COST_FILTERING = 81;
   static final boolean FORCE_REINIT_OF_PREEXISTING_INDEXES = Boolean.getBoolean("cqengine.reinit.preexisting.indexes");
   final String tableName;
   final SimpleAttribute primaryKeyAttribute;
   final SimpleAttribute foreignKeyAttribute;
   SQLiteConfig.SynchronousMode pragmaSynchronous;
   SQLiteConfig.JournalMode pragmaJournalMode;
   boolean canModifySyncAndJournaling;

   public SQLiteIndex(Attribute attribute, SimpleAttribute primaryKeyAttribute, SimpleAttribute foreignKeyAttribute, String tableNameSuffix) {
      super(attribute, new HashSet() {
         {
            this.add(Equal.class);
            this.add(In.class);
            this.add(LessThan.class);
            this.add(GreaterThan.class);
            this.add(Between.class);
            this.add(StringStartsWith.class);
            this.add(Has.class);
         }
      });
      this.tableName = DBUtils.sanitizeForTableName(attribute.getAttributeName()) + tableNameSuffix;
      this.primaryKeyAttribute = primaryKeyAttribute;
      this.foreignKeyAttribute = foreignKeyAttribute;
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      return query instanceof FilterQuery || super.supportsQuery(query, queryOptions);
   }

   public boolean isMutable() {
      return true;
   }

   public boolean isQuantized() {
      return false;
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public ResultSet retrieve(final Query query, final QueryOptions queryOptions) {
      final ConnectionManager connectionManager = this.getConnectionManager(queryOptions);
      final CloseableRequestResources.CloseableResourceGroup closeableResourceGroup = CloseableRequestResources.forQueryOptions(queryOptions).addGroup();
      if (query instanceof FilterQuery) {
         final FilterQuery filterQuery = (FilterQuery)query;
         return new ResultSet() {
            public Iterator iterator() {
               Connection searchConnection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
               final java.sql.ResultSet searchResultSet = DBQueries.getAllIndexEntries(SQLiteIndex.this.tableName, searchConnection);
               closeableResourceGroup.add(DBUtils.wrapAsCloseable(searchResultSet));
               return new LazyIterator() {
                  protected Object computeNext() {
                     try {
                        Object objectKey;
                        Comparable objectValue;
                        do {
                           if (!searchResultSet.next()) {
                              close();
                              return this.endOfData();
                           }

                           objectKey = DBUtils.getValueFromResultSet(1, searchResultSet, SQLiteIndex.this.primaryKeyAttribute.getAttributeType());
                           objectValue = (Comparable)DBUtils.getValueFromResultSet(2, searchResultSet, SQLiteIndex.this.attribute.getAttributeType());
                        } while(!filterQuery.matchesValue(objectValue, queryOptions));

                        return SQLiteIndex.this.foreignKeyAttribute.getValue(objectKey, queryOptions);
                     } catch (Exception var3) {
                        this.endOfData();
                        close();
                        throw new IllegalStateException("Unable to retrieve the ResultSet item.", var3);
                     }
                  }
               };
            }

            public boolean contains(Object object) {
               Connection connection = null;
               java.sql.ResultSet searchResultSet = null;

               boolean var4;
               try {
                  connection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
                  searchResultSet = DBQueries.getIndexEntryByObjectKey(SQLiteIndex.this.primaryKeyAttribute.getValue(object, queryOptions), SQLiteIndex.this.tableName, connection);
                  var4 = this.lazyMatchingValuesIterable(searchResultSet).iterator().hasNext();
               } finally {
                  DBUtils.closeQuietly(searchResultSet);
               }

               return var4;
            }

            public boolean matches(Object object) {
               return query.matches(object, queryOptions);
            }

            public Query getQuery() {
               return query;
            }

            public QueryOptions getQueryOptions() {
               return queryOptions;
            }

            public int getRetrievalCost() {
               return 81;
            }

            public int getMergeCost() {
               Connection connection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
               return DBQueries.count(QueryFactory.has(SQLiteIndex.this.primaryKeyAttribute), SQLiteIndex.this.tableName, connection);
            }

            public int size() {
               Connection connection = null;
               java.sql.ResultSet searchResultSet = null;

               int var4;
               try {
                  connection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
                  searchResultSet = DBQueries.getAllIndexEntries(SQLiteIndex.this.tableName, connection);
                  Iterable iterator = this.lazyMatchingValuesIterable(searchResultSet);
                  var4 = IteratorUtil.countElements(iterator);
               } finally {
                  DBUtils.closeQuietly(searchResultSet);
               }

               return var4;
            }

            public void close() {
               closeableResourceGroup.close();
            }

            Iterable lazyMatchingValuesIterable(final java.sql.ResultSet searchResultSet) {
               return new Iterable() {
                  public Iterator iterator() {
                     return new LazyIterator() {
                        Object currentKey = null;

                        protected Object computeNext() {
                           try {
                              while(searchResultSet.next()) {
                                 Object objectKey = DBUtils.getValueFromResultSet(1, searchResultSet, SQLiteIndex.this.primaryKeyAttribute.getAttributeType());
                                 if (this.currentKey == null || !this.currentKey.equals(objectKey)) {
                                    Comparable objectValue = (Comparable)DBUtils.getValueFromResultSet(2, searchResultSet, SQLiteIndex.this.attribute.getAttributeType());
                                    if (filterQuery.matchesValue(objectValue, queryOptions)) {
                                       this.currentKey = objectKey;
                                       return objectKey;
                                    }
                                 }
                              }

                              close();
                              return this.endOfData();
                           } catch (Exception var3) {
                              this.endOfData();
                              close();
                              throw new IllegalStateException("Unable to retrieve the ResultSet item.", var3);
                           }
                        }
                     };
                  }
               };
            }
         };
      } else {
         return new ResultSet() {
            public Iterator iterator() {
               Connection searchConnection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
               final java.sql.ResultSet searchResultSet = DBQueries.search(query, SQLiteIndex.this.tableName, searchConnection);
               closeableResourceGroup.add(DBUtils.wrapAsCloseable(searchResultSet));
               return new LazyIterator() {
                  protected Object computeNext() {
                     try {
                        if (!searchResultSet.next()) {
                           close();
                           return this.endOfData();
                        } else {
                           Object objectKey = DBUtils.getValueFromResultSet(1, searchResultSet, SQLiteIndex.this.primaryKeyAttribute.getAttributeType());
                           return SQLiteIndex.this.foreignKeyAttribute.getValue(objectKey, queryOptions);
                        }
                     } catch (Exception var2) {
                        this.endOfData();
                        close();
                        throw new IllegalStateException("Unable to retrieve the ResultSet item.", var2);
                     }
                  }
               };
            }

            public int getRetrievalCost() {
               return 80;
            }

            public int getMergeCost() {
               Connection connection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
               return DBQueries.count(query, SQLiteIndex.this.tableName, connection);
            }

            public boolean contains(Object object) {
               Object objectKey = SQLiteIndex.this.primaryKeyAttribute.getValue(object, queryOptions);
               Connection connection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
               return DBQueries.contains(objectKey, query, SQLiteIndex.this.tableName, connection);
            }

            public boolean matches(Object object) {
               return query.matches(object, queryOptions);
            }

            public int size() {
               Connection connection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
               boolean attributeHasAtMostOneValue = SQLiteIndex.this.attribute instanceof SimpleAttribute || SQLiteIndex.this.attribute instanceof SimpleNullableAttribute;
               boolean queryIsADisjointInQuery = query instanceof In && ((In)query).isDisjoint();
               return !queryIsADisjointInQuery && !attributeHasAtMostOneValue ? DBQueries.countDistinct(query, SQLiteIndex.this.tableName, connection) : DBQueries.count(query, SQLiteIndex.this.tableName, connection);
            }

            public void close() {
               closeableResourceGroup.close();
            }

            public Query getQuery() {
               return query;
            }

            public QueryOptions getQueryOptions() {
               return queryOptions;
            }
         };
      }
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      return this.doAddAll(objectSet, queryOptions, false);
   }

   boolean doAddAll(ObjectSet objectSet, QueryOptions queryOptions, boolean isInit) {
      boolean var11;
      try {
         ConnectionManager connectionManager = this.getConnectionManager(queryOptions);
         if (!connectionManager.isApplyUpdateForIndexEnabled(this)) {
            boolean var15 = false;
            return var15;
         }

         Connection connection = connectionManager.getConnection(this, queryOptions);
         if (!FORCE_REINIT_OF_PREEXISTING_INDEXES && isInit && DBQueries.indexTableExists(this.tableName, connection)) {
            boolean var16 = false;
            return var16;
         }

         DBQueries.createIndexTable(this.tableName, this.primaryKeyAttribute.getAttributeType(), this.getAttribute().getAttributeType(), connection);
         SQLiteIndexFlags.BulkImportExternallyManged bulkImportExternallyManged = (SQLiteIndexFlags.BulkImportExternallyManged)queryOptions.get(SQLiteIndexFlags.BulkImportExternallyManged.class);
         boolean isBulkImport = bulkImportExternallyManged == null && FlagsEnabled.isFlagEnabled(queryOptions, SQLiteIndexFlags.BULK_IMPORT);
         boolean isSuspendSyncAndJournaling = FlagsEnabled.isFlagEnabled(queryOptions, SQLiteIndexFlags.BULK_IMPORT_SUSPEND_SYNC_AND_JOURNALING);
         if ((bulkImportExternallyManged != null || isBulkImport) && !objectSet.isEmpty()) {
            DBQueries.dropIndexOnTable(this.tableName, connection);
            if (isSuspendSyncAndJournaling) {
               if (!this.canModifySyncAndJournaling) {
                  throw new IllegalStateException("Cannot suspend sync and journaling because it was not possible to read the original 'synchronous' and 'journal_mode' pragmas during the index initialization.");
               }

               DBQueries.suspendSyncAndJournaling(connection);
            } else if (this.canModifySyncAndJournaling) {
               DBQueries.setSyncAndJournaling(connection, this.pragmaSynchronous, this.pragmaJournalMode);
            }
         } else {
            DBQueries.createIndexOnTable(this.tableName, connection);
            if (this.canModifySyncAndJournaling) {
               DBQueries.setSyncAndJournaling(connection, this.pragmaSynchronous, this.pragmaJournalMode);
            }
         }

         Iterable rows = rowIterable(objectSet, this.primaryKeyAttribute, this.getAttribute(), queryOptions);
         int rowsModified = DBQueries.bulkAdd(rows, this.tableName, connection);
         if (isBulkImport || SQLiteIndexFlags.BulkImportExternallyManged.LAST.equals(bulkImportExternallyManged)) {
            DBQueries.createIndexOnTable(this.tableName, connection);
            if (isSuspendSyncAndJournaling) {
               DBQueries.setSyncAndJournaling(connection, this.pragmaSynchronous, this.pragmaJournalMode);
            }
         }

         var11 = rowsModified > 0;
      } finally {
         objectSet.close();
      }

      return var11;
   }

   static Iterable rowIterable(final Iterable objects, final SimpleAttribute primaryKeyAttribute, final Attribute indexAttribute, final QueryOptions queryOptions) {
      return new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               final Iterator objectIterator = objects.iterator();
               Iterator valuesIterator = null;
               Object currentObjectKey;
               DBQueries.Row next;

               protected DBQueries.Row computeNext() {
                  while(true) {
                     if (this.computeNextOrNull()) {
                        if (this.next == null) {
                           continue;
                        }

                        return this.next;
                     }

                     return (DBQueries.Row)this.endOfData();
                  }
               }

               boolean computeNextOrNull() {
                  if (this.valuesIterator == null || !this.valuesIterator.hasNext()) {
                     if (!this.objectIterator.hasNext()) {
                        return false;
                     }

                     Object next = this.objectIterator.next();
                     this.currentObjectKey = primaryKeyAttribute.getValue(next, queryOptions);
                     this.valuesIterator = indexAttribute.getValues(next, queryOptions).iterator();
                  }

                  if (this.valuesIterator.hasNext()) {
                     this.next = new DBQueries.Row(this.currentObjectKey, this.valuesIterator.next());
                     return true;
                  } else {
                     this.next = null;
                     return true;
                  }
               }
            };
         }
      };
   }

   public boolean removeAll(ObjectSet objectSet, QueryOptions queryOptions) {
      boolean var8;
      try {
         ConnectionManager connectionManager = this.getConnectionManager(queryOptions);
         if (!connectionManager.isApplyUpdateForIndexEnabled(this)) {
            boolean var12 = false;
            return var12;
         }

         Connection connection = connectionManager.getConnection(this, queryOptions);
         boolean isBulkImport = queryOptions.get(SQLiteIndexFlags.BulkImportExternallyManged.class) != null || FlagsEnabled.isFlagEnabled(queryOptions, SQLiteIndexFlags.BULK_IMPORT);
         if (isBulkImport) {
            DBQueries.createIndexTable(this.tableName, this.primaryKeyAttribute.getAttributeType(), this.getAttribute().getAttributeType(), connection);
         } else {
            this.createTableIndexIfNeeded(connection);
         }

         Iterable objectKeys = objectKeyIterable(objectSet, this.primaryKeyAttribute, queryOptions);
         if (this.canModifySyncAndJournaling) {
            DBQueries.setSyncAndJournaling(connection, this.pragmaSynchronous, this.pragmaJournalMode);
         }

         int rowsModified = DBQueries.bulkRemove(objectKeys, this.tableName, connection);
         var8 = rowsModified > 0;
      } finally {
         objectSet.close();
      }

      return var8;
   }

   static Iterable objectKeyIterable(final Iterable objects, final SimpleAttribute primaryKeyAttribute, final QueryOptions queryOptions) {
      return new Iterable() {
         public Iterator iterator() {
            return new UnmodifiableIterator() {
               final Iterator iterator = objects.iterator();

               public boolean hasNext() {
                  return this.iterator.hasNext();
               }

               public Object next() {
                  Object next = this.iterator.next();
                  return primaryKeyAttribute.getValue(next, queryOptions);
               }
            };
         }
      };
   }

   public void clear(QueryOptions queryOptions) {
      ConnectionManager connectionManager = this.getConnectionManager(queryOptions);
      if (connectionManager.isApplyUpdateForIndexEnabled(this)) {
         Connection connection = connectionManager.getConnection(this, queryOptions);
         this.createTableIndexIfNeeded(connection);
         DBQueries.clearIndexTable(this.tableName, connection);
      }
   }

   public void init(ObjectStore objectStore, QueryOptions queryOptions) {
      ConnectionManager connectionManager = this.getConnectionManager(queryOptions);
      Connection connection = connectionManager.getConnection(this, queryOptions);
      this.pragmaJournalMode = DBQueries.getPragmaJournalModeOrNull(connection);
      this.pragmaSynchronous = DBQueries.getPragmaSynchronousOrNull(connection);
      this.canModifySyncAndJournaling = this.pragmaJournalMode != null && this.pragmaSynchronous != null;
      this.doAddAll(ObjectSet.fromObjectStore(objectStore, queryOptions), queryOptions, true);
   }

   void createTableIndexIfNeeded(Connection connection) {
      DBQueries.createIndexTable(this.tableName, this.primaryKeyAttribute.getAttributeType(), this.getAttribute().getAttributeType(), connection);
      DBQueries.createIndexOnTable(this.tableName, connection);
   }

   ConnectionManager getConnectionManager(QueryOptions queryOptions) {
      ConnectionManager connectionManager = (ConnectionManager)queryOptions.get(ConnectionManager.class);
      if (connectionManager == null) {
         throw new IllegalStateException("A ConnectionManager is required but was not provided in the QueryOptions.");
      } else {
         return connectionManager;
      }
   }

   public CloseableIterable getDistinctKeys(QueryOptions queryOptions) {
      return this.getDistinctKeys((Comparable)null, true, (Comparable)null, true, queryOptions);
   }

   public CloseableIterable getDistinctKeys(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.getDistinctKeysInRange(lowerBound, lowerInclusive, upperBound, upperInclusive, false, queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(QueryOptions queryOptions) {
      return this.getDistinctKeysDescending((Comparable)null, true, (Comparable)null, true, queryOptions);
   }

   public CloseableIterable getDistinctKeysDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.getDistinctKeysInRange(lowerBound, lowerInclusive, upperBound, upperInclusive, true, queryOptions);
   }

   public Integer getCountOfDistinctKeys(QueryOptions queryOptions) {
      ConnectionManager connectionManager = this.getConnectionManager(queryOptions);
      Connection connection = connectionManager.getConnection(this, queryOptions);
      return DBQueries.getCountOfDistinctKeys(this.tableName, connection);
   }

   public CloseableIterable getStatisticsForDistinctKeysDescending(QueryOptions queryOptions) {
      return this.getStatisticsForDistinctKeys(queryOptions, true);
   }

   public CloseableIterable getStatisticsForDistinctKeys(QueryOptions queryOptions) {
      return this.getStatisticsForDistinctKeys(queryOptions, false);
   }

   CloseableIterable getStatisticsForDistinctKeys(final QueryOptions queryOptions, final boolean sortByKeyDescending) {
      final CloseableRequestResources.CloseableResourceGroup closeableResourceGroup = CloseableRequestResources.forQueryOptions(queryOptions).addGroup();
      return new CloseableIterable() {
         public CloseableIterator iterator() {
            ConnectionManager connectionManager = SQLiteIndex.this.getConnectionManager(queryOptions);
            Connection connection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
            final java.sql.ResultSet resultSet = DBQueries.getDistinctKeysAndCounts(sortByKeyDescending, SQLiteIndex.this.tableName, connection);
            closeableResourceGroup.add(DBUtils.wrapAsCloseable(resultSet));
            return new LazyCloseableIterator() {
               protected KeyStatistics computeNext() {
                  try {
                     if (!resultSet.next()) {
                        this.close();
                        return (KeyStatistics)this.endOfData();
                     } else {
                        Comparable key = (Comparable)DBUtils.getValueFromResultSet(1, resultSet, SQLiteIndex.this.attribute.getAttributeType());
                        Integer count = (Integer)DBUtils.getValueFromResultSet(2, resultSet, Integer.class);
                        return new KeyStatistics(key, count);
                     }
                  } catch (Exception var3) {
                     this.endOfData();
                     this.close();
                     throw new IllegalStateException("Unable to retrieve the ResultSet item.", var3);
                  }
               }

               public void close() {
                  closeableResourceGroup.close();
               }
            };
         }
      };
   }

   CloseableIterable getDistinctKeysInRange(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, final boolean descending, final QueryOptions queryOptions) {
      final Query query = this.getKeyRangeRestriction(lowerBound, lowerInclusive, upperBound, upperInclusive);
      final CloseableRequestResources.CloseableResourceGroup closeableResourceGroup = CloseableRequestResources.forQueryOptions(queryOptions).addGroup();
      return new CloseableIterable() {
         public CloseableIterator iterator() {
            ConnectionManager connectionManager = SQLiteIndex.this.getConnectionManager(queryOptions);
            Connection searchConnection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
            final java.sql.ResultSet searchResultSet = DBQueries.getDistinctKeys(query, descending, SQLiteIndex.this.tableName, searchConnection);
            closeableResourceGroup.add(DBUtils.wrapAsCloseable(searchResultSet));
            return new LazyCloseableIterator() {
               protected Comparable computeNext() {
                  try {
                     if (!searchResultSet.next()) {
                        this.close();
                        return (Comparable)this.endOfData();
                     } else {
                        return (Comparable)DBUtils.getValueFromResultSet(1, searchResultSet, SQLiteIndex.this.attribute.getAttributeType());
                     }
                  } catch (Exception var2) {
                     this.endOfData();
                     this.close();
                     throw new IllegalStateException("Unable to retrieve the ResultSet item.", var2);
                  }
               }

               public void close() {
                  closeableResourceGroup.close();
               }
            };
         }
      };
   }

   public CloseableIterable getKeysAndValues(QueryOptions queryOptions) {
      return this.getKeysAndValues((Comparable)null, true, (Comparable)null, true, queryOptions);
   }

   public CloseableIterable getKeysAndValues(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.getKeysAndValuesInRange(lowerBound, lowerInclusive, upperBound, upperInclusive, false, queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(QueryOptions queryOptions) {
      return this.getKeysAndValuesDescending((Comparable)null, true, (Comparable)null, true, queryOptions);
   }

   public CloseableIterable getKeysAndValuesDescending(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, QueryOptions queryOptions) {
      return this.getKeysAndValuesInRange(lowerBound, lowerInclusive, upperBound, upperInclusive, true, queryOptions);
   }

   CloseableIterable getKeysAndValuesInRange(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive, final boolean descending, final QueryOptions queryOptions) {
      final Query query = this.getKeyRangeRestriction(lowerBound, lowerInclusive, upperBound, upperInclusive);
      final CloseableRequestResources.CloseableResourceGroup closeableResourceGroup = CloseableRequestResources.forQueryOptions(queryOptions).addGroup();
      return new CloseableIterable() {
         public CloseableIterator iterator() {
            ConnectionManager connectionManager = SQLiteIndex.this.getConnectionManager(queryOptions);
            Connection searchConnection = connectionManager.getConnection(SQLiteIndex.this, queryOptions);
            final java.sql.ResultSet searchResultSet = DBQueries.getKeysAndValues(query, descending, SQLiteIndex.this.tableName, searchConnection);
            closeableResourceGroup.add(DBUtils.wrapAsCloseable(searchResultSet));
            return new LazyCloseableIterator() {
               protected KeyValue computeNext() {
                  try {
                     if (!searchResultSet.next()) {
                        this.close();
                        return (KeyValue)this.endOfData();
                     } else {
                        Object objectKey = DBUtils.getValueFromResultSet(1, searchResultSet, SQLiteIndex.this.primaryKeyAttribute.getAttributeType());
                        Comparable objectValue = (Comparable)DBUtils.getValueFromResultSet(2, searchResultSet, SQLiteIndex.this.attribute.getAttributeType());
                        Object object = SQLiteIndex.this.foreignKeyAttribute.getValue(objectKey, queryOptions);
                        return new KeyValueMaterialized(objectValue, object);
                     }
                  } catch (Exception var4) {
                     this.endOfData();
                     this.close();
                     throw new IllegalStateException("Unable to retrieve the ResultSet item.", var4);
                  }
               }

               public void close() {
                  closeableResourceGroup.close();
               }
            };
         }
      };
   }

   Query getKeyRangeRestriction(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, boolean upperInclusive) {
      Object query;
      if (lowerBound != null && upperBound != null) {
         query = QueryFactory.between(this.attribute, lowerBound, lowerInclusive, upperBound, upperInclusive);
      } else if (lowerBound != null) {
         query = lowerInclusive ? QueryFactory.greaterThanOrEqualTo(this.attribute, lowerBound) : QueryFactory.greaterThan(this.attribute, lowerBound);
      } else if (upperBound != null) {
         query = upperInclusive ? QueryFactory.lessThanOrEqualTo(this.attribute, upperBound) : QueryFactory.lessThan(this.attribute, upperBound);
      } else {
         query = QueryFactory.has(this.attribute);
      }

      return (Query)query;
   }

   public Integer getCountForKey(Comparable key, QueryOptions queryOptions) {
      return this.retrieve(QueryFactory.equal(this.attribute, key), queryOptions).size();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SQLiteIndex that = (SQLiteIndex)o;
         return this.attribute.equals(that.attribute);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.getClass().hashCode();
      result = 31 * result + this.attribute.hashCode();
      return result;
   }

   public static SQLiteIndex onAttribute(Attribute attribute, SimpleAttribute objectKeyAttribute, SimpleAttribute foreignKeyAttribute) {
      return new SQLiteIndex(attribute, objectKeyAttribute, foreignKeyAttribute, "");
   }
}
