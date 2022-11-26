package org.antlr.codegen;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.Tool;
import org.antlr.analysis.DFA;
import org.antlr.analysis.DFAOptimizer;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.Label;
import org.antlr.analysis.LookaheadSet;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.SemanticContext;
import org.antlr.analysis.Transition;
import org.antlr.grammar.v3.ANTLRLexer;
import org.antlr.grammar.v3.ANTLRParser;
import org.antlr.grammar.v3.ActionTranslator;
import org.antlr.grammar.v3.CodeGenTreeWalker;
import org.antlr.misc.BitSet;
import org.antlr.misc.IntSet;
import org.antlr.misc.Interval;
import org.antlr.misc.IntervalSet;
import org.antlr.misc.Utils;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.tool.AttributeScope;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Rule;
import org.antlr.tool.ToolSTGroupFile;
import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STWriter;

public class CodeGenerator {
   public static final int MSCL_DEFAULT = 300;
   public static int MAX_SWITCH_CASE_LABELS = 300;
   public static final int MSA_DEFAULT = 3;
   public static int MIN_SWITCH_ALTS = 3;
   public boolean GENERATE_SWITCHES_WHEN_POSSIBLE = true;
   public static boolean LAUNCH_ST_INSPECTOR = false;
   public static final int MADSI_DEFAULT = 60;
   public static int MAX_ACYCLIC_DFA_STATES_INLINE = 60;
   public static String classpathTemplateRootDirectoryName = "org/antlr/codegen/templates";
   public Grammar grammar;
   protected String language;
   public Target target = null;
   protected STGroup templates;
   protected STGroup baseTemplates;
   protected ST recognizerST;
   protected ST outputFileST;
   protected ST headerFileST;
   protected int uniqueLabelNumber = 1;
   protected Tool tool;
   protected boolean debug;
   protected boolean trace;
   protected boolean profile;
   protected int lineWidth = 72;
   public ACyclicDFACodeGenerator acyclicDFAGenerator = new ACyclicDFACodeGenerator(this);
   public static final String VOCAB_FILE_EXTENSION = ".tokens";
   protected static final String vocabFilePattern = "<tokens:{it|<it.name>=<it.type>\n}><literals:{it|<it.name>=<it.type>\n}>";

   public CodeGenerator(Tool tool, Grammar grammar, String language) {
      this.tool = tool;
      this.grammar = grammar;
      this.language = language;
      this.target = loadLanguageTarget(language);
   }

   public static Target loadLanguageTarget(String language) {
      Target target = null;
      String targetName = "org.antlr.codegen." + language + "Target";

      try {
         Class c = Class.forName(targetName).asSubclass(Target.class);
         target = (Target)c.newInstance();
      } catch (ClassNotFoundException var4) {
         target = new Target();
      } catch (InstantiationException var5) {
         ErrorManager.error(23, targetName, (Throwable)var5);
      } catch (IllegalAccessException var6) {
         ErrorManager.error(23, targetName, (Throwable)var6);
      }

      return target;
   }

