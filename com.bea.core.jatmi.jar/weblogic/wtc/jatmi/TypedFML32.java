package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import weblogic.wtc.WTCLogger;

public final class TypedFML32 extends MBStringTypes implements TypedBuffer, Serializable, FML {
   private static final long serialVersionUID = -6427570805762043258L;
   private static final int F_MAGIC_BASE = -10000;
   private static final int F_MAGIC32 = -10000;
   private static final int F_MAGICPTR = 4;
   private static final int F_OVHD32 = 24;
   private static final int F_MAXLEN = Integer.MAX_VALUE;
   private static final int F_MAXFLDS = 33554431;
   private static final int FNMASK32 = 33554431;
   private static final int FTMASK32 = 63;
   private static final int FTSHIFT32 = 25;
   private static final int FLDID_SIZE = 4;
   private static final int FLDLEN_SIZE = 4;
   private static final int NUMSYSTBLS = 6;
   private static final int INITIAL_SIZE = 1024;
   private static final int DFLT_FLD_BKTS = 256;
   private static final int DFLT_ID_BKTS = 64;
   private static final byte FBUFENC = 1;
   private static final byte FTERMENC = 2;
   protected int magic = -10000;
   protected int len = 24;
   protected int maxlen = Integer.MAX_VALUE;
   protected int nfields;
   protected int nie;
   protected int indxintvl = 16;
   protected HashMap _flds;
   protected HashMap fldid_occs;
   protected FldTbl[] fldtbls;
   private FldTbl[] systbls;
   private FmlIterator myIter = null;
   private boolean in_presend = false;
   private int numPointerFields;
   private int numFml32Fields;
   private int numMBStringFields;
   private int numView32Fields;
   private int numLongFields;
   private transient byte[] myScratch;
   transient volatile int modCount = 0;
   private int updatedModCount = 0;
   private Object[] myArray = null;
   private boolean use64BitsLong = false;
   private boolean callFromReadWriteObject = false;

   public TypedFML32() {
      super("FML32", 23);
      this._commonInit((TypedFML32)null, 256, 64);
   }

   public TypedFML32(int maxFlds, int maxIds) {
      super("FML32", 23);
      this._commonInit((TypedFML32)null, maxFlds, maxIds);
   }

   public TypedFML32(FldTbl[] tables) {
      super("FML32", 23);
      this._commonInit((TypedFML32)null, 256, 64);
      if (tables != null) {
         this.fldtbls = new FldTbl[tables.length];

         for(int lcv = 0; lcv < tables.length; ++lcv) {
            this.fldtbls[lcv] = tables[lcv];
         }

      }
   }

   public TypedFML32(FldTbl[] tables, int maxFlds, int maxIds) {
      super("FML32", 23);
      this._commonInit((TypedFML32)null, maxFlds, maxIds);
      if (tables != null) {
         this.fldtbls = new FldTbl[tables.length];

         for(int lcv = 0; lcv < tables.length; ++lcv) {
            this.fldtbls[lcv] = tables[lcv];
         }

      }
   }

   public TypedFML32(FldTbl table) {
      super("FML32", 23);
      this._commonInit((TypedFML32)null, 256, 64);
      if (table != null) {
         this.fldtbls = new FldTbl[1];
         this.fldtbls[0] = table;
      }
   }

   public TypedFML32(FldTbl table, int maxFlds, int maxIds) {
      super("FML32", 23);
      this._commonInit((TypedFML32)null, maxFlds, maxIds);
      if (table != null) {
         this.fldtbls = new FldTbl[1];
         this.fldtbls[0] = table;
      }
   }

   public TypedFML32(TypedFML32 copyFrom) {
      super("FML32", 23);
      this.setMBEncoding(copyFrom.mbencoding);
      this.magic = copyFrom.magic;
      this.len = copyFrom.len;
      this.maxlen = copyFrom.maxlen;
      this.nfields = copyFrom.nfields;
      this.nie = copyFrom.nie;
      this.indxintvl = copyFrom.indxintvl;
      this.setFieldTables(copyFrom.fldtbls);
      this._commonInit(copyFrom, 256, 64);
      this.numPointerFields = copyFrom.numPointerFields;
      this.numFml32Fields = copyFrom.numFml32Fields;
      this.numView32Fields = copyFrom.numView32Fields;
      this.numMBStringFields = copyFrom.numMBStringFields;
   }

   private void _commonInit(TypedFML32 copy, int fldsSize, int idSize) {
      if (copy == null) {
         this._flds = new HashMap(fldsSize);
         this.fldid_occs = new HashMap(idSize);
      } else {
         this._flds = new HashMap(copy._flds);
         this.fldid_occs = new HashMap(copy.fldid_occs);
      }

      this.systbls = new FldTbl[6];
      this.systbls[0] = new Usysfl32();
      this.systbls[1] = new evt_mib();
      this.systbls[2] = new islflds();
      this.systbls[3] = new secflds();
      this.systbls[4] = new tmibflds();
      this.systbls[5] = new tpadm();
   }

   public synchronized void setFieldTables(FldTbl[] tables) {
      if (tables == null) {
         this.fldtbls = null;
      } else {
         this.fldtbls = new FldTbl[tables.length];

         for(int lcv = 0; lcv < tables.length; ++lcv) {
            this.fldtbls[lcv] = tables[lcv];
         }

      }
   }

   public synchronized FldTbl[] getFieldTables() {
      return this.fldtbls;
   }

   public int Fldno(int fldid32) {
      return fldid32 & 33554431;
   }

   public int Fldtype(int fldid32) {
      return fldid32 >> 25 & 63;
   }

