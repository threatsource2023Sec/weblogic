package weblogic.corba.cos.codebase;

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
import org.omg.CORBA.Repository;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.SendingContext.CodeBaseOperations;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class CodeBaseImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$DomainManager;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$Policy;
   // $FF: synthetic field
   private static Class array$Lorg$omg$CORBA$ValueDefPackage$FullValueDescription;
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
   private static Class class$org$omg$CORBA$Repository;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$Request;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$SetOverrideType;
   // $FF: synthetic field
   private static Class class$org$omg$CORBA$ValueDefPackage$FullValueDescription;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String[] var5;
      String var67;
      String var68;
      NVList var72;
      org.omg.CORBA.Object var74;
      NamedValue var76;
      int var77;
      Context var80;
      Request var86;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var80 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var68 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var72 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var76 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            } catch (ClassNotFoundException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            }

            Request var82 = ((org.omg.CORBA.Object)var4)._create_request(var80, var68, var72, var76);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var82, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var64) {
               throw new MarshalException("error marshalling return", var64);
            }
         case 1:
            ExceptionList var81;
            ContextList var84;
            try {
               MsgInput var12 = var2.getMsgInput();
               var80 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var68 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var72 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var76 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var81 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var84 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            } catch (ClassNotFoundException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            }

            var86 = ((org.omg.CORBA.Object)var4)._create_request(var80, var68, var72, var76, var81, var84);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var86, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var61) {
               throw new MarshalException("error marshalling return", var61);
            }
         case 2:
            var74 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var74, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var60) {
               throw new MarshalException("error marshalling return", var60);
            }
         case 3:
            DomainManager[] var78 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var78, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var59) {
               throw new MarshalException("error marshalling return", var59);
            }
         case 4:
            var74 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var74, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var58) {
               throw new MarshalException("error marshalling return", var58);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var77 = var6.readInt();
            } catch (IOException var57) {
               throw new UnmarshalException("error unmarshalling arguments", var57);
            }

            Policy var71 = ((org.omg.CORBA.Object)var4)._get_policy(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var71, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var56) {
               throw new MarshalException("error marshalling return", var56);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var77 = var7.readInt();
            } catch (IOException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            }

            int var75 = ((org.omg.CORBA.Object)var4)._hash(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var75);
               break;
            } catch (IOException var54) {
               throw new MarshalException("error marshalling return", var54);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var67 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var52) {
               throw new UnmarshalException("error unmarshalling arguments", var52);
            } catch (ClassNotFoundException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            }

            boolean var79 = ((org.omg.CORBA.Object)var4)._is_a(var67);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var79);
               break;
            } catch (IOException var51) {
               throw new MarshalException("error marshalling return", var51);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var74 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            } catch (ClassNotFoundException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            }

            boolean var83 = ((org.omg.CORBA.Object)var4)._is_equivalent(var74);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var83);
               break;
            } catch (IOException var48) {
               throw new MarshalException("error marshalling return", var48);
            }
         case 9:
            boolean var73 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var73);
               break;
            } catch (IOException var47) {
               throw new MarshalException("error marshalling return", var47);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var67 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            } catch (ClassNotFoundException var46) {
               throw new UnmarshalException("error unmarshalling arguments", var46);
            }

            var86 = ((org.omg.CORBA.Object)var4)._request(var67);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var86, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var44) {
               throw new MarshalException("error marshalling return", var44);
            }
         case 12:
            Policy[] var70;
            SetOverrideType var85;
            try {
               MsgInput var14 = var2.getMsgInput();
               var70 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var85 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            } catch (ClassNotFoundException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            }

            org.omg.CORBA.Object var88 = ((org.omg.CORBA.Object)var4)._set_policy_override(var70, var85);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var88, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var41) {
               throw new MarshalException("error marshalling return", var41);
            }
         case 13:
            try {
               MsgInput var13 = var2.getMsgInput();
               var67 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            } catch (ClassNotFoundException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            }

            String[] var87 = ((CodeBaseOperations)var4).bases(var67);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var87, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               break;
            } catch (IOException var38) {
               throw new MarshalException("error marshalling return", var38);
            }
         case 14:
            Repository var69 = ((CodeBaseOperations)var4).get_ir();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var69, class$org$omg$CORBA$Repository == null ? (class$org$omg$CORBA$Repository = class$("org.omg.CORBA.Repository")) : class$org$omg$CORBA$Repository);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 15:
            try {
               MsgInput var15 = var2.getMsgInput();
               var67 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            } catch (ClassNotFoundException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            }

            String var89 = ((CodeBaseOperations)var4).implementation(var67);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var89, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var34) {
               throw new MarshalException("error marshalling return", var34);
            }
         case 16:
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = (String[])var16.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            } catch (ClassNotFoundException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            }

            String[] var90 = ((CodeBaseOperations)var4).implementations(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var90, array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
               break;
            } catch (IOException var31) {
               throw new MarshalException("error marshalling return", var31);
            }
         case 17:
            try {
               MsgInput var17 = var2.getMsgInput();
               var67 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var29) {
               throw new UnmarshalException("error unmarshalling arguments", var29);
            } catch (ClassNotFoundException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            }

            String var91 = ((CodeBaseOperations)var4).implementationx(var67);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var91, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 18:
            try {
               MsgInput var18 = var2.getMsgInput();
               var67 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            FullValueDescription var92 = ((CodeBaseOperations)var4).meta(var67);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var92, class$org$omg$CORBA$ValueDefPackage$FullValueDescription == null ? (class$org$omg$CORBA$ValueDefPackage$FullValueDescription = class$("org.omg.CORBA.ValueDefPackage.FullValueDescription")) : class$org$omg$CORBA$ValueDefPackage$FullValueDescription);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 19:
            try {
               MsgInput var19 = var2.getMsgInput();
               var5 = (String[])var19.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            } catch (ClassNotFoundException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            FullValueDescription[] var20 = ((CodeBaseOperations)var4).metas(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var20, array$Lorg$omg$CORBA$ValueDefPackage$FullValueDescription == null ? (array$Lorg$omg$CORBA$ValueDefPackage$FullValueDescription = class$("[Lorg.omg.CORBA.ValueDefPackage.FullValueDescription;")) : array$Lorg$omg$CORBA$ValueDefPackage$FullValueDescription);
               break;
            } catch (IOException var22) {
               throw new MarshalException("error marshalling return", var22);
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
            return ((CodeBaseOperations)var3).bases((String)var2[0]);
         case 14:
            return ((CodeBaseOperations)var3).get_ir();
         case 15:
            return ((CodeBaseOperations)var3).implementation((String)var2[0]);
         case 16:
            return ((CodeBaseOperations)var3).implementations((String[])var2[0]);
         case 17:
            return ((CodeBaseOperations)var3).implementationx((String)var2[0]);
         case 18:
            return ((CodeBaseOperations)var3).meta((String)var2[0]);
         case 19:
            return ((CodeBaseOperations)var3).metas((String[])var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
