package com.bea.core.repackaged.aopalliance.intercept;

import java.lang.reflect.AccessibleObject;

public interface Joinpoint {
   Object proceed() throws Throwable;

   Object getThis();

   AccessibleObject getStaticPart();
}
