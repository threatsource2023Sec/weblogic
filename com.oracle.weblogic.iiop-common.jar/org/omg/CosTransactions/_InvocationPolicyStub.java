package org.omg.CosTransactions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyHelper;
import org.omg.CORBA.PolicyTypeHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;

public class _InvocationPolicyStub extends ObjectImpl implements InvocationPolicy {
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/InvocationPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

   public short ipv() {
      InputStream $in = null;

      short $result;
      try {
         OutputStream $out = this._request("_get_ipv", true);
         $in = this._invoke($out);
         $result = InvocationPolicyValueHelper.read($in);
         short var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.ipv();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public int policy_type() {
      InputStream $in = null;

      int $result;
      try {
         OutputStream $out = this._request("_get_policy_type", true);
         $in = this._invoke($out);
         $result = PolicyTypeHelper.read($in);
         int var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.policy_type();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public Policy copy() {
      InputStream $in = null;

      Policy $result;
      try {
         OutputStream $out = this._request("copy", true);
         $in = this._invoke($out);
         $result = PolicyHelper.read($in);
         Policy var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.copy();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public void destroy() {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("destroy", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.destroy();
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
