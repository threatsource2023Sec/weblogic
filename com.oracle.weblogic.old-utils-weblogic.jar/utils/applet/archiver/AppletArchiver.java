package utils.applet.archiver;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import utils.applet.AWTLogin;

public class AppletArchiver extends Frame implements Runnable, ActionListener, WindowListener, ItemListener {
   TextField tfu;
   TextField tfj;
   TextField tfwatch;
   TextArea ta;
   PrintStream out;
   String currentURL;
   Button get;
   Button cancel;
   Button clear;
   Button help;
   Container bag;
   Checkbox saveAsJar;
   Checkbox compressJar;
   Checkbox saveAsCab;
   Vector frames = new Vector();
   Parser p;
   String jarName;
   URL url;
   boolean dialogOpen = false;
   MenuBar mb;
   MenuItem loadListMenu;
   MenuItem exit;
   String[] loadRemote = new String[6];
   Image icon;
   static ThreadGroup appletTG = new ThreadGroup("AppletArchiver applets");
   Hashtable applets = new Hashtable();
   static final String helpMsg = "AppletArchiver loads and views applets.  The applet\nis started in a separate frame.  The tool remembers\nall of the remote classes, images, and other \"resources\"\nthat were loaded by the applet.  When the applet's frame\nis closed, the tool packages the classes into either\na .jar or .cab archive.\n\nusage: <applet-URL> <archive-name>\n\n";
   static final int PAD = 10;
   int w;
   int h;
   private String authInfo = null;
   private Object dialogLock = new Object();
   static AppletArchiver instance;
   private static final String PRE_PROCESSOR_ENABLE = "weblogic.classloader.preprocessor.enable";
   private static final String L10N_LOCALIZER_ENABLE = "weblogic.l10ntools.l10nlookup.enable";

   void pe(String s) {
      System.out.println("ERROR: " + s);
      this.out.println("ERROR: " + s);
   }

   static void p(String s) {
      System.out.println(s);
   }

   public void windowActivated(WindowEvent e) {
      this.setVisible(true);
   }

   public void windowClosed(WindowEvent e) {
   }

   public void windowClosing(WindowEvent e) {
      this.exitHook();
   }

   public void windowDeactivated(WindowEvent e) {
   }

   public void windowDeiconified(WindowEvent e) {
   }

   public void windowIconified(WindowEvent e) {
   }

   public void windowOpened(WindowEvent e) {
   }

   public AppletArchiver(String url) {
      super("AppletArchiver");
      this.loadRemote[0] = "weblogic";
      this.loadRemote[1] = "utils";
      this.loadRemote[2] = "qa";
      this.loadRemote[3] = "tutorial";
      this.loadRemote[4] = "javax";
      this.loadRemote[5] = "examples";
      this.setBackground(Color.lightGray);
      this.currentURL = url;
      this.addWindowListener(this);
      GridBagLayout gb = new GridBagLayout();
      this.setLayout(gb);
      this.setSize(500, 300);
      GridBagConstraints gbc = new GridBagConstraints();
      this.bag = this.constructButtonPanel();
      gbc.gridx = gbc.gridy = 0;
      gbc.anchor = 12;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.weighty = 0.0;
      gbc.gridwidth = 0;
      gbc.gridheight = -1;
      this.bag.setBackground(Color.gray);
      gb.setConstraints(this.bag, gbc);
      this.add(this.bag);
      gbc.anchor = 12;
      gbc.fill = 1;
      ++gbc.gridy;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;
      gbc.gridheight = 0;
      gbc.gridwidth = 0;
      this.ta = new TextArea("", 15, 20, 0);
      this.ta.setBackground(Color.lightGray);
      this.ta.setEditable(false);
      this.out = new TextOut(this.ta);
      gb.setConstraints(this.ta, gbc);
      this.add(this.ta);
      this.pack();
      this.setIcon();
   }

