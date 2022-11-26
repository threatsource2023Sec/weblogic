package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class UserTcb extends tcb {
   private static final long serialVersionUID = -8015074384529379179L;
   private int hint_index = -1;
   public TypedBuffer user_data;
   public int user_data_len;
   private byte[] tcb_fillin;
   private transient byte[] myScratch;
   private boolean dump = false;
   private static int nwmsg_size_limit = -1;
   private boolean use64BitsLong = false;

   public UserTcb() {
      super((short)0);
      getNWMsgSizeLimit();
   }

   public UserTcb(int index) {
      super((short)0);
      this.hint_index = index;
      getNWMsgSizeLimit();
   }

   public UserTcb(TypedBuffer udata) {
      super((short)0);
      this.user_data = udata;
      this.hint_index = udata.getHintIndex();
      getNWMsgSizeLimit();
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

   public int getHintIndex() {
      return this.hint_index;
   }

   public void setUse64BitsLong(boolean val) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      this.use64BitsLong = val;
      if (this.user_data != null) {
         switch (this.user_data.getHintIndex()) {
            case 18:
               ((TypedFML)this.user_data).setUse64BitsLong(true);
               break;
            case 19:
               ((TypedView)this.user_data).setUse64BitsLong(true);
            case 20:
            case 21:
            case 22:
            default:
               break;
            case 23:
               ((TypedFML32)this.user_data).setUse64BitsLong(true);
               break;
            case 24:
               ((TypedView32)this.user_data).setUse64BitsLong(true);
         }

         if (traceEnabled) {
            ntrace.doTrace("/UserTcb/setUse64BitsLong/" + val);
         }
      }

   }

   public boolean getUse64BitsLong() {
      return this.use64BitsLong;
   }

   public boolean prepareForCache() {
      return false;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      boolean pduEnabled = ntrace.isTraceEnabled(32);
      if (traceEnabled) {
         ntrace.doTrace("[/UserTcb/_tmpresend/" + encoder + "/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      int send_size = false;
      int output_data_size = false;
      if (this.user_data == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/UserTcb/_tmpresend/10/TPEINVAL");
         }

         throw new TPException(4, "User TCB has null user data");
      } else {
         String type = this.user_data.getType();
         String subtype = this.user_data.getSubtype();
         if (pduEnabled) {
            ntrace.doTrace("UDATA: buffer type(" + type + ", " + subtype + ")");
         }

         int initial_size;
         int new_size;
         try {
            initial_size = encoder.size();
            Utilities.xdr_encode_string(encoder, type);
            Utilities.xdr_encode_string(encoder, subtype);
            new_size = encoder.size();
            calculated_size += new_size - initial_size;
            initial_size = new_size;
         } catch (IOException var18) {
            if (traceEnabled) {
               ntrace.doTrace("*]/UserTcb/_tmpresend/20/" + var18);
            }

            throw var18;
         }

         if (this.use64BitsLong) {
            switch (this.user_data.getHintIndex()) {
               case 18:
                  ((TypedFML)this.user_data).setUse64BitsLong(true);
                  break;
               case 19:
                  try {
                     if (((TypedView)this.user_data).containsOldView()) {
                        throw new TPException(4, "VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                     }

                     ((TypedView)this.user_data).setUse64BitsLong(true);
                  } catch (AbstractMethodError var16) {
                     throw new TPException(4, "VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                  }
               case 20:
               case 21:
               case 22:
               default:
                  break;
               case 23:
                  ((TypedFML32)this.user_data).setUse64BitsLong(true);
                  break;
               case 24:
                  try {
                     if (((TypedView32)this.user_data).containsOldView()) {
                        throw new TPException(4, "VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                     }

                     ((TypedView32)this.user_data).setUse64BitsLong(true);
                  } catch (AbstractMethodError var17) {
                     throw new TPException(4, "VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                  }
            }
         }

         int send_size;
         try {
            this.user_data._tmpresend(encoder);
            new_size = encoder.size();
            send_size = new_size - initial_size;
         } catch (IOException var19) {
            if (traceEnabled) {
               ntrace.doTrace("*]/UserTcb/_tmpresend/30/" + var19);
            }

            throw var19;
         } catch (TPException var20) {
            if (traceEnabled) {
               ntrace.doTrace("*]/UserTcb/_tmpresend/40/" + var20);
            }

            throw var20;
         }

         myheader.setLen(calculated_size + send_size);

         try {
            int pad_bytes = Utilities.roundup4(send_size) - send_size;

            for(int lcv = 0; lcv < pad_bytes; ++lcv) {
               encoder.writeByte(0);
            }
         } catch (IOException var21) {
            if (traceEnabled) {
               ntrace.doTrace("*]/UserTcb/_tmpresend/50/" + var21);
            }

            throw var21;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/UserTcb/_tmpresend/60/");
         }

      }
   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      boolean pduEnabled = ntrace.isTraceEnabled(32);
      boolean udataEnabled = ntrace.isTraceEnabled(64);
      if (traceEnabled) {
         ntrace.doTrace("[/UserTcb/_tmpostrecv/recv_size=" + recv_size + "/hint_index=" + hint_index);
      }

      if (nwmsg_size_limit > 0 && recv_size > nwmsg_size_limit) {
         if (traceEnabled) {
            ntrace.doTrace("*]/UserTcb/_tmpostrecv/5/-1/NWMsg Size Limit Exceeds");
         }

         return -1;
      } else {
         int pad_bytes = Utilities.roundup4(recv_size) - recv_size;
         if (this.myScratch == null) {
            this.myScratch = new byte[50];
         }

         String type = Utilities.xdr_decode_string(decoder, this.myScratch);
         recv_size -= Utilities.xdr_length_string(type);
         String subtype = Utilities.xdr_decode_string(decoder, this.myScratch);
         recv_size -= Utilities.xdr_length_string(subtype);
         int skip_size = pad_bytes;
         this.user_data_len = recv_size;
         DataInputStream ndis;
         if (udataEnabled && this.dump) {
            ntrace.doTrace("Inbound UDATA: buffer type(" + type + ", " + subtype + ")");
            byte[] tmp = new byte[recv_size];
            decoder.read(tmp);
            ndis = new DataInputStream(new ByteArrayInputStream(tmp));
            new TDumpByte("User Data", tmp, -1);
            this.dump = false;
         } else {
            if (pduEnabled) {
               ntrace.doTrace("Inbound UDATA: buffer type(" + type + ", " + subtype + ")");
            }

            ndis = decoder;
            skip_size = pad_bytes + recv_size;
         }

         TypedBuffer theUserData;
         if ((theUserData = TypedBufferFactory.createTypedBuffer(hint_index, type, subtype)) == null) {
            decoder.skipBytes(skip_size);
            if (traceEnabled) {
               ntrace.doTrace("]/UserTcb/_tmpostrecv/-2/create VIEW buffer failed");
            }

            return -2;
         } else {
            if (this.use64BitsLong) {
               switch (hint_index) {
                  case 18:
                     ((TypedFML)theUserData).setUse64BitsLong(true);
                     break;
                  case 19:
                     try {
                        if (((TypedView)theUserData).containsOldView()) {
                           throw new IOException("VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                        }

                        ((TypedView)theUserData).setUse64BitsLong(true);
                     } catch (AbstractMethodError var19) {
                        throw new IOException("VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                     } catch (Exception var20) {
                        throw new IOException("VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                     }
                  case 20:
                  case 21:
                  case 22:
                  default:
                     break;
                  case 23:
                     ((TypedFML32)theUserData).setUse64BitsLong(true);
                     break;
                  case 24:
                     try {
                        if (((TypedView32)theUserData).containsOldView()) {
                           throw new IOException("VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                        }

                        ((TypedView32)theUserData).setUse64BitsLong(true);
                     } catch (AbstractMethodError var21) {
                        throw new IOException("VIEW class should be re-built with -64bits to support 64-bit FLD_LONG");
                     }
               }

               if (traceEnabled) {
                  ntrace.doTrace("/UserTcb/_tmpostrecv/set64BitsLong=true for data type " + hint_index);
               }
            }

            try {
               theUserData._tmpostrecv(ndis, recv_size);
            } catch (TPException var22) {
               if (traceEnabled) {
                  ntrace.doTrace("]/UserTcb/_tmpostrecv/-1/receive data failed");
               }

               return -1;
            }

            this.user_data = theUserData;

            for(int lcv = 0; lcv < pad_bytes; ++lcv) {
               decoder.readByte();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/UserTcb/_tmpostrecv/10");
            }

            return 0;
         }
      }
   }

   public final int _tmpostrecv65(DataInputStream decoder, int recv_size, int hint_index, String type, String subtype, int opcode) throws IOException {
      TypedBuffer theUserData = null;
      boolean pduEnabled = ntrace.isTraceEnabled(32);
      boolean udataEnabled = ntrace.isTraceEnabled(64);
      if (pduEnabled || udataEnabled) {
         ntrace.doTrace("UDATA: buffer type(" + type + ", " + subtype + ")");
      }

      int pad_bytes = Utilities.roundup4(recv_size) - recv_size;
      if (this.myScratch == null) {
         this.myScratch = new byte[50];
      }

      if (opcode != 12) {
         if ((theUserData = TypedBufferFactory.createTypedBuffer(hint_index, type, subtype)) == null) {
            decoder.skipBytes(recv_size + pad_bytes);
            return -2;
         }

         try {
            if (opcode != 11) {
               theUserData._tmpostrecv(decoder, recv_size);
            }
         } catch (TPException var17) {
            return -1;
         }
      }

      this.user_data = theUserData;

      for(int lcv = 0; lcv < pad_bytes; ++lcv) {
         decoder.readByte();
      }

      return 0;
   }

   public void dumpData(boolean b) {
      this.dump = b;
   }
}
