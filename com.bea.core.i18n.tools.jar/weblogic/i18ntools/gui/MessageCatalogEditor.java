package weblogic.i18ntools.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.awt.image.ImageProducer;
import java.io.File;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import weblogic.i18n.tools.BasicMessage;
import weblogic.i18n.tools.BasicMessageCatalog;

abstract class MessageCatalogEditor extends JFrame implements WindowListener, ComponentListener {
   private String myFileTitle = "";
   private static final boolean debug = false;
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   private String myCatDirectory = ".";

   public String getFileTitle() {
      return this.myFileTitle;
   }

   public void setFileTitle(String titl) {
      this.myFileTitle = titl;
   }

   public void setCatalogDirectory(String dir) {
      if (dir == null) {
         dir = ".";
      }

      File f = new File(dir);
      if (f.isDirectory()) {
         this.myCatDirectory = dir;
      } else {
         f = f.getAbsoluteFile();
         this.myCatDirectory = f.getParent();
      }

   }

   public String getCatalogDirectory() {
      return this.myCatDirectory;
   }

   private String getSourceBranch(String top, String weblogicHome) {
      String classes = weblogicHome.substring(top.length() + 1);
      String branch = classes.substring(0, classes.lastIndexOf(95));
      return branch;
   }

   public MessageCatalogEditor() {
      this.addComponentListener(this);
   }

   abstract void setCatalog(BasicMessageCatalog var1, String var2);

   abstract void setSimpleMessageFields(BasicMessage var1, boolean var2);

   abstract void setLogMessageFields(BasicMessage var1, boolean var2);

   public void darken(Component cmp) {
      Color clr = cmp.getForeground();
      clr = clr.darker().darker();
      cmp.setForeground(clr);
   }

   public Image loadImage(String resourceName) {
      Image image = null;

      try {
         URL url = this.getClass().getResource(resourceName);
         Toolkit tk = Toolkit.getDefaultToolkit();
         image = tk.createImage((ImageProducer)url.getContent());
      } catch (Exception var5) {
      }

      return image;
   }

   protected String correctQuotes(String temp) {
      int idx = 0;
      int startIdx = 0;

      while(idx >= 0) {
         idx = temp.indexOf("'", startIdx);
         if (idx >= 0) {
            if (idx < temp.length() - 1 && temp.charAt(idx + 1) == '\'') {
               startIdx = idx + 2;
            } else {
               int option = JOptionPane.showConfirmDialog(this, this.fmt.msgDblQuote(), this.fmt.tagQuote(), 0);
               if (option != 0) {
                  break;
               }

               if (idx < temp.length() - 1) {
                  temp = temp.substring(0, idx + 1) + "'" + temp.substring(idx + 1);
               } else {
                  temp = temp.substring(0, idx + 1) + "'";
               }

               startIdx = idx + 2;
            }
         }
      }

      return temp;
   }

   public void componentResized(ComponentEvent ev) {
      if (this.getSize().height < this.getPreferredSize().height) {
         this.pack();
      } else if (this.getSize().width < this.getPreferredSize().width) {
         this.pack();
      }

   }

   public void componentMoved(ComponentEvent ev) {
   }

   public void componentShown(ComponentEvent ev) {
   }

   public void componentHidden(ComponentEvent ev) {
   }
}
