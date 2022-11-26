package weblogic.servlet.ejb2jsp.gui;

import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import weblogic.servlet.ejb2jsp.dd.FilesystemInfoDescriptor;
import weblogic.tools.ui.AWTUtils;
import weblogic.utils.StringUtils;

public class FilesystemInfoDescriptorPanel extends BasePanel implements ActionListener {
   FilesystemInfoDescriptor _bean;
   private static final String[] _saveAs_constrained = new String[]{"DIRECTORY", "JAR"};
   JLabel _EJBJarFile;
   JTextField _javacPath;
   JTextField _javacFlags;
   JTextField _package;
   JCheckBox _keepgenerated;
   JComboBox _saveAs;
   JTextField _saveDirTldFile;
   JTextField _saveDirClassDir;
   JTextField _saveJarFile;
   JTextField _saveJarTmpdir;
   JLabel[] _sourcePath;
   JButton _sourcePath_editButton;
   JPanel _sourcePath_panel;
   JLabel[] _compileClasspath;
   JButton _compileClasspath_editButton;
   JPanel _compileClasspath_panel;

   public FilesystemInfoDescriptorPanel(FilesystemInfoDescriptor _b) {
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
      l = new JLabel("EJB Jar File");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._EJBJarFile = new JLabel("");
      this.add(this._EJBJarFile, gbc);
      URL u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      ImageIcon img = new ImageIcon(u);
      JButton hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "EJBJarFile");
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
      l = new JLabel("Java Compiler");
      l.setToolTipText("sets the compiler to use for building");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._javacPath = new JTextField("");
      this._javacPath.setToolTipText("sets the compiler to use for building");
      this.add(this._javacPath, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "javacPath");
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
      l = new JLabel("Extra Flags");
      l.setToolTipText("sets extra compiler flags for build, if needed");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._javacFlags = new JTextField("");
      this._javacFlags.setToolTipText("sets extra compiler flags for build, if needed");
      this.add(this._javacFlags, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "javacFlags");
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
      l = new JLabel("java package");
      l.setToolTipText("sets the output package for generated java files");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._package = new JTextField("");
      this._package.setToolTipText("sets the output package for generated java files");
      this.add(this._package, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "package");
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
      l = new JLabel("keep generated code");
      l.setToolTipText("sets if generated code is saved after a build");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.gridwidth = 2;
      this._keepgenerated = new JCheckBox("");
      this._keepgenerated.setToolTipText("sets if generated code is saved after a build");
      this.add(this._keepgenerated, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "keepgenerated");
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
      l = new JLabel("Output Type");
      l.setToolTipText("Output can be to a WEB-INF/classes DIRECTORY or a taglib JAR");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.gridwidth = 2;
      this._saveAs = new JComboBox(_saveAs_constrained);
      this._saveAs.setToolTipText("Output can be to a WEB-INF/classes DIRECTORY or a taglib JAR");
      this.add(this._saveAs, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "saveAs");
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
      l = new JLabel("DIRECTORY .tld File");
      l.setToolTipText("sets the .tld location for DIRECTORY output");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._saveDirTldFile = new JTextField("");
      this._saveDirTldFile.setToolTipText("sets the .tld location for DIRECTORY output");
      this.add(this._saveDirTldFile, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "saveDirTldFile");
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
      l = new JLabel("DIRECTORY classes");
      l.setToolTipText("sets classes location for DIRECTORY output");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._saveDirClassDir = new JTextField("");
      this._saveDirClassDir.setToolTipText("sets classes location for DIRECTORY output");
      this.add(this._saveDirClassDir, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "saveDirClassDir");
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
      l = new JLabel("JAR Output File");
      l.setToolTipText("sets the output jar file when output type is JAR");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._saveJarFile = new JTextField("");
      this._saveJarFile.setToolTipText("sets the output jar file when output type is JAR");
      this.add(this._saveJarFile, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "saveJarFile");
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
      l = new JLabel("JAR tmp dir");
      l.setToolTipText("a tmp directory used for packaging JAR output");
      l.setFont(lblfont);
      l.setOpaque(false);
      this.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      gbc.gridwidth = 2;
      this._saveJarTmpdir = new JTextField("");
      this._saveJarTmpdir.setToolTipText("a tmp directory used for packaging JAR output");
      this.add(this._saveJarTmpdir, gbc);
      u = this.getClass().getResource("/weblogic/tools/ui/images/prefs.gif");
      img = new ImageIcon(u);
      hep = new JButton(img);
      hep.addActionListener(Main.getInstance());
      hep.setBorder(new EmptyBorder(0, 0, 0, 0));
      hep.putClientProperty("help-anchor", "saveJarTmpdir");
      gbc.gridx += 2;
      gbc.gridwidth = 1;
      gbc.weightx = 0.0;
      gbc.fill = 0;
      this.add(hep, gbc);
      JPanel pan = new JPanel();
      pan.setToolTipText("sets directory of the EJB sources");
      Border border = new LineBorder(Color.black);
      Border border = new TitledBorder(border, "EJB Source Path", 0, 0, lblfont);
      pan.setBorder(border);
      pan.setLayout(new GridBagLayout());
      this._sourcePath_panel = pan;
      this._sourcePath_editButton = new JButton("Edit sourcePath elements...");
      this._sourcePath_editButton.addActionListener(this);
      this._sourcePath_setFields();
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.gridwidth = 3;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.add(pan, gbc);
      pan = new JPanel();
      pan.setToolTipText("If this EJB jar depends on additional classes when it is compiled, you should add those additional classpath elements here.");
      border = new LineBorder(Color.black);
      border = new TitledBorder(border, "Extra Compile Classpath", 0, 0, lblfont);
      pan.setBorder(border);
      pan.setLayout(new GridBagLayout());
      this._compileClasspath_panel = pan;
      this._compileClasspath_editButton = new JButton("Edit compileClasspath elements...");
      this._compileClasspath_editButton.addActionListener(this);
      this._compileClasspath_setFields();
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.gridwidth = 3;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.add(pan, gbc);
   }

   public void bean2fields() {
      this._EJBJarFile.setText(this._bean.getEJBJarFile());
      this._javacPath.setText(this._bean.getJavacPath());
      this._javacFlags.setText(this._bean.getJavacFlags());
      this._package.setText(this._bean.getPackage());
      this._keepgenerated.setSelected(this._bean.getKeepgenerated());
      this._saveAs.setSelectedItem(this._bean.getSaveAs());
      this._saveDirTldFile.setText(this._bean.getSaveDirTldFile());
      this._saveDirClassDir.setText(this._bean.getSaveDirClassDir());
      this._saveJarFile.setText(this._bean.getSaveJarFile());
      this._saveJarTmpdir.setText(this._bean.getSaveJarTmpdir());
      String[] vals = this._bean.getSourcePath();

      int i;
      for(i = 0; i < vals.length; ++i) {
         this._sourcePath[i].setText("" + vals[i]);
      }

      vals = this._bean.getCompileClasspath();

      for(i = 0; i < vals.length; ++i) {
         this._compileClasspath[i].setText("" + vals[i]);
      }

   }

   public void fields2bean() {
      String tmp = null;
      tmp = this._EJBJarFile.getText().trim();
      if (!tmp.equals(this._bean.getEJBJarFile())) {
         this.dirty = true;
         this._bean.setEJBJarFile(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._javacPath.getText().trim();
      if (!tmp.equals(this._bean.getJavacPath())) {
         this.dirty = true;
         this._bean.setJavacPath(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._javacFlags.getText().trim();
      if (!tmp.equals(this._bean.getJavacFlags())) {
         this.dirty = true;
         this._bean.setJavacFlags(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._package.getText().trim();
      if (!tmp.equals(this._bean.getPackage())) {
         this.dirty = true;
         this._bean.setPackage(StringUtils.valueOf(tmp));
      }

      tmp = null;
      if (this._bean.getKeepgenerated() != this._keepgenerated.isSelected()) {
         this.dirty = true;
         this._bean.setKeepgenerated(this._keepgenerated.isSelected());
      }

      tmp = null;
      tmp = (String)this._saveAs.getSelectedItem();
      String currentVal = this._bean.getSaveAs();
      if (!tmp.equals(currentVal)) {
         this.dirty = true;
         this._bean.setSaveAs(tmp);
      }

      tmp = null;
      tmp = this._saveDirTldFile.getText().trim();
      if (!tmp.equals(this._bean.getSaveDirTldFile())) {
         this.dirty = true;
         this._bean.setSaveDirTldFile(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._saveDirClassDir.getText().trim();
      if (!tmp.equals(this._bean.getSaveDirClassDir())) {
         this.dirty = true;
         this._bean.setSaveDirClassDir(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._saveJarFile.getText().trim();
      if (!tmp.equals(this._bean.getSaveJarFile())) {
         this.dirty = true;
         this._bean.setSaveJarFile(StringUtils.valueOf(tmp));
      }

      tmp = null;
      tmp = this._saveJarTmpdir.getText().trim();
      if (!tmp.equals(this._bean.getSaveJarTmpdir())) {
         this.dirty = true;
         this._bean.setSaveJarTmpdir(StringUtils.valueOf(tmp));
      }

      tmp = null;
      String[] vals = new String[this._sourcePath.length];

      int i;
      for(i = 0; i < this._sourcePath.length; ++i) {
         tmp = this._sourcePath[i].getText().trim();
         vals[i] = tmp;
      }

      this._bean.setSourcePath(vals);
      tmp = null;
      vals = new String[this._compileClasspath.length];

      for(i = 0; i < this._compileClasspath.length; ++i) {
         tmp = this._compileClasspath[i].getText().trim();
         vals[i] = tmp;
      }

      this._bean.setCompileClasspath(vals);
   }

   public void actionPerformed(ActionEvent ev) {
      Object src = ev.getSource();
      Frame f;
      String[] elems;
      String[] selems;
      int i;
      ArrayEditorDialog aed;
      if (src == this._sourcePath_editButton) {
         f = this.getParentFrame();
         elems = this._bean.getSourcePath();
         if (elems == null) {
            elems = new String[0];
         }

         selems = new String[elems.length];

         for(i = 0; i < selems.length; ++i) {
            selems[i] = "" + elems[i];
         }

         aed = new ArrayEditorDialog(f, "Edit sourcePath Elements", true, selems);
         aed.pack();
         AWTUtils.centerOnWindow(aed, f);
         aed.show();
         selems = aed.getElements();
         this._bean.setSourcePath(selems);
         this._sourcePath_setFields();
      }

      if (src == this._compileClasspath_editButton) {
         f = this.getParentFrame();
         elems = this._bean.getCompileClasspath();
         if (elems == null) {
            elems = new String[0];
         }

         selems = new String[elems.length];

         for(i = 0; i < selems.length; ++i) {
            selems[i] = "" + elems[i];
         }

         aed = new ArrayEditorDialog(f, "Edit compileClasspath Elements", true, selems);
         aed.pack();
         AWTUtils.centerOnWindow(aed, f);
         aed.show();
         selems = aed.getElements();
         this._bean.setCompileClasspath(selems);
         this._compileClasspath_setFields();
      }

   }

   private void _sourcePath_setFields() {
      JPanel pan = this._sourcePath_panel;
      pan.removeAll();
      GridBagConstraints g = new GridBagConstraints();
      g.insets = new Insets(1, 1, 1, 1);
      g.gridx = 0;
      g.gridy = -1;
      g.gridheight = 1;
      g.anchor = 17;
      String[] elems = this._bean.getSourcePath();
      int nelems = 0;
      if (elems != null) {
         nelems = elems.length;
      }

      this._sourcePath = new JLabel[nelems];

      for(int i = 0; i < nelems; ++i) {
         ++g.gridy;
         this._sourcePath[i] = new JLabel("" + elems[i]);
         g.fill = 2;
         g.weightx = 1.0;
         g.gridx = 0;
         pan.add(this._sourcePath[i], g);
      }

      ++g.gridy;
      g.fill = 0;
      g.weightx = 0.0;
      g.gridx = 0;
      g.insets = new Insets(5, 5, 5, 5);
      pan.add(this._sourcePath_editButton, g);
      pan.invalidate();
      this.invalidate();
      pan.doLayout();
      this.repaint();
      pan.repaint();
   }

   private void _compileClasspath_setFields() {
      JPanel pan = this._compileClasspath_panel;
      pan.removeAll();
      GridBagConstraints g = new GridBagConstraints();
      g.insets = new Insets(1, 1, 1, 1);
      g.gridx = 0;
      g.gridy = -1;
      g.gridheight = 1;
      g.anchor = 17;
      String[] elems = this._bean.getCompileClasspath();
      int nelems = 0;
      if (elems != null) {
         nelems = elems.length;
      }

      this._compileClasspath = new JLabel[nelems];

      for(int i = 0; i < nelems; ++i) {
         ++g.gridy;
         this._compileClasspath[i] = new JLabel("" + elems[i]);
         g.fill = 2;
         g.weightx = 1.0;
         g.gridx = 0;
         pan.add(this._compileClasspath[i], g);
      }

      ++g.gridy;
      g.fill = 0;
      g.weightx = 0.0;
      g.gridx = 0;
      g.insets = new Insets(5, 5, 5, 5);
      pan.add(this._compileClasspath_editButton, g);
      pan.invalidate();
      this.invalidate();
      pan.doLayout();
      this.repaint();
      pan.repaint();
   }

   public FilesystemInfoDescriptor getBean() {
      return this._bean;
   }

   public static void main(String[] a) throws Exception {
      new JFrame("mytest");
   }
}
