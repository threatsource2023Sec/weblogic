package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.parsing.ParseState;

public class PointcutEntry implements ParseState.Entry {
   private final String name;

   public PointcutEntry(String name) {
      this.name = name;
   }

   public String toString() {
      return "Pointcut '" + this.name + "'";
   }
}
