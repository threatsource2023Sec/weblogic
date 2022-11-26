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

public class _ControlStub extends ObjectImpl implements Control {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/Control:1.0"};

   public Terminator get_terminator() throws Unavailable {
      InputStream $in = null;

      Terminator $result;
      try {
         OutputStream $out = this._request("get_terminator", true);
         $in = this._invoke($out);
         $result = TerminatorHelper.read($in);
         Terminator var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/Unavailable:1.0")) {
            throw UnavailableHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_terminator();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public Coordinator get_coordinator() throws Unavailable {
      InputStream $in = null;

      Coordinator $result;
      try {
         OutputStream $out = this._request("get_coordinator", true);
         $in = this._invoke($out);
         $result = CoordinatorHelper.read($in);
         Coordinator var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/Unavailable:1.0")) {
            throw UnavailableHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_coordinator();
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