   private Container constructButtonPanel() {
      Container c = new Panel();
      GridBagLayout gb = new GridBagLayout();
      GridBagConstraints gbc = new GridBagConstraints();
      c.setLayout(gb);
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.gridx = gbc.gridy = 0;
      gbc.gridwidth = 1;
      gbc.anchor = 13;
      gbc.fill = 0;
      this.saveAsJar = new Checkbox(" Save to .jar file", true);
      this.saveAsJar.addItemListener(this);
      gb.setConstraints(this.saveAsJar, gbc);
      c.add(this.saveAsJar);
      ++gbc.gridx;
      gbc.anchor = 17;
      this.compressJar = new Checkbox("Compress .jar", true);
      this.compressJar.addItemListener(this);
      gb.setConstraints(this.compressJar, gbc);
      c.add(this.compressJar);
      this.saveAsCab = new Checkbox("Save to .cab file", false);
      this.saveAsCab.addItemListener(this);
      String os = System.getProperty("os.name");
      if (os != null && os.toUpperCase().indexOf("WINDOWS") >= 0) {
         gbc.gridx = 0;
         gbc.anchor = 13;
         ++gbc.gridy;
         gb.setConstraints(this.saveAsCab, gbc);
         c.add(this.saveAsCab);
      }

      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.anchor = 13;
      gbc.weightx = 0.0;
      gbc.gridwidth = 1;
      gbc.fill = 0;
      Label l = new Label("Applet URL");
      gb.setConstraints(l, gbc);
      c.add(l);
      ++gbc.gridx;
      gbc.fill = 2;
      gbc.anchor = 17;
      gbc.weightx = 1.0;
      gbc.gridwidth = 0;
      this.tfu = new TextField("url", 30);
      this.tfu.addActionListener(this);
      this.tfu.setBackground(Color.white);
      gb.setConstraints(this.tfu, gbc);
      c.add(this.tfu);
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.fill = 0;
      gbc.anchor = 13;
      gbc.gridwidth = 1;
      l = new Label("Archive file");
      gb.setConstraints(l, gbc);
      c.add(l);
      ++gbc.gridx;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.anchor = 17;
      gbc.gridwidth = 0;
      this.tfj = new TextField("jar", 30);
      this.tfj.addActionListener(this);
      this.tfj.setBackground(Color.white);
      gb.setConstraints(this.tfj, gbc);
      c.add(this.tfj);
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      gbc.gridwidth = 1;
      l = new Label("Show trace when loading...");
      gb.setConstraints(l, gbc);
      ++gbc.gridx;
      gbc.fill = 2;
      gbc.anchor = 17;
      gbc.weightx = 1.0;
      gbc.gridwidth = 0;
      this.tfwatch = new TextField("", 30);
      this.tfwatch.setBackground(Color.white);
      gb.setConstraints(this.tfwatch, gbc);
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.fill = 0;
      gbc.anchor = 13;
      gbc.weightx = 0.0;
      gbc.gridwidth = 1;
      this.get = new Button("Get it");
      this.get.addActionListener(this);
      gb.setConstraints(this.get, gbc);
      c.add(this.get);
      ++gbc.gridx;
      gbc.anchor = 17;
      this.cancel = new Button("Cancel get");
      this.cancel.addActionListener(this);
      this.cancel.setEnabled(false);
      gb.setConstraints(this.cancel, gbc);
      c.add(this.cancel);
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.anchor = 13;
      this.clear = new Button("Clear output");
      this.clear.addActionListener(this);
      gb.setConstraints(this.clear, gbc);
      c.add(this.clear);
      ++gbc.gridx;
      gbc.anchor = 17;
      this.help = new Button("Help");
      this.help.addActionListener(this);
      gb.setConstraints(this.help, gbc);
      c.add(this.help);
      this.mb = new MenuBar();
      Menu mn = new Menu("Options");
      mn.add(this.loadListMenu = new MenuItem("Remote class-loading list..."));
      mn.add(this.exit = new MenuItem("Exit"));
      this.mb.add(mn);
      this.setMenuBar(this.mb);
      this.loadListMenu.addActionListener(this);
      this.exit.addActionListener(this);
      return c;
   }

