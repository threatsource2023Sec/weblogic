package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class TypeNotSupportedHelper {
   private static String _id = "IDL:weblogic/corba/cos/naming/NamingContextAny/TypeNotSupported:1.0";
   private static TypeCode __typeCode = null;
   private static boolean __active = false;

   public static void insert(Any a, TypeNotSupported that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static TypeNotSupported extract(Any a) {
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
               StructMember[] _members0 = new StructMember[0];
               TypeCode _tcOf_members0 = null;
               __typeCode = ORB.init().create_exception_tc(id(), "TypeNotSupported", _members0);
               __active = false;
            }
         }
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static TypeNotSupported read(InputStream istream) {
      TypeNotSupported value = new TypeNotSupported();
      istream.read_string();
      return value;
   }

   public static void write(OutputStream ostream, TypeNotSupported value) {
      ostream.write_string(id());
   }
}
