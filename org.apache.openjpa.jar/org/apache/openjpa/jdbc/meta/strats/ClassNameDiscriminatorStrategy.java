package org.apache.openjpa.jdbc.meta.strats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class ClassNameDiscriminatorStrategy extends InValueDiscriminatorStrategy {
   private static final Localizer _loc = Localizer.forPackage(ClassNameDiscriminatorStrategy.class);
   public static final String ALIAS = "class-name";

   public String getAlias() {
      return "class-name";
   }

   protected int getJavaType() {
      return 9;
   }

   protected Object getDiscriminatorValue(ClassMapping cls) {
      return cls.getDescribedType().getName();
   }

   protected Class getClass(Object val, JDBCStore store) throws ClassNotFoundException {
      ClassLoader loader = this.getClassLoader(store);
      return Class.forName((String)val, true, loader);
   }

   public void loadSubclasses(JDBCStore store) throws SQLException, ClassNotFoundException {
      if (this.isFinal) {
         this.disc.setSubclassesLoaded(true);
      } else {
         Column col = this.disc.getColumns()[0];
         DBDictionary dict = store.getDBDictionary();
         SQLBuffer select = dict.toSelect((new SQLBuffer(dict)).append(col), store.getFetchConfiguration(), (new SQLBuffer(dict)).append(col.getTable()), (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, (SQLBuffer)null, true, false, 0L, Long.MAX_VALUE);
         Log log = this.disc.getMappingRepository().getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("load-subs", (Object)col.getTable().getFullName()));
         }

         ClassLoader loader = this.getClassLoader(store);
         Connection conn = store.getConnection();
         PreparedStatement stmnt = null;
         ResultSet rs = null;

         try {
            stmnt = select.prepareStatement(conn);
            rs = stmnt.executeQuery();

            while(rs.next()) {
               String className = dict.getString(rs, 1);
               if (StringUtils.isEmpty(className)) {
                  throw new ClassNotFoundException(_loc.get("no-class-name", this.disc.getClassMapping(), col).getMessage());
               }

               Class.forName(className, true, loader);
            }

            this.disc.setSubclassesLoaded(true);
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var22) {
               }
            }

            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var21) {
               }
            }

            try {
               conn.close();
            } catch (SQLException var20) {
            }

         }
      }
   }

   private ClassLoader getClassLoader(JDBCStore store) {
      return store.getConfiguration().getClassResolverInstance().getClassLoader(this.disc.getClassMapping().getDescribedType(), store.getContext().getClassLoader());
   }
}
