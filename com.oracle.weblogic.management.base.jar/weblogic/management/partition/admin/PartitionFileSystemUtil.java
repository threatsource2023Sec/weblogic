package weblogic.management.partition.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.utils.FileUtils;

public final class PartitionFileSystemUtil {
   private static final DebugLogger debugPFS = DebugLogger.getDebugLogger("DebugPartitionFileSystem");
   static final String DOMAIN_CONTENT_FILE = "domainDir.dat";
   static final String PARTITION_ID_FILE = "id.dat";
   static final int MAX_RETRIES = 5;
   static final int RETRY_WAIT_MILLIS = 1000;

   public static boolean domainContentFileExists() {
      Path domainContentPath = Paths.get(DomainDir.getInitInfoDir(), "domainDir.dat");
      return Files.exists(domainContentPath, new LinkOption[0]);
   }

   public static void createDomainContentFile() throws IOException {
      Path inDomainHome = Paths.get(DomainDir.getRootDir());
      Path domainContentPath = Paths.get(DomainDir.getInitInfoDir(), "domainDir.dat");
      Collection excludedDomainDirs = new ArrayList(10);
      FileSystem fileSystem = FileSystems.getDefault();

      assert fileSystem != null;

      excludedDomainDirs.add(fileSystem.getPathMatcher("glob:nodemanager"));
      excludedDomainDirs.add(fileSystem.getPathMatcher("glob:bin/nodemanager"));
      excludedDomainDirs.add(fileSystem.getPathMatcher("glob:console-ext"));
      excludedDomainDirs.add(fileSystem.getPathMatcher("glob:partitions"));
      excludedDomainDirs.add(fileSystem.getPathMatcher("glob:config/partitions"));
      excludedDomainDirs.add(fileSystem.getPathMatcher("glob:config/deployments/*"));
      excludedDomainDirs.add(fileSystem.getPathMatcher("glob:servers/*/tmp/*"));
      DomainDirectoryVisitor visitor = null;
      boolean retryRead = true;

      for(int retryCount = 1; retryRead && retryCount <= 5; ++retryCount) {
         try {
            visitor = new DomainDirectoryVisitor(inDomainHome, excludedDomainDirs);
            Files.walkFileTree(inDomainHome, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, visitor);
            retryRead = false;
         } catch (IOException var14) {
            if (debugPFS.isDebugEnabled()) {
               debugPFS.debug("createDomainContentFile - read attempt " + retryCount + " threw exception: " + var14.toString());
            }

            if (retryCount == 5) {
               throw var14;
            }

            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var12) {
            }
         }
      }

