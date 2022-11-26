package org.omg.CosTransactions;

import org.omg.CORBA.Any;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class TransIdentityHelper {
   private static String _id = "IDL:omg.org/CosTransactions/TransIdentity:1.0";
   private static TypeCode __typeCode = null;
   private static boolean __active = false;

   public static void insert(Any a, TransIdentity that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static TransIdentity extract(Any a) {
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
               StructMember[] _members0 = new StructMember[3];
               TypeCode _tcOf_members0 = null;
               _tcOf_members0 = CoordinatorHelper.type();
               _members0[0] = new StructMember("coord", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = TerminatorHelper.type();
               _members0[1] = new StructMember("term", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = otid_tHelper.type();
               _members0[2] = new StructMember("otid", _tcOf_members0, (IDLType)null);
               __typeCode = ORB.init().create_struct_tc(id(), "TransIdentity", _members0);
               __active = false;
            }
         }
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static TransIdentity read(InputStream istream) {
      TransIdentity value = new TransIdentity();
      value.coord = CoordinatorHelper.read(istream);
      value.term = TerminatorHelper.read(istream);
      value.otid = otid_tHelper.read(istream);
      return value;
   }

   public static void write(OutputStream ostream, TransIdentity value) {
      CoordinatorHelper.write(ostream, value.coord);
      TerminatorHelper.write(ostream, value.term);
      otid_tHelper.write(ostream, value.otid);
   }
}
