package org.antlr.gunit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrammarInfo {
   private String grammarName;
   private String treeGrammarName = null;
   private String grammarPackage = null;
   private String testPackage = null;
   private String adaptor = null;
   private List ruleTestSuites = new ArrayList();
   private StringBuffer unitTestResult = new StringBuffer();

   public String getGrammarName() {
      return this.grammarName;
   }

   public void setGrammarName(String grammarName) {
      this.grammarName = grammarName;
   }

   public String getTreeGrammarName() {
      return this.treeGrammarName;
   }

   public void setTreeGrammarName(String treeGrammarName) {
      this.treeGrammarName = treeGrammarName;
   }

   public String getTestPackage() {
      return this.testPackage;
   }

   public void setTestPackage(String testPackage) {
      this.testPackage = testPackage;
   }

   public String getGrammarPackage() {
      return this.grammarPackage;
   }

   public void setGrammarPackage(String grammarPackage) {
      this.grammarPackage = grammarPackage;
   }

   public String getAdaptor() {
      return this.adaptor;
   }

   public void setAdaptor(String adaptor) {
      this.adaptor = adaptor;
   }

   public List getRuleTestSuites() {
      return Collections.unmodifiableList(this.ruleTestSuites);
   }

   public void addRuleTestSuite(gUnitTestSuite testSuite) {
      this.ruleTestSuites.add(testSuite);
   }

   public void appendUnitTestResult(String result) {
      this.unitTestResult.append(result);
   }

   public String getUnitTestResult() {
      return this.unitTestResult.toString();
   }

   public void setUnitTestResult(StringBuffer unitTestResult) {
      this.unitTestResult = unitTestResult;
   }
}
