package com.bea.core.repackaged.jdt.internal.compiler.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
   public static String[] find(File f, String pattern) {
      List files = new ArrayList();
      find0(f, pattern, files);
      String[] result = new String[files.size()];
      files.toArray(result);
      return result;
   }

   private static void find0(File f, String pattern, List collector) {
      if (f.isDirectory()) {
         String[] files = f.list();
         if (files == null) {
            return;
         }

         int i = 0;

         for(int max = files.length; i < max; ++i) {
            File current = new File(f, files[i]);
            if (current.isDirectory()) {
               find0(current, pattern, collector);
            } else {
               String name = current.getName().toLowerCase();
               if (name.endsWith(pattern)) {
                  if (name.endsWith("module-info.java")) {
                     collector.add(0, current.getAbsolutePath());
                  } else {
                     collector.add(current.getAbsolutePath());
                  }
               }
            }
         }
      }

   }
}
