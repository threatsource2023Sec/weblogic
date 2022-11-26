package weblogic.persistence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.transaction.Transaction;
import weblogic.transaction.TransactionHelper;

public final class QueryProxyImpl implements TypedQuery {
   private final TransactionalEntityManagerProxyImpl emProxy;
   private final Method createQueryMethod;
   private final Object[] createQueryArgs;
   private final Map hints = new LinkedHashMap();
   private final Map params = new HashMap();
   private int firstResult = 0;
   private int maxResults = Integer.MAX_VALUE;
   private FlushModeType flushMode = null;
   private LockModeType lockMode = null;

   public QueryProxyImpl(TransactionalEntityManagerProxyImpl emProxy, Method createQueryMethod, Object[] createQueryArgs) {
      this.emProxy = emProxy;
      this.createQueryMethod = createQueryMethod;
      this.createQueryArgs = createQueryArgs;
   }

   private Query instantiateQuery(EntityManager em) {
      try {
         return (Query)this.createQueryMethod.invoke(em, this.createQueryArgs);
      } catch (Exception var5) {
         Throwable cause = var5;
         if (var5 instanceof InvocationTargetException) {
            InvocationTargetException ite = (InvocationTargetException)var5;
            if (ite.getCause() != null) {
               cause = ite.getCause();
            }
         }

         if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else {
            throw new PersistenceException((Throwable)cause);
         }
      }
   }

   private Query createQuery(EntityManager em) {
      Query query = this.instantiateQuery(em);
      if (this.firstResult > 0) {
         query.setFirstResult(this.firstResult);
      }

      if (this.maxResults != Integer.MAX_VALUE) {
         query.setMaxResults(this.maxResults);
      }

      if (this.flushMode != null) {
         query.setFlushMode(this.flushMode);
      }

      if (this.lockMode != null) {
         query.setLockMode(this.lockMode);
      }

      Iterator var3 = this.hints.keySet().iterator();

      while(var3.hasNext()) {
         String hintName = (String)var3.next();
         query.setHint(hintName, this.hints.get(hintName));
      }

      var3 = this.params.keySet().iterator();

      while(var3.hasNext()) {
         Object key = var3.next();
         Object val = this.params.get(key);
         TemporalType temporalType = null;
         if (val instanceof ParameterValue) {
            temporalType = ((ParameterValue)val).temporalType;
            val = ((ParameterValue)val).value;
         }

         if (key instanceof IndexKey) {
            int index = ((IndexKey)key).index;
            if (temporalType == null) {
               query.setParameter(index, val);
            } else if (val instanceof Date) {
               query.setParameter(index, (Date)val, temporalType);
            } else {
               query.setParameter(index, (Calendar)val, temporalType);
            }
         } else if (key instanceof Parameter) {
            if (temporalType == null) {
               query.setParameter((Parameter)key, val);
            } else if (val instanceof Date) {
               query.setParameter((Parameter)key, (Date)val, temporalType);
            } else {
               query.setParameter((Parameter)key, (Calendar)val, temporalType);
            }
         } else if (temporalType == null) {
            query.setParameter((String)key, val);
         } else if (val instanceof Date) {
            query.setParameter((String)key, (Date)val, temporalType);
         } else {
            query.setParameter((String)key, (Calendar)val, temporalType);
         }
      }

      return query;
   }

