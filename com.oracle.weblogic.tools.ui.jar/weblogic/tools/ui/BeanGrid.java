package weblogic.tools.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import weblogic.servlet.jsp.BeanUtils;
import weblogic.utils.StringUtils;

public class BeanGrid extends ModelPanel implements ActionListener, ListSelectionListener, MouseListener {
   private static MarathonTextFormatter fmt = new MarathonTextFormatter();
   private boolean m_onlyAcceptUniqueEntries;
   private JTable table;
   private BeanTableModel model;
   private DefaultTableColumnModel columnModel;
   private DefaultListSelectionModel selectionModel;
   private JButton deleteB;
   private JButton addB;
   private JButton editB;
   private JScrollPane tableScrollPane;
   private Border m_border;
   private boolean m_createBorder;
   private String m_editorDialogTitle;
   Container buttonPane;
   Class bc;
   BeanInfo bi;
   PropertyDescriptor[] pds;
   Object[] beans;
   String[] props;
   String[] titles;
   IBeanRowEditor bre;

   public BeanGrid(Object[] beans) {
      this(beans, (IBeanRowEditor)null);
   }

   public BeanGrid(Object[] beans, IBeanRowEditor bre) {
      this(beans, (String[])null, (String[])null, (String[])null, bre);
   }

   public BeanGrid(Object[] beans, String[] colProps, String[] colTitles) {
      this(beans, colProps, colTitles, (String[])null);
   }

   public BeanGrid(Object[] beans, String[] colProps, String[] colTitles, String[] helpAnchors) {
      this(beans, colProps, colTitles, helpAnchors, (IBeanRowEditor)null);
   }

   public BeanGrid(Object[] beans, String[][] propData, IBeanRowEditor bre) {
      this.m_onlyAcceptUniqueEntries = false;
      this.m_border = new EmptyBorder(2, 2, 2, 2);
      this.m_createBorder = false;
      this.m_editorDialogTitle = null;
      this.buttonPane = null;
      String[][] inverted = this.invertArray(propData);
      this.init(beans, inverted[0], inverted[1], inverted[2], bre);
   }

   public BeanGrid(Object[] beans, String[] colProps, String[] colTitles, String[] helpAnchors, IBeanRowEditor bre) {
      this.m_onlyAcceptUniqueEntries = false;
      this.m_border = new EmptyBorder(2, 2, 2, 2);
      this.m_createBorder = false;
      this.m_editorDialogTitle = null;
      this.buttonPane = null;
      this.init(beans, colProps, colTitles, helpAnchors, bre);
   }

   public void setEditorDialogTitle(String s) {
      this.m_editorDialogTitle = s;
   }

   public void setOnlyAcceptUniqueEntries(boolean aue) {
      this.m_onlyAcceptUniqueEntries = aue;
   }

   public boolean alreadyExists(Object o) {
      for(int i = 0; i < this.beans.length; ++i) {
         if (null != o && o.equals(this.beans[i])) {
            return true;
         }
      }

      return false;
   }

   public void cleanup() {
      this.bi = null;
      this.beans = null;
      this.bre = null;
   }

   public void setEditable(boolean b) {
      this.model.setEditable(b);
      this.table.setShowVerticalLines(false);
      this.table.setBackground(Color.lightGray);
   }

   public JTable getTable() {
      return this.table;
   }

   private void init(Object[] beans, String[] colProps, String[] colTitles, String[] helpAnchors, IBeanRowEditor bre) {
      this.beans = beans;
      this.bre = bre;
      if (this.m_createBorder) {
         this.setBorder(this.m_border);
      }

      if (beans != null) {
         this.bc = beans.getClass().getComponentType();
         if (this.bc.isArray()) {
            throw new RuntimeException("multi-dimensional array");
         } else if (BeanUtils.isStringConvertible(this.bc)) {
            throw new RuntimeException("use PrimitiveGrid for primitive types");
         } else {
            try {
               this.bi = Introspector.getBeanInfo(this.bc);
            } catch (IntrospectionException var8) {
               throw new RuntimeException(var8.toString());
            }

            this.pds = this.bi.getPropertyDescriptors();
            this.props = colProps;
            this.fixPDs();
            int initialHeight;
            if (this.props == null) {
               this.props = new String[this.pds.length];

               for(initialHeight = 0; initialHeight < this.pds.length; ++initialHeight) {
                  this.props[initialHeight] = this.pds[initialHeight].getName();
               }
            }

            this.titles = colTitles;
            if (this.titles == null) {
               this.titles = new String[this.props.length];

               for(initialHeight = 0; initialHeight < this.titles.length; ++initialHeight) {
                  this.titles[initialHeight] = StringUtils.ucfirst(this.props[initialHeight]);
               }
            }

            this.makeTable();

            for(initialHeight = 0; helpAnchors != null && initialHeight < helpAnchors.length; ++initialHeight) {
               this.table.putClientProperty("wl.helpanchor." + initialHeight, helpAnchors[initialHeight]);
            }

            for(initialHeight = 0; initialHeight < this.titles.length; ++initialHeight) {
               TableColumn tc = this.columnModel.getColumn(initialHeight);
               tc.setHeaderValue(uncolon(this.titles[initialHeight]));
            }

            initialHeight = this.table.getRowCount() * this.table.getRowHeight();
            int initialWidth = this.table.getColumnModel().getTotalColumnWidth();
            this.table.setPreferredScrollableViewportSize(new Dimension(initialWidth, initialHeight));
            this.tableScrollPane = new JScrollPane(this.table);
            this.tableScrollPane.setBorder((Border)null);
            this.setLayout(new BorderLayout());
            this.add(this.tableScrollPane, "Center");
            this.buttonPane = this.makeButtonPane();
            this.add(this.buttonPane, "South");
         }
      }
   }