   public void setAuthenticationInfo(String s) {
      this.authInfo = s;
   }

   public String getAuthenticationInfo(boolean showIfNull) {
      if (this.authInfo != null) {
         return this.authInfo;
      } else if (!showIfNull) {
         return null;
      } else {
         synchronized(this.dialogLock) {
            return this.authInfo != null ? this.authInfo : (this.authInfo = AWTLogin.showLoginDialog(this, "Authorization Required"));
         }
      }
   }

   public Image getIcon() {
      return this.icon;
   }

   public void setIcon() {
      try {
         URL u = this.getClass().getResource("/weblogic/graphics/W.gif");
         this.icon = Toolkit.getDefaultToolkit().createImage((ImageProducer)u.getContent());
         this.setIconImage(this.icon);
      } catch (Exception var2) {
         var2.printStackTrace();
      } catch (Error var3) {
         var3.printStackTrace();
      }

   }

   void exitHook() {
      System.exit(0);
   }

   void getIt() {
      String u = this.tfu.getText();
      this.jarName = this.tfj.getText();
      if (u == null) {
         this.pe("specify a URL");
      } else if (this.jarName == null) {
         this.pe("specify a jar file");
      } else {
         try {
            if (u.indexOf(":/") == -1) {
               u = "http://" + u;
            }

            this.url = new URL(u);
            this.tfu.setText(u);
            this.p = new Parser(this.url, this);
            (new Thread(this.p, "fetcher")).start();
         } catch (Exception var3) {
            this.pe("bad URL: " + u);
            return;
         }

         this.get.setEnabled(false);
         this.cancel.setEnabled(true);
      }
   }

   synchronized void startApplet() {
      try {
         Vector v = this.p.v;
         AppletClassLoader loader = null;
         File cabDir = null;
         if (this.saveAsCab.getState()) {
            Random r = new Random(System.currentTimeMillis());
            String dir = this.url.getHost() + (r.nextInt() & Integer.MAX_VALUE);
            cabDir = new File(dir);
         }

         if (v.size() != 0) {
            for(int i = 0; i < v.size(); ++i) {
               Hashtable atts = (Hashtable)v.elementAt(i);
               String s = (String)atts.get("CODE");
               if (s == null) {
                  this.pe("no 'CODE' attribute in tag: " + atts);
               } else {
                  s = (String)atts.get("CODEBASE");
                  URL cb;
                  if (s != null) {
                     if (!s.endsWith("/")) {
                        s = s + "/";
                     }

                     cb = new URL(this.url, s);
                  } else {
                     cb = this.url;
                  }

                  if (loader == null) {
                     loader = new AppletClassLoader(cb, this.out, this.jarName, cabDir, this.loadRemote, this.tfwatch.getText(), this.compressJar.getState());
                     Thread.currentThread().setContextClassLoader(loader);
                  }

                  AppletFrame f = new AppletFrame(cb, this.url, this, atts, loader);
                  (new Thread(appletTG, f, "Applet-" + cb.toString())).start();
               }
            }

            return;
         }

         this.pe(this.p.u + " doesn't contain an APPLET tag");
      } catch (Exception var12) {
         var12.printStackTrace();
         return;
      } finally {
         this.p = null;
      }

   }

   void finished() {
      this.get.setEnabled(true);
      this.cancel.setEnabled(false);
   }

   void cancelIt() {
      this.get.setEnabled(true);
      this.cancel.setEnabled(false);
      if (this.p != null) {
         this.p.cancel();
      }

   }

