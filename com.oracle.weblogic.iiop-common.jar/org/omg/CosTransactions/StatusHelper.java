package org.omg.CosTransactions;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class StatusHelper {
   private static String _id = "IDL:omg.org/CosTransactions/Status:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, Status that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static Status extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = ORB.init().create_enum_tc(id(), "Status", new String[]{"StatusActive", "StatusMarkedRollback", "StatusPrepared", "StatusCommitted", "StatusRolledBack", "StatusUnknown", "StatusNoTransaction", "StatusPreparing", "StatusCommitting", "StatusRollingBack"});
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static Status read(InputStream istream) {
      return Status.from_int(istream.read_long());
   }

   public static void write(OutputStream ostream, Status value) {
      ostream.write_long(value.value());
   }
}
