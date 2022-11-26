package weblogic.ejb.container.persistence.spi;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EnterpriseBean;
import javax.ejb.EntityBean;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.internal.InvocationWrapper;

public interface CMPBeanManager {
   EnterpriseBean lookup(Object var1) throws InternalException;

   EJBObject remoteFindByPrimaryKey(Method var1, Object var2) throws InternalException;

   EJBLocalObject localFindByPrimaryKey(Method var1, Object var2) throws InternalException;

   Set remoteWrapperSetFinder(Method var1, Object[] var2, boolean var3) throws InternalException;

   Set localWrapperSetFinder(Method var1, Object[] var2, boolean var3) throws InternalException;

   EJBObject remoteScalarFinder(Method var1, Object[] var2) throws InternalException;

   EJBLocalObject localScalarFinder(Method var1, Object[] var2) throws InternalException;

   Collection remoteCollectionFinder(Method var1, Object[] var2) throws InternalException;

   Collection localCollectionFinder(Method var1, Object[] var2) throws InternalException;

   Set remoteSetFinder(Method var1, Object[] var2) throws InternalException;

   Set localSetFinder(Method var1, Object[] var2) throws InternalException;

   void postFinderCleanup(Object var1, Collection var2, boolean var3, boolean var4);

   void remove(InvocationWrapper var1, EntityBean var2, boolean var3) throws InternalException;

   Object finderGetEoFromBeanOrPk(EntityBean var1, Object var2, boolean var3) throws InternalException;

   EntityBean getBeanFromRS(Object var1, RSInfo var2) throws InternalException;

   EntityBean getBeanFromPool() throws InternalException;

   void addBeanToInsertStmt(PreparedStatement[] var1, List var2, CMPBean var3, boolean var4, boolean var5) throws Exception;

   void addBeanToDeleteStmt(PreparedStatement[] var1, List var2, boolean[] var3, CMPBean var4, boolean var5, boolean var6) throws Exception;

   boolean needsToBeInserted(Object var1) throws SystemException, RollbackException;
}
