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
import weblogic.servlet.ejb2jsp.Utils;
import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.utils.StringUtils;

public class EJBMethodDescriptorPanel extends BasePanel implements ActionListener {
   static final long serialVersionUID = 2139021295039679016L;
   EJBMethodDescriptor _bean;
   JLabel _name;
   JTextField _tagName;
   JCheckBox _enabled;
   JCheckBox _evalOut;
   JLabel _targetType;
   JLabel _returnType;
   JLabel _signature;
   JTextField _info;

   public EJBMethodDescriptorPanel(EJBMethodDescriptor _b) {
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
      l = new JLabel("Method Name");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._name = new JLabel("");
      this.add(this._name, gbc);
      URL u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      ImageIcon img = new ImageIcon(u);
      JButton hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "methodName");
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
      l = new JLabel("Tag Name");
      l.setToolTipText("sets the JSP tag name for this EJB method");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._tagName = new JTextField("");
      this._tagName.setToolTipText("sets the JSP tag name for this EJB method");
      this.add(this._tagName, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "tagName");
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
      l = new JLabel("Generate tag");
      l.setToolTipText("sets whether a tag should be generated for this method");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.gridwidth = 2;
      this._enabled = new JCheckBox("");
      this._enabled.setToolTipText("sets whether a tag should be generated for this method");
      this.add(this._enabled, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "tagEnabled");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
      if (!Utils.isVoid(this._bean.getReturnType())) {
         gbc.gridx = 0;
         gbc.gridwidth = 1;
         ++gbc.gridy;
         gbc.weightx = 0.0;
         gbc.fill = 0;
         gbc.anchor = 13;
         l = new JLabel("Eval Out");
         l.setToolTipText("setting this will automatically print the return value to the servlet output stream");
         l.setFont(lblfont);
         l.setOpaque(false);
         this.add(l, gbc);
         ++gbc.gridx;
         gbc.anchor = 17;
         gbc.gridwidth = 2;
         this._evalOut = new JCheckBox("");
         this._evalOut.setToolTipText("setting this will automatically print the return value to the servlet output stream");
         this.add(this._evalOut, gbc);
         u = this.getClass().getResource("/weblogic/graphics/ques.gif");
         img = new ImageIcon(u);
         hep = new JButton(img);
         hep.addActionListener(Main.getInstance());
         hep.setBorder(new EmptyBorder(0, 0, 0, 0));
         hep.putClientProperty("help-anchor", "evalOut");
         gbc.gridx += 2;
         gbc.gridwidth = 1;
         gbc.weightx = 0.0;
         gbc.fill = 0;
         this.add(hep, gbc);
      }

      gbc.gridx = 0;
      gbc.gridwidth = 1;
      ++gbc.gridy;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      gbc.anchor = 13;
      l = new JLabel("Target Type");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._targetType = new JLabel("");
      this.add(this._targetType, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "targetType");
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
      l = new JLabel("Return Type");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._returnType = new JLabel("");
      this.add(this._returnType, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "returnType");
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
      l = new JLabel("Method Signature");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._signature = new JLabel("");
      this.add(this._signature, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "signature");
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
      l = new JLabel("Description");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._info = new JTextField("");
      this.add(this._info, gbc);
      u = this.getClass().getResource("/weblogic/graphics/ques.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "info");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
   }

   public void bean2fields() {
      this._name.setText(this._bean.getName());
      this._tagName.setText(this._bean.getTagName());
      this._enabled.setSelected(this._bean.isEnabled());
      if (this._evalOut != null) {
         this._evalOut.setSelected(this._bean.isEvalOut());
      }

      this._targetType.setText(this._bean.getTargetType());
      this._returnType.setText(this._bean.getReturnType());
      this._signature.setText(this._bean.getSignature());
      this._info.setText(this._bean.getInfo());
   }

   public void fields2bean() {
      String tmp = null;
      tmp = this._name.getText().trim();
      if (!tmp.equals(this._bean.getName())) {
         this.dirty = true;
         this._bean.setName(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._tagName.getText().trim();
      if (!tmp.equals(this._bean.getTagName())) {
         this.dirty = true;
         this._bean.setTagName(StringUtils.valueOf(tmp));
      }

      tmp = null;
      if (this._bean.isEnabled() != this._enabled.isSelected()) {
         this.dirty = true;
         this._bean.setEnabled(this._enabled.isSelected());
      }

      if (this._evalOut != null) {
         tmp = null;
         if (this._bean.isEvalOut() != this._evalOut.isSelected()) {
            this.dirty = true;
            this._bean.setEvalOut(this._evalOut.isSelected());
         }
      }

      tmp = null;
      tmp = this._targetType.getText().trim();
      if (!tmp.equals(this._bean.getTargetType())) {
         this.dirty = true;
         this._bean.setTargetType(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._returnType.getText().trim();
      if (!tmp.equals(this._bean.getReturnType())) {
         this.dirty = true;
         this._bean.setReturnType(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._signature.getText().trim();
      if (!tmp.equals(this._bean.getSignature())) {
         this.dirty = true;
      }

      tmp = null;
      tmp = this._info.getText().trim();
      if (!tmp.equals(this._bean.getInfo())) {
         this.dirty = true;
         this._bean.setInfo(StringUtils.valueOf(tmp));
      }

   }

   public void actionPerformed(ActionEvent ev) {
      ev.getSource();
   }

   public EJBMethodDescriptor getBean() {
      return this._bean;
   }

   public static void main(String[] a) throws Exception {
      new JFrame("mytest");
   }
}
