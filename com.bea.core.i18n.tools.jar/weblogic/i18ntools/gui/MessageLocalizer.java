package weblogic.i18ntools.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;
import weblogic.i18n.tools.Action;
import weblogic.i18n.tools.BasicMessage;
import weblogic.i18n.tools.BasicMessageCatalog;
import weblogic.i18n.tools.Cause;
import weblogic.i18n.tools.DuplicateElementException;
import weblogic.i18n.tools.LogMessage;
import weblogic.i18n.tools.Message;
import weblogic.i18n.tools.MessageBody;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.MessageCatalogParser;
import weblogic.i18n.tools.MessageDetail;
import weblogic.i18n.tools.MessageNotFoundException;
import weblogic.i18n.tools.WrongTypeException;
import weblogic.i18ntools.internal.I18nConfig;
import weblogic.i18ntools.parser.LocaleCatalogParser;
import weblogic.i18ntools.parser.LocaleCatalogWriter;
import weblogic.i18ntools.parser.LocaleLogMessage;
import weblogic.i18ntools.parser.LocaleMessage;
import weblogic.i18ntools.parser.LocaleMessageCatalog;

public final class MessageLocalizer extends MessageCatalogEditor implements ActionListener, WindowListener, MouseListener {
   private static final boolean debug = false;
   private static final int MASTER_TEXT_AREA_LENGTH = 20;
   public static final boolean DISPLAY_DEFAULT = true;
   private static final String[] myDefArgs = new String[0];
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   private InputArgs myInpArgs;
   private JFileChooser myChooser = null;
   private MessageSearchDialog myLogSearchDialog = null;
   private MessageSearchDialog mySimpleSearchDialog = null;
   private GUIClientTextField mySourceTreeRootField = null;
   private LocalizingMessagePair myPair = null;
   private Vector myPairs = null;
   private JComboBox myWhichLocale;
   private MessageLocale[] myAvailableLocales;
   private boolean myInChangeMode = false;
   private JPanel myMasterCatalogInfoPanel;
   private JPanel myLogMsgEditingPanel;
   private JPanel mySimpleMsgEditingPanel;
   private GridBagConstraints myMasterCatalogLayoutConstraints;
   private GridBagConstraints myContPaneConstraints;
   private JButton myDoThisLocale;
   private JButton myLogAddChangeButton;
   private JButton myLogClearButton;
   private JButton mySimpleAddChangeButton;
   private JButton mySimpleClearButton;
   private Date myMessageDate;
   private boolean myDoingLogMessages;
   private boolean myDoingSimpleMessages;
   private JLabel myLocaleCatalogField;
   private JLabel myLocaleLabel;
   private Color myBackgroundColor;
   private Color myDarkColor;
   private boolean myDisplayMode = true;
   private CopyCutPasteMenu myPopup;
   private Clipboard myClipboard;
   private String myInitFileName;
   private JLabel myI18nPackageLabel;
   private JLabel myL10nPackageLabel;
   private JLabel mySubsystemLabel;
   private JLabel myVersionLabel;
   private JLabel myBaseidLabel;
   private JLabel myEndidLabel;
   private JLabel myPrefixLabel;
   private JLabel myMasterLogLastUpdated;
   private JLabel myMasterLogMessageId;
   private JLabel myMasterLogComment;
   private JTextArea myMasterLogMessageBodyText;
   private JTextArea myMasterMessageDetailText;
   private JTextArea myMasterMessageCauseText;
   private JTextArea myMasterMessageActionText;
   private JLabel myLocaleLogLastUpdated;
   private JLabel myLocaleLogMessageId;
   private GUIClientTextField myLocaleLogComment;
   private JTextArea myLocaleLogMessageBodyText;
   private JTextArea myLocaleMessageDetailText;
   private JTextArea myLocaleMessageCauseText;
   private JTextArea myLocaleMessageActionText;
   private JLabel myMasterSimpleLastUpdated;
   private JLabel myMasterSimpleMessageId;
   private JLabel myMasterSimpleComment;
   private JTextArea myMasterSimpleMessageBodyText;
   private JLabel myLocaleSimpleLastUpdated;
   private JLabel myLocaleSimpleMessageId;
   private GUIClientTextField myLocaleSimpleComment;
   private JTextArea myLocaleSimpleMessageBodyText;
   private I18nConfig cfg = new I18nConfig();

   public static void main(String[] args) {
      try {
         MessageLocalizer msgLocalizer = new MessageLocalizer(args);
         msgLocalizer.runMessageLocalizer();
      } catch (Exception var2) {
         System.err.println(var2.toString());
         System.exit(1);
      }

   }

   private void usage() {
      System.out.println(this.fmt.usageL10n());
   }

   private MessageLocalizer(String[] args) {
      this.myInpArgs = InputArgs.parse(args, myDefArgs);
      boolean server = this.myInpArgs.isAllowingServerIds();
      this.cfg.setServer(server);
      this.cfg.setVerbose(true);
   }

   private void runMessageLocalizer() throws Exception {
      if (this.myInpArgs.getHelp()) {
         this.usage();
      } else {
         if (this.myInpArgs.getUnresolvedArgs() == null) {
            String initFileName = this.myInpArgs.getFileName();
            this.myInitFileName = "";
            File initFile = null;
            if (initFileName != null) {
               initFile = new File(this.myInpArgs.getFileName());
               if (!initFile.exists()) {
                  JOptionPane.showMessageDialog(this, this.fmt.msgNotExist(initFileName), this.fmt.tagError(), 0);
                  return;
               }

               if (initFile.isFile()) {
                  this.myInitFileName = initFile.getName();
                  this.setCatalogDirectory(initFile.getParent());
               } else {
                  this.setCatalogDirectory(initFile.toString());
               }
            }

            this.myPair = new LocalizingMessagePair(this, (MessageCatalog)null, (LocaleMessageCatalog)null);
            this.myPairs = new Vector(5);
            this.myPairs.addElement(this.myPair);
            this.createDisplayableScreen();
            this.addWindowListener(this);
            this.myClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (this.myInitFileName.length() > 0) {
               this.validateCatalogFileName(initFile.toString());
            }

            this.setVisible(true);
         } else {
            String[] badArgs = this.myInpArgs.getUnresolvedArgs();
            StringBuilder paramTemplate = new StringBuilder();

            for(int i = 0; i < badArgs.length; ++i) {
               paramTemplate.append("\n ").append(badArgs[i]);
            }

            String msg = this.fmt.badArgs(paramTemplate.toString());
            System.err.println(msg);
         }

      }
   }

