package com.solarmetric.manage.jmx.gui.remote.wl81;

import com.solarmetric.ide.ui.swing.XButton;
import com.solarmetric.ide.ui.swing.XDialog;
import com.solarmetric.ide.ui.swing.XLabel;
import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XTextField;
import com.solarmetric.ide.util.Dialogs;
import com.solarmetric.manage.jmx.gui.JMXTree;
import com.solarmetric.manage.jmx.remote.wl81.WL81RemoteMBeanServerFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.management.MBeanServer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class WL81ConnectDialog extends XDialog {
   private static Localizer _loc = Localizer.forPackage(WL81ConnectDialog.class);
   private JTextField hostField;
   private JTextField portField;
   private JButton cancelButton;
   private JButton okButton;
   private Log _log;
   private JMXTree _jmxTree;

   public WL81ConnectDialog(String title, boolean modal, JMXTree jmxTree, Log log) {
      this.setTitle(title);
      this._log = log;
      this._jmxTree = jmxTree;
      JPanel contents = new XPanel();
      contents.setLayout(new BorderLayout());
      JLabel label = new XLabel(_loc.get("connect-dialog-label").getMessage());
      label.setHorizontalAlignment(0);
      label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      contents.add(label, "North");
      JPanel mainPanel = new XPanel();
      mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      mainPanel.setLayout(new BoxLayout(mainPanel, 1));
      JPanel inputs = new XPanel();
      inputs.setLayout(new GridLayout(2, 2));
      label = new XLabel(_loc.get("host-label").getMessage());
      label.setHorizontalAlignment(4);
      label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      inputs.add(label);
      this.hostField = new XTextField(_loc.get("host-default-wl81").getMessage());
      inputs.add(this.hostField);
      label = new XLabel(_loc.get("port-label").getMessage());
      label.setHorizontalAlignment(4);
      label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      inputs.add(label);
      this.portField = new XTextField(_loc.get("port-default-wl81").getMessage());
      inputs.add(this.portField);
      mainPanel.add(Box.createVerticalGlue());
      mainPanel.add(inputs);
      mainPanel.add(Box.createVerticalGlue());
      contents.add(mainPanel, "Center");
      JPanel buttonPanel = new XPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
      buttonPanel.add(Box.createHorizontalGlue());
      this.cancelButton = new XButton(_loc.get("cancel-label").getMessage());
      this.cancelButton.setMnemonic(67);
      buttonPanel.add(this.cancelButton);
      this.okButton = new XButton(_loc.get("ok-label").getMessage());
      buttonPanel.add(this.okButton);
      contents.add(buttonPanel, "South");
      this.setContentPane(contents);
      this.setDefaultCloseOperation(2);
      this.cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            WL81ConnectDialog.this.hide();
            WL81ConnectDialog.this.dispose();
         }
      });
      this.okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               WL81RemoteMBeanServerFactory factory = new WL81RemoteMBeanServerFactory();
               MBeanServer mbServer = factory.createDefaultRemoteMBeanServer(WL81ConnectDialog.this.hostField.getText(), Integer.parseInt(WL81ConnectDialog.this.portField.getText()), WL81ConnectDialog.this._log);
               WL81ConnectDialog.this._jmxTree.add(mbServer, WL81ConnectDialog.this.hostField.getText() + ":" + WL81ConnectDialog.this.portField.getText());
               WL81ConnectDialog.this.hide();
               WL81ConnectDialog.this.dispose();
            } catch (Exception var4) {
               Dialogs.showMessageDialog(WL81ConnectDialog.this, WL81ConnectDialog._loc.get("cant-connect", "default", WL81ConnectDialog.this.hostField.getText(), WL81ConnectDialog.this.portField.getText()).getMessage(), WL81ConnectDialog._loc.get("cant-connect-short").getMessage(), 0);
               WL81ConnectDialog.this._log.error(WL81ConnectDialog._loc.get("cant-connect", "default", WL81ConnectDialog.this.hostField.getText(), WL81ConnectDialog.this.portField.getText()).getMessage(), var4);
            }

         }
      });
      this.getRootPane().setDefaultButton(this.okButton);
   }
}
