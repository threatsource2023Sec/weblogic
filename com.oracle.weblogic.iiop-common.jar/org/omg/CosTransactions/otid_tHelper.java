package org.omg.CosTransactions;

import org.omg.CORBA.Any;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class otid_tHelper {
   private static String _id = "IDL:omg.org/CosTransactions/otid_t:1.0";
   private static TypeCode __typeCode = null;
   private static boolean __active = false;

   public static void insert(Any a, otid_t that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static otid_t extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         Class var0 = TypeCode.class;
         synchronized(TypeCode.class) {
            if (__typeCode == null) {
               if (__active) {
                  return ORB.init().create_recursive_tc(_id);
               }

               __active = true;
               StructMember[] _members0 = new StructMember[3];
               TypeCode _tcOf_members0 = null;
               _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_long);
               _members0[0] = new StructMember("formatID", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_long);
               _members0[1] = new StructMember("bqual_length", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_octet);
               _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
               _members0[2] = new StructMember("tid", _tcOf_members0, (IDLType)null);
               __typeCode = ORB.init().create_struct_tc(id(), "otid_t", _members0);
               __active = false;
            }
         }
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static otid_t read(InputStream istream) {
      otid_t value = new otid_t();
      value.formatID = istream.read_long();
      value.bqual_length = istream.read_long();
      int _len0 = istream.read_long();
      value.tid = new byte[_len0];
      istream.read_octet_array(value.tid, 0, _len0);
      return value;
   }

   public static void write(OutputStream ostream, otid_t value) {
      ostream.write_long(value.formatID);
      ostream.write_long(value.bqual_length);
      ostream.write_long(value.tid.length);
      ostream.write_octet_array(value.tid, 0, value.tid.length);
   }
}
