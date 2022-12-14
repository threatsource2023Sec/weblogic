package org.antlr.gunit.swingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class WorkSpaceView extends JFrame {
   protected JSplitPane splitListClient;
   protected JTabbedPane tabEditors;
   protected JPanel paneToolBar;
   protected StatusBarController paneStatus;
   protected TestCaseEditController paneEditor;
   protected JToolBar toolbar;
   protected JTextArea txtEditor;
   protected RuleListController listRules;
   protected JMenuBar menuBar;
   protected JScrollPane scrollCode;
   protected JPanel resultPane;
   protected JButton btnOpenGrammar;

   protected void initComponents() {
      this.paneEditor = new TestCaseEditController(this);
      this.paneStatus = new StatusBarController();
      this.toolbar = new JToolBar();
      this.toolbar.setBorder(BorderFactory.createEmptyBorder());
      this.toolbar.setFloatable(false);
      this.toolbar.setBorder(BorderFactory.createEmptyBorder());
      this.txtEditor = new JTextArea();
      this.txtEditor.setLineWrap(false);
      this.txtEditor.setFont(new Font("Courier New", 0, 13));
      this.scrollCode = new JScrollPane(this.txtEditor, 22, 30);
      this.scrollCode.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
      this.tabEditors = new JTabbedPane();
      this.tabEditors.addTab("Case Editor", ImageFactory.getSingleton().TEXTFILE16, this.paneEditor.getView());
      this.tabEditors.addTab("Script Source", ImageFactory.getSingleton().WINDOW16, this.scrollCode);
      this.listRules = new RuleListController();
      this.splitListClient = new JSplitPane(1, this.listRules.getView(), this.tabEditors);
      this.splitListClient.setResizeWeight(0.4);
      this.splitListClient.setBorder(BorderFactory.createEmptyBorder());
      this.getContentPane().add(this.toolbar, "North");
      this.getContentPane().add(this.splitListClient, "Center");
      this.getContentPane().add(this.paneStatus.getView(), "South");
      this.setPreferredSize(new Dimension(900, 500));
      this.setDefaultCloseOperation(3);
   }
}
