package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public abstract class MapTableFieldStrategy extends ContainerFieldStrategy implements LRSMapFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(MapTableFieldStrategy.class);

   public FieldMapping getFieldMapping() {
      return this.field;
   }

   public ClassMapping[] getIndependentKeyMappings(boolean traverse) {
      return traverse ? this.field.getKeyMapping().getIndependentTypeMappings() : ClassMapping.EMPTY_MAPPINGS;
   }

   public ClassMapping[] getIndependentValueMappings(boolean traverse) {
      return traverse ? this.field.getElementMapping().getIndependentTypeMappings() : ClassMapping.EMPTY_MAPPINGS;
   }

   public ForeignKey getJoinForeignKey(ClassMapping cls) {
      return this.field.getJoinForeignKey();
   }

   public Object deriveKey(JDBCStore store, Object value) {
      return null;
   }

   public Object deriveValue(JDBCStore store, Object key) {
      return null;
   }

   public Joins joinKeyRelation(Joins joins, ClassMapping key) {
      return this.joinKeyRelation(joins, false, false);
   }

   public Joins joinValueRelation(Joins joins, ClassMapping val) {
      return this.joinRelation(joins, false, false);
   }

   public void map(boolean adapt) {
      if (this.field.getTypeCode() != 13) {
         throw new MetaDataException(_loc.get("not-map", (Object)this.field));
      } else if (this.field.getKey().getValueMappedBy() != null) {
         throw new MetaDataException(_loc.get("mapped-by-key", (Object)this.field));
      } else {
         this.field.getValueInfo().assertNoSchemaComponents(this.field, !adapt);
      }
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getAllRows(this.field.getTable(), 2);
      row.whereForeignKey(this.field.getJoinForeignKey(), sm);
      rm.flushAllRows(row);
   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return 0;
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      if (this.field.isLRS()) {
         sm.storeObjectField(this.field.getIndex(), new LRSProxyMap(this));
      } else {
         Joins[] joins = new Joins[2];
         Result[] res = this.getResults(sm, store, fetch, 2, joins, false);

         try {
            Map map = (Map)sm.newProxy(this.field.getIndex());

            while(res[0].next() && (res[1] == res[0] || res[1].next())) {
               Object key = this.loadKey(sm, store, fetch, res[0], joins[0]);
               Object val = this.loadValue(sm, store, fetch, res[1], joins[1]);
               map.put(key, val);
            }

            sm.storeObject(this.field.getIndex(), map);
         } finally {
            res[0].close();
            if (res[1] != res[0]) {
               res[1].close();
            }

         }
      }
   }

   public Object loadKeyProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return this.loadKey((OpenJPAStateManager)null, store, fetch, res, joins);
   }

   public Object loadProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return this.loadValue((OpenJPAStateManager)null, store, fetch, res, joins);
   }

   public Joins join(Joins joins, boolean forceOuter) {
      return this.field.join(joins, forceOuter, true);
   }

   public Joins joinKey(Joins joins, boolean forceOuter) {
      return this.field.join(joins, forceOuter, true);
   }

   protected ForeignKey getJoinForeignKey() {
      return this.field.getJoinForeignKey();
   }

   protected ClassMapping[] getIndependentElementMappings(boolean traverse) {
      return ClassMapping.EMPTY_MAPPINGS;
   }
}