   public void loadTemplates(String language) {
      String langDir = classpathTemplateRootDirectoryName + "/" + language;
      STGroup coreTemplates = new ToolSTGroupFile(langDir + "/" + language + ".stg");
      this.baseTemplates = coreTemplates;
      String outputOption = (String)this.grammar.getOption("output");
      ToolSTGroupFile astTemplates;
      ToolSTGroupFile astParserTemplates;
      if (outputOption != null && outputOption.equals("AST")) {
         if (this.debug && this.grammar.type != 1) {
            astTemplates = new ToolSTGroupFile(langDir + "/Dbg.stg");
            astTemplates.importTemplates((STGroup)coreTemplates);
            this.baseTemplates = astTemplates;
            astParserTemplates = new ToolSTGroupFile(langDir + "/AST.stg");
            astParserTemplates.importTemplates((STGroup)astTemplates);
            ToolSTGroupFile astParserTemplates;
            if (this.grammar.type == 3) {
               astParserTemplates = new ToolSTGroupFile(langDir + "/ASTTreeParser.stg");
               astParserTemplates.importTemplates((STGroup)astParserTemplates);
            } else {
               astParserTemplates = new ToolSTGroupFile(langDir + "/ASTParser.stg");
               astParserTemplates.importTemplates((STGroup)astParserTemplates);
            }

            STGroup astDbgTemplates = new ToolSTGroupFile(langDir + "/ASTDbg.stg");
            astDbgTemplates.importTemplates((STGroup)astParserTemplates);
            this.templates = astDbgTemplates;
            astTemplates.iterateAcrossValues = true;
            astDbgTemplates.iterateAcrossValues = true;
            astParserTemplates.iterateAcrossValues = true;
         } else {
            astTemplates = new ToolSTGroupFile(langDir + "/AST.stg");
            astTemplates.importTemplates((STGroup)coreTemplates);
            if (this.grammar.type == 3) {
               astParserTemplates = new ToolSTGroupFile(langDir + "/ASTTreeParser.stg");
               astParserTemplates.importTemplates((STGroup)astTemplates);
            } else {
               astParserTemplates = new ToolSTGroupFile(langDir + "/ASTParser.stg");
               astParserTemplates.importTemplates((STGroup)astTemplates);
            }

            this.templates = astParserTemplates;
            astTemplates.iterateAcrossValues = true;
            astParserTemplates.iterateAcrossValues = true;
         }
      } else if (outputOption != null && outputOption.equals("template")) {
         if (this.debug && this.grammar.type != 1) {
            astTemplates = new ToolSTGroupFile(langDir + "/Dbg.stg");
            astTemplates.importTemplates((STGroup)coreTemplates);
            this.baseTemplates = astTemplates;
            astParserTemplates = new ToolSTGroupFile(langDir + "/ST.stg");
            astParserTemplates.importTemplates((STGroup)astTemplates);
            this.templates = astParserTemplates;
            astTemplates.iterateAcrossValues = true;
         } else {
            astTemplates = new ToolSTGroupFile(langDir + "/ST.stg");
            astTemplates.importTemplates((STGroup)coreTemplates);
            this.templates = astTemplates;
         }

         this.templates.iterateAcrossValues = true;
      } else if (this.debug && this.grammar.type != 1) {
         astTemplates = new ToolSTGroupFile(langDir + "/Dbg.stg");
         astTemplates.importTemplates((STGroup)coreTemplates);
         this.templates = astTemplates;
         this.baseTemplates = this.templates;
         this.baseTemplates.iterateAcrossValues = true;
      } else {
         this.templates = coreTemplates;
         coreTemplates.iterateAcrossValues = true;
      }

   }

