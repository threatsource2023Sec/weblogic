package weblogic.i18ntools.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import weblogic.i18n.Localizer;
import weblogic.i18n.tools.Action;
import weblogic.i18n.tools.BasicMessage;
import weblogic.i18n.tools.BasicMessageCatalog;
import weblogic.i18n.tools.Cause;
import weblogic.i18n.tools.Config;
import weblogic.i18n.tools.DuplicateElementException;
import weblogic.i18n.tools.LogMessage;
import weblogic.i18n.tools.Message;
import weblogic.i18n.tools.MessageBody;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.MessageCatalogParser;
import weblogic.i18n.tools.MessageCatalogWriter;
import weblogic.i18n.tools.MessageDetail;
import weblogic.i18n.tools.MessageNotFoundException;
import weblogic.i18n.tools.MessageWithMethod;
import weblogic.i18n.tools.WrongTypeException;
import weblogic.i18ntools.internal.I18nConfig;

public final class MessageEditor extends MessageCatalogEditor implements ActionListener, WindowListener {
   private static final String NO_MESSAGE_ID = "";
   private static final String[] myDefArgs = new String[0];
   static MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   private JFileChooser myChooser = null;
   private MessageCatalogDialog myNewCatalogDialog = null;
   private MessageSearchDialog myLogSearchDialog = null;
   private MessageSearchDialog mySimpleSearchDialog = null;
   private GUIClientTextField mySourceTreeRootField = null;
   private MessageCatalog myCatalog = null;
   private MessageCatalogParser myParser;
   private boolean myInChangeMode = false;
   private String mySavedId;
   private String mySavedMethod;
   private JPanel myCatalogInfoPanel;
   private JPanel myLogMsgEditingPanel;
   private JPanel mySimpleMsgEditingPanel;
   private GridBagConstraints myCatalogLayoutConstraints;
   private GridBagConstraints myContPaneConstraints;
   private JButton myLogAddChangeButton;
   private JButton myLogClearButton;
   private JButton mySimpleAddChangeButton;
   private JButton mySimpleClearButton;
   private Date myMessageDate;
   private boolean myDoingLogMessages;
   private boolean myDoingSimpleMessages;
   private Vector myViewers;
   private JButton myFindIdButton;
   private InputArgs myInpArgs = null;
   private String myInitFileName;
   private JLabel myI18nPackageLabel;
   private JLabel myL10nPackageLabel;
   private JLabel mySubsystemLabel;
   private JLabel myVersionLabel;
   private JLabel myBaseidLabel;
   private JLabel myEndidLabel;
   private JLabel myLoggableLabel;
   private JLabel myPrefixLabel;
   private JLabel myLogLastUpdated;
   private GUIClientTextField myLogMessageId;
   private GUIClientTextField myLogComment;
   private GUIClientTextField myLogMethod;
   private JComboBox mySeverity;
   private JComboBox myMethodType;
   private JTextArea myLogMessageBodyText;
   private JTextArea myMessageDetailText;
   private JTextArea myMessageCauseText;
   private JTextArea myMessageActionText;
   private JCheckBox myIsStackTrace;
   private JCheckBox myIsRetired;
   private JLabel mySimpleLastUpdated;
   private GUIClientTextField mySimpleMessageId;
   private GUIClientTextField mySimpleComment;
   private GUIClientTextField mySimpleMethod;
   private JTextArea mySimpleMessageBodyText;
   private I18nConfig config = new I18nConfig();

   public boolean isAllowingServerIds() {
      return this.myInpArgs.isAllowingServerIds();
   }

   public I18nConfig getConfig() {
      return this.config;
   }

   public static void main(String[] args) {
      try {
         MessageEditor editor = new MessageEditor(args);
         editor.runEditor();
      } catch (Exception var2) {
         var2.printStackTrace();
         System.exit(1);
      }

   }

   private void usage() {
      System.out.println(fmt.usage());
   }

   private MessageEditor(String[] args) throws Exception {
      this.myInpArgs = InputArgs.parse(args, myDefArgs);
      boolean server = this.myInpArgs.isAllowingServerIds();
      boolean verbose = this.myInpArgs.isVerbose();
      this.config.setServer(server);
      this.config.setVerbose(verbose);
      if (verbose) {
         this.myInpArgs.dump();
      }

   }

   private void runEditor() throws Exception {
      if (this.myInpArgs.getHelp()) {
         this.usage();
      } else if (this.myInpArgs.getUnresolvedArgs() == null) {
         String initFileName = this.myInpArgs.getFileName();
         this.myInitFileName = "";
         File initFile = null;
         if (initFileName != null) {
            initFile = new File(this.myInpArgs.getFileName());
            if (initFile.exists()) {
               if (initFile.isFile()) {
                  this.myInitFileName = initFile.getName();
                  this.setCatalogDirectory(initFile.getParent());
               } else {
                  this.setCatalogDirectory(initFile.toString());
               }
            } else {
               if (!initFileName.endsWith(".xml")) {
                  JOptionPane.showMessageDialog(this, fmt.noDir(initFileName), fmt.tagError(), 0);
                  return;
               }

               if (this.config.isVerbose()) {
                  System.err.println("Starting in parent of new file: " + initFile.getParent());
               }

               File initFileParent = initFile.getParentFile();
               if (initFileParent == null) {
                  this.myInitFileName = initFileName;
                  this.setCatalogDirectory(".");
               } else {
                  if (!initFileParent.exists()) {
                     JOptionPane.showMessageDialog(this, fmt.noDir(initFileParent.toString()), fmt.tagError(), 0);
                     return;
                  }

                  this.myInitFileName = initFile.getName();
                  this.setCatalogDirectory(initFileParent.getAbsolutePath());
               }
            }
         }

         this.createDisplayableScreen();
         this.addWindowListener(this);
         this.myViewers = new Vector(5);
         this.setVisible(true);
         if (initFile != null) {
            if (initFile.exists()) {
               if (initFile.isFile()) {
                  this.validateCatalogFileName(initFile.toString());
               }
            } else if (initFileName.endsWith(".xml")) {
               if (this.config.isVerbose()) {
                  System.err.println("showing create dialog with " + initFile.getAbsolutePath());
               }

               this.showNewCatalogCreate(initFile.getAbsolutePath());
            }
         }

      } else {
         String[] badArgs = this.myInpArgs.getUnresolvedArgs();
         StringBuilder paramTemplate = new StringBuilder();

         for(int i = 0; i < badArgs.length; ++i) {
            paramTemplate.append("\n ").append(badArgs[i]);
         }

         System.err.println(fmt.badArgs(paramTemplate.toString()));
      }
   }

   public void createDisplayableScreen() {
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.createDisplayableScreen");
      }

