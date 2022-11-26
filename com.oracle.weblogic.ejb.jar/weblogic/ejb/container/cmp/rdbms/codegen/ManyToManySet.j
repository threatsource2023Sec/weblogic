@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import javax.transaction.xa.Xid;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
import java.io.Serializable;
import java.io.IOException;

import javax.ejb.EJBObject;
import javax.ejb.EJBLocalObject;
import javax.ejb.EntityContext;
import javax.ejb.EJBException;
import javax.ejb.NoSuchEntityException;

import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.QueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheKey;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.EoWrapper;
import weblogic.ejb.container.persistence.spi.EloWrapper;
import weblogic.ejb.container.cmp.rdbms.RDBMSM2NSet;
import weblogic.ejb.container.cmp.rdbms.RDBMSSet;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.ejb20.persistence.spi.PersistenceRuntimeException;

import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.transaction.TransactionHelper;

import weblogic.utils.Debug;

public final class @setClassName implements RDBMSM2NSet, Set, Serializable,Cloneable {

  // =================================================================
  // Class  variable(s)
  private static final weblogic.diagnostics.debug.DebugLogger @debugVar =
    weblogic.ejb.container.EJBDebugService.cmpRuntimeLogger;

  private CMPBeanManager @bmVar;                  // bean manager of the related target bean in this collection
  private RDBMSPersistenceManager @pmVar;         // persistence manager of the bean that owns this collection
  private @owningBeanInterfaceName @creatorVar;
  private Method @finderVar;
  private Object @cpkVar;
  private @EJBObject @ceoVar;
  private Collection @cacheVar;
  private boolean @symmetricVar = @symmetricRelationship;

  private boolean orderDatabaseOperations = false;// if this is 'True', then we defer all Join Table INSERTs
                                                  // to COMMIT time, we do *not* INSERT them here in this Set.

  @declareAddSet;

  private Xid @createTxIdVar;
  private boolean isCreatorBeanInvalidated = false;


