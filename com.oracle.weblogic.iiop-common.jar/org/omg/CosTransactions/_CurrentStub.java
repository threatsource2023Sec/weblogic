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

public class _CurrentStub extends ObjectImpl implements Current {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/Current:1.0"};

   public void begin() throws SubtransactionsUnavailable {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("begin", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/SubtransactionsUnavailable:1.0")) {
            throw SubtransactionsUnavailableHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.begin();
      } finally {
         this._releaseReply($in);
      }

   }

   public void commit(boolean report_heuristics) throws NoTransaction, HeuristicMixed, HeuristicHazard {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("commit", true);
         $out.write_boolean(report_heuristics);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/NoTransaction:1.0")) {
            throw NoTransactionHelper.read($in);
         }

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

   public void rollback() throws NoTransaction {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rollback", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/NoTransaction:1.0")) {
            throw NoTransactionHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.rollback();
      } finally {
         this._releaseReply($in);
      }

   }

   public void rollback_only() throws NoTransaction {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rollback_only", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/NoTransaction:1.0")) {
            throw NoTransactionHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.rollback_only();
      } finally {
         this._releaseReply($in);
      }

   }

   public Status get_status() {
      InputStream $in = null;

      Status $result;
      try {
         OutputStream $out = this._request("get_status", true);
         $in = this._invoke($out);
         $result = StatusHelper.read($in);
         Status var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_status();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public String get_transaction_name() {
      InputStream $in = null;

      String _id;
      try {
         OutputStream $out = this._request("get_transaction_name", true);
         $in = this._invoke($out);
         _id = $in.read_string();
         String var4 = _id;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         _id = this.get_transaction_name();
      } finally {
         this._releaseReply($in);
      }

      return _id;
   }

   public void set_timeout(int seconds) {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("set_timeout", true);
         $out.write_ulong(seconds);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         this.set_timeout(seconds);
      } finally {
         this._releaseReply($in);
      }

   }

   public int get_timeout() {
      InputStream $in = null;

      int $result;
      try {
         OutputStream $out = this._request("get_timeout", true);
         $in = this._invoke($out);
         $result = $in.read_ulong();
         int var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_timeout();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public Control get_control() {
      InputStream $in = null;

      Control $result;
      try {
         OutputStream $out = this._request("get_control", true);
         $in = this._invoke($out);
         $result = ControlHelper.read($in);
         Control var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_control();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public Control suspend() {
      InputStream $in = null;

      Control $result;
      try {
         OutputStream $out = this._request("suspend", true);
         $in = this._invoke($out);
         $result = ControlHelper.read($in);
         Control var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.suspend();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public void resume(Control which) throws InvalidControl {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("resume", true);
         ControlHelper.write($out, which);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/InvalidControl:1.0")) {
            throw InvalidControlHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         this.resume(which);
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
