package com.oracle.buzzmessagebus.api;

import com.oracle.buzzmessagebus.impl.BuzzImpl;

public abstract class BuzzFactory {
   public static BuzzAdmin.Builder getBuilder(String... classNames) {
      return BuzzImpl.builder();
   }
}
