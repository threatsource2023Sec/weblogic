package weblogic.tools.ui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DualListPanel extends JPanel implements ActionListener, ListSelectionListener {
   static final String IMG_BASE_PATH = "/weblogic/tools/ui/graphics/";
   static final String LEFT_PATH = "/weblogic/tools/ui/graphics/arrow_left_enabled.gif";
   static final String RIGHT_PATH = "/weblogic/tools/ui/graphics/arrow_right_enabled.gif";
   private static final int DEFAULT_LIST_HEIGHT = 250;
   protected JList selected;
   protected JList total;
   protected JButton left;
   protected JButton right;
   protected JLabel selLabel;
   protected JLabel totalLabel;
   protected int m_listHeight = 250;
   private static final int LABEL_LIST_GAP = 2;
   private static final int OUTSIDE_GAP = 5;
   static String[] totalData = new String[]{"Larry", "Moe", "Curly", "Fred", "Wilma", "Barney", "Betty"};
   static String[] selData = new String[]{"Moe", "Curly", "Barney", "Shemp"};

   public DualListPanel(String seltitle, String totaltitle) {
      this.add(this.selected = new JList());
      this.add(this.total = new JList());
      this.add(this.left = this.makeImageButton("/weblogic/tools/ui/graphics/arrow_left_enabled.gif"));
      this.add(this.right = this.makeImageButton("/weblogic/tools/ui/graphics/arrow_right_enabled.gif"));
      this.add(this.selLabel = new JLabel(seltitle));
      this.add(this.totalLabel = new JLabel(totaltitle));
      this.selLabel.setLabelFor(this.selected);
      this.totalLabel.setLabelFor(this.total);
      this.selected.setBorder(new BevelBorder(1));
      this.total.setBorder(new BevelBorder(1));
      this.left.addActionListener(this);
      this.right.addActionListener(this);
      this.selected.addListSelectionListener(this);
      this.total.addListSelectionListener(this);
      this.total.setSelectionMode(0);
      this.selected.setSelectionMode(0);
      this.syncEnabled();
   }

   public Object[] getUnselected() {
      return getListData(this.total);
   }

   public Object[] getTotal() {
      Object[] a = this.getSelected();
      Object[] b = this.getUnselected();
      Object[] ret = new Object[a.length + b.length];
      System.arraycopy(a, 0, ret, 0, a.length);
      System.arraycopy(b, 0, ret, a.length, b.length);
      return ret;
   }

   public Object[] getSelected() {
      return getListData(this.selected);
   }

   public void setTotal(Object[] x) {
      Object[] currentSel = this.getSelected();
      Object[] leftover = computeLeftover(currentSel, x);
      this.total.setListData(leftover);
      Object[] goodsels = removeBadSels(currentSel, x);
      this.selected.setListData(goodsels);
   }

   public void setSelected(Object[] x) {
      Object[] totaldata = this.getTotal();
      Object[] leftover = computeLeftover(x, totaldata);
      this.total.setListData(leftover);
      Object[] goodSels = removeBadSels(x, totaldata);
      this.selected.setListData(goodSels);
   }

   protected void listsChanged() {
   }

   static Object[] removeBadSels(Object[] sel, Object[] tot) {
      if (sel == null) {
         sel = new Object[0];
      }

      if (tot == null) {
         tot = new Object[0];
      }

      List array = new ArrayList();

      for(int i = 0; i < sel.length; ++i) {
         boolean ok = false;

         for(int j = 0; j < tot.length; ++j) {
            if (sel[i].equals(tot[j])) {
               ok = true;
               break;
            }
         }

         if (ok) {
            array.add(sel[i]);
         }
      }

      Object[] ret = new Object[array.size()];
      array.toArray(ret);
      return ret;
   }

   static Object[] computeLeftover(Object[] sel, Object[] tot) {
      if (sel == null) {
         sel = new Object[0];
      }

      if (tot == null) {
         tot = new Object[0];
      }

      List array = new ArrayList();

      for(int i = 0; i < tot.length; ++i) {
         boolean add = true;

         for(int j = 0; j < sel.length; ++j) {
            if (tot[i].equals(sel[j])) {
               add = false;
               break;
            }
         }

         if (add) {
            array.add(tot[i]);
         }
      }

      Object[] ret = new Object[array.size()];
      array.toArray(ret);
      return ret;
   }

   static Object[] getListData(JList l) {
      ListModel lm = l.getModel();
      List array = new ArrayList();
      int sz = lm.getSize();

      for(int i = 0; i < sz; ++i) {
         array.add(lm.getElementAt(i));
      }

      Object[] ret = new Object[sz];
      array.toArray(ret);
      return ret;
   }

   private void syncEnabled() {
      this.right.setEnabled(!this.selected.isSelectionEmpty());
      this.left.setEnabled(!this.total.isSelectionEmpty());
   }

   public void actionPerformed(ActionEvent ev) {
      try {
         this.action(ev);
      } finally {
         this.syncEnabled();
      }

   }

   public void setListHeight(int h) {
      this.m_listHeight = h;
   }

   private void action(ActionEvent ev) {
      JList oldlist;
      JList newlist;
      if (ev.getSource() == this.right) {
         oldlist = this.selected;
         newlist = this.total;
      } else {
         oldlist = this.total;
         newlist = this.selected;
      }

      int ind = oldlist.getSelectedIndex();
      if (ind != -1) {
         Object shiftObj = oldlist.getSelectedValue();
         oldlist.setListData(remove(getListData(oldlist), ind));
         newlist.setListData(add(getListData(newlist), shiftObj));
         oldlist.clearSelection();
         newlist.clearSelection();
         int sz = oldlist.getModel().getSize();
         if (ind >= sz) {
            ind = sz - 1;
         }

         oldlist.setSelectedIndex(ind);
         this.listsChanged();
      }
   }

   static Object[] remove(Object[] x, int i) {
      if (i >= 0 && i <= x.length - 1) {
         Object[] ret = new Object[x.length - 1];
         System.arraycopy(x, 0, ret, 0, i);
         if (i != ret.length) {
            System.arraycopy(x, i + 1, ret, i, ret.length - i);
         }

         return ret;
      } else {
         return x;
      }
   }

   static Object[] add(Object[] x, Object o) {
      Object[] ret = new Object[x.length + 1];
      System.arraycopy(x, 0, ret, 0, x.length);
      ret[x.length] = o;
      return ret;
   }

   public void valueChanged(ListSelectionEvent lse) {
      this.syncEnabled();
   }

   public Dimension getPreferredSize() {
      return this.layout(false);
   }

   public void doLayout() {
      this.layout(true);
   }

   private Dimension layout(boolean b) {
      Border bdr = this.getBorder();
      Insets bsets = null;
      if (bdr != null) {
         bsets = bdr.getBorderInsets(this);
      } else {
         bsets = new Insets(0, 0, 0, 0);
      }

      int leftMargin = bsets.left + 5;
      int rightMargin = bsets.left + 5;
      int topMargin = bsets.top + 5;
      int bottmMargin = bsets.bottom + 5;
      Dimension d = null;
      int lwidth = 100;
      int lheight = this.m_listHeight;
      lwidth = Math.max(this.selLabel.getPreferredSize().width, lwidth);
      lwidth = Math.max(this.totalLabel.getPreferredSize().width, lwidth);
      d = this.selected.getPreferredSize();
      lwidth = Math.max(lwidth, d.width);
      if (lheight == 250) {
         lheight = Math.max(lheight, d.height);
      }

      d = this.total.getPreferredSize();
      lwidth = Math.max(lwidth, d.width);
      if (lheight == 250) {
         lheight = Math.max(lheight, d.height);
      }

      lheight = Math.min(lheight, 500);
      int lblHeight = this.selLabel.getPreferredSize().height;
      lblHeight = Math.max(this.totalLabel.getPreferredSize().height, lblHeight);
      d = this.selLabel.getPreferredSize();
      if (b) {
         this.selLabel.setBounds(leftMargin, topMargin, d.width, d.height);
      }

      int listy = topMargin + d.height + 2;
      if (b) {
         this.selected.setBounds(leftMargin, listy, lwidth, lheight);
      }

      int listGap = this.left.getPreferredSize().width;
      listGap = Math.max(this.right.getPreferredSize().width, listGap);
      listGap += 10;
      d = this.totalLabel.getPreferredSize();
      if (b) {
         this.totalLabel.setBounds(leftMargin + lwidth + listGap, topMargin, d.width, d.height);
         this.total.setBounds(leftMargin + lwidth + listGap, listy, lwidth, lheight);
      }

      int buttonGap = 10;
      d = this.left.getPreferredSize();
      int bwidth = d.width;
      int bheight = d.height;
      int buttonx = leftMargin + lwidth + 5;
      int buttony = listy + lheight / 2 - bheight - buttonGap / 2;
      if (b) {
         this.right.setBounds(buttonx, buttony, bwidth, bheight);
      }

      buttony = listy + lheight / 2 + buttonGap / 2;
      if (b) {
         this.left.setBounds(buttonx, buttony, bwidth, bheight);
         return null;
      } else {
         Dimension ret = new Dimension();
         ret.width = 0;
         ret.width += leftMargin;
         ret.width += lwidth * 2;
         ret.width += listGap;
         ret.width += rightMargin;
         ret.height = 0;
         ret.height += topMargin;
         ret.height += lblHeight;
         ret.height += 2;
         ret.height += lheight;
         ret.height += bottmMargin;
         return ret;
      }
   }

   private static void p(String s) {
      System.err.println("[DualListPanel]: " + s);
   }

   private JButton makeImageButton(String imgPath) {
      ImageIcon ii = new ImageIcon(Util.loadImage(imgPath));
      JButton ret = new JButton(ii);
      Dimension d = new Dimension(ii.getIconWidth(), ii.getIconHeight());
      ret.setPreferredSize(d);
      return ret;
   }

   public static void main(String[] a) {
      JFrame test = new JFrame("test");
      DualListPanel dual = new DualListPanel("Selected", "Available");
      dual.setTotal(totalData);
      dual.setSelected(selData);
      dual.setBorder(new TitledBorder("Widget Test"));
      test.getContentPane().add(dual);
      test.setLocation(100, 100);
      test.pack();
      test.setVisible(true);
   }
}
