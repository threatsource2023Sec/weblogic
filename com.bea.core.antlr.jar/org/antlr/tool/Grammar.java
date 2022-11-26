package org.antlr.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.antlr.Tool;
import org.antlr.analysis.DFA;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.LL1Analyzer;
import org.antlr.analysis.LL1DFA;
import org.antlr.analysis.LookaheadSet;
import org.antlr.analysis.NFA;
import org.antlr.analysis.NFAConversionThread;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.NFAToDFAConverter;
import org.antlr.analysis.SemanticContext;
import org.antlr.analysis.Transition;
import org.antlr.codegen.CodeGenerator;
import org.antlr.codegen.Target;
import org.antlr.grammar.v3.ANTLRLexer;
import org.antlr.grammar.v3.ANTLRParser;
import org.antlr.grammar.v3.ANTLRTreePrinter;
import org.antlr.grammar.v3.ActionAnalysis;
import org.antlr.grammar.v3.DefineGrammarItemsWalker;
import org.antlr.grammar.v3.TreeToNFAConverter;
import org.antlr.misc.Barrier;
import org.antlr.misc.IntSet;
import org.antlr.misc.IntervalSet;
import org.antlr.misc.MultiMap;
import org.antlr.misc.OrderedHashSet;
import org.antlr.misc.Utils;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

public class Grammar {
   public static final String SYNPRED_RULE_PREFIX = "synpred";
   public static final String GRAMMAR_FILE_EXTENSION = ".g";
   public static final String LEXER_GRAMMAR_FILE_EXTENSION = ".g";
   public static final int INITIAL_DECISION_LIST_SIZE = 300;
   public static final int INVALID_RULE_INDEX = -1;
   public static final int RULE_LABEL = 1;
   public static final int TOKEN_LABEL = 2;
   public static final int RULE_LIST_LABEL = 3;
   public static final int TOKEN_LIST_LABEL = 4;
   public static final int CHAR_LABEL = 5;
   public static final int WILDCARD_TREE_LABEL = 6;
   public static final int WILDCARD_TREE_LIST_LABEL = 7;
   public static String[] LabelTypeToString = new String[]{"<invalid>", "rule", "token", "rule-list", "token-list", "wildcard-tree", "wildcard-tree-list"};
   public static final String ARTIFICIAL_TOKENS_RULENAME = "Tokens";
   public static final String FRAGMENT_RULE_MODIFIER = "fragment";
   public static final String SYNPREDGATE_ACTION_NAME = "synpredgate";
   public static int[] ANTLRLiteralEscapedCharValue = new int[255];
   public static String[] ANTLRLiteralCharValueEscape = new String[255];
   public static final int LEXER = 1;
   public static final int PARSER = 2;
   public static final int TREE_PARSER = 3;
   public static final int COMBINED = 4;
   public static final String[] grammarTypeToString;
   public static final String[] grammarTypeToFileNameSuffix;
   public static MultiMap validDelegations;
   public CommonTokenStream tokenBuffer;
   public static final String IGNORE_STRING_IN_GRAMMAR_FILE_NAME = "__";
   public static final String AUTO_GENERATED_TOKEN_NAME_PREFIX = "T__";
   public String name;
   public int type;
   protected Map options;
   public static final Set legalLexerOptions;
   public static final Set legalParserOptions;
   public static final Set legalTreeParserOptions;
   public static final Set doNotCopyOptionsToLexer;
   public static final Map defaultOptions;
   public static final Set legalBlockOptions;
   public static final Map defaultBlockOptions;
   public static final Map defaultLexerBlockOptions;
   public static final Set legalTokenOptions;
   public static final String defaultTokenOption = "node";
   protected int global_k;
   private Map actions;
   public NFA nfa;
   protected NFAFactory factory;
   public CompositeGrammar composite;
   public CompositeGrammarTree compositeTreeNode;
   public String label;
   protected IntSet charVocabulary;
   Map lineColumnToLookaheadDFAMap;
   public Tool tool;
   protected Set ruleRefs;
   protected Set scopedRuleRefs;
   protected Set tokenIDRefs;
   protected int decisionCount;
   protected Set leftRecursiveRules;
   protected boolean externalAnalysisAbort;
   public int numNonLLStar;
   protected LinkedHashMap nameToSynpredASTMap;
   public List precRuleInitCodeBlocks;
   public boolean atLeastOneRuleMemoizes;
   public boolean atLeastOneBacktrackOption;
   public boolean implicitLexer;
   protected LinkedHashMap nameToRuleMap;
   public Set overriddenRules;
   protected Set delegatedRuleReferences;
   public List lexerRuleNamesInCombined;
   protected Map scopes;
   protected GrammarAST grammarTree;
   protected Vector indexToDecision;
   protected CodeGenerator generator;
   public NameSpaceChecker nameSpaceChecker;
   public LL1Analyzer ll1Analyzer;
   protected String lexerGrammarTemplate;
   protected ST lexerGrammarST;
   protected String fileName;
   public long DFACreationWallClockTimeInMS;
   public int numberOfSemanticPredicates;
   public int numberOfManualLookaheadOptions;
   public Set setOfNondeterministicDecisionNumbers;
   public Set setOfNondeterministicDecisionNumbersResolvedWithPredicates;
   public Set blocksWithSynPreds;
   public Set decisionsWhoseDFAsUsesSynPreds;
   public Set synPredNamesUsedInDFA;
   public Set blocksWithSemPreds;
   public Set decisionsWhoseDFAsUsesSemPreds;
   protected boolean allDecisionDFACreated;
   protected boolean builtFromString;
   GrammarSanity sanity;
   Target target;

   public Grammar(Tool tool, String fileName, CompositeGrammar composite) {
      this.global_k = -1;
      this.actions = new HashMap();
      this.charVocabulary = null;
      this.lineColumnToLookaheadDFAMap = new HashMap();
      this.ruleRefs = new HashSet();
      this.scopedRuleRefs = new HashSet();
      this.tokenIDRefs = new HashSet();
      this.decisionCount = 0;
      this.numNonLLStar = 0;
      this.precRuleInitCodeBlocks = new ArrayList();
      this.nameToRuleMap = new LinkedHashMap();
      this.overriddenRules = new HashSet();
      this.delegatedRuleReferences = new HashSet();
      this.lexerRuleNamesInCombined = new ArrayList();
      this.scopes = new HashMap();
      this.grammarTree = null;
      this.indexToDecision = new Vector(300);
      this.nameSpaceChecker = new NameSpaceChecker(this);
      this.ll1Analyzer = new LL1Analyzer(this);
      this.lexerGrammarTemplate = "grammar(name, options, imports, actionNames, actions, literals, rules) ::= <<\nlexer grammar <name>;\n<if(options)>options {\n  <options:{it | <it.name>=<it.value>;<\\n>}>\n}<\\n>\n<endif>\n<if(imports)>import <imports; separator=\", \">;<endif>\n<actionNames,actions:{n,a|@<n> {<a>\\}\n}>\n<literals:{it | <it.ruleName> : <it.literal> ;\n}>\n<rules>\n>>\n";
      this.numberOfSemanticPredicates = 0;
      this.numberOfManualLookaheadOptions = 0;
      this.setOfNondeterministicDecisionNumbers = new HashSet();
      this.setOfNondeterministicDecisionNumbersResolvedWithPredicates = new HashSet();
      this.blocksWithSynPreds = new HashSet();
      this.decisionsWhoseDFAsUsesSynPreds = new HashSet();
      this.synPredNamesUsedInDFA = new HashSet();
      this.blocksWithSemPreds = new HashSet();
      this.decisionsWhoseDFAsUsesSemPreds = new HashSet();
      this.allDecisionDFACreated = false;
      this.builtFromString = false;
      this.sanity = new GrammarSanity(this);
      this.composite = composite;
      this.setTool(tool);
      this.setFileName(fileName);
      if (composite.delegateGrammarTreeRoot == null) {
         composite.setDelegationRoot(this);
      }

      STGroup lexerGrammarSTG = new STGroupString(this.lexerGrammarTemplate);
      this.lexerGrammarST = lexerGrammarSTG.getInstanceOf("grammar");
      this.target = CodeGenerator.loadLanguageTarget((String)this.getOption("language"));
   }

   public Grammar() {
      this((Tool)null);
   }

   public Grammar(Tool tool) {
      this.global_k = -1;
      this.actions = new HashMap();
      this.charVocabulary = null;
      this.lineColumnToLookaheadDFAMap = new HashMap();
      this.ruleRefs = new HashSet();
      this.scopedRuleRefs = new HashSet();
      this.tokenIDRefs = new HashSet();
      this.decisionCount = 0;
      this.numNonLLStar = 0;
      this.precRuleInitCodeBlocks = new ArrayList();
      this.nameToRuleMap = new LinkedHashMap();
      this.overriddenRules = new HashSet();
      this.delegatedRuleReferences = new HashSet();
      this.lexerRuleNamesInCombined = new ArrayList();
      this.scopes = new HashMap();
      this.grammarTree = null;
      this.indexToDecision = new Vector(300);
      this.nameSpaceChecker = new NameSpaceChecker(this);
      this.ll1Analyzer = new LL1Analyzer(this);
      this.lexerGrammarTemplate = "grammar(name, options, imports, actionNames, actions, literals, rules) ::= <<\nlexer grammar <name>;\n<if(options)>options {\n  <options:{it | <it.name>=<it.value>;<\\n>}>\n}<\\n>\n<endif>\n<if(imports)>import <imports; separator=\", \">;<endif>\n<actionNames,actions:{n,a|@<n> {<a>\\}\n}>\n<literals:{it | <it.ruleName> : <it.literal> ;\n}>\n<rules>\n>>\n";
      this.numberOfSemanticPredicates = 0;
      this.numberOfManualLookaheadOptions = 0;
      this.setOfNondeterministicDecisionNumbers = new HashSet();
      this.setOfNondeterministicDecisionNumbersResolvedWithPredicates = new HashSet();
      this.blocksWithSynPreds = new HashSet();
      this.decisionsWhoseDFAsUsesSynPreds = new HashSet();
      this.synPredNamesUsedInDFA = new HashSet();
      this.blocksWithSemPreds = new HashSet();
      this.decisionsWhoseDFAsUsesSemPreds = new HashSet();
      this.allDecisionDFACreated = false;
      this.builtFromString = false;
      this.sanity = new GrammarSanity(this);
      this.setTool(tool);
      this.builtFromString = true;
      this.composite = new CompositeGrammar(this);
      STGroup lexerGrammarSTG = new STGroupString(this.lexerGrammarTemplate);
      this.lexerGrammarST = lexerGrammarSTG.getInstanceOf("grammar");
      this.target = CodeGenerator.loadLanguageTarget((String)this.getOption("language"));
   }

