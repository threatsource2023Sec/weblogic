package weblogic.ejb.container.persistence.spi;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import javax.ejb.EntityBean;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanManager;

public interface PersistenceManager {
   void setup(BeanManager var1) throws Exception;

   Object findByPrimaryKey(EntityBean var1, Method var2, Object var3) throws Throwable;

   Object scalarFinder(EntityBean var1, Method var2, Object[] var3) throws Throwable;

   Collection collectionFinder(EntityBean var1, Method var2, Object[] var3) throws Throwable;

   Enumeration enumFinder(EntityBean var1, Method var2, Object[] var3) throws Throwable;

   EntityBean findByPrimaryKeyLoadBean(EntityBean var1, Method var2, Object var3) throws Throwable;

   Map scalarFinderLoadBean(EntityBean var1, Method var2, Object[] var3) throws Throwable;

   Map collectionFinderLoadBean(EntityBean var1, Method var2, Object[] var3) throws Throwable;

   void loadBeanFromRS(EntityBean var1, RSInfo var2) throws InternalException;

   void updateClassLoader(ClassLoader var1);
}
