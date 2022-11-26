package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.IOException;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.NO_RESOURCES;
import org.omg.CORBA.NO_RESPONSE;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA.UNKNOWN;
import weblogic.iiop.Connection;
import weblogic.iiop.ConnectionManager;
import weblogic.tgiop.TGIOPConnection;
import weblogic.utils.io.Chunk;
import weblogic.wtc.gwt.MethodParameters;

public final class TGIOPUtil {
   public static final int TPTCMPASSTHRU = 1;
   public static final int TPSELF = 1;
   public static final int TPBIND = 1;

   public static void routeSetHost(tfmh msg, String host, int hostlen, short port, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/TGIOPUtil/routeSetHost/0");
      }

      if (flags != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TGIOPUtil/routeSetHost/10");
         }

         throw new TPException(4);
      } else {
         msg.callout = null;
         RouteTcb currRouteTcb;
         if (msg.route != null) {
            currRouteTcb = (RouteTcb)msg.route.body;
            if (msg.tdom_vals != null) {
               TdomValsTcb currTdomValsTcb = (TdomValsTcb)msg.tdom_vals.body;
               currRouteTcb.setFlags((short)(currRouteTcb.getFlags() | 1));
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TGIOPUtil/routeSetHost/20");
            }

         } else {
            msg.route = new tcm((short)9, new RouteTcb(hostlen));
            currRouteTcb = (RouteTcb)msg.route.body;
            currRouteTcb.setPort(port);
            if (msg.tdom_vals != null) {
               currRouteTcb.setFlags((short)(currRouteTcb.getFlags() | 1));
            }

            if (host == null) {
               currRouteTcb.setSvcTmidInd((short)1);
            } else {
               currRouteTcb.setHost(host);
               currRouteTcb.setHostLen(hostlen);
               int val = (hostlen - 1) / 4 + 1;
               currRouteTcb.setSvcTmidInd((short)val);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TGIOPUtil/routeSetHost/30");
            }

         }
      }
   }

   public static final int getShortBigEndian(byte[] buf, int pos) {
      int k = buf[pos] << 8 & '\uff00';
      int l = buf[pos + 1] & 255;
      return k | l;
   }

   public static final int getShortLittleEndian(byte[] buf, int pos) {
      int k = buf[pos + 1] << 8 & '\uff00';
      int l = buf[pos] & 255;
      return k | l;
   }

   public static final int extractLong(DataInputStream ds, int byteOrder) throws IOException {
      byte[] lenBuf = new byte[4];
      ds.readFully(lenBuf);
      int val;
      if (byteOrder == 0) {
         val = MessageHeaderUtils.getIntBigEndian(lenBuf, 0);
      } else {
         val = MessageHeaderUtils.getIntLittleEndian(lenBuf, 0);
      }

      return val;
   }

   public static final short extractShort(DataInputStream ds, int byteOrder) throws IOException {
      byte[] lenBuf = new byte[2];
      ds.readFully(lenBuf);
      short val;
      if (byteOrder == 0) {
         val = (short)getShortBigEndian(lenBuf, 0);
      } else {
         val = (short)getShortLittleEndian(lenBuf, 0);
      }

      return val;
   }

   public static void calloutSet(tfmh msg, Objinfo objinfo, Objrecv currObjrecv, int flags) throws TPException {
      String host = null;
      int hostlen = 1;
      int port = 0;
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/TGIOPUtil/calloutSet/0");
      }

      if (flags == 0 && objinfo != null) {
         if (msg.callout != null) {
            if (traceEnabled) {
               ntrace.doTrace("]/TGIOPUtil/calloutSet/20");
            }

         } else {
            msg.callout = new tcm((short)16, new CalloutTcb());
            CalloutTcb currCalloutTcb = (CalloutTcb)msg.callout.body;
            if (msg.tdom_vals != null) {
               currCalloutTcb.setFlags(currCalloutTcb.getFlags() | 1);
            }

            String domVal = currCalloutTcb.getSrc().getDomain();
            currCalloutTcb.setSrc(new ClientInfo(objinfo.getSendSrcCltinfo()));
            currCalloutTcb.getSrc().setVersion(1);
            currCalloutTcb.getSrc().setDomain(domVal);
            domVal = currCalloutTcb.getDest().getDomain();
            currCalloutTcb.setDest(new ClientInfo(objinfo.getCltinfo()));
            currCalloutTcb.getDest().setVersion(1);
            currCalloutTcb.getDest().setDomain(domVal);
            currCalloutTcb.setConnGen(objinfo.getConnGen());
            currCalloutTcb.setConnId(objinfo.getConnId());
            if (currObjrecv != null) {
               host = currObjrecv.getHost();
               hostlen = host.length();
               port = currObjrecv.getPort();
            }

            if (host != null) {
               currCalloutTcb.setHostlen(hostlen);
               currCalloutTcb.setHost(new String(host));
            } else {
               currCalloutTcb.setHostlen(1);
               currCalloutTcb.setHost(new String(""));
            }

            currCalloutTcb.setPort(port);
            msg.route = null;
            if (traceEnabled) {
               ntrace.doTrace("]/TGIOPUtil/calloutSet/40");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/TGIOPUtil/calloutSet/10");
         }

         throw new TPException(4);
      }
   }

   public static void injectMsgIntoRMI(tfmh tmmsg, MethodParameters currMethodParms) throws IOException {
      Chunk tail;
      Chunk head = tail = Chunk.getChunk();
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/TGIOPUtil/injectMsgIntoRMI/0/" + tmmsg + "/" + currMethodParms);
      }

      TypedTGIOP tgiopBuf = (TypedTGIOP)((UserTcb)tmmsg.user.body).user_data;
      int remaining = tgiopBuf.send_size;
      if (traceEnabled) {
         ntrace.doTrace("/TGIOPUtil/injectMsgIntoRMI/10/remaining = " + remaining);
      }

      int toCopy;
      for(int tpos = 0; remaining > 0; remaining -= toCopy) {
         if (tail.end == Chunk.CHUNK_SIZE) {
            tail.next = Chunk.getChunk();
            tail = tail.next;
         }

         toCopy = Math.min(remaining, Chunk.CHUNK_SIZE - tail.end);
         System.arraycopy(tgiopBuf.tgiop, tpos, tail.buf, tail.end, toCopy);
         tpos += toCopy;
         tail.end += toCopy;
      }

      if (traceEnabled) {
         ntrace.doTrace("/TGIOPUtil/injectMsgIntoRMI/20");
      }

      Connection c = new TGIOPConnection(currMethodParms);
      ConnectionManager.dispatch(c, head);
      if (traceEnabled) {
         ntrace.doTrace("]/TGIOPUtil/injectMsgIntoRMI/30");
      }

   }

   public static Exception mapTPError(int tperrno) {
      Object exc;
      switch (tperrno) {
         case 1:
            exc = new TRANSACTION_ROLLEDBACK();
            break;
         case 2:
         case 4:
         case 9:
         case 11:
         case 12:
         case 16:
         case 19:
         case 20:
         case 21:
         case 22:
            exc = new INTERNAL();
            break;
         case 3:
         case 7:
         case 15:
            exc = new TRANSIENT();
            break;
         case 5:
            exc = new NO_RESOURCES();
            break;
         case 6:
            exc = new NO_IMPLEMENT();
            break;
         case 8:
            exc = new NO_PERMISSION();
            break;
         case 10:
            exc = new OBJ_ADAPTER();
            break;
         case 13:
            exc = new NO_RESPONSE();
            break;
         case 14:
            exc = new INVALID_TRANSACTION();
            break;
         case 17:
         case 18:
            exc = new MARSHAL();
            break;
         case 23:
            exc = new INITIALIZE();
            break;
         default:
            exc = new UNKNOWN();
      }

      return (Exception)exc;
   }
}
