package weblogic.corba.cos.naming;

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
import org.omg.CosNaming.BindingHelper;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingListHelper;
import org.omg.CosNaming.BindingListHolder;

public class _BindingIteratorStub extends ObjectImpl implements BindingIterator {
   private static String[] __ids = new String[]{"IDL:weblogic/corba/cos/naming/BindingIterator:1.0", "IDL:omg.org/CosNaming/BindingIterator:1.0"};

   public boolean next_one(BindingHolder b) {
      InputStream $in = null;

      boolean $result;
      try {
         OutputStream $out = this._request("next_one", true);
         $in = this._invoke($out);
         $result = $in.read_boolean();
         b.value = BindingHelper.read($in);
         boolean var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.next_one(b);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public boolean next_n(int how_many, BindingListHolder bl) {
      InputStream $in = null;

      boolean $result;
      try {
         OutputStream $out = this._request("next_n", true);
         $out.write_ulong(how_many);
         $in = this._invoke($out);
         $result = $in.read_boolean();
         bl.value = BindingListHelper.read($in);
         boolean var6 = $result;
         return var6;
      } catch (ApplicationException var11) {
         $in = var11.getInputStream();
         String _id = var11.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var12) {
         $result = this.next_n(how_many, bl);
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
