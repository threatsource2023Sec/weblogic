package com.oracle.jrockit.jfr.client;

import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.management.FlightRecordingMBean;
import com.oracle.jrockit.jfr.management.NoSuchRecordingException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public class FlightRecordingClient implements FlightRecordingMBean {
   protected final FlightRecorderClient client;
   protected final FlightRecordingMBean mbean;
   protected final ObjectName objectName;

   FlightRecordingClient(FlightRecorderClient client, FlightRecordingMBean mbean, ObjectName objectName) {
      this.client = client;
      this.objectName = objectName;
      this.mbean = mbean;
   }

   protected FlightRecordingClient(FlightRecordingClient other) {
      this(other.client, other.mbean, other.objectName);
   }

   public ObjectName getObjectName() {
      return this.objectName;
   }

   public boolean isDestinationCompressed() {
      return this.mbean.isDestinationCompressed();
   }

   public void setDestinationCompressed(boolean compress) {
      this.mbean.setDestinationCompressed(compress);
   }

   public void copyTo(String path, boolean compress) throws IllegalStateException, IOException {
      this.mbean.copyTo(path, compress);
   }

   public void close() {
      this.mbean.close();
   }

   public void closeStream(long id) throws IOException, IllegalArgumentException {
      this.mbean.closeStream(id);
   }

   public String getDestination() {
      return this.mbean.getDestination();
   }

   public long getDuration() {
      return this.mbean.getDuration();
   }

   public void addEventDefaults(List defaults) throws OpenDataException {
      this.mbean.addEventDefaults(defaults);
   }

   public List getEventDefaults() throws OpenDataException {
      return this.mbean.getEventDefaults();
   }

   public void setEventDefaults(List defaults) throws OpenDataException {
      this.mbean.setEventDefaults(defaults);
   }

   public List getEventSettings() throws OpenDataException {
      return this.mbean.getEventSettings();
   }

   public long getId() {
      return this.mbean.getId();
   }

   public long getMaxAge() {
      return this.mbean.getMaxAge();
   }

   public long getMaxSize() {
      return this.mbean.getMaxSize();
   }

   public String getName() {
      return this.mbean.getName();
   }

   public long getDataSize() {
      return this.mbean.getDataSize();
   }

   public CompositeData getOptions() throws OpenDataException {
      return this.mbean.getOptions();
   }

   public long getPeriod(int id) throws NoSuchEventException {
      return this.mbean.getPeriod(id);
   }

   public Date getStartTime() {
      return this.mbean.getStartTime();
   }

   public long getThreshold(int id) throws NoSuchEventException {
      return this.mbean.getThreshold(id);
   }

   public boolean isEventEnabled(int id) throws NoSuchEventException {
      return this.mbean.isEventEnabled(id);
   }

   public boolean isRunning() {
      return this.mbean.isRunning();
   }

   public boolean isStackTraceEnabled(int id) throws NoSuchEventException {
      return this.mbean.isStackTraceEnabled(id);
   }

   public boolean isStarted() {
      return this.mbean.isStarted();
   }

   public boolean isStopped() {
      return this.mbean.isStopped();
   }

   public long openStream() throws IOException {
      return this.mbean.openStream();
   }

   public long openStream(Date start, Date end) throws IOException {
      return this.mbean.openStream(start, end);
   }

   public ObjectName cloneRecording(String newName, boolean stop) throws IOException {
      return this.mbean.cloneRecording(newName, stop);
   }

   public FlightRecordingClient cloneRecordingObject(String newName, boolean stop) throws IOException {
      try {
         return this.client.cloneRecordingObject(this.objectName, newName, stop);
      } catch (NoSuchRecordingException var4) {
         throw new InternalError();
      }
   }

   public void copyTo(String path) throws IllegalStateException, IOException {
      this.mbean.copyTo(path);
   }

   public byte[] readStream(long id) throws IOException, IllegalArgumentException {
      return this.mbean.readStream(id);
   }

   public void setDestination(String path) throws IOException {
      this.mbean.setDestination(path);
   }

   public void setDuration(long millis) {
      this.mbean.setDuration(millis);
   }

   public void setEventEnabled(int id, boolean on) throws NoSuchEventException {
      this.mbean.setEventEnabled(id, on);
   }

   public void setEventSettings(List d) throws OpenDataException {
      this.mbean.setEventSettings(d);
   }

   public void setMaxAge(long millis) {
      this.mbean.setMaxAge(millis);
   }

   public void setMaxSize(long bytes) {
      this.mbean.setMaxSize(bytes);
   }

   public void setOptions(CompositeData d) throws OpenDataException {
      this.mbean.setOptions(d);
   }

   public void setPeriod(int id, long period) throws NoSuchEventException {
      this.mbean.setPeriod(id, period);
   }

   public void setStackTraceEnabled(int id, boolean on) throws NoSuchEventException {
      this.mbean.setStackTraceEnabled(id, on);
   }

   public void setStartTime(Date d) {
      this.mbean.setStartTime(d);
   }

   public void setThreshold(int id, long nanos) throws NoSuchEventException {
      this.mbean.setThreshold(id, nanos);
   }

   public void start() {
      this.mbean.start();
   }

   public void stop() throws IOException {
      this.mbean.stop();
   }

   public InputStream openStreamObject() throws IOException {
      final long id = this.openStream();
      return new InputStream() {
         private byte[] bytes;
         private int pos;
         private int count;

         public void close() throws IOException {
            super.close();
            FlightRecordingClient.this.closeStream(id);
         }

         private boolean fill() throws IOException {
            if (this.pos >= this.count) {
               this.bytes = FlightRecordingClient.this.readStream(id);
               if (this.bytes == null) {
                  return false;
               }

               this.pos = 0;
               this.count = this.bytes.length;
            }

            return true;
         }

         public int read() throws IOException {
            return !this.fill() ? -1 : this.bytes[this.pos++] & 255;
         }

         public int read(byte[] b, int off, int len) throws IOException {
            if (b == null) {
               throw new NullPointerException();
            } else if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
               if (len == 0) {
                  return 0;
               } else if (!this.fill()) {
                  return -1;
               } else {
                  int n = Math.min(this.count - this.pos, len);
                  System.arraycopy(this.bytes, this.pos, b, off, n);
                  this.pos += n;
                  return n;
               }
            } else {
               throw new IndexOutOfBoundsException();
            }
         }
      };
   }

   public InputStream openUncompressedStreamObject() throws IOException {
      return new GZIPInputStream(this.openStreamObject());
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(this.objectName);
      String dest = this.getDestination();
      if (dest != null) {
         buf.append(", destination=").append(dest);
      }

      Date startTime = this.getStartTime();
      if (startTime != null) {
         buf.append(", start=").append(startTime);
      }

      Date d = this.getDataStartTime();
      if (d != null) {
         buf.append(", dataStartTime=").append(d);
      }

      d = this.getDataEndTime();
      if (d != null) {
         buf.append(", dataEndTime=").append(d);
      }

      long dur = this.getDuration();
      if (dur != 0L) {
         buf.append(", duration=").append(dur);
      }

      long maxage = this.getMaxAge();
      if (maxage != 0L) {
         buf.append(", maxAge=").append(maxage);
      }

      long maxsize = this.getMaxSize();
      if (maxsize != 0L) {
         buf.append(", maxSize=").append(maxsize);
      }

      return buf.toString();
   }

   public boolean isToDisk() {
      return this.mbean.isToDisk();
   }

   public void setToDisk(boolean b) {
      this.mbean.setToDisk(b);
   }

   public Date getDataEndTime() {
      return this.mbean.getDataEndTime();
   }

   public Date getDataStartTime() {
      return this.mbean.getDataStartTime();
   }
}
