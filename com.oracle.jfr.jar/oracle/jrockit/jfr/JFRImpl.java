package oracle.jrockit.jfr;

import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.Producer;
import com.oracle.jrockit.jfr.management.NoSuchRecordingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.openmbean.OpenDataException;
import oracle.jrockit.jfr.events.EventControl;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.events.EventHandler;
import oracle.jrockit.jfr.events.EventHandlerCreator;
import oracle.jrockit.jfr.events.JavaEventDescriptor;
import oracle.jrockit.jfr.events.JavaProducerDescriptor;
import oracle.jrockit.jfr.settings.EventDefaultSet;
import oracle.jrockit.jfr.settings.EventSetting;
import oracle.jrockit.jfr.settings.EventSettings;
import oracle.jrockit.log.Logger;
import oracle.jrockit.log.MsgLevel;

public abstract class JFRImpl extends JFR implements RecordingObserver {
   protected final Logger logger = Logger.loggerFor("jfr");
   protected final TimeZone timezone = TimeZone.getDefault();
   protected final Locale locale = Locale.getDefault();
   private final AtomicInteger idCounter = new AtomicInteger(4096);
   private final AtomicLong recordingCounter = new AtomicLong();
   private final HashMap producerMap = new HashMap();
   private final HashMap eventsControls = new HashMap();
   private final Object eventLock = new Object();
   private final Object mbeanLock = new Object();
   private final Timer timer = new Timer("JFR request timer", true);
   private final HashMap recordings = new HashMap();
   private final Repository repository;
   private final Object startBarrier = new Object();
   private int numRealRecordings;
   private final FlightRecorder mbeanObject;
   private final Options options;
   private final MetaProducer metaProducer = new MetaProducer(this);
   private final Settings settings;
   private Recording defaultRecording;
   List mbeanServers;
   private boolean active;

