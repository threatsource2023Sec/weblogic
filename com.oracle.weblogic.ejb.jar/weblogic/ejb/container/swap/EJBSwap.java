package weblogic.ejb.container.swap;

import weblogic.ejb.container.InternalException;

public interface EJBSwap {
   Object read(Object var1, Class var2) throws InternalException;

   void write(Object var1, Object var2, long var3) throws InternalException;

   void remove(Object var1);

   void destroy();

   void updateClassLoader(ClassLoader var1);

   void updateIdleTimeoutMS(long var1);
}