   public Grammar(String grammarString) throws RecognitionException {
      this((Tool)null, grammarString);
   }

   public Grammar(Tool tool, String grammarString) throws RecognitionException {
      this(tool);
      this.setFileName("<string>");
      StringReader r = new StringReader(grammarString);
      this.parseAndBuildAST(r);
      this.composite.assignTokenTypes();
      this.addRulesForSyntacticPredicates();
      this.composite.defineGrammarSymbols();
      this.checkNameSpaceAndActions();
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setName(String name) {
      if (name != null) {
         String saneFile = this.fileName.replace('\\', '/');
         int lastSlash = saneFile.lastIndexOf(47);
         String onlyFileName = saneFile.substring(lastSlash + 1, this.fileName.length());
         if (!this.builtFromString) {
            int lastDot = onlyFileName.lastIndexOf(46);
            String onlyFileNameNoSuffix;
            if (lastDot < 0) {
               ErrorManager.error(9, (Object)this.fileName);
               onlyFileNameNoSuffix = onlyFileName + ".g";
            } else {
               onlyFileNameNoSuffix = onlyFileName.substring(0, lastDot);
            }

            if (!name.equals(onlyFileNameNoSuffix)) {
               ErrorManager.error(8, name, (Object)this.fileName);
            }
         }

         this.name = name;
      }
   }

   public void setGrammarContent(String grammarString) throws RecognitionException {
      StringReader r = new StringReader(grammarString);
      this.parseAndBuildAST(r);
      this.composite.assignTokenTypes();
      this.composite.defineGrammarSymbols();
   }

   public void parseAndBuildAST() throws IOException {
      BufferedReader br = null;

      try {
         FileReader fr = new FileReader(this.fileName);
         br = new BufferedReader(fr);
         this.parseAndBuildAST(br);
         br.close();
         br = null;
      } finally {
         if (br != null) {
            br.close();
         }

      }

   }

   public void parseAndBuildAST(Reader r) {
      ANTLRLexer lexer;
      try {
         lexer = new ANTLRLexer(new ANTLRReaderStream(r));
      } catch (IOException var7) {
         ErrorManager.internalError("unexpected stream error from parsing " + this.fileName, var7);
         return;
      }

      lexer.setFileName(this.getFileName());
      this.tokenBuffer = new CommonTokenStream(lexer);
      ANTLRParser parser = ANTLRParser.createParser(this.tokenBuffer);
      parser.setFileName(this.getFileName());
      ANTLRParser.grammar__return result = null;

      try {
         result = parser.grammar_(this);
      } catch (RecognitionException var6) {
         ErrorManager.internalError("unexpected parser recognition error from " + this.fileName, var6);
      }

      this.dealWithTreeFilterMode();
      if (lexer.hasASTOperator && !this.buildAST()) {
         Object value = this.getOption("output");
         if (value == null) {
            ErrorManager.grammarWarning(149, this, (Token)null);
            this.setOption("output", "AST", (Token)null);
         } else {
            ErrorManager.grammarError(164, this, (Token)null, value);
         }
      }

      this.setGrammarTree(result.getTree());
      this.grammarTree.setUnknownTokenBoundaries();
      this.setFileName(lexer.getFileName());
      if (this.grammarTree.findFirstType(79) == null) {
         ErrorManager.error(150, (Object)this.getFileName());
      }

   }

   protected void dealWithTreeFilterMode() {
      Object filterMode = (String)this.getOption("filter");
      if (this.type == 3 && filterMode != null && filterMode.toString().equals("true")) {
         Object backtrack = (String)this.getOption("backtrack");
         Object output = this.getOption("output");
         Object rewrite = this.getOption("rewrite");
         if (backtrack != null && !backtrack.toString().equals("true")) {
            ErrorManager.error(167, "backtrack", (Object)backtrack);
         }

         if (output != null && !output.toString().equals("AST")) {
            ErrorManager.error(167, "output", (Object)output);
            this.setOption("output", "", (Token)null);
         }

         if (rewrite != null && !rewrite.toString().equals("true")) {
            ErrorManager.error(167, "rewrite", (Object)rewrite);
         }

         this.setOption("backtrack", "true", (Token)null);
         if (output != null && output.toString().equals("AST")) {
            this.setOption("rewrite", "true", (Token)null);
         }
      }

   }

   public void translateLeftRecursiveRule(GrammarAST ruleAST) {
      CommonTreeNodeStream input = new CommonTreeNodeStream(ruleAST);
      LeftRecursiveRuleAnalyzer leftRecursiveRuleWalker = new LeftRecursiveRuleAnalyzer(input, this, ruleAST.enclosingRuleName);
      boolean isLeftRec = false;

      try {
         isLeftRec = leftRecursiveRuleWalker.rec_rule(this);
      } catch (RecognitionException var9) {
         ErrorManager.error(15, (Throwable)var9);
      }

      if (isLeftRec) {
         List rules = new ArrayList();
         rules.add(leftRecursiveRuleWalker.getArtificialPrecStartRule());
         rules.add(leftRecursiveRuleWalker.getArtificialOpPrecRule());
         rules.add(leftRecursiveRuleWalker.getArtificialPrimaryRule());
         Iterator i$ = rules.iterator();

         while(i$.hasNext()) {
            String r = (String)i$.next();
            GrammarAST t = this.parseArtificialRule(r);
            this.addRule(this.grammarTree, t);
         }

      }
   }

   public void defineGrammarSymbols() {
      if (Tool.internalOption_PrintGrammarTree) {
         System.out.println(this.grammarTree.toStringList());
      }

      DefineGrammarItemsWalker defineItemsWalker = new DefineGrammarItemsWalker(new CommonTreeNodeStream(this.getGrammarTree()));

      try {
         defineItemsWalker.grammar_(this);
      } catch (RecognitionException var3) {
         ErrorManager.error(15, (Throwable)var3);
      }

   }

   public void checkNameSpaceAndActions() {
      this.examineAllExecutableActions();
      this.checkAllRulesForUselessLabels();
      this.nameSpaceChecker.checkConflicts();
   }

   public boolean validImport(Grammar delegate) {
      List validDelegators = (List)validDelegations.get(delegate.type);
      return validDelegators != null && validDelegators.contains(this.type);
   }

   public String getLexerGrammar() {
      if (this.lexerGrammarST.getAttribute("literals") == null && this.lexerGrammarST.getAttribute("rules") == null) {
         return null;
      } else {
         this.lexerGrammarST.add("name", this.name);
         if (this.getActions().get("lexer") != null) {
            this.lexerGrammarST.add("actionNames", ((Map)this.getActions().get("lexer")).keySet());
            this.lexerGrammarST.add("actions", ((Map)this.getActions().get("lexer")).values());
         }

         if (this.options != null) {
            Iterator i$ = this.options.keySet().iterator();

            while(i$.hasNext()) {
               String optionName = (String)i$.next();
               if (!doNotCopyOptionsToLexer.contains(optionName)) {
                  Object value = this.options.get(optionName);
                  this.lexerGrammarST.addAggr("options.{name,value}", optionName, value);
               }
            }
         }

         return this.lexerGrammarST.render();
      }
   }

   public String getImplicitlyGeneratedLexerFileName() {
      return this.name + "__" + ".g";
   }

   public String getRecognizerName() {
      String suffix = "";
      List grammarsFromRootToMe = this.composite.getDelegators(this);
      String qualifiedName = this.name;
      if (grammarsFromRootToMe != null) {
         StringBuilder buf = new StringBuilder();
         Iterator i$ = grammarsFromRootToMe.iterator();

         while(i$.hasNext()) {
            Grammar g = (Grammar)i$.next();
            buf.append(g.name);
            buf.append('_');
         }

         buf.append(this.name);
         qualifiedName = buf.toString();
      }

      if (this.type == 4 || this.type == 1 && this.implicitLexer) {
         suffix = grammarTypeToFileNameSuffix[this.type];
      }

      return qualifiedName + suffix;
   }

   public GrammarAST addArtificialMatchTokensRule(GrammarAST grammarAST, List ruleNames, List delegateNames, boolean filterMode) {
      ST matchTokenRuleST;
      if (filterMode) {
         matchTokenRuleST = new ST("Tokens options {k=1; backtrack=true;} : <rules; separator=\"|\">;");
      } else {
         matchTokenRuleST = new ST("Tokens : <rules; separator=\"|\">;");
      }

      int i;
      String dname;
      for(i = 0; i < ruleNames.size(); ++i) {
         dname = (String)ruleNames.get(i);
         matchTokenRuleST.add("rules", dname);
      }

      for(i = 0; i < delegateNames.size(); ++i) {
         dname = (String)delegateNames.get(i);
         matchTokenRuleST.add("rules", dname + ".Tokens");
      }

      GrammarAST r = this.parseArtificialRule(matchTokenRuleST.render());
      this.addRule(grammarAST, r);
      return r;
   }

   public GrammarAST parseArtificialRule(String ruleText) {
      ANTLRLexer lexer = new ANTLRLexer(new ANTLRStringStream(ruleText));
      ANTLRParser parser = ANTLRParser.createParser(new CommonTokenStream(lexer));
      parser.setGrammar(this);
      parser.setGrammarType(this.type);

      try {
         ANTLRParser.rule_return result = parser.rule();
         return result.getTree();
      } catch (Exception var5) {
         ErrorManager.error(12, (Throwable)var5);
         return null;
      }
   }

   public void addRule(GrammarAST grammarTree, GrammarAST t) {
      GrammarAST p = null;

      for(int i = 0; i < grammarTree.getChildCount(); ++i) {
         p = (GrammarAST)grammarTree.getChild(i);
         if (p == null || p.getType() == 79 || p.getType() == 65) {
            break;
         }
      }

      if (p != null) {
         grammarTree.addChild(t);
      }

   }

   protected List getArtificialRulesForSyntacticPredicates(LinkedHashMap nameToSynpredASTMap) {
      List rules = new ArrayList();
      if (nameToSynpredASTMap == null) {
         return rules;
      } else {
         boolean isLexer = this.grammarTree.getType() == 50;
         Iterator i$ = nameToSynpredASTMap.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            String synpredName = (String)entry.getKey();
            GrammarAST fragmentAST = (GrammarAST)entry.getValue();
            GrammarAST ruleAST = ANTLRParser.createSimpleRuleAST(synpredName, fragmentAST, isLexer);
            rules.add(ruleAST);
         }

         return rules;
      }
   }

