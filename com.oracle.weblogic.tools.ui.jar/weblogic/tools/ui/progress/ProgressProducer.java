package weblogic.tools.ui.progress;

public interface ProgressProducer {
   void setProgressListener(ProgressListener var1);

   void cancelExecution();
}
