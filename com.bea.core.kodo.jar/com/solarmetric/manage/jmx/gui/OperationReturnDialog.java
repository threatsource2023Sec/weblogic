package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.swing.XButton;
import com.solarmetric.ide.ui.swing.XDialog;
import com.solarmetric.ide.ui.swing.XLabel;
import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.apache.openjpa.lib.util.Localizer;

public class OperationReturnDialog extends XDialog {
   private static Localizer _loc = Localizer.forPackage(OperationReturnDialog.class);

   public OperationReturnDialog(Object retVal) {
      this.setTitle(_loc.get("operation-success-short").getMessage());
      JPanel contents = new XPanel();
      contents.setLayout(new BorderLayout());
      JLabel label = new XLabel(_loc.get("operation-success").getMessage());
      label.setHorizontalAlignment(0);
      label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      contents.add(label, "North");
      JPanel mainPanel = new XPanel();
      mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      mainPanel.setLayout(new GridLayout(1, 1));
      mainPanel.setPreferredSize(new Dimension(300, 100));
      JTextArea retValArea = new XTextArea(retVal.toString());
      mainPanel.add(new XScrollPane(retValArea));
      contents.add(mainPanel, "Center");
      JPanel buttonPanel = new XPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
      buttonPanel.add(Box.createHorizontalGlue());
      JButton okButton = new XButton(_loc.get("ok-label").getMessage());
      buttonPanel.add(okButton);
      contents.add(buttonPanel, "South");
      this.setContentPane(contents);
      this.setDefaultCloseOperation(2);
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OperationReturnDialog.this.hide();
            OperationReturnDialog.this.dispose();
         }
      });
      this.getRootPane().setDefaultButton(okButton);
   }
}
