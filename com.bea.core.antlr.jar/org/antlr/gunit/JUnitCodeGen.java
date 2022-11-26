package org.antlr.gunit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplateGroupLoader;
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;

public class JUnitCodeGen {
   public GrammarInfo grammarInfo;
   public Map ruleWithReturn;
   private final String testsuiteDir;
   private String outputDirectoryPath;
   private static final Handler console = new ConsoleHandler();
   private static final Logger logger = Logger.getLogger(JUnitCodeGen.class.getName());

   public JUnitCodeGen(GrammarInfo grammarInfo, String testsuiteDir) throws ClassNotFoundException {
      this(grammarInfo, determineClassLoader(), testsuiteDir);
   }

   private static ClassLoader determineClassLoader() {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader == null) {
         classLoader = JUnitCodeGen.class.getClassLoader();
      }

      return classLoader;
   }

   public JUnitCodeGen(GrammarInfo grammarInfo, ClassLoader classLoader, String testsuiteDir) throws ClassNotFoundException {
      this.outputDirectoryPath = ".";
      this.grammarInfo = grammarInfo;
      this.testsuiteDir = testsuiteDir;
      this.ruleWithReturn = new HashMap();
      Class parserClass = this.locateParserClass(grammarInfo, classLoader);
      Method[] methods = parserClass.getDeclaredMethods();
      Method[] arr$ = methods;
      int len$ = methods.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         if (!method.getReturnType().getName().equals("void")) {
            this.ruleWithReturn.put(method.getName(), method.getReturnType().getName().replace('$', '.'));
         }
      }

   }

   private Class locateParserClass(GrammarInfo grammarInfo, ClassLoader classLoader) throws ClassNotFoundException {
      String parserClassName = grammarInfo.getGrammarName() + "Parser";
      if (grammarInfo.getGrammarPackage() != null) {
         parserClassName = grammarInfo.getGrammarPackage() + "." + parserClassName;
      }

      return classLoader.loadClass(parserClassName);
   }

   public String getOutputDirectoryPath() {
      return this.outputDirectoryPath;
   }

   public void setOutputDirectoryPath(String outputDirectoryPath) {
      this.outputDirectoryPath = outputDirectoryPath;
   }

   public void compile() throws IOException {
      String junitFileName;
      if (this.grammarInfo.getTreeGrammarName() != null) {
         junitFileName = "Test" + this.grammarInfo.getTreeGrammarName();
      } else {
         junitFileName = "Test" + this.grammarInfo.getGrammarName();
      }

      String lexerName = this.grammarInfo.getGrammarName() + "Lexer";
      String parserName = this.grammarInfo.getGrammarName() + "Parser";
      StringTemplateGroupLoader loader = new CommonGroupLoader("org/antlr/gunit", (StringTemplateErrorListener)null);
      StringTemplateGroup.registerGroupLoader(loader);
      StringTemplateGroup.registerDefaultLexer(AngleBracketTemplateLexer.class);
      StringBuffer buf = this.compileToBuffer(junitFileName, lexerName, parserName);
      this.writeTestFile(".", junitFileName + ".java", buf.toString());
   }

   public StringBuffer compileToBuffer(String className, String lexerName, String parserName) {
      StringTemplateGroup group = StringTemplateGroup.loadGroup("junit");
      StringBuffer buf = new StringBuffer();
      buf.append(this.genClassHeader(group, className, lexerName, parserName));
      buf.append(this.genTestRuleMethods(group));
      buf.append("\n\n}");
      return buf;
   }

   protected String genClassHeader(StringTemplateGroup group, String junitFileName, String lexerName, String parserName) {
      StringTemplate classHeaderST = group.getInstanceOf("classHeader");
      if (this.grammarInfo.getTestPackage() != null) {
         classHeaderST.setAttribute("header", (Object)("package " + this.grammarInfo.getTestPackage() + ";"));
      }

      classHeaderST.setAttribute("junitFileName", (Object)junitFileName);
      String lexerPath = null;
      String parserPath = null;
      String treeParserPath = null;
      String packagePath = null;
      boolean isTreeGrammar = false;
      boolean hasPackage = false;
      if (this.grammarInfo.getGrammarPackage() != null) {
         hasPackage = true;
         packagePath = "./" + this.grammarInfo.getGrammarPackage().replace('.', '/');
         lexerPath = this.grammarInfo.getGrammarPackage() + "." + lexerName;
         parserPath = this.grammarInfo.getGrammarPackage() + "." + parserName;
         if (this.grammarInfo.getTreeGrammarName() != null) {
            treeParserPath = this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getTreeGrammarName();
            isTreeGrammar = true;
         }
      } else {
         lexerPath = lexerName;
         parserPath = parserName;
         if (this.grammarInfo.getTreeGrammarName() != null) {
            treeParserPath = this.grammarInfo.getTreeGrammarName();
            isTreeGrammar = true;
         }
      }

      String treeAdaptorPath = null;
      boolean hasTreeAdaptor = false;
      if (this.grammarInfo.getAdaptor() != null) {
         hasTreeAdaptor = true;
         treeAdaptorPath = this.grammarInfo.getAdaptor();
      }

      classHeaderST.setAttribute("hasTreeAdaptor", (Object)hasTreeAdaptor);
      classHeaderST.setAttribute("treeAdaptorPath", (Object)treeAdaptorPath);
      classHeaderST.setAttribute("hasPackage", (Object)hasPackage);
      classHeaderST.setAttribute("packagePath", (Object)packagePath);
      classHeaderST.setAttribute("lexerPath", (Object)lexerPath);
      classHeaderST.setAttribute("parserPath", (Object)parserPath);
      classHeaderST.setAttribute("treeParserPath", (Object)treeParserPath);
      classHeaderST.setAttribute("isTreeGrammar", (Object)isTreeGrammar);
      return classHeaderST.toString();
   }

   protected String genTestRuleMethods(StringTemplateGroup group) {
      StringBuffer buf = new StringBuffer();
      if (this.grammarInfo.getTreeGrammarName() != null) {
         this.genTreeMethods(group, buf);
      } else {
         this.genParserMethods(group, buf);
      }

      return buf.toString();
   }

   private void genParserMethods(StringTemplateGroup group, StringBuffer buf) {
      Iterator i$ = this.grammarInfo.getRuleTestSuites().iterator();

      while(i$.hasNext()) {
         gUnitTestSuite ts = (gUnitTestSuite)i$.next();
         int i = 0;

         StringTemplate testRuleMethodST;
         for(Iterator i$ = ts.testSuites.entrySet().iterator(); i$.hasNext(); buf.append(testRuleMethodST.toString())) {
            Map.Entry entry = (Map.Entry)i$.next();
            gUnitTestInput input = (gUnitTestInput)entry.getKey();
            ++i;
            String testRuleName;
            if (((AbstractTest)entry.getValue()).getType() == 4 && this.ruleWithReturn.containsKey(ts.getRuleName())) {
               testRuleMethodST = group.getInstanceOf("testRuleMethod2");
               testRuleName = ((AbstractTest)entry.getValue()).getText();
               testRuleMethodST.setAttribute("methodName", (Object)("test" + this.changeFirstCapital(ts.getRuleName()) + i));
               testRuleMethodST.setAttribute("testRuleName", (Object)('"' + ts.getRuleName() + '"'));
               testRuleMethodST.setAttribute("test", (Object)input);
               testRuleMethodST.setAttribute("returnType", this.ruleWithReturn.get(ts.getRuleName()));
               testRuleMethodST.setAttribute("expecting", (Object)testRuleName);
            } else {
               if (ts.isLexicalRule()) {
                  testRuleName = ts.getLexicalRuleName();
               } else {
                  testRuleName = ts.getRuleName();
               }

               testRuleMethodST = group.getInstanceOf("testRuleMethod");
               String outputString = ((AbstractTest)entry.getValue()).getText();
               testRuleMethodST.setAttribute("isLexicalRule", (Object)ts.isLexicalRule());
               testRuleMethodST.setAttribute("methodName", (Object)("test" + this.changeFirstCapital(testRuleName) + i));
               testRuleMethodST.setAttribute("testRuleName", (Object)('"' + testRuleName + '"'));
               testRuleMethodST.setAttribute("test", (Object)input);
               testRuleMethodST.setAttribute("tokenType", (Object)this.getTypeString(((AbstractTest)entry.getValue()).getType()));
               outputString = normalizeTreeSpec(outputString);
               if (((AbstractTest)entry.getValue()).getType() == 4) {
                  testRuleMethodST.setAttribute("expecting", (Object)outputString);
               } else if (((AbstractTest)entry.getValue()).getType() == 18) {
                  testRuleMethodST.setAttribute("expecting", (Object)outputString);
               } else {
                  outputString = outputString.replaceAll("\n", "");
                  testRuleMethodST.setAttribute("expecting", (Object)('"' + escapeForJava(outputString) + '"'));
               }
            }
         }
      }

   }

   private void genTreeMethods(StringTemplateGroup group, StringBuffer buf) {
      Iterator i$ = this.grammarInfo.getRuleTestSuites().iterator();

      while(i$.hasNext()) {
         gUnitTestSuite ts = (gUnitTestSuite)i$.next();
         int i = 0;

         StringTemplate testRuleMethodST;
         for(Iterator i$ = ts.testSuites.entrySet().iterator(); i$.hasNext(); buf.append(testRuleMethodST.toString())) {
            Map.Entry entry = (Map.Entry)i$.next();
            gUnitTestInput input = (gUnitTestInput)entry.getKey();
            ++i;
            String outputString;
            if (((AbstractTest)entry.getValue()).getType() == 4 && this.ruleWithReturn.containsKey(ts.getTreeRuleName())) {
               testRuleMethodST = group.getInstanceOf("testTreeRuleMethod2");
               outputString = ((AbstractTest)entry.getValue()).getText();
               testRuleMethodST.setAttribute("methodName", (Object)("test" + this.changeFirstCapital(ts.getTreeRuleName()) + "_walks_" + this.changeFirstCapital(ts.getRuleName()) + i));
               testRuleMethodST.setAttribute("testTreeRuleName", (Object)('"' + ts.getTreeRuleName() + '"'));
               testRuleMethodST.setAttribute("testRuleName", (Object)('"' + ts.getRuleName() + '"'));
               testRuleMethodST.setAttribute("test", (Object)input);
               testRuleMethodST.setAttribute("returnType", this.ruleWithReturn.get(ts.getTreeRuleName()));
               testRuleMethodST.setAttribute("expecting", (Object)outputString);
            } else {
               testRuleMethodST = group.getInstanceOf("testTreeRuleMethod");
               outputString = ((AbstractTest)entry.getValue()).getText();
               testRuleMethodST.setAttribute("methodName", (Object)("test" + this.changeFirstCapital(ts.getTreeRuleName()) + "_walks_" + this.changeFirstCapital(ts.getRuleName()) + i));
               testRuleMethodST.setAttribute("testTreeRuleName", (Object)('"' + ts.getTreeRuleName() + '"'));
               testRuleMethodST.setAttribute("testRuleName", (Object)('"' + ts.getRuleName() + '"'));
               testRuleMethodST.setAttribute("test", (Object)input);
               testRuleMethodST.setAttribute("tokenType", (Object)this.getTypeString(((AbstractTest)entry.getValue()).getType()));
               if (((AbstractTest)entry.getValue()).getType() == 4) {
                  testRuleMethodST.setAttribute("expecting", (Object)outputString);
               } else if (((AbstractTest)entry.getValue()).getType() == 18) {
                  testRuleMethodST.setAttribute("expecting", (Object)outputString);
               } else {
                  testRuleMethodST.setAttribute("expecting", (Object)('"' + escapeForJava(outputString) + '"'));
               }
            }
         }
      }

   }

   public String getTypeString(int type) {
      String typeText;
      switch (type) {
         case 5:
            typeText = "org.antlr.gunit.gUnitParser.AST";
            break;
         case 6:
         case 7:
         case 8:
         case 9:
         case 11:
         case 13:
         case 14:
         case 15:
         case 17:
         case 19:
         case 20:
         default:
            typeText = "org.antlr.gunit.gUnitParser.EOF";
            break;
         case 10:
            typeText = "org.antlr.gunit.gUnitParser.FAIL";
            break;
         case 12:
            typeText = "org.antlr.gunit.gUnitParser.ML_STRING";
            break;
         case 16:
            typeText = "org.antlr.gunit.gUnitParser.OK";
            break;
         case 18:
            typeText = "org.antlr.gunit.gUnitParser.RETVAL";
            break;
         case 21:
            typeText = "org.antlr.gunit.gUnitParser.STRING";
      }

      return typeText;
   }

   protected void writeTestFile(String dir, String fileName, String content) {
      try {
         File f = new File(dir, fileName);
         FileWriter w = new FileWriter(f);
         BufferedWriter bw = new BufferedWriter(w);
         bw.write(content);
         bw.close();
         w.close();
      } catch (IOException var7) {
         logger.log(Level.SEVERE, "can't write file", var7);
      }

   }

   public static String escapeForJava(String inputString) {
      inputString = inputString.replace("\\", "\\\\");
      inputString = inputString.replace("\"", "\\\"");
      inputString = inputString.replace("\n", "\\n").replace("\t", "\\t").replace("\r", "\\r").replace("\b", "\\b").replace("\f", "\\f");
      return inputString;
   }

   protected String changeFirstCapital(String ruleName) {
      String firstChar = String.valueOf(ruleName.charAt(0));
      return firstChar.toUpperCase() + ruleName.substring(1);
   }

   public static String normalizeTreeSpec(String t) {
      List words = new ArrayList();
      int i = 0;
      StringBuilder word = new StringBuilder();

      while(true) {
         while(true) {
            while(i < t.length()) {
               if (t.charAt(i) != '(' && t.charAt(i) != ')') {
                  if (Character.isWhitespace(t.charAt(i))) {
                     if (word.length() > 0) {
                        words.add(word.toString());
                        word.setLength(0);
                     }

                     ++i;
                  } else if (t.charAt(i) == '"' && i - 1 >= 0 && (t.charAt(i - 1) == '(' || Character.isWhitespace(t.charAt(i - 1)))) {
                     ++i;

                     while(i < t.length() && t.charAt(i) != '"') {
                        if (t.charAt(i) == '\\' && i + 1 < t.length() && t.charAt(i + 1) == '"') {
                           word.append('"');
                           i += 2;
                        } else {
                           word.append(t.charAt(i));
                           ++i;
                        }
                     }

                     ++i;
                     words.add(word.toString());
                     word.setLength(0);
                  } else {
                     word.append(t.charAt(i));
                     ++i;
                  }
               } else {
                  if (word.length() > 0) {
                     words.add(word.toString());
                     word.setLength(0);
                  }

                  words.add(String.valueOf(t.charAt(i)));
                  ++i;
               }
            }

            if (word.length() > 0) {
               words.add(word.toString());
            }

            StringBuilder buf = new StringBuilder();

            for(int j = 0; j < words.size(); ++j) {
               if (j > 0 && !((String)words.get(j)).equals(")") && !((String)words.get(j - 1)).equals("(")) {
                  buf.append(' ');
               }

               buf.append((String)words.get(j));
            }

            return buf.toString();
         }
      }
   }

   static {
      logger.addHandler(console);
   }
}
