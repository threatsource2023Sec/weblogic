package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class WNameHelper {
   private static String _id = "IDL:weblogic/corba/cos/naming/NamingContextAny/WName:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, WNameComponent[] that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static WNameComponent[] extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = WNameComponentHelper.type();
         __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
         __typeCode = ORB.init().create_alias_tc(id(), "WName", __typeCode);
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static WNameComponent[] read(InputStream istream) {
      WNameComponent[] value = null;
      int _len0 = istream.read_long();
      value = new WNameComponent[_len0];

      for(int _o1 = 0; _o1 < value.length; ++_o1) {
         value[_o1] = WNameComponentHelper.read(istream);
      }

      return value;
   }

   public static void write(OutputStream ostream, WNameComponent[] value) {
      ostream.write_long(value.length);

      for(int _i0 = 0; _i0 < value.length; ++_i0) {
         WNameComponentHelper.write(ostream, value[_i0]);
      }

   }
}
