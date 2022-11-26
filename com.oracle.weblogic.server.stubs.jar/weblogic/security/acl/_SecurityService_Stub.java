package weblogic.security.acl;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.security.acl.internal.AuthenticatedUser;

public final class _SecurityService_Stub extends Stub implements SecurityService {
   private static String[] _type_ids = new String[]{"RMI:weblogic.security.acl.SecurityService:0000000000000000"};
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$UserInfo;
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$internal$AuthenticatedUser;

   public String[] _ids() {
      return _type_ids;
   }

   public final AuthenticatedUser authenticate(UserInfo var1) throws RemoteException {
      try {
         InputStream var2 = null;

         AuthenticatedUser var6;
         try {
            OutputStream var3 = (OutputStream)this._request("authenticate", true);
            var3.write_value(var1, class$weblogic$security$acl$UserInfo == null ? (class$weblogic$security$acl$UserInfo = class$("weblogic.security.acl.UserInfo")) : class$weblogic$security$acl$UserInfo);
            var2 = (InputStream)this._invoke(var3);
            AuthenticatedUser var4 = (AuthenticatedUser)var2.read_value(class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.authenticate(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
