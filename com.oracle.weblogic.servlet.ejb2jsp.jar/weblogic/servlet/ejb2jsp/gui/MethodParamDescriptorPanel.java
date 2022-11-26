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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import weblogic.servlet.ejb2jsp.dd.MethodParamDescriptor;
import weblogic.utils.StringUtils;

public class MethodParamDescriptorPanel extends BasePanel implements ActionListener {
   MethodParamDescriptor _bean;
   private static final String[] _default_constrained = new String[]{"NONE", "EXPRESSION", "METHOD"};
   JTextField _name;
   JLabel _type;
   JComboBox _default;
   JTextField _defaultValue;
   JTextArea _defaultMethod;

   public MethodParamDescriptorPanel(MethodParamDescriptor _b) {
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
      l = new JLabel("Attribute Name");
      l.setToolTipText("sets the attribute name.  The name should not conflict with names of other attributes of this tag.");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._name = new JTextField("");
      this._name.setToolTipText("sets the attribute name.  The name should not conflict with names of other attributes of this tag.");
      this.add(this._name, gbc);
      URL u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      ImageIcon img = new ImageIcon(u);
      JButton hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "attributeName");
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
      l = new JLabel("Attribute Type");
      l.setToolTipText("Describes the expected java type for this attribute");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._type = new JLabel("");
      this._type.setToolTipText("Describes the expected java type for this attribute");
      this.add(this._type, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "type");
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
      l = new JLabel("Default Attribute Value");
      l.setToolTipText("sets if/how to get a default value for this attribute if it isn't specified in a tag");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.gridwidth = 2;
      this._default = new JComboBox(_default_constrained);
      this._default.setToolTipText("sets if/how to get a default value for this attribute if it isn't specified in a tag");
      this.add(this._default, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "default");
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
      l = new JLabel("Default Expression");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._defaultValue = new JTextField("");
      this.add(this._defaultValue, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "defaultValue");
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
      l = new JLabel("Default Method Body");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 1;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._defaultMethod = new JTextArea("", 15, 40);
      this._defaultMethod.setBorder(new BevelBorder(1));
      this.add(this._defaultMethod, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "defaultMethod");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
   }

   public void bean2fields() {
      this._name.setText(this._bean.getName());
      this._type.setText(this._bean.getType());
      this._default.setSelectedItem(this._bean.getDefault());
      this._defaultValue.setText(this._bean.getDefaultValue());
      this._defaultMethod.setText(this._bean.getDefaultMethod());
   }

   public void fields2bean() {
      String tmp = null;
      tmp = this._name.getText().trim();
      if (!tmp.equals(this._bean.getName())) {
         this.dirty = true;
         this._bean.setName(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._type.getText().trim();
      if (!tmp.equals(this._bean.getType())) {
         this.dirty = true;
         this._bean.setType(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = (String)this._default.getSelectedItem();
      String currentVal = this._bean.getDefault();
      if (!tmp.equals(currentVal)) {
         this.dirty = true;
         this._bean.setDefault(tmp);
      }

      tmp = null;
      tmp = this._defaultValue.getText().trim();
      if (!tmp.equals(this._bean.getDefaultValue())) {
         this.dirty = true;
         this._bean.setDefaultValue(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._defaultMethod.getText().trim();
      if (!tmp.equals(this._bean.getDefaultMethod())) {
         this.dirty = true;
         this._bean.setDefaultMethod(StringUtils.valueOf(tmp));
      }

   }

   public void actionPerformed(ActionEvent ev) {
      ev.getSource();
   }

   public MethodParamDescriptor getBean() {
      return this._bean;
   }

   public static void main(String[] a) throws Exception {
      new JFrame("mytest");
   }
}
