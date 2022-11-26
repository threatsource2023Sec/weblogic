package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class FileSystemUtils {
   public static boolean deleteRecursively(@Nullable File root) {
      if (root == null) {
         return false;
      } else {
         try {
            return deleteRecursively(root.toPath());
         } catch (IOException var2) {
            return false;
         }
      }
   }

   public static boolean deleteRecursively(@Nullable Path root) throws IOException {
      if (root == null) {
         return false;
      } else if (!Files.exists(root, new LinkOption[0])) {
         return false;
      } else {
         Files.walkFileTree(root, new SimpleFileVisitor() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               Files.delete(file);
               return FileVisitResult.CONTINUE;
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
               Files.delete(dir);
               return FileVisitResult.CONTINUE;
            }
         });
         return true;
      }
   }

   public static void copyRecursively(File src, File dest) throws IOException {
      Assert.notNull(src, (String)"Source File must not be null");
      Assert.notNull(dest, (String)"Destination File must not be null");
      copyRecursively(src.toPath(), dest.toPath());
   }

   public static void copyRecursively(final Path src, final Path dest) throws IOException {
      Assert.notNull(src, (String)"Source Path must not be null");
      Assert.notNull(dest, (String)"Destination Path must not be null");
      BasicFileAttributes srcAttr = Files.readAttributes(src, BasicFileAttributes.class);
      if (srcAttr.isDirectory()) {
         Files.walkFileTree(src, new SimpleFileVisitor() {
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
               Files.createDirectories(dest.resolve(src.relativize(dir)));
               return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
               return FileVisitResult.CONTINUE;
            }
         });
      } else {
         if (!srcAttr.isRegularFile()) {
            throw new IllegalArgumentException("Source File must denote a directory or file");
         }

         Files.copy(src, dest);
      }

   }
}
