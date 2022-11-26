package weblogic.corba.cos.naming;

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
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingIteratorOperations;
import org.omg.CosNaming.BindingListHolder;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class BindingIteratorImpl_WLSkel extends Skeleton {
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
   private static Class class$org$omg$CosNaming$BindingHolder;
   // $FF: synthetic field
   private static Class class$org$omg$CosNaming$BindingListHolder;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      boolean var16;
      int var50;
      String var52;
      String var54;
      org.omg.CORBA.Object var55;
      NVList var57;
      NamedValue var60;
      Context var61;
      Request var69;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var61 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var54 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var57 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var60 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var48) {
               throw new UnmarshalException("error unmarshalling arguments", var48);
            } catch (ClassNotFoundException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            }

            Request var64 = ((org.omg.CORBA.Object)var4)._create_request(var61, var54, var57, var60);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var64, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var47) {
               throw new MarshalException("error marshalling return", var47);
            }
         case 1:
            ExceptionList var63;
            ContextList var66;
            try {
               MsgInput var12 = var2.getMsgInput();
               var61 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var54 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var57 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var60 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var63 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var66 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            } catch (ClassNotFoundException var46) {
               throw new UnmarshalException("error unmarshalling arguments", var46);
            }

            var69 = ((org.omg.CORBA.Object)var4)._create_request(var61, var54, var57, var60, var63, var66);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var69, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var44) {
               throw new MarshalException("error marshalling return", var44);
            }
         case 2:
            var55 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var55, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var43) {
               throw new MarshalException("error marshalling return", var43);
            }
         case 3:
            DomainManager[] var58 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var58, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var42) {
               throw new MarshalException("error marshalling return", var42);
            }
         case 4:
            var55 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var55, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var41) {
               throw new MarshalException("error marshalling return", var41);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var50 = var6.readInt();
            } catch (IOException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            }

            Policy var56 = ((org.omg.CORBA.Object)var4)._get_policy(var50);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var56, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var39) {
               throw new MarshalException("error marshalling return", var39);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var50 = var7.readInt();
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            }

            int var59 = ((org.omg.CORBA.Object)var4)._hash(var50);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var59);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var52 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            } catch (ClassNotFoundException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            }

            boolean var62 = ((org.omg.CORBA.Object)var4)._is_a(var52);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var62);
               break;
            } catch (IOException var34) {
               throw new MarshalException("error marshalling return", var34);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var55 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            } catch (ClassNotFoundException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            }

            boolean var65 = ((org.omg.CORBA.Object)var4)._is_equivalent(var55);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var65);
               break;
            } catch (IOException var31) {
               throw new MarshalException("error marshalling return", var31);
            }
         case 9:
            boolean var53 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var53);
               break;
            } catch (IOException var30) {
               throw new MarshalException("error marshalling return", var30);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var52 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            } catch (ClassNotFoundException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            }

            var69 = ((org.omg.CORBA.Object)var4)._request(var52);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var69, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var27) {
               throw new MarshalException("error marshalling return", var27);
            }
         case 12:
            Policy[] var51;
            SetOverrideType var68;
            try {
               MsgInput var14 = var2.getMsgInput();
               var51 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var68 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            } catch (ClassNotFoundException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            }

            org.omg.CORBA.Object var70 = ((org.omg.CORBA.Object)var4)._set_policy_override(var51, var68);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var70, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var24) {
               throw new MarshalException("error marshalling return", var24);
            }
         case 13:
            ((BindingIteratorOperations)var4).destroy();
            this.associateResponseData(var2, var3);
            break;
         case 14:
            BindingListHolder var67;
            try {
               MsgInput var15 = var2.getMsgInput();
               var50 = var15.readInt();
               var67 = (BindingListHolder)var15.readObject(class$org$omg$CosNaming$BindingListHolder == null ? (class$org$omg$CosNaming$BindingListHolder = class$("org.omg.CosNaming.BindingListHolder")) : class$org$omg$CosNaming$BindingListHolder);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            var16 = ((BindingIteratorOperations)var4).next_n(var50, var67);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var16);
               break;
            } catch (IOException var21) {
               throw new MarshalException("error marshalling return", var21);
            }
         case 15:
            BindingHolder var5;
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (BindingHolder)var13.readObject(class$org$omg$CosNaming$BindingHolder == null ? (class$org$omg$CosNaming$BindingHolder = class$("org.omg.CosNaming.BindingHolder")) : class$org$omg$CosNaming$BindingHolder);
            } catch (IOException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            } catch (ClassNotFoundException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            }

            var16 = ((BindingIteratorOperations)var4).next_one(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var16);
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
            ((BindingIteratorOperations)var3).destroy();
            return null;
         case 14:
            return new Boolean(((BindingIteratorOperations)var3).next_n((Integer)var2[0], (BindingListHolder)var2[1]));
         case 15:
            return new Boolean(((BindingIteratorOperations)var3).next_one((BindingHolder)var2[0]));
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
