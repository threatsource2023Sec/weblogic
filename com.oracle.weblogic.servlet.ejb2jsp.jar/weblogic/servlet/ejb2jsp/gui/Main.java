package weblogic.servlet.ejb2jsp.gui;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import weblogic.servlet.ejb2jsp.Utils;
import weblogic.servlet.ejb2jsp.dd.BeanDescriptor;
import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.servlet.ejb2jsp.dd.EJBTaglibDescriptor;
import weblogic.servlet.ejb2jsp.dd.FilesystemInfoDescriptor;
import weblogic.servlet.ejb2jsp.dd.MethodParamDescriptor;
import weblogic.tools.ui.AWTUtils;
import weblogic.tools.ui.GUIPrintStream;
import weblogic.utils.io.XMLWriter;

public class Main implements TreeSelectionListener, ActionListener, WindowListener {
   public static final boolean debug = false;
   private Stack tasks = new Stack();
   EJBTaglibDescriptor currentBean;
   Hashtable panels = new Hashtable();
   String[] cmdLine;
   JScrollPane left;
   JScrollPane right;
   JFrame frame;
   JTree tree;
   JSplitPane split;
   JMenuBar mb;
   JMenu menu;
   JPanel splash;
   JTextArea compileTA;
   GUIPrintStream guiPS;
   Prefs prefs;
   JMenuItem saveMenu;
   JMenuItem saveAsMenu;
   JMenuItem quitMenu;
   JMenuItem openMenu;
   JMenuItem newMenu;
   JMenuItem compileMenu;
   JMenuItem resolveMenu;
   JMenuItem prefsMenu;
   JMenuItem helpMenu;
   JFrame docFrame;
   HelpFrame contextHelp;
   Image frameIcon = null;
   private static Main theMain;
   private boolean dirty = false;
   File currentDirectory = new File(".");
   File currentProject = null;

   static void p(String s) {
   }

   private BasePanel currentPanel() {
      JViewport vp = this.right.getViewport();
      Component comp = vp.getView();
      if (comp == null) {
         return null;
      } else {
         return comp instanceof BasePanel ? (BasePanel)comp : null;
      }
   }

   private void setClean() {
      if (this.currentPanel() != null) {
         this.currentPanel().setDirty(false);
      }

   }

   private void fields2bean() {
      BasePanel bp = this.currentPanel();
      if (bp != null) {
         try {
            bp.fields2bean();
            boolean b = bp.isDirty();
            if (b) {
               p("panel " + bp.getClass().getName() + " says he's dirty");
            }

            this.dirty |= b;
         } catch (Exception var3) {
            this.displayException(var3);
         }

      }
   }

   private void objectSelected(Object o) throws Exception {
      Class c = o.getClass();
      if (!c.isArray()) {
         JViewport vp = this.right.getViewport();
         Component comp = null;
         if (c == FilesystemInfoDescriptor.class) {
            FilesystemInfoDescriptor bean = (FilesystemInfoDescriptor)o;
            comp = new FilesystemInfoDescriptorPanel(bean);
            vp.setView((Component)null);
            vp.setView(comp);
         } else if (c == BeanDescriptor.class) {
            BeanDescriptor bean = (BeanDescriptor)o;
            Component comp = new BeanDescriptorPanel(bean);
            vp.setView((Component)null);
            vp.setView(comp);
         } else if (c == EJBTaglibDescriptor.class) {
            EJBTaglibDescriptor bean = (EJBTaglibDescriptor)o;
            Component comp = new EJBTaglibDescriptorPanel(bean);
            vp.setView((Component)null);
            vp.setView(comp);
         } else if (c == MethodParamDescriptor.class) {
            MethodParamDescriptor bean = (MethodParamDescriptor)o;
            Component comp = new MethodParamDescriptorPanel(bean);
            vp.setView((Component)null);
            vp.setView(comp);
         } else if (c == EJBMethodDescriptor.class) {
            EJBMethodDescriptor bean = (EJBMethodDescriptor)o;
            Component comp = new EJBMethodDescriptorPanel(bean);
            vp.setView((Component)null);
            vp.setView(comp);
         } else {
            System.err.println("ERROR: I don't understand type " + c.getName());
         }

      }
   }

   private void objectDeselected(Object o) throws Exception {
      Class c = o.getClass();
      if (!c.isArray()) {
         JViewport vp = this.right.getViewport();
         Component comp = vp.getView();
         if (comp != null && comp instanceof BasePanel) {
            BasePanel bp = null;
            bp = (BasePanel)comp;
            bp.fields2bean();
         }

      }
   }

