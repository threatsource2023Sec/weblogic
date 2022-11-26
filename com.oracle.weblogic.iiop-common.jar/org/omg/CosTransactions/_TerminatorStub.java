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

public class _TerminatorStub extends ObjectImpl implements Terminator {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/Terminator:1.0"};

   public void commit(boolean report_heuristics) throws HeuristicMixed, HeuristicHazard {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("commit", true);
         $out.write_boolean(report_heuristics);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicMixed:1.0")) {
            throw HeuristicMixedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicHazard:1.0")) {
            throw HeuristicHazardHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         this.commit(report_heuristics);
      } finally {
         this._releaseReply($in);
      }

   }

   public void rollback() {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rollback", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.rollback();
      } finally {
         this._releaseReply($in);
      }

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
