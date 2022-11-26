package org.apache.openjpa.slice.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class DistributedTemplate implements Statement, Iterable {
   protected List stmts = new ArrayList();
   protected final DistributedConnection con;
   protected Statement master;

   public DistributedTemplate(DistributedConnection c) {
      this.con = c;
   }

   public Iterator iterator() {
      return this.stmts.iterator();
   }

   public void add(Statement s) {
      if (this.stmts.isEmpty()) {
         this.master = s;
      }

      try {
         if (!this.con.contains(s.getConnection())) {
            throw new IllegalArgumentException(s + " has different connection");
         }

         this.stmts.add(s);
      } catch (SQLException var3) {
         var3.printStackTrace();
      }

   }

   public void addBatch(String sql) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.addBatch(sql);
      }

   }

   public void cancel() throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.cancel();
      }

   }

   public void clearBatch() throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.clearBatch();
      }

   }

   public void clearWarnings() throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.clearWarnings();
      }

   }

   public void close() throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.close();
      }

   }

   public boolean execute(String arg0) throws SQLException {
      boolean ret = true;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret &= s.execute(arg0)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public boolean execute(String arg0, int arg1) throws SQLException {
      boolean ret = true;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret &= s.execute(arg0, arg1)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public boolean execute(String arg0, int[] arg1) throws SQLException {
      boolean ret = true;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret &= s.execute(arg0, arg1)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public boolean execute(String arg0, String[] arg1) throws SQLException {
      boolean ret = true;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret &= s.execute(arg0, arg1)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public int[] executeBatch() throws SQLException {
      int[] ret = new int[0];
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         int[] tmp = s.executeBatch();
         ret = new int[ret.length + tmp.length];
         System.arraycopy(tmp, 0, ret, ret.length - tmp.length, tmp.length);
      }

      return ret;
   }

   public ResultSet executeQuery() throws SQLException {
      DistributedResultSet rs = new DistributedResultSet();
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         rs.add(s.executeQuery((String)null));
      }

      return rs;
   }

   public ResultSet executeQuery(String arg0) throws SQLException {
      DistributedResultSet rs = new DistributedResultSet();
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         rs.add(s.executeQuery(arg0));
      }

      return rs;
   }

   public int executeUpdate(String arg0) throws SQLException {
      int ret = 0;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret += s.executeUpdate(arg0)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public int executeUpdate(String arg0, int arg1) throws SQLException {
      int ret = 0;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret += s.executeUpdate(arg0, arg1)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public int executeUpdate(String arg0, int[] arg1) throws SQLException {
      int ret = 0;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret += s.executeUpdate(arg0, arg1)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public int executeUpdate(String arg0, String[] arg1) throws SQLException {
      int ret = 0;

      Statement s;
      for(Iterator i$ = this.iterator(); i$.hasNext(); ret += s.executeUpdate(arg0, arg1)) {
         s = (Statement)i$.next();
      }

      return ret;
   }

   public Connection getConnection() throws SQLException {
      return this.con;
   }

   public int getFetchDirection() throws SQLException {
      return this.master.getFetchDirection();
   }

   public int getFetchSize() throws SQLException {
      return this.master.getFetchSize();
   }

   public ResultSet getGeneratedKeys() throws SQLException {
      DistributedResultSet mrs = new DistributedResultSet();
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         mrs.add(s.getGeneratedKeys());
      }

      return mrs;
   }

   public int getMaxFieldSize() throws SQLException {
      return this.master.getMaxFieldSize();
   }

   public int getMaxRows() throws SQLException {
      return this.master.getMaxRows();
   }

   public boolean getMoreResults() throws SQLException {
      Iterator i$ = this.iterator();

      Statement s;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         s = (Statement)i$.next();
      } while(!s.getMoreResults());

      return true;
   }

   public boolean getMoreResults(int arg0) throws SQLException {
      Iterator i$ = this.iterator();

      Statement s;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         s = (Statement)i$.next();
      } while(!s.getMoreResults(arg0));

      return true;
   }

   public int getQueryTimeout() throws SQLException {
      return this.master.getQueryTimeout();
   }

   public ResultSet getResultSet() throws SQLException {
      DistributedResultSet rs = new DistributedResultSet();
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         rs.add(s.getResultSet());
      }

      return rs;
   }

   public int getResultSetConcurrency() throws SQLException {
      return this.master.getResultSetConcurrency();
   }

   public int getResultSetHoldability() throws SQLException {
      return this.master.getResultSetHoldability();
   }

   public int getResultSetType() throws SQLException {
      return this.master.getResultSetType();
   }

   public int getUpdateCount() throws SQLException {
      return this.master.getUpdateCount();
   }

   public SQLWarning getWarnings() throws SQLException {
      return this.master.getWarnings();
   }

   public void setCursorName(String name) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.setCursorName(name);
      }

   }

   public void setEscapeProcessing(boolean flag) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.setEscapeProcessing(flag);
      }

   }

   public void setFetchDirection(int dir) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.setFetchDirection(dir);
      }

   }

   public void setFetchSize(int size) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.setFetchSize(size);
      }

   }

   public void setMaxFieldSize(int size) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.setMaxFieldSize(size);
      }

   }

   public void setMaxRows(int n) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.setMaxFieldSize(n);
      }

   }

   public void setQueryTimeout(int n) throws SQLException {
      Iterator i$ = this.iterator();

      while(i$.hasNext()) {
         Statement s = (Statement)i$.next();
         s.setMaxFieldSize(n);
      }

   }
}