   public void addRulesForSyntacticPredicates() {
      List synpredRules = this.getArtificialRulesForSyntacticPredicates(this.nameToSynpredASTMap);

      for(int i = 0; i < synpredRules.size(); ++i) {
         GrammarAST rAST = (GrammarAST)synpredRules.get(i);
         this.grammarTree.addChild(rAST);
      }

   }

   public void createRuleStartAndStopNFAStates() {
      if (this.nfa == null) {
         this.nfa = new NFA(this);
         this.factory = new NFAFactory(this.nfa);
         Collection rules = this.getRules();

         Rule r;
         NFAState ruleEndState;
         for(Iterator i$ = rules.iterator(); i$.hasNext(); r.stopState = ruleEndState) {
            r = (Rule)i$.next();
            String ruleName = r.name;
            NFAState ruleBeginState = this.factory.newState();
            ruleBeginState.setDescription("rule " + ruleName + " start");
            ruleBeginState.enclosingRule = r;
            r.startState = ruleBeginState;
            ruleEndState = this.factory.newState();
            ruleEndState.setDescription("rule " + ruleName + " end");
            ruleEndState.setAcceptState(true);
            ruleEndState.enclosingRule = r;
         }

      }
   }

   public void buildNFA() {
      if (this.nfa == null) {
         this.createRuleStartAndStopNFAStates();
      }

      if (!this.nfa.complete) {
         if (!this.getRules().isEmpty()) {
            CommonTreeNodeStream input = new CommonTreeNodeStream(this.getGrammarTree());
            TreeToNFAConverter nfaBuilder = new TreeToNFAConverter(input, this, this.nfa, this.factory);

            try {
               nfaBuilder.grammar_();
            } catch (RecognitionException var4) {
               ErrorManager.error(15, this.name, (Throwable)var4);
            }

            this.nfa.complete = true;
         }
      }
   }

   public void createLookaheadDFAs() {
      this.createLookaheadDFAs(true);
   }

   public void createLookaheadDFAs(boolean wackTempStructures) {
      if (this.nfa == null) {
         this.buildNFA();
      }

      this.checkAllRulesForLeftRecursion();
      long start = System.currentTimeMillis();
      int numDecisions = this.getNumberOfDecisions();
      if (NFAToDFAConverter.SINGLE_THREADED_NFA_CONVERSION) {
         for(int decision = 1; decision <= numDecisions; ++decision) {
            NFAState decisionStartState = this.getDecisionNFAStartState(decision);
            if (this.leftRecursiveRules.contains(decisionStartState.enclosingRule)) {
               if (this.composite.watchNFAConversion) {
                  System.out.println("ignoring decision " + decision + " within left-recursive rule " + decisionStartState.enclosingRule.name);
               }
            } else if (!this.externalAnalysisAbort && decisionStartState.getNumberOfTransitions() > 1) {
               Rule r = decisionStartState.enclosingRule;
               if (!r.isSynPred || this.synPredNamesUsedInDFA.contains(r.name)) {
                  DFA dfa = null;
                  if (this.getUserMaxLookahead(decision) == 0 || this.getUserMaxLookahead(decision) == 1) {
                     dfa = this.createLL_1_LookaheadDFA(decision);
                  }

                  if (dfa == null) {
                     if (this.composite.watchNFAConversion) {
                        System.out.println("decision " + decision + " not suitable for LL(1)-optimized DFA analysis");
                     }

                     dfa = this.createLookaheadDFA(decision, wackTempStructures);
                  }

                  if (dfa.startState == null) {
                     this.setLookaheadDFA(decision, (DFA)null);
                  }

                  if (Tool.internalOption_PrintDFA) {
                     System.out.println("DFA d=" + decision);
                     FASerializer serializer = new FASerializer(this.nfa.grammar);
                     String result = serializer.serialize(dfa.startState);
                     System.out.println(result);
                  }
               }
            }
         }
      } else {
         ErrorManager.info("two-threaded DFA conversion");
         Barrier barrier = new Barrier(3);
         int midpoint = numDecisions / 2;
         NFAConversionThread t1 = new NFAConversionThread(this, barrier, 1, midpoint);
         (new Thread(t1)).start();
         if (midpoint == numDecisions / 2) {
            ++midpoint;
         }

         NFAConversionThread t2 = new NFAConversionThread(this, barrier, midpoint, numDecisions);
         (new Thread(t2)).start();

         try {
            barrier.waitForRelease();
         } catch (InterruptedException var11) {
            ErrorManager.internalError("what the hell? DFA interruptus", var11);
         }
      }

      long stop = System.currentTimeMillis();
      this.DFACreationWallClockTimeInMS = stop - start;
      this.allDecisionDFACreated = true;
   }

   public DFA createLL_1_LookaheadDFA(int decision) {
      Decision d = this.getDecision(decision);
      String enclosingRule = d.startState.enclosingRule.name;
      Rule r = d.startState.enclosingRule;
      NFAState decisionStartState = this.getDecisionNFAStartState(decision);
      if (this.composite.watchNFAConversion) {
         System.out.println("--------------------\nattempting LL(1) DFA (d=" + decisionStartState.getDecisionNumber() + ") for " + decisionStartState.getDescription());
      }

      if (r.isSynPred && !this.synPredNamesUsedInDFA.contains(enclosingRule)) {
         return null;
      } else {
         int numAlts = this.getNumberOfAltsForDecisionNFA(decisionStartState);
         LookaheadSet[] altLook = new LookaheadSet[numAlts + 1];

         int i;
         for(int alt = 1; alt <= numAlts; ++alt) {
            i = decisionStartState.translateDisplayAltToWalkAlt(alt);
            NFAState altLeftEdge = this.getNFAStateForAltOfDecision(decisionStartState, i);
            NFAState altStartState = (NFAState)altLeftEdge.transition[0].target;
            altLook[alt] = this.ll1Analyzer.LOOK(altStartState);
         }

         boolean decisionIsLL_1 = true;

         label82:
         for(i = 1; i <= numAlts; ++i) {
            for(int j = i + 1; j <= numAlts; ++j) {
               LookaheadSet collision = altLook[i].intersection(altLook[j]);
               if (!collision.isNil()) {
                  decisionIsLL_1 = false;
                  break label82;
               }
            }
         }

         boolean foundConfoundingPredicate = this.ll1Analyzer.detectConfoundingPredicates(decisionStartState);
         if (decisionIsLL_1 && !foundConfoundingPredicate) {
            if (NFAToDFAConverter.debug) {
               System.out.println("decision " + decision + " is simple LL(1)");
            }

            DFA lookaheadDFA = new LL1DFA(decision, decisionStartState, altLook);
            this.setLookaheadDFA(decision, lookaheadDFA);
            this.updateLineColumnToLookaheadDFAMap(lookaheadDFA);
            return lookaheadDFA;
         } else if (this.getUserMaxLookahead(decision) == 1 && this.getAutoBacktrackMode(decision) && !foundConfoundingPredicate) {
            List edges = new ArrayList();

            for(int i = 1; i < altLook.length; ++i) {
               LookaheadSet s = altLook[i];
               edges.add(s.tokenTypeSet);
            }

            List disjoint = this.makeEdgeSetsDisjoint(edges);
            MultiMap edgeMap = new MultiMap();

            for(int i = 0; i < disjoint.size(); ++i) {
               IntervalSet ds = (IntervalSet)disjoint.get(i);

               for(int alt = 1; alt < altLook.length; ++alt) {
                  LookaheadSet look = altLook[alt];
                  if (!ds.and(look.tokenTypeSet).isNil()) {
                     edgeMap.map(ds, alt);
                  }
               }
            }

            DFA lookaheadDFA = new LL1DFA(decision, decisionStartState, edgeMap);
            this.setLookaheadDFA(decision, lookaheadDFA);
            this.updateLineColumnToLookaheadDFAMap(lookaheadDFA);
            return lookaheadDFA;
         } else {
            return null;
         }
      }
   }

