package weblogic.corba.ejb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;

public class _CorbaBeanHomeStub extends ObjectImpl implements CorbaBeanHome {
   private static String[] __ids = new String[]{"IDL:weblogic/corba/CorbaBeanHome:1.0"};

   public void remove(Object obj) throws RemoveException, EJBException {
      throw new UnsupportedOperationException();
   }

   public CorbaBeanObject create() throws CreateException {
      InputStream $in = null;

      CorbaBeanObject $result;
      try {
         OutputStream $out = this._request("create", true);
         $in = this._invoke($out);
         $result = CorbaBeanObjectHelper.read($in);
         CorbaBeanObject var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:javax/ejb/CreateException:1.0")) {
            throw CreateExceptionHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.create();
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
      org.omg.CORBA.Object obj = ORB.init((String[])args, (Properties)props).string_to_object(str);
      Delegate delegate = ((ObjectImpl)obj)._get_delegate();
      this._set_delegate(delegate);
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      String[] args = null;
      Properties props = null;
      String str = ORB.init((String[])args, (Properties)props).object_to_string(this);
      s.writeUTF(str);
   }
}
