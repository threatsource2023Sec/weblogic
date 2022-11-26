package org.antlr.gunit.swingui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

public class Rule extends DefaultListModel {
   private String name;

   public Rule(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public boolean getNotEmpty() {
      return !this.isEmpty();
   }

   public String toString() {
      return this.name;
   }

   public void addTestCase(TestCase newItem) {
      this.addElement(newItem);
   }

   public List getTestCases() {
      List result = new ArrayList();

      for(int i = 0; i < this.size(); ++i) {
         result.add((TestCase)this.getElementAt(i));
      }

      return result;
   }
}
