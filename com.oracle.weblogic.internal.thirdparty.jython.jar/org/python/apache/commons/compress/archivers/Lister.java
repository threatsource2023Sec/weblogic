package org.python.apache.commons.compress.archivers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public final class Lister {
   private static final ArchiveStreamFactory factory = new ArchiveStreamFactory();

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         usage();
      } else {
         System.out.println("Analysing " + args[0]);
         File f = new File(args[0]);
         if (!f.isFile()) {
            System.err.println(f + " doesn't exist or is a directory");
         }

         InputStream fis = new BufferedInputStream(Files.newInputStream(f.toPath()));
         Throwable var3 = null;

         try {
            ArchiveInputStream ais = createArchiveInputStream(args, fis);
            Throwable var5 = null;

            try {
               System.out.println("Created " + ais.toString());

               ArchiveEntry ae;
               while((ae = ais.getNextEntry()) != null) {
                  System.out.println(ae.getName());
               }
            } catch (Throwable var28) {
               var5 = var28;
               throw var28;
            } finally {
               if (ais != null) {
                  if (var5 != null) {
                     try {
                        ais.close();
                     } catch (Throwable var27) {
                        var5.addSuppressed(var27);
                     }
                  } else {
                     ais.close();
                  }
               }

            }
         } catch (Throwable var30) {
            var3 = var30;
            throw var30;
         } finally {
            if (fis != null) {
               if (var3 != null) {
                  try {
                     fis.close();
                  } catch (Throwable var26) {
                     var3.addSuppressed(var26);
                  }
               } else {
                  fis.close();
               }
            }

         }

      }
   }

   private static ArchiveInputStream createArchiveInputStream(String[] args, InputStream fis) throws ArchiveException {
      return args.length > 1 ? factory.createArchiveInputStream(args[1], fis) : factory.createArchiveInputStream(fis);
   }

   private static void usage() {
      System.out.println("Parameters: archive-name [archive-type]");
   }
}
