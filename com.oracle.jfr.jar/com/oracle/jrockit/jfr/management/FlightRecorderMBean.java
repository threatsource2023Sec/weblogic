package com.oracle.jrockit.jfr.management;

import com.oracle.jrockit.jfr.NoSuchEventException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface FlightRecorderMBean {
   String MBEAN_NAME = "com.oracle.jrockit:type=FlightRecorder";

   List getEventDefaults() throws OpenDataException;

   List getEventDefaults(ObjectName var1) throws OpenDataException, NoSuchRecordingException;

   void setEventDefaults(ObjectName var1, List var2) throws OpenDataException, NoSuchRecordingException, SecurityException;

   void addEventDefaults(ObjectName var1, List var2) throws OpenDataException, NoSuchRecordingException, SecurityException;

   List getEventSettings() throws OpenDataException;

   List getRecordings() throws OpenDataException;

   List getProducers() throws OpenDataException;

   CompositeData getRecordingOptions(ObjectName var1) throws OpenDataException, NoSuchRecordingException;

   CompositeData getRecordingOptionsDefaults() throws OpenDataException;

   void setRecordingOptions(ObjectName var1, CompositeData var2) throws OpenDataException, NoSuchRecordingException, SecurityException;

   void enableDefaultRecording() throws SecurityException;

   void disableDefaultRecording() throws SecurityException;

   boolean isDefaultRecordingRunning();

   ObjectName createRecording(String var1) throws SecurityException;

   ObjectName startRecording(String var1, CompositeData var2, List var3, List var4) throws OpenDataException, SecurityException;

   long getDataSize(ObjectName var1) throws NoSuchRecordingException;

   Date getDataStartTime(ObjectName var1) throws NoSuchRecordingException;

   Date getDataEndTime(ObjectName var1) throws NoSuchRecordingException;

   void start(ObjectName var1) throws NoSuchRecordingException, SecurityException;

   void stop(ObjectName var1) throws NoSuchRecordingException, IOException, SecurityException;

   void close(ObjectName var1) throws NoSuchRecordingException, SecurityException;

   ObjectName cloneRecording(ObjectName var1, String var2, boolean var3) throws NoSuchRecordingException, IOException, SecurityException;

   void copyTo(ObjectName var1, String var2) throws IllegalStateException, IOException, NoSuchRecordingException, SecurityException;

   void copyTo(ObjectName var1, String var2, boolean var3) throws IllegalStateException, IOException, NoSuchRecordingException, SecurityException;

   long openStream(ObjectName var1) throws NoSuchRecordingException, IOException, SecurityException;

   long openStream(ObjectName var1, Date var2, Date var3) throws IOException, NoSuchRecordingException, SecurityException;

   void closeStream(long var1) throws IOException, IllegalArgumentException, SecurityException;

   byte[] readStream(long var1) throws IOException, IllegalArgumentException, SecurityException;

   List getEventSettings(ObjectName var1) throws OpenDataException, NoSuchEventException, NoSuchRecordingException;

   void updateEventSettings(ObjectName var1, List var2) throws OpenDataException, NoSuchEventException, NoSuchRecordingException, SecurityException;

   boolean isEventEnabled(ObjectName var1, int var2) throws NoSuchEventException, NoSuchRecordingException;

   boolean isEventEnabled(int var1) throws NoSuchEventException;

   void setEventEnabled(ObjectName var1, int var2, boolean var3) throws NoSuchEventException, NoSuchRecordingException, SecurityException;

   boolean isStackTraceEnabled(ObjectName var1, int var2) throws NoSuchEventException, NoSuchRecordingException;

   boolean isStackTraceEnabled(int var1) throws NoSuchEventException;

   void setStackTraceEnabled(ObjectName var1, int var2, boolean var3) throws NoSuchEventException, NoSuchRecordingException, SecurityException;

   long getThreshold(ObjectName var1, int var2) throws NoSuchEventException, NoSuchRecordingException;

   long getThreshold(int var1) throws NoSuchEventException;

   void setThreshold(ObjectName var1, int var2, long var3) throws NoSuchEventException, NoSuchRecordingException, SecurityException;

   long getPeriod(ObjectName var1, int var2) throws NoSuchEventException, NoSuchRecordingException;

   void setPeriod(ObjectName var1, int var2, long var3) throws NoSuchEventException, NoSuchRecordingException, SecurityException;

   List getEventDescriptors() throws OpenDataException;

   List getAvailablePresets() throws OpenDataException;

   long getMaximumRepositoryChunkSize();

   long getGlobalBufferSize();

   long getThreadBufferSize();

   long getNumGlobalBuffers();

   String getRepositoryPath();

   CompositeData getStatistics() throws OpenDataException;
}
