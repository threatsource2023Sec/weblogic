package com.bea.security.providers.xacml;

import weblogic.security.service.ContextHandler;

public interface ContextConverterFactory {
   ContextConverter getConverter(ContextHandler var1);
}
