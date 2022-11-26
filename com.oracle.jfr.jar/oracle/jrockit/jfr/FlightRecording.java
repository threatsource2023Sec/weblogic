package oracle.jrockit.jfr;

import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.management.FlightRecordingMBean;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public final class FlightRecording extends StandardMBean implements FlightRecordingMBean {
   private final Recording recording;
   private final FlightRecorder flightRecorder;

   public FlightRecording(Recording r, FlightRecorder f) throws OpenDataException, NotCompliantMBeanException {
      super(FlightRecordingMBean.class);
      this.flightRecorder = f;
      this.recording = r;
   }

   public long getId() {
      return this.recording.getId();
   }

   public CompositeData getOptions() throws OpenDataException {
      return this.flightRecorder.getRecordingOptions(this.recording);
   }

   public void setOptions(CompositeData options) throws OpenDataException {
      this.flightRecorder.setRecordingOptions(this.recording, options);
   }

   public void addEventDefaults(List defaults) throws OpenDataException {
      this.flightRecorder.addEventDefaults(this.recording, defaults);
   }

   public List getEventDefaults() throws OpenDataException {
      return this.flightRecorder.getEventDefaults(this.recording);
   }

   public void setEventDefaults(List defaults) throws OpenDataException {
      this.flightRecorder.setEventDefaults(this.recording, defaults);
   }

   public List getEventSettings() throws OpenDataException {
      return this.flightRecorder.getEventSettings(this.recording);
   }

   public void setEventSettings(List d) throws OpenDataException {
      this.flightRecorder.updateEventSettings(this.recording, d);
   }

   public long getThreshold(int id) throws NoSuchEventException {
      return this.flightRecorder.getThreshold(this.recording, id);
   }

   public boolean isEventEnabled(int id) throws NoSuchEventException {
      return this.flightRecorder.isEventEnabled(this.recording, id);
   }

   public boolean isStackTraceEnabled(int id) throws NoSuchEventException {
      return this.flightRecorder.isStackTraceEnabled(this.recording, id);
   }

   public void setEventEnabled(int id, boolean on) throws NoSuchEventException {
      this.flightRecorder.setEventEnabled(this.recording, id, on);
   }

   public void setStackTraceEnabled(int id, boolean on) throws NoSuchEventException {
      this.flightRecorder.setStackTraceEnabled(this.recording, id, on);
   }

   public void setThreshold(int id, long threshold) throws NoSuchEventException {
      this.flightRecorder.setThreshold(this.recording, id, threshold);
   }

   public long getPeriod(int id) throws NoSuchEventException {
      return this.flightRecorder.getPeriod(this.recording, id);
   }

   public void setPeriod(int id, long period) throws NoSuchEventException {
      this.flightRecorder.setPeriod(this.recording, id, period);
   }

   public String getDestination() {
      return this.recording.getDestination();
   }

   public long getDuration() {
      return this.recording.getDuration(TimeUnit.MILLISECONDS);
   }

   public long getMaxAge() {
      return this.recording.getMaxAge(TimeUnit.MILLISECONDS);
   }

   public long getMaxSize() {
      return this.recording.getMaxSize();
   }

   public String getName() {
      return this.recording.getName();
   }

   public long getDataSize() {
      return this.recording.getDataSize();
   }

   public Date getStartTime() {
      return this.recording.getStartTime();
   }

   public void setStartTime(Date d) {
      this.recording.setStartTime(d);
   }

   public void setDestination(String path) throws IOException {
      this.recording.setDestination(path);
   }

   public void setDuration(long time) {
      this.recording.setDuration(time, TimeUnit.MILLISECONDS);
   }

   public boolean isDestinationCompressed() {
      return this.recording.isDestinationCompressed();
   }

   public void setDestinationCompressed(boolean compress) {
      JFR.checkControl();
      this.recording.setDestinationCompressed(compress);
   }

   public void setMaxAge(long time) {
      JFR.checkControl();
      this.recording.setMaxAge(time, TimeUnit.MILLISECONDS);
   }

   public void setMaxSize(long bytes) {
      JFR.checkControl();
      this.recording.setMaxSize(bytes);
   }

   public void start() {
      JFR.checkControl();
      this.recording.start();
   }

   public void stop() throws IOException {
      JFR.checkControl();
      this.recording.stop();
   }

   public void close() {
      JFR.checkControl();
      this.flightRecorder.close(this.recording);
   }

   public boolean isRunning() {
      return this.recording.isRunning();
   }

   public boolean isStarted() {
      return this.recording.isStarted();
   }

   public boolean isStopped() {
      return this.recording.isStopped();
   }

   public void closeStream(long id) throws IOException, IllegalArgumentException {
      this.flightRecorder.closeStream(id);
   }

   public long openStream() throws IOException {
      return this.flightRecorder.openStream(this.recording);
   }

   public long openStream(Date start, Date end) throws IOException, IllegalStateException {
      return this.flightRecorder.openStream(this.recording, start, end);
   }

   public byte[] readStream(long id) throws IOException, IllegalArgumentException {
      return this.flightRecorder.readStream(id);
   }

   public ObjectName cloneRecording(String newName, boolean stop) throws IOException {
      return this.flightRecorder.cloneRecording(this.recording, newName, stop);
   }

   public void copyTo(String path, boolean compress) throws IllegalStateException, IOException {
      this.flightRecorder.copyTo(this.recording, path, compress);
   }

   public void copyTo(String path) throws IllegalStateException, IOException {
      this.copyTo(path, false);
   }

   public boolean isToDisk() {
      return this.recording.isToDisk();
   }

   public void setToDisk(boolean b) {
      JFR.checkControl();
      this.recording.setToDisk(b);
   }

   public Date getDataEndTime() {
      return this.recording.getDataEndTime();
   }

   public Date getDataStartTime() {
      return this.recording.getDataStartTime();
   }
}
