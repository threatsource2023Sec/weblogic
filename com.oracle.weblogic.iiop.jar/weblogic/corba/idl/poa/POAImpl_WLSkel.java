package weblogic.corba.idl.poa;

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
import org.omg.PortableInterceptor.PolicyFactoryOperations;
import org.omg.PortableServer.AdapterActivator;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAOperations;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyValue;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class POAImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$B;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$DomainManager;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$Policy;
   // $FF: synthetic field
   private static Class array$Lorg$omg$PortableServer$POA;
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
   private static Class class$org$omg$PortableServer$AdapterActivator;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$IdAssignmentPolicy;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$IdAssignmentPolicyValue;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$IdUniquenessPolicy;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$IdUniquenessPolicyValue;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$ImplicitActivationPolicy;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$ImplicitActivationPolicyValue;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$LifespanPolicy;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$LifespanPolicyValue;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$POA;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$POAManager;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$RequestProcessingPolicy;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$RequestProcessingPolicyValue;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$Servant;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$ServantManager;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$ServantRetentionPolicy;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$ServantRetentionPolicyValue;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$ThreadPolicy;
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$ThreadPolicyValue;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var142;
      AdapterActivator var144;
      String var145;
      ServantManager var147;
      Servant var148;
      NVList var150;
      org.omg.CORBA.Object var151;
      NamedValue var153;
      byte[] var154;
      boolean var157;
      int var165;
      Request var167;
      Context var179;
      boolean var192;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var179 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var145 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var150 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var153 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var140) {
               throw new UnmarshalException("error unmarshalling arguments", var140);
            } catch (ClassNotFoundException var141) {
               throw new UnmarshalException("error unmarshalling arguments", var141);
            }

            Request var158 = ((org.omg.CORBA.Object)var4)._create_request(var179, var145, var150, var153);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var158, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var139) {
               throw new MarshalException("error marshalling return", var139);
            }
         case 1:
            ExceptionList var156;
            ContextList var160;
            try {
               MsgInput var12 = var2.getMsgInput();
               var179 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var145 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var150 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var153 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var156 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var160 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var137) {
               throw new UnmarshalException("error unmarshalling arguments", var137);
            } catch (ClassNotFoundException var138) {
               throw new UnmarshalException("error unmarshalling arguments", var138);
            }

            var167 = ((org.omg.CORBA.Object)var4)._create_request(var179, var145, var150, var153, var156, var160);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var167, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var136) {
               throw new MarshalException("error marshalling return", var136);
            }
         case 2:
            var151 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var151, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var135) {
               throw new MarshalException("error marshalling return", var135);
            }
         case 3:
            DomainManager[] var178 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var178, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var134) {
               throw new MarshalException("error marshalling return", var134);
            }
         case 4:
            var151 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var151, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var133) {
               throw new MarshalException("error marshalling return", var133);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var165 = var6.readInt();
            } catch (IOException var132) {
               throw new UnmarshalException("error unmarshalling arguments", var132);
            }

            Policy var149 = ((org.omg.CORBA.Object)var4)._get_policy(var165);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var149, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var131) {
               throw new MarshalException("error marshalling return", var131);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var165 = var7.readInt();
            } catch (IOException var130) {
               throw new UnmarshalException("error unmarshalling arguments", var130);
            }

            int var152 = ((org.omg.CORBA.Object)var4)._hash(var165);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var152);
               break;
            } catch (IOException var129) {
               throw new MarshalException("error marshalling return", var129);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var142 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var127) {
               throw new UnmarshalException("error unmarshalling arguments", var127);
            } catch (ClassNotFoundException var128) {
               throw new UnmarshalException("error unmarshalling arguments", var128);
            }

            boolean var155 = ((org.omg.CORBA.Object)var4)._is_a(var142);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var155);
               break;
            } catch (IOException var126) {
               throw new MarshalException("error marshalling return", var126);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var151 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var124) {
               throw new UnmarshalException("error unmarshalling arguments", var124);
            } catch (ClassNotFoundException var125) {
               throw new UnmarshalException("error unmarshalling arguments", var125);
            }

            boolean var159 = ((org.omg.CORBA.Object)var4)._is_equivalent(var151);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var159);
               break;
            } catch (IOException var123) {
               throw new MarshalException("error marshalling return", var123);
            }
         case 9:
            var157 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var157);
               break;
            } catch (IOException var122) {
               throw new MarshalException("error marshalling return", var122);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var142 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var120) {
               throw new UnmarshalException("error unmarshalling arguments", var120);
            } catch (ClassNotFoundException var121) {
               throw new UnmarshalException("error unmarshalling arguments", var121);
            }

            var167 = ((org.omg.CORBA.Object)var4)._request(var142);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var167, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var119) {
               throw new MarshalException("error marshalling return", var119);
            }
         case 12:
            SetOverrideType var164;
            Policy[] var173;
            try {
               MsgInput var14 = var2.getMsgInput();
               var173 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var164 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var117) {
               throw new UnmarshalException("error unmarshalling arguments", var117);
            } catch (ClassNotFoundException var118) {
               throw new UnmarshalException("error unmarshalling arguments", var118);
            }

            org.omg.CORBA.Object var175 = ((org.omg.CORBA.Object)var4)._set_policy_override(var173, var164);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var175, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var116) {
               throw new MarshalException("error marshalling return", var116);
            }
         case 13:
            try {
               MsgInput var13 = var2.getMsgInput();
               var148 = (Servant)var13.readObject(class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
            } catch (IOException var114) {
               throw new UnmarshalException("error unmarshalling arguments", var114);
            } catch (ClassNotFoundException var115) {
               throw new UnmarshalException("error unmarshalling arguments", var115);
            }

            byte[] var174 = ((POAOperations)var4).activate_object(var148);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var174, array$B == null ? (array$B = class$("[B")) : array$B);
               break;
            } catch (IOException var113) {
               throw new MarshalException("error marshalling return", var113);
            }
         case 14:
            Servant var172;
            try {
               MsgInput var16 = var2.getMsgInput();
               var154 = (byte[])var16.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
               var172 = (Servant)var16.readObject(class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
            } catch (IOException var111) {
               throw new UnmarshalException("error unmarshalling arguments", var111);
            } catch (ClassNotFoundException var112) {
               throw new UnmarshalException("error unmarshalling arguments", var112);
            }

            ((POAOperations)var4).activate_object_with_id(var154, var172);
            this.associateResponseData(var2, var3);
            break;
         case 15:
            POAManager var171;
            Policy[] var177;
            try {
               MsgInput var18 = var2.getMsgInput();
               var142 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var171 = (POAManager)var18.readObject(class$org$omg$PortableServer$POAManager == null ? (class$org$omg$PortableServer$POAManager = class$("org.omg.PortableServer.POAManager")) : class$org$omg$PortableServer$POAManager);
               var177 = (Policy[])var18.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
            } catch (IOException var109) {
               throw new UnmarshalException("error unmarshalling arguments", var109);
            } catch (ClassNotFoundException var110) {
               throw new UnmarshalException("error unmarshalling arguments", var110);
            }

            POA var181 = ((POAOperations)var4).create_POA(var142, var171, var177);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var181, class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA);
               break;
            } catch (IOException var108) {
               throw new MarshalException("error marshalling return", var108);
            }
         case 16:
            IdAssignmentPolicyValue var170;
            try {
               MsgInput var15 = var2.getMsgInput();
               var170 = (IdAssignmentPolicyValue)var15.readObject(class$org$omg$PortableServer$IdAssignmentPolicyValue == null ? (class$org$omg$PortableServer$IdAssignmentPolicyValue = class$("org.omg.PortableServer.IdAssignmentPolicyValue")) : class$org$omg$PortableServer$IdAssignmentPolicyValue);
            } catch (IOException var106) {
               throw new UnmarshalException("error unmarshalling arguments", var106);
            } catch (ClassNotFoundException var107) {
               throw new UnmarshalException("error unmarshalling arguments", var107);
            }

            IdAssignmentPolicy var176 = ((POAOperations)var4).create_id_assignment_policy(var170);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var176, class$org$omg$PortableServer$IdAssignmentPolicy == null ? (class$org$omg$PortableServer$IdAssignmentPolicy = class$("org.omg.PortableServer.IdAssignmentPolicy")) : class$org$omg$PortableServer$IdAssignmentPolicy);
               break;
            } catch (IOException var105) {
               throw new MarshalException("error marshalling return", var105);
            }
         case 17:
            IdUniquenessPolicyValue var169;
            try {
               MsgInput var17 = var2.getMsgInput();
               var169 = (IdUniquenessPolicyValue)var17.readObject(class$org$omg$PortableServer$IdUniquenessPolicyValue == null ? (class$org$omg$PortableServer$IdUniquenessPolicyValue = class$("org.omg.PortableServer.IdUniquenessPolicyValue")) : class$org$omg$PortableServer$IdUniquenessPolicyValue);
            } catch (IOException var103) {
               throw new UnmarshalException("error unmarshalling arguments", var103);
            } catch (ClassNotFoundException var104) {
               throw new UnmarshalException("error unmarshalling arguments", var104);
            }

            IdUniquenessPolicy var180 = ((POAOperations)var4).create_id_uniqueness_policy(var169);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var180, class$org$omg$PortableServer$IdUniquenessPolicy == null ? (class$org$omg$PortableServer$IdUniquenessPolicy = class$("org.omg.PortableServer.IdUniquenessPolicy")) : class$org$omg$PortableServer$IdUniquenessPolicy);
               break;
            } catch (IOException var102) {
               throw new MarshalException("error marshalling return", var102);
            }
         case 18:
            ImplicitActivationPolicyValue var168;
            try {
               MsgInput var19 = var2.getMsgInput();
               var168 = (ImplicitActivationPolicyValue)var19.readObject(class$org$omg$PortableServer$ImplicitActivationPolicyValue == null ? (class$org$omg$PortableServer$ImplicitActivationPolicyValue = class$("org.omg.PortableServer.ImplicitActivationPolicyValue")) : class$org$omg$PortableServer$ImplicitActivationPolicyValue);
            } catch (IOException var100) {
               throw new UnmarshalException("error unmarshalling arguments", var100);
            } catch (ClassNotFoundException var101) {
               throw new UnmarshalException("error unmarshalling arguments", var101);
            }

            ImplicitActivationPolicy var182 = ((POAOperations)var4).create_implicit_activation_policy(var168);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var182, class$org$omg$PortableServer$ImplicitActivationPolicy == null ? (class$org$omg$PortableServer$ImplicitActivationPolicy = class$("org.omg.PortableServer.ImplicitActivationPolicy")) : class$org$omg$PortableServer$ImplicitActivationPolicy);
               break;
            } catch (IOException var99) {
               throw new MarshalException("error marshalling return", var99);
            }
         case 19:
            LifespanPolicyValue var166;
            try {
               MsgInput var20 = var2.getMsgInput();
               var166 = (LifespanPolicyValue)var20.readObject(class$org$omg$PortableServer$LifespanPolicyValue == null ? (class$org$omg$PortableServer$LifespanPolicyValue = class$("org.omg.PortableServer.LifespanPolicyValue")) : class$org$omg$PortableServer$LifespanPolicyValue);
            } catch (IOException var97) {
               throw new UnmarshalException("error unmarshalling arguments", var97);
            } catch (ClassNotFoundException var98) {
               throw new UnmarshalException("error unmarshalling arguments", var98);
            }

            LifespanPolicy var184 = ((POAOperations)var4).create_lifespan_policy(var166);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var184, class$org$omg$PortableServer$LifespanPolicy == null ? (class$org$omg$PortableServer$LifespanPolicy = class$("org.omg.PortableServer.LifespanPolicy")) : class$org$omg$PortableServer$LifespanPolicy);
               break;
            } catch (IOException var96) {
               throw new MarshalException("error marshalling return", var96);
            }
         case 20:
            Any var183;
            try {
               MsgInput var22 = var2.getMsgInput();
               var165 = var22.readInt();
               var183 = (Any)var22.readObject(class$org$omg$CORBA$Any == null ? (class$org$omg$CORBA$Any = class$("org.omg.CORBA.Any")) : class$org$omg$CORBA$Any);
            } catch (IOException var94) {
               throw new UnmarshalException("error unmarshalling arguments", var94);
            } catch (ClassNotFoundException var95) {
               throw new UnmarshalException("error unmarshalling arguments", var95);
            }

            Policy var187 = ((PolicyFactoryOperations)var4).create_policy(var165, var183);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var187, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var93) {
               throw new MarshalException("error marshalling return", var93);
            }
         case 21:
            try {
               MsgInput var21 = var2.getMsgInput();
               var142 = (String)var21.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var91) {
               throw new UnmarshalException("error unmarshalling arguments", var91);
            } catch (ClassNotFoundException var92) {
               throw new UnmarshalException("error unmarshalling arguments", var92);
            }

            org.omg.CORBA.Object var186 = ((POAOperations)var4).create_reference(var142);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var186, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var90) {
               throw new MarshalException("error marshalling return", var90);
            }
         case 22:
            String var185;
            try {
               MsgInput var24 = var2.getMsgInput();
               var154 = (byte[])var24.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
               var185 = (String)var24.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var88) {
               throw new UnmarshalException("error unmarshalling arguments", var88);
            } catch (ClassNotFoundException var89) {
               throw new UnmarshalException("error unmarshalling arguments", var89);
            }

            org.omg.CORBA.Object var189 = ((POAOperations)var4).create_reference_with_id(var154, var185);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var189, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var87) {
               throw new MarshalException("error marshalling return", var87);
            }
         case 23:
            RequestProcessingPolicyValue var163;
            try {
               MsgInput var23 = var2.getMsgInput();
               var163 = (RequestProcessingPolicyValue)var23.readObject(class$org$omg$PortableServer$RequestProcessingPolicyValue == null ? (class$org$omg$PortableServer$RequestProcessingPolicyValue = class$("org.omg.PortableServer.RequestProcessingPolicyValue")) : class$org$omg$PortableServer$RequestProcessingPolicyValue);
            } catch (IOException var85) {
               throw new UnmarshalException("error unmarshalling arguments", var85);
            } catch (ClassNotFoundException var86) {
               throw new UnmarshalException("error unmarshalling arguments", var86);
            }

            RequestProcessingPolicy var188 = ((POAOperations)var4).create_request_processing_policy(var163);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var188, class$org$omg$PortableServer$RequestProcessingPolicy == null ? (class$org$omg$PortableServer$RequestProcessingPolicy = class$("org.omg.PortableServer.RequestProcessingPolicy")) : class$org$omg$PortableServer$RequestProcessingPolicy);
               break;
            } catch (IOException var84) {
               throw new MarshalException("error marshalling return", var84);
            }
         case 24:
            ServantRetentionPolicyValue var162;
            try {
               MsgInput var25 = var2.getMsgInput();
               var162 = (ServantRetentionPolicyValue)var25.readObject(class$org$omg$PortableServer$ServantRetentionPolicyValue == null ? (class$org$omg$PortableServer$ServantRetentionPolicyValue = class$("org.omg.PortableServer.ServantRetentionPolicyValue")) : class$org$omg$PortableServer$ServantRetentionPolicyValue);
            } catch (IOException var82) {
               throw new UnmarshalException("error unmarshalling arguments", var82);
            } catch (ClassNotFoundException var83) {
               throw new UnmarshalException("error unmarshalling arguments", var83);
            }

            ServantRetentionPolicy var190 = ((POAOperations)var4).create_servant_retention_policy(var162);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var190, class$org$omg$PortableServer$ServantRetentionPolicy == null ? (class$org$omg$PortableServer$ServantRetentionPolicy = class$("org.omg.PortableServer.ServantRetentionPolicy")) : class$org$omg$PortableServer$ServantRetentionPolicy);
               break;
            } catch (IOException var81) {
               throw new MarshalException("error marshalling return", var81);
            }
         case 25:
            ThreadPolicyValue var161;
            try {
               MsgInput var26 = var2.getMsgInput();
               var161 = (ThreadPolicyValue)var26.readObject(class$org$omg$PortableServer$ThreadPolicyValue == null ? (class$org$omg$PortableServer$ThreadPolicyValue = class$("org.omg.PortableServer.ThreadPolicyValue")) : class$org$omg$PortableServer$ThreadPolicyValue);
            } catch (IOException var79) {
               throw new UnmarshalException("error unmarshalling arguments", var79);
            } catch (ClassNotFoundException var80) {
               throw new UnmarshalException("error unmarshalling arguments", var80);
            }

            ThreadPolicy var191 = ((POAOperations)var4).create_thread_policy(var161);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var191, class$org$omg$PortableServer$ThreadPolicy == null ? (class$org$omg$PortableServer$ThreadPolicy = class$("org.omg.PortableServer.ThreadPolicy")) : class$org$omg$PortableServer$ThreadPolicy);
               break;
            } catch (IOException var78) {
               throw new MarshalException("error marshalling return", var78);
            }
         case 26:
            try {
               MsgInput var27 = var2.getMsgInput();
               var154 = (byte[])var27.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
            } catch (IOException var76) {
               throw new UnmarshalException("error unmarshalling arguments", var76);
            } catch (ClassNotFoundException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            }

            ((POAOperations)var4).deactivate_object(var154);
            this.associateResponseData(var2, var3);
            break;
         case 27:
            try {
               MsgInput var29 = var2.getMsgInput();
               var157 = var29.readBoolean();
               var192 = var29.readBoolean();
            } catch (IOException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            }

            ((POAOperations)var4).destroy(var157, var192);
            this.associateResponseData(var2, var3);
            break;
         case 28:
            try {
               MsgInput var30 = var2.getMsgInput();
               var142 = (String)var30.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var192 = var30.readBoolean();
            } catch (IOException var73) {
               throw new UnmarshalException("error unmarshalling arguments", var73);
            } catch (ClassNotFoundException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            }

            POA var194 = ((POAOperations)var4).find_POA(var142, var192);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var194, class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA);
               break;
            } catch (IOException var72) {
               throw new MarshalException("error marshalling return", var72);
            }
         case 29:
            var148 = ((POAOperations)var4).get_servant();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var148, class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
               break;
            } catch (IOException var71) {
               throw new MarshalException("error marshalling return", var71);
            }
         case 30:
            var147 = ((POAOperations)var4).get_servant_manager();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var147, class$org$omg$PortableServer$ServantManager == null ? (class$org$omg$PortableServer$ServantManager = class$("org.omg.PortableServer.ServantManager")) : class$org$omg$PortableServer$ServantManager);
               break;
            } catch (IOException var70) {
               throw new MarshalException("error marshalling return", var70);
            }
         case 31:
            var154 = ((POAOperations)var4).id();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var154, array$B == null ? (array$B = class$("[B")) : array$B);
               break;
            } catch (IOException var69) {
               throw new MarshalException("error marshalling return", var69);
            }
         case 32:
            try {
               MsgInput var28 = var2.getMsgInput();
               var154 = (byte[])var28.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
            } catch (IOException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            } catch (ClassNotFoundException var68) {
               throw new UnmarshalException("error unmarshalling arguments", var68);
            }

            org.omg.CORBA.Object var193 = ((POAOperations)var4).id_to_reference(var154);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var193, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var66) {
               throw new MarshalException("error marshalling return", var66);
            }
         case 33:
            try {
               MsgInput var31 = var2.getMsgInput();
               var154 = (byte[])var31.readObject(array$B == null ? (array$B = class$("[B")) : array$B);
            } catch (IOException var64) {
               throw new UnmarshalException("error unmarshalling arguments", var64);
            } catch (ClassNotFoundException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            }

            Servant var195 = ((POAOperations)var4).id_to_servant(var154);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var195, class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
               break;
            } catch (IOException var63) {
               throw new MarshalException("error marshalling return", var63);
            }
         case 34:
            try {
               MsgInput var32 = var2.getMsgInput();
               var151 = (org.omg.CORBA.Object)var32.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
            } catch (ClassNotFoundException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            }

            byte[] var196 = ((POAOperations)var4).reference_to_id(var151);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var196, array$B == null ? (array$B = class$("[B")) : array$B);
               break;
            } catch (IOException var60) {
               throw new MarshalException("error marshalling return", var60);
            }
         case 35:
            try {
               MsgInput var33 = var2.getMsgInput();
               var151 = (org.omg.CORBA.Object)var33.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var58) {
               throw new UnmarshalException("error unmarshalling arguments", var58);
            } catch (ClassNotFoundException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            }

            Servant var197 = ((POAOperations)var4).reference_to_servant(var151);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var197, class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
               break;
            } catch (IOException var57) {
               throw new MarshalException("error marshalling return", var57);
            }
         case 36:
            try {
               MsgInput var34 = var2.getMsgInput();
               var148 = (Servant)var34.readObject(class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
            } catch (IOException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            } catch (ClassNotFoundException var56) {
               throw new UnmarshalException("error unmarshalling arguments", var56);
            }

            byte[] var198 = ((POAOperations)var4).servant_to_id(var148);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var198, array$B == null ? (array$B = class$("[B")) : array$B);
               break;
            } catch (IOException var54) {
               throw new MarshalException("error marshalling return", var54);
            }
         case 37:
            try {
               MsgInput var35 = var2.getMsgInput();
               var148 = (Servant)var35.readObject(class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
            } catch (IOException var52) {
               throw new UnmarshalException("error unmarshalling arguments", var52);
            } catch (ClassNotFoundException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            }

            org.omg.CORBA.Object var199 = ((POAOperations)var4).servant_to_reference(var148);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var199, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var51) {
               throw new MarshalException("error marshalling return", var51);
            }
         case 38:
            try {
               MsgInput var36 = var2.getMsgInput();
               var148 = (Servant)var36.readObject(class$org$omg$PortableServer$Servant == null ? (class$org$omg$PortableServer$Servant = class$("org.omg.PortableServer.Servant")) : class$org$omg$PortableServer$Servant);
            } catch (IOException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            } catch (ClassNotFoundException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            }

            ((POAOperations)var4).set_servant(var148);
            this.associateResponseData(var2, var3);
            break;
         case 39:
            try {
               MsgInput var37 = var2.getMsgInput();
               var147 = (ServantManager)var37.readObject(class$org$omg$PortableServer$ServantManager == null ? (class$org$omg$PortableServer$ServantManager = class$("org.omg.PortableServer.ServantManager")) : class$org$omg$PortableServer$ServantManager);
            } catch (IOException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            } catch (ClassNotFoundException var48) {
               throw new UnmarshalException("error unmarshalling arguments", var48);
            }

            ((POAOperations)var4).set_servant_manager(var147);
            this.associateResponseData(var2, var3);
            break;
         case 40:
            POAManager var146 = ((POAOperations)var4).the_POAManager();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var146, class$org$omg$PortableServer$POAManager == null ? (class$org$omg$PortableServer$POAManager = class$("org.omg.PortableServer.POAManager")) : class$org$omg$PortableServer$POAManager);
               break;
            } catch (IOException var46) {
               throw new MarshalException("error marshalling return", var46);
            }
         case 41:
            var144 = ((POAOperations)var4).the_activator();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var144, class$org$omg$PortableServer$AdapterActivator == null ? (class$org$omg$PortableServer$AdapterActivator = class$("org.omg.PortableServer.AdapterActivator")) : class$org$omg$PortableServer$AdapterActivator);
               break;
            } catch (IOException var45) {
               throw new MarshalException("error marshalling return", var45);
            }
         case 42:
            try {
               MsgInput var38 = var2.getMsgInput();
               var144 = (AdapterActivator)var38.readObject(class$org$omg$PortableServer$AdapterActivator == null ? (class$org$omg$PortableServer$AdapterActivator = class$("org.omg.PortableServer.AdapterActivator")) : class$org$omg$PortableServer$AdapterActivator);
            } catch (IOException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            } catch (ClassNotFoundException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            }

            ((POAOperations)var4).the_activator(var144);
            this.associateResponseData(var2, var3);
            break;
         case 43:
            POA[] var143 = ((POAOperations)var4).the_children();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var143, array$Lorg$omg$PortableServer$POA == null ? (array$Lorg$omg$PortableServer$POA = class$("[Lorg.omg.PortableServer.POA;")) : array$Lorg$omg$PortableServer$POA);
               break;
            } catch (IOException var42) {
               throw new MarshalException("error marshalling return", var42);
            }
         case 44:
            var142 = ((POAOperations)var4).the_name();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var142, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var41) {
               throw new MarshalException("error marshalling return", var41);
            }
         case 45:
            POA var5 = ((POAOperations)var4).the_parent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA);
               break;
            } catch (IOException var40) {
               throw new MarshalException("error marshalling return", var40);
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
            return ((POAOperations)var3).activate_object((Servant)var2[0]);
         case 14:
            ((POAOperations)var3).activate_object_with_id((byte[])var2[0], (Servant)var2[1]);
            return null;
         case 15:
            return ((POAOperations)var3).create_POA((String)var2[0], (POAManager)var2[1], (Policy[])var2[2]);
         case 16:
            return ((POAOperations)var3).create_id_assignment_policy((IdAssignmentPolicyValue)var2[0]);
         case 17:
            return ((POAOperations)var3).create_id_uniqueness_policy((IdUniquenessPolicyValue)var2[0]);
         case 18:
            return ((POAOperations)var3).create_implicit_activation_policy((ImplicitActivationPolicyValue)var2[0]);
         case 19:
            return ((POAOperations)var3).create_lifespan_policy((LifespanPolicyValue)var2[0]);
         case 20:
            return ((PolicyFactoryOperations)var3).create_policy((Integer)var2[0], (Any)var2[1]);
         case 21:
            return ((POAOperations)var3).create_reference((String)var2[0]);
         case 22:
            return ((POAOperations)var3).create_reference_with_id((byte[])var2[0], (String)var2[1]);
         case 23:
            return ((POAOperations)var3).create_request_processing_policy((RequestProcessingPolicyValue)var2[0]);
         case 24:
            return ((POAOperations)var3).create_servant_retention_policy((ServantRetentionPolicyValue)var2[0]);
         case 25:
            return ((POAOperations)var3).create_thread_policy((ThreadPolicyValue)var2[0]);
         case 26:
            ((POAOperations)var3).deactivate_object((byte[])var2[0]);
            return null;
         case 27:
            ((POAOperations)var3).destroy((Boolean)var2[0], (Boolean)var2[1]);
            return null;
         case 28:
            return ((POAOperations)var3).find_POA((String)var2[0], (Boolean)var2[1]);
         case 29:
            return ((POAOperations)var3).get_servant();
         case 30:
            return ((POAOperations)var3).get_servant_manager();
         case 31:
            return ((POAOperations)var3).id();
         case 32:
            return ((POAOperations)var3).id_to_reference((byte[])var2[0]);
         case 33:
            return ((POAOperations)var3).id_to_servant((byte[])var2[0]);
         case 34:
            return ((POAOperations)var3).reference_to_id((org.omg.CORBA.Object)var2[0]);
         case 35:
            return ((POAOperations)var3).reference_to_servant((org.omg.CORBA.Object)var2[0]);
         case 36:
            return ((POAOperations)var3).servant_to_id((Servant)var2[0]);
         case 37:
            return ((POAOperations)var3).servant_to_reference((Servant)var2[0]);
         case 38:
            ((POAOperations)var3).set_servant((Servant)var2[0]);
            return null;
         case 39:
            ((POAOperations)var3).set_servant_manager((ServantManager)var2[0]);
            return null;
         case 40:
            return ((POAOperations)var3).the_POAManager();
         case 41:
            return ((POAOperations)var3).the_activator();
         case 42:
            ((POAOperations)var3).the_activator((AdapterActivator)var2[0]);
            return null;
         case 43:
            return ((POAOperations)var3).the_children();
         case 44:
            return ((POAOperations)var3).the_name();
         case 45:
            return ((POAOperations)var3).the_parent();
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
