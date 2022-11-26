package com.bea.xbeanmarshal.runtime;

import com.bea.xml.XmlRuntimeException;

public abstract class BindingContextFactory {
   protected static final String DEFAULT_IMPL = "com.bea.xbeanmarshal.runtime.internal.BindingContextFactoryImpl";

   public static BindingContextFactory newInstance() {
      try {
         Class default_impl = Class.forName("com.bea.xbeanmarshal.runtime.internal.BindingContextFactoryImpl");
         BindingContextFactory factory = (BindingContextFactory)default_impl.newInstance();
         return factory;
      } catch (ClassNotFoundException var2) {
         throw new XmlRuntimeException(var2);
      } catch (InstantiationException var3) {
         throw new XmlRuntimeException(var3);
      } catch (IllegalAccessException var4) {
         throw new XmlRuntimeException(var4);
      }
   }
}
