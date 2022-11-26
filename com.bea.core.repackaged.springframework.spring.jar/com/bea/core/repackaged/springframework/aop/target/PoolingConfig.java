package com.bea.core.repackaged.springframework.aop.target;

public interface PoolingConfig {
   int getMaxSize();

   int getActiveCount() throws UnsupportedOperationException;

   int getIdleCount() throws UnsupportedOperationException;
}