   JFRImpl(Options options) {
      this.settings = new Settings(this.eventLock, this.eventsControls, this.recordings.values(), this.metaProducer);
      this.mbeanServers = Collections.emptyList();
      this.active = true;
      this.options = options;
      this.repository = new Repository(this, options, this.logger);

      try {
         this.mbeanObject = new FlightRecorder(options, this.logger, this);
      } catch (Exception var5) {
         this.logger.error("Could not create Flight Recorder MBean", var5);
         throw new InternalError();
      }

      this.loadDefaultSettings(this.settings, "default.jfs");
      List settingFiles = options.settingsFiles();
      if (!settingFiles.isEmpty()) {
         Iterator i$ = settingFiles.iterator();

         while(i$.hasNext()) {
            String s = (String)i$.next();
            this.loadDefaultSettings(this.settings, s);
         }
      }

      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
         public void run() {
            JFRImpl.this.shutdown();
            JFRImpl.this.destroy();
         }
      }));
      this.addProducer(this.metaProducer, this.metaProducer.getControls());
   }

   protected void startDefaultRecording() {
      if (this.options.defaultRecording()) {
         this.enableDefaultRecording();
      }

   }

   public boolean active() {
      return true;
   }

   public void bind(MBeanServer server) {
      checkControl();
      synchronized(this.mbeanLock) {
         ArrayList l = new ArrayList(this.mbeanServers.size() + 1);
         l.addAll(this.mbeanServers);
         l.add(server);
         this.mbeanServers = l;
      }

      try {
         server.registerMBean(this.getMBean(), new ObjectName("com.oracle.jrockit:type=FlightRecorder"));
      } catch (InstanceAlreadyExistsException var7) {
      } catch (MBeanRegistrationException var8) {
      } catch (NotCompliantMBeanException var9) {
      } catch (MalformedObjectNameException var10) {
      }

      synchronized(this.eventLock) {
         Iterator i$ = this.recordings.values().iterator();

         while(i$.hasNext()) {
            Recording r = (Recording)i$.next();
            if (r.objectName != null) {
               this.bind(r, server);
            }
         }

      }
   }

   private void bind(Recording r, MBeanServer server) {
      try {
         FlightRecording fr = new FlightRecording(r, this.mbeanObject);
         server.registerMBean(fr, r.objectName);
      } catch (InstanceAlreadyExistsException var4) {
      } catch (OpenDataException var5) {
      } catch (MBeanRegistrationException var6) {
      } catch (NotCompliantMBeanException var7) {
      }

   }

   ObjectName bind(Recording r) {
      try {
         synchronized(this.eventLock) {
            if (r.objectName == null) {
               this.logger.log(MsgLevel.TRACE, "Binding recording %s", new Object[]{r});
               r.objectName = new ObjectName("com.oracle.jrockit:type=FlightRecording,id=" + r.getId() + ",name=" + r.getName());
               if (r.isReleased()) {
                  return null;
               }

               Iterator i$ = this.mbeanServers.iterator();

               while(i$.hasNext()) {
                  MBeanServer s = (MBeanServer)i$.next();
                  this.bind(r, s);
               }
            }
         }
      } catch (MalformedObjectNameException var7) {
      }

      return r.objectName;
   }

   public void unbind(MBeanServer server) {
      checkControl();
      synchronized(this.mbeanLock) {
         ArrayList l = new ArrayList(this.mbeanServers.size());
         Iterator i$ = this.mbeanServers.iterator();

         while(true) {
            if (!i$.hasNext()) {
               this.mbeanServers = l;
               break;
            }

            MBeanServer s = (MBeanServer)i$.next();
            if (s != server) {
               l.add(s);
            }
         }
      }

      try {
         server.unregisterMBean(new ObjectName("com.oracle.jrockit:type=FlightRecorder"));
      } catch (InstanceNotFoundException var8) {
      } catch (MBeanRegistrationException var9) {
      } catch (MalformedObjectNameException var10) {
      }

      synchronized(this.eventLock) {
         Iterator i$ = this.recordings.values().iterator();

         while(i$.hasNext()) {
            Recording r = (Recording)i$.next();
            this.unbind(r, server);
         }

      }
   }

   private void unbind(Recording r, MBeanServer server) {
      if (r.objectName != null) {
         ObjectName name = r.objectName;

         try {
            server.unregisterMBean(name);
         } catch (InstanceNotFoundException var5) {
         } catch (MBeanRegistrationException var6) {
         }

      }
   }

   private void unbind(Recording r) {
      assert Thread.holdsLock(this.eventLock);

      Iterator i$ = this.mbeanServers.iterator();

      while(i$.hasNext()) {
         MBeanServer s = (MBeanServer)i$.next();
         this.unbind(r, s);
      }

   }

   EventSettings getEventSettings() {
      return this.settings;
   }

   public void addProducer(Producer p, int id, List events, Map pools) {
      ArrayList descriptors = new ArrayList();
      Iterator i$ = events.iterator();

      while(i$.hasNext()) {
         EventHandler e = (EventHandler)i$.next();
         descriptors.add(e.getDescriptor());
      }

      JavaProducerDescriptor pc = new JavaProducerDescriptor(id, p.getName(), p.getDescription(), p.getURI(), descriptors, pools);
      this.addProducer(pc, events);
   }

   public void addEventsToRegisteredProducer(Producer p, int id, List events, Map pools) {
      ProducerDescriptor pc;
      synchronized(this.eventLock) {
         pc = (ProducerDescriptor)this.producerMap.get(id);
         if (pc == null) {
            throw new IllegalStateException("Not a registered producer " + p);
         }

         if (!pc.getURI().equals(p.getURI())) {
            throw new IllegalStateException(id + " already registered as other producer" + pc);
         }

         if (!(pc instanceof JavaProducerDescriptor)) {
            throw new InternalError();
         }
      }

      JavaProducerDescriptor jpc = (JavaProducerDescriptor)pc;
      ArrayList descriptors = new ArrayList();
      Iterator i$ = jpc.getEvents().iterator();

      while(i$.hasNext()) {
         EventDescriptor d = (EventDescriptor)i$.next();
         descriptors.add((JavaEventDescriptor)d);
      }

      i$ = events.iterator();

      while(i$.hasNext()) {
         EventHandler e = (EventHandler)i$.next();
         descriptors.add(e.getDescriptor());
      }

      jpc = new JavaProducerDescriptor(id, p.getName(), p.getDescription(), p.getURI(), descriptors, pools);
      this.addProducer(jpc, events);
   }

   protected void addProducer(ProducerDescriptor pc, Collection events) {
      this.add(pc);
      synchronized(this.eventLock) {
         this.producerMap.put(pc.getId(), pc);
         Iterator i$ = events.iterator();

         while(i$.hasNext()) {
            EventControl e = (EventControl)i$.next();
            this.eventsControls.put(e.getId(), e);
         }

         this.settings.addEvents(events);
      }
   }

   public void removeProducer(int pid) {
      ProducerDescriptor p = null;
      synchronized(this.eventLock) {
         p = (ProducerDescriptor)this.producerMap.get(pid);
         if (p == null) {
            return;
         }

         ArrayList events = new ArrayList();
         EventSetting off = new EventSetting(0);
         Iterator i$ = p.getEvents().iterator();

         while(true) {
            if (!i$.hasNext()) {
               this.settings.removeEvents(events);
               break;
            }

            EventDescriptor d = (EventDescriptor)i$.next();
            EventControl c = (EventControl)this.eventsControls.remove(d.getId());
            c.apply(off);
            events.add(c);
         }
      }

      this.rotate();
      this.remove(p);
      synchronized(this.eventLock) {
         this.producerMap.remove(pid);
      }
   }

   protected final void loadDefaultSettings(EventSettings dest, String path) {
      try {
         dest.addEventDefaultSet(this.findEventDefaultSet(path));
      } catch (Exception var4) {
      }

   }

   protected final EventDefaultSet findEventDefaultSet(String path) throws Exception {
      try {
         return EventDefaultSet.find(path);
      } catch (IOException var3) {
         this.logger.log(MsgLevel.WARN, var3, "Could not read %s", new Object[]{path});
         throw var3;
      } catch (URISyntaxException var4) {
         this.logger.log(MsgLevel.WARN, var4, "Syntax error in %s", new Object[]{path});
         throw var4;
      } catch (Exception var5) {
         this.logger.log(MsgLevel.WARN, var5, "Syntax error in %s", new Object[]{path});
         throw var5;
      }
   }

   public final EventDescriptor getEvent(int eventID) throws NoSuchEventException {
      synchronized(this.eventLock) {
         EventControl e = (EventControl)this.eventsControls.get(eventID);
         if (e == null) {
            throw new NoSuchEventException(String.valueOf(eventID));
         } else {
            return e.getDescriptor();
         }
      }
   }

   public final ProducerDescriptor getProducer(int producerID) throws NoSuchProducerException {
      synchronized(this.eventLock) {
         ProducerDescriptor d = (ProducerDescriptor)this.producerMap.get(producerID);
         if (d == null) {
            throw new NoSuchProducerException(String.valueOf(producerID));
         } else {
            return d;
         }
      }
   }

   public final Collection getProducers() {
      synchronized(this.eventLock) {
         return new ArrayList(this.producerMap.values());
      }
   }

   public final Collection getEvents() {
      ArrayList result = new ArrayList();
      synchronized(this.eventLock) {
         Iterator i$ = this.eventsControls.values().iterator();

         while(i$.hasNext()) {
            EventControl e = (EventControl)i$.next();
            result.add(e.getDescriptor());
         }

         return result;
      }
   }

   public final EventHandler createHandler(JavaEventDescriptor d, Class receiverType, Map pools) throws InvalidEventDefinitionException {
      EventHandlerCreator c = new EventHandlerCreator(this, d, receiverType, pools);
      return c.createHandler();
   }

   public final void started(Recording rec) {
      this.settings.update();

      assert !Thread.holdsLock(this.eventLock);

      synchronized(this.startBarrier) {
         if (rec.isToDisk()) {
            int n = ++this.numRealRecordings;

            assert n >= 1;

            if (n == 1) {
               this.logger.debug("First recording starting...");
               this.start(rec.isClone());
            }

            if (n > 1) {
               this.logger.debug("Recording starting, issuing buffer rotation...");
               this.rotate();
            }
         }

      }
   }

   public final void stopped(Recording rec) {
      assert !Thread.holdsLock(this.eventLock);

      synchronized(this.startBarrier) {
         if (rec.isToDisk()) {
            int n = --this.numRealRecordings;

            assert n >= 0;

            if (n == 0) {
               this.logger.debug("Last recording stopping...");
               this.stop();
            }

            if (n > 0) {
               this.logger.debug("Recording stopping, issuing buffer rotation...");
               this.rotate();
            }
         }
      }

      boolean isCont;
      synchronized(this.eventLock) {
         isCont = this.defaultRecording == rec;
      }

      this.settings.update();
      if (isCont) {
         this.disableDefaultRecording();
      }

   }

   public final FlightRecorder getMBean() {
      return this.mbeanObject;
   }

   protected abstract void add(ProducerDescriptor var1);

   protected abstract void remove(ProducerDescriptor var1);

   protected abstract void rotate();

   protected abstract void start(boolean var1);

   protected abstract void stop();

   protected abstract void shutdown();

   public abstract ByteBuffer getThreadBuffer(int var1);

   public abstract void releaseThreadBuffer(ByteBuffer var1, boolean var2);

   public abstract long counterTime();

   public abstract long nanoToCounter(long var1);

   public abstract long stackTraceID(int var1);

   public abstract long classID(Class var1);

   public abstract int threadID();

   public abstract JFRStats getJFRStats();

   public int nextID() {
      return this.idCounter.incrementAndGet();
   }

   public Timer getTimer() {
      return this.timer;
   }

   protected final void onNewChunk() {
      this.metaProducer.onNewChunk();
   }

   protected final void chunkDone() {
      this.metaProducer.chunkDone();
   }

   protected final Repository getRepository() {
      return this.repository;
   }

   protected final void addChunk(RepositoryChunk chunk) {
      synchronized(this.eventLock) {
         Iterator i$ = this.recordings.values().iterator();

         while(i$.hasNext()) {
            Recording r = (Recording)i$.next();
            r.addChunk(chunk);
         }

      }
   }

   final Recording getRecording(long id) throws NoSuchRecordingException {
      synchronized(this.eventLock) {
         Recording r = (Recording)this.recordings.get(id);
         if (r == null) {
            throw new NoSuchRecordingException();
         } else {
            return r;
         }
      }
   }

   final Collection getRecordings() {
      return new ArrayList(this.recordings.values());
   }

   private final Recording createRecording(String name, long id, boolean isClone) throws InstanceAlreadyExistsException {
      if (name == null) {
         name = "Recording " + id;
      }

      synchronized(this.eventLock) {
         if (!this.active) {
            throw new UnsupportedOperationException("Shutdown");
         } else if (this.recordings.containsKey(id)) {
            throw new InstanceAlreadyExistsException(name);
         } else {
            Recording r = new Recording(this.logger, this.timer, name, id, this.settings.subAggregator(), isClone, new RecordingObserver[]{this});
            this.recordings.put(id, r);
            return r;
         }
      }
   }

   final Recording createRecording(String name) {
      try {
         return this.createRecording(name, this.recordingCounter.incrementAndGet(), false);
      } catch (InstanceAlreadyExistsException var3) {
         throw cannotHappen(var3);
      }
   }

   final Recording cloneRecording(Recording r, String newName, boolean stop) throws IOException {
      Recording r2 = null;
      synchronized(this.eventLock) {
         try {
            r2 = this.createRecording(newName, this.recordingCounter.incrementAndGet(), true);
         } catch (InstanceAlreadyExistsException var10) {
            throw cannotHappen(var10);
         }
      }

      RecordingOptionsImpl opts = new RecordingOptionsImpl(r);
      opts.setMaxAge(0L, TimeUnit.NANOSECONDS);
      opts.setMaxSize(0L);
      r2.setOptions(r);
      if (r.isStarted()) {
         r2.start();
      }

      synchronized(this.eventLock) {
         r2.copyChunks(r);
      }

      if (stop) {
         r2.stop();
      }

      r2.setMaxAge(r.getMaxAge(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
      r2.setMaxSize(r.getMaxSize());
      return r2;
   }

   void enableDefaultRecording() {
      Recording r = null;
      synchronized(this.eventLock) {
         if (this.defaultRecording == null) {
            try {
               this.defaultRecording = this.createRecording("JRockit default", 0L, false);
            } catch (InstanceAlreadyExistsException var5) {
               throw cannotHappen(var5);
            }

            this.defaultRecording.setToDisk(this.options.defaultRecordingToDisk());
            this.defaultRecording.setMaxAge(this.options.defaultRecordingMaxAge(), TimeUnit.NANOSECONDS);
            this.defaultRecording.setMaxSize(this.options.defaultRecordingMaxSize());
            r = this.defaultRecording;
         }
      }

      if (r != null) {
         r.start();
         this.bind(r);
      }

   }

   void disableDefaultRecording() {
      Recording r = null;
      synchronized(this.eventLock) {
         r = this.defaultRecording;
         this.defaultRecording = null;
      }

      if (r != null) {
         this.release(r);
      }

   }

   void release(Recording r) {
      if (r.isRunning()) {
         try {
            r.stop();
         } catch (IllegalStateException var5) {
         } catch (IOException var6) {
            this.logger.log(MsgLevel.WARN, var6, "Exception while stopping %s", new Object[]{r});
         }
      }

      r.release();
      synchronized(this.eventLock) {
         this.recordings.remove(r.getId());
         this.unbind(r);
      }
   }

   boolean isContinuousModeRunning() {
      synchronized(this.eventLock) {
         return this.recordings.containsKey(0);
      }
   }

   void destroy() {
      try {
         Iterator i$ = this.mbeanServers.iterator();

         while(i$.hasNext()) {
            MBeanServer server = (MBeanServer)i$.next();
            server.unregisterMBean(new ObjectName("com.oracle.jrockit:type=FlightRecorder"));
         }
      } catch (InstanceNotFoundException var5) {
      } catch (MBeanRegistrationException var6) {
      } catch (MalformedObjectNameException var7) {
      }

      this.timer.cancel();
      ArrayList l;
      synchronized(this.eventLock) {
         this.active = false;
         l = new ArrayList(this.recordings.values());
      }

      Iterator i$ = l.iterator();

      while(i$.hasNext()) {
         Recording r = (Recording)i$.next();
         this.release(r);
      }

      this.mbeanObject.destroy();
      this.repository.destroy();
   }
}
