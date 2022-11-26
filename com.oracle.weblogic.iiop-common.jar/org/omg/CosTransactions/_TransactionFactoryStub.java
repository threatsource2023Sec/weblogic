package org.omg.CosTransactions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;

public class _TransactionFactoryStub extends ObjectImpl implements TransactionFactory {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/TransactionFactory:1.0"};

   public Control create(int time_out) {
      InputStream $in = null;

      Control $result;
      try {
         OutputStream $out = this._request("create", true);
         $out.write_ulong(time_out);
         $in = this._invoke($out);
         $result = ControlHelper.read($in);
         Control var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.create(time_out);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public Control recreate(PropagationContext ctx) {
      InputStream $in = null;

      Control $result;
      try {
         OutputStream $out = this._request("recreate", true);
         PropagationContextHelper.write($out, ctx);
         $in = this._invoke($out);
         $result = ControlHelper.read($in);
         Control var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.recreate(ctx);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }

   private void readObject(ObjectInputStream s) throws IOException {
      String str = s.readUTF();
      String[] args = null;
      Properties props = null;
      ORB orb = ORB.init((String[])args, (Properties)props);

      try {
         Object obj = orb.string_to_object(str);
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         this._set_delegate(delegate);
      } finally {
         orb.destroy();
      }

   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      String[] args = null;
      Properties props = null;
      ORB orb = ORB.init((String[])args, (Properties)props);

      try {
         String str = orb.object_to_string(this);
         s.writeUTF(str);
      } finally {
         orb.destroy();
      }

   }
}