   public int executeUpdate() {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      int var3;
      try {
         var3 = this.createQuery(em).executeUpdate();
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var3;
   }

   public List getResultList() {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      List var3;
      try {
         var3 = this.createQuery(em).getResultList();
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var3;
   }

   public Object getSingleResult() {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      Object var3;
      try {
         var3 = this.createQuery(em).getSingleResult();
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var3;
   }

   public Parameter getParameter(int position) {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      Parameter var4;
      try {
         var4 = this.instantiateQuery(em).getParameter(position);
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var4;
   }

   public Parameter getParameter(int position, Class type) {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      Parameter var5;
      try {
         var5 = this.instantiateQuery(em).getParameter(position, type);
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var5;
   }

   public Parameter getParameter(String name) {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      Parameter var4;
      try {
         var4 = this.instantiateQuery(em).getParameter(name);
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var4;
   }

   public Parameter getParameter(String name, Class type) {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      Parameter var5;
      try {
         var5 = this.instantiateQuery(em).getParameter(name, type);
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var5;
   }

   public Set getParameters() {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);

      Set var3;
      try {
         var3 = this.instantiateQuery(em).getParameters();
      } finally {
         if (tx == null && em != null) {
            em.close();
         }

      }

      return var3;
   }

   public TypedQuery setFirstResult(int startPosition) {
      this.validatePositiveArgument(startPosition);
      this.firstResult = startPosition;
      return this;
   }

   public int getFirstResult() {
      return this.firstResult;
   }

   public TypedQuery setFlushMode(FlushModeType flushMode) {
      this.flushMode = flushMode;
      return this;
   }

   public FlushModeType getFlushMode() {
      return this.flushMode != null ? this.flushMode : FlushModeType.AUTO;
   }

   public TypedQuery setHint(String hintName, Object value) {
      this.hints.put(hintName, value);
      return this;
   }

   public Map getHints() {
      return Collections.unmodifiableMap(this.hints);
   }

   public TypedQuery setLockMode(LockModeType lockMode) {
      this.lockMode = lockMode;
      return this;
   }

   public LockModeType getLockMode() {
      return this.lockMode;
   }

   public TypedQuery setMaxResults(int maxResult) {
      this.validatePositiveArgument(maxResult);
      this.maxResults = maxResult;
      return this;
   }

   public int getMaxResults() {
      return this.maxResults;
   }

   public TypedQuery setParameter(int position, Calendar value, TemporalType type) {
      this.params.put(new IndexKey(position), new ParameterValue(value, type));
      return this;
   }

   public TypedQuery setParameter(int position, Date value, TemporalType type) {
      this.params.put(new IndexKey(position), new ParameterValue(value, type));
      return this;
   }

   public TypedQuery setParameter(int position, Object value) {
      this.params.put(new IndexKey(position), value);
      return this;
   }

   public TypedQuery setParameter(Parameter param, Calendar value, TemporalType type) {
      this.params.put(param, new ParameterValue(value, type));
      return this;
   }

   public TypedQuery setParameter(Parameter param, Date value, TemporalType type) {
      this.params.put(param, new ParameterValue(value, type));
      return this;
   }

   public TypedQuery setParameter(Parameter param, Object value) {
      this.params.put(param, value);
      return this;
   }

   public TypedQuery setParameter(String name, Calendar value, TemporalType temporalType) {
      this.params.put(name, new ParameterValue(value, temporalType));
      return this;
   }

   public TypedQuery setParameter(String name, Date value, TemporalType temporalType) {
      this.params.put(name, new ParameterValue(value, temporalType));
      return this;
   }

   public TypedQuery setParameter(String name, Object value) {
      this.params.put(name, value);
      return this;
   }

   public boolean isBound(Parameter param) {
      return this.params.containsKey(param);
   }

   public Object getParameterValue(int position) {
      Object val = this.params.get(new IndexKey(position));
      if (val == null) {
         throw new IllegalStateException("No parameter bound to position " + position);
      } else {
         return val instanceof ParameterValue ? ((ParameterValue)val).value : val;
      }
   }

   public Object getParameterValue(Parameter param) {
      Object val = this.params.get(param);
      if (val == null) {
         throw new IllegalStateException("No parameter bound equal to " + param);
      } else {
         return val instanceof ParameterValue ? ((ParameterValue)val).value : val;
      }
   }

   public Object getParameterValue(String name) {
      Object val = this.params.get(name);
      if (val == null) {
         throw new IllegalStateException("No parameter bound with name " + name);
      } else {
         return val instanceof ParameterValue ? ((ParameterValue)val).value : val;
      }
   }

   public Object unwrap(Class cls) {
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      EntityManager em = (EntityManager)this.emProxy.getPersistenceContext(tx);
      return this.createQuery(em).unwrap(cls);
   }

   private void validatePositiveArgument(int arg) {
      if (arg < 0) {
         throw new IllegalArgumentException("Argument is negative: " + arg);
      }
   }

   private static final class IndexKey {
      public int index;

      public IndexKey(int index) {
         this.index = index;
      }

      public int hashCode() {
         return this.index;
      }

      public boolean equals(Object obj) {
         if (obj instanceof IndexKey) {
            return this.index == ((IndexKey)obj).index;
         } else {
            return false;
         }
      }
   }

   private final class ParameterValue {
      public Object value;
      public TemporalType temporalType;

      public ParameterValue(Object value, TemporalType temporalType) {
         this.value = value;
         this.temporalType = temporalType;
      }
   }
}
