package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxy;

public class RelationMapTableFieldStrategy extends RelationToManyTableFieldStrategy implements LRSMapFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(RelationMapTableFieldStrategy.class);

   public FieldMapping getFieldMapping() {
      return this.field;
   }

   public ClassMapping[] getIndependentKeyMappings(boolean traverse) {
      return ClassMapping.EMPTY_MAPPINGS;
   }

   public ClassMapping[] getIndependentValueMappings(boolean traverse) {
      return this.getIndependentElementMappings(traverse);
   }

   public ForeignKey getJoinForeignKey(ClassMapping cls) {
      return super.getJoinForeignKey(cls);
   }

   public Column[] getKeyColumns(ClassMapping cls) {
      return cls.getFieldMapping(this.field.getKey().getValueMappedByMetaData().getIndex()).getColumns();
   }

   public Column[] getValueColumns(ClassMapping cls) {
      return this.field.getElementMapping().getColumns();
   }

   public void selectKey(Select sel, ClassMapping key, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      throw new InternalException();
   }

   public Object loadKey(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      throw new InternalException();
   }

   public Object deriveKey(JDBCStore store, Object value) {
      OpenJPAStateManager sm = RelationStrategies.getStateManager(value, store.getContext());
      return sm == null ? null : sm.fetchField(this.field.getKey().getValueMappedByMetaData().getIndex(), false);
   }

   public Object deriveValue(JDBCStore store, Object key) {
      return null;
   }

   public void selectValue(Select sel, ClassMapping val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      this.selectElement(sel, val, store, fetch, 0, joins);
   }

   public Object loadValue(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return this.loadElement(sm, store, fetch, res, joins);
   }

   public Result[] getResults(final OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch, final int eagerMode, final Joins[] joins, boolean lrs) throws SQLException {
      ValueMapping val = this.field.getElementMapping();
      final ClassMapping[] vals = val.getIndependentTypeMappings();
      Union union = store.getSQLFactory().newUnion(vals.length);
      if (fetch.getSubclassFetchMode(val.getTypeMapping()) != 1) {
         union.abortUnion();
      }

      union.setLRS(lrs);
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            joins[1] = RelationMapTableFieldStrategy.this.selectAll(sel, vals[idx], sm, store, fetch, eagerMode);
         }
      });
      Result res = union.execute(store, fetch);
      return new Result[]{res, res};
   }

   public Joins joinKeyRelation(Joins joins, ClassMapping key) {
      return joins;
   }

   public Joins joinValueRelation(Joins joins, ClassMapping val) {
      return this.joinElementRelation(joins, val);
   }

   protected Proxy newLRSProxy() {
      return new LRSProxyMap(this);
   }

   protected void add(JDBCStore store, Object coll, Object obj) {
      if (obj != null) {
         ((Map)coll).put(this.deriveKey(store, obj), obj);
      }

   }

   protected Collection toCollection(Object val) {
      return val == null ? null : ((Map)val).values();
   }

   public void map(boolean adapt) {
      if (this.field.getTypeCode() != 13) {
         throw new MetaDataException(_loc.get("not-map", (Object)this.field));
      } else if (this.field.getKey().getValueMappedBy() == null) {
         throw new MetaDataException(_loc.get("not-mapped-by-key", (Object)this.field));
      } else {
         super.map(adapt);
      }
   }

   public Joins joinKey(Joins joins, boolean forceOuter) {
      return this.joinRelation(this.join(joins, forceOuter), forceOuter, false);
   }
}
