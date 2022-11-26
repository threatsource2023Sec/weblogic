package org.antlr.gunit.swingui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import org.antlr.gunit.swingui.model.Rule;
import org.antlr.gunit.swingui.model.TestSuite;
import org.antlr.gunit.swingui.model.TestSuiteFactory;
import org.antlr.gunit.swingui.runner.gUnitAdapter;

public class WorkSpaceController implements IController {
   private TestSuite currentTestSuite;
   private String testSuiteFileName = null;
   private final WorkSpaceView view = new WorkSpaceView();
   private final RunnerController runner = new RunnerController();

   public WorkSpaceController() {
      this.view.resultPane = (JPanel)this.runner.getView();
      this.view.initComponents();
      this.initEventHandlers();
      this.initToolbar();
   }

   public void show() {
      this.view.setTitle("gUnitEditor");
      this.view.setVisible(true);
      this.view.pack();
   }

   public Component getEmbeddedView() {
      return this.view.paneEditor.getView();
   }

   private void initEventHandlers() {
      this.view.tabEditors.addChangeListener(new TabChangeListener());
      this.view.listRules.setListSelectionListener(new RuleListSelectionListener());
      this.view.paneEditor.onTestCaseNumberChange = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            WorkSpaceController.this.view.listRules.getView().updateUI();
         }
      };
   }

   private void OnCreateTest() {
      JFileChooser jfc = new JFileChooser();
      jfc.setDialogTitle("Create test suite from grammar");
      jfc.setDialogType(0);
      jfc.setFileFilter(new FileFilter() {
         public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".g");
         }

         public String getDescription() {
            return "ANTLR grammar file (*.g)";
         }
      });
      if (jfc.showOpenDialog(this.view) == 0) {
         this.view.paneStatus.setProgressIndetermined(true);
         File grammarFile = jfc.getSelectedFile();
         this.currentTestSuite = TestSuiteFactory.createTestSuite(grammarFile);
         this.view.listRules.initialize(this.currentTestSuite);
         this.view.tabEditors.setSelectedIndex(0);
         this.view.paneStatus.setText("Grammar: " + this.currentTestSuite.getGrammarName());
         this.view.paneStatus.setProgressIndetermined(false);
         this.testSuiteFileName = null;
      }
   }

   private void OnSaveTest() {
      TestSuiteFactory.saveTestSuite(this.currentTestSuite);
      JOptionPane.showMessageDialog(this.view, "Testsuite saved to:\n" + this.currentTestSuite.getTestSuiteFile().getAbsolutePath());
   }

   private void OnOpenTest() {
      JFileChooser jfc = new JFileChooser();
      jfc.setDialogTitle("Open existing gUnit test suite");
      jfc.setDialogType(0);
      jfc.setFileFilter(new FileFilter() {
         public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".gunit");
         }

         public String getDescription() {
            return "ANTLR unit test file (*.gunit)";
         }
      });
      if (jfc.showOpenDialog(this.view) == 0) {
         File testSuiteFile = jfc.getSelectedFile();

         try {
            this.testSuiteFileName = testSuiteFile.getCanonicalPath();
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }

         this.view.paneStatus.setProgressIndetermined(true);
         this.currentTestSuite = TestSuiteFactory.loadTestSuite(testSuiteFile);
         this.view.listRules.initialize(this.currentTestSuite);
         this.view.paneStatus.setText(this.currentTestSuite.getGrammarName());
         this.view.tabEditors.setSelectedIndex(0);
         this.view.paneStatus.setProgressIndetermined(false);
      }
   }

   private void OnSelectRule(Rule rule) {
      if (rule == null) {
         throw new IllegalArgumentException("Null");
      } else {
         this.view.paneEditor.OnLoadRule(rule);
         this.view.paneStatus.setRule(rule.getName());
         this.runner.OnShowRuleResult(rule);
      }
   }

   private void OnSelectTextPane() {
      Thread worker = new Thread() {
         public void run() {
            WorkSpaceController.this.view.paneStatus.setProgressIndetermined(true);
            WorkSpaceController.this.view.txtEditor.setText(TestSuiteFactory.getScript(WorkSpaceController.this.currentTestSuite));
            WorkSpaceController.this.view.paneStatus.setProgressIndetermined(false);
         }
      };
      worker.start();
   }

   private void OnRunTest() {
      TestSuiteFactory.saveTestSuite(this.currentTestSuite);

      try {
         gUnitAdapter adapter = new gUnitAdapter(this.currentTestSuite);
         if (this.currentTestSuite == null) {
            return;
         }

         adapter.run();
         this.runner.OnShowSuiteResult(this.currentTestSuite);
         this.view.tabEditors.addTab("Test Result", ImageFactory.getSingleton().FILE16, this.runner.getView());
         this.view.tabEditors.setSelectedComponent(this.runner.getView());
      } catch (Exception var2) {
         JOptionPane.showMessageDialog(this.view, "Fail to run test:\n" + var2.getMessage(), "Error", 0);
      }

   }

   private void initToolbar() {
      this.view.toolbar.add(new JButton(new CreateAction()));
      this.view.toolbar.add(new JButton(new OpenAction()));
      this.view.toolbar.add(new JButton(new SaveAction()));
      this.view.toolbar.add(new JButton(new RunAction()));
   }

   public Object getModel() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Component getView() {
      return this.view;
   }

   private class RunAction extends AbstractAction {
      public RunAction() {
         super("Run", ImageFactory.getSingleton().NEXT);
         this.putValue("ShortDescription", "Run the current test suite");
         this.putValue("AcceleratorKey", KeyStroke.getKeyStroke(82, 2));
      }

      public void actionPerformed(ActionEvent e) {
         WorkSpaceController.this.OnRunTest();
      }
   }

   private class OpenAction extends AbstractAction {
      public OpenAction() {
         super("Open", ImageFactory.getSingleton().OPEN);
         this.putValue("ShortDescription", "Open an existing test suite");
         this.putValue("AcceleratorKey", KeyStroke.getKeyStroke(79, 2));
      }

      public void actionPerformed(ActionEvent e) {
         WorkSpaceController.this.OnOpenTest();
      }
   }

   private class SaveAction extends AbstractAction {
      public SaveAction() {
         super("Save", ImageFactory.getSingleton().SAVE);
         this.putValue("ShortDescription", "Save the test suite");
      }

      public void actionPerformed(ActionEvent e) {
         WorkSpaceController.this.OnSaveTest();
      }
   }

   private class CreateAction extends AbstractAction {
      public CreateAction() {
         super("Create", ImageFactory.getSingleton().ADDFILE);
         this.putValue("ShortDescription", "Create a test suite from an ANTLR grammar");
      }

      public void actionPerformed(ActionEvent e) {
         WorkSpaceController.this.OnCreateTest();
      }
   }

   public class TabChangeListener implements ChangeListener {
      public void stateChanged(ChangeEvent evt) {
         if (WorkSpaceController.this.view.tabEditors.getSelectedIndex() == 1) {
            WorkSpaceController.this.OnSelectTextPane();
         }

      }
   }

   private class RuleListSelectionListener implements ListSelectionListener {
      private RuleListSelectionListener() {
      }

      public void valueChanged(ListSelectionEvent event) {
         if (!event.getValueIsAdjusting()) {
            JList list = (JList)event.getSource();
            Rule rule = (Rule)list.getSelectedValue();
            if (rule != null) {
               WorkSpaceController.this.OnSelectRule(rule);
            }

         }
      }

      // $FF: synthetic method
      RuleListSelectionListener(Object x1) {
         this();
      }
   }
}
