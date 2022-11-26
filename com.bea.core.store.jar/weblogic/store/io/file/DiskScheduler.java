package weblogic.store.io.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import weblogic.store.common.StoreRCMUtils;
import weblogic.store.io.file.direct.DirectIOManager;
import weblogic.utils.time.Timer;

public final class DiskScheduler {
   private static final boolean VERBOSE = false;
   private static final double MS_PER_MIN = 60000.0;
   private static final double[] FULL_ROTATIONS = new double[]{14.285714285714286, 11.11111111111111, 8.333333333333334, 6.0, 4.0};
   private static final int CALIBRATION_ITERATIONS = 30;
   private static final double NS_PER_MS = 1000000.0;
   private static final int SAMPLE_ITER = 2000;
   private double fullRotation;
   private boolean disabled;
   private Random rand = new Random();
   private Timer timer = Timer.createTimer();
   private long lastStop;
   private long start;
   private double bpms = 100.0;
   private double min = Double.MAX_VALUE;
   private int lastBlock;
   private int cnt;
   private double totalWrite;
   private double temp;
   private int blockSize;
   private ByteBuffer block;
   private static final int NUM_BLOCKS = 16384;

   public void calibrate(final String dirName) {
      this.disabled = Boolean.getBoolean("weblogic.store.DisableDiskScheduler");

      class CalibrateRunnable implements Runnable {
         public void run() {
            File f = null;

            try {
               f = File.createTempFile("wlds", (String)null, new File(dirName));
               DiskScheduler.this.calibrate(f);
            } catch (IOException var6) {
               DiskScheduler.this.disabled = true;
            } finally {
               if (f != null) {
                  f.delete();
               }

               if (DiskScheduler.this.block != null) {
                  DirectIOManager.getFileMemoryManager().freeDirectBuffer(DiskScheduler.this.block);
                  DiskScheduler.this.block = null;
               }

            }

         }
      }

      CalibrateRunnable runnable = new CalibrateRunnable();

      try {
         StoreRCMUtils.accountAsGlobal((Runnable)runnable);
      } catch (Exception var4) {
         throw new RuntimeException(var4.getCause());
      }
   }

   public void disable() {
      this.disabled = true;
   }

   public boolean isEnabled() {
      return !this.disabled;
   }

   private FileChannel openFile(File calibrationFile) throws IOException {
      DirectIOManager manager = DirectIOManager.getManager();
      this.blockSize = manager.checkAlignment(calibrationFile);
      FileChannel channel;
      if (this.blockSize > 0) {
         channel = manager.openBasic(calibrationFile, "rwD", false);
      } else {
         channel = manager.openBasic(calibrationFile, "rwd", false);
         this.blockSize = 512;
      }

      this.block = DirectIOManager.getFileMemoryManager().allocateDirectBuffer(this.blockSize);
      return channel;
   }

   private void calibrate(File calibrationFile) throws IOException {
      FileChannel channel = this.openFile(calibrationFile);

      for(int i = 0; i < 30; ++i) {
         this.block.rewind();
         channel.write(this.block, (long)(i * this.blockSize));
      }

      try {
         channel.write(this.block, 0L);
         this.block.rewind();
         channel.write(this.block, 0L);
         long start = this.timer.timestamp();
         int guess = 0;
         this.fullRotation = FULL_ROTATIONS[guess];
         int consecutive = 0;

         for(int i = 0; i < 30; ++i) {
            this.block.rewind();
            channel.write(this.block, (long)(i * this.blockSize));
            long now = this.timer.timestamp();
            long elapsed = now - start;
            start = now;
            if (i != 0) {
               double elapsedMS = (double)elapsed / 1000000.0;
               if (elapsedMS < FULL_ROTATIONS[guess] * 0.9) {
                  ++consecutive;
                  if (consecutive >= 3) {
                     ++guess;
                     if (guess >= FULL_ROTATIONS.length) {
                        this.disabled = true;
                        return;
                     }

                     this.fullRotation = FULL_ROTATIONS[guess];
                     consecutive = 0;
                  }
               } else {
                  consecutive = 0;
               }
            }
         }

      } finally {
         channel.close();
      }
   }

   public int getNextBlock() {
      if (this.disabled) {
         return this.lastBlock + 1;
      } else {
         long now = this.timer.timestamp();
         double delayMS = (double)(now - this.lastStop) / 1000000.0;
         int skip = (int)(this.bpms * (delayMS + this.min));
         if (skip < 1) {
            skip = 1;
         }

         skip %= 2000;
         return this.lastBlock + skip;
      }
   }

   public void start() {
      if (!this.disabled) {
         this.start = this.timer.timestamp();
      }
   }

   public void stop(int blockWritten) {
      if (this.disabled) {
         this.lastBlock = blockWritten;
      } else {
         long now = this.timer.timestamp();
         long time = now - this.start;
         if (time >= 0L) {
            this.lastStop = now;
            if (blockWritten < this.lastBlock) {
               this.lastBlock = blockWritten;
            } else {
               this.lastBlock = blockWritten;
               double elapsedMS = (double)time / 1000000.0;
               this.temp = 1.0 - this.min / elapsedMS;
               this.totalWrite += elapsedMS;
               if (elapsedMS < this.min) {
                  this.min = elapsedMS;
               }

               if (elapsedMS > this.fullRotation) {
                  ++this.bpms;
               } else {
                  if (this.rand.nextDouble() < this.temp * this.temp * this.temp && this.bpms > 0.0) {
                     --this.bpms;
                  }

               }
            }
         }
      }
   }

   public String toString() {
      if (this.disabled) {
         return "DiskScheduler DISABLED.  Write caching detected";
      } else {
         int rpm = (int)(1.0 / this.fullRotation * 60000.0);
         return "DiskScheduler " + rpm + " RPM disk";
      }
   }

   public static void main(String[] args) throws Exception {
      DiskScheduler ds = new DiskScheduler();
      ds.calibrate(".");
      System.out.println(ds.toString());
      Thread.currentThread().setPriority(10);
      File f = File.createTempFile("test", (String)null, new File("."));
      RandomAccessFile raf = new RandomAccessFile(f, "rwd");
      FileChannel channel = raf.getChannel();
      ByteBuffer buf = ByteBuffer.allocateDirect(1048576);

      int l;
      for(int wrote = 0; wrote < 16384 * ds.blockSize; wrote += l) {
         l = Math.min(1048576, 16384 * ds.blockSize - wrote);
         buf.limit(l);
         channel.write(buf, 0L);
      }

      long start = System.currentTimeMillis();
      buf.limit(ds.blockSize);

      for(int i = 1; i < Integer.MAX_VALUE; ++i) {
         int nextBlock = ds.getNextBlock() % 16384;
         int loc = nextBlock * ds.blockSize;
         buf.rewind();
         ds.start();
         channel.write(buf, (long)loc);
         ds.stop(nextBlock);
         if (i % 2000 == 0) {
            long now = System.currentTimeMillis();
            long e = now - start;
            start = now;
            System.out.println("WRITES / SEC: " + 2000000L / e);
         }
      }

      raf.close();
      f.delete();
   }
}
