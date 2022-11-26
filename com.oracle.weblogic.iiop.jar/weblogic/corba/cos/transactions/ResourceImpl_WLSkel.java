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
import org.omg.CosTransactions.ResourceOperations;
import org.omg.CosTransactions.Vote;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ResourceImpl_WLSkel extends Skeleton {
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
   private static Class class$org$omg$CosTransactions$Vote;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var45;
      String var47;
      org.omg.CORBA.Object var48;
      int var49;
      NVList var51;
      Context var54;
      NamedValue var55;
      Request var61;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var54 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var47 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var51 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var55 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            } catch (ClassNotFoundException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            }

            Request var58 = ((org.omg.CORBA.Object)var4)._create_request(var54, var47, var51, var55);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var58, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var41) {
               throw new MarshalException("error marshalling return", var41);
            }
         case 1:
            ExceptionList var57;
            ContextList var60;
            try {
               MsgInput var12 = var2.getMsgInput();
               var54 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var47 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var51 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var55 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var57 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var60 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            } catch (ClassNotFoundException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            }

            var61 = ((org.omg.CORBA.Object)var4)._create_request(var54, var47, var51, var55, var57, var60);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var61, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var38) {
               throw new MarshalException("error marshalling return", var38);
            }
         case 2:
            var48 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var48, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 3:
            DomainManager[] var52 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var52, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var36) {
               throw new MarshalException("error marshalling return", var36);
            }
         case 4:
            var48 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var48, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var35) {
               throw new MarshalException("error marshalling return", var35);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var49 = var6.readInt();
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            }

            Policy var50 = ((org.omg.CORBA.Object)var4)._get_policy(var49);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var50, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var49 = var7.readInt();
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            int var53 = ((org.omg.CORBA.Object)var4)._hash(var49);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var53);
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

            boolean var56 = ((org.omg.CORBA.Object)var4)._is_a(var45);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var56);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var48 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            boolean var59 = ((org.omg.CORBA.Object)var4)._is_equivalent(var48);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var59);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 9:
            boolean var46 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var46);
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
            SetOverrideType var13;
            Policy[] var44;
            try {
               MsgInput var14 = var2.getMsgInput();
               var44 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var13 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            org.omg.CORBA.Object var15 = ((org.omg.CORBA.Object)var4)._set_policy_override(var44, var13);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var15, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var18) {
               throw new MarshalException("error marshalling return", var18);
            }
         case 13:
            ((ResourceOperations)var4).commit();
            this.associateResponseData(var2, var3);
            break;
         case 14:
            ((ResourceOperations)var4).commit_one_phase();
            this.associateResponseData(var2, var3);
            break;
         case 15:
            ((ResourceOperations)var4).forget();
            this.associateResponseData(var2, var3);
            break;
         case 16:
            Vote var5 = ((ResourceOperations)var4).prepare();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$org$omg$CosTransactions$Vote == null ? (class$org$omg$CosTransactions$Vote = class$("org.omg.CosTransactions.Vote")) : class$org$omg$CosTransactions$Vote);
               break;
            } catch (IOException var17) {
               throw new MarshalException("error marshalling return", var17);
            }
         case 17:
            ((ResourceOperations)var4).rollback();
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
            ((ResourceOperations)var3).commit();
            return null;
         case 14:
            ((ResourceOperations)var3).commit_one_phase();
            return null;
         case 15:
            ((ResourceOperations)var3).forget();
            return null;
         case 16:
            return ((ResourceOperations)var3).prepare();
         case 17:
            ((ResourceOperations)var3).rollback();
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
