package org.apache.openjpa.persistence.jdbc;

import java.util.Collection;
import javax.persistence.LockModeType;
import org.apache.openjpa.jdbc.kernel.DelegatingJDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.kernel.DelegatingFetchConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.persistence.FetchPlanImpl;
import org.apache.openjpa.persistence.PersistenceExceptions;

public class JDBCFetchPlanImpl extends FetchPlanImpl implements JDBCFetchPlan {
   private DelegatingJDBCFetchConfiguration _fetch;

   public JDBCFetchPlanImpl(FetchConfiguration fetch) {
      super(fetch);
   }

   protected DelegatingFetchConfiguration newDelegatingFetchConfiguration(FetchConfiguration fetch) {
      this._fetch = new DelegatingJDBCFetchConfiguration((JDBCFetchConfiguration)fetch, PersistenceExceptions.TRANSLATOR);
      return this._fetch;
   }

   public FetchMode getEagerFetchMode() {
      return FetchMode.fromKernelConstant(this._fetch.getEagerFetchMode());
   }

   public JDBCFetchPlanImpl setEagerFetchMode(FetchMode mode) {
      this._fetch.setEagerFetchMode(mode.toKernelConstant());
      return this;
   }

   public JDBCFetchPlan setEagerFetchMode(int mode) {
      this._fetch.setEagerFetchMode(mode);
      return this;
   }

   public FetchMode getSubclassFetchMode() {
      return FetchMode.fromKernelConstant(this._fetch.getSubclassFetchMode());
   }

   public JDBCFetchPlanImpl setSubclassFetchMode(FetchMode mode) {
      this._fetch.setSubclassFetchMode(mode.toKernelConstant());
      return this;
   }

   public JDBCFetchPlan setSubclassFetchMode(int mode) {
      this._fetch.setSubclassFetchMode(mode);
      return this;
   }

   public ResultSetType getResultSetType() {
      return ResultSetType.fromKernelConstant(this._fetch.getResultSetType());
   }

   public JDBCFetchPlanImpl setResultSetType(ResultSetType type) {
      this._fetch.setResultSetType(type.toKernelConstant());
      return this;
   }

   public JDBCFetchPlan setResultSetType(int mode) {
      this._fetch.setResultSetType(mode);
      return this;
   }

   public FetchDirection getFetchDirection() {
      return FetchDirection.fromKernelConstant(this._fetch.getFetchDirection());
   }

   public JDBCFetchPlanImpl setFetchDirection(FetchDirection direction) {
      this._fetch.setFetchDirection(direction.toKernelConstant());
      return this;
   }

   public JDBCFetchPlan setFetchDirection(int direction) {
      this._fetch.setFetchDirection(direction);
      return this;
   }

   public LRSSizeAlgorithm getLRSSizeAlgorithm() {
      return LRSSizeAlgorithm.fromKernelConstant(this._fetch.getLRSSize());
   }

   public JDBCFetchPlanImpl setLRSSizeAlgorithm(LRSSizeAlgorithm lrsSizeAlgorithm) {
      this._fetch.setLRSSize(lrsSizeAlgorithm.toKernelConstant());
      return this;
   }

   public int getLRSSize() {
      return this._fetch.getLRSSize();
   }

   public JDBCFetchPlan setLRSSize(int lrsSizeMode) {
      this._fetch.setLRSSize(lrsSizeMode);
      return this;
   }

   public JoinSyntax getJoinSyntax() {
      return JoinSyntax.fromKernelConstant(this._fetch.getJoinSyntax());
   }

   public JDBCFetchPlanImpl setJoinSyntax(JoinSyntax syntax) {
      this._fetch.setJoinSyntax(syntax.toKernelConstant());
      return this;
   }

   public JDBCFetchPlan setJoinSyntax(int syntax) {
      this._fetch.setJoinSyntax(syntax);
      return this;
   }

   public IsolationLevel getIsolation() {
      return IsolationLevel.fromConnectionConstant(this._fetch.getIsolation());
   }

