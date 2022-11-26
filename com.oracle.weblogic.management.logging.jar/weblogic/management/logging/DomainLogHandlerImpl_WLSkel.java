package weblogic.management.logging;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.List;
import weblogic.logging.LogEntry;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class DomainLogHandlerImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Lweblogic$logging$LogEntry;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$List;

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
      switch (var1) {
         case 0:
            ((DomainLogHandler)var4).ping();
            this.associateResponseData(var2, var3);
            break;
         case 1:
            LogEntry[] var26;
            try {
               MsgInput var6 = var2.getMsgInput();
               var26 = (LogEntry[])var6.readObject(array$Lweblogic$logging$LogEntry == null ? (array$Lweblogic$logging$LogEntry = class$("[Lweblogic.logging.LogEntry;")) : array$Lweblogic$logging$LogEntry);
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            } catch (ClassNotFoundException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            }

            ((DomainLogHandler)var4).publishLogEntries(var26);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            String var9;
            String var10;
            String var11;
            String var12;
            String var13;
            String var14;
            String var15;
            String var16;
            String var17;
            String var27;
            String var28;
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var27 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var28 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var9 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var10 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var11 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var12 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var13 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var14 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var15 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var16 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var17 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            ((DomainLogHandler)var4).sendALAlertTrap(var5, var27, var28, var9, var10, var11, var12, var13, var14, var15, var16, var17);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            List var7;
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var7 = (List)var8.readObject(class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List);
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            ((DomainLogHandler)var4).sendTrap(var5, var7);
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
            ((DomainLogHandler)var3).ping();
            return null;
         case 1:
            ((DomainLogHandler)var3).publishLogEntries((LogEntry[])var2[0]);
            return null;
         case 2:
            ((DomainLogHandler)var3).sendALAlertTrap((String)var2[0], (String)var2[1], (String)var2[2], (String)var2[3], (String)var2[4], (String)var2[5], (String)var2[6], (String)var2[7], (String)var2[8], (String)var2[9], (String)var2[10], (String)var2[11]);
            return null;
         case 3:
            ((DomainLogHandler)var3).sendTrap((String)var2[0], (List)var2[1]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