   public void itemStateChanged(ItemEvent ev) {
      Object src = ev.getSource();
      if (src == this.saveAsCab) {
         this.saveAsJar.setState(!this.saveAsCab.getState());
         this.compressJar.setEnabled(this.saveAsJar.getState());
      } else if (src == this.saveAsJar) {
         this.compressJar.setEnabled(this.saveAsJar.getState());
         this.saveAsCab.setState(!this.saveAsCab.getState());
      }

   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == this.tfu) {
         this.getIt();
      } else if (src == this.exit) {
         this.exitHook();
      } else if (src == this.loadListMenu) {
         if (!this.dialogOpen) {
            this.dialogOpen = true;
            (new OptionsDialog(this)).setVisible(true);
         }
      } else if (src == this.tfj) {
         this.getIt();
      } else if (src == this.get) {
         this.getIt();
      } else if (src == this.cancel) {
         this.cancelIt();
      } else if (src == this.clear) {
         this.ta.setText("");
      } else if (src == this.p) {
         this.finished();
         String result = e.getActionCommand();
         if (result.equals("done")) {
            this.startApplet();
         } else {
            this.out.println("Error occurred when loading HTML page: " + result);
         }
      } else if (src == this.help) {
         this.out.print("AppletArchiver loads and views applets.  The applet\nis started in a separate frame.  The tool remembers\nall of the remote classes, images, and other \"resources\"\nthat were loaded by the applet.  When the applet's frame\nis closed, the tool packages the classes into either\na .jar or .cab archive.\n\nusage: <applet-URL> <archive-name>\n\n");
      }

   }

   public void run() {
   }

   public static AppletArchiver getInstance() {
      return instance;
   }

   public static void main(String[] a) throws Exception {
      System.setProperty("weblogic.classloader.preprocessor.enable", "false");
      System.setProperty("weblogic.l10ntools.l10nlookup.enable", "false");
      AppletArchiver as = new AppletArchiver("Archiver Tool");
      instance = as;
      if (a.length > 0) {
         as.tfu.setText(a[0]);
      }

      if (a.length > 1) {
         as.tfj.setText(a[1]);
         if (a[1].endsWith(".cab")) {
            as.saveAsJar.setState(false);
            as.saveAsCab.setState(true);
            as.compressJar.setEnabled(false);
         }
      }

      as.setLocation(new Point(30, 30));
      as.setVisible(true);
      as.run();
   }

   static {
      appletTG.setMaxPriority(5);
   }

   class OptionsDialog extends Dialog implements ActionListener, WindowListener, ItemListener, TextListener {
      private AppletArchiver ar;
      private List options;
      private Button delete;
      private Button ok;
      private Button cancel;
      private Button add;
      private TextField tf;
      private GridBagLayout gb = new GridBagLayout();
      private GridBagConstraints gbc = new GridBagConstraints();

      OptionsDialog(AppletArchiver arc) {
         super(arc, "Packages to load remotely", false);
         this.setLayout(this.gb);
         this.gbc.insets = new Insets(2, 2, 2, 2);
         this.gbc.gridx = this.gbc.gridy = 0;
         GridBagConstraints var10001 = this.gbc;
         this.gbc.anchor = 17;
         this.ar = arc;
         this.options = new List(arc.loadRemote.length);

         for(int i = 0; i < arc.loadRemote.length; ++i) {
            this.options.add(arc.loadRemote[i]);
         }

         this.add(this.options);
         ++this.gbc.gridx;
         this.add(this.delete = new Button("Delete"));
         ++this.gbc.gridx;
         this.add(this.ok = new Button("OK"));
         ++this.gbc.gridx;
         this.add(this.cancel = new Button("Cancel"));
         ++this.gbc.gridx;
         this.add(this.add = new Button("Add"));
         ++this.gbc.gridy;
         this.gbc.gridx = 0;
         this.gbc.weightx = 1.0;
         var10001 = this.gbc;
         this.gbc.gridwidth = 0;
         var10001 = this.gbc;
         this.gbc.fill = 2;
         this.add(this.constructFieldPanel());
         ++this.gbc.gridy;
         this.add(this.constructTextPanel());
         this.delete.setEnabled(false);
         this.add.setEnabled(false);
         this.options.addItemListener(this);
         this.delete.addActionListener(this);
         this.ok.addActionListener(this);
         this.cancel.addActionListener(this);
         this.add.addActionListener(this);
         this.tf.addActionListener(this);
         this.tf.addTextListener(this);
         this.addWindowListener(this);
         this.pack();
         Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
         Dimension sz = this.getPreferredSize();
         Point p = new Point(0, 0);
         p.x = (screen.width - sz.width) / 2;
         p.y = (screen.height - sz.height) / 2;
         this.setLocation(p);
      }

      private Component constructTextPanel() {
         Panel p = new Panel();
         GridBagLayout gb = new GridBagLayout();
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(0, 0, 0, 0);
         gbc.gridx = gbc.gridy = 0;
         gbc.anchor = 16;
         gbc.gridwidth = 0;
         p.setLayout(gb);
         Label l = new Label("Classes with these Package names");
         gb.setConstraints(l, gbc);
         p.add(l);
         ++gbc.gridy;
         gbc.anchor = 18;
         l = new Label("will be forced to load remotely.");
         gb.setConstraints(l, gbc);
         p.add(l);
         return p;
      }

      private Component constructFieldPanel() {
         Panel p = new Panel();
         GridBagLayout gb2 = new GridBagLayout();
         GridBagConstraints gbc2 = new GridBagConstraints();
         gbc2.insets = new Insets(3, 5, 3, 0);
         p.setLayout(gb2);
         Label l = new Label("Add Package name:");
         gbc2.anchor = 13;
         gb2.setConstraints(l, gbc2);
         p.add(l);
         gbc2.anchor = 17;
         gbc2.fill = 2;
         gbc2.weightx = 1.0;
         ++gbc2.gridy;
         gbc2.insets.left = 0;
         gbc2.insets.right = 5;
         this.tf = new TextField("", 10);
         gb2.setConstraints(this.tf, gbc2);
         p.add(this.tf);
         return p;
      }

      public Component add(Component c) {
         this.gb.setConstraints(c, this.gbc);
         return super.add(c);
      }

      public void textValueChanged(TextEvent e) {
         if (e.getSource() == this.tf) {
            String t = this.tf.getText();
            if (t != null && t.length() > 0) {
               this.add.setEnabled(true);
            } else {
               this.add.setEnabled(false);
            }

         }
      }

      public void itemStateChanged(ItemEvent e) {
         Object src = e.getSource();
         if (src == this.options) {
            String sel = this.options.getSelectedItem();
            if (sel == null) {
               this.delete.setEnabled(false);
            } else {
               this.delete.setEnabled(true);
            }

         }
      }

      public void actionPerformed(ActionEvent e) {
         Object src = e.getSource();
         String t;
         if (src == this.tf) {
            t = this.tf.getText();
            if (t != null && t.length() > 0) {
               this.add.setEnabled(true);
            }
         } else if (src != this.ok && src != this.cancel) {
            if (src == this.delete) {
               int ind = this.options.getSelectedIndex();
               if (ind > 0) {
                  this.options.delItem(ind);
               }

               this.delete.setEnabled(false);
            } else if (src == this.add) {
               t = this.tf.getText();
               if (t != null && t.length() > 0) {
                  this.options.add(t);
                  this.tf.setText("");
               }

               this.add.setEnabled(false);
            }
         } else {
            this.exitHook(src == this.ok);
         }

      }

      public void windowActivated(WindowEvent e) {
      }

      public void windowClosed(WindowEvent e) {
      }

      public void windowClosing(WindowEvent e) {
         this.exitHook(false);
      }

      public void windowDeactivated(WindowEvent e) {
      }

      public void windowDeiconified(WindowEvent e) {
      }

      public void windowIconified(WindowEvent e) {
      }

      public void windowOpened(WindowEvent e) {
      }

      private void exitHook(boolean save) {
         this.ar.dialogOpen = false;
         if (save) {
            int n = this.options.getItemCount();
            String[] lt = new String[n];

            for(int i = 0; i < n; ++i) {
               lt[i] = this.options.getItem(i);
            }

            this.ar.loadRemote = lt;
         }

         this.setVisible(false);
         super.dispose();
      }
   }
}
