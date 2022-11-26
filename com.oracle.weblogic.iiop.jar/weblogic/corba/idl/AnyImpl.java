package weblogic.corba.idl;

import java.io.IOException;
import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import weblogic.corba.utils.ClassInfo;
import weblogic.corba.utils.CorbaUtils;
import weblogic.iiop.IIOPInputStream;
import weblogic.iiop.IIOPOutputStream;

public class AnyImpl extends Any {
   private static final long serialVersionUID = -4455920080878260180L;
   private static final boolean DEBUG = false;
   private Object value;
   private long longValue = 0L;
   private double doubleValue = 0.0;
   private TypeCode type = TypeCodeImpl.get_primitive_tc(0);
   private boolean initialized = false;

   public AnyImpl() {
      this.initialized = true;
   }

   public void type(TypeCode type) {
      this.type = type;
      this.reset();
   }

   public TypeCode type() {
      return this.type;
   }

   public boolean equal(Any any) {
      if (any == this) {
         return true;
      } else if (any != null && any instanceof AnyImpl) {
         AnyImpl aimpl = (AnyImpl)any;
         return this.type.equal(any.type()) && (this.value == null && aimpl.value == null || this.value != null && this.value.equals(aimpl.value)) && this.longValue == aimpl.longValue && this.doubleValue == aimpl.doubleValue;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int retVal = this.type == null ? 0 : this.type.hashCode();
      if (this.value != null) {
         retVal ^= this.value.hashCode();
      }

      retVal ^= (new Long(this.longValue)).hashCode();
      retVal ^= (new Double(this.doubleValue)).hashCode();
      return retVal;
   }

   public boolean equals(Object other) {
      return other instanceof Any ? this.equal((Any)other) : false;
   }

   public void read_value(InputStream is, TypeCode type) throws MARSHAL {
      this.type(type);
      this.read_value_internal(is, type);
   }

   private void read_value_internal(InputStream is, TypeCode type) throws MARSHAL {
      switch (type.kind().value()) {
         case 0:
         case 1:
            break;
         case 2:
            this.insert_short(is.read_short());
            break;
         case 3:
            this.insert_long(is.read_long());
            break;
         case 4:
            this.insert_ushort(is.read_ushort());
            break;
         case 5:
            this.insert_ulong(is.read_ulong());
            break;
         case 6:
            this.insert_float(is.read_float());
            break;
         case 7:
            this.insert_double(is.read_double());
            break;
         case 8:
            this.insert_boolean(is.read_boolean());
            break;
         case 9:
            this.insert_char(is.read_char());
            break;
         case 10:
            this.insert_octet(is.read_octet());
            break;
         case 11:
            this.value = is.read_any();
            break;
         case 12:
            this.value = is.read_TypeCode();
            break;
         case 13:
            this.value = is.read_Principal();
            break;
         case 14:
            this.value = is.read_Object();
            break;
         case 15:
         case 16:
         case 17:
         case 19:
         case 20:
         case 22:
            ClassInfo cinfo = ClassInfo.findClassInfo(TypeCodeImpl.getRepositoryId(type));
            if (cinfo != null && cinfo.forClass() != null) {
               this.value = IIOPInputStream.read_IDLEntity(is, cinfo.forClass());
            } else {
               IIOPOutputStream out = new IIOPOutputStream();
               copyStreamable(out, is, type);
               this.value = out.createExactInputStream();
            }
            break;
         case 18:
            this.value = is.read_string();
            break;
         case 21:
            try {
               this.read_value_internal(is, type.content_type());
               break;
            } catch (BadKind var5) {
               throw new MARSHAL(var5.toString());
            }
         case 23:
            this.insert_longlong(is.read_longlong());
            break;
         case 24:
            this.insert_ulonglong(is.read_ulonglong());
            break;
         case 25:
            this.insert_double(is.read_double());
            break;
         case 26:
            this.insert_wchar(is.read_wchar());
            break;
         case 27:
            this.value = is.read_wstring();
            break;
         case 28:
         case 31:
         default:
            throw new MARSHAL("Unsupported any type: " + type);
         case 29:
         case 30:
            this.value = ((org.omg.CORBA_2_3.portable.InputStream)is).read_value();
            break;
         case 32:
            this.value = ((org.omg.CORBA_2_3.portable.InputStream)is).read_abstract_interface();
      }

      this.initialized = true;
   }

   private static void copyStreamable(OutputStream os, InputStream is, TypeCode tc) {
      try {
         int alen;
         switch (tc.kind().value()) {
            case 0:
            case 1:
               break;
            case 2:
               os.write_short(is.read_short());
               break;
            case 3:
               os.write_long(is.read_long());
               break;
            case 4:
               os.write_ushort(is.read_ushort());
               break;
            case 5:
               os.write_ulong(is.read_ulong());
               break;
            case 6:
               os.write_float(is.read_float());
               break;
            case 7:
               os.write_double(is.read_double());
               break;
            case 8:
               os.write_boolean(is.read_boolean());
               break;
            case 9:
               os.write_char(is.read_char());
               break;
            case 10:
               os.write_octet(is.read_octet());
               break;
            case 11:
               os.write_any(is.read_any());
               break;
            case 12:
               os.write_TypeCode(is.read_TypeCode());
               break;
            case 13:
               os.write_Principal(is.read_Principal());
               break;
            case 14:
               os.write_Object(is.read_Object());
               break;
            case 16:
               Any discriminator = new AnyImpl();
               discriminator.read_value(is, tc.discriminator_type());
               discriminator.write_value(os);
               copyStreamable(os, is, tc.member_type(getUnionIndex(tc, discriminator)));
               break;
            case 17:
               os.write_long(is.read_long());
               break;
            case 18:
               os.write_string(is.read_string());
               break;
            case 19:
               int len = is.read_ulong();
               os.write_ulong(len);

               for(alen = 0; alen < len; ++alen) {
                  copyStreamable(os, is, tc.content_type());
               }

               return;
            case 20:
               alen = tc.length();

               for(int i = 0; i < alen; ++i) {
                  copyStreamable(os, is, tc.content_type());
               }

               return;
            case 21:
            case 30:
               copyStreamable(os, is, tc.content_type());
               break;
            case 22:
               os.write_string(is.read_string());
            case 15:
               for(int i = 0; i < tc.member_count(); ++i) {
                  copyStreamable(os, is, tc.member_type(i));
               }

               return;
            case 23:
               os.write_longlong(is.read_longlong());
               break;
            case 24:
               os.write_ulonglong(is.read_ulonglong());
               break;
            case 25:
               os.write_double(is.read_double());
               break;
            case 26:
               os.write_wchar(is.read_wchar());
               break;
            case 27:
               os.write_wstring(is.read_wstring());
               break;
            case 28:
            case 31:
            default:
               throw new MARSHAL("Unsupported any type: " + tc);
            case 29:
               ((org.omg.CORBA_2_3.portable.OutputStream)os).write_value(((org.omg.CORBA_2_3.portable.InputStream)is).read_value());
               break;
            case 32:
               ((org.omg.CORBA_2_3.portable.OutputStream)os).write_abstract_interface(((org.omg.CORBA_2_3.portable.InputStream)is).read_abstract_interface());
         }

      } catch (BadKind var7) {
         throw new MARSHAL("Corrupt any type: " + tc);
      } catch (Bounds var8) {
         throw new MARSHAL("Corrupt any type: " + tc);
      }
   }

   private static int getUnionIndex(TypeCode tc, Any value) throws BadKind, Bounds {
      for(int i = 0; i < tc.member_count(); ++i) {
         if (tc.member_label(i).equal(value)) {
            return i;
         }
      }

      return tc.default_index();
   }

   public void write_value(OutputStream os) {
      this.write_value(os, this.type);
   }

   public void write_value(OutputStream os, TypeCode type) {
      if (type == null) {
         type = this.type;
         os.write_TypeCode(type);
      }

      int tc = type.kind().value();
      switch (tc) {
         case 0:
         case 1:
            break;
         case 2:
            os.write_short(this.extract_short());
            break;
         case 3:
            os.write_long(this.extract_long());
            break;
         case 4:
            os.write_ushort(this.extract_ushort());
            break;
         case 5:
            os.write_ulong(this.extract_ulong());
            break;
         case 6:
            os.write_float(this.extract_float());
            break;
         case 7:
            os.write_double(this.extract_double());
            break;
         case 8:
            os.write_boolean(this.extract_boolean());
            break;
         case 9:
            os.write_char(this.extract_char());
            break;
         case 10:
            os.write_octet(this.extract_octet());
            break;
         case 11:
            os.write_any((Any)this.value);
            break;
         case 12:
            os.write_TypeCode((TypeCode)this.value);
            break;
         case 13:
            os.write_Principal((Principal)this.value);
            break;
         case 14:
            os.write_Object((org.omg.CORBA.Object)this.value);
            break;
         case 15:
         case 16:
         case 17:
         case 19:
         case 20:
         case 22:
            if (this.value instanceof IDLEntity) {
               Class c = CorbaUtils.getClassFromID(TypeCodeImpl.getRepositoryId(type));
               IIOPOutputStream.write_IDLEntity(os, (IDLEntity)this.value, c);
            } else {
               if (!(this.value instanceof InputStream)) {
                  throw new MARSHAL("Cannot marshal: " + this.value + " of type " + tc);
               }

               InputStream in = (InputStream)this.value;

               try {
                  in.reset();
               } catch (IOException var6) {
               }

               copyStreamable(os, in, type);
            }
            break;
         case 18:
            os.write_string((String)this.value);
            break;
         case 21:
            try {
               this.write_value(os, type.content_type());
               break;
            } catch (BadKind var7) {
               throw new MARSHAL(var7.toString());
            }
         case 23:
            os.write_longlong(this.extract_longlong());
            break;
         case 24:
            os.write_ulonglong(this.extract_ulonglong());
            break;
         case 25:
            os.write_double(this.extract_double());
            break;
         case 26:
            os.write_wchar(this.extract_wchar());
            break;
         case 27:
            os.write_wstring((String)this.value);
            break;
         case 28:
         case 31:
         default:
            throw new MARSHAL("Unsupported any type: " + tc);
         case 29:
         case 30:
            ((org.omg.CORBA_2_3.portable.OutputStream)os).write_value((Serializable)this.value);
            break;
         case 32:
            ((org.omg.CORBA_2_3.portable.OutputStream)os).write_abstract_interface(this.value);
      }

   }

   public OutputStream create_output_stream() {
      return new IIOPOutputStream();
   }

   public InputStream create_input_stream() {
      IIOPOutputStream out = new IIOPOutputStream();
      this.write_value(out);
      IIOPInputStream in = out.createExactInputStream();
      return in;
   }

   public Serializable extract_Value() throws BAD_OPERATION {
      if (this.type != null && this.value != null && this.initialized && (this.type.kind().value() == 29 || this.type.kind().value() == 30)) {
         return (Serializable)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_Value(Serializable obj) {
      this.type = TypeCodeImpl.get_primitive_tc(29);
      this.value = obj;
      this.initialized = true;
   }

   public void insert_Value(Serializable obj, TypeCode type) {
      this.type = type;
      this.value = obj;
      this.initialized = true;
   }

   public void insert_Streamable(Streamable streamable) {
      this.type = streamable._type();
      IIOPOutputStream out = new IIOPOutputStream();
      streamable._write(out);
      this.value = out.createExactInputStream();
      this.initialized = true;
   }

   public short extract_short() throws BAD_OPERATION {
      if (this.type.kind().value() == 2 && this.initialized) {
         return (short)((int)(65535L & this.longValue));
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_short(short s) {
      this.type = TypeCodeImpl.get_primitive_tc(2);
      this.longValue = (long)s;
      this.initialized = true;
   }

   public int extract_long() throws BAD_OPERATION {
      if (this.type.kind().value() == 3 && this.initialized) {
         return (int)(-1L & this.longValue);
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_long(int l) {
      this.type = TypeCodeImpl.get_primitive_tc(3);
      this.longValue = (long)l;
      this.initialized = true;
   }

   public long extract_longlong() throws BAD_OPERATION {
      if (this.type.kind().value() == 23 && this.initialized) {
         return this.longValue;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_longlong(long l) {
      this.type = TypeCodeImpl.get_primitive_tc(23);
      this.longValue = l;
      this.initialized = true;
   }

   public short extract_ushort() throws BAD_OPERATION {
      if (this.type.kind().value() == 4 && this.initialized) {
         return (short)((int)(65535L & this.longValue));
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_ushort(short s) {
      this.type = TypeCodeImpl.get_primitive_tc(4);
      this.longValue = (long)s;
      this.initialized = true;
   }

   public int extract_ulong() throws BAD_OPERATION {
      if (this.type.kind().value() == 5 && this.initialized) {
         return (int)(-1L & this.longValue);
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_ulong(int l) {
      this.type = TypeCodeImpl.get_primitive_tc(5);
      this.longValue = (long)l;
      this.initialized = true;
   }

   public long extract_ulonglong() throws BAD_OPERATION {
      if (this.type.kind().value() == 24 && this.initialized) {
         return this.longValue;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_ulonglong(long l) {
      this.type = TypeCodeImpl.get_primitive_tc(24);
      this.longValue = l;
      this.initialized = true;
   }

   public float extract_float() throws BAD_OPERATION {
      if (this.type.kind().value() == 6 && this.initialized) {
         return (float)this.doubleValue;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_float(float f) {
      this.type = TypeCodeImpl.get_primitive_tc(6);
      this.doubleValue = (double)f;
      this.initialized = true;
   }

   public double extract_double() throws BAD_OPERATION {
      if (this.type.kind().value() == 7 && this.initialized) {
         return this.doubleValue;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_double(double d) {
      this.type = TypeCodeImpl.get_primitive_tc(7);
      this.doubleValue = d;
      this.initialized = true;
   }

   public boolean extract_boolean() throws BAD_OPERATION {
      if (this.type.kind().value() == 8 && this.initialized) {
         return this.longValue == 1L;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_boolean(boolean b) {
      this.type = TypeCodeImpl.get_primitive_tc(8);
      this.longValue = (long)(b ? 1 : 0);
      this.initialized = true;
   }

   public char extract_char() throws BAD_OPERATION {
      if (this.type.kind().value() == 9 && this.initialized) {
         return (char)((int)this.longValue);
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_char(char c) throws DATA_CONVERSION {
      this.type = TypeCodeImpl.get_primitive_tc(9);
      this.longValue = (long)c;
      this.initialized = true;
   }

   public byte extract_octet() throws BAD_OPERATION {
      if (this.type.kind().value() == 10 && this.initialized) {
         return (byte)((int)(255L & this.longValue));
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_octet(byte o) {
      this.type = TypeCodeImpl.get_primitive_tc(10);
      this.longValue = (long)o;
      this.initialized = true;
   }

   public char extract_wchar() throws BAD_OPERATION {
      if (this.type.kind().value() == 26 && this.initialized) {
         return (char)((int)this.longValue);
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_wchar(char c) {
      this.type = TypeCodeImpl.get_primitive_tc(26);
      this.longValue = (long)c;
      this.initialized = true;
   }

   public Any extract_any() throws BAD_OPERATION {
      if (this.type != null && this.value != null && this.initialized && this.type.kind().value() == 11) {
         return (Any)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_any(Any a) {
      this.type = TypeCodeImpl.get_primitive_tc(11);
      this.value = a;
      this.initialized = true;
   }

   public String extract_string() throws BAD_OPERATION {
      if (this.type.kind().value() == 18 && this.initialized) {
         return (String)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_string(String s) throws DATA_CONVERSION, MARSHAL {
      this.type = TypeCodeImpl.get_primitive_tc(18);
      this.value = s;
      this.initialized = true;
   }

   public String extract_wstring() throws BAD_OPERATION {
      if (this.type.kind().value() == 27 && this.initialized) {
         return (String)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_wstring(String s) throws MARSHAL {
      this.type = TypeCodeImpl.get_primitive_tc(27);
      this.value = s;
      this.initialized = true;
   }

   public org.omg.CORBA.Object extract_Object() throws BAD_OPERATION {
      if (this.type != null && this.value != null && this.initialized && this.type.kind().value() == 14) {
         return (org.omg.CORBA.Object)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_Object(org.omg.CORBA.Object o, TypeCode t) throws BAD_OPERATION {
      this.type = t;
      this.value = o;
      this.initialized = true;
   }

   public void insert_Object(org.omg.CORBA.Object obj) {
      this.type = TypeCodeImpl.get_primitive_tc(14);
      this.value = obj;
      this.initialized = true;
   }

   public TypeCode extract_TypeCode() throws BAD_OPERATION {
      if (this.type != null && this.value != null && this.initialized && this.type.kind().value() == 12) {
         return (TypeCode)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_TypeCode(TypeCode t) {
      this.type = TypeCodeImpl.get_primitive_tc(12);
      this.value = t;
      this.initialized = true;
   }

   public Principal extract_Principal() throws BAD_OPERATION {
      if (this.type != null && this.value != null && this.initialized && this.type.kind().value() == 13) {
         return (Principal)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   public void insert_Principal(Principal p) {
      this.type = TypeCodeImpl.get_primitive_tc(13);
      this.value = p;
      this.initialized = true;
   }

   public void insert_IDLEntity(IDLEntity entity, TypeCode type) {
      this.type = type;
      this.value = entity;
      this.initialized = true;
   }

   public IDLEntity extract_IDLEntity() {
      if (this.type != null && this.value != null && this.initialized) {
         return (IDLEntity)this.value;
      } else {
         throw new BAD_OPERATION();
      }
   }

   private void reset() {
      this.value = null;
      this.longValue = 0L;
      this.doubleValue = 0.0;
      this.initialized = false;
   }

   public static void write(OutputStream out, Any any, TypeCode type) {
   }

   private static void p(String s) {
      System.out.println("<AnyImpl> " + s);
   }

   public String toString() {
      return this.type().toString() + ": " + this.value;
   }
}
