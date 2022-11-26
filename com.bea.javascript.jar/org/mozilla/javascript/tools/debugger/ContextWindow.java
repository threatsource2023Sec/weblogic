package org.mozilla.javascript.tools.debugger;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventListener;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeCall;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.debug.DebuggableEngine;

class ContextWindow extends JPanel implements ActionListener {
   JComboBox context;
   Vector toolTips;
   JTabbedPane tabs;
   JTabbedPane tabs2;
   MyTreeTable thisTable;
   MyTreeTable localsTable;
   VariableModel model;
   MyTableModel tableModel;
   Evaluator evaluator;
   EvalTextArea cmdLine;
   JSplitPane split;
   Main db;
   boolean enabled;
   // $FF: synthetic field
   static Class class$java$awt$event$WindowListener;

   ContextWindow(final Main var1) {
      this.db = var1;
      this.enabled = false;
      JPanel var2 = new JPanel();
      final JToolBar var3 = new JToolBar();
      var3.setName("Variables");
      var3.setLayout(new GridLayout());
      var3.add(var2);
      final JPanel var4 = new JPanel();
      var4.setLayout(new GridLayout());
      final JPanel var5 = new JPanel();
      var5.setLayout(new GridLayout());
      var4.add(var3);
      JLabel var6 = new JLabel("Context:");
      this.context = new JComboBox();
      this.context.setLightWeightPopupEnabled(false);
      this.toolTips = new Vector();
      var6.setBorder(this.context.getBorder());
      this.context.addActionListener(this);
      this.context.setActionCommand("ContextSwitch");
      GridBagLayout var7 = new GridBagLayout();
      var2.setLayout(var7);
      GridBagConstraints var8 = new GridBagConstraints();
      var8.insets.left = 5;
      var8.anchor = 17;
      var8.ipadx = 5;
      var7.setConstraints(var6, var8);
      var2.add(var6);
      GridBagConstraints var9 = new GridBagConstraints();
      var9.gridwidth = 0;
      var9.fill = 2;
      var9.anchor = 17;
      var7.setConstraints(this.context, var9);
      var2.add(this.context);
      this.tabs = new JTabbedPane(3);
      this.tabs.setPreferredSize(new Dimension(500, 300));
      this.thisTable = new MyTreeTable(new AbstractTreeTableModel(new DefaultMutableTreeNode()) {
         public Object getChild(Object var1, int var2) {
            return null;
         }

         public int getChildCount(Object var1) {
            return 0;
         }

         public int getColumnCount() {
            return 2;
         }

         public String getColumnName(int var1) {
            switch (var1) {
               case 0:
                  return " Name";
               case 1:
                  return " Value";
               default:
                  return null;
            }
         }

         public Object getValueAt(Object var1, int var2) {
            return null;
         }
      });
      JScrollPane var10 = new JScrollPane(this.thisTable);
      var10.getViewport().setViewSize(new Dimension(5, 2));
      this.tabs.add("this", var10);
      this.localsTable = new MyTreeTable(new AbstractTreeTableModel(new DefaultMutableTreeNode()) {
         public Object getChild(Object var1, int var2) {
            return null;
         }

         public int getChildCount(Object var1) {
            return 0;
         }

         public int getColumnCount() {
            return 2;
         }

         public String getColumnName(int var1) {
            switch (var1) {
               case 0:
                  return " Name";
               case 1:
                  return " Value";
               default:
                  return null;
            }
         }

         public Object getValueAt(Object var1, int var2) {
            return null;
         }
      });
      this.localsTable.setAutoResizeMode(4);
      this.localsTable.setPreferredSize((Dimension)null);
      var10 = new JScrollPane(this.localsTable);
      this.tabs.add("Locals", var10);
      var9.weightx = var9.weighty = 1.0;
      var9.gridheight = 0;
      var9.fill = 1;
      var9.anchor = 17;
      var7.setConstraints(this.tabs, var9);
      var2.add(this.tabs);
      this.evaluator = new Evaluator(var1);
      this.cmdLine = new EvalTextArea(var1);
      this.tableModel = this.evaluator.tableModel;
      var10 = new JScrollPane(this.evaluator);
      final JToolBar var11 = new JToolBar();
      var11.setName("Evaluate");
      this.tabs2 = new JTabbedPane(3);
      this.tabs2.add("Watch", var10);
      this.tabs2.add("Evaluate", new JScrollPane(this.cmdLine));
      this.tabs2.setPreferredSize(new Dimension(500, 300));
      var11.setLayout(new GridLayout());
      var11.add(this.tabs2);
      var5.add(var11);
      this.evaluator.setAutoResizeMode(4);
      this.split = new JSplitPane(1, var4, var5);
      this.split.setOneTouchExpandable(true);
      Main.setResizeWeight(this.split, 0.5);
      this.setLayout(new BorderLayout());
      this.add(this.split, "Center");
      final JSplitPane var16 = this.split;
      ComponentListener var19 = new ComponentListener() {
         boolean t1Docked = true;
         boolean t2Docked = true;

         void check(Component var1x) {
            Container var2 = ContextWindow.this.getParent();
            if (var2 != null) {
               Container var3x = var3.getParent();
               boolean var4x = true;
               boolean var5x = true;
               boolean var6 = false;
               JFrame var7;
               if (var3x != null) {
                  if (var3x == var4) {
                     var4x = true;
                  } else {
                     while(!(var3x instanceof JFrame)) {
                        var3x = var3x.getParent();
                     }

                     var7 = (JFrame)var3x;
                     var1.addTopLevel("Variables", var7);
                     if (!var7.isResizable()) {
                        var7.setResizable(true);
                        var7.setDefaultCloseOperation(0);
                        final EventListener[] var8 = var7.getListeners(ContextWindow.class$java$awt$event$WindowListener != null ? ContextWindow.class$java$awt$event$WindowListener : (ContextWindow.class$java$awt$event$WindowListener = ContextWindow.class$("java.awt.event.WindowListener")));
                        var7.removeWindowListener((WindowListener)var8[0]);
                        var7.addWindowListener(new WindowAdapter() {
                           public void windowClosing(WindowEvent var1x) {
                              ContextWindow.this.context.hidePopup();
                              ((WindowListener)var8[0]).windowClosing(var1x);
                           }
                        });
                     }

                     var4x = false;
                  }
               }

               var3x = var11.getParent();
               if (var3x != null) {
                  if (var3x == var5) {
                     var5x = true;
                  } else {
                     while(!(var3x instanceof JFrame)) {
                        var3x = var3x.getParent();
                     }

                     var7 = (JFrame)var3x;
                     var1.addTopLevel("Evaluate", var7);
                     var7.setResizable(true);
                     var5x = false;
                  }
               }

               if (!var4x || !this.t2Docked || !var5x || !this.t2Docked) {
                  this.t1Docked = var4x;
                  this.t2Docked = var5x;
                  JSplitPane var9 = (JSplitPane)var2;
                  if (var4x) {
                     if (var5x) {
                        var16.setDividerLocation(0.5);
                     } else {
                        var16.setDividerLocation(1.0);
                     }

                     if (var6) {
                        var9.setDividerLocation(0.66);
                     }
                  } else if (var5x) {
                     var16.setDividerLocation(0.0);
                     var9.setDividerLocation(0.66);
                  } else {
                     var9.setDividerLocation(1.0);
                  }

               }
            }
         }

         public void componentHidden(ComponentEvent var1x) {
            this.check(var1x.getComponent());
         }

         public void componentMoved(ComponentEvent var1x) {
            this.check(var1x.getComponent());
         }

         public void componentResized(ComponentEvent var1x) {
            this.check(var1x.getComponent());
         }

         public void componentShown(ComponentEvent var1x) {
            this.check(var1x.getComponent());
         }
      };
      var4.addContainerListener(new ContainerListener() {
         public void componentAdded(ContainerEvent var1) {
            Container var2 = ContextWindow.this.getParent();
            JSplitPane var3x = (JSplitPane)var2;
            if (var1.getChild() == var3) {
               if (var11.getParent() == var5) {
                  var16.setDividerLocation(0.5);
               } else {
                  var16.setDividerLocation(1.0);
               }

               var3x.setDividerLocation(0.66);
            }

         }

         public void componentRemoved(ContainerEvent var1) {
            Container var2 = ContextWindow.this.getParent();
            JSplitPane var3x = (JSplitPane)var2;
            if (var1.getChild() == var3) {
               if (var11.getParent() == var5) {
                  var16.setDividerLocation(0.0);
                  var3x.setDividerLocation(0.66);
               } else {
                  var3x.setDividerLocation(1.0);
               }
            }

         }
      });
      var3.addComponentListener(var19);
      var11.addComponentListener(var19);
      this.disable();
   }