   public synchronized void Fchg(FmlKey key, Object value) throws Ferror {
      Short value_short = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      TypedFML32 value_fml32 = null;
      TypedBuffer value_ptr = null;
      TypedMBString value_mbstring = null;
      TypedView32 value_view = null;
      FViewFld value_viewf = null;
      boolean exist = false;
      Object org_value = null;
      int org_length = 0;
      String vname = null;
      if (key == null) {
         throw new Ferror(13, "Input key is null");
      } else {
         Boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
         FmlKey workkey = new FmlKey(key);
         int fldid32 = workkey.get_fldid();
         int occurrence = workkey.get_occurrence();
         int fldno32;
         if ((fldno32 = fldid32 & 33554431) == 0) {
            throw new Ferror(5, "Unknown fldid32: " + fldid32);
         } else {
            Integer occ = (Integer)this.fldid_occs.get(new Integer(fldid32));
            int int_occ;
            if (occ == null) {
               int_occ = 0;
            } else {
               int_occ = occ + 1;
            }

            if (occurrence >= 0 && occurrence < int_occ) {
               org_value = this._flds.get(workkey);
               if (org_value != null) {
                  exist = true;
               }
            }

            int fldtype32 = fldid32 >> 25 & 63;
            Integer fldid_key = new Integer(fldid32);
            int add_length;
            int null_add_length;
            switch (fldtype32) {
               case 0:
                  if (value == null) {
                     value_short = new Short((short)0);
                  } else {
                     if (!(value instanceof Short)) {
                        throw new Ferror(6, "Expected Short");
                     }

                     value_short = new Short((Short)value);
                  }

                  add_length = 8;
                  null_add_length = 8;
                  if (exist) {
                     org_length = 8;
                  }
                  break;
               case 1:
                  if (value == null) {
                     value_long = new Long(0L);
                  } else {
                     if (!(value instanceof Integer) && !(value instanceof Long)) {
                        throw new Ferror(6, "Expected Integer or Long");
                     }

                     if (value instanceof Integer) {
                        value_long = new Long((long)(Integer)value);
                     } else if (value instanceof Long) {
                        value_long = new Long((Long)value);
                     }
                  }

                  add_length = 12;
                  null_add_length = 12;
                  if (exist) {
                     org_length = 12;
                  }
                  break;
               case 2:
                  if (value == null) {
                     value_char = new Character('\u0000');
                  } else {
                     if (!(value instanceof Character)) {
                        throw new Ferror(6, "Expected Character");
                     }

                     value_char = new Character((Character)value);
                  }

                  add_length = 8;
                  null_add_length = 8;
                  if (exist) {
                     org_length = 8;
                  }
                  break;
               case 3:
                  if (value == null) {
                     value_float = new Float(0.0);
                  } else {
                     if (!(value instanceof Float)) {
                        throw new Ferror(6, "Expected Float");
                     }

                     value_float = new Float((Float)value);
                  }

                  add_length = 8;
                  null_add_length = 8;
                  if (exist) {
                     org_length = 8;
                  }
                  break;
               case 4:
                  if (value == null) {
                     value_double = new Double(0.0);
                  } else {
                     if (!(value instanceof Double)) {
                        throw new Ferror(6, "Expected Double");
                     }

                     value_double = new Double((Double)value);
                  }

                  add_length = 12;
                  null_add_length = 12;
                  if (exist) {
                     org_length = 12;
                  }
                  break;
               case 5:
                  if (value == null) {
                     value_string = new String("");
                  } else {
                     if (!(value instanceof String)) {
                        throw new Ferror(6, "Expected String");
                     }

                     value_string = new String((String)value);
                  }

                  add_length = 12 + Utilities.roundup4(value_string.length() + 1);
                  null_add_length = 16;
                  if (exist) {
                     org_length = 12 + Utilities.roundup4(((String)org_value).length() + 1);
                  }
                  break;
               case 6:
                  if (value == null) {
                     value_carray = null;
                     add_length = 12;
                  } else {
                     if (!(value instanceof byte[])) {
                        throw new Ferror(6, "Expected byte[]");
                     }

                     value_carray = new byte[((byte[])((byte[])value)).length];
                     System.arraycopy(value, 0, value_carray, 0, value_carray.length);
                     add_length = 12 + Utilities.roundup4(value_carray.length);
                  }

                  null_add_length = 12;
                  if (exist) {
                     org_length = 12 + Utilities.roundup4(((byte[])((byte[])org_value)).length);
                  }
                  break;
               case 7:
               case 8:
               default:
                  throw new Ferror(6, "Check fldid32: " + fldid32);
               case 9:
                  if (value == null) {
                     value_ptr = null;
                     add_length = 4 + Utilities.xdr_length_string((String)null) + Utilities.xdr_length_string((String)null) + 4;
                  } else {
                     if (!(value instanceof TypedBuffer)) {
                        throw new Ferror(6, "Expected TypedBuffer");
                     }

                     value_ptr = (TypedBuffer)value;
                     add_length = 4 + Utilities.xdr_length_string(value_ptr.getType()) + Utilities.xdr_length_string(value_ptr.getSubtype());
                  }

                  null_add_length = 4 + Utilities.xdr_length_string((String)null) + Utilities.xdr_length_string((String)null) + 4;
                  if (exist) {
                     if (org_value != null) {
                        org_length = 4 + Utilities.xdr_length_string(((TypedBuffer)org_value).getType()) + Utilities.xdr_length_string(((TypedBuffer)org_value).getSubtype());
                     } else {
                        org_length = 4 + Utilities.xdr_length_string((String)null) + Utilities.xdr_length_string((String)null) + 4;
                     }
                  }
                  break;
               case 10:
                  if (value == null) {
                     if (this.fldtbls != null) {
                        value_fml32 = new TypedFML32(this.fldtbls);
                     } else {
                        value_fml32 = new TypedFML32();
                     }
                  } else {
                     if (!(value instanceof TypedFML32)) {
                        throw new Ferror(6, "Expected TypedFML32");
                     }

                     value_fml32 = new TypedFML32((TypedFML32)value);
                  }

                  add_length = 4;
                  null_add_length = 28;
                  if (exist) {
                     org_length = 4;
                  }
                  break;
               case 11:
                  if (value == null) {
                     value_viewf = null;
                     add_length = 8;
                     null_add_length = 8;
                  } else {
                     if (!(value instanceof FViewFld)) {
                        throw new Ferror(6, "Expected FViewFld");
                     }

                     value_viewf = new FViewFld((FViewFld)value);
                     vname = value_viewf.getViewName();
                     add_length = 8 + vname.length();
                     null_add_length = 8;
                     org_length = 0;
                     if (this.use64BitsLong || enable64BitsLong) {
                        TypedView32 vdata = ((FViewFld)value).data;

                        try {
                           if (vdata.containsOldView()) {
                              throw new Ferror(15, "Nested VIEW32 should be re-built to support 64-bit FLD_LONG");
                           }
                        } catch (AbstractMethodError var42) {
                           throw new Ferror(15, "Nested VIEW32 should be re-built to support 64-bit FLD_LONG");
                        }
                     }
                  }
                  break;
               case 12:
                  if (value == null) {
                     value_mbstring = null;
                  } else {
                     if (!(value instanceof TypedMBString)) {
                        throw new Ferror(6, "Expected TypedMBString");
                     }

                     value_mbstring = new TypedMBString((TypedMBString)value);
                  }

                  add_length = 16;
                  null_add_length = 16;
                  if (exist) {
                     org_length = 16;
                  }
            }

            if (occurrence == -1) {
               workkey.set_occurrence(int_occ);
               this.fldid_occs.put(fldid_key, new Integer(int_occ));
            } else {
               if (occurrence >= int_occ) {
                  this.fldid_occs.put(fldid_key, new Integer(occurrence));
               }

               for(int lcv = int_occ; lcv < occurrence; ++lcv) {
                  FmlKey nullkey = new FmlKey(fldid32, lcv);
                  this.len += null_add_length;
                  ++this.nfields;
                  if (this.len > Integer.MAX_VALUE || this.nfields > 33554431) {
                     this.len -= null_add_length;
                     --this.nfields;
                     this.fldid_occs.put(fldid_key, new Integer(lcv - 1));
                     throw new Ferror(3);
                  }

                  switch (fldtype32) {
                     case 0:
                        Short null_short = new Short((short)0);
                        this._flds.put(nullkey, null_short);
                        break;
                     case 1:
                        Long null_long = new Long(0L);
                        this._flds.put(nullkey, null_long);
                        ++this.numLongFields;
                        break;
                     case 2:
                        Character null_char = new Character('\u0000');
                        this._flds.put(nullkey, null_char);
                        break;
                     case 3:
                        Float null_float = new Float(0.0);
                        this._flds.put(nullkey, null_float);
                        break;
                     case 4:
                        Double null_double = new Double(0.0);
                        this._flds.put(nullkey, null_double);
                        break;
                     case 5:
                        String null_string = new String("");
                        this._flds.put(nullkey, null_string);
                        break;
                     case 6:
                        this._flds.put(nullkey, (Object)null);
                     case 7:
                     case 8:
                     default:
                        break;
                     case 9:
                        this._flds.put(nullkey, (Object)null);
                        this.magic |= 4;
                        ++this.numPointerFields;
                        break;
                     case 10:
                        if (this.fldtbls == null) {
                           this._flds.put(nullkey, new TypedFML32());
                        } else {
                           this._flds.put(nullkey, new TypedFML32(this.fldtbls));
                        }

                        ++this.numFml32Fields;
                        break;
                     case 11:
                        this._flds.put(nullkey, (Object)null);
                        ++this.numView32Fields;
                        break;
                     case 12:
                        this._flds.put(nullkey, (Object)null);
                        ++this.numMBStringFields;
                  }
               }
            }

            this.len += add_length;
            if (exist) {
               this.len -= org_length;
            } else {
               ++this.nfields;
            }

            if (this.len <= Integer.MAX_VALUE && this.nfields <= 33554431) {
               switch (fldtype32) {
                  case 0:
                     this._flds.put(workkey, value_short);
                     break;
                  case 1:
                     this._flds.put(workkey, value_long);
                     if (!exist) {
                        ++this.numLongFields;
                     }
                     break;
                  case 2:
                     this._flds.put(workkey, value_char);
                     break;
                  case 3:
                     this._flds.put(workkey, value_float);
                     break;
                  case 4:
                     this._flds.put(workkey, value_double);
                     break;
                  case 5:
                     this._flds.put(workkey, value_string);
                     break;
                  case 6:
                     this._flds.put(workkey, value_carray);
                  case 7:
                  case 8:
                  default:
                     break;
                  case 9:
                     this._flds.put(workkey, value_ptr);
                     ++this.numPointerFields;
                     this.magic |= 4;
                     break;
                  case 10:
                     this._flds.put(workkey, value_fml32);
                     if (value_fml32.hasPointerFields()) {
                        this.magic |= 4;
                     }

                     ++this.numFml32Fields;
                     break;
                  case 11:
                     this._flds.put(workkey, value_viewf);
                     ++this.numView32Fields;
                     break;
                  case 12:
                     this._flds.put(workkey, value_mbstring);
                     ++this.numMBStringFields;
               }

               ++this.modCount;
            } else {
               this.len -= add_length;
               if (exist) {
                  this.len += org_length;
               } else {
                  --this.nfields;
                  --int_occ;
                  if (int_occ >= 0) {
                     this.fldid_occs.put(fldid_key, new Integer(int_occ));
                  } else {
                     this.fldid_occs.remove(fldid_key);
                  }
               }

               throw new Ferror(3);
            }
         }
      }
   }

