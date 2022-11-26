package kodo.jdo.jdbc;

import java.util.Arrays;
import java.util.Collection;
import kodo.jdo.FetchPlanImpl;
import kodo.jdo.JDOExceptions;
import org.apache.openjpa.jdbc.kernel.DelegatingJDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.kernel.DelegatingFetchConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;

public class JDBCFetchPlanImpl extends FetchPlanImpl implements JDBCFetchPlan {
   private DelegatingJDBCFetchConfiguration _fetch;

   public JDBCFetchPlanImpl(FetchConfiguration fetch) {
      super(fetch);
   }

   protected DelegatingFetchConfiguration newDelegatingFetchConfiguration(FetchConfiguration fetch) {
      this._fetch = new DelegatingJDBCFetchConfiguration((JDBCFetchConfiguration)fetch, JDOExceptions.TRANSLATOR);
      return this._fetch;
   }

   public int getEagerFetchMode() {
      return this._fetch.getEagerFetchMode();
   }

   public JDBCFetchPlan setEagerFetchMode(int mode) {
      this._fetch.setEagerFetchMode(mode);
      return this;
   }

   public int getSubclassFetchMode() {
      return this._fetch.getSubclassFetchMode();
   }

   public JDBCFetchPlan setSubclassFetchMode(int mode) {
      this._fetch.setSubclassFetchMode(mode);
      return this;
   }

   public int getResultSetType() {
      return this._fetch.getResultSetType();
   }

   public JDBCFetchPlan setResultSetType(int type) {
      this._fetch.setResultSetType(type);
      return this;
   }

   public int getFetchDirection() {
      return this._fetch.getFetchDirection();
   }

   public JDBCFetchPlan setFetchDirection(int direction) {
      this._fetch.setFetchDirection(direction);
      return this;
   }

   public int getLRSSize() {
      return this._fetch.getLRSSize();
   }

   public JDBCFetchPlan setLRSSize(int lrsSize) {
      this._fetch.setLRSSize(lrsSize);
      return this;
   }

   public int getJoinSyntax() {
      return this._fetch.getJoinSyntax();
   }

   public JDBCFetchPlan setJoinSyntax(int syntax) {
      this._fetch.setJoinSyntax(syntax);
      return this;
   }

   public Collection getJoins() {
      return this._fetch.getJoins();
   }

   public boolean hasJoin(Class cls, String field) {
      return this._fetch.hasJoin(toFieldName(cls, field));
   }

   public JDBCFetchPlan addJoin(String field) {
      this._fetch.addJoin(field);
      return this;
   }

   public JDBCFetchPlan addJoin(Class cls, String field) {
      return this.addJoin(toFieldName(cls, field));
   }

   public JDBCFetchPlan setJoins(String[] fields) {
      return this.setJoins((Collection)Arrays.asList(fields));
   }

   public JDBCFetchPlan setJoins(Class cls, String[] fields) {
      return this.setJoins(cls, (Collection)Arrays.asList(fields));
   }

   public JDBCFetchPlan setJoins(Collection fields) {
      this._fetch.clearJoins();
      this._fetch.addJoins(fields);
      return this;
   }

   public JDBCFetchPlan setJoins(Class cls, Collection fields) {
      return this.setJoins(toFieldNames(cls, fields));
   }

   public JDBCFetchPlan removeJoin(String field) {
      this._fetch.removeJoin(field);
      return this;
   }

   public JDBCFetchPlan removeJoin(Class cls, String field) {
      return this.removeJoin(toFieldName(cls, field));
   }

   public JDBCFetchPlan clearJoins() {
      this._fetch.clearJoins();
      return this;
   }
}