   public ST genRecognizer() {
      this.loadTemplates(this.language);
      if (this.templates == null) {
         return null;
      } else if (ErrorManager.doNotAttemptAnalysis()) {
         return null;
      } else {
         this.target.performGrammarAnalysis(this, this.grammar);
         if (ErrorManager.doNotAttemptCodeGen()) {
            return null;
         } else {
            DFAOptimizer optimizer = new DFAOptimizer(this.grammar);
            optimizer.optimize();
            this.outputFileST = this.templates.getInstanceOf("outputFile");
            if (this.templates.isDefined("headerFile")) {
               this.headerFileST = this.templates.getInstanceOf("headerFile");
            } else {
               this.headerFileST = new ST(this.templates, "xyz");
               this.headerFileST.add("cyclicDFAs", (Object)null);
            }

            boolean filterMode = this.grammar.getOption("filter") != null && this.grammar.getOption("filter").equals("true");
            boolean canBacktrack = this.grammar.getSyntacticPredicates() != null || this.grammar.composite.getRootGrammar().atLeastOneBacktrackOption || filterMode;
            Map actions = this.grammar.getActions();
            this.verifyActionScopesOkForTarget(actions);
            this.translateActionAttributeReferences(actions);
            ST gateST = this.templates.getInstanceOf("actionGate");
            if (filterMode) {
               gateST = this.templates.getInstanceOf("filteringActionGate");
            }

            this.grammar.setSynPredGateIfNotAlready(gateST);
            this.headerFileST.add("actions", actions);
            this.outputFileST.add("actions", actions);
            this.headerFileST.add("buildTemplate", this.grammar.buildTemplate());
            this.outputFileST.add("buildTemplate", this.grammar.buildTemplate());
            this.headerFileST.add("buildAST", this.grammar.buildAST());
            this.outputFileST.add("buildAST", this.grammar.buildAST());
            this.outputFileST.add("rewriteMode", this.grammar.rewriteMode());
            this.headerFileST.add("rewriteMode", this.grammar.rewriteMode());
            this.outputFileST.add("backtracking", canBacktrack);
            this.headerFileST.add("backtracking", canBacktrack);
            String memoize = (String)this.grammar.getOption("memoize");
            this.outputFileST.add("memoize", this.grammar.atLeastOneRuleMemoizes || memoize != null && memoize.equals("true") && canBacktrack);
            this.headerFileST.add("memoize", this.grammar.atLeastOneRuleMemoizes || memoize != null && memoize.equals("true") && canBacktrack);
            this.outputFileST.add("trace", this.trace);
            this.headerFileST.add("trace", this.trace);
            this.outputFileST.add("profile", this.profile);
            this.headerFileST.add("profile", this.profile);
            if (this.grammar.type == 1) {
               this.recognizerST = this.templates.getInstanceOf("lexer");
               this.outputFileST.add("LEXER", true);
               this.headerFileST.add("LEXER", true);
               this.recognizerST.add("filterMode", filterMode);
            } else if (this.grammar.type != 2 && this.grammar.type != 4) {
               this.recognizerST = this.templates.getInstanceOf("treeParser");
               this.outputFileST.add("TREE_PARSER", true);
               this.headerFileST.add("TREE_PARSER", true);
               this.recognizerST.add("filterMode", filterMode);
            } else {
               this.recognizerST = this.templates.getInstanceOf("parser");
               this.outputFileST.add("PARSER", true);
               this.headerFileST.add("PARSER", true);
            }

            this.outputFileST.add("recognizer", this.recognizerST);
            this.headerFileST.add("recognizer", this.recognizerST);
            this.outputFileST.add("actionScope", this.grammar.getDefaultActionScope(this.grammar.type));
            this.headerFileST.add("actionScope", this.grammar.getDefaultActionScope(this.grammar.type));
            String targetAppropriateFileNameString = this.target.getTargetStringLiteralFromString(this.grammar.getFileName());
            this.outputFileST.add("fileName", targetAppropriateFileNameString);
            this.headerFileST.add("fileName", targetAppropriateFileNameString);
            this.outputFileST.add("ANTLRVersion", this.tool.VERSION);
            this.headerFileST.add("ANTLRVersion", this.tool.VERSION);
            this.outputFileST.add("generatedTimestamp", Tool.getCurrentTimeStamp());
            this.headerFileST.add("generatedTimestamp", Tool.getCurrentTimeStamp());
            CodeGenTreeWalker gen = new CodeGenTreeWalker(new CommonTreeNodeStream(this.grammar.getGrammarTree()));

            try {
               gen.grammar_(this.grammar, this.recognizerST, this.outputFileST, this.headerFileST);
            } catch (RecognitionException var13) {
               ErrorManager.error(15, (Throwable)var13);
            }

            this.genTokenTypeConstants(this.recognizerST);
            this.genTokenTypeConstants(this.outputFileST);
            this.genTokenTypeConstants(this.headerFileST);
            if (this.grammar.type != 1) {
               this.genTokenTypeNames(this.recognizerST);
               this.genTokenTypeNames(this.outputFileST);
               this.genTokenTypeNames(this.headerFileST);
            }

            Set synpredNames = null;
            if (this.grammar.synPredNamesUsedInDFA.size() > 0) {
               synpredNames = this.grammar.synPredNamesUsedInDFA;
            }

            this.outputFileST.add("synpreds", synpredNames);
            this.headerFileST.add("synpreds", synpredNames);
            this.recognizerST.add("grammar", this.grammar);
            if (ErrorManager.getErrorState().errors > 0) {
               return null;
            } else {
               if (LAUNCH_ST_INSPECTOR) {
                  this.outputFileST.inspect();
                  if (this.templates.isDefined("headerFile")) {
                     this.headerFileST.inspect();
                  }
               }

               try {
                  this.target.genRecognizerFile(this.tool, this, this.grammar, this.outputFileST);
                  ST tokenVocabSerialization;
                  if (this.templates.isDefined("headerFile")) {
                     tokenVocabSerialization = this.templates.getInstanceOf("headerFileExtension");
                     this.target.genRecognizerHeaderFile(this.tool, this, this.grammar, this.headerFileST, tokenVocabSerialization.render());
                  }

                  tokenVocabSerialization = this.genTokenVocabOutput();
                  String vocabFileName = this.getVocabFileName();
                  if (vocabFileName != null) {
                     this.write(tokenVocabSerialization, vocabFileName);
                  }
               } catch (IOException var12) {
                  ErrorManager.error(1, (Throwable)var12);
               }

               return this.outputFileST;
            }
         }
      }
   }

