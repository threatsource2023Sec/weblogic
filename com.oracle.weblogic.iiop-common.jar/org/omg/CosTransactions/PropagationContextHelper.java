package org.omg.CosTransactions;

import org.omg.CORBA.Any;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class PropagationContextHelper {
   private static String _id = "IDL:omg.org/CosTransactions/PropagationContext:1.0";
   private static TypeCode __typeCode = null;
   private static boolean __active = false;

   public static void insert(Any a, PropagationContext that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static PropagationContext extract(Any a) {
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
               StructMember[] _members0 = new StructMember[4];
               TypeCode _tcOf_members0 = null;
               _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_ulong);
               _members0[0] = new StructMember("timeout", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = TransIdentityHelper.type();
               _members0[1] = new StructMember("current", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = TransIdentityHelper.type();
               _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
               _members0[2] = new StructMember("parents", _tcOf_members0, (IDLType)null);
               _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_any);
               _members0[3] = new StructMember("implementation_specific_data", _tcOf_members0, (IDLType)null);
               __typeCode = ORB.init().create_struct_tc(id(), "PropagationContext", _members0);
               __active = false;
            }
         }
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static PropagationContext read(InputStream istream) {
      PropagationContext value = new PropagationContext();
      value.timeout = istream.read_ulong();
      value.current = TransIdentityHelper.read(istream);
      int _len0 = istream.read_long();
      value.parents = new TransIdentity[_len0];

      for(int _o1 = 0; _o1 < value.parents.length; ++_o1) {
         value.parents[_o1] = TransIdentityHelper.read(istream);
      }

      value.implementation_specific_data = istream.read_any();
      return value;
   }

   public static void write(OutputStream ostream, PropagationContext value) {
      ostream.write_ulong(value.timeout);
      TransIdentityHelper.write(ostream, value.current);
      ostream.write_long(value.parents.length);

      for(int _i0 = 0; _i0 < value.parents.length; ++_i0) {
         TransIdentityHelper.write(ostream, value.parents[_i0]);
      }

      ostream.write_any(value.implementation_specific_data);
   }
}
