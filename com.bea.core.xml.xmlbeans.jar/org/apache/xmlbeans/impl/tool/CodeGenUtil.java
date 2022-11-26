package org.apache.xmlbeans.impl.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.xmlbeans.SystemProperties;

public class CodeGenUtil {
   public static String DEFAULT_MEM_START = "8m";
   public static String DEFAULT_MEM_MAX = "256m";
   public static String DEFAULT_COMPILER = "javac";
   public static String DEFAULT_JAR = "jar";

   public static URI resolve(URI base, URI child) {
      URI ruri = base.resolve(child);
      if ("file".equals(ruri.getScheme()) && !child.equals(ruri) && base.getPath().startsWith("//") && !ruri.getPath().startsWith("//")) {
         String path = "///".concat(ruri.getPath());

         try {
            ruri = new URI("file", (String)null, path, ruri.getQuery(), ruri.getFragment());
         } catch (URISyntaxException var5) {
         }
      }

      return ruri;
   }

   static void addAllJavaFiles(List srcFiles, List args) {
      Iterator i = srcFiles.iterator();

      while(i.hasNext()) {
         File f = (File)i.next();
         if (!f.isDirectory()) {
            args.add(quoteAndEscapeFilename(f.getAbsolutePath()));
         } else {
            List inside = Arrays.asList(f.listFiles(new FileFilter() {
               public boolean accept(File file) {
                  return file.isFile() && file.getName().endsWith(".java") || file.isDirectory();
               }
            }));
            addAllJavaFiles(inside, args);
         }
      }

   }

   private static String quoteAndEscapeFilename(String filename) {
      return filename.indexOf(" ") < 0 ? filename : "\"" + filename.replaceAll("\\\\", "\\\\\\\\") + "\"";
   }

   private static String quoteNoEscapeFilename(String filename) {
      return filename.indexOf(" ") >= 0 && File.separatorChar != '/' ? "\"" + filename + "\"" : filename;
   }

   /** @deprecated */
   public static boolean externalCompile(List srcFiles, File outdir, File[] cp, boolean debug) {
      return externalCompile(srcFiles, outdir, cp, debug, DEFAULT_COMPILER, (String)null, DEFAULT_MEM_START, DEFAULT_MEM_MAX, false, false);
   }

   public static boolean externalCompile(List srcFiles, File outdir, File[] cp, boolean debug, String javacPath, String memStart, String memMax, boolean quiet, boolean verbose) {
      return externalCompile(srcFiles, outdir, cp, debug, javacPath, (String)null, memStart, memMax, quiet, verbose);
   }

   public static boolean externalCompile(List srcFiles, File outdir, File[] cp, boolean debug, String javacPath, String genver, String memStart, String memMax, boolean quiet, boolean verbose) {
      List args = new ArrayList();
      File javac = findJavaTool(javacPath == null ? DEFAULT_COMPILER : javacPath);

      assert javac.exists() : "compiler not found " + javac;

      args.add(javac.getAbsolutePath());
      if (outdir == null) {
         outdir = new File(".");
      } else {
         args.add("-d");
         args.add(quoteAndEscapeFilename(outdir.getAbsolutePath()));
      }

      if (cp == null) {
         cp = systemClasspath();
      }

      if (cp.length > 0) {
         StringBuffer classPath = new StringBuffer();
         classPath.append(outdir.getAbsolutePath());

         for(int i = 0; i < cp.length; ++i) {
            classPath.append(File.pathSeparator);
            classPath.append(cp[i].getAbsolutePath());
         }

         args.add("-classpath");
         args.add(quoteAndEscapeFilename(classPath.toString()));
      }

      if (genver == null) {
         genver = "1.4";
      }

      args.add("-source");
      args.add(genver);
      args.add("-target");
      args.add(genver);
      args.add(debug ? "-g" : "-g:none");
      if (verbose) {
         args.add("-verbose");
      }

      addAllJavaFiles(srcFiles, args);
      File clFile = null;

      try {
         clFile = File.createTempFile("javac", "");
         FileWriter fw = new FileWriter(clFile);
         Iterator i = args.iterator();
         i.next();

         while(i.hasNext()) {
            String arg = (String)i.next();
            fw.write(arg);
            fw.write(10);
         }

         fw.close();
         List newargs = new ArrayList();
         newargs.add(args.get(0));
         if (memStart != null && memStart.length() != 0) {
            newargs.add("-J-Xms" + memStart);
         }

         if (memMax != null && memMax.length() != 0) {
            newargs.add("-J-Xmx" + memMax);
         }

         newargs.add("@" + clFile.getAbsolutePath());
         args = newargs;
      } catch (Exception var20) {
         System.err.println("Could not create command-line file for javac");
      }

      try {
         String[] strArgs = (String[])((String[])args.toArray(new String[args.size()]));
         if (verbose) {
            System.out.print("compile command:");

            for(int i = 0; i < strArgs.length; ++i) {
               System.out.print(" " + strArgs[i]);
            }

            System.out.println();
         }

         Process proc = Runtime.getRuntime().exec(strArgs);
         StringBuffer errorBuffer = new StringBuffer();
         StringBuffer outputBuffer = new StringBuffer();
         new ThreadedReader(proc.getInputStream(), outputBuffer);
         new ThreadedReader(proc.getErrorStream(), errorBuffer);
         proc.waitFor();
         if (verbose || proc.exitValue() != 0) {
            if (outputBuffer.length() > 0) {
               System.out.println(outputBuffer.toString());
               System.out.flush();
            }

            if (errorBuffer.length() > 0) {
               System.err.println(errorBuffer.toString());
               System.err.flush();
            }

            if (proc.exitValue() != 0) {
               return false;
            }
         }
      } catch (Throwable var19) {
         System.err.println(var19.toString());
         System.err.println(var19.getCause());
         var19.printStackTrace(System.err);
         return false;
      }

      if (clFile != null) {
         clFile.delete();
      }

      return true;
   }

