package kodo.persistence;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.TemporalType;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.persistence.OpenJPAQuerySPI;
import org.apache.openjpa.persistence.QueryOperationType;

/** @deprecated */
class KodoQueryImpl implements KodoQuery {
   private final OpenJPAQuerySPI _delegate;

   public KodoQueryImpl(OpenJPAQuerySPI del) {
      this._delegate = del;
   }

   public KodoEntityManager getEntityManager() {
      return KodoPersistence.cast((EntityManager)this._delegate.getEntityManager());
   }

   public String getLanguage() {
      return this._delegate.getLanguage();
   }

   public QueryOperationType getOperation() {
      return this._delegate.getOperation();
   }

   public FetchPlan getFetchPlan() {
      return new FetchPlanImpl((org.apache.openjpa.persistence.FetchPlanImpl)this._delegate.getFetchPlan());
   }

   public String getQueryString() {
      return this._delegate.getQueryString();
   }

   public boolean getIgnoreChanges() {
      return this._delegate.getIgnoreChanges();
   }

   public KodoQuery setIgnoreChanges(boolean ignore) {
      this._delegate.setIgnoreChanges(ignore);
      return this;
   }

   public KodoQuery addFilterListener(FilterListener listener) {
      this._delegate.addFilterListener(listener);
      return this;
   }

   public KodoQuery removeFilterListener(FilterListener listener) {
      this._delegate.removeFilterListener(listener);
      return this;
   }

   public KodoQuery addAggregateListener(AggregateListener listener) {
      this._delegate.addAggregateListener(listener);
      return this;
   }

   public KodoQuery removeAggregateListener(AggregateListener listener) {
      this._delegate.removeAggregateListener(listener);
      return this;
   }

   public Collection getCandidateCollection() {
      return this._delegate.getCandidateCollection();
   }

   public KodoQuery setCandidateCollection(Collection coll) {
      this._delegate.setCandidateCollection(coll);
      return this;
   }

   public Class getResultClass() {
      return this._delegate.getResultClass();
   }

   public KodoQuery setResultClass(Class type) {
      this._delegate.setResultClass(type);
      return this;
   }

   public boolean hasSubclasses() {
      return this._delegate.hasSubclasses();
   }

   public KodoQuery setSubclasses(boolean subs) {
      this._delegate.setSubclasses(subs);
      return this;
   }

   public int getFirstResult() {
      return this._delegate.getFirstResult();
   }

   public int getMaxResults() {
      return this._delegate.getMaxResults();
   }

   public KodoQuery compile() {
      this._delegate.compile();
      return this;
   }

   public boolean hasPositionalParameters() {
      return this._delegate.hasPositionalParameters();
   }

   public Object[] getPositionalParameters() {
      return this._delegate.getPositionalParameters();
   }

   public Map getNamedParameters() {
      return this._delegate.getNamedParameters();
   }

   public KodoQuery setParameters(Map params) {
      this._delegate.setParameters(params);
      return this;
   }

   public KodoQuery setParameters(Object... params) {
      this._delegate.setParameters(params);
      return this;
   }

   public KodoQuery closeAll() {
      this._delegate.closeAll();
      return this;
   }

   public String[] getDataStoreActions(Map params) {
      return this._delegate.getDataStoreActions(params);
   }

   public KodoQuery setMaxResults(int maxResult) {
      this._delegate.setMaxResults(maxResult);
      return this;
   }

   public KodoQuery setFirstResult(int startPosition) {
      this._delegate.setFirstResult(startPosition);
      return this;
   }

   public KodoQuery setHint(String hintName, Object value) {
      this._delegate.setHint(hintName, value);
      return this;
   }

   public KodoQuery setParameter(String name, Object value) {
      this._delegate.setParameter(name, value);
      return this;
   }

   public KodoQuery setParameter(String name, Date value, TemporalType temporalType) {
      this._delegate.setParameter(name, value, temporalType);
      return this;
   }

   public KodoQuery setParameter(String name, Calendar value, TemporalType temporalType) {
      this._delegate.setParameter(name, value, temporalType);
      return this;
   }

   public KodoQuery setParameter(int position, Object value) {
      this._delegate.setParameter(position, value);
      return this;
   }

   public KodoQuery setParameter(int position, Date value, TemporalType temporalType) {
      this._delegate.setParameter(position, value, temporalType);
      return this;
   }

   public KodoQuery setParameter(int position, Calendar value, TemporalType temporalType) {
      this._delegate.setParameter(position, value, temporalType);
      return this;
   }

   public KodoQuery setFlushMode(FlushModeType flushMode) {
      this._delegate.setFlushMode(flushMode);
      return this;
   }

   public FlushModeType getFlushMode() {
      return this._delegate.getFlushMode();
   }

   public List getResultList() {
      return this._delegate.getResultList();
   }

   public Object getSingleResult() {
      return this._delegate.getSingleResult();
   }

   public int executeUpdate() {
      return this._delegate.executeUpdate();
   }
}
