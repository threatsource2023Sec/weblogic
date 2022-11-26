package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.naming.Context;
import javax.naming.NamingException;

@FunctionalInterface
public interface JndiCallback {
   @Nullable
   Object doInContext(Context var1) throws NamingException;
}
