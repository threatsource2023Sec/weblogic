package org.antlr.gunit.swingui.runner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ParserLoader extends ClassLoader {
   private HashMap classList;
   private String grammar;

   public ParserLoader(String grammarName, String classDir) throws IOException, ClassNotFoundException {
      String lexerName = grammarName + "Lexer";
      File dir = new File(classDir);
      if (dir.isDirectory()) {
         this.classList = new HashMap();
         this.grammar = grammarName;
         File[] files = dir.listFiles(new ClassFilenameFilter(grammarName));
         File[] arr$ = files;
         int len$ = files.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File f = arr$[i$];
            InputStream in = new BufferedInputStream(new FileInputStream(f));
            byte[] classData = new byte[in.available()];
            in.read(classData);
            in.close();
            Class newClass = this.defineClass((String)null, classData, 0, classData.length);

            assert newClass != null;

            this.resolveClass(newClass);
            String fileName = f.getName();
            String className = fileName.substring(0, fileName.lastIndexOf("."));
            this.classList.put(className, newClass);
         }

         if (this.classList.isEmpty() || !this.classList.containsKey(lexerName)) {
            throw new ClassNotFoundException(lexerName + " not found.");
         }
      } else {
         throw new IOException(classDir + " is not a directory.");
      }
   }

   public synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      if (name.startsWith(this.grammar)) {
         if (this.classList.containsKey(name)) {
            return (Class)this.classList.get(name);
         } else {
            throw new ClassNotFoundException(name);
         }
      } else {
         Class c = this.findSystemClass(name);
         return c;
      }
   }

   protected static class ClassFilenameFilter implements FilenameFilter {
      private String grammarName;

      protected ClassFilenameFilter(String name) {
         this.grammarName = name;
      }

      public boolean accept(File dir, String name) {
         return name.startsWith(this.grammarName) && name.endsWith(".class");
      }
   }
}