   private static String uncolon(String s) {
      int len = false;
      int len;
      if (s != null && (len = s.length()) != 0) {
         if (s.charAt(len - 1) == ':') {
            s = s.substring(0, len - 1);
         }

         return s;
      } else {
         return s;
      }
   }

   private String[][] invertArray(String[][] data) {
      for(int i = 0; i < data.length; ++i) {
         if (data[i].length != 3) {
            throw new IllegalArgumentException("expected row length 3, not " + data[i].length);
         }
      }

      String[][] ret = new String[3][data.length];

      for(int i = 0; i < data.length; ++i) {
         ret[0][i] = data[i][0];
         ret[1][i] = data[i][1];
         ret[2][i] = data[i][2];
      }

      return ret;
   }

   public void setParentInfo(Object parentBean, String propName) {
      this.model.setParentInfo(parentBean, propName);
   }

   public void setParentAdder(Object parentBean, String propName) {
      this.model.setParentAdder(parentBean, propName);
   }

   public void setConstrained(String colname, Object[] vals) {
      this.setConstrained(colname, vals, false);
   }

   public void setConstrained(String colname, Object[] vals, boolean editableCombox) {
      JComboBox cb = new JComboBox(vals);
      cb.setEditable(editableCombox);
      DefaultCellEditor dce = new DefaultCellEditor(cb);
      TableColumn tc = this.table.getColumn(uncolon(colname));
      tc.setCellEditor(dce);
   }

   public Object[] getBeans() {
      return this.beans;
   }

   public void setBeans(Object[] o) {
      Class oldc = this.beans.getClass().getComponentType();
      Class newc = o.getClass().getComponentType();
      if (!oldc.isAssignableFrom(newc)) {
         String newType = newc.getName();
         String oldType = oldc.getName();
         throw new IllegalArgumentException("type mismatch: new=" + newType + " old=" + oldType);
      } else {
         this.beans = (Object[])((Object[])o.clone());
         this.model.setBeans(o);
         this.model.fireTableDataChanged();
      }
   }

   public boolean isEditable(int col) {
      return this.model.isCellEditable(0, col);
   }

   public void setEditable(int col, boolean b) {
      this.model.setEditable(col, b);
   }

   public void modelToUI() {
   }

   public void uiToModel() {
   }

   private void fixPDs() {
      List l = new ArrayList();

      for(int i = 0; i < this.pds.length; ++i) {
         if (this.pds[i].getReadMethod() != null) {
            l.add(this.pds[i]);
         }
      }

      this.pds = new PropertyDescriptor[l.size()];
      l.toArray(this.pds);
   }

   private void makeTable() {
      this.model = new BeanTableModel(this.bc, this.bi, this.pds, this.beans, this.props, this.titles);
      this.table = new JTable();
      this.table.addMouseListener(this);
      this.table.setBorder((Border)null);
      DefaultTableCellRenderer cr = (DefaultTableCellRenderer)this.table.getTableHeader().getDefaultRenderer();
      cr.setHorizontalAlignment(2);
      this.table.setModel(this.model);
      this.columnModel = (DefaultTableColumnModel)this.table.getColumnModel();
      this.selectionModel = (DefaultListSelectionModel)this.table.getSelectionModel();
      this.selectionModel.addListSelectionListener(this);
      DefaultListSelectionModel var10001 = this.selectionModel;
      this.selectionModel.setSelectionMode(0);
   }

