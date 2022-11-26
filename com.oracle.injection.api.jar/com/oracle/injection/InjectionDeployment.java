package com.oracle.injection;

import java.util.Collection;

public interface InjectionDeployment {
   Collection getArchives();

   BeanManager getBeanManager(String var1);
}
