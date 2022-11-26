package weblogic.jdbc.vendor.oracle;

import java.sql.Array;
import java.sql.SQLException;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;

/** @deprecated */
@Deprecated
public interface OracleArray extends Array {
   ArrayDescriptor getDescriptor() throws SQLException;

   Datum[] getOracleArray() throws SQLException;

   Datum[] getOracleArray(long var1, int var3) throws SQLException;

   String getSQLTypeName() throws SQLException;

   OracleConnection getConnection() throws SQLException;

   oracle.jdbc.OracleConnection getOracleConnection() throws SQLException;

   int length() throws SQLException;

   double[] getDoubleArray() throws SQLException;

   double[] getDoubleArray(long var1, int var3) throws SQLException;

   float[] getFloatArray() throws SQLException;

   float[] getFloatArray(long var1, int var3) throws SQLException;

   int[] getIntArray() throws SQLException;

   int[] getIntArray(long var1, int var3) throws SQLException;

   long[] getLongArray() throws SQLException;

   long[] getLongArray(long var1, int var3) throws SQLException;

   short[] getShortArray() throws SQLException;

   short[] getShortArray(long var1, int var3) throws SQLException;

   void setAutoBuffering(boolean var1) throws SQLException;

   void setAutoIndexing(boolean var1) throws SQLException;

   boolean getAutoBuffering() throws SQLException;

   boolean getAutoIndexing() throws SQLException;

   void setAutoIndexing(boolean var1, int var2) throws SQLException;
}
