package com.bea.core.repackaged.aspectj.runtime.internal.cflowstack;

public interface ThreadStackFactory {
   ThreadStack getNewThreadStack();

   ThreadCounter getNewThreadCounter();
}
