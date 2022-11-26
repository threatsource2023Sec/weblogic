package org.antlr.gunit.swingui;

import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;

public class ImageFactory {
   private static ImageFactory singleton;
   private static final String IMG_DIR = "org/antlr/gunit/swingui/images/";
   public ImageIcon ACCEPT = this.getImage("accept.png");
   public ImageIcon ADD = this.getImage("add.png");
   public ImageIcon DELETE = this.getImage("delete24.png");
   public ImageIcon TEXTFILE = this.getImage("textfile24.png");
   public ImageIcon ADDFILE = this.getImage("addfile24.png");
   public ImageIcon TEXTFILE16 = this.getImage("textfile16.png");
   public ImageIcon WINDOW16 = this.getImage("windowb16.png");
   public ImageIcon FAV16 = this.getImage("favb16.png");
   public ImageIcon SAVE = this.getImage("floppy24.png");
   public ImageIcon OPEN = this.getImage("folder24.png");
   public ImageIcon EDIT16 = this.getImage("edit16.png");
   public ImageIcon FILE16 = this.getImage("file16.png");
   public ImageIcon NEXT = this.getImage("next24.png");
   public ImageIcon RUN_PASS = this.getImage("runpass.png");
   public ImageIcon RUN_FAIL = this.getImage("runfail.png");
   public ImageIcon TESTSUITE = this.getImage("testsuite.png");
   public ImageIcon TESTGROUP = this.getImage("testgroup.png");
   public ImageIcon TESTGROUPX = this.getImage("testgroupx.png");

   public static ImageFactory getSingleton() {
      if (singleton == null) {
         singleton = new ImageFactory();
      }

      return singleton;
   }

   private ImageFactory() {
   }

   private ImageIcon getImage(String name) {
      name = "org/antlr/gunit/swingui/images/" + name;

      try {
         ClassLoader loader = ImageFactory.class.getClassLoader();
         InputStream in = loader.getResourceAsStream(name);
         byte[] data = new byte[in.available()];
         in.read(data);
         in.close();
         return new ImageIcon(data);
      } catch (IOException var5) {
         System.err.println("Can't load image file: " + name);
         System.exit(1);
      } catch (RuntimeException var6) {
         System.err.println("Can't load image file: " + name);
         System.exit(1);
      }

      return null;
   }
}
