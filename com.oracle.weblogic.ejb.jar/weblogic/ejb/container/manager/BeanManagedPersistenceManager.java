package weblogic.ejb.container.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import javax.ejb.EntityBean;
import javax.transaction.Transaction;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.RSInfo;

public final class BeanManagedPersistenceManager implements PersistenceManager {
   BaseEntityManager beanManager = null;

   public void setup(BeanManager beanManager) throws InternalException {
      this.beanManager = (BaseEntityManager)beanManager;
   }

   public Object findByPrimaryKey(EntityBean bean, Method finderMethod, Object pk) throws InternalException {
      try {
         return finderMethod.invoke(bean, pk);
      } catch (IllegalAccessException var6) {
         EJBRuntimeUtils.throwInternalException("Exception in ejbFindByPrimaryKey", var6);
         throw new AssertionError("cannot reach");
      } catch (InvocationTargetException var7) {
         Throwable t = var7.getTargetException();
         EJBRuntimeUtils.throwInternalException("Exception in ejbFindByPrimaryKey", t);
         throw new AssertionError("cannot reach");
      }
   }

   public EntityBean findByPrimaryKeyLoadBean(EntityBean bean, Method finderMethod, Object pk) throws InternalException {
      throw new InternalException("NYI");
   }

   public Object scalarFinder(EntityBean bean, Method finderMethod, Object[] args) throws InternalException {
      try {
         Transaction tx = TransactionService.getTransactionManager().getTransaction();
         this.beanManager.flushModifiedBeans(tx);
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InternalException(var9.getMessage());
      }

      try {
         return finderMethod.invoke(bean, args);
      } catch (IllegalAccessException var6) {
         EJBRuntimeUtils.throwInternalException("Exception in " + finderMethod.getName(), var6);
         throw new AssertionError("cannot reach");
      } catch (InvocationTargetException var7) {
         Throwable t = var7.getTargetException();
         EJBRuntimeUtils.throwInternalException("Exception in " + finderMethod.getName(), t);
         throw new AssertionError("cannot reach");
      }
   }

   public Map scalarFinderLoadBean(EntityBean bean, Method finderMethod, Object[] args) throws InternalException {
      throw new InternalException("NYI");
   }

   public Enumeration enumFinder(EntityBean bean, Method finderMethod, Object[] args) throws InternalException {
      try {
         Transaction tx = TransactionService.getTransactionManager().getTransaction();
         this.beanManager.flushModifiedBeans(tx);
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InternalException(var9.getMessage());
      }

      try {
         return (Enumeration)finderMethod.invoke(bean, args);
      } catch (IllegalAccessException var6) {
         EJBRuntimeUtils.throwInternalException("Exception in " + finderMethod.getName(), var6);
         throw new AssertionError("cannot reach");
      } catch (InvocationTargetException var7) {
         Throwable t = var7.getTargetException();
         EJBRuntimeUtils.throwInternalException("Exception in " + finderMethod.getName(), t);
         throw new AssertionError("cannot reach");
      }
   }

   public Collection collectionFinder(EntityBean bean, Method finderMethod, Object[] args) throws InternalException {
      try {
         Transaction tx = TransactionService.getTransactionManager().getTransaction();
         this.beanManager.flushModifiedBeans(tx);
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InternalException(var9.getMessage());
      }

      try {
         return (Collection)finderMethod.invoke(bean, args);
      } catch (IllegalAccessException var6) {
         EJBRuntimeUtils.throwInternalException("Exception in " + finderMethod.getName(), var6);
         throw new AssertionError("cannot reach");
      } catch (InvocationTargetException var7) {
         Throwable t = var7.getTargetException();
         EJBRuntimeUtils.throwInternalException("Exception in " + finderMethod.getName(), t);
         throw new AssertionError("cannot reach");
      }
   }

   public Map collectionFinderLoadBean(EntityBean bean, Method finderMethod, Object[] args) throws InternalException {
      throw new InternalException("NYI");
   }

   public void loadBeanFromRS(EntityBean bean, RSInfo rsInfo) throws InternalException {
      throw new InternalException("NYI");
   }

   public void updateClassLoader(ClassLoader cl) {
   }
}
