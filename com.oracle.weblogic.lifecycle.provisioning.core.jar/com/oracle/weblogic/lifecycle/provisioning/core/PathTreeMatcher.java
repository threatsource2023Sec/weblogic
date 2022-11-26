package com.oracle.weblogic.lifecycle.provisioning.core;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

public class PathTreeMatcher {
   private final Collection pathMatchers;
   private final Path root;

   public PathTreeMatcher(Path root, Collection pathMatchers) {
      this.root = root;
      this.pathMatchers = pathMatchers;
   }

   public Collection getIncludedPaths() throws IOException {
      Object returnValue;
      if (this.root != null && this.pathMatchers != null && !this.pathMatchers.isEmpty()) {
         Visitor visitor = new Visitor();
         Files.walkFileTree(this.root, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, visitor);
         returnValue = visitor.getMatchedPaths();
      } else {
         returnValue = Collections.emptySet();
      }

      return (Collection)returnValue;
   }

   private final class Visitor extends SimpleFileVisitor {
      private final Collection matchedPaths;

      private Visitor() {
         this.matchedPaths = new ArrayList();
      }

      public final Collection getMatchedPaths() {
         return this.matchedPaths;
      }

      public final FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
         if (file != null && PathTreeMatcher.this.pathMatchers != null && !PathTreeMatcher.this.pathMatchers.isEmpty()) {
            Collection matchedPaths = this.getMatchedPaths();

            assert matchedPaths != null;

            Iterator var4 = PathTreeMatcher.this.pathMatchers.iterator();

            while(var4.hasNext()) {
               PathMatcher pathMatcher = (PathMatcher)var4.next();
               if (pathMatcher != null && pathMatcher.matches(file)) {
                  matchedPaths.add(file);
                  break;
               }
            }
         }

         return FileVisitResult.CONTINUE;
      }

      // $FF: synthetic method
      Visitor(Object x1) {
         this();
      }
   }
}