   public static File[] systemClasspath() {
      List cp = new ArrayList();
      String[] systemcp = SystemProperties.getProperty("java.class.path").split(File.pathSeparator);

      for(int i = 0; i < systemcp.length; ++i) {
         cp.add(new File(systemcp[i]));
      }

      return (File[])((File[])cp.toArray(new File[cp.size()]));
   }

   /** @deprecated */
   public static boolean externalJar(File srcdir, File outfile) {
      return externalJar(srcdir, outfile, DEFAULT_JAR, false, false);
   }

   /** @deprecated */
   public static boolean externalJar(File srcdir, File outfile, String jarPath, boolean quiet, boolean verbose) {
      List args = new ArrayList();
      File jar = findJavaTool(jarPath == null ? DEFAULT_JAR : jarPath);

      assert jar.exists() : "jar not found " + jar;

      args.add(jar.getAbsolutePath());
      args.add("cf");
      args.add(quoteNoEscapeFilename(outfile.getAbsolutePath()));
      args.add("-C");
      args.add(quoteNoEscapeFilename(srcdir.getAbsolutePath()));
      args.add(".");

      try {
         String[] strArgs = (String[])((String[])args.toArray(new String[args.size()]));
         if (verbose) {
            System.out.print("jar command:");

            for(int i = 0; i < strArgs.length; ++i) {
               System.out.print(" " + strArgs[i]);
            }

            System.out.println();
         }

         Process proc = Runtime.getRuntime().exec(strArgs);
         StringBuffer errorBuffer = new StringBuffer();
         StringBuffer outputBuffer = new StringBuffer();
         new ThreadedReader(proc.getInputStream(), outputBuffer);
         new ThreadedReader(proc.getErrorStream(), errorBuffer);
         proc.waitFor();
         if (verbose || proc.exitValue() != 0) {
            if (outputBuffer.length() > 0) {
               System.out.println(outputBuffer.toString());
               System.out.flush();
            }

            if (errorBuffer.length() > 0) {
               System.err.println(errorBuffer.toString());
               System.err.flush();
            }

            if (proc.exitValue() != 0) {
               return false;
            }
         }

         return true;
      } catch (Throwable var13) {
         var13.printStackTrace(System.err);
         return false;
      }
   }

   private static File findJavaTool(String tool) {
      File toolFile = new File(tool);
      if (toolFile.isFile()) {
         return toolFile;
      } else {
         File result = new File(tool + ".exe");
         if (result.isFile()) {
            return result;
         } else {
            String home = SystemProperties.getProperty("java.home");
            String sep = File.separator;
            result = new File(home + sep + ".." + sep + "bin", tool);
            if (result.isFile()) {
               return result;
            } else {
               result = new File(result.getPath() + ".exe");
               if (result.isFile()) {
                  return result;
               } else {
                  result = new File(home + sep + "bin", tool);
                  if (result.isFile()) {
                     return result;
                  } else {
                     result = new File(result.getPath() + ".exe");
                     return result.isFile() ? result : toolFile;
                  }
               }
            }
         }
      }
   }

   private static class ThreadedReader {
      public ThreadedReader(InputStream stream, final StringBuffer output) {
         final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
         Thread readerThread = new Thread(new Runnable() {
            public void run() {
               while(true) {
                  try {
                     String s;
                     if ((s = reader.readLine()) != null) {
                        output.append(s + "\n");
                        continue;
                     }
                  } catch (Exception var3) {
                  }

                  return;
               }
            }
         });
         readerThread.start();
      }
   }
}