   private void updateLineColumnToLookaheadDFAMap(DFA lookaheadDFA) {
      GrammarAST decisionAST = this.nfa.grammar.getDecisionBlockAST(lookaheadDFA.decisionNumber);
      int line = decisionAST.getLine();
      int col = decisionAST.getCharPositionInLine();
      this.lineColumnToLookaheadDFAMap.put(line + ":" + col, lookaheadDFA);
   }

   protected List makeEdgeSetsDisjoint(List edges) {
      OrderedHashSet disjointSets = new OrderedHashSet();
      int numEdges = edges.size();

      for(int e = 0; e < numEdges; ++e) {
         IntervalSet t = (IntervalSet)edges.get(e);
         if (!disjointSets.contains(t)) {
            IntervalSet remainder = t;
            int numDisjointElements = disjointSets.size();

            for(int i = 0; i < numDisjointElements; ++i) {
               IntervalSet s_i = (IntervalSet)disjointSets.get(i);
               if (!t.and(s_i).isNil()) {
                  IntervalSet intersection = s_i.and(t);
                  disjointSets.set(i, intersection);
                  IntervalSet existingMinusNewElements = s_i.subtract(t);
                  if (!existingMinusNewElements.isNil()) {
                     disjointSets.add(existingMinusNewElements);
                  }

                  remainder = t.subtract(s_i);
                  if (remainder.isNil()) {
                     break;
                  }

                  t = remainder;
               }
            }

            if (!remainder.isNil()) {
               disjointSets.add(remainder);
            }
         }
      }

      return disjointSets.elements();
   }

   public DFA createLookaheadDFA(int decision, boolean wackTempStructures) {
      Decision d = this.getDecision(decision);
      String enclosingRule = d.startState.enclosingRule.name;
      Rule r = d.startState.enclosingRule;
      NFAState decisionStartState = this.getDecisionNFAStartState(decision);
      long startDFA = 0L;
      if (this.composite.watchNFAConversion) {
         System.out.println("--------------------\nbuilding lookahead DFA (d=" + decisionStartState.getDecisionNumber() + ") for " + decisionStartState.getDescription());
         startDFA = System.currentTimeMillis();
      }

      DFA lookaheadDFA = new DFA(decision, decisionStartState);
      boolean failed = lookaheadDFA.probe.isNonLLStarDecision() || lookaheadDFA.probe.analysisOverflowed();
      if (failed && lookaheadDFA.okToRetryDFAWithK1()) {
         this.decisionsWhoseDFAsUsesSynPreds.remove(lookaheadDFA);
         d.blockAST.setBlockOption(this, "k", Utils.integer(1));
         if (this.composite.watchNFAConversion) {
            System.out.print("trying decision " + decision + " again with k=1; reason: " + lookaheadDFA.getReasonForFailure());
         }

         lookaheadDFA = null;
         lookaheadDFA = new DFA(decision, decisionStartState);
      }

      this.setLookaheadDFA(decision, lookaheadDFA);
      if (wackTempStructures) {
         Iterator i$ = lookaheadDFA.getUniqueStates().values().iterator();

         while(i$.hasNext()) {
            DFAState s = (DFAState)i$.next();
            s.reset();
         }
      }

      this.updateLineColumnToLookaheadDFAMap(lookaheadDFA);
      if (this.composite.watchNFAConversion) {
         long stopDFA = System.currentTimeMillis();
         System.out.println("cost: " + lookaheadDFA.getNumberOfStates() + " states, " + (int)(stopDFA - startDFA) + " ms");
      }

      return lookaheadDFA;
   }

   public void externallyAbortNFAToDFAConversion() {
      this.externalAnalysisAbort = true;
   }

   public boolean NFAToDFAConversionExternallyAborted() {
      return this.externalAnalysisAbort;
   }

   public int getNewTokenType() {
      ++this.composite.maxTokenType;
      return this.composite.maxTokenType;
   }

   public void defineToken(String text, int tokenType) {
      if (this.composite.tokenIDToTypeMap.get(text) == null) {
         if (text.charAt(0) == '\'') {
            this.composite.stringLiteralToTypeMap.put(text, Utils.integer(tokenType));
            if (tokenType >= this.composite.typeToStringLiteralList.size()) {
               this.composite.typeToStringLiteralList.setSize(tokenType + 1);
            }

            this.composite.typeToStringLiteralList.set(tokenType, text);
         } else {
            this.composite.tokenIDToTypeMap.put(text, Utils.integer(tokenType));
         }

         int index = 7 + tokenType - 1;
         this.composite.maxTokenType = Math.max(this.composite.maxTokenType, tokenType);
         if (index >= this.composite.typeToTokenList.size()) {
            this.composite.typeToTokenList.setSize(index + 1);
         }

         String prevToken = (String)this.composite.typeToTokenList.get(index);
         if (prevToken == null || prevToken.charAt(0) == '\'') {
            this.composite.typeToTokenList.set(index, text);
         }

      }
   }

   public void defineRule(Token ruleToken, String modifier, Map options, GrammarAST tree, GrammarAST argActionAST, int numAlts) {
      String ruleName = ruleToken.getText();
      if (this.getLocallyDefinedRule(ruleName) != null) {
         ErrorManager.grammarError(101, this, ruleToken, ruleName);
      } else if ((this.type == 2 || this.type == 3) && Character.isUpperCase(ruleName.charAt(0))) {
         ErrorManager.grammarError(102, this, ruleToken, ruleName);
      } else {
         Rule r = new Rule(this, ruleName, this.composite.ruleIndex, numAlts);
         r.modifier = modifier;
         this.nameToRuleMap.put(ruleName, r);
         this.setRuleAST(ruleName, tree);
         r.setOptions(options, ruleToken);
         r.argActionAST = argActionAST;
         this.composite.ruleIndexToRuleList.setSize(this.composite.ruleIndex + 1);
         this.composite.ruleIndexToRuleList.set(this.composite.ruleIndex, r);
         ++this.composite.ruleIndex;
         if (ruleName.startsWith("synpred")) {
            r.isSynPred = true;
         }

      }
   }

   public String defineSyntacticPredicate(GrammarAST blockAST, String currentRuleName) {
      if (this.nameToSynpredASTMap == null) {
         this.nameToSynpredASTMap = new LinkedHashMap();
      }

      String predName = "synpred" + (this.nameToSynpredASTMap.size() + 1) + "_" + this.name;
      blockAST.setTreeEnclosingRuleNameDeeply(predName);
      this.nameToSynpredASTMap.put(predName, blockAST);
      return predName;
   }

   public LinkedHashMap getSyntacticPredicates() {
      return this.nameToSynpredASTMap;
   }

   public GrammarAST getSyntacticPredicate(String name) {
      return this.nameToSynpredASTMap == null ? null : (GrammarAST)this.nameToSynpredASTMap.get(name);
   }

   public void synPredUsedInDFA(DFA dfa, SemanticContext semCtx) {
      this.decisionsWhoseDFAsUsesSynPreds.add(dfa);
      semCtx.trackUseOfSyntacticPredicates(this);
   }

   public void defineNamedAction(GrammarAST ampersandAST, String scope, GrammarAST nameAST, GrammarAST actionAST) {
      if (scope == null) {
         scope = this.getDefaultActionScope(this.type);
      }

      String actionName = nameAST.getText();
      Map scopeActions = (Map)this.getActions().get(scope);
      if (scopeActions == null) {
         scopeActions = new HashMap();
         this.getActions().put(scope, scopeActions);
      }

      Object a = ((Map)scopeActions).get(actionName);
      if (a != null) {
         ErrorManager.grammarError(144, this, nameAST.getToken(), nameAST.getText());
      } else {
         ((Map)scopeActions).put(actionName, actionAST);
      }

      if (this == this.composite.getRootGrammar() && actionName.equals("header")) {
         List allgrammars = this.composite.getRootGrammar().getDelegates();
         Iterator i$ = allgrammars.iterator();

         while(i$.hasNext()) {
            Grammar delegate = (Grammar)i$.next();
            if (this.target.isValidActionScope(delegate.type, scope)) {
               delegate.defineNamedAction(ampersandAST, scope, nameAST, actionAST);
            }
         }
      }

   }

   public void setSynPredGateIfNotAlready(ST gateST) {
      String scope = this.getDefaultActionScope(this.type);
      Map actionsForGrammarScope = (Map)this.getActions().get(scope);
      if (actionsForGrammarScope == null || !((Map)actionsForGrammarScope).containsKey("synpredgate")) {
         if (actionsForGrammarScope == null) {
            actionsForGrammarScope = new HashMap();
            this.getActions().put(scope, actionsForGrammarScope);
         }

         ((Map)actionsForGrammarScope).put("synpredgate", gateST);
      }

   }

   public Map getActions() {
      return this.actions;
   }

   public String getDefaultActionScope(int grammarType) {
      switch (grammarType) {
         case 1:
            return "lexer";
         case 2:
         case 4:
            return "parser";
         case 3:
            return "treeparser";
         default:
            return null;
      }
   }

