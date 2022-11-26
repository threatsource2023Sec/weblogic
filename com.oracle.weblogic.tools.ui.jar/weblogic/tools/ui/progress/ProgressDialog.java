package weblogic.tools.ui.progress;

import java.awt.Container;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ProgressDialog extends JDialog implements ProgressListener {
   private JTextArea textArea = new JTextArea();
   private ProgressEvent event = new ProgressEvent();
   private ProgressProducer progressProducer;

   public ProgressDialog(Frame owner, String title, boolean modal) {
      super(owner, title, modal);
      Container container = this.getContentPane();
      this.textArea.setEditable(false);
      JPanel panel = new JPanel();
      panel.add(new JScrollPane(this.textArea));
      panel.add(new JButton("OK"));
      container.add(panel);
   }

   public void updateProgress(ProgressEvent e) {
      this.textArea.append(e.getMessage() + "\n");
   }

   public void update(String progress) {
      this.event.setEventInfo(progress, 1);
      this.updateProgress(this.event);
   }

   public void update(String progress, int level) {
      this.event.setEventInfo(progress, level);
      this.updateProgress(this.event);
   }

   public void setProgressProducer(ProgressProducer producer) {
      this.progressProducer = producer;
   }
}
