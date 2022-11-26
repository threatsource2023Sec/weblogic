package org.antlr.gunit.swingui;

import java.awt.Color;
import java.awt.Component;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import org.antlr.gunit.swingui.model.Rule;
import org.antlr.gunit.swingui.model.TestCase;
import org.antlr.gunit.swingui.model.TestSuite;

public class RunnerController implements IController {
   private RunnerView view = new RunnerView();

   public Object getModel() {
      return null;
   }

   public Component getView() {
      return this.view;
   }

   public void update() {
      this.view.initComponents();
   }

   public void OnShowSuiteResult(TestSuite suite) {
      this.update();
      this.view.tree.setModel(new RunnerTreeModel(suite));
      this.view.tree.setCellRenderer(new RunnerTreeRenderer());
   }

   public void OnShowRuleResult(Rule rule) {
      this.update();
   }

   private class RunnerTreeRenderer implements TreeCellRenderer {
      private RunnerTreeRenderer() {
      }

      public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
         JLabel label = new JLabel();
         if (value instanceof TestSuiteTreeNode) {
            label.setText(value.toString());
            label.setIcon(ImageFactory.getSingleton().TESTSUITE);
         } else if (value instanceof TestGroupTreeNode) {
            TestGroupTreeNode node = (TestGroupTreeNode)value;
            label.setText(value.toString());
            label.setIcon(node.hasFail ? ImageFactory.getSingleton().TESTGROUPX : ImageFactory.getSingleton().TESTGROUP);
         } else {
            if (!(value instanceof TestCaseTreeNode)) {
               throw new IllegalArgumentException("Invalide tree node type + " + value.getClass().getName());
            }

            TestCaseTreeNode nodex = (TestCaseTreeNode)value;
            label.setIcon(nodex.data.isPass() ? ImageFactory.getSingleton().RUN_PASS : ImageFactory.getSingleton().RUN_FAIL);
            label.setText(value.toString());
         }

         return label;
      }

      // $FF: synthetic method
      RunnerTreeRenderer(Object x1) {
         this();
      }
   }

   private class RunnerTreeModel extends DefaultTreeModel {
      public RunnerTreeModel(TestSuite testSuite) {
         super(RunnerController.this.new TestSuiteTreeNode(testSuite));
      }
   }

   private class TestCaseTreeNode extends DefaultMutableTreeNode {
      private TestCase data;

      private TestCaseTreeNode(TestCase tc) {
         super(tc.toString());
         this.data = tc;
      }

      // $FF: synthetic method
      TestCaseTreeNode(TestCase x1, Object x2) {
         this(x1);
      }
   }

   private class TestGroupTreeNode extends DefaultMutableTreeNode {
      private Rule data;
      private boolean hasFail;

      private TestGroupTreeNode(Rule rule) {
         super(rule.getName());
         this.hasFail = false;
         Iterator i$ = rule.getTestCases().iterator();

         while(i$.hasNext()) {
            TestCase tc = (TestCase)i$.next();
            this.add(RunnerController.this.new TestCaseTreeNode(tc));
         }

         this.data = rule;
      }

      public String toString() {
         int iPass = 0;
         int iFail = 0;
         Iterator i$ = this.data.getTestCases().iterator();

         while(i$.hasNext()) {
            TestCase tc = (TestCase)i$.next();
            if (tc.isPass()) {
               ++iPass;
            } else {
               ++iFail;
            }
         }

         this.hasFail = iFail > 0;
         return String.format("%s (pass %d, fail %d)", this.data.getName(), iPass, iFail);
      }

      // $FF: synthetic method
      TestGroupTreeNode(Rule x1, Object x2) {
         this(x1);
      }
   }

   private class TestSuiteTreeNode extends DefaultMutableTreeNode {
      private TestSuite data;

      public TestSuiteTreeNode(TestSuite suite) {
         super(suite.getGrammarName());

         for(int i = 0; i < suite.getRuleCount(); ++i) {
            Rule rule = suite.getRule(i);
            if (rule.getNotEmpty()) {
               this.add(RunnerController.this.new TestGroupTreeNode(rule));
            }
         }

         this.data = suite;
      }

      public String toString() {
         return String.format("%s (%d test groups)", this.data.getGrammarName(), this.getChildCount());
      }
   }

   public class RunnerView extends JPanel {
      private JTextArea textArea = new JTextArea();
      private JTree tree = new JTree();
      private JScrollPane scroll;

      public RunnerView() {
         this.scroll = new JScrollPane(this.tree, 22, 30);
      }

      public void initComponents() {
         this.tree.setOpaque(false);
         this.scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
         this.scroll.setOpaque(false);
         this.setLayout(new BoxLayout(this, 1));
         this.add(this.scroll);
         this.setBorder(BorderFactory.createEmptyBorder());
         this.setOpaque(false);
      }
   }
}
