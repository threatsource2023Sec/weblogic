package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.swing.XButton;
import com.solarmetric.ide.ui.swing.XDialog;
import com.solarmetric.ide.ui.swing.XLabel;
import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XTable;
import com.solarmetric.ide.util.Dialogs;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class OperationExecuteDialog extends XDialog {
   private static Localizer _loc = Localizer.forPackage(OperationExecuteDialog.class);
   private MBeanServer _server;
   private ObjectInstance _instance;
   private MBeanOperationInfo _opInfo;
   private OperationExecuteTableModel _opexModel;
   private Log _log;

   public OperationExecuteDialog(String title, boolean modal, MBeanServer server, ObjectInstance instance, MBeanOperationInfo opInfo, Log log) {
      this.setTitle(title);
      this._server = server;
      this._instance = instance;
      this._opInfo = opInfo;
      this._log = log;
      JPanel contents = new XPanel();
      contents.setLayout(new BorderLayout());
      JLabel label = new XLabel(this._opInfo.getReturnType() + " " + this._opInfo.getName() + " (" + this._opInfo.getDescription() + ")");
      label.setHorizontalAlignment(0);
      label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      contents.add(label, "North");
      JPanel mainPanel = new XPanel();
      mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      mainPanel.setLayout(new GridLayout(1, 1));
      mainPanel.setPreferredSize(new Dimension(300, 100));
      this._opexModel = new OperationExecuteTableModel(this._opInfo);
      JTable argTable = new XTable(this._opexModel);
      mainPanel.add(new XScrollPane(argTable));
      contents.add(mainPanel, "Center");
      JPanel buttonPanel = new XPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
      buttonPanel.add(Box.createHorizontalGlue());
      JButton cancelButton = new XButton(_loc.get("cancel-label").getMessage());
      buttonPanel.add(cancelButton);
      JButton okButton = new XButton(_loc.get("exec-label").getMessage());
      buttonPanel.add(okButton);
      contents.add(buttonPanel, "South");
      this.setContentPane(contents);
      ActionListener cancel = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OperationExecuteDialog.this.hide();
            OperationExecuteDialog.this.dispose();
         }
      };
      cancelButton.addActionListener(cancel);
      this.setDefaultCloseOperation(2);
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               String[] signature = new String[OperationExecuteDialog.this._opInfo.getSignature().length];

               for(int i = 0; i < OperationExecuteDialog.this._opInfo.getSignature().length; ++i) {
                  signature[i] = OperationExecuteDialog.this._opInfo.getSignature()[i].getType();
               }

               Object retVal = OperationExecuteDialog.this._server.invoke(OperationExecuteDialog.this._instance.getObjectName(), OperationExecuteDialog.this._opInfo.getName(), OperationExecuteDialog.this._opexModel.getArgs(), signature);
               OperationExecuteDialog.this.hide();
               OperationExecuteDialog.this.dispose();
               if (retVal == null) {
                  Dialogs.showMessageDialog(OperationExecuteDialog.this, OperationExecuteDialog._loc.get("operation-success").getMessage(), OperationExecuteDialog._loc.get("operation-success-short").getMessage(), 1);
               } else {
                  JDialog dialog = new OperationReturnDialog(retVal);
                  dialog.pack();
                  dialog.setVisible(true);
               }
            } catch (Exception var5) {
               Dialogs.showMessageDialog(OperationExecuteDialog.this, OperationExecuteDialog._loc.get("operation-failed", var5.getMessage()).getMessage(), OperationExecuteDialog._loc.get("operation-failed-short").getMessage(), 0);
               OperationExecuteDialog.this._log.error(OperationExecuteDialog._loc.get("operation-failed", var5.getMessage()), var5);
            }

         }
      });
      this.getRootPane().setDefaultButton(okButton);
   }
}
