package weblogic.i18ntools.gui;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

public final class LocalizerMenuBar extends JMenuBar {
   private static final boolean debug = false;
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   private MessageLocalizer myLocalizer;
   private JMenu myFileMenu;

   public LocalizerMenuBar(MessageLocalizer editor) {
      this.myLocalizer = editor;
      this.setAlignmentX(0.0F);
      this.setAlignmentY(0.0F);
      JMenu fileMenu = this.add(new JMenu(this.fmt.menuFile()));
      fileMenu.setMnemonic('F');
      JMenuItem menuItem = new JMenuItem(this.fmt.menuSave());
      fileMenu.add(menuItem);
      menuItem.setHorizontalTextPosition(4);
      menuItem.setMnemonic('S');
      menuItem.setToolTipText(this.fmt.tipSave());
      menuItem.setActionCommand("WriteCatalog");
      menuItem.addActionListener(this.myLocalizer);
      fileMenu.add(new JSeparator());
      menuItem = new JMenuItem(this.fmt.menuExit());
      fileMenu.add(menuItem);
      menuItem.setMnemonic('x');
      menuItem.setActionCommand("Exit");
      menuItem.addActionListener(this.myLocalizer);
      JMenu editMenu = this.add(new JMenu(this.fmt.menuEdit()));
      editMenu.setMnemonic('E');
      menuItem = new JMenuItem(this.fmt.menuSearch());
      editMenu.add(menuItem);
      setEllipsis(menuItem);
      menuItem.setMnemonic('s');
      menuItem.setActionCommand("Search");
      menuItem.addActionListener(this.myLocalizer);
      JMenu viewMenu = this.add(new JMenu(this.fmt.menuView()));
      viewMenu.setMnemonic('V');
      menuItem = new JMenuItem(this.fmt.menuAllMsgs());
      viewMenu.add(menuItem);
      menuItem.setActionCommand("View");
      menuItem.addActionListener(this.myLocalizer);
      JMenu optionMenu = this.add(new JMenu(this.fmt.menuOptions()));
      optionMenu.setMnemonic('O');
      ButtonGroup displayTextGroup = new ButtonGroup();
      JRadioButtonMenuItem checkMenuItem = new JRadioButtonMenuItem(this.fmt.buttonMasterText());
      checkMenuItem.setSelected(true);
      optionMenu.add(checkMenuItem);
      displayTextGroup.add(checkMenuItem);
      checkMenuItem.setActionCommand("DisplayMasterText");
      checkMenuItem.addActionListener(this.myLocalizer);
      checkMenuItem.setSelected(true);
      checkMenuItem = new JRadioButtonMenuItem(this.fmt.buttonLeaveBlank());
      displayTextGroup.add(checkMenuItem);
      optionMenu.add(checkMenuItem);
      checkMenuItem.setActionCommand("LeaveFieldsClear");
      checkMenuItem.addActionListener(this.myLocalizer);
      checkMenuItem.setSelected(false);
      optionMenu.add(new JSeparator());
      ButtonGroup fontGroup = new ButtonGroup();
      checkMenuItem = new JRadioButtonMenuItem(this.fmt.menuVarFont());
      checkMenuItem.setSelected(true);
      optionMenu.add(checkMenuItem);
      fontGroup.add(checkMenuItem);
      checkMenuItem.setActionCommand("VariableWidthFont");
      checkMenuItem.addActionListener(this.myLocalizer);
      checkMenuItem = new JRadioButtonMenuItem(this.fmt.menuFixedFont());
      fontGroup.add(checkMenuItem);
      optionMenu.add(checkMenuItem);
      checkMenuItem.setActionCommand("FixedWidthFont");
      checkMenuItem.addActionListener(this.myLocalizer);
      this.validate();
      this.setMinimumSize(this.getPreferredSize());
   }

   private static void setEllipsis(JMenuItem mnuItem) {
      mnuItem.setText(mnuItem.getText() + "...");
   }
}
