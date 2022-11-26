package oracle.jrockit.jfr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;
import javax.management.ObjectName;
import oracle.jrockit.jfr.settings.EventSettings;
import oracle.jrockit.log.Logger;
import oracle.jrockit.log.MsgLevel;

public final class Recording implements RecordingOptions {
   private final long id;
   private final Timer timer;
   private final String name;
   private final Logger logger;
   private final boolean isClone;
   private boolean toDisk = true;
   private boolean isStarting;
   private TimerTask startTask;
   private TimerTask stopTask;
   private Date startTime;
   private Date endTime;
   private long duration;
   private long maxSize;
   private long maxAge;
   private long size;
   private boolean done;
   private boolean started;
   private volatile boolean released;
   private boolean destinationCompressed;
   private String dest;
   private final LinkedList chunks = new LinkedList();
   private List observers = Collections.emptyList();
   private final Object chunkLock = new Object();
   private final Object observerLock = new Object();
   final Settings.Aggregator settingsAggregator;
   ObjectName objectName;

   Recording(Logger logger, Timer timer, String name, long id, Settings.Aggregator settings, boolean isClone, RecordingObserver... observers) {
      this.logger = logger;
      this.timer = timer;
      this.name = name;
      this.id = id;
      this.settingsAggregator = settings;
      this.isClone = isClone;
      if (observers.length > 0) {
         this.observers = new ArrayList(Arrays.asList(observers));
      }

   }

   private void added(RepositoryChunk c) {
      c.use();
      this.size += c.getSize();
      if (this.logger.isDebug()) {
         this.logger.log(MsgLevel.DEBUG, "Recording %s:%d added chunk %s, current size=%d", new Object[]{this.name, this.id, c.toString(), this.size});
      }

   }

   private void removed(RepositoryChunk c) {
      this.size -= c.getSize();
      if (this.logger.isDebug()) {
         this.logger.log(MsgLevel.DEBUG, "Recording %s:%d removed chunk %s, current size=%d", new Object[]{this.name, this.id, c.toString(), this.size});
      }

      c.release();
   }

   public void addChunk(RepositoryChunk chunk) {
      if (this.toDisk) {
         synchronized(this.chunkLock) {
            if (this.isStarted() && !this.done && !this.isStarting) {
               if (this.maxAge != 0L && !this.chunks.isEmpty()) {
                  Date oldest = new Date(chunk.getEndTime().getTime() - this.maxAge);

                  while(!this.chunks.isEmpty()) {
                     RepositoryChunk c = (RepositoryChunk)this.chunks.peek();
                     if (c.getEndTime().after(oldest)) {
                        break;
                     }

                     this.chunks.removeFirst();
                     this.removed(c);
                  }
               }

               this.chunks.addLast(chunk);
               this.added(chunk);

               while(this.maxSize != 0L && this.size > this.maxSize && this.chunks.size() > 1) {
                  RepositoryChunk c = (RepositoryChunk)this.chunks.removeFirst();
                  this.removed(c);
               }

            }
         }
      }
   }

   public void addObserver(RecordingObserver observer) {
      synchronized(this.observerLock) {
         List newList = new ArrayList();
         newList.addAll(this.observers);
         newList.add(observer);
         this.observers = newList;
      }
   }

   public long getActualDuration(TimeUnit unit) {
      return !this.isStopped() ? 0L : unit.convert(this.endTime.getTime() - this.startTime.getTime(), TimeUnit.MILLISECONDS);
   }

   public Date getDataEndTime() {
      synchronized(this.chunkLock) {
         if (this.chunks.isEmpty()) {
            return null;
         } else {
            RepositoryChunk chunk = (RepositoryChunk)this.chunks.getLast();
            return chunk.getEndTime();
         }
      }
   }

   public Date getDataStartTime() {
      synchronized(this.chunkLock) {
         if (this.chunks.isEmpty()) {
            return null;
         } else {
            RepositoryChunk chunk = (RepositoryChunk)this.chunks.getFirst();
            return chunk.getStartTime();
         }
      }
   }

   public long getDataSize() {
      return this.size;
   }

   public long getDuration(TimeUnit unit) {
      return unit.convert(this.duration, TimeUnit.MILLISECONDS);
   }

   public Date getEndTime() {
      return this.endTime;
   }

   public long getId() {
      return this.id;
   }

   public ReadableByteChannel getChannel() throws IOException {
      synchronized(this.chunkLock) {
         return new ChunksChannel(this.chunks);
      }
   }

