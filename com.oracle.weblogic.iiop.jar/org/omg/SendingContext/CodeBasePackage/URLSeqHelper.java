package org.omg.SendingContext.CodeBasePackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class URLSeqHelper {
   private static String _id = "IDL:omg.org/SendingContext/CodeBase/URLSeq:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, String[] that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static String[] extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = ORB.init().create_string_tc(0);
         __typeCode = ORB.init().create_alias_tc(URLHelper.id(), "URL", __typeCode);
         __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
         __typeCode = ORB.init().create_alias_tc(id(), "URLSeq", __typeCode);
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static String[] read(InputStream istream) {
      String[] value = null;
      int _len0 = istream.read_long();
      value = new String[_len0];

      for(int _o1 = 0; _o1 < value.length; ++_o1) {
         value[_o1] = URLHelper.read(istream);
      }

      return value;
   }

   public static void write(OutputStream ostream, String[] value) {
      ostream.write_long(value.length);

      for(int _i0 = 0; _i0 < value.length; ++_i0) {
         URLHelper.write(ostream, value[_i0]);
      }

   }
}
