package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

public abstract class ContainerFieldStrategy extends AbstractFieldStrategy {
   protected abstract ClassMapping[] getIndependentElementMappings(boolean var1);

   public void appendIsEmpty(SQLBuffer sql, Select sel, Joins joins) {
      this.testEmpty(sql, sel, joins, true);
   }

   public void appendIsNotEmpty(SQLBuffer sql, Select sel, Joins joins) {
      this.testEmpty(sql, sel, joins, false);
   }

   public void appendIsNull(SQLBuffer sql, Select sel, Joins joins) {
      this.testEmpty(sql, sel, joins, true);
   }

   public void appendIsNotNull(SQLBuffer sql, Select sel, Joins joins) {
      this.testEmpty(sql, sel, joins, false);
   }

   private void testEmpty(SQLBuffer sql, Select sel, Joins joins, boolean empty) {
      if (empty) {
         sql.append("0 = ");
      } else {
         sql.append("0 < ");
      }

      this.appendSize(sql, sel, joins);
   }

   protected abstract ForeignKey getJoinForeignKey();

   public void appendSize(SQLBuffer sql, Select sel, Joins joins) {
      DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
      dict.assertSupport(dict.supportsSubselect, "SupportsSubselect");
      ClassMapping[] ind = this.getIndependentElementMappings(false);
      if (ind != null && ind.length > 1) {
         throw RelationStrategies.unjoinable(this.field);
      } else {
         ForeignKey fk = this.getJoinForeignKey();
         appendJoinCount(sql, sel, joins, dict, this.field, fk);
      }
   }

   protected static void appendJoinCount(SQLBuffer sql, Select sel, Joins joins, DBDictionary dict, FieldMapping field, ForeignKey fk) {
      String fullTable = dict.getFullName(fk.getTable(), false);
      sql.append("(SELECT COUNT(*) FROM ").append(fullTable).append(" WHERE ");
      appendUnaliasedJoin(sql, sel, joins, dict, field, fk);
      sql.append(")");
   }

   public static void appendUnaliasedJoin(SQLBuffer sql, Select sel, Joins joins, DBDictionary dict, FieldMapping field, ForeignKey fk) {
      String fullTable = dict.getFullName(fk.getTable(), false);
      Column[] cols = fk.getColumns();
      Column[] pks = fk.getPrimaryKeyColumns();
      int count = 0;

      int i;
      for(i = 0; i < cols.length; ++count) {
         if (count > 0) {
            sql.append(" AND ");
         }

         sql.append(fullTable).append(".").append(cols[i]).append(" = ").append(sel.getColumnAlias(pks[i], joins));
         ++i;
      }

      cols = fk.getConstantColumns();

      for(i = 0; i < cols.length; ++count) {
         if (count > 0) {
            sql.append(" AND ");
         }

         sql.append(fullTable).append(".").append(cols[i]).append(" = ").appendValue(fk.getConstant(cols[i]), cols[i]);
         ++i;
      }

      pks = fk.getConstantPrimaryKeyColumns();

      for(i = 0; i < pks.length; ++count) {
         if (count > 0) {
            sql.append(" AND ");
         }

         sql.append(sel.getColumnAlias(pks[i], joins)).append(" = ").appendValue(fk.getPrimaryKeyConstant(pks[i]), pks[i]);
         ++i;
      }

   }
}
