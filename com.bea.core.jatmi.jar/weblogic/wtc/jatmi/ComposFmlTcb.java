package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import weblogic.wtc.WTCLogger;

public final class ComposFmlTcb extends tcb {
   private TypedFML mybuf;
   private static final int TPQCORRID = 1;
   private static final int TPQFAILUREQ = 2;
   private static final int TPQBEFOREMSGID = 4;
   private static final int TPQGETBYMSGIDOLD = 8;
   private static final int TPQMSGID = 16;
   private static final int TPQPRIORITY = 32;
   private static final int TPQTOP = 64;
   private static final int TPQWAIT = 128;
   private static final int TPQREPLYQ = 256;
   private static final int TPQTIME_ABS = 512;
   private static final int TPQTIME_REL = 1024;
   private static final int TPQGETBYCORRIDOLD = 2048;
   private static final int TPQPEEK = 4096;
   private static final int TPQDELIVERYQOS = 8192;
   private static final int TPQREPLYQOS = 16384;
   private static final int TPQEXPTIME_ABS = 32768;
   private static final int TPQEXPTIME_REL = 65536;
   private static final int TPQEXPTIME_NONE = 131072;
   private static final int TPQGETBYMSGID = 262152;
   private static final int TPQGETBYCORRID = 526336;

   public ComposFmlTcb() {
      super((short)6);
   }

   public ComposFmlTcb(String qname, EnqueueRequest ctl) throws TPException {
      super((short)6);
      int flags = 0;
      if (qname != null && ctl != null) {
         flags |= 16;

         try {
            this.mybuf = new TypedFML();
            this.mybuf.Fchg(new FmlKey(40973, 0), qname);
            byte[] hello_mommy;
            if ((hello_mommy = ctl.getmsgid()) != null) {
               flags |= 4;
               this.mybuf.Fchg(new FmlKey(49174, 0), hello_mommy);
            }

            QueueTimeField hello_sailor;
            if ((hello_sailor = ctl.getdeq_time()) != null) {
               if (hello_sailor.isRelative()) {
                  flags |= 1024;
               } else {
                  flags |= 512;
               }

               this.mybuf.Fchg(new FmlKey(8196, 0), new Integer(hello_sailor.getTime()));
            }

            Integer hello_farmer;
            if ((hello_farmer = ctl.getpriority()) != null) {
               flags |= 32;
               this.mybuf.Fchg(new FmlKey(8194, 0), hello_farmer);
            }

            if ((hello_mommy = ctl.getcorrid()) != null) {
               flags |= 1;
               this.mybuf.Fchg(new FmlKey(49173, 0), hello_mommy);
            }

            String hello_matey;
            if ((hello_matey = ctl.getreplyqueue()) != null) {
               flags |= 256;
               this.mybuf.Fchg(new FmlKey(40972, 0), hello_matey);
            }

            if ((hello_matey = ctl.getfailurequeue()) != null) {
               flags |= 2;
               this.mybuf.Fchg(new FmlKey(40971, 0), hello_matey);
            }

            int hello_daddy = ctl.getdelivery_qos();
            flags |= 8192;
            this.mybuf.Fchg(new FmlKey(8197, 0), new Integer(hello_daddy));
            hello_daddy = ctl.getreply_qos();
            flags |= 16384;
            this.mybuf.Fchg(new FmlKey(8198, 0), new Integer(hello_daddy));
            if ((hello_sailor = ctl.getexp_time()) != null) {
               if (hello_sailor.isRelative()) {
                  flags |= 65536;
               } else {
                  flags |= 32768;
               }

               this.mybuf.Fchg(new FmlKey(8199, 0), new Integer(hello_sailor.getTime()));
            }

            if (ctl.isTPQTOP()) {
               flags |= 64;
            }

            if (ctl.isTPQEXPTIME_NONE()) {
               flags |= 131072;
            }

            this.mybuf.Fchg(new FmlKey(8195, 0), new Integer(flags));
         } catch (Ferror var11) {
            throw new TPException(12, "Unable to add field " + var11);
         }
      }
   }

   public ComposFmlTcb(String qname, byte[] msgid, byte[] corrid, boolean doWait, boolean doPeek) throws TPException {
      super((short)6);
      int flags = 0;
      if (qname != null) {
         flags |= 32;
         flags |= 16;
         flags |= 1;
         flags |= 8192;
         flags |= 16384;
         flags |= 256;
         flags |= 2;

         try {
            this.mybuf = new TypedFML();
            this.mybuf.Fchg(new FmlKey(40973, 0), qname);
            if (msgid != null) {
               flags |= 262152;
               this.mybuf.Fchg(new FmlKey(49174, 0), msgid);
            }

            if (corrid != null) {
               flags |= 526336;
               this.mybuf.Fchg(new FmlKey(49173, 0), corrid);
            }

            if (doWait) {
               flags |= 128;
            }

            if (doPeek) {
               flags |= 4096;
            }

            this.mybuf.Fchg(new FmlKey(8195, 0), new Integer(flags));
         } catch (Ferror var13) {
            throw new TPException(12, "Unable to add field " + var13);
         }
      }
   }

   public byte[] getMsgid() {
      if (this.mybuf == null) {
         return null;
      } else {
         try {
            Integer iFlags;
            if ((iFlags = (Integer)this.mybuf.Fget(new FmlKey(8195, 0))) == null) {
               return null;
            } else {
               int flags = iFlags;
               if ((flags & 16) == 0) {
                  return null;
               } else {
                  byte[] ret = (byte[])((byte[])this.mybuf.Fget(new FmlKey(49174, 0)));
                  return ret;
               }
            }
         } catch (Ferror var5) {
            return null;
         }
      }
   }

