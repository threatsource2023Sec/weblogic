package com.bea.core.repackaged.springframework.util.backoff;

@FunctionalInterface
public interface BackOff {
   BackOffExecution start();
}
