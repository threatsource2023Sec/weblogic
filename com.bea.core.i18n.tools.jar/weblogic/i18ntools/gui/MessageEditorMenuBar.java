package weblogic.i18ntools.gui;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

public final class MessageEditorMenuBar extends JMenuBar {
   private static final boolean debug = false;
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   private MessageEditor myEditor;
   private JMenu myFileMenu;

   public MessageEditorMenuBar(MessageEditor editor) {
      this.myEditor = editor;
      this.setAlignmentX(0.0F);
      this.setAlignmentY(0.0F);
      JMenu fileMenu = this.add(new JMenu(this.fmt.menuFile()));
      fileMenu.setMnemonic('F');
      JMenuItem menuItem = new JMenuItem(this.fmt.menuNewCatalog());
      fileMenu.add(menuItem);
      menuItem.setHorizontalTextPosition(4);
      setEllipsis(menuItem);
      menuItem.setMnemonic('N');
      menuItem.setToolTipText(this.fmt.tipNewCatalog());
      menuItem.setActionCommand("NewCatalog");
      menuItem.addActionListener(this.myEditor);
      menuItem = new JMenuItem(this.fmt.menuSave());
      fileMenu.add(menuItem);
      menuItem.setHorizontalTextPosition(4);
      menuItem.setMnemonic('S');
      menuItem.setActionCommand("WriteCatalog");
      menuItem.addActionListener(this.myEditor);
      fileMenu.add(new JSeparator());
      menuItem = new JMenuItem(this.fmt.menuExit());
      fileMenu.add(menuItem);
      menuItem.setMnemonic('x');
      menuItem.setActionCommand("Exit");
      menuItem.addActionListener(this.myEditor);
      JMenu editMenu = this.add(new JMenu(this.fmt.menuEdit()));
      editMenu.setMnemonic('E');
      menuItem = new JMenuItem(this.fmt.menuSearch());
      editMenu.add(menuItem);
      setEllipsis(menuItem);
      menuItem.setMnemonic('s');
      menuItem.setActionCommand("Search");
      menuItem.addActionListener(this.myEditor);
      JMenu viewMenu = this.add(new JMenu(this.fmt.menuView()));
      viewMenu.setMnemonic('V');
      menuItem = new JMenuItem(this.fmt.menuAllMsgs());
      viewMenu.add(menuItem);
      menuItem.setActionCommand("View");
      menuItem.addActionListener(this.myEditor);
      JMenu optionMenu = this.add(new JMenu(this.fmt.menuOptions()));
      optionMenu.setMnemonic('O');
      ButtonGroup fontGroup = new ButtonGroup();
      JRadioButtonMenuItem checkMenuItem = new JRadioButtonMenuItem(this.fmt.menuVarFont());
      checkMenuItem.setSelected(true);
      optionMenu.add(checkMenuItem);
      fontGroup.add(checkMenuItem);
      checkMenuItem.setActionCommand("VariableWidthFont");
      checkMenuItem.addActionListener(this.myEditor);
      checkMenuItem = new JRadioButtonMenuItem(this.fmt.menuFixedFont());
      fontGroup.add(checkMenuItem);
      optionMenu.add(checkMenuItem);
      checkMenuItem.setActionCommand("FixedWidthFont");
      checkMenuItem.addActionListener(this.myEditor);
      this.validate();
      this.setMinimumSize(this.getPreferredSize());
   }

   private static void setEllipsis(JMenuItem mnuItem) {
      mnuItem.setText(mnuItem.getText() + "...");
   }
}
