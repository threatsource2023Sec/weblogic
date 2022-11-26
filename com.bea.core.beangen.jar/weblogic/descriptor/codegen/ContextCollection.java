package weblogic.descriptor.codegen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamClassIterator;
import com.bea.util.jam.JamService;
import com.bea.util.jam.JamServiceFactory;
import com.bea.util.jam.JamServiceParams;
import com.bea.util.jam.annotation.JavadocTagParser;
import com.bea.util.jam.annotation.LineDelimitedTagParser;
import com.bea.util.jam.visitor.MVisitor;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ContextCollection extends AbstractCollection {
   JamService jamService;
   protected CodeGenerator codeGenerator;
   protected int index = 0;
   protected int size;
   protected long lastModified = 0L;

   public ContextCollection(CodeGenerator codeGenerator) throws IOException {
      this.codeGenerator = codeGenerator;
      JamServiceFactory factory = JamServiceFactory.getInstance();
      JamServiceParams params = factory.createServiceParams();
      params.addClassLoader(this.getClass().getClassLoader());
      params.setPropertyInitializer((MVisitor)null);
      JavadocTagParser jtp = new LineDelimitedTagParser();
      jtp.setAddSingleValueMembers(true);
      params.setJavadocTagParser(jtp);
      File srcRoot = new File(codeGenerator.getOpts().getSourceDir());
      String[] sources = codeGenerator.getOpts().getSources();

      for(int i = 0; i < sources.length; ++i) {
         File fd = new File(srcRoot, sources[i]);
         if (fd.isDirectory()) {
            this.addSubdirs(params, fd);
         } else {
            if (!sources[i].endsWith(".java")) {
               sources[i] = sources[i].replace('.', File.separatorChar) + ".java";
            }

            boolean addIt = true;
            String[] excludes = codeGenerator.getOpts().getExcludes();

            for(int j = 0; j < excludes.length; ++j) {
               if (excludes[j].equals(sources[i])) {
                  addIt = false;
                  break;
               }
            }

            if (addIt) {
               File jamFile = new File(srcRoot, sources[i]);
               if (!jamFile.canRead()) {
                  throw new IOException("Unable to read file: " + jamFile.toString());
               }

               long jamFileModified = jamFile.lastModified();
               if (jamFileModified > this.lastModified) {
                  this.lastModified = jamFileModified;
               }

               params.includeSourceFile(jamFile);
            }
         }
      }

      String sourcePath = codeGenerator.getOpts().getSourcePath();
      if (sourcePath != null) {
         File[] path = this.parsePath(sourcePath);

         for(int i = 0; i < path.length; ++i) {
            params.addSourcepath(path[i]);
         }
      }

      String classPath = codeGenerator.getOpts().getClasspath();
      if (classPath == null) {
         classPath = System.getProperty("java.class.path");
      }

      if (classPath != null) {
         File[] path = this.parsePath(classPath);

         for(int i = 0; i < path.length; ++i) {
            params.addClasspath(path[i]);
         }
      }

      this.jamService = factory.createService(params);
   }

   public long getLastModified() {
      return this.lastModified;
   }

   private File[] parsePath(String path) {
      if (path == null) {
         return new File[0];
      } else {
         path = path.trim();
         if (path.length() == 0) {
            return new File[0];
         } else {
            StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
            File[] out = new File[st.countTokens()];

            String token;
            for(int x = 0; st.hasMoreTokens(); out[x++] = new File(token)) {
               token = st.nextToken();
            }

            return out;
         }
      }
   }

   void addSubdirs(JamServiceParams params, File srcRoot) {
      File[] files = srcRoot.listFiles(new FileFilter() {
         public boolean accept(File f) {
            if (f.isDirectory()) {
               return true;
            } else {
               return f.getName().endsWith(".java");
            }
         }
      });

      for(int i = 0; i < files.length; ++i) {
         if (files[i].isDirectory()) {
            this.addSubdirs(params, files[i]);
         } else {
            boolean addIt = true;
            String[] excludes = this.codeGenerator.getOpts().getExcludes();

            for(int j = 0; j < excludes.length; ++j) {
               if (excludes[j].equals(files[i].getPath())) {
                  addIt = false;
                  break;
               }
            }

            if (addIt) {
               params.includeSourceFile(files[i]);
            }
         }
      }

   }

   protected final JClass[] getJClasses() {
      throw new IllegalStateException();
   }

   public Iterator iterator() {
      return new ContextIterator(this.jamService.getClasses());
   }

   public int size() {
      return this.size;
   }

   protected class ContextIterator implements Iterator {
      JamClassIterator iter;

      ContextIterator(JamClassIterator iter) {
         this.iter = iter;
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public Object next() {
         JClass nextClass;
         do {
            if (!this.iter.hasNext()) {
               return null;
            }

            nextClass = this.iter.nextClass();
         } while(!this.includeClass(nextClass));

         return ContextCollection.this.codeGenerator.getCodeGeneratorContext(nextClass);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      protected boolean includeClass(JClass clazz) {
         return true;
      }
   }
}
