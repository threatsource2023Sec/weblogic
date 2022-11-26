package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.NamingContextHelper;

public abstract class CannotProceedHelper {
   private static String _id = "IDL:weblogic/corba/cos/naming/NamingContextAny/CannotProceed:1.0";
   private static TypeCode __typeCode = null;
   private static boolean __active = false;

   public static void insert(Any a, CannotProceed that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static CannotProceed extract(Any a) {
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
               StructMember[] _members0 = new StructMember[2];
               TypeCode _tcOf_members0 = null;
               _tcOf_members0 = NamingContextHelper.type();
               _members0[0] = new StructMember("cxt", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = WNameComponentHelper.type();
               _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
               _tcOf_members0 = ORB.init().create_alias_tc(WNameHelper.id(), "WName", _tcOf_members0);
               _members0[1] = new StructMember("rest_of_name", _tcOf_members0, (IDLType)null);
               __typeCode = ORB.init().create_exception_tc(id(), "CannotProceed", _members0);
               __active = false;
            }
         }
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static CannotProceed read(InputStream istream) {
      CannotProceed value = new CannotProceed();
      istream.read_string();
      value.cxt = NamingContextHelper.read(istream);
      value.rest_of_name = WNameHelper.read(istream);
      return value;
   }

   public static void write(OutputStream ostream, CannotProceed value) {
      ostream.write_string(id());
      NamingContextHelper.write(ostream, value.cxt);
      WNameHelper.write(ostream, value.rest_of_name);
   }
}
