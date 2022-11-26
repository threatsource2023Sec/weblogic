package com.bea.core.repackaged.jdt.core.compiler;

import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblem;

public abstract class CategorizedProblem implements IProblem {
   public static final int CAT_UNSPECIFIED = 0;
   public static final int CAT_BUILDPATH = 10;
   public static final int CAT_SYNTAX = 20;
   public static final int CAT_IMPORT = 30;
   public static final int CAT_TYPE = 40;
   public static final int CAT_MEMBER = 50;
   public static final int CAT_INTERNAL = 60;
   public static final int CAT_JAVADOC = 70;
   public static final int CAT_CODE_STYLE = 80;
   public static final int CAT_POTENTIAL_PROGRAMMING_PROBLEM = 90;
   public static final int CAT_NAME_SHADOWING_CONFLICT = 100;
   public static final int CAT_DEPRECATION = 110;
   public static final int CAT_UNNECESSARY_CODE = 120;
   public static final int CAT_UNCHECKED_RAW = 130;
   public static final int CAT_NLS = 140;
   public static final int CAT_RESTRICTION = 150;
   public static final int CAT_MODULE = 160;
   public static final int CAT_COMPLIANCE = 170;

   public abstract int getCategoryID();

   public abstract String getMarkerType();

   public String[] getExtraMarkerAttributeNames() {
      return CharOperation.NO_STRINGS;
   }

   public Object[] getExtraMarkerAttributeValues() {
      return DefaultProblem.EMPTY_VALUES;
   }

   public boolean isInfo() {
      return false;
   }
}
