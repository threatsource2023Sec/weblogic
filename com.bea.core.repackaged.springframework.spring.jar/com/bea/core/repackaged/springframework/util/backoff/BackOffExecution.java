package com.bea.core.repackaged.springframework.util.backoff;

@FunctionalInterface
public interface BackOffExecution {
   long STOP = -1L;

   long nextBackOff();
}
