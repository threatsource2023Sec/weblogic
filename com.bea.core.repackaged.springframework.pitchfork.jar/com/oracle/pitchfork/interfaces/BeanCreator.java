package com.oracle.pitchfork.interfaces;

import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.Constructor;

public interface BeanCreator {
   Object createInterceptor(InterceptorMetadataI var1);

   Object createBean() throws InstantiationException, IllegalAccessException;

   Constructor getBeansConstructor();
}
