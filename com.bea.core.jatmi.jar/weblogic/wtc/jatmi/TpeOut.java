package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

public final class TpeOut extends OutputStream {
   private int elevel = 0;
   private tplle lle = null;
   private OutputStream out = null;
   private byte[] myTuxBuf;
   private int myMark = 0;
   private int myNeedToWrite = 32;
   private int myProtocol = 10;
   private boolean parsedMetahdr = false;
   private static Timer timer = null;
   private static int timeout = -1;

   public TpeOut() {
      this.myTuxBuf = new byte[32];
   }

   public TpeOut(OutputStream o) {
      this.out = o;
      this.myTuxBuf = new byte[32];
      if (timeout == -1) {
         String str = System.getProperty("weblogic.wtc.socket.write.timeout");
         if (str == null) {
            timeout = 0;
         } else {
            try {
               if (Integer.parseInt(str) > 0) {
                  timeout = Integer.parseInt(str);
                  timer = new Timer(true);
               }
            } catch (Exception var4) {
               timeout = 0;
            }
         }
      }

   }

   public int setElevel(int level) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/setElevel/");
      }

      if (level != 1 && level != 2 && level != 32 && level != 4) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeOut/setElevel/20/1");
         }

         return 1;
      } else {
         this.elevel = level;
         if (traceEnabled) {
            ntrace.doTrace("]/TpeOut/setElevel/10/0");
         }

         return 0;
      }
   }

   public void setLLE(tplle l) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/setLLE/");
      }

      this.lle = l;
      if (traceEnabled) {
         ntrace.doTrace("]/TpeOut/setLLE/10/");
      }

   }

   public void setOutputStream(OutputStream o) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/setOutputStream/");
      }

      this.out = o;
      if (traceEnabled) {
         ntrace.doTrace("]/TpeOut/setOutputStream/10/");
      }

   }

   public void setProtocol(int protocol) {
      this.myProtocol = protocol;
   }

   public void close() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/close/");
      }

      if (this.out != null) {
         this.out.close();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TpeOut/close/10/");
      }

   }

   public void flush() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/flush/");
      }

      if (this.out != null) {
         this.out.flush();
      }

      if (traceEnabled) {
         ntrace.doTrace("</TpeOut/flush/10/");
      }

   }

   private void writeTuxedoMessage() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/writeTuxedoMessage/");
      }

      if (this.out == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeOut/writeTuxedoMessage/10/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("/TpeOut/writeTuxedoMessage/myNeedToWrite=" + this.myNeedToWrite + "/myMark=" + this.myMark + "/myProtocol=" + this.myProtocol + "/parsedMetahdr=" + this.parsedMetahdr);
         }

         if (this.parsedMetahdr && this.myMark >= this.myNeedToWrite) {
            if (this.elevel > 0 && this.lle != null) {
               this.lle.crypGetSBuf(this.myTuxBuf, this.myNeedToWrite);
            }

            try {
               if (timer != null) {
                  MonitorTask task = new MonitorTask(this.out);
                  timer.schedule(task, (long)(timeout * 1000));
                  this.out.write(this.myTuxBuf, 0, this.myNeedToWrite);
                  task.enableFlag();
               } else {
                  this.out.write(this.myTuxBuf, 0, this.myNeedToWrite);
               }
            } catch (IOException var4) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeOut/writeTuxedoMessage/30/" + var4);
               }

               throw var4;
            }

            this.myMark = 0;
            this.myNeedToWrite = 32;
            this.parsedMetahdr = false;
            if (traceEnabled) {
               ntrace.doTrace("*]/TpeOut/writeTuxedoMessage/40/");
            }

         } else if (this.parsedMetahdr) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TpeOut/writeTuxedoMessage/50/");
            }

         } else if (this.myMark < this.myNeedToWrite) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TpeOut/writeTuxedoMessage/60/");
            }

         } else {
            int size;
            if (this.myProtocol <= 13) {
               size = Utilities.baReadInt(this.myTuxBuf, 28);
            } else {
               size = Utilities.baReadInt(this.myTuxBuf, 16);
            }

            if (this.myTuxBuf.length < size) {
               byte[] tmp = new byte[size];
               System.arraycopy(this.myTuxBuf, 0, tmp, 0, 32);
               this.myTuxBuf = tmp;
            }

            this.parsedMetahdr = true;
            this.myNeedToWrite = size;
            if (traceEnabled) {
               ntrace.doTrace("]/TpeOut/writeTuxedoMessage/70/");
            }

         }
      }
   }

   public void write(byte[] b) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/write(b)/");
      }

      try {
         this.write(b, 0, b.length);
      } catch (IOException var4) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeOut/write/10/" + var4);
         }

         throw var4;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TpeOut/write/20/");
      }

   }

   public void write(byte[] b, int off, int len) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/write(b)/");
      }

      int b_offset = off;
      int bytes_left = len;

      while(bytes_left != 0) {
         int needBytes = this.myNeedToWrite - this.myMark;
         int toCopy = needBytes < bytes_left ? needBytes : bytes_left;
         if (toCopy > 0) {
            System.arraycopy(b, b_offset, this.myTuxBuf, this.myMark, toCopy);
            this.myMark += toCopy;
            b_offset += toCopy;
            bytes_left -= toCopy;
         }

         if (this.myMark < this.myNeedToWrite) {
            if (traceEnabled) {
               ntrace.doTrace("]/TpeOut/write/10/");
            }

            return;
         }

         try {
            this.writeTuxedoMessage();
         } catch (IOException var10) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TpeOut/write/20/" + var10);
            }

            throw var10;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TpeOut/write/30/");
      }

   }

   public void write(int b) throws IOException {
      boolean traceEnabled = ntrace.getTraceLevel() == 55102;
      if (traceEnabled) {
         ntrace.doTrace("[/TpeOut/write(" + b + ")/");
      }

      if (this.myMark >= this.myNeedToWrite) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TpeOut/write/10/");
         }

         throw new IOException("ERROR: Invalid state found (" + this.myMark + "/" + this.myNeedToWrite + ")");
      } else {
         this.myTuxBuf[this.myMark] = (byte)(b & 255);
         ++this.myMark;
         if (this.myMark < this.myNeedToWrite) {
            if (traceEnabled) {
               ntrace.doTrace("]/TpeOut/write/20/");
            }

         } else {
            try {
               this.writeTuxedoMessage();
            } catch (IOException var4) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeOut/write/30/" + var4);
               }

               throw var4;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TpeOut/write/40/");
            }

         }
      }
   }

   class MonitorTask extends TimerTask {
      OutputStream outputStream;
      boolean flag = false;

      public MonitorTask(OutputStream o) {
         this.outputStream = o;
      }

      public void enableFlag() {
         this.flag = true;
      }

      public void run() {
         if (!this.flag) {
            try {
               this.outputStream.close();
            } catch (IOException var2) {
               var2.printStackTrace();
            }
         }

      }
   }
}
