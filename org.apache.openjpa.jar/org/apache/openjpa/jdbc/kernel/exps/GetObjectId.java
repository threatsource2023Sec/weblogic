package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;

class GetObjectId extends AbstractVal {
   private static final Localizer _loc = Localizer.forPackage(GetObjectId.class);
   private final PCPath _path;
   private ClassMetaData _meta = null;

   public GetObjectId(PCPath path) {
      this._path = path;
   }

   public Column[] getColumns(ExpState state) {
      return this._path.getClassMapping(state).getPrimaryKeyColumns();
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public Class getType() {
      return Object.class;
   }

   public void setImplicitType(Class type) {
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      ExpState state = this._path.initialize(sel, ctx, 4);
      ClassMapping cls = this._path.getClassMapping(state);
      if (cls != null && cls.getEmbeddingMapping() == null) {
         return state;
      } else {
         throw new UserException(_loc.get("bad-getobjectid", (Object)this._path.getFieldMapping(state)));
      }
   }

   public Object toDataStoreValue(Select sel, ExpContext ctx, ExpState state, Object val) {
      ClassMapping mapping = this._path.getClassMapping(state);
      if (mapping.getIdentityType() == 1) {
         return val instanceof Id ? Numbers.valueOf(((Id)val).getId()) : Filters.convert(val, Long.TYPE);
      } else if (mapping.getIdentityType() == 0) {
         return val instanceof OpenJPAId ? ((OpenJPAId)val).getIdObject() : val;
      } else {
         Object[] pks = ApplicationIds.toPKValues(val, mapping);
         if (pks.length == 1) {
            return pks[0];
         } else if (val == null) {
            return pks;
         } else {
            while(!mapping.isPrimaryKeyObjectId(false)) {
               mapping = mapping.getJoinablePCSuperclassMapping();
            }

            Column[] cols = mapping.getPrimaryKeyColumns();
            Object[] vals = new Object[cols.length];

            for(int i = 0; i < cols.length; ++i) {
               Joinable join = mapping.assertJoinable(cols[i]);
               vals[i] = pks[mapping.getField(join.getFieldIndex()).getPrimaryKeyIndex()];
               vals[i] = join.getJoinValue(vals[i], cols[i], ctx.store);
            }

            return vals;
         }
      }
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this.selectColumns(sel, ctx, state, true);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this._path.selectColumns(sel, ctx, state, true);
   }

   public void groupBy(Select sel, ExpContext ctx, ExpState state) {
      this._path.groupBy(sel, ctx, state);
   }

   public void orderBy(Select sel, ExpContext ctx, ExpState state, boolean asc) {
      this._path.orderBy(sel, ctx, state, asc);
   }

   public Object load(ExpContext ctx, ExpState state, Result res) throws SQLException {
      return this._path.load(ctx, state, res, true);
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      this._path.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return this._path.length(sel, ctx, state);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      this._path.appendTo(sel, ctx, state, sql, index);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._path.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}
