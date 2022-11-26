@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.Array;
import java.io.Serializable;
import java.io.IOException;

import javax.ejb.EJBObject;
import javax.ejb.EJBLocalObject;
import javax.ejb.EntityContext;
import javax.ejb.EJBException;
import javax.ejb.NoSuchEntityException;

import javax.transaction.xa.Xid;
import javax.transaction.Transaction;

import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.QueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheKey;
import weblogic.ejb.container.internal.EntityEJBContextImpl;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.EoWrapper;
import weblogic.ejb.container.persistence.spi.EloWrapper;

import weblogic.ejb.container.cmp.rdbms.RDBMSSet;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb20.persistence.spi.PersistenceRuntimeException;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import javax.transaction.TransactionManager;
import weblogic.transaction.TransactionHelper;


import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.collections.ArraySet;

public final class @setClassName implements RDBMSSet, Set, Serializable,Cloneable
{
  // =================================================================
  // Class  variable(s)
  private static final weblogic.diagnostics.debug.DebugLogger @debugVar =
    weblogic.ejb.container.EJBDebugService.cmpRuntimeLogger;

  private CMPBeanManager @bmVar;
  private Method @finderVar;
  private Object @cpkVar;
  private @EJBObject @ceoVar;
  private Set @cacheVar;
  
  @perhapsDeclareReadWriteVars

  private boolean isCreatorBeanInvalidated = false;
  private Xid @createTxIdVar;
  private @owningBeanInterfaceName @creatorVar;



