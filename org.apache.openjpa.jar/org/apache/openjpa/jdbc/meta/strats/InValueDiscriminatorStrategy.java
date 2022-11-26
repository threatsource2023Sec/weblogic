package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.DiscriminatorMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public abstract class InValueDiscriminatorStrategy extends AbstractDiscriminatorStrategy {
   private static final Localizer _loc = Localizer.forPackage(InValueDiscriminatorStrategy.class);

   protected abstract int getJavaType();

   protected abstract Object getDiscriminatorValue(ClassMapping var1);

   protected abstract Class getClass(Object var1, JDBCStore var2) throws ClassNotFoundException;

   public void map(boolean adapt) {
      ClassMapping cls = this.disc.getClassMapping();
      if (cls.getJoinablePCSuperclassMapping() == null && cls.getEmbeddingMetaData() == null) {
         DiscriminatorMappingInfo info = this.disc.getMappingInfo();
         info.assertNoJoin(this.disc, true);
         info.assertNoForeignKey(this.disc, !adapt);
         info.assertNoUnique(this.disc, false);
         Column tmplate = new Column();
         tmplate.setJavaType(this.getJavaType());
         tmplate.setName("typ");
         Column[] cols = info.getColumns(this.disc, new Column[]{tmplate}, adapt);
         this.disc.setColumns(cols);
         this.disc.setColumnIO(info.getColumnIO());
         Index idx = info.getIndex(this.disc, cols, adapt);
         this.disc.setIndex(idx);
      } else {
         throw new MetaDataException(_loc.get("not-base-disc", (Object)cls));
      }
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.disc.getClassMapping().getTable(), 1, sm, true);
      Object cls = this.getDiscriminatorValue((ClassMapping)sm.getMetaData());
      if (this.disc.getColumnIO().isInsertable(0, cls == null)) {
         row.setObject(this.disc.getColumns()[0], cls);
      }

   }

   public boolean select(Select sel, ClassMapping mapping) {
      if (this.isFinal) {
         return false;
      } else {
         sel.select(this.disc.getColumns());
         return true;
      }
   }

   public Class getClass(JDBCStore store, ClassMapping base, Result res) throws SQLException, ClassNotFoundException {
      if (!this.isFinal && res.contains(this.disc.getColumns()[0]) && (base.getPCSuperclass() != null || base.getJoinablePCSubclassMappings().length != 0)) {
         Object cls = res.getObject(this.disc.getColumns()[0], this.disc.getJavaType(), (Object)null);
         return this.getClass(cls, store);
      } else {
         return base.getDescribedType();
      }
   }

   public boolean hasClassConditions(ClassMapping base, boolean subclasses) {
      if (!this.isFinal && (base.getJoinablePCSuperclassMapping() != null || !subclasses)) {
         ClassMapping[] subs = base.getJoinablePCSubclassMappings();
         return subs.length != 0 || base.getJoinablePCSuperclassMapping() != null;
      } else {
         return false;
      }
   }

   public SQLBuffer getClassConditions(Select sel, Joins joins, ClassMapping base, boolean subclasses) {
      Column col = this.disc.getColumns()[0];
      SQLBuffer sql = new SQLBuffer(sel.getConfiguration().getDBDictionaryInstance());
      boolean outer = joins != null && joins.isOuter();
      if (outer) {
         sql.append("(");
      }

      String alias = sel.getColumnAlias(col, joins);
      sql.append(alias);
      ClassMapping[] subs = base.getJoinablePCSubclassMappings();
      if (!outer && (!subclasses || subs.length == 0)) {
         return sql.append(" = ").appendValue(this.getDiscriminatorValue(base), col);
      } else {
         if (outer) {
            sql.append(" IS ").appendValue((Object)null).append(" OR ").append(alias);
         }

         sql.append(" IN (");
         sql.appendValue(this.getDiscriminatorValue(base), col);

         for(int i = 0; i < subs.length; ++i) {
            sql.append(", ").appendValue(this.getDiscriminatorValue(subs[i]), col);
         }

         sql.append(")");
         if (outer) {
            sql.append(")");
         }

         return sql;
      }
   }
}
