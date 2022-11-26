package com.bea.security.providers.xacml;

import weblogic.security.spi.Direction;

public class DirectionConverterRegistry {
   private DirectionConverterFactory factory;

   public DirectionConverterRegistry(DirectionConverterFactory factory) {
      this.factory = factory;
   }

   public DirectionConverter getConverter(Direction d) {
      return this.factory != null ? this.factory.getConverter(d) : null;
   }
}
