package weblogic.messaging.kernel;

public interface Threshold {
   long getHighThreshold();

   long getLowThreshold();

   long getThresholdTime();

   void setThresholds(long var1, long var3);

   void addListener(ThresholdListener var1);

   void removeListener(ThresholdListener var1);
}