   public void Fchg(int fldid, int occurrence, Object value) throws Ferror {
      this.Fchg(new FmlKey(fldid, occurrence), value);
   }

   public synchronized void Fadd(int fldid32, Object value) throws Ferror {
      Short value_short = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      TypedFML32 value_fml32 = null;
      TypedBuffer value_ptr = null;
      TypedMBString value_mbstring = null;
      FViewFld value_viewf = null;
      Boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
      if (this.Fldno(fldid32) == 0) {
         throw new Ferror(5, "Unknown fldid32: " + fldid32);
      } else {
         int fldtype32 = fldid32 >> 25 & 63;
         int add_length;
         switch (fldtype32) {
            case 0:
               if (value == null) {
                  value_short = new Short((short)0);
               } else {
                  if (!(value instanceof Short)) {
                     throw new Ferror(6, "Expected Short");
                  }

                  value_short = new Short((Short)value);
               }

               add_length = 8;
               break;
            case 1:
               if (value == null) {
                  value_long = new Long(0L);
               } else {
                  if (!(value instanceof Integer) && !(value instanceof Long)) {
                     throw new Ferror(6, "Expected Integer or Long");
                  }

                  if (value instanceof Integer) {
                     value_long = new Long(((Integer)value).longValue());
                  } else if (value instanceof Long) {
                     value_long = new Long((Long)value);
                  }
               }

               add_length = 12;
               ++this.numLongFields;
               break;
            case 2:
               if (value == null) {
                  value_char = new Character('\u0000');
               } else {
                  if (!(value instanceof Character)) {
                     throw new Ferror(6, "Expected Character");
                  }

                  value_char = new Character((Character)value);
               }

               add_length = 8;
               break;
            case 3:
               if (value == null) {
                  value_float = new Float(0.0);
               } else {
                  if (!(value instanceof Float)) {
                     throw new Ferror(6, "Expected Float");
                  }

                  value_float = new Float((Float)value);
               }

               add_length = 8;
               break;
            case 4:
               if (value == null) {
                  value_double = new Double(0.0);
               } else {
                  if (!(value instanceof Double)) {
                     throw new Ferror(6, "Expected Double");
                  }

                  value_double = new Double((Double)value);
               }

               add_length = 12;
               break;
            case 5:
               if (value == null) {
                  value_string = new String("");
               } else {
                  if (!(value instanceof String)) {
                     throw new Ferror(6, "Expected String");
                  }

                  value_string = new String((String)value);
               }

               add_length = 12 + Utilities.roundup4(value_string.length() + 1);
               break;
            case 6:
               if (value == null) {
                  value_carray = null;
                  add_length = 12;
               } else {
                  if (!(value instanceof byte[])) {
                     throw new Ferror(6, "Expected byte[]");
                  }

                  value_carray = new byte[((byte[])((byte[])value)).length];
                  System.arraycopy(value, 0, value_carray, 0, value_carray.length);
                  add_length = 12 + Utilities.roundup4(value_carray.length);
               }
               break;
            case 7:
            case 8:
            default:
               throw new Ferror(6, "Check fldid32: " + fldid32);
            case 9:
               if (value == null) {
                  value_ptr = null;
                  add_length = 4 + Utilities.xdr_length_string((String)null) + Utilities.xdr_length_string((String)null) + 4;
               } else {
                  if (!(value instanceof TypedBuffer)) {
                     throw new Ferror(6, "Expected TypedBuffer");
                  }

                  value_ptr = (TypedBuffer)value;
                  add_length = 4 + Utilities.xdr_length_string(value_ptr.getType()) + Utilities.xdr_length_string(value_ptr.getSubtype());
               }
               break;
            case 10:
               if (value == null) {
                  if (this.fldtbls != null) {
                     value_fml32 = new TypedFML32(this.fldtbls);
                  } else {
                     value_fml32 = new TypedFML32();
                  }
               } else {
                  if (!(value instanceof TypedFML32)) {
                     throw new Ferror(6, "Expected TypedFML32");
                  }

                  value_fml32 = new TypedFML32((TypedFML32)value);
               }

               add_length = 4;
               break;
            case 11:
               if (value == null) {
                  value_viewf = null;
                  add_length = 8;
                  break;
               } else {
                  if (!(value instanceof FViewFld)) {
                     throw new Ferror(6, "Expected FViewFld");
                  }

                  FViewFld oldvfld = (FViewFld)value;
                  TypedView32 oldView = oldvfld.getViewData();
                  TypedView32 newView;
                  if (oldView != null && oldView instanceof TypedView32 && (newView = oldView.doClone()) != null) {
                     if (this.use64BitsLong || enable64BitsLong) {
                        try {
                           if (newView.containsOldView()) {
                              throw new Ferror(15, "Nested VIEW32 should be re-built to support 64-bit FLD_LONG");
                           }
                        } catch (AbstractMethodError var27) {
                           throw new Ferror(15, "Nested VIEW32 should be re-built to support 64-bit FLD_LONG");
                        }
                     }

                     String vname = oldvfld.getViewName();
                     value_viewf = new FViewFld(vname, newView);
                     add_length = 8 + vname.length();
                     break;
                  }

                  throw new Ferror(15, "Expected VIEW32 buffer");
               }
            case 12:
               if (value == null) {
                  value_mbstring = null;
               } else {
                  if (!(value instanceof TypedMBString)) {
                     throw new Ferror(6, "Expected TypedMBString");
                  }

                  value_mbstring = new TypedMBString((TypedMBString)value);
               }

               add_length = 16;
         }

         this.len += add_length;
         ++this.nfields;
         if (this.len <= Integer.MAX_VALUE && this.nfields <= 33554431) {
            Integer fldid_key = new Integer(fldid32);
            Integer occ = (Integer)this.fldid_occs.get(fldid_key);
            int int_occ;
            if (occ == null) {
               int_occ = 0;
            } else {
               int_occ = occ + 1;
            }

            this.fldid_occs.put(fldid_key, new Integer(int_occ));
            FmlKey workkey = new FmlKey(fldid32, int_occ);
            switch (fldtype32) {
               case 0:
                  this._flds.put(workkey, value_short);
                  break;
               case 1:
                  this._flds.put(workkey, value_long);
                  break;
               case 2:
                  this._flds.put(workkey, value_char);
                  break;
               case 3:
                  this._flds.put(workkey, value_float);
                  break;
               case 4:
                  this._flds.put(workkey, value_double);
                  break;
               case 5:
                  this._flds.put(workkey, value_string);
                  break;
               case 6:
                  this._flds.put(workkey, value_carray);
               case 7:
               case 8:
               default:
                  break;
               case 9:
                  this._flds.put(workkey, value_ptr);
                  ++this.numPointerFields;
                  this.magic |= 4;
                  break;
               case 10:
                  this._flds.put(workkey, value_fml32);
                  if (value_fml32.hasPointerFields()) {
                     this.magic |= 4;
                  }

                  ++this.numFml32Fields;
                  break;
               case 11:
                  this._flds.put(workkey, value_viewf);
                  ++this.numView32Fields;
                  break;
               case 12:
                  this._flds.put(workkey, value_mbstring);
                  ++this.numMBStringFields;
            }

            ++this.modCount;
         } else {
            this.len -= add_length;
            --this.nfields;
            throw new Ferror(3);
         }
      }
   }

