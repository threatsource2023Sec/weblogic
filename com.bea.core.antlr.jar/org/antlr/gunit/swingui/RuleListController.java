package org.antlr.gunit.swingui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import org.antlr.gunit.swingui.model.Rule;
import org.antlr.gunit.swingui.model.TestSuite;

public class RuleListController implements IController {
   private final JList list = new JList();
   private final JScrollPane scroll;
   private ListModel model;
   private TestSuite testSuite;

   public RuleListController() {
      this.scroll = new JScrollPane(this.list, 22, 30);
      this.model = null;
      this.testSuite = null;
      this.initComponents();
   }

   public JScrollPane getView() {
      return this.scroll;
   }

   private void setTestSuite(TestSuite newTestSuite) {
      this.testSuite = newTestSuite;
      this.model = new RuleListModel();
      this.list.setModel(this.model);
   }

   public void initialize(TestSuite ts) {
      this.setTestSuite(ts);
      if (this.model.getSize() > 0) {
         this.list.setSelectedIndex(0);
      }

      this.list.updateUI();
   }

   private void initComponents() {
      this.scroll.setViewportBorder(BorderFactory.createEtchedBorder());
      this.scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Rules"));
      this.scroll.setOpaque(false);
      this.list.setOpaque(false);
      this.list.setSelectionMode(1);
      this.list.setLayoutOrientation(0);
      this.list.setCellRenderer(new RuleListItemRenderer());
   }

   public void setListSelectionListener(ListSelectionListener l) {
      this.list.addListSelectionListener(l);
   }

   public Object getModel() {
      return this.model;
   }

   private class RuleListModel implements ListModel {
      public RuleListModel() {
         if (RuleListController.this.testSuite == null) {
            throw new NullPointerException("Null test suite");
         }
      }

      public int getSize() {
         return RuleListController.this.testSuite.getRuleCount();
      }

      public Object getElementAt(int index) {
         return RuleListController.this.testSuite.getRule(index);
      }

      public void addListDataListener(ListDataListener l) {
      }

      public void removeListDataListener(ListDataListener l) {
      }
   }

   private class RuleListItemRenderer extends JLabel implements ListCellRenderer {
      public RuleListItemRenderer() {
         this.setPreferredSize(new Dimension(50, 18));
      }

      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
         if (value instanceof Rule) {
            Rule item = (Rule)value;
            this.setText(item.toString());
            this.setForeground(list.getForeground());
            this.setIcon(item.getNotEmpty() ? ImageFactory.getSingleton().FAV16 : null);
            if (list.getSelectedValue() == item) {
               this.setBackground(Color.LIGHT_GRAY);
               this.setOpaque(true);
            } else {
               this.setOpaque(false);
            }
         } else {
            this.setText("Error!");
         }

         return this;
      }
   }
}
