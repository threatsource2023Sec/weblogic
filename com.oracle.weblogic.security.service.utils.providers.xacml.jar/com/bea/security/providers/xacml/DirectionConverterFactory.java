package com.bea.security.providers.xacml;

import weblogic.security.spi.Direction;

public interface DirectionConverterFactory {
   DirectionConverter getConverter(Direction var1);
}
