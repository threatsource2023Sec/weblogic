package org.antlr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.antlr.analysis.DFA;
import org.antlr.analysis.DFAOptimizer;
import org.antlr.analysis.DecisionProbe;
import org.antlr.analysis.NFAContext;
import org.antlr.analysis.NFAToDFAConverter;
import org.antlr.codegen.CodeGenerator;
import org.antlr.misc.Graph;
import org.antlr.runtime.misc.Stats;
import org.antlr.tool.BuildDependencyGenerator;
import org.antlr.tool.CompositeGrammar;
import org.antlr.tool.DOTGenerator;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarReport;
import org.antlr.tool.GrammarReport2;
import org.antlr.tool.GrammarSpelunker;
import org.antlr.tool.Rule;
import org.stringtemplate.v4.STGroup;

public class Tool {
   public final Properties antlrSettings = new Properties();
   public final String VERSION;
   public static final String UNINITIALIZED_DIR = "<unset-dir>";
   private List grammarFileNames;
   private boolean generate_NFA_dot;
   private boolean generate_DFA_dot;
   private String outputDirectory;
   private boolean haveOutputDir;
   private String inputDirectory;
   private String parentGrammarDirectory;
   private String grammarOutputDirectory;
   private boolean haveInputDir;
   private String libDirectory;
   private boolean debug;
   private boolean trace;
   private boolean profile;
   private boolean report;
   private boolean printGrammar;
   private boolean depend;
   private boolean forceAllFilesToOutputDir;
   private boolean forceRelativeOutput;
   protected boolean deleteTempLexer;
   private boolean verbose;
   private boolean make;
   private boolean showBanner;
   private static boolean exitNow = false;
   private static boolean return_dont_exit = false;
   public String forcedLanguageOption;
   public static boolean internalOption_PrintGrammarTree = false;
   public static boolean internalOption_PrintDFA = false;
   public static boolean internalOption_ShowNFAConfigsInDFA = false;
   public static boolean internalOption_watchNFAConversion = false;

   public static void main(String[] args) {
      Tool antlr = new Tool(args);
      if (!exitNow) {
         antlr.process();
         if (return_dont_exit) {
            return;
         }

         if (ErrorManager.getNumErrors() > 0) {
            System.exit(1);
         }

         System.exit(0);
      }

   }

   private void loadResources() {
      InputStream in = this.getClass().getResourceAsStream("antlr.properties");
      if (in != null) {
         try {
            this.antlrSettings.load(in);
         } catch (Exception var3) {
         }
      }

   }

   public Tool() {
      String version = Tool.class.getPackage().getImplementationVersion();
      this.VERSION = version != null ? version : "3.x";
      this.grammarFileNames = new ArrayList();
      this.generate_NFA_dot = false;
      this.generate_DFA_dot = false;
      this.outputDirectory = ".";
      this.haveOutputDir = false;
      this.inputDirectory = null;
      this.haveInputDir = false;
      this.libDirectory = ".";
      this.debug = false;
      this.trace = false;
      this.profile = false;
      this.report = false;
      this.printGrammar = false;
      this.depend = false;
      this.forceAllFilesToOutputDir = false;
      this.forceRelativeOutput = false;
      this.deleteTempLexer = true;
      this.verbose = false;
      this.make = false;
      this.showBanner = true;
      this.loadResources();
   }

   public Tool(String[] args) {
      String version = Tool.class.getPackage().getImplementationVersion();
      this.VERSION = version != null ? version : "3.x";
      this.grammarFileNames = new ArrayList();
      this.generate_NFA_dot = false;
      this.generate_DFA_dot = false;
      this.outputDirectory = ".";
      this.haveOutputDir = false;
      this.inputDirectory = null;
      this.haveInputDir = false;
      this.libDirectory = ".";
      this.debug = false;
      this.trace = false;
      this.profile = false;
      this.report = false;
      this.printGrammar = false;
      this.depend = false;
      this.forceAllFilesToOutputDir = false;
      this.forceRelativeOutput = false;
      this.deleteTempLexer = true;
      this.verbose = false;
      this.make = false;
      this.showBanner = true;
      this.loadResources();
      this.processArgs(args);
   }

