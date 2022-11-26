package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.DiscriminatorMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class SubclassJoinDiscriminatorStrategy extends AbstractDiscriminatorStrategy {
   public static final String ALIAS = "subclass-join";
   private static final Localizer _loc = Localizer.forPackage(SubclassJoinDiscriminatorStrategy.class);

   public String getAlias() {
      return "subclass-join";
   }

   public void map(boolean adapt) {
      ClassMapping cls = this.disc.getClassMapping();
      if (cls.getJoinablePCSuperclassMapping() == null && cls.getEmbeddingMetaData() == null) {
         DiscriminatorMappingInfo info = this.disc.getMappingInfo();
         info.assertNoSchemaComponents(this.disc, true);
         DBDictionary dict = cls.getMappingRepository().getDBDictionary();
         if (dict.joinSyntax == 1) {
            throw new MetaDataException(_loc.get("outer-join-support", (Object)cls));
         }
      } else {
         throw new MetaDataException(_loc.get("not-base-disc", (Object)cls));
      }
   }

   public boolean select(Select sel, ClassMapping mapping) {
      if (this.isFinal) {
         return false;
      } else {
         boolean seld = false;
         if (mapping.getPrimaryKeyColumns().length > 0 && mapping.getJoinablePCSuperclassMapping() != null) {
            sel.select(mapping.getPrimaryKeyColumns()[0]);
            seld = true;
         }

         ClassMapping[] subs = mapping.getJoinablePCSubclassMappings();
         if (subs.length == 0) {
            return seld;
         } else {
            for(int i = 0; i < subs.length; ++i) {
               if (subs[i].getJoinablePCSuperclassMapping() != null) {
                  Column[] pks = subs[i].getPrimaryKeyColumns();
                  if (pks.length > 0) {
                     sel.select(pks[0], subs[i].joinSuperclass(sel.newJoins(), true));
                     seld = true;
                  }
               }
            }

            return seld;
         }
      }
   }

   public Class getClass(JDBCStore store, ClassMapping base, Result res) throws SQLException, ClassNotFoundException {
      if (this.isFinal) {
         return base.getDescribedType();
      } else {
         ClassMapping[] subs = base.getJoinablePCSubclassMappings();
         Class derived = base.getDescribedType();

         for(int i = 0; i < subs.length; ++i) {
            Column[] pks = subs[i].getPrimaryKeyColumns();
            if (pks.length != 0 && derived.isAssignableFrom(subs[i].getDescribedType()) && res.contains(pks[0]) && res.getObject(pks[0], -1, (Object)null) != null) {
               derived = subs[i].getDescribedType();
            }
         }

         return derived;
      }
   }

   public boolean hasClassConditions(ClassMapping base, boolean subclasses) {
      if (!this.isFinal && !subclasses) {
         ClassMapping[] subs = base.getJoinablePCSubclassMappings();
         return subs.length != 0;
      } else {
         return false;
      }
   }

   public SQLBuffer getClassConditions(Select sel, Joins joins, ClassMapping base, boolean subclasses) {
      ClassMapping[] subs = base.getJoinablePCSubclassMappings();
      SQLBuffer buf = null;

      for(int i = 0; i < subs.length; ++i) {
         Column[] pks = subs[i].getPrimaryKeyColumns();
         if (pks.length != 0) {
            if (buf == null) {
               buf = new SQLBuffer(sel.getConfiguration().getDBDictionaryInstance());
               sel.getColumnAlias(base.getPrimaryKeyColumns()[0], joins);
            } else {
               buf.append(" AND ");
            }

            buf.append(sel.getColumnAlias(pks[0], joins)).append(" IS NULL");
         }
      }

      return buf;
   }
}
