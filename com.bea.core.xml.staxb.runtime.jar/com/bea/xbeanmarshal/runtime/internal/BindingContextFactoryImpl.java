package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.runtime.BindingContextFactory;

public final class BindingContextFactoryImpl extends BindingContextFactory {
   public static BindingContextImpl createBindingContext(BindingLoader b) {
      if (b == null) {
         throw new IllegalArgumentException(" Cannot create an xbeanmarshal BindingContext from a NULL BindingLoader.");
      } else {
         BindingLoader bindingLoader = buildBindingLoader(b);
         return new BindingContextImpl(bindingLoader);
      }
   }

   private static BindingLoader buildBindingLoader(BindingLoader b) {
      return b;
   }
}