   public synchronized Object Fget(FmlKey key) throws Ferror {
      Short value_short = null;
      Integer value_integer = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      TypedFML32 value_fml32 = null;
      FViewFld vfld = null;
      if (key == null) {
         throw new Ferror(13, "Input key is null");
      } else {
         int fldid32 = key.get_fldid();
         int occurrence = key.get_occurrence();
         if (occurrence < 0) {
            throw new Ferror(13, "Bad key occurrence for fldid32: " + fldid32);
         } else {
            int fldno32;
            if ((fldno32 = fldid32 & 33554431) == 0) {
               throw new Ferror(5, "Unknown fldid32: " + fldid32);
            } else {
               int fldtype32 = fldid32 >> 25 & 63;
               switch (fldtype32) {
                  case 0:
                     if ((value_short = (Short)this._flds.get(key)) == null) {
                        throw new Ferror(4);
                     }

                     return new Short(value_short);
                  case 1:
                     if ((value_long = (Long)this._flds.get(key)) == null) {
                        throw new Ferror(4);
                     }

                     return new Integer(value_long.intValue());
                  case 2:
                     if ((value_char = (Character)this._flds.get(key)) == null) {
                        throw new Ferror(4);
                     }

                     return new Character(value_char);
                  case 3:
                     if ((value_float = (Float)this._flds.get(key)) == null) {
                        throw new Ferror(4);
                     }

                     return new Float(value_float);
                  case 4:
                     if ((value_double = (Double)this._flds.get(key)) == null) {
                        throw new Ferror(4);
                     }

                     return new Double(value_double);
                  case 5:
                     if ((value_string = (String)this._flds.get(key)) == null) {
                        throw new Ferror(4);
                     }

                     return new String(value_string);
                  case 6:
                     if (!this._flds.containsKey(key)) {
                        throw new Ferror(4);
                     } else {
                        byte[] value_carray;
                        if ((value_carray = (byte[])((byte[])this._flds.get(key))) == null) {
                           return null;
                        }

                        byte[] output = new byte[value_carray.length];
                        System.arraycopy(value_carray, 0, output, 0, value_carray.length);
                        return output;
                     }
                  case 7:
                  case 8:
                  default:
                     throw new Ferror(6, "Check fldid32: " + fldid32);
                  case 9:
                     if (!this._flds.containsKey(key)) {
                        throw new Ferror(4);
                     }

                     return this._flds.get(key);
                  case 10:
                     if (!this._flds.containsKey(key)) {
                        throw new Ferror(4);
                     }

                     return new TypedFML32((TypedFML32)this._flds.get(key));
                  case 11:
                     if (!this._flds.containsKey(key)) {
                        throw new Ferror(4);
                     }

                     vfld = (FViewFld)this._flds.get(key);
                     TypedView32 view = vfld.getViewData().doClone();
                     return new FViewFld(vfld.getViewName(), view);
                  case 12:
                     if (!this._flds.containsKey(key)) {
                        throw new Ferror(4);
                     } else {
                        return new TypedMBString((TypedMBString)this._flds.get(key));
                     }
               }
            }
         }
      }
   }

   public synchronized Object Fget(FmlKey key, boolean getLong) throws Ferror {
      Long value_long = null;
      if (!getLong) {
         return this.Fget(key);
      } else if (key == null) {
         throw new Ferror(13, "Input key is null");
      } else {
         int fldid32 = key.get_fldid();
         int occurrence = key.get_occurrence();
         if (occurrence < 0) {
            throw new Ferror(13, "Bad key occurrence for fldid32: " + fldid32);
         } else {
            int fldno32;
            if ((fldno32 = fldid32 & 33554431) == 0) {
               throw new Ferror(5, "Unknown fldid32: " + fldid32);
            } else {
               int fldtype32 = fldid32 >> 25 & 63;
               if (fldtype32 != 1) {
                  return this.Fget(key);
               } else if ((value_long = (Long)this._flds.get(key)) == null) {
                  throw new Ferror(4);
               } else {
                  return new Long(value_long);
               }
            }
         }
      }
   }

   public Object Fget(int fldid, int occurrence) throws Ferror {
      return this.Fget(new FmlKey(fldid, occurrence));
   }

   public Object Fget(int fldid, int occurrence, boolean getLong) throws Ferror {
      return this.Fget(new FmlKey(fldid, occurrence), getLong);
   }

   void updateSortedArray() {
      this.updatedModCount = this.modCount;
      Set eSet = this._flds.entrySet();
      Object[] eArray = eSet.toArray();
      FmlComparator comp = new FmlComparator();
      Arrays.sort(eArray, comp);
      this.myArray = eArray;
   }

   public Iterator Fiterator() {
      return this._flds.entrySet().iterator();
   }

   public Iterator sortedFiterator() {
      if (this.myArray == null || this.updatedModCount != this.modCount) {
         this.updateSortedArray();
      }

      return new FmlIterator();
   }

   public int Foccur(int fldid) {
      Integer occ = (Integer)this.fldid_occs.get(new Integer(fldid));
      return occ == null ? 0 : occ + 1;
   }

   private boolean searchPointerFields() {
      Iterator fmlentries = this.sortedFiterator();

      while(fmlentries.hasNext()) {
         Map.Entry element = (Map.Entry)fmlentries.next();
         FmlKey fmlkey = (FmlKey)element.getKey();
         int newfldid32 = fmlkey.get_fldid();
         switch (this.Fldtype(newfldid32)) {
            case 9:
               return true;
            case 10:
               TypedFML32 myFml32 = (TypedFML32)element.getValue();
               if (myFml32.hasPointerFields()) {
                  return true;
               }
         }
      }

      return false;
   }

   protected boolean hasPointerFields() {
      return (this.magic & 4) != 0;
   }

