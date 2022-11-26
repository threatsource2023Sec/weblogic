package com.sun.faces.application.resource;

import com.sun.faces.util.Util;
import java.util.ArrayDeque;
import java.util.Iterator;
import javax.faces.context.ExternalContext;

public class ResourcePathsIterator implements Iterator {
   private final int maxDepth;
   private final ExternalContext externalContext;
   private final String[] extensions;
   private final String[] restrictedDirectories;
   private final ArrayDeque stack = new ArrayDeque();
   private String next;

   public ResourcePathsIterator(String rootPath, int maxDepth, String[] extensions, String[] restrictedDirectories, ExternalContext externalContext) {
      this.maxDepth = maxDepth;
      this.externalContext = externalContext;
      this.extensions = extensions;
      this.restrictedDirectories = restrictedDirectories;
      this.visit(rootPath);
   }

   public boolean hasNext() {
      if (this.next != null) {
         return true;
      } else {
         this.tryTake();
         return this.next != null;
      }
   }

   public String next() {
      if (this.next == null) {
         this.tryTake();
      }

      String nextReturn = this.next;
      this.next = null;
      return nextReturn;
   }

   private void visit(String resourcePath) {
      this.stack.addAll(this.externalContext.getResourcePaths(resourcePath));
   }

   private void tryTake() {
      if (!this.stack.isEmpty()) {
         while(this.next == null && !this.stack.isEmpty()) {
            String nextCandidate = (String)this.stack.removeFirst();
            if (isDirectory(nextCandidate)) {
               if (!Util.startsWithOneOf(nextCandidate, this.restrictedDirectories) && !directoryExceedsMaxDepth(nextCandidate, (long)this.maxDepth)) {
                  this.visit(nextCandidate);
               }
            } else if (isValidCandidate(nextCandidate, this.extensions)) {
               this.next = nextCandidate;
            }
         }

      }
   }

   private static boolean isDirectory(String resourcePath) {
      return resourcePath.endsWith("/");
   }

   private static boolean directoryExceedsMaxDepth(String resourcePath, long max) {
      return resourcePath.chars().filter((i) -> {
         return i == 47;
      }).count() > max;
   }

   private static boolean isValidCandidate(String resourcePath, String[] extensions) {
      if (extensions != null && extensions.length != 0) {
         String[] var2 = extensions;
         int var3 = extensions.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String extension = var2[var4];
            if (resourcePath.endsWith(extension)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