   public ReadableByteChannel getChannel(Date startTime, Date endTime) throws IOException {
      synchronized(this.chunkLock) {
         ArrayList l = new ArrayList(this.chunks.size());
         Iterator i$ = this.chunks.iterator();

         while(i$.hasNext()) {
            RepositoryChunk c = (RepositoryChunk)i$.next();
            Date start = c.getStartTime();
            Date end = c.getEndTime();
            if (!end.before(startTime)) {
               if (start.compareTo(endTime) > 0) {
                  break;
               }

               l.add(c);
            }
         }

         return new ChunksChannel(l);
      }
   }

   public InputStream getInputStream() throws IOException {
      return Channels.newInputStream(this.getChannel());
   }

   public InputStream getInputStream(Date startTime, Date endTime) throws IOException {
      return Channels.newInputStream(this.getChannel(startTime, endTime));
   }

   public long getMaxAge(TimeUnit unit) {
      return unit.convert(this.maxAge, TimeUnit.MILLISECONDS);
   }

   public long getMaxSize() {
      return this.maxSize;
   }

   public String getName() {
      return this.name;
   }

   public Date getStartTime() {
      return this.startTime;
   }

   public boolean isStarted() {
      return this.started;
   }

   public boolean isStopped() {
      return this.done;
   }

   public boolean isRunning() {
      return this.isStarted() && this.endTime == null;
   }

   public boolean isReleased() {
      return this.released;
   }

   public ObjectName getObjectName() {
      return this.objectName;
   }

   public boolean isBound() {
      return this.objectName != null;
   }

   public void setToDisk(boolean b) {
      if (this.isStarted()) {
         throw new IllegalStateException("Recording is started");
      } else {
         this.toDisk = b;
      }
   }

   public boolean isToDisk() {
      return this.toDisk;
   }

   public void release() {
      synchronized(this.chunkLock) {
         Iterator i$ = this.chunks.iterator();

         while(i$.hasNext()) {
            RepositoryChunk c = (RepositoryChunk)i$.next();
            this.removed(c);
         }

         this.chunks.clear();
         this.released = true;
      }
   }

   public void removeObserver(RecordingObserver observer) {
      synchronized(this.observerLock) {
         List newList = new ArrayList();
         Iterator i$ = this.observers.iterator();

         while(i$.hasNext()) {
            RecordingObserver o = (RecordingObserver)i$.next();
            if (o != observer) {
               newList.add(o);
            }
         }

         this.observers = newList;
      }
   }

   public void setDuration(long time, TimeUnit unit) {
      this.duration = TimeUnit.MILLISECONDS.convert(time, unit);
      this.updateTimer();
   }

   public void setMaxAge(long time, TimeUnit unit) {
      this.maxAge = TimeUnit.MILLISECONDS.convert(time, unit);
   }

   public void setMaxSize(long bytes) {
      this.maxSize = bytes;
   }

   public boolean isClone() {
      return this.isClone;
   }

   public void start() {
      synchronized(this.chunkLock) {
         if (this.isStopped()) {
            throw new IllegalStateException("Recording finished");
         }

         if (this.isStarted()) {
            return;
         }

         this.started = true;
         this.isStarting = !this.isClone;
         if (this.startTime == null) {
            this.startTime = new Date();
         }

         this.updateTimer();
         this.logger.info("Starting recording " + this);
      }

      List list;
      synchronized(this.observerLock) {
         list = this.observers;
      }

      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         RecordingObserver o = (RecordingObserver)i$.next();
         o.started(this);
      }

