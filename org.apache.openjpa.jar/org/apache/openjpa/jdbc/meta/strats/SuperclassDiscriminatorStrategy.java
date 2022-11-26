package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

public class SuperclassDiscriminatorStrategy extends AbstractDiscriminatorStrategy {
   public void map(boolean adapt) {
      for(ClassMapping sup = this.disc.getClassMapping().getJoinablePCSuperclassMapping(); sup != null; sup = sup.getJoinablePCSuperclassMapping()) {
         if (sup.getDiscriminator().getValue() != null || sup.getDiscriminator().getStrategy() instanceof ValueMapDiscriminatorStrategy) {
            this.disc.setValue(this.disc.getMappingInfo().getValue(this.disc, adapt));
            break;
         }
      }

   }

   public void loadSubclasses(JDBCStore store) throws SQLException, ClassNotFoundException {
      this.disc.getClassMapping().getPCSuperclassMapping().getDiscriminator().loadSubclasses(store);
      this.disc.setSubclassesLoaded(true);
   }

   public Class getClass(JDBCStore store, ClassMapping base, Result res) throws SQLException, ClassNotFoundException {
      return this.disc.getClassMapping().getPCSuperclassMapping().getDiscriminator().getClass(store, base, res);
   }

   public boolean hasClassConditions(ClassMapping base, boolean subclasses) {
      return this.disc.getClassMapping().getPCSuperclassMapping().getDiscriminator().hasClassConditions(base, subclasses);
   }

   public SQLBuffer getClassConditions(Select sel, Joins joins, ClassMapping base, boolean subclasses) {
      return this.disc.getClassMapping().getPCSuperclassMapping().getDiscriminator().getClassConditions(sel, joins, base, subclasses);
   }
}
