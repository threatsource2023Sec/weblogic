package weblogic.cluster.singleton;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Set;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteLeasingBasisImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Set;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var5;
      int var13;
      int var36;
      String var42;
      switch (var1) {
         case 0:
            String var37;
            int var39;
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var37 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var39 = var8.readInt();
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            } catch (ClassNotFoundException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            }

            boolean var41 = ((RemoteLeasingBasis)var4).acquire(var5, var37, var39);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var41);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var36 = var6.readInt();
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            String[] var38 = ((RemoteLeasingBasis)var4).findExpiredLeases(var36);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var38, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               break;
            } catch (IOException var31) {
               throw new MarshalException("error marshalling return", var31);
            }
         case 2:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            } catch (ClassNotFoundException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            }

            String var40 = ((RemoteLeasingBasis)var4).findOwner(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var40, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 3:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            var42 = ((RemoteLeasingBasis)var4).findPreviousOwner(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var42, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 4:
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var42 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            } catch (ClassNotFoundException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            ((RemoteLeasingBasis)var4).release(var5, var42);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            try {
               MsgInput var12 = var2.getMsgInput();
               var36 = var12.readInt();
               var42 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            } catch (ClassNotFoundException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            var13 = ((RemoteLeasingBasis)var4).renewAllLeases(var36, var42);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var13);
               break;
            } catch (IOException var20) {
               throw new MarshalException("error marshalling return", var20);
            }
         case 6:
            Set var10;
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var10 = (Set)var14.readObject(class$java$util$Set == null ? (class$java$util$Set = class$("java.util.Set")) : class$java$util$Set);
               var13 = var14.readInt();
            } catch (IOException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            } catch (ClassNotFoundException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            int var15 = ((RemoteLeasingBasis)var4).renewLeases(var5, var10, var13);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var15);
               break;
            } catch (IOException var17) {
               throw new MarshalException("error marshalling return", var17);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return new Boolean(((RemoteLeasingBasis)var3).acquire((String)var2[0], (String)var2[1], (Integer)var2[2]));
         case 1:
            return ((RemoteLeasingBasis)var3).findExpiredLeases((Integer)var2[0]);
         case 2:
            return ((RemoteLeasingBasis)var3).findOwner((String)var2[0]);
         case 3:
            return ((RemoteLeasingBasis)var3).findPreviousOwner((String)var2[0]);
         case 4:
            ((RemoteLeasingBasis)var3).release((String)var2[0], (String)var2[1]);
            return null;
         case 5:
            return new Integer(((RemoteLeasingBasis)var3).renewAllLeases((Integer)var2[0], (String)var2[1]));
         case 6:
            return new Integer(((RemoteLeasingBasis)var3).renewLeases((String)var2[0], (Set)var2[1], (Integer)var2[2]));
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
