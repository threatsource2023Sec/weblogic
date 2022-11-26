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

public class _CoordinatorStub extends ObjectImpl implements Coordinator {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/Coordinator:1.0"};

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

   public Status get_parent_status() {
      InputStream $in = null;

      Status $result;
      try {
         OutputStream $out = this._request("get_parent_status", true);
         $in = this._invoke($out);
         $result = StatusHelper.read($in);
         Status var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_parent_status();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public Status get_top_level_status() {
      InputStream $in = null;

      Status $result;
      try {
         OutputStream $out = this._request("get_top_level_status", true);
         $in = this._invoke($out);
         $result = StatusHelper.read($in);
         Status var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_top_level_status();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public boolean is_same_transaction(Coordinator tc) {
      InputStream $in = null;

      boolean $result;
      try {
         OutputStream $out = this._request("is_same_transaction", true);
         CoordinatorHelper.write($out, tc);
         $in = this._invoke($out);
         $result = $in.read_boolean();
         boolean var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.is_same_transaction(tc);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public boolean is_related_transaction(Coordinator tc) {
      InputStream $in = null;

      boolean $result;
      try {
         OutputStream $out = this._request("is_related_transaction", true);
         CoordinatorHelper.write($out, tc);
         $in = this._invoke($out);
         $result = $in.read_boolean();
         boolean var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.is_related_transaction(tc);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public boolean is_ancestor_transaction(Coordinator tc) {
      InputStream $in = null;

      boolean $result;
      try {
         OutputStream $out = this._request("is_ancestor_transaction", true);
         CoordinatorHelper.write($out, tc);
         $in = this._invoke($out);
         $result = $in.read_boolean();
         boolean var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.is_ancestor_transaction(tc);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public boolean is_descendant_transaction(Coordinator tc) {
      InputStream $in = null;

      boolean $result;
      try {
         OutputStream $out = this._request("is_descendant_transaction", true);
         CoordinatorHelper.write($out, tc);
         $in = this._invoke($out);
         $result = $in.read_boolean();
         boolean var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.is_descendant_transaction(tc);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public boolean is_top_level_transaction() {
      InputStream $in = null;

      boolean $result;
      try {
         OutputStream $out = this._request("is_top_level_transaction", true);
         $in = this._invoke($out);
         $result = $in.read_boolean();
         boolean var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.is_top_level_transaction();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public int hash_transaction() {
      InputStream $in = null;

      int $result;
      try {
         OutputStream $out = this._request("hash_transaction", true);
         $in = this._invoke($out);
         $result = $in.read_ulong();
         int var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.hash_transaction();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public int hash_top_level_tran() {
      InputStream $in = null;

      int $result;
      try {
         OutputStream $out = this._request("hash_top_level_tran", true);
         $in = this._invoke($out);
         $result = $in.read_ulong();
         int var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.hash_top_level_tran();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public RecoveryCoordinator register_resource(Resource r) throws Inactive {
      InputStream $in = null;

      RecoveryCoordinator $result;
      try {
         OutputStream $out = this._request("register_resource", true);
         ResourceHelper.write($out, r);
         $in = this._invoke($out);
         $result = RecoveryCoordinatorHelper.read($in);
         RecoveryCoordinator var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/Inactive:1.0")) {
            throw InactiveHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.register_resource(r);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public void register_synchronization(Synchronization sync) throws Inactive, SynchronizationUnavailable {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("register_synchronization", true);
         SynchronizationHelper.write($out, sync);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/Inactive:1.0")) {
            throw InactiveHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/SynchronizationUnavailable:1.0")) {
            throw SynchronizationUnavailableHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         this.register_synchronization(sync);
      } finally {
         this._releaseReply($in);
      }

   }

   public void register_subtran_aware(SubtransactionAwareResource r) throws Inactive, NotSubtransaction {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("register_subtran_aware", true);
         SubtransactionAwareResourceHelper.write($out, r);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/Inactive:1.0")) {
            throw InactiveHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/NotSubtransaction:1.0")) {
            throw NotSubtransactionHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         this.register_subtran_aware(r);
      } finally {
         this._releaseReply($in);
      }

   }

   public void rollback_only() throws Inactive {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rollback_only", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/Inactive:1.0")) {
            throw InactiveHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.rollback_only();
      } finally {
         this._releaseReply($in);
      }

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

   public Control create_subtransaction() throws SubtransactionsUnavailable, Inactive {
      InputStream $in = null;

      Control $result;
      try {
         OutputStream $out = this._request("create_subtransaction", true);
         $in = this._invoke($out);
         $result = ControlHelper.read($in);
         Control var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/SubtransactionsUnavailable:1.0")) {
            throw SubtransactionsUnavailableHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosTransactions/Inactive:1.0")) {
            throw InactiveHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.create_subtransaction();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public PropagationContext get_txcontext() throws Unavailable {
      InputStream $in = null;

      PropagationContext $result;
      try {
         OutputStream $out = this._request("get_txcontext", true);
         $in = this._invoke($out);
         $result = PropagationContextHelper.read($in);
         PropagationContext var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosTransactions/Unavailable:1.0")) {
            throw UnavailableHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_txcontext();
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
