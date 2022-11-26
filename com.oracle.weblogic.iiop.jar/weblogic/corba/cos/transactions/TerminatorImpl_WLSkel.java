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
import org.omg.CosTransactions.TerminatorOperations;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class TerminatorImpl_WLSkel extends Skeleton {
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

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      boolean var5;
      String var45;
      String var46;
      org.omg.CORBA.Object var47;
      int var48;
      NVList var50;
      Context var53;
      NamedValue var54;
      Request var61;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var53 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var46 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var50 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var54 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            } catch (ClassNotFoundException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            }

            Request var57 = ((org.omg.CORBA.Object)var4)._create_request(var53, var46, var50, var54);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var57, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var41) {
               throw new MarshalException("error marshalling return", var41);
            }
         case 1:
            ExceptionList var56;
            ContextList var59;
            try {
               MsgInput var12 = var2.getMsgInput();
               var53 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var46 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var50 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var54 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var56 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var59 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            } catch (ClassNotFoundException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            }

            var61 = ((org.omg.CORBA.Object)var4)._create_request(var53, var46, var50, var54, var56, var59);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var61, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var38) {
               throw new MarshalException("error marshalling return", var38);
            }
         case 2:
            var47 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var47, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 3:
            DomainManager[] var51 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var51, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var36) {
               throw new MarshalException("error marshalling return", var36);
            }
         case 4:
            var47 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var47, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var35) {
               throw new MarshalException("error marshalling return", var35);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var48 = var6.readInt();
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            }

            Policy var49 = ((org.omg.CORBA.Object)var4)._get_policy(var48);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var49, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var48 = var7.readInt();
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            int var52 = ((org.omg.CORBA.Object)var4)._hash(var48);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var52);
               break;
            } catch (IOException var31) {
               throw new MarshalException("error marshalling return", var31);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var45 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            } catch (ClassNotFoundException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            }

            boolean var55 = ((org.omg.CORBA.Object)var4)._is_a(var45);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var55);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var47 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            boolean var58 = ((org.omg.CORBA.Object)var4)._is_equivalent(var47);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var58);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 9:
            var5 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var5);
               break;
            } catch (IOException var24) {
               throw new MarshalException("error marshalling return", var24);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var45 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            var61 = ((org.omg.CORBA.Object)var4)._request(var45);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var61, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var21) {
               throw new MarshalException("error marshalling return", var21);
            }
         case 12:
            Policy[] var44;
            SetOverrideType var60;
            try {
               MsgInput var14 = var2.getMsgInput();
               var44 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var60 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            org.omg.CORBA.Object var15 = ((org.omg.CORBA.Object)var4)._set_policy_override(var44, var60);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var15, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var18) {
               throw new MarshalException("error marshalling return", var18);
            }
         case 13:
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = var13.readBoolean();
            } catch (IOException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            }

            ((TerminatorOperations)var4).commit(var5);
            this.associateResponseData(var2, var3);
            break;
         case 14:
            ((TerminatorOperations)var4).rollback();
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
            ((TerminatorOperations)var3).commit((Boolean)var2[0]);
            return null;
         case 14:
            ((TerminatorOperations)var3).rollback();
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
