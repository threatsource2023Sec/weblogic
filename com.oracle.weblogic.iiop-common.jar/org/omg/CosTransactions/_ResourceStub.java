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

public class _ResourceStub extends ObjectImpl implements Resource {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/Resource:1.0"};

   public Vote prepare() throws HeuristicMixed, HeuristicHazard {
      InputStream $in = null;

      Vote $result;
      try {
         OutputStream $out = this._request("prepare", true);
         $in = this._invoke($out);
         $result = VoteHelper.read($in);
         Vote var4 = $result;
         return var4;
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
         $result = this.prepare();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public void rollback() throws HeuristicCommit, HeuristicMixed, HeuristicHazard {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rollback", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicCommit:1.0")) {
            throw HeuristicCommitHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicMixed:1.0")) {
            throw HeuristicMixedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicHazard:1.0")) {
            throw HeuristicHazardHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.rollback();
      } finally {
         this._releaseReply($in);
      }

   }

   public void commit() throws NotPrepared, HeuristicRollback, HeuristicMixed, HeuristicHazard {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("commit", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/NotPrepared:1.0")) {
            throw NotPreparedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicRollback:1.0")) {
            throw HeuristicRollbackHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicMixed:1.0")) {
            throw HeuristicMixedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicHazard:1.0")) {
            throw HeuristicHazardHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.commit();
      } finally {
         this._releaseReply($in);
      }

   }

   public void commit_one_phase() throws HeuristicHazard {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("commit_one_phase", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/HeuristicHazard:1.0")) {
            throw HeuristicHazardHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.commit_one_phase();
      } finally {
         this._releaseReply($in);
      }

   }

   public void forget() {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("forget", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.forget();
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