   public JDBCFetchPlan setIsolation(IsolationLevel level) {
      this._fetch.setIsolation(level.getConnectionConstant());
      return this;
   }

   public JDBCFetchPlan addFetchGroup(String group) {
      return (JDBCFetchPlan)super.addFetchGroup(group);
   }

   public JDBCFetchPlan addFetchGroups(Collection groups) {
      return (JDBCFetchPlan)super.addFetchGroups(groups);
   }

   public JDBCFetchPlan addFetchGroups(String... groups) {
      return (JDBCFetchPlan)super.addFetchGroups(groups);
   }

   public JDBCFetchPlan addField(Class cls, String field) {
      return (JDBCFetchPlan)super.addField(cls, field);
   }

   public JDBCFetchPlan addField(String field) {
      return (JDBCFetchPlan)super.addField(field);
   }

   public JDBCFetchPlan addFields(Class cls, Collection fields) {
      return (JDBCFetchPlan)super.addFields(cls, fields);
   }

   public JDBCFetchPlan addFields(Class cls, String... fields) {
      return (JDBCFetchPlan)super.addFields(cls, fields);
   }

   public JDBCFetchPlan addFields(Collection fields) {
      return (JDBCFetchPlan)super.addFields(fields);
   }

   public JDBCFetchPlan addFields(String... fields) {
      return (JDBCFetchPlan)super.addFields(fields);
   }

   public JDBCFetchPlan clearFetchGroups() {
      return (JDBCFetchPlan)super.clearFetchGroups();
   }

   public JDBCFetchPlan clearFields() {
      return (JDBCFetchPlan)super.clearFields();
   }

   public JDBCFetchPlan removeFetchGroup(String group) {
      return (JDBCFetchPlan)super.removeFetchGroup(group);
   }

   public JDBCFetchPlan removeFetchGroups(Collection groups) {
      return (JDBCFetchPlan)super.removeFetchGroups(groups);
   }

   public JDBCFetchPlan removeFetchGroups(String... groups) {
      return (JDBCFetchPlan)super.removeFetchGroups(groups);
   }

   public JDBCFetchPlan removeField(Class cls, String field) {
      return (JDBCFetchPlan)super.removeField(cls, field);
   }

   public JDBCFetchPlan removeField(String field) {
      return (JDBCFetchPlan)super.removeField(field);
   }

   public JDBCFetchPlan removeFields(Class cls, Collection fields) {
      return (JDBCFetchPlan)super.removeFields(cls, fields);
   }

   public JDBCFetchPlan removeFields(Class cls, String... fields) {
      return (JDBCFetchPlan)super.removeFields(cls, fields);
   }

   public JDBCFetchPlan removeFields(Collection fields) {
      return (JDBCFetchPlan)super.removeFields(fields);
   }

   public JDBCFetchPlan removeFields(String... fields) {
      return (JDBCFetchPlan)super.removeFields(fields);
   }

   public JDBCFetchPlan resetFetchGroups() {
      return (JDBCFetchPlan)super.resetFetchGroups();
   }

   public JDBCFetchPlan setQueryResultCacheEnabled(boolean cache) {
      return (JDBCFetchPlan)super.setQueryResultCacheEnabled(cache);
   }

   public JDBCFetchPlan setFetchBatchSize(int fetchBatchSize) {
      return (JDBCFetchPlan)super.setFetchBatchSize(fetchBatchSize);
   }

   public JDBCFetchPlan setLockTimeout(int timeout) {
      return (JDBCFetchPlan)super.setLockTimeout(timeout);
   }

   public JDBCFetchPlan setMaxFetchDepth(int depth) {
      return (JDBCFetchPlan)super.setMaxFetchDepth(depth);
   }

   public JDBCFetchPlan setReadLockMode(LockModeType mode) {
      return (JDBCFetchPlan)super.setReadLockMode(mode);
   }

   public JDBCFetchPlan setWriteLockMode(LockModeType mode) {
      return (JDBCFetchPlan)super.setWriteLockMode(mode);
   }
}
