package org.omg.CosTransactions;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class NonTxTargetPolicyValueHelper {
   private static String _id = "IDL:omg.org/CosTransactions/NonTxTargetPolicyValue:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, short that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static short extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = ORB.init().get_primitive_tc(TCKind.tk_ushort);
         __typeCode = ORB.init().create_alias_tc(id(), "NonTxTargetPolicyValue", __typeCode);
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static short read(InputStream istream) {
      short value = false;
      short value = istream.read_ushort();
      return value;
   }

   public static void write(OutputStream ostream, short value) {
      ostream.write_ushort(value);
   }
}
