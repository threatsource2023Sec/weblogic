package org.omg.CosTransactions;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class TransactionalObjectHelper {
   private static String _id = "IDL:omg.org/CosTransactions/TransactionalObject:1.0";
   private static TypeCode __typeCode = null;

   public static void insert(Any a, TransactionalObject that) {
      OutputStream out = a.create_output_stream();
      a.type(type());
      write(out, that);
      a.read_value(out.create_input_stream(), type());
   }

   public static TransactionalObject extract(Any a) {
      return read(a.create_input_stream());
   }

   public static synchronized TypeCode type() {
      if (__typeCode == null) {
         __typeCode = ORB.init().create_interface_tc(id(), "TransactionalObject");
      }

      return __typeCode;
   }

   public static String id() {
      return _id;
   }

   public static TransactionalObject read(InputStream istream) {
      return narrow(istream.read_Object(_TransactionalObjectStub.class));
   }

   public static void write(OutputStream ostream, TransactionalObject value) {
      ostream.write_Object(value);
   }

   public static TransactionalObject narrow(Object obj) {
      if (obj == null) {
         return null;
      } else if (obj instanceof TransactionalObject) {
         return (TransactionalObject)obj;
      } else if (!obj._is_a(id())) {
         throw new BAD_PARAM();
      } else {
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         _TransactionalObjectStub stub = new _TransactionalObjectStub();
         stub._set_delegate(delegate);
         return stub;
      }
   }

   public static TransactionalObject unchecked_narrow(Object obj) {
      if (obj == null) {
         return null;
      } else if (obj instanceof TransactionalObject) {
         return (TransactionalObject)obj;
      } else {
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         _TransactionalObjectStub stub = new _TransactionalObjectStub();
         stub._set_delegate(delegate);
         return stub;
      }
   }
}
