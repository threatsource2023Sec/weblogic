package com.bea.core.repackaged.springframework.transaction.support;

public interface ResourceHolder {
   void reset();

   void unbound();

   boolean isVoid();
}
