package kodo.jdbc.runtime;

import kodo.runtime.FetchConfiguration;
import kodo.runtime.KodoPersistenceManager;
import org.apache.openjpa.jdbc.kernel.LRSSizes;
import org.apache.openjpa.jdbc.sql.JoinSyntaxes;

/** @deprecated */
public class JDBCFetchConfiguration extends FetchConfiguration implements LRSSizes, JoinSyntaxes {
   public static final int JOIN_SYNTAX_SQL92 = 0;
   public static final int JOIN_SYNTAX_TRADITIONAL = 1;
   public static final int JOIN_SYNTAX_DATABASE = 2;
   private final org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration _fetch;

   public JDBCFetchConfiguration(KodoPersistenceManager pm, org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration fetch) {
      super(pm, fetch);
      this._fetch = fetch;
   }

   public int getEagerFetchMode() {
      return this._fetch.getEagerFetchMode();
   }

   public void setEagerFetchMode(int mode) {
      this._fetch.setEagerFetchMode(mode);
   }

   public int getSubclassFetchMode() {
      return this._fetch.getSubclassFetchMode();
   }

   public void setSubclassFetchMode(int mode) {
      this._fetch.setSubclassFetchMode(mode);
   }

   public int getResultSetType() {
      return this._fetch.getResultSetType();
   }

   public void setResultSetType(int type) {
      this._fetch.setResultSetType(type);
   }

   public int getFetchDirection() {
      return this._fetch.getFetchDirection();
   }

   public void setFetchDirection(int direction) {
      this._fetch.setFetchDirection(direction);
   }

   public int getLRSSize() {
      return this._fetch.getLRSSize();
   }

   public void setLRSSize(int lrsSize) {
      this._fetch.setLRSSize(lrsSize);
   }

   public int getJoinSyntax() {
      return this._fetch.getJoinSyntax();
   }

   public void setJoinSyntax(int syntax) {
      this._fetch.setJoinSyntax(syntax);
   }

   public Object clone() {
      return new JDBCFetchConfiguration(this.getPersistenceManager(), (org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration)this._fetch.clone());
   }
}