   public void createDisplayableScreen() {
      this.setTitle(this.fmt.titleL10n());
      Image icon = this.loadImage("/weblogic/i18ntools/gui/images/W.gif");
      this.setIconImage(icon);
      Container contPane = this.getContentPane();
      this.myBackgroundColor = contPane.getBackground();
      this.myDarkColor = contPane.getForeground();
      contPane.getLocale();
      Locale[] avLoc = Locale.getAvailableLocales();
      int avLocLen = avLoc.length;
      this.myAvailableLocales = new MessageLocale[avLocLen];

      for(int i = 0; i < avLocLen; ++i) {
         this.myAvailableLocales[i] = new MessageLocale(avLoc[i]);
      }

      Arrays.sort(this.myAvailableLocales, this.myAvailableLocales[0]);
      String[] localeDescriptions = new String[this.myAvailableLocales.length];

      for(int i = 0; i < avLocLen; ++i) {
         localeDescriptions[i] = this.myAvailableLocales[i].getLocale().getDisplayName();
      }

      contPane.setLayout(new GridBagLayout());
      this.myContPaneConstraints = new GridBagConstraints();
      LocalizerMenuBar menuBar = new LocalizerMenuBar(this);
      this.myContPaneConstraints.anchor = 11;
      this.myContPaneConstraints.fill = 2;
      this.myContPaneConstraints.weighty = 0.0;
      this.myContPaneConstraints.weightx = 1.0;
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
      JLabel editorTitle = new JLabel(this.fmt.titleL10n());
      Font fnt = editorTitle.getFont();
      fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
      editorTitle.setFont(fnt);
      this.darken(editorTitle);
      bgGBC.insets = new Insets(1, 10, 1, 10);
      bg.add(editorTitle, bgGBC);
      GridBagConstraints topPanelLayoutContstraints = new GridBagConstraints();
      topPanelLayoutContstraints.fill = 2;
      topPanelLayoutContstraints.insets = new Insets(1, 10, 1, 10);
      JPanel topPanel = new JPanel();
      topPanel.setLayout(new GridBagLayout());
      topPanelLayoutContstraints.gridwidth = 1;
      topPanelLayoutContstraints.gridx = topPanelLayoutContstraints.gridy = 0;
      JLabel sourceTreeRootLabel = new JLabel(this.fmt.labelMasterCat());
      topPanel.add(sourceTreeRootLabel, topPanelLayoutContstraints);
      ++topPanelLayoutContstraints.gridx;
      this.mySourceTreeRootField = new GUIClientTextField("", 20);
      this.mySourceTreeRootField.setActionCommand("ValidateCatalogFile");
      this.mySourceTreeRootField.addActionListener(this);
      topPanel.add(this.mySourceTreeRootField, topPanelLayoutContstraints);
      JButton fileButton = new JButton(this.fmt.buttonBrowse());
      fileButton.addActionListener(this);
      fileButton.setActionCommand("Browse");
      ++topPanelLayoutContstraints.gridx;
      topPanel.add(fileButton, topPanelLayoutContstraints);
      bg.add(topPanel, bgGBC);
      this.myMasterCatalogInfoPanel = new JPanel();
      this.myMasterCatalogInfoPanel.setLayout(new GridBagLayout());
      this.myMasterCatalogInfoPanel.setBorder(BorderFactory.createLoweredBevelBorder());
      this.myMasterCatalogLayoutConstraints = new GridBagConstraints();
      this.myMasterCatalogLayoutConstraints.fill = 2;
      this.myMasterCatalogLayoutConstraints.insets = new Insets(1, 10, 1, 10);
      this.myMasterCatalogLayoutConstraints.gridx = this.myMasterCatalogLayoutConstraints.gridy = 0;
      this.myI18nPackageLabel = this.addCatalogAttribute(new JLabel(this.fmt.labelI18nPkg()), true);
      this.myL10nPackageLabel = this.addCatalogAttribute(new JLabel(this.fmt.labelL10nPkg()), false);
      this.mySubsystemLabel = this.addCatalogAttribute(new JLabel(this.fmt.labelSubsystem()), true);
      this.myVersionLabel = this.addCatalogAttribute(new JLabel(this.fmt.labelVersion()), false);
      this.myBaseidLabel = this.addCatalogAttribute(new JLabel(this.fmt.labelBaseId()), true);
      this.myEndidLabel = this.addCatalogAttribute(new JLabel(this.fmt.labelEndId()), false);
      this.myPrefixLabel = this.addCatalogAttribute(new JLabel(this.fmt.labelPrefix()), true);
      bg.add(this.myMasterCatalogInfoPanel, bgGBC);
      GridBagConstraints localePanelLayoutContstraints = new GridBagConstraints();
      localePanelLayoutContstraints.fill = 2;
      localePanelLayoutContstraints.insets = new Insets(1, 10, 1, 10);
      JPanel localePanel = new JPanel();
      localePanel.setLayout(new GridBagLayout());
      localePanelLayoutContstraints.gridwidth = 1;
      localePanelLayoutContstraints.gridx = localePanelLayoutContstraints.gridy = 0;
      JLabel localeLabel = new JLabel(this.fmt.labelLocale());
      localePanel.add(localeLabel, localePanelLayoutContstraints);
      ++localePanelLayoutContstraints.gridx;
      this.myWhichLocale = new JComboBox(localeDescriptions);
      this.myWhichLocale.addActionListener(this);
      this.myWhichLocale.setActionCommand("Locale");
      localePanel.add(this.myWhichLocale, localePanelLayoutContstraints);
      JLabel localeCatalogLabel = new JLabel(this.fmt.labelLocaleCat());
      localePanelLayoutContstraints.gridx = 0;
      ++localePanelLayoutContstraints.gridy;
      localePanel.add(localeCatalogLabel, localePanelLayoutContstraints);
      this.myLocaleCatalogField = new JLabel("");
      this.darken(this.myLocaleCatalogField);
      ++localePanelLayoutContstraints.gridx;
      localePanel.add(this.myLocaleCatalogField, localePanelLayoutContstraints);
      localePanelLayoutContstraints.gridx = 0;
      ++localePanelLayoutContstraints.gridy;
      Dimension dim = new Dimension(1, 1);
      localePanel.add(new Box.Filler(dim, dim, dim), localePanelLayoutContstraints);
      this.myDoThisLocale = new JButton(this.fmt.buttonCreate());
      this.myDoThisLocale.setEnabled(false);
      this.myDoThisLocale.addActionListener(this);
      ++localePanelLayoutContstraints.gridx;
      localePanel.add(this.myDoThisLocale, localePanelLayoutContstraints);
      bg.add(localePanel, bgGBC);
      contPane.add(bg, this.myContPaneConstraints);
      this.myLogMsgEditingPanel = this.makeLogMessagePanel();
      this.mySimpleMsgEditingPanel = this.makeSimpleMessagePanel();
      contPane.add(this.myLogMsgEditingPanel, this.myContPaneConstraints);
      contPane.add(this.mySimpleMsgEditingPanel, this.myContPaneConstraints);
      this.setPanelsForCatalog();
      this.setLocaleFieldsEnabled(false);
      this.pack();
   }

   private void setLocaleFieldsEnabled(boolean enableOrNot) {
      this.setLocaleLogFieldsEnabled(enableOrNot);
      this.setLocaleSimpleFieldsEnabled(enableOrNot);
   }

   private void setLocaleLogFieldsEnabled(boolean enableOrNot) {
      this.myLocaleLogLastUpdated.setEnabled(enableOrNot);
      this.myLocaleLogMessageId.setEnabled(enableOrNot);
      this.myLocaleLogComment.setEnabled(enableOrNot);
      this.myLocaleLogMessageBodyText.setEnabled(enableOrNot);
      this.myLocaleMessageDetailText.setEnabled(enableOrNot);
      this.myLocaleMessageCauseText.setEnabled(enableOrNot);
      this.myLocaleMessageActionText.setEnabled(enableOrNot);
   }

