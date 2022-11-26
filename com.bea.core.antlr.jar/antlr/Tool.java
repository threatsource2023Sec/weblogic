package antlr;

import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;

public class Tool {
   public static String version = "";
   ToolErrorHandler errorHandler;
   protected boolean hasError = false;
   boolean genDiagnostics = false;
   boolean genDocBook = false;
   boolean genHTML = false;
   protected String outputDir = ".";
   protected String grammarFile;
   transient Reader f;
   protected String literalsPrefix;
   protected boolean upperCaseMangledLiterals;
   protected NameSpace nameSpace;
   protected String namespaceAntlr;
   protected String namespaceStd;
   protected boolean genHashLines;
   protected boolean noConstructors;
   private BitSet cmdLineArgValid;

   public Tool() {
      this.f = new InputStreamReader(System.in);
      this.literalsPrefix = "LITERAL_";
      this.upperCaseMangledLiterals = false;
      this.nameSpace = null;
      this.namespaceAntlr = null;
      this.namespaceStd = null;
      this.genHashLines = true;
      this.noConstructors = false;
      this.cmdLineArgValid = new BitSet();
      this.errorHandler = new DefaultToolErrorHandler(this);
   }

   public String getGrammarFile() {
      return this.grammarFile;
   }

   public boolean hasError() {
      return this.hasError;
   }

   public NameSpace getNameSpace() {
      return this.nameSpace;
   }

   public String getNamespaceStd() {
      return this.namespaceStd;
   }

   public String getNamespaceAntlr() {
      return this.namespaceAntlr;
   }

   public boolean getGenHashLines() {
      return this.genHashLines;
   }

   public String getLiteralsPrefix() {
      return this.literalsPrefix;
   }

   public boolean getUpperCaseMangledLiterals() {
      return this.upperCaseMangledLiterals;
   }

   public void setFileLineFormatter(FileLineFormatter var1) {
      FileLineFormatter.setFormatter(var1);
   }

   protected void checkForInvalidArguments(String[] var1, BitSet var2) {
      for(int var3 = 0; var3 < var1.length; ++var3) {
         if (!var2.member(var3)) {
            this.warning("invalid command-line argument: " + var1[var3] + "; ignored");
         }
      }

   }

   public void copyFile(String var1, String var2) throws IOException {
      File var3 = new File(var1);
      File var4 = new File(var2);
      BufferedReader var5 = null;
      BufferedWriter var6 = null;

      try {
         if (!var3.exists() || !var3.isFile()) {
            throw new FileCopyException("FileCopy: no such source file: " + var1);
         } else if (!var3.canRead()) {
            throw new FileCopyException("FileCopy: source file is unreadable: " + var1);
         } else {
            if (var4.exists()) {
               if (!var4.isFile()) {
                  throw new FileCopyException("FileCopy: destination is not a file: " + var2);
               }

               new DataInputStream(System.in);
               if (!var4.canWrite()) {
                  throw new FileCopyException("FileCopy: destination file is unwriteable: " + var2);
               }
            } else {
               File var9 = this.parent(var4);
               if (!var9.exists()) {
                  throw new FileCopyException("FileCopy: destination directory doesn't exist: " + var2);
               }

               if (!var9.canWrite()) {
                  throw new FileCopyException("FileCopy: destination directory is unwriteable: " + var2);
               }
            }

            var5 = new BufferedReader(new FileReader(var3));
            var6 = new BufferedWriter(new FileWriter(var4));
            char[] var7 = new char[1024];

            while(true) {
               int var8 = var5.read(var7, 0, 1024);
               if (var8 == -1) {
                  return;
               }

               var6.write(var7, 0, var8);
            }
         }
      } finally {
         if (var5 != null) {
            try {
               var5.close();
            } catch (IOException var20) {
            }
         }

         if (var6 != null) {
            try {
               var6.close();
            } catch (IOException var19) {
            }
         }

      }
   }

   public void doEverythingWrapper(String[] var1) {
      int var2 = this.doEverything(var1);
      System.exit(var2);
   }

