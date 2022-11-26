package oracle.jrockit.jfr;

import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.management.FlightRecorderMBean;
import com.oracle.jrockit.jfr.management.FlightRecordingMBean;
import com.oracle.jrockit.jfr.management.NoSuchRecordingException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.openmbean.EventDefaultType;
import oracle.jrockit.jfr.openmbean.EventDescriptorType;
import oracle.jrockit.jfr.openmbean.EventSettingType;
import oracle.jrockit.jfr.openmbean.JFRStatsType;
import oracle.jrockit.jfr.openmbean.PresetFileType;
import oracle.jrockit.jfr.openmbean.ProducerDescriptorType;
import oracle.jrockit.jfr.openmbean.RecordingOptionsType;
import oracle.jrockit.jfr.openmbean.RecordingType;
import oracle.jrockit.jfr.settings.EventDefaultSet;
import oracle.jrockit.jfr.settings.EventSetting;
import oracle.jrockit.jfr.settings.EventSettings;
import oracle.jrockit.log.Logger;
import oracle.jrockit.log.MsgLevel;

public final class FlightRecorder extends StandardMBean implements FlightRecorderMBean {
   private final JFRImpl jfrImpl;
   private final EventSettingType eventSettingType;
   private final RecordingOptionsType recordingOptionsType;
   private final RecordingType recordingType;
   private final EventDescriptorType eventDescriptorType;
   private final ProducerDescriptorType producerDescriptorType;
   private final EventDefaultType eventDefaultType;
   private final PresetFileType presetFileType;
   private final JFRStatsType jfrStatsType;
   private final Logger logger;
   private final Options options;
   private final AtomicLong idCounter = new AtomicLong();
   private final Map streams = Collections.synchronizedMap(new HashMap());

   FlightRecorder(Options options, Logger logger, JFRImpl jfrImpl) throws NotCompliantMBeanException, OpenDataException {
      super(FlightRecorderMBean.class);
      this.options = options;
      this.logger = logger;
      this.jfrImpl = jfrImpl;
      this.eventSettingType = new EventSettingType(jfrImpl);
      this.recordingOptionsType = new RecordingOptionsType();
      this.recordingType = new RecordingType(this.recordingOptionsType);
      this.eventDescriptorType = new EventDescriptorType();
      this.eventDefaultType = new EventDefaultType();
      this.presetFileType = new PresetFileType(this.eventDefaultType);
      this.producerDescriptorType = new ProducerDescriptorType(this.eventDescriptorType);
      this.jfrStatsType = new JFRStatsType();
   }

   void destroy() {
      Iterator i$ = this.streams.values().iterator();

      while(i$.hasNext()) {
         RecordingStream s = (RecordingStream)i$.next();

         try {
            s.close();
         } catch (IOException var4) {
         }
      }

      this.streams.clear();
   }

   void addEventDefaults(Recording recording, List defaults) throws OpenDataException {
      JFR.checkControl();
      recording.getEventSettings().addEventDefaultSet(new EventDefaultSet(this.eventDefaultType.toJavaTypeData(defaults)));
   }

   public void addEventDefaults(ObjectName recording, List defaults) throws OpenDataException, NoSuchRecordingException {
      this.addEventDefaults(this.getRecording(recording), defaults);
   }

   private List getEventDefaults(EventSettings c) throws OpenDataException {
      return this.eventDefaultType.toCompositeData(c.getEventDefaults());
   }

   public List getEventDefaults() throws OpenDataException {
      return this.getEventDefaults(this.jfrImpl.getEventSettings());
   }

   List getEventDefaults(Recording recording) throws OpenDataException {
      return this.getEventDefaults(recording.getEventSettings());
   }

   public List getEventDefaults(ObjectName recording) throws OpenDataException, NoSuchRecordingException {
      return this.getEventDefaults(this.getRecording(recording));
   }

   void setEventDefaults(Recording recording, List defaults) throws OpenDataException {
      JFR.checkControl();
      recording.getEventSettings().replaceEventDefaultSets(Collections.singletonList(new EventDefaultSet(this.eventDefaultType.toJavaTypeData(defaults))));
   }

   public void setEventDefaults(ObjectName recording, List defaults) throws OpenDataException, NoSuchRecordingException {
      this.setEventDefaults(this.getRecording(recording), defaults);
   }

   public List getEventSettings() throws OpenDataException {
      return this.eventSettingType.toCompositeData(this.jfrImpl.getEventSettings().getSettings());
   }

