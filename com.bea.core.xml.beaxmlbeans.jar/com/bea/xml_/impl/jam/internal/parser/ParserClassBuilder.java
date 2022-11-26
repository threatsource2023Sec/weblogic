package com.bea.xml_.impl.jam.internal.parser;

import com.bea.xml_.impl.jam.JamClassLoader;
import com.bea.xml_.impl.jam.mutable.MClass;
import com.bea.xml_.impl.jam.provider.JamClassBuilder;
import com.bea.xml_.impl.jam.provider.JamClassPopulator;
import com.bea.xml_.impl.jam.provider.JamServiceContext;
import com.bea.xml_.impl.jam.provider.ResourcePath;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ParserClassBuilder extends JamClassBuilder implements JamClassPopulator {
   private static final boolean VERBOSE = false;
   private ResourcePath mSourcePath;
   private boolean mVerbose;
   private PrintWriter mOut;

   private ParserClassBuilder() {
      this.mVerbose = false;
      this.mOut = new PrintWriter(System.out);
   }

   public ParserClassBuilder(JamServiceContext jsp) {
      this.mVerbose = false;
      this.mOut = new PrintWriter(System.out);
      this.mSourcePath = jsp.getInputSourcepath();
   }

   public MClass build(String pkg, String name) {
      if (pkg == null) {
         throw new IllegalArgumentException("null pkg");
      } else if (name == null) {
         throw new IllegalArgumentException("null name");
      } else {
         String filespec = pkg.replace('.', File.separatorChar) + File.separatorChar + name + ".java";
         if (name.indexOf(".") != -1) {
            throw new IllegalArgumentException("inner classes are NYI at the moment");
         } else {
            InputStream in = this.mSourcePath.findInPath(filespec);
            if (in == null) {
               if (this.mVerbose) {
                  this.mOut.println("[ParserClassBuilder] could not find " + filespec);
               }

               return null;
            } else {
               if (this.mVerbose) {
                  this.mOut.println("[ParserClassBuilder] loading class " + pkg + "  " + name);
                  this.mOut.println("[ParserClassBuilder] from file " + filespec);
               }

               Reader rin = new InputStreamReader(in);

               try {
                  rin.close();
               } catch (IOException var9) {
                  var9.printStackTrace();
               }

               return null;
            }
         }
      }
   }

   public void populate(MClass m) {
      throw new IllegalStateException("NYI");
   }

   private static MClass[] parse(Reader in, JamClassLoader loader) throws Exception {
      if (in == null) {
         throw new IllegalArgumentException("null in");
      } else if (loader == null) {
         throw new IllegalArgumentException("null loader");
      } else {
         throw new IllegalStateException("temporarily NI");
      }
   }

   public static void main(String[] files) {
      (new MainTool()).process(files);
   }

   // $FF: synthetic method
   ParserClassBuilder(Object x0) {
      this();
   }

   static class MainTool {
      private List mFailures = new ArrayList();
      private int mCount = 0;
      private PrintWriter mOut;
      private long mStartTime;

      MainTool() {
         this.mOut = new PrintWriter(System.out);
         this.mStartTime = System.currentTimeMillis();
      }

      public void process(String[] files) {
         int fails;
         try {
            for(fails = 0; fails < files.length; ++fails) {
               File input = new File(files[fails]);
               this.parse(new ParserClassBuilder(), input);
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }

         this.mOut.println("\n\n\n");
         fails = this.mFailures.size();
         if (fails != 0) {
            this.mOut.println("The following files failed to parse:");

            for(int i = 0; i < fails; ++i) {
               this.mOut.println(((File)this.mFailures.get(i)).getAbsolutePath());
            }
         }

         this.mOut.println((this.mCount - fails) * 100 / this.mCount + "% (" + (this.mCount - fails) + "/" + this.mCount + ") " + "of input java files successfully parsed.");
         this.mOut.println("Total time: " + (System.currentTimeMillis() - this.mStartTime) / 1000L + " seconds.");
         this.mOut.flush();
         System.out.flush();
         System.err.flush();
      }

      private void parse(ParserClassBuilder parser, File input) throws Exception {
         System.gc();
         File[] files;
         if (input.isDirectory()) {
            files = input.listFiles();

            for(int i = 0; i < files.length; ++i) {
               this.parse(parser, files[i]);
            }
         } else {
            if (!input.getName().endsWith(".java")) {
               return;
            }

            ++this.mCount;
            files = null;

            try {
               MClass[] results = ParserClassBuilder.parse(new FileReader(input), (JamClassLoader)null);
               if (results == null) {
                  this.mOut.println("[error, parser result is null]");
                  this.addFailure(input);
               }
            } catch (Throwable var5) {
               var5.printStackTrace(this.mOut);
               this.addFailure(input);
            }
         }

      }

      private void addFailure(File file) {
         this.mFailures.add(file);
      }
   }
}
