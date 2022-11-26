package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.Ide;
import com.solarmetric.ide.IdeConstants;
import com.solarmetric.ide.ui.ComponentProvider;
import com.solarmetric.ide.ui.SMenu;
import com.solarmetric.ide.ui.ToolbarProvider;
import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XSplitPane;
import com.solarmetric.ide.util.IconManager;
import com.solarmetric.ide.util.SplashScreen;
import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.jmx.NotificationDispatchListener;
import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class JMXGui extends Ide implements JMXInterface {
   private static final Localizer s_loc = Localizer.forPackage(JMXGui.class);
   private IconManager _iconManager = IconManager.forClass(this.getClass());
   private Log _log;
   private MBeanServer _mbeanServer;
   private boolean _inited = false;
   private HashMap _mbeanPanels = new HashMap();
   private HashMap _dashboardPanels = new HashMap();
   private JMXTree _jmxTree;
   private Configuration _conf;
   private static String[] _connActionNames = new String[]{"com.solarmetric.manage.jmx.gui.remote.mx4j1.MX4JConnectAction", "com.solarmetric.manage.jmx.gui.remote.jmx2.JMX2ConnectAction", "com.solarmetric.manage.jmx.gui.remote.jboss322.JBossConnectAction", "com.solarmetric.manage.jmx.gui.remote.jboss4.JBossConnectAction", "com.solarmetric.manage.jmx.gui.remote.wl81.WL81ConnectAction"};

   public JMXGui(Configuration conf) {
      this._conf = conf;
      this._log = ManagementLog.get(conf);
   }

   public Configuration getConfiguration() {
      return this._conf;
   }

   public ImageIcon getSplashScreen() {
      return this._iconManager.getIcon("solarmetric.gif");
   }

   public String getName() {
      return "JMX Viewer";
   }

   public void initialize(JComponent container, SplashScreen status) {
      if (!this._inited) {
         this._inited = true;
         this._jmxTree = new JMXTree(false, this._log);
         SMenu file = this.getManager().getMenu().get(IdeConstants.FILE);
         ArrayList connActions = new ArrayList();

         for(int i = 0; i < _connActionNames.length; ++i) {
            AbstractAction connAction = this.createConnAction(_connActionNames[i]);
            if (connAction != null) {
               connActions.add(connAction);
               file.addItem(connAction);
            }
         }

         this.addToolbar(s_loc.get("toolbar-name").getMessage(), (Action[])((Action[])connActions.toArray(new Action[connActions.size()])));
         if (this._mbeanServer != null) {
            this._jmxTree.add(this._mbeanServer, "Primary MBeanServer");
         }

         JSplitPane splitPane = (JSplitPane)container;
         this._jmxTree.addTreeSelectionListener(new JMXTSL(splitPane));
         splitPane.setLeftComponent(new XScrollPane(this._jmxTree));
         splitPane.setRightComponent(new XScrollPane(new XPanel()));
         splitPane.setDividerLocation(275);
         splitPane.setDividerSize(3);
         splitPane.setPreferredSize(new Dimension(900, 600));
      }
   }

   protected void shutdown() {
      this._jmxTree.close();
   }

   public JComponent newContentContainer() {
      JComponent c = new XSplitPane(1);
      c.setPreferredSize(this.getDefaultFrameSize());
      return c;
   }

   public Dimension getDefaultFrameSize() {
      return new Dimension(780, 580);
   }

   public void setMBeanServer(MBeanServer mbeanServer) throws Exception {
      if (this._mbeanServer != null) {
         throw new Exception(s_loc.get("mbeanserver-already-reg").getMessage());
      } else {
         this._mbeanServer = mbeanServer;
      }
   }

   public void setConfiguration(Configuration conf) {
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   private ComponentProvider createMBeanViewer(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo, NotificationDispatchListener dispatcher) {
      MBeanAttributeInfo[] attrs = mbInfo.getAttributes();
      String viewerClassName = null;

      for(int i = 0; i < attrs.length; ++i) {
         if (attrs[i].getName().equals("CustomMBeanViewerName")) {
            try {
               viewerClassName = (String)server.getAttribute(instance.getObjectName(), attrs[i].getName());
               break;
            } catch (Exception var12) {
               this._log.warn(var12);
            }
         }
      }

      if (viewerClassName != null) {
         try {
            Class customViewer = Class.forName(viewerClassName);
            Constructor viewerConstr = customViewer.getConstructor(MBeanServer.class, ObjectInstance.class, MBeanInfo.class, NotificationDispatchListener.class, Configuration.class);
            Object o = viewerConstr.newInstance(server, instance, mbInfo, dispatcher, this._conf);
            if (o instanceof Configurable) {
               Configurable oconf = (Configurable)o;
               oconf.setConfiguration(this._conf);
               oconf.startConfiguration();
               oconf.endConfiguration();
            }

            if (!(o instanceof ComponentProvider)) {
               o = new SimpleComponentProvider((JComponent)o);
            }

            return (ComponentProvider)o;
         } catch (Exception var11) {
            this._log.warn(var11);
         }
      }

      return new SimpleComponentProvider(new MBeanPanel(server, instance, mbInfo, dispatcher, this._conf));
   }

   private ComponentProvider createDashboardViewer(MBeanServer server, DashboardMetaData dashboard) {
      return new SimpleComponentProvider(new DashboardPanel(server, dashboard, this._jmxTree, this._conf));
   }

   public void run() {
      main(this, new String[0]);
   }

   private AbstractAction createConnAction(String classname) {
      try {
         Class actionClass = Class.forName(classname);
         Constructor actionConstructor = actionClass.getConstructor(JMXTree.class, Log.class);
         AbstractAction connAction = (AbstractAction)actionConstructor.newInstance(this._jmxTree, this._log);
         return connAction;
      } catch (Exception var5) {
         this._log.warn(s_loc.get("cant-create-connection", classname), var5);
         return null;
      }
   }

   private static class SimpleComponentProvider implements ComponentProvider {
      private JComponent _component;

      private SimpleComponentProvider(JComponent comp) {
         this._component = comp;
      }

      public JComponent getComponent() {
         return this._component;
      }

      // $FF: synthetic method
      SimpleComponentProvider(JComponent x0, Object x1) {
         this(x0);
      }
   }

   private class JMXTSL implements TreeSelectionListener {
      private JSplitPane _splitPane;
      private ComponentProvider _currentComponentProvider;

      private JMXTSL(JSplitPane splitPane) {
         this._splitPane = splitPane;
      }

      public void valueChanged(TreeSelectionEvent e) {
         MBeanServer server;
         if (e.getPath().getLastPathComponent() instanceof MBeanTreeNode) {
            MBeanTreeNode node = (MBeanTreeNode)e.getPath().getLastPathComponent();
            server = node.getServer();
            ObjectInstance instance = node.getInstance();
            MBeanInfo mbInfo = node.getMBeanInfo();
            NotificationDispatchListener d = node.getDispatcher();
            if (JMXGui.this._mbeanPanels.containsKey(node)) {
               this.setRightComponent((ComponentProvider)JMXGui.this._mbeanPanels.get(node));
            } else {
               ComponentProvider cx = JMXGui.this.createMBeanViewer(server, instance, mbInfo, d);
               this.setRightComponent(cx);
               JMXGui.this._mbeanPanels.put(node, cx);
               MBeanNotificationInfo[] notifInfos = mbInfo.getNotifications();

               for(int i = 0; i < notifInfos.length; ++i) {
                  d.activateNotificationTypes(notifInfos[i], notifInfos[i].getNotifTypes());
               }
            }
         } else if (e.getPath().getLastPathComponent() instanceof MDashboardTreeNode) {
            MDashboardTreeNode nodex = (MDashboardTreeNode)e.getPath().getLastPathComponent();
            if (JMXGui.this._dashboardPanels.containsKey(nodex)) {
               this.setRightComponent((ComponentProvider)JMXGui.this._dashboardPanels.get(nodex));
            } else {
               server = nodex.getServer();
               DashboardMetaData dashboard = nodex.getDashboard();
               ComponentProvider c = JMXGui.this.createDashboardViewer(server, dashboard);
               this.setRightComponent(c);
               JMXGui.this._dashboardPanels.put(nodex, c);
            }
         }

      }

      private void setRightComponent(ComponentProvider p) {
         if (this._currentComponentProvider instanceof ToolbarProvider) {
            JMXGui.this.removeToolbar(((ToolbarProvider)this._currentComponentProvider).getToolbar());
         }

         this._currentComponentProvider = p;
         this._splitPane.setRightComponent(p.getComponent());
         if (p instanceof ToolbarProvider) {
            JMXGui.this.addToolbar(((ToolbarProvider)p).getToolbar());
         }

         int loc = this._splitPane.getDividerLocation();
         this._splitPane.setDividerLocation(loc);
      }

      // $FF: synthetic method
      JMXTSL(JSplitPane x1, Object x2) {
         this(x1);
      }
   }
}
