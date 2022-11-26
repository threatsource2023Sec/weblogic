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
import org.omg.CosTransactions.Control;
import org.omg.CosTransactions.PropagationContext;
import org.omg.CosTransactions.TransactionFactoryOperations;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class TransactionFactoryImpl_WLSkel extends Skeleton {
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
   private static Class class$org$omg$CosTransactions$Control;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$PropagationContext;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      int var49;
      String var51;
      String var53;
      org.omg.CORBA.Object var54;
      NVList var56;
      NamedValue var59;
      Context var60;
      Request var67;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var60 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var53 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var56 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var59 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            } catch (ClassNotFoundException var48) {
               throw new UnmarshalException("error unmarshalling arguments", var48);
            }

            Request var63 = ((org.omg.CORBA.Object)var4)._create_request(var60, var53, var56, var59);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var63, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var46) {
               throw new MarshalException("error marshalling return", var46);
            }
         case 1:
            ExceptionList var62;
            ContextList var65;
            try {
               MsgInput var12 = var2.getMsgInput();
               var60 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var53 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var56 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var59 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var62 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var65 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            } catch (ClassNotFoundException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            }

            var67 = ((org.omg.CORBA.Object)var4)._create_request(var60, var53, var56, var59, var62, var65);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var67, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var43) {
               throw new MarshalException("error marshalling return", var43);
            }
         case 2:
            var54 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var54, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var42) {
               throw new MarshalException("error marshalling return", var42);
            }
         case 3:
            DomainManager[] var57 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var57, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var41) {
               throw new MarshalException("error marshalling return", var41);
            }
         case 4:
            var54 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var54, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var40) {
               throw new MarshalException("error marshalling return", var40);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var49 = var6.readInt();
            } catch (IOException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            Policy var55 = ((org.omg.CORBA.Object)var4)._get_policy(var49);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var55, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var38) {
               throw new MarshalException("error marshalling return", var38);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var49 = var7.readInt();
            } catch (IOException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            int var58 = ((org.omg.CORBA.Object)var4)._hash(var49);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var58);
               break;
            } catch (IOException var36) {
               throw new MarshalException("error marshalling return", var36);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var51 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            } catch (ClassNotFoundException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            }

            boolean var61 = ((org.omg.CORBA.Object)var4)._is_a(var51);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var61);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var54 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            } catch (ClassNotFoundException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            boolean var64 = ((org.omg.CORBA.Object)var4)._is_equivalent(var54);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var64);
               break;
            } catch (IOException var30) {
               throw new MarshalException("error marshalling return", var30);
            }
         case 9:
            boolean var52 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var52);
               break;
            } catch (IOException var29) {
               throw new MarshalException("error marshalling return", var29);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var51 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            } catch (ClassNotFoundException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            }

            var67 = ((org.omg.CORBA.Object)var4)._request(var51);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var67, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var26) {
               throw new MarshalException("error marshalling return", var26);
            }
         case 12:
            Policy[] var50;
            SetOverrideType var66;
            try {
               MsgInput var14 = var2.getMsgInput();
               var50 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var66 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            } catch (ClassNotFoundException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            }

            org.omg.CORBA.Object var69 = ((org.omg.CORBA.Object)var4)._set_policy_override(var50, var66);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var69, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var23) {
               throw new MarshalException("error marshalling return", var23);
            }
         case 13:
            try {
               MsgInput var13 = var2.getMsgInput();
               var49 = var13.readInt();
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            Control var68 = ((TransactionFactoryOperations)var4).create(var49);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var68, class$org$omg$CosTransactions$Control == null ? (class$org$omg$CosTransactions$Control = class$("org.omg.CosTransactions.Control")) : class$org$omg$CosTransactions$Control);
               break;
            } catch (IOException var21) {
               throw new MarshalException("error marshalling return", var21);
            }
         case 14:
            PropagationContext var5;
            try {
               MsgInput var15 = var2.getMsgInput();
               var5 = (PropagationContext)var15.readObject(class$org$omg$CosTransactions$PropagationContext == null ? (class$org$omg$CosTransactions$PropagationContext = class$("org.omg.CosTransactions.PropagationContext")) : class$org$omg$CosTransactions$PropagationContext);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            Control var16 = ((TransactionFactoryOperations)var4).recreate(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var16, class$org$omg$CosTransactions$Control == null ? (class$org$omg$CosTransactions$Control = class$("org.omg.CosTransactions.Control")) : class$org$omg$CosTransactions$Control);
               break;
            } catch (IOException var18) {
               throw new MarshalException("error marshalling return", var18);
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
            return ((TransactionFactoryOperations)var3).create((Integer)var2[0]);
         case 14:
            return ((TransactionFactoryOperations)var3).recreate((PropagationContext)var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
