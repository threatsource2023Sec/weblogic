package org.apache.openjpa.slice.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class DistributedConnection implements Connection {
   private final List real;
   private final Connection master;

   public DistributedConnection(List connections) {
      if (connections != null && !connections.isEmpty()) {
         this.real = connections;
         this.master = (Connection)connections.get(0);
      } else {
         throw new NullPointerException();
      }
   }

   public boolean contains(Connection c) {
      return this.real.contains(c);
   }

   public void clearWarnings() throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.clearWarnings();
      }

   }

   public void close() throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.close();
      }

   }

   public void commit() throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.commit();
      }

   }

   public Statement createStatement() throws SQLException {
      DistributedStatement ret = new DistributedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.createStatement());
      }

      return ret;
   }

   public Statement createStatement(int arg0, int arg1) throws SQLException {
      DistributedStatement ret = new DistributedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.createStatement(arg0, arg1));
      }

      return ret;
   }

   public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
      DistributedStatement ret = new DistributedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.createStatement(arg0, arg1, arg2));
      }

      return ret;
   }

   public boolean getAutoCommit() throws SQLException {
      return this.master.getAutoCommit();
   }

   public String getCatalog() throws SQLException {
      return this.master.getCatalog();
   }

   public int getHoldability() throws SQLException {
      return this.master.getHoldability();
   }

   public DatabaseMetaData getMetaData() throws SQLException {
      return this.master.getMetaData();
   }

   public int getTransactionIsolation() throws SQLException {
      return this.master.getTransactionIsolation();
   }

   public Map getTypeMap() throws SQLException {
      return this.master.getTypeMap();
   }

   public SQLWarning getWarnings() throws SQLException {
      return this.master.getWarnings();
   }

   public boolean isClosed() throws SQLException {
      boolean ret = true;

      Connection c;
      for(Iterator i$ = this.real.iterator(); i$.hasNext(); ret &= c.isClosed()) {
         c = (Connection)i$.next();
      }

      return ret;
   }

   public boolean isReadOnly() throws SQLException {
      boolean ret = true;

      Connection c;
      for(Iterator i$ = this.real.iterator(); i$.hasNext(); ret &= c.isReadOnly()) {
         c = (Connection)i$.next();
      }

      return ret;
   }

   public String nativeSQL(String arg0) throws SQLException {
      return this.master.nativeSQL(arg0);
   }

   public CallableStatement prepareCall(String arg0) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public PreparedStatement prepareStatement(String arg0) throws SQLException {
      if (arg0.startsWith("SELECT SEQUENCE_VALUE FROM OPENJPA_SEQUENCE_TABLE")) {
         return this.master.prepareStatement(arg0);
      } else {
         DistributedPreparedStatement ret = new DistributedPreparedStatement(this);
         Iterator i$ = this.real.iterator();

         while(i$.hasNext()) {
            Connection c = (Connection)i$.next();
            ret.add(c.prepareStatement(arg0));
         }

         return ret;
      }
   }

   public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
      DistributedPreparedStatement ret = new DistributedPreparedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.prepareStatement(arg0, arg1));
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
      DistributedPreparedStatement ret = new DistributedPreparedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.prepareStatement(arg0, arg1));
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
      DistributedPreparedStatement ret = new DistributedPreparedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.prepareStatement(arg0, arg1));
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
      DistributedPreparedStatement ret = new DistributedPreparedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.prepareStatement(arg0, arg1, arg2));
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
      DistributedPreparedStatement ret = new DistributedPreparedStatement(this);
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         ret.add(c.prepareStatement(arg0, arg1, arg2));
      }

      return ret;
   }

   public void releaseSavepoint(Savepoint arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.releaseSavepoint(arg0);
      }

   }

   public void rollback() throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.rollback();
      }

   }

   public void rollback(Savepoint arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.rollback(arg0);
      }

   }

   public void setAutoCommit(boolean arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.setAutoCommit(arg0);
      }

   }

   public void setCatalog(String arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.setCatalog(arg0);
      }

   }

   public void setHoldability(int arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.setHoldability(arg0);
      }

   }

   public void setReadOnly(boolean arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.setReadOnly(arg0);
      }

   }

   public Savepoint setSavepoint() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Savepoint setSavepoint(String arg0) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setTransactionIsolation(int arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.setTransactionIsolation(arg0);
      }

   }

   public void setTypeMap(Map arg0) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         Connection c = (Connection)i$.next();
         c.setTypeMap(arg0);
      }

   }
}
