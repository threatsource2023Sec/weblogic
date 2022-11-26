package com.bea.util.jam.internal.parser.tree;

import java.util.HashMap;
import java.util.List;

public class JavaTreeAnalyser extends JavaTreeConsts {
   private static final JavaTreeParser jtParser = new JavaTreeParser();

   public static HashMap parse(String file, boolean verbose) {
      return jtParser.parse(file, verbose);
   }

   public static String getPackageName(HashMap tree) {
      return (String)tree.get(2);
   }

   public static String getSourceFile(HashMap tree) {
      return (String)tree.get(1);
   }

   public static String getQualifiedClassName(HashMap tree) {
      return (String)tree.get(5);
   }

   public static List getImportList(HashMap tree) {
      return (List)tree.get(3);
   }

   public static List getImportIsStaticList(HashMap tree) {
      return (List)tree.get(4);
   }
}
