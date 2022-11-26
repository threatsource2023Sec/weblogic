package com.bea.util.jam.internal.javadoc;

import com.bea.util.jam.JImport;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.internal.elements.ImportImpl;
import com.bea.util.jam.internal.parser.tree.JavaTreeAnalyser;
import com.bea.util.jam.provider.JamLogger;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class JavadocImportHandler {
   private static void printLogger(JamLogger log, String msg) {
      if (log.isVerbose(JavadocImportHandler.class)) {
         log.verbose(msg);
      }

   }

   private static ClassImports parseFile(ElementContext eContext, File file, JamLogger log) {
      if (file == null) {
         printLogger(log, "WARNING:null file");
         return null;
      } else {
         file = file.getAbsoluteFile();
         if (!file.exists()) {
            printLogger(log, "WARNING:" + file + " does not exist");
            return null;
         } else if (file.isDirectory()) {
            printLogger(log, "WARNING: cannot be included as a source file because it is a directory.");
            return null;
         } else {
            HashMap tree = JavaTreeAnalyser.parse(file.getPath(), log.isVerbose(JavadocImportHandler.class));
            String cName = JavaTreeAnalyser.getQualifiedClassName(tree);
            if (cName == null) {
               printLogger(log, "WARNING: File [" + file.toString() + "] is illegal java file");
               return null;
            } else {
               ClassImports ret = new ClassImports();
               ret.qualifiedName = cName;
               ret.sourceFile = JavaTreeAnalyser.getSourceFile(tree);
               List importList = JavaTreeAnalyser.getImportList(tree);
               List importIsStaticList = JavaTreeAnalyser.getImportIsStaticList(tree);

               assert importList.size() == importIsStaticList.size();

               for(int i = 0; i < importList.size(); ++i) {
                  JImport imp = new ImportImpl(eContext, (String)importList.get(i), Boolean.valueOf((String)importIsStaticList.get(i)));
                  ret.importList.add(imp);
               }

               return ret;
            }
         }
      }
   }

   public static HashMap genImportSpecs(ElementContext eContext, File[] files, JamLogger logger) {
      HashMap ret = new HashMap();
      if (files == null) {
         return ret;
      } else {
         File[] var4 = files;
         int var5 = files.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File file = var4[var6];
            ClassImports isi = parseFile(eContext, file, logger);
            if (isi != null) {
               ret.put(isi.qualifiedName, isi);
            }
         }

         return ret;
      }
   }

   public static class ClassImports {
      public String qualifiedName;
      public String sourceFile;
      public final List importList = new ArrayList();

      public List toStrImportList() {
         List importSpecs = new ArrayList();
         Iterator var2 = this.importList.iterator();

         while(var2.hasNext()) {
            JImport imp = (JImport)var2.next();
            importSpecs.add(imp.getQualifiedName());
         }

         return importSpecs;
      }

      public JImport[] toJImportArray() {
         JImport[] ret = new JImport[this.importList.size()];
         return (JImport[])this.importList.toArray(ret);
      }

      public String toString() {
         StringBuffer msg = new StringBuffer();
         msg.append("file=" + this.sourceFile);
         msg.append("qualifiedName=").append(this.qualifiedName).append("\n");

         for(int i = 0; i < this.importList.size(); ++i) {
            msg.append(((JImport)this.importList.get(i)).toString());
         }

         return msg.toString();
      }

      public static JImport[] toJImportArray(ElementContext eContext, List importSpecs) {
         if (importSpecs == null) {
            return new ImportImpl[0];
         } else {
            ImportImpl[] ret = new ImportImpl[importSpecs.size()];

            for(int i = 0; i < importSpecs.size(); ++i) {
               ret[i] = new ImportImpl(eContext, (String)importSpecs.get(i), false);
            }

            return ret;
         }
      }
   }
}
