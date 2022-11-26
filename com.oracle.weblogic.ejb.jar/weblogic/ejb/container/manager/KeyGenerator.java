package weblogic.ejb.container.manager;

import weblogic.ejb.container.interfaces.BeanInfo;

public interface KeyGenerator {
   void setup(BeanInfo var1);

   Object nextKey();
}
