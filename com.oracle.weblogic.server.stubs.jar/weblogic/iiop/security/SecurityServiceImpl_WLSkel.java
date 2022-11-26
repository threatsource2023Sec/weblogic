package weblogic.iiop.security;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedUser;

public final class SecurityServiceImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$UserInfo;
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$internal$AuthenticatedUser;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      switch (var1) {
         case 0:
            UserInfo var5;
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (UserInfo)var6.readObject(class$weblogic$security$acl$UserInfo == null ? (class$weblogic$security$acl$UserInfo = class$("weblogic.security.acl.UserInfo")) : class$weblogic$security$acl$UserInfo);
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            } catch (ClassNotFoundException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            AuthenticatedUser var7 = ((SecurityServiceImpl)var4).authenticate(var5, var2);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var7, class$weblogic$security$acl$internal$AuthenticatedUser == null ? (class$weblogic$security$acl$internal$AuthenticatedUser = class$("weblogic.security.acl.internal.AuthenticatedUser")) : class$weblogic$security$acl$internal$AuthenticatedUser);
               return var3;
            } catch (IOException var9) {
               throw new MarshalException("error marshalling return", var9);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((SecurityService)var3).authenticate((UserInfo)var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
