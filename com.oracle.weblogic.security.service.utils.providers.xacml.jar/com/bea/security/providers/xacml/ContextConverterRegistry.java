package com.bea.security.providers.xacml;

import weblogic.security.service.ContextHandler;

public class ContextConverterRegistry {
   private ContextConverterFactory factory;

   public ContextConverterRegistry(ContextConverterFactory factory) {
      this.factory = factory;
   }

   public ContextConverter getConverter(ContextHandler context) {
      return this.factory != null ? this.factory.getConverter(context) : null;
   }
}
