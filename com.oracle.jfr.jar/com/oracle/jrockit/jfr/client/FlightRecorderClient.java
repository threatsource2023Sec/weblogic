package com.oracle.jrockit.jfr.client;

import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.management.FlightRecorderMBean;
import com.oracle.jrockit.jfr.management.FlightRecordingMBean;
import com.oracle.jrockit.jfr.management.NoSuchRecordingException;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import oracle.jrockit.jfr.FlightRecorder;
import oracle.jrockit.jfr.JFR;

public class FlightRecorderClient implements FlightRecorderMBean {
   private final FlightRecorderMBean remote;
   private final MBeanServerConnection server;
   private static final ObjectName flightRecorderName;

   public FlightRecorderClient() throws InstanceNotFoundException, NullPointerException, IOException {
      JFR jfr = (JFR)AccessController.doPrivileged(new PrivilegedAction() {
         public JFR run() {
            return JFR.get();
         }
      });
      this.remote = jfr.getMBean();
      this.server = null;
   }

   public FlightRecorderClient(MBeanServerConnection server) throws InstanceNotFoundException, NullPointerException, IOException {
      this(server, flightRecorderName);
   }

   public FlightRecorderClient(MBeanServerConnection server, ObjectName name) throws InstanceNotFoundException, NullPointerException, IOException {
      FlightRecorderMBean f = null;
      this.server = server;
      f = (FlightRecorderMBean)MBeanServerInvocationHandler.newProxyInstance(server, name, FlightRecorderMBean.class, true);
      this.remote = f;
   }

   private void checkBound() {
   }

   public ObjectName createRecording(String name) {
      this.checkBound();
      return this.remote.createRecording(name);
   }

   private FlightRecordingMBean getRecording(ObjectName name) throws NoSuchRecordingException {
      if (this.server != null) {
         return (FlightRecordingMBean)MBeanServerInvocationHandler.newProxyInstance(this.server, name, FlightRecordingMBean.class, true);
      } else if (this.remote instanceof FlightRecorder) {
         FlightRecorder fr = (FlightRecorder)this.remote;

         try {
            return fr.getRecordingMBean(name);
         } catch (OpenDataException var4) {
            throw (InternalError)(new InternalError(var4.getMessage())).initCause(var4);
         }
      } else {
         throw new NoSuchRecordingException(name.toString());
      }
   }

   public FlightRecordingClient createRecordingObject(String name) throws NoSuchRecordingException {
      ObjectName objectName = this.createRecording(name);
      return new FlightRecordingClient(this, this.getRecording(objectName), objectName);
   }

   public ObjectName startRecording(String name, CompositeData options, List eventSettings, List eventDefaults) throws OpenDataException {
      return this.remote.startRecording(name, options, eventSettings, eventDefaults);
   }

   public FlightRecordingClient startRecordingObject(String name, CompositeData options, List eventSettings, List eventDefaults) throws OpenDataException {
      ObjectName objectName = this.startRecording(name, options, eventSettings, eventDefaults);

      try {
         return new FlightRecordingClient(this, this.getRecording(objectName), objectName);
      } catch (NoSuchRecordingException var7) {
         throw new InternalError();
      }
   }

   public void copyTo(ObjectName recording, String path, boolean compress) throws IllegalStateException, IOException, NoSuchRecordingException {
      this.remote.copyTo(recording, path, compress);
   }

   public void disableDefaultRecording() {
      this.checkBound();
      this.remote.disableDefaultRecording();
   }

   public void enableDefaultRecording() {
      this.checkBound();
      this.remote.enableDefaultRecording();
   }

   public boolean isDefaultRecordingRunning() {
      this.checkBound();
      return this.remote.isDefaultRecordingRunning();
   }

   public long getDataSize(ObjectName recording) throws NoSuchRecordingException {
      this.checkBound();
      return this.remote.getDataSize(recording);
   }

   public void addEventDefaults(ObjectName recording, List defaults) throws OpenDataException, NoSuchRecordingException {
      this.remote.addEventDefaults(recording, defaults);
   }

   public List getEventDefaults() throws OpenDataException {
      return this.remote.getEventDefaults();
   }

   public List getEventDefaults(ObjectName recording) throws OpenDataException, NoSuchRecordingException {
      return this.remote.getEventDefaults(recording);
   }

   public void setEventDefaults(ObjectName recording, List defaults) throws OpenDataException, NoSuchRecordingException {
      this.remote.setEventDefaults(recording, defaults);
   }

   public List getEventSettings() throws OpenDataException {
      this.checkBound();
      return this.remote.getEventSettings();
   }

   public List getProducers() throws OpenDataException {
      this.checkBound();
      return this.remote.getProducers();
   }

   public List getRecordings() throws OpenDataException {
      this.checkBound();
      return this.remote.getRecordings();
   }

   public List getRecordingObjects() throws OpenDataException, NoSuchRecordingException {
      List cl = this.getRecordings();
      ArrayList l = new ArrayList(cl.size());
      Iterator i$ = cl.iterator();

      while(i$.hasNext()) {
         CompositeData d = (CompositeData)i$.next();
         ObjectName name = (ObjectName)d.get("objectName");

         try {
            l.add(new FlightRecordingClient(this, this.getRecording(name), name));
         } catch (NoSuchRecordingException var7) {
         }
      }

      return l;
   }

   public void close(ObjectName recording) throws NoSuchRecordingException {
      this.checkBound();
      this.remote.close(recording);
   }

