package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public final class TypedFML extends StandardTypes implements TypedBuffer, Serializable, FML {
   private static final long serialVersionUID = 4289835762041622281L;
   private static final int F_MAGIC_BASE = -10000;
   private static final int F_MAGICBITS = 1;
   private static final int F_MAGIC = -9999;
   private static final int F_OVHD = 24;
   private static final int F_MAXLEN = 65535;
   private static final int F_MAXFLDS = 8191;
   private static final int FNMASK = 8191;
   private static final int FTMASK = 7;
   private static final int FTSHIFT = 13;
   private static final int FLDID_SIZE = 2;
   private static final int FLDLEN_SIZE = 2;
   private static final int INITIAL_SIZE = 1024;
   private int magic = -9999;
   private int len = 24;
   private int maxlen = 65535;
   private int nfields;
   private int nie;
   private int indxintvl = 16;
   private TreeMap flds;
   private HashMap fldid_occs;
   private FldTbl[] fldtbls;
   private Usysflds usys;
   private tmqflds tmq;
   private int numLongFields;
   private boolean use64BitsLong = false;
   private boolean callFromReadWriteObject = false;

   public TypedFML() {
      super("FML", 18);
      this.flds = new TreeMap();
      this.fldid_occs = new HashMap();
      this.usys = new Usysflds();
      this.tmq = new tmqflds();
   }

   public TypedFML(FldTbl[] tables) {
      super("FML", 18);
      this.flds = new TreeMap();
      this.fldid_occs = new HashMap();
      this.usys = new Usysflds();
      this.tmq = new tmqflds();
      if (tables != null) {
         this.fldtbls = new FldTbl[tables.length];

         for(int lcv = 0; lcv < tables.length; ++lcv) {
            this.fldtbls[lcv] = tables[lcv];
         }

      }
   }

   public TypedFML(FldTbl table) {
      super("FML", 18);
      this.flds = new TreeMap();
      this.fldid_occs = new HashMap();
      this.usys = new Usysflds();
      this.tmq = new tmqflds();
      if (table != null) {
         this.fldtbls = new FldTbl[1];
         this.fldtbls[0] = table;
      }
   }

   public TypedFML(TypedFML copyFrom) {
      super("FML", 18);
      this.magic = copyFrom.magic;
      this.len = copyFrom.len;
      this.maxlen = copyFrom.maxlen;
      this.nfields = copyFrom.nfields;
      this.nie = copyFrom.nie;
      this.indxintvl = copyFrom.indxintvl;
      this.flds = new TreeMap(copyFrom.flds);
      this.fldid_occs = new HashMap(copyFrom.fldid_occs);
      this.setFieldTables(copyFrom.fldtbls);
      this.usys = new Usysflds();
      this.tmq = new tmqflds();
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

   public int Fldno(int fldid) {
      return fldid & 8191;
   }

   public int Fldtype(int fldid) {
      return fldid >> 13 & 7;
   }

   private int roundup4(int a) {
      return a + 3 & -4;
   }

   public synchronized void Fchg(FmlKey key, Object value) throws Ferror {
      Short value_short = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      if (key == null) {
         throw new Ferror(13);
      } else {
         Boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
         FmlKey workkey = new FmlKey(key);
         int fldid = workkey.get_fldid();
         int occurance = workkey.get_occurance();
         if (this.Fldno(fldid) == 0) {
            throw new Ferror(5);
         } else {
            Integer occ = (Integer)this.fldid_occs.get(new Integer(fldid));
            Integer fldid_key = new Integer(fldid);
            boolean exist = false;
            Object org_value = null;
            int org_length = 0;
            int int_occ;
            if (occ == null) {
               int_occ = 0;
            } else {
               int_occ = occ + 1;
            }

            if (occurance >= 0 && occurance < int_occ) {
               exist = true;
               org_value = this.flds.get(workkey);
            }

            int fldtype = this.Fldtype(fldid);
            int add_length;
            int lcv;
            byte null_add_length;
            switch (fldtype) {
               case 0:
                  if (value == null) {
                     value_short = new Short((short)0);
                  } else {
                     if (!(value instanceof Short)) {
                        throw new Ferror(6);
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
                        value_long = new Long(((Integer)value).longValue());
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
                        throw new Ferror(6);
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
                        throw new Ferror(6);
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
                        throw new Ferror(6);
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
                        throw new Ferror(6);
                     }

                     value_string = new String((String)value);
                  }

                  add_length = 12 + this.roundup4(value_string.length() + 1);
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
                        throw new Ferror(6);
                     }

                     value_carray = new byte[((byte[])((byte[])value)).length];

                     for(lcv = 0; lcv < value_carray.length; ++lcv) {
                        value_carray[lcv] = ((byte[])((byte[])value))[lcv];
                     }

                     add_length = 12 + this.roundup4(value_carray.length);
                  }

                  null_add_length = 12;
                  if (exist) {
                     org_length = 12 + Utilities.roundup4(((byte[])((byte[])org_value)).length);
                  }
                  break;
               default:
                  throw new Ferror(5);
            }

            if (occurance == -1) {
               workkey.set_occurance(int_occ);
               this.fldid_occs.put(fldid_key, new Integer(int_occ));
            } else {
               if (occurance >= int_occ) {
                  this.fldid_occs.put(fldid_key, new Integer(occurance));
               }

               for(lcv = int_occ; lcv < occurance - 1; ++lcv) {
                  FmlKey nullkey = new FmlKey(fldid, lcv);
                  this.len += null_add_length;
                  ++this.nfields;
                  if (this.len > 65535 || this.nfields > 8191) {
                     this.len -= null_add_length;
                     --this.nfields;
                     this.fldid_occs.put(fldid_key, new Integer(lcv - 1));
                     throw new Ferror(3);
                  }

                  switch (fldtype) {
                     case 0:
                        Short null_short = new Short((short)0);
                        this.flds.put(nullkey, null_short);
                        break;
                     case 1:
                        Long null_long = new Long(0L);
                        this.flds.put(nullkey, null_long);
                        ++this.numLongFields;
                        break;
                     case 2:
                        Character null_char = new Character('\u0000');
                        this.flds.put(nullkey, null_char);
                        break;
                     case 3:
                        Float null_float = new Float(0.0);
                        this.flds.put(nullkey, null_float);
                        break;
                     case 4:
                        Double null_double = new Double(0.0);
                        this.flds.put(nullkey, null_double);
                        break;
                     case 5:
                        String null_string = new String("");
                        this.flds.put(nullkey, null_string);
                        break;
                     case 6:
                        this.flds.put(nullkey, (Object)null);
                  }
               }
            }

            if (exist) {
               this.len -= org_length;
            } else {
               ++this.nfields;
            }

            this.len += add_length;
            if (this.len <= 65535 && this.nfields <= 8191) {
               switch (fldtype) {
                  case 0:
                     this.flds.put(workkey, value_short);
                     break;
                  case 1:
                     this.flds.put(workkey, value_long);
                     if (!exist) {
                        ++this.numLongFields;
                     }
                     break;
                  case 2:
                     this.flds.put(workkey, value_char);
                     break;
                  case 3:
                     this.flds.put(workkey, value_float);
                     break;
                  case 4:
                     this.flds.put(workkey, value_double);
                     break;
                  case 5:
                     this.flds.put(workkey, value_string);
                     break;
                  case 6:
                     this.flds.put(workkey, value_carray);
               }

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

   public void Fchg(int fldid, int occurance, Object value) throws Ferror {
      this.Fchg(new FmlKey(fldid, occurance), value);
   }

   public synchronized void Fadd(int fldid, Object value) throws Ferror {
      Short value_short = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      if (this.Fldno(fldid) == 0) {
         throw new Ferror(5, "Unknown fldid: " + fldid);
      } else {
         int fldtype = this.Fldtype(fldid);
         int add_length;
         switch (fldtype) {
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
            default:
               throw new Ferror(6, "Check fldid: " + fldid);
         }

         this.len += add_length;
         ++this.nfields;
         if (this.len <= 65535 && this.nfields <= 8191) {
            Integer fldid_key = new Integer(fldid);
            Integer occ = (Integer)this.fldid_occs.get(fldid_key);
            int int_occ;
            if (occ == null) {
               int_occ = 0;
            } else {
               int_occ = occ + 1;
            }

            this.fldid_occs.put(fldid_key, new Integer(int_occ));
            FmlKey workkey = new FmlKey(fldid, int_occ);
            switch (fldtype) {
               case 0:
                  this.flds.put(workkey, value_short);
                  break;
               case 1:
                  this.flds.put(workkey, value_long);
                  break;
               case 2:
                  this.flds.put(workkey, value_char);
                  break;
               case 3:
                  this.flds.put(workkey, value_float);
                  break;
               case 4:
                  this.flds.put(workkey, value_double);
                  break;
               case 5:
                  this.flds.put(workkey, value_string);
                  break;
               case 6:
                  this.flds.put(workkey, value_carray);
            }

         } else {
            this.len -= add_length;
            --this.nfields;
            throw new Ferror(3);
         }
      }
   }

   public synchronized Object Fget(FmlKey key) throws Ferror {
      Short value_short = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      if (key == null) {
         throw new Ferror(13);
      } else {
         int fldid = key.get_fldid();
         int occurance = key.get_occurance();
         if (occurance < 0) {
            throw new Ferror(13);
         } else if (this.Fldno(fldid) == 0) {
            throw new Ferror(5);
         } else {
            int fldtype = this.Fldtype(fldid);
            switch (fldtype) {
               case 0:
                  if ((value_short = (Short)this.flds.get(key)) == null) {
                     throw new Ferror(4);
                  }

                  return new Short(value_short);
               case 1:
                  if ((value_long = (Long)this.flds.get(key)) == null) {
                     throw new Ferror(4);
                  }

                  return new Integer(value_long.intValue());
               case 2:
                  if ((value_char = (Character)this.flds.get(key)) == null) {
                     throw new Ferror(4);
                  }

                  return new Character(value_char);
               case 3:
                  if ((value_float = (Float)this.flds.get(key)) == null) {
                     throw new Ferror(4);
                  }

                  return new Float(value_float);
               case 4:
                  if ((value_double = (Double)this.flds.get(key)) == null) {
                     throw new Ferror(4);
                  }

                  return new Double(value_double);
               case 5:
                  if ((value_string = (String)this.flds.get(key)) == null) {
                     throw new Ferror(4);
                  }

                  return new String(value_string);
               case 6:
                  if (!this.flds.containsKey(key)) {
                     throw new Ferror(4);
                  } else {
                     byte[] value_carray;
                     if ((value_carray = (byte[])((byte[])this.flds.get(key))) == null) {
                        return null;
                     }

                     byte[] output = new byte[value_carray.length];

                     for(int lcv = 0; lcv < value_carray.length; ++lcv) {
                        output[lcv] = value_carray[lcv];
                     }

                     return output;
                  }
               default:
                  throw new Ferror(5);
            }
         }
      }
   }

   public synchronized Object Fget(FmlKey key, boolean getLong) throws Ferror {
      Short value_short = null;
      Long value_long = null;
      if (!getLong) {
         return this.Fget(key);
      } else if (key == null) {
         throw new Ferror(13, "Input key is null");
      } else {
         int fldid = key.get_fldid();
         int occurance = key.get_occurance();
         if (occurance < 0) {
            throw new Ferror(13);
         } else if (this.Fldno(fldid) == 0) {
            throw new Ferror(5);
         } else {
            int fldtype = this.Fldtype(fldid);
            if (fldtype != 1) {
               return this.Fget(key);
            } else if ((value_long = (Long)this.flds.get(key)) == null) {
               throw new Ferror(4);
            } else {
               return new Long(value_long);
            }
         }
      }
   }

   public Object Fget(int fldid, int occurance) throws Ferror {
      return this.Fget(new FmlKey(fldid, occurance));
   }

   public Object Fget(int fldid, int occurance, boolean getLong) throws Ferror {
      return this.Fget(new FmlKey(fldid, occurance), getLong);
   }

   public Iterator Fiterator() {
      return this.flds.entrySet().iterator();
   }

   public int Foccur(int fldid) {
      Integer occ = (Integer)this.fldid_occs.get(new Integer(fldid));
      return occ == null ? 0 : occ + 1;
   }

   public synchronized void Fdel(FmlKey key) throws Ferror {
      if (key == null) {
         throw new Ferror(13);
      } else {
         int fldid = key.get_fldid();
         int occurance = key.get_occurance();
         if (occurance < 0) {
            throw new Ferror(13);
         } else if (this.Fldno(fldid) == 0) {
            throw new Ferror(5);
         } else {
            int fldtype = this.Fldtype(fldid);
            Integer fldid_key = new Integer(fldid);
            Integer occ = (Integer)this.fldid_occs.get(fldid_key);
            if (occ == null) {
               throw new Ferror(13, "Bad key occurance for fldid: " + fldid);
            } else {
               int int_occ = occ;
               if (int_occ < occurance) {
                  throw new Ferror(4);
               } else {
                  Object removed = this.flds.remove(key);
                  FmlKey newkey = new FmlKey(fldid, -1);
                  if (occurance < int_occ) {
                     int lcv;
                     for(lcv = occurance + 1; lcv <= int_occ; ++lcv) {
                        newkey.set_occurance(lcv);
                        Object mycopy = this.Fget(newkey);
                        newkey.set_occurance(lcv - 1);
                        this.Fchg(newkey, mycopy);
                     }

                     newkey.set_occurance(lcv - 1);
                     this.flds.remove(newkey);
                  }

                  --int_occ;
                  if (int_occ >= 0) {
                     this.fldid_occs.put(fldid_key, new Integer(int_occ));
                  } else {
                     this.fldid_occs.remove(fldid_key);
                  }

                  int sub_length;
                  switch (fldtype) {
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
                        sub_length = 12 + this.roundup4(value_string.length() + 1);
                        break;
                     case 6:
                        if (removed == null) {
                           sub_length = 12;
                        } else {
                           byte[] value_carray = (byte[])((byte[])removed);
                           sub_length = 12 + this.roundup4(value_carray.length);
                        }
                        break;
                     default:
                        throw new Ferror(5);
                  }

                  this.len -= sub_length;
                  --this.nfields;
               }
            }
         }
      }
   }

   public void Fdel(int fldid, int occurance) throws Ferror {
      this.Fdel(new FmlKey(fldid, occurance));
   }

   public synchronized String Fname(int fldid) throws Ferror {
      String ret;
      if (this.fldtbls != null) {
         for(int lcv = 0; lcv < this.fldtbls.length; ++lcv) {
            if (this.fldtbls[lcv] != null && (ret = this.fldtbls[lcv].Fldid_to_name(fldid)) != null) {
               return ret;
            }
         }
      }

      return (ret = this.usys.Fldid_to_name(fldid)) != null ? ret : this.tmq.Fldid_to_name(fldid);
   }

   public synchronized int Fldid(String name) throws Ferror {
      int fldid;
      if (this.fldtbls != null) {
         for(int lcv = 0; lcv < this.fldtbls.length; ++lcv) {
            if (this.fldtbls[lcv] != null && (fldid = this.fldtbls[lcv].name_to_Fldid(name)) != -1) {
               return fldid;
            }
         }
      }

      if ((fldid = this.usys.name_to_Fldid(name)) != -1) {
         return fldid;
      } else if ((fldid = this.tmq.name_to_Fldid(name)) != -1) {
         return fldid;
      } else {
         throw new Ferror(8, "Unknown fldname: " + name);
      }
   }

   public int Fused() {
      return this.len;
   }

   public synchronized void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      Short value_short = null;
      Integer value_integer = null;
      Long value_long = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      int output_data_size = false;
      boolean indeedUse64BitsLong = false;
      Boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
      encoder.writeInt(this.magic);
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
      if (this.numLongFields > 0 && !indeedUse64BitsLong) {
         actual_len = this.len - 4 * this.numLongFields;
      }

      encoder.writeInt(actual_len);
      encoder.writeInt(this.maxlen);
      encoder.writeInt(this.nfields);
      encoder.writeInt(this.nie);
      encoder.writeInt(this.indxintvl);
      Iterator fmlentries = this.flds.entrySet().iterator();

      while(true) {
         while(true) {
            label61:
            while(fmlentries.hasNext()) {
               Map.Entry element = (Map.Entry)fmlentries.next();
               FmlKey fmlkey = (FmlKey)element.getKey();
               int fldid = fmlkey.get_fldid();
               int fldtype = this.Fldtype(fldid);
               encoder.writeInt(fldid);
               int vallen;
               int pad;
               int lcv;
               switch (fldtype) {
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
                     pad = this.roundup4(vallen) - vallen;
                     encoder.writeInt(value_stringBytes.length + 1 + 2 + 2);
                     encoder.writeInt(value_stringBytes.length + 1);
                     encoder.write(value_stringBytes);
                     lcv = 0;

                     while(true) {
                        if (lcv > pad) {
                           continue label61;
                        }

                        encoder.writeByte(0);
                        ++lcv;
                     }
                  case 6:
                     byte[] value_carray = (byte[])((byte[])element.getValue());
                     if (value_carray == null) {
                        encoder.writeInt(0);
                        encoder.writeInt(0);
                     } else {
                        vallen = value_carray.length;
                        pad = this.roundup4(vallen) - vallen;
                        encoder.writeInt(vallen + 2 + 2);
                        encoder.writeInt(vallen);
                        encoder.write(value_carray);

                        for(lcv = 0; lcv < pad; ++lcv) {
                           encoder.writeByte(0);
                        }
                     }
               }
            }

            return;
         }
      }
   }

   public synchronized void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException, IOException {
      this._tmpostrecv65(decoder);
   }

   public void _tmpostrecv65(DataInputStream decoder) throws IOException, TPException {
      int occurance = 0;
      int current_fldid = -1;
      Short value_short = null;
      Long value_long = null;
      Integer value_int = null;
      Character value_char = null;
      Float value_float = null;
      Double value_double = null;
      String value_string = null;
      byte[] value_carray = null;
      boolean indeedUse64BitsLong = false;
      Boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
      this.magic = decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      int mynfields = decoder.readInt();
      decoder.readInt();
      decoder.readInt();

      for(int lcv = 0; lcv < mynfields; ++lcv) {
         int fldid = decoder.readInt();
         if (this.Fldno(fldid) == 0) {
            throw new TPException(4, "Invalid fieldid encountered: " + fldid);
         }

         int fldtype = this.Fldtype(fldid);
         int lcv2;
         FmlKey fmlkey;
         int fldlen;
         int pad;
         switch (fldtype) {
            case 0:
               if (current_fldid != fldid) {
                  occurance = 0;
                  current_fldid = fldid;
               }

               fmlkey = new FmlKey(fldid, occurance++);
               value_short = new Short((short)decoder.readInt());

               try {
                  this.Fchg(fmlkey, value_short);
                  break;
               } catch (Ferror var32) {
                  throw new TPException(4, "Ferror encountered while adding a SHORT field " + var32);
               }
            case 1:
               if (current_fldid != fldid) {
                  occurance = 0;
                  current_fldid = fldid;
               }

               fmlkey = new FmlKey(fldid, occurance++);
               if (this.callFromReadWriteObject) {
                  if (enable64BitsLong) {
                     indeedUse64BitsLong = true;
                  } else {
                     indeedUse64BitsLong = false;
                  }
               } else {
                  if (this.use64BitsLong != enable64BitsLong) {
                     throw new TPException(12, "Client or WTC doesn't support 64-bit FLD_LONG");
                  }

                  indeedUse64BitsLong = this.use64BitsLong;
               }

               if (indeedUse64BitsLong) {
                  value_long = new Long(decoder.readLong());
               } else {
                  value_long = new Long((long)decoder.readInt());
               }

               try {
                  this.Fchg(fmlkey, value_long);
                  break;
               } catch (Ferror var31) {
                  throw new TPException(4, "Ferror encountered while adding a LONG field " + var31);
               }
            case 2:
               if (current_fldid != fldid) {
                  occurance = 0;
                  current_fldid = fldid;
               }

               fmlkey = new FmlKey(fldid, occurance++);
               value_char = new Character((char)decoder.readInt());

               try {
                  this.Fchg(fmlkey, value_char);
                  break;
               } catch (Ferror var30) {
                  throw new TPException(4, "Ferror encountered while adding a CHAR field " + var30);
               }
            case 3:
               if (current_fldid != fldid) {
                  occurance = 0;
                  current_fldid = fldid;
               }

               fmlkey = new FmlKey(fldid, occurance++);
               value_float = new Float(decoder.readFloat());

               try {
                  this.Fchg(fmlkey, value_float);
                  break;
               } catch (Ferror var29) {
                  throw new TPException(4, "Ferror encountered while adding a FLOAT field " + var29);
               }
            case 4:
               if (current_fldid != fldid) {
                  occurance = 0;
                  current_fldid = fldid;
               }

               fmlkey = new FmlKey(fldid, occurance++);
               value_double = new Double(decoder.readDouble());

               try {
                  this.Fchg(fmlkey, value_double);
                  break;
               } catch (Ferror var28) {
                  throw new TPException(4, "Ferror encountered while adding a DOUBLE field " + var28);
               }
            case 5:
               if (current_fldid != fldid) {
                  occurance = 0;
                  current_fldid = fldid;
               }

               fmlkey = new FmlKey(fldid, occurance++);
               fldlen = decoder.readInt() - 2 - 2;
               pad = this.roundup4(fldlen) - fldlen;
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

               try {
                  this.Fchg(fmlkey, value_string);
                  break;
               } catch (Ferror var27) {
                  throw new TPException(4, "Ferror encountered while adding a STRING field " + var27);
               }
            case 6:
               if (current_fldid != fldid) {
                  occurance = 0;
                  current_fldid = fldid;
               }

               fmlkey = new FmlKey(fldid, occurance++);
               fldlen = decoder.readInt() - 2 - 2;
               pad = this.roundup4(fldlen) - fldlen;
               decoder.readInt();
               byte[] value_carray;
               if (fldlen <= 0) {
                  value_carray = null;
               } else {
                  value_carray = new byte[fldlen];

                  for(lcv2 = 0; lcv2 < fldlen; ++lcv2) {
                     value_carray[lcv2] = decoder.readByte();
                  }

                  decoder.skipBytes(pad);
               }

               try {
                  this.Fchg(fmlkey, value_carray);
                  break;
               } catch (Ferror var26) {
                  throw new TPException(4, "Ferror encountered while adding a CARRAY field " + var26);
               }
            default:
               throw new TPException(4, "Encountered unknown field type " + fldtype);
         }
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
         throw new IOException("FML presend threw TPException" + var6);
      }

      out.write(fillin, 0, send_size);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.flds = new TreeMap();
      this.fldid_occs = new HashMap();
      this.usys = new Usysflds();
      this.tmq = new tmqflds();

      try {
         DataInputStream decoder = new DataInputStream(in);
         this.callFromReadWriteObject = true;
         this._tmpostrecv(decoder, in.available());
         this.callFromReadWriteObject = false;
      } catch (TPException var3) {
         throw new IOException(var3.toString());
      }
   }

   public void setUse64BitsLong(boolean val) {
      this.use64BitsLong = val;
   }

   public boolean getUse64BitsLong() {
      return this.use64BitsLong;
   }
}
