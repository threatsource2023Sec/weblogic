package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.TemporaryDirectory;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.Factory;

public class TemporaryDirectoryFactory implements Factory {
   private final TemporaryDirectory temporaryDirectoryAnnotation;

   TemporaryDirectoryFactory(TemporaryDirectory temporaryDirectoryAnnotation) {
      Objects.requireNonNull(temporaryDirectoryAnnotation);
      this.temporaryDirectoryAnnotation = temporaryDirectoryAnnotation;
   }

   public final Path provide() {
      String className = TemporaryDirectoryFactory.class.getName();
      String methodName = "provide";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "provide");
      }

      TemporaryDirectory td = this.temporaryDirectoryAnnotation;

      assert td != null;

      String prefix = td.prefix();
      if (prefix != null) {
         prefix = prefix.trim();
         if (prefix.isEmpty()) {
            prefix = null;
         }
      }

      Path returnValue;
      try {
         returnValue = Files.createTempDirectory(prefix);
      } catch (IOException var8) {
         throw new IllegalStateException(var8);
      }

      assert returnValue != null;

      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, className, "provide", "Created temporary directory: {0}", returnValue);
      }

      File temporaryDirectory = returnValue.toFile();

      assert temporaryDirectory != null;

      temporaryDirectory.deleteOnExit();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "provide", returnValue);
      }

      return returnValue;
   }

   public final void dispose(Path killMe) {
      String className = TemporaryDirectoryFactory.class.getName();
      String methodName = "dispose";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "dispose", killMe);
      }

      Objects.requireNonNull(killMe);
      if (!Files.isDirectory(killMe, new LinkOption[0])) {
         throw new IllegalArgumentException("!Files.isDirectory(killMe): " + killMe);
      } else {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "dispose", "Deleting {0}...", killMe);
         }

         try {
            Files.walkFileTree(killMe, new SimpleFileVisitor() {
               public final FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                  FileVisitResult superResult = super.visitFile(file, attributes);
                  Files.delete(file);
                  return superResult;
               }

               public final FileVisitResult postVisitDirectory(Path directory, IOException directoryIterationFailure) throws IOException {
                  FileVisitResult superResult = super.postVisitDirectory(directory, directoryIterationFailure);
                  Files.delete(directory);
                  return superResult;
               }
            });
         } catch (IOException var6) {
            throw new IllegalStateException(var6);
         }

         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "dispose", "...Deleted {0}", killMe);
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "dispose");
         }

      }
   }
}
