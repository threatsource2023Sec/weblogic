package org.apache.openjpa.jdbc.sql;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.exps.Val;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Sequence;
import org.apache.openjpa.jdbc.schema.Table;
import serp.util.Numbers;

public final class SQLBuffer implements Serializable, Cloneable {
   private static final String PARAMETER_TOKEN = "?";
   private final DBDictionary _dict;
   private final StringBuffer _sql = new StringBuffer();
   private List _subsels = null;
   private List _params = null;
   private List _cols = null;

   public SQLBuffer(DBDictionary dict) {
      this._dict = dict;
   }

   public SQLBuffer(SQLBuffer buf) {
      this._dict = buf._dict;
      this.append(buf);
   }

   public Object clone() {
      return new SQLBuffer(this);
   }

   public boolean isEmpty() {
      return this._sql.length() == 0;
   }

   public SQLBuffer append(SQLBuffer buf) {
      this.append(buf, this._sql.length(), this._params == null ? 0 : this._params.size(), true);
      return this;
   }

   private void append(SQLBuffer buf, int sqlIndex, int paramIndex, boolean subsels) {
      if (subsels) {
         if (this._subsels != null && !this._subsels.isEmpty() && sqlIndex != this._sql.length()) {
            throw new IllegalStateException();
         }

         if (buf._subsels != null && !buf._subsels.isEmpty()) {
            if (sqlIndex != this._sql.length()) {
               throw new IllegalStateException();
            }

            if (this._subsels == null) {
               this._subsels = new ArrayList(buf._subsels.size());
            }

            for(int i = 0; i < buf._subsels.size(); ++i) {
               this._subsels.add(((Subselect)buf._subsels.get(i)).clone(sqlIndex, paramIndex));
            }
         }
      }

      if (sqlIndex == this._sql.length()) {
         this._sql.append(buf._sql.toString());
      } else {
         this._sql.insert(sqlIndex, buf._sql.toString());
      }

      if (buf._params != null) {
         if (this._params == null) {
            this._params = new ArrayList();
         }

         if (this._cols == null && buf._cols != null) {
            this._cols = new ArrayList();

            while(this._cols.size() < this._params.size()) {
               this._cols.add((Object)null);
            }
         }

         if (paramIndex == this._params.size()) {
            this._params.addAll(buf._params);
            if (buf._cols != null) {
               this._cols.addAll(buf._cols);
            } else if (this._cols != null) {
               while(this._cols.size() < this._params.size()) {
                  this._cols.add((Object)null);
               }
            }
         } else {
            this._params.addAll(paramIndex, buf._params);
            if (buf._cols != null) {
               this._cols.addAll(paramIndex, buf._cols);
            } else if (this._cols != null) {
               while(this._cols.size() < this._params.size()) {
                  this._cols.add(paramIndex, (Object)null);
               }
            }
         }
      }

   }

   public SQLBuffer append(Table table) {
      this._sql.append(this._dict.getFullName(table, false));
      return this;
   }

   public SQLBuffer append(Sequence seq) {
      this._sql.append(this._dict.getFullName(seq));
      return this;
   }

   public SQLBuffer append(Column col) {
      this._sql.append(col.getName());
      return this;
   }

   public SQLBuffer append(String s) {
      this._sql.append(s);
      return this;
   }

   public SQLBuffer append(Select sel, JDBCFetchConfiguration fetch) {
      return this.append(sel, fetch, false);
   }

   public SQLBuffer appendCount(Select sel, JDBCFetchConfiguration fetch) {
      return this.append(sel, fetch, true);
   }

   private SQLBuffer append(Select sel, JDBCFetchConfiguration fetch, boolean count) {
      this._sql.append("(");
      Subselect sub = new Subselect();
      sub.select = sel;
      sub.fetch = fetch;
      sub.count = count;
      sub.sqlIndex = this._sql.length();
      sub.paramIndex = this._params == null ? 0 : this._params.size();
      this._sql.append(")");
      if (this._subsels == null) {
         this._subsels = new ArrayList(2);
      }

      this._subsels.add(sub);
      return this;
   }

   public boolean replace(Select old, Select sel) {
      if (this._subsels == null) {
         return false;
      } else {
         for(int i = 0; i < this._subsels.size(); ++i) {
            Subselect sub = (Subselect)this._subsels.get(i);
            if (sub.select == old) {
               sub.select = sel;
               return true;
            }
         }

         return false;
      }
   }

   public SQLBuffer appendValue(Object o) {
      return this.appendValue(o, (Column)null);
   }

