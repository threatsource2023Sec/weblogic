package oracle.jrockit.jfr;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class RecordingOptionsImpl implements RecordingOptions {
   private String destination;
   private Date startTime;
   private long duration;
   private long maxSize;
   private long maxAge;
   private boolean destinationCompressed;
   private boolean toDisk;

   public RecordingOptionsImpl(String destination, boolean destinationCompressed, Date startTime, long duration, long maxSize, long maxAge, boolean toDisk) {
      this.destination = destination;
      this.destinationCompressed = destinationCompressed;
      this.startTime = startTime;
      this.duration = TimeUnit.NANOSECONDS.convert(duration, TimeUnit.MILLISECONDS);
      this.maxSize = maxSize;
      this.maxAge = TimeUnit.NANOSECONDS.convert(maxAge, TimeUnit.MILLISECONDS);
      this.toDisk = toDisk;
   }

   public RecordingOptionsImpl(RecordingOptions opts) {
      this.destination = opts.getDestination();
      this.destinationCompressed = opts.isDestinationCompressed();
      this.startTime = opts.getStartTime();
      this.duration = opts.getDuration(TimeUnit.NANOSECONDS);
      this.maxSize = opts.getMaxSize();
      this.maxAge = opts.getMaxAge(TimeUnit.NANOSECONDS);
      this.toDisk = opts.isToDisk();
   }

   public RecordingOptionsImpl() {
      this.destination = null;
      this.destinationCompressed = false;
      this.startTime = null;
      this.duration = 0L;
      this.maxSize = 0L;
      this.maxAge = 0L;
      this.toDisk = true;
   }

   public long getDuration(TimeUnit unit) {
      return unit.convert(this.duration, TimeUnit.NANOSECONDS);
   }

   public long getMaxAge(TimeUnit unit) {
      return unit.convert(this.maxAge, TimeUnit.NANOSECONDS);
   }

   public long getMaxSize() {
      return this.maxSize;
   }

   public Date getStartTime() {
      return this.startTime;
   }

   public String getDestination() {
      return this.destination;
   }

   public boolean isDestinationCompressed() {
      return this.destinationCompressed;
   }

   public boolean isToDisk() {
      return this.toDisk;
   }

   public void setDestination(String destination) {
      this.destination = destination;
   }

   public void setStartTime(Date startTime) {
      this.startTime = startTime;
   }

   public void setDuration(long duration, TimeUnit unit) {
      this.duration = TimeUnit.NANOSECONDS.convert(duration, unit);
   }

   public void setMaxSize(long maxSize) {
      this.maxSize = maxSize;
   }

   public void setMaxAge(long maxAge, TimeUnit unit) {
      this.maxAge = TimeUnit.NANOSECONDS.convert(maxAge, unit);
   }

   public void setDestinationCompressed(boolean destinationCompressed) {
      this.destinationCompressed = destinationCompressed;
   }

   public void setToDisk(boolean toDisk) {
      this.toDisk = toDisk;
   }
}
