package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.InputStream;
import weblogic.socket.MuxableSocket;

public final class TpeIn extends InputStream {
   private int elevel = 0;
   private InputStream in = null;
   private tplle lle = null;
   private boolean opened = false;
   private byte[] myTuxBuf;
   private int myMark = 0;
   private int myOffset = 0;
   private int myNeedToRead = 32;
   private int myProtocol = 10;
   private boolean parsedMetahdr = false;
   private boolean gotEOF = false;
   private MuxableSocket myMuxableSocket = null;

   public TpeIn() {
      this.myTuxBuf = new byte[32];
   }

   public TpeIn(InputStream io) {
      this.in = io;
      this.opened = true;
      this.myTuxBuf = new byte[32];
   }

   public int setElevel(int level) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/setELevel(" + level + ")/");
      }

      if (level != 1 && level != 2 && level != 32 && level != 4) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/setElevel/20/1");
         }

         return 1;
      } else {
         this.elevel = level;
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/setElevel/10/0");
         }

         return 0;
      }
   }

   public void setLLE(tplle l) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/setLLE/");
      }

      this.lle = l;
      if (traceEnabled) {
         ntrace.doTrace("]/TpeIn/setLLE/10/");
      }

   }

   public void setInputStream(InputStream i) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/setInputStream/");
      }

      this.in = i;
      this.opened = true;
      if (traceEnabled) {
         ntrace.doTrace("]/TpeIn/setInputStream/10/");
      }

   }

   public void setProtocol(int protocol) {
      this.myProtocol = protocol;
   }

   public void setMuxableSocket(MuxableSocket sock) {
      this.myMuxableSocket = sock;
   }

   public void close() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/close/");
      }

      if (!this.opened) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/close/10/");
         }

      } else {
         if (this.in != null) {
            this.in.close();
            this.in = null;
         }

         this.lle = null;
         this.opened = false;
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/close/10/");
         }

      }
   }

   private boolean readTuxedoMessage(boolean doBlock) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/readTuxedoMessage/" + doBlock);
      }

      if (this.in == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/readTuxedoMessage/10/false");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("/TpeIn/readTuxedoMessage/myNeedToRead=" + this.myNeedToRead + "/myOffset=" + this.myOffset + "/myMark=" + this.myMark + "/myProtocol=" + this.myProtocol + "/parsedMetahdr=" + this.parsedMetahdr);
         }

         if (this.myNeedToRead == 0 && this.myMark == this.myOffset) {
            if (traceEnabled) {
               ntrace.doTrace("/TpeIn/readTuxedoMessage/resetting mark for next Tuxedo buffer");
            }

            this.myNeedToRead = 32;
            this.myOffset = 0;
            this.myMark = 0;
            this.parsedMetahdr = false;
         }

         if (this.gotEOF) {
            if (this.myMark < this.myOffset) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TpeIn/readTuxedoMessage/20/true");
               }

               return true;
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("]/TpeIn/readTuxedoMessage/30/false");
               }

               return false;
            }
         } else if (this.myNeedToRead == 0) {
            if (traceEnabled) {
               ntrace.doTrace("]/TpeIn/readTuxedoMessage/40/true");
            }

            return true;
         } else {
            int toRead;
            try {
               int available;
               if ((available = this.in.available()) == 0 && !doBlock) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/readTuxedoMessage/50/true");
                  }

                  return true;
               }

               toRead = this.myNeedToRead < available ? this.myNeedToRead : (doBlock ? this.myNeedToRead : available);
            } catch (IOException var16) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/readTuxedoMessage/60/" + var16);
               }

               throw var16;
            }

            if (traceEnabled) {
               ntrace.doTrace("/TpeIn/readTuxedoMessage/toRead=" + toRead);
            }

            int received;
            int lcv;
            try {
               if (this.myMuxableSocket != null) {
                  synchronized(this.myMuxableSocket) {
                     while(!this.myMuxableSocket.isMessageComplete()) {
                        if (traceEnabled) {
                           ntrace.doTrace("/TpeIn/readTuxedoMessage/message is not complete, wait");
                        }

                        try {
                           this.myMuxableSocket.wait();
                        } catch (InterruptedException var12) {
                        }
                     }
                  }

                  lcv = this.myMuxableSocket.getBuffer().length;
                  if (this.myTuxBuf.length < lcv) {
                     byte[] tbuf = new byte[lcv];
                     System.arraycopy(this.myTuxBuf, 0, tbuf, 0, this.myTuxBuf.length);
                     this.myTuxBuf = tbuf;
                  }

                  received = this.in.read(this.myTuxBuf, this.myOffset, lcv);
               } else {
                  received = this.in.read(this.myTuxBuf, this.myOffset, toRead);
               }
            } catch (IOException var15) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/readTuxedoMessage/70/" + var15);
               }

               throw var15;
            }

            if (received == -1) {
               this.gotEOF = true;
               if (this.myMark < this.myOffset) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/readTuxedoMessage/80/true");
                  }

                  return true;
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/readTuxedoMessage/90/false");
                  }

                  return false;
               }
            } else if (this.lle != null && this.elevel != 0 && this.lle.crypGetRBuf(this.myTuxBuf, this.myOffset, received) != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/readTuxedoMessage/100/");
               }

               throw new IOException("ERROR: Unable to LLE decrypt network packet");
            } else {
               this.myOffset += received;
               this.myNeedToRead -= received;
               if (traceEnabled) {
                  ntrace.doTrace("/TpeIn/readTuxedoMessage/myNeedToRead=" + this.myNeedToRead + "/myOffset=" + this.myOffset + "/myMark=" + this.myMark);
               }

               if (ntrace.isTraceEnabled(32)) {
                  new TDumpByte("myTuxBuf", this.myTuxBuf);
               }

               if (this.myNeedToRead != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/readTuxedoMessage/110/true");
                  }

                  return true;
               } else if (this.parsedMetahdr) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/readTuxedoMessage/120/true");
                  }

                  return true;
               } else {
                  int size;
                  if (this.myProtocol <= 13) {
                     if (Utilities.baReadInt(this.myTuxBuf, 0) != 1938831426) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/Tpein/readTuxedoMessage/125/Invalid MagicNumber");
                        }

                        throw new IOException("ERROR: Invalid TDOMAIN packet magic number");
                     }

                     size = Utilities.baReadInt(this.myTuxBuf, 28);
                  } else {
                     size = Utilities.baReadInt(this.myTuxBuf, 16);
                  }

                  if (size < 32) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TpeIn/readTuxedoMessage/130/size=" + size);
                     }

                     throw new IOException("ERROR: Invalid TDOMAIN packet found");
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("/TpeIn/readTuxedoMessage/size=" + size);
                     }

                     if (this.myTuxBuf.length < size) {
                        byte[] tmp = new byte[size];

                        for(lcv = 0; lcv < 32; ++lcv) {
                           tmp[lcv] = this.myTuxBuf[lcv];
                        }

                        this.myTuxBuf = tmp;
                     }

                     this.myNeedToRead = size - 32;
                     this.parsedMetahdr = true;

                     try {
                        if (!this.readTuxedoMessage(false)) {
                           if (traceEnabled) {
                              ntrace.doTrace("]/TpeIn/readTuxedoMessage/130/false");
                           }

                           return false;
                        }
                     } catch (IOException var13) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/TpeIn/readTuxedoMessage/140/" + var13);
                        }

                        throw var13;
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/TpeIn/readTuxedoMessage/150/true");
                     }

                     return true;
                  }
               }
            }
         }
      }
   }

   public int read() throws IOException {
      boolean traceEnabled = ntrace.getTraceLevel() == 55101;
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/read()/");
      }

      if (this.in == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/read()/10/-1");
         }

         return -1;
      } else {
         if (this.myMark >= this.myOffset) {
            try {
               if (!this.readTuxedoMessage(true)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/read()/20/-1");
                  }

                  return -1;
               }
            } catch (IOException var4) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/read()/30/" + var4);
               }

               throw var4;
            }

            if (this.myMark >= this.myOffset) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/read()/40/");
               }

               throw new IOException("ERROR: Invalid internal state: " + this.myMark + "/" + this.myOffset);
            }
         }

         int ret = this.myTuxBuf[this.myMark] & 255;
         ++this.myMark;
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/read()/50/" + ret);
         }

         return ret;
      }
   }

   public int read(byte[] b) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/read(b)/" + b.length);
      }

      int ret;
      try {
         ret = this.read(b, 0, b.length);
      } catch (IOException var5) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TpeIn/read(b)/10/" + var5);
         }

         throw var5;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TpeIn/read(b)/20/" + ret);
      }

      return ret;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/read(b)/" + b.length + "/" + off + "/" + len);
      }

      if (this.in != null && b != null && off >= 0 && len >= 0 && off + len <= b.length) {
         if (b.length == 0) {
            if (traceEnabled) {
               ntrace.doTrace("]/TpeIn/read(b)/20/0");
            }

            return 0;
         } else if (this.myMark + len <= this.myOffset) {
            System.arraycopy(this.myTuxBuf, this.myMark, b, off, len);
            this.myMark += len;
            if (traceEnabled) {
               ntrace.doTrace("]/TpeIn/read(b)/30/" + len);
            }

            return len;
         } else {
            boolean doBlock = this.myMark >= this.myOffset;

            try {
               if (!this.readTuxedoMessage(doBlock)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/read(b)/40/-1");
                  }

                  return -1;
               }
            } catch (IOException var8) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/read(b)/50/" + var8);
               }

               throw var8;
            }

            if (this.myMark >= this.myOffset) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/read(b)/60/");
               }

               throw new IOException("ERROR: Invalid internal state: " + this.myMark + "/" + this.myOffset);
            } else {
               int copySize = this.myOffset - this.myMark;
               if (len < copySize) {
                  copySize = len;
               }

               System.arraycopy(this.myTuxBuf, this.myMark, b, off, copySize);
               this.myMark += copySize;
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/read()/70/" + copySize);
               }

               return copySize;
            }
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/read(b)/10/-1");
         }

         return -1;
      }
   }

   public long skip(long n) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/skip(" + n + ")/");
      }

      if (this.in == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/skip/10/0");
         }

         return 0L;
      } else {
         int integer_n = (int)n;
         if (this.myMark + integer_n <= this.myOffset) {
            this.myMark += integer_n;
            if (traceEnabled) {
               ntrace.doTrace("]/TpeIn/skip/20/" + n);
            }

            return n;
         } else {
            try {
               if (!this.readTuxedoMessage(false)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/skip/30/-1");
                  }

                  return -1L;
               }
            } catch (IOException var7) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/skip/40/" + var7);
               }

               throw var7;
            }

            int copySize = this.myOffset - this.myMark;
            if (integer_n < copySize) {
               copySize = integer_n;
            }

            this.myMark += copySize;
            if (traceEnabled) {
               ntrace.doTrace("*]/TpeIn/read()/50/" + copySize);
            }

            return (long)copySize;
         }
      }
   }

   public int available() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/available/");
      }

      if (this.in == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TpeIn/available/10/0");
         }

         return 0;
      } else {
         int ret = this.myOffset - this.myMark;
         if (ret > 0) {
            if (traceEnabled) {
               ntrace.doTrace("]/TpeIn/available/20/" + ret);
            }

            return ret;
         } else {
            try {
               if (!this.readTuxedoMessage(false)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TpeIn/skip/30/0");
                  }

                  return 0;
               }
            } catch (IOException var4) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TpeIn/skip/40/" + var4);
               }

               throw var4;
            }

            ret = this.myOffset - this.myMark;
            if (traceEnabled) {
               ntrace.doTrace("]/TpeIn/available/50/" + ret);
            }

            return ret;
         }
      }
   }

   public void mark(int readlimit) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/mark/");
         ntrace.doTrace("]/TpeIn/mark/");
      }

   }

   public void reset() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/reset/");
         ntrace.doTrace("]/TpeIn/reset/");
      }

   }

   public boolean markSupported() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TpeIn/markSupported/");
         ntrace.doTrace("]/TpeIn/markSupported/false");
      }

      return false;
   }
}
