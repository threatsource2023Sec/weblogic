package weblogic.servlet.ejb2jsp.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTML.Tag;
import weblogic.tools.ui.AWTUtils;

public class HelpFrame extends JFrame implements HyperlinkListener {
   private static final boolean debug = false;
   private boolean wasVisible;
   JFrame parent;
   JEditorPane html;
   JScrollPane scroll;
   HTMLDocument doc;
   private Map anchors = new HashMap();

   public void hyperlinkUpdate(HyperlinkEvent hle) {
      if (hle.getEventType() == EventType.ACTIVATED) {
         URL u = hle.getURL();
         String desc = hle.getDescription();
         Object src = hle.getSource();
         p("hyperlink event: u='" + u + "' desc='" + desc + "' src type=" + src.getClass().getName());
         if (desc != null) {
            this.scroll2anchor(desc);
         }
      }
   }

   public void scroll2anchor(String anchor) {
      Integer I = (Integer)this.anchors.get(anchor);
      if (I != null) {
         int offset = I;
         p("scroll to " + offset);
         Dimension rect;
         if (!this.isVisible() && !this.wasVisible) {
            this.wasVisible = true;
            rect = this.getPreferredSize();
            rect.width = Math.max(rect.width, 350);
            this.setSize(rect.width + 30, 400);
            AWTUtils.centerOnWindow(this, this.parent);
         }

         this.setVisible(true);
         rect = null;

         Rectangle rect;
         try {
            rect = this.html.modelToView(offset);
            p("got rect=" + rect);
         } catch (Exception var7) {
            var7.printStackTrace();
            return;
         }

         JViewport vp = this.scroll.getViewport();
         Point p = new Point();
         p.x = 0;
         p.y = rect.y;
         vp.setViewPosition(p);
      }
   }

   static void p(String s) {
   }

   static String readHTML(String file) throws Exception {
      FileInputStream is = new FileInputStream(file);
      byte[] b = new byte[is.available()];
      is.read(b);
      is.close();
      String s = new String(b);
      return s;
   }

   static String readHTML(URL u) throws Exception {
      InputStream is = u.openStream();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] b = new byte[1024];
      int r = false;

      int r;
      while((r = is.read(b)) > 0) {
         baos.write(b, 0, r);
      }

      is.close();
      b = baos.toByteArray();
      String s = new String(b);
      return s;
   }

   private Image getFrameIcon() {
      return AWTUtils.loadImage(this.getClass().getClassLoader(), "/weblogic/graphics/W.gif");
   }

   public HelpFrame(String title, JFrame parent, URL u) throws Exception {
      super(title);
      Image I = this.getFrameIcon();
      if (I != null) {
         this.setIconImage(I);
      }

      this.parent = parent;
      String text = readHTML(u);
      this.html = new JEditorPane("text/html", text);
      this.html.setEditable(false);
      this.html.addHyperlinkListener(this);
      this.scroll = new JScrollPane(this.html);
      this.getContentPane().add(this.scroll);
      this.doc = (HTMLDocument)this.html.getDocument();
      HTMLDocument.Iterator atags = this.doc.getIterator(Tag.A);

      for(int i = false; atags.isValid(); atags.next()) {
         AttributeSet as = atags.getAttributes();
         Enumeration names = as.getAttributeNames();

         while(names.hasMoreElements()) {
            Object s = names.nextElement();
            Object val = as.getAttribute(s);
            String sstring = s.toString();
            String valstring = val.toString();
            if (sstring.equals("name") && !valstring.equals("a")) {
               int offset = atags.getStartOffset();
               p("got anchor \"" + valstring + "\" at offset " + offset);
               this.anchors.put(valstring, new Integer(offset));
               break;
            }
         }
      }

      p("CTOR done, anchors=" + this.anchors);
   }
}
