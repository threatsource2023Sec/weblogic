package org.antlr.gunit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import junit.framework.TestCase;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.stringtemplate.StringTemplate;

public abstract class gUnitBaseTest extends TestCase {
   public String treeAdaptorPath;
   public String packagePath;
   public String lexerPath;
   public String parserPath;
   public String treeParserPath;
   protected String stdout;
   protected String stderr;
   private PrintStream console;
   private PrintStream consoleErr;

   public gUnitBaseTest() {
      this.console = System.out;
      this.consoleErr = System.err;
   }

   public String execLexer(String testRuleName, int line, String testInput, boolean isFile) throws Exception {
      Object input;
      if (isFile) {
         String filePath = testInput;
         File testInputFile = new File(testInput);
         if (!testInputFile.exists() && this.packagePath != null) {
            testInputFile = new File(this.packagePath, testInput);
            if (testInputFile.exists()) {
               filePath = testInputFile.getCanonicalPath();
            }
         }

         input = new ANTLRFileStream(filePath);
      } else {
         input = new ANTLRStringStream(testInput);
      }

      PrintStream ps = null;
      PrintStream ps2 = null;

      String var16;
      try {
         Class lexer = Class.forName(this.lexerPath).asSubclass(Lexer.class);
         Constructor lexConstructor = lexer.getConstructor(CharStream.class);
         Lexer lexObj = (Lexer)lexConstructor.newInstance(input);
         ((CharStream)input).setLine(line);
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
         if (currentIndex != ((CharStream)input).size()) {
            ps2.println("extra text found, '" + ((CharStream)input).substring(currentIndex, ((CharStream)input).size() - 1) + "'");
         }

         this.stdout = null;
         this.stderr = null;
         if (err.toString().length() <= 0) {
            if (out.toString().length() > 0) {
               this.stdout = out.toString();
            }

            if (err.toString().length() != 0 || out.toString().length() != 0) {
               return this.stdout;
            }

            var16 = null;
            return var16;
         }

         this.stderr = err.toString();
         var16 = this.stderr;
      } catch (ClassNotFoundException var40) {
         this.handleUnexpectedException(var40);
         return this.stdout;
      } catch (SecurityException var41) {
         this.handleUnexpectedException(var41);
         return this.stdout;
      } catch (NoSuchMethodException var42) {
         this.handleUnexpectedException(var42);
         return this.stdout;
      } catch (IllegalArgumentException var43) {
         this.handleUnexpectedException(var43);
         return this.stdout;
      } catch (InstantiationException var44) {
         this.handleUnexpectedException(var44);
         return this.stdout;
      } catch (IllegalAccessException var45) {
         this.handleUnexpectedException(var45);
         return this.stdout;
      } catch (InvocationTargetException var46) {
         if (var46.getCause() != null) {
            this.stderr = var46.getCause().toString();
         } else {
            this.stderr = var46.toString();
         }

         String var10 = this.stderr;
         return var10;
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
         } catch (Exception var39) {
            var39.printStackTrace();
         }

      }