   public void closeStream(long id) throws IOException, IllegalArgumentException {
      this.checkBound();
      this.remote.closeStream(id);
   }

   public List getEventSettings(ObjectName recording) throws OpenDataException, NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      return this.remote.getEventSettings(recording);
   }

   public long getPeriod(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      return this.remote.getPeriod(recording, id);
   }

   public CompositeData getRecordingOptions(ObjectName recording) throws OpenDataException, NoSuchRecordingException {
      this.checkBound();
      return this.remote.getRecordingOptions(recording);
   }

   public long getThreshold(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      return this.remote.getThreshold(recording, id);
   }

   public boolean isEventEnabled(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      return this.remote.isEventEnabled(recording, id);
   }

   public boolean isStackTraceEnabled(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      return this.remote.isStackTraceEnabled(recording, id);
   }

   public long openStream(ObjectName recording, Date start, Date end) throws IOException, NoSuchRecordingException {
      this.checkBound();
      return this.remote.openStream(recording, start, end);
   }

   public long openStream(ObjectName recording) throws NoSuchRecordingException, IOException {
      this.checkBound();
      return this.remote.openStream(recording);
   }

   public byte[] readStream(long id) throws IOException, IllegalArgumentException {
      this.checkBound();
      return this.remote.readStream(id);
   }

   public void setEventEnabled(ObjectName recording, int id, boolean on) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      this.remote.setEventEnabled(recording, id, on);
   }

   public void setPeriod(ObjectName recording, int id, long threshold) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      this.remote.setPeriod(recording, id, threshold);
   }

   public void setRecordingOptions(ObjectName recording, CompositeData options) throws OpenDataException, NoSuchRecordingException {
      this.checkBound();
      this.remote.setRecordingOptions(recording, options);
   }

   public void setStackTraceEnabled(ObjectName recording, int id, boolean on) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      this.remote.setStackTraceEnabled(recording, id, on);
   }

   public void setThreshold(ObjectName recording, int id, long threshold) throws NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      this.remote.setThreshold(recording, id, threshold);
   }

   public void start(ObjectName recording) throws NoSuchRecordingException {
      this.checkBound();
      this.remote.start(recording);
   }

   public void stop(ObjectName recording) throws NoSuchRecordingException, IOException {
      this.checkBound();
      this.remote.stop(recording);
   }

   public void updateEventSettings(ObjectName recording, List settings) throws OpenDataException, NoSuchEventException, NoSuchRecordingException {
      this.checkBound();
      this.remote.updateEventSettings(recording, settings);
   }

   public long getThreshold(int id) throws NoSuchEventException {
      this.checkBound();
      return this.remote.getThreshold(id);
   }

   public boolean isEventEnabled(int id) throws NoSuchEventException {
      this.checkBound();
      return this.remote.isEventEnabled(id);
   }

   public boolean isStackTraceEnabled(int id) throws NoSuchEventException {
      this.checkBound();
      return this.remote.isStackTraceEnabled(id);
   }

   public List getEventDescriptors() throws OpenDataException {
      this.checkBound();
      return this.remote.getEventDescriptors();
   }

   public ObjectName cloneRecording(ObjectName recording, String newName, boolean stop) throws NoSuchRecordingException, IOException {
      this.checkBound();
      return this.remote.cloneRecording(recording, newName, stop);
   }

   public FlightRecordingClient cloneRecordingObject(ObjectName recording, String newName, boolean stop) throws NoSuchRecordingException, IOException {
      ObjectName name = this.cloneRecording(recording, newName, stop);
      return new FlightRecordingClient(this, this.getRecording(name), name);
   }

   public void copyTo(ObjectName recording, String path) throws IllegalStateException, IOException, NoSuchRecordingException {
      this.checkBound();
      this.remote.copyTo(recording, path);
   }

   public List getAvailablePresets() throws OpenDataException {
      return this.remote.getAvailablePresets();
   }

   public long getMaximumRepositoryChunkSize() {
      return this.remote.getMaximumRepositoryChunkSize();
   }

   public long getGlobalBufferSize() {
      return this.remote.getGlobalBufferSize();
   }

   public long getThreadBufferSize() {
      return this.remote.getThreadBufferSize();
   }

   public long getNumGlobalBuffers() {
      return this.remote.getNumGlobalBuffers();
   }

   public String getRepositoryPath() {
      return this.remote.getRepositoryPath();
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("Flight Recorder MBean Client = {\n");

      try {
         Iterator i$ = this.getRecordingObjects().iterator();

         while(i$.hasNext()) {
            FlightRecordingClient r = (FlightRecordingClient)i$.next();
            buf.append('\t').append(r).append('\n');
         }
      } catch (Exception var4) {
         buf.append(var4);
      }

      buf.append('}');
      return buf.toString();
   }

   public CompositeData getRecordingOptionsDefaults() throws OpenDataException {
      return this.remote.getRecordingOptionsDefaults();
   }

   public CompositeData getStatistics() throws OpenDataException {
      return this.remote.getStatistics();
   }

   public Date getDataEndTime(ObjectName recording) throws NoSuchRecordingException {
      return this.remote.getDataEndTime(recording);
   }

   public Date getDataStartTime(ObjectName recording) throws NoSuchRecordingException {
      return this.remote.getDataStartTime(recording);
   }

   static {
      try {
         flightRecorderName = new ObjectName("com.oracle.jrockit:type=FlightRecorder");
      } catch (MalformedObjectNameException var1) {
         throw new Error(var1);
      }
   }
}
