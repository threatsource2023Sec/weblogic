package com.bea.core.repackaged.springframework.aop.target.dynamic;

public interface Refreshable {
   void refresh();

   long getRefreshCount();

   long getLastRefreshTime();
}