   public SQLBuffer appendValue(Object o, Column col) {
      if (o == null) {
         this._sql.append("NULL");
      } else if (o instanceof Raw) {
         this._sql.append(o.toString());
      } else {
         this._sql.append("?");
         if (this._params == null) {
            this._params = new ArrayList();
         }

         if (col != null && this._cols == null) {
            this._cols = new ArrayList();

            while(this._cols.size() < this._params.size()) {
               this._cols.add((Object)null);
            }
         }

         this._params.add(o);
         if (this._cols != null) {
            this._cols.add(col);
         }
      }

      return this;
   }

   public SQLBuffer appendValue(boolean b) {
      return this.appendValue(b, (Column)null);
   }

   public SQLBuffer appendValue(boolean b, Column col) {
      return this.appendValue(b ? Boolean.TRUE : Boolean.FALSE, col);
   }

   public SQLBuffer appendValue(byte b) {
      return this.appendValue((byte)b, (Column)null);
   }

   public SQLBuffer appendValue(byte b, Column col) {
      return this.appendValue(new Byte(b), col);
   }

   public SQLBuffer appendValue(char c) {
      return this.appendValue((char)c, (Column)null);
   }

   public SQLBuffer appendValue(char c, Column col) {
      return this.appendValue(new Character(c), col);
   }

   public SQLBuffer appendValue(double d) {
      return this.appendValue(d, (Column)null);
   }

   public SQLBuffer appendValue(double d, Column col) {
      return this.appendValue(new Double(d), col);
   }

   public SQLBuffer appendValue(float f) {
      return this.appendValue(f, (Column)null);
   }

   public SQLBuffer appendValue(float f, Column col) {
      return this.appendValue(new Float(f), col);
   }

   public SQLBuffer appendValue(int i) {
      return this.appendValue((int)i, (Column)null);
   }

   public SQLBuffer appendValue(int i, Column col) {
      return this.appendValue(Numbers.valueOf(i), col);
   }

   public SQLBuffer appendValue(long l) {
      return this.appendValue(l, (Column)null);
   }

   public SQLBuffer appendValue(long l, Column col) {
      return this.appendValue(Numbers.valueOf(l), col);
   }

   public SQLBuffer appendValue(short s) {
      return this.appendValue((short)s, (Column)null);
   }

   public SQLBuffer appendValue(short s, Column col) {
      return this.appendValue(new Short(s), col);
   }

   public List getParameters() {
      return this._params == null ? Collections.EMPTY_LIST : this._params;
   }

   public String getSQL() {
      return this.getSQL(false);
   }

   public String getSQL(boolean replaceParams) {
      this.resolveSubselects();
      String sql = this._sql.toString();
      if (replaceParams && this._params != null && !this._params.isEmpty()) {
         StringBuffer buf = new StringBuffer();
         Iterator pi = this._params.iterator();

         for(int i = 0; i < sql.length(); ++i) {
            if (sql.charAt(i) != '?') {
               buf.append(sql.charAt(i));
            } else {
               Object param = pi.hasNext() ? pi.next() : null;
               if (param == null) {
                  buf.append("NULL");
               } else if (!(param instanceof Number) && !(param instanceof Boolean)) {
                  if (!(param instanceof String) && !(param instanceof Character)) {
                     buf.append("?");
                  } else {
                     buf.append("'").append(param).append("'");
                  }
               } else {
                  buf.append(param);
               }
            }
         }

         return buf.toString();
      } else {
         return sql;
      }
   }

   private void resolveSubselects() {
      if (this._subsels != null && !this._subsels.isEmpty()) {
         for(int i = this._subsels.size() - 1; i >= 0; --i) {
            Subselect sub = (Subselect)this._subsels.get(i);
            SQLBuffer buf;
            if (sub.count) {
               buf = sub.select.toSelectCount();
            } else {
               buf = sub.select.toSelect(false, sub.fetch);
            }

            buf.resolveSubselects();
            this.append(buf, sub.sqlIndex, sub.paramIndex, false);
         }

         this._subsels.clear();
      }
   }

   public PreparedStatement prepareStatement(Connection conn) throws SQLException {
      return this.prepareStatement(conn, 1003, 1007);
   }

   public PreparedStatement prepareStatement(Connection conn, int rsType, int rsConcur) throws SQLException {
      return this.prepareStatement(conn, (JDBCFetchConfiguration)null, rsType, rsConcur);
   }

