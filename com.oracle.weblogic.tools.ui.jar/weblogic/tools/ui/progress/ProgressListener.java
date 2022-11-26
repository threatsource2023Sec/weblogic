package weblogic.tools.ui.progress;

public interface ProgressListener {
   void updateProgress(ProgressEvent var1);

   void update(String var1);

   void update(String var1, int var2);

   void setProgressProducer(ProgressProducer var1);
}
