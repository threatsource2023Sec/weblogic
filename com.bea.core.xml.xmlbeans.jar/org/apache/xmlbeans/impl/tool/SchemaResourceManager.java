package org.apache.xmlbeans.impl.tool;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.xmlbeans.impl.common.IOUtil;

public class SchemaResourceManager extends BaseSchemaResourceManager {
   private File _directory;

   public static void printUsage() {
      System.out.println("Maintains \"xsdownload.xml\", an index of locally downloaded .xsd files");
      System.out.println("usage: sdownload [-dir directory] [-refresh] [-recurse] [-sync] [url/file...]");
      System.out.println("");
      System.out.println("URLs that are specified are downloaded if they aren't already cached.");
      System.out.println("In addition:");
      System.out.println("  -dir specifies the directory for the xsdownload.xml file (default .).");
      System.out.println("  -sync synchronizes the index to any local .xsd files in the tree.");
      System.out.println("  -recurse recursively downloads imported and included .xsd files.");
      System.out.println("  -refresh redownloads all indexed .xsd files.");
      System.out.println("If no files or URLs are specified, all indexed files are relevant.");
   }

   public static void main(String[] args) throws IOException {
      if (args.length == 0) {
         printUsage();
         System.exit(0);
      } else {
         Set flags = new HashSet();
         flags.add("h");
         flags.add("help");
         flags.add("usage");
         flags.add("license");
         flags.add("version");
         flags.add("sync");
         flags.add("refresh");
         flags.add("recurse");
         Set opts = new HashSet();
         opts.add("dir");
         CommandLine cl = new CommandLine(args, flags, opts);
         if (cl.getOpt("h") == null && cl.getOpt("help") == null && cl.getOpt("usage") == null) {
            String[] badopts = cl.getBadOpts();
            if (badopts.length <= 0) {
               if (cl.getOpt("license") != null) {
                  CommandLine.printLicense();
                  System.exit(0);
               } else if (cl.getOpt("version") != null) {
                  CommandLine.printVersion();
                  System.exit(0);
               } else {
                  args = cl.args();
                  boolean sync = cl.getOpt("sync") != null;
                  boolean refresh = cl.getOpt("refresh") != null;
                  boolean imports = cl.getOpt("recurse") != null;
                  String dir = cl.getOpt("dir");
                  if (dir == null) {
                     dir = ".";
                  }

                  File directory = new File(dir);

                  SchemaResourceManager mgr;
                  try {
                     mgr = new SchemaResourceManager(directory);
                  } catch (IllegalStateException var16) {
                     if (var16.getMessage() != null) {
                        System.out.println(var16.getMessage());
                     } else {
                        var16.printStackTrace();
                     }

                     System.exit(1);
                     return;
                  }

                  List uriList = new ArrayList();
                  List fileList = new ArrayList();

                  for(int i = 0; i < args.length; ++i) {
                     if (looksLikeURL(args[i])) {
                        uriList.add(args[i]);
                     } else {
                        fileList.add(new File(directory, args[i]));
                     }
                  }

                  Iterator i = fileList.iterator();

                  while(i.hasNext()) {
                     File file = (File)i.next();
                     if (!isInDirectory(file, directory)) {
                        System.err.println("File not within directory: " + file);
                        i.remove();
                     }
                  }

                  List fileList = collectXSDFiles((File[])((File[])fileList.toArray(new File[0])));
                  String[] uris = (String[])((String[])uriList.toArray(new String[0]));
                  File[] files = (File[])((File[])fileList.toArray(new File[0]));
                  String[] filenames = relativeFilenames(files, directory);
                  if (uris.length + filenames.length > 0) {
                     mgr.process(uris, filenames, sync, refresh, imports);
                  } else {
                     mgr.processAll(sync, refresh, imports);
                  }

                  mgr.writeCache();
                  System.exit(0);
               }
            } else {
               for(int i = 0; i < badopts.length; ++i) {
                  System.out.println("Unrecognized option: " + badopts[i]);
               }

               printUsage();
               System.exit(0);
            }
         } else {
            printUsage();
            System.exit(0);
         }
      }
   }

   private static boolean looksLikeURL(String str) {
      return str.startsWith("http:") || str.startsWith("https:") || str.startsWith("ftp:") || str.startsWith("file:");
   }

   private static String relativeFilename(File file, File directory) {
      return file != null && !file.equals(directory) ? relativeFilename(file.getParentFile(), directory) + "/" + file.getName() : ".";
   }

   private static String[] relativeFilenames(File[] files, File directory) {
      String[] result = new String[files.length];

      for(int i = 0; i < files.length; ++i) {
         result[i] = relativeFilename(files[i], directory);
      }

      return result;
   }

   private static boolean isInDirectory(File file, File dir) {
      if (file == null) {
         return false;
      } else {
         return file.equals(dir) ? true : isInDirectory(file.getParentFile(), dir);
      }
   }

   public SchemaResourceManager(File directory) {
      this._directory = directory;
      this.init();
   }

   protected void warning(String msg) {
      System.out.println(msg);
   }

   protected boolean fileExists(String filename) {
      return (new File(this._directory, filename)).exists();
   }

   protected InputStream inputStreamForFile(String filename) throws IOException {
      return new FileInputStream(new File(this._directory, filename));
   }

   protected void writeInputStreamToFile(InputStream input, String filename) throws IOException {
      File targetFile = new File(this._directory, filename);
      File parent = targetFile.getParentFile();
      if (!parent.exists()) {
         parent.mkdirs();
      }

      OutputStream output = new FileOutputStream(targetFile);
      IOUtil.copyCompletely((InputStream)input, (OutputStream)output);
   }

   protected void deleteFile(String filename) {
      (new File(this._directory, filename)).delete();
   }

   protected String[] getAllXSDFilenames() {
      File[] allFiles = (File[])((File[])collectXSDFiles(new File[]{this._directory}).toArray(new File[0]));
      return relativeFilenames(allFiles, this._directory);
   }

   private static List collectXSDFiles(File[] dirs) {
      List files = new ArrayList();

      for(int i = 0; i < dirs.length; ++i) {
         File f = dirs[i];
         if (!f.isDirectory()) {
            files.add(f);
         } else {
            files.addAll(collectXSDFiles(f.listFiles(new FileFilter() {
               public boolean accept(File file) {
                  return file.isDirectory() || file.isFile() && file.getName().endsWith(".xsd");
               }
            })));
         }
      }

      return files;
   }
}
