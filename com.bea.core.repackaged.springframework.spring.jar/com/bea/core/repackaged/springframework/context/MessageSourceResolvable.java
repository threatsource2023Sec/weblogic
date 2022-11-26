package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.lang.Nullable;

@FunctionalInterface
public interface MessageSourceResolvable {
   @Nullable
   String[] getCodes();

   @Nullable
   default Object[] getArguments() {
      return null;
   }

   @Nullable
   default String getDefaultMessage() {
      return null;
   }
}
