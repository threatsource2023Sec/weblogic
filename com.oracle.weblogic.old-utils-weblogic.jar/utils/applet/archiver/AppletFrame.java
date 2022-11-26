package utils.applet.archiver;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class AppletFrame extends Frame implements AppletStub, AppletContext, Runnable, WindowListener, ActionListener {
   URL codebase;
   URL docbase;
   AppletArchiver ar;
   AppletClassLoader loader;
   Hashtable atts;
   Applet applet;
   int w;
   int h;
   int currentState;
   Dimension appletSize;
   Queue queue = new Queue(250);
   Label status;
   MenuItem exitNSave;
   MenuItem showAtts;
   static final int APPLET_START = 0;
   static final int APPLET_STOP = 1;
   static final int APPLET_DESTROY = 2;

   public AppletFrame(URL cbase, URL dbase, AppletArchiver ar, Hashtable atts, AppletClassLoader loader) {
      super(dbase.toString());
      this.setLayout(new BorderLayout(5, 5));
      this.setBackground(Color.lightGray);
      this.codebase = cbase;
      this.docbase = dbase;
      this.ar = ar;
      this.loader = loader;
      this.atts = atts;
      String width = (String)atts.get("WIDTH");

      try {
         this.w = Integer.parseInt(width);
      } catch (Exception var15) {
         this.w = 200;
      }

      this.addWindowListener(this);
      String height = (String)atts.get("HEIGHT");

      try {
         this.h = Integer.parseInt(height);
      } catch (Exception var14) {
         this.h = 200;
      }

      this.appletSize = new Dimension(this.w, this.h);
      this.status = new Label("");
      this.add(this.status, "South");
      Font f = new Font("SansSerif", 1, 12);
      this.status.setFont(f);
      FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(f);
      this.h = this.h + fm.getHeight() + 10;
      this.w += 10;
      this.setSize(this.w, this.h);
      Image i = ar.getIcon();
      if (i != null) {
         this.setIconImage(i);
      }

      MenuBar mb = new MenuBar();
      Menu mn = new Menu("Options");
      mn.add(this.exitNSave = new MenuItem("Quit and Save"));
      mn.add(this.showAtts = new MenuItem("Show tag..."));
      mb.add(mn);
      this.setMenuBar(mb);
      this.exitNSave.addActionListener(this);
      this.showAtts.addActionListener(this);
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation(new Point((screen.width - this.w) / 2, (screen.height - this.h) / 2));
   }

   public void setSize(int x, int y) {
      super.setSize(x, y);
   }

   public void run() {
      try {
         String s = this.getParameter("CODE");
         if (s.endsWith(".class")) {
            s = s.substring(0, s.length() - 6);
         }

         this.showStatus("loading " + s + ':');
         this.applet = (Applet)this.loader.loadClass(s).newInstance();
         this.ar.applets.put(this.applet.getClass().getName(), this.applet);
         this.applet.setStub(this);
         this.applet.hide();
         this.add(this.applet, "North");
         this.setVisible(true);
         this.applet.resize(this.appletSize);
         this.showStatus("initializing " + s + ':');
         this.applet.init();
         this.validate();
         this.applet.resize(this.appletSize);
         this.validate();
         this.showStatus("starting " + s + ':');
         this.applet.start();
         this.applet.show();
         this.currentState = 0;
         this.showStatus("Applet " + s + " started.");
         this.doLayout();
         Toolkit.getDefaultToolkit().sync();
         this.eventLoop();
         this.loader.close();
      } catch (Error var9) {
         var9.printStackTrace();
         this.showStatus("error: " + var9.toString());

         try {
            Thread.sleep(3000L);
         } catch (Exception var8) {
         }
      } catch (Exception var10) {
         var10.printStackTrace();
         this.showStatus("error: " + var10.toString());
      } finally {
         if (this.applet != null) {
            this.ar.applets.remove(this.applet.getClass().getName());
         }

         this.setVisible(false);
      }

   }

   int getNextEvent() {
      Integer I = (Integer)this.queue.get();
      return I == null ? 2 : I;
   }

   void putNextEvent(int i) {
      try {
         this.queue.put(new Integer(i));
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   void eventLoop() {
      while(this.currentState != 2) {
         switch (this.getNextEvent()) {
            case 0:
               if (this.currentState == 1) {
                  this.applet.resize(this.appletSize);
                  this.applet.start();
                  this.validate();
                  this.applet.show();
               }

               this.currentState = 0;
               break;
            case 1:
               if (this.currentState == 0) {
                  this.applet.hide();
                  this.applet.stop();
               }

               this.currentState = 1;
               break;
            case 2:
               if (this.currentState == 0) {
                  this.applet.hide();
                  this.applet.stop();
               }

               this.applet.destroy();
               this.currentState = 2;
         }
      }

   }

   public void actionPerformed(ActionEvent ev) {
      Object src = ev.getSource();
      if (src == this.exitNSave) {
         this.putNextEvent(2);
      } else if (src == this.showAtts) {
         (new Thread(new AttsDialog(this))).start();
      }

   }

   public void windowOpened(WindowEvent e) {
   }

   public synchronized void windowClosing(WindowEvent e) {
      this.putNextEvent(2);
   }

   public void windowClosed(WindowEvent e) {
   }

   public synchronized void windowIconified(WindowEvent e) {
      this.putNextEvent(1);
   }

   public synchronized void windowDeiconified(WindowEvent e) {
      this.putNextEvent(0);
   }

   public void windowActivated(WindowEvent e) {
   }

   public void windowDeactivated(WindowEvent e) {
   }

   void p(String s) {
      this.ar.out.println(s);
   }

   public boolean isActive() {
      return this.isVisible();
   }

   public URL getDocumentBase() {
      return this.docbase;
   }

   public URL getCodeBase() {
      return this.codebase;
   }

   public String getParameter(String name) {
      return (String)this.atts.get(name);
   }

   public AppletContext getAppletContext() {
      return this;
   }

   public void appletResize(int x, int y) {
      if (this.applet != null) {
         this.applet.resize(x, y);
      }

   }

   public Image getImage(URL u) {
      try {
         return this.loader.loadImage(u);
      } catch (IOException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public AudioClip getAudioClip(URL u) {
      try {
         return Applet.newAudioClip(u);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public Applet getApplet(String name) {
      return (Applet)this.ar.applets.get(name);
   }

   public Enumeration getApplets() {
      return this.ar.applets.elements();
   }

   public void showStatus(String s) {
      if (s != null) {
         this.status.setText(s);
      } else {
         this.status.setText("");
      }

   }

   public void showDocument(URL u) {
      this.ar.out.println("warn: applet.showDocument(" + u + ") ignored");
   }

   public void showDocument(URL u, String s) {
      try {
         URL n = new URL(u, s);
         this.ar.out.println("warn: applet.showDocument(" + n + ") ignored");
      } catch (Exception var4) {
      }

   }

   public void setStream(String a, InputStream b) {
   }

   public InputStream getStream(String a) {
      return null;
   }

   public Iterator getStreamKeys() {
      return null;
   }
}
