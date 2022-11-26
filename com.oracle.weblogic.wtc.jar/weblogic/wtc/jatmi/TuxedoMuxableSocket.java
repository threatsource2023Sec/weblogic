package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.socket.MuxableSocket;
import weblogic.socket.NIOConnection;
import weblogic.socket.SocketInfo;
import weblogic.socket.SocketMuxer;
import weblogic.utils.io.Chunk;
import weblogic.wtc.WTCLogger;

public final class TuxedoMuxableSocket implements MuxableSocket {
   private rdsession myTuxReadSession;
   private Socket in;
   private SocketMuxer myMuxer;
   private InputStream in_stream;
   private int in_timeout;
   private boolean opened = false;
   private MuxableSocket filter = null;
   private int elevel = 0;
   private tplle lle = null;
   private int myProtocol = 10;
   private static final int INITIAL_SIZE = 1000;
   private byte[] myTuxBuf;
   private int myOffset = 0;
   private int myDecryptOffset = 0;
   private ByteArrayInputStream myInputStream;
   private DataInputStream myDataStream;
   private SocketInfo sockInfo;
   private boolean canDispatch;
   private boolean useJSSE = false;
   private static int nwmsg_size_limit = -1;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServerRuntimeMBean runtime;

   public TuxedoMuxableSocket() {
      this.myTuxBuf = new byte[1000];
      this.myInputStream = new ByteArrayInputStream(this.myTuxBuf);
      this.myInputStream.mark(0);
      this.myDataStream = new DataInputStream(this.myInputStream);
      this.myMuxer = SocketMuxer.getMuxer();

      try {
         this.myMuxer.register(this);
      } catch (IOException var2) {
      }

      this.setSocketFilter(this);
      getNWMsgSizeLimit();
      this.runtime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
   }

   public DataInputStream getInputStream() {
      return this.myDataStream;
   }

   public TuxedoMuxableSocket(Socket io, boolean useSSL) throws IOException, SocketException {
      this.in = io;
      this.in_stream = this.in.getInputStream();
      this.in_timeout = this.in.getSoTimeout();
      this.opened = true;
      this.canDispatch = false;
      this.myTuxBuf = new byte[1000];
      this.myInputStream = new ByteArrayInputStream(this.myTuxBuf);
      this.myInputStream.mark(0);
      this.myDataStream = new DataInputStream(this.myInputStream);
      this.myMuxer = SocketMuxer.getMuxer();
      if (!useSSL) {
         this.canDispatch = true;
         this.myMuxer.register(this);
      }

      this.setSocketFilter(this);
      getNWMsgSizeLimit();
      this.runtime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
   }

   public static void getNWMsgSizeLimit() {
      if (nwmsg_size_limit == -1) {
         String nwmsg_size_limit_str;
         if ((nwmsg_size_limit_str = System.getProperty("weblogic.wtc.nwmsg_size_limit")) != null) {
            nwmsg_size_limit = Integer.parseInt(nwmsg_size_limit_str);
            if (nwmsg_size_limit < 0) {
               nwmsg_size_limit = 0;
            }
         } else {
            nwmsg_size_limit = 0;
         }
      }

   }

   public void setRecvSession(rdsession recvSession) {
      this.myTuxReadSession = recvSession;
      synchronized(this) {
         this.canDispatch = true;
      }

      this.myMuxer.read(this.getSocketFilter());
   }

   public byte[] getBuffer() {
      if (this.myOffset >= this.myTuxBuf.length) {
         byte[] tmp = new byte[this.myTuxBuf.length + 1000];
         System.arraycopy(this.myTuxBuf, 0, tmp, 0, this.myTuxBuf.length);
         this.myTuxBuf = tmp;
         this.myInputStream = new ByteArrayInputStream(this.myTuxBuf);
         this.myInputStream.mark(0);
         this.myDataStream = new DataInputStream(this.myInputStream);
      }

      return this.myTuxBuf;
   }

   public int getBufferOffset() {
      return this.myOffset;
   }

   public void setBufferOffset(int offset) {
      this.myOffset = offset;
   }

   public void incrementBufferOffset(int count) {
      this.myOffset += count;
   }