   public void defineLexerRuleFoundInParser(Token ruleToken, GrammarAST ruleAST) {
      StringBuilder buf = new StringBuilder();
      buf.append("// $ANTLR src \"");
      buf.append(this.getFileName());
      buf.append("\" ");
      buf.append(ruleAST.getLine());
      buf.append("\n");

      for(int i = ruleAST.getTokenStartIndex(); i <= ruleAST.getTokenStopIndex() && i < this.tokenBuffer.size(); ++i) {
         CommonToken t = (CommonToken)this.tokenBuffer.get(i);
         if (t.getType() == 16) {
            buf.append("(");
         } else if (t.getType() == 4) {
            buf.append("{");
            buf.append(t.getText());
            buf.append("}");
         } else if (t.getType() != 83 && t.getType() != 90 && t.getType() != 41 && t.getType() != 14) {
            if (t.getType() == 12) {
               buf.append("[");
               buf.append(t.getText());
               buf.append("]");
            } else {
               buf.append(t.getText());
            }
         } else {
            buf.append("{");
            buf.append(t.getText());
            buf.append("}?");
         }
      }

      String ruleText = buf.toString();
      if (this.getGrammarIsRoot()) {
         this.lexerGrammarST.add("rules", ruleText);
      }

      this.composite.lexerRules.add(ruleToken.getText());
   }

   public void defineLexerRuleForAliasedStringLiteral(String tokenID, String literal, int tokenType) {
      if (this.getGrammarIsRoot()) {
         this.lexerGrammarST.addAggr("literals.{ruleName,type,literal}", tokenID, Utils.integer(tokenType), literal);
      }

      this.composite.lexerRules.add(tokenID);
   }

   public void defineLexerRuleForStringLiteral(String literal, int tokenType) {
      String tokenID = this.computeTokenNameFromLiteral(tokenType, literal);
      this.defineToken(tokenID, tokenType);
      if (this.getGrammarIsRoot()) {
         this.lexerGrammarST.addAggr("literals.{ruleName,type,literal}", tokenID, Utils.integer(tokenType), literal);
      }

   }

   public Rule getLocallyDefinedRule(String ruleName) {
      Rule r = (Rule)this.nameToRuleMap.get(ruleName);
      return r;
   }

   public Rule getRule(String ruleName) {
      Rule r = this.composite.getRule(ruleName);
      return r;
   }

   public Rule getRule(String scopeName, String ruleName) {
      if (scopeName != null) {
         Grammar scope = this.composite.getGrammar(scopeName);
         return scope == null ? null : scope.getLocallyDefinedRule(ruleName);
      } else {
         return this.getRule(ruleName);
      }
   }

   public int getRuleIndex(String scopeName, String ruleName) {
      Rule r = this.getRule(scopeName, ruleName);
      return r != null ? r.index : -1;
   }

   public int getRuleIndex(String ruleName) {
      return this.getRuleIndex((String)null, ruleName);
   }

   public String getRuleName(int ruleIndex) {
      Rule r = (Rule)this.composite.ruleIndexToRuleList.get(ruleIndex);
      return r != null ? r.name : null;
   }

   public boolean generateMethodForRule(String ruleName) {
      if (ruleName.equals("Tokens")) {
         return true;
      } else if (this.overriddenRules.contains(ruleName)) {
         return false;
      } else {
         Rule r = this.getLocallyDefinedRule(ruleName);
         return !r.isSynPred || r.isSynPred && this.synPredNamesUsedInDFA.contains(ruleName);
      }
   }

   public AttributeScope defineGlobalScope(String name, Token scopeAction) {
      AttributeScope scope = new AttributeScope(this, name, scopeAction);
      this.scopes.put(name, scope);
      return scope;
   }

   public AttributeScope createReturnScope(String ruleName, Token retAction) {
      AttributeScope scope = new AttributeScope(this, ruleName, retAction);
      scope.isReturnScope = true;
      return scope;
   }

   public AttributeScope createRuleScope(String ruleName, Token scopeAction) {
      AttributeScope scope = new AttributeScope(this, ruleName, scopeAction);
      scope.isDynamicRuleScope = true;
      return scope;
   }

   public AttributeScope createParameterScope(String ruleName, Token argAction) {
      AttributeScope scope = new AttributeScope(this, ruleName, argAction);
      scope.isParameterScope = true;
      return scope;
   }

   public AttributeScope getGlobalScope(String name) {
      return (AttributeScope)this.scopes.get(name);
   }

   public Map getGlobalScopes() {
      return this.scopes;
   }

   protected void defineLabel(Rule r, Token label, GrammarAST element, int type) {
      boolean err = this.nameSpaceChecker.checkForLabelTypeMismatch(r, label, type);
      if (!err) {
         r.defineLabel(label, element, type);
      }
   }

