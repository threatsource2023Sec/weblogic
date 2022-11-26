package org.antlr.gunit;

import java.io.File;
import java.io.IOException;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Interp {
   static String testPackage;
   static boolean genJUnit;
   static String gunitFile;

   public static void main(String[] args) throws IOException, ClassNotFoundException, RecognitionException {
      CharStream input = null;
      String testsuiteDir = System.getProperty("user.dir");
      processArgs(args);
      File f;
      if (genJUnit) {
         if (gunitFile != null) {
            input = new ANTLRFileStream(gunitFile);
            f = new File(gunitFile);
            testsuiteDir = getTestsuiteDir(f.getCanonicalPath(), f.getName());
         } else {
            input = new ANTLRInputStream(System.in);
         }

         GrammarInfo grammarInfo = parse((CharStream)input);
         grammarInfo.setTestPackage(testPackage);
         JUnitCodeGen generater = new JUnitCodeGen(grammarInfo, testsuiteDir);
         generater.compile();
      } else {
         if (gunitFile != null) {
            input = new ANTLRFileStream(gunitFile);
            f = new File(gunitFile);
            testsuiteDir = getTestsuiteDir(f.getCanonicalPath(), f.getName());
         } else {
            input = new ANTLRInputStream(System.in);
         }

         gUnitExecutor executer = new gUnitExecutor(parse((CharStream)input), testsuiteDir);
         System.out.print(executer.execTest());
         System.exit(executer.failures.size() + executer.invalids.size());
      }
   }

   public static void processArgs(String[] args) {
      if (args != null && args.length != 0) {
         for(int i = 0; i < args.length; ++i) {
            if (args[i].equals("-p")) {
               if (i + 1 >= args.length) {
                  System.err.println("missing library directory with -lib option; ignoring");
               } else {
                  ++i;
                  testPackage = args[i];
               }
            } else if (args[i].equals("-o")) {
               genJUnit = true;
            } else {
               gunitFile = args[i];
            }
         }

      }
   }

   public static GrammarInfo parse(CharStream input) throws RecognitionException {
      gUnitLexer lexer = new gUnitLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      GrammarInfo grammarInfo = new GrammarInfo();
      gUnitParser parser = new gUnitParser(tokens, grammarInfo);
      parser.gUnitDef();
      return grammarInfo;
   }

   public static String getTestsuiteDir(String fullPath, String fileName) {
      return fullPath.substring(0, fullPath.length() - fileName.length());
   }
}