   public void processArgs(String[] args) {
      if (this.isVerbose()) {
         ErrorManager.info("ANTLR Parser Generator  Version " + this.VERSION);
         this.showBanner = false;
      }

      if (args != null && args.length != 0) {
         for(int i = 0; i < args.length; ++i) {
            File outDir;
            if (!args[i].equals("-o") && !args[i].equals("-fo")) {
               if (args[i].equals("-lib")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing library directory with -lib option; ignoring");
                  } else {
                     ++i;
                     this.setLibDirectory(args[i]);
                     if (this.getLibraryDirectory().endsWith("/") || this.getLibraryDirectory().endsWith("\\")) {
                        this.setLibDirectory(this.getLibraryDirectory().substring(0, this.getLibraryDirectory().length() - 1));
                     }

                     outDir = new File(this.getLibraryDirectory());
                     if (!outDir.exists()) {
                        ErrorManager.error(5, (Object)this.getLibraryDirectory());
                        this.setLibDirectory(".");
                     }
                  }
               } else if (args[i].equals("-language")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing language name; ignoring");
                  } else {
                     ++i;
                     this.forcedLanguageOption = args[i];
                  }
               } else if (args[i].equals("-nfa")) {
                  this.setGenerate_NFA_dot(true);
               } else if (args[i].equals("-dfa")) {
                  this.setGenerate_DFA_dot(true);
               } else if (args[i].equals("-debug")) {
                  this.setDebug(true);
               } else if (args[i].equals("-trace")) {
                  this.setTrace(true);
               } else if (args[i].equals("-report")) {
                  this.setReport(true);
               } else if (args[i].equals("-profile")) {
                  this.setProfile(true);
               } else if (args[i].equals("-print")) {
                  this.setPrintGrammar(true);
               } else if (args[i].equals("-depend")) {
                  this.setDepend(true);
               } else if (args[i].equals("-verbose")) {
                  this.setVerbose(true);
               } else if (args[i].equals("-version")) {
                  version();
                  exitNow = true;
               } else if (args[i].equals("-make")) {
                  this.setMake(true);
               } else if (args[i].equals("-message-format")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing output format with -message-format option; using default");
                  } else {
                     ++i;
                     ErrorManager.setFormat(args[i]);
                  }
               } else if (args[i].equals("-Xgrtree")) {
                  internalOption_PrintGrammarTree = true;
               } else if (args[i].equals("-Xdfa")) {
                  internalOption_PrintDFA = true;
               } else if (args[i].equals("-Xnoprune")) {
                  DFAOptimizer.PRUNE_EBNF_EXIT_BRANCHES = false;
               } else if (args[i].equals("-Xnocollapse")) {
                  DFAOptimizer.COLLAPSE_ALL_PARALLEL_EDGES = false;
               } else if (args[i].equals("-Xdbgconversion")) {
                  NFAToDFAConverter.debug = true;
               } else if (args[i].equals("-Xmultithreaded")) {
                  NFAToDFAConverter.SINGLE_THREADED_NFA_CONVERSION = false;
               } else if (args[i].equals("-Xnomergestopstates")) {
                  DFAOptimizer.MERGE_STOP_STATES = false;
               } else if (args[i].equals("-Xdfaverbose")) {
                  internalOption_ShowNFAConfigsInDFA = true;
               } else if (args[i].equals("-Xwatchconversion")) {
                  internalOption_watchNFAConversion = true;
               } else if (args[i].equals("-XdbgST")) {
                  CodeGenerator.LAUNCH_ST_INSPECTOR = true;
                  STGroup.trackCreationEvents = true;
                  return_dont_exit = true;
               } else if (args[i].equals("-Xmaxinlinedfastates")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing max inline dfa states -Xmaxinlinedfastates option; ignoring");
                  } else {
                     ++i;
                     CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE = Integer.parseInt(args[i]);
                  }
               } else if (args[i].equals("-Xmaxswitchcaselabels")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing max switch case labels -Xmaxswitchcaselabels option; ignoring");
                  } else {
                     ++i;
                     CodeGenerator.MAX_SWITCH_CASE_LABELS = Integer.parseInt(args[i]);
                  }
               } else if (args[i].equals("-Xminswitchalts")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing min switch alternatives -Xminswitchalts option; ignoring");
                  } else {
                     ++i;
                     CodeGenerator.MIN_SWITCH_ALTS = Integer.parseInt(args[i]);
                  }
               } else if (args[i].equals("-Xm")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing max recursion with -Xm option; ignoring");
                  } else {
                     ++i;
                     NFAContext.MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK = Integer.parseInt(args[i]);
                  }
               } else if (args[i].equals("-Xmaxdfaedges")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing max number of edges with -Xmaxdfaedges option; ignoring");
                  } else {
                     ++i;
                     DFA.MAX_STATE_TRANSITIONS_FOR_TABLE = Integer.parseInt(args[i]);
                  }
               } else if (args[i].equals("-Xconversiontimeout")) {
                  if (i + 1 >= args.length) {
                     System.err.println("missing max time in ms -Xconversiontimeout option; ignoring");
                  } else {
                     ++i;
                     DFA.MAX_TIME_PER_DFA_CREATION = Integer.parseInt(args[i]);
                  }
               } else if (args[i].equals("-Xnfastates")) {
                  DecisionProbe.verbose = true;
               } else if (args[i].equals("-Xsavelexer")) {
                  this.deleteTempLexer = false;
               } else if (args[i].equals("-X")) {
                  Xhelp();
               } else if (args[i].charAt(0) != '-') {
                  this.addGrammarFile(args[i]);
               }
            } else if (i + 1 >= args.length) {
               System.err.println("missing output directory with -fo/-o option; ignoring");
            } else {
               if (args[i].equals("-fo")) {
                  this.setForceAllFilesToOutputDir(true);
               }

               ++i;
               this.outputDirectory = args[i];
               if (this.outputDirectory.endsWith("/") || this.outputDirectory.endsWith("\\")) {
                  this.outputDirectory = this.outputDirectory.substring(0, this.getOutputDirectory().length() - 1);
               }

               outDir = new File(this.outputDirectory);
               this.haveOutputDir = true;
               if (outDir.exists() && !outDir.isDirectory()) {
                  ErrorManager.error(6, (Object)this.outputDirectory);
                  this.setLibDirectory(".");
               }
            }
         }

      } else {
         help();
      }
   }

   public boolean buildRequired(String grammarFileName) throws IOException {
      BuildDependencyGenerator bd = new BuildDependencyGenerator(this, grammarFileName);
      List outputFiles = bd.getGeneratedFileList();
      List inputFiles = bd.getDependenciesFileList();
      File grammarFile;
      if (this.haveInputDir) {
         grammarFile = new File(this.inputDirectory, grammarFileName);
      } else {
         grammarFile = new File(grammarFileName);
      }

      long grammarLastModified = grammarFile.lastModified();
      Iterator i$ = outputFiles.iterator();

      while(i$.hasNext()) {
         File outputFile = (File)i$.next();
         if (!outputFile.exists() || grammarLastModified > outputFile.lastModified()) {
            if (this.isVerbose()) {
               if (!outputFile.exists()) {
                  System.out.println("Output file " + outputFile + " does not exist: must build " + grammarFile);
               } else {
                  System.out.println("Output file " + outputFile + " is not up-to-date: must build " + grammarFile);
               }
            }

            return true;
         }

         if (inputFiles != null) {
            Iterator i$ = inputFiles.iterator();

            while(i$.hasNext()) {
               File inputFile = (File)i$.next();
               if (inputFile.lastModified() > outputFile.lastModified()) {
                  if (this.isVerbose()) {
                     System.out.println("Input file " + inputFile + " is newer than output: must rebuild " + grammarFile);
                  }

                  return true;
               }
            }
         }
      }

      if (this.isVerbose()) {
         System.out.println("Grammar " + grammarFile + " is up to date - build skipped");
      }

      return false;
   }

   public void process() {
      boolean exceptionWhenWritingLexerFile = false;
      if (this.isVerbose() && this.showBanner) {
         ErrorManager.info("ANTLR Parser Generator  Version " + this.VERSION);
         this.showBanner = false;
      }

      try {
         this.sortGrammarFiles();
      } catch (Exception var22) {
         ErrorManager.error(10, (Throwable)var22);
      } catch (Error var23) {
         ErrorManager.error(10, (Throwable)var23);
      }

      Iterator i$ = this.grammarFileNames.iterator();

      while(true) {
         String grammarFileName;
         while(true) {
            if (!i$.hasNext()) {
               return;
            }

            grammarFileName = (String)i$.next();
            if (!this.make) {
               break;
            }

            try {
               if (!this.buildRequired(grammarFileName)) {
                  continue;
               }
            } catch (Exception var24) {
               ErrorManager.error(10, (Throwable)var24);
            }
            break;
         }

         if (this.isVerbose() && !this.isDepend()) {
            System.out.println(grammarFileName);
         }

         try {
            if (this.isDepend()) {
               BuildDependencyGenerator dep = new BuildDependencyGenerator(this, grammarFileName);
               System.out.println(dep.getDependencies().render());
            } else {
               Grammar rootGrammar = this.getRootGrammar(grammarFileName);
               rootGrammar.composite.assignTokenTypes();
               rootGrammar.addRulesForSyntacticPredicates();
               rootGrammar.composite.defineGrammarSymbols();
               rootGrammar.composite.createNFAs();
               this.generateRecognizer(rootGrammar);
               if (this.isPrintGrammar()) {
                  rootGrammar.printGrammar(System.out);
               }

               if (this.isReport()) {
                  GrammarReport2 greport = new GrammarReport2(rootGrammar);
                  System.out.print(greport.toString());
               }

               if (this.isProfile()) {
                  GrammarReport greport = new GrammarReport(rootGrammar);
                  Stats.writeReport("grammar.stats", greport.toNotifyString());
               }

               String lexerGrammarStr = rootGrammar.getLexerGrammar();
               if (rootGrammar.type == 4 && lexerGrammarStr != null) {
                  String lexerGrammarFileName = rootGrammar.getImplicitlyGeneratedLexerFileName();

                  try {
                     Writer w = this.getOutputFile(rootGrammar, lexerGrammarFileName);
                     w.write(lexerGrammarStr);
                     w.close();
                  } catch (IOException var21) {
                     exceptionWhenWritingLexerFile = true;
                     throw var21;
                  }

                  boolean var20 = false;

                  try {
                     var20 = true;
                     StringReader sr = new StringReader(lexerGrammarStr);
                     Grammar lexerGrammar = new Grammar(this);
                     lexerGrammar.composite.watchNFAConversion = internalOption_watchNFAConversion;
                     lexerGrammar.implicitLexer = true;
                     File lexerGrammarFullFile = new File(this.getFileDirectory(lexerGrammarFileName), lexerGrammarFileName);
                     lexerGrammar.setFileName(lexerGrammarFullFile.toString());
                     lexerGrammar.importTokenVocabulary(rootGrammar);
                     lexerGrammar.parseAndBuildAST(sr);
                     sr.close();
                     lexerGrammar.composite.assignTokenTypes();
                     lexerGrammar.addRulesForSyntacticPredicates();
                     lexerGrammar.composite.defineGrammarSymbols();
                     lexerGrammar.composite.createNFAs();
                     this.generateRecognizer(lexerGrammar);
                     var20 = false;
                  } finally {
                     if (var20) {
                        if (this.deleteTempLexer) {
                           File outputDir = this.getOutputDirectory(lexerGrammarFileName);
                           File outputFile = new File(outputDir, lexerGrammarFileName);
                           outputFile.delete();
                        }

                     }
                  }

                  if (this.deleteTempLexer) {
                     File outputDir = this.getOutputDirectory(lexerGrammarFileName);
                     File outputFile = new File(outputDir, lexerGrammarFileName);
                     outputFile.delete();
                  }
               }
            }
         } catch (IOException var26) {
            if (exceptionWhenWritingLexerFile) {
               ErrorManager.error(1, (Throwable)var26);
            } else {
               ErrorManager.error(7, grammarFileName, (Throwable)var26);
            }
         } catch (Exception var27) {
            ErrorManager.error(10, grammarFileName, (Throwable)var27);
         }
      }
   }

   public void sortGrammarFiles() throws IOException {
      Graph g = new Graph();
      List missingFiles = new ArrayList();
      Iterator i$ = this.grammarFileNames.iterator();

      while(i$.hasNext()) {
         String gfile = (String)i$.next();

         try {
            GrammarSpelunker grammar = new GrammarSpelunker(this.inputDirectory, gfile);
            grammar.parse();
            String vocabName = grammar.getTokenVocab();
            String grammarName = grammar.getGrammarName();
            if (vocabName != null) {
               g.addEdge(gfile, vocabName + ".tokens");
            }

            g.addEdge(grammarName + ".tokens", gfile);
         } catch (FileNotFoundException var8) {
            ErrorManager.error(7, gfile, (Throwable)var8);
            missingFiles.add(gfile);
         }
      }

      List sorted = g.sort();
      this.grammarFileNames.clear();

      for(int i = 0; i < sorted.size(); ++i) {
         String f = (String)sorted.get(i);
         if (!missingFiles.contains(f) && (f.endsWith(".g") || f.endsWith(".g3"))) {
            this.grammarFileNames.add(f);
         }
      }

   }

   public Grammar getRootGrammar(String grammarFileName) throws IOException {
      CompositeGrammar composite = new CompositeGrammar();
      Grammar grammar = new Grammar(this, grammarFileName, composite);
      composite.setDelegationRoot(grammar);
      File f;
      if (this.haveInputDir) {
         f = new File(this.inputDirectory, grammarFileName);
      } else {
         f = new File(grammarFileName);
      }

      this.parentGrammarDirectory = f.getParent();
      if (grammarFileName.lastIndexOf(File.separatorChar) == -1) {
         this.grammarOutputDirectory = ".";
      } else {
         this.grammarOutputDirectory = grammarFileName.substring(0, grammarFileName.lastIndexOf(File.separatorChar));
      }

      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      grammar.parseAndBuildAST(br);
      composite.watchNFAConversion = internalOption_watchNFAConversion;
      br.close();
      fr.close();
      return grammar;
   }

   protected void generateRecognizer(Grammar grammar) {
      String language = (String)grammar.getOption("language");
      if (language != null) {
         CodeGenerator generator = new CodeGenerator(this, grammar, language);
         grammar.setCodeGenerator(generator);
         generator.setDebug(this.isDebug());
         generator.setProfile(this.isProfile());
         generator.setTrace(this.isTrace());
         if (this.isGenerate_NFA_dot()) {
            this.generateNFAs(grammar);
         }

         generator.genRecognizer();
         if (this.isGenerate_DFA_dot()) {
            this.generateDFAs(grammar);
         }

         List delegates = grammar.getDirectDelegates();

         for(int i = 0; delegates != null && i < delegates.size(); ++i) {
            Grammar delegate = (Grammar)delegates.get(i);
            if (delegate != grammar) {
               this.generateRecognizer(delegate);
            }
         }
      }

   }

   public void generateDFAs(Grammar g) {
      for(int d = 1; d <= g.getNumberOfDecisions(); ++d) {
         DFA dfa = g.getLookaheadDFA(d);
         if (dfa != null) {
            DOTGenerator dotGenerator = new DOTGenerator(g);
            String dot = dotGenerator.getDOT(dfa.startState);
            String dotFileName = g.name + "." + "dec-" + d;
            if (g.implicitLexer) {
               dotFileName = g.name + Grammar.grammarTypeToFileNameSuffix[g.type] + "." + "dec-" + d;
            }

            try {
               this.writeDOTFile(g, dotFileName, dot);
            } catch (IOException var8) {
               ErrorManager.error(14, dotFileName, (Throwable)var8);
            }
         }
      }

   }

   protected void generateNFAs(Grammar g) {
      DOTGenerator dotGenerator = new DOTGenerator(g);
      Collection rules = new HashSet(g.getAllImportedRules());
      rules.addAll(g.getRules());
      Iterator i$ = rules.iterator();

      while(i$.hasNext()) {
         Rule r = (Rule)i$.next();

         try {
            String dot = dotGenerator.getDOT(r.startState);
            if (dot != null) {
               this.writeDOTFile(g, r, dot);
            }
         } catch (IOException var7) {
            ErrorManager.error(1, (Throwable)var7);
         }
      }

   }

   protected void writeDOTFile(Grammar g, Rule r, String dot) throws IOException {
      this.writeDOTFile(g, r.grammar.name + "." + r.name, dot);
   }

   protected void writeDOTFile(Grammar g, String name, String dot) throws IOException {
      Writer fw = this.getOutputFile(g, name + ".dot");
      fw.write(dot);
      fw.close();
   }

   private static void version() {
      ErrorManager.info("ANTLR Parser Generator  Version " + (new Tool()).VERSION);
   }

   private static void help() {
      ErrorManager.info("ANTLR Parser Generator  Version " + (new Tool()).VERSION);
      System.err.println("usage: java org.antlr.Tool [args] file.g [file2.g file3.g ...]");
      System.err.println("  -o outputDir          specify output directory where all output is generated");
      System.err.println("  -fo outputDir         same as -o but force even files with relative paths to dir");
      System.err.println("  -lib dir              specify location of token files");
      System.err.println("  -depend               generate file dependencies");
      System.err.println("  -report               print out a report about the grammar(s) processed");
      System.err.println("  -print                print out the grammar without actions");
      System.err.println("  -debug                generate a parser that emits debugging events");
      System.err.println("  -profile              generate a parser that computes profiling information");
      System.err.println("  -trace                generate a recognizer that traces rule entry/exit");
      System.err.println("  -nfa                  generate an NFA for each rule");
      System.err.println("  -dfa                  generate a DFA for each decision point");
      System.err.println("  -message-format name  specify output style for messages");
      System.err.println("  -verbose              generate ANTLR version and other information");
      System.err.println("  -make                 only build if generated files older than grammar");
      System.err.println("  -version              print the version of ANTLR and exit.");
      System.err.println("  -language L           override language grammar option; generate L");
      System.err.println("  -X                    display extended argument list");
   }

   private static void Xhelp() {
      ErrorManager.info("ANTLR Parser Generator  Version " + (new Tool()).VERSION);
      System.err.println("  -Xgrtree                print the grammar AST");
      System.err.println("  -Xdfa                   print DFA as text ");
      System.err.println("  -Xnoprune               test lookahead against EBNF block exit branches");
      System.err.println("  -Xnocollapse            collapse incident edges into DFA states");
      System.err.println("  -Xdbgconversion         dump lots of info during NFA conversion");
      System.err.println("  -Xconversiontimeout     use to restrict NFA conversion exponentiality");
      System.err.println("  -Xmultithreaded         run the analysis in 2 threads");
      System.err.println("  -Xnomergestopstates     do not merge stop states");
      System.err.println("  -Xdfaverbose            generate DFA states in DOT with NFA configs");
      System.err.println("  -Xwatchconversion       print a message for each NFA before converting");
      System.err.println("  -XdbgST                 put tags at start/stop of all templates in output");
      System.err.println("  -Xnfastates             for nondeterminisms, list NFA states for each path");
      System.err.println("  -Xm m                   max number of rule invocations during conversion           [" + NFAContext.MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK + "]");
      System.err.println("  -Xmaxdfaedges m         max \"comfortable\" number of edges for single DFA state     [" + DFA.MAX_STATE_TRANSITIONS_FOR_TABLE + "]");
      System.err.println("  -Xmaxinlinedfastates m  max DFA states before table used rather than inlining      [60]");
      System.err.println("  -Xmaxswitchcaselabels m don't generate switch() statements for dfas bigger  than m [300]");
      System.err.println("  -Xminswitchalts m       don't generate switch() statements for dfas smaller than m [3]");
      System.err.println("  -Xsavelexer             don't delete temporary lexers generated from combined grammars");
   }

   public void setMaxSwitchCaseLabels(int maxSwitchCaseLabels) {
      CodeGenerator.MAX_SWITCH_CASE_LABELS = maxSwitchCaseLabels;
   }

   public void setMinSwitchAlts(int minSwitchAlts) {
      CodeGenerator.MIN_SWITCH_ALTS = minSwitchAlts;
   }

   public void setOutputDirectory(String outputDirectory) {
      this.haveOutputDir = true;
      this.outputDirectory = outputDirectory;
   }

   public void setForceRelativeOutput(boolean forceRelativeOutput) {
      this.forceRelativeOutput = forceRelativeOutput;
   }

   public void setInputDirectory(String inputDirectory) {
      this.inputDirectory = inputDirectory;
      this.haveInputDir = true;
   }

   public Writer getOutputFile(Grammar g, String fileName) throws IOException {
      if (this.getOutputDirectory() == null) {
         return new StringWriter();
      } else {
         File outputDir;
         if (fileName.endsWith(".tokens")) {
            if (this.haveOutputDir) {
               outputDir = new File(this.getOutputDirectory());
            } else {
               outputDir = new File(".");
            }
         } else {
            outputDir = this.getOutputDirectory(g.getFileName());
         }

         File outputFile = new File(outputDir, fileName);
         if (!outputDir.exists()) {
            outputDir.mkdirs();
         }

         FileWriter fw = new FileWriter(outputFile);
         return new BufferedWriter(fw);
      }
   }

   public File getOutputDirectory(String fileNameWithPath) {
      String fileDirectory;
      if (fileNameWithPath.lastIndexOf(File.separatorChar) == -1) {
         fileDirectory = this.grammarOutputDirectory;
      } else {
         fileDirectory = fileNameWithPath.substring(0, fileNameWithPath.lastIndexOf(File.separatorChar));
      }

      File outputDir;
      if (this.haveOutputDir) {
         if ((fileDirectory == null || this.forceRelativeOutput || !(new File(fileDirectory)).isAbsolute() && !fileDirectory.startsWith("~")) && !this.isForceAllFilesToOutputDir()) {
            if (fileDirectory != null) {
               outputDir = new File(this.getOutputDirectory(), fileDirectory);
            } else {
               outputDir = new File(this.getOutputDirectory());
            }
         } else {
            outputDir = new File(this.getOutputDirectory());
         }
      } else {
         outputDir = new File(fileDirectory);
      }

      return outputDir;
   }

   public String getLibraryFile(String fileName) throws IOException {
      File f = new File(this.getLibraryDirectory() + File.separator + fileName);
      return f.exists() ? f.getAbsolutePath() : this.parentGrammarDirectory + File.separator + fileName;
   }

   public String getFileDirectory(String fileName) {
      File f;
      if (this.haveInputDir && !fileName.startsWith(File.separator)) {
         f = new File(this.inputDirectory, fileName);
      } else {
         f = new File(fileName);
      }

      return f.getParent();
   }

   public File getImportedVocabFile(String vocabName) {
      File f = new File(this.getLibraryDirectory(), File.separator + vocabName + ".tokens");
      if (f.exists()) {
         return f;
      } else {
         if (this.haveOutputDir) {
            f = new File(this.getOutputDirectory(), vocabName + ".tokens");
         } else {
            f = new File(vocabName + ".tokens");
         }

         return f;
      }
   }

   public void panic() {
      throw new Error("ANTLR panic");
   }

   public static String getCurrentTimeStamp() {
      GregorianCalendar calendar = new GregorianCalendar();
      int y = calendar.get(1);
      int m = calendar.get(2) + 1;
      int d = calendar.get(5);
      int h = calendar.get(11);
      int min = calendar.get(12);
      int sec = calendar.get(13);
      String sy = String.valueOf(y);
      String sm = m < 10 ? "0" + m : String.valueOf(m);
      String sd = d < 10 ? "0" + d : String.valueOf(d);
      String sh = h < 10 ? "0" + h : String.valueOf(h);
      String smin = min < 10 ? "0" + min : String.valueOf(min);
      String ssec = sec < 10 ? "0" + sec : String.valueOf(sec);
      return sy + "-" + sm + "-" + sd + " " + sh + ":" + smin + ":" + ssec;
   }

   public List getGrammarFileNames() {
      return this.grammarFileNames;
   }

   public boolean isGenerate_NFA_dot() {
      return this.generate_NFA_dot;
   }

   public boolean isGenerate_DFA_dot() {
      return this.generate_DFA_dot;
   }

   public String getOutputDirectory() {
      return this.outputDirectory;
   }

   public String getLibraryDirectory() {
      return this.libDirectory;
   }

   public boolean isDebug() {
      return this.debug;
   }

   public boolean isTrace() {
      return this.trace;
   }

   public boolean isProfile() {
      return this.profile;
   }

   public boolean isReport() {
      return this.report;
   }

   public boolean isPrintGrammar() {
      return this.printGrammar;
   }

   public boolean isDepend() {
      return this.depend;
   }

   public boolean isForceAllFilesToOutputDir() {
      return this.forceAllFilesToOutputDir;
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public int getConversionTimeout() {
      return DFA.MAX_TIME_PER_DFA_CREATION;
   }

   public String getMessageFormat() {
      return ErrorManager.getMessageFormat().toString();
   }

   public int getNumErrors() {
      return ErrorManager.getNumErrors();
   }

   public boolean getMake() {
      return this.make;
   }

   public void setMessageFormat(String format) {
      ErrorManager.setFormat(format);
   }

   public void setGrammarFileNames(List grammarFileNames) {
      this.grammarFileNames = grammarFileNames;
   }

   public void addGrammarFile(String grammarFileName) {
      if (!this.grammarFileNames.contains(grammarFileName)) {
         this.grammarFileNames.add(grammarFileName);
      }

   }

   public void setGenerate_NFA_dot(boolean generate_NFA_dot) {
      this.generate_NFA_dot = generate_NFA_dot;
   }

   public void setGenerate_DFA_dot(boolean generate_DFA_dot) {
      this.generate_DFA_dot = generate_DFA_dot;
   }

   public void setLibDirectory(String libDirectory) {
      this.libDirectory = libDirectory;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public void setTrace(boolean trace) {
      this.trace = trace;
   }

   public void setProfile(boolean profile) {
      this.profile = profile;
   }

   public void setReport(boolean report) {
      this.report = report;
   }

   public void setPrintGrammar(boolean printGrammar) {
      this.printGrammar = printGrammar;
   }

   public void setDepend(boolean depend) {
      this.depend = depend;
   }

   public void setForceAllFilesToOutputDir(boolean forceAllFilesToOutputDir) {
      this.forceAllFilesToOutputDir = forceAllFilesToOutputDir;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setMake(boolean make) {
      this.make = make;
   }
}