   public void defineTokenRefLabel(String ruleName, Token label, GrammarAST tokenRef) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         if (this.type != 1 || tokenRef.getType() != 18 && tokenRef.getType() != 16 && tokenRef.getType() != 55 && tokenRef.getType() != 19 && tokenRef.getType() != 98) {
            this.defineLabel(r, label, tokenRef, 2);
         } else {
            this.defineLabel(r, label, tokenRef, 5);
         }
      }

   }

   public void defineWildcardTreeLabel(String ruleName, Token label, GrammarAST tokenRef) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         this.defineLabel(r, label, tokenRef, 6);
      }

   }

   public void defineWildcardTreeListLabel(String ruleName, Token label, GrammarAST tokenRef) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         this.defineLabel(r, label, tokenRef, 7);
      }

   }

   public void defineRuleRefLabel(String ruleName, Token label, GrammarAST ruleRef) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         this.defineLabel(r, label, ruleRef, 1);
      }

   }

   public void defineTokenListLabel(String ruleName, Token label, GrammarAST element) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         this.defineLabel(r, label, element, 4);
      }

   }

   public void defineRuleListLabel(String ruleName, Token label, GrammarAST element) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         if (!r.getHasMultipleReturnValues()) {
            ErrorManager.grammarError(134, this, label, label.getText());
         }

         this.defineLabel(r, label, element, 3);
      }

   }

   public Set getLabels(Set rewriteElements, int labelType) {
      Set labels = new HashSet();
      Iterator i$ = rewriteElements.iterator();

      while(i$.hasNext()) {
         GrammarAST el = (GrammarAST)i$.next();
         if (el.getType() == 48) {
            String labelName = el.getText();
            Rule enclosingRule = this.getLocallyDefinedRule(el.enclosingRuleName);
            if (enclosingRule != null) {
               LabelElementPair pair = enclosingRule.getLabel(labelName);
               if (pair != null && pair.type == labelType && !labelName.equals(el.enclosingRuleName)) {
                  labels.add(labelName);
               }
            }
         }
      }

      return labels;
   }

   protected void examineAllExecutableActions() {
      Collection rules = this.getRules();
      Iterator i$ = rules.iterator();

      while(i$.hasNext()) {
         Rule r = (Rule)i$.next();
         List actions = r.getInlineActions();

         for(int i = 0; i < actions.size(); ++i) {
            GrammarAST actionAST = (GrammarAST)actions.get(i);
            ActionAnalysis sniffer = new ActionAnalysis(this, r.name, actionAST);
            sniffer.analyze();
         }

         Collection namedActions = r.getActions().values();
         Iterator i$ = namedActions.iterator();

         while(i$.hasNext()) {
            Object namedAction = i$.next();
            GrammarAST actionAST = (GrammarAST)namedAction;
            ActionAnalysis sniffer = new ActionAnalysis(this, r.name, actionAST);
            sniffer.analyze();
         }
      }

   }

   public void checkAllRulesForUselessLabels() {
      if (this.type != 1) {
         Set rules = this.nameToRuleMap.keySet();
         Iterator i$ = rules.iterator();

         while(i$.hasNext()) {
            String ruleName = (String)i$.next();
            Rule r = this.getRule(ruleName);
            this.removeUselessLabels(r.getRuleLabels());
            this.removeUselessLabels(r.getRuleListLabels());
         }

      }
   }

   protected void removeUselessLabels(Map ruleToElementLabelPairMap) {
      if (ruleToElementLabelPairMap != null) {
         Collection labels = ruleToElementLabelPairMap.values();
         List kill = new ArrayList();
         Iterator i$ = labels.iterator();

         while(i$.hasNext()) {
            LabelElementPair pair = (LabelElementPair)i$.next();
            Rule refdRule = this.getRule(pair.elementRef.getText());
            if (refdRule != null && !refdRule.getHasReturnValue() && !pair.actionReferencesLabel) {
               kill.add(pair.label.getText());
            }
         }

         for(int i = 0; i < kill.size(); ++i) {
            String labelToKill = (String)kill.get(i);
            ruleToElementLabelPairMap.remove(labelToKill);
         }

      }
   }

   public void altReferencesRule(String enclosingRuleName, GrammarAST refScopeAST, GrammarAST refAST, int outerAltNum) {
      Rule r = this.getRule(enclosingRuleName);
      if (r != null) {
         r.trackRuleReferenceInAlt(refAST, outerAltNum);
         Token refToken = refAST.getToken();
         if (!this.ruleRefs.contains(refAST)) {
            this.ruleRefs.add(refAST);
         }

      }
   }

   public void altReferencesTokenID(String ruleName, GrammarAST refAST, int outerAltNum) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         r.trackTokenReferenceInAlt(refAST, outerAltNum);
         if (!this.tokenIDRefs.contains(refAST.getToken())) {
            this.tokenIDRefs.add(refAST.getToken());
         }

      }
   }

   public void referenceRuleLabelPredefinedAttribute(String ruleName) {
      Rule r = this.getRule(ruleName);
      if (r != null && this.type != 1) {
         r.referencedPredefinedRuleAttributes = true;
      }

   }

   public List checkAllRulesForLeftRecursion() {
      return this.sanity.checkAllRulesForLeftRecursion();
   }

   public Set getLeftRecursiveRules() {
      if (this.nfa == null) {
         this.buildNFA();
      }

      if (this.leftRecursiveRules != null) {
         return this.leftRecursiveRules;
      } else {
         this.sanity.checkAllRulesForLeftRecursion();
         return this.leftRecursiveRules;
      }
   }

   public void checkRuleReference(GrammarAST scopeAST, GrammarAST refAST, GrammarAST argsAST, String currentRuleName) {
      this.sanity.checkRuleReference(scopeAST, refAST, argsAST, currentRuleName);
   }

   public boolean isEmptyRule(GrammarAST block) {
      BitSet nonEmptyTerminals = new BitSet();
      nonEmptyTerminals.set(94);
      nonEmptyTerminals.set(88);
      nonEmptyTerminals.set(18);
      nonEmptyTerminals.set(98);
      nonEmptyTerminals.set(80);
      return this.findFirstTypeOutsideRewrite(block, nonEmptyTerminals) == null;
   }

   protected GrammarAST findFirstTypeOutsideRewrite(GrammarAST block, BitSet types) {
      ArrayList worklist = new ArrayList();
      worklist.add(block);

      while(!worklist.isEmpty()) {
         GrammarAST current = (GrammarAST)worklist.remove(worklist.size() - 1);
         if (current.getType() != 75) {
            if (current.getType() >= 0 && types.get(current.getType())) {
               return current;
            }

            worklist.addAll(Arrays.asList(current.getChildrenAsArray()));
         }
      }

      return null;
   }

   public boolean isAtomTokenType(int ttype) {
      return ttype == 98 || ttype == 18 || ttype == 19 || ttype == 88 || ttype == 55 || this.type != 1 && ttype == 94;
   }

   public int getTokenType(String tokenName) {
      Integer I;
      if (tokenName.charAt(0) == '\'') {
         I = (Integer)this.composite.stringLiteralToTypeMap.get(tokenName);
      } else {
         I = (Integer)this.composite.tokenIDToTypeMap.get(tokenName);
      }

      int i = I != null ? I : -7;
      return i;
   }

   public Set getTokenIDs() {
      return this.composite.tokenIDToTypeMap.keySet();
   }

   public Collection getTokenTypesWithoutID() {
      List types = new ArrayList();

      for(int t = 4; t <= this.getMaxTokenType(); ++t) {
         String name = this.getTokenDisplayName(t);
         if (name.charAt(0) == '\'') {
            types.add(Utils.integer(t));
         }
      }

      return types;
   }

   public Set getTokenDisplayNames() {
      Set names = new HashSet();

      for(int t = 4; t <= this.getMaxTokenType(); ++t) {
         names.add(this.getTokenDisplayName(t));
      }

      return names;
   }

   public static int getCharValueFromGrammarCharLiteral(String literal) {
      switch (literal.length()) {
         case 3:
            return literal.charAt(1);
         case 4:
            if (Character.isDigit(literal.charAt(2))) {
               ErrorManager.error(100, (Object)("invalid char literal: " + literal));
               return -1;
            } else {
               int escChar = literal.charAt(2);
               int charVal = ANTLRLiteralEscapedCharValue[escChar];
               if (charVal == 0) {
                  return escChar;
               }

               return charVal;
            }
         case 8:
            String unicodeChars = literal.substring(3, literal.length() - 1);
            return Integer.parseInt(unicodeChars, 16);
         default:
            ErrorManager.error(100, (Object)("invalid char literal: " + literal));
            return -1;
      }
   }

   public static StringBuffer getUnescapedStringFromGrammarStringLiteral(String literal) {
      StringBuffer buf = new StringBuffer();
      int last = literal.length() - 1;

      for(int i = 1; i < last; ++i) {
         char c = literal.charAt(i);
         if (c == '\\') {
            ++i;
            c = literal.charAt(i);
            if (Character.toUpperCase(c) == 'U') {
               ++i;
               String unicodeChars = literal.substring(i, i + 4);
               int val = Integer.parseInt(unicodeChars, 16);
               i += 3;
               buf.append((char)val);
            } else if (Character.isDigit(c)) {
               ErrorManager.error(100, (Object)("invalid char literal: " + literal));
               buf.append("\\").append(c);
            } else {
               buf.append((char)ANTLRLiteralEscapedCharValue[c]);
            }
         } else {
            buf.append(c);
         }
      }

      return buf;
   }

   public int importTokenVocabulary(Grammar importFromGr) {
      Set importedTokenIDs = importFromGr.getTokenIDs();
      Iterator i$ = importedTokenIDs.iterator();

      while(i$.hasNext()) {
         String tokenID = (String)i$.next();
         int tokenType = importFromGr.getTokenType(tokenID);
         this.composite.maxTokenType = Math.max(this.composite.maxTokenType, tokenType);
         if (tokenType >= 4) {
            this.defineToken(tokenID, tokenType);
         }
      }

      return this.composite.maxTokenType;
   }

   public void importGrammar(GrammarAST grammarNameAST, String label) {
      String grammarName = grammarNameAST.getText();
      String gname = grammarName + ".g";
      BufferedReader br = null;

      try {
         String fullName = this.tool.getLibraryFile(gname);
         FileReader fr = new FileReader(fullName);
         br = new BufferedReader(fr);
         Grammar delegateGrammar = new Grammar(this.tool, gname, this.composite);
         delegateGrammar.label = label;
         this.addDelegateGrammar(delegateGrammar);
         delegateGrammar.parseAndBuildAST(br);
         delegateGrammar.addRulesForSyntacticPredicates();
         if (this.validImport(delegateGrammar)) {
            if (this.type != 4 || !delegateGrammar.name.equals(this.name + grammarTypeToFileNameSuffix[1]) && !delegateGrammar.name.equals(this.name + grammarTypeToFileNameSuffix[2])) {
               if (delegateGrammar.grammarTree != null && delegateGrammar.type == 1 && this.type == 4) {
                  this.lexerGrammarST.add("imports", grammarName);
               }

               return;
            }

            ErrorManager.grammarError(163, this, grammarNameAST.token, this, delegateGrammar);
            return;
         }

         ErrorManager.grammarError(161, this, grammarNameAST.token, this, delegateGrammar);
      } catch (IOException var20) {
         ErrorManager.error(7, gname, (Throwable)var20);
         return;
      } finally {
         if (br != null) {
            try {
               br.close();
            } catch (IOException var19) {
               ErrorManager.error(2, gname, (Throwable)var19);
            }
         }

      }

   }

   protected void addDelegateGrammar(Grammar delegateGrammar) {
      CompositeGrammarTree t = this.composite.delegateGrammarTreeRoot.findNode(this);
      t.addChild(new CompositeGrammarTree(delegateGrammar));
      delegateGrammar.composite = this.composite;
   }

   public int importTokenVocabulary(GrammarAST tokenVocabOptionAST, String vocabName) {
      if (!this.getGrammarIsRoot()) {
         ErrorManager.grammarWarning(160, this, tokenVocabOptionAST.token, this.name);
         return this.composite.maxTokenType;
      } else {
         File fullFile = this.tool.getImportedVocabFile(vocabName);

         try {
            FileReader fr = new FileReader(fullFile);
            BufferedReader br = new BufferedReader(fr);
            StreamTokenizer tokenizer = new StreamTokenizer(br);
            tokenizer.parseNumbers();
            tokenizer.wordChars(95, 95);
            tokenizer.eolIsSignificant(true);
            tokenizer.slashSlashComments(true);
            tokenizer.slashStarComments(true);
            tokenizer.ordinaryChar(61);
            tokenizer.quoteChar(39);
            tokenizer.whitespaceChars(32, 32);
            tokenizer.whitespaceChars(9, 9);
            int lineNum = 1;
            int token = tokenizer.nextToken();

            while(true) {
               while(true) {
                  String tokenID;
                  while(true) {
                     if (token == -1) {
                        br.close();
                        return this.composite.maxTokenType;
                     }

                     if (token == -3) {
                        tokenID = tokenizer.sval;
                        break;
                     }

                     if (token == 39) {
                        tokenID = "'" + tokenizer.sval + "'";
                        break;
                     }

                     ErrorManager.error(13, vocabName + ".tokens", (Object)Utils.integer(lineNum));

                     while(tokenizer.nextToken() != 10) {
                     }

                     token = tokenizer.nextToken();
                  }

                  token = tokenizer.nextToken();
                  if (token != 61) {
                     ErrorManager.error(13, vocabName + ".tokens", (Object)Utils.integer(lineNum));

                     while(tokenizer.nextToken() != 10) {
                     }

                     token = tokenizer.nextToken();
                  } else {
                     token = tokenizer.nextToken();
                     if (token != -2) {
                        ErrorManager.error(13, vocabName + ".tokens", (Object)Utils.integer(lineNum));

                        while(tokenizer.nextToken() != 10) {
                        }

                        token = tokenizer.nextToken();
                     } else {
                        int tokenType = (int)tokenizer.nval;
                        token = tokenizer.nextToken();
                        this.composite.maxTokenType = Math.max(this.composite.maxTokenType, tokenType);
                        this.defineToken(tokenID, tokenType);
                        ++lineNum;
                        if (token == 10) {
                           token = tokenizer.nextToken();
                        } else {
                           ErrorManager.error(13, vocabName + ".tokens", (Object)Utils.integer(lineNum));

                           while(tokenizer.nextToken() != 10) {
                           }

                           token = tokenizer.nextToken();
                        }
                     }
                  }
               }
            }
         } catch (FileNotFoundException var11) {
            ErrorManager.error(3, (Object)fullFile);
         } catch (IOException var12) {
            ErrorManager.error(4, fullFile, (Throwable)var12);
         } catch (Exception var13) {
            ErrorManager.error(4, fullFile, (Throwable)var13);
         }

         return this.composite.maxTokenType;
      }
   }

   public String getTokenDisplayName(int ttype) {
      if (this.type == 1 && ttype >= 0 && ttype <= 65535) {
         return getANTLRCharLiteralForChar(ttype);
      } else {
         String tokenName;
         if (ttype < 0) {
            tokenName = (String)this.composite.typeToTokenList.get(7 + ttype);
         } else {
            int index = ttype - 1;
            index += 7;
            if (index < this.composite.typeToTokenList.size()) {
               tokenName = (String)this.composite.typeToTokenList.get(index);
               if (tokenName != null && tokenName.startsWith("T__")) {
                  tokenName = (String)this.composite.typeToStringLiteralList.get(ttype);
               }
            } else {
               tokenName = String.valueOf(ttype);
            }
         }

         return tokenName;
      }
   }

   public Set getStringLiterals() {
      return this.composite.stringLiteralToTypeMap.keySet();
   }

   public String getGrammarTypeString() {
      return grammarTypeToString[this.type];
   }

   public int getGrammarMaxLookahead() {
      if (this.global_k >= 0) {
         return this.global_k;
      } else {
         Object k = this.getOption("k");
         if (k == null) {
            this.global_k = 0;
         } else if (k instanceof Integer) {
            Integer kI = (Integer)k;
            this.global_k = kI;
         } else if (k.equals("*")) {
            this.global_k = 0;
         }

         return this.global_k;
      }
   }

   public String setOption(String key, Object value, Token optionsStartToken) {
      if (this.legalOption(key)) {
         ErrorManager.grammarError(133, this, optionsStartToken, key);
         return null;
      } else if (!this.optionIsValid(key, value)) {
         return null;
      } else {
         if (key.equals("backtrack") && value.toString().equals("true")) {
            this.composite.getRootGrammar().atLeastOneBacktrackOption = true;
         }

         if (this.options == null) {
            this.options = new HashMap();
         }

         this.options.put(key, value);
         return key;
      }
   }

   public boolean legalOption(String key) {
      switch (this.type) {
         case 1:
            return !legalLexerOptions.contains(key);
         case 2:
            return !legalParserOptions.contains(key);
         case 3:
            return !legalTreeParserOptions.contains(key);
         default:
            return !legalParserOptions.contains(key);
      }
   }

   public void setOptions(Map options, Token optionsStartToken) {
      if (options == null) {
         this.options = null;
      } else {
         Set keys = options.keySet();
         Iterator it = keys.iterator();

         while(it.hasNext()) {
            String optionName = (String)it.next();
            Object optionValue = options.get(optionName);
            String stored = this.setOption(optionName, optionValue, optionsStartToken);
            if (stored == null) {
               it.remove();
            }
         }

      }
   }

   public Object getOption(String key) {
      return this.composite.getOption(key);
   }

   public Object getLocallyDefinedOption(String key) {
      Object value = null;
      if (this.options != null) {
         value = this.options.get(key);
      }

      if (value == null) {
         value = defaultOptions.get(key);
      }

      return value;
   }

   public Object getBlockOption(GrammarAST blockAST, String key) {
      String v = (String)blockAST.getBlockOption(key);
      if (v != null) {
         return v;
      } else {
         return this.type == 1 ? defaultLexerBlockOptions.get(key) : defaultBlockOptions.get(key);
      }
   }

   public int getUserMaxLookahead(int decision) {
      int user_k = 0;
      GrammarAST blockAST = this.nfa.grammar.getDecisionBlockAST(decision);
      Object k = blockAST.getBlockOption("k");
      if (k == null) {
         user_k = this.nfa.grammar.getGrammarMaxLookahead();
         return user_k;
      } else {
         if (k instanceof Integer) {
            Integer kI = (Integer)k;
            user_k = kI;
         } else if (k.equals("*")) {
            user_k = 0;
         }

         return user_k;
      }
   }

   public boolean getAutoBacktrackMode(int decision) {
      NFAState decisionNFAStartState = this.getDecisionNFAStartState(decision);
      String autoBacktrack = (String)this.getBlockOption(decisionNFAStartState.associatedASTNode, "backtrack");
      if (autoBacktrack == null) {
         autoBacktrack = (String)this.nfa.grammar.getOption("backtrack");
      }

      return autoBacktrack != null && autoBacktrack.equals("true");
   }

   public boolean optionIsValid(String key, Object value) {
      return true;
   }

   public boolean buildAST() {
      String outputType = (String)this.getOption("output");
      return outputType != null ? outputType.toString().equals("AST") : false;
   }

   public boolean rewriteMode() {
      Object outputType = this.getOption("rewrite");
      return outputType != null ? outputType.toString().equals("true") : false;
   }

   public boolean isBuiltFromString() {
      return this.builtFromString;
   }

   public boolean buildTemplate() {
      String outputType = (String)this.getOption("output");
      return outputType != null ? outputType.toString().equals("template") : false;
   }

   public Collection getRules() {
      return this.nameToRuleMap.values();
   }

   public Set getDelegatedRules() {
      return this.composite.getDelegatedRules(this);
   }

   public Set getAllImportedRules() {
      return this.composite.getAllImportedRules(this);
   }

   public List getDelegates() {
      return this.composite.getDelegates(this);
   }

   public boolean getHasDelegates() {
      return !this.getDelegates().isEmpty();
   }

   public List getDelegateNames() {
      List names = new ArrayList();
      List delegates = this.composite.getDelegates(this);
      if (delegates != null) {
         Iterator i$ = delegates.iterator();

         while(i$.hasNext()) {
            Grammar g = (Grammar)i$.next();
            names.add(g.name);
         }
      }

      return names;
   }

   public List getDirectDelegates() {
      return this.composite.getDirectDelegates(this);
   }

   public List getIndirectDelegates() {
      return this.composite.getIndirectDelegates(this);
   }

   public List getDelegators() {
      return this.composite.getDelegators(this);
   }

   public Grammar getDelegator() {
      return this.composite.getDelegator(this);
   }

   public Set getDelegatedRuleReferences() {
      return this.delegatedRuleReferences;
   }

   public boolean getGrammarIsRoot() {
      return this.composite.delegateGrammarTreeRoot.grammar == this;
   }

   public void setRuleAST(String ruleName, GrammarAST t) {
      Rule r = this.getLocallyDefinedRule(ruleName);
      if (r != null) {
         r.tree = t;
         r.EORNode = t.getLastChild();
      }

   }

   public NFAState getRuleStartState(String ruleName) {
      return this.getRuleStartState((String)null, ruleName);
   }

   public NFAState getRuleStartState(String scopeName, String ruleName) {
      Rule r = this.getRule(scopeName, ruleName);
      return r != null ? r.startState : null;
   }

   public String getRuleModifier(String ruleName) {
      Rule r = this.getRule(ruleName);
      return r != null ? r.modifier : null;
   }

   public NFAState getRuleStopState(String ruleName) {
      Rule r = this.getRule(ruleName);
      return r != null ? r.stopState : null;
   }

   public int assignDecisionNumber(NFAState state) {
      ++this.decisionCount;
      state.setDecisionNumber(this.decisionCount);
      return this.decisionCount;
   }

   protected Decision getDecision(int decision) {
      int index = decision - 1;
      if (index >= this.indexToDecision.size()) {
         return null;
      } else {
         Decision d = (Decision)this.indexToDecision.get(index);
         return d;
      }
   }

   public List getDecisions() {
      return this.indexToDecision;
   }

   protected Decision createDecision(int decision) {
      int index = decision - 1;
      if (index < this.indexToDecision.size()) {
         return this.getDecision(decision);
      } else {
         Decision d = new Decision();
         d.decision = decision;
         d.grammar = this;
         this.indexToDecision.setSize(this.getNumberOfDecisions());
         this.indexToDecision.set(index, d);
         return d;
      }
   }

   public List getDecisionNFAStartStateList() {
      List states = new ArrayList(100);

      for(int d = 0; d < this.indexToDecision.size(); ++d) {
         Decision dec = (Decision)this.indexToDecision.get(d);
         states.add(dec.startState);
      }

      return states;
   }

   public NFAState getDecisionNFAStartState(int decision) {
      Decision d = this.getDecision(decision);
      return d == null ? null : d.startState;
   }

   public DFA getLookaheadDFA(int decision) {
      Decision d = this.getDecision(decision);
      return d == null ? null : d.dfa;
   }

   public GrammarAST getDecisionBlockAST(int decision) {
      Decision d = this.getDecision(decision);
      return d == null ? null : d.blockAST;
   }

   public List getLookaheadDFAColumnsForLineInFile(int line) {
      String prefix = line + ":";
      List columns = new ArrayList();
      Iterator i$ = this.lineColumnToLookaheadDFAMap.keySet().iterator();

      while(i$.hasNext()) {
         String key = (String)i$.next();
         if (key.startsWith(prefix)) {
            columns.add(Integer.valueOf(key.substring(prefix.length())));
         }
      }

      return columns;
   }

   public DFA getLookaheadDFAFromPositionInFile(int line, int col) {
      return (DFA)this.lineColumnToLookaheadDFAMap.get(line + ":" + col);
   }

   public Map getLineColumnToLookaheadDFAMap() {
      return this.lineColumnToLookaheadDFAMap;
   }

   public int getNumberOfDecisions() {
      return this.decisionCount;
   }

   public int getNumberOfCyclicDecisions() {
      int n = 0;

      for(int i = 1; i <= this.getNumberOfDecisions(); ++i) {
         Decision d = this.getDecision(i);
         if (d.dfa != null && d.dfa.isCyclic()) {
            ++n;
         }
      }

      return n;
   }

   public void setLookaheadDFA(int decision, DFA lookaheadDFA) {
      Decision d = this.createDecision(decision);
      d.dfa = lookaheadDFA;
      GrammarAST ast = d.startState.associatedASTNode;
      ast.setLookaheadDFA(lookaheadDFA);
   }

   public void setDecisionNFA(int decision, NFAState state) {
      Decision d = this.createDecision(decision);
      d.startState = state;
   }

   public void setDecisionBlockAST(int decision, GrammarAST blockAST) {
      Decision d = this.createDecision(decision);
      d.blockAST = blockAST;
   }

   public boolean allDecisionDFAHaveBeenCreated() {
      return this.allDecisionDFACreated;
   }

   public int getMaxTokenType() {
      return this.composite.maxTokenType;
   }

   public int getMaxCharValue() {
      return this.generator != null ? this.generator.target.getMaxCharValue(this.generator) : '\uffff';
   }

   public IntSet getTokenTypes() {
      return (IntSet)(this.type == 1 ? this.getAllCharValues() : IntervalSet.of(4, this.getMaxTokenType()));
   }

   public IntSet getAllCharValues() {
      if (this.charVocabulary != null) {
         return this.charVocabulary;
      } else {
         IntSet allChar = IntervalSet.of(0, this.getMaxCharValue());
         return allChar;
      }
   }

   public static String getANTLRCharLiteralForChar(int c) {
      if (c < 0) {
         ErrorManager.internalError("invalid char value " + c);
         return "'<INVALID>'";
      } else if (c < ANTLRLiteralCharValueEscape.length && ANTLRLiteralCharValueEscape[c] != null) {
         return '\'' + ANTLRLiteralCharValueEscape[c] + '\'';
      } else if (UnicodeBlock.of((char)c) == UnicodeBlock.BASIC_LATIN && !Character.isISOControl((char)c)) {
         if (c == 92) {
            return "'\\\\'";
         } else {
            return c == 39 ? "'\\''" : '\'' + Character.toString((char)c) + '\'';
         }
      } else {
         String hex = Integer.toHexString(c | 65536).toUpperCase().substring(1, 5);
         String unicodeStr = "'\\u" + hex + "'";
         return unicodeStr;
      }
   }

   public IntSet complement(IntSet set) {
      IntSet c = set.complement(this.getTokenTypes());
      return c;
   }

   public IntSet complement(int atom) {
      return this.complement(IntervalSet.of(atom));
   }

   public boolean isValidSet(TreeToNFAConverter nfabuilder, GrammarAST t) {
      boolean valid;
      try {
         int alts = nfabuilder.testBlockAsSet(t);
         valid = alts > 1;
      } catch (RecognitionException var5) {
         valid = false;
      }

      return valid;
   }

   public IntSet getSetFromRule(TreeToNFAConverter nfabuilder, String ruleName) throws RecognitionException {
      Rule r = this.getRule(ruleName);
      if (r == null) {
         return null;
      } else {
         IntSet elements = nfabuilder.setRule(r.tree);
         return elements;
      }
   }

   public int getNumberOfAltsForDecisionNFA(NFAState decisionState) {
      if (decisionState == null) {
         return 0;
      } else {
         int n = 1;

         for(NFAState p = decisionState; p.transition[1] != null; p = (NFAState)p.transition[1].target) {
            ++n;
         }

         return n;
      }
   }

   public NFAState getNFAStateForAltOfDecision(NFAState decisionState, int alt) {
      if (decisionState != null && alt > 0) {
         int n = 1;
         NFAState p = decisionState;

         while(p != null) {
            if (n == alt) {
               return p;
            }

            ++n;
            Transition next = p.transition[1];
            p = null;
            if (next != null) {
               p = (NFAState)next.target;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public LookaheadSet FIRST(NFAState s) {
      return this.ll1Analyzer.FIRST(s);
   }

   public LookaheadSet LOOK(NFAState s) {
      return this.ll1Analyzer.LOOK(s);
   }

   public void setCodeGenerator(CodeGenerator generator) {
      this.generator = generator;
   }

   public CodeGenerator getCodeGenerator() {
      return this.generator;
   }

   public GrammarAST getGrammarTree() {
      return this.grammarTree;
   }

   public void setGrammarTree(GrammarAST value) {
      this.grammarTree = value;
   }

   public Tool getTool() {
      return this.tool;
   }

   public void setTool(Tool tool) {
      this.tool = tool;
   }

   public String computeTokenNameFromLiteral(int tokenType, String literal) {
      return "T__" + tokenType;
   }

   public String toString() {
      return this.grammarTreeToString(this.grammarTree);
   }

   public String grammarTreeToString(GrammarAST t) {
      return this.grammarTreeToString(t, true);
   }

   public String grammarTreeToString(GrammarAST t, boolean showActions) {
      String s;
      try {
         s = t.getLine() + ":" + (t.getCharPositionInLine() + 1) + ": ";
         s = s + (new ANTLRTreePrinter(new CommonTreeNodeStream(t))).toString(this, showActions);
      } catch (Exception var5) {
         s = "<invalid or missing tree structure>";
      }

      return s;
   }

   public void printGrammar(PrintStream output) {
      ANTLRTreePrinter printer = new ANTLRTreePrinter(new CommonTreeNodeStream(this.getGrammarTree()));

      try {
         String g = printer.toString(this, false);
         output.println(g);
      } catch (RecognitionException var4) {
         ErrorManager.error(100, (Throwable)var4);
      }

   }

   static {
      ANTLRLiteralEscapedCharValue[110] = 10;
      ANTLRLiteralEscapedCharValue[114] = 13;
      ANTLRLiteralEscapedCharValue[116] = 9;
      ANTLRLiteralEscapedCharValue[98] = 8;
      ANTLRLiteralEscapedCharValue[102] = 12;
      ANTLRLiteralEscapedCharValue[92] = 92;
      ANTLRLiteralEscapedCharValue[39] = 39;
      ANTLRLiteralEscapedCharValue[34] = 34;
      ANTLRLiteralCharValueEscape[10] = "\\n";
      ANTLRLiteralCharValueEscape[13] = "\\r";
      ANTLRLiteralCharValueEscape[9] = "\\t";
      ANTLRLiteralCharValueEscape[8] = "\\b";
      ANTLRLiteralCharValueEscape[12] = "\\f";
      ANTLRLiteralCharValueEscape[92] = "\\\\";
      ANTLRLiteralCharValueEscape[39] = "\\'";
      grammarTypeToString = new String[]{"<invalid>", "lexer", "parser", "tree", "combined"};
      grammarTypeToFileNameSuffix = new String[]{"<invalid>", "Lexer", "Parser", "", "Parser"};
      validDelegations = new MultiMap() {
         {
            this.map(1, 1);
            this.map(1, 2);
            this.map(1, 4);
            this.map(2, 2);
            this.map(2, 4);
            this.map(3, 3);
         }
      };
      legalLexerOptions = new HashSet() {
         {
            this.add("language");
            this.add("tokenVocab");
            this.add("TokenLabelType");
            this.add("superClass");
            this.add("filter");
            this.add("k");
            this.add("backtrack");
            this.add("memoize");
         }
      };
      legalParserOptions = new HashSet() {
         {
            this.add("language");
            this.add("tokenVocab");
            this.add("output");
            this.add("rewrite");
            this.add("ASTLabelType");
            this.add("TokenLabelType");
            this.add("superClass");
            this.add("k");
            this.add("backtrack");
            this.add("memoize");
         }
      };
      legalTreeParserOptions = new HashSet() {
         {
            this.add("language");
            this.add("tokenVocab");
            this.add("output");
            this.add("rewrite");
            this.add("ASTLabelType");
            this.add("TokenLabelType");
            this.add("superClass");
            this.add("k");
            this.add("backtrack");
            this.add("memoize");
            this.add("filter");
         }
      };
      doNotCopyOptionsToLexer = new HashSet() {
         {
            this.add("output");
            this.add("ASTLabelType");
            this.add("superClass");
            this.add("k");
            this.add("backtrack");
            this.add("memoize");
            this.add("rewrite");
         }
      };
      defaultOptions = new HashMap() {
         {
            this.put("language", "Java");
         }
      };
      legalBlockOptions = new HashSet() {
         {
            this.add("k");
            this.add("greedy");
            this.add("backtrack");
            this.add("memoize");
         }
      };
      defaultBlockOptions = new HashMap() {
         {
            this.put("greedy", "true");
         }
      };
      defaultLexerBlockOptions = new HashMap() {
         {
            this.put("greedy", "true");
         }
      };
      legalTokenOptions = new HashSet() {
         {
            this.add("node");
            this.add("type");
            this.add("text");
            this.add("assoc");
         }
      };
   }

   public class LabelElementPair {
      public Token label;
      public GrammarAST elementRef;
      public String referencedRuleName;
      public boolean actionReferencesLabel;
      public int type;

      public LabelElementPair(Token label, GrammarAST elementRef) {
         this.label = label;
         this.elementRef = elementRef;
         this.referencedRuleName = elementRef.getText();
      }

      public Rule getReferencedRule() {
         return Grammar.this.getRule(this.referencedRuleName);
      }

      public String toString() {
         return this.elementRef.toString();
      }
   }

   public static class Decision {
      public Grammar grammar;
      public int decision;
      public NFAState startState;
      public GrammarAST blockAST;
      public DFA dfa;
   }
}