   public void valueChanged(TreeSelectionEvent e) {
      TreePath oldPath = e.getOldLeadSelectionPath();
      TreePath newPath = e.getNewLeadSelectionPath();
      Object oldObject = null;
      Object newObject = null;
      DefaultMutableTreeNode node = null;
      if (oldPath != null) {
         node = (DefaultMutableTreeNode)oldPath.getLastPathComponent();
         if (node != null) {
            oldObject = node.getUserObject();
         }
      }

      if (newPath != null) {
         node = (DefaultMutableTreeNode)newPath.getLastPathComponent();
         if (node != null) {
            newObject = node.getUserObject();
         }
      }

      if (oldObject != null) {
         p("deselected type: " + oldObject.getClass().getName());

         try {
            this.objectDeselected(oldObject);
         } catch (Exception var9) {
            this.displayException(var9);
         }
      }

      if (newObject != null) {
         p("selected type: " + newObject.getClass().getName());

         try {
            this.objectSelected(newObject);
         } catch (Exception var8) {
            this.displayException(var8);
         }
      }

   }

   public Main(String[] a) {
      this.cmdLine = a;
      Thread t = new Thread(new MainWorker(this), "MainGuiWorker");
      t.start();
   }

   private static void usage() {
      System.err.println("usage: Main <ejb-jar-file> <ejb-source-dir>, -or-");
      System.err.println("       Main <ejb2jsp-project-file>");
   }

   EJBTaglibDescriptor loadRootBean() throws Exception {
      if (this.cmdLine != null && this.cmdLine.length != 0) {
         if (this.cmdLine.length == 1) {
            if (this.cmdLine[0].endsWith(".ejb2jsp")) {
               return this.loadBeanFromFile(new File(this.cmdLine[0]));
            }

            if (this.cmdLine[0].endsWith(".jar")) {
               File ejbjar = new File(this.cmdLine[0]);
               ejbjar = new File(ejbjar.getAbsolutePath());
               return this.loadFromPaths(ejbjar.getAbsolutePath(), ejbjar.getParent());
            }

            usage();
         }

         if (this.cmdLine.length != 2) {
            usage();
            return null;
         } else {
            try {
               return this.loadFromPaths(this.cmdLine[0], this.cmdLine[1]);
            } catch (Exception var2) {
               this.displayException(var2);
               return null;
            }
         }
      } else {
         return null;
      }
   }

   EJBTaglibDescriptor loadBeanFromFile(File f) throws Exception {
      EJBTaglibDescriptor dd = EJBTaglibDescriptor.load(f);
      this.currentProject = f;
      return dd;
   }

   public void pushTask(Runnable r) {
      synchronized(this.tasks) {
         this.tasks.push(r);
         this.tasks.notify();
      }
   }

   public Runnable getTask() throws InterruptedException {
      synchronized(this.tasks) {
         while(this.tasks.isEmpty()) {
            this.tasks.wait();
         }

         Runnable r = (Runnable)this.tasks.firstElement();
         this.tasks.removeElementAt(0);
         return r;
      }
   }

   private boolean askToSave() {
      this.fields2bean();
      if (!this.dirty) {
         return true;
      } else {
         int opt = JOptionPane.showConfirmDialog(this.frame, "Changes to the current project have not been saved.  Do you want to save now?", "Confirm Exit", 1, 1);
         if (opt == 1) {
            return true;
         } else if (opt == 2) {
            return false;
         } else {
            if (this.saveMenu.isEnabled()) {
               this.doSave();
            } else {
               this.doSaveAs();
            }

            return true;
         }
      }
   }

   public void doSave() {
      this.saveCurrentBean();
   }

   private void saveCurrentBean() {
      this.fields2bean();
      XMLWriter x = null;

      try {
         FileOutputStream fos = new FileOutputStream(this.currentProject);
         x = new XMLWriter(fos);
         this.currentBean.toXML(x);
         x.flush();
         x.close();
         fos.close();
         this.dirty = false;
         x = null;
      } catch (Exception var11) {
         this.displayException(var11);
      } finally {
         if (x != null) {
            try {
               x.close();
            } catch (Exception var10) {
            }
         }

      }

   }