  public @setClassName(@owningBeanInterfaceName @creatorVar,
                              CMPBeanManager @bmVar,
                              Method @finderVar
                             ) {
    try {
      this.@creatorVar = @creatorVar;
      this.@bmVar = @bmVar;
      this.@finderVar = @finderVar;
      EntityEJBContextImpl @ctxVar = (EntityEJBContextImpl) @creatorVar.__WL_getEntityContext();
      @cpkVar = @ctxVar.__WL_getPrimaryKey();
      @ceoVar = @ctxVar.@getWLGetEJBObject();

      @perhapsInitReadWriteVars
      
      if (((weblogic.transaction.Transaction)TransactionHelper.getTransactionHelper().getTransaction()) != null)
        @createTxIdVar = ((weblogic.transaction.Transaction)TransactionHelper.getTransactionHelper().getTransaction()).getXid();
      
    @standardCatch
  }


  private void populateCache() {
    try {
      assert (@cpkVar !=null);

      @perhapsPopulateFromQueryCache 

      @perhapsInvokeFinder

      @perhapsReconcileReadWriteChanges

      @perhapsPutInQueryCache
     
      isCreatorBeanInvalidated = false;
    @standardCatch
  }


  //======================================================================
  //implementation of RDBMSSet
  public void doAdd(Object o)
  {
     @perhapsDoAddForReadWrite
  }

  public void doRemove(Object o) {
    doRemove(o, true);
  }

  public void doRemove(Object o, boolean remove)
  {
    @perhapsDoRemoveForReadWrite
  }

  public void doAddToCache(Object o)
  {
    if (@debugEnabled) @debugSay("doAddToCache() called.");

    if (@cacheVar==null) {
      @cacheVar = new HashSet(10);
    }

    if (o == null) return;

    @EJBObjectForField eo = (@EJBObjectForField)o;

    @EoWrapper wrap = new @EoWrapper(eo);

    assert (!@cacheVar.contains(wrap));

    @cacheVar.add(wrap);
  }


  //======================================================================
  //implementation of java.util.Collection
  public Iterator iterator() {

    checkTransaction();

    if (@cacheVar==null) {
      populateCache();
    }

    return new @iteratorClassName(@cacheVar.iterator(), this);
  }

  public Xid getXid() { return @createTxIdVar; }

  public void setTransaction(javax.transaction.Transaction newCreateTx) {
    if (newCreateTx != null)
      @createTxIdVar = ((weblogic.transaction.Transaction)newCreateTx).getXid(); 
    else
      @createTxIdVar = null;
   }

  public void setIsCreatorBeanInvalidated(boolean value) {
   isCreatorBeanInvalidated = value;
  }

  public boolean add(Object o) {

    checkTransaction();

    if (o==null)
      throw new IllegalArgumentException();

    if (!(o instanceof @elementInterfaceName))
      throw new IllegalArgumentException(
        "Attempted to add an object of type '" +
        o.getClass().getName() +
        "' to collection, but the type must be '" +
        "@elementInterfaceName' instead.");

    try {
      @EJBObjectForField eo = (@EJBObjectForField)o;
      @relatedBeanInterfaceName @beanVar = (@relatedBeanInterfaceName)
        @bmVar.lookup(eo.getPrimaryKey());
      int @oldStateVar = @beanVar.__WL_getMethodState();
      try {
        @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
        @perhapsDeclarePkVar
        @perhapsAssignPkVar

        try {
          @beanVar.__WL_checkExistsOnMethod();
        }
        catch (NoSuchEntityException nsee) {
          Loggable l = EJBLogger.logbeanDoesNotExistLoggable("@relatedEjbName",eo.getPrimaryKey().toString());
          throw new IllegalArgumentException(l.getMessage());  
        }
        if (@cpkVar.equals(@pkVarForBean)) {
          return false;
        }
        else {
          @beanVar.@relatedBeanSetMethod(
            (@remoteInterfaceName)@ceoVar);
          return true;
        }
      } finally {
        @beanVar.__WL_setMethodState(@oldStateVar);
      }
     @standardCatch
  }


  public boolean addAll(Collection @colVar) {
    checkTransaction();

    if (@colVar==null) return false;

    Collection temp = new ArrayList();
    temp.addAll(@colVar);
    Iterator @iterVar = temp.iterator();

    boolean changed = false;
    while (@iterVar.hasNext()) {
      changed |= add(@iterVar.next());
    }
    return changed;
  }


  public void clear() {
    clear(false);
  }


  public void clear(boolean ejbStore) {
    checkTransaction();

    if (@cacheVar==null) {
      populateCache();
    }

    Collection cln = (Collection)((HashSet)@cacheVar).clone();
    Iterator @iterVar = cln.iterator();
    while (@iterVar.hasNext()) {
      @EoWrapper @wrapperVar = (@EoWrapper)@iterVar.next();
      uncheckedTxnRemove(@wrapperVar.@getEJBObjectForField(), ejbStore, true);
    }
  }


  public boolean contains(Object o) {
    checkTransaction();

    if (o==null)
      return false;

    if (!(o instanceof @elementInterfaceName))
      return false;

    try {
      @EJBObjectForField eo = (@EJBObjectForField)o;
      if (@cacheVar !=null) {
        @EoWrapper wrap = new @EoWrapper(eo);
        return @cacheVar.contains(wrap);
      }
      else {
        @relatedBeanInterfaceName @beanVar = (@relatedBeanInterfaceName)
          @bmVar.lookup(eo.getPrimaryKey());

        try {
          @beanVar.__WL_checkExistsOnMethod();
        }
        catch (NoSuchEntityException nsee) {
          Loggable l = EJBLogger.logbeanDoesNotExistLoggable("@relatedEjbName",eo.getPrimaryKey().toString());
          throw new IllegalArgumentException(l.getMessage());  
        }

        int @oldStateVar = @beanVar.__WL_getMethodState();
        try {
          @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
          @perhapsDeclarePkVar
          @perhapsAssignPkVar
          if (@cpkVar.equals(@pkVarForBean)) {
            return true;
          }
          else {
            return false;
          }
        } finally {
          @beanVar.__WL_setMethodState(@oldStateVar);
        }
      }
    @standardCatch
  }

  public boolean containsAll(Collection eos) {
    checkTransaction();

    if (eos==null)
      return true;

    Iterator iter = eos.iterator();
    while (iter.hasNext()) {
      if (!contains(iter.next())) {
        return false;
      }
    }

    return true;
  }


  public boolean equals(Object o) {
    checkTransaction();

    if (!(o instanceof @setClassName))
      return false;

    @setClassName other = (@setClassName)o;

    if (@cacheVar==null)
      populateCache();
    if (other.@cacheVar==null)
      other.populateCache();

    return @cacheVar.equals(other.@cacheVar);
  }


  public int hashCode() {
    checkTransaction();

    if (@cacheVar==null)
      populateCache();
    return @cacheVar.hashCode();
  }


  public boolean isEmpty() {
    checkTransaction();

    if (@cacheVar==null)
      populateCache();

    return @cacheVar.isEmpty();
  }


  public boolean remove(Object o)
  {
    return remove(o, false);
  }

  public boolean remove(Object o, boolean ejbStore)
  {
    return remove(o, ejbStore, true);
  }


  // The flag 'remove' controls whether the Relationship's
  // underlying __WL_cache does a remove() operation.
  // If an Iterator of the __WL_cache is used to effect a remove()
  // then we must be sure to not do a __WL_cache.remove()
  //   that is the intended use of the 'remove' flag.

  public boolean remove(Object o, boolean ejbStore, boolean remove)

  {
    checkTransaction();
    return uncheckedTxnRemove(o, ejbStore, remove);

  }

  private boolean uncheckedTxnRemove(Object o, boolean ejbStore, 
                                               boolean remove)
  {
    if (o==null)
      throw new IllegalArgumentException();

    if (!(o instanceof @elementInterfaceName))
      throw new IllegalArgumentException(
        "Attempted to add an object of type '" +
        o.getClass().getName() +
        "' to collection, but the type must be '" +
        "@elementInterfaceName' instead.");

    try {
      @EJBObjectForField eo = (@EJBObjectForField)o;
      @relatedBeanInterfaceName @beanVar = (@relatedBeanInterfaceName)
        @bmVar.lookup(eo.getPrimaryKey());

      try {
        @beanVar.__WL_checkExistsOnMethod();
      }
      catch (NoSuchEntityException nsee) {
          Loggable l = EJBLogger.logbeanDoesNotExistLoggable("@relatedEjbName",eo.getPrimaryKey().toString());
          throw new IllegalArgumentException(l.getMessage());  
      }

      int @oldStateVar = @beanVar.__WL_getMethodState();
      try {
        @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
        @perhapsDeclarePkVar
        @perhapsAssignPkVar
        if (!@cpkVar.equals(@pkVarForBean)) {
          return false;
        }
        else {
          @beanVar.@varPrefix@relatedBeanSetMethod(null, ejbStore, remove);
          return true;
        }
      } finally {
        @beanVar.__WL_setMethodState(@oldStateVar);
      }
    @standardCatch
  }


  public boolean removeAll(Collection col) {
    checkTransaction();

    if (col==null)
      return false;

    boolean changed = false;
    Iterator iter = col.iterator();
    while (iter.hasNext()) {
      changed |= uncheckedTxnRemove(iter.next(), false, true);
    }

    return changed;
  }


  public boolean retainAll(Collection c) {
    checkTransaction();

    Iterator iter = iterator();
    List toRemove = new ArrayList();

    while (iter.hasNext()) {
      @EJBObjectForField eo = (@EJBObjectForField)iter.next();

      if (c != null && !c.contains(eo)) {
        toRemove.add(eo);
      }
    }

    Iterator remIter = toRemove.iterator();
    boolean changed = false;
    while (remIter.hasNext()) {
      @EJBObjectForField eo = (@EJBObjectForField)remIter.next();
      changed |= this.remove(eo);
    }

    return changed;
  }


  public int size() {
    checkTransaction();

    if (@cacheVar==null)
      populateCache();

    return @cacheVar.size();
  }


  public Object[] toArray()
  {
    if (@debugEnabled) @debugSay("toArray() called.");

    checkTransaction();

    if (@cacheVar==null) populateCache();

    int i = 0;
    Object[] arry = new Object[@cacheVar.size()];
    Iterator iter = @cacheVar.iterator();
    while (iter.hasNext()) {
      @EoWrapper wrap = (@EoWrapper)iter.next();
      arry[i++] = wrap.@getEJBObjectForField();
    }

    return arry;
  }


  public Object[] toArray(Object[] a)
  {
    if (@debugEnabled) @debugSay("toArray() called.");

    checkTransaction();

    if (a==null)
      throw new ArrayStoreException(
        "Null argument passed to @setClassName.toArray");

    try {
      Class toClass = a.getClass().getComponentType();

      if (@cacheVar==null) populateCache();

      if (a.length<@cacheVar.size())
        a = (Object[])Array.newInstance(toClass, @cacheVar.size());

      int i = 0;
      Iterator iter = @cacheVar.iterator();
      while (iter.hasNext()) {
        @EoWrapper wrap = (@EoWrapper)iter.next();
        @elementInterfaceName elem = 
          (@elementInterfaceName)wrap.@getEJBObjectForField();

        if (i==0) {
          Class colClass = elem.getClass();
          if (!toClass.isAssignableFrom(colClass)) {
            throw new ArrayStoreException(
              "Argument type '" +
              toClass.getName() +
              "' passed to @setClassName.toArray " +
              "is not a superclass of the element type of the collection '" +
              colClass.getName() +
              "'.");
          }
        }

        a[i++] = elem;
      }

      while (i<a.length) {
        a[i++] = null;
      }

      return a;
    @standardCatch
  }

  private void checkTransaction()
  {
    weblogic.transaction.Transaction tx = (weblogic.transaction.Transaction)
                   TransactionHelper.getTransactionHelper().getTransaction();
    
    if ((tx == null) && (@createTxIdVar == null))
      return;
    else if ((tx == null) && (@createTxIdVar != null)) 
      if (! @isReadOnly) {
          Loggable l1 = EJBLogger.logaccessedCmrCollectionInDifferentTransactionLoggable("@ejbName", "@cmrName");
          throw new IllegalStateException(l1.getMessageText());
      }
    else if (!tx.getXid().equals(@createTxIdVar) && ! @isReadOnly ) {
      Loggable l1 = EJBLogger.logaccessedCmrCollectionInDifferentTransactionLoggable("@ejbName", "@cmrName");
      throw new IllegalStateException(l1.getMessageText());
    }    
    
  }
  
   public boolean checkIfCurrentTxEqualsCreateTx(Transaction currentTx) {
     
     
     if(currentTx != null && ((weblogic.transaction.Transaction)
        currentTx).getXid().equals(@createTxIdVar))
       return true;
     else
       return false;
  }
 
  public Object clone()  throws CloneNotSupportedException {
     return super.clone();
  }
 
  private void writeObject(java.io.ObjectOutputStream out)
    throws IOException
  {
    throw new EJBException(
      "Attempt to serialize a collection that implements a cmr-field.  " +
      "Collections managed by the CMP RDBMS persistence manager may not " +
      "be passed directly to a remote client.");
  }

  private void readObject(java.io.ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    // this method is never called

    throw new EJBException(
      "Attempt to serialize a collection that implements a cmr-field.  " +
      "Collections managed by the CMP RDBMS persistence manager may not " +
      "be passed directly to a remote client.");
  }

  private static void @debugSay(String s) {
    @debugVar.debug("[@setClassName] " + s);
  }


@perhapsImplementQueryCachingMethods



}
@end rule: main

@start rule: standardCatch
} catch (RuntimeException re) {
      if (@debugEnabled) {
        @debugSay("rethrowing RuntimeException.");
        re.printStackTrace();
      }
      throw re;
    } catch (Exception ex) {
      if (@debugEnabled) {
        @debugSay("wrapping Exception in PersistenceRuntimeException.");
        ex.printStackTrace();
      }
      throw new PersistenceRuntimeException(ex);
    }
@end rule: standardCatch

@start rule: reconcileReadWriteChanges
	  Iterator @addIter = @addVar.iterator();
	  while (@addIter.hasNext()) {
	    @EoWrapper @wrapperVar = (@EoWrapper)@addIter.next();
	    if (!@cacheVar.contains(@wrapperVar)) {
	      @cacheVar.add(@wrapperVar);
	    }
	  }
	  Iterator @remIter = @removeVar.iterator();
	  while (@remIter.hasNext()) {
	    @EoWrapper @wrapperVar = (@EoWrapper)@remIter.next();
	
	    if (@cacheVar.contains(@wrapperVar)) {
	      @cacheVar.remove(@wrapperVar);
	    }
	  }
	  @addVar = null;
	  @removeVar = null;
@end rule: reconcileReadWriteChanges

@start rule: doAddForReadWrite
    if (@debugEnabled) @debugSay("doAdd() called.");

    @EJBObjectForField eo = (@EJBObjectForField)o;

    checkTransaction();

    @EoWrapper wrap = new @EoWrapper(eo);
    if (@cacheVar==null) {
      if (@removeVar.contains(wrap)) {
        @removeVar.remove(wrap);
      }

      assert (!@addVar.contains(wrap));

      @addVar.add(wrap);
    }
    else {
      assert (!@cacheVar.contains(wrap));

      @cacheVar.add(wrap);
    }
    
    if (!isCreatorBeanInvalidated) {
     @pmVar.registerInvalidatedBean(@cpkVar);
     isCreatorBeanInvalidated = true;
    }
@end rule: doAddForReadWrite

@start rule: doRemoveForReadWrite
    checkTransaction();

    @EJBObjectForField eo = (@EJBObjectForField)o;

    @EoWrapper wrap = new @EoWrapper(eo);
    if (@cacheVar==null) {
      if (@addVar.contains(wrap)) {
        @addVar.remove(wrap);
      }

      assert (!@removeVar.contains(wrap));

      @removeVar.add(wrap);
    }
    else {
      if (remove) {
        assert (@cacheVar.contains(wrap));

        @cacheVar.remove(wrap);
      }
    }
    if(!isCreatorBeanInvalidated) {
      @pmVar.registerInvalidatedBean(@cpkVar);
      isCreatorBeanInvalidated = true;
    }
@end rule: doRemoveForReadWrite

@start rule: queryCachingMethods
  private Set getFromQueryCache() throws InternalException {
    weblogic.ejb.container.manager.TTLManager roMgr = 
                        (weblogic.ejb.container.manager.TTLManager)@bmVar;
    return (Set)roMgr.getFromQueryCache(@finderVar.getName(), 
                                   new Object[] { @cpkVar }, 
                                   @isLocal, true);
  }

  // If this collection was loaded using eager relationship caching,
  // the associated query is passed in
  public void putInQueryCache(QueryCacheKey sourceQuery) {
    weblogic.ejb.container.manager.TTLManager roMgr = 
                        (weblogic.ejb.container.manager.TTLManager)@bmVar;
    Collection cacheKeys = new ArrayList(@cacheVar.size());
    Iterator iterator = @cacheVar.iterator();
 
    while(iterator.hasNext()) {
      Object obj = iterator.next();
      if (obj instanceof EloWrapper) {
        obj = ((EloWrapper)obj).getPrimaryKey();
      } else {
        obj = ((EoWrapper)obj).getPrimaryKey();
      }
      cacheKeys.add(new QueryCacheElement(obj, roMgr));
    }

    if (!cacheKeys.isEmpty()) {
      QueryCacheKey qckey = new QueryCacheKey(@finderVar.getName(), 
                            new Object[] { @cpkVar }, roMgr,
                            QueryCacheKey.RET_TYPE_SET);
      if (sourceQuery != null) {
        sourceQuery.addDestinationQuery(qckey);
        qckey.addSourceQuery(sourceQuery);
      }
      roMgr.putInQueryCache(qckey, cacheKeys);
    }
  }
@end rule: queryCachingMethods

@start rule: loadIfNotPostCreate   
      if (@creatorVar.__WL_getMethodState() == WLEnterpriseBean.STATE_EJB_POSTCREATE) {
		if (@cacheVar == null) @cacheVar = new HashSet();
      }
@end rule: loadIfNotPostCreate
