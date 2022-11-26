package weblogic.corba.cos.transactions;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;
import org.omg.CosTransactions.ControlOperations;
import org.omg.CosTransactions.Coordinator;
import org.omg.CosTransactions.Terminator;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ControlImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$DomainManager;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$Policy;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$Context;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$ContextList;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$ExceptionList;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$NVList;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$NamedValue;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$Object;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$Policy;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$Request;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$SetOverrideType;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$Coordinator;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$Terminator;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var47;
      String var49;
      org.omg.CORBA.Object var50;
      int var51;
      NVList var53;
      NamedValue var56;
      Context var57;
      Request var63;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var57 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var49 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var53 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var56 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            } catch (ClassNotFoundException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            }

            Request var60 = ((org.omg.CORBA.Object)var4)._create_request(var57, var49, var53, var56);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var60, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var42) {
               throw new MarshalException("error marshalling return", var42);
            }
         case 1:
            ExceptionList var59;
            ContextList var62;
            try {
               MsgInput var12 = var2.getMsgInput();
               var57 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var49 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var53 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var56 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var59 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var62 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            } catch (ClassNotFoundException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            }

            var63 = ((org.omg.CORBA.Object)var4)._create_request(var57, var49, var53, var56, var59, var62);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var63, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var39) {
               throw new MarshalException("error marshalling return", var39);
            }
         case 2:
            var50 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var50, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var38) {
               throw new MarshalException("error marshalling return", var38);
            }
         case 3:
            DomainManager[] var54 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var54, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 4:
            var50 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var50, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var36) {
               throw new MarshalException("error marshalling return", var36);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var51 = var6.readInt();
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            }

            Policy var52 = ((org.omg.CORBA.Object)var4)._get_policy(var51);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var52, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var34) {
               throw new MarshalException("error marshalling return", var34);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var51 = var7.readInt();
            } catch (IOException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            }

            int var55 = ((org.omg.CORBA.Object)var4)._hash(var51);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var55);
               break;
            } catch (IOException var32) {
               throw new MarshalException("error marshalling return", var32);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var47 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            } catch (ClassNotFoundException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            }

            boolean var58 = ((org.omg.CORBA.Object)var4)._is_a(var47);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var58);
               break;
            } catch (IOException var29) {
               throw new MarshalException("error marshalling return", var29);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var50 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            } catch (ClassNotFoundException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            }

            boolean var61 = ((org.omg.CORBA.Object)var4)._is_equivalent(var50);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var61);
               break;
            } catch (IOException var26) {
               throw new MarshalException("error marshalling return", var26);
            }
         case 9:
            boolean var48 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var48);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var47 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            } catch (ClassNotFoundException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            var63 = ((org.omg.CORBA.Object)var4)._request(var47);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var63, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var22) {
               throw new MarshalException("error marshalling return", var22);
            }
         case 12:
            SetOverrideType var13;
            Policy[] var46;
            try {
               MsgInput var14 = var2.getMsgInput();
               var46 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var13 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            org.omg.CORBA.Object var15 = ((org.omg.CORBA.Object)var4)._set_policy_override(var46, var13);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var15, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var19) {
               throw new MarshalException("error marshalling return", var19);
            }
         case 13:
            Coordinator var45 = ((ControlOperations)var4).get_coordinator();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var45, class$org$omg$CosTransactions$Coordinator == null ? (class$org$omg$CosTransactions$Coordinator = class$("org.omg.CosTransactions.Coordinator")) : class$org$omg$CosTransactions$Coordinator);
               break;
            } catch (IOException var18) {
               throw new MarshalException("error marshalling return", var18);
            }
         case 14:
            Terminator var5 = ((ControlOperations)var4).get_terminator();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$org$omg$CosTransactions$Terminator == null ? (class$org$omg$CosTransactions$Terminator = class$("org.omg.CosTransactions.Terminator")) : class$org$omg$CosTransactions$Terminator);
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
            return ((org.omg.CORBA.Object)var3)._create_request((Context)var2[0], (String)var2[1], (NVList)var2[2], (NamedValue)var2[3]);
         case 1:
            return ((org.omg.CORBA.Object)var3)._create_request((Context)var2[0], (String)var2[1], (NVList)var2[2], (NamedValue)var2[3], (ExceptionList)var2[4], (ContextList)var2[5]);
         case 2:
            return ((org.omg.CORBA.Object)var3)._duplicate();
         case 3:
            return ((org.omg.CORBA.Object)var3)._get_domain_managers();
         case 4:
            return ((org.omg.CORBA.Object)var3)._get_interface_def();
         case 5:
            return ((org.omg.CORBA.Object)var3)._get_policy((Integer)var2[0]);
         case 6:
            return new Integer(((org.omg.CORBA.Object)var3)._hash((Integer)var2[0]));
         case 7:
            return new Boolean(((org.omg.CORBA.Object)var3)._is_a((String)var2[0]));
         case 8:
            return new Boolean(((org.omg.CORBA.Object)var3)._is_equivalent((org.omg.CORBA.Object)var2[0]));
         case 9:
            return new Boolean(((org.omg.CORBA.Object)var3)._non_existent());
         case 10:
            ((org.omg.CORBA.Object)var3)._release();
            return null;
         case 11:
            return ((org.omg.CORBA.Object)var3)._request((String)var2[0]);
         case 12:
            return ((org.omg.CORBA.Object)var3)._set_policy_override((Policy[])var2[0], (SetOverrideType)var2[1]);
         case 13:
            return ((ControlOperations)var3).get_coordinator();
         case 14:
            return ((ControlOperations)var3).get_terminator();
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
