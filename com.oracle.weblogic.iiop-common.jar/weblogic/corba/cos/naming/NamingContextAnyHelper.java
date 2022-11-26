package weblogic.corba.cos.naming;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class NamingContextAnyHelper {
   private static String _id = "IDL:weblogic/corba/cos/naming/NamingContextAny:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, NamingContextAny that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static NamingContextAny extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = ORB.init().create_interface_tc(id(), "NamingContextAny");
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static NamingContextAny read(InputStream istream) {
      return narrow(istream.read_Object(_NamingContextAnyStub.class));
   }

   public static void write(OutputStream ostream, NamingContextAny value) {
      ostream.write_Object(value);
   }

   public static NamingContextAny narrow(Object obj) {
      if (obj == null) {
         return null;
      } else if (obj instanceof NamingContextAny) {
         return (NamingContextAny)obj;
      } else if (!obj._is_a(id())) {
         throw new BAD_PARAM();
      } else {
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         _NamingContextAnyStub stub = new _NamingContextAnyStub();
         stub._set_delegate(delegate);
         return stub;
      }
   }

   public static NamingContextAny unchecked_narrow(Object obj) {
      if (obj == null) {
         return null;
      } else if (obj instanceof NamingContextAny) {
         return (NamingContextAny)obj;
      } else {
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         _NamingContextAnyStub stub = new _NamingContextAnyStub();
         stub._set_delegate(delegate);
         return stub;
      }
   }
}
