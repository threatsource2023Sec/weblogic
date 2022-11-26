package com.bea.core.repackaged.jdt.internal.compiler.env;

public class AccessRestriction {
   private AccessRule accessRule;
   public byte classpathEntryType;
   public static final byte COMMAND_LINE = 0;
   public static final byte PROJECT = 1;
   public static final byte LIBRARY = 2;
   public String classpathEntryName;

   public AccessRestriction(AccessRule accessRule, byte classpathEntryType, String classpathEntryName) {
      this.accessRule = accessRule;
      this.classpathEntryName = classpathEntryName;
      this.classpathEntryType = classpathEntryType;
   }

   public int getProblemId() {
      return this.accessRule.getProblemId();
   }

   public boolean ignoreIfBetter() {
      return this.accessRule.ignoreIfBetter();
   }
}
