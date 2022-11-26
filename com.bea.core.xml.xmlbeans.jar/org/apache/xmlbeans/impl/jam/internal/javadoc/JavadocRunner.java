package org.apache.xmlbeans.impl.jam.internal.javadoc;

import com.sun.javadoc.Doclet;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.xmlbeans.impl.jam.provider.JamLogger;

public class JavadocRunner extends Doclet {
   private static final String JAVADOC_RUNNER_150 = "org.apache.xmlbeans.impl.jam.internal.javadoc.JavadocRunner_150";

   public static JavadocRunner newInstance() {
      try {
         Class.forName("com.sun.javadoc.AnnotationDesc");
      } catch (ClassNotFoundException var4) {
         return new JavadocRunner();
      }

      try {
         Class onefive = Class.forName("org.apache.xmlbeans.impl.jam.internal.javadoc.JavadocRunner_150");
         return (JavadocRunner)onefive.newInstance();
      } catch (ClassNotFoundException var1) {
      } catch (IllegalAccessException var2) {
      } catch (InstantiationException var3) {
      }

      return new JavadocRunner();
   }

   synchronized RootDoc run(File[] files, PrintWriter out, String sourcePath, String classPath, String[] javadocArgs, JamLogger logger) throws IOException, FileNotFoundException {
      if (files != null && files.length != 0) {
         List argList = new ArrayList();
         if (javadocArgs != null) {
            argList.addAll(Arrays.asList(javadocArgs));
         }

         argList.add("-private");
         if (sourcePath != null) {
            argList.add("-sourcepath");
            argList.add(sourcePath);
         }

         if (classPath != null) {
            argList.add("-classpath");
            argList.add(classPath);
            argList.add("-docletpath");
            argList.add(classPath);
         }

         for(int i = 0; i < files.length; ++i) {
            argList.add(files[i].toString());
            if (out != null) {
               out.println(files[i].toString());
            }
         }

         String[] args = new String[argList.size()];
         argList.toArray(args);
         StringWriter spew = null;
         PrintWriter spewWriter;
         if (out == null) {
            spewWriter = new PrintWriter(spew = new StringWriter());
         } else {
            spewWriter = out;
         }

         ClassLoader originalCCL = Thread.currentThread().getContextClassLoader();

         RootDoc var14;
         try {
            JavadocResults.prepare();
            if (logger.isVerbose((Object)this)) {
               logger.verbose("Invoking javadoc.  Command line equivalent is: ");
               StringWriter sw = new StringWriter();
               sw.write("javadoc ");

               for(int i = 0; i < args.length; ++i) {
                  sw.write("'");
                  sw.write(args[i]);
                  sw.write("' ");
               }

               logger.verbose("  " + sw.toString());
            }

            int result = Main.execute("JAM", spewWriter, spewWriter, spewWriter, this.getClass().getName(), args);
            RootDoc root = JavadocResults.getRoot();
            if (result != 0 || root == null) {
               spewWriter.flush();
               if (JavadocClassloadingException.isClassloadingProblemPresent()) {
                  throw new JavadocClassloadingException();
               }

               throw new RuntimeException("Unknown javadoc problem: result=" + result + ", root=" + root + ":\n" + (spew == null ? "" : spew.toString()));
            }

            var14 = root;
         } catch (RuntimeException var18) {
            throw var18;
         } finally {
            Thread.currentThread().setContextClassLoader(originalCCL);
         }

         return var14;
      } else {
         throw new FileNotFoundException("No input files found.");
      }
   }

   public static boolean start(RootDoc root) {
      JavadocResults.setRoot(root);
      return true;
   }
}