   public List getRecordings() throws OpenDataException {
      Collection recordings = this.jfrImpl.getRecordings();
      Iterator i = recordings.iterator();

      while(i.hasNext()) {
         Recording r = (Recording)i.next();
         if (!r.isBound()) {
            this.logger.log(MsgLevel.TRACE, "Recording %s not bound", new Object[]{r});
            i.remove();
         }
      }

      return this.recordingType.toCompositeData(recordings);
   }

   public List getProducers() throws OpenDataException {
      return this.producerDescriptorType.toCompositeData(this.jfrImpl.getProducers());
   }

   public List getEventDescriptors() throws OpenDataException {
      return this.eventDescriptorType.toCompositeData(this.jfrImpl.getEvents());
   }

   public void enableDefaultRecording() {
      JFR.checkControl();
      this.jfrImpl.enableDefaultRecording();
   }

   public void disableDefaultRecording() {
      JFR.checkControl();
      this.jfrImpl.disableDefaultRecording();
   }

   public boolean isDefaultRecordingRunning() {
      return this.jfrImpl.isContinuousModeRunning();
   }

   CompositeData getRecordingOptions(Recording recording) throws OpenDataException {
      return this.recordingOptionsType.toCompositeTypeData((RecordingOptions)recording);
   }

   public CompositeData getRecordingOptions(ObjectName recording) throws OpenDataException, NoSuchRecordingException {
      return this.getRecordingOptions(this.getRecording(recording));
   }

   void setRecordingOptions(Recording recording, CompositeData options) throws OpenDataException {
      JFR.checkControl();
      RecordingOptions o = this.recordingOptionsType.toJavaTypeData(options);

      try {
         recording.setOptions(o);
      } catch (IOException var6) {
         OpenDataException odex = new OpenDataException(var6.getMessage());
         odex.initCause(var6);
         throw odex;
      }
   }

   public void setRecordingOptions(ObjectName recording, CompositeData options) throws OpenDataException, NoSuchRecordingException {
      this.setRecordingOptions(this.getRecording(recording), options);
   }

   ObjectName cloneRecording(Recording recording, String newName, boolean stop) throws IOException {
      JFR.checkControl();
      Recording r2 = this.jfrImpl.cloneRecording(recording, newName, stop);
      return this.jfrImpl.bind(r2);
   }

   public ObjectName cloneRecording(ObjectName recording, String newName, boolean stop) throws NoSuchRecordingException, IOException {
      return this.cloneRecording(this.getRecording(recording), newName, stop);
   }

   void copyTo(Recording recording, String path, boolean compress) throws IOException {
      JFR.checkControl();
      recording.copyTo(path, compress);
   }

   public void copyTo(ObjectName recording, String path, boolean compress) throws IllegalStateException, IOException, NoSuchRecordingException {
      this.copyTo(this.getRecording(recording), path, compress);
   }

   public void copyTo(ObjectName recording, String path) throws IllegalStateException, IOException, NoSuchRecordingException {
      this.copyTo(recording, path, false);
   }

   public ObjectName createRecording(String name) {
      JFR.checkControl();
      Recording r = this.jfrImpl.createRecording(name);
      return this.jfrImpl.bind(r);
   }

   public ObjectName startRecording(String name, CompositeData options, List eventSettings, List eventDefaults) throws OpenDataException {
      JFR.checkControl();
      Recording r = this.jfrImpl.createRecording(name);

      try {
         if (eventSettings != null) {
            this.updateEventSettings(r, eventSettings);
         }

         if (options != null) {
            this.setRecordingOptions(r, options);
         }

         if (eventDefaults != null) {
            this.addEventDefaults(r, eventDefaults);
         }

         if (r.getStartTime() == null) {
            r.start();
         }

         return this.jfrImpl.bind(r);
      } catch (OpenDataException var7) {
         this.close(r);
         throw var7;
      } catch (RuntimeException var8) {
         this.close(r);
         throw var8;
      }
   }

   private Recording getRecording(ObjectName recording) throws NoSuchRecordingException {
      long id = Long.parseLong(recording.getKeyProperty("id"));
      return this.jfrImpl.getRecording(id);
   }

   public FlightRecordingMBean getRecordingMBean(ObjectName recording) throws NoSuchRecordingException, OpenDataException {
      try {
         return new FlightRecording(this.getRecording(recording), this);
      } catch (NotCompliantMBeanException var3) {
         throw JFR.cannotHappen(var3);
      }
   }

   public void close(ObjectName recording) throws NoSuchRecordingException {
      this.close(this.getRecording(recording));
   }

