package weblogic.tools.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class AWTUtils {
   private static final boolean debug = false;
   protected static boolean confirm = false;

   public static Image loadImage(ClassLoader cl, String imgname) {
      URL u = cl.getResource(imgname);
      if (u == null) {
         return loadImage(imgname);
      } else {
         Toolkit tk = Toolkit.getDefaultToolkit();

         try {
            return tk.createImage((ImageProducer)u.getContent());
         } catch (IOException var5) {
            return null;
         }
      }
   }

   public static Image loadImage(String resourceName) {
      try {
         URL url = AWTUtils.class.getResource(resourceName);
         if (url == null) {
            url = AWTUtils.class.getResource("/weblogic/graphics/" + resourceName);
         }

         Toolkit tk = Toolkit.getDefaultToolkit();
         return tk.createImage((ImageProducer)url.getContent());
      } catch (Exception var3) {
         return null;
      }
   }

   public static URL getResourceURL(String imgName) {
      URL u = AWTUtils.class.getResource(imgName);
      if (u == null) {
         u = AWTUtils.class.getResource("/weblogic/graphics/" + imgName);
      }

      return u;
   }

   public static GridBagConstraints gbc(int x, int y, int w, int h) {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = 1;
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      if (gbc.gridwidth > 1) {
         gbc.weightx = (double)gbc.gridwidth;
      }

      gbc.insets = new Insets(5, 5, 5, 5);
      return gbc;
   }

   public static void frontAndCenter(Window w) {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension sz = w.getSize();
      int xt = (screen.width - sz.width) / 2;
      int yt = (screen.height - sz.height) / 2;
      w.setLocation(new Point(xt, yt));
   }

   public static void centerOnWindow(Window A, Window B) {
      if (B != null && B.isVisible()) {
         Dimension as = A.getSize();
         Dimension bs = B.getSize();
         Point bpos = B.getLocation();
         if (bs.width - as.width < 20 && bs.height - as.height < 20) {
            bpos.x += 20;
         } else {
            bpos.x += (bs.width - as.width) / 2;
         }

         bpos.y += (bs.height - as.height) / 2;
         bpos.x = Math.max(bpos.x, 5);
         bpos.y = Math.max(bpos.y, 5);
         A.setLocation(bpos);
      } else {
         frontAndCenter(A);
      }
   }

   public static Point getNextCascadeLocation(Point previous, Window w) {
      int x = previous.x + 20;
      int y = previous.y + 20;
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension size = w.getSize();
      if (x + size.height > screen.height || y + size.height > screen.height) {
         x = 0;
         y = 0;
      }

      return new Point(x, y);
   }

   public static boolean confirm(Frame frame, String message) {
      int value = JOptionPane.showConfirmDialog(frame, "Confirm", message, 0);
      boolean confirm = false;
      switch (value) {
         case 0:
            confirm = true;
            break;
         case 1:
            confirm = false;
            break;
         default:
            throw new Error("Boolean logic fails us!");
      }

      return confirm;
   }

   public static void message(Frame frame, String message) {
      JOptionPane.showMessageDialog(frame, "Error", message, 0);
   }

   public static boolean confirm(String message) {
      return confirm((Frame)null, message);
   }

   public static void message(String message) {
      message((Frame)null, message);
   }

   public static void initLookAndFeel() {
      initLookAndFeel((String)null, false);
   }

   public static void initLookAndFeel(String laf, boolean verbose) {
      LookAndFeel initialLAF = UIManager.getLookAndFeel();
      if (laf != null) {
         try {
            if (laf.equals("java")) {
               UIManager.setLookAndFeel("javax.swing.jlf.JLFLookAndFeel");
            } else if (laf.equals("basic")) {
               UIManager.setLookAndFeel("javax.swing.plaf.basic.BasicLookAndFeel");
            } else if (laf.equals("metal")) {
               UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } else if (laf.equals("motif")) {
               UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            } else if (laf.startsWith("win")) {
               UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else {
               UIManager.setLookAndFeel(laf);
            }

            return;
         } catch (Exception var8) {
            if (verbose) {
               System.err.println("Could not set LookAndFeel to " + laf + ": " + var8.toString());
            }

            laf = null;
         }
      }

      try {
         String osname = System.getProperty("os.name");
         if (osname != null) {
            osname = osname.toLowerCase();
            if (osname.indexOf("digital unix") >= 0 || osname.indexOf("linux") >= 0 || osname.indexOf("solaris") >= 0) {
               UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
               return;
            }
         }
      } catch (Exception var7) {
      }

      if (laf == null) {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (Exception var6) {
            if (verbose) {
               System.err.println("Trying to set SystemLookAndFeel: " + var6.toString());
            }

            try {
               UIManager.setLookAndFeel(initialLAF);
            } catch (Exception var5) {
            }
         }
      }

   }
}
