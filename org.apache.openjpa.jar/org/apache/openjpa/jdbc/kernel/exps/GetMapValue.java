package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.strats.ContainerFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.LRSMapFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.RelationStrategies;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.meta.ClassMetaData;

class GetMapValue extends AbstractVal {
   private final Val _map;
   private final Val _key;
   private final String _alias;
   private ClassMetaData _meta = null;
   private Class _cast = null;

   public GetMapValue(Val map, Val key, String alias) {
      this._map = map;
      this._key = key;
      this._alias = alias;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public boolean isVariable() {
      return false;
   }

   public Class getType() {
      return this._cast != null ? this._cast : this._map.getType();
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      ExpState mapState = this._map.initialize(sel, ctx, 0);
      ExpState keyState = this._key.initialize(sel, ctx, 0);
      return new GetMapValueExpState(sel.and(mapState.joins, keyState.joins), mapState, keyState);
   }

   public Object toDataStoreValue(Select sel, ExpContext ctx, ExpState state, Object val) {
      GetMapValueExpState gstate = (GetMapValueExpState)state;
      return this._map.toDataStoreValue(sel, ctx, gstate.mapState, val);
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state).append(" AS ").append(this._alias), (Object)this);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      GetMapValueExpState gstate = (GetMapValueExpState)state;
      this._map.selectColumns(sel, ctx, gstate.mapState, true);
      this._key.selectColumns(sel, ctx, gstate.keyState, true);
   }

   public void groupBy(Select sel, ExpContext ctx, ExpState state) {
      sel.groupBy(this.newSQLBuffer(sel, ctx, state));
   }

   public void orderBy(Select sel, ExpContext ctx, ExpState state, boolean asc) {
      sel.orderBy(this._alias, asc, false);
   }

   private SQLBuffer newSQLBuffer(Select sel, ExpContext ctx, ExpState state) {
      this.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      SQLBuffer buf = new SQLBuffer(ctx.store.getDBDictionary());
      this.appendTo(sel, ctx, state, buf, 0);
      return buf;
   }

   public Object load(ExpContext ctx, ExpState state, Result res) throws SQLException {
      return Filters.convert(res.getObject(this, 1012, (Object)null), this.getType());
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      GetMapValueExpState gstate = (GetMapValueExpState)state;
      this._map.calculateValue(sel, ctx, gstate.mapState, (Val)null, (ExpState)null);
      this._key.calculateValue(sel, ctx, gstate.keyState, (Val)null, (ExpState)null);
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      if (!(this._map instanceof PCPath)) {
         throw new UnsupportedOperationException();
      } else if (!(this._key instanceof Const)) {
         throw new UnsupportedOperationException();
      } else {
         GetMapValueExpState gstate = (GetMapValueExpState)state;
         PCPath map = (PCPath)this._map;
         Object key = ((Const)this._key).getValue(ctx, gstate.keyState);
         FieldMapping field = map.getFieldMapping(gstate.mapState);
         if (!(field.getStrategy() instanceof LRSMapFieldStrategy)) {
            throw new UnsupportedOperationException();
         } else {
            LRSMapFieldStrategy strat = (LRSMapFieldStrategy)field.getStrategy();
            ClassMapping[] clss = strat.getIndependentValueMappings(true);
            if (clss != null && clss.length > 1) {
               throw RelationStrategies.unjoinable(field);
            } else {
               ClassMapping cls = clss.length == 0 ? null : clss[0];
               ForeignKey fk = strat.getJoinForeignKey(cls);
               sql.append("(SELECT ");
               Column[] values = field.getElementMapping().getColumns();

               for(int i = 0; i < values.length; ++i) {
                  if (i > 0) {
                     sql.append(", ");
                  }

                  sql.append(values[i].getTable()).append(".").append(values[i]);
               }

               sql.append(" FROM ").append(values[0].getTable());
               sql.append(" WHERE ");
               ContainerFieldStrategy.appendUnaliasedJoin(sql, sel, (Joins)null, ctx.store.getDBDictionary(), field, fk);
               sql.append(" AND ");
               key = strat.toKeyDataStoreValue(key, ctx.store);
               Column[] cols = strat.getKeyColumns(cls);
               Object[] vals = cols.length == 1 ? null : (Object[])((Object[])key);

               for(int i = 0; i < cols.length; ++i) {
                  sql.append(cols[i].getTable()).append(".").append(cols[i]);
                  if (vals == null) {
                     sql.append(key == null ? " IS " : " = ").appendValue(key, cols[i]);
                  } else {
                     sql.append(vals[i] == null ? " IS " : " = ").appendValue(vals[i], cols[i]);
                  }
               }

               sql.append(")");
            }
         }
      }
   }

   private static class GetMapValueExpState extends ExpState {
      public final ExpState mapState;
      public final ExpState keyState;

      public GetMapValueExpState(Joins joins, ExpState mapState, ExpState keyState) {
         super(joins);
         this.mapState = mapState;
         this.keyState = keyState;
      }
   }
}