      this.setTitle(fmt.titleEditor());
      Image icon = this.loadImage("/weblogic/i18ntools/gui/images/W.gif");
      this.setIconImage(icon);
      Container contPane = this.getContentPane();
      contPane.setLayout(new GridBagLayout());
      this.myContPaneConstraints = new GridBagConstraints();
      MessageEditorMenuBar menuBar = new MessageEditorMenuBar(this);
      this.myContPaneConstraints.anchor = 11;
      this.myContPaneConstraints.fill = 2;
      this.myContPaneConstraints.weightx = 1.0;
      this.myContPaneConstraints.weighty = 0.0;
      this.myContPaneConstraints.gridwidth = 0;
      contPane.add(menuBar, this.myContPaneConstraints);
      this.myContPaneConstraints.weighty = 1.0;
      this.myContPaneConstraints.anchor = 10;
      JPanel bg = new JPanel();
      bg.setLayout(new GridBagLayout());
      GridBagConstraints bgGBC = new GridBagConstraints();
      bgGBC.anchor = 10;
      bgGBC.weightx = 1.0;
      bgGBC.gridwidth = 0;
      JLabel editorTitle = new JLabel(fmt.labelEditor());
      Font fnt = editorTitle.getFont();
      fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
      editorTitle.setFont(fnt);
      this.darken(editorTitle);
      bgGBC.insets = new Insets(1, 10, 1, 10);
      bg.add(editorTitle, bgGBC);
      JLabel serverOrNo;
      if (this.myInpArgs.isAllowingServerIds()) {
         serverOrNo = new JLabel(fmt.labelServerCats());
      } else {
         serverOrNo = new JLabel(fmt.labelNonServerCats());
      }

