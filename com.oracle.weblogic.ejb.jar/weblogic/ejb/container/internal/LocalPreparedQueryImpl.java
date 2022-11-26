package weblogic.ejb.container.internal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.ejb.FinderException;
import weblogic.ejb.PreparedQuery;
import weblogic.ejb.container.interfaces.LocalQueryHandler;
import weblogic.ejb20.internal.WLQueryPropertiesImpl;
import weblogic.utils.StackTraceUtils;

public class LocalPreparedQueryImpl extends WLQueryPropertiesImpl implements PreparedQuery {
   private LocalQueryHandler handler;
   private String sql;
   private String ejbql;
   private Map arguments = new TreeMap();
   private Map flattenedArguments = new TreeMap();

   public LocalPreparedQueryImpl(String ejbql, LocalQueryHandler handler, Properties props) throws FinderException {
      this.ejbql = ejbql;
      this.handler = handler;
      this.setProperties(props);
   }

   public String getEjbql() {
      return this.ejbql;
   }

   public Collection find() throws FinderException {
      try {
         Object[] returnArray = (Object[])((Object[])this.handler.executePreparedQuery(this.sql, this, this.arguments, this.flattenedArguments, false));
         this.sql = (String)returnArray[0];
         this.flattenedArguments = (Map)returnArray[1];
         this.arguments.clear();
         return (Collection)returnArray[2];
      } catch (FinderException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw new FinderException(StackTraceUtils.throwable2StackTrace(var3));
      }
   }

   public ResultSet execute() throws FinderException {
      try {
         Object[] returnArray = (Object[])((Object[])this.handler.executePreparedQuery(this.sql, this, this.arguments, this.flattenedArguments, true));
         this.sql = (String)returnArray[0];
         this.flattenedArguments = (Map)returnArray[1];
         this.arguments.clear();
         return (ResultSet)returnArray[2];
      } catch (FinderException var2) {
         throw var2;
      } catch (Throwable var3) {
         throw new FinderException(StackTraceUtils.throwable2StackTrace(var3));
      }
   }

   public void setString(int index, String value) {
      this.setParameter(index, value);
   }

   public void setBigDecimal(int index, BigDecimal value) {
      this.setParameter(index, value);
   }

   public void setBigInteger(int index, BigInteger value) {
      this.setParameter(index, value);
   }

   public void setBinary(int index, byte[] value) {
      this.setParameter(index, value);
   }

   public void setBoolean(int index, boolean value) {
      this.setParameter(index, new Boolean(value));
   }

   public void setByte(int index, byte value) {
      this.setParameter(index, new Byte(value));
   }

   public void setCharacter(int index, char value) {
      this.setParameter(index, new Character(value));
   }

   public void setShort(int index, short value) {
      this.setParameter(index, new Short(value));
   }

   public void setInt(int index, int value) {
      this.setParameter(index, new Integer(value));
   }

   public void setLong(int index, long value) {
      this.setParameter(index, new Long(value));
   }

   public void setFloat(int index, float value) {
      this.setParameter(index, new Float(value));
   }

   public void setDouble(int index, double value) {
      this.setParameter(index, new Double(value));
   }

   public void setDate(int index, Date value) {
      this.setParameter(index, value);
   }

   public void setDate(int index, java.util.Date value) {
      this.setParameter(index, value);
   }

   public void setTime(int index, Time value) {
      this.setParameter(index, value);
   }

   public void setTime(int index, java.util.Date value) {
      this.setParameter(index, value);
   }

   public void setTimestamp(int index, Timestamp value) {
      this.setParameter(index, value);
   }

   public void setTimestamp(int index, java.util.Date value) {
      this.setParameter(index, value);
   }

   public void setCalender(int index, Calendar value) {
      this.setParameter(index, value);
   }

   public void setObject(int index, Object value) {
      this.setParameter(index, value);
   }

   private void setParameter(int index, Object value) {
      Integer key = new Integer(index);
      Object previous = this.arguments.put(key, value);
      if (previous != null) {
         this.arguments.put(key, previous);
         throw new RuntimeException("Attempt to set more than one value for query parameter '" + key + "'.  Previous value was '" + previous + "'.");
      }
   }

   public String toString() {
      return "PreparedQueryImpl: [ \nejbql: " + this.ejbql + "\nsql: " + this.sql + "\narguments: " + this.arguments + "\nflattenedArguments: " + this.flattenedArguments + "\n]";
   }
}