   protected void verifyActionScopesOkForTarget(Map actions) {
      Iterator i$ = actions.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         String scope = (String)entry.getKey();
         if (!this.target.isValidActionScope(this.grammar.type, scope)) {
            Map scopeActions = (Map)entry.getValue();
            GrammarAST actionAST = (GrammarAST)scopeActions.values().iterator().next();
            ErrorManager.grammarError(143, this.grammar, actionAST.getToken(), scope, this.grammar.getGrammarTypeString());
         }
      }

   }

   protected void translateActionAttributeReferences(Map actions) {
      Iterator i$ = actions.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         Map scopeActions = (Map)entry.getValue();
         this.translateActionAttributeReferencesForSingleScope((Rule)null, scopeActions);
      }

   }

   public void translateActionAttributeReferencesForSingleScope(Rule r, Map scopeActions) {
      String ruleName = null;
      if (r != null) {
         ruleName = r.name;
      }

      Iterator i$ = scopeActions.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         String name = (String)entry.getKey();
         GrammarAST actionAST = (GrammarAST)entry.getValue();
         List chunks = this.translateAction(ruleName, actionAST);
         scopeActions.put(name, chunks);
      }

   }

   public void generateLocalFOLLOW(GrammarAST referencedElementNode, String referencedElementName, String enclosingRuleName, int elementIndex) {
      NFAState followingNFAState = referencedElementNode.followingNFAState;
      LookaheadSet follow = null;
      if (followingNFAState != null) {
         follow = this.grammar.FIRST(followingNFAState);
      }

      if (follow == null) {
         ErrorManager.internalError("no follow state or cannot compute follow");
         follow = new LookaheadSet();
      }

      if (follow.member(-1)) {
         follow.remove(-1);
      }

      Object tokenTypeList;
      long[] words;
      if (follow.tokenTypeSet == null) {
         words = new long[1];
         tokenTypeList = new ArrayList();
      } else {
         BitSet bits = BitSet.of((IntSet)follow.tokenTypeSet);
         words = bits.toPackedArray();
         tokenTypeList = follow.tokenTypeSet.toList();
      }

      String[] wordStrings = new String[words.length];

      for(int j = 0; j < words.length; ++j) {
         long w = words[j];
         wordStrings[j] = this.target.getTarget64BitStringFromValue(w);
      }

      this.recognizerST.addAggr("bitsets.{name,inName,bits,tokenTypes,tokenIndex}", referencedElementName, enclosingRuleName, wordStrings, tokenTypeList, Utils.integer(elementIndex));
      this.outputFileST.addAggr("bitsets.{name,inName,bits,tokenTypes,tokenIndex}", referencedElementName, enclosingRuleName, wordStrings, tokenTypeList, Utils.integer(elementIndex));
      this.headerFileST.addAggr("bitsets.{name,inName,bits,tokenTypes,tokenIndex}", referencedElementName, enclosingRuleName, wordStrings, tokenTypeList, Utils.integer(elementIndex));
   }

   public ST genLookaheadDecision(ST recognizerST, DFA dfa) {
      ST decisionST;
      if (dfa.canInlineDecision()) {
         decisionST = this.acyclicDFAGenerator.genFixedLookaheadDecision(this.getTemplates(), dfa);
      } else {
         dfa.createStateTables(this);
         this.outputFileST.add("cyclicDFAs", dfa);
         this.headerFileST.add("cyclicDFAs", dfa);
         decisionST = this.templates.getInstanceOf("dfaDecision");
         String description = dfa.getNFADecisionStartState().getDescription();
         description = this.target.getTargetStringLiteralFromString(description);
         if (description != null) {
            decisionST.add("description", description);
         }

         decisionST.add("decisionNumber", Utils.integer(dfa.getDecisionNumber()));
      }

      return decisionST;
   }

   public ST generateSpecialState(DFAState s) {
      ST stateST = this.templates.getInstanceOf("cyclicDFAState");
      stateST.add("needErrorClause", true);
      stateST.add("semPredState", s.isResolvedWithPredicates());
      stateST.add("stateNumber", s.stateNumber);
      stateST.add("decisionNumber", s.dfa.decisionNumber);
      boolean foundGatedPred = false;
      ST eotST = null;

      for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
         Transition edge = s.transition(i);
         ST edgeST;
         if (edge.label.getAtom() == -2) {
            edgeST = this.templates.getInstanceOf("eotDFAEdge");
            stateST.remove("needErrorClause");
            eotST = edgeST;
         } else {
            edgeST = this.templates.getInstanceOf("cyclicDFAEdge");
            ST exprST = this.genLabelExpr(this.templates, edge, 1);
            edgeST.add("labelExpr", exprST);
         }

         edgeST.add("edgeNumber", Utils.integer(i + 1));
         edgeST.add("targetStateNumber", Utils.integer(edge.target.stateNumber));
         if (!edge.label.isSemanticPredicate()) {
            DFAState t = (DFAState)edge.target;
            SemanticContext preds = t.getGatedPredicatesInNFAConfigurations();
            if (preds != null) {
               foundGatedPred = true;
               ST predST = preds.genExpr(this, this.getTemplates(), t.dfa);
               edgeST.add("predicates", predST.render());
            }
         }

         if (edge.label.getAtom() != -2) {
            stateST.add("edges", edgeST);
         }
      }

      if (foundGatedPred) {
         stateST.add("semPredState", foundGatedPred);
      }

      if (eotST != null) {
         stateST.add("edges", eotST);
      }

      return stateST;
   }

   protected ST genLabelExpr(STGroup templates, Transition edge, int k) {
      Label label = edge.label;
      if (label.isSemanticPredicate()) {
         return this.genSemanticPredicateExpr(templates, edge);
      } else if (label.isSet()) {
         return this.genSetExpr(templates, label.getSet(), k, true);
      } else {
         ST eST = templates.getInstanceOf("lookaheadTest");
         eST.add("atom", this.getTokenTypeAsTargetLabel(label.getAtom()));
         eST.add("atomAsInt", Utils.integer(label.getAtom()));
         eST.add("k", Utils.integer(k));
         return eST;
      }
   }

   protected ST genSemanticPredicateExpr(STGroup templates, Transition edge) {
      DFA dfa = ((DFAState)edge.target).dfa;
      Label label = edge.label;
      SemanticContext semCtx = label.getSemanticContext();
      return semCtx.genExpr(this, templates, dfa);
   }

   public ST genSetExpr(STGroup templates, IntSet set, int k, boolean partOfDFA) {
      if (!(set instanceof IntervalSet)) {
         throw new IllegalArgumentException("unable to generate expressions for non IntervalSet objects");
      } else {
         IntervalSet iset = (IntervalSet)set;
         if (iset.getIntervals() != null && !iset.getIntervals().isEmpty()) {
            String testSTName = "lookaheadTest";
            String testRangeSTName = "lookaheadRangeTest";
            String testSetSTName = "lookaheadSetTest";
            String varSTName = "lookaheadVarName";
            if (!partOfDFA) {
               testSTName = "isolatedLookaheadTest";
               testRangeSTName = "isolatedLookaheadRangeTest";
               testSetSTName = "isolatedLookaheadSetTest";
               varSTName = "isolatedLookaheadVarName";
            }

            ST setST = templates.getInstanceOf("setTest");
            int b;
            if (!templates.isDefined(testSetSTName)) {
               Iterator iter = iset.getIntervals().iterator();

               for(int rangeNumber = 1; iter.hasNext(); ++rangeNumber) {
                  Interval I = (Interval)iter.next();
                  int a = I.a;
                  b = I.b;
                  ST eST;
                  if (a == b) {
                     eST = templates.getInstanceOf(testSTName);
                     eST.add("atom", this.getTokenTypeAsTargetLabel(a));
                     eST.add("atomAsInt", Utils.integer(a));
                  } else {
                     eST = templates.getInstanceOf(testRangeSTName);
                     eST.add("lower", this.getTokenTypeAsTargetLabel(a));
                     eST.add("lowerAsInt", Utils.integer(a));
                     eST.add("upper", this.getTokenTypeAsTargetLabel(b));
                     eST.add("upperAsInt", Utils.integer(b));
                     eST.add("rangeNumber", Utils.integer(rangeNumber));
                  }

                  eST.add("k", Utils.integer(k));
                  setST.add("ranges", eST);
               }

               return setST;
            } else {
               ST sST = templates.getInstanceOf(testSetSTName);
               Iterator iter = iset.getIntervals().iterator();
               int rangeNumber = 1;

               while(true) {
                  while(iter.hasNext()) {
                     Interval I = (Interval)iter.next();
                     b = I.a;
                     int b = I.b;
                     if (b - b < 4) {
                        for(int i = b; i <= b; ++i) {
                           sST.add("values", this.getTokenTypeAsTargetLabel(i));
                           sST.add("valuesAsInt", Utils.integer(i));
                        }
                     } else {
                        ST eST = templates.getInstanceOf(testRangeSTName);
                        eST.add("lower", this.getTokenTypeAsTargetLabel(b));
                        eST.add("lowerAsInt", Utils.integer(b));
                        eST.add("upper", this.getTokenTypeAsTargetLabel(b));
                        eST.add("upperAsInt", Utils.integer(b));
                        eST.add("rangeNumber", Utils.integer(rangeNumber));
                        eST.add("k", Utils.integer(k));
                        setST.add("ranges", eST);
                        ++rangeNumber;
                     }
                  }

                  sST.add("k", Utils.integer(k));
                  setST.add("ranges", sST);
                  return setST;
               }
            }
         } else {
            ST emptyST = new ST(templates, "");
            emptyST.impl.name = "empty-set-expr";
            return emptyST;
         }
      }
   }

   protected void genTokenTypeConstants(ST code) {
      Iterator i$ = this.grammar.getTokenIDs().iterator();

      while(true) {
         String tokenID;
         int tokenType;
         do {
            if (!i$.hasNext()) {
               return;
            }

            tokenID = (String)i$.next();
            tokenType = this.grammar.getTokenType(tokenID);
         } while(tokenType != -1 && tokenType < 4);

         code.addAggr("tokens.{name,type}", tokenID, Utils.integer(tokenType));
      }
   }

   protected void genTokenTypeNames(ST code) {
      for(int t = 4; t <= this.grammar.getMaxTokenType(); ++t) {
         String tokenName = this.grammar.getTokenDisplayName(t);
         if (tokenName != null) {
            tokenName = this.target.getTargetStringLiteralFromString(tokenName, true);
            code.add("tokenNames", tokenName);
         }
      }

   }

   public String getTokenTypeAsTargetLabel(int ttype) {
      if (this.grammar.type == 1) {
         String name = this.grammar.getTokenDisplayName(ttype);
         return this.target.getTargetCharLiteralFromANTLRCharLiteral(this, name);
      } else {
         return this.target.getTokenTypeAsTargetLabel(this, ttype);
      }
   }

   protected ST genTokenVocabOutput() {
      ST vocabFileST = new ST("<tokens:{it|<it.name>=<it.type>\n}><literals:{it|<it.name>=<it.type>\n}>");
      vocabFileST.add("literals", (Object)null);
      vocabFileST.add("tokens", (Object)null);
      vocabFileST.impl.name = "vocab-file";
      Iterator i$ = this.grammar.getTokenIDs().iterator();

      String literal;
      int tokenType;
      while(i$.hasNext()) {
         literal = (String)i$.next();
         tokenType = this.grammar.getTokenType(literal);
         if (tokenType >= 4) {
            vocabFileST.addAggr("tokens.{name,type}", literal, Utils.integer(tokenType));
         }
      }

      i$ = this.grammar.getStringLiterals().iterator();

      while(i$.hasNext()) {
         literal = (String)i$.next();
         tokenType = this.grammar.getTokenType(literal);
         if (tokenType >= 4) {
            vocabFileST.addAggr("tokens.{name,type}", literal, Utils.integer(tokenType));
         }
      }

      return vocabFileST;
   }

   public List translateAction(String ruleName, GrammarAST actionTree) {
      if (actionTree.getType() == 12) {
         return this.translateArgAction(ruleName, actionTree);
      } else {
         ActionTranslator translator = new ActionTranslator(this, ruleName, actionTree);
         List chunks = translator.translateToChunks();
         chunks = this.target.postProcessAction(chunks, actionTree.token);
         return chunks;
      }
   }

   public List translateArgAction(String ruleName, GrammarAST actionTree) {
      String actionText = actionTree.token.getText();
      List args = getListOfArgumentsFromAction(actionText, 44);
      List translatedArgs = new ArrayList();
      Iterator i$ = args.iterator();

      while(i$.hasNext()) {
         String arg = (String)i$.next();
         if (arg != null) {
            Token actionToken = new CommonToken(4, arg);
            ActionTranslator translator = new ActionTranslator(this, ruleName, actionToken, actionTree.outerAltNum);
            List chunks = translator.translateToChunks();
            chunks = this.target.postProcessAction(chunks, actionToken);
            ST catST = new ST(this.templates, "<chunks>");
            catST.add("chunks", chunks);
            translatedArgs.add(catST);
         }
      }

      if (translatedArgs.isEmpty()) {
         return null;
      } else {
         return translatedArgs;
      }
   }

   public static List getListOfArgumentsFromAction(String actionText, int separatorChar) {
      List args = new ArrayList();
      getListOfArgumentsFromAction(actionText, 0, -1, separatorChar, args);
      return args;
   }

   public static int getListOfArgumentsFromAction(String actionText, int start, int targetChar, int separatorChar, List args) {
      if (actionText == null) {
         return -1;
      } else {
         actionText = actionText.replaceAll("//.*\n", "");
         int n = actionText.length();
         int p = start;
         int last = start;

         while(p < n && actionText.charAt(p) != targetChar) {
            int c = actionText.charAt(p);
            switch (c) {
               case '"':
                  ++p;

                  for(; p < n && actionText.charAt(p) != '"'; ++p) {
                     if (actionText.charAt(p) == '\\' && p + 1 < n && actionText.charAt(p + 1) == '"') {
                        ++p;
                     }
                  }

                  ++p;
                  break;
               case '\'':
                  ++p;

                  for(; p < n && actionText.charAt(p) != '\''; ++p) {
                     if (actionText.charAt(p) == '\\' && p + 1 < n && actionText.charAt(p + 1) == '\'') {
                        ++p;
                     }
                  }

                  ++p;
                  break;
               case '(':
                  p = getListOfArgumentsFromAction(actionText, p + 1, 41, separatorChar, args);
                  break;
               case '<':
                  if (actionText.indexOf(62, p + 1) >= p) {
                     p = getListOfArgumentsFromAction(actionText, p + 1, 62, separatorChar, args);
                  } else {
                     ++p;
                  }
                  break;
               case '[':
                  p = getListOfArgumentsFromAction(actionText, p + 1, 93, separatorChar, args);
                  break;
               case '{':
                  p = getListOfArgumentsFromAction(actionText, p + 1, 125, separatorChar, args);
                  break;
               default:
                  if (c == separatorChar && targetChar == -1) {
                     String arg = actionText.substring(last, p);
                     args.add(arg.trim());
                     last = p + 1;
                  }

                  ++p;
            }
         }

         if (targetChar == -1 && p <= n) {
            String arg = actionText.substring(last, p).trim();
            if (arg.length() > 0) {
               args.add(arg.trim());
            }
         }

         ++p;
         return p;
      }
   }

   public ST translateTemplateConstructor(String ruleName, int outerAltNum, Token actionToken, String templateActionText) {
      ANTLRLexer lexer = new ANTLRLexer(new ANTLRStringStream(templateActionText));
      lexer.setFileName(this.grammar.getFileName());
      ANTLRParser parser = ANTLRParser.createParser(new CommonTokenStream(lexer));
      parser.setFileName(this.grammar.getFileName());
      ANTLRParser.rewrite_template_return parseResult = null;

      try {
         parseResult = parser.rewrite_template();
      } catch (RecognitionException var13) {
         ErrorManager.grammarError(146, this.grammar, actionToken, templateActionText);
      } catch (Exception var14) {
         ErrorManager.internalError("can't parse template action", var14);
      }

      GrammarAST rewriteTree = parseResult.getTree();
      CodeGenTreeWalker gen = new CodeGenTreeWalker(new CommonTreeNodeStream(rewriteTree));
      gen.init(this.grammar);
      gen.setCurrentRuleName(ruleName);
      gen.setOuterAltNum(outerAltNum);
      ST st = null;

      try {
         st = gen.rewrite_template();
      } catch (RecognitionException var12) {
         ErrorManager.error(15, (Throwable)var12);
      }

      return st;
   }

   public void issueInvalidScopeError(String x, String y, Rule enclosingRule, Token actionToken, int outerAltNum) {
      Rule r = this.grammar.getRule(x);
      AttributeScope scope = this.grammar.getGlobalScope(x);
      if (scope == null && r != null) {
         scope = r.ruleScope;
      }

      if (scope == null) {
         ErrorManager.grammarError(140, this.grammar, actionToken, x);
      } else if (scope.getAttribute(y) == null) {
         ErrorManager.grammarError(141, this.grammar, actionToken, x, y);
      }

   }

   public void issueInvalidAttributeError(String x, String y, Rule enclosingRule, Token actionToken, int outerAltNum) {
      if (enclosingRule == null) {
         ErrorManager.grammarError(111, this.grammar, actionToken, x, y);
      } else {
         Grammar.LabelElementPair label = enclosingRule.getRuleLabel(x);
         if (label != null || enclosingRule.getRuleRefsInAlt(x, outerAltNum) != null) {
            String refdRuleName = x;
            if (label != null) {
               refdRuleName = enclosingRule.getRuleLabel(x).referencedRuleName;
            }

            Rule refdRule = this.grammar.getRule(refdRuleName);
            AttributeScope scope = refdRule.getAttributeScope(y);
            if (scope == null) {
               ErrorManager.grammarError(116, this.grammar, actionToken, refdRuleName, y);
            } else if (scope.isParameterScope) {
               ErrorManager.grammarError(115, this.grammar, actionToken, refdRuleName, y);
            } else if (scope.isDynamicRuleScope) {
               ErrorManager.grammarError(112, this.grammar, actionToken, refdRuleName, y);
            }
         }

      }
   }

   public void issueInvalidAttributeError(String x, Rule enclosingRule, Token actionToken, int outerAltNum) {
      if (enclosingRule == null) {
         ErrorManager.grammarError(111, this.grammar, actionToken, x);
      } else {
         Grammar.LabelElementPair label = enclosingRule.getRuleLabel(x);
         AttributeScope scope = enclosingRule.getAttributeScope(x);
         if (label == null && enclosingRule.getRuleRefsInAlt(x, outerAltNum) == null && !enclosingRule.name.equals(x)) {
            if (scope != null && scope.isDynamicRuleScope) {
               ErrorManager.grammarError(142, this.grammar, actionToken, x);
            } else {
               ErrorManager.grammarError(114, this.grammar, actionToken, x);
            }
         } else {
            ErrorManager.grammarError(117, this.grammar, actionToken, x);
         }

      }
   }

   public STGroup getTemplates() {
      return this.templates;
   }

   public STGroup getBaseTemplates() {
      return this.baseTemplates;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public void setTrace(boolean trace) {
      this.trace = trace;
   }

   public void setProfile(boolean profile) {
      this.profile = profile;
      if (profile) {
         this.setDebug(true);
      }

   }

   public ST getRecognizerST() {
      return this.outputFileST;
   }

   public String getRecognizerFileName(String name, int type) {
      ST extST = this.templates.getInstanceOf("codeFileExtension");
      String recognizerName = this.grammar.getRecognizerName();
      return recognizerName + extST.render();
   }

   public String getVocabFileName() {
      return this.grammar.isBuiltFromString() ? null : this.grammar.name + ".tokens";
   }

   public void write(ST code, String fileName) throws IOException {
      Writer w = this.tool.getOutputFile(this.grammar, fileName);
      STWriter wr = new AutoIndentWriter(w);
      wr.setLineWidth(this.lineWidth);
      code.write(wr);
      w.close();
   }

   protected boolean canGenerateSwitch(DFAState s) {
      if (!this.GENERATE_SWITCHES_WHEN_POSSIBLE) {
         return false;
      } else {
         int size = 0;

         for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
            Transition edge = s.transition(i);
            if (edge.label.isSemanticPredicate()) {
               return false;
            }

            if (edge.label.getAtom() == -2) {
               int EOTPredicts = ((DFAState)edge.target).getUniquelyPredictedAlt();
               if (EOTPredicts == -1) {
                  return false;
               }
            }

            if (((DFAState)edge.target).getGatedPredicatesInNFAConfigurations() != null) {
               return false;
            }

            size += edge.label.getSet().size();
         }

         if (s.getNumberOfTransitions() >= MIN_SWITCH_ALTS && size <= MAX_SWITCH_CASE_LABELS) {
            return true;
         } else {
            return false;
         }
      }
   }

   public String createUniqueLabel(String name) {
      return name + this.uniqueLabelNumber++;
   }
}
