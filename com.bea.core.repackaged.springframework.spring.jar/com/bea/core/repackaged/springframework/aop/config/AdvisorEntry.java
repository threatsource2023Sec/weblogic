package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.parsing.ParseState;

public class AdvisorEntry implements ParseState.Entry {
   private final String name;

   public AdvisorEntry(String name) {
      this.name = name;
   }

   public String toString() {
      return "Advisor '" + this.name + "'";
   }
}