      this.darken(serverOrNo);
      bg.add(serverOrNo, bgGBC);
      GridBagConstraints topPanelLayoutContstraints = new GridBagConstraints();
      topPanelLayoutContstraints.fill = 2;
      topPanelLayoutContstraints.insets = new Insets(1, 10, 1, 10);
      JPanel topPanel = new JPanel();
      topPanel.setLayout(new GridBagLayout());
      topPanelLayoutContstraints.gridwidth = 1;
      topPanelLayoutContstraints.gridx = topPanelLayoutContstraints.gridy = 0;
      JLabel sourceTreeRootLabel = new JLabel(fmt.labelMsgCat());
      topPanel.add(sourceTreeRootLabel, topPanelLayoutContstraints);
      ++topPanelLayoutContstraints.gridx;
      this.mySourceTreeRootField = new GUIClientTextField(this.myInitFileName, 20);
      this.mySourceTreeRootField.setActionCommand("ValidateCatalogFile");
      this.mySourceTreeRootField.addActionListener(this);
      topPanel.add(this.mySourceTreeRootField, topPanelLayoutContstraints);
      JButton fileButton = new JButton(fmt.buttonBrowse());
      fileButton.addActionListener(this);
      fileButton.setActionCommand("Browse");
      ++topPanelLayoutContstraints.gridx;
      topPanel.add(fileButton, topPanelLayoutContstraints);
      bg.add(topPanel, bgGBC);
      this.myCatalogInfoPanel = new JPanel();
      this.myCatalogInfoPanel.setLayout(new GridBagLayout());
      this.myCatalogInfoPanel.setBorder(BorderFactory.createLoweredBevelBorder());
      this.myCatalogLayoutConstraints = new GridBagConstraints();
      this.myCatalogLayoutConstraints.fill = 2;
      this.myCatalogLayoutConstraints.insets = new Insets(1, 10, 1, 10);
      this.myCatalogLayoutConstraints.gridx = this.myCatalogLayoutConstraints.gridy = 0;
      this.myI18nPackageLabel = this.addCatalogAttribute(new JLabel(fmt.labelI18nPkg()), true);
      this.myL10nPackageLabel = this.addCatalogAttribute(new JLabel(fmt.labelL10nPkg()), false);
      this.mySubsystemLabel = this.addCatalogAttribute(new JLabel(fmt.labelSubsystem()), true);
      this.myVersionLabel = this.addCatalogAttribute(new JLabel(fmt.labelVersion()), false);
      this.myBaseidLabel = this.addCatalogAttribute(new JLabel(fmt.labelBaseId()), true);
      this.myEndidLabel = this.addCatalogAttribute(new JLabel(fmt.labelEndId()), false);
      this.myLoggableLabel = this.addCatalogAttribute(new JLabel(fmt.labelLoggables()), true);
      this.myPrefixLabel = this.addCatalogAttribute(new JLabel(fmt.labelPrefix()), false);
      bg.add(this.myCatalogInfoPanel, bgGBC);
      contPane.add(bg, this.myContPaneConstraints);
      this.myLogMsgEditingPanel = this.makeLogMessagePanel();
      this.mySimpleMsgEditingPanel = this.makeSimpleMessagePanel();
      contPane.add(this.myLogMsgEditingPanel, this.myContPaneConstraints);
      contPane.add(this.mySimpleMsgEditingPanel, this.myContPaneConstraints);
      this.setPanelsForCatalog();
      this.pack();
   }

   private JPanel makeLogMessagePanel() {
      JPanel logMsgEditingPanel = new JPanel();
      logMsgEditingPanel.setLayout(new BorderLayout());
      logMsgEditingPanel.setBorder(new BevelBorder(1));
      JPanel logMessagePanel = this.makeLogMessageEditingFields();
      JPanel actionPanel = new JPanel();
      this.myLogAddChangeButton = new JButton(fmt.buttonAdd());
      this.myLogAddChangeButton.setActionCommand("AddLog");
      this.myLogAddChangeButton.addActionListener(this);
      actionPanel.add(this.myLogAddChangeButton);
      this.setAddChangeMode("Add");
      this.myLogClearButton = new JButton(fmt.buttonClear());
      this.myLogClearButton.addActionListener(this);
      this.myLogClearButton.setActionCommand("ClearLog");
      actionPanel.add(this.myLogClearButton);
      if (this.config.isVerbose()) {
         actionPanel.setBackground(Color.yellow);
      }

      logMsgEditingPanel.add(logMessagePanel, "Center");
      logMsgEditingPanel.add(actionPanel, "South");
      return logMsgEditingPanel;
   }

   private JPanel makeSimpleMessagePanel() {
      JPanel simpleMsgEditingPanel = new JPanel();
      simpleMsgEditingPanel.setLayout(new BorderLayout());
      simpleMsgEditingPanel.setBorder(new BevelBorder(1));
      JPanel simpleMessagePanel = this.makeSimpleMessageEditingFields();
      JPanel actionPanel = new JPanel();
      this.mySimpleAddChangeButton = new JButton(fmt.buttonAdd());
      this.mySimpleAddChangeButton.setActionCommand("AddSimple");
      this.mySimpleAddChangeButton.addActionListener(this);
      actionPanel.add(this.mySimpleAddChangeButton);
      this.setAddChangeMode("Add");
      this.mySimpleClearButton = new JButton(fmt.buttonClear());
      this.mySimpleClearButton.addActionListener(this);
      this.mySimpleClearButton.setActionCommand("ClearSimple");
      actionPanel.add(this.mySimpleClearButton);
      if (this.config.isVerbose()) {
         actionPanel.setBackground(Color.yellow);
      }

      simpleMsgEditingPanel.add(simpleMessagePanel, "North");
      simpleMsgEditingPanel.add(actionPanel, "Center");
      return simpleMsgEditingPanel;
   }

   private JPanel makeLogMessageEditingFields() {
      JPanel logMessagePanel = new JPanel();
      logMessagePanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 1.0;
      gbc.anchor = 10;
      gbc.gridwidth = 0;
      gbc.insets = new Insets(3, 10, 3, 10);
      gbc.gridx = gbc.gridy = 0;
      JLabel logMessageTitle = new JLabel(fmt.logMessageTitle());
      Font fnt = logMessageTitle.getFont();
      fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
      logMessageTitle.setFont(fnt);
      this.darken(logMessageTitle);
      logMessagePanel.add(logMessageTitle, gbc);
      ++gbc.gridy;
      gbc.gridwidth = 1;
      gbc.fill = 2;
      JLabel lastUpdatedLabel = new JLabel(fmt.labelLastUpdated());
      lastUpdatedLabel.setToolTipText(fmt.tipLastUpdated());
      logMessagePanel.add(lastUpdatedLabel, gbc);
      ++gbc.gridx;
      this.myLogLastUpdated = new JLabel((new Date()).toString());
      this.darken(this.myLogLastUpdated);
      logMessagePanel.add(this.myLogLastUpdated, gbc);
      gbc.gridx = 0;
      ++gbc.gridy;
      JLabel messageIdLabel = new JLabel(fmt.labelMessageId());
      logMessagePanel.add(messageIdLabel, gbc);
      ++gbc.gridx;
      JPanel lookGood = new JPanel(new GridBagLayout());
      GridBagConstraints lgConst = new GridBagConstraints();
      lgConst.anchor = 17;
      lgConst.weightx = 1.0;
      lgConst.insets = new Insets(0, 0, 0, 0);
      lgConst.gridx = 0;
      lgConst.gridy = 0;
      this.myLogMessageId = new GUIClientTextField("", 20) {
         public void focusLost(FocusEvent ev) {
            if (MessageEditor.this.config.isVerbose()) {
               System.err.println("focusLost, ev = " + ev.toString());
            }

            this.setSelectionStart(0);
            this.setSelectionEnd(0);
            if (MessageEditor.this.myCatalog != null && !ev.isTemporary()) {
               this.postActionEvent();
            }

         }
      };
      this.myLogMessageId.setActionCommand("CheckLogId");
      this.myLogMessageId.addActionListener(this);
      this.darken(this.myLogMessageId);
      lookGood.add(this.myLogMessageId, lgConst);
      this.myFindIdButton = new JButton(fmt.buttonNextId());
      this.myFindIdButton.addActionListener(this);
      this.myFindIdButton.setActionCommand("GetNextID");
      lgConst.anchor = 13;
      ++lgConst.gridx;
      lookGood.add(this.myFindIdButton, lgConst);
      logMessagePanel.add(lookGood, gbc);
      if (this.config.isVerbose()) {
         lookGood.setBackground(Color.pink);
      }

      JLabel commentLabel = new JLabel(fmt.labelComment());
      commentLabel.setToolTipText("Comment to reside in xml catalog about this message");
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(commentLabel, gbc);
      ++gbc.gridx;
      this.myLogComment = new GUIClientTextField("", 20);
      logMessagePanel.add(this.myLogComment, gbc);
      JLabel messageMethodLabel = new JLabel(fmt.labelMethod());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(messageMethodLabel, gbc);
      ++gbc.gridx;
      this.myLogMethod = new GUIClientTextField("", 20) {
         public void focusLost(FocusEvent ev) {
            if (MessageEditor.this.config.isVerbose()) {
               System.err.println("focusLost, ev = " + ev.toString());
            }

            this.setSelectionStart(0);
            this.setSelectionEnd(0);
            if (MessageEditor.this.myCatalog != null && !ev.isTemporary()) {
               this.postActionEvent();
            }

         }
      };
      this.myLogMethod.setActionCommand("CheckLogMethodName");
      this.myLogMethod.addActionListener(this);
      logMessagePanel.add(this.myLogMethod, gbc);
      JLabel methodTypeLabel = new JLabel(fmt.labelMethodType());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(methodTypeLabel, gbc);
      ++gbc.gridx;
      this.myMethodType = new JComboBox(Config.METHOD_TYPES);
      logMessagePanel.add(this.myMethodType, gbc);
      JLabel severityLabel = new JLabel(fmt.labelSeverity());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(severityLabel, gbc);
      ++gbc.gridx;
      if (this.myInpArgs.isAllowingServerIds()) {
         this.mySeverity = new JComboBox(Localizer.SERVER_SEVERITIES);
      } else {
         this.mySeverity = new JComboBox(Localizer.NON_SERVER_SEVERITIES);
      }

      logMessagePanel.add(this.mySeverity, gbc);
      JLabel messageBodyLabel = new JLabel(fmt.labelBody());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(messageBodyLabel, gbc);
      ++gbc.gridx;
      this.myLogMessageBodyText = new JTextArea("", 3, 30);
      JScrollPane messageBody = new JScrollPane(this.myLogMessageBodyText);
      logMessagePanel.add(messageBody, gbc);
      JLabel messageDetailLabel = new JLabel(fmt.labelDetail());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(messageDetailLabel, gbc);
      ++gbc.gridx;
      this.myMessageDetailText = new JTextArea("", 3, 30);
      JScrollPane messageDetail = new JScrollPane(this.myMessageDetailText);
      logMessagePanel.add(messageDetail, gbc);
      JLabel messageCauseLabel = new JLabel(fmt.labelCause());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(messageCauseLabel, gbc);
      ++gbc.gridx;
      this.myMessageCauseText = new JTextArea("", 3, 30);
      JScrollPane messageCause = new JScrollPane(this.myMessageCauseText);
      logMessagePanel.add(messageCause, gbc);
      JLabel messageActionLabel = new JLabel(fmt.labelAction());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(messageActionLabel, gbc);
      ++gbc.gridx;
      this.myMessageActionText = new JTextArea("", 3, 30);
      JScrollPane messageAction = new JScrollPane(this.myMessageActionText);
      logMessagePanel.add(messageAction, gbc);
      JLabel stacktraceLabel = new JLabel(fmt.labelStack());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(stacktraceLabel, gbc);
      ++gbc.gridx;
      this.myIsStackTrace = new JCheckBox();
      this.myIsStackTrace.setSelected((new LogMessage(this.config)).getStackTrace());
      logMessagePanel.add(this.myIsStackTrace, gbc);
      JLabel retiredLabel = new JLabel(fmt.labelRetired());
      gbc.gridx = 0;
      ++gbc.gridy;
      logMessagePanel.add(retiredLabel, gbc);
      ++gbc.gridx;
      this.myIsRetired = new JCheckBox();
      this.myIsRetired.setSelected((new LogMessage(this.config)).isRetired());
      logMessagePanel.add(this.myIsRetired, gbc);
      return logMessagePanel;
   }

   private JPanel makeSimpleMessageEditingFields() {
      JPanel simpleMessagePanel = new JPanel();
      simpleMessagePanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 1.0;
      gbc.anchor = 10;
      gbc.gridwidth = 0;
      gbc.insets = new Insets(3, 10, 3, 10);
      gbc.gridx = gbc.gridy = 0;
      JLabel simpleMessageTitle = new JLabel(fmt.simpleMessageTitle());
      Font fnt = simpleMessageTitle.getFont();
      fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
      simpleMessageTitle.setFont(fnt);
      this.darken(simpleMessageTitle);
      simpleMessagePanel.add(simpleMessageTitle, gbc);
      ++gbc.gridy;
      gbc.gridwidth = 1;
      gbc.fill = 2;
      JLabel lastUpdatedLabel = new JLabel(fmt.labelLastUpdated());
      lastUpdatedLabel.setToolTipText(fmt.tipLastUpdated());
      simpleMessagePanel.add(lastUpdatedLabel, gbc);
      ++gbc.gridx;
      this.mySimpleLastUpdated = new JLabel((new Date()).toString());
      this.darken(this.mySimpleLastUpdated);
      simpleMessagePanel.add(this.mySimpleLastUpdated, gbc);
      gbc.gridx = 0;
      ++gbc.gridy;
      JLabel messageIdLabel = new JLabel(fmt.labelMessageId());
      simpleMessagePanel.add(messageIdLabel, gbc);
      ++gbc.gridx;
      this.mySimpleMessageId = new GUIClientTextField("", 20) {
         public void focusLost(FocusEvent ev) {
            if (MessageEditor.this.config.isVerbose()) {
               System.err.println("focusLost, ev = " + ev.toString());
            }

            this.setSelectionStart(0);
            this.setSelectionEnd(0);
            if (MessageEditor.this.myCatalog != null && !ev.isTemporary()) {
               this.postActionEvent();
            }

         }
      };
      this.mySimpleMessageId.setActionCommand("CheckSimpleId");
      this.mySimpleMessageId.addActionListener(this);
      simpleMessagePanel.add(this.mySimpleMessageId, gbc);
      JLabel commentLabel = new JLabel(fmt.labelComment());
      commentLabel.setToolTipText(fmt.tipComment());
      gbc.gridx = 0;
      ++gbc.gridy;
      simpleMessagePanel.add(commentLabel, gbc);
      ++gbc.gridx;
      this.mySimpleComment = new GUIClientTextField("", 20);
      simpleMessagePanel.add(this.mySimpleComment, gbc);
      JLabel messageMethodLabel = new JLabel(fmt.labelMethod());
      gbc.gridx = 0;
      ++gbc.gridy;
      simpleMessagePanel.add(messageMethodLabel, gbc);
      ++gbc.gridx;
      this.mySimpleMethod = new GUIClientTextField("", 20) {
         public void focusLost(FocusEvent ev) {
            if (MessageEditor.this.config.isVerbose()) {
               System.err.println("focusLost, ev = " + ev.toString());
            }

            this.setSelectionStart(0);
            this.setSelectionEnd(0);
            if (MessageEditor.this.myCatalog != null && !ev.isTemporary()) {
               this.postActionEvent();
            }

         }
      };
      this.mySimpleMethod.setActionCommand("CheckSimpleMethodName");
      this.mySimpleMethod.addActionListener(this);
      simpleMessagePanel.add(this.mySimpleMethod, gbc);
      JLabel messageBodyLabel = new JLabel(fmt.labelBody());
      gbc.gridx = 0;
      ++gbc.gridy;
      simpleMessagePanel.add(messageBodyLabel, gbc);
      ++gbc.gridx;
      this.mySimpleMessageBodyText = new JTextArea("", 3, 30);
      JScrollPane messageBody = new JScrollPane(this.mySimpleMessageBodyText);
      simpleMessagePanel.add(messageBody, gbc);
      return simpleMessagePanel;
   }

   private JLabel addCatalogAttribute(JLabel label, boolean newRow) {
      if (newRow) {
         this.myCatalogLayoutConstraints.gridx = 0;
         ++this.myCatalogLayoutConstraints.gridy;
      } else {
         ++this.myCatalogLayoutConstraints.gridx;
      }

      this.myCatalogInfoPanel.add(label, this.myCatalogLayoutConstraints);
      JLabel attrLabel = new JLabel(fmt.labelNA());
      this.darken(attrLabel);
      ++this.myCatalogLayoutConstraints.gridx;
      this.myCatalogInfoPanel.add(attrLabel, this.myCatalogLayoutConstraints);
      return attrLabel;
   }

   public void actionPerformed(ActionEvent ev) {
      String cmd = ev.getActionCommand();
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.actionPerformed, cmd=" + cmd);
      }

      if (cmd.equals("Browse")) {
         if (this.myChooser == null) {
            this.myChooser = new JFileChooser(new File(this.getCatalogDirectory()));
            XmlFileFilter xmlFilter = new XmlFileFilter();
            this.myChooser.setFileFilter(xmlFilter);
         }

         this.myChooser.setFileSelectionMode(0);
         int retval = this.myChooser.showDialog(this, (String)null);
         if (retval == 0) {
            File theFile = this.myChooser.getSelectedFile();
            if (theFile != null) {
               Cursor crsr = this.getCursor();
               this.setCursor(3);
               File fileChosen = this.myChooser.getSelectedFile();
               String fileName = fileChosen.getAbsolutePath();
               this.setCatalogDirectory(fileChosen.getParent());
               this.mySourceTreeRootField.setText(fileName);
               this.mySourceTreeRootField.postActionEvent();
               this.mySourceTreeRootField.repaint();
               this.setCursor(crsr);
               return;
            }
         }
      } else if (!cmd.equals("Help")) {
         if (cmd.equals("ValidateCatalogFile")) {
            this.validateCatalogFileName(this.mySourceTreeRootField.getText());
         } else if (cmd.equals("NewCatalog")) {
            this.showNewCatalogCreate();
         } else if (cmd.equals("GetNextID")) {
            this.myLogMessageId.setText(this.getNextMessageId());
         } else if (cmd.equals("WriteCatalog")) {
            this.writeCatalogToDisk();
         } else if (cmd.equals("AddLog")) {
            this.addOrChangeLogMessage();
         } else if (cmd.equals("AddSimple")) {
            this.addOrChangeSimpleMessage();
         } else if (cmd.equals("CheckLogMethodName")) {
            this.checkMethodName(this.myLogMethod.getText());
         } else if (cmd.equals("CheckSimpleMethodName")) {
            if (!this.mySimpleMethod.getText().equals("")) {
               this.checkMethodName(this.mySimpleMethod.getText());
            }
         } else if (cmd.equals("CheckLogId")) {
            this.checkLogId(this.myLogMessageId.getText());
         } else if (cmd.equals("CheckSimpleId")) {
            this.checkSimpleId(this.mySimpleMessageId.getText());
         } else if (cmd.equals("ClearLog")) {
            this.clearLogMessageFields();
         } else if (cmd.equals("ClearSimple")) {
            this.clearSimpleMessageFields();
         } else if (cmd.equals("View")) {
            this.showMessageViewer();
         } else if (cmd.equals("VariableWidthFont")) {
            this.setFonts("Variable");
         } else if (cmd.equals("FixedWidthFont")) {
            this.setFonts("Fixed");
         } else if (cmd.equals("Search")) {
            if (this.myCatalog != null) {
               this.showSearchDialog();
            } else {
               JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
            }
         } else if (cmd.equals("Exit")) {
            this.windowClosing((WindowEvent)null);
         }
      }

   }

   private void setFonts(String type) {
      if (this.config.isVerbose()) {
         System.err.println("setFonts, type = " + type);
      }

      Font fnt = this.mySourceTreeRootField.getFont();
      int style = fnt.getStyle();
      int size = fnt.getSize();
      if (type.equals("Variable")) {
         type = "Dialog";
      } else {
         type = "Monospaced";
      }

      Font newFont = new Font(type, style, size);
      this.myLogLastUpdated.setFont(newFont);
      this.myLogMessageId.setFont(newFont);
      this.myLogMethod.setFont(newFont);
      this.mySeverity.setFont(newFont);
      this.myMethodType.setFont(newFont);
      this.myLogComment.setFont(newFont);
      this.myLogMessageBodyText.setFont(newFont);
      this.myMessageDetailText.setFont(newFont);
      this.myMessageCauseText.setFont(newFont);
      this.myMessageActionText.setFont(newFont);
      this.mySimpleLastUpdated.setFont(newFont);
      this.mySimpleMessageId.setFont(newFont);
      this.mySimpleComment.setFont(newFont);
      this.mySimpleMethod.setFont(newFont);
      this.mySimpleMessageBodyText.setFont(newFont);
      this.pack();
      this.repaint();
   }

   private void showMessageViewer() {
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.showMessageViewer");
      }

      if (this.myCatalog == null) {
         JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
      } else {
         MessageViewer msgVwr = this.findMessageViewer(this.myCatalog.getPath());
         if (msgVwr != null) {
            msgVwr.setVisible(false);
            this.myViewers.removeElement(msgVwr);
         }

         MessageViewer newMsgVwr = new MessageViewer(this, this.myCatalog, this.myCatalog.getPath(), "");
         this.myViewers.addElement(newMsgVwr);
      }

   }

   private MessageViewer findMessageViewer(String path) {
      MessageViewer retMsgVwr = null;
      MessageViewer msgVwr = null;
      int i;
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.findMessageViewer, path looking for is = " + path);
         System.err.println("  Viewers we know about are:");

         for(i = 0; i < this.myViewers.size(); ++i) {
            System.err.println("     viewer path = " + ((MessageViewer)this.myViewers.elementAt(i)).getFilePath());
         }
      }

      for(i = 0; i < this.myViewers.size(); ++i) {
         msgVwr = (MessageViewer)this.myViewers.elementAt(i);
         if (msgVwr.getFilePath().equals(path)) {
            retMsgVwr = msgVwr;
            break;
         }
      }

      if (this.config.isVerbose()) {
         if (retMsgVwr == null) {
            System.err.println("  none found");
         } else {
            System.err.println("  Found this one: " + retMsgVwr.getFilePath());
         }
      }

      return retMsgVwr;
   }

   private void showSearchDialog() {
      this.removeSearchDialog();
      if (this.myCatalog.getCatType() == 2) {
         this.myLogSearchDialog = new MessageSearchDialog(this, fmt.msgSearchLog(), this.myCatalog);
      } else if (this.myCatalog.getCatType() == 1) {
         this.mySimpleSearchDialog = new MessageSearchDialog(this, fmt.msgSearchSimple(), this.myCatalog);
      }

   }

   private void removeSearchDialog() {
      if (this.myLogSearchDialog != null) {
         this.myLogSearchDialog.setVisible(false);
      }

      if (this.mySimpleSearchDialog != null) {
         this.mySimpleSearchDialog.setVisible(false);
      }

   }

   private boolean validateCatalogFileName(String fileName) {
      if (this.config.isVerbose()) {
         System.err.println("parsing catalog: " + fileName);
      }

      this.clearCatalogInfo();
      this.setupParser();

      try {
         this.myCatalog = this.myParser.parse(fileName);
         this.setCatalog(this.myCatalog, fileName);
      } catch (Exception var3) {
         JOptionPane.showMessageDialog(this, fmt.msgBadParse(fileName, var3.toString()), fmt.tagError(), 0);
         return false;
      }

      this.removeSearchDialog();
      return true;
   }

   public void setupParser() {
      Config cfg = new Config();
      boolean server = this.myInpArgs.isAllowingServerIds();
      cfg.setServer(server);
      this.myParser = new MessageCatalogParser(cfg, false, true);
   }

   private void setCatalogNames(String pathAndFile) {
      File fil = new File(pathAndFile);
      String catalogFileName = fil.getName();
      this.myCatalog.setPath(fil.getAbsolutePath());
      if (this.config.isVerbose()) {
         System.out.println("setCatalogNames, Path = " + fil.getAbsolutePath() + "   catalogFileName =" + catalogFileName);
      }

      this.myCatalog.set("filename", catalogFileName);
   }

   private void showNewCatalogCreate() {
      this.showNewCatalogCreate("");
   }

   private void showNewCatalogCreate(String fileName) {
      if (this.myNewCatalogDialog == null) {
         this.myNewCatalogDialog = new MessageCatalogDialog(this, fmt.msgCreateCat(), fileName);
      } else {
         this.myNewCatalogDialog.setVisible(true);
      }

   }

   private boolean checkLogId(String id) {
      if (!this.checkNotBlank(id, "Message id")) {
         return false;
      } else if (!this.myInChangeMode) {
         return this.checkItemUnique(id, "Id");
      } else {
         return this.myInChangeMode && !id.equals(this.mySavedId) ? this.checkItemUnique(id, "Id", this.mySavedId) : true;
      }
   }

   private boolean checkNotBlank(String item, String name) {
      if (item.equals("")) {
         JOptionPane.showMessageDialog(this, fmt.msgBlankItem(name), fmt.tagError(), 0);
         return false;
      } else {
         return true;
      }
   }

   private boolean checkMethodName(String method) {
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.checkMethodName   method=" + method);
      }

      boolean success = true;
      if (this.myCatalog == null) {
         JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
      } else if (!this.checkNotBlank(method, "Method")) {
         success = false;
      } else if (!this.myInChangeMode || this.myInChangeMode && !method.equals(this.mySavedMethod)) {
         Object tempMsg;
         if (this.myCatalog.getCatType() == 2) {
            tempMsg = new LogMessage(this.config);
         } else if (this.myCatalog.getCatType() == 1) {
            tempMsg = new Message(this.config);
         } else {
            tempMsg = null;
         }

         if (tempMsg != null) {
            try {
               ((MessageWithMethod)tempMsg).setMethod(method);
            } catch (NoSuchElementException var5) {
               success = false;
               JOptionPane.showMessageDialog(this, fmt.msgBadMethod() + "filesInDir(String dirName, int numFiles)", fmt.tagError(), 0);
            }

            if (success) {
               String methodName = ((MessageWithMethod)tempMsg).getMethodName();
               success = this.checkForJavaIdentifier(methodName, "method name");
               if (success) {
                  if (!this.myInChangeMode) {
                     success = this.checkItemUnique(methodName, "Method name");
                  } else if (this.myInChangeMode && !method.equals(this.mySavedMethod)) {
                     success = this.checkItemUnique(methodName, "Method name", this.mySavedId);
                  }
               }
            }
         }
      }

      return success;
   }

   private boolean checkSimpleId(String id) {
      boolean success = true;
      if (!this.checkNotBlank(id, "Message id")) {
         success = false;
      } else if (!this.myInChangeMode || this.myInChangeMode && !id.equals(this.mySavedId)) {
         success = this.checkForJavaIdentifier(id, "id");
         if (success) {
            if (!this.myInChangeMode) {
               success = this.checkItemUnique(id, "Id");
            } else if (this.myInChangeMode && !id.equals(this.mySavedId)) {
               success = this.checkItemUnique(id, "Id", this.mySavedId);
            }
         }
      }

      return success;
   }

   private boolean checkItemUnique(String item, String type, String savedId) {
      boolean success = true;
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.checkItemUnique item-" + item + "   type=" + type + "   savedId=" + savedId);
      }

      if (this.myCatalog == null) {
         JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
      } else {
         Vector msgs;
         if (this.myCatalog.getCatType() == 2) {
            msgs = this.myCatalog.getLogMessages();
         } else if (this.myCatalog.getCatType() == 1) {
            msgs = this.myCatalog.getMessages();
         } else {
            msgs = null;
         }

         if (msgs != null) {
            Enumeration e = msgs.elements();

            while(e.hasMoreElements()) {
               MessageWithMethod msg = (MessageWithMethod)e.nextElement();
               String theItem = null;
               if (type.equals("Method name")) {
                  theItem = msg.getMethodName();
               } else if (type.equals("Id")) {
                  theItem = ((BasicMessage)msg).getMessageId();
               }

               if (theItem != null) {
                  if (this.myInChangeMode) {
                     if (savedId != null && !((BasicMessage)msg).getMessageId().equals(savedId) && theItem.equals(item)) {
                        success = false;
                        break;
                     }
                  } else if (theItem.equals(item)) {
                     success = false;
                     break;
                  }
               }
            }
         }

         if (!success) {
            JOptionPane.showMessageDialog(this, fmt.msgUniqueType(type), fmt.tagError(), 0);
         }
      }

      return success;
   }

   private boolean checkItemUnique(String item, String type) {
      return this.checkItemUnique(item, type, (String)null);
   }

   private boolean checkForJavaIdentifier(String str, String type) {
      boolean success = true;

      for(int i = 0; i < str.length(); ++i) {
         char chr = str.charAt(i);
         if (i == 0 && !Character.isJavaIdentifierStart(chr) || !Character.isJavaIdentifierPart(chr)) {
            JOptionPane.showMessageDialog(this, fmt.msgJavaId(type), fmt.tagError(), 0);
            success = false;
            break;
         }
      }

      return success;
   }

   private boolean addOrChangeLogMessage() {
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.addOrChangeLogMessage");
      }

      this.myDoingLogMessages = true;
      this.myDoingSimpleMessages = false;
      boolean success = true;
      boolean success2 = true;
      boolean result = false;
      if (this.myCatalog != null) {
         String addOrChange = this.getAddChangeString();
         String msgID = this.myLogMessageId.getText();
         success = this.checkLogId(msgID);
         success2 = this.checkMethodName(this.myLogMethod.getText());
         if (success && success2) {
            success = false;
            if (this.myLogMessageBodyText.getText() != null && !this.myLogMessageBodyText.getText().equals("")) {
               LogMessage logMsg = new LogMessage(this.config);
               if (!this.myInChangeMode && msgID.equals("")) {
                  msgID = this.getNextMessageId();
               }

               if (!msgID.equals("")) {
                  this.myMessageDate = new Date();
                  this.myLogLastUpdated.setText(this.myMessageDate.toString());
                  this.myLogMessageId.setText(msgID);
                  logMsg.set("messageid", msgID);
                  logMsg.set("method", this.myLogMethod.getText());
                  logMsg.set("methodtype", Config.METHOD_TYPES[this.myMethodType.getSelectedIndex()]);
                  logMsg.set("datelastchanged", Long.toString(this.myMessageDate.getTime()));
                  if (this.myInpArgs.isAllowingServerIds()) {
                     logMsg.set("severity", Localizer.SERVER_SEVERITIES[this.mySeverity.getSelectedIndex()]);
                  } else {
                     logMsg.set("severity", Localizer.NON_SERVER_SEVERITIES[this.mySeverity.getSelectedIndex()]);
                  }

                  logMsg.setComment(this.myLogComment.getText());
                  String temp = this.myLogMessageBodyText.getText();
                  temp = this.correctQuotes(temp);
                  logMsg.addMessageBody(new MessageBody(temp));
                  temp = this.myMessageDetailText.getText();
                  temp = this.correctQuotes(temp);
                  logMsg.addMessageDetail(new MessageDetail(temp));
                  temp = this.myMessageCauseText.getText();
                  temp = this.correctQuotes(temp);
                  logMsg.addCause(new Cause(temp));
                  temp = this.myMessageActionText.getText();
                  temp = this.correctQuotes(temp);
                  logMsg.addAction(new Action(temp));
                  logMsg.setStackTrace(this.myIsStackTrace.isSelected());
                  logMsg.setRetired(this.myIsRetired.isSelected());
                  logMsg.setParent(this.myCatalog);
                  String error = logMsg.validate();
                  if (error != null) {
                     JOptionPane.showMessageDialog(this, error, fmt.tagError(), 0);
                     result = false;
                     success = false;
                  } else {
                     MessageViewer msgViewer;
                     if (this.myInChangeMode) {
                        result = false;

                        try {
                           this.myCatalog.changeLogMessage(logMsg, this.mySavedId);
                           result = true;
                           msgViewer = this.findMessageViewer(this.myCatalog.getPath());
                           if (msgViewer != null) {
                              msgViewer.changeLogMessage(logMsg, this.mySavedId);
                           }
                        } catch (WrongTypeException var12) {
                           JOptionPane.showMessageDialog(this, fmt.msgWrongTypeX(), fmt.tagFatal(), 0);
                           return false;
                        } catch (MessageNotFoundException var13) {
                           JOptionPane.showMessageDialog(this, fmt.msgNoMsg(), fmt.tagError(), 0);
                        }
                     } else {
                        result = false;

                        try {
                           this.myCatalog.addLogMessage(logMsg);
                           result = true;
                           msgViewer = this.findMessageViewer(this.myCatalog.getPath());
                           if (msgViewer != null) {
                              msgViewer.addLogMessage(logMsg);
                           }
                        } catch (WrongTypeException var10) {
                           JOptionPane.showMessageDialog(this, fmt.msgNotLogCat(), fmt.tagError(), 0);
                        } catch (DuplicateElementException var11) {
                           JOptionPane.showMessageDialog(this, var11.toString(), fmt.msgDupId(), 0);
                        }
                     }
                  }

                  if (result) {
                     this.writeCatalogToDisk();
                     this.myLogClearButton.doClick();
                     this.setAddChangeMode("Add");
                     success = true;
                  } else {
                     JOptionPane.showMessageDialog(this, fmt.msgAddFailed(addOrChange), fmt.tagError(), 0);
                  }
               } else {
                  JOptionPane.showMessageDialog(this, fmt.msgAddFailedInternalError(addOrChange), fmt.tagError(), 0);
               }
            } else {
               JOptionPane.showMessageDialog(this, fmt.msgBlankBody(), fmt.tagError(), 0);
            }
         }
      } else {
         JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
      }

      return success;
   }

   private boolean addOrChangeSimpleMessage() {
      this.myDoingLogMessages = false;
      this.myDoingSimpleMessages = true;
      boolean success = true;
      boolean success2 = true;
      boolean result = false;
      if (this.myCatalog != null) {
         String addOrChange = this.getAddChangeString();
         String msgID = this.mySimpleMessageId.getText();
         success = this.checkSimpleId(msgID);
         if (!this.mySimpleMethod.getText().equals("")) {
            success2 = this.checkMethodName(this.mySimpleMethod.getText());
         }

         if (success && success2) {
            success = false;
            if (this.mySimpleMessageBodyText.getText() != null && !this.mySimpleMessageBodyText.getText().equals("")) {
               Message simpleMsg = new Message(this.config);
               if (msgID.equals("")) {
                  JOptionPane.showMessageDialog(this, fmt.msgEnterId(), fmt.tagError(), 0);
               } else {
                  this.myMessageDate = new Date();
                  this.mySimpleLastUpdated.setText(this.myMessageDate.toString());
                  simpleMsg.set("messageid", msgID);
                  simpleMsg.set("datelastchanged", Long.toString(this.myMessageDate.getTime()));
                  simpleMsg.set("method", this.mySimpleMethod.getText());
                  String temp = this.mySimpleMessageBodyText.getText();
                  temp = this.correctQuotes(temp);
                  simpleMsg.addMessageBody(new MessageBody(temp));
                  simpleMsg.setComment(this.mySimpleComment.getText());
                  simpleMsg.setParent(this.myCatalog);
                  String error = simpleMsg.validate();
                  if (error != null) {
                     JOptionPane.showMessageDialog(this, error, fmt.tagError(), 0);
                     result = false;
                     success = false;
                  } else {
                     MessageViewer msgVwr;
                     if (this.myInChangeMode) {
                        result = false;

                        try {
                           this.myCatalog.changeMessage(simpleMsg, this.mySavedId);
                           result = true;
                           msgVwr = this.findMessageViewer(this.myCatalog.getPath());
                           if (msgVwr != null) {
                              msgVwr.changeSimpleMessage(simpleMsg, this.mySavedId);
                           }
                        } catch (WrongTypeException var12) {
                           JOptionPane.showMessageDialog(this, fmt.msgWrongTypeX(), fmt.tagFatal(), 0);
                           return false;
                        } catch (MessageNotFoundException var13) {
                           JOptionPane.showMessageDialog(this, var13.toString(), fmt.msgNoMsg(), 0);
                        }
                     } else {
                        result = false;

                        try {
                           this.myCatalog.addMessage(simpleMsg);
                           result = true;
                           msgVwr = this.findMessageViewer(this.myCatalog.getPath());
                           if (msgVwr != null) {
                              msgVwr.addMessage(simpleMsg);
                           }
                        } catch (WrongTypeException var10) {
                           JOptionPane.showMessageDialog(this, fmt.msgNotSimpleCat(), fmt.tagError(), 0);
                        } catch (DuplicateElementException var11) {
                           JOptionPane.showMessageDialog(this, var11.toString(), fmt.msgDupId(), 0);
                        }
                     }
                  }

                  if (result) {
                     this.writeCatalogToDisk();
                     this.mySimpleClearButton.doClick();
                     this.setAddChangeMode("Add");
                     success = true;
                  } else {
                     JOptionPane.showMessageDialog(this, fmt.msgAddFailed(addOrChange), fmt.tagError(), 0);
                  }
               }
            } else {
               JOptionPane.showMessageDialog(this, fmt.msgBlankBody(), fmt.tagError(), 0);
            }
         }
      } else {
         JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
      }

      return success;
   }

   private String getNextMessageId() {
      String msgID = "";
      int id = -1;
      if (this.myCatalog == null) {
         JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
      } else {
         if (this.config.isVerbose()) {
            System.err.println("MessageEditor.getNextMessageId, myCatalog.getCatType = " + this.myCatalog.getCatType());
         }

         if (this.myCatalog.getCatType() == 0) {
            id = this.myCatalog.getBaseid();
         } else if (this.myCatalog.getCatType() != 2) {
            JOptionPane.showMessageDialog(this, fmt.msgInternalError("getNextMessageId should never be called for anything but a LOG Catalog"), "Fatal error", 0);
         } else {
            Vector logMsgs = this.myCatalog.getLogMessages();
            if (logMsgs.size() <= 0) {
               id = this.myCatalog.getBaseid();
            } else {
               Enumeration e = logMsgs.elements();

               label46:
               while(true) {
                  int logMsgId;
                  do {
                     if (!e.hasMoreElements()) {
                        break label46;
                     }

                     LogMessage lMsg = (LogMessage)e.nextElement();
                     logMsgId = Integer.parseInt(lMsg.getMessageId());
                     if (this.config.isVerbose()) {
                        System.err.println("MessageEditor.getNextMessageId, logMessage id = " + lMsg.getMessageId() + " as integer it is " + logMsgId + "  and id currently is " + id);
                     }
                  } while(id != -1 && id > logMsgId);

                  id = logMsgId + 1;
               }
            }
         }

         if (id == -1) {
            JOptionPane.showMessageDialog(this, fmt.msgNoUniqueId(), fmt.tagError(), 0);
         } else {
            msgID = Integer.toString(id);
         }
      }

      return msgID;
   }

   private void clearLogMessageFields() {
      this.myLogLastUpdated.setText((new Date()).toString());
      this.myLogMessageId.setText("");
      this.myLogMethod.setText("");
      this.myMethodType.setSelectedIndex(0);
      this.mySeverity.setSelectedIndex(0);
      this.myLogComment.setText("");
      this.myLogComment.setSize(this.myLogComment.getPreferredSize());
      this.myLogMessageBodyText.setText("");
      this.myLogMessageBodyText.setSize(this.myLogMessageBodyText.getPreferredSize());
      this.myMessageDetailText.setText("");
      this.myMessageDetailText.setSize(this.myMessageDetailText.getPreferredSize());
      this.myMessageCauseText.setText("");
      this.myMessageCauseText.setSize(this.myMessageCauseText.getPreferredSize());
      this.myMessageActionText.setText("");
      this.myMessageActionText.setSize(this.myMessageActionText.getPreferredSize());
      this.myIsStackTrace.setSelected((new LogMessage(this.config)).getStackTrace());
      this.myIsRetired.setSelected((new LogMessage(this.config)).isRetired());
      this.setAddChangeMode("Add");
      this.pack();
   }

   private void clearSimpleMessageFields() {
      this.mySimpleLastUpdated.setText((new Date()).toString());
      this.mySimpleMessageId.setText("");
      this.mySimpleComment.setText("");
      this.mySimpleComment.setSize(this.mySimpleComment.getPreferredSize());
      this.mySimpleMethod.setText("");
      this.mySimpleMessageBodyText.setText("");
      this.mySimpleMessageBodyText.setSize(this.mySimpleMessageBodyText.getPreferredSize());
      this.setAddChangeMode("Add");
      this.pack();
   }

   public void setLogMessageFields(BasicMessage bscMsg, boolean fromViewer) {
      if (bscMsg != null) {
         LogMessage msg = (LogMessage)bscMsg;
         this.mySavedId = msg.getMessageId();
         this.mySavedMethod = msg.getMethodName();
         this.setPanelsForCatalog();
         if (msg.getDateLastChanged() != null) {
            this.myLogLastUpdated.setText((new Date(Long.parseLong(msg.getDateLastChanged()))).toString());
         } else {
            this.myLogLastUpdated.setText((new Date()).toString());
         }

         this.myLogMessageId.setText(msg.getMessageId());
         this.myLogMethod.setText(msg.getMethod());
         String mType = msg.getMethodType();

         int i;
         for(i = 0; i < Config.METHOD_TYPES.length && !mType.toUpperCase().equals(Config.METHOD_TYPES[i].toUpperCase()); ++i) {
         }

         if (i < Config.METHOD_TYPES.length) {
            this.myMethodType.setSelectedIndex(i);
         } else {
            JOptionPane.showMessageDialog(this, fmt.msgInvalidType(mType), "Invalid method type", 0);
         }

         String svrty = msg.getSeverity();
         if (this.myInpArgs.isAllowingServerIds()) {
            for(i = 0; i < Localizer.SERVER_SEVERITIES.length && !svrty.toUpperCase().equals(Localizer.SERVER_SEVERITIES[i].toUpperCase()); ++i) {
            }

            if (i < Localizer.SERVER_SEVERITIES.length) {
               this.mySeverity.setSelectedIndex(i);
            } else {
               JOptionPane.showMessageDialog(this, fmt.msgInvalidSeverity(svrty), "Invalid severity", 0);
            }
         } else {
            for(i = 0; i < Localizer.NON_SERVER_SEVERITIES.length && !svrty.toUpperCase().equals(Localizer.NON_SERVER_SEVERITIES[i].toUpperCase()); ++i) {
            }

            if (i < Localizer.NON_SERVER_SEVERITIES.length) {
               this.mySeverity.setSelectedIndex(i);
            } else {
               JOptionPane.showMessageDialog(this, fmt.msgInvalidSeverity(svrty), "Invalid severity", 0);
            }
         }

         if (msg.getComment() != null) {
            this.myLogComment.setText(msg.getComment());
         } else {
            this.myLogComment.setText("");
         }

         if (msg.getMessageBody() != null) {
            this.myLogMessageBodyText.setText(msg.getMessageBody().getCdata());
         } else {
            this.myLogMessageBodyText.setText("");
         }

         if (msg.getMessageDetail() != null) {
            this.myMessageDetailText.setText(msg.getMessageDetail().getCdata());
         } else {
            this.myMessageDetailText.setText("");
         }

         if (msg.getCause() != null) {
            this.myMessageCauseText.setText(msg.getCause().getCdata());
         } else {
            this.myMessageCauseText.setText("");
         }

         if (msg.getAction() != null) {
            this.myMessageActionText.setText(msg.getAction().getCdata());
         } else {
            this.myMessageActionText.setText("");
         }

         this.myIsStackTrace.setSelected(msg.getStackTrace());
         this.myIsRetired.setSelected(msg.isRetired());
         this.setAddChangeMode("Change");
         this.pack();
         this.repaint();
         if (!fromViewer) {
            MessageViewer msgVwr = this.findMessageViewer(this.myCatalog.getPath());
            if (msgVwr != null) {
               msgVwr.showMessage(msg);
            }
         }
      }

   }

   public void setSimpleMessageFields(BasicMessage bscmsg, boolean fromViewer) {
      if (bscmsg != null) {
         Message msg = (Message)bscmsg;
         this.mySavedId = msg.getMessageId();
         this.mySavedMethod = msg.getMethodName();
         this.setPanelsForCatalog();
         if (msg.getDateLastChanged() != null) {
            this.mySimpleLastUpdated.setText((new Date(Long.parseLong(msg.getDateLastChanged()))).toString());
         } else {
            this.mySimpleLastUpdated.setText((new Date()).toString());
         }

         this.mySimpleMessageId.setText(msg.getMessageId());
         this.mySimpleMethod.setText(msg.getMethod());
         if (msg.getMessageBody() != null) {
            this.mySimpleMessageBodyText.setText(msg.getMessageBody().getCdata());
         } else {
            this.mySimpleMessageBodyText.setText("");
         }

         if (msg.getComment() != null) {
            this.mySimpleComment.setText(msg.getComment());
         } else {
            this.mySimpleComment.setText("");
         }

         this.setAddChangeMode("Change");
         this.pack();
         this.repaint();
         if (!fromViewer) {
            MessageViewer msgVwr = this.findMessageViewer(this.myCatalog.getPath());
            if (msgVwr != null) {
               msgVwr.showMessage(msg);
            }
         }
      }

   }

   public void setCatalog(BasicMessageCatalog cat, String fileName) {
      this.myCatalog = (MessageCatalog)cat;
      this.setCatalogNames(fileName);
      this.mySourceTreeRootField.setText(fileName);
      this.myI18nPackageLabel.setText(this.myCatalog.get("i18n_package"));
      this.myL10nPackageLabel.setText(this.myCatalog.get("l10n_package"));
      this.mySubsystemLabel.setText(this.myCatalog.get("subsystem"));
      this.myVersionLabel.setText(this.myCatalog.get("version"));
      this.myBaseidLabel.setText(this.myCatalog.get("baseid"));
      this.myEndidLabel.setText(this.myCatalog.get("endid"));
      this.myLoggableLabel.setText(this.myCatalog.get("loggables"));
      this.myPrefixLabel.setText(this.myCatalog.get("prefix"));
      this.setPanelsForCatalog();
      this.setTitle("WebLogic Message Editor - " + this.myCatalog.getFileName());
      this.pack();
   }

   private void clearCatalogInfo() {
      this.myI18nPackageLabel.setText("");
      this.myL10nPackageLabel.setText("");
      this.mySubsystemLabel.setText("");
      this.myVersionLabel.setText("");
      this.myBaseidLabel.setText("");
      this.myEndidLabel.setText("");
      this.myLoggableLabel.setText("");
      this.myPrefixLabel.setText("");
      this.setTitle(fmt.titleEditor());
   }

   public void writeCatalogToDisk() {
      if (this.myCatalog != null) {
         try {
            MessageCatalogWriter.writeFormattedCatalog(this.myCatalog.getPath(), this.myCatalog);
         } catch (IOException var2) {
            JOptionPane.showMessageDialog(this, fmt.msgOpenFailed(this.myCatalog.getPath()), fmt.tagError(), 0);
         }
      } else {
         JOptionPane.showMessageDialog(this, fmt.msgChooseCat(), fmt.tagError(), 0);
      }

   }

   private void setAddChangeMode(String mode) {
      if (mode.equals("Change")) {
         this.myInChangeMode = true;
      } else {
         this.myInChangeMode = false;
      }

      if (this.myLogAddChangeButton != null) {
         this.myLogAddChangeButton.setText(this.getAddChangeString());
      }

      if (this.mySimpleAddChangeButton != null) {
         this.mySimpleAddChangeButton.setText(this.getAddChangeString());
      }

   }

   private String getAddChangeString() {
      String str;
      if (this.myInChangeMode) {
         str = fmt.buttonUpdate();
      } else {
         str = fmt.buttonAdd();
      }

      return str;
   }

   private void setPanelsForCatalog() {
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.setPanelsForCatalog");
      }

      if (this.myCatalog == null) {
         this.myLogMsgEditingPanel.setVisible(true);
         this.mySimpleMsgEditingPanel.setVisible(false);
      } else {
         int type = this.myCatalog.getCatType();
         if (type != 2 && (type != 0 || this.myCatalog.get("baseid") == null)) {
            if (type != 1 && (type != 0 || this.myCatalog.get("baseid") != null)) {
               this.clearLogMessageFields();
               this.clearSimpleMessageFields();
               this.myLogMsgEditingPanel.setVisible(false);
               this.mySimpleMsgEditingPanel.setVisible(true);
            } else {
               this.myLogMsgEditingPanel.setVisible(false);
               this.mySimpleMsgEditingPanel.setVisible(true);
               this.clearSimpleMessageFields();
            }
         } else {
            this.myLogMsgEditingPanel.setVisible(true);
            this.mySimpleMsgEditingPanel.setVisible(false);
            this.clearLogMessageFields();
         }
      }

   }

   public void windowActivated(WindowEvent ev) {
   }

   public void windowClosed(WindowEvent ev) {
   }

   public void windowClosing(WindowEvent ev) {
      if (this.config.isVerbose()) {
         System.err.println("MessageEditor.windowClosing");
      }

      if (ev != null) {
         Object src = ev.getSource();
         if (this.config.isVerbose()) {
            System.err.println("MessageEditor.windowClosing, src = " + src);
         }

         if (src instanceof MessageViewer) {
            this.myViewers.removeElement(src);
         } else {
            System.exit(0);
         }
      } else {
         System.exit(0);
      }

   }

   public void windowDeactivated(WindowEvent ev) {
   }

   public void windowDeiconified(WindowEvent ev) {
   }

   public void windowIconified(WindowEvent ev) {
   }

   public void windowOpened(WindowEvent ev) {
   }
}
