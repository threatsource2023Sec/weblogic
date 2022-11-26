package weblogic.ejb.container.cmp.rdbms;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public interface RDBMSM2NSet extends RDBMSSet {
   Object getCreatorPk();

   Set getAddSet();

   void addToAddSet(Object var1);

   String getAddJoinTableSQL();

   void setAddJoinTableSQLParams(PreparedStatement var1, Object var2, Object var3) throws SQLException;
}