   private Container makeButtonPane() {
      JPanel ret = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 0, 5, 6);
      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.fill = 0;
      gbc.anchor = 13;
      gbc.weightx = gbc.weighty = 0.0;
      gbc.weightx = 1.0;
      this.addB = new JButton(fmt.getAddEllipsis());
      ret.add(this.addB, gbc);
      gbc.weightx = 0.0;
      this.addB.addActionListener(this);
      ++gbc.gridx;
      this.editB = new JButton(fmt.getEditEllipsis());
      ret.add(this.editB, gbc);
      this.editB.addActionListener(this);
      ++gbc.gridx;
      this.deleteB = new JButton(fmt.getDelete());
      ret.add(this.deleteB, gbc);
      this.deleteB.addActionListener(this);
      this.editB.setEnabled(false);
      this.deleteB.setEnabled(false);
      ret.setBorder((Border)null);
      return ret;
   }

   public JButton getAddButton() {
      return this.addB;
   }

   public JButton getEditButton() {
      return this.editB;
   }

   public JButton getDeleteButton() {
      return this.deleteB;
   }

   private static void p(String s) {
      System.err.println(s);
   }

   private void deleteBean(Object bean) {
      try {
         Method m = bean.getClass().getMethod("onDelete");
         m.invoke(bean);
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      } catch (InvocationTargetException var4) {
         var4.printStackTrace();
      } catch (NoSuchMethodException var5) {
         System.out.println("Warning:  couldn't find onDelete() on " + bean.getClass());
      }

   }

   public void valueChanged(ListSelectionEvent ev) {
      int sel = this.selectionModel.getLeadSelectionIndex();
      int cnt = this.table.getRowCount();
      if (this.selectionModel.isSelectionEmpty()) {
         this.editB.setEnabled(false);
         this.deleteB.setEnabled(false);
      } else {
         this.editB.setEnabled(true & this.bre != null);
         this.deleteB.setEnabled(true);
      }

   }

   public void mouseClicked(MouseEvent ev) {
      if (ev.getClickCount() >= 2) {
         int row = this.table.getSelectedRow();
         if (this.beans != null && row >= 0 && row < this.beans.length) {
            this.editObject(this.beans[row]);
         }

      }
   }

   public void mousePressed(MouseEvent ev) {
   }

   public void mouseReleased(MouseEvent ev) {
   }

   public void mouseEntered(MouseEvent ev) {
   }

   public void mouseExited(MouseEvent ev) {
   }

   public void actionPerformed(ActionEvent ev) {
      int sel = this.selectionModel.getLeadSelectionIndex();
      Object src = ev.getSource();
      int cnt = this.table.getRowCount();
      if (src == this.deleteB) {
         if (sel < 0 || sel >= cnt) {
            return;
         }

         this.doDelete(sel);
         this.beans = this.model.getBeans();
         --cnt;
         if (sel < cnt) {
            this.selectionModel.setSelectionInterval(sel, sel);
         } else if (cnt > 0) {
            this.selectionModel.setSelectionInterval(sel - 1, sel - 1);
         }

         if (this.model.getRowCount() == 0) {
            this.editB.setEnabled(false);
            this.deleteB.setEnabled(false);
         }
      } else if (src == this.addB) {
         Object newobj = this.doAdd();
         if (newobj == null) {
            return;
         }

         this.model.addRow(newobj);
         this.beans = this.model.getBeans();
      } else if (src == this.editB) {
         this.editObject(this.beans[sel]);
      }

   }

   protected void editObject(Object obj) {
      if (this.bre != null) {
         RowEditorDialog red = null;
         red = new RowEditorDialog(this.getEnclosingFrame(), "", true, this.bre);
         red.editObject(obj);
         this.model.fireTableDataChanged();
      }

   }

   public void doDelete(int index) {
      this.deleteBean(this.beans[index]);
      this.model.removeRow(index);
   }

   public Object doAdd() {
      if (this.bre == null) {
         return null;
      } else {
         RowEditorDialog red = new RowEditorDialog(this.getEnclosingFrame(), this.m_editorDialogTitle, true, this.bre);
         Object newobj = red.addObject();

         while(newobj != null && this.m_onlyAcceptUniqueEntries && this.alreadyExists(newobj)) {
            String msg = fmt.getEntryAlreadyExists();
            String title = fmt.getIllegalEntry();
            JOptionPane.showMessageDialog(this, msg, title, 0);
            red.editObject(newobj);
            if (red.wasCancelled()) {
               newobj = null;
            }
         }

         return newobj;
      }
   }

   protected Frame getEnclosingFrame() {
      Component comp = this.getParent();

      while(!(comp instanceof Frame)) {
         comp = comp.getParent();
         if (comp == null) {
            return null;
         }
      }

      return (Frame)comp;
   }

   public Dimension getMinimumSize() {
      Dimension d1 = this.tableScrollPane.getPreferredSize();
      Dimension d2 = this.buttonPane.getMinimumSize();
      Dimension result = this.buttonPane.getMinimumSize();
      return result;
   }

   public Dimension getPreferredSize() {
      return this.getMinimumSize();
   }
}