   public int doEverything(String[] var1) {
      antlr.preprocessor.Tool var2 = new antlr.preprocessor.Tool(this, var1);
      boolean var3 = var2.preprocess();
      String[] var4 = var2.preprocessedArgList();
      this.processArguments(var4);
      if (!var3) {
         return 1;
      } else {
         this.f = this.getGrammarReader();
         ANTLRLexer var5 = new ANTLRLexer(this.f);
         TokenBuffer var6 = new TokenBuffer(var5);
         LLkAnalyzer var7 = new LLkAnalyzer(this);
         MakeGrammar var8 = new MakeGrammar(this, var1, var7);

         try {
            ANTLRParser var9 = new ANTLRParser(var6, var8, this);
            var9.setFilename(this.grammarFile);
            var9.grammar();
            if (this.hasError()) {
               this.fatalError("Exiting due to errors.");
            }

            this.checkForInvalidArguments(var4, this.cmdLineArgValid);
            String var11 = "antlr." + this.getLanguage(var8) + "CodeGenerator";

            try {
               CodeGenerator var10 = (CodeGenerator)Utils.createInstanceOf(var11);
               var10.setBehavior(var8);
               var10.setAnalyzer(var7);
               var10.setTool(this);
               var10.gen();
            } catch (ClassNotFoundException var13) {
               this.panic("Cannot instantiate code-generator: " + var11);
            } catch (InstantiationException var14) {
               this.panic("Cannot instantiate code-generator: " + var11);
            } catch (IllegalArgumentException var15) {
               this.panic("Cannot instantiate code-generator: " + var11);
            } catch (IllegalAccessException var16) {
               this.panic("code-generator class '" + var11 + "' is not accessible");
            }
         } catch (RecognitionException var17) {
            this.fatalError("Unhandled parser error: " + var17.getMessage());
         } catch (TokenStreamException var18) {
            this.fatalError("TokenStreamException: " + var18.getMessage());
         }

         return 0;
      }
   }

   public void error(String var1) {
      this.hasError = true;
      System.err.println("error: " + var1);
   }

   public void error(String var1, String var2, int var3, int var4) {
      this.hasError = true;
      System.err.println(FileLineFormatter.getFormatter().getFormatString(var2, var3, var4) + var1);
   }

   public String fileMinusPath(String var1) {
      String var2 = System.getProperty("file.separator");
      int var3 = var1.lastIndexOf(var2);
      return var3 == -1 ? var1 : var1.substring(var3 + 1);
   }

   public String getLanguage(MakeGrammar var1) {
      if (this.genDiagnostics) {
         return "Diagnostic";
      } else if (this.genHTML) {
         return "HTML";
      } else {
         return this.genDocBook ? "DocBook" : var1.language;
      }
   }

   public String getOutputDirectory() {
      return this.outputDir;
   }

   private static void help() {
      System.err.println("usage: java antlr.Tool [args] file.g");
      System.err.println("  -o outputDir       specify output directory where all output generated.");
      System.err.println("  -glib superGrammar specify location of supergrammar file.");
      System.err.println("  -debug             launch the ParseView debugger upon parser invocation.");
      System.err.println("  -html              generate a html file from your grammar.");
      System.err.println("  -docbook           generate a docbook sgml file from your grammar.");
      System.err.println("  -diagnostic        generate a textfile with diagnostics.");
      System.err.println("  -trace             have all rules call traceIn/traceOut.");
      System.err.println("  -traceLexer        have lexer rules call traceIn/traceOut.");
      System.err.println("  -traceParser       have parser rules call traceIn/traceOut.");
      System.err.println("  -traceTreeParser   have tree parser rules call traceIn/traceOut.");
      System.err.println("  -h|-help|--help    this message");
   }

   public static void main(String[] var0) {
      System.err.println("ANTLR Parser Generator   Version 2.7.7 (20060906)   1989-2005");
      version = "2.7.7 (20060906)";

      try {
         boolean var1 = false;
         if (var0.length == 0) {
            var1 = true;
         } else {
            for(int var2 = 0; var2 < var0.length; ++var2) {
               if (var0[var2].equals("-h") || var0[var2].equals("-help") || var0[var2].equals("--help")) {
                  var1 = true;
                  break;
               }
            }
         }

         if (var1) {
            help();
         } else {
            Tool var4 = new Tool();
            var4.doEverything(var0);
            var4 = null;
         }
      } catch (Exception var3) {
         System.err.println(System.getProperty("line.separator") + System.getProperty("line.separator"));
         System.err.println("#$%%*&@# internal error: " + var3.toString());
         System.err.println("[complain to nearest government official");
         System.err.println(" or send hate-mail to parrt@antlr.org;");
         System.err.println(" please send stack trace with report.]" + System.getProperty("line.separator"));
         var3.printStackTrace();
      }

   }

