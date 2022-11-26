package weblogic.corba.ejb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.RemoveException;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ObjectImpl;

public class _CorbaBeanObjectStub extends ObjectImpl implements CorbaBeanObject {
   private static String[] __ids = new String[]{"IDL:weblogic/corba/CorbaBeanObject:1.0"};

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }

   public EJBLocalHome getEJBLocalHome() throws EJBException {
      throw new UnsupportedOperationException();
   }

   public Object getPrimaryKey() throws EJBException {
      throw new UnsupportedOperationException();
   }

   public void remove() throws RemoveException, EJBException {
      throw new UnsupportedOperationException();
   }

   public boolean isIdentical(EJBLocalObject o) throws EJBException {
      throw new UnsupportedOperationException();
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
