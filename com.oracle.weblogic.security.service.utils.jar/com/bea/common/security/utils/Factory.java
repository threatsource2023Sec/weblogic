package com.bea.common.security.utils;

import java.lang.reflect.InvocationTargetException;

public interface Factory {
   Object newInstance() throws InvocationTargetException;

   void destroyInstance(Object var1);
}