   void close(Recording recording) {
      JFR.checkControl();
      this.jfrImpl.release(recording);
   }

   private EventSetting getSetting(Recording r, int id) throws NoSuchEventException {
      return r.getEventSettings().getSetting(id);
   }

   List getEventSettings(Recording r) throws OpenDataException {
      return this.eventSettingType.toCompositeData(r.getEventSettings().getSettings());
   }

   public List getEventSettings(ObjectName recording) throws OpenDataException, NoSuchRecordingException {
      return this.getEventSettings(this.getRecording(recording));
   }

   long getPeriod(Recording r, int id) throws NoSuchEventException {
      return this.getSetting(r, id).getPeriod();
   }

   public long getPeriod(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      return this.getPeriod(this.getRecording(recording), id);
   }

   public long getDataSize(ObjectName recording) throws NoSuchRecordingException {
      return this.getRecording(recording).getDataSize();
   }

   public Date getDataStartTime(ObjectName recording) throws NoSuchRecordingException {
      return this.getRecording(recording).getDataStartTime();
   }

   public Date getDataEndTime(ObjectName recording) throws NoSuchRecordingException {
      return this.getRecording(recording).getDataEndTime();
   }

   long getThreshold(Recording r, int id) throws NoSuchEventException {
      return this.getSetting(r, id).getThreshold();
   }

   public long getThreshold(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      return this.getThreshold(this.getRecording(recording), id);
   }

   boolean isEventEnabled(Recording r, int id) throws NoSuchEventException {
      return this.getSetting(r, id).isEnabled();
   }

   public boolean isEventEnabled(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      return this.isEventEnabled(this.getRecording(recording), id);
   }

   boolean isStackTraceEnabled(Recording r, int id) throws NoSuchEventException {
      return this.getSetting(r, id).isStacktraceEnabled();
   }

   public boolean isStackTraceEnabled(ObjectName recording, int id) throws NoSuchEventException, NoSuchRecordingException {
      return this.isStackTraceEnabled(this.getRecording(recording), id);
   }

   void updateEventSettings(Recording recording, List settings) throws OpenDataException {
      JFR.checkControl();
      recording.getEventSettings().putSettings(this.eventSettingType.toJavaTypeData(settings));
   }

   public void updateEventSettings(ObjectName recording, List settings) throws OpenDataException, NoSuchRecordingException {
      this.updateEventSettings(this.getRecording(recording), settings);
   }

   long openStream(Recording recording, Date start, Date end) throws IOException {
      JFR.checkControl();
      RecordingStream s = new RecordingStream(recording.getChannel(start, end));
      long id = this.idCounter.incrementAndGet();
      this.streams.put(id, s);
      return id;
   }

   long openStream(Recording recording) throws IOException {
      JFR.checkControl();
      RecordingStream s = new RecordingStream(recording.getChannel());
      long id = this.idCounter.incrementAndGet();
      this.streams.put(id, s);
      return id;
   }

   public long openStream(ObjectName recording, Date start, Date end) throws IOException, NoSuchRecordingException {
      return this.openStream(this.getRecording(recording), start, end);
   }

   public long openStream(ObjectName recording) throws IOException, NoSuchRecordingException {
      return this.openStream(this.getRecording(recording));
   }

   public void closeStream(long id) throws IOException, IllegalArgumentException {
      JFR.checkControl();
      RecordingStream s = (RecordingStream)this.streams.get(id);
      if (s == null) {
         throw new IllegalArgumentException("No such stream " + id);
      } else {
         s.close();
         this.streams.remove(id);
      }
   }

   public byte[] readStream(long id) throws IOException, IllegalArgumentException {
      JFR.checkControl();
      RecordingStream s = (RecordingStream)this.streams.get(id);
      if (s == null) {
         throw new IllegalArgumentException("No such stream " + id);
      } else {
         return s.read();
      }
   }

   void setEventEnabled(Recording r, int id, boolean on) throws NoSuchEventException {
      JFR.checkControl();
      EventDescriptor d = this.jfrImpl.getEvent(id);
      EventSetting s = this.getSetting(r, id);
      EventSetting n = new EventSetting(d, on, s.isStacktraceEnabled(), s.getThreshold(), s.getPeriod());
      r.getEventSettings().putSettings(Collections.singletonList(n));
   }

   public void setEventEnabled(ObjectName recording, int id, boolean on) throws NoSuchEventException, NoSuchRecordingException {
      this.setEventEnabled(this.getRecording(recording), id, on);
   }

