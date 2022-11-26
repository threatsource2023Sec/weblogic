package org.stringtemplate.v4.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;

public class STViewFrame extends JFrame {
   private JToolBar toolBar1;
   public JSplitPane treeContentSplitPane;
   public JSplitPane treeAttributesSplitPane;
   public JScrollPane treeScrollPane;
   protected JTree tree;
   protected JScrollPane attributeScrollPane;
   protected JTree attributes;
   public JSplitPane outputTemplateSplitPane;
   protected JScrollPane scrollPane7;
   public JTextPane output;
   public JTabbedPane templateBytecodeTraceTabPanel;
   private JPanel panel1;
   private JScrollPane scrollPane3;
   public JTextPane template;
   private JScrollPane scrollPane2;
   public JTree ast;
   protected JScrollPane scrollPane15;
   protected JTextPane bytecode;
   private JScrollPane scrollPane1;
   public JTextPane trace;
   public JScrollPane errorScrollPane;
   protected JList errorList;

   public STViewFrame() {
      this.initComponents();
   }

   private void initComponents() {
      this.toolBar1 = new JToolBar();
      this.treeContentSplitPane = new JSplitPane();
      this.treeAttributesSplitPane = new JSplitPane();
      this.treeScrollPane = new JScrollPane();
      this.tree = new JTree();
      this.attributeScrollPane = new JScrollPane();
      this.attributes = new JTree();
      this.outputTemplateSplitPane = new JSplitPane();
      this.scrollPane7 = new JScrollPane();
      this.output = new JTextPane();
      this.templateBytecodeTraceTabPanel = new JTabbedPane();
      this.panel1 = new JPanel();
      this.scrollPane3 = new JScrollPane();
      this.template = new JTextPane();
      this.scrollPane2 = new JScrollPane();
      this.ast = new JTree();
      this.scrollPane15 = new JScrollPane();
      this.bytecode = new JTextPane();
      this.scrollPane1 = new JScrollPane();
      this.trace = new JTextPane();
      this.errorScrollPane = new JScrollPane();
      this.errorList = new JList();
      Container contentPane = this.getContentPane();
      contentPane.setLayout(new GridBagLayout());
      ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[]{0, 0};
      ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[]{0, 0, 0, 0};
      ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[]{1.0, 1.0E-4};
      ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
      contentPane.add(this.toolBar1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
      this.treeContentSplitPane.setResizeWeight(0.25);
      this.treeAttributesSplitPane.setOrientation(0);
      this.treeAttributesSplitPane.setResizeWeight(0.7);
      this.treeScrollPane.setViewportView(this.tree);
      this.treeAttributesSplitPane.setTopComponent(this.treeScrollPane);
      this.attributeScrollPane.setViewportView(this.attributes);
      this.treeAttributesSplitPane.setBottomComponent(this.attributeScrollPane);
      this.treeContentSplitPane.setLeftComponent(this.treeAttributesSplitPane);
      this.outputTemplateSplitPane.setOrientation(0);
      this.outputTemplateSplitPane.setResizeWeight(0.7);
      this.scrollPane7.setViewportView(this.output);
      this.outputTemplateSplitPane.setTopComponent(this.scrollPane7);
      this.panel1.setLayout(new BoxLayout(this.panel1, 0));
      this.scrollPane3.setViewportView(this.template);
      this.panel1.add(this.scrollPane3);
      this.scrollPane2.setViewportView(this.ast);
      this.panel1.add(this.scrollPane2);
      this.templateBytecodeTraceTabPanel.addTab("template", this.panel1);
      this.scrollPane15.setViewportView(this.bytecode);
      this.templateBytecodeTraceTabPanel.addTab("bytecode", this.scrollPane15);
      this.scrollPane1.setViewportView(this.trace);
      this.templateBytecodeTraceTabPanel.addTab("trace", this.scrollPane1);
      this.outputTemplateSplitPane.setBottomComponent(this.templateBytecodeTraceTabPanel);
      this.treeContentSplitPane.setRightComponent(this.outputTemplateSplitPane);
      contentPane.add(this.treeContentSplitPane, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
      this.errorScrollPane.setViewportView(this.errorList);
      contentPane.add(this.errorScrollPane, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
      this.pack();
      this.setLocationRelativeTo(this.getOwner());
   }
}
