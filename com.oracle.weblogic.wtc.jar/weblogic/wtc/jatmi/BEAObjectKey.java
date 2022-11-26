package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Tpconvert;
import com.bea.core.jatmi.common.ntrace;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public final class BEAObjectKey {
   private byte[] keyData;
   private int keyLength;
   private int requestId;
   private int msgType;
   private byte responseExpected;
   private int verMinor;
   private int verMajor;
   public static final int BEAOBJKEY_GIOP_HEADER_SIZE = 12;
   public static final int BEAOBJKEY_GIOP_MAX_VERSION = 1;
   public static final int BEAOBJKEY_GIOP_MAJOR_VERSION_1 = 1;
   public static final int BEAOBJKEY_GIOP_MINOR_VERSION_1 = 1;
   public static final int BEAOBJKEY_GIOP_REQUEST_MESSAGE = 0;
   public static final int BEAOBJKEY_GIOP_REPLY_MESSAGE = 1;
   public static final int BEAOBJKEY_GIOP_CANCELREQUEST_MESSAGE = 2;
   public static final int BEAOBJKEY_GIOP_LOCATE_MESSAGE = 3;
   public static final int BEAOBJKEY_GIOP_LOCATEREPLY_MESSAGE = 4;
   public static final int BEAOBJKEY_GIOP_CLOSECONNECTION_MESSAGE = 5;
   public static final int BEAOBJKEY_GIOP_MESSAGEERROR_MESSAGE = 6;
   public static final int BEAOBJKEY_GIOP_SYSTEM_EXCEPTION = 2;
   public static final int BEAOBJKEY_GIOP_LOC_SYSTEM_EXCEPTION = 4;
   public static final int BEAOBJKEY_GIOP_UNKNOWN_OBJECT = 0;
   public static final int BEAOBJKEY_GIOP_COMPLETED_MAYBE = 2;
   public static final int BEAOBJKEY_GIOP_KEYADDR = 0;
   public static final int BEAOBJKEY_GIOP_PROFILEADDR = 1;
   public static final int BEAOBJKEY_GIOP_REFERENCEADDR = 2;
   public static final int BEAOBJKEY_TAG_INTERNET_IOP = 0;
   public static final int BEAOBJKEY_ENCAPKEYHEADERSIZE = 8;
   public static final int BEAOBJKEY_KEY_MAJOR_VERSION = 1;
   public static final int BEAOBJKEY_KEY_MINOR_VERSION = 2;
   public static final int BEAOBJKEY_KEYTYPE_ICEBERG = 8;
   public static final int BEAOBJKEY_TPFW = 1;
   public static final int BEAOBJKEY_INTFREP = 2;
   public static final int BEAOBJKEY_FF = 3;
   public static final int BEAOBJKEY_ROOT = 4;
   public static final int BEAOBJKEY_NTS = 5;
   public static final int BEAOBJKEY_USER = 6;
   public static final int BEAOBJKEY_NAMESERVICE = 7;
   public static final int BEAOBJKEY_TAG_CLNT_ROUTE_INFO = 1111834884;
   public static final int BEAOBJKEY_TAG_OBJKEY_HASH = 1111834886;

   public BEAObjectKey(TypedTGIOP tgiopMsg) throws TPException, IOException {
      int oldFmt = false;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/BEAObjectKey/BEAObjectKey/0");
      }

      this.requestId = 0;
      int bufLen = tgiopMsg.send_size;
      if (bufLen < 12) {
         if (traceEnabled) {
            ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/5");
         }

         throw new TPException(9);
      } else {
         int bytesLeft = bufLen + 12;
         DataInputStream ds = new DataInputStream(new ByteArrayInputStream(tgiopMsg.tgiop));
         byte[] typechar = new byte[4];
         ds.readFully(typechar);
         String val = new String(typechar);
         if (!val.equals("GIOP")) {
            throw new TPException(9);
         } else {
            if (traceEnabled) {
               ntrace.doTrace("/BEAObjectKey/BEAObjectKey/10");
            }

            if ((this.verMajor = ds.readByte()) > 1) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/15");
               }

               throw new TPException(12);
            } else {
               this.verMinor = ds.readByte();
               if (this.verMinor <= 1 && this.verMajor <= 1) {
                  oldFmt = true;
               }

               int byteorder = ds.readByte();
               if (traceEnabled) {
                  ntrace.doTrace("/BEAObjectKey/BEAObjectKey/17 byteorder = " + byteorder);
               }

               this.msgType = ds.readByte();
               if (this.msgType != 0 && this.msgType != 3) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/20/" + this.msgType);
                  }

                  throw new TPException(12);
               } else {
                  ds.readByte();
                  ds.readByte();
                  ds.readByte();
                  ds.readByte();
                  bytesLeft -= 12;
                  if (traceEnabled) {
                     ntrace.doTrace("/BEAObjectKey/BEAObjectKey/25/" + this.msgType);
                  }

                  int lv;
                  int length2;
                  if (oldFmt && this.msgType == 0) {
                     bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                     int length = TGIOPUtil.extractLong(ds, byteorder);

                     for(bytesLeft -= 4; length > 0; --length) {
                        ds.readInt();
                        bytesLeft -= 4;
                        bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                        length2 = TGIOPUtil.extractLong(ds, byteorder);
                        bytesLeft -= 4;
                        if (length2 > bytesLeft) {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/27");
                           }

                           throw new TPException(12);
                        }

                        for(lv = 0; lv < length2; ++lv) {
                           ds.readByte();
                           --bytesLeft;
                        }
                     }
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/BEAObjectKey/BEAObjectKey/30");
                  }

                  bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                  this.requestId = TGIOPUtil.extractLong(ds, byteorder);
                  bytesLeft -= 4;
                  if (this.msgType == 0) {
                     this.responseExpected = ds.readByte();
                     ds.readByte();
                     ds.readByte();
                     ds.readByte();
                     bytesLeft -= 4;
                  } else {
                     this.responseExpected = 0;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/BEAObjectKey/BEAObjectKey/31/" + this.requestId + "/" + this.responseExpected);
                  }

                  int var10000;
                  if (oldFmt) {
                     bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                     this.keyLength = TGIOPUtil.extractLong(ds, byteorder);
                     bytesLeft -= 4;
                     if (this.keyLength > bytesLeft + 4) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/35");
                        }

                        throw new TPException(12);
                     }

                     this.keyData = new byte[this.keyLength];

                     for(lv = 0; lv < this.keyLength; ++lv) {
                        this.keyData[lv] = ds.readByte();
                     }

                     var10000 = bytesLeft - this.keyLength;
                  } else {
                     int addr_disp = TGIOPUtil.extractShort(ds, byteorder);
                     bytesLeft -= 2;
                     switch (addr_disp) {
                        case 0:
                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           this.keyLength = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;
                           if (this.keyLength > bytesLeft + 4) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/40");
                              }

                              throw new TPException(12);
                           }

                           this.keyData = new byte[this.keyLength];

                           for(lv = 0; lv < this.keyLength; ++lv) {
                              this.keyData[lv] = ds.readByte();
                           }

                           var10000 = bytesLeft - this.keyLength;
                           break;
                        case 2:
                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           int idx = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;
                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           length2 = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;
                           if (length2 > bytesLeft) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/45");
                              }

                              throw new TPException(12);
                           }

                           for(lv = 0; lv < length2; ++lv) {
                              ds.readByte();
                              --bytesLeft;
                           }

                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           int num_idx = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;

                           for(lv = 1; lv <= num_idx && lv != idx; ++lv) {
                              ds.readInt();
                              bytesLeft -= 4;
                              bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                              length2 = TGIOPUtil.extractLong(ds, byteorder);
                              bytesLeft -= 4;
                              if (length2 > bytesLeft) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/50");
                                 }

                                 throw new TPException(12);
                              }
                           }

                           if (lv != idx) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/55");
                              }

                              throw new TPException(12);
                           }
                        case 1:
                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           int tag = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;
                           if (tag != 0) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/60");
                              }

                              throw new TPException(9);
                           }

                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           length2 = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;
                           if (length2 > bytesLeft) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/65");
                              }

                              throw new TPException(12);
                           }

                           byteorder = ds.readByte();
                           --bytesLeft;
                           ds.readByte();
                           ds.readByte();
                           bytesLeft -= 2;
                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           length2 = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;
                           if (length2 > bytesLeft) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/70");
                              }

                              throw new TPException(12);
                           }

                           for(lv = 0; lv < length2; ++lv) {
                              ds.readByte();
                              --bytesLeft;
                           }

                           ds.readShort();
                           bytesLeft -= 2;
                           bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                           this.keyLength = TGIOPUtil.extractLong(ds, byteorder);
                           bytesLeft -= 4;
                           if (this.keyLength > bytesLeft + 4) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/75");
                              }

                              throw new TPException(12);
                           }

                           this.keyData = new byte[this.keyLength];

                           for(lv = 0; lv < this.keyLength; ++lv) {
                              this.keyData[lv] = ds.readByte();
                           }

                           var10000 = bytesLeft - this.keyLength;
                           break;
                        default:
                           if (traceEnabled) {
                              ntrace.doTrace("*]/BEAObjectKey/BEAObjectKey/80");
                           }

                           throw new TPException(12);
                     }
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("]/BEAObjectKey/BEAObjectKey/90");
                  }

               }
            }
         }
      }
   }

   public BEAObjectKey(byte[] buf) {
      this.keyLength = buf.length;
      this.keyData = new byte[this.keyLength];

      for(int lcv = 0; lcv < this.keyLength; ++lcv) {
         this.keyData[lcv] = buf[lcv];
      }

   }

   public byte[] getBEAObjectKey() {
      return this.keyData;
   }

   public int getBEAObjectKeyLen() {
      return this.keyLength;
   }

   public int getRequestId() {
      return this.requestId;
   }

   public int getMsgType() {
      return this.msgType;
   }

   public int getMajorVersion() {
      return this.verMajor;
   }

   public int getMinorVersion() {
      return this.verMinor;
   }

   public byte getResponseExpected() {
      return this.responseExpected;
   }

   public void getInfo(Objinfo objinfo) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/BEAObjectKey/getInfo/0");
      }

      if (this.keyLength < 8) {
         if (traceEnabled) {
            ntrace.doTrace("*]/BEAObjectKey/getInfo/10");
         }

         throw new TPException(9);
      } else {
         DataInputStream ds = new DataInputStream(new ByteArrayInputStream(this.keyData));
         int bytesLeft = this.keyLength + 8;
         int bufLen = bytesLeft;
         int byteorder = ds.readByte();
         byte[] typechar = new byte[3];
         ds.readFully(typechar);
         String val = new String(typechar);
         if (!val.equals("BEA")) {
            if (traceEnabled) {
               ntrace.doTrace("*]/BEAObjectKey/getInfo/20");
            }

            throw new TPException(9);
         } else {
            if (traceEnabled) {
               ntrace.doTrace("/BEAObjectKey/getInfo/30");
            }

            objinfo.setKeyType(ds.readByte());
            if (objinfo.getKeyType() != 8) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/BEAObjectKey/getInfo/40");
               }

               throw new TPException(9);
            } else {
               byte verMajor;
               if ((verMajor = ds.readByte()) > 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/BEAObjectKey/getInfo/45");
                  }

                  throw new TPException(12);
               } else {
                  int verMinor = ds.readByte();
                  ds.readByte();
                  bytesLeft -= 8;
                  if (traceEnabled) {
                     ntrace.doTrace("/BEAObjectKey/getInfo/50");
                  }

                  bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                  int lcv = TGIOPUtil.extractLong(ds, byteorder);
                  bytesLeft -= 4;
                  if (lcv != 0 && lcv <= bytesLeft) {
                     typechar = new byte[lcv];

                     int i;
                     for(i = 0; i < lcv; ++i) {
                        typechar[i] = ds.readByte();
                     }

                     bytesLeft -= lcv;
                     objinfo.setDomainId(new String(typechar, 0, lcv - 1));
                     bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                     lcv = TGIOPUtil.extractLong(ds, byteorder);
                     bytesLeft -= 4;
                     objinfo.setGroupId(lcv);
                     bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                     lcv = TGIOPUtil.extractLong(ds, byteorder);
                     bytesLeft -= 4;
                     if (lcv != 0 && lcv <= bytesLeft) {
                        typechar = new byte[lcv];

                        for(i = 0; i < lcv; ++i) {
                           typechar[i] = ds.readByte();
                        }

                        bytesLeft -= lcv;
                        objinfo.setIntfId(new String(typechar, 0, lcv - 1));
                        bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                        lcv = TGIOPUtil.extractLong(ds, byteorder);
                        bytesLeft -= 4;
                        if (lcv != 0 && lcv <= bytesLeft) {
                           typechar = new byte[lcv];

                           for(i = 0; i < lcv; ++i) {
                              typechar[i] = ds.readByte();
                           }

                           bytesLeft -= lcv;
                           objinfo.setObjectId(new ObjectId(typechar, lcv));
                           if (verMinor > 1 && verMajor == 1) {
                              if (traceEnabled) {
                                 ntrace.doTrace("/BEAObjectKey/getInfo/80");
                              }

                              bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                              int ncomp = TGIOPUtil.extractLong(ds, byteorder);

                              for(bytesLeft -= 4; ncomp > 0; --ncomp) {
                                 bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                 int keycompid = TGIOPUtil.extractLong(ds, byteorder);
                                 bytesLeft -= 4;
                                 bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                 int len = TGIOPUtil.extractLong(ds, byteorder);
                                 bytesLeft -= 4;
                                 switch (keycompid) {
                                    case 1111834884:
                                       if (traceEnabled) {
                                          ntrace.doTrace("/BEAObjectKey/getInfo/90");
                                       }

                                       bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                       lcv = TGIOPUtil.extractLong(ds, byteorder);
                                       bytesLeft -= 4;
                                       len -= 4;
                                       objinfo.getCltinfo().setVersion(lcv);
                                       bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                       lcv = TGIOPUtil.extractLong(ds, byteorder);
                                       bytesLeft -= 4;
                                       len -= 4;
                                       typechar = new byte[lcv];

                                       for(i = 0; i < lcv; ++i) {
                                          typechar[i] = ds.readByte();
                                       }

                                       bytesLeft -= lcv;
                                       len -= lcv;
                                       String cltid = new String(typechar);
                                       if (traceEnabled) {
                                          ntrace.doTrace("/BEAObjectKey/getInfo/95 cltid = " + cltid);
                                       }

                                       objinfo.getCltinfo().setId(Tpconvert.getClientId(cltid));
                                       bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                       lcv = TGIOPUtil.extractLong(ds, byteorder);
                                       bytesLeft -= 4;
                                       len -= 4;
                                       objinfo.getCltinfo().setCntxt(lcv);
                                       bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                       lcv = TGIOPUtil.extractLong(ds, byteorder);
                                       bytesLeft -= 4;
                                       len -= 4;
                                       objinfo.getCltinfo().setPid(lcv);
                                       bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                       lcv = TGIOPUtil.extractLong(ds, byteorder);
                                       bytesLeft -= 4;
                                       len -= 4;
                                       objinfo.getCltinfo().setQaddr(lcv);
                                       objinfo.getCltinfo().setDomain(new String(objinfo.getDomainId()));
                                       objinfo.setIsACallout(1);
                                       break;
                                    case 1111834886:
                                       if (traceEnabled) {
                                          ntrace.doTrace("/BEAObjectKey/getInfo/100");
                                       }

                                       bytesLeft -= this.alignLong(ds, bufLen - bytesLeft);
                                       lcv = TGIOPUtil.extractLong(ds, byteorder);
                                       bytesLeft -= 4;
                                       len -= 4;
                                       objinfo.setScaIntfBkt(lcv);
                                 }

                                 if (len > bytesLeft) {
                                    if (traceEnabled) {
                                       ntrace.doTrace("]/BEAObjectKey/getInfo/110");
                                    }

                                    return;
                                 }

                                 while(len > 0) {
                                    ds.readByte();
                                    --bytesLeft;
                                    --len;
                                 }
                              }
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("]/BEAObjectKey/getInfo/120");
                           }

                        } else {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/BEAObjectKey/getInfo/70/" + lcv + "/" + bytesLeft);
                           }

                           throw new TPException(12);
                        }
                     } else {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/BEAObjectKey/getInfo/60");
                        }

                        throw new TPException(12);
                     }
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/BEAObjectKey/getInfo/55");
                     }

                     throw new TPException(12);
                  }
               }
            }
         }
      }
   }

   public static boolean isValidBEAObjKey(byte[] objKey) {
      return objKey.length >= 8 && objKey[1] == 66 && objKey[2] == 69 && objKey[3] == 65;
   }

   public static String getDomId(byte[] objKey) throws IOException, TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/BEAObjectKey/getDomId/0");
      }

      if (!isValidBEAObjKey(objKey)) {
         if (traceEnabled) {
            ntrace.doTrace("*]/BEAObjectKey/getDomId/5");
         }

         throw new TPException(4);
      } else {
         int byteorder = objKey[0];
         DataInputStream ds = new DataInputStream(new ByteArrayInputStream(objKey));

         int lv;
         for(lv = 0; lv < 8; ++lv) {
            ds.readByte();
         }

         if (traceEnabled) {
            ntrace.doTrace("/BEAObjectKey/getDomId/10");
         }

         lv = TGIOPUtil.extractLong(ds, byteorder);
         if (lv == 0) {
            return new String("");
         } else {
            byte[] typechar = new byte[lv];

            for(int i = 0; i < lv; ++i) {
               typechar[i] = ds.readByte();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/BEAObjectKey/getDomId/20");
            }

            return new String(typechar, 0, lv - 1);
         }
      }
   }

   public int alignLong(DataInputStream ds, int offset) throws IOException {
      int align = 4;
      int incr = offset & align - 1;
      if (incr != 0) {
         incr = align - incr;

         for(int i = 0; i < incr; ++i) {
            ds.readByte();
         }
      }

      return incr;
   }
}
