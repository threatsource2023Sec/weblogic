package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.IstringHelper;

public abstract class WNameComponentHelper {
   private static String _id = "IDL:weblogic/corba/cos/naming/NamingContextAny/WNameComponent:1.0";
   private static TypeCode __typeCode = null;
   private static boolean __active = false;

   public static void insert(Any a, WNameComponent that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static WNameComponent extract(Any a) {
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
               _tcOf_members0 = ORB.init().create_wstring_tc(0);
               _members0[0] = new StructMember("id", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = ORB.init().create_string_tc(0);
               _tcOf_members0 = ORB.init().create_alias_tc(IstringHelper.id(), "Istring", _tcOf_members0);
               _members0[1] = new StructMember("kind", _tcOf_members0, (IDLType)null);
               __typeCode = ORB.init().create_struct_tc(id(), "WNameComponent", _members0);
               __active = false;
            }
         }
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static WNameComponent read(InputStream istream) {
      WNameComponent value = new WNameComponent();
      value.id = istream.read_wstring();
      value.kind = istream.read_string();
      return value;
   }

   public static void write(OutputStream ostream, WNameComponent value) {
      ostream.write_wstring(value.id);
      ostream.write_string(value.kind);
   }
}
