package com.solarmetric.manage;

public class TimeSamplingStatistic extends SimpleStatistic {
   ValueProvider _valProvider;
   TimeSamplingThread _samplingThread;
   long _samplingIntervalMillis = 1000L;

   public TimeSamplingStatistic(String name, String description, String ordinateDescription, ValueProvider valProvider) {
      super(name, description, ordinateDescription);
      this._valProvider = valProvider;
   }

   public TimeSamplingStatistic(String name, String description, String ordinateDescription, int drawStyle, boolean ignoreDuplicates, ValueProvider valProvider) {
      super(name, description, ordinateDescription, drawStyle, ignoreDuplicates);
      this._valProvider = valProvider;
   }

   public boolean addListener(StatisticListener listener) {
      this.startSamplingThread();
      return super.addListener(listener);
   }

   public boolean removeListener(StatisticListener listener) {
      boolean retVal = super.removeListener(listener);
      if (!this.hasListeners()) {
         this.stopSamplingThread();
      }

      return retVal;
   }

   public void clearListeners() {
      super.clearListeners();
      this.stopSamplingThread();
   }

   private void startSamplingThread() {
      if (this._samplingThread == null) {
         this._samplingThread = new TimeSamplingThread();
         this._samplingThread.setDaemon(true);
         this._samplingThread.start();
      }

   }

   private void stopSamplingThread() {
      if (this._samplingThread != null) {
         this._samplingThread.close();
         this._samplingThread = null;
      }

   }

   class TimeSamplingThread extends Thread {
      private boolean _active = true;

      public void close() {
         this._active = false;
      }

      public void run() {
         for(; this._active; TimeSamplingStatistic.this.setValue(TimeSamplingStatistic.this._valProvider.getValue())) {
            try {
               Thread.sleep(TimeSamplingStatistic.this._samplingIntervalMillis);
            } catch (InterruptedException var2) {
            }
         }

      }
   }
}
