package weblogic.i18ntools.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import weblogic.i18n.tools.LogMessage;
import weblogic.i18n.tools.Message;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.MessageFinder;

public final class MessageSearchDialog extends JDialog implements ActionListener {
   private static final boolean debug = false;
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   MessageCatalog myCatalog = null;
   MessageEditor myParent = null;
   GUIClientTextField myMessageId = null;
   GUIClientTextField myMethod = null;
   GUIClientTextField myMessageText = null;

   public MessageSearchDialog(MessageEditor parent, String title, MessageCatalog msgCat) {
      super(parent, title);
      this.myCatalog = msgCat;
      this.myParent = parent;
      Container contPane = this.getContentPane();
      contPane.setLayout(new BorderLayout());
      JPanel fieldsPanel = this.initFieldsPanel();
      contPane.add(fieldsPanel, "North");
      JPanel buttonPanel = this.initButtonPanel();
      contPane.add(buttonPanel, "South");
      this.pack();
      this.centerWindow(this);
      this.setVisible(true);
   }

   private void centerWindow(Window win) {
      Point winLoc = new Point();
      Dimension parentDim = this.myParent.getSize();
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

   private JPanel initFieldsPanel() {
      JPanel fieldsPanel = new JPanel();
      fieldsPanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 1.0;
      gbc.anchor = 10;
      gbc.gridwidth = 1;
      gbc.fill = 2;
      gbc.insets = new Insets(3, 10, 3, 10);
      gbc.gridx = gbc.gridy = 0;
      JLabel messageIdLabel = new JLabel(this.fmt.labelMessageId());
      fieldsPanel.add(messageIdLabel, gbc);
      ++gbc.gridx;
      this.myMessageId = new GUIClientTextField("", 20);
      fieldsPanel.add(this.myMessageId, gbc);
      JLabel messageTextLabel;
      if (this.myCatalog.getCatType() == 2) {
         messageTextLabel = new JLabel(this.fmt.labelMethod());
         gbc.gridx = 0;
         ++gbc.gridy;
         fieldsPanel.add(messageTextLabel, gbc);
         ++gbc.gridx;
         this.myMethod = new GUIClientTextField("", 20);
         fieldsPanel.add(this.myMethod, gbc);
      }

      messageTextLabel = new JLabel(this.fmt.labelMsgSearch());
      gbc.gridx = 0;
      ++gbc.gridy;
      fieldsPanel.add(messageTextLabel, gbc);
      ++gbc.gridx;
      this.myMessageText = new GUIClientTextField("", 20);
      fieldsPanel.add(this.myMessageText, gbc);
      return fieldsPanel;
   }

   private JPanel initButtonPanel() {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JButton cancelButton = new JButton(this.fmt.buttonCancel());
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MessageSearchDialog.this.setVisible(false);
         }
      });
      JButton findFirstButton = new JButton(this.fmt.buttonFindFirst());
      findFirstButton.addActionListener(this);
      findFirstButton.setActionCommand("Findfirst");
      JButton findSubsequentButton = new JButton(this.fmt.buttonFindNext());
      findSubsequentButton.addActionListener(this);
      findSubsequentButton.setActionCommand("Findnext");
      this.getRootPane().setDefaultButton(findFirstButton);
      buttonPanel.add(Box.createHorizontalGlue());
      buttonPanel.add(findFirstButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
      buttonPanel.add(findSubsequentButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
      buttonPanel.add(cancelButton);
      buttonPanel.add(Box.createHorizontalGlue());
      return buttonPanel;
   }

   public void actionPerformed(ActionEvent ev) {
      String cmd = ev.getActionCommand();
      if (this.myCatalog != null) {
         boolean first;
         if (cmd.equals("Findfirst")) {
            first = true;
         } else {
            first = false;
         }

         if (this.myCatalog.getCatType() == 2) {
            LogMessage msg = MessageFinder.findLogMessage(this.myCatalog, first, this.myMessageId.getText(), this.myMethod.getText(), this.myMessageText.getText());
            if (msg != null) {
               this.myParent.setLogMessageFields(msg, false);
            } else {
               JOptionPane.showMessageDialog(this.myParent, this.fmt.msgNoMsg(), this.fmt.tagWarn(), 2);
            }
         } else if (this.myCatalog.getCatType() == 1) {
            Message msg = MessageFinder.findSimpleMessage(this.myCatalog, first, this.myMessageId.getText(), this.myMessageText.getText());
            if (msg != null) {
               this.myParent.setSimpleMessageFields(msg, false);
            } else {
               JOptionPane.showMessageDialog(this.myParent, this.fmt.msgNoMsg(), this.fmt.tagWarn(), 2);
            }
         }
      } else {
         JOptionPane.showMessageDialog(this.myParent, this.fmt.msgNoCat(), this.fmt.tagError(), 0);
      }

   }
}