   public PrintWriter openOutputFile(String var1) throws IOException {
      if (this.outputDir != ".") {
         File var2 = new File(this.outputDir);
         if (!var2.exists()) {
            var2.mkdirs();
         }
      }

      return new PrintWriter(new PreservingFileWriter(this.outputDir + System.getProperty("file.separator") + var1));
   }

   public Reader getGrammarReader() {
      BufferedReader var1 = null;

      try {
         if (this.grammarFile != null) {
            var1 = new BufferedReader(new FileReader(this.grammarFile));
         }
      } catch (IOException var3) {
         this.fatalError("cannot open grammar file " + this.grammarFile);
      }

      return var1;
   }

   public void reportException(Exception var1, String var2) {
      System.err.println(var2 == null ? var1.getMessage() : var2 + ": " + var1.getMessage());
   }

   public void reportProgress(String var1) {
      System.out.println(var1);
   }

   public void fatalError(String var1) {
      System.err.println(var1);
      Utils.error(var1);
   }

   /** @deprecated */
   public void panic() {
      this.fatalError("panic");
   }

   /** @deprecated */
   public void panic(String var1) {
      this.fatalError("panic: " + var1);
   }

   public File parent(File var1) {
      String var2 = var1.getParent();
      if (var2 == null) {
         return var1.isAbsolute() ? new File(File.separator) : new File(System.getProperty("user.dir"));
      } else {
         return new File(var2);
      }
   }

   public static Vector parseSeparatedList(String var0, char var1) {
      StringTokenizer var2 = new StringTokenizer(var0, String.valueOf(var1));
      Vector var3 = new Vector(10);

      while(var2.hasMoreTokens()) {
         var3.appendElement(var2.nextToken());
      }

      return var3.size() == 0 ? null : var3;
   }

   public String pathToFile(String var1) {
      String var2 = System.getProperty("file.separator");
      int var3 = var1.lastIndexOf(var2);
      return var3 == -1 ? "." + System.getProperty("file.separator") : var1.substring(0, var3 + 1);
   }

   protected void processArguments(String[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].equals("-diagnostic")) {
            this.genDiagnostics = true;
            this.genHTML = false;
            this.setArgOK(var2);
         } else if (var1[var2].equals("-o")) {
            this.setArgOK(var2);
            if (var2 + 1 >= var1.length) {
               this.error("missing output directory with -o option; ignoring");
            } else {
               ++var2;
               this.setOutputDirectory(var1[var2]);
               this.setArgOK(var2);
            }
         } else if (var1[var2].equals("-html")) {
            this.genHTML = true;
            this.genDiagnostics = false;
            this.setArgOK(var2);
         } else if (var1[var2].equals("-docbook")) {
            this.genDocBook = true;
            this.genDiagnostics = false;
            this.setArgOK(var2);
         } else if (var1[var2].charAt(0) != '-') {
            this.grammarFile = var1[var2];
            this.setArgOK(var2);
         }
      }

   }

   public void setArgOK(int var1) {
      this.cmdLineArgValid.add(var1);
   }

   public void setOutputDirectory(String var1) {
      this.outputDir = var1;
   }

   public void toolError(String var1) {
      System.err.println("error: " + var1);
   }

   public void warning(String var1) {
      System.err.println("warning: " + var1);
   }

   public void warning(String var1, String var2, int var3, int var4) {
      System.err.println(FileLineFormatter.getFormatter().getFormatString(var2, var3, var4) + "warning:" + var1);
   }

   public void warning(String[] var1, String var2, int var3, int var4) {
      if (var1 == null || var1.length == 0) {
         this.panic("bad multi-line message to Tool.warning");
      }

      System.err.println(FileLineFormatter.getFormatter().getFormatString(var2, var3, var4) + "warning:" + var1[0]);

      for(int var5 = 1; var5 < var1.length; ++var5) {
         System.err.println(FileLineFormatter.getFormatter().getFormatString(var2, var3, var4) + "    " + var1[var5]);
      }

   }

   public void setNameSpace(String var1) {
      if (null == this.nameSpace) {
         this.nameSpace = new NameSpace(StringUtils.stripFrontBack(var1, "\"", "\""));
      }

   }
}
