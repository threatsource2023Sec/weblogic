package weblogic.corba.cos.naming;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtOperations;
import org.omg.CosNaming.NamingContextOperations;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class NamingContextImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$DomainManager;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$Policy;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CosNaming$NameComponent;
   // $FF: synthetic field
   private static Class array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$Any;
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
   private static Class class$org$omg$CosNaming$BindingIteratorHolder;
   // $FF: synthetic field
   private static Class class$org$omg$CosNaming$BindingListHolder;
   // $FF: synthetic field
   private static Class class$org$omg$CosNaming$NamingContext;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      NameComponent[] var5;
      String var99;
      String var100;
      WNameComponent[] var101;
      NVList var103;
      NamedValue var106;
      int var107;
      org.omg.CORBA.Object var115;
      Context var119;
      Request var122;
      NamingContext var124;
      String var134;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var119 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var100 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var103 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var106 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var97) {
               throw new UnmarshalException("error unmarshalling arguments", var97);
            } catch (ClassNotFoundException var98) {
               throw new UnmarshalException("error unmarshalling arguments", var98);
            }

            Request var111 = ((org.omg.CORBA.Object)var4)._create_request(var119, var100, var103, var106);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var111, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var96) {
               throw new MarshalException("error marshalling return", var96);
            }
         case 1:
            ExceptionList var109;
            ContextList var114;
            try {
               MsgInput var12 = var2.getMsgInput();
               var119 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var100 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var103 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var106 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var109 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var114 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var94) {
               throw new UnmarshalException("error unmarshalling arguments", var94);
            } catch (ClassNotFoundException var95) {
               throw new UnmarshalException("error unmarshalling arguments", var95);
            }

            var122 = ((org.omg.CORBA.Object)var4)._create_request(var119, var100, var103, var106, var109, var114);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var122, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var93) {
               throw new MarshalException("error marshalling return", var93);
            }
         case 2:
            var115 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var115, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var92) {
               throw new MarshalException("error marshalling return", var92);
            }
         case 3:
            DomainManager[] var116 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var116, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var91) {
               throw new MarshalException("error marshalling return", var91);
            }
         case 4:
            var115 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var115, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var90) {
               throw new MarshalException("error marshalling return", var90);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var107 = var6.readInt();
            } catch (IOException var89) {
               throw new UnmarshalException("error unmarshalling arguments", var89);
            }

            Policy var102 = ((org.omg.CORBA.Object)var4)._get_policy(var107);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var102, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var88) {
               throw new MarshalException("error marshalling return", var88);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var107 = var7.readInt();
            } catch (IOException var87) {
               throw new UnmarshalException("error unmarshalling arguments", var87);
            }

            int var104 = ((org.omg.CORBA.Object)var4)._hash(var107);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var104);
               break;
            } catch (IOException var86) {
               throw new MarshalException("error marshalling return", var86);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var99 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var84) {
               throw new UnmarshalException("error unmarshalling arguments", var84);
            } catch (ClassNotFoundException var85) {
               throw new UnmarshalException("error unmarshalling arguments", var85);
            }

            boolean var108 = ((org.omg.CORBA.Object)var4)._is_a(var99);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var108);
               break;
            } catch (IOException var83) {
               throw new MarshalException("error marshalling return", var83);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var115 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var81) {
               throw new UnmarshalException("error unmarshalling arguments", var81);
            } catch (ClassNotFoundException var82) {
               throw new UnmarshalException("error unmarshalling arguments", var82);
            }

            boolean var113 = ((org.omg.CORBA.Object)var4)._is_equivalent(var115);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var113);
               break;
            } catch (IOException var80) {
               throw new MarshalException("error marshalling return", var80);
            }
         case 9:
            boolean var112 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var112);
               break;
            } catch (IOException var79) {
               throw new MarshalException("error marshalling return", var79);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var99 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            } catch (ClassNotFoundException var78) {
               throw new UnmarshalException("error unmarshalling arguments", var78);
            }

            var122 = ((org.omg.CORBA.Object)var4)._request(var99);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var122, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var76) {
               throw new MarshalException("error marshalling return", var76);
            }
         case 12:
            Policy[] var110;
            SetOverrideType var121;
            try {
               MsgInput var14 = var2.getMsgInput();
               var110 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var121 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            } catch (ClassNotFoundException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            }

            org.omg.CORBA.Object var123 = ((org.omg.CORBA.Object)var4)._set_policy_override(var110, var121);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var123, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var73) {
               throw new MarshalException("error marshalling return", var73);
            }
         case 13:
            org.omg.CORBA.Object var120;
            try {
               MsgInput var15 = var2.getMsgInput();
               var5 = (NameComponent[])var15.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
               var120 = (org.omg.CORBA.Object)var15.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            } catch (ClassNotFoundException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            }

            ((NamingContextOperations)var4).bind(var5, var120);
            this.associateResponseData(var2, var3);
            break;
         case 14:
            Any var118;
            try {
               MsgInput var16 = var2.getMsgInput();
               var101 = (WNameComponent[])var16.readObject(array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent == null ? (array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent = class$("[Lweblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;")) : array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent);
               var118 = (Any)var16.readObject(class$org$omg$CORBA$Any == null ? (class$org$omg$CORBA$Any = class$("org.omg.CORBA.Any")) : class$org$omg$CORBA$Any);
            } catch (IOException var69) {
               throw new UnmarshalException("error unmarshalling arguments", var69);
            } catch (ClassNotFoundException var70) {
               throw new UnmarshalException("error unmarshalling arguments", var70);
            }

            ((NamingContextAnyOperations)var4).bind_any(var101, var118);
            this.associateResponseData(var2, var3);
            break;
         case 15:
            NamingContext var117;
            try {
               MsgInput var17 = var2.getMsgInput();
               var5 = (NameComponent[])var17.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
               var117 = (NamingContext)var17.readObject(class$org$omg$CosNaming$NamingContext == null ? (class$org$omg$CosNaming$NamingContext = class$("org.omg.CosNaming.NamingContext")) : class$org$omg$CosNaming$NamingContext);
            } catch (IOException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            } catch (ClassNotFoundException var68) {
               throw new UnmarshalException("error unmarshalling arguments", var68);
            }

            ((NamingContextOperations)var4).bind_context(var5, var117);
            this.associateResponseData(var2, var3);
            break;
         case 16:
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (NameComponent[])var13.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
            } catch (IOException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            } catch (ClassNotFoundException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            }

            var124 = ((NamingContextOperations)var4).bind_new_context(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var124, class$org$omg$CosNaming$NamingContext == null ? (class$org$omg$CosNaming$NamingContext = class$("org.omg.CosNaming.NamingContext")) : class$org$omg$CosNaming$NamingContext);
               break;
            } catch (IOException var64) {
               throw new MarshalException("error marshalling return", var64);
            }
         case 17:
            ((NamingContextOperations)var4).destroy();
            this.associateResponseData(var2, var3);
            break;
         case 18:
            BindingListHolder var127;
            BindingIteratorHolder var128;
            try {
               MsgInput var20 = var2.getMsgInput();
               var107 = var20.readInt();
               var127 = (BindingListHolder)var20.readObject(class$org$omg$CosNaming$BindingListHolder == null ? (class$org$omg$CosNaming$BindingListHolder = class$("org.omg.CosNaming.BindingListHolder")) : class$org$omg$CosNaming$BindingListHolder);
               var128 = (BindingIteratorHolder)var20.readObject(class$org$omg$CosNaming$BindingIteratorHolder == null ? (class$org$omg$CosNaming$BindingIteratorHolder = class$("org.omg.CosNaming.BindingIteratorHolder")) : class$org$omg$CosNaming$BindingIteratorHolder);
            } catch (IOException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            } catch (ClassNotFoundException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            }

            ((NamingContextOperations)var4).list(var107, var127, var128);
            this.associateResponseData(var2, var3);
            break;
         case 19:
            NamingContext var105 = ((NamingContextOperations)var4).new_context();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var105, class$org$omg$CosNaming$NamingContext == null ? (class$org$omg$CosNaming$NamingContext = class$("org.omg.CosNaming.NamingContext")) : class$org$omg$CosNaming$NamingContext);
               break;
            } catch (IOException var61) {
               throw new MarshalException("error marshalling return", var61);
            }
         case 20:
            org.omg.CORBA.Object var126;
            try {
               MsgInput var19 = var2.getMsgInput();
               var5 = (NameComponent[])var19.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
               var126 = (org.omg.CORBA.Object)var19.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            } catch (ClassNotFoundException var60) {
               throw new UnmarshalException("error unmarshalling arguments", var60);
            }

            ((NamingContextOperations)var4).rebind(var5, var126);
            this.associateResponseData(var2, var3);
            break;
         case 21:
            Any var125;
            try {
               MsgInput var21 = var2.getMsgInput();
               var101 = (WNameComponent[])var21.readObject(array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent == null ? (array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent = class$("[Lweblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;")) : array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent);
               var125 = (Any)var21.readObject(class$org$omg$CORBA$Any == null ? (class$org$omg$CORBA$Any = class$("org.omg.CORBA.Any")) : class$org$omg$CORBA$Any);
            } catch (IOException var57) {
               throw new UnmarshalException("error unmarshalling arguments", var57);
            } catch (ClassNotFoundException var58) {
               throw new UnmarshalException("error unmarshalling arguments", var58);
            }

            ((NamingContextAnyOperations)var4).rebind_any(var101, var125);
            this.associateResponseData(var2, var3);
            break;
         case 22:
            try {
               MsgInput var22 = var2.getMsgInput();
               var5 = (NameComponent[])var22.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
               var124 = (NamingContext)var22.readObject(class$org$omg$CosNaming$NamingContext == null ? (class$org$omg$CosNaming$NamingContext = class$("org.omg.CosNaming.NamingContext")) : class$org$omg$CosNaming$NamingContext);
            } catch (IOException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            } catch (ClassNotFoundException var56) {
               throw new UnmarshalException("error unmarshalling arguments", var56);
            }

            ((NamingContextOperations)var4).rebind_context(var5, var124);
            this.associateResponseData(var2, var3);
            break;
         case 23:
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (NameComponent[])var18.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
            } catch (IOException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            } catch (ClassNotFoundException var54) {
               throw new UnmarshalException("error unmarshalling arguments", var54);
            }

            org.omg.CORBA.Object var129 = ((NamingContextOperations)var4).resolve(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var129, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var52) {
               throw new MarshalException("error marshalling return", var52);
            }
         case 24:
            try {
               MsgInput var23 = var2.getMsgInput();
               var101 = (WNameComponent[])var23.readObject(array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent == null ? (array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent = class$("[Lweblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;")) : array$Lweblogic$corba$cos$naming$NamingContextAnyPackage$WNameComponent);
            } catch (IOException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            } catch (ClassNotFoundException var51) {
               throw new UnmarshalException("error unmarshalling arguments", var51);
            }

            Any var130 = ((NamingContextAnyOperations)var4).resolve_any(var101);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var130, class$org$omg$CORBA$Any == null ? (class$org$omg$CORBA$Any = class$("org.omg.CORBA.Any")) : class$org$omg$CORBA$Any);
               break;
            } catch (IOException var49) {
               throw new MarshalException("error marshalling return", var49);
            }
         case 25:
            try {
               MsgInput var24 = var2.getMsgInput();
               var99 = (String)var24.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            } catch (ClassNotFoundException var48) {
               throw new UnmarshalException("error unmarshalling arguments", var48);
            }

            org.omg.CORBA.Object var131 = ((NamingContextExtOperations)var4).resolve_str(var99);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var131, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var46) {
               throw new MarshalException("error marshalling return", var46);
            }
         case 26:
            try {
               MsgInput var25 = var2.getMsgInput();
               var99 = (String)var25.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            } catch (ClassNotFoundException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            }

            Any var132 = ((NamingContextAnyOperations)var4).resolve_str_any(var99);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var132, class$org$omg$CORBA$Any == null ? (class$org$omg$CORBA$Any = class$("org.omg.CORBA.Any")) : class$org$omg$CORBA$Any);
               break;
            } catch (IOException var43) {
               throw new MarshalException("error marshalling return", var43);
            }
         case 27:
            try {
               MsgInput var26 = var2.getMsgInput();
               var99 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            } catch (ClassNotFoundException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            }

            NameComponent[] var133 = ((NamingContextExtOperations)var4).to_name(var99);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var133, array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
               break;
            } catch (IOException var40) {
               throw new MarshalException("error marshalling return", var40);
            }
         case 28:
            try {
               MsgInput var27 = var2.getMsgInput();
               var5 = (NameComponent[])var27.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            } catch (ClassNotFoundException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            var134 = ((NamingContextExtOperations)var4).to_string(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var134, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 29:
            try {
               MsgInput var29 = var2.getMsgInput();
               var99 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var134 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            } catch (ClassNotFoundException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            }

            String var30 = ((NamingContextExtOperations)var4).to_url(var99, var134);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var30, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var34) {
               throw new MarshalException("error marshalling return", var34);
            }
         case 30:
            try {
               MsgInput var28 = var2.getMsgInput();
               var5 = (NameComponent[])var28.readObject(array$Lorg$omg$CosNaming$NameComponent == null ? (array$Lorg$omg$CosNaming$NameComponent = class$("[Lorg.omg.CosNaming.NameComponent;")) : array$Lorg$omg$CosNaming$NameComponent);
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            } catch (ClassNotFoundException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            }

            ((NamingContextOperations)var4).unbind(var5);
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
            ((NamingContextOperations)var3).bind((NameComponent[])var2[0], (org.omg.CORBA.Object)var2[1]);
            return null;
         case 14:
            ((NamingContextAnyOperations)var3).bind_any((WNameComponent[])var2[0], (Any)var2[1]);
            return null;
         case 15:
            ((NamingContextOperations)var3).bind_context((NameComponent[])var2[0], (NamingContext)var2[1]);
            return null;
         case 16:
            return ((NamingContextOperations)var3).bind_new_context((NameComponent[])var2[0]);
         case 17:
            ((NamingContextOperations)var3).destroy();
            return null;
         case 18:
            ((NamingContextOperations)var3).list((Integer)var2[0], (BindingListHolder)var2[1], (BindingIteratorHolder)var2[2]);
            return null;
         case 19:
            return ((NamingContextOperations)var3).new_context();
         case 20:
            ((NamingContextOperations)var3).rebind((NameComponent[])var2[0], (org.omg.CORBA.Object)var2[1]);
            return null;
         case 21:
            ((NamingContextAnyOperations)var3).rebind_any((WNameComponent[])var2[0], (Any)var2[1]);
            return null;
         case 22:
            ((NamingContextOperations)var3).rebind_context((NameComponent[])var2[0], (NamingContext)var2[1]);
            return null;
         case 23:
            return ((NamingContextOperations)var3).resolve((NameComponent[])var2[0]);
         case 24:
            return ((NamingContextAnyOperations)var3).resolve_any((WNameComponent[])var2[0]);
         case 25:
            return ((NamingContextExtOperations)var3).resolve_str((String)var2[0]);
         case 26:
            return ((NamingContextAnyOperations)var3).resolve_str_any((String)var2[0]);
         case 27:
            return ((NamingContextExtOperations)var3).to_name((String)var2[0]);
         case 28:
            return ((NamingContextExtOperations)var3).to_string((NameComponent[])var2[0]);
         case 29:
            return ((NamingContextExtOperations)var3).to_url((String)var2[0], (String)var2[1]);
         case 30:
            ((NamingContextOperations)var3).unbind((NameComponent[])var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
