package weblogic.tools.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.ImageProducer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import weblogic.Home;

public class Util {
   private static final boolean debug = false;
   private static int PATH_DISPLAY_THRESHOLD_LEN = 40;
   public static final int YES = 0;
   public static final int NO = 1;
   public static final int CANCEL = 2;

   public static void centerWindow(Window win, Window parent) {
      Point parentLoc = parent.getLocation();
      Point winLoc = new Point();
      Dimension parentDim = parent.getSize();
      Dimension winDim = win.getSize();
      winLoc.x = parentLoc.x + parentDim.width / 2 - winDim.width / 2;
      winLoc.y = parentLoc.y + parentDim.height / 2 - winDim.height / 2;
      if (winLoc.x < 0) {
         winLoc.x = parentLoc.x;
      }

      if (winLoc.y < 0) {
         winLoc.y = parentLoc.y;
      }

      win.setLocation(winLoc);
   }

   public static void centerWindow(Window win) {
      Point winLoc = new Point();
      Dimension parentDim = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension winDim = win.getSize();
      winLoc.x = parentDim.width / 2 - winDim.width / 2;
      winLoc.y = parentDim.height / 2 - winDim.height / 2;
      if (winLoc.x < 0) {
         winLoc.x = 0;
      }

      if (winLoc.y < 0) {
         winLoc.y = 0;
      }

      win.setLocation(winLoc);
   }

   public static void frontAndCenter(Window w) {
      centerWindow(w);
   }

   public static void frontAndCenter(Window w, Window parent) {
      centerWindow(w, parent);
   }

   public static String[] splitCompletely(String s, String delim, boolean returnDelim) {
      return splitCompletely(new StringTokenizer(s, delim, returnDelim));
   }

   public static String[] splitCompletely(String s, String delim) {
      return splitCompletely(new StringTokenizer(s, delim));
   }

   public static String[] splitCompletely(String s) {
      return splitCompletely(new StringTokenizer(s));
   }

   private static String[] splitCompletely(StringTokenizer stringTokenizer) {
      int i = stringTokenizer.countTokens();
      String[] st = new String[i];

      for(int j = 0; j < i; ++j) {
         st[j] = stringTokenizer.nextToken();
      }

      return st;
   }

   public static void initLookAndFeel(String laf) {
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
         } catch (Exception var7) {
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
      } catch (Exception var6) {
      }