   private void setLocaleSimpleFieldsEnabled(boolean enableOrNot) {
      this.myLocaleSimpleLastUpdated.setEnabled(enableOrNot);
      this.myLocaleSimpleMessageId.setEnabled(enableOrNot);
      this.myLocaleSimpleComment.setEnabled(enableOrNot);
      this.myLocaleSimpleMessageBodyText.setEnabled(enableOrNot);
   }

   private JPanel makeLogMessagePanel() {
      JPanel logMsgEditingPanel = new JPanel();
      logMsgEditingPanel.setLayout(new BorderLayout());
      logMsgEditingPanel.setBorder(new BevelBorder(1));
      JPanel logMessagePanel = this.makeLogMessageEditingFields();
      JPanel actionPanel = new JPanel();
      this.myLogAddChangeButton = new JButton(this.fmt.buttonAdd());
      this.myLogAddChangeButton.setActionCommand("AddLog");
      this.myLogAddChangeButton.addActionListener(this);
      actionPanel.add(this.myLogAddChangeButton);
      this.setAddChangeMode("Add");
      this.myLogClearButton = new JButton(this.fmt.buttonClear());
      this.myLogClearButton.addActionListener(this);
      this.myLogClearButton.setActionCommand("ClearLog");
      actionPanel.add(this.myLogClearButton);
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
      this.mySimpleAddChangeButton = new JButton(this.fmt.buttonAdd());
      this.mySimpleAddChangeButton.setActionCommand("AddSimple");
      this.mySimpleAddChangeButton.addActionListener(this);
      actionPanel.add(this.mySimpleAddChangeButton);
      this.setAddChangeMode("Add");
      this.mySimpleClearButton = new JButton(this.fmt.buttonClear());
      this.mySimpleClearButton.addActionListener(this);
      this.mySimpleClearButton.setActionCommand("ClearSimple");
      actionPanel.add(this.mySimpleClearButton);
      simpleMsgEditingPanel.add(simpleMessagePanel, "North");
      simpleMsgEditingPanel.add(actionPanel, "Center");
      return simpleMsgEditingPanel;
   }

   private JPanel makeLogMessageEditingFields() {
      JPanel logMessagePanel = new JPanel();
      logMessagePanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 0.0;
      gbc.anchor = 11;
      gbc.gridwidth = 0;
      gbc.insets = new Insets(3, 10, 3, 10);
      gbc.gridx = gbc.gridy = 0;
      JLabel logMessageTitle = new JLabel(this.fmt.logMessageTitle());
      Font fnt = logMessageTitle.getFont();
      fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
      logMessageTitle.setFont(fnt);
      this.darken(logMessageTitle);
      logMessagePanel.add(logMessageTitle, gbc);
      ++gbc.gridy;
      gbc.gridwidth = 1;
      gbc.fill = 2;
      JLabel filler = new JLabel("");
      logMessagePanel.add(filler, gbc);
      ++gbc.gridx;
      JLabel masterLabel = new JLabel(this.fmt.labelMasterCat());
      logMessagePanel.add(masterLabel, gbc);
      ++gbc.gridx;
      this.myLocaleLabel = new JLabel(this.fmt.labelLocaleCat());
      logMessagePanel.add(this.myLocaleLabel, gbc);
      ++gbc.gridy;
      gbc.gridx = 0;
      JLabel lastUpdatedLabel = new JLabel(this.fmt.labelLastUpdated());
      logMessagePanel.add(lastUpdatedLabel, gbc);
      ++gbc.gridx;
      this.myMasterLogLastUpdated = new JLabel("");
      this.darken(this.myMasterLogLastUpdated);
      logMessagePanel.add(this.myMasterLogLastUpdated, gbc);
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.myLocaleLogLastUpdated = new JLabel("");
      this.darken(this.myLocaleLogLastUpdated);
      logMessagePanel.add(this.myLocaleLogLastUpdated, gbc);
      gbc.weightx = 0.0;
      this.makeLabel(gbc, logMessagePanel, this.fmt.labelMessageId(), this.fmt.tipMessageId());
      this.myMasterLogMessageId = new JLabel("");
      this.darken(this.myMasterLogMessageId);
      logMessagePanel.add(this.myMasterLogMessageId, gbc);
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.myLocaleLogMessageId = new JLabel("");
      this.darken(this.myLocaleLogMessageId);
      logMessagePanel.add(this.myLocaleLogMessageId, gbc);
      gbc.weightx = 0.0;
      this.makeLabel(gbc, logMessagePanel, this.fmt.labelComment(), this.fmt.tipComment());
      this.myMasterLogComment = new JLabel("");
      this.darken(this.myMasterLogComment);
      logMessagePanel.add(this.myMasterLogComment, gbc);
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.myLocaleLogComment = new GUIClientTextField("", 20);
      this.myLocaleLogComment.addMouseListener(this);
      logMessagePanel.add(this.myLocaleLogComment, gbc);
      gbc.weightx = 0.0;
      this.makeLabel(gbc, logMessagePanel, this.fmt.labelBody(), (String)null);
      this.myMasterLogMessageBodyText = new JTextArea("", 3, 20);
      this.configureTextArea(this.myMasterLogMessageBodyText);
      logMessagePanel.add(this.myMasterLogMessageBodyText, gbc);
      this.myLocaleLogMessageBodyText = this.makeLocaleTextArea(gbc, logMessagePanel);
      this.makeLabel(gbc, logMessagePanel, this.fmt.labelDetail(), (String)null);
      this.myMasterMessageDetailText = new JTextArea("", 3, 20);
      this.configureTextArea(this.myMasterMessageDetailText);
      logMessagePanel.add(this.myMasterMessageDetailText, gbc);
      this.myLocaleMessageDetailText = this.makeLocaleTextArea(gbc, logMessagePanel);
      this.makeLabel(gbc, logMessagePanel, this.fmt.labelCause(), (String)null);
      this.myMasterMessageCauseText = new JTextArea("", 3, 20);
      this.configureTextArea(this.myMasterMessageCauseText);
      logMessagePanel.add(this.myMasterMessageCauseText, gbc);
      this.myLocaleMessageCauseText = this.makeLocaleTextArea(gbc, logMessagePanel);
      this.makeLabel(gbc, logMessagePanel, this.fmt.labelAction(), (String)null);
      this.myMasterMessageActionText = new JTextArea("", 3, 20);
      this.configureTextArea(this.myMasterMessageActionText);
      logMessagePanel.add(this.myMasterMessageActionText, gbc);
      this.myLocaleMessageActionText = this.makeLocaleTextArea(gbc, logMessagePanel);
      return logMessagePanel;
   }

   private void makeLabel(GridBagConstraints gbc, JPanel panl, String label, String tooltip) {
      gbc.gridx = 0;
      ++gbc.gridy;
      JLabel theLabel = new JLabel(label);
      if (tooltip != null) {
         theLabel.setToolTipText(tooltip);
      }

      panl.add(theLabel, gbc);
      ++gbc.gridx;
   }