   public synchronized void Fdel(FmlKey key) throws Ferror {
      boolean ptr_warning = false;
      if (key == null) {
         throw new Ferror(13, "Input key is null");
      } else {
         int fldid32 = key.get_fldid();
         int occurrence = key.get_occurrence();
         if (occurrence < 0) {
            throw new Ferror(13, "Bad key occurrence for fldid32: " + fldid32);
         } else {
            int fldno32;
            if ((fldno32 = fldid32 & 33554431) == 0) {
               throw new Ferror(5, "Unknown fldid32: " + fldid32);
            } else {
               this.Fldtype(fldid32);
               int fldtype32 = fldid32 >> 25 & 63;
               Integer fldid_key = new Integer(fldid32);
               Integer occ = (Integer)this.fldid_occs.get(fldid_key);
               if (occ == null) {
                  throw new Ferror(13, "Bad key occurrence for fldid32: " + fldid32);
               } else {
                  int int_occ = occ;
                  if (int_occ < occurrence) {
                     throw new Ferror(4);
                  } else {
                     Object removed = this._flds.remove(key);
                     FmlKey newkey = new FmlKey(fldid32, -1);
                     if (occurrence < int_occ - 1) {
                        int lcv;
                        for(lcv = occurrence + 1; lcv < int_occ; ++lcv) {
                           newkey.set_occurrence(lcv);
                           Object mycopy = this.Fget(newkey);
                           newkey.set_occurrence(lcv - 1);
                           this.Fchg(newkey, mycopy);
                        }

                        newkey.set_occurrence(lcv - 1);
                        this._flds.remove(newkey);
                     }

                     --int_occ;
                     if (int_occ >= 0) {
                        this.fldid_occs.put(fldid_key, new Integer(int_occ));
                     } else {
                        this.fldid_occs.remove(fldid_key);
                     }

                     int sub_length;
                     switch (fldtype32) {
                        case 0:
                        case 2:
                        case 3:
                           sub_length = 8;
                           break;
                        case 1:
                           sub_length = 12;
                           if (this.numLongFields > 0) {
                              --this.numLongFields;
                           }
                           break;
                        case 4:
                           sub_length = 12;
                           break;
                        case 5:
                           String value_string = (String)removed;
                           sub_length = 12 + Utilities.roundup4(value_string.length() + 1);
                           break;
                        case 6:
                           if (removed == null) {
                              sub_length = 12;
                           } else {
                              byte[] value_carray = (byte[])((byte[])removed);
                              sub_length = 12 + Utilities.roundup4(value_carray.length);
                           }
                           break;
                        case 7:
                        case 8:
                        default:
                           throw new Ferror(6, "Check fldid32: " + fldid32);
                        case 9:
                           if (removed == null) {
                              sub_length = 4 + Utilities.xdr_length_string((String)null) + Utilities.xdr_length_string((String)null) + 4;
                           } else {
                              TypedBuffer value_ptr = (TypedBuffer)removed;
                              sub_length = 4 + Utilities.xdr_length_string(value_ptr.getType()) + Utilities.xdr_length_string(value_ptr.getSubtype());
                           }

                           if (this.numPointerFields > 0) {
                              --this.numPointerFields;
                           }
                           break;
                        case 10:
                           sub_length = 4;
                           if (this.numFml32Fields > 0) {
                              --this.numFml32Fields;
                           }
                           break;
                        case 11:
                           sub_length = 8;
                           if (removed != null) {
                              String vname = ((FViewFld)removed).getViewName();
                              sub_length = vname.length();
                           }

                           if (this.numView32Fields > 0) {
                              --this.numView32Fields;
                           }
                           break;
                        case 12:
                           if (removed == null) {
                              sub_length = 16;
                           } else {
                              TypedMBString value_mbstring = (TypedMBString)removed;
                              sub_length = 16;
                           }

                           if (this.numMBStringFields > 0) {
                              --this.numMBStringFields;
                           }
                     }

                     this.len -= sub_length;
                     --this.nfields;
                     if ((this.magic & 4) != 0 && this.numPointerFields == 0) {
                        if (this.numFml32Fields == 0) {
                           this.magic &= -5;
                        } else if (!this.searchPointerFields()) {
                           this.magic &= -5;
                        }
                     }

                     ++this.modCount;
                  }
               }
            }
         }
      }
   }

   public void Fdel(int fldid, int occurrence) throws Ferror {
      this.Fdel(new FmlKey(fldid, occurrence));
   }

   public synchronized String Fname(int fldid32) throws Ferror {
      int lcv;
      String ret;
      if (this.fldtbls != null) {
         for(lcv = 0; lcv < this.fldtbls.length; ++lcv) {
            if (this.fldtbls[lcv] != null && (ret = this.fldtbls[lcv].Fldid_to_name(fldid32)) != null) {
               return ret;
            }
         }
      }

      for(lcv = 0; lcv < 6; ++lcv) {
         if ((ret = this.systbls[lcv].Fldid_to_name(fldid32)) != null) {
            return ret;
         }
      }

      throw new Ferror(5, "Unknown fldid32: " + fldid32);
   }

   public synchronized int Fldid(String name) throws Ferror {
      int fldid32;
      int lcv;
      if (this.fldtbls != null) {
         for(lcv = 0; lcv < this.fldtbls.length; ++lcv) {
            if (this.fldtbls[lcv] != null && (fldid32 = this.fldtbls[lcv].name_to_Fldid(name)) != -1) {
               return fldid32;
            }
         }
      }

      for(lcv = 0; lcv < 6; ++lcv) {
         if ((fldid32 = this.systbls[lcv].name_to_Fldid(name)) != -1) {
            return fldid32;
         }
      }

      throw new Ferror(8, "Unknown fldname: " + name);
   }

   public int Fused() {
      return this.len;
   }

