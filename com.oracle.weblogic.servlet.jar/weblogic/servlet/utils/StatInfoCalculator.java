package weblogic.servlet.utils;

public class StatInfoCalculator {
   protected final OutputStreamStatInfo statOrigOut;
   protected final OutputStreamStatInfo statGzipOut;

   public StatInfoCalculator(StatOutputStream statOrigOut, StatOutputStream statGzipOut) {
      this.statOrigOut = statOrigOut;
      this.statGzipOut = statGzipOut;
   }

   public float getCpuTimeInMilliSec() {
      return this.convertToMilliSec(this.statGzipOut.getCpuTime());
   }

   public long getOrigContentLength() {
      return this.statGzipOut.getContentLength();
   }

   public long getGzipedContentLength() {
      return this.statOrigOut.getContentLength();
   }

   public float getCompressionRatio() {
      return (float)this.getGzipedContentLength() / (float)this.getOrigContentLength();
   }

   private float convertToMilliSec(long interval) {
      return (float)interval / 1000.0F / 1000.0F;
   }
}
