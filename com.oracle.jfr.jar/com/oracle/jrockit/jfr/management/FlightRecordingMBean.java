package com.oracle.jrockit.jfr.management;

import com.oracle.jrockit.jfr.NoSuchEventException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface FlightRecordingMBean {
   String MBEAN_NAME = "com.oracle.jrockit:type=FlightRecording";

   long getId();

   CompositeData getOptions() throws OpenDataException;

   void setOptions(CompositeData var1) throws OpenDataException, SecurityException;

   long getDuration();

   void setDuration(long var1) throws SecurityException;

   boolean isToDisk();

   void setToDisk(boolean var1);

   long getMaxAge() throws SecurityException;

   void setMaxAge(long var1) throws SecurityException;

   long getMaxSize();

   void setMaxSize(long var1) throws SecurityException;

   String getDestination();

   void setDestination(String var1) throws IOException, SecurityException;

   boolean isDestinationCompressed();

   void setDestinationCompressed(boolean var1) throws SecurityException;

   Date getStartTime();

   void setStartTime(Date var1) throws SecurityException;

   String getName();

   long getDataSize();

   Date getDataStartTime();

   Date getDataEndTime();

   void start() throws IllegalStateException, SecurityException;

   void stop() throws IllegalStateException, IOException, SecurityException;

   void close() throws IllegalStateException, SecurityException;

   boolean isStarted();

   boolean isStopped();

   boolean isRunning();

   ObjectName cloneRecording(String var1, boolean var2) throws IOException, SecurityException;

   void copyTo(String var1) throws IllegalStateException, IOException, SecurityException;

   void copyTo(String var1, boolean var2) throws IllegalStateException, IOException, SecurityException;

   List getEventDefaults() throws OpenDataException;

   void setEventDefaults(List var1) throws OpenDataException, SecurityException;

   void addEventDefaults(List var1) throws OpenDataException, SecurityException;

   List getEventSettings() throws OpenDataException;

   void setEventSettings(List var1) throws OpenDataException, SecurityException;

   boolean isEventEnabled(int var1) throws NoSuchEventException;

   boolean isStackTraceEnabled(int var1) throws NoSuchEventException;

   void setEventEnabled(int var1, boolean var2) throws NoSuchEventException, SecurityException;

   void setStackTraceEnabled(int var1, boolean var2) throws NoSuchEventException, SecurityException;

   long getThreshold(int var1) throws NoSuchEventException;

   void setThreshold(int var1, long var2) throws NoSuchEventException, SecurityException;

   long getPeriod(int var1) throws NoSuchEventException;

   void setPeriod(int var1, long var2) throws NoSuchEventException, SecurityException;

   long openStream() throws IOException, SecurityException;

   long openStream(Date var1, Date var2) throws IOException, SecurityException;

   void closeStream(long var1) throws IOException, IllegalArgumentException, SecurityException;

   byte[] readStream(long var1) throws IOException, IllegalArgumentException, SecurityException;
}
