package weblogic.tools.ui.progress;

import java.util.Arrays;
import java.util.Vector;
import weblogic.utils.Debug;

public class ProgressDispatcher implements ProgressListener {
   protected ProgressListener[] listeners;
   protected ProgressEvent event = new ProgressEvent();
   private ProgressProducer progressProducer;

   public void updateProgress(ProgressEvent event) {
      this.update(event);
   }

   public void update(String progressInfo) {
      this.update(progressInfo, 1);
   }

   public void update(String progressInfo, int level) {
      this.event.setEventInfo(progressInfo, level);
      this.update(this.event);
   }

   public void addProgressListener(ProgressListener listener) {
      Debug.assertion(listener != null);
      Vector temp = new Vector();
      if (this.listeners != null) {
         temp.addAll(Arrays.asList((Object[])this.listeners));
      }

      temp.add(listener);
      this.listeners = new ProgressListener[temp.size()];
      temp.copyInto(this.listeners);
   }

   private void update(ProgressEvent currentEvent) {
      if (null != this.listeners) {
         for(int i = 0; i < this.listeners.length; ++i) {
            this.listeners[i].updateProgress(currentEvent);
         }
      }

   }

   public void setProgressProducer(ProgressProducer producer) {
      this.progressProducer = producer;
   }
}
