package org.antlr.gunit.swingui.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.gunit.swingui.parsers.ANTLRv3Lexer;
import org.antlr.gunit.swingui.parsers.ANTLRv3Parser;
import org.antlr.gunit.swingui.parsers.StGUnitLexer;
import org.antlr.gunit.swingui.parsers.StGUnitParser;
import org.antlr.gunit.swingui.runner.TestSuiteAdapter;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class TestSuiteFactory {
   private static String TEMPLATE_FILE = "org/antlr/gunit/swingui/gunit.stg";
   private static StringTemplateGroup templates;
   public static final String TEST_SUITE_EXT = ".gunit";
   public static final String GRAMMAR_EXT = ".g";

   public static TestSuite createTestSuite(File grammarFile) {
      if (grammarFile != null && grammarFile.exists() && grammarFile.isFile()) {
         String fileName = grammarFile.getName();
         String grammarName = fileName.substring(0, fileName.lastIndexOf(46));
         String grammarDir = grammarFile.getParent();
         File testFile = new File(grammarDir + File.separator + grammarName + ".gunit");
         TestSuite result = new TestSuite(grammarName, testFile);
         result.rules = loadRulesFromGrammar(grammarFile);
         if (saveTestSuite(result)) {
            return result;
         } else {
            throw new RuntimeException("Can't save test suite file.");
         }
      } else {
         throw new RuntimeException("Invalid grammar file.");
      }
   }

   private static List loadRulesFromGrammar(File grammarFile) {
      List ruleNames = new ArrayList();

      try {
         Reader reader = new BufferedReader(new FileReader(grammarFile));
         ANTLRv3Lexer lexer = new ANTLRv3Lexer(new ANTLRReaderStream(reader));
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         ANTLRv3Parser parser = new ANTLRv3Parser(tokens);
         parser.rules = ruleNames;
         parser.grammarDef();
         reader.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      List ruleList = new ArrayList();
      Iterator i$ = ruleNames.iterator();

      while(i$.hasNext()) {
         String str = (String)i$.next();
         ruleList.add(new Rule(str));
      }

      return ruleList;
   }

   public static boolean saveTestSuite(TestSuite testSuite) {
      String data = getScript(testSuite);

      try {
         FileWriter fw = new FileWriter(testSuite.getTestSuiteFile());
         fw.write(data);
         fw.flush();
         fw.close();
         return true;
      } catch (IOException var3) {
         var3.printStackTrace();
         return false;
      }
   }

   public static String getScript(TestSuite testSuite) {
      if (testSuite == null) {
         return null;
      } else {
         StringTemplate gUnitScript = templates.getInstanceOf("gUnitFile");
         gUnitScript.setAttribute("testSuite", (Object)testSuite);
         return gUnitScript.toString();
      }
   }

   public static TestSuite loadTestSuite(File file) {
      if (file.getName().endsWith(".g")) {
         throw new RuntimeException(file.getName() + " is a grammar file not a gunit file");
      } else {
         File grammarFile = getGrammarFile(file);
         if (grammarFile == null) {
            throw new RuntimeException("Can't find grammar file associated with gunit file: " + file.getAbsoluteFile());
         } else {
            TestSuite result = new TestSuite("", file);

            try {
               Reader reader = new BufferedReader(new FileReader(file));
               StGUnitLexer lexer = new StGUnitLexer(new ANTLRReaderStream(reader));
               CommonTokenStream tokens = new CommonTokenStream(lexer);
               StGUnitParser parser = new StGUnitParser(tokens);
               TestSuiteAdapter adapter = new TestSuiteAdapter(result);
               parser.adapter = adapter;
               parser.gUnitDef();
               result.setTokens(tokens);
               reader.close();
            } catch (Exception var8) {
               throw new RuntimeException("Error reading test suite file.\n" + var8.getMessage());
            }

            List completeRuleList = loadRulesFromGrammar(grammarFile);
            Iterator i$ = completeRuleList.iterator();

            while(i$.hasNext()) {
               Rule rule = (Rule)i$.next();
               if (!result.hasRule(rule)) {
                  result.addRule(rule);
               }
            }

            return result;
         }
      }
   }

   private static File getGrammarFile(File testsuiteFile) {
      String sTestFile;
      try {
         sTestFile = testsuiteFile.getCanonicalPath();
      } catch (IOException var4) {
         return null;
      }

      String fname = sTestFile.substring(0, sTestFile.lastIndexOf(46)) + ".g";
      File fileGrammar = new File(fname);
      if (fileGrammar.exists() && fileGrammar.isFile()) {
         return fileGrammar;
      } else {
         fname = sTestFile.substring(0, sTestFile.lastIndexOf(46)) + "Parser" + ".g";
         return fileGrammar.exists() && fileGrammar.isFile() ? fileGrammar : fileGrammar;
      }
   }

   static {
      ClassLoader loader = TestSuiteFactory.class.getClassLoader();
      InputStream in = loader.getResourceAsStream(TEMPLATE_FILE);
      if (in == null) {
         throw new RuntimeException("internal error: Can't find templates " + TEMPLATE_FILE);
      } else {
         Reader rd = new InputStreamReader(in);
         templates = new StringTemplateGroup(rd);
      }
   }
}
