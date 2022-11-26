package weblogic.wtc.corba.internal;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.wtc.gwt.MethodParameters;
import weblogic.wtc.gwt.TuxedoCorbaConnection;
import weblogic.wtc.jatmi.BEAObjectKey;
import weblogic.wtc.jatmi.BindInfo;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.ClientInfo;
import weblogic.wtc.jatmi.Objinfo;
import weblogic.wtc.jatmi.ObjinfoImpl;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TypedTGIOP;

public final class ORBSocketOutputStream extends OutputStream {
   ORBSocket orbSocket;
   TuxedoCorbaConnection tuxConnection;
   boolean closed;
   byte[] hdrBuf;
   int hdrOffset;
   TypedTGIOP tuxTGIOPBuf;
   int bufOffset;
   static final int GIOPBigEndian = 0;

   public ORBSocketOutputStream(ORBSocket s, TuxedoCorbaConnection connection) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketOutputStream");
      }

      this.orbSocket = s;
      this.tuxConnection = connection;
      this.closed = false;
      this.hdrBuf = new byte[12];
      this.hdrOffset = 0;
      this.tuxTGIOPBuf = null;
      this.bufOffset = 0;
      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketOutputStream");
      }

   }

   public synchronized void write(int b) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketOutputStream/write/" + b);
      }

      if (this.closed) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketOutputStream/write/10");
         }

         throw new IOException("Output Stream is closed");
      } else {
         if (this.hdrOffset == 12) {
            this.tuxTGIOPBuf.tgiop[this.bufOffset++] = (byte)b;
            if (this.bufOffset == this.tuxTGIOPBuf.send_size) {
               this.handleCompleteMsg(this.tuxTGIOPBuf);
            }
         } else {
            this.hdrBuf[this.hdrOffset++] = (byte)b;
            if (this.hdrOffset == 12) {
               this.handleCompleteHdr(this.hdrBuf);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ORBSocketOutputStream/write/50");
         }

      }
   }

   public synchronized void write(byte[] b, int off, int len) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketOutputStream/write/" + Utilities.prettyByteArray(b) + "/" + off + "/" + len);
      }

      if (this.closed) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketOutputStream/write/10");
         }

         throw new IOException("Output Stream is closed");
      } else {
         if (this.hdrOffset == 12) {
            this.writeMsgBody(b, off, len);
         } else {
            int maxLen = 12 - this.hdrOffset;
            int copyLen = maxLen < len ? maxLen : len;
            System.arraycopy(b, off, this.hdrBuf, this.hdrOffset, copyLen);
            this.hdrOffset += copyLen;
            if (this.hdrOffset == 12) {
               this.handleCompleteHdr(this.hdrBuf);
               if (copyLen < len) {
                  this.writeMsgBody(b, off + copyLen, len - copyLen);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ORBSocketOutputStream/write/50");
         }

      }
   }

   public void flush() throws IOException {
      if (this.closed) {
         boolean traceEnabled = ntrace.isTraceEnabled(8);
         if (traceEnabled) {
            ntrace.doTrace("[/ORBSocketOutputStream/flush");
            ntrace.doTrace("*]/ORBSocketOutputStream/flush/10");
         }

         throw new IOException("Output Stream is closed");
      }
   }

   public void close() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketOutputStream/close");
      }

      this.closed = true;
      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketOutputStream/close/30");
      }

   }

   private void writeMsgBody(byte[] b, int off, int len) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketOutputStream/writeMsgBody/" + Utilities.prettyByteArray(b) + "/" + off + "/" + len);
      }

      int maxLen = this.tuxTGIOPBuf.send_size - this.bufOffset;
      int copyLen = maxLen < len ? maxLen : len;
      System.arraycopy(b, off, this.tuxTGIOPBuf.tgiop, this.bufOffset, copyLen);
      this.bufOffset += copyLen;
      if (this.bufOffset == this.tuxTGIOPBuf.send_size) {
         this.handleCompleteMsg(this.tuxTGIOPBuf);
         if (copyLen < len) {
            this.write(b, off + copyLen, len - copyLen);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketOutputStream/writeMsgBody/50");
      }

   }

   private void handleCompleteHdr(byte[] buf) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketOutputStream/handleCompleteHdr/" + Utilities.prettyByteArray(buf));
      }

      if (buf[0] == 71 && buf[1] == 73 && buf[2] == 79 && buf[3] == 80) {
         int b1;
         int b2;
         int b3;
         int b4;
         if (buf[6] == 0) {
            b1 = buf[8] << 24 & -16777216;
            b2 = buf[9] << 16 & 16711680;
            b3 = buf[10] << 8 & '\uff00';
            b4 = buf[11] << 0 & 255;
         } else {
            b1 = buf[11] << 24 & -16777216;
            b2 = buf[10] << 16 & 16711680;
            b3 = buf[9] << 8 & '\uff00';
            b4 = buf[8] << 0 & 255;
         }

         int size = (b1 | b2 | b3 | b4) + 12;
         this.tuxTGIOPBuf = new TypedTGIOP(size);
         System.arraycopy(buf, 0, this.tuxTGIOPBuf.tgiop, 0, 12);
         this.bufOffset = 12;
         if (traceEnabled) {
            ntrace.doTrace("]/ORBSocketOutputStream/handleCompleteHdr/50");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketOutputStream/handleCompleteHdr/10");
         }

         throw new IOException("Invalid GIOP message");
      }
   }

   private void handleCompleteMsg(TypedTGIOP msg) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketOutputStream/handleCompleteMsg/" + msg);
      }

      try {
         this.hdrOffset = 0;
         BEAObjectKey objKey = new BEAObjectKey(msg);
         Objinfo objInfo = new ObjinfoImpl(objKey, (ClientInfo)null, (BindInfo)null, 0);
         int flags = 0;
         int msgType = objKey.getMsgType();
         if (msgType == 2 || msgType == 5 || msgType == 6 || msgType == 0 && objKey.getResponseExpected() == 0) {
            flags |= 4;
         }

         CallDescriptor cd = this.tuxConnection.tpMethodReq(msg, objInfo, (MethodParameters)null, flags);
         if ((flags & 4) == 0) {
            this.orbSocket.addOutstandingRequest(cd, objKey);
         }
      } catch (TPException var8) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketOutputStream/handleCompleteMsg/20/" + var8);
         }

         throw new IOException("Could not send message via WTC : " + var8);
      } catch (Exception var9) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketOutputStream/handleCompleteMsg/30/" + var9);
            var9.printStackTrace();
         }

         throw new IOException("Could not send message via WTC : " + var9);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketOutputStream/handleCompleteMsg/50");
      }

   }
}
