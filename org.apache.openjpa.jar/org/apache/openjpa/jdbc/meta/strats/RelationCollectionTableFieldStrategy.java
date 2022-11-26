package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxy;

public class RelationCollectionTableFieldStrategy extends RelationToManyTableFieldStrategy implements LRSCollectionFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(RelationCollectionTableFieldStrategy.class);

   public FieldMapping getFieldMapping() {
      return this.field;
   }

   public ClassMapping[] getIndependentElementMappings(boolean traverse) {
      return super.getIndependentElementMappings(traverse);
   }

   public Column[] getElementColumns(ClassMapping elem) {
      return this.field.getElementMapping().getColumns();
   }

   public ForeignKey getJoinForeignKey(ClassMapping elem) {
      return super.getJoinForeignKey(elem);
   }

   public void selectElement(Select sel, ClassMapping elem, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode, Joins joins) {
      super.selectElement(sel, elem, store, fetch, eagerMode, joins);
   }

   public Object loadElement(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return super.loadElement(sm, store, fetch, res, joins);
   }

   public Joins join(Joins joins, ClassMapping elem) {
      return super.join(joins, elem);
   }

   public Joins joinElementRelation(Joins joins, ClassMapping elem) {
      return super.joinElementRelation(joins, elem);
   }

   protected Proxy newLRSProxy() {
      return new LRSProxyCollection(this);
   }

   public void map(boolean adapt) {
      if (this.field.getTypeCode() != 12 && this.field.getTypeCode() != 11) {
         throw new MetaDataException(_loc.get("not-coll", (Object)this.field));
      } else {
         super.map(adapt);
      }
   }
}
