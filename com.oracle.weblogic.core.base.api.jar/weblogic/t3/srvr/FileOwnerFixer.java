package weblogic.t3.srvr;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import weblogic.diagnostics.debug.DebugLogger;

public class FileOwnerFixer {
   private static FileOwnerFixer fof = new FileOwnerFixer();
   private static boolean isActive = supportsPosixFileAttributeView();
   private ConcurrentLinkedQueue paths = new ConcurrentLinkedQueue();
   private static final DebugLogger dbgLogger = DebugLogger.getDebugLogger("DebugFileOwnerFixer");
   int modifiedCount = 0;

   public static void addPathJDK6(File file) {
      if (file == null) {
         if (dbgLogger.isDebugEnabled()) {
            dbgLogger.debug("An attempt was made to register a null file", new Throwable());
         }
      } else {
         addPath(file.toPath());
      }

   }

   public static void addPath(Path path) {
      if (path == null) {
         if (dbgLogger.isDebugEnabled()) {
            dbgLogger.debug("An attempt was made to register a null path", new Throwable());
         }
      } else if (isActive && isStateOK("An attempt was made to add a path, " + path + ", after the ownerships have been fixed.")) {
         fof.doAddPath(path);
      }

   }

   public static int fixFileOwnerships(String uid, String gid) {
      if (isActive && isStateOK("An attempt to fix file ownership was made after ownerships were already fixed once.")) {
         int modifiedCount = fof.doFixFileOwnerships(uid, gid);
         fof = null;
         return modifiedCount;
      } else {
         return 0;
      }
   }

   private static boolean supportsPosixFileAttributeView() {
      return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
   }

   private void doAddPath(Path path) {
      if (this.thisAndAncestorsWereNeverAdded(path)) {
         this.paths.add(this.getEarliestNewAncestor(path));
      }

   }

   private Path getEarliestNewAncestor(Path path) {
      Path parent = path.getParent();
      return parent != null && !Files.exists(parent, new LinkOption[0]) ? this.getEarliestNewAncestor(parent) : path;
   }

   private boolean thisAndAncestorsWereNeverAdded(Path path) {
      Iterator var2 = this.paths.iterator();

      Path p;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         p = (Path)var2.next();
      } while(!path.startsWith(p));

      return false;
   }

   private static boolean isStateOK(String message) {
      if (fof == null && dbgLogger.isDebugEnabled()) {
         dbgLogger.debug(message, new Throwable());
      }

      return fof != null;
   }

   private int doFixFileOwnerships(String uid, String gid) {
      UserPrincipalLookupService upls = FileSystems.getDefault().getUserPrincipalLookupService();
      UserPrincipal up = this.getUserPrincipal(uid, upls);
      GroupPrincipal gp = this.getGroupPrincipal(gid, upls);
      if (this.paths.size() > 0) {
         Iterator var6 = this.getDistinctPaths((Path[])this.paths.toArray(new Path[this.paths.size()])).iterator();

         while(var6.hasNext()) {
            Path path = (Path)var6.next();
            if (Files.isDirectory(path, new LinkOption[0])) {
               this.fixTreeOwnership(up, gp, path);
            } else if (Files.exists(path, new LinkOption[0])) {
               this.fixPathOwnership(up, gp, path);
            }
         }
      }

      return this.modifiedCount;
   }

   protected List getDistinctPaths(Path... ps) {
      List distinctPaths = new LinkedList();
      boolean[] isDup = new boolean[ps.length];

      for(int i = 0; i < ps.length - 1; ++i) {
         if (!isDup[i]) {
            Path basePath = ps[i];
            boolean keepBasePath = true;

            for(int j = i + 1; j < ps.length; ++j) {
               Path otherPath = ps[j];
               if (basePath.startsWith(otherPath)) {
                  keepBasePath = false;
               } else if (otherPath.startsWith(basePath)) {
                  isDup[j] = true;
               }
            }

            if (keepBasePath) {
               distinctPaths.add(basePath);
            }
         }
      }

      if (!isDup[ps.length - 1]) {
         distinctPaths.add(ps[ps.length - 1]);
      }

      return distinctPaths;
   }

   private void fixTreeOwnership(final UserPrincipal up, final GroupPrincipal gp, Path path) {
      try {
         Files.walkFileTree(path, new SimpleFileVisitor() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               if (Files.exists(file, new LinkOption[0])) {
                  FileOwnerFixer.this.fixPathOwnership(up, gp, file);
                  return FileVisitResult.CONTINUE;
               } else {
                  return FileVisitResult.SKIP_SUBTREE;
               }
            }

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
               if (Files.exists(dir, new LinkOption[0])) {
                  FileOwnerFixer.this.fixPathOwnership(up, gp, dir);
               }

               return FileVisitResult.CONTINUE;
            }
         });
      } catch (IOException var5) {
         throw handleException(var5, "Unable to walk directory tree at " + path);
      }
   }

   private void fixPathOwnership(UserPrincipal up, GroupPrincipal gp, Path path) {
      try {
         Files.setOwner(path, up);
         ((PosixFileAttributeView)Files.getFileAttributeView(path, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)).setGroup(gp);
         ++this.modifiedCount;
      } catch (IOException var5) {
         throw handleException(var5, "Failed to set ownership for path " + path);
      }
   }

   private GroupPrincipal getGroupPrincipal(String gid, UserPrincipalLookupService upls) {
      try {
         return upls.lookupPrincipalByGroupName(gid);
      } catch (IOException var4) {
         throw handleException(var4, "Failed to retrieve group principal for group id " + gid);
      }
   }

   private UserPrincipal getUserPrincipal(String uid, UserPrincipalLookupService upls) {
      try {
         return upls.lookupPrincipalByName(uid);
      } catch (IOException var4) {
         throw handleException(var4, "Failed to retrieve user principal for user id " + uid);
      }
   }

   private static RuntimeException handleException(IOException e, String msg) {
      return new RuntimeException(msg, e);
   }
}
