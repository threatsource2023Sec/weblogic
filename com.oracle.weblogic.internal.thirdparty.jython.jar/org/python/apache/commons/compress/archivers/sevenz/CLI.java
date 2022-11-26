package org.python.apache.commons.compress.archivers.sevenz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class CLI {
   private static final byte[] BUF = new byte[8192];

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         usage();
      } else {
         Mode mode = grabMode(args);
         System.out.println(mode.getMessage() + " " + args[0]);
         File f = new File(args[0]);
         if (!f.isFile()) {
            System.err.println(f + " doesn't exist or is a directory");
         }

         SevenZFile archive = new SevenZFile(f);
         Throwable var4 = null;

         try {
            SevenZArchiveEntry ae;
            try {
               while((ae = archive.getNextEntry()) != null) {
                  mode.takeAction(archive, ae);
               }
            } catch (Throwable var13) {
               var4 = var13;
               throw var13;
            }
         } finally {
            if (archive != null) {
               if (var4 != null) {
                  try {
                     archive.close();
                  } catch (Throwable var12) {
                     var4.addSuppressed(var12);
                  }
               } else {
                  archive.close();
               }
            }

         }

      }
   }

   private static void usage() {
      System.out.println("Parameters: archive-name [list|extract]");
   }

   private static Mode grabMode(String[] args) {
      return args.length < 2 ? CLI.Mode.LIST : (Mode)Enum.valueOf(Mode.class, args[1].toUpperCase());
   }

   private static enum Mode {
      LIST("Analysing") {
         public void takeAction(SevenZFile archive, SevenZArchiveEntry entry) {
            System.out.print(entry.getName());
            if (entry.isDirectory()) {
               System.out.print(" dir");
            } else {
               System.out.print(" " + entry.getCompressedSize() + "/" + entry.getSize());
            }

            if (entry.getHasLastModifiedDate()) {
               System.out.print(" " + entry.getLastModifiedDate());
            } else {
               System.out.print(" no last modified date");
            }

            if (!entry.isDirectory()) {
               System.out.println(" " + this.getContentMethods(entry));
            } else {
               System.out.println("");
            }

         }

         private String getContentMethods(SevenZArchiveEntry entry) {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            Iterator var4 = entry.getContentMethods().iterator();

            while(var4.hasNext()) {
               SevenZMethodConfiguration m = (SevenZMethodConfiguration)var4.next();
               if (!first) {
                  sb.append(", ");
               }

               first = false;
               sb.append(m.getMethod());
               if (m.getOptions() != null) {
                  sb.append("(").append(m.getOptions()).append(")");
               }
            }

            return sb.toString();
         }
      },
      EXTRACT("Extracting") {
         public void takeAction(SevenZFile archive, SevenZArchiveEntry entry) throws IOException {
            File outFile = new File(entry.getName());
            if (entry.isDirectory()) {
               if (!outFile.isDirectory() && !outFile.mkdirs()) {
                  throw new IOException("Cannot create directory " + outFile);
               } else {
                  System.out.println("created directory " + outFile);
               }
            } else {
               System.out.println("extracting to " + outFile);
               File parent = outFile.getParentFile();
               if (parent != null && !parent.exists() && !parent.mkdirs()) {
                  throw new IOException("Cannot create " + parent);
               } else {
                  FileOutputStream fos = new FileOutputStream(outFile);
                  Throwable var6 = null;

                  try {
                     long total = entry.getSize();
                     long off = 0L;

                     while(off < total) {
                        int toRead = (int)Math.min(total - off, (long)CLI.BUF.length);
                        int bytesRead = archive.read(CLI.BUF, 0, toRead);
                        if (bytesRead < 1) {
                           throw new IOException("reached end of entry " + entry.getName() + " after " + off + " bytes, expected " + total);
                        }

                        off += (long)bytesRead;
                        fos.write(CLI.BUF, 0, bytesRead);
                     }
                  } catch (Throwable var21) {
                     var6 = var21;
                     throw var21;
                  } finally {
                     if (fos != null) {
                        if (var6 != null) {
                           try {
                              fos.close();
                           } catch (Throwable var20) {
                              var6.addSuppressed(var20);
                           }
                        } else {
                           fos.close();
                        }
                     }

                  }

               }
            }
         }
      };

      private final String message;

      private Mode(String message) {
         this.message = message;
      }

      public String getMessage() {
         return this.message;
      }

      public abstract void takeAction(SevenZFile var1, SevenZArchiveEntry var2) throws IOException;

      // $FF: synthetic method
      Mode(String x2, Object x3) {
         this(x2);
      }
   }
}