   public void doCompile() {
      int i = 0;
      p("docompile " + i++);
      this.fields2bean();
      p("docompile " + i++);
      GUIPrintStream gs = this.getGUIPrintStream();
      p("docompile " + i++);

      try {
         p("docompile " + i++);
         this.right.getViewport().setView(this.compileTA);
         p("docompile " + i++);
         PrintStream ps = new PrintStream(gs);
         p("docompile " + i++);
         Utils.compile(this.currentBean, ps);
         p("docompile " + i++);
         ps.flush();
         p("docompile " + i++);
      } catch (Exception var4) {
         this.displayException(var4);
      }

   }

   public void doSaveAs() {
      try {
         JFileChooser ch = new JFileChooser(this.currentDirectory);
         ch.setFileFilter(new EJB2JSPFileChooser());
         boolean approved = false;

         do {
            int op = ch.showDialog(this.frame, "Save ejb2jsp project");
            if (op == 1) {
               return;
            }

            this.currentDirectory = ch.getCurrentDirectory();
            File project = ch.getSelectedFile();
            String name = project.getName();
            if (name != null) {
               if (!name.endsWith(".ejb2jsp")) {
                  name = name + ".ejb2jsp";
                  project = new File(new File(project.getParent()), name);
               }

               if (project.exists()) {
                  op = JOptionPane.showConfirmDialog(this.frame, "A file called \"" + name + "\" already exists.  Do you want to overwrite it?", "Confirm file overwrite", 0, 3);
                  if (op == 1) {
                     continue;
                  }
               }

               this.currentProject = project;
               this.saveCurrentBean();
               this.saveMenu.setEnabled(true);
               approved = true;
            }
         } while(!approved);
      } catch (Exception var6) {
         this.displayException(var6);
      }

   }

   private void doExit() {
      try {
         this.prefs.save();
      } catch (Exception var5) {
         var5.printStackTrace();
      } finally {
         System.exit(0);
      }

   }

   public void doQuit() {
      if (this.askToSave()) {
         this.doExit();
      }

   }

   public void doOpen() {
      p("doOpen");

      try {
         JFileChooser ch = new JFileChooser(this.currentDirectory);
         ch.setFileFilter(new EJB2JSPFileChooser());
         int op = ch.showDialog(this.frame, "Open ejb2jsp project");
         if (op == 0) {
            this.currentDirectory = ch.getCurrentDirectory();
            File project = ch.getSelectedFile();
            EJBTaglibDescriptor opened = this.loadBeanFromFile(project);
            this.setRootBean(opened);
            this.currentProject = project;
            this.saveMenu.setEnabled(true);
            this.saveAsMenu.setEnabled(true);
            this.compileMenu.setEnabled(true);
         }
      } catch (Exception var5) {
         this.displayException(var5);
      }

   }

   public void doResolve() {
      try {
         Utils.resolveSources(this.currentBean);
      } catch (Exception var2) {
      }

   }