   public byte[] getCoorid() {
      if (this.mybuf == null) {
         return null;
      } else {
         try {
            Integer iFlags;
            if ((iFlags = (Integer)this.mybuf.Fget(new FmlKey(8195, 0))) == null) {
               return null;
            } else {
               int flags = iFlags;
               if ((flags & 1) == 0) {
                  return null;
               } else {
                  byte[] ret = (byte[])((byte[])this.mybuf.Fget(new FmlKey(49173, 0)));
                  return ret;
               }
            }
         } catch (Ferror var5) {
            return null;
         }
      }
   }

   public String getReplyQueue() {
      if (this.mybuf == null) {
         return null;
      } else {
         try {
            Integer iFlags;
            if ((iFlags = (Integer)this.mybuf.Fget(new FmlKey(8195, 0))) == null) {
               return null;
            } else {
               int flags = iFlags;
               if ((flags & 256) == 0) {
                  return null;
               } else {
                  String ret = (String)this.mybuf.Fget(new FmlKey(40972, 0));
                  return ret;
               }
            }
         } catch (Ferror var5) {
            return null;
         }
      }
   }

   public String getFailureQueue() {
      if (this.mybuf == null) {
         return null;
      } else {
         try {
            Integer iFlags;
            if ((iFlags = (Integer)this.mybuf.Fget(new FmlKey(8195, 0))) == null) {
               return null;
            } else {
               int flags = iFlags;
               if ((flags & 2) == 0) {
                  return null;
               } else {
                  String ret = (String)this.mybuf.Fget(new FmlKey(40971, 0));
                  return ret;
               }
            }
         } catch (Ferror var5) {
            return null;
         }
      }
   }

   public Integer getPriority() {
      if (this.mybuf == null) {
         return null;
      } else {
         try {
            Integer iFlags;
            if ((iFlags = (Integer)this.mybuf.Fget(new FmlKey(8195, 0))) == null) {
               return null;
            } else {
               int flags = iFlags;
               if ((flags & 32) == 0) {
                  return null;
               } else {
                  Integer ret = (Integer)this.mybuf.Fget(new FmlKey(8194, 0));
                  return ret;
               }
            }
         } catch (Ferror var5) {
            return null;
         }
      }
   }

   public int getDeliveryQualityOfService() {
      if (this.mybuf == null) {
         return 1;
      } else {
         try {
            Integer iFlags;
            if ((iFlags = (Integer)this.mybuf.Fget(new FmlKey(8195, 0))) == null) {
               return 1;
            } else {
               int flags = iFlags;
               if ((flags & 8192) == 0) {
                  return 1;
               } else {
                  Integer qos = (Integer)this.mybuf.Fget(new FmlKey(8197, 0));
                  int ret = qos;
                  return ret;
               }
            }
         } catch (Ferror var6) {
            return 1;
         }
      }
   }

   public int getReplyQualityOfService() {
      if (this.mybuf == null) {
         return 1;
      } else {
         try {
            Integer iFlags;
            if ((iFlags = (Integer)this.mybuf.Fget(new FmlKey(8195, 0))) == null) {
               return 1;
            } else {
               int flags = iFlags;
               if ((flags & 16384) == 0) {
                  return 1;
               } else {
                  Integer qos = (Integer)this.mybuf.Fget(new FmlKey(8198, 0));
                  int ret = qos;
                  return ret;
               }
            }
         } catch (Ferror var6) {
            return 1;
         }
      }
   }

   public Integer getDiagnostic() {
      if (this.mybuf == null) {
         return null;
      } else {
         try {
            Integer ret = (Integer)this.mybuf.Fget(new FmlKey(8193, 0));
            return ret;
         } catch (Ferror var3) {
            return null;
         }
      }
   }

   public int getR65size() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ComposFmlTcb/getR65size/");
      }

      int ret = this.mybuf.Fused();
      if (traceEnabled) {
         ntrace.doTrace("]/ComposFmlTcb/getR65size/" + ret);
      }

      return ret;
   }

   public boolean prepareForCache() {
      this.mybuf = null;
      return true;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      int calculated_size = myheader.getHeaderLen();
      int send_size = false;
      if (this.mybuf == null) {
         throw new TPException(4);
      } else {
         int initial_size = encoder.size();
         this.mybuf._tmpresend(encoder);
         int new_size = encoder.size();
         int send_size = new_size - initial_size;
         calculated_size += send_size;
         myheader.setLen(calculated_size);
      }
   }

   public int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      return this._tmpostrecv65(decoder);
   }

   protected void _tmpresend65(DataOutputStream encoder) throws IOException {
      if (this.mybuf != null) {
         try {
            this.mybuf._tmpresend(encoder);
         } catch (TPException var3) {
            throw new IOException("tperrno caught: " + var3);
         }
      }
   }

   public int _tmpostrecv65(DataInputStream decoder) throws IOException {
      this.mybuf = new TypedFML();

      try {
         this.mybuf._tmpostrecv65(decoder);
         return 0;
      } catch (TPException var3) {
         return -1;
      } catch (IOException var4) {
         WTCLogger.logIOEbadTypedBuffer("COMPOS_FML TCB", var4.getMessage());
         return -1;
      }
   }
}
