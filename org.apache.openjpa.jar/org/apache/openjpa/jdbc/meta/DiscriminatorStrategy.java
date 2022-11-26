package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

public interface DiscriminatorStrategy extends Strategy {
   void setDiscriminator(Discriminator var1);

   boolean select(Select var1, ClassMapping var2);

   void loadSubclasses(JDBCStore var1) throws SQLException, ClassNotFoundException;

   Class getClass(JDBCStore var1, ClassMapping var2, Result var3) throws SQLException, ClassNotFoundException;

   boolean hasClassConditions(ClassMapping var1, boolean var2);

   SQLBuffer getClassConditions(Select var1, Joins var2, ClassMapping var3, boolean var4);
}