   public void doHelp() {
      try {
         this.initHelp();
         if (this.docFrame != null) {
            this.docFrame.setVisible(true);
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void doPrefs() {
      try {
         PreferencesDialog pd = new PreferencesDialog(this.frame, "Preferences", true, this.prefs);
         pd.pack();
         AWTUtils.centerOnWindow(pd, this.frame);
         pd.show();
         this.prefs = pd.getPrefs();
      } catch (Exception var2) {
         this.displayException(var2);
      }

   }

   public void doNew() {
      try {
         JFileChooser ch = new JFileChooser(this.currentDirectory);
         ch.setFileFilter(new JarFileChooser());
         int op = ch.showDialog(this.frame, "Open EJB Jar File");
         if (op == 1) {
            return;
         }

         this.currentDirectory = ch.getCurrentDirectory();
         File sourcePath = this.currentDirectory;
         File ejbJar = ch.getSelectedFile();
         this.currentBean = this.loadFromPaths(ejbJar.getAbsolutePath(), sourcePath.getAbsolutePath());
         this.currentBean.getFileInfo().setJavacPath(this.prefs.compiler);
      } catch (Exception var5) {
         this.displayException(var5);
      }

   }

   private EJBTaglibDescriptor loadFromPaths(String ejbjar, String sourceDir) throws Exception {
      try {
         if (this.prefs.sourceDir != null && (this.prefs.sourceDir = this.prefs.sourceDir.trim()).length() > 0) {
            sourceDir = this.prefs.sourceDir + File.pathSeparator + sourceDir;
         }

         EJBTaglibDescriptor newBean = Utils.createDefaultDescriptor(ejbjar, sourceDir, this.prefs.webapp);
         this.setRootBean(newBean);
         this.currentProject = null;
         this.saveMenu.setEnabled(false);
         this.saveAsMenu.setEnabled(true);
         this.compileMenu.setEnabled(true);
         return newBean;
      } catch (Exception var4) {
         this.displayException(var4);
         return null;
      }
   }

   private File fixJChooserDirBug(File f) {
      if (f.exists() && f.isDirectory()) {
         return f;
      } else {
         String path = f.getAbsolutePath().replace('/', File.separatorChar);
         int ind = path.lastIndexOf(File.separatorChar);
         if (ind <= 0) {
            return f;
         } else {
            String append = path.substring(ind + 1);
            String firstPath = path.substring(0, ind);
            ind = firstPath.lastIndexOf(File.separatorChar);
            if (ind <= 0) {
               return f;
            } else {
               String append2 = firstPath.substring(ind + 1);
               return append.equals(append2) ? new File(firstPath) : f;
            }
         }
      }
   }

   public void setRootBean(EJBTaglibDescriptor b) {
      this.tree = null;
      this.left.setViewportView((Component)null);
      this.currentBean = b;
      int width = this.split.getWidth();
      this.split.setDividerLocation(width / 4);
      if (b == null) {
         this.saveAsMenu.setEnabled(false);
         this.saveMenu.setEnabled(false);
         this.compileMenu.setEnabled(false);
         this.resolveMenu.setEnabled(false);
      } else {
         this.resolveMenu.setEnabled(true);

         try {
            this.tree = new EJBTaglibDescriptorTree(b);
         } catch (Exception var4) {
            this.displayException(var4);
            return;
         }

         this.tree.addTreeSelectionListener(this);
         this.left.setViewportView(this.tree);
      }
   }

   private Image getFrameIcon() {
      if (this.frameIcon == null) {
         this.frameIcon = AWTUtils.loadImage(this.getClass().getClassLoader(), "/weblogic/graphics/W.gif");
      }

      return this.frameIcon;
   }

   private void initSplash() {
      this.splash = new JPanel();
      Image I = AWTUtils.loadImage(this.getClass().getClassLoader(), "/weblogic/graphics/logo-trans.gif");
      if (I != null) {
         ImageIcon ii = new ImageIcon(I);
         this.splash.add(new JLabel(ii));
         p("initSplash: added Image=" + I);
      } else {
         p("initSplash: cannot find image");
      }

   }

   private void initHelp() {
      try {
         if (this.docFrame != null) {
            return;
         }

         URL u = this.getClass().getResource("documentation.html");
         InputStream is = u.openStream();
         byte[] b = new byte[512];
         StringBuffer sb = new StringBuffer();

         int r;
         while((r = is.read(b)) > 0) {
            sb.append(new String(b, 0, r));
         }

         is.close();
         JEditorPane html = new JEditorPane("text/html", sb.toString());
         html.setEditable(false);
         JScrollPane scroll = new JScrollPane(html);
         this.docFrame = new JFrame("documentation");
         Image I = this.getFrameIcon();
         if (I != null) {
            this.docFrame.setIconImage(I);
         }

         this.docFrame.getContentPane().add(scroll);
         this.docFrame.setSize(500, 400);
         this.docFrame.setLocation(200, 200);
         this.helpMenu.setEnabled(true);
         u = this.getClass().getResource("help.html");
         this.contextHelp = new HelpFrame("EJB to JSP Help", this.frame, u);
      } catch (Exception var9) {
         var9.printStackTrace();
      }

   }

   public static Main getInstance() {
      return theMain;
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src instanceof JComponent) {
         JComponent jc = (JComponent)src;
         String anchor = (String)jc.getClientProperty("help-anchor");
         if (anchor != null) {
            p("help anchor: '" + anchor + "'");
            this.contextHelp.scroll2anchor(anchor);
            return;
         }
      }

      Runnable r = null;
      if (src == this.saveMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doSave();
            }
         };
      } else if (src == this.saveAsMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doSaveAs();
            }
         };
      } else if (src == this.quitMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doQuit();
            }
         };
      } else if (src == this.openMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doOpen();
            }
         };
      } else if (src == this.newMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doNew();
            }
         };
      } else if (src == this.compileMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doCompile();
            }
         };
      } else if (src == this.resolveMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doResolve();
            }
         };
      } else if (src == this.helpMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doHelp();
            }
         };
      } else if (src == this.prefsMenu) {
         r = new Runnable() {
            public void run() {
               Main.this.doPrefs();
            }
         };
      }

      if (r != null) {
         this.pushTask(r);
      }

   }

   public void start() throws Exception {
      this.prefs = new Prefs();
      this.prefs.load();
      this.initSplash();
      this.left = new JScrollPane((Component)null);
      this.right = new JScrollPane(this.splash);
      this.split = new JSplitPane(1, true, this.left, this.right);
      this.frame = new JFrame("EJB To JSP Tool");
      Image I = this.getFrameIcon();
      if (I != null) {
         this.frame.setIconImage(I);
      }

      this.frame.addWindowListener(this);
      this.frame.getContentPane().add(this.split);
      this.mb = new JMenuBar();
      this.menu = new JMenu("File");
      this.menu.add(this.newMenu = new JMenuItem("New..."));
      this.menu.add(this.openMenu = new JMenuItem("Open..."));
      this.menu.add(this.compileMenu = new JMenuItem("Build Project"));
      this.menu.add(this.saveMenu = new JMenuItem("Save"));
      this.menu.add(this.saveAsMenu = new JMenuItem("Save As..."));
      this.menu.add(this.quitMenu = new JMenuItem("Quit"));
      this.menu.add(this.resolveMenu = new JMenuItem("Resolve Attributes..."));
      this.menu.add(this.prefsMenu = new JMenuItem("Preferences..."));
      this.resolveMenu.setEnabled(false);
      this.resolveMenu.setToolTipText("resolves tag attribute names against EJB interface sources");
      this.openMenu.setToolTipText("opens an existing ejb2jsp project file");
      this.newMenu.setToolTipText("creates a new ejb2jsp project from an EJB jar file");
      this.compileMenu.setToolTipText("builds the current project into the JSP tag library");
      this.openMenu.addActionListener(this);
      this.newMenu.addActionListener(this);
      this.compileMenu.addActionListener(this);
      this.saveMenu.addActionListener(this);
      this.saveAsMenu.addActionListener(this);
      this.quitMenu.addActionListener(this);
      this.resolveMenu.addActionListener(this);
      this.prefsMenu.addActionListener(this);
      this.mb.add(this.menu);
      this.helpMenu = new JMenuItem("Help");
      this.menu.add(this.helpMenu);
      this.helpMenu.addActionListener(this);
      this.helpMenu.setEnabled(false);
      Runnable r = new Runnable() {
         public void run() {
            Main.this.initHelp();
         }
      };
      this.pushTask(r);
      this.frame.setJMenuBar(this.mb);
      this.frame.setSize(800, 500);
      this.frame.setLocation(200, 200);
      this.setRootBean(this.loadRootBean());
      this.split.setDividerLocation(200);
      this.frame.setVisible(true);
   }

   public static void main(String[] a) throws Exception {
      System.setProperty("line.separator", "\n");
      System.setProperty("javax.xml.parsers.SAXParserFactory", "weblogic.apache.xerces.jaxp.SAXParserFactoryImpl");
      System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "weblogic.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
      AWTUtils.initLookAndFeel();
      Main m = new Main(a);
      theMain = m;
      m.start();
   }

   private GUIPrintStream getGUIPrintStream() {
      if (this.guiPS == null) {
         this.compileTA = new JTextArea("", 20, 30);
         this.guiPS = new GUIPrintStream(this.compileTA);
      }

      this.compileTA.setText("");
      return this.guiPS;
   }

   public void displayException(Throwable t) {
      System.err.println("error occurred: " + t);
      t.printStackTrace();
      String msg = t.toString();
      int ind1 = msg.indexOf(13);
      int ind2 = msg.indexOf(10);
      int ind = Math.min(ind1, ind2);
      if (ind > 0) {
         msg = msg.substring(0, ind);
      }

      JOptionPane.showMessageDialog(this.frame, msg, "Error occurred", 0);
   }

   public void windowOpened(WindowEvent e) {
   }

   public void windowClosing(WindowEvent e) {
      if (this.askToSave()) {
         this.doExit();
      } else {
         this.frame.setVisible(true);
      }

   }

   public void windowClosed(WindowEvent e) {
   }

   public void windowIconified(WindowEvent e) {
   }

   public void windowDeiconified(WindowEvent e) {
   }

   public void windowActivated(WindowEvent e) {
   }

   public void windowDeactivated(WindowEvent e) {
   }
}
