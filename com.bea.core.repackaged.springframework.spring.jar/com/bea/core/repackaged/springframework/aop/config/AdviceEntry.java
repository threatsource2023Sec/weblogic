package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.parsing.ParseState;

public class AdviceEntry implements ParseState.Entry {
   private final String kind;

   public AdviceEntry(String kind) {
      this.kind = kind;
   }

   public String toString() {
      return "Advice (" + this.kind + ")";
   }
}
