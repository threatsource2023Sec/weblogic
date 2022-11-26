package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Reader;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ReaderBlockGetterImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$C;
   // $FF: synthetic field
   private static Class class$java$io$Reader;

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
            ((ReaderBlockGetter)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = var6.readInt();
            } catch (IOException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            }

            ((ReaderBlockGetter)var4).close(var5);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = var7.readInt();
            } catch (IOException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            }

            char[] var32 = ((ReaderBlockGetter)var4).getBlock(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var32, array$C == null ? (array$C = class$("[C")) : array$C);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 3:
            var5 = ((ReaderBlockGetter)var4).getBlockSize();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var5);
               break;
            } catch (IOException var27) {
               throw new MarshalException("error marshalling return", var27);
            }
         case 4:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = var8.readInt();
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            }

            Reader var34 = ((ReaderBlockGetter)var4).getReader(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var34, class$java$io$Reader == null ? (class$java$io$Reader = class$("java.io.Reader")) : class$java$io$Reader);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 5:
            int var33;
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = var10.readInt();
               var33 = var10.readInt();
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            ((ReaderBlockGetter)var4).mark(var5, var33);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = var9.readInt();
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            boolean var35 = ((ReaderBlockGetter)var4).markSupported(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var35);
               break;
            } catch (IOException var22) {
               throw new MarshalException("error marshalling return", var22);
            }
         case 7:
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = var11.readInt();
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            boolean var37 = ((ReaderBlockGetter)var4).ready(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var37);
               break;
            } catch (IOException var20) {
               throw new MarshalException("error marshalling return", var20);
            }
         case 8:
            Reader var31;
            int var36;
            try {
               MsgInput var13 = var2.getMsgInput();
               var31 = (Reader)var13.readObject(class$java$io$Reader == null ? (class$java$io$Reader = class$("java.io.Reader")) : class$java$io$Reader);
               var36 = var13.readInt();
            } catch (IOException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            } catch (ClassNotFoundException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            int var14 = ((ReaderBlockGetter)var4).register(var31, var36);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var14);
               break;
            } catch (IOException var17) {
               throw new MarshalException("error marshalling return", var17);
            }
         case 9:
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = var12.readInt();
            } catch (IOException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            }

            ((ReaderBlockGetter)var4).reset(var5);
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
            ((ReaderBlockGetter)var3).close();
            return null;
         case 1:
            ((ReaderBlockGetter)var3).close((Integer)var2[0]);
            return null;
         case 2:
            return ((ReaderBlockGetter)var3).getBlock((Integer)var2[0]);
         case 3:
            return new Integer(((ReaderBlockGetter)var3).getBlockSize());
         case 4:
            return ((ReaderBlockGetter)var3).getReader((Integer)var2[0]);
         case 5:
            ((ReaderBlockGetter)var3).mark((Integer)var2[0], (Integer)var2[1]);
            return null;
         case 6:
            return new Boolean(((ReaderBlockGetter)var3).markSupported((Integer)var2[0]));
         case 7:
            return new Boolean(((ReaderBlockGetter)var3).ready((Integer)var2[0]));
         case 8:
            return new Integer(((ReaderBlockGetter)var3).register((Reader)var2[0], (Integer)var2[1]));
         case 9:
            ((ReaderBlockGetter)var3).reset((Integer)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