   void setPeriod(Recording r, int id, long period) throws NoSuchEventException {
      JFR.checkControl();
      EventDescriptor d = this.jfrImpl.getEvent(id);
      EventSetting s = this.getSetting(r, id);
      EventSetting n = new EventSetting(d, s.isEnabled(), s.isStacktraceEnabled(), s.getThreshold(), period);
      r.getEventSettings().putSettings(Collections.singletonList(n));
   }

   public void setPeriod(ObjectName recording, int id, long period) throws NoSuchRecordingException, NoSuchEventException {
      this.setPeriod(this.getRecording(recording), id, period);
   }

   void setStackTraceEnabled(Recording r, int id, boolean on) throws NoSuchEventException {
      JFR.checkControl();
      EventDescriptor d = this.jfrImpl.getEvent(id);
      EventSetting s = this.getSetting(r, id);
      EventSetting n = new EventSetting(d, s.isEnabled(), on, s.getThreshold(), s.getPeriod());
      r.getEventSettings().putSettings(Collections.singletonList(n));
   }

   public void setStackTraceEnabled(ObjectName recording, int id, boolean on) throws NoSuchRecordingException, NoSuchEventException {
      this.setStackTraceEnabled(this.getRecording(recording), id, on);
   }

   void setThreshold(Recording r, int id, long threshold) throws NoSuchEventException {
      JFR.checkControl();
      EventDescriptor d = this.jfrImpl.getEvent(id);
      EventSetting s = this.getSetting(r, id);
      EventSetting n = new EventSetting(d, s.isEnabled(), s.isStacktraceEnabled(), threshold, s.getPeriod());
      r.getEventSettings().putSettings(Collections.singletonList(n));
   }

   public void setThreshold(ObjectName recording, int id, long threshold) throws NoSuchEventException, NoSuchRecordingException {
      this.setThreshold(this.getRecording(recording), id, threshold);
   }

   public void start(ObjectName recording) throws NoSuchRecordingException {
      JFR.checkControl();
      this.getRecording(recording).start();
   }

   public void stop(ObjectName recording) throws NoSuchRecordingException, IOException {
      JFR.checkControl();
      this.getRecording(recording).stop();
   }

   public long getThreshold(int id) throws NoSuchEventException {
      return this.jfrImpl.getEventSettings().getSetting(id).getThreshold();
   }

   public boolean isEventEnabled(int id) throws NoSuchEventException {
      return this.jfrImpl.getEventSettings().getSetting(id).isEnabled();
   }

   public boolean isStackTraceEnabled(int id) throws NoSuchEventException {
      return this.jfrImpl.getEventSettings().getSetting(id).isStacktraceEnabled();
   }

   public long getGlobalBufferSize() {
      return (long)this.options.globalBufferSize();
   }

   public long getThreadBufferSize() {
      return (long)this.options.threadBufferSize();
   }

   public long getMaximumRepositoryChunkSize() {
      return this.options.maxChunkSize();
   }

   public long getNumGlobalBuffers() {
      return (long)this.options.numGlobalBuffers();
   }

   public String getRepositoryPath() {
      return this.options.repository();
   }

   public List getAvailablePresets() throws OpenDataException {
      ArrayList l = new ArrayList();
      File root = null;
      String dev = System.getProperty("jrockit.launcher.dir");
      if (dev != null) {
         root = new File(dev);
      } else {
         String javahome = System.getProperty("java.home");
         root = new File(javahome, "lib/jfr");
      }

      File[] arr$ = root.listFiles(new FileFilter() {
         public boolean accept(File pathname) {
            return pathname.getPath().toLowerCase().endsWith(".jfs");
         }
      });
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         File f = arr$[i$];

         try {
            l.add(new PresetFile(f));
         } catch (IOException var9) {
            this.logger.log(MsgLevel.WARN, var9, "Could not load %s", new Object[]{f});
         } catch (URISyntaxException var10) {
            this.logger.log(MsgLevel.WARN, var10, "Error reading %s", new Object[]{f});
         } catch (ParseException var11) {
            this.logger.log(MsgLevel.WARN, var11, "Syntax error in %s; %s", new Object[]{f, var11.getMessage()});
         }
      }

      return this.presetFileType.toCompositeData(l);
   }

   public CompositeData getRecordingOptionsDefaults() throws OpenDataException {
      return this.recordingOptionsType.toCompositeTypeData((RecordingOptions)(new RecordingOptionsImpl()));
   }

   public CompositeData getStatistics() throws OpenDataException {
      return this.jfrStatsType.toCompositeTypeData(this.jfrImpl.getJFRStats());
   }
}
