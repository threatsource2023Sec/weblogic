package org.antlr.gunit.swingui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.runtime.CommonTokenStream;

public class TestSuite {
   protected List rules;
   protected String grammarName;
   protected CommonTokenStream tokens;
   protected File testSuiteFile;

   protected TestSuite(String gname, File testFile) {
      this.grammarName = gname;
      this.testSuiteFile = testFile;
      this.rules = new ArrayList();
   }

   public File getTestSuiteFile() {
      return this.testSuiteFile;
   }

   public void addRule(Rule currentRule) {
      if (currentRule == null) {
         throw new IllegalArgumentException("Null rule");
      } else {
         this.rules.add(currentRule);
      }
   }

   public boolean hasRule(Rule rule) {
      Iterator i$ = this.rules.iterator();

      Rule r;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         r = (Rule)i$.next();
      } while(!r.getName().equals(rule.getName()));

      return true;
   }

   public int getRuleCount() {
      return this.rules.size();
   }

   public void setRules(List newRules) {
      this.rules.clear();
      this.rules.addAll(newRules);
   }

   public void setGrammarName(String name) {
      this.grammarName = name;
   }

   public String getGrammarName() {
      return this.grammarName;
   }

   public Rule getRule(int index) {
      return (Rule)this.rules.get(index);
   }

   public CommonTokenStream getTokens() {
      return this.tokens;
   }

   public void setTokens(CommonTokenStream ts) {
      this.tokens = ts;
   }

   public Rule getRule(String name) {
      Iterator i$ = this.rules.iterator();

      Rule rule;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         rule = (Rule)i$.next();
      } while(!rule.getName().equals(name));

      return rule;
   }

   public List getRulesForStringTemplate() {
      return this.rules;
   }
}
