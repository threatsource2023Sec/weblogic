package weblogic.io.common.internal;

import java.io.FilenameFilter;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class T3FileSystemProxyImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class class$java$io$FilenameFilter;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$weblogic$io$common$internal$OneWayInputClient;
   // $FF: synthetic field
   private static Class class$weblogic$io$common$internal$OneWayInputServer;
   // $FF: synthetic field
   private static Class class$weblogic$io$common$internal$OneWayOutputClient;
   // $FF: synthetic field
   private static Class class$weblogic$io$common$internal$OneWayOutputServer;

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
      String var94;
      int var97;
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var90) {
               throw new UnmarshalException("error unmarshalling arguments", var90);
            } catch (ClassNotFoundException var91) {
               throw new UnmarshalException("error unmarshalling arguments", var91);
            }

            boolean var92 = ((T3FileSystemProxy)var4).absoluteExists(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var92);
               break;
            } catch (IOException var89) {
               throw new MarshalException("error marshalling return", var89);
            }
         case 1:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var87) {
               throw new UnmarshalException("error unmarshalling arguments", var87);
            } catch (ClassNotFoundException var88) {
               throw new UnmarshalException("error unmarshalling arguments", var88);
            }

            boolean var93 = ((T3FileSystemProxy)var4).canRead(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var93);
               break;
            } catch (IOException var86) {
               throw new MarshalException("error marshalling return", var86);
            }
         case 2:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var84) {
               throw new UnmarshalException("error unmarshalling arguments", var84);
            } catch (ClassNotFoundException var85) {
               throw new UnmarshalException("error unmarshalling arguments", var85);
            }

            boolean var95 = ((T3FileSystemProxy)var4).canWrite(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var95);
               break;
            } catch (IOException var83) {
               throw new MarshalException("error marshalling return", var83);
            }
         case 3:
            OneWayInputClient var99;
            int var100;
            try {
               MsgInput var12 = var2.getMsgInput();
               var99 = (OneWayInputClient)var12.readObject(class$weblogic$io$common$internal$OneWayInputClient == null ? (class$weblogic$io$common$internal$OneWayInputClient = class$("weblogic.io.common.internal.OneWayInputClient")) : class$weblogic$io$common$internal$OneWayInputClient);
               var94 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var97 = var12.readInt();
               var100 = var12.readInt();
            } catch (IOException var81) {
               throw new UnmarshalException("error unmarshalling arguments", var81);
            } catch (ClassNotFoundException var82) {
               throw new UnmarshalException("error unmarshalling arguments", var82);
            }

            OneWayInputServer var103 = ((T3FileSystemProxy)var4).createInputStream(var99, var94, var97, var100);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var103, class$weblogic$io$common$internal$OneWayInputServer == null ? (class$weblogic$io$common$internal$OneWayInputServer = class$("weblogic.io.common.internal.OneWayInputServer")) : class$weblogic$io$common$internal$OneWayInputServer);
               break;
            } catch (IOException var80) {
               throw new MarshalException("error marshalling return", var80);
            }
         case 4:
            OneWayOutputClient var98;
            try {
               MsgInput var11 = var2.getMsgInput();
               var98 = (OneWayOutputClient)var11.readObject(class$weblogic$io$common$internal$OneWayOutputClient == null ? (class$weblogic$io$common$internal$OneWayOutputClient = class$("weblogic.io.common.internal.OneWayOutputClient")) : class$weblogic$io$common$internal$OneWayOutputClient);
               var94 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var97 = var11.readInt();
            } catch (IOException var78) {
               throw new UnmarshalException("error unmarshalling arguments", var78);
            } catch (ClassNotFoundException var79) {
               throw new UnmarshalException("error unmarshalling arguments", var79);
            }

            OneWayOutputServer var102 = ((T3FileSystemProxy)var4).createOutputStream(var98, var94, var97);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var102, class$weblogic$io$common$internal$OneWayOutputServer == null ? (class$weblogic$io$common$internal$OneWayOutputServer = class$("weblogic.io.common.internal.OneWayOutputServer")) : class$weblogic$io$common$internal$OneWayOutputServer);
               break;
            } catch (IOException var77) {
               throw new MarshalException("error marshalling return", var77);
            }
         case 5:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            } catch (ClassNotFoundException var76) {
               throw new UnmarshalException("error unmarshalling arguments", var76);
            }

            boolean var96 = ((T3FileSystemProxy)var4).delete(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var96);
               break;
            } catch (IOException var74) {
               throw new MarshalException("error marshalling return", var74);
            }
         case 6:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            } catch (ClassNotFoundException var73) {
               throw new UnmarshalException("error unmarshalling arguments", var73);
            }

            boolean var101 = ((T3FileSystemProxy)var4).exists(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var101);
               break;
            } catch (IOException var71) {
               throw new MarshalException("error marshalling return", var71);
            }
         case 7:
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var69) {
               throw new UnmarshalException("error unmarshalling arguments", var69);
            } catch (ClassNotFoundException var70) {
               throw new UnmarshalException("error unmarshalling arguments", var70);
            }

            String var104 = ((T3FileSystemProxy)var4).getCanonicalPath(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var104, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var68) {
               throw new MarshalException("error marshalling return", var68);
            }
         case 8:
            var5 = ((T3FileSystemProxy)var4).getName();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var67) {
               throw new MarshalException("error marshalling return", var67);
            }
         case 9:
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = (String)var14.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            } catch (ClassNotFoundException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            }

            String var105 = ((T3FileSystemProxy)var4).getName(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var105, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var64) {
               throw new MarshalException("error marshalling return", var64);
            }
         case 10:
            try {
               MsgInput var15 = var2.getMsgInput();
               var5 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            } catch (ClassNotFoundException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            }

            String var106 = ((T3FileSystemProxy)var4).getParent(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var106, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var61) {
               throw new MarshalException("error marshalling return", var61);
            }
         case 11:
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = (String)var16.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            } catch (ClassNotFoundException var60) {
               throw new UnmarshalException("error unmarshalling arguments", var60);
            }

            boolean var107 = ((T3FileSystemProxy)var4).isAbsoluteDirectory(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var107);
               break;
            } catch (IOException var58) {
               throw new MarshalException("error marshalling return", var58);
            }
         case 12:
            try {
               MsgInput var17 = var2.getMsgInput();
               var5 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var56) {
               throw new UnmarshalException("error unmarshalling arguments", var56);
            } catch (ClassNotFoundException var57) {
               throw new UnmarshalException("error unmarshalling arguments", var57);
            }

            boolean var108 = ((T3FileSystemProxy)var4).isDirectory(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var108);
               break;
            } catch (IOException var55) {
               throw new MarshalException("error marshalling return", var55);
            }
         case 13:
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            } catch (ClassNotFoundException var54) {
               throw new UnmarshalException("error unmarshalling arguments", var54);
            }

            boolean var109 = ((T3FileSystemProxy)var4).isFile(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var109);
               break;
            } catch (IOException var52) {
               throw new MarshalException("error marshalling return", var52);
            }
         case 14:
            try {
               MsgInput var19 = var2.getMsgInput();
               var5 = (String)var19.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            } catch (ClassNotFoundException var51) {
               throw new UnmarshalException("error unmarshalling arguments", var51);
            }

            long var110 = ((T3FileSystemProxy)var4).lastModified(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeLong(var110);
               break;
            } catch (IOException var49) {
               throw new MarshalException("error marshalling return", var49);
            }
         case 15:
            try {
               MsgInput var20 = var2.getMsgInput();
               var5 = (String)var20.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            } catch (ClassNotFoundException var48) {
               throw new UnmarshalException("error unmarshalling arguments", var48);
            }

            long var111 = ((T3FileSystemProxy)var4).length(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeLong(var111);
               break;
            } catch (IOException var46) {
               throw new MarshalException("error marshalling return", var46);
            }
         case 16:
            try {
               MsgInput var21 = var2.getMsgInput();
               var5 = (String)var21.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            } catch (ClassNotFoundException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            }

            String[] var113 = ((T3FileSystemProxy)var4).list(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var113, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               break;
            } catch (IOException var43) {
               throw new MarshalException("error marshalling return", var43);
            }
         case 17:
            FilenameFilter var112;
            try {
               MsgInput var23 = var2.getMsgInput();
               var5 = (String)var23.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var112 = (FilenameFilter)var23.readObject(class$java$io$FilenameFilter == null ? (class$java$io$FilenameFilter = class$("java.io.FilenameFilter")) : class$java$io$FilenameFilter);
            } catch (IOException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            } catch (ClassNotFoundException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            }

            String[] var115 = ((T3FileSystemProxy)var4).list(var5, var112);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var115, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               break;
            } catch (IOException var40) {
               throw new MarshalException("error marshalling return", var40);
            }
         case 18:
            try {
               MsgInput var22 = var2.getMsgInput();
               var5 = (String)var22.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            } catch (ClassNotFoundException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            boolean var114 = ((T3FileSystemProxy)var4).mkdir(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var114);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 19:
            try {
               MsgInput var24 = var2.getMsgInput();
               var5 = (String)var24.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            } catch (ClassNotFoundException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            }

            boolean var116 = ((T3FileSystemProxy)var4).mkdirs(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var116);
               break;
            } catch (IOException var34) {
               throw new MarshalException("error marshalling return", var34);
            }
         case 20:
            var5 = ((T3FileSystemProxy)var4).pathSeparator();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 21:
            String var25;
            try {
               MsgInput var26 = var2.getMsgInput();
               var5 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var25 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            } catch (ClassNotFoundException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            boolean var27 = ((T3FileSystemProxy)var4).renameTo(var5, var25);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var27);
               break;
            } catch (IOException var30) {
               throw new MarshalException("error marshalling return", var30);
            }
         case 22:
            var5 = ((T3FileSystemProxy)var4).separator();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var29) {
               throw new MarshalException("error marshalling return", var29);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return new Boolean(((T3FileSystemProxy)var3).absoluteExists((String)var2[0]));
         case 1:
            return new Boolean(((T3FileSystemProxy)var3).canRead((String)var2[0]));
         case 2:
            return new Boolean(((T3FileSystemProxy)var3).canWrite((String)var2[0]));
         case 3:
            return ((T3FileSystemProxy)var3).createInputStream((OneWayInputClient)var2[0], (String)var2[1], (Integer)var2[2], (Integer)var2[3]);
         case 4:
            return ((T3FileSystemProxy)var3).createOutputStream((OneWayOutputClient)var2[0], (String)var2[1], (Integer)var2[2]);
         case 5:
            return new Boolean(((T3FileSystemProxy)var3).delete((String)var2[0]));
         case 6:
            return new Boolean(((T3FileSystemProxy)var3).exists((String)var2[0]));
         case 7:
            return ((T3FileSystemProxy)var3).getCanonicalPath((String)var2[0]);
         case 8:
            return ((T3FileSystemProxy)var3).getName();
         case 9:
            return ((T3FileSystemProxy)var3).getName((String)var2[0]);
         case 10:
            return ((T3FileSystemProxy)var3).getParent((String)var2[0]);
         case 11:
            return new Boolean(((T3FileSystemProxy)var3).isAbsoluteDirectory((String)var2[0]));
         case 12:
            return new Boolean(((T3FileSystemProxy)var3).isDirectory((String)var2[0]));
         case 13:
            return new Boolean(((T3FileSystemProxy)var3).isFile((String)var2[0]));
         case 14:
            return new Long(((T3FileSystemProxy)var3).lastModified((String)var2[0]));
         case 15:
            return new Long(((T3FileSystemProxy)var3).length((String)var2[0]));
         case 16:
            return ((T3FileSystemProxy)var3).list((String)var2[0]);
         case 17:
            return ((T3FileSystemProxy)var3).list((String)var2[0], (FilenameFilter)var2[1]);
         case 18:
            return new Boolean(((T3FileSystemProxy)var3).mkdir((String)var2[0]));
         case 19:
            return new Boolean(((T3FileSystemProxy)var3).mkdirs((String)var2[0]));
         case 20:
            return ((T3FileSystemProxy)var3).pathSeparator();
         case 21:
            return new Boolean(((T3FileSystemProxy)var3).renameTo((String)var2[0], (String)var2[1]));
         case 22:
            return ((T3FileSystemProxy)var3).separator();
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