   private JTextArea makeLocaleTextArea(GridBagConstraints gbc, JPanel panl) {
      ++gbc.gridx;
      gbc.weightx = 1.0;
      JTextArea area = new JTextArea("", 3, 30);
      area.addMouseListener(this);
      JScrollPane pane = new JScrollPane(area);
      panl.add(pane, gbc);
      gbc.weightx = 0.0;
      return area;
   }

   private JPanel makeSimpleMessageEditingFields() {
      JPanel simpleMessagePanel = new JPanel();
      simpleMessagePanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 0.0;
      gbc.anchor = 11;
      gbc.gridwidth = 0;
      gbc.insets = new Insets(3, 10, 3, 10);
      gbc.gridx = gbc.gridy = 0;
      JLabel simpleMessageTitle = new JLabel(this.fmt.simpleMessageTitle());
      Font fnt = simpleMessageTitle.getFont();
      fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
      simpleMessageTitle.setFont(fnt);
      this.darken(simpleMessageTitle);
      simpleMessagePanel.add(simpleMessageTitle, gbc);
      gbc.gridwidth = 1;
      gbc.fill = 2;
      this.makeLabel(gbc, simpleMessagePanel, this.fmt.labelLastUpdated(), (String)null);
      this.myMasterSimpleLastUpdated = new JLabel("");
      this.darken(this.myMasterSimpleLastUpdated);
      simpleMessagePanel.add(this.myMasterSimpleLastUpdated, gbc);
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.myLocaleSimpleLastUpdated = new JLabel("");
      this.darken(this.myLocaleSimpleLastUpdated);
      simpleMessagePanel.add(this.myLocaleSimpleLastUpdated, gbc);
      gbc.weightx = 0.0;
      this.makeLabel(gbc, simpleMessagePanel, this.fmt.labelMessageId(), (String)null);
      this.myMasterSimpleMessageId = new JLabel("");
      this.darken(this.myMasterSimpleMessageId);
      simpleMessagePanel.add(this.myMasterSimpleMessageId, gbc);
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.myLocaleSimpleMessageId = new JLabel("");
      this.darken(this.myLocaleSimpleMessageId);
      simpleMessagePanel.add(this.myLocaleSimpleMessageId, gbc);
      gbc.weightx = 0.0;
      this.makeLabel(gbc, simpleMessagePanel, this.fmt.labelComment(), this.fmt.tipComment());
      this.myMasterSimpleComment = new JLabel("");
      this.darken(this.myMasterSimpleComment);
      simpleMessagePanel.add(this.myMasterSimpleComment, gbc);
      ++gbc.gridx;
      gbc.weightx = 1.0;
      this.myLocaleSimpleComment = new GUIClientTextField("", 20);
      this.darken(this.myLocaleSimpleComment);
      simpleMessagePanel.add(this.myLocaleSimpleComment, gbc);
      gbc.weightx = 0.0;
      this.makeLabel(gbc, simpleMessagePanel, this.fmt.labelBody(), (String)null);
      this.myMasterSimpleMessageBodyText = new JTextArea("", 3, 20);
      this.configureTextArea(this.myMasterSimpleMessageBodyText);
      simpleMessagePanel.add(this.myMasterSimpleMessageBodyText, gbc);
      this.myLocaleSimpleMessageBodyText = this.makeLocaleTextArea(gbc, simpleMessagePanel);
      return simpleMessagePanel;
   }

   private void configureTextArea(JTextArea txtArea) {
      txtArea.setLineWrap(true);
      txtArea.setWrapStyleWord(true);
      txtArea.setEditable(false);
      txtArea.setBackground(this.myBackgroundColor);
      txtArea.setDisabledTextColor(this.myDarkColor);
   }

   private JLabel addCatalogAttribute(JLabel label, boolean newRow) {
      if (newRow) {
         this.myMasterCatalogLayoutConstraints.gridx = 0;
         ++this.myMasterCatalogLayoutConstraints.gridy;
      } else {
         ++this.myMasterCatalogLayoutConstraints.gridx;
      }

      this.myMasterCatalogInfoPanel.add(label, this.myMasterCatalogLayoutConstraints);
      JLabel attrLabel = new JLabel(this.fmt.labelNA());
      this.darken(attrLabel);
      ++this.myMasterCatalogLayoutConstraints.gridx;
      this.myMasterCatalogInfoPanel.add(attrLabel, this.myMasterCatalogLayoutConstraints);
      return attrLabel;
   }