      return var16;
   }

   public Object execParser(String testRuleName, int line, String testInput, boolean isFile) throws Exception {
      Object input;
      if (isFile) {
         String filePath = testInput;
         File testInputFile = new File(testInput);
         if (!testInputFile.exists() && this.packagePath != null) {
            testInputFile = new File(this.packagePath, testInput);
            if (testInputFile.exists()) {
               filePath = testInputFile.getCanonicalPath();
            }
         }

         input = new ANTLRFileStream(filePath);
      } else {
         input = new ANTLRStringStream(testInput);
      }

      PrintStream ps = null;
      PrintStream ps2 = null;
      ByteArrayOutputStream out = null;
      ByteArrayOutputStream err = null;

      String var63;
      try {
         Class lexer = Class.forName(this.lexerPath).asSubclass(Lexer.class);
         Constructor lexConstructor = lexer.getConstructor(CharStream.class);
         Lexer lexObj = (Lexer)lexConstructor.newInstance(input);
         ((CharStream)input).setLine(line);
         CommonTokenStream tokens = new CommonTokenStream(lexObj);
         Class parser = Class.forName(this.parserPath).asSubclass(Parser.class);
         Constructor parConstructor = parser.getConstructor(TokenStream.class);
         Parser parObj = (Parser)parConstructor.newInstance(tokens);
         Method ruleName;
         if (this.treeAdaptorPath != null) {
            ruleName = parser.getMethod("setTreeAdaptor", TreeAdaptor.class);
            Class _treeAdaptor = Class.forName(this.treeAdaptorPath).asSubclass(TreeAdaptor.class);
            ruleName.invoke(parObj, _treeAdaptor.newInstance());
         }

         ruleName = parser.getMethod(testRuleName);
         out = new ByteArrayOutputStream();
         err = new ByteArrayOutputStream();
         ps = new PrintStream(out);
         ps2 = new PrintStream(err);
         System.setOut(ps);
         System.setErr(ps2);
         Object ruleReturn = ruleName.invoke(parObj);
         String astString = null;
         String stString = null;
         Class _return;
         if (ruleReturn != null && ruleReturn.getClass().toString().indexOf(testRuleName + "_return") > 0) {
            try {
               _return = Class.forName(this.parserPath + "$" + testRuleName + "_return");
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
            } catch (Exception var52) {
               System.err.println(var52);
            }
         }

         this.stdout = "";
         this.stderr = "";
         if (tokens.index() != tokens.size() - 1) {
            this.stderr = this.stderr + "Stopped parsing at token index " + tokens.index() + ": ";
         }

         this.stdout = this.stdout + out.toString();
         this.stderr = this.stderr + err.toString();
         if (err.toString().length() > 0) {
            var63 = this.stderr;
            return var63;
         }

         if (out.toString().length() > 0) {
            var63 = this.stdout;
            return var63;
         }

         if (astString == null) {
            if (stString != null) {
               var63 = stString;
               return var63;
            }

            if (ruleReturn != null) {
               Object var64 = ruleReturn;
               return var64;
            }

            if (err.toString().length() != 0 || out.toString().length() != 0) {
               return this.stdout;
            }

            _return = null;
            return _return;
         }

         var63 = astString;
      } catch (ClassNotFoundException var53) {
         this.handleUnexpectedException(var53);
         return this.stdout;
      } catch (SecurityException var54) {
         this.handleUnexpectedException(var54);
         return this.stdout;
      } catch (NoSuchMethodException var55) {
         this.handleUnexpectedException(var55);
         return this.stdout;
      } catch (IllegalAccessException var56) {
         this.handleUnexpectedException(var56);
         return this.stdout;
      } catch (InvocationTargetException var57) {
         this.stdout = out.toString();
         this.stderr = err.toString();
         if (var57.getCause() != null) {
            this.stderr = this.stderr + var57.getCause().toString();
         } else {
            this.stderr = this.stderr + var57.toString();
         }

         String var13 = this.stderr;
         return var13;
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
         } catch (Exception var51) {
            var51.printStackTrace();
         }

      }

      return var63;
   }

   public Object execTreeParser(String testTreeRuleName, String testRuleName, String testInput, boolean isFile) throws Exception {
      Object input;
      if (isFile) {
         String filePath = testInput;
         File testInputFile = new File(testInput);
         if (!testInputFile.exists() && this.packagePath != null) {
            testInputFile = new File(this.packagePath, testInput);
            if (testInputFile.exists()) {
               filePath = testInputFile.getCanonicalPath();
            }
         }

         input = new ANTLRFileStream(filePath);
      } else {
         input = new ANTLRStringStream(testInput);
      }

      PrintStream ps = null;
      PrintStream ps2 = null;

      String var72;
      try {
         Class lexer = Class.forName(this.lexerPath).asSubclass(Lexer.class);
         Constructor lexConstructor = lexer.getConstructor(CharStream.class);
         Lexer lexObj = (Lexer)lexConstructor.newInstance(input);
         CommonTokenStream tokens = new CommonTokenStream(lexObj);
         Class parser = Class.forName(this.parserPath).asSubclass(Parser.class);
         Constructor parConstructor = parser.getConstructor(TokenStream.class);
         Parser parObj = (Parser)parConstructor.newInstance(tokens);
         TreeAdaptor customTreeAdaptor = null;
         Method ruleName;
         if (this.treeAdaptorPath != null) {
            ruleName = parser.getMethod("setTreeAdaptor", TreeAdaptor.class);
            Class _treeAdaptor = Class.forName(this.treeAdaptorPath).asSubclass(TreeAdaptor.class);
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
         Class _return = Class.forName(this.parserPath + "$" + testRuleName + "_return");
         Method returnName = _return.getMethod("getTree");
         CommonTree tree = (CommonTree)returnName.invoke(ruleReturn);
         CommonTreeNodeStream nodes;
         if (customTreeAdaptor != null) {
            nodes = new CommonTreeNodeStream(customTreeAdaptor, tree);
         } else {
            nodes = new CommonTreeNodeStream(tree);
         }

         nodes.setTokenStream(tokens);
         Class treeParser = Class.forName(this.treeParserPath).asSubclass(TreeParser.class);
         Constructor treeParConstructor = treeParser.getConstructor(TreeNodeStream.class);
         TreeParser treeParObj = (TreeParser)treeParConstructor.newInstance(nodes);
         Method treeRuleName = treeParser.getMethod(testTreeRuleName);
         Object treeRuleReturn = treeRuleName.invoke(treeParObj);
         String astString = null;
         String stString = null;
         Class _treeReturn;
         if (treeRuleReturn != null && treeRuleReturn.getClass().toString().indexOf(testTreeRuleName + "_return") > 0) {
            try {
               _treeReturn = Class.forName(this.treeParserPath + "$" + testTreeRuleName + "_return");
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
            } catch (Exception var61) {
               System.err.println(var61);
            }
         }

         this.stdout = null;
         this.stderr = null;
         if (tokens.index() != tokens.size() - 1) {
            throw new InvalidInputException();
         }

         if (err.toString().length() > 0) {
            this.stderr = err.toString();
            var72 = this.stderr;
            return var72;
         }

         if (out.toString().length() > 0) {
            this.stdout = out.toString();
         }

         if (astString != null) {
            var72 = astString;
            return var72;
         }

         if (stString == null) {
            if (treeRuleReturn != null) {
               Object var73 = treeRuleReturn;
               return var73;
            }

            if (err.toString().length() == 0 && out.toString().length() == 0) {
               _treeReturn = null;
               return _treeReturn;
            }

            return this.stdout;
         }

         var72 = stString;
      } catch (ClassNotFoundException var62) {
         this.handleUnexpectedException(var62);
         return this.stdout;
      } catch (SecurityException var63) {
         this.handleUnexpectedException(var63);
         return this.stdout;
      } catch (NoSuchMethodException var64) {
         this.handleUnexpectedException(var64);
         return this.stdout;
      } catch (IllegalAccessException var65) {
         this.handleUnexpectedException(var65);
         return this.stdout;
      } catch (InvocationTargetException var66) {
         if (var66.getCause() != null) {
            this.stderr = var66.getCause().toString();
         } else {
            this.stderr = var66.toString();
         }

         String var12 = this.stderr;
         return var12;
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
         } catch (Exception var60) {
            var60.printStackTrace();
         }

      }

      return var72;
   }

   public Object examineExecResult(int tokenType, Object retVal) {
      System.out.println("expect " + (tokenType == 16 ? "OK" : "FAIL") + "stderr==" + this.stderr);
      if (tokenType == 16) {
         return this.stderr != null && this.stderr.length() != 0 ? "FAIL, " + this.stderr : "OK";
      } else if (tokenType == 10) {
         return this.stderr != null && this.stderr.length() > 0 ? "FAIL" : "OK";
      } else {
         return retVal;
      }
   }

   protected void handleUnexpectedException(Exception e) {
      e.printStackTrace();
      System.exit(1);
   }
}
