package org.antlr.gunit.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.antlr.gunit.swingui.model.ITestCaseInput;
import org.antlr.gunit.swingui.model.ITestCaseOutput;
import org.antlr.gunit.swingui.model.Rule;
import org.antlr.gunit.swingui.model.TestCase;
import org.antlr.gunit.swingui.model.TestCaseInputFile;
import org.antlr.gunit.swingui.model.TestCaseInputMultiString;
import org.antlr.gunit.swingui.model.TestCaseInputString;
import org.antlr.gunit.swingui.model.TestCaseOutputAST;
import org.antlr.gunit.swingui.model.TestCaseOutputResult;
import org.antlr.gunit.swingui.model.TestCaseOutputReturn;
import org.antlr.gunit.swingui.model.TestCaseOutputStdOut;

public class TestCaseEditController implements IController {
   private JPanel view = new JPanel();
   private JScrollPane scroll;
   private JPanel paneDetail;
   private AbstractEditorPane paneDetailInput;
   private AbstractEditorPane paneDetailOutput;
   private JToolBar toolbar;
   private JList listCases;
   private ListModel listModel;
   public ActionListener onTestCaseNumberChange;
   private InputFileEditor editInputFile;
   private InputStringEditor editInputString;
   private InputMultiEditor editInputMulti;
   private OutputResultEditor editOutputResult;
   private OutputAstEditor editOutputAST;
   private OutputStdEditor editOutputStd;
   private OutputReturnEditor editOutputReturn;
   private JComboBox comboInputType;
   private JComboBox comboOutputType;
   private static final String IN_TYPE_STRING = "Single-line Text";
   private static final String IN_TYPE_MULTI = "Multi-line Text";
   private static final String IN_TYPE_FILE = "Disk File";
   private static final String OUT_TYPE_BOOL = "OK or Fail";
   private static final String OUT_TYPE_AST = "AST";
   private static final String OUT_TYPE_STD = "Standard Output";
   private static final String OUT_TYPE_RET = "Return Value";
   private static final String DEFAULT_IN_SCRIPT = "";
   private static final String DEFAULT_OUT_SCRIPT = "";
   private static final Object[] INPUT_TYPE = new Object[]{"Single-line Text", "Multi-line Text", "Disk File"};
   private static final Object[] OUTPUT_TYPE = new Object[]{"OK or Fail", "AST", "Standard Output", "Return Value"};
   private static final int TEST_CASE_DETAIL_WIDTH = 300;
   private static final int TEST_EDITOR_WIDTH = 280;
   private static final int TEST_CASE_DETAIL_HEIGHT = 250;
   private static final int TEST_EDITOR_HEIGHT = 120;
   private Rule currentRule = null;
   private TestCase currentTestCase = null;
   private static final HashMap TypeNameTable = new HashMap();
   private static TestCaseListRenderer listRenderer;

   public TestCaseEditController(WorkSpaceView workspace) {
      this.initComponents();
   }

   public TestCaseEditController() {
      this.initComponents();
   }

   public void OnLoadRule(Rule rule) {
      if (rule == null) {
         throw new IllegalArgumentException("Null");
      } else {
         this.currentRule = rule;
         this.currentTestCase = null;
         this.listModel = rule;
         this.listCases.setModel(this.listModel);
      }
   }

   public void setCurrentTestCase(TestCase testCase) {
      if (testCase == null) {
         throw new IllegalArgumentException("Null");
      } else {
         this.listCases.setSelectedValue(testCase, true);
         this.currentTestCase = testCase;
      }
   }

   public Rule getCurrentRule() {
      return this.currentRule;
   }

