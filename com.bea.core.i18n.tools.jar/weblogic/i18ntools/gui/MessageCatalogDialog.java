package weblogic.i18ntools.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.WrongTypeException;

public final class MessageCatalogDialog extends JDialog implements ActionListener {
   private static final boolean debug = false;
   static final String[] CATALOG_TYPES = new String[]{"Log messages", "Simple messages"};
   private MsgEditorTextFormatter fmt;
   private MessageCatalog myCatalog;
   private JFileChooser myChooser;
   private MessageEditor myEditor;
   private JComboBox myCatalogTypeField;
   private JCheckBox myIsLoggables;
   private GUIClientTextField myBaseidField;
   private GUIClientTextField myEndidField;
   private GUIClientTextField myI18nPackageField;
   private GUIClientTextField myL10nPackageField;
   private GUIClientTextField mySubsystemField;
   private GUIClientTextField myPrefixField;
   private JLabel myVersionField;
   private JLabel myNewBaseidLabel;
   private JLabel myNewEndidLabel;
   private GUIClientTextField mySourceTreeRootField;

   public MessageCatalogDialog(MessageEditor parent, String title) {
      this(parent, title, "");
   }

   public MessageCatalogDialog(MessageEditor parent, String title, String inFile) {
      super(parent, title);
      this.fmt = MsgEditorTextFormatter.getInstance();
      this.myCatalog = null;
      this.myChooser = null;
      this.mySourceTreeRootField = null;
      this.myEditor = parent;
      this.getContentPane().setLayout(new GridBagLayout());
      JPanel newCatPanel = new JPanel();
      newCatPanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(1, 10, 1, 10);
      gbc.gridx = gbc.gridy = 0;
      gbc.anchor = 10;
      JLabel sourceTreeRootLabel = new JLabel(this.fmt.labelMsgCat());
      newCatPanel.add(sourceTreeRootLabel, gbc);
      ++gbc.gridx;
      String lFile = "";
      if (inFile != null && inFile.length() > 0 && this.validateCatalogFileName(inFile)) {
         lFile = inFile;
      }

      this.mySourceTreeRootField = new GUIClientTextField(lFile, 20);
      this.mySourceTreeRootField.setActionCommand("ValidateCatalogFile");
      this.mySourceTreeRootField.addActionListener(this);
      newCatPanel.add(this.mySourceTreeRootField, gbc);
      JButton fileButton = new JButton(this.fmt.buttonBrowse());
      fileButton.addActionListener(this);
      fileButton.setActionCommand("Browse");
      ++gbc.gridx;
      newCatPanel.add(fileButton, gbc);
      JLabel catalogTypeLabel = new JLabel(this.fmt.labelCatType());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(catalogTypeLabel, gbc);
      this.myCatalogTypeField = new JComboBox(CATALOG_TYPES);
      ++gbc.gridx;
      newCatPanel.add(this.myCatalogTypeField, gbc);
      this.myCatalogTypeField.addActionListener(this);
      this.myCatalogTypeField.setActionCommand("CatalogTypeSelected");
      JLabel i18nPackageLabel = new JLabel(this.fmt.labelI18nPkg());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(i18nPackageLabel, gbc);
      this.myI18nPackageField = new GUIClientTextField("", 20);
      ++gbc.gridx;
      newCatPanel.add(this.myI18nPackageField, gbc);
      JLabel l10nPackageLabel = new JLabel(this.fmt.labelL10nPkg());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(l10nPackageLabel, gbc);
      this.myL10nPackageField = new GUIClientTextField("", 20);
      ++gbc.gridx;
      newCatPanel.add(this.myL10nPackageField, gbc);
      JLabel subsystemLabel = new JLabel(this.fmt.labelSubsystem());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(subsystemLabel, gbc);
      this.mySubsystemField = new GUIClientTextField("", 20);
      ++gbc.gridx;
      newCatPanel.add(this.mySubsystemField, gbc);
      JLabel prefixLabel = new JLabel(this.fmt.labelPrefix());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(prefixLabel, gbc);
      this.myPrefixField = new GUIClientTextField("", 20);
      ++gbc.gridx;
      newCatPanel.add(this.myPrefixField, gbc);
      JLabel versionLabel = new JLabel(this.fmt.labelVersion());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(versionLabel, gbc);
      this.myVersionField = new JLabel("1.0");
      ++gbc.gridx;
      gbc.anchor = 17;
      newCatPanel.add(this.myVersionField, gbc);
      gbc.anchor = 10;
      this.myNewBaseidLabel = new JLabel(this.fmt.labelBaseId());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(this.myNewBaseidLabel, gbc);
      this.myBaseidField = new GUIClientTextField("", 20);
      ++gbc.gridx;
      newCatPanel.add(this.myBaseidField, gbc);
      this.myNewEndidLabel = new JLabel(this.fmt.labelEndId());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(this.myNewEndidLabel, gbc);
      this.myEndidField = new GUIClientTextField("", 20);
      ++gbc.gridx;
      newCatPanel.add(this.myEndidField, gbc);
      JLabel loggablesLabel = new JLabel(this.fmt.labelLoggables());
      gbc.gridx = 0;
      ++gbc.gridy;
      newCatPanel.add(loggablesLabel, gbc);
      this.myIsLoggables = new JCheckBox();
      this.myIsLoggables.setSelected((new MessageCatalog(this.myEditor.getConfig())).getLoggables());
      ++gbc.gridx;
      newCatPanel.add(this.myIsLoggables, gbc);
      GridBagConstraints contPaneConstraints = new GridBagConstraints();
      contPaneConstraints.anchor = 11;
      contPaneConstraints.fill = 2;
      contPaneConstraints.weightx = 1.0;
      contPaneConstraints.gridwidth = 0;
      this.getContentPane().add(newCatPanel, contPaneConstraints);
      JPanel createActionPanel = new JPanel();
      JButton addChangeButton = new JButton(this.fmt.buttonCreate());
      addChangeButton.setActionCommand("CreateCatalog");
      addChangeButton.addActionListener(this);
      createActionPanel.add(addChangeButton);
      JButton cancelButton = new JButton(this.fmt.buttonCancel());
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MessageCatalogDialog.this.clearFields();
            MessageCatalogDialog.this.setVisible(false);
         }
      });
      createActionPanel.add(cancelButton);
      this.getContentPane().add(createActionPanel, contPaneConstraints);
      this.pack();
      this.centerWindow(this);
      this.setVisible(true);
   }

   private void centerWindow(Window win) {
      Point winLoc = new Point();
      Dimension parentDim = this.myEditor.getSize();
      Dimension dialogDim = this.getSize();
      winLoc.x = parentDim.width / 2 - dialogDim.width / 2;
      winLoc.y = parentDim.height / 2 - dialogDim.height / 2;
      if (winLoc.x < 0) {
         winLoc.x = 0;
      }

      if (winLoc.y < 0) {
         winLoc.y = 0;
      }

      win.setLocation(winLoc);
   }

   public void actionPerformed(ActionEvent ev) {
      String cmd = ev.getActionCommand();
      if (cmd.equals("CreateCatalog")) {
         if (this.validateCatalogFileName(this.mySourceTreeRootField.getText()) && this.createCatalogFromFields()) {
            this.myEditor.setCatalog(this.myCatalog, this.mySourceTreeRootField.getText());
            this.myEditor.writeCatalogToDisk();
            this.clearFields();
            this.myEditor.setupParser();
            this.setVisible(false);
         }
      } else if (cmd.equals("ValidateCatalogFile")) {
         this.validateCatalogFileName(this.mySourceTreeRootField.getText());
      } else if (cmd.equals("CatalogTypeSelected")) {
         this.configureForCatalogType(CATALOG_TYPES[this.myCatalogTypeField.getSelectedIndex()]);
      } else if (cmd.equals("Browse")) {
         if (this.myChooser == null) {
            this.myChooser = new JFileChooser(new File(this.myEditor.getCatalogDirectory()));
            XmlFileFilter xmlFilter = new XmlFileFilter();
            this.myChooser.setFileFilter(xmlFilter);
         }

         this.myChooser.setFileSelectionMode(2);
         int retval = this.myChooser.showDialog(this, (String)null);
         if (retval == 0) {
            File theFile = this.myChooser.getSelectedFile();
            if (theFile != null) {
               File fileChosen = this.myChooser.getSelectedFile();
               this.myEditor.setCatalogDirectory(fileChosen.getParent());
               String fileName = fileChosen.getAbsolutePath();
               this.mySourceTreeRootField.setText(fileName);
               this.mySourceTreeRootField.postActionEvent();
               this.mySourceTreeRootField.repaint();
               return;
            }
         }
      }

   }

   private void configureForCatalogType(String type) {
      if (type.equals(CATALOG_TYPES[0])) {
         this.myBaseidField.setVisible(true);
         this.myEndidField.setVisible(true);
         this.myNewBaseidLabel.setVisible(true);
         this.myNewEndidLabel.setVisible(true);
         this.myIsLoggables.setVisible(true);
      } else if (type.equals(CATALOG_TYPES[1])) {
         this.myBaseidField.setVisible(false);
         this.myEndidField.setVisible(false);
         this.myNewBaseidLabel.setVisible(false);
         this.myNewEndidLabel.setVisible(false);
         this.myIsLoggables.setVisible(false);
      }

      this.pack();
   }

   private boolean validateCatalogFileName(String fileName) {
      File fil = new File(fileName);
      if (fil.isDirectory()) {
         JOptionPane.showMessageDialog(this, this.fmt.msgMustBeFile(), this.fmt.tagError(), 0);
         return false;
      } else if (!fileName.endsWith(".xml")) {
         JOptionPane.showMessageDialog(this, this.fmt.msgMustBeXMLFile(), this.fmt.tagError(), 0);
         return false;
      } else {
         if (fil.exists()) {
            int option = JOptionPane.showConfirmDialog(this, this.fmt.msgFileExists(), this.fmt.tagExists(), 0);
            if (option == 1) {
               this.mySourceTreeRootField.setText("");
               return false;
            }
         }

         return true;
      }
   }

   private boolean createCatalogFromFields() {
      boolean worked = false;
      if (this.mySourceTreeRootField.getText() != null && !this.mySourceTreeRootField.getText().equals("")) {
         if (this.mySubsystemField.getText() != null && !this.mySubsystemField.getText().equals("")) {
            if (this.myVersionField.getText() != null && !this.myVersionField.getText().equals("")) {
               if (this.myCatalogTypeField.getSelectedIndex() == 1) {
                  worked = true;
               } else if (this.myCatalogTypeField.getSelectedIndex() == 0) {
                  if (this.myBaseidField.getText() != null && !this.myBaseidField.getText().equals("")) {
                     if (this.myEndidField.getText() != null && !this.myEndidField.getText().equals("")) {
                        try {
                           int intBaseId = Integer.parseInt(this.myBaseidField.getText());
                           if (intBaseId < 1000000) {
                              if (this.myEditor.isAllowingServerIds()) {
                                 if (intBaseId < 500000) {
                                    worked = true;
                                 } else {
                                    JOptionPane.showMessageDialog(this, this.fmt.msgInvalidServerBase(500000), this.fmt.tagError(), 0);
                                    worked = false;
                                 }
                              } else if (intBaseId >= 500000) {
                                 worked = true;
                              } else {
                                 JOptionPane.showMessageDialog(this, this.fmt.msgInvalidNonServerBase(500000), this.fmt.tagError(), 0);
                                 worked = false;
                              }
                           } else {
                              JOptionPane.showMessageDialog(this, this.fmt.msgInvalidBase(), this.fmt.tagError(), 0);
                              worked = false;
                           }
                        } catch (NumberFormatException var5) {
                           JOptionPane.showMessageDialog(this, this.fmt.msgNotIntBase(), this.fmt.tagError(), 0);
                           worked = false;
                        }
                     } else {
                        JOptionPane.showMessageDialog(this, this.fmt.msgEnterEndId(), this.fmt.tagError(), 0);
                     }
                  } else {
                     JOptionPane.showMessageDialog(this, this.fmt.msgEnterBaseId(), this.fmt.tagError(), 0);
                  }

                  if (worked) {
                     try {
                        if (Integer.parseInt(this.myEndidField.getText()) >= 1000000 || Integer.parseInt(this.myEndidField.getText()) <= Integer.parseInt(this.myBaseidField.getText())) {
                           worked = false;
                           JOptionPane.showMessageDialog(this, this.fmt.msgBadEndId(), this.fmt.tagError(), 0);
                        }
                     } catch (NumberFormatException var6) {
                        worked = false;
                        JOptionPane.showMessageDialog(this, this.fmt.msgNotIntEnd(), this.fmt.tagError(), 0);
                     }
                  }
               }
            } else {
               JOptionPane.showMessageDialog(this, this.fmt.msgEnterVersion(), this.fmt.tagError(), 0);
            }
         } else {
            JOptionPane.showMessageDialog(this, this.fmt.msgEnterSubsystem(), this.fmt.tagError(), 0);
         }
      } else {
         JOptionPane.showMessageDialog(this, this.fmt.msgEnterPath(), this.fmt.tagError(), 0);
      }

      if (worked) {
         this.myCatalog = new MessageCatalog(this.myEditor.getConfig());
         if (this.myI18nPackageField.getText() != null && !this.myI18nPackageField.getText().equals("")) {
            this.myCatalog.set("i18n_package", this.myI18nPackageField.getText());
         }

         if (this.myL10nPackageField.getText() != null && !this.myL10nPackageField.getText().equals("")) {
            this.myCatalog.set("l10n_package", this.myL10nPackageField.getText());
         }

         this.myCatalog.set("subsystem", this.mySubsystemField.getText());
         this.myCatalog.set("version", this.myVersionField.getText());
         byte type;
         if (this.myCatalogTypeField.getSelectedIndex() == 0) {
            this.myCatalog.set("baseid", this.myBaseidField.getText());
            this.myCatalog.set("endid", this.myEndidField.getText());
            this.myCatalog.set("loggables", this.myIsLoggables.isSelected() ? "true" : "false");
            this.myCatalog.set("prefix", this.myPrefixField.getText());
            type = 2;
         } else {
            type = 1;
         }

         try {
            this.myCatalog.verifyMessageCatalog(type);
         } catch (WrongTypeException var4) {
            System.err.println(var4.toString());
         }
      }

      return worked;
   }

   private void clearFields() {
      this.mySourceTreeRootField.setText("");
      this.myI18nPackageField.setText("");
      this.myL10nPackageField.setText("");
      this.mySubsystemField.setText("");
      this.myBaseidField.setText("");
      this.myEndidField.setText("");
      this.myPrefixField.setText("");
   }
}