   public synchronized void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TypedFML32/_tmpresend/" + encoder);
      }

      Short value_short = null;
      Integer value_integer = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      TypedBuffer value_ptr = null;
      TypedView32 value_view = null;
      FViewFld vfld = null;
      int output_data_size = false;
      byte[][] present_ptr_data = (byte[][])null;
      int[] present_ptr_data_size = null;
      int ptrCount = 0;
      int fmlCount = false;
      boolean indeedUse64BitsLong = false;
      Boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
      if (encoder == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TypedFML32/_tmpresend/10/encoder==null");
         }

         throw new TPException(12, "Encoder not defined");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("numPointerFields=" + this.numPointerFields + " numView32Fields=" + this.numView32Fields + " numFml32Fields=" + this.numFml32Fields + " numLongFields=" + this.numLongFields);
         }

         if (this.callFromReadWriteObject) {
            if (enable64BitsLong) {
               indeedUse64BitsLong = true;
            } else {
               indeedUse64BitsLong = false;
            }
         } else {
            if (this.numLongFields > 0 && this.use64BitsLong != enable64BitsLong) {
               throw new TPException(12, "Remote domain or WTC doesn't support 64-bit FLD_LONG");
            }

            indeedUse64BitsLong = this.use64BitsLong;
         }

         int actual_len = this.len;

         try {
            encoder.writeInt(this.magic);
            Iterator fmlentries;
            FmlKey fmlkey;
            Map.Entry element;
            int fldid32;
            int fldtype32;
            if (this.numPointerFields > 0 || this.numFml32Fields > 0 || this.numMBStringFields > 0 || this.numView32Fields > 0) {
               present_ptr_data = new byte[this.numPointerFields + this.numFml32Fields + this.numMBStringFields + this.numView32Fields][];
               present_ptr_data_size = new int[this.numPointerFields + this.numFml32Fields + this.numMBStringFields + this.numView32Fields];
               if (this._flds.size() == 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TypedFML32/_tmpresend/20/flds==empty");
                  }

                  throw new TPException(12, "Flds not defined");
               }

               fmlentries = this.sortedFiterator();
               int added_size = 0;

               label205:
               while(true) {
                  while(true) {
                     do {
                        if (!fmlentries.hasNext()) {
                           actual_len = this.len + added_size;
                           break label205;
                        }

                        element = (Map.Entry)fmlentries.next();
                        fmlkey = (FmlKey)element.getKey();
                        fldid32 = fmlkey.get_fldid();
                     } while((fldtype32 = this.Fldtype(fldid32)) != 9 && fldtype32 != 10 && fldtype32 != 12 && fldtype32 != 11);

                     if (fldtype32 == 11) {
                        vfld = (FViewFld)element.getValue();
                        if (vfld == null) {
                           present_ptr_data_size[ptrCount] = 0;
                           present_ptr_data[ptrCount] = null;
                           ++ptrCount;
                           continue;
                        }
                     } else {
                        value_ptr = (TypedBuffer)element.getValue();
                        if (value_ptr == null) {
                           present_ptr_data_size[ptrCount] = 0;
                           present_ptr_data[ptrCount] = null;
                           ++ptrCount;
                           continue;
                        }
                     }

                     if (this.in_presend) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/FML32/_tmpresend/45/Cycle");
                        }

                        throw new TPException(12, "Cycle found in FML buffer");
                     }

                     try {
                        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                        DataOutputStream interior_encoder = new DataOutputStream(bstream);
                        this.in_presend = true;
                        if (traceEnabled) {
                           ntrace.doTrace("/FML32/_tmpresend/55/calling internal ptr,fml32");
                        }

                        if (fldtype32 == 12) {
                           byte[] mbpack = this.Fmbpack32((TypedMBString)value_ptr);
                           interior_encoder.write(mbpack);
                        } else if (fldtype32 == 11) {
                           vfld.getViewData().setUse64BitsLong(indeedUse64BitsLong);
                           vfld.getViewData()._tmpresend(interior_encoder);
                        } else {
                           value_ptr._tmpresend(interior_encoder);
                        }

                        this.in_presend = false;
                        present_ptr_data_size[ptrCount] = interior_encoder.size();
                        present_ptr_data[ptrCount] = bstream.toByteArray();
                     } catch (TPException var40) {
                        this.in_presend = false;
                        if (traceEnabled) {
                           ntrace.doTrace("*]/FML32/_tmpresend/20/" + var40);
                        }

                        throw var40;
                     } catch (IOException var41) {
                        this.in_presend = false;
                        if (traceEnabled) {
                           ntrace.doTrace("*]/FML32/_tmpresend/30/" + var41);
                        }

                        throw new TPException(12, "Embedded IO Exception: " + var41);
                     } catch (Exception var42) {
                        this.in_presend = false;
                        if (traceEnabled) {
                           ntrace.doTrace("*]/FML32/_tmpresend/40");
                        }

                        throw new TPException(12, "Embedded Exception: " + var42);
                     }

                     added_size += Utilities.roundup4(present_ptr_data_size[ptrCount]);
                     ++ptrCount;
                  }
               }
            }

            if (this.numLongFields > 0 && !indeedUse64BitsLong) {
               actual_len = this.len - 4 * this.numLongFields;
            }

            encoder.writeInt(actual_len);
            encoder.writeInt(this.maxlen);
            encoder.writeInt(this.nfields);
            encoder.writeInt(this.nie);
            encoder.writeInt(this.indxintvl);
            ptrCount = 0;
            fmlentries = this.sortedFiterator();

            label257:
            while(fmlentries.hasNext()) {
               element = (Map.Entry)fmlentries.next();
               fmlkey = (FmlKey)element.getKey();
               fldid32 = fmlkey.get_fldid();
               fldtype32 = fldid32 >> 25 & 63;
               encoder.writeInt(fldid32);
               int vallen;
               int pad;
               int lcv;
               switch (fldtype32) {
                  case 0:
                     value_short = (Short)element.getValue();
                     encoder.writeInt(value_short.intValue());
                     break;
                  case 1:
                     if (!indeedUse64BitsLong) {
                        value_long = (Long)element.getValue();
                        if (value_long > 2147483647L) {
                           throw new TPException(4, "64-bit Long is not supported");
                        }

                        encoder.writeInt(value_long.intValue());
                     } else {
                        value_long = (Long)element.getValue();
                        encoder.writeLong(value_long);
                     }
                     break;
                  case 2:
                     value_char = (Character)element.getValue();
                     char mychar = value_char;
                     encoder.writeInt(mychar);
                     break;
                  case 3:
                     value_float = (Float)element.getValue();
                     encoder.writeFloat(value_float);
                     break;
                  case 4:
                     value_double = (Double)element.getValue();
                     encoder.writeDouble(value_double);
                     break;
                  case 5:
                     value_string = (String)element.getValue();
                     byte[] value_stringBytes = Utilities.getEncBytes(value_string);
                     vallen = value_stringBytes.length + 1;
                     pad = Utilities.roundup4(vallen) - vallen;
                     encoder.writeInt(value_stringBytes.length + 1 + 4 + 4);
                     encoder.writeInt(value_stringBytes.length + 1);
                     encoder.write(value_stringBytes);
                     lcv = 0;

                     while(true) {
                        if (lcv > pad) {
                           continue label257;
                        }

                        encoder.writeByte(0);
                        ++lcv;
                     }
                  case 6:
                     byte[] value_carray = (byte[])((byte[])element.getValue());
                     if (value_carray != null) {
                        vallen = value_carray.length;
                        pad = Utilities.roundup4(vallen) - vallen;
                        encoder.writeInt(vallen + 4 + 4);
                        encoder.writeInt(vallen);
                        encoder.write(value_carray);

                        for(lcv = 0; lcv < pad; ++lcv) {
                           encoder.writeByte(0);
                        }
                     } else {
                        encoder.writeInt(8);
                        encoder.writeInt(0);
                     }
                  case 7:
                  case 8:
                  default:
                     break;
                  case 9:
                     if (present_ptr_data != null && present_ptr_data[ptrCount] != null) {
                        value_ptr = (TypedBuffer)element.getValue();
                        Utilities.xdr_encode_string(encoder, value_ptr.getType());
                        Utilities.xdr_encode_string(encoder, value_ptr.getSubtype());
                        vallen = present_ptr_data_size[ptrCount];
                        pad = Utilities.roundup4(vallen) - vallen;
                        encoder.writeInt(vallen);
                        encoder.write(present_ptr_data[ptrCount], 0, vallen);

                        for(lcv = 0; lcv < pad; ++lcv) {
                           encoder.writeByte(0);
                        }
                     } else {
                        encoder.writeInt(0);
                        encoder.writeInt(0);
                        encoder.writeInt(0);
                     }

                     ++ptrCount;
                     break;
                  case 10:
                     if (present_ptr_data != null && present_ptr_data[ptrCount] != null) {
                        vallen = present_ptr_data_size[ptrCount];
                        pad = Utilities.roundup4(vallen) - vallen;
                        encoder.write(present_ptr_data[ptrCount], 0, vallen);

                        for(lcv = 0; lcv < pad; ++lcv) {
                           encoder.writeByte(0);
                        }

                        ++ptrCount;
                        break;
                     }

                     WTCLogger.logErrorFML32badField(ptrCount);
                     throw new TPException(12, "Invalid pointer field found for " + fldid32);
                  case 11:
                     if (present_ptr_data != null && present_ptr_data[ptrCount] != null) {
                        vfld = (FViewFld)element.getValue();
                        Utilities.xdr_encode_string(encoder, vfld.getViewName());
                        vallen = present_ptr_data_size[ptrCount];
                        encoder.write(present_ptr_data[ptrCount], 0, vallen);
                     }

                     ++ptrCount;
                     break;
                  case 12:
                     if (present_ptr_data != null && present_ptr_data[ptrCount] != null) {
                        byte[] mbpack = present_ptr_data[ptrCount];
                        ByteArrayInputStream bis = new ByteArrayInputStream(mbpack);
                        DataInputStream dis = new DataInputStream(bis);
                        int udlen = dis.readInt();
                        vallen = mbpack.length - 4;
                        byte[] value = new byte[vallen];
                        dis.read(value);
                        pad = Utilities.roundup4(vallen) - vallen;
                        encoder.writeInt(12 + vallen);
                        encoder.writeInt(udlen);
                        encoder.writeInt(vallen);
                        encoder.write(value);

                        for(lcv = 0; lcv < pad; ++lcv) {
                           encoder.writeByte(0);
                        }
                     } else {
                        encoder.writeInt(16);
                        encoder.writeInt(0);
                        encoder.writeInt(0);
                     }

                     ++ptrCount;
               }
            }
         } catch (IOException var43) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TypedFML32/_tmpresend/50/" + var43);
            }

            throw var43;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TypedFML32/_tmpresend/60/");
         }

      }
   }

   public synchronized void _tmpostrecv(DataInputStream decoder, int recv_size) throws IOException, TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TypedFML32/_tmpostrecv/" + recv_size);
      }

      int occurrence = 0;
      int current_fldid32 = -1;
      Short value_short = null;
      Long value_long = null;
      Integer value_int = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      TypedBuffer value_ptr = null;
      TypedFML32 value_fml32 = null;
      TypedMBString value_mbstring = null;
      TypedView32 view = null;
      FViewFld vfld = null;
      boolean indeedUse64BitsLong = false;
      Boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");

      try {
         this.magic = decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         int mynfields = decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         if (this.callFromReadWriteObject) {
            if (enable64BitsLong) {
               indeedUse64BitsLong = true;
            } else {
               indeedUse64BitsLong = false;
            }
         } else {
            if (this.use64BitsLong != enable64BitsLong) {
               throw new TPException(12, "Remote domain or WTC doesn't support 64-bit FLD_LONG");
            }

            indeedUse64BitsLong = this.use64BitsLong;
         }

         for(int lcv = 0; lcv < mynfields; ++lcv) {
            int fldid32 = decoder.readInt();
            if (this.Fldno(fldid32) == 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TypedFML32/_tmpostrecv/10/" + fldid32);
               }

               throw new TPException(4, "Invalid field number: " + fldid32);
            }

            int fldtype32 = this.Fldtype(fldid32);
            int lcv2;
            FmlKey fmlkey;
            int fldlen;
            int pad;
            switch (fldtype32) {
               case 0:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  value_short = new Short((short)decoder.readInt());
                  this.Fchg(fmlkey, value_short);
                  break;
               case 1:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  if (indeedUse64BitsLong) {
                     value_long = new Long(decoder.readLong());
                  } else {
                     value_long = new Long((long)decoder.readInt());
                  }

                  this.Fchg(fmlkey, value_long);
                  break;
               case 2:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  value_char = new Character((char)decoder.readInt());
                  this.Fchg(fmlkey, value_char);
                  break;
               case 3:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  value_float = new Float(decoder.readFloat());
                  this.Fchg(fmlkey, value_float);
                  break;
               case 4:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  value_double = new Double(decoder.readDouble());
                  this.Fchg(fmlkey, value_double);
                  break;
               case 5:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  fldlen = decoder.readInt() - 4 - 4;
                  pad = Utilities.roundup4(fldlen) - fldlen;
                  decoder.readInt();
                  if (fldlen <= 1) {
                     decoder.skipBytes(pad + 1);
                     value_string = new String("");
                  } else {
                     byte[] stringme = new byte[fldlen - 1];

                     for(lcv2 = 0; lcv2 < fldlen - 1; ++lcv2) {
                        stringme[lcv2] = decoder.readByte();
                     }

                     decoder.skipBytes(pad + 1);
                     value_string = Utilities.getEncString(stringme);
                  }

                  this.Fchg(fmlkey, value_string);
                  break;
               case 6:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  fldlen = decoder.readInt() - 4 - 4;
                  pad = Utilities.roundup4(fldlen) - fldlen;
                  decoder.readInt();
                  byte[] value_carray;
                  if (fldlen == 0) {
                     value_carray = null;
                  } else {
                     value_carray = new byte[fldlen];

                     for(lcv2 = 0; lcv2 < fldlen; ++lcv2) {
                        value_carray[lcv2] = decoder.readByte();
                     }

                     decoder.skipBytes(pad);
                  }

                  this.Fchg(fmlkey, value_carray);
                  break;
               case 7:
               case 8:
               default:
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TypedFML32/_tmpostrecv/60/");
                  }

                  throw new TPException(9, "Got field of unknown type failing " + fldtype32);
               case 9:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  if (this.myScratch == null) {
                     this.myScratch = new byte[50];
                  }

                  String type = Utilities.xdr_decode_string(decoder, this.myScratch);
                  Utilities.xdr_decode_string(decoder, this.myScratch);
                  if (type == null) {
                     if (decoder.readInt() != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/TypedFML32/_tmpostrecv/20/");
                        }

                        throw new TPException(9, "Malformed FML32 buffer");
                     }

                     value_ptr = null;
                  } else {
                     int array_size;
                     if ((array_size = decoder.readInt()) == 0) {
                        value_ptr = null;
                     } else {
                        if ((value_ptr = TypedBufferFactory.createTypedBuffer(type, (String)null)) == null) {
                           WTCLogger.logWarnFML32badType(type);

                           for(lcv2 = 0; lcv2 < array_size; ++lcv2) {
                              decoder.readByte();
                           }
                        }

                        if (value_ptr != null) {
                           try {
                              value_ptr._tmpostrecv(decoder, array_size);
                           } catch (TPException var41) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/TypedFML32/_tmpostrecv/30/" + var41);
                              }

                              throw var41;
                           }
                        }

                        pad = Utilities.roundup4(array_size) - array_size;

                        for(lcv2 = 0; lcv2 < pad; ++lcv2) {
                           decoder.readByte();
                        }
                     }
                  }

                  this.Fchg(fmlkey, value_ptr);
                  break;
               case 10:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  value_fml32 = new TypedFML32();
                  value_fml32.setUse64BitsLong(indeedUse64BitsLong);
                  value_fml32._tmpostrecv(decoder, 0);
                  this.Fchg(fmlkey, value_fml32);
                  break;
               case 11:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  if (this.myScratch == null) {
                     this.myScratch = new byte[50];
                  }

                  String subtype = Utilities.xdr_decode_string(decoder, this.myScratch);
                  view = (TypedView32)TypedBufferFactory.createTypedBuffer("VIEW32", subtype);
                  if (view != null) {
                     if (vfld == null) {
                        vfld = new FViewFld();
                     }

                     try {
                        if (indeedUse64BitsLong && view.containsOldView()) {
                           throw new TPException(4, "Expected a VIEW32 class build with -64bit option");
                        }

                        view.setUse64BitsLong(indeedUse64BitsLong);
                        view._tmpostrecv(decoder, 0);
                        vfld.setViewName(subtype);
                        vfld.setViewData(view);
                        this.Fchg(fmlkey, vfld);
                     } catch (AbstractMethodError var40) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/TypedFML32/_tmpostrecv/try to use an old version of VIEW class");
                        }

                        throw new TPException(4, "Expected a VIEW32 class build with -64bit option");
                     }
                  }
                  break;
               case 12:
                  if (current_fldid32 != fldid32) {
                     occurrence = 0;
                     current_fldid32 = fldid32;
                  }

                  fmlkey = new FmlKey(fldid32, occurrence++);
                  fldlen = decoder.readInt();
                  int udlen = decoder.readInt();
                  int vallen = decoder.readInt();
                  if (vallen == 0) {
                     value_mbstring = null;
                  } else {
                     ByteArrayOutputStream bos = new ByteArrayOutputStream();
                     DataOutputStream dos = new DataOutputStream(bos);
                     dos.writeInt(udlen);
                     byte[] value = new byte[vallen];

                     for(lcv2 = 0; lcv2 < vallen; ++lcv2) {
                        value[lcv2] = decoder.readByte();
                     }

                     dos.write(value);
                     pad = Utilities.roundup4(fldlen) - fldlen;
                     decoder.skipBytes(pad);
                     value_mbstring = this.Fmbunpack32(bos.toByteArray());
                  }

                  this.Fchg(fmlkey, value_mbstring);
            }
         }
      } catch (IOException var42) {
         WTCLogger.logIOEbadTypedBuffer("TypedFML32", var42.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("*]/TypedFML32/_tmpostrecv/70/" + var42);
         }

         throw var42;
      } catch (Ferror var43) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TypedFML32/_tmpostrecv/80/" + var43);
         }

         throw new TPException(12, "Unable to update FML32 buffer.  Embedded Ferror is " + var43);
      } catch (TPException var44) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TypedFML32/_tmpostrecv/90/" + var44);
         }

         throw var44;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TypedFML32/_tmpostrecv/100/");
      }

   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      byte[] fillin = null;
      int send_size = false;

      byte[] fillin;
      int send_size;
      try {
         ByteArrayOutputStream bstream = new ByteArrayOutputStream();
         DataOutputStream encoder = new DataOutputStream(bstream);
         this.callFromReadWriteObject = true;
         this._tmpresend(encoder);
         this.callFromReadWriteObject = false;
         send_size = encoder.size();
         fillin = bstream.toByteArray();
      } catch (TPException var6) {
         throw new IOException("TypedFML32 presend threw TPException " + var6);
      }

      out.write(fillin, 0, send_size);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this._commonInit((TypedFML32)null, 256, 64);

      try {
         DataInputStream decoder = new DataInputStream(in);
         this.callFromReadWriteObject = true;
         this._tmpostrecv(decoder, in.available());
         this.callFromReadWriteObject = false;
      } catch (TPException var3) {
         throw new IOException(var3.toString());
      }

      this.convertMBStringFieldsFromBytes((String)null);
   }

   private byte[] Fmbpack32(TypedMBString mbstring) {
      byte[] magic = new byte[]{77, 66, 0, 0};

      try {
         String mbencoding = mbstring.getMBEncoding();
         byte[] mbencbytes;
         if (mbencoding == null) {
            magic[3] = (byte)(magic[3] | 1);
            mbencbytes = null;
            mbstring.convertToBytes(this.mbencoding);
         } else {
            mbencbytes = Utilities.getEncBytes(mbencoding);
            mbstring.convertToBytes((String)null);
         }

         byte[] udata = mbstring.getBytes();
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(bos);
         dos.writeInt(udata.length);
         dos.write(magic);
         if (mbencbytes != null) {
            dos.write(mbencbytes);
            dos.write(0);
         }

         dos.write(udata);
         return bos.toByteArray();
      } catch (Exception var8) {
         return null;
      }
   }

   private TypedMBString Fmbunpack32(byte[] buffer) {
      ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
      DataInputStream dis = new DataInputStream(bis);

      try {
         int udlen = dis.readInt();
         byte[] magic = new byte[4];
         dis.read(magic);
         String mbencoding = null;
         if ((magic[3] & 1) == 0) {
            int mbenclen = buffer.length - 4 - magic.length - udlen - 1;
            byte[] mbencbytes = new byte[mbenclen];
            dis.read(mbencbytes);
            dis.read();
            mbencoding = Utilities.getEncString(mbencbytes);
         }

         byte[] udata = new byte[udlen];
         dis.read(udata);
         TypedMBString mbstring = new TypedMBString();
         mbstring.setBytes(udata);
         if (mbencoding != null) {
            mbstring.setMBEncoding(mbencoding);
         }

         return mbstring;
      } catch (Exception var9) {
         return null;
      }
   }

   void convertMBStringFieldsFromBytes(String bufmbenc) throws UnsupportedEncodingException {
      if (bufmbenc == null) {
         bufmbenc = this.mbencoding;
      }

      Iterator fmlentries = this.sortedFiterator();

      while(fmlentries.hasNext()) {
         Map.Entry element = (Map.Entry)fmlentries.next();
         Object value = element.getValue();
         if (value != null) {
            FmlKey fmlkey = (FmlKey)element.getKey();
            int fldid = fmlkey.get_fldid();
            switch (this.Fldtype(fldid)) {
               case 10:
                  TypedFML32 fml32 = (TypedFML32)value;
                  fml32.convertMBStringFieldsFromBytes(bufmbenc);
                  break;
               case 12:
                  TypedMBString mbstring = (TypedMBString)value;
                  mbstring.convertFromBytes(bufmbenc);
            }
         }
      }

   }

   boolean hasMBStringFields() {
      Iterator fmlentries = this.sortedFiterator();

      while(fmlentries.hasNext()) {
         Map.Entry element = (Map.Entry)fmlentries.next();
         Object value = element.getValue();
         if (value != null) {
            FmlKey fmlkey = (FmlKey)element.getKey();
            int fldid = fmlkey.get_fldid();
            switch (this.Fldtype(fldid)) {
               case 10:
                  TypedFML32 fml32 = (TypedFML32)value;
                  if (fml32.hasMBStringFields()) {
                     return true;
                  }
                  break;
               case 12:
                  return true;
            }
         }
      }

      return false;
   }

   public void setUse64BitsLong(boolean val) {
      this.use64BitsLong = val;
   }

   public boolean getUse64BitsLong() {
      return this.use64BitsLong;
   }

   public boolean containsOldView() {
      if (this.numView32Fields <= 0) {
         return false;
      } else {
         Iterator fmlentries = this.sortedFiterator();

         while(true) {
            Map.Entry element;
            int fldtype32;
            do {
               if (!fmlentries.hasNext()) {
                  return false;
               }

               element = (Map.Entry)fmlentries.next();
               FmlKey fmlkey = (FmlKey)element.getKey();
               int fldid32 = fmlkey.get_fldid();
               fldtype32 = this.Fldtype(fldid32);
            } while(fldtype32 != 11);

            TypedView32 vdata = ((FViewFld)element.getValue()).getViewData();

            try {
               if (!vdata.getBuiltWith64Bit() || vdata.containsOldView()) {
                  return true;
               }
            } catch (AbstractMethodError var9) {
               return true;
            }
         }
      }
   }

   private final class FmlIterator implements Iterator {
      Object current;
      int i = 0;
      int s;
      int expectedModCount;

      public FmlIterator() {
         this.expectedModCount = TypedFML32.this.modCount;
         this.s = TypedFML32.this.myArray.length;
      }

      public boolean hasNext() {
         return this.i < this.s;
      }

      public Object next() {
         if (TypedFML32.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         } else if (this.i < this.s) {
            this.current = TypedFML32.this.myArray[this.i++];
            return this.current;
         } else {
            throw new NoSuchElementException("Exceed array boundary " + this.s + ": " + this.i);
         }
      }

      public void remove() {
         if (this.current == null) {
            throw new IllegalStateException();
         } else if (TypedFML32.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         } else {
            FmlKey k = (FmlKey)((Map.Entry)this.current).getKey();

            try {
               TypedFML32.this.Fdel(k);
               TypedFML32.this.updateSortedArray();
               this.current = null;
               this.s = TypedFML32.this.myArray.length;
               this.expectedModCount = TypedFML32.this.modCount;
            } catch (Ferror var3) {
               throw new IllegalStateException();
            }
         }
      }
   }

   private final class FmlComparator implements Comparator {
      private FmlComparator() {
      }

      public int compare(Object o1, Object o2) {
         Map.Entry e1 = (Map.Entry)o1;
         Map.Entry e2 = (Map.Entry)o2;
         FmlKey k1 = (FmlKey)e1.getKey();
         FmlKey k2 = (FmlKey)e2.getKey();
         return k1.compareTo(k2);
      }

      public boolean equals(Object obj) {
         return obj == this && obj instanceof FmlComparator;
      }

      // $FF: synthetic method
      FmlComparator(Object x1) {
         this();
      }
   }
}
