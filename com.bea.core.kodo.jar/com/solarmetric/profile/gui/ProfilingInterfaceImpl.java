package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.ComponentProvider;
import com.solarmetric.ide.ui.ToolbarProvider;
import com.solarmetric.ide.ui.swing.XFileChooser;
import com.solarmetric.ide.ui.swing.XLabel;
import com.solarmetric.ide.ui.swing.XSlider;
import com.solarmetric.ide.ui.swing.XSplitPane;
import com.solarmetric.ide.ui.swing.XToolBar;
import com.solarmetric.ide.util.Dialogs;
import com.solarmetric.ide.util.IconManager;
import com.solarmetric.profile.Node;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingAgentImpl;
import com.solarmetric.profile.ProfilingIO;
import com.solarmetric.profile.ProfilingInterface;
import com.solarmetric.profile.ProfilingLog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Numbers;

public abstract class ProfilingInterfaceImpl implements ProfilingInterface, ComponentProvider, ToolbarProvider, Configurable {
   private static final Localizer s_loc = Localizer.forPackage(ProfilingInterfaceImpl.class);
   private IconManager _localIconManager;
   private IconManager _iconManager;
   private Log _log;
   private JSplitPane _contents;
   private NodePanel _nodePanel;
   private ProfilingAgentImpl _agent;
   private ProfilingTree _tree;
   private boolean _inited;
   private boolean _updatable;
   private JFileChooser _fileChooser;
   private JToolBar _toolbar;
   private JSlider _refreshSlider;
   private RefreshThread _refreshThread;
   protected Action refreshAction;
   protected Action showDescendantsAction;
   protected Map categoryActions;
   protected Action exportAction;
   protected Action resetAction;
   private Configuration _conf;

   public abstract String getVersionString();

   public static ProfilingInterfaceImpl configureInterface(ProfilingAgent agent, boolean updatable, ProfilingInterfaceImpl iface) {
      iface.setUpdatable(updatable);
      iface.setProfilingAgent(agent);
      iface.init();
      return iface;
   }

   public ProfilingInterfaceImpl() {
      this._localIconManager = IconManager.forClass(ProfilingInterfaceImpl.class);
      this._iconManager = IconManager.forClass(this.getClass());
      this._inited = false;
      this._updatable = true;
      this._fileChooser = new XFileChooser();
      this._refreshSlider = new XSlider(1, 26, 10);
      this.categoryActions = new HashMap();
      this._toolbar = new XToolBar(s_loc.get("toolbar-name").getMessage());
   }

   public ProfilingInterfaceImpl(Configuration conf) {
      this();
      this.setConfiguration(conf);
   }

