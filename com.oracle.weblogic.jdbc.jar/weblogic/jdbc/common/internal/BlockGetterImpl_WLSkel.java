package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class BlockGetterImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$B;
   // $FF: synthetic field
   private static Class class$java$io$InputStream;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      int var5;
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = var6.readInt();
            } catch (IOException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            }

            int var31 = ((BlockGetter)var4).available(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var31);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 1:
            ((BlockGetter)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = var7.readInt();
            } catch (IOException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            ((BlockGetter)var4).close(var5);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = var8.readInt();
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            }

            byte[] var32 = ((BlockGetter)var4).getBlock(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var32, array$B == null ? (array$B = class$("[B")) : array$B);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 4:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = var9.readInt();
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            InputStream var34 = ((BlockGetter)var4).getStream(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var34, class$java$io$InputStream == null ? (class$java$io$InputStream = class$("java.io.InputStream")) : class$java$io$InputStream);
               break;
            } catch (IOException var23) {
               throw new MarshalException("error marshalling return", var23);
            }
         case 5:
            int var33;
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = var11.readInt();
               var33 = var11.readInt();
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            ((BlockGetter)var4).mark(var5, var33);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = var10.readInt();
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            boolean var36 = ((BlockGetter)var4).markSupported(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var36);
               break;
            } catch (IOException var20) {
               throw new MarshalException("error marshalling return", var20);
            }
         case 7:
            InputStream var30;
            int var35;
            try {
               MsgInput var13 = var2.getMsgInput();
               var30 = (InputStream)var13.readObject(class$java$io$InputStream == null ? (class$java$io$InputStream = class$("java.io.InputStream")) : class$java$io$InputStream);
               var35 = var13.readInt();
            } catch (IOException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            } catch (ClassNotFoundException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            int var14 = ((BlockGetter)var4).register(var30, var35);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var14);
               break;
            } catch (IOException var17) {
               throw new MarshalException("error marshalling return", var17);
            }
         case 8:
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = var12.readInt();
            } catch (IOException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            }

            ((BlockGetter)var4).reset(var5);
            this.associateResponseData(var2, var3);
            break;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return new Integer(((BlockGetter)var3).available((Integer)var2[0]));
         case 1:
            ((BlockGetter)var3).close();
            return null;
         case 2:
            ((BlockGetter)var3).close((Integer)var2[0]);
            return null;
         case 3:
            return ((BlockGetter)var3).getBlock((Integer)var2[0]);
         case 4:
            return ((BlockGetter)var3).getStream((Integer)var2[0]);
         case 5:
            ((BlockGetter)var3).mark((Integer)var2[0], (Integer)var2[1]);
            return null;
         case 6:
            return new Boolean(((BlockGetter)var3).markSupported((Integer)var2[0]));
         case 7:
            return new Integer(((BlockGetter)var3).register((InputStream)var2[0], (Integer)var2[1]));
         case 8:
            ((BlockGetter)var3).reset((Integer)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
