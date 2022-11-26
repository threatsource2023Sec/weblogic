package weblogic.tools.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

public class UIFactory {
   public static int STD_DIALOG_WIDTH = 300;
   public static int STD_DIALOG_HEIGHT = 125;
   private static MarathonTextFormatter m_fmt = new MarathonTextFormatter();
   private static Action copyAction;
   private static Action pasteAction;
   private static Action cutAction;
   private static Class clazz = (new UIFactory()).getClass();
   private static ImageIcon emptyIcon = getIcon("/weblogic/tools/ui/images/emptyIcon.gif");
   private static Action clearAction = new AbstractAction("Clear") {
      public void actionPerformed(ActionEvent e) {
         Object source = e.getSource();
         Component comp = null;
         if (source instanceof Component) {
            comp = (Component)source;

            while(comp != null) {
               if (comp instanceof JTextComponent) {
                  ((JTextComponent)comp).setText("");
                  break;
               }

               if (comp instanceof JPopupMenu) {
                  comp = ((JPopupMenu)comp).getInvoker();
               } else {
                  comp = ((Component)comp).getParent();
               }
            }
         }

      }
   };
   private static Action[] editableComponentActions;
   private static Action[] nonEditableComponentActions;
   private static int comboBoxMinWidth;
   private static Image m_busyImage;
   private static ImageIcon m_busyIcon;
   String[] m_funBusyImages = new String[]{"/weblogic/marathon/resources/images/animations/itchy.gif", "/weblogic/marathon/resources/images/animations/hellokty.gif", "/weblogic/marathon/resources/images/animations/womanwrench.gif", "/weblogic/marathon/resources/images/animations/stimpy.gif"};
   private boolean m_funImages = true;
   public static final int STD_COMP_BUFFER_VALUE = 11;

   public static ImageIcon getIcon(String imagePath) {
      ImageIcon icon = null;
      if (imagePath != null) {
         URL url = clazz.getResource(imagePath);
         if (url != null) {
            icon = new ImageIcon(url);
         }
      }

      if (icon == null) {
         icon = emptyIcon;
      }

      return icon;
   }

   public static JFrame getFrame(String title) {
      JFrame result = new JFrame(title);
      result.setSize(500, 300);
      return result;
   }

   public static Action getCutAction() {
      return cutAction;
   }

   public static Action getCopyAction() {
      return copyAction;
   }

   public static Action getPasteAction() {
      return pasteAction;
   }

   public static JTextArea getTextArea(boolean editable) {
      JTextArea result = new JTextArea();
      result.setEditable(editable);
      return (JTextArea)setupListeners(result, editable);
   }

   private static JTextComponent setupListeners(JTextComponent result, boolean editable) {
      return result;
   }

   public static JTextArea getTextArea() {
      return getTextArea(true);
   }

   public static JTextField getTextField() {
      return getTextField(true);
   }

   public static JTextField getTextField(int width) {
      return getTextField(true, width);
   }

   public static JTextField getTextField(boolean editable, int width) {
      return (JTextField)setupListeners(new JTextField(width), editable);
   }

   public static JTextField getTextField(boolean editable) {
      return (JTextField)setupListeners(new JTextField(), editable);
   }

   public static JTextField getPasswordField() {
      return new JPasswordField();
   }

   public static NumberBox getIntegerField() {
      return new NumberBox();
   }

   public static JRadioButton getRadioButton() {
      return new JRadioButton();
   }

   public static JRadioButton getRadioButton(String label) {
      return new JRadioButton(label);
   }

   public static JCheckBox getCheckBox() {
      return new JCheckBox();
   }

   public static JToggleButton getToggleButton(String s) {
      return new JToggleButton(s);
   }

   public static JButton getButton(String s) {
      return new JButton(s);
   }

   public static JButton getChooser() {
      return new JButton(m_fmt.getBrowseEllipsis());
   }

   public static JTextField getTextField(String s) {
      JTextField result = getTextField(true);
      result.setText(s);
      result = (JTextField)setupListeners(result, true);
      return result;
   }

   public static JLabel getLabel(String t) {
      JLabel result = new JLabel(t);
      return result;
   }

   public static JLabel getMandatoryLabel(String t) {
      JLabel result = new JLabel(formatMandatory(t));
      return result;
   }

   public static JLabel getMandatoryLabel(String t, int where) {
      JLabel result = new JLabel(formatMandatory(t), where);
      return result;
   }

   public static JLabel getLabel(String t, int where) {
      JLabel result = new JLabel(t, where);
      return result;
   }