      if (laf == null) {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (Exception var5) {
            try {
               UIManager.setLookAndFeel(initialLAF);
            } catch (Exception var4) {
            }
         }
      }

   }

   public static Image loadImage(ClassLoader cl, String name) {
      URL u = cl.getResource(name);
      if (u == null) {
         return loadImage(name);
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
         URL url = Util.class.getResource(resourceName);
         if (url == null) {
            url = Util.class.getResource("/weblogic/graphics/" + resourceName);
         }

         Toolkit tk = Toolkit.getDefaultToolkit();
         return tk.createImage((ImageProducer)url.getContent());
      } catch (Exception var3) {
         return null;
      }
   }

   public static URL getResourceURL(String name) {
      URL url = null;
      int idx = name.lastIndexOf(35);
      if (idx >= 0) {
         String anchor = name.substring(idx + 1);
         name = name.substring(0, idx);
         url = Util.class.getResource(name);
         if (url != null) {
            try {
               url = new URL(url, '#' + anchor);
            } catch (MalformedURLException var5) {
            }
         }
      } else {
         url = Util.class.getResource(name);
         if (url == null) {
            url = Util.class.getResource("/weblogic/graphics/" + name);
         }
      }

      return url;
   }

   public static String getResourceString(String key, ResourceBundle bundle) {
      try {
         return bundle.getString(key);
      } catch (MissingResourceException var4) {
         String msg = "Missing string resource(" + key + ")";
         System.out.println(msg);
         return msg;
      }
   }

   public static char getResourceChar(String key, ResourceBundle bundle) {
      try {
         return bundle.getString(key).charAt(0);
      } catch (MissingResourceException var3) {
         var3.printStackTrace();
         return '\u0000';
      }
   }

   public static void setTextFieldEnabled(JTextField tf, boolean enabled) {
      tf.setEnabled(enabled);
      tf.repaint();
   }

   public static void setLabelEnabled(JLabel label, boolean enabled) {
      label.setEnabled(enabled);
      label.repaint();
   }

   public static void setPanelEnabled(JPanel panel, boolean enabled) {
      Component[] ca = panel.getComponents();

      for(int i = 0; i < ca.length; ++i) {
         if (ca[i] instanceof JPanel) {
            setPanelEnabled((JPanel)ca[i], enabled);
         }

         if (ca[i] instanceof JTextField) {
            setTextFieldEnabled((JTextField)ca[i], enabled);
         } else if (ca[i] instanceof JLabel) {
            setLabelEnabled((JLabel)ca[i], enabled);
         } else {
            ca[i].setEnabled(enabled);
            ca[i].repaint();
         }
      }

   }

   public static JFrame getComponentFrame(Component comp) {
      return (JFrame)SwingUtilities.getAncestorOfClass(JFrame.class, comp);
   }

   public static Window getComponentWindow(Component comp) {
      return (Window)SwingUtilities.getAncestorOfClass(Window.class, comp);
   }

   public static String getTypeName(Class type) {
      if (type.isArray()) {
         try {
            Class cl = type;
            int dimensions = 0;

            StringBuffer sb;
            for(sb = new StringBuffer(); cl.isArray(); cl = cl.getComponentType()) {
               ++dimensions;
            }

            sb.append(cl.getName());

            for(int i = 0; i < dimensions; ++i) {
               sb.append("[]");
            }

            return sb.toString();
         } catch (Throwable var5) {
         }
      }

      return type.getName();
   }

   public static String format(String message, Object[] args) {
      return MessageFormat.format(message, args);
   }

   public static File getFile(String path) {
      File file = new File(path);

      try {
         return new File(file.getCanonicalPath());
      } catch (IOException var3) {
         return file;
      }
   }

   public static String getPath(File f) {
      if (f != null) {
         try {
            return f.getCanonicalPath();
         } catch (Exception var2) {
            return f.getAbsolutePath();
         }
      } else {
         return null;
      }
   }

   public static String getPathLeaf(String path) {
      if (path.indexOf(File.separatorChar) != -1) {
         path = path.replace(File.separatorChar, '/');
      }

      return path.substring(path.lastIndexOf(47) + 1);
   }

   public static String getPathParent(String path) {
      if (path.indexOf(File.separatorChar) != -1) {
         path = path.replace(File.separatorChar, '/');
      }

      return path.substring(0, path.lastIndexOf(47) + 1);
   }

   public static boolean mkdirs(File dir) {
      if (dir == null) {
         throw new IllegalArgumentException("null directory file");
      } else {
         if (!dir.exists()) {
            String path = getPath(dir);
            int pLen = path.length();
            if (pLen > PATH_DISPLAY_THRESHOLD_LEN) {
               path = "..." + path.substring(pLen - PATH_DISPLAY_THRESHOLD_LEN);
            }

            String msg = "The directory\n" + path + "\ndoes not exist. Create it?";
            if (confirm((Component)null, "Create directory?", msg)) {
               return dir.mkdirs();
            }
         }

         return true;
      }
   }

   public static boolean confirm(Component c, String title, String message) {
      Object[] options = new Object[]{"Yes", "No"};
      int value = JOptionPane.showOptionDialog(c, message, title, 0, 3, (Icon)null, options, options[1]);
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

   public static int confirmWithCancel(Component component, String title, String message) {
      Object[] options = new Object[]{"Yes", "No", "Cancel"};
      return JOptionPane.showOptionDialog(component, message, title, 1, 3, (Icon)null, options, options[0]);
   }

   public static void copyFile(InputStream is, File to) throws IOException {
      byte[] buf = new byte[1024];
      FileOutputStream fos = new FileOutputStream(to);
      BufferedOutputStream bos = new BufferedOutputStream(fos);

      int r;
      while((r = is.read(buf)) > 0) {
         bos.write(buf, 0, r);
      }

      is.close();
      bos.close();
   }

   public static void copyFile(File from, File to) throws IOException {
      FileInputStream fis = new FileInputStream(from);
      BufferedInputStream bis = new BufferedInputStream(fis);
      copyFile((InputStream)bis, to);
   }

   public static URL toURL(File f) throws MalformedURLException {
      String path = f.getAbsolutePath();
      if (File.separatorChar != '/') {
         path = path.replace(File.separatorChar, '/');
      }

      if (!path.startsWith("/")) {
         path = "/" + path;
      }

      if (!path.endsWith("/") && f.isDirectory()) {
         path = path + "/";
      }

      return new URL("file", "", path);
   }

   public static void gatherFiles(File dir, Hashtable ht) {
      String[] files = dir.list();
      if (files != null) {
         for(int i = 0; i < files.length; ++i) {
            File f = new File(dir, files[i]);
            if (f.isDirectory()) {
               gatherFiles(f, ht);
            } else {
               ht.put(f.getPath(), f);
            }
         }
      }

   }

   public static void clearFiles(File dir) {
      if (dir != null && dir.isDirectory() && dir.exists()) {
         String[] files = dir.list();
         if (files != null) {
            for(int i = 0; i < files.length; ++i) {
               File file = new File(dir, files[i]);
               file.delete();
            }
         }
      }

   }

   public static String getFileURL(String path) {
      String result = Home.getPath();
      result = result.replace(File.separatorChar, '/');
      return result + "/classes" + path;
   }

   private static void focusOnFirstWidget(JComponent comp) {
      if (null != comp) {
         Component firstComponent = null;
         Component[] children = findAllFocusableComponents(comp);

         for(int i = 0; i < children.length; ++i) {
            Component thisComponent = children[i];
            if (i == 0) {
               firstComponent = thisComponent;
            } else {
               ppp("THIS:" + thisComponent.getX() + " " + thisComponent.getY());
               ppp("FIRST:" + firstComponent.getX() + " " + firstComponent.getY());
               if (thisComponent.getX() < firstComponent.getX() && thisComponent.getY() < firstComponent.getY()) {
                  ppp("NEW FIRST COMP AT " + thisComponent.getX() + " " + thisComponent.getY());
                  firstComponent = thisComponent;
               }
            }
         }

         if (null != firstComponent) {
            firstComponent.requestFocus();
         }
      }

   }

   private static JComponent[] findAllFocusableComponents(Component comp) {
      ArrayList result = new ArrayList();
      if (comp instanceof JComponent) {
         findAllFocusableComponents((JComponent)comp, result);
      }

      return (JComponent[])((JComponent[])result.toArray(new JComponent[0]));
   }

   private static void findAllFocusableComponents(Component comp, List result) {
      if (comp instanceof JComponent) {
         Component[] comps = ((JComponent)comp).getComponents();

         for(int i = 0; i < comps.length; ++i) {
            if (isInputWidget(comps[i])) {
               result.add(comps[i]);
            } else {
               ArrayList result2 = new ArrayList();
               findAllFocusableComponents(comps[i], result2);
               Iterator it = result2.iterator();

               while(it.hasNext()) {
                  Object c = it.next();
                  result.add(c);
               }
            }
         }
      }

   }

   private static boolean isInputWidget(Component c) {
      return c instanceof JComboBox || c instanceof JTextComponent || c instanceof JToggleButton || c instanceof NumberBox;
   }

   private static void ppp(String s) {
      System.out.println("[Util] " + s);
   }
}