   public void setConfiguration(Configuration conf) {
      this._conf = conf;
      this._log = ProfilingLog.get(conf);
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public Configuration getConfiguration() {
      return this._conf;
   }

   public void setProfilingAgent(ProfilingAgent agent) {
      if (agent == null) {
         throw new IllegalStateException(s_loc.get("null-agent").getMessage());
      } else if (!(agent instanceof ProfilingAgentImpl)) {
         throw new IllegalStateException(s_loc.get("invalid-agent-type", ProfilingAgentImpl.class.getName(), agent.getClass().getName()).getMessage());
      } else {
         if (this._agent == null) {
            this._agent = (ProfilingAgentImpl)agent;
         }

      }
   }

   public void setUpdatable(boolean updatable) {
      this._updatable = updatable;
      if (!updatable) {
         this._refreshSlider.setValue(this._refreshSlider.getMaximum());
      }

   }

   public JComponent getComponent() {
      return this._contents;
   }

   public JToolBar getToolbar() {
      return this._toolbar;
   }

   public void close() {
   }

   public String[] getInitialCategoryNames() {
      return new String[0];
   }

   public void addCategories(String[] names) {
      for(int i = 0; i < names.length; ++i) {
         CategoryAction ca = new CategoryAction(s_loc.get("show-category-action", names[i]).getMessage(), names[i]);

         try {
            ImageIcon icon = this._iconManager.getIcon("category-" + names[i] + ".gif");
            ca.putValue("SmallIcon", icon);
         } catch (Exception var5) {
         }

         ca.setEnabled(true);
         if (!this.categoryActions.containsKey(names[i])) {
            this.categoryActions.put(names[i], ca);
            JButton button = this._toolbar.add(ca);
            setButtonSize(button);
         }
      }

   }

   public void init() {
      if (!this._inited) {
         this._inited = true;
         if (this._agent == null) {
            throw new IllegalStateException(s_loc.get("no-agent").getMessage());
         } else {
            this._nodePanel = new NodePanel();
            ProfilingPanel profPanel = new ProfilingPanel(this._agent, this);
            this._tree = profPanel.getTree();
            profPanel.getTree().addTreeSelectionListener(new TreeSelectionListener() {
               public void valueChanged(TreeSelectionEvent e) {
                  Object o = ProfilingInterfaceImpl.this._tree.getLastSelectedPathComponent();
                  if (o instanceof Node) {
                     ProfilingInterfaceImpl.this._nodePanel.setNode((Node)o);
                  } else {
                     ProfilingInterfaceImpl.this._nodePanel.setNode((Node)null);
                  }

               }
            });
            this._contents = new XSplitPane(1, profPanel, this._nodePanel);
            this._contents.setDividerLocation(350);
            this._contents.setDividerSize(3);
            this._contents.setPreferredSize(new Dimension(900, 600));
            this.initActions();
            this.initSlider();
            this.resetUpdateFrequency();
            JButton button;
            if (this._updatable) {
               button = this._toolbar.add(this.refreshAction);
               setButtonSize(button);
               this._toolbar.add(this._refreshSlider);
            }

            button = this._toolbar.add(this.showDescendantsAction);
            setButtonSize(button);
            button = this._toolbar.add(this.exportAction);
            setButtonSize(button);
            if (this._updatable) {
               button = this._toolbar.add(this.resetAction);
               setButtonSize(button);
            }

            this.addCategories(this.getInitialCategoryNames());
         }
      }
   }

   public void run() {
      Ide ide = this.newIde();
      ide.setInterface(this);
      ide.load();
   }

   public abstract Ide newIde();

   private void initActions() {
      this.refreshAction = new AbstractAction(s_loc.get("refresh-action").getMessage()) {
         public void actionPerformed(ActionEvent e) {
            ProfilingInterfaceImpl.this._tree.updateTree();
            ProfilingInterfaceImpl.this._nodePanel.update();
         }
      };
      ImageIcon icon = this._localIconManager.getIcon("refresh.gif");
      this.refreshAction.putValue("SmallIcon", icon);
      this.refreshAction.putValue("ShortDescription", s_loc.get("refresh-action").getMessage());
      this.showDescendantsAction = new AbstractAction(s_loc.get("show-descendant-action").getMessage()) {
         public void actionPerformed(ActionEvent e) {
            this.setEnabled(false);
            ProfilingInterfaceImpl.this._tree.getProfilingTreeModel().showAncestor(false, (Object)null);
         }
      };
      icon = this._localIconManager.getIcon("show-descendants.gif");
      this.showDescendantsAction.putValue("SmallIcon", icon);
      this.showDescendantsAction.putValue("ShortDescription", s_loc.get("show-descendant-action").getMessage());
      this.showDescendantsAction.setEnabled(this._tree.getProfilingTreeModel().getShowAncestors());
      this.exportAction = new AbstractAction(s_loc.get("export-action").getMessage()) {
         public void actionPerformed(ActionEvent e) {
            if (ProfilingInterfaceImpl.this._fileChooser.showSaveDialog(ProfilingInterfaceImpl.this._contents) == 0) {
               File file = ProfilingInterfaceImpl.this._fileChooser.getSelectedFile();
               if (!file.getName().endsWith(ProfilingInterfaceImpl.s_loc.get("prx-suffix").getMessage())) {
                  file = new File(file.getParentFile(), file.getName() + ProfilingInterfaceImpl.s_loc.get("prx-suffix").getMessage());
               }

               try {
                  ProfilingIO.exportAgent(ProfilingInterfaceImpl.this.getVersionString(), ProfilingInterfaceImpl.this._conf, ProfilingInterfaceImpl.this._agent, file);
               } catch (Exception var4) {
                  Dialogs.showMessageDialog(ProfilingInterfaceImpl.this._contents, ProfilingInterfaceImpl.s_loc.get("cant-export-detail", var4.getMessage()).getMessage(), ProfilingInterfaceImpl.s_loc.get("cant-export-title").getMessage(), 0);
                  if (ProfilingInterfaceImpl.this._log != null) {
                     ProfilingInterfaceImpl.this._log.error(ProfilingInterfaceImpl.s_loc.get("cant-export-log"), var4);
                  } else {
                     var4.printStackTrace();
                  }
               }
            }

         }
      };
      icon = this._localIconManager.getIcon("export.gif");
      this.exportAction.putValue("SmallIcon", icon);
      this.exportAction.putValue("ShortDescription", s_loc.get("export-action").getMessage());
      this.resetAction = new AbstractAction(s_loc.get("reset-action").getMessage()) {
         public void actionPerformed(ActionEvent e) {
            ProfilingInterfaceImpl.this._agent.reset();
            ProfilingInterfaceImpl.this._tree.invalidate();
            ProfilingInterfaceImpl.this._tree.repaint();
         }
      };
      icon = this._localIconManager.getIcon("reset.gif");
      this.resetAction.putValue("SmallIcon", icon);
      this.resetAction.putValue("ShortDescription", s_loc.get("reset-action").getMessage());
   }

   private void initSlider() {
      this._refreshSlider.setMajorTickSpacing(10);
      this._refreshSlider.setSnapToTicks(false);
      this._refreshSlider.setPaintLabels(true);
      Dictionary labels = new Hashtable();
      JLabel label = new XLabel(s_loc.get("slider-fast").getMessage());
      Font font = label.getFont().deriveFont(9.0F);
      label.setFont(font);
      labels.put(Numbers.valueOf(1), label);
      label = new XLabel(s_loc.get("slider-slow").getMessage());
      label.setFont(font);
      labels.put(Numbers.valueOf(this._refreshSlider.getMaximum() * 2 / 3), label);
      label = new XLabel(s_loc.get("slider-never").getMessage());
      label.setFont(font);
      labels.put(Numbers.valueOf(this._refreshSlider.getMaximum()), label);
      this._refreshSlider.setLabelTable(labels);
      Dimension d = this._refreshSlider.getPreferredSize();
      if (d == null) {
         d = new Dimension();
      } else {
         d = new Dimension(d);
      }

      d.setSize(150.0, d.getHeight());
      this._refreshSlider.setMaximumSize(d);
      this._refreshSlider.getModel().addChangeListener(new RefreshSliderChangeListener());
   }

   protected void resetUpdateFrequency() {
      if (this._refreshSlider != null) {
         if (this._refreshSlider.getValue() == this._refreshSlider.getMaximum()) {
            if (this._refreshThread != null) {
               this._refreshThread.setActive(false);
               this._refreshThread = null;
            }
         } else {
            int updateTime = this._refreshSlider.getValue() * 100;
            if (this._refreshThread == null) {
               this._refreshThread = new RefreshThread();
               this._refreshThread.setUpdateFrequency(updateTime);
               this._refreshThread.start();
            } else {
               this._refreshThread.setUpdateFrequency(updateTime);
            }
         }

      }
   }

   public static void setButtonSize(JButton button) {
      Icon icon = button.getIcon();
      if (icon != null) {
         Dimension d = new Dimension(icon.getIconWidth() + 4, icon.getIconHeight() + 4);
         button.setMaximumSize(d);
         button.setPreferredSize(d);
      }

   }

   protected abstract class Ide extends ProfilingIde {
      public Ide() {
         super(ProfilingInterfaceImpl.this._conf);
      }

      public String getName() {
         return "Profiling Console";
      }

      public boolean isUpdatable() {
         return true;
      }

      public ProfilingAgentImpl getAgent() {
         return ProfilingInterfaceImpl.this._agent;
      }

      public ProfilingInterfaceImpl newInterface() {
         throw new IllegalStateException();
      }

      public void load() {
         main(this, new String[0]);
      }
   }

   private class RefreshThread extends Thread {
      private int _updateFrequency;
      private boolean _active;

      private RefreshThread() {
         this._active = true;
         this.setName("Profiling Refresh");
         this.setDaemon(true);
      }

      public synchronized void setUpdateFrequency(int freq) {
         this._updateFrequency = freq;
      }

      public synchronized int getUpdateFrequency() {
         return this._updateFrequency;
      }

      public synchronized void setActive(boolean active) {
         this._active = active;
      }

      public synchronized boolean isActive() {
         return this._active;
      }

      public void run() {
         while(this.isActive()) {
            try {
               ProfilingInterfaceImpl.this._tree.updateTree();
               ProfilingInterfaceImpl.this._nodePanel.update();
            } catch (Exception var3) {
               if (ProfilingInterfaceImpl.this._log.isWarnEnabled()) {
                  ProfilingInterfaceImpl.this._log.warn(ProfilingInterfaceImpl.s_loc.get("refresh-thread-error"), var3);
               }
            }

            try {
               Thread.sleep((long)this.getUpdateFrequency());
            } catch (InterruptedException var2) {
            }
         }

      }

      // $FF: synthetic method
      RefreshThread(Object x1) {
         this();
      }
   }

   private class RefreshSliderChangeListener implements ChangeListener {
      private RefreshSliderChangeListener() {
      }

      public void stateChanged(ChangeEvent e) {
         if (e.getSource() instanceof BoundedRangeModel && !((BoundedRangeModel)e.getSource()).getValueIsAdjusting()) {
            ProfilingInterfaceImpl.this.resetUpdateFrequency();
         }

      }

      // $FF: synthetic method
      RefreshSliderChangeListener(Object x1) {
         this();
      }
   }

   private static class CumulativeComparator implements Comparator {
      private CumulativeComparator() {
      }

      public int compare(Object o1, Object o2) {
         if (o1 instanceof Node && o2 instanceof Node) {
            double sum1 = ((Node)o1).getStatistic().getSampleSum();
            double sum2 = ((Node)o2).getStatistic().getSampleSum();
            if (sum1 == sum2) {
               return 0;
            } else {
               return sum1 < sum2 ? 1 : -1;
            }
         } else {
            return 0;
         }
      }

      // $FF: synthetic method
      CumulativeComparator(Object x0) {
         this();
      }
   }

   private class CategoryAction extends AbstractAction {
      private String _category;

      CategoryAction(String description, String category) {
         super(description);
         this._category = category;
      }

      public void actionPerformed(ActionEvent e) {
         List l = new ArrayList();
         Iterator iter = ProfilingInterfaceImpl.this._agent.getAllNodes().iterator();

         while(iter.hasNext()) {
            Node n = (Node)iter.next();
            if (this._category.equals(n.getInfo().getCategory())) {
               l.add(n);
            }
         }

         Collections.sort(l, new CumulativeComparator());
         ProfilingInterfaceImpl.this._tree.getProfilingTreeModel().showAncestor(true, l);
         ProfilingInterfaceImpl.this.showDescendantsAction.setEnabled(true);
      }
   }
}