   public static JCheckBox getCheckBox(String t) {
      JCheckBox result = new JCheckBox(t);
      return result;
   }

   public static JComboBox getSortedComboBox() {
      JComboBox result = new SortedComboBox();
      Component editor = result.getEditor().getEditorComponent();
      if (editor instanceof JTextField) {
         ((JTextField)editor).setColumns(40);
      }

      return result;
   }

   public static JComboBox getComboBox() {
      return getComboBox(new String[0]);
   }

   public static JComboBox getComboBox(Object[] model) {
      JComboBox result = new JComboBox(model) {
         public Dimension getPreferredSize() {
            Dimension pref = super.getPreferredSize();
            Dimension min = super.getMinimumSize();
            pref.width = Math.max(pref.width, min.width);
            pref.height = Math.max(pref.height, min.height);
            return pref;
         }
      };
      Component editor = result.getEditor().getEditorComponent();
      if (editor instanceof JTextField) {
         ((JTextField)editor).setColumns(40);
      }

      Dimension d = result.getMinimumSize();
      d.width = Math.max(d.width, getComboBoxMinWidth(result));
      result.setMinimumSize(d);
      result.setEditable(true);
      return result;
   }

   private static synchronized int getComboBoxMinWidth(JComboBox c) {
      if (comboBoxMinWidth != -1) {
         return comboBoxMinWidth;
      } else {
         Font f = c.getFont();
         FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(f);
         return comboBoxMinWidth = fm.stringWidth("abcdefghij");
      }
   }

   public static JList getList() {
      return new JList(new DefaultListModel());
   }

   public static JTable getTable() {
      return new JTable();
   }

   public static Image getBusyImage() {
      if (null == m_busyImage) {
         m_busyImage = Util.loadImage("/weblogic/marathon/resources/images/animations/validate_animation.gif");
      }

      return m_busyImage;
   }

   public static ImageIcon getBusyIcon() {
      if (null == m_busyIcon) {
         m_busyIcon = new ImageIcon(getBusyImage());
      }

      return m_busyIcon;
   }

   public static JDialog getBusyDialog(Component parent, String message, String title) {
      JDialog result = null;
      JOptionPane optionPane = new JOptionPane(message, 1, -1, getBusyIcon());
      result = optionPane.createDialog(parent, title);
      result.setModal(false);
      return result;
   }

   public static GridBagConstraints getBasicGridBagConstraints() {
      GridBagConstraints result = new GridBagConstraints();
      result.gridx = result.gridy = 0;
      result.fill = 1;
      result.weightx = 1.0;
      return result;
   }

   public static GridBagConstraints getSimpleGridBagConstraints() {
      GridBagConstraints result = new GridBagConstraints();
      int n = 5;
      result.insets = new Insets(n, n, n, n);
      result.gridx = 0;
      result.gridy = 0;
      return result;
   }

   public static GridBagConstraints getGridBagConstraints() {
      GridBagConstraints result = new GridBagConstraints();
      int n = 0;
      result.insets = new Insets(n, n, n, n);
      result.anchor = 18;
      result.gridx = 0;
      result.gridy = 0;
      result.fill = 1;
      result.weightx = 1.0;
      result.gridwidth = 0;
      return result;
   }

   public static String formatMandatory(String s) {
      return "* " + s;
   }

   private static String formatNormal(String s) {
      return s;
   }

   private static void ppp(String s) {
      System.out.println("[UIFactory] " + s);
   }

   static {
      ImageIcon icon = getIcon("/weblogic/marathon/resources/images/toolbar/copy.gif");
      copyAction = new DefaultEditorKit.CopyAction();
      copyAction.putValue("Name", m_fmt.getCopy());
      copyAction.putValue("SmallIcon", icon);
      icon = getIcon("/weblogic/marathon/resources/images/toolbar/cut.gif");
      cutAction = new DefaultEditorKit.CutAction();
      cutAction.putValue("Name", m_fmt.getCut());
      cutAction.putValue("SmallIcon", icon);
      icon = getIcon("/weblogic/marathon/resources/images/toolbar/paste.gif");
      pasteAction = new DefaultEditorKit.PasteAction();
      pasteAction.putValue("Name", m_fmt.getPaste());
      pasteAction.putValue("SmallIcon", icon);
      Action[] temp = new Action[]{cutAction, copyAction, pasteAction};
      editableComponentActions = temp;
      Action[] temp2 = new Action[]{copyAction, clearAction};
      nonEditableComponentActions = temp2;
      comboBoxMinWidth = -1;
      m_busyImage = null;
      m_busyIcon = null;
   }
}