   public void actionPerformed(ActionEvent ev) {
      String cmd = ev.getActionCommand();
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
      } else if (cmd.equals("Locale")) {
         this.setLocaleCatalogPath();
         this.pack();
      } else if (cmd.equals("CreateCatalog")) {
         this.createLocaleCatalog();
      } else if (cmd.equals("EditCatalog")) {
         this.setupForEditLocaleCatalog();
      } else if (cmd.equals("EndEdit")) {
         this.endEditing();
      } else if (cmd.equals("ValidateCatalogFile")) {
         this.validateCatalogFileName(this.mySourceTreeRootField.getText());
      } else if (cmd.equals("WriteCatalog")) {
         this.writeCatalogToDisk();
      } else if (cmd.equals("AddLog")) {
         this.addOrChangeLogMessage();
      } else if (cmd.equals("AddSimple")) {
         this.addOrChangeSimpleMessage();
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
      } else if (cmd.equals("DisplayMasterText")) {
         this.setDisplayMode("DisplayMaster");
      } else if (cmd.equals("LeaveFieldsClear")) {
         this.setDisplayMode("Clear");
      } else if (cmd.equals("Search")) {
         if (this.myPair.getMasterCatalog() != null) {
            this.showSearchDialog();
         } else {
            JOptionPane.showMessageDialog(this, this.fmt.msgChooseCat(), this.fmt.tagError(), 0);
         }
      } else if (cmd.equals("Cut")) {
         this.cutSelectedText(ev);
         this.myPopup.setVisible(false);
      } else if (cmd.equals("Copy")) {
         this.copySelectedText(ev);
         this.myPopup.setVisible(false);
      } else if (cmd.equals("Paste")) {
         this.pasteSelectedText(ev);
         this.myPopup.setVisible(false);
      } else if (cmd.equals("Exit")) {
         this.windowClosing((WindowEvent)null);
      }

   }

   private void cutSelectedText(ActionEvent ev) {
      JTextComponent src = this.myPopup.getSource();
      if (src != null) {
         src.cut();
      }

   }

   private void copySelectedText(ActionEvent ev) {
      JTextComponent src = this.myPopup.getSource();
      if (src != null) {
         src.copy();
      }

   }

   private void pasteSelectedText(ActionEvent ev) {
      JTextComponent src = this.myPopup.getSource();
      if (src != null) {
         src.paste();
      }

   }

   private void createLocaleCatalog() {
      LocaleMessageCatalog cat = new LocaleMessageCatalog(this.cfg);
      this.setLocaleCatalog(cat, this.myLocaleCatalogField.getText());
      this.myPair.getLocaleCatalog().set("version", this.myPair.getMasterCatalog().get("version"));
      this.myPair.getLocaleCatalog().set("l10n_package", this.myPair.getMasterCatalog().get("l10n_package"));
      if (this.writeCatalogToDisk()) {
         this.setupLocaleParser(cat.getLocale());
         this.setupForEndEdit();
      }

   }

   private void setupForEditLocaleCatalog() {
      MessageLocale loc = this.myAvailableLocales[this.myWhichLocale.getSelectedIndex()];
      LocaleCatalogParser parser = this.setupLocaleParser(loc.getLocale());
      String fileName = this.myLocaleCatalogField.getText();

      try {
         this.setLocaleCatalog(parser.parse(fileName), fileName);
         this.setupForEndEdit();
      } catch (Exception var5) {
         JOptionPane.showMessageDialog(this, this.fmt.msgBadParseL10n(fileName, var5.toString()), this.fmt.tagError(), 0);
      }

      this.removeSearchDialog();
   }

   private void setupForEndEdit() {
      this.myDoThisLocale.setText(this.fmt.msgEndEdit());
      this.myDoThisLocale.setActionCommand("EndEdit");
      this.myLocaleLabel.setText("Locale Catalog: " + this.myPair.getLocaleCatalog().getLocale().getDisplayName());
      this.myWhichLocale.setEnabled(false);
   }

   private void endEditing() {
      this.myDoThisLocale.setEnabled(false);
      this.myWhichLocale.setEnabled(true);
      this.myPair.setLocaleCatalog((LocaleMessageCatalog)null);
   }

   public LocaleCatalogParser setupLocaleParser(Locale loc) {
      return new LocaleCatalogParser(loc, this.cfg);
   }

   private void setDisplayMode(String mode) {
      if (mode.equals("Clear")) {
         this.myDisplayMode = false;
      } else {
         this.myDisplayMode = true;
      }

   }

   private void setFonts(String type) {
      Font fnt = this.mySourceTreeRootField.getFont();
      int style = fnt.getStyle();
      int size = fnt.getSize();
      if (type.equals("Variable")) {
         type = "Dialog";
      } else {
         type = "Monospaced";
      }

      Font newFont = new Font(type, style, size);
      this.myLocaleLogComment.setFont(newFont);
      this.myLocaleLogMessageBodyText.setFont(newFont);
      this.myLocaleMessageDetailText.setFont(newFont);
      this.myLocaleMessageCauseText.setFont(newFont);
      this.myLocaleMessageActionText.setFont(newFont);
      this.myLocaleSimpleMessageId.setFont(newFont);
      this.myLocaleSimpleComment.setFont(newFont);
      this.myLocaleSimpleMessageBodyText.setFont(newFont);
      this.pack();
   }

   private void setLocaleCatalogPath() {
      if (this.myPair.getMasterCatalog() == null) {
         this.myPair.setLocaleCatalog((LocaleMessageCatalog)null);
         this.myLocaleCatalogField.setText("");
         this.myLocaleCatalogField.setEnabled(false);
         this.myDoThisLocale.setEnabled(false);
      } else {
         int idx = this.myWhichLocale.getSelectedIndex();
         MessageLocale locale = this.myAvailableLocales[idx];
         String lan = locale.getLocale().getLanguage();
         String cntry = locale.getLocale().getCountry();
         String var = locale.getLocale().getVariant();
         String masterCatalogPath = this.myPair.getMasterCatalog().getPath();
         new File(this.myPair.getMasterCatalog().getFileName());
         char sep = File.separatorChar;
         int location = masterCatalogPath.lastIndexOf(sep);
         String dir;
         if (cntry.equals("")) {
            dir = lan;
         } else if (var.equals("")) {
            dir = lan + sep + cntry;
         } else {
            dir = lan + sep + cntry + sep + var;
         }

         String localeCatalogPath = masterCatalogPath.substring(0, location + 1) + dir + sep + masterCatalogPath.substring(location + 1);
         this.setFileTitle(masterCatalogPath.substring(location + 1) + "  " + locale.getLocale().getDisplayName());
         this.myLocaleCatalogField.setText(localeCatalogPath);
         File fil = new File(localeCatalogPath);
         this.myDoThisLocale.setEnabled(true);
         if (fil.exists()) {
            if (this.myPair.getLocaleCatalog() == null || !this.myPair.getLocaleCatalog().getPath().equals(localeCatalogPath)) {
               this.myDoThisLocale.setText("Edit this catalog");
               this.myDoThisLocale.setActionCommand("EditCatalog");
            }
         } else {
            this.myDoThisLocale.setText("Create this catalog");
            this.myDoThisLocale.setActionCommand("CreateCatalog");
         }
      }

   }

   private void showMessageViewer() {
      if (this.myPair.getMasterCatalog() == null) {
         JOptionPane.showMessageDialog(this, this.fmt.msgChooseCat(), this.fmt.tagError(), 0);
      } else if (this.myPair.getLocaleCatalog() == null) {
         this.myPair.addMessageViewers("Master Catalog ", (String)null);
      } else {
         this.myPair.addMessageViewers("Master Catalog ", "Locale Catalog " + this.myPair.getLocaleCatalog().getLocale().getDisplayName() + " ");
      }

   }

   private void showSearchDialog() {
      this.removeSearchDialog();
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
      this.clearCatalogInfo();

      try {
         MessageCatalog mCat = (new MessageCatalogParser(this.cfg, false)).parse(fileName);
         this.myPair = new LocalizingMessagePair(this, mCat, (LocaleMessageCatalog)null);
         this.myPairs.addElement(this.myPair);
         this.setMasterCatalog(mCat, fileName);
      } catch (Exception var3) {
         JOptionPane.showMessageDialog(this, this.fmt.msgBadParseL10n(fileName, var3.toString()), this.fmt.tagError(), 0);
         return false;
      }

      this.removeSearchDialog();
      return true;
   }

   private boolean checkNotBlank(String item, String name) {
      if (item.equals("")) {
         JOptionPane.showMessageDialog(this, this.fmt.msgBlankItem(name), this.fmt.tagError(), 0);
         return false;
      } else {
         return true;
      }
   }

   private void addOrChangeLogMessage() {
      this.myDoingLogMessages = true;
      this.myDoingSimpleMessages = false;
      boolean result = false;
      if (this.myPair.getMasterCatalog() == null) {
         JOptionPane.showMessageDialog(this, this.fmt.msgChooseMaster(), this.fmt.tagError(), 0);
      } else if (this.myPair.getLocaleCatalog() == null) {
         JOptionPane.showMessageDialog(this, this.fmt.msgChooseL10N(), this.fmt.tagError(), 0);
      } else {
         String addOrChange = this.getAddChangeString();
         String msgID = this.myMasterLogMessageId.getText();
         if (this.compareBlankness(this.myMasterLogMessageBodyText, this.myLocaleLogMessageBodyText, "Message Body") && this.compareBlankness(this.myMasterMessageDetailText, this.myLocaleMessageDetailText, "Detail") && this.compareBlankness(this.myMasterMessageCauseText, this.myLocaleMessageCauseText, "Cause") && this.compareBlankness(this.myMasterMessageActionText, this.myLocaleMessageActionText, "Action")) {
            LocaleLogMessage logMsg = new LocaleLogMessage(this.cfg);
            this.myMessageDate = new Date();
            this.myLocaleLogLastUpdated.setText(this.myMessageDate.toString());
            logMsg.set("messageid", msgID);
            logMsg.set("datelastchanged", Long.toString(this.myMessageDate.getTime()));
            logMsg.setComment(this.myLocaleLogComment.getText());
            String temp = this.myLocaleLogMessageBodyText.getText();
            temp = this.correctQuotes(temp);
            logMsg.addMessageBody(new MessageBody(temp));
            temp = this.myLocaleMessageDetailText.getText();
            temp = this.correctQuotes(temp);
            logMsg.addMessageDetail(new MessageDetail(temp));
            temp = this.myLocaleMessageCauseText.getText();
            temp = this.correctQuotes(temp);
            logMsg.addCause(new Cause(temp));
            temp = this.myLocaleMessageActionText.getText();
            temp = this.correctQuotes(temp);
            logMsg.addAction(new Action(temp));
            if (this.myInChangeMode) {
               try {
                  this.myPair.changeLogMessage(logMsg);
                  result = true;
               } catch (WrongTypeException var9) {
                  JOptionPane.showMessageDialog(this, this.fmt.msgWrongTypeX(), this.fmt.tagFatal(), 0);
                  return;
               } catch (MessageNotFoundException var10) {
                  JOptionPane.showMessageDialog(this, var10.toString(), this.fmt.tagNoMsg(), 0);
               }
            } else {
               try {
                  this.myPair.addLogMessage(logMsg);
                  result = true;
               } catch (WrongTypeException var7) {
                  JOptionPane.showMessageDialog(this, this.fmt.msgNotLogCat(), this.fmt.tagError(), 0);
               } catch (DuplicateElementException var8) {
                  JOptionPane.showMessageDialog(this, var8.toString(), this.fmt.msgDupId(), 0);
               }
            }

            if (result) {
               this.writeCatalogToDisk();
               this.myLogClearButton.doClick();
               this.setAddChangeMode("Add");
            } else {
               JOptionPane.showMessageDialog(this, this.fmt.msgAddFailed(addOrChange), this.fmt.tagError(), 0);
            }
         }
      }

   }

   private boolean compareBlankness(JTextArea master, JTextArea locale, String fieldName) {
      boolean success = true;
      return success;
   }

   private void addOrChangeSimpleMessage() {
      this.myDoingLogMessages = false;
      this.myDoingSimpleMessages = true;
      boolean result = false;
      if (this.myPair.getMasterCatalog() == null) {
         JOptionPane.showMessageDialog(this, this.fmt.msgChooseMaster(), this.fmt.tagError(), 0);
      } else if (this.myPair.getLocaleCatalog() == null) {
         JOptionPane.showMessageDialog(this, this.fmt.msgChooseL10N(), this.fmt.tagError(), 0);
      } else {
         String addOrChange = this.getAddChangeString();
         String msgID = this.myLocaleSimpleMessageId.getText();
         if (this.compareBlankness(this.myMasterSimpleMessageBodyText, this.myLocaleSimpleMessageBodyText, "Message Body")) {
            LocaleMessage simpleMsg = new LocaleMessage(this.cfg);
            if (msgID.equals("")) {
               JOptionPane.showMessageDialog(this, this.fmt.msgEnterId(), this.fmt.tagError(), 0);
            } else {
               this.myMessageDate = new Date();
               this.myLocaleSimpleLastUpdated.setText(this.myMessageDate.toString());
               simpleMsg.set("messageid", msgID);
               simpleMsg.set("datelastchanged", Long.toString(this.myMessageDate.getTime()));
               String temp = this.myLocaleSimpleMessageBodyText.getText();
               temp = this.correctQuotes(temp);
               simpleMsg.addMessageBody(new MessageBody(temp));
               simpleMsg.setComment(this.myLocaleSimpleComment.getText());
               if (this.myInChangeMode) {
                  try {
                     this.myPair.changeMessage(simpleMsg);
                     result = true;
                  } catch (WrongTypeException var9) {
                     JOptionPane.showMessageDialog(this, this.fmt.msgWrongTypeX(), this.fmt.tagFatal(), 0);
                     return;
                  } catch (MessageNotFoundException var10) {
                     JOptionPane.showMessageDialog(this, var10.toString(), this.fmt.tagNoMsg(), 0);
                  }
               } else {
                  try {
                     this.myPair.addMessage(simpleMsg);
                     result = true;
                  } catch (WrongTypeException var7) {
                     JOptionPane.showMessageDialog(this, this.fmt.msgNotSimpleCat(), this.fmt.tagError(), 0);
                  } catch (DuplicateElementException var8) {
                     JOptionPane.showMessageDialog(this, var8.toString(), this.fmt.msgDupId(), 0);
                  }
               }

               if (result) {
                  this.writeCatalogToDisk();
                  this.mySimpleClearButton.doClick();
                  this.setAddChangeMode("Add");
               } else {
                  JOptionPane.showMessageDialog(this, this.fmt.msgAddFailed(addOrChange), this.fmt.tagError(), 0);
               }
            }
         }
      }

   }

   private void clearLogMessageFields() {
      this.myMasterLogLastUpdated.setText("");
      this.myMasterLogMessageId.setText("");
      this.myMasterLogComment.setText("");
      this.myMasterLogMessageBodyText.setText("");
      this.myMasterMessageDetailText.setText("");
      this.myMasterMessageCauseText.setText("");
      this.myMasterMessageActionText.setText("");
      this.myLocaleLogLastUpdated.setText("");
      this.myLocaleLogMessageId.setText("");
      this.myLocaleLogComment.setText("");
      this.myLocaleLogComment.setSize(this.myLocaleLogComment.getPreferredSize());
      this.myLocaleLogMessageBodyText.setText("");
      this.myLocaleLogMessageBodyText.setSize(this.myLocaleLogMessageBodyText.getPreferredSize());
      this.myLocaleMessageDetailText.setText("");
      this.myLocaleMessageDetailText.setSize(this.myLocaleMessageDetailText.getPreferredSize());
      this.myLocaleMessageCauseText.setText("");
      this.myLocaleMessageCauseText.setSize(this.myLocaleMessageCauseText.getPreferredSize());
      this.myLocaleMessageActionText.setText("");
      this.myLocaleMessageActionText.setSize(this.myLocaleMessageActionText.getPreferredSize());
      this.setAddChangeMode("Add");
      this.pack();
   }

   private void clearSimpleMessageFields() {
      this.myMasterSimpleLastUpdated.setText("");
      this.myMasterSimpleMessageId.setText("");
      this.myMasterSimpleComment.setText("");
      this.myMasterSimpleMessageBodyText.setText("");
      this.myLocaleSimpleLastUpdated.setText("");
      this.myLocaleSimpleMessageId.setText("");
      this.myLocaleSimpleComment.setText("");
      this.myLocaleSimpleComment.setSize(this.myLocaleSimpleComment.getPreferredSize());
      this.myLocaleSimpleMessageBodyText.setText("");
      this.myLocaleSimpleMessageBodyText.setSize(this.myLocaleSimpleMessageBodyText.getPreferredSize());
      this.setAddChangeMode("Add");
      this.pack();
   }

   public void setLogMessageFields(BasicMessage bscMsg, boolean fromViewer) {
      LogMessage masterLogMsg = null;
      LocaleLogMessage locLogMsg = null;
      if (bscMsg != null) {
         if (this.myPair.getLocaleCatalog() == null) {
            JOptionPane.showMessageDialog(this, this.fmt.msgChooseCat(), this.fmt.tagError(), 0);
         } else {
            if (bscMsg instanceof LogMessage) {
               masterLogMsg = (LogMessage)bscMsg;
               locLogMsg = (LocaleLogMessage)this.myPair.getLocaleCatalog().findMessage(masterLogMsg.getMessageId());
            } else {
               locLogMsg = (LocaleLogMessage)bscMsg;
               masterLogMsg = (LogMessage)this.myPair.getMasterCatalog().findMessage(locLogMsg.getMessageId());
            }

            this.setPanelsForCatalog();
            if (masterLogMsg.getDateLastChanged() != null) {
               this.myMasterLogLastUpdated.setText((new Date(Long.parseLong(masterLogMsg.getDateLastChanged()))).toString());
            } else {
               this.myMasterLogLastUpdated.setText("");
            }

            this.myMasterLogMessageId.setText(masterLogMsg.getMessageId());
            if (masterLogMsg.getMessageBody() != null) {
               this.myMasterLogMessageBodyText.setText(masterLogMsg.getMessageBody().getCdata());
            } else {
               this.myMasterLogMessageBodyText.setText("");
            }

            if (masterLogMsg.getComment() != null) {
               this.myMasterLogComment.setText(masterLogMsg.getComment());
            } else {
               this.myMasterLogComment.setText("");
            }

            if (masterLogMsg.getMessageDetail() != null) {
               this.myMasterMessageDetailText.setText(masterLogMsg.getMessageDetail().getCdata());
            } else {
               this.myMasterMessageDetailText.setText("");
            }

            if (masterLogMsg.getCause() != null) {
               this.myMasterMessageCauseText.setText(masterLogMsg.getCause().getCdata());
            } else {
               this.myMasterMessageCauseText.setText("");
            }

            if (masterLogMsg.getAction() != null) {
               this.myMasterMessageActionText.setText(masterLogMsg.getAction().getCdata());
            } else {
               this.myMasterMessageActionText.setText("");
            }

            if (locLogMsg == null) {
               this.myLocaleLogLastUpdated.setText((new Date()).toString());
               this.myLocaleLogMessageId.setText(masterLogMsg.getMessageId());
               this.setAddChangeMode("Add");
               if (this.myDisplayMode) {
                  if (masterLogMsg.getMessageBody() != null) {
                     this.myLocaleLogMessageBodyText.setText(masterLogMsg.getMessageBody().getCdata());
                  } else {
                     this.myLocaleLogMessageBodyText.setText("");
                  }

                  if (masterLogMsg.getComment() != null) {
                     this.myLocaleLogComment.setText(masterLogMsg.getComment());
                  } else {
                     this.myLocaleLogComment.setText("");
                  }

                  if (masterLogMsg.getMessageDetail() != null) {
                     this.myLocaleMessageDetailText.setText(masterLogMsg.getMessageDetail().getCdata());
                  } else {
                     this.myLocaleMessageDetailText.setText("");
                  }

                  if (masterLogMsg.getCause() != null) {
                     this.myLocaleMessageCauseText.setText(masterLogMsg.getCause().getCdata());
                  } else {
                     this.myLocaleMessageCauseText.setText("");
                  }

                  if (masterLogMsg.getAction() != null) {
                     this.myLocaleMessageActionText.setText(masterLogMsg.getAction().getCdata());
                  } else {
                     this.myLocaleMessageActionText.setText("");
                  }
               }
            } else {
               if (locLogMsg.getDateLastChanged() != null) {
                  this.myLocaleLogLastUpdated.setText((new Date(Long.parseLong(locLogMsg.getDateLastChanged()))).toString());
               } else {
                  this.myLocaleLogLastUpdated.setText("");
               }

               this.myLocaleLogMessageId.setText(locLogMsg.getMessageId());
               if (locLogMsg.getMessageBody() != null) {
                  this.myLocaleLogMessageBodyText.setText(locLogMsg.getMessageBody().getCdata());
               } else {
                  this.myLocaleLogMessageBodyText.setText("");
               }

               if (locLogMsg.getComment() != null) {
                  this.myLocaleLogComment.setText(locLogMsg.getComment());
               } else {
                  this.myLocaleLogComment.setText("");
               }

               if (locLogMsg.getMessageDetail() != null) {
                  this.myLocaleMessageDetailText.setText(locLogMsg.getMessageDetail().getCdata());
               } else {
                  this.myLocaleMessageDetailText.setText("");
               }

               if (locLogMsg.getCause() != null) {
                  this.myLocaleMessageCauseText.setText(locLogMsg.getCause().getCdata());
               } else {
                  this.myLocaleMessageCauseText.setText("");
               }

               if (locLogMsg.getAction() != null) {
                  this.myLocaleMessageActionText.setText(locLogMsg.getAction().getCdata());
               } else {
                  this.myLocaleMessageActionText.setText("");
               }

               this.setAddChangeMode("Change");
            }

            this.setLocaleLogFieldsEnabled(true);
            this.pack();
            this.repaint();
            this.myPair.showMessages(masterLogMsg, locLogMsg);
         }
      }

   }

   public void setSimpleMessageFields(BasicMessage bscMsg, boolean fromViewer) {
      Message masterMsg = null;
      LocaleMessage locMsg = null;
      if (bscMsg != null) {
         if (bscMsg instanceof Message) {
            masterMsg = (Message)bscMsg;
            locMsg = (LocaleMessage)this.myPair.getLocaleCatalog().findMessage(masterMsg.getMessageId());
         } else {
            locMsg = (LocaleMessage)bscMsg;
            masterMsg = (Message)this.myPair.getMasterCatalog().findMessage(locMsg.getMessageId());
         }

         this.setPanelsForCatalog();
         if (masterMsg.getDateLastChanged() != null) {
            this.myMasterSimpleLastUpdated.setText((new Date(Long.parseLong(masterMsg.getDateLastChanged()))).toString());
         } else {
            this.myMasterSimpleLastUpdated.setText((new Date()).toString());
         }

         this.myMasterSimpleMessageId.setText(masterMsg.getMessageId());
         if (masterMsg.getMessageBody() != null) {
            this.myMasterSimpleMessageBodyText.setText(masterMsg.getMessageBody().getCdata());
         } else {
            this.myMasterSimpleMessageBodyText.setText("");
         }

         if (locMsg == null) {
            this.myLocaleSimpleLastUpdated.setText((new Date()).toString());
            this.myLocaleSimpleMessageId.setText(masterMsg.getMessageId());
            this.setAddChangeMode("Add");
         } else {
            if (locMsg.getDateLastChanged() != null) {
               this.myLocaleSimpleLastUpdated.setText((new Date(Long.parseLong(locMsg.getDateLastChanged()))).toString());
            } else {
               this.myLocaleSimpleLastUpdated.setText("");
            }

            this.myLocaleSimpleMessageId.setText(locMsg.getMessageId());
            if (locMsg.getMessageBody() != null) {
               this.myLocaleSimpleMessageBodyText.setText(locMsg.getMessageBody().getCdata());
            } else {
               this.myLocaleSimpleMessageBodyText.setText("");
            }

            if (locMsg.getComment() != null) {
               this.myLocaleSimpleComment.setText(locMsg.getComment());
            } else {
               this.myLocaleSimpleComment.setText("");
            }

            this.setAddChangeMode("Change");
         }

         this.setLocaleSimpleFieldsEnabled(true);
         this.pack();
         this.repaint();
         this.myPair.showMessages(masterMsg, locMsg);
      }

   }

   public void setCatalog(BasicMessageCatalog cat) {
      if (cat instanceof MessageCatalog) {
         this.setMasterCatalog((MessageCatalog)cat, ((MessageCatalog)cat).getPath());
      } else {
         this.setLocaleCatalog((LocaleMessageCatalog)cat, ((LocaleMessageCatalog)cat).getPath());
      }

   }

   public void setCatalog(BasicMessageCatalog cat, String fileName) {
      if (cat instanceof MessageCatalog) {
         this.setMasterCatalog((MessageCatalog)cat, fileName);
      } else {
         this.setLocaleCatalog((LocaleMessageCatalog)cat, fileName);
      }

   }

   public LogMessage getMasterLogMessage(String id) {
      return (LogMessage)this.myPair.getMasterCatalog().findMessage(id);
   }

   private void setMasterCatalog(MessageCatalog cat, String fileName) {
      this.myPair.setMasterCatalog(cat, fileName);
      this.mySourceTreeRootField.setText(fileName);
      this.myI18nPackageLabel.setText(cat.get("i18n_package"));
      this.myL10nPackageLabel.setText(cat.get("l10n_package"));
      this.mySubsystemLabel.setText(cat.get("subsystem"));
      this.myVersionLabel.setText(cat.get("version"));
      this.myBaseidLabel.setText(cat.get("baseid"));
      this.myEndidLabel.setText(cat.get("endid"));
      this.myPrefixLabel.setText(cat.get("prefix"));
      this.setPanelsForCatalog();
      this.setLocaleCatalogPath();
      this.pack();
   }

   private void setLocaleCatalog(LocaleMessageCatalog cat, String fileName) {
      this.myPair.setLocaleCatalog(cat);
      this.myPair.getLocaleCatalog().setPath(fileName);
      if (cat.getMasterCatalog() == null) {
         cat.setMasterCatalog(this.myPair.getMasterCatalog());
      }

      this.setLocaleForLocaleCatalog(cat);
      this.myLocaleCatalogField.setText(fileName);
      this.setCatalog(cat.getMasterCatalog());
      this.setupForEndEdit();
   }

   private void setLocaleForLocaleCatalog(LocaleMessageCatalog cat) {
      if (cat.getLocale() == null) {
         int idx = this.myWhichLocale.getSelectedIndex();
         MessageLocale locale = this.myAvailableLocales[idx];
         cat.setLocale(locale.getLocale());
      } else {
         this.myWhichLocale.setSelectedIndex(this.findAvailableLocaleIndex(new MessageLocale(cat.getLocale())));
      }

   }

   private int findAvailableLocaleIndex(MessageLocale loc) {
      for(int i = 0; i < this.myAvailableLocales.length; ++i) {
         if (loc.equals(this.myAvailableLocales[i])) {
            return i;
         }
      }

      return -1;
   }

   private void clearCatalogInfo() {
      this.myI18nPackageLabel.setText("");
      this.myL10nPackageLabel.setText("");
      this.mySubsystemLabel.setText("");
      this.myVersionLabel.setText("");
      this.myBaseidLabel.setText("");
      this.myEndidLabel.setText("");
      this.myPrefixLabel.setText("");
   }

   public boolean writeCatalogToDisk() {
      boolean success = false;
      if (this.myPair.getLocaleCatalog() != null) {
         String localePath = this.myPair.getLocaleCatalog().getPath();
         File parentPath = (new File(localePath)).getParentFile();
         if (parentPath != null && !parentPath.mkdirs() && !parentPath.exists()) {
            JOptionPane.showMessageDialog(this, this.fmt.noDir(parentPath.toString()), this.fmt.tagError(), 0);
            return false;
         }

         try {
            if (this.myInpArgs.getEncoding() != null) {
               this.myPair.getLocaleCatalog().setOutputEncoding(this.myInpArgs.getEncoding());
            }

            LocaleCatalogWriter.writeFormattedCatalog(localePath, this.myPair.getLocaleCatalog());
            success = true;
         } catch (IOException var5) {
            JOptionPane.showMessageDialog(this, this.fmt.msgBadWrite(this.myPair.getLocaleCatalog().getPath()), this.fmt.tagError(), 0);
         }
      } else {
         JOptionPane.showMessageDialog(this, this.fmt.msgChooseCat(), this.fmt.tagError(), 0);
      }

      return success;
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
         str = "Update";
      } else {
         str = "Add";
      }

      return str;
   }

   private void setPanelsForCatalog() {
      if (this.myPair.getMasterCatalog() == null) {
         this.myLogMsgEditingPanel.setVisible(true);
         this.mySimpleMsgEditingPanel.setVisible(false);
         this.myWhichLocale.setEnabled(false);
         this.myDoThisLocale.setEnabled(false);
         this.myPair.setLocaleCatalog((LocaleMessageCatalog)null);
      } else {
         this.myWhichLocale.setEnabled(true);
         this.myDoThisLocale.setEnabled(true);
         int type = this.myPair.getMasterCatalog().getCatType();
         if (type != 2 && (type != 0 || this.myPair.getMasterCatalog().get("baseid") == null)) {
            if (type != 1 && (type != 0 || this.myPair.getMasterCatalog().get("baseid") != null)) {
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
      if (ev != null) {
         Object src = ev.getSource();
         if (src instanceof MessageViewer) {
            for(int i = 0; i < this.myPairs.size(); ++i) {
               this.myPair.removeViewer((MessageViewer)src);
            }
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

   public void mouseEntered(MouseEvent ev) {
   }

   public void mouseExited(MouseEvent ev) {
   }

   public void mousePressed(MouseEvent ev) {
   }

   public void mouseReleased(MouseEvent ev) {
   }

   public void mouseClicked(MouseEvent ev) {
      if ((ev.getModifiers() & 4) > 0) {
         Component src = (Component)ev.getSource();
         this.myPopup = new CopyCutPasteMenu(this);
         if (src instanceof JTextComponent) {
            this.myPopup.setEnabledItems((JTextComponent)src);
            this.myPopup.setSource((JTextComponent)src);
         }

         this.myPopup.show(src, ev.getX(), ev.getY());
      }

   }
}
