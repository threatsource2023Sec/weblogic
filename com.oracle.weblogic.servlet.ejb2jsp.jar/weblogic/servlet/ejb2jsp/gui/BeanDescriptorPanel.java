package weblogic.servlet.ejb2jsp.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import weblogic.servlet.ejb2jsp.dd.BeanDescriptor;
import weblogic.utils.StringUtils;

public class BeanDescriptorPanel extends BasePanel implements ActionListener {
   BeanDescriptor _bean;
   JLabel _EJBName;
   JLabel _homeType;
   JLabel _remoteType;
   JLabel _EJBType;
   JCheckBox _enabled;
   JTextField _JNDIName;

   public BeanDescriptorPanel(BeanDescriptor _b) {
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
      lblfont = new Font(lblfont.getFontName(), 1, lblfont.getSize());
      gbc.gridx = 0;
      gbc.gridwidth = 1;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      l = new JLabel("EJB Name");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._EJBName = new JLabel("");
      this.add(this._EJBName, gbc);
      URL u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      ImageIcon img = new ImageIcon(u);
      JButton hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "EJBName");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
      gbc.gridx = 0;
      gbc.gridwidth = 1;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      l = new JLabel("Home Type");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._homeType = new JLabel("");
      this.add(this._homeType, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "homeType");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
      gbc.gridx = 0;
      gbc.gridwidth = 1;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      l = new JLabel("Remote Type");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._remoteType = new JLabel("");
      this.add(this._remoteType, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "remoteType");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
      gbc.gridx = 0;
      gbc.gridwidth = 1;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      l = new JLabel("EJB Type");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._EJBType = new JLabel("");
      this.add(this._EJBType, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "EJBType");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
      gbc.gridx = 0;
      gbc.gridwidth = 1;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      l = new JLabel("tags enabled");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.gridwidth = 2;
      this._enabled = new JCheckBox("");
      this.add(this._enabled, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "ejbEnabled");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
      gbc.gridx = 0;
      gbc.gridwidth = 1;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      l = new JLabel("JNDI Name");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._JNDIName = new JTextField("");
      this.add(this._JNDIName, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "JNDIName");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
   }

   public void bean2fields() {
      this._EJBName.setText(this._bean.getEJBName());
      this._homeType.setText(this._bean.getHomeType());
      this._remoteType.setText(this._bean.getRemoteType());
      this._EJBType.setText(this._bean.getEJBType());
      this._enabled.setSelected(this._bean.isEnabled());
      this._JNDIName.setText(this._bean.getJNDIName());
   }

   public void fields2bean() {
      String tmp = null;
      tmp = this._EJBName.getText().trim();
      if (!tmp.equals(this._bean.getEJBName())) {
         this.dirty = true;
         this._bean.setEJBName(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._homeType.getText().trim();
      if (!tmp.equals(this._bean.getHomeType())) {
         this.dirty = true;
         this._bean.setHomeType(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._remoteType.getText().trim();
      if (!tmp.equals(this._bean.getRemoteType())) {
         this.dirty = true;
         this._bean.setRemoteType(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._EJBType.getText().trim();
      if (!tmp.equals(this._bean.getEJBType())) {
         this.dirty = true;
         this._bean.setEJBType(StringUtils.valueOf(tmp));
      }

      if (this._bean.isEnabled() != this._enabled.isSelected()) {
         this.dirty = true;
         this._bean.setEnabled(this._enabled.isSelected());
      }

      tmp = null;
      tmp = this._JNDIName.getText().trim();
      if (!tmp.equals(this._bean.getJNDIName())) {
         this.dirty = true;
         this._bean.setJNDIName(StringUtils.valueOf(tmp));
      }

   }

   public void actionPerformed(ActionEvent ev) {
      ev.getSource();
   }

   public BeanDescriptor getBean() {
      return this._bean;
   }

   public static void main(String[] a) throws Exception {
      new JFrame("mytest");
   }
}
