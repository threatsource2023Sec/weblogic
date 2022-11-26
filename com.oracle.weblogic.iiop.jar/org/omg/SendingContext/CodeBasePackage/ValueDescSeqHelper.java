package org.omg.SendingContext.CodeBasePackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueDescSeqHelper {
   private static String _id = "IDL:omg.org/SendingContext/CodeBase/ValueDescSeq:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, FullValueDescription[] that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static FullValueDescription[] extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = FullValueDescriptionHelper.type();
         __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
         __typeCode = ORB.init().create_alias_tc(id(), "ValueDescSeq", __typeCode);
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static FullValueDescription[] read(InputStream istream) {
      FullValueDescription[] value = null;
      int _len0 = istream.read_long();
      value = new FullValueDescription[_len0];

      for(int _o1 = 0; _o1 < value.length; ++_o1) {
         value[_o1] = FullValueDescriptionHelper.read(istream);
      }

      return value;
   }

   public static void write(OutputStream ostream, FullValueDescription[] value) {
      ostream.write_long(value.length);

      for(int _i0 = 0; _i0 < value.length; ++_i0) {
         FullValueDescriptionHelper.write(ostream, value[_i0]);
      }

   }
}
