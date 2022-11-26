package com.bea.security.providers.xacml;

import weblogic.security.spi.Resource;

public interface ResourceConverterFactory {
   ResourceConverter getConverter(Resource var1);
}
