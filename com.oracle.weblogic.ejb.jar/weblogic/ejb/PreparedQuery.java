package weblogic.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.FinderException;

public interface PreparedQuery extends QueryProperties {
   Collection find() throws FinderException;

   ResultSet execute() throws FinderException;

   String getEjbql();

   void setString(int var1, String var2);

   void setBigDecimal(int var1, BigDecimal var2);

   void setBigInteger(int var1, BigInteger var2);

   void setBinary(int var1, byte[] var2);

   void setBoolean(int var1, boolean var2);

   void setByte(int var1, byte var2);

   void setCharacter(int var1, char var2);

   void setShort(int var1, short var2);

   void setInt(int var1, int var2);

   void setLong(int var1, long var2);

   void setFloat(int var1, float var2);

   void setDouble(int var1, double var2);

   void setDate(int var1, Date var2);

   void setDate(int var1, java.util.Date var2);

   void setTime(int var1, Time var2);

   void setTime(int var1, java.util.Date var2);

   void setTimestamp(int var1, Timestamp var2);

   void setTimestamp(int var1, java.util.Date var2);

   void setCalender(int var1, Calendar var2);

   void setObject(int var1, Object var2);
}
