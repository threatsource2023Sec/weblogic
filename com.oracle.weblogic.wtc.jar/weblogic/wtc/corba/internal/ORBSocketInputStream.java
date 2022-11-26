package weblogic.wtc.corba.internal;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import weblogic.wtc.gwt.TuxedoCorbaConnection;
import weblogic.wtc.jatmi.BEAObjectKey;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.ReqOid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.TypedTGIOP;

public final class ORBSocketInputStream extends InputStream {
   ORBSocket orbSocket;
   TuxedoCorbaConnection tuxConnection;
   boolean closed;
   int bufLen;
   int bufOffset;
   byte[] tuxBuf;
   Reply tuxReply;
   TypedTGIOP tuxReplyBuf;

   public ORBSocketInputStream(ORBSocket s, TuxedoCorbaConnection connection) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketInputStream");
      }

      this.orbSocket = s;
      this.tuxConnection = connection;
      this.closed = false;
      this.bufOffset = 0;
      this.bufLen = 0;
      this.tuxBuf = null;
      this.tuxReply = null;
      this.tuxReplyBuf = null;
      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketInputStream");
      }

   }

   public synchronized int read() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketInputStream/read");
      }

      if (this.closed) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketInputStream/read/10");
         }

         throw new IOException("Input stream is closed");
      } else {
         if (this.bufLen == 0) {
            this.getNextReply();
         }

         if (this.bufLen == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/ORBSocketInputStream/read/20");
            }

            throw new IOException("No reply found");
         } else {
            int ret = this.tuxBuf[this.bufOffset++];
            if (this.bufOffset == this.bufLen) {
               this.bufOffset = 0;
               this.bufLen = 0;
               this.tuxBuf = null;
               this.tuxReply = null;
               this.tuxReplyBuf = null;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/ORBSocketInputStream/read/30" + ret);
            }

            return ret;
         }
      }
   }

   public synchronized int read(byte[] b, int off, int len) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketInputStream/read/" + Utilities.prettyByteArray(b) + "/" + off + "/" + len);
      }

      if (this.closed) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketInputStream/read/10");
         }

         throw new IOException("Input Stream is closed");
      } else if (len == 0) {
         return 0;
      } else {
         if (this.bufLen == 0) {
            this.getNextReply();
         }

         if (this.bufLen == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/ORBSocketInputStream/read/20");
            }

            throw new IOException("No reply found");
         } else {
            int maxLen = this.bufLen - this.bufOffset;
            int copyLen = maxLen < len ? maxLen : len;
            System.arraycopy(this.tuxBuf, this.bufOffset, b, off, copyLen);
            this.bufOffset += copyLen;
            if (this.bufOffset == this.bufLen) {
               this.bufOffset = 0;
               this.bufLen = 0;
               this.tuxBuf = null;
               this.tuxReply = null;
               this.tuxReplyBuf = null;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/ORBSocketInputStream/read/30/" + copyLen);
            }

            return copyLen;
         }
      }
   }

   public int available() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketInputStream/available");
      }

      if (this.closed) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketInputStream/available/10");
         }

         throw new IOException("Input Stream is closed");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/ORBSocketInputStream/available/30/" + (this.bufLen - this.bufOffset));
         }

         return this.bufLen - this.bufOffset;
      }
   }

   public void close() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketInputStream/close");
      }

      this.closed = true;
      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketInputStream/close/30");
      }

   }

   private void getNextReply() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketInputStream/getNextReply");
      }

      int flags = 128;

      try {
         this.tuxReply = this.tuxConnection.tpgetrply((CallDescriptor)null, flags);
         CallDescriptor cd = this.tuxReply.getCallDescriptor();
         BEAObjectKey objKey = this.orbSocket.removeOutstandingRequest(cd);
         TypedBuffer data = this.tuxReply.getReplyBuffer();
         if (data.getHintIndex() != 10 && traceEnabled) {
            ntrace.doTrace("[/ORBSocketInputStream/getNextReply/10/" + data.getHintIndex() + "/" + cd + "/" + objKey);
         }

         this.tuxReplyBuf = (TypedTGIOP)data;
         this.tuxBuf = this.tuxReplyBuf.tgiop;
         this.bufLen = this.tuxReplyBuf.send_size;
         this.bufOffset = 0;
      } catch (TPReplyException var6) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketInputStream/getNextReply/30/" + var6);
         }

         this.mapTPExceptionToCorbaException("Could not get reply via WTC : " + var6, var6.gettperrno(), var6.getExceptionReply() != null ? var6.getExceptionReply().getCallDescriptor() : null);
      } catch (TPException var7) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketInputStream/getNextReply/40/" + var7);
         }

         this.mapTPExceptionToCorbaException("Could not get reply via WTC : " + var7, var7.gettperrno(), var7.getReplyRtn() != null ? var7.getReplyRtn().getCallDescriptor() : null);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketInputStream/getNextReply/60");
      }

   }

   private void mapTPExceptionToCorbaException(String msg, int err, CallDescriptor cd) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketInputStream/mapTPExceptionToCorbaException/" + msg + "/" + err + "/" + cd);
      }

      boolean inTx = false;

      try {
         if (((ReqOid)cd).getXID() != null) {
            inTx = true;
         } else {
            CallDescriptor ocd = this.orbSocket.getOriginalCallDescriptor(cd);
            if (ocd != null && ((ReqOid)ocd).getXID() != null) {
               inTx = true;
            }
         }
      } catch (Exception var11) {
      }

      String id;
      try {
         switch (err) {
            case 1:
               id = "IDL:omg.org/CORBA/TRANSACTION_ROLLEDBACK:1.0";
               break;
            case 2:
            case 4:
            case 7:
            case 9:
            case 11:
            case 12:
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 25:
            default:
               id = "IDL:omg.org/CORBA/INTERNAL:1.0";
               break;
            case 3:
               id = "IDL:omg.org/CORBA/TRANSIENT:1.0";
               break;
            case 5:
               id = "IDL:omg.org/CORBA/NO_RESOURCE:1.0";
               break;
            case 6:
               id = "IDL:omg.org/CORBA/NO_IMPLEMENT:1.0";
               break;
            case 8:
               id = "IDL:omg.org/CORBA/NO_PERMISSION:1.0";
               break;
            case 10:
               if (inTx) {
                  id = "IDL:omg.org/CORBA/TRANSACTION_ROLLEDBACK:1.0";
               } else {
                  id = "IDL:omg.org/CORBA/OBJ_ADAPTER:1.0";
               }
               break;
            case 13:
               if (inTx) {
                  id = "IDL:omg.org/CORBA/TRANSACTION_ROLLEDBACK:1.0";
               } else {
                  id = "IDL:omg.org/CORBA/NO_RESPONSE:1.0";
               }
               break;
            case 14:
               id = "IDL:omg.org/CORBA/INVALIDTRANSACTION:1.0";
               break;
            case 15:
               id = "IDL:omg.org/CORBA/TRANSIENT:1.0";
               break;
            case 17:
            case 18:
               id = "IDL:omg.org/CORBA/MARSHAL:1.0";
               break;
            case 23:
               id = "IDL:omg.org/CORBA/BAD_INV_ORDER:1.0";
         }

         ByteArrayOutputStream bos = new ByteArrayOutputStream(100);
         DataOutputStream os = new DataOutputStream(bos);
         BEAObjectKey objKey = this.orbSocket.removeOutstandingRequest(cd);
         os.write(71);
         os.write(73);
         os.write(79);
         os.write(80);
         if (objKey != null) {
            os.write(objKey.getMajorVersion());
            os.write(objKey.getMinorVersion());
         } else {
            os.write(1);
            os.write(0);
         }

         os.write(0);
         if (objKey != null && objKey.getMsgType() == 0) {
            os.write(1);
            os.writeInt(0);
            os.writeInt(0);
            os.writeInt(objKey.getRequestId());
            os.writeInt(2);
            this.writeString(os, id);
            os.writeInt(0);
            os.writeInt(2);
         } else if (objKey != null && objKey.getMsgType() == 3) {
            os.write(4);
            os.writeInt(0);
            os.writeInt(objKey.getRequestId());
            if (objKey.getMajorVersion() == 1 && objKey.getMinorVersion() <= 1) {
               os.writeInt(0);
            } else {
               os.writeInt(4);
               this.writeString(os, id);
               os.writeInt(0);
               os.writeInt(2);
            }
         } else {
            os.write(6);
            os.writeInt(0);
         }

         this.tuxBuf = bos.toByteArray();
         this.bufLen = bos.size();
         this.bufOffset = 0;
         int msgSize = this.bufLen - 12;
         this.tuxBuf[8] = (byte)(msgSize >>> 24 & 255);
         this.tuxBuf[9] = (byte)(msgSize >>> 16 & 255);
         this.tuxBuf[10] = (byte)(msgSize >>> 8 & 255);
         this.tuxBuf[11] = (byte)(msgSize >>> 0 & 255);
      } catch (Exception var12) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocketInputStream/mapTPExceptionToCorbaException/50/" + var12);
         }

         throw new IOException("Error creating CORBA System exception: " + var12);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketInputStream/mapTPExceptionToCorbaException/60/" + id + "/" + this.bufLen + "/" + Utilities.prettyByteArray(this.tuxBuf));
      }

   }

   private void writeString(DataOutputStream os, String str) throws IOException {
      byte[] strBytes = Utilities.getEncBytes(str);
      int len = strBytes.length + 1;
      os.writeInt(len);
      os.write(strBytes);
      os.write(0);
      int rndup = len % 4;
      if (rndup > 0) {
         for(int i = 0; i < 4 - rndup; ++i) {
            os.write(0);
         }
      }

   }
}
