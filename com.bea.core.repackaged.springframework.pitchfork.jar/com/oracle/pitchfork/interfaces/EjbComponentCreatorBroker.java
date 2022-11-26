package com.oracle.pitchfork.interfaces;

import com.oracle.pitchfork.interfaces.inject.ComponentContributor;

public interface EjbComponentCreatorBroker {
   void initialize(ClassLoader var1, String var2, String var3, boolean var4, ComponentContributor var5);

   Object getBean(String var1, Class var2, boolean var3) throws IllegalAccessException, InstantiationException;

   Object assembleEJB3Proxy(Object var1, String var2);
}
