package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class WStringNameHelper {
   private static String _id = "IDL:weblogic/corba/cos/naming/NamingContextAny/WStringName:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, String that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static String extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = ORB.init().create_wstring_tc(0);
         __typeCode = ORB.init().create_alias_tc(id(), "WStringName", __typeCode);
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static String read(InputStream istream) {
      String value = null;
      value = istream.read_wstring();
      return value;
   }

   public static void write(OutputStream ostream, String value) {
      ostream.write_wstring(value);
   }
}
