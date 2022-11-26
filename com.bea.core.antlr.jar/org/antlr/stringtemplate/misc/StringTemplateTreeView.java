package org.antlr.stringtemplate.misc;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.event.TreeSelectionListener;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class StringTemplateTreeView extends JFrame {
   static final int WIDTH = 200;
   static final int HEIGHT = 300;

   public StringTemplateTreeView(String label, StringTemplate st) {
      super(label);
      JTreeStringTemplatePanel tp = new JTreeStringTemplatePanel(new JTreeStringTemplateModel(st), (TreeSelectionListener)null);
      Container content = this.getContentPane();
      content.add(tp, "Center");
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            Frame f = (Frame)e.getSource();
            f.setVisible(false);
            f.dispose();
         }
      });
      this.setSize(200, 300);
   }

   public static void main(String[] args) {
      StringTemplateGroup group = new StringTemplateGroup("dummy");
      StringTemplate bold = group.defineTemplate("bold", "<b>$attr$</b>");
      StringTemplate banner = group.defineTemplate("banner", "the banner");
      StringTemplate st = new StringTemplate(group, "<html>\n$banner(a=b)$<p><b>$name$:$email$</b>$if(member)$<i>$fontTag$member</font></i>$endif$");
      st.setAttribute("name", (Object)"Terence");
      st.setAttribute("name", (Object)"Tom");
      st.setAttribute("email", (Object)"parrt@cs.usfca.edu");
      st.setAttribute("templateAttr", (Object)bold);
      StringTemplateTreeView frame = new StringTemplateTreeView("StringTemplate JTree Example", st);
      frame.setVisible(true);
   }
}