   public boolean isMessageComplete() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoMuxableSocket/isMessageComplete");
      }

      if (this.useJSSE) {
         if (this.myOffset == 0 || this.myOffset < 32) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoMuxableSocket/isMessageComplete/10/return false");
            }

            return false;
         }

         int size;
         if (this.myProtocol <= 13) {
            size = com.bea.core.jatmi.common.Utilities.baReadInt(this.myTuxBuf, 28);
         } else {
            size = com.bea.core.jatmi.common.Utilities.baReadInt(this.myTuxBuf, 16);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoMuxableSocket/isMessageComplete/expect msg size=" + size);
         }

         if (this.myOffset < size) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoMuxableSocket/isMessageComplete/20/current offset " + this.myOffset + " less than actual msg size " + size + "/return false");
            }

            return false;
         }

         synchronized(this) {
            this.notifyAll();
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoMuxableSocket/isMessageComplete/30/return true");
      }

      return true;
   }

   public void dispatch() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoMuxableSocket/dispatch/");
      }

      int metahdrOffset = 0;
      if (traceEnabled) {
         ntrace.doTrace("/TuxedoMuxableSocket/dispatch/myOffset=" + this.myOffset + "/myDecryptOffset=" + this.myDecryptOffset + "/myProtocol=" + this.myProtocol);
      }

      synchronized(this) {
         if (!this.canDispatch) {
            if (traceEnabled) {
               ntrace.doTrace("/TuxedoMuxableSocket/dispatch/no dispatch yet");
            }

            this.myInputStream.reset();
            this.myMuxer.read(this.getSocketFilter());
            this.notify();
            return;
         }
      }

      int reqid = 0;

      while(true) {
         String state = this.runtime.getState();
         if (state == "RUNNING") {
            if (this.myOffset < 32) {
               this.myMuxer.read(this.getSocketFilter());
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/10");
               }

               return;
            } else {
               if (this.lle != null && this.elevel != 0 && this.myDecryptOffset < this.myOffset) {
                  if (this.lle.crypGetRBuf(this.myTuxBuf, this.myDecryptOffset, this.myOffset - this.myDecryptOffset) != 0) {
                     this.myMuxer.closeSocket(this.getSocketFilter());
                     this.opened = false;
                     if (this.myTuxReadSession != null) {
                        this.myTuxReadSession.connectionHasTerminated();
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/20");
                     }

                     return;
                  }

                  this.myDecryptOffset = this.myOffset;
               }

               this.myInputStream.reset();

               while(metahdrOffset <= this.myOffset) {
                  int data_left = this.myOffset - metahdrOffset;
                  int lcv;
                  if (data_left < 32) {
                     for(lcv = metahdrOffset; lcv < this.myOffset; ++lcv) {
                        this.myTuxBuf[lcv - metahdrOffset] = this.myTuxBuf[lcv];
                     }

                     this.myOffset = data_left;
                     this.myDecryptOffset = data_left;
                     this.myMuxer.read(this.getSocketFilter());
                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/30/");
                     }

                     return;
                  }

                  int size;
                  if (this.myProtocol <= 13) {
                     if (com.bea.core.jatmi.common.Utilities.baReadInt(this.myTuxBuf, 0) != 1938831426) {
                        this.myMuxer.closeSocket(this.getSocketFilter());
                        this.opened = false;
                        if (this.myTuxReadSession != null) {
                           this.myTuxReadSession.connectionHasTerminated();
                        }

                        if (traceEnabled) {
                           ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/35/Invalid MagicNumber");
                        }

                        WTCLogger.logErrorInvalidMagicNumber();
                        return;
                     }

                     size = com.bea.core.jatmi.common.Utilities.baReadInt(this.myTuxBuf, metahdrOffset + 28);
                  } else {
                     size = com.bea.core.jatmi.common.Utilities.baReadInt(this.myTuxBuf, metahdrOffset + 16);
                  }

                  if (size < 32) {
                     this.myMuxer.closeSocket(this.getSocketFilter());
                     this.opened = false;
                     if (this.myTuxReadSession != null) {
                        this.myTuxReadSession.connectionHasTerminated();
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/40/invalid size=" + size);
                     }

                     return;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/TuxedoMuxableSocket/dispatch/size=" + size);
                  }

                  if (nwmsg_size_limit > 0 && size > nwmsg_size_limit) {
                     this.myMuxer.closeSocket(this.getSocketFilter());
                     this.opened = false;
                     if (this.myTuxReadSession != null) {
                        this.myTuxReadSession.connectionHasTerminated();
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/45/NWMsg Size Limit Exceeds");
                     }

                     return;
                  }

                  if (size > this.myTuxBuf.length) {
                     byte[] tmp = new byte[size];
                     System.arraycopy(this.myTuxBuf, metahdrOffset, tmp, 0, data_left);
                     this.myTuxBuf = tmp;
                     this.myInputStream = new ByteArrayInputStream(this.myTuxBuf);
                     this.myInputStream.mark(0);
                     this.myDataStream = new DataInputStream(this.myInputStream);
                     this.myOffset = data_left;
                     this.myDecryptOffset = data_left;
                     this.myMuxer.read(this.getSocketFilter());
                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/50");
                     }

                     return;
                  }

                  if (size > data_left) {
                     if (metahdrOffset != 0) {
                        for(lcv = metahdrOffset; lcv < this.myOffset; ++lcv) {
                           this.myTuxBuf[lcv - metahdrOffset] = this.myTuxBuf[lcv];
                        }

                        this.myOffset = data_left;
                        this.myDecryptOffset = data_left;
                     }

                     this.myMuxer.read(this.getSocketFilter());
                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/60");
                     }

                     return;
                  }

                  tfmh tmmsg = this.myTuxReadSession.allocTfmh();
                  if (ntrace.isTraceEnabled(64)) {
                     tmmsg.dumpUData(true);
                  }

                  int returnCode;
                  try {
                     if (this.myProtocol <= 13) {
                        returnCode = tmmsg.read_dom_65_tfmh(this.myDataStream, this.myProtocol);
                     } else {
                        returnCode = tmmsg.read_tfmh(this.myDataStream);
                     }
                  } catch (IOException var15) {
                     this.myMuxer.closeSocket(this.getSocketFilter());
                     this.opened = false;
                     if (this.myTuxReadSession != null) {
                        this.myTuxReadSession.connectionHasTerminated();
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/70/" + var15);
                     }

                     return;
                  }

                  switch (returnCode) {
                     case -2:
                        WTCLogger.logDroppedMessage();
                        if (traceEnabled) {
                           ntrace.doTrace("/TuxedoMuxableSocket/dispatch/dropped message");
                        }

                        TdomTcb tdom;
                        if (tmmsg.tdom != null && (tdom = (TdomTcb)tmmsg.tdom.body) != null) {
                           reqid = tdom.get_reqid();
                        } else {
                           reqid = 0;
                        }

                        TdomTcb fail_tmmsg_tdom = new TdomTcb(3, reqid, 0, (String)null);
                        fail_tmmsg_tdom.set_diagnostic(12);
                        tfmh fail_tmmsg = new tfmh(1);
                        fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
                        tmmsg = fail_tmmsg;
                     case 0:
                        this.myTuxReadSession.dispatch(tmmsg);
                        metahdrOffset += size;
                        break;
                     case -1:
                     default:
                        this.myMuxer.closeSocket(this.getSocketFilter());
                        this.opened = false;
                        if (this.myTuxReadSession != null) {
                           this.myTuxReadSession.connectionHasTerminated();
                        }

                        if (traceEnabled) {
                           ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/90/failure=" + returnCode);
                        }

                        return;
                  }
               }

               this.myMuxer.closeSocket(this.getSocketFilter());
               this.opened = false;
               if (this.myTuxReadSession != null) {
                  this.myTuxReadSession.connectionHasTerminated();
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/100/metahdrOffset=" + metahdrOffset + "/myOffset=" + this.myOffset);
               }

               return;
            }
         }

         if (reqid == 10) {
            this.myMuxer.closeSocket(this.getSocketFilter());
            this.opened = false;
            if (this.myTuxReadSession != null) {
               this.myTuxReadSession.connectionHasTerminated();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoMuxableSocket/dispatch/WebLogicServer is not in RUNNING mode");
            }

            return;
         }

         if (traceEnabled) {
            ntrace.doTrace("/TuxedoMuxableSocket/dispatch/Wait WebLogicServer to be RUNNING mode");
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var14) {
         }

         ++reqid;
      }
   }

   public Socket getSocket() {
      return this.in;
   }

   public boolean closeSocketOnError() {
      return true;
   }

   public InputStream getSocketInputStream() {
      return this.in_stream;
   }

   public void setSoTimeout(int to) throws SocketException {
      if (to != this.in_timeout) {
         this.in.setSoTimeout(to);
      }
   }

   public boolean isClosed() {
      return !this.opened;
   }

   public void hasException(Throwable t) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoMuxableSocket/hasException/" + t);
         t.printStackTrace();
      }

      this.opened = false;
      if (this.myTuxReadSession != null) {
         this.myTuxReadSession.connectionHasTerminated();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoMuxableSocket/hasException/10");
      }

   }

   public void endOfStream() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoMuxableSocket/endOfStream/");
      }

      this.opened = false;
      if (this.myTuxReadSession != null) {
         this.myTuxReadSession.connectionHasTerminated();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoMuxableSocket/endOfStream/10");
      }

   }

   public boolean timeout() {
      this.endOfStream();
      return true;
   }

   public final boolean requestTimeout() {
      return true;
   }

   public int getIdleTimeoutMillis() {
      return 0;
   }

   public int getCompleteMessageTimeoutMillis() {
      return 0;
   }

   public int setElevel(int level) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoMuxableSocket/setELevel(" + level + ")/");
      }

      if (level != 1 && level != 2 && level != 32 && level != 4) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoMuxableSocket/setElevel/20/1");
         }

         return 1;
      } else {
         this.elevel = level;
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoMuxableSocket/setElevel/10/0");
         }

         return 0;
      }
   }

   public void setLLE(tplle l) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoMuxableSocket/setLLE/");
      }

      this.lle = l;
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoMuxableSocket/setLLE/10/");
      }

   }

   public void setProtocol(int protocol) {
      this.myProtocol = protocol;
   }

   public void close() {
      if (this.sockInfo != null) {
         this.myMuxer.closeSocket(this.getSocketFilter());
      }

      this.opened = false;
      if (this.myTuxReadSession != null) {
         this.myTuxReadSession.connectionHasTerminated();
      }

   }

   public void setSocketFilter(MuxableSocket ms) {
      this.filter = ms;
   }

   public MuxableSocket getSocketFilter() {
      return this.filter;
   }

   public SocketInfo getSocketInfo() {
      return this.sockInfo;
   }

   public void setSocketInfo(SocketInfo info) {
      this.sockInfo = info;
   }

   public boolean supportsScatteredRead() {
      return false;
   }

   public long read(NIOConnection connection) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void incrementBufferOffset(Chunk c, int bytesRead) throws MaxMessageSizeExceededException {
      if (!this.useJSSE) {
         throw new UnsupportedOperationException();
      } else {
         boolean traceEnabled = ntrace.isTraceEnabled(4);
         if (traceEnabled) {
            ntrace.doTrace("[/TuxedoMuxableSocket/incrementBufferOffset/bytesRead=" + bytesRead + "/current buffer size=" + this.myTuxBuf.length);
         }

         this.myOffset += bytesRead;
         if (traceEnabled) {
            ntrace.doTrace("/TuxedoMuxableSocket/incrementBufferOffset/got chunks, total size=" + Chunk.size(c));
         }

         int cur_offset = this.myTuxBuf.length;
         if (this.myOffset >= this.myTuxBuf.length) {
            byte[] tmp = new byte[this.myOffset + 1000];
            System.arraycopy(this.myTuxBuf, 0, tmp, 0, this.myTuxBuf.length);
            this.myTuxBuf = tmp;
            this.myInputStream = new ByteArrayInputStream(this.myTuxBuf);
            this.myInputStream.mark(0);
            this.myDataStream = new DataInputStream(this.myInputStream);
         }

         while(c != null) {
            if (c.end > 0) {
               System.arraycopy(c.buf, 0, this.myTuxBuf, cur_offset, c.end);
               cur_offset += c.end;
               c = c.next;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoMuxableSocket/incrementBufferOffset/myOffset=" + this.myOffset + "/expand buffer to " + this.myTuxBuf.length);
         }

      }
   }

   public ByteBuffer getByteBuffer() {
      ByteBuffer tmp = ByteBuffer.wrap(this.getBuffer());
      tmp.position(this.getBufferOffset());
      return tmp;
   }

   public void setUseJSSE(boolean a) {
      this.useJSSE = a;
   }
}