  public @setClassName(@owningBeanInterfaceName @creatorVar,
                              CMPBeanManager @bmVar,
                              Method @finderVar,
                              RDBMSPersistenceManager @pmVar)
  {
    if (@debugEnabled) @debugSay("@setClassName() called.");

    try {
      this.@bmVar = @bmVar;
      this.@pmVar = @pmVar;
      this.@creatorVar = @creatorVar;
      this.@finderVar = @finderVar;
      EntityContext @ctxVar = @creatorVar.__WL_getEntityContext();
      @cpkVar = @ctxVar.getPrimaryKey();
      @ceoVar = @ctxVar.@getEJBObject();
    
      if (((weblogic.transaction.Transaction)TransactionHelper.getTransactionHelper().getTransaction()) != null)
        @createTxIdVar = ((weblogic.transaction.Transaction)TransactionHelper.getTransactionHelper().getTransaction()).getXid();
    
      orderDatabaseOperations = @pmVar.getOrderDatabaseOperations();
    @standardCatch
  }

  private void populateCache()
  {
    if (@debugEnabled) @debugSay("populateCache() called.");

    try {
      assert (@cpkVar !=null);

      if (@debugEnabled) {
        @debugSay("calling @wrapperSetFinder with pk: " + @cpkVar);
      }

    @perhapsPopulateFromQueryCache

    isCreatorBeanInvalidated = false;
    @readOnlyFinderRunsInItsOwnTransaction

    if (orderDatabaseOperations) {
      @addAddSetToCache
    }

    @perhapsPutInQueryCache

    @standardCatch
  }




  //======================================================================
  //implementation of RDBMSM2NSet

  public Object getCreatorPk() {
    return @cpkVar;
  }

  public void doAdd(Object o) {
    if (@debugEnabled) @debugSay("doAdd() called.");

    @EJBObjectForField eo = (@EJBObjectForField)o;
    checkTransaction();

    @EoWrapper wrap = new @EoWrapper(eo);
    if (@cacheVar !=null) {
      if (@debugEnabled) {
        @debugSay("doAdd() adding to cached collection.");
      }

      assert (!@cacheVar.contains(wrap));

      @cacheVar.add(wrap);
    }
    if(!isCreatorBeanInvalidated) {
      @pmVar.registerInvalidatedBean(@cpkVar);
      isCreatorBeanInvalidated = true;
    }
  }

  public void doRemove(Object o) {
    doRemove(o, true);
  }

  public void doRemove(Object o, boolean remove) {
    if (@debugEnabled) @debugSay("doRemove() called.");

    @EJBObjectForField eo = (@EJBObjectForField)o;

    checkTransaction();

    @EoWrapper wrap = new @EoWrapper(eo);
    if (@cacheVar !=null) {
      if (remove) {

        assert (@cacheVar.contains(wrap));

        if (@debugEnabled) {
          @debugSay("doRemove() removing from cached collection.");
        }
        @cacheVar.remove(wrap);
      }
    }
    if(!isCreatorBeanInvalidated) {
      @pmVar.registerInvalidatedBean(@cpkVar);
      isCreatorBeanInvalidated = true;
    }
  }

  public void doAddToCache(Object o) { }   // not used here.  Used in One To Many Set only


  public Set getAddSet() {
    if (@addSetVar != null) {
      return @addSetVar;
    }
    @addSetVar = new HashSet();
    return @addSetVar;
  }

  //======================================================================




  //======================================================================
  //implementation of java.util.Collection
  public Iterator iterator() {
    if (@debugEnabled) @debugSay("iterator() called.");

    checkTransaction();

    if (@cacheVar==null) {
      populateCache();
    }

    return new @iteratorClassName(@cacheVar.iterator(), this);
  }


  private boolean existsJoinTable(Object @pk1Var, Object @pk2Var) {
    if (@debugEnabled) @debugSay("existsJoinTable() called. "
        + "(" + @pk1Var + ", " + @pk2Var + ")");

    java.sql.Connection @conVar = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;

    int selectForUpdateVal = @pmVar.getSelectForUpdateValue();

    java.lang.String @queryVar = null;

    switch(selectForUpdateVal) {
      case DDConstants.SELECT_FOR_UPDATE_DISABLED:
        @queryVar = "@existsJoinTableQuery";
        break;

      case DDConstants.SELECT_FOR_UPDATE:
        @queryVar = "@existsJoinTableQueryForUpdate";
        break;

      case DDConstants.SELECT_FOR_UPDATE_NO_WAIT:
        @queryVar = "@existsJoinTableQueryForUpdateNoWait";
        break;

      default:
        throw new AssertionError(
        "Unknown selectForUpdate type: '"+selectForUpdateVal+"'");
    }

    try {
      __WL_con = @pmVar.getConnection();

      @stmtVar = @conVar.prepareStatement(@queryVar);

      @setJoinTableParams

      @rsVar = @stmtVar.executeQuery();
      if (@rsVar.next()) {
        if (@debugEnabled) {
          System.out.println("@setClassName.existsJoinTable "
            + "found relation: " + "(" + @pk1Var + ", " + @pk2Var + ")");
        }
        return true;
      } else {
        if (@debugEnabled) {
          System.out.println("@setClassName.existsJoinTable could not "
            + "find relation: " + "(" + @pk1Var + ", " + @pk2Var + ")");
        }
        return false;
      }
    @standardCatch
      finally {
      @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    }
  }


  private boolean addJoinTable(Object @pk1Var, Object @pk2Var)
    throws java.sql.SQLException
  {
    if (@debugEnabled) @debugSay("addJoinTable() called.");

    java.sql.Connection @conVar = null;
    java.sql.PreparedStatement @stmtVar = null;
    try {
      @conVar = @pmVar.getConnection();
      String @queryVar = getAddJoinTableSQL();
      if (@debugEnabled) {
        @debugSay("@setClassName.addJoinTable() " +
          "produced sqlString: " + @queryVar);
      }
      @stmtVar = @conVar.prepareStatement(@queryVar);

      setAddJoinTableSQLParams(@stmtVar,
                               @pk1Var,
                               @pk2Var);

      int effected = @stmtVar.executeUpdate();
      if (effected != 1) {
        Loggable l = EJBLogger.logerrorInsertingInJoinTableLoggable
                           (@pk1Var.toString(),@pk2Var.toString(),Integer.toString(effected),"@joinTableName");
        throw new EJBException(l.getMessage());
      }
      return true;
    } catch (java.sql.SQLException se) {
      if(@debugEnabled) {
        @debugSay("@setClassName.addJoinTable() "+
           "checking for duplicate key " + __WL_con);
      }
      try {
        boolean exists = existsJoinTable(@pk1Var, @pk2Var);
        if (exists) return false;
        throw se;
      } catch (Exception ex) {
        if (@debugEnabled) {
          @debugSay("Exception during existsJoinTable is being ignored.");
          ex.printStackTrace();
        }
        throw se;
      }
    } finally {
      @pmVar.releaseResources(@conVar, @stmtVar, null);
    }
  }

  public String getAddJoinTableSQL()
  {
    return "INSERT INTO @joinTableName (@joinColumnsSql) VALUES (@joinColsQMs)";
  }

  public void setAddJoinTableSQLParams(java.sql.PreparedStatement @stmtVar,
                                       Object                     @pk1Var,
                                       Object                     @pk2Var)
    throws java.sql.SQLException
  {
    @setJoinTableParams
  }


  public Xid getXid() { return @createTxIdVar; }
 
  public void setTransaction(Transaction newCreateTx) { 
    // if the newCreateTx is diff than the createTx, then clear the M2NSet - 
    // addSet, before setting the new Tx, as the M2N set is maintained per tx.
    if( (@createTxIdVar != null) && !@createTxIdVar.equals(
         ((weblogic.transaction.Transaction)
         newCreateTx).getXid()) && @addSetVar != null)
      @addSetVar.clear();
      
    if (newCreateTx != null)
      @createTxIdVar = ((weblogic.transaction.Transaction)newCreateTx).getXid();
    else
      @createTxIdVar = null;
  }

  public void setIsCreatorBeanInvalidated(boolean value) {
   isCreatorBeanInvalidated = value;
  }

  public boolean add(Object o)
  {
    if (@debugEnabled) @debugSay("add() called.");

    checkTransaction();

    if (o==null)
      throw new IllegalArgumentException();

    if (!(o instanceof @relatedRemoteInterfaceName))
      throw new IllegalArgumentException(
        "Attempted to add an object of type '" +
        o.getClass().getName() +
        "' to collection, but the type must be '" +
        "@relatedRemoteInterfaceName' instead.");

    try {
      @EJBObjectForField eo = (@EJBObjectForField)o;
      @relatedBeanInterfaceName @beanVar = (@relatedBeanInterfaceName)
        @bmVar.lookup(eo.getPrimaryKey());

      try {
        @beanVar.__WL_checkExistsOnMethod();
      }
      catch (NoSuchEntityException nsee) {
        throw new IllegalArgumentException(
          "Instance of EJB '@relatedEjbName' with primary key '" + 
          eo.getPrimaryKey() +
          "' does not exist.");
      }

      boolean changed = false;
      Object  otherPK = null;

      if (orderDatabaseOperations) {
        @addBodyBulk
      }
      else {
        @addBodyNoBulk
      }

      if (changed) {
        if (orderDatabaseOperations) {

          // add to Bulk Add Set          
          if (@debugEnabled) {
            @debugSay("adding pk '"+otherPK+"' to addSet");
          }
          addToAddSet(otherPK);
        }

        doAdd(eo);

        int @oldStateVar = @beanVar.__WL_getMethodState();
        try {
          @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
          RDBMSM2NSet @colVar = (RDBMSM2NSet)
            @beanVar.@relatedGetMethodName();
          @colVar.doAdd(@ceoVar);

          if (orderDatabaseOperations) {

            // add the PK of this side of M-N relation to the
            // deferred addSet of the other side of the M-N relation.

            @colVar.addToAddSet(@cpkVar);
          }

        } finally {
          @beanVar.__WL_setMethodState(@oldStateVar);
        }
      }
      return changed;
    @standardCatch
  }


  public boolean addAll(Collection @colVar) {
    if (@debugEnabled) @debugSay("addAll() called.");

    checkTransaction();

    if (@colVar==null) return false;
    Iterator @iterVar = @colVar.iterator();

    boolean changed = false;
    while (@iterVar.hasNext()) {
      changed |= add(@iterVar.next());
    }
    return changed;
  }


  @addToBulkAddSetMethod

  public void clear() {
    if (@debugEnabled) @debugSay("clear() called.");

    checkTransaction();

    if (@cacheVar==null) {
      populateCache();
    }

    Collection cln = (Collection)((HashSet)@cacheVar).clone();
    Iterator @iterVar = cln.iterator();
    while (@iterVar.hasNext()) {
      @EoWrapper @wrapperVar = (@EoWrapper)@iterVar.next();

      remove(@wrapperVar.@getEJBObjectForField());
    }
  }


  public boolean contains(Object o)
  {
    if (@debugEnabled) @debugSay("contains() called.");

    checkTransaction();

    if (o==null) throw new IllegalArgumentException();

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

      if (@cacheVar !=null) {
        @EoWrapper wrap = new @EoWrapper(eo);

        return @cacheVar.contains(wrap);
      }

      // we may have a deferred the INSERT of this bean
      if (orderDatabaseOperations) {
        if (getAddSet().contains(eo.getPrimaryKey())) {
          return true;
        }
      }
      return existsJoinTable(@cpkVar, eo.getPrimaryKey());
    @standardCatch
  }


  public boolean containsAll(Collection eos)
  {
    if (@debugEnabled) @debugSay("containsAll() called.");

    checkTransaction();

    if (eos==null) return true;

    Iterator iter = eos.iterator();
    while (iter.hasNext()) {
      if (!contains(iter.next())) {
        return false;
      }
    }

    return true;
  }


  public boolean equals(Object o)
  {
    if (@debugEnabled) @debugSay("equals() called.");

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


  public int hashCode()
  {
    if (@debugEnabled) @debugSay("hashCode() called.");

    checkTransaction();

    if (@cacheVar==null)
      populateCache();

    return @cacheVar.hashCode();
  }


  public boolean isEmpty()
  {
    if (@debugEnabled) @debugSay("isEmpty() called.");

    checkTransaction();

    if (@cacheVar==null)
      populateCache();

    return @cacheVar.isEmpty();
  }


  private boolean removeJoinTable(Object @pk1Var, Object @pk2Var)
  {
    if (@debugEnabled) @debugSay("removeJoinTable() called.");

    java.sql.Connection @conVar = null;
    java.sql.PreparedStatement @stmtVar = null;
    try {
      @conVar = @pmVar.getConnection();
      String @queryVar = "delete from @joinTableName where @joinParamsSql";
      if (@debugEnabled) {
        @debugSay("@setClassName.removeJoinTable() " +
          "produced sqlString: " + @queryVar);
      }
      @stmtVar = @conVar.prepareStatement(@queryVar);

      @setJoinTableParams

      if (@stmtVar.executeUpdate() != 1) {
        return false;
      }
      else {
        return true;
      }
    @standardCatch
      finally {
        @pmVar.releaseResources(@conVar, @stmtVar, null);
    }
  }


  public boolean remove(Object o) {
    return remove(o, true, true);
  }


  // The flag 'remove' controls whether the Relationship's
  // underlying __WL_cache does a remove() operation.
  // If an Iterator of the __WL_cache is used to effect a remove()
  // then we must be sure to not do a __WL_cache.remove()
  //   that is the intended use of the 'remove' flag.

  public boolean remove(Object o, boolean dummyIgnore, boolean remove)
  {
    if (@debugEnabled) @debugSay("remove() called.");

    checkTransaction();

    if (o==null)
      throw new IllegalArgumentException(
        "Null value passed to @setClassName.remove.");

    if (!(o instanceof @relatedRemoteInterfaceName)) {
      throw new IllegalArgumentException(
        "@setClassName.remove called with argument of type '" +
        o.getClass().getName() +
        "', must be '@relatedRemoteInterfaceName'.");
    }

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

      boolean changed = false;

      if (orderDatabaseOperations) {
        @removeBodyBulk
      }
      else {
        @removeBodyNoBulk
      }

      if (changed) {
        doRemove(eo, remove);

        int @oldStateVar = @beanVar.__WL_getMethodState();
        try {
          @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
          RDBMSM2NSet @colVar = (RDBMSM2NSet)
            @beanVar.@relatedGetMethodName();

          @colVar.doRemove(@ceoVar);

          if (orderDatabaseOperations) {

            // remove from other cached INSERT AddSet if entry exists
            @colVar.getAddSet().remove(@cpkVar);
          }
        } finally {
          @beanVar.__WL_setMethodState(@oldStateVar);
        }
      }
      return changed;
     @standardCatch
  }


  public boolean removeAll(Collection col)
  {
    if (@debugEnabled) @debugSay("removeAll() called.");

    checkTransaction();

    if (col==null) return false;

    boolean changed = false;
    Iterator iter = col.iterator();
    while (iter.hasNext()) {
      changed |= remove(iter.next());
    }

    return changed;
  }


  public boolean retainAll(Collection col)
  {
    if (@debugEnabled) @debugSay("retainAll() called.");

    checkTransaction();

    Set retainSet = new HashSet();
    Iterator iter = null;
    if (col!=null) {
      iter = col.iterator();
      while (iter.hasNext()) {
        Object o = iter.next();
        if (o instanceof @relatedRemoteInterfaceName) {
          @EJBObjectForField eo = (@EJBObjectForField)o;
          retainSet.add(new @EoWrapper(eo));
        }
      }
    }
    if (@cacheVar==null) {
      populateCache();
    }
    iter = @cacheVar.iterator();
    List removeList = new ArrayList();

    while (iter.hasNext()) {
      @EoWrapper wrap = (@EoWrapper)iter.next();

      if (!retainSet.contains(wrap)) {
        removeList.add(wrap.@getEJBObjectForField());
      }
    }

    iter = removeList.iterator();
    boolean changed = false;
    while (iter.hasNext()) {
      @EJBObjectForField eo = (@EJBObjectForField)iter.next();
      changed |= remove(eo);
    }

    return changed;
  }


  public int size()
  {
    if (@debugEnabled) @debugSay("size() called.");

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
        @relatedRemoteInterfaceName elem =
         (@relatedRemoteInterfaceName)wrap.@getEJBObjectForField();

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


  public Object clone() throws CloneNotSupportedException {
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


@start rule: addToBulkAddSetMethod
  public void addToAddSet(Object pk)

  {
    if (@debugEnabled)
      @debugSay("@setClassName: addToAddSet:  creator pk '"+@cpkVar+
                "' adding to cached Join Table Insert pk '"+pk+"'");

    getAddSet().add(pk);

    @pmVar.registerM2NJoinTableInsert("@cmrName", @cpkVar);

    // our creator needs to know that its addSet has been added to
    @creatorVar.__WL_setM2NInsert(true);
    
  }
@end rule: addToBulkAddSetMethod


@start rule: addBodyNoBulk

      // do add check for non-deferred Join Table INSERTs

      changed = addJoinTable(@cpkVar, eo.getPrimaryKey());
      if (@symmetricVar) {
        if (!@cpkVar.equals(eo.getPrimaryKey())) {
          addJoinTable(eo.getPrimaryKey(), @cpkVar);
        }
      }
@end rule: addBodyNoBulk


@start rule: addBodyBulk

      // do add check for deferred Join Table INSERTs

      otherPK = eo.getPrimaryKey();
      boolean contains = getAddSet().contains(otherPK);
      if (contains) {
        if (@debugEnabled)
          @debugSay("@setClassName: add: skipping JoinTable check, "+
                    "addSet already contains cached Join Table INSERT pk '"+otherPK+"'");

        return false;       // this PK already added, exit false
      }

      boolean exists = false;
      exists = existsJoinTable(@cpkVar, eo.getPrimaryKey());
      if (exists) {
        return false;       // this PK already in Join Table, exit false
      }
      changed = (!exists);
@end rule: addBodyBulk


@start rule: removeFromBulkAddSet

      // do existence check for deferred Join Table INSERT

      if (getAddSet().contains(otherPK)) {
        if (@debugEnabled)
          @debugSay("@setClassName: remove: skipping JoinTable DELETE, "+
                    "remove from addSet which contains cached Join Table INSERT pk '"+otherPK+"'");
        getAddSet().remove(otherPK);
        changed = true;
      }
      else {
        @removeBodyNoBulk
      }
@end rule: removeFromBulkAddSet


@start rule: removeBodyNoBulk

      // do existence check for non-deferred Join Table INSERT

      changed = removeJoinTable(@cpkVar, eo.getPrimaryKey());
      if (@symmetricVar) {
        if (!@cpkVar.equals(eo.getPrimaryKey())) {
          removeJoinTable(eo.getPrimaryKey(), @cpkVar);
        }
      }
@end rule: removeBodyNoBulk


@start rule: removeBodyBulk
      Object otherPK = eo.getPrimaryKey();

      // if we have previously cached a deferred INSERT
      //   for this PK,
      // then simply undo the add and set changed == true
      // else go and do the normal Join Table remove step

      if (getAddSet().contains(otherPK)) {
        if (@debugEnabled)
          @debugSay("@setClassName: remove: skipping JoinTable DELETE, "+
                    "remove from addSet which contains cached Join Table INSERT pk '"+otherPK+"'");
        getAddSet().remove(otherPK);
        changed = true;
      }
      else {
        @removeBodyNoBulk
      }
@end rule: removeBodyBulk


@start rule: addAddSetToCache

      // add any cached ADDs to the @cacheVar

      if (@addSetVar != null) {
        Set s = getAddSet();

        Iterator it = s.iterator();
        while (it.hasNext()) {
          Object otherPK = it.next();
          if (@debugEnabled)
            @debugSay("@setClassName: adding from AddSet to __WL_cache: "+ 
                      "Join Table INSERT pk '"+otherPK+"'");
          Object eo      = @bmVar.finderGetEoFromBeanOrPk(null, otherPK, true);
          if (eo == null) {
            throw new RDBMSException("Relationship encountered unexpected condition.  "+
                                     "The Many To Many Set '@setClassName' addAddSetToCache attempted "+
                                     "to get ejbObject for pk '"+otherPK+"' from "+
                                     " @bmVar.finderGetEoFromBeanOrPk, but got back unexpected NULL. ");
          }
          doAdd(eo);
        }
      }
@end rule: addAddSetToCache



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

@start rule: queryCachingMethods

  private Set getFromQueryCache() throws InternalException {
    weblogic.ejb.container.manager.TTLManager roMgr = 
                        (weblogic.ejb.container.manager.TTLManager)@bmVar;
    return (Set)roMgr.getFromQueryCache(@finderVar.getName(), 
                                   new Object[] { @cpkVar }, 
                                   @isLocal, true);
  }

  // If this collection was loaded using eager relationship caching,
  // the source query is passed in
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
