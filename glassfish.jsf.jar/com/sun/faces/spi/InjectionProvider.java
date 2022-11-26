package com.sun.faces.spi;

public interface InjectionProvider {
   void inject(Object var1) throws InjectionProviderException;

   void invokePreDestroy(Object var1) throws InjectionProviderException;

   void invokePostConstruct(Object var1) throws InjectionProviderException;
}
