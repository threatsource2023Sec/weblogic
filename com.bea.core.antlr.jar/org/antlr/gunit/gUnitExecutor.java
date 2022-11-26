package org.antlr.gunit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.stringtemplate.CommonGroupLoader;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplateGroupLoader;
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;

public class gUnitExecutor implements ITestSuite {
   public GrammarInfo grammarInfo;
   private final ClassLoader grammarClassLoader;
   private final String testsuiteDir;
   public int numOfTest;
   public int numOfSuccess;
   public int numOfFailure;
   private String title;
   public int numOfInvalidInput;
   private String parserName;
   private String lexerName;
   public List failures;
   public List invalids;
   private PrintStream console;
   private PrintStream consoleErr;

   public gUnitExecutor(GrammarInfo grammarInfo, String testsuiteDir) {
      this(grammarInfo, determineClassLoader(), testsuiteDir);
   }

   private static ClassLoader determineClassLoader() {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader == null) {
         classLoader = gUnitExecutor.class.getClassLoader();
      }

      return classLoader;
   }

   public gUnitExecutor(GrammarInfo grammarInfo, ClassLoader grammarClassLoader, String testsuiteDir) {
      this.console = System.out;
      this.consoleErr = System.err;
      this.grammarInfo = grammarInfo;
      this.grammarClassLoader = grammarClassLoader;
      this.testsuiteDir = testsuiteDir;
      this.numOfTest = 0;
      this.numOfSuccess = 0;
      this.numOfFailure = 0;
      this.numOfInvalidInput = 0;
      this.failures = new ArrayList();
      this.invalids = new ArrayList();
   }

   protected ClassLoader getGrammarClassLoader() {
      return this.grammarClassLoader;
   }

   protected final Class classForName(String name) throws ClassNotFoundException {
      return this.getGrammarClassLoader().loadClass(name);
   }

   public String execTest() throws IOException {
      StringTemplate testResultST = this.getTemplateGroup().getInstanceOf("testResult");

      try {
         if (this.grammarInfo.getGrammarPackage() != null) {
            this.parserName = this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getGrammarName() + "Parser";
            this.lexerName = this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getGrammarName() + "Lexer";
         } else {
            this.parserName = this.grammarInfo.getGrammarName() + "Parser";
            this.lexerName = this.grammarInfo.getGrammarName() + "Lexer";
         }

         if (this.grammarInfo.getTreeGrammarName() != null) {
            this.title = "executing testsuite for tree grammar:" + this.grammarInfo.getTreeGrammarName() + " walks " + this.parserName;
         } else {
            this.title = "executing testsuite for grammar:" + this.grammarInfo.getGrammarName();
         }

         this.executeTests();
         testResultST.setAttribute("title", (Object)this.title);
         testResultST.setAttribute("num_of_test", this.numOfTest);
         testResultST.setAttribute("num_of_failure", this.numOfFailure);
         if (this.numOfFailure > 0) {
            testResultST.setAttribute("failure", (Object)this.failures);
         }

         if (this.numOfInvalidInput > 0) {
            testResultST.setAttribute("has_invalid", (Object)true);
            testResultST.setAttribute("num_of_invalid", this.numOfInvalidInput);
            testResultST.setAttribute("invalid", (Object)this.invalids);
         }
      } catch (Exception var3) {
         this.handleUnexpectedException(var3);
      }

      return testResultST.toString();
   }

   private StringTemplateGroup getTemplateGroup() {
      StringTemplateGroupLoader loader = new CommonGroupLoader("org/antlr/gunit", (StringTemplateErrorListener)null);
      StringTemplateGroup.registerGroupLoader(loader);
      StringTemplateGroup.registerDefaultLexer(AngleBracketTemplateLexer.class);
      StringTemplateGroup group = StringTemplateGroup.loadGroup("gUnitTestResult");
      return group;
   }

   private gUnitTestResult runCorrectParser(String parserName, String lexerName, String rule, String lexicalRule, String treeRule, gUnitTestInput input) throws Exception {
      if (lexicalRule != null) {
         return this.runLexer(lexerName, lexicalRule, input);
      } else {
         return treeRule != null ? this.runTreeParser(parserName, lexerName, rule, treeRule, input) : this.runParser(parserName, lexerName, rule, input);
      }
   }

   private void executeTests() throws Exception {
      Iterator i$ = this.grammarInfo.getRuleTestSuites().iterator();

      label52:
      while(i$.hasNext()) {
         gUnitTestSuite ts = (gUnitTestSuite)i$.next();
         String rule = ts.getRuleName();
         String lexicalRule = ts.getLexicalRuleName();
         String treeRule = ts.getTreeRuleName();
         Iterator i$ = ts.testSuites.entrySet().iterator();

         while(true) {
            while(true) {
               gUnitTestInput input;
               gUnitTestResult result;
               AbstractTest test;
               while(true) {
                  if (!i$.hasNext()) {
                     continue label52;
                  }

                  Map.Entry entry = (Map.Entry)i$.next();
                  input = (gUnitTestInput)entry.getKey();
                  ++this.numOfTest;
                  result = null;
                  test = (AbstractTest)entry.getValue();

                  try {
                     result = this.runCorrectParser(this.parserName, this.lexerName, rule, lexicalRule, treeRule, input);
                     break;
                  } catch (InvalidInputException var13) {
                     ++this.numOfInvalidInput;
                     test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line, input.input);
                     test.setActual(input.input);
                     this.invalids.add(test);
                  }
               }

               String expected = test.getExpected();
               String actual = test.getResult(result);
               test.setActual(actual);
               if (actual == null) {
                  ++this.numOfFailure;
                  test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line, input.input);
                  test.setActual("null");
                  this.failures.add(test);
                  this.onFail(test);
               } else if (expected.equals(actual) || expected.equals("FAIL") && !actual.equals("OK")) {
                  ++this.numOfSuccess;
                  this.onPass(test);
               } else if (((AbstractTest)ts.testSuites.get(input)).getType() == 4) {
                  ++this.numOfFailure;
                  test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line, input.input);
                  test.setActual("\t{ACTION} is not supported in the grammarInfo yet...");
                  this.failures.add(test);
                  this.onFail(test);
               } else {
                  ++this.numOfFailure;
                  test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line, input.input);
                  this.failures.add(test);
                  this.onFail(test);
               }
            }
         }
      }

   }

   protected gUnitTestResult runLexer(String lexerName, String testRuleName, gUnitTestInput testInput) throws Exception {
      PrintStream ps = null;
      PrintStream ps2 = null;

      gUnitTestResult var9;
      try {
         CharStream input = this.getANTLRInputStream(testInput);
         Class lexer = this.classForName(lexerName).asSubclass(Lexer.class);
         Constructor lexConstructor = lexer.getConstructor(CharStream.class);
         Lexer lexObj = (Lexer)lexConstructor.newInstance(input);
         Method ruleName = lexer.getMethod("m" + testRuleName);
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         ByteArrayOutputStream err = new ByteArrayOutputStream();
         ps = new PrintStream(out);
         ps2 = new PrintStream(err);
         System.setOut(ps);
         System.setErr(ps2);
         ruleName.invoke(lexObj);
         Method ruleName2 = lexer.getMethod("getCharIndex");
         int currentIndex = (Integer)ruleName2.invoke(lexObj);
         if (currentIndex != input.size()) {
            ps2.print("extra text found, '" + input.substring(currentIndex, input.size() - 1) + "'");
         }

         gUnitTestResult var16;
         if (err.toString().length() <= 0) {
            String stdout = null;
            if (out.toString().length() > 0) {
               stdout = out.toString();
            }

            var16 = new gUnitTestResult(true, stdout, true);
            return var16;
         }

         gUnitTestResult testResult = new gUnitTestResult(false, err.toString(), true);
         testResult.setError(err.toString());
         var16 = testResult;
         return var16;
      } catch (IOException var41) {
         var9 = this.getTestExceptionResult(var41);
      } catch (ClassNotFoundException var42) {
         this.handleUnexpectedException(var42);
         throw new Exception("This should be unreachable?");
      } catch (SecurityException var43) {
         this.handleUnexpectedException(var43);
         throw new Exception("This should be unreachable?");
      } catch (NoSuchMethodException var44) {
         this.handleUnexpectedException(var44);
         throw new Exception("This should be unreachable?");
      } catch (IllegalArgumentException var45) {
         this.handleUnexpectedException(var45);
         throw new Exception("This should be unreachable?");
      } catch (InstantiationException var46) {
         this.handleUnexpectedException(var46);
         throw new Exception("This should be unreachable?");
      } catch (IllegalAccessException var47) {
         this.handleUnexpectedException(var47);
         throw new Exception("This should be unreachable?");
      } catch (InvocationTargetException var48) {
         var9 = this.getTestExceptionResult(var48);
         return var9;
      } finally {
         try {
            if (ps != null) {
               ps.close();
            }

            if (ps2 != null) {
               ps2.close();
            }

            System.setOut(this.console);
            System.setErr(this.consoleErr);
         } catch (Exception var40) {
            var40.printStackTrace();
         }

      }

      return var9;
   }

   protected gUnitTestResult runParser(String parserName, String lexerName, String testRuleName, gUnitTestInput testInput) throws Exception {
      PrintStream ps = null;
      PrintStream ps2 = null;

      gUnitTestResult var11;
      try {
         CharStream input = this.getANTLRInputStream(testInput);
         Class lexer = this.classForName(lexerName).asSubclass(Lexer.class);
         Constructor lexConstructor = lexer.getConstructor(CharStream.class);
         Lexer lexObj = (Lexer)lexConstructor.newInstance(input);
         CommonTokenStream tokens = new CommonTokenStream(lexObj);
         Class parser = this.classForName(parserName).asSubclass(Parser.class);
         Constructor parConstructor = parser.getConstructor(TokenStream.class);
         Parser parObj = (Parser)parConstructor.newInstance(tokens);
         Method ruleName;
         if (this.grammarInfo.getAdaptor() != null) {
            ruleName = parser.getMethod("setTreeAdaptor", TreeAdaptor.class);
            Class _treeAdaptor = this.classForName(this.grammarInfo.getAdaptor()).asSubclass(TreeAdaptor.class);
            ruleName.invoke(parObj, _treeAdaptor.newInstance());
         }

         ruleName = parser.getMethod(testRuleName);
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         ByteArrayOutputStream err = new ByteArrayOutputStream();
         ps = new PrintStream(out);
         ps2 = new PrintStream(err);
         System.setOut(ps);
         System.setErr(ps2);
         Object ruleReturn = ruleName.invoke(parObj);
         String astString = null;
         String stString = null;
         if (ruleReturn != null && ruleReturn.getClass().toString().indexOf(testRuleName + "_return") > 0) {
            try {
               Class _return = this.classForName(parserName + "$" + testRuleName + "_return");
               Method[] methods = _return.getDeclaredMethods();
               Method[] arr$ = methods;
               int len$ = methods.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  Method method = arr$[i$];
                  Method returnName;
                  if (method.getName().equals("getTree")) {
                     returnName = _return.getMethod("getTree");
                     CommonTree tree = (CommonTree)returnName.invoke(ruleReturn);
                     astString = tree.toStringTree();
                  } else if (method.getName().equals("getTemplate")) {
                     returnName = _return.getMethod("getTemplate");
                     StringTemplate st = (StringTemplate)returnName.invoke(ruleReturn);
                     stString = st.toString();
                  }
               }
            } catch (Exception var56) {
               System.err.println(var56);
            }
         }

         this.checkForValidInput(tokens, ps2);
         gUnitTestResult var70;
         if (err.toString().length() <= 0) {
            String stdout = null;
            if (out.toString().length() > 0) {
               stdout = out.toString();
            }

            if (astString != null) {
               var70 = new gUnitTestResult(true, stdout, astString);
               return var70;
            }

            if (stString != null) {
               var70 = new gUnitTestResult(true, stdout, stString);
               return var70;
            }

            if (ruleReturn != null) {
               var70 = new gUnitTestResult(true, stdout, String.valueOf(ruleReturn));
               return var70;
            }

            var70 = new gUnitTestResult(true, stdout, stdout);
            return var70;
         }

         gUnitTestResult testResult = new gUnitTestResult(false, err.toString());
         testResult.setError(err.toString());
         var70 = testResult;
         return var70;
      } catch (IOException var57) {
         var11 = this.getTestExceptionResult(var57);
         return var11;
      } catch (ClassNotFoundException var58) {
         this.handleUnexpectedException(var58);
         throw new Exception("This should be unreachable?");
      } catch (SecurityException var59) {
         this.handleUnexpectedException(var59);
         throw new Exception("This should be unreachable?");
      } catch (NoSuchMethodException var60) {
         this.handleUnexpectedException(var60);
         throw new Exception("This should be unreachable?");
      } catch (IllegalArgumentException var61) {
         this.handleUnexpectedException(var61);
         throw new Exception("This should be unreachable?");
      } catch (InstantiationException var62) {
         this.handleUnexpectedException(var62);
         throw new Exception("This should be unreachable?");
      } catch (IllegalAccessException var63) {
         this.handleUnexpectedException(var63);
         throw new Exception("This should be unreachable?");
      } catch (InvocationTargetException var64) {
         var11 = this.getTestExceptionResult(var64);
      } finally {
         try {
            if (ps != null) {
               ps.close();
            }

            if (ps2 != null) {
               ps2.close();
            }

            System.setOut(this.console);
            System.setErr(this.consoleErr);
         } catch (Exception var55) {
            var55.printStackTrace();
         }

      }

      return var11;
   }

   protected gUnitTestResult runTreeParser(String parserName, String lexerName, String testRuleName, String testTreeRuleName, gUnitTestInput testInput) throws Exception {
      PrintStream ps = null;
      PrintStream ps2 = null;

      gUnitTestResult var14;
      try {
         CharStream input = this.getANTLRInputStream(testInput);
         String treeParserPath;
         if (this.grammarInfo.getGrammarPackage() != null) {
            treeParserPath = this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getTreeGrammarName();
         } else {
            treeParserPath = this.grammarInfo.getTreeGrammarName();
         }

         Class lexer = this.classForName(lexerName).asSubclass(Lexer.class);
         Constructor lexConstructor = lexer.getConstructor(CharStream.class);
         Lexer lexObj = (Lexer)lexConstructor.newInstance(input);
         CommonTokenStream tokens = new CommonTokenStream(lexObj);
         Class parser = this.classForName(parserName).asSubclass(Parser.class);
         Constructor parConstructor = parser.getConstructor(TokenStream.class);
         Parser parObj = (Parser)parConstructor.newInstance(tokens);
         TreeAdaptor customTreeAdaptor = null;
         Method ruleName;
         if (this.grammarInfo.getAdaptor() != null) {
            ruleName = parser.getMethod("setTreeAdaptor", TreeAdaptor.class);
            Class _treeAdaptor = this.classForName(this.grammarInfo.getAdaptor()).asSubclass(TreeAdaptor.class);
            customTreeAdaptor = (TreeAdaptor)_treeAdaptor.newInstance();
            ruleName.invoke(parObj, customTreeAdaptor);
         }

         ruleName = parser.getMethod(testRuleName);
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         ByteArrayOutputStream err = new ByteArrayOutputStream();
         ps = new PrintStream(out);
         ps2 = new PrintStream(err);
         System.setOut(ps);
         System.setErr(ps2);
         Object ruleReturn = ruleName.invoke(parObj);
         Class _return = this.classForName(parserName + "$" + testRuleName + "_return");
         Method returnName = _return.getMethod("getTree");
         CommonTree tree = (CommonTree)returnName.invoke(ruleReturn);
         CommonTreeNodeStream nodes;
         if (customTreeAdaptor != null) {
            nodes = new CommonTreeNodeStream(customTreeAdaptor, tree);
         } else {
            nodes = new CommonTreeNodeStream(tree);
         }

         nodes.setTokenStream(tokens);
         Class treeParser = this.classForName(treeParserPath).asSubclass(TreeParser.class);
         Constructor treeParConstructor = treeParser.getConstructor(TreeNodeStream.class);
         TreeParser treeParObj = (TreeParser)treeParConstructor.newInstance(nodes);
         Method treeRuleName = treeParser.getMethod(testTreeRuleName);
         Object treeRuleReturn = treeRuleName.invoke(treeParObj);
         String astString = null;
         String stString = null;
         if (treeRuleReturn != null && treeRuleReturn.getClass().toString().indexOf(testTreeRuleName + "_return") > 0) {
            try {
               Class _treeReturn = this.classForName(treeParserPath + "$" + testTreeRuleName + "_return");
               Method[] methods = _treeReturn.getDeclaredMethods();
               Method[] arr$ = methods;
               int len$ = methods.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  Method method = arr$[i$];
                  Method treeReturnName;
                  if (method.getName().equals("getTree")) {
                     treeReturnName = _treeReturn.getMethod("getTree");
                     CommonTree returnTree = (CommonTree)treeReturnName.invoke(treeRuleReturn);
                     astString = returnTree.toStringTree();
                  } else if (method.getName().equals("getTemplate")) {
                     treeReturnName = _return.getMethod("getTemplate");
                     StringTemplate st = (StringTemplate)treeReturnName.invoke(treeRuleReturn);
                     stString = st.toString();
                  }
               }
            } catch (Exception var68) {
               System.err.println(var68);
            }
         }

         this.checkForValidInput(tokens, ps2);
         gUnitTestResult var82;
         if (err.toString().length() <= 0) {
            String stdout = null;
            if (out.toString().length() > 0) {
               stdout = out.toString();
            }

            if (astString != null) {
               var82 = new gUnitTestResult(true, stdout, astString);
               return var82;
            }

            if (stString != null) {
               var82 = new gUnitTestResult(true, stdout, stString);
               return var82;
            }

            if (treeRuleReturn != null) {
               var82 = new gUnitTestResult(true, stdout, String.valueOf(treeRuleReturn));
               return var82;
            }

            var82 = new gUnitTestResult(true, stdout, stdout);
            return var82;
         }

         gUnitTestResult testResult = new gUnitTestResult(false, err.toString());
         testResult.setError(err.toString());
         var82 = testResult;
         return var82;
      } catch (IOException var69) {
         var14 = this.getTestExceptionResult(var69);
      } catch (ClassNotFoundException var70) {
         this.handleUnexpectedException(var70);
         throw new Exception("Should not be reachable?");
      } catch (SecurityException var71) {
         this.handleUnexpectedException(var71);
         throw new Exception("Should not be reachable?");
      } catch (NoSuchMethodException var72) {
         this.handleUnexpectedException(var72);
         throw new Exception("Should not be reachable?");
      } catch (IllegalArgumentException var73) {
         this.handleUnexpectedException(var73);
         throw new Exception("Should not be reachable?");
      } catch (InstantiationException var74) {
         this.handleUnexpectedException(var74);
         throw new Exception("Should not be reachable?");
      } catch (IllegalAccessException var75) {
         this.handleUnexpectedException(var75);
         throw new Exception("Should not be reachable?");
      } catch (InvocationTargetException var76) {
         var14 = this.getTestExceptionResult(var76);
         return var14;
      } finally {
         try {
            if (ps != null) {
               ps.close();
            }

            if (ps2 != null) {
               ps2.close();
            }

            System.setOut(this.console);
            System.setErr(this.consoleErr);
         } catch (Exception var67) {
            var67.printStackTrace();
         }

      }

      return var14;
   }

   private CharStream getANTLRInputStream(gUnitTestInput testInput) throws IOException {
      Object input;
      if (testInput.isFile) {
         String filePath = testInput.input;
         File testInputFile = new File(filePath);
         if (!testInputFile.exists()) {
            testInputFile = new File(this.testsuiteDir, filePath);
            if (testInputFile.exists()) {
               filePath = testInputFile.getCanonicalPath();
            } else if (this.grammarInfo.getGrammarPackage() != null) {
               testInputFile = new File("." + File.separator + this.grammarInfo.getGrammarPackage().replace(".", File.separator), filePath);
               if (testInputFile.exists()) {
                  filePath = testInputFile.getCanonicalPath();
               }
            }
         }

         input = new ANTLRFileStream(filePath);
      } else {
         input = new ANTLRStringStream(testInput.input);
      }

      return (CharStream)input;
   }

   private gUnitTestResult getTestExceptionResult(Exception e) {
      gUnitTestResult testResult;
      if (e.getCause() != null) {
         testResult = new gUnitTestResult(false, e.getCause().toString(), true);
         testResult.setError(e.getCause().toString());
      } else {
         testResult = new gUnitTestResult(false, e.toString(), true);
         testResult.setError(e.toString());
      }

      return testResult;
   }

   protected void checkForValidInput(CommonTokenStream tokens, PrintStream ps2) {
      if (tokens.index() != tokens.size() - 1) {
         List endingTokens = tokens.getTokens(tokens.index(), tokens.size() - 1);
         Iterator i$ = endingTokens.iterator();

         while(i$.hasNext()) {
            Token endToken = (Token)i$.next();
            if (!"<EOF>".equals(endToken.getText())) {
               ps2.print("Invalid input");
               return;
            }
         }
      }

   }

   public void onPass(ITestCase passTest) {
   }

   public void onFail(ITestCase failTest) {
   }

   protected void handleUnexpectedException(Exception e) {
      e.printStackTrace();
      System.exit(1);
   }
}
