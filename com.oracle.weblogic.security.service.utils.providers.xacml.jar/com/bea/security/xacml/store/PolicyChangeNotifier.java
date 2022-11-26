package com.bea.security.xacml.store;

public interface PolicyChangeNotifier {
   void addListener(PolicyChangeListener var1);

   boolean removeListener(PolicyChangeListener var1);
}
