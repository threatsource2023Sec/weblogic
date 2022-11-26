package org.antlr.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.antlr.codegen.CodeGenerator;
import org.antlr.grammar.v3.ANTLRTreePrinter;
import org.antlr.grammar.v3.LeftRecursiveRuleWalker;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeNodeStream;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class LeftRecursiveRuleAnalyzer extends LeftRecursiveRuleWalker {
   public Grammar g;
   public CodeGenerator generator;
   public String ruleName;
   Map tokenToPrec = new HashMap();
   public LinkedHashMap binaryAlts = new LinkedHashMap();
   public LinkedHashMap ternaryAlts = new LinkedHashMap();
   public LinkedHashMap suffixAlts = new LinkedHashMap();
   public List prefixAlts = new ArrayList();
   public List otherAlts = new ArrayList();
   public GrammarAST retvals;
   public STGroup recRuleTemplates;
   public String language;
   public Map altAssociativity = new HashMap();

   public LeftRecursiveRuleAnalyzer(TreeNodeStream input, Grammar g, String ruleName) {
      super(input);
      this.g = g;
      this.ruleName = ruleName;
      this.language = (String)g.getOption("language");
      this.generator = new CodeGenerator(g.tool, g, this.language);
      this.generator.loadTemplates(this.language);
      this.loadPrecRuleTemplates();
   }

   public void loadPrecRuleTemplates() {
      this.recRuleTemplates = new ToolSTGroupFile(CodeGenerator.classpathTemplateRootDirectoryName + "/LeftRecursiveRules.stg");
      if (!this.recRuleTemplates.isDefined("recRuleName")) {
         ErrorManager.error(20, (Object)"PrecRules");
      }
   }

   public void setReturnValues(GrammarAST t) {
      System.out.println(t);
      this.retvals = t;
   }

   public void setTokenPrec(GrammarAST t, int alt) {
      int ttype = this.g.getTokenType(t.getText());
      this.tokenToPrec.put(ttype, alt);
      ASSOC assoc = LeftRecursiveRuleAnalyzer.ASSOC.left;
      if (t.terminalOptions != null) {
         String a = (String)t.terminalOptions.get("assoc");
         if (a != null) {
            if (a.equals(LeftRecursiveRuleAnalyzer.ASSOC.right.toString())) {
               assoc = LeftRecursiveRuleAnalyzer.ASSOC.right;
            } else {
               ErrorManager.error(168, "assoc", (Object)assoc);
            }
         }
      }

      if (this.altAssociativity.get(alt) != null && this.altAssociativity.get(alt) != assoc) {
         ErrorManager.error(169, (Object)alt);
      }

      this.altAssociativity.put(alt, assoc);
   }

   public void binaryAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
      altTree = GrammarAST.dupTree(altTree);
      rewriteTree = GrammarAST.dupTree(rewriteTree);
      this.stripSynPred(altTree);
      this.stripLeftRecursion(altTree);
      int nextPrec = this.nextPrecedence(alt);
      ST refST = this.recRuleTemplates.getInstanceOf("recRuleRef");
      refST.add("ruleName", this.ruleName);
      refST.add("arg", nextPrec);
      altTree = this.replaceRuleRefs(altTree, refST.render());
      String altText = this.text(altTree);
      altText = altText.trim();
      altText = altText + "{}";
      ST nameST = this.recRuleTemplates.getInstanceOf("recRuleName");
      nameST.add("ruleName", this.ruleName);
      rewriteTree = this.replaceRuleRefs(rewriteTree, "$" + nameST.render());
      String rewriteText = this.text(rewriteTree);
      this.binaryAlts.put(alt, altText + (rewriteText != null ? " " + rewriteText : ""));
   }

   public void ternaryAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
      altTree = GrammarAST.dupTree(altTree);
      rewriteTree = GrammarAST.dupTree(rewriteTree);
      this.stripSynPred(altTree);
      this.stripLeftRecursion(altTree);
      int nextPrec = this.nextPrecedence(alt);
      ST refST = this.recRuleTemplates.getInstanceOf("recRuleRef");
      refST.add("ruleName", this.ruleName);
      refST.add("arg", nextPrec);
      altTree = this.replaceLastRuleRef(altTree, refST.render());
      String altText = this.text(altTree);
      altText = altText.trim();
      altText = altText + "{}";
      ST nameST = this.recRuleTemplates.getInstanceOf("recRuleName");
      nameST.add("ruleName", this.ruleName);
      rewriteTree = this.replaceRuleRefs(rewriteTree, "$" + nameST.render());
      String rewriteText = this.text(rewriteTree);
      this.ternaryAlts.put(alt, altText + (rewriteText != null ? " " + rewriteText : ""));
   }

   public void prefixAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
      altTree = GrammarAST.dupTree(altTree);
      rewriteTree = GrammarAST.dupTree(rewriteTree);
      this.stripSynPred(altTree);
      int nextPrec = this.precedence(alt);
      ST refST = this.recRuleTemplates.getInstanceOf("recRuleRef");
      refST.add("ruleName", this.ruleName);
      refST.add("arg", nextPrec);
      altTree = this.replaceRuleRefs(altTree, refST.render());
      String altText = this.text(altTree);
      altText = altText.trim();
      altText = altText + "{}";
      ST nameST = this.recRuleTemplates.getInstanceOf("recRuleName");
      nameST.add("ruleName", this.ruleName);
      rewriteTree = this.replaceRuleRefs(rewriteTree, nameST.render());
      String rewriteText = this.text(rewriteTree);
      this.prefixAlts.add(altText + (rewriteText != null ? " " + rewriteText : ""));
   }

   public void suffixAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
      altTree = GrammarAST.dupTree(altTree);
      rewriteTree = GrammarAST.dupTree(rewriteTree);
      this.stripSynPred(altTree);
      this.stripLeftRecursion(altTree);
      ST nameST = this.recRuleTemplates.getInstanceOf("recRuleName");
      nameST.add("ruleName", this.ruleName);
      rewriteTree = this.replaceRuleRefs(rewriteTree, "$" + nameST.render());
      String rewriteText = this.text(rewriteTree);
      String altText = this.text(altTree);
      altText = altText.trim();
      this.suffixAlts.put(alt, altText + (rewriteText != null ? " " + rewriteText : ""));
   }

   public void otherAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
      altTree = GrammarAST.dupTree(altTree);
      rewriteTree = GrammarAST.dupTree(rewriteTree);
      this.stripSynPred(altTree);
      this.stripLeftRecursion(altTree);
      String altText = this.text(altTree);
      String rewriteText = this.text(rewriteTree);
      this.otherAlts.add(altText + (rewriteText != null ? " " + rewriteText : ""));
   }

   public String getArtificialPrecStartRule() {
      ST ruleST = this.recRuleTemplates.getInstanceOf("recRuleStart");
      ruleST.add("ruleName", this.ruleName);
      ruleST.add("minPrec", 0);
      ruleST.add("userRetvals", this.retvals);
      this.fillRetValAssignments(ruleST, "recRuleName");
      System.out.println("start: " + ruleST);
      return ruleST.render();
   }

   public String getArtificialOpPrecRule() {
      ST ruleST = this.recRuleTemplates.getInstanceOf("recRule");
      ruleST.add("ruleName", this.ruleName);
      ruleST.add("buildAST", this.grammar.buildAST());
      ST argDefST = this.generator.getTemplates().getInstanceOf("recRuleDefArg");
      ruleST.add("precArgDef", argDefST);
      ST ruleArgST = this.generator.getTemplates().getInstanceOf("recRuleArg");
      ruleST.add("argName", ruleArgST);
      ST setResultST = this.generator.getTemplates().getInstanceOf("recRuleSetResultAction");
      ruleST.add("setResultAction", setResultST);
      ruleST.add("userRetvals", this.retvals);
      this.fillRetValAssignments(ruleST, "recPrimaryName");
      LinkedHashMap opPrecRuleAlts = new LinkedHashMap();
      opPrecRuleAlts.putAll(this.binaryAlts);
      opPrecRuleAlts.putAll(this.ternaryAlts);
      opPrecRuleAlts.putAll(this.suffixAlts);
      Iterator i$ = opPrecRuleAlts.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         int alt = (Integer)entry.getKey();
         String altText = (String)entry.getValue();
         ST altST = this.recRuleTemplates.getInstanceOf("recRuleAlt");
         ST predST = this.generator.getTemplates().getInstanceOf("recRuleAltPredicate");
         predST.add("opPrec", this.precedence(alt));
         predST.add("ruleName", this.ruleName);
         altST.add("pred", predST);
         altST.add("alt", altText);
         ruleST.add("alts", altST);
      }

      System.out.println(ruleST);
      return ruleST.render();
   }

   public String getArtificialPrimaryRule() {
      ST ruleST = this.recRuleTemplates.getInstanceOf("recPrimaryRule");
      ruleST.add("ruleName", this.ruleName);
      ruleST.add("alts", this.prefixAlts);
      ruleST.add("alts", this.otherAlts);
      ruleST.add("userRetvals", this.retvals);
      System.out.println(ruleST);
      return ruleST.render();
   }

   public GrammarAST replaceRuleRefs(GrammarAST t, String name) {
      if (t == null) {
         return null;
      } else {
         Iterator i$ = t.findAllType(80).iterator();

         while(i$.hasNext()) {
            GrammarAST rref = (GrammarAST)i$.next();
            if (rref.getText().equals(this.ruleName)) {
               rref.setText(name);
            }
         }

         return t;
      }
   }

   public static boolean hasImmediateRecursiveRuleRefs(GrammarAST t, String ruleName) {
      if (t == null) {
         return false;
      } else {
         Iterator i$ = t.findAllType(80).iterator();

         GrammarAST rref;
         do {
            if (!i$.hasNext()) {
               return false;
            }

            rref = (GrammarAST)i$.next();
         } while(!rref.getText().equals(ruleName));

         return true;
      }
   }

   public GrammarAST replaceLastRuleRef(GrammarAST t, String name) {
      if (t == null) {
         return null;
      } else {
         GrammarAST last = null;

         GrammarAST rref;
         for(Iterator i$ = t.findAllType(80).iterator(); i$.hasNext(); last = rref) {
            rref = (GrammarAST)i$.next();
         }

         if (last != null && last.getText().equals(this.ruleName)) {
            last.setText(name);
         }

         return t;
      }
   }

   public void stripSynPred(GrammarAST altAST) {
      GrammarAST t = (GrammarAST)altAST.getChild(0);
      if (t.getType() == 14 || t.getType() == 89 || t.getType() == 90) {
         altAST.deleteChild(0);
      }

   }

   public void stripLeftRecursion(GrammarAST altAST) {
      GrammarAST rref = (GrammarAST)altAST.getChild(0);
      if (rref.getType() == 80 && rref.getText().equals(this.ruleName)) {
         altAST.deleteChild(0);
         GrammarAST newFirstChild = (GrammarAST)altAST.getChild(0);
         altAST.setTokenStartIndex(newFirstChild.getTokenStartIndex());
      }

   }

   public String text(GrammarAST t) {
      if (t == null) {
         return null;
      } else {
         try {
            return (new ANTLRTreePrinter(new CommonTreeNodeStream(t))).toString(this.grammar, true);
         } catch (Exception var3) {
            ErrorManager.error(15, (Throwable)var3);
            return null;
         }
      }
   }

   public int precedence(int alt) {
      return this.numAlts - alt + 1;
   }

   public int nextPrecedence(int alt) {
      int p = this.precedence(alt);
      if (this.altAssociativity.get(alt) == LeftRecursiveRuleAnalyzer.ASSOC.left) {
         ++p;
      }

      return p;
   }

   public void fillRetValAssignments(ST ruleST, String srcName) {
      if (this.retvals != null) {
         Iterator i$ = this.getNamesFromArgAction(this.retvals.token).iterator();

         while(i$.hasNext()) {
            String name = (String)i$.next();
            ST setRetValST = this.generator.getTemplates().getInstanceOf("recRuleSetReturnAction");
            ST ruleNameST = this.recRuleTemplates.getInstanceOf(srcName);
            ruleNameST.add("ruleName", this.ruleName);
            setRetValST.add("src", ruleNameST);
            setRetValST.add("name", name);
            ruleST.add("userRetvalAssignments", setRetValST);
         }

      }
   }

   public Collection getNamesFromArgAction(Token t) {
      AttributeScope returnScope = this.grammar.createReturnScope("", t);
      returnScope.addAttributes(t.getText(), 44);
      return returnScope.attributes.keySet();
   }

   public String toString() {
      return "PrecRuleOperatorCollector{binaryAlts=" + this.binaryAlts + ", rec=" + this.tokenToPrec + ", ternaryAlts=" + this.ternaryAlts + ", suffixAlts=" + this.suffixAlts + ", prefixAlts=" + this.prefixAlts + ", otherAlts=" + this.otherAlts + '}';
   }

   public static enum ASSOC {
      left,
      right;
   }
}