      synchronized(this.chunkLock) {
         this.isStarting = false;
      }
   }

   public void stop() throws IOException {
      synchronized(this.chunkLock) {
         if (!this.isStarted()) {
            throw new IllegalStateException("Not started");
         }

         if (this.isStopped()) {
            return;
         }

         this.endTime = new Date();
         this.updateTimer();
      }

      List list;
      synchronized(this.observerLock) {
         list = this.observers;
      }

      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         RecordingObserver o = (RecordingObserver)i$.next();
         o.stopped(this);
      }

      if (this.dest != null) {
         try {
            this.copyTo(this.dest, this.destinationCompressed);
         } catch (IOException var4) {
            this.dest = null;
            throw var4;
         }
      }

      this.done = true;
      this.logger.info("Stopped recording " + this);
   }

   public void copyTo(String path, boolean compress) throws IOException {
      File f = new File(path);

      try {
         ChunksChannel src;
         int n;
         synchronized(this.chunkLock) {
            src = new ChunksChannel(this.chunks);
            n = this.chunks.size();
         }

         this.logger.log(MsgLevel.DEBUG, "Copy %d chunks to %s", new Object[]{n, f.getPath()});
         FileChannel out = null;

         try {
            FileOutputStream os = new FileOutputStream(f);

            try {
               if (!compress) {
                  out = os.getChannel();
                  src.transferTo(out);
                  out.force(true);
               } else {
                  GZIPOutputStream gz = new GZIPOutputStream(os);
                  ByteBuffer buf = ByteBuffer.allocate(5120);

                  while(true) {
                     buf.clear();
                     if (src.read(buf) == -1) {
                        gz.flush();
                        gz.finish();
                        gz.close();
                        break;
                     }

                     gz.write(buf.array(), 0, buf.position());
                  }
               }
            } finally {
               os.close();
            }
         } finally {
            src.close();
         }

         this.logger.log(MsgLevel.INFO, "Copied %d chunks to %s", new Object[]{n, f.getPath()});
      } catch (IOException var23) {
         this.logger.log(MsgLevel.ERROR, var23, "Could not copy to %s", new Object[]{f.getPath()});
         f.delete();
         throw var23;
      }
   }

   public String getDestination() {
      return this.dest;
   }

   public void setDestination(String path) throws IOException {
      if (path != null) {
         File destFile = new File(path);
         FileWriter fw = new FileWriter(destFile);
         fw.close();
      }

      this.dest = path;
   }

   public boolean isDestinationCompressed() {
      return this.destinationCompressed;
   }

   public void setDestinationCompressed(boolean destinationCompressed) {
      this.destinationCompressed = destinationCompressed;
   }

   public void setOptions(RecordingOptions o) throws IOException {
      this.setDestination(o.getDestination());
      this.setDuration(o.getDuration(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
      this.setMaxAge(o.getMaxAge(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
      this.setMaxSize(o.getMaxSize());
      this.setStartTime(o.getStartTime());
      this.setDestinationCompressed(o.isDestinationCompressed());
   }

   private void updateTimer() {
      if (this.isStarted()) {
         synchronized(this.chunkLock) {
            if (this.stopTask != null) {
               this.stopTask.cancel();
               this.stopTask = null;
            }

            if (!this.isStopped()) {
               if (this.duration != 0L) {
                  this.stopTask = new TimerTask() {
                     public void run() {
                        try {
                           Recording.this.stop();
                        } catch (Throwable var2) {
                           Recording.this.logger.error("Exception when stopping recording:", var2);
                        }

                     }
                  };
                  this.timer.schedule(this.stopTask, new Date(this.startTime.getTime() + this.duration));
               }

            }
         }
      }
   }

   public void setStartTime(Date startTime) {
      synchronized(this.chunkLock) {
         if (!this.isStarted()) {
            if (this.startTask != null) {
               this.startTask.cancel();
               this.startTask = null;
            }

            if (startTime != null) {
               this.startTask = new TimerTask() {
                  public void run() {
                     try {
                        Recording.this.start();
                     } catch (IllegalStateException var2) {
                        assert Recording.this.isStarted();
                     } catch (Throwable var3) {
                        Recording.this.logger.error("Error when starting recording:", var3);
                     }

                  }
               };
               this.timer.schedule(this.startTask, startTime);
            }

            this.startTime = startTime;
         }
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("[id=").append(this.id);
      if (this.name != null) {
         buf.append(", name=").append(this.name);
      }

      if (this.dest != null) {
         buf.append(", destination=").append(this.dest);
      }

      if (this.startTime != null) {
         buf.append(", start=").append(this.startTime);
      }

      if (this.endTime != null) {
         buf.append(", end=").append(this.endTime);
      }

      if (this.duration != 0L) {
         buf.append(", duration=").append(this.duration).append("ms");
      }

      if (this.maxSize != 0L) {
         buf.append(", maxSize=").append(this.maxSize);
      }

      if (this.maxAge != 0L) {
         buf.append(", maxAge=").append(this.maxAge).append("ms");
      }

      buf.append(']');
      return buf.toString();
   }

   public int hashCode() {
      return (int)this.id;
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.release();
   }

   void copyChunks(Recording other) {
      ArrayList l = new ArrayList();
      synchronized(other.chunkLock) {
         l.addAll(other.chunks);
      }

      synchronized(this.chunkLock) {
         l.addAll(this.chunks);
         this.size = 0L;
         Collections.sort(l, new Comparator() {
            public int compare(RepositoryChunk o1, RepositoryChunk o2) {
               return o1.getStartTime().compareTo(o2.getStartTime());
            }
         });
         RepositoryChunk prev = null;
         Iterator i$ = l.iterator();

         while(i$.hasNext()) {
            RepositoryChunk c = (RepositoryChunk)i$.next();
            if (c != prev) {
               this.chunks.add(c);
               this.added(c);
               prev = c;
            }
         }

      }
   }

   public EventSettings getEventSettings() {
      return this.settingsAggregator;
   }
}
