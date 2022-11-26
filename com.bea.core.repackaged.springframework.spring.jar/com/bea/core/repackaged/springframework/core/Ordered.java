package com.bea.core.repackaged.springframework.core;

public interface Ordered {
   int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
   int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

   int getOrder();
}