   public PreparedStatement prepareStatement(Connection conn, JDBCFetchConfiguration fetch, int rsType, int rsConcur) throws SQLException {
      if (rsType == -1 && fetch == null) {
         rsType = 1003;
      } else if (rsType == -1) {
         rsType = fetch.getResultSetType();
      }

      if (rsConcur == -1) {
         rsConcur = 1007;
      }

      PreparedStatement stmnt;
      if (rsType == 1003 && rsConcur == 1007) {
         stmnt = conn.prepareStatement(this.getSQL());
      } else {
         stmnt = conn.prepareStatement(this.getSQL(), rsType, rsConcur);
      }

      try {
         this.setParameters(stmnt);
         if (fetch != null) {
            if (fetch.getFetchBatchSize() > 0) {
               stmnt.setFetchSize(fetch.getFetchBatchSize());
            }

            if (rsType != 1003 && fetch.getFetchDirection() != 1000) {
               stmnt.setFetchDirection(fetch.getFetchDirection());
            }
         }

         return stmnt;
      } catch (SQLException var9) {
         try {
            stmnt.close();
         } catch (SQLException var8) {
         }

         throw var9;
      }
   }

   public CallableStatement prepareCall(Connection conn) throws SQLException {
      return this.prepareCall(conn, 1003, 1007);
   }

   public CallableStatement prepareCall(Connection conn, int rsType, int rsConcur) throws SQLException {
      return this.prepareCall(conn, (JDBCFetchConfiguration)null, rsType, rsConcur);
   }

   public CallableStatement prepareCall(Connection conn, JDBCFetchConfiguration fetch, int rsType, int rsConcur) throws SQLException {
      if (rsType == -1 && fetch == null) {
         rsType = 1003;
      } else if (rsType == -1) {
         rsType = fetch.getResultSetType();
      }

      if (rsConcur == -1) {
         rsConcur = 1007;
      }

      CallableStatement stmnt;
      if (rsType == 1003 && rsConcur == 1007) {
         stmnt = conn.prepareCall(this.getSQL());
      } else {
         stmnt = conn.prepareCall(this.getSQL(), rsType, rsConcur);
      }

      try {
         this.setParameters((PreparedStatement)stmnt);
         if (fetch != null) {
            if (fetch.getFetchBatchSize() > 0) {
               stmnt.setFetchSize(fetch.getFetchBatchSize());
            }

            if (rsType != 1003 && fetch.getFetchDirection() != 1000) {
               stmnt.setFetchDirection(fetch.getFetchDirection());
            }
         }

         return stmnt;
      } catch (SQLException var9) {
         try {
            stmnt.close();
         } catch (SQLException var8) {
         }

         throw var9;
      }
   }

   public void setParameters(PreparedStatement ps) throws SQLException {
      if (this._params != null) {
         for(int i = 0; i < this._params.size(); ++i) {
            Column col = this._cols == null ? null : (Column)this._cols.get(i);
            this._dict.setUnknown(ps, i + 1, this._params.get(i), col);
         }

      }
   }

   public int hashCode() {
      int hash = this._sql.hashCode();
      return this._params == null ? hash : hash ^ this._params.hashCode();
   }

   public boolean sqlEquals(String sql) {
      return this._sql.toString().equals(sql);
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof SQLBuffer)) {
         return false;
      } else {
         SQLBuffer buf = (SQLBuffer)other;
         return this._sql.equals(buf._sql) && ObjectUtils.equals(this._params, buf._params);
      }
   }

   public void addCastForParam(String oper, Val val) {
      if (this._sql.charAt(this._sql.length() - 1) == '?') {
         String castString = this._dict.addCastAsType(oper, val);
         if (castString != null) {
            this._sql.replace(this._sql.length() - 1, this._sql.length(), castString);
         }
      }

   }

   public void replaceSqlString(int start, int end, String newString) {
      this._sql.replace(start, end, newString);
   }

   public void setParameters(List params) {
      this._params = params;
   }

   public List getColumns() {
      return this._cols;
   }

   private static class Subselect {
      public Select select;
      public JDBCFetchConfiguration fetch;
      public boolean count;
      public int sqlIndex;
      public int paramIndex;

      private Subselect() {
      }

      public Subselect clone(int sqlIndex, int paramIndex) {
         if (sqlIndex == 0 && paramIndex == 0) {
            return this;
         } else {
            Subselect sub = new Subselect();
            sub.select = this.select;
            sub.fetch = this.fetch;
            sub.count = this.count;
            sub.sqlIndex = this.sqlIndex + sqlIndex;
            sub.paramIndex = this.paramIndex + paramIndex;
            return sub;
         }
      }

      // $FF: synthetic method
      Subselect(Object x0) {
         this();
      }
   }
}
