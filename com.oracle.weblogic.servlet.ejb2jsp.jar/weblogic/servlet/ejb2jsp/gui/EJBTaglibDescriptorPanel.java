package weblogic.servlet.ejb2jsp.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import weblogic.servlet.ejb2jsp.dd.EJBTaglibDescriptor;

public class EJBTaglibDescriptorPanel extends BasePanel implements ActionListener {
   EJBTaglibDescriptor _bean;

   public EJBTaglibDescriptorPanel(EJBTaglibDescriptor _b) {
      this._bean = _b;
      this.addFields();
      this.bean2fields();
   }

   private Frame getParentFrame() {
      Object comp;
      for(comp = this; comp != null && !(comp instanceof Frame); comp = ((Component)comp).getParent()) {
      }

      if (comp == null) {
         throw new RuntimeException("not contained in frame?");
      } else {
         return (Frame)comp;
      }
   }

   private void addFields() {
      this.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = -1;
      gbc.insets = new Insets(5, 5, 5, 5);
      JLabel l = new JLabel("");
      Font lblfont = l.getFont();
      new Font(lblfont.getFontName(), 1, lblfont.getSize());
   }

   public void bean2fields() {
   }

   public void fields2bean() {
   }

   public void actionPerformed(ActionEvent ev) {
      ev.getSource();
   }

   public EJBTaglibDescriptor getBean() {
      return this._bean;
   }

   public static void main(String[] a) throws Exception {
      new JFrame("mytest");
   }
}
