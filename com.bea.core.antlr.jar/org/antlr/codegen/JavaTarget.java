package org.antlr.codegen;

import java.util.Iterator;
import java.util.Set;
import org.antlr.Tool;
import org.antlr.tool.Grammar;
import org.antlr.tool.Rule;
import org.stringtemplate.v4.ST;

public class JavaTarget extends Target {
   public boolean useBaseTemplatesForSynPredFragments() {
      return false;
   }

   protected ST chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, ST recognizerST, ST cyclicDFAST) {
      return recognizerST;
   }

   protected void performGrammarAnalysis(CodeGenerator generator, Grammar grammar) {
      super.performGrammarAnalysis(generator, grammar);
      Iterator i$ = grammar.getRules().iterator();

      while(i$.hasNext()) {
         Rule rule = (Rule)i$.next();
         rule.throwsSpec.add("RecognitionException");
      }

      Set delegatedRules = grammar.getDelegatedRules();
      if (delegatedRules != null) {
         Iterator i$ = delegatedRules.iterator();

         while(i$.hasNext()) {
            Rule rule = (Rule)i$.next();
            rule.throwsSpec.add("RecognitionException");
         }
      }

   }
}
