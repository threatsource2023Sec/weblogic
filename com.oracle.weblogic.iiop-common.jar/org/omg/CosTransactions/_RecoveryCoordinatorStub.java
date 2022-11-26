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

public class _RecoveryCoordinatorStub extends ObjectImpl implements RecoveryCoordinator {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/RecoveryCoordinator:1.0"};

   public Status replay_completion(Resource r) throws NotPrepared {
      InputStream $in = null;

      Status $result;
      try {
         OutputStream $out = this._request("replay_completion", true);
         ResourceHelper.write($out, r);
         $in = this._invoke($out);
         $result = StatusHelper.read($in);
         Status var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/NotPrepared:1.0")) {
            throw NotPreparedHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.replay_completion(r);
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
