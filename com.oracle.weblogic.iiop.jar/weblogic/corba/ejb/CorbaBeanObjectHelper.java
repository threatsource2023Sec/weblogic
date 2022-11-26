package weblogic.corba.ejb;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class CorbaBeanObjectHelper {
   private static String _id = "IDL:weblogic/corba/ejb/CorbaBeanObject:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, CorbaBeanObject that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static CorbaBeanObject extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = ORB.init().create_interface_tc(id(), "CorbaBeanObject");
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static CorbaBeanObject read(InputStream istream) {
      return narrow(istream.read_Object(_CorbaBeanObjectStub.class));
   }

   public static void write(OutputStream ostream, CorbaBeanObject value) {
      ostream.write_Object((Object)value);
   }

   public static CorbaBeanObject narrow(Object obj) {
      if (obj == null) {
         return null;
      } else if (obj instanceof CorbaBeanObject) {
         return (CorbaBeanObject)obj;
      } else if (!obj._is_a(id())) {
         throw new BAD_PARAM();
      } else {
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         _CorbaBeanObjectStub stub = new _CorbaBeanObjectStub();
         stub._set_delegate(delegate);
         return stub;
      }
   }

   public static CorbaBeanObject unchecked_narrow(Object obj) {
      if (obj == null) {
         return null;
      } else if (obj instanceof CorbaBeanObject) {
         return (CorbaBeanObject)obj;
      } else {
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         _CorbaBeanObjectStub stub = new _CorbaBeanObjectStub();
         stub._set_delegate(delegate);
         return stub;
      }
   }
}
