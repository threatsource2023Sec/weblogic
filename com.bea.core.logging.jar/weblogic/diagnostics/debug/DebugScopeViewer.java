package weblogic.diagnostics.debug;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class DebugScopeViewer extends JFrame {
   private static final String PROPERTY_COL = "ServerDebug Properties";
   private InputStream inputStream = null;
   private DefaultTreeModel treeModel = null;
   private MyTableModel propertyTableModel = null;
   private TreeSelectionModel treeSelectionModel = null;
   private Action exitAction = new ExitAction();
   private DebugScopeNode rootNode = null;
   private JTable propertyTable;

   public DebugScopeViewer(InputStream is) {
      this.inputStream = is;
      this.setTitle("DebugScope Viewer");
      this.setContentPane(this.createSplitPane());
      this.setJMenuBar(this.createMenuBar());
      this.setLocation(100, 100);
      this.setSize(new Dimension(400, 300));
   }

   private JSplitPane createSplitPane() {
      JScrollPane leftPane = this.createTreePane();
      JScrollPane rightPane = this.createTablePane();
      JSplitPane splitPane = new JSplitPane(1, leftPane, rightPane);
      splitPane.setOneTouchExpandable(true);
      splitPane.setDividerLocation(150);
      Dimension minimumSize = new Dimension(100, 50);
      leftPane.setMinimumSize(minimumSize);
      rightPane.setMinimumSize(minimumSize);
      return splitPane;
   }

   private JScrollPane createTreePane() {
      JScrollPane treePane = null;
      ObjectInputStream ois = null;

      try {
         ois = new ObjectInputStream(this.inputStream);
         this.rootNode = ((DebugScopeTree)ois.readObject()).getRootNode();
         MyDefaultMutableTreeNode rootTreeNode = new MyDefaultMutableTreeNode(new UserData(this.rootNode));
         this.buildTreeNodes(rootTreeNode, this.rootNode);
         this.treeModel = new DefaultTreeModel(rootTreeNode);
         JTree tree = new JTree(this.treeModel);
         tree.setEditable(true);
         tree.setInvokesStopCellEditing(true);
         tree.setShowsRootHandles(true);
         this.treeSelectionModel = tree.getSelectionModel();
         this.treeSelectionModel.setSelectionMode(1);
         this.treeSelectionModel.addTreeSelectionListener(new MyTreeSelectionListener());
         treePane = new JScrollPane(tree);
      } catch (Exception var13) {
         var13.printStackTrace();
      } finally {
         if (ois != null) {
            try {
               ois.close();
            } catch (IOException var12) {
            }
         }

      }

      return treePane;
   }

   private JScrollPane createTablePane() {
      JScrollPane propsPane = null;
      Dimension dim = new Dimension(450, 225);
      this.propertyTableModel = new MyTableModel("ServerDebug Properties");
      this.propertyTable = new JTable(this.propertyTableModel);
      propsPane = new JScrollPane(this.propertyTable);
      propsPane.setPreferredSize(dim);
      return propsPane;
   }

   private void buildTreeNodes(MyDefaultMutableTreeNode treeNode, DebugScopeNode node) {
      Set children = node.getChildDebugScopeNodes();
      Iterator iter = children.iterator();

      while(iter.hasNext()) {
         DebugScopeNode n = (DebugScopeNode)iter.next();
         MyDefaultMutableTreeNode tn = new MyDefaultMutableTreeNode(new UserData(n));
         treeNode.add(tn);
         this.buildTreeNodes(tn, n);
      }

   }

   private JMenuBar createMenuBar() {
      JMenuBar menuBar = new JMenuBar();
      JMenu menu = new JMenu("File");
      JMenuItem menuItem = new JMenuItem(this.exitAction);
      menu.add(menuItem);
      menuBar.add(menu);
      return menuBar;
   }

   public static void main(String[] args) throws Exception {
      InputStream is = null;
      if (args != null && args.length != 0) {
         is = new FileInputStream(args[0]);
      } else {
         is = DebugScopeViewer.class.getResourceAsStream("DebugScopeTree.ser");
      }

      DebugScopeViewer frame = new DebugScopeViewer((InputStream)is);
      frame.setDefaultCloseOperation(3);
      frame.pack();
      frame.show();
   }

   private static class UserData {
      private DebugScopeNode node;

      public UserData(DebugScopeNode n) {
         this.node = n;
      }

      public String toString() {
         return this.node.toString();
      }
   }

   private static class MyTableModel extends AbstractTableModel {
      private String[] columnNames = null;
      private Vector rowData = null;

      public MyTableModel(String columnName) {
         this.columnNames = new String[1];
         this.columnNames[0] = columnName;
         this.rowData = new Vector();
      }

      public String getColumnName(int col) {
         return this.columnNames[col];
      }

      public int getRowCount() {
         return this.rowData.size();
      }

      public int getColumnCount() {
         return this.columnNames.length;
      }

      public Object getValueAt(int row, int col) {
         return this.rowData.get(row);
      }

      public boolean isCellEditable(int row, int col) {
         return false;
      }

      public void setValueAt(Object value, int row, int col) {
      }

      public void removeAllElements() {
         this.rowData.removeAllElements();
         this.fireTableStructureChanged();
      }

      public void addElement(Object data) {
         this.rowData.add(data);
         this.fireTableStructureChanged();
      }
   }

   private static class MyDefaultMutableTreeNode extends DefaultMutableTreeNode {
      public MyDefaultMutableTreeNode(Object userData) {
         super(userData);
      }
   }

   private class MyTreeSelectionListener implements TreeSelectionListener {
      private MyTreeSelectionListener() {
      }

      public void valueChanged(TreeSelectionEvent event) {
         TreePath tp = DebugScopeViewer.this.treeSelectionModel.getSelectionPath();
         if (tp != null) {
            MyDefaultMutableTreeNode node = (MyDefaultMutableTreeNode)tp.getLastPathComponent();
            if (node != null) {
               UserData nodeInfo = (UserData)node.getUserObject();
               DebugScopeViewer.this.propertyTableModel.removeAllElements();
               Iterator iter = nodeInfo.node.getDebugAttributes().iterator();

               while(iter.hasNext()) {
                  DebugScopeViewer.this.propertyTableModel.addElement(iter.next());
               }

            }
         }
      }

      // $FF: synthetic method
      MyTreeSelectionListener(Object x1) {
         this();
      }
   }

   private class ExitAction extends AbstractAction {
      public ExitAction() {
         super("Exit");
      }

      public void actionPerformed(ActionEvent ev) {
         DebugScopeViewer.this.dispatchEvent(new WindowEvent(DebugScopeViewer.this, 201));
      }
   }
}