      Collection filteredDomainDirs = visitor.getDomainDirs();
      if (filteredDomainDirs != null && filteredDomainDirs.size() >= 10) {
         boolean retryWrite = true;

         for(int retryCount = 1; retryWrite && retryCount <= 5; ++retryCount) {
            try {
               writeDomainDirData(domainContentPath, filteredDomainDirs);
               retryWrite = false;
            } catch (IOException var13) {
               if (debugPFS.isDebugEnabled()) {
                  debugPFS.debug("createDomainContentFile - write attempt " + retryCount + " threw exception: " + var13.toString());
               }

               if (retryCount == 5) {
                  throw var13;
               }

               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var11) {
               }
            }
         }

      } else {
         throw new AssertionError("Unable to determine a valid list of directories for the current domain home.");
      }
   }

   private static final void writeDomainDirData(Path domainContentFilePath, Collection directories) throws IOException {
      BufferedWriter writer = null;

      try {
         writer = Files.newBufferedWriter(domainContentFilePath, StandardCharsets.UTF_8);
         if (directories != null) {
            Iterator var3 = directories.iterator();

            while(var3.hasNext()) {
               Path directory = (Path)var3.next();
               if (directory != null && directory.toString().length() > 0) {
                  writer.write(directory.toString() + "\n");
               }
            }
         }
      } catch (IOException var8) {
         throw var8;
      } finally {
         if (writer != null) {
            writer.close();
            writer = null;
         }

      }

   }

   public static File getPartitionIDFile(String pfsRoot) {
      File pfsRootFile = new File(pfsRoot);
      return new File(pfsRootFile.getParent(), "id.dat");
   }

   public static File getPartitionConfigIDFile(String pfsConfigRoot) {
      File pfsRootFile = new File(pfsConfigRoot);
      return new File(pfsRootFile, "id.dat");
   }

   public static String getID(String pfsRoot) throws IOException {
      File partitionIDFile = getPartitionIDFile(pfsRoot);
      FileReader fr = null;
      String return_value = null;

      try {
         fr = new FileReader(partitionIDFile);

         String idEntry;
         for(BufferedReader br = new BufferedReader(fr); (idEntry = br.readLine()) != null; return_value = idEntry) {
         }
      } catch (IOException var9) {
         throw new IOException("Error reading partition ID file.", var9);
      } finally {
         if (fr != null) {
            fr.close();
         }

      }

      return return_value;
   }

   public static void createIDFile(String pfsRoot, String partitionID) throws IOException {
      String partitionIDFileName = getPartitionIDFile(pfsRoot).getPath();
      FileUtils.writeToFile(partitionID.getBytes(), partitionIDFileName);
   }

   public static boolean createPartitionFileSystem(String pfsRoot, String partitionName) throws IOException {
      return createPartitionFileSystem(pfsRoot, partitionName, true);
   }

   public static boolean createPartitionFileSystem(String pfsRoot, String partitionName, boolean useConfigDir) throws IOException {
      File domainContentFile = new File(DomainDir.getInitInfoDir(), "domainDir.dat");
      if (!domainContentFile.exists()) {
         return false;
      } else {
         FileReader fr = null;
         boolean return_value = true;

         try {
            fr = new FileReader(domainContentFile);
            BufferedReader br = new BufferedReader(fr);
            String configPrefix = "config" + File.separator;

            String dirEntry;
            File dirEntryFile;
            for(int offset = configPrefix.length(); (dirEntry = br.readLine()) != null; dirEntryFile.mkdirs()) {
               if (useConfigDir && dirEntry.startsWith(configPrefix)) {
                  dirEntryFile = new File(DomainDir.getConfigDir() + File.separator + "partitions" + File.separator + partitionName + File.separator + dirEntry.substring(offset));
               } else {
                  dirEntryFile = new File(pfsRoot + File.separator + dirEntry);
               }
            }
         } catch (IOException var15) {
            throw var15;
         } catch (IndexOutOfBoundsException var16) {
            return_value = false;
         } finally {
            if (fr != null) {
               fr.close();
            }

         }

         return return_value;
      }
   }

   public static void backupPartitionFileSystem(String pfsRoot, String partitionID) throws IOException {
      File pfsDirectory = (new File(pfsRoot)).getParentFile();
      File pfsBackupDirectory = new File(pfsDirectory.getParentFile(), pfsDirectory.getName() + partitionID);
      Path sourcePath = pfsDirectory.toPath();
      Path targetPath = pfsBackupDirectory.toPath();
      boolean retry = true;

      for(int retryCount = 0; retry && retryCount < 10; ++retryCount) {
         try {
            Files.move(sourcePath, targetPath);
            retry = false;
         } catch (IOException var12) {
            if (debugPFS.isDebugEnabled()) {
               debugPFS.debug("backupPartitionFileSystem - move attempt " + retryCount + " threw exception: " + var12.toString());
            }

            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var10) {
            }
         }
      }

      if (retry) {
         try {
            Files.walkFileTree(sourcePath, new PFSCopyFileVisitor(sourcePath, targetPath));
            Files.walkFileTree(sourcePath, new PFSDeleteDirVisitor());
         } catch (Exception var11) {
            if (debugPFS.isDebugEnabled()) {
               debugPFS.debug("backupPartitionFileSystem - copy attempt threw exception: " + var11.toString());
            }
         }
      }

   }

   public static void backupPartitionConfigFileSystem(String partitionName, String partitionID) throws IOException {
      File pfsConfigDirectory = new File(DomainDir.getConfigDir() + File.separator + "partitions" + File.separator + partitionName);
      File pfsBackupDirectory = new File(pfsConfigDirectory.getParentFile(), pfsConfigDirectory.getName() + partitionID);
      Path sourcePath = pfsConfigDirectory.toPath();
      Path targetPath = pfsBackupDirectory.toPath();
      boolean retry = false;

      try {
         Files.move(sourcePath, targetPath);
      } catch (IOException var10) {
         if (debugPFS.isDebugEnabled()) {
            debugPFS.debug("backupPartitionConfigFileSystem - first move attempt threw exception: " + var10.toString());
         }

         retry = true;
      }

      if (retry) {
         try {
            Thread.sleep(1000L);
            Files.move(sourcePath, targetPath);
            retry = false;
         } catch (InterruptedException var8) {
         } catch (IOException var9) {
            if (debugPFS.isDebugEnabled()) {
               debugPFS.debug("backupPartitionConfigFileSystem - second move attempt threw exception: " + var9.toString());
            }
         }
      }

      if (retry) {
         Files.walkFileTree(sourcePath, new PFSCopyFileVisitor(sourcePath, targetPath));
         Files.walkFileTree(sourcePath, new PFSDeleteDirVisitor());
      }

   }

   public static void deletePartitionFileSystem(String pfsRoot) throws IOException {
      try {
         File pfsDirectory = (new File(pfsRoot)).getParentFile();
         Files.walkFileTree(pfsDirectory.toPath(), new PFSDeleteDirVisitor());
      } catch (NoSuchFileException var2) {
      }

   }

   public static void deletePartitionConfigFileSystem(String partitionName) throws IOException {
      try {
         File pfsConfigDirectory = new File(DomainDir.getConfigDir() + File.separator + "partitions" + File.separator + partitionName);
         Files.walkFileTree(pfsConfigDirectory.toPath(), new PFSDeleteDirVisitor());
      } catch (NoSuchFileException var2) {
      }

   }

   private static final class DomainDirectoryVisitor extends SimpleFileVisitor {
      private final ArrayList domainDirs;
      private final Path relativeTo;
      private final Collection excludedPatterns;

      private DomainDirectoryVisitor(Path relativeTo, Collection excludedPatterns) {
         assert relativeTo != null;

         assert excludedPatterns != null;

         this.domainDirs = new ArrayList(100);
         this.relativeTo = relativeTo;
         this.excludedPatterns = excludedPatterns;
      }

      public final Collection getDomainDirs() {
         return this.domainDirs;
      }

      public final FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
         Path relative = this.relativeTo.relativize(directory);
         if (relative != null && !relative.toString().isEmpty()) {
            boolean excluded = false;
            Iterator var5 = this.excludedPatterns.iterator();

            while(var5.hasNext()) {
               PathMatcher pathMatcher = (PathMatcher)var5.next();
               if (pathMatcher != null && pathMatcher.matches(relative)) {
                  excluded = true;
                  break;
               }
            }

            if (excluded) {
               return FileVisitResult.SKIP_SUBTREE;
            }

            this.domainDirs.add(relative);
         }

         return FileVisitResult.CONTINUE;
      }

      public final FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
         if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
            PartitionFileSystemUtil.debugPFS.debug("DomainDirectoryVisitor encountered error while visiting " + file.toString() + ", exception: " + exc.toString());
         }

         return FileVisitResult.SKIP_SUBTREE;
      }

      // $FF: synthetic method
      DomainDirectoryVisitor(Path x0, Collection x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class PFSDeleteDirVisitor extends SimpleFileVisitor {
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
         boolean retry = false;

         try {
            Files.deleteIfExists(file);
         } catch (IOException var7) {
            if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
               PartitionFileSystemUtil.debugPFS.debug("PFSDeleteDirVisitor visitFile - first delete attempt threw exception: " + var7.toString());
            }

            retry = true;
         }

         if (retry) {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var5) {
            }

            try {
               Files.deleteIfExists(file);
            } catch (NoSuchFileException var6) {
               if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
                  PartitionFileSystemUtil.debugPFS.debug("PFSDeleteDirVisitor visitFile - delete retry threw ignored exception: " + var6.toString());
               }
            }
         }

         return FileVisitResult.CONTINUE;
      }

      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
         boolean retry = false;

         try {
            Files.deleteIfExists(dir);
         } catch (IOException var7) {
            if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
               PartitionFileSystemUtil.debugPFS.debug("PFSDeleteDirVisitor postVisitDirectory - first delete attempt threw exception: " + var7.toString());
            }

            retry = true;
         }

         if (retry) {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var5) {
            }

            try {
               Files.deleteIfExists(dir);
            } catch (NoSuchFileException var6) {
               if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
                  PartitionFileSystemUtil.debugPFS.debug("PFSDeleteDirVisitor postVisitDirectory - delete retry threw ignored exception: " + var6.toString());
               }
            }
         }

         return FileVisitResult.CONTINUE;
      }

      public FileVisitResult visitFileFailed(Path file, IOException exception) throws IOException {
         if (exception instanceof NoSuchFileException) {
            if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
               PartitionFileSystemUtil.debugPFS.debug("PFSDeleteDirVisitor visitFileFailed - ignoring exception: " + exception.toString());
            }

            return FileVisitResult.CONTINUE;
         } else {
            throw exception;
         }
      }
   }

   public static class PFSCopyFileVisitor extends SimpleFileVisitor {
      private Path sourcePath;
      private final Path targetPath;

      public PFSCopyFileVisitor(Path sourcePath, Path targetPath) {
         this.sourcePath = sourcePath;
         this.targetPath = targetPath;
      }

      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
         try {
            if (this.sourcePath == null) {
               this.sourcePath = dir;
            } else {
               Files.createDirectories(this.targetPath.resolve(this.sourcePath.relativize(dir)));
            }
         } catch (NoSuchFileException var4) {
            if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
               PartitionFileSystemUtil.debugPFS.debug("PFSCopyFileVisitor preVisitDirectory - createDirectories threw ignored exception: " + var4.toString());
            }
         }

         return FileVisitResult.CONTINUE;
      }

      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
         try {
            if (Files.exists(file, new LinkOption[0])) {
               Files.copy(file, this.targetPath.resolve(this.sourcePath.relativize(file)));
            }
         } catch (NoSuchFileException var4) {
            if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
               PartitionFileSystemUtil.debugPFS.debug("PFSCopyFileVisitor visitFile - copy threw ignored exception: " + var4.toString());
            }
         }

         return FileVisitResult.CONTINUE;
      }

      public FileVisitResult visitFileFailed(Path file, IOException exception) throws IOException {
         if (exception instanceof NoSuchFileException) {
            if (PartitionFileSystemUtil.debugPFS.isDebugEnabled()) {
               PartitionFileSystemUtil.debugPFS.debug("PFSCopyDirVisitor visitFileFailed - ignoring exception: " + exception.toString());
            }

            return FileVisitResult.CONTINUE;
         } else {
            throw exception;
         }
      }
   }
}