   private void initComponents() {
      this.listCases = new JList();
      this.listCases.addListSelectionListener(new TestCaseListSelectionListener());
      this.listCases.setCellRenderer(listRenderer);
      this.listCases.setOpaque(false);
      this.scroll = new JScrollPane(this.listCases);
      this.scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Test Cases"));
      this.scroll.setOpaque(false);
      this.scroll.setViewportBorder(BorderFactory.createEtchedBorder());
      this.editInputString = new InputStringEditor();
      this.editInputMulti = new InputMultiEditor();
      this.editInputFile = new InputFileEditor();
      this.editOutputResult = new OutputResultEditor();
      this.editOutputAST = new OutputAstEditor();
      this.editOutputStd = new OutputStdEditor();
      this.editOutputReturn = new OutputReturnEditor();
      this.paneDetail = new JPanel();
      this.paneDetail.setBorder(BorderFactory.createEmptyBorder());
      this.paneDetail.setOpaque(false);
      this.comboInputType = new JComboBox(INPUT_TYPE);
      this.comboInputType.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            TestCaseEditController.this.OnInputTestCaseTypeChanged(TestCaseEditController.this.comboInputType.getSelectedItem());
         }
      });
      this.comboOutputType = new JComboBox(OUTPUT_TYPE);
      this.comboOutputType.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            TestCaseEditController.this.OnOutputTestCaseTypeChanged(TestCaseEditController.this.comboOutputType.getSelectedItem());
         }
      });
      this.paneDetailInput = new InputEditorPane(this.comboInputType);
      this.paneDetailOutput = new OutputEditorPane(this.comboOutputType);
      BoxLayout layout = new BoxLayout(this.paneDetail, 3);
      this.paneDetail.setLayout(layout);
      this.paneDetail.add(this.paneDetailInput);
      this.paneDetail.add(this.paneDetailOutput);
      this.toolbar = new JToolBar("Edit TestCases", 1);
      this.toolbar.setFloatable(false);
      this.toolbar.add(new AddTestCaseAction());
      this.toolbar.add(new RemoveTestCaseAction());
      this.view.setLayout(new BorderLayout());
      this.view.setBorder(BorderFactory.createEmptyBorder());
      this.view.setOpaque(false);
      this.view.add(this.toolbar, "West");
      this.view.add(this.scroll, "Center");
      this.view.add(this.paneDetail, "East");
   }

   private void updateInputEditor() {
      JComponent editor = null;
      if (this.currentTestCase != null) {
         ITestCaseInput input = this.currentTestCase.getInput();
         if (input instanceof TestCaseInputString) {
            this.editInputString.setText(input.getScript());
            editor = this.editInputString;
            this.comboInputType.setSelectedItem("Single-line Text");
         } else if (input instanceof TestCaseInputMultiString) {
            this.editInputMulti.setText(input.getScript());
            editor = this.editInputMulti.getView();
            this.comboInputType.setSelectedItem("Multi-line Text");
         } else {
            if (!(input instanceof TestCaseInputFile)) {
               throw new Error("Wrong type");
            }

            this.editInputFile.setText(input.getScript());
            editor = this.editInputFile;
            this.comboInputType.setSelectedItem("Disk File");
         }
      }

      this.paneDetailInput.setEditor((JComponent)editor);
   }

   private void updateOutputEditor() {
      JComponent editor = null;
      if (this.currentTestCase != null) {
         ITestCaseOutput output = this.currentTestCase.getOutput();
         if (output instanceof TestCaseOutputAST) {
            this.editOutputAST.setText(output.getScript());
            editor = this.editOutputAST.getView();
            this.comboOutputType.setSelectedItem("AST");
         } else if (output instanceof TestCaseOutputResult) {
            this.editOutputResult.setValue(output.getScript());
            editor = this.editOutputResult;
            this.comboOutputType.setSelectedItem("OK or Fail");
         } else if (output instanceof TestCaseOutputStdOut) {
            this.editOutputStd.setText(output.getScript());
            editor = this.editOutputStd.getView();
            this.comboOutputType.setSelectedItem("Standard Output");
         } else {
            if (!(output instanceof TestCaseOutputReturn)) {
               throw new Error("Wrong type");
            }

            this.editOutputReturn.setText(output.getScript());
            editor = this.editOutputReturn.getView();
            this.comboOutputType.setSelectedItem("Return Value");
         }
      }

      this.paneDetailOutput.setEditor((JComponent)editor);
   }

   private void OnInputTestCaseTypeChanged(Object inputTypeStr) {
      if (this.currentTestCase != null) {
         Object input;
         if (inputTypeStr == "Single-line Text") {
            input = new TestCaseInputString("");
         } else if (inputTypeStr == "Multi-line Text") {
            input = new TestCaseInputMultiString("");
         } else {
            if (inputTypeStr != "Disk File") {
               throw new Error("Wrong Type");
            }

            input = new TestCaseInputFile("");
         }

         if (input.getClass().equals(this.currentTestCase.getInput().getClass())) {
            return;
         }

         this.currentTestCase.setInput((ITestCaseInput)input);
      }

      this.updateInputEditor();
   }

   private void OnOutputTestCaseTypeChanged(Object outputTypeStr) {
      if (this.currentTestCase != null) {
         Object output;
         if (outputTypeStr == "AST") {
            output = new TestCaseOutputAST("");
         } else if (outputTypeStr == "OK or Fail") {
            output = new TestCaseOutputResult(false);
         } else if (outputTypeStr == "Standard Output") {
            output = new TestCaseOutputStdOut("");
         } else {
            if (outputTypeStr != "Return Value") {
               throw new Error("Wrong Type");
            }

            output = new TestCaseOutputReturn("");
         }

         if (output.getClass().equals(this.currentTestCase.getOutput().getClass())) {
            return;
         }

         this.currentTestCase.setOutput((ITestCaseOutput)output);
      }

      this.updateOutputEditor();
   }

   private void OnTestCaseSelected(TestCase testCase) {
      this.currentTestCase = testCase;
      this.updateInputEditor();
      this.updateOutputEditor();
   }

   private void OnAddTestCase() {
      if (this.currentRule != null) {
         TestCase newCase = new TestCase(new TestCaseInputString(""), new TestCaseOutputResult(true));
         this.currentRule.addTestCase(newCase);
         this.setCurrentTestCase(newCase);
         this.listCases.setSelectedValue(newCase, true);
         this.listCases.updateUI();
         this.OnTestCaseSelected(newCase);
         this.onTestCaseNumberChange.actionPerformed((ActionEvent)null);
      }
   }

   private void OnRemoveTestCase() {
      if (this.currentTestCase != null) {
         this.currentRule.removeElement(this.currentTestCase);
         this.listCases.updateUI();
         TestCase nextActiveCase = this.listCases.isSelectionEmpty() ? null : (TestCase)this.listCases.getSelectedValue();
         this.OnTestCaseSelected(nextActiveCase);
         this.onTestCaseNumberChange.actionPerformed((ActionEvent)null);
      }
   }

   public Object getModel() {
      return this.currentRule;
   }

   public Component getView() {
      return this.view;
   }

   static {
      TypeNameTable.put(TestCaseInputString.class, "Single-line Text");
      TypeNameTable.put(TestCaseInputMultiString.class, "Multi-line Text");
      TypeNameTable.put(TestCaseInputFile.class, "Disk File");
      TypeNameTable.put(TestCaseOutputResult.class, "OK or Fail");
      TypeNameTable.put(TestCaseOutputAST.class, "AST");
      TypeNameTable.put(TestCaseOutputStdOut.class, "Standard Output");
      TypeNameTable.put(TestCaseOutputReturn.class, "Return Value");
      listRenderer = new TestCaseListRenderer();
   }

   private static class TestCaseListRenderer implements ListCellRenderer {
      private static Font IN_FONT = new Font("mono", 0, 12);
      private static Font OUT_FONT = new Font("default", 1, 12);

      private TestCaseListRenderer() {
      }

      public static String clamp(String text, int len) {
         return text.length() > len ? text.substring(0, len - 3).concat("...") : text;
      }

      public static String clampAtNewLine(String text) {
         int pos = text.indexOf(10);
         return pos >= 0 ? text.substring(0, pos).concat("...") : text;
      }

      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
         JPanel pane = new JPanel();
         if (value instanceof TestCase) {
            TestCase item = (TestCase)value;
            JLabel labIn = new JLabel(clamp(clampAtNewLine(item.getInput().getScript()), 18));
            JLabel labOut = new JLabel(clamp(clampAtNewLine(item.getOutput().getScript()), 18));
            labOut.setFont(OUT_FONT);
            labIn.setFont(IN_FONT);
            labIn.setIcon(item.getInput() instanceof TestCaseInputFile ? ImageFactory.getSingleton().FILE16 : ImageFactory.getSingleton().EDIT16);
            pane.setBorder(BorderFactory.createEtchedBorder());
            pane.setLayout(new BoxLayout(pane, 1));
            pane.add(labIn);
            pane.add(labOut);
            pane.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
         }

         return pane;
      }

      // $FF: synthetic method
      TestCaseListRenderer(Object x0) {
         this();
      }
   }

   private class RemoveTestCaseAction extends AbstractAction {
      public RemoveTestCaseAction() {
         super("Remove", ImageFactory.getSingleton().DELETE);
         this.putValue("ShortDescription", "Remove a gUnit test case.");
      }

      public void actionPerformed(ActionEvent e) {
         TestCaseEditController.this.OnRemoveTestCase();
      }
   }

   private class AddTestCaseAction extends AbstractAction {
      public AddTestCaseAction() {
         super("Add", ImageFactory.getSingleton().ADD);
         this.putValue("ShortDescription", "Add a gUnit test case.");
      }

      public void actionPerformed(ActionEvent e) {
         TestCaseEditController.this.OnAddTestCase();
      }
   }

   private class TestCaseListSelectionListener implements ListSelectionListener {
      private TestCaseListSelectionListener() {
      }

      public void valueChanged(ListSelectionEvent e) {
         if (!e.getValueIsAdjusting()) {
            JList list = (JList)e.getSource();
            TestCase value = (TestCase)list.getSelectedValue();
            if (value != null) {
               TestCaseEditController.this.OnTestCaseSelected(value);
            }

         }
      }

      // $FF: synthetic method
      TestCaseListSelectionListener(Object x1) {
         this();
      }
   }

   public class OutputReturnEditor extends OutputAstEditor {
      public OutputReturnEditor() {
         super();
      }
   }

   public class OutputStdEditor extends OutputAstEditor {
      public OutputStdEditor() {
         super();
      }
   }

   public class OutputAstEditor implements CaretListener {
      private JTextArea textArea = new JTextArea(20, 30);
      private JScrollPane scroll;

      public OutputAstEditor() {
         this.scroll = new JScrollPane(this.textArea, 22, 32);
         this.scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
         this.textArea.addCaretListener(this);
      }

      public void caretUpdate(CaretEvent arg0) {
         TestCaseEditController.this.currentTestCase.getOutput().setScript(this.getText());
         TestCaseEditController.this.listCases.updateUI();
      }

      public void setText(String text) {
         this.textArea.setText(text);
      }

      public String getText() {
         return this.textArea.getText();
      }

      public JScrollPane getView() {
         return this.scroll;
      }
   }

   public class OutputResultEditor extends JPanel implements ActionListener {
      private JToggleButton tbFail = new JToggleButton("Fail");
      private JToggleButton tbOk = new JToggleButton("OK");

      public OutputResultEditor() {
         ButtonGroup group = new ButtonGroup();
         group.add(this.tbFail);
         group.add(this.tbOk);
         this.add(this.tbFail);
         this.add(this.tbOk);
         this.tbFail.addActionListener(this);
         this.tbOk.addActionListener(this);
         this.setPreferredSize(new Dimension(280, 100));
      }

      public void actionPerformed(ActionEvent e) {
         TestCaseOutputResult output = (TestCaseOutputResult)TestCaseEditController.this.currentTestCase.getOutput();
         if (e.getSource() == this.tbFail) {
            output.setScript(false);
         } else {
            output.setScript(true);
         }

         TestCaseEditController.this.listCases.updateUI();
      }

      public void setValue(String value) {
         if (TestCaseOutputResult.OK.equals(value)) {
            this.tbOk.setSelected(true);
         } else {
            this.tbFail.setSelected(true);
         }

      }
   }

   public class InputFileEditor extends InputStringEditor {
      public InputFileEditor() {
         super();
      }
   }

   public class InputMultiEditor implements CaretListener {
      private JTextArea textArea = new JTextArea(20, 30);
      private JScrollPane scroll;

      public InputMultiEditor() {
         this.scroll = new JScrollPane(this.textArea, 22, 32);
         this.scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
         this.textArea.addCaretListener(this);
      }

      public void caretUpdate(CaretEvent arg0) {
         TestCaseEditController.this.currentTestCase.getInput().setScript(this.getText());
         TestCaseEditController.this.listCases.updateUI();
      }

      public String getText() {
         return this.textArea.getText();
      }

      public void setText(String text) {
         this.textArea.setText(text);
      }

      public JComponent getView() {
         return this.scroll;
      }
   }

   public class InputStringEditor extends JTextField implements CaretListener {
      public InputStringEditor() {
         this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
         this.addCaretListener(this);
      }

      public void caretUpdate(CaretEvent arg0) {
         TestCaseEditController.this.currentTestCase.getInput().setScript(this.getText());
         TestCaseEditController.this.listCases.updateUI();
      }
   }

   public class OutputEditorPane extends AbstractEditorPane {
      public OutputEditorPane(JComboBox comboBox) {
         super(comboBox, "Output");
      }
   }

   public class InputEditorPane extends AbstractEditorPane {
      public InputEditorPane(JComboBox comboBox) {
         super(comboBox, "Input");
      }
   }

   public abstract class AbstractEditorPane extends JPanel {
      private JComboBox combo;
      private JComponent editor;
      private String title;
      private JLabel placeHolder = new JLabel();

      public AbstractEditorPane(JComboBox comboBox, String title) {
         this.combo = comboBox;
         this.editor = this.placeHolder;
         this.title = title;
         this.initComponents();
      }

      private void initComponents() {
         this.placeHolder.setPreferredSize(new Dimension(300, 250));
         this.setLayout(new BoxLayout(this, 1));
         this.add(this.combo, "North");
         this.add(this.editor, "Center");
         this.setOpaque(false);
         this.setBorder(BorderFactory.createTitledBorder(this.title));
         this.setPreferredSize(new Dimension(300, 250));
      }

      public void setEditor(JComponent newEditor) {
         if (newEditor == null) {
            newEditor = this.placeHolder;
         }

         this.remove(this.editor);
         this.add((Component)newEditor);
         this.editor = (JComponent)newEditor;
         this.updateUI();
      }
   }
}
