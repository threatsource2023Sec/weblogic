package com.bea.core.repackaged.springframework.context;

public interface LifecycleProcessor extends Lifecycle {
   void onRefresh();

   void onClose();
}