   public void actionPerformed(ActionEvent var1) {
      if (this.enabled) {
         if (var1.getActionCommand().equals("ContextSwitch")) {
            ContextHelper var2 = new ContextHelper();
            Context var3 = this.db.getCurrentContext();
            DebuggableEngine var4 = var3.getDebuggableEngine();
            var2.attach(var3);
            int var5 = this.context.getSelectedIndex();
            this.context.setToolTipText(this.toolTips.elementAt(var5).toString());
            int var7 = var4.getFrameCount();
            if (var5 >= var7) {
               var2.reset();
               return;
            }

            Scriptable var6 = var4.getFrame(var5).getVariableObject();
            NativeCall var8 = null;
            if (var6 instanceof NativeCall) {
               var8 = (NativeCall)var6;
               var6 = var8.getThisObj();
            }

            this.thisTable.resetTree(this.model = new VariableModel(var6));
            if (var8 == null) {
               JTree var9 = this.localsTable.resetTree(new AbstractTreeTableModel(new DefaultMutableTreeNode()) {
                  public Object getChild(Object var1, int var2) {
                     return null;
                  }

                  public int getChildCount(Object var1) {
                     return 0;
                  }

                  public int getColumnCount() {
                     return 2;
                  }

                  public String getColumnName(int var1) {
                     switch (var1) {
                        case 0:
                           return " Name";
                        case 1:
                           return " Value";
                        default:
                           return null;
                     }
                  }

                  public Object getValueAt(Object var1, int var2) {
                     return null;
                  }
               });
            } else {
               this.localsTable.resetTree(this.model = new VariableModel(var8));
            }

            var2.reset();
            this.db.contextSwitch(var5);
            this.tableModel.updateModel();
         }

      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public void disable() {
      this.context.setEnabled(false);
      this.thisTable.setEnabled(false);
      this.localsTable.setEnabled(false);
      this.evaluator.setEnabled(false);
      this.cmdLine.setEnabled(false);
   }

   public void disableUpdate() {
      this.enabled = false;
   }

   public void enable() {
      this.context.setEnabled(true);
      this.thisTable.setEnabled(true);
      this.localsTable.setEnabled(true);
      this.evaluator.setEnabled(true);
      this.cmdLine.setEnabled(true);
   }

   public void enableUpdate() {
      this.enabled = true;
   }
}
