package org.antlr.gunit;

import java.util.LinkedHashMap;
import java.util.Map;

public class gUnitTestSuite {
   protected String rule = null;
   protected String lexicalRule = null;
   protected String treeRule = null;
   protected boolean isLexicalRule = false;
   protected Map testSuites = new LinkedHashMap();

   public gUnitTestSuite() {
   }

   public gUnitTestSuite(String rule) {
      this.rule = rule;
   }

   public gUnitTestSuite(String treeRule, String rule) {
      this.rule = rule;
      this.treeRule = treeRule;
   }

   public void setRuleName(String ruleName) {
      this.rule = ruleName;
   }

   public void setLexicalRuleName(String lexicalRule) {
      this.lexicalRule = lexicalRule;
      this.isLexicalRule = true;
   }

   public void setTreeRuleName(String treeRuleName) {
      this.treeRule = treeRuleName;
   }

   public String getRuleName() {
      return this.rule;
   }

   public String getLexicalRuleName() {
      return this.lexicalRule;
   }

   public String getTreeRuleName() {
      return this.treeRule;
   }

   public boolean isLexicalRule() {
      return this.isLexicalRule;
   }

   public void addTestCase(gUnitTestInput input, AbstractTest expect) {
      if (input != null && expect != null) {
         expect.setTestedRuleName(this.rule == null ? this.lexicalRule : this.rule);
         expect.setTestCaseIndex(this.testSuites.size());
         this.testSuites.put(input, expect);
      }

   }
}
