package org.apache.openjpa.jdbc.meta.strats;

import java.lang.reflect.Modifier;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.DiscriminatorStrategy;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public abstract class AbstractDiscriminatorStrategy extends AbstractStrategy implements DiscriminatorStrategy {
   private static final Localizer _loc = Localizer.forPackage(AbstractDiscriminatorStrategy.class);
   protected Discriminator disc = null;
   protected boolean isFinal = false;

   public void setDiscriminator(Discriminator owner) {
      this.disc = owner;
      ClassMapping cls = this.disc.getClassMapping();
      this.isFinal = Modifier.isFinal(cls.getDescribedType().getModifiers());
   }

   public boolean select(Select sel, ClassMapping mapping) {
      return false;
   }

   public void loadSubclasses(JDBCStore store) throws SQLException, ClassNotFoundException {
      if (!this.isFinal) {
         Log log = this.disc.getMappingRepository().getLog();
         if (log.isWarnEnabled()) {
            log.warn(_loc.get("cant-init-subs", (Object)this.disc.getClassMapping()));
         }
      }

      this.disc.setSubclassesLoaded(true);
   }

   public Class getClass(JDBCStore store, ClassMapping base, Result result) throws SQLException, ClassNotFoundException {
      return base.getDescribedType();
   }

   public boolean hasClassConditions(ClassMapping base, boolean subs) {
      return false;
   }

   public SQLBuffer getClassConditions(Select sel, Joins joins, ClassMapping base, boolean subs) {
      return null;
   }
}
