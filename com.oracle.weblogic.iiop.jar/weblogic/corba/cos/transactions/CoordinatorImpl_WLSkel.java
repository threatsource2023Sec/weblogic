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
import org.omg.CosTransactions.Coordinator;
import org.omg.CosTransactions.CoordinatorOperations;
import org.omg.CosTransactions.PropagationContext;
import org.omg.CosTransactions.RecoveryCoordinator;
import org.omg.CosTransactions.Resource;
import org.omg.CosTransactions.Status;
import org.omg.CosTransactions.SubtransactionAwareResource;
import org.omg.CosTransactions.Synchronization;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class CoordinatorImpl_WLSkel extends Skeleton {
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
   private static Class class$org$omg$CosTransactions$Coordinator;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$PropagationContext;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$RecoveryCoordinator;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$Resource;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$Status;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$SubtransactionAwareResource;
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$Synchronization;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      boolean var78;
      String var79;
      Coordinator var80;
      int var82;
      NVList var83;
      String var86;
      NamedValue var87;
      Status var88;
      org.omg.CORBA.Object var94;
      Context var99;
      Request var100;
      switch (var1) {
         case 0:
            try {
               MsgInput var9 = var2.getMsgInput();
               var99 = (Context)var9.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var79 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var83 = (NVList)var9.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var87 = (NamedValue)var9.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
            } catch (IOException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            } catch (ClassNotFoundException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            }

            Request var93 = ((org.omg.CORBA.Object)var4)._create_request(var99, var79, var83, var87);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var93, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var73) {
               throw new MarshalException("error marshalling return", var73);
            }
         case 1:
            ExceptionList var92;
            ContextList var96;
            try {
               MsgInput var12 = var2.getMsgInput();
               var99 = (Context)var12.readObject(class$org$omg$CORBA$Context == null ? (class$org$omg$CORBA$Context = class$("org.omg.CORBA.Context")) : class$org$omg$CORBA$Context);
               var79 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var83 = (NVList)var12.readObject(class$org$omg$CORBA$NVList == null ? (class$org$omg$CORBA$NVList = class$("org.omg.CORBA.NVList")) : class$org$omg$CORBA$NVList);
               var87 = (NamedValue)var12.readObject(class$org$omg$CORBA$NamedValue == null ? (class$org$omg$CORBA$NamedValue = class$("org.omg.CORBA.NamedValue")) : class$org$omg$CORBA$NamedValue);
               var92 = (ExceptionList)var12.readObject(class$org$omg$CORBA$ExceptionList == null ? (class$org$omg$CORBA$ExceptionList = class$("org.omg.CORBA.ExceptionList")) : class$org$omg$CORBA$ExceptionList);
               var96 = (ContextList)var12.readObject(class$org$omg$CORBA$ContextList == null ? (class$org$omg$CORBA$ContextList = class$("org.omg.CORBA.ContextList")) : class$org$omg$CORBA$ContextList);
            } catch (IOException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            } catch (ClassNotFoundException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            }

            var100 = ((org.omg.CORBA.Object)var4)._create_request(var99, var79, var83, var87, var92, var96);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var100, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var70) {
               throw new MarshalException("error marshalling return", var70);
            }
         case 2:
            var94 = ((org.omg.CORBA.Object)var4)._duplicate();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var94, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var69) {
               throw new MarshalException("error marshalling return", var69);
            }
         case 3:
            DomainManager[] var97 = ((org.omg.CORBA.Object)var4)._get_domain_managers();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var97, array$Lorg$omg$CORBA$DomainManager == null ? (array$Lorg$omg$CORBA$DomainManager = class$("[Lorg.omg.CORBA.DomainManager;")) : array$Lorg$omg$CORBA$DomainManager);
               break;
            } catch (IOException var68) {
               throw new MarshalException("error marshalling return", var68);
            }
         case 4:
            var94 = ((org.omg.CORBA.Object)var4)._get_interface_def();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var94, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var67) {
               throw new MarshalException("error marshalling return", var67);
            }
         case 5:
            try {
               MsgInput var6 = var2.getMsgInput();
               var82 = var6.readInt();
            } catch (IOException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            }

            Policy var81 = ((org.omg.CORBA.Object)var4)._get_policy(var82);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var81, class$org$omg$CORBA$Policy == null ? (class$org$omg$CORBA$Policy = class$("org.omg.CORBA.Policy")) : class$org$omg$CORBA$Policy);
               break;
            } catch (IOException var65) {
               throw new MarshalException("error marshalling return", var65);
            }
         case 6:
            try {
               MsgInput var7 = var2.getMsgInput();
               var82 = var7.readInt();
            } catch (IOException var64) {
               throw new UnmarshalException("error unmarshalling arguments", var64);
            }

            int var85 = ((org.omg.CORBA.Object)var4)._hash(var82);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var85);
               break;
            } catch (IOException var63) {
               throw new MarshalException("error marshalling return", var63);
            }
         case 7:
            try {
               MsgInput var8 = var2.getMsgInput();
               var86 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
            } catch (ClassNotFoundException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            }

            boolean var91 = ((org.omg.CORBA.Object)var4)._is_a(var86);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var91);
               break;
            } catch (IOException var60) {
               throw new MarshalException("error marshalling return", var60);
            }
         case 8:
            try {
               MsgInput var10 = var2.getMsgInput();
               var94 = (org.omg.CORBA.Object)var10.readObject(class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
            } catch (IOException var58) {
               throw new UnmarshalException("error unmarshalling arguments", var58);
            } catch (ClassNotFoundException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            }

            boolean var95 = ((org.omg.CORBA.Object)var4)._is_equivalent(var94);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var95);
               break;
            } catch (IOException var57) {
               throw new MarshalException("error marshalling return", var57);
            }
         case 9:
            var78 = ((org.omg.CORBA.Object)var4)._non_existent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var78);
               break;
            } catch (IOException var56) {
               throw new MarshalException("error marshalling return", var56);
            }
         case 10:
            ((org.omg.CORBA.Object)var4)._release();
            this.associateResponseData(var2, var3);
            break;
         case 11:
            try {
               MsgInput var11 = var2.getMsgInput();
               var86 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var54) {
               throw new UnmarshalException("error unmarshalling arguments", var54);
            } catch (ClassNotFoundException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            }

            var100 = ((org.omg.CORBA.Object)var4)._request(var86);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var100, class$org$omg$CORBA$Request == null ? (class$org$omg$CORBA$Request = class$("org.omg.CORBA.Request")) : class$org$omg$CORBA$Request);
               break;
            } catch (IOException var53) {
               throw new MarshalException("error marshalling return", var53);
            }
         case 12:
            Policy[] var90;
            SetOverrideType var98;
            try {
               MsgInput var14 = var2.getMsgInput();
               var90 = (Policy[])var14.readObject(array$Lorg$omg$CORBA$Policy == null ? (array$Lorg$omg$CORBA$Policy = class$("[Lorg.omg.CORBA.Policy;")) : array$Lorg$omg$CORBA$Policy);
               var98 = (SetOverrideType)var14.readObject(class$org$omg$CORBA$SetOverrideType == null ? (class$org$omg$CORBA$SetOverrideType = class$("org.omg.CORBA.SetOverrideType")) : class$org$omg$CORBA$SetOverrideType);
            } catch (IOException var51) {
               throw new UnmarshalException("error unmarshalling arguments", var51);
            } catch (ClassNotFoundException var52) {
               throw new UnmarshalException("error unmarshalling arguments", var52);
            }

            org.omg.CORBA.Object var102 = ((org.omg.CORBA.Object)var4)._set_policy_override(var90, var98);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var102, class$org$omg$CORBA$Object == null ? (class$org$omg$CORBA$Object = class$("org.omg.CORBA.Object")) : class$org$omg$CORBA$Object);
               break;
            } catch (IOException var50) {
               throw new MarshalException("error marshalling return", var50);
            }
         case 13:
            Control var89 = ((CoordinatorOperations)var4).create_subtransaction();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var89, class$org$omg$CosTransactions$Control == null ? (class$org$omg$CosTransactions$Control = class$("org.omg.CosTransactions.Control")) : class$org$omg$CosTransactions$Control);
               break;
            } catch (IOException var49) {
               throw new MarshalException("error marshalling return", var49);
            }
         case 14:
            var88 = ((CoordinatorOperations)var4).get_parent_status();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var88, class$org$omg$CosTransactions$Status == null ? (class$org$omg$CosTransactions$Status = class$("org.omg.CosTransactions.Status")) : class$org$omg$CosTransactions$Status);
               break;
            } catch (IOException var48) {
               throw new MarshalException("error marshalling return", var48);
            }
         case 15:
            var88 = ((CoordinatorOperations)var4).get_status();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var88, class$org$omg$CosTransactions$Status == null ? (class$org$omg$CosTransactions$Status = class$("org.omg.CosTransactions.Status")) : class$org$omg$CosTransactions$Status);
               break;
            } catch (IOException var47) {
               throw new MarshalException("error marshalling return", var47);
            }
         case 16:
            var88 = ((CoordinatorOperations)var4).get_top_level_status();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var88, class$org$omg$CosTransactions$Status == null ? (class$org$omg$CosTransactions$Status = class$("org.omg.CosTransactions.Status")) : class$org$omg$CosTransactions$Status);
               break;
            } catch (IOException var46) {
               throw new MarshalException("error marshalling return", var46);
            }
         case 17:
            var86 = ((CoordinatorOperations)var4).get_transaction_name();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var86, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var45) {
               throw new MarshalException("error marshalling return", var45);
            }
         case 18:
            PropagationContext var84 = ((CoordinatorOperations)var4).get_txcontext();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var84, class$org$omg$CosTransactions$PropagationContext == null ? (class$org$omg$CosTransactions$PropagationContext = class$("org.omg.CosTransactions.PropagationContext")) : class$org$omg$CosTransactions$PropagationContext);
               break;
            } catch (IOException var44) {
               throw new MarshalException("error marshalling return", var44);
            }
         case 19:
            var82 = ((CoordinatorOperations)var4).hash_top_level_tran();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var82);
               break;
            } catch (IOException var43) {
               throw new MarshalException("error marshalling return", var43);
            }
         case 20:
            var82 = ((CoordinatorOperations)var4).hash_transaction();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var82);
               break;
            } catch (IOException var42) {
               throw new MarshalException("error marshalling return", var42);
            }
         case 21:
            try {
               MsgInput var13 = var2.getMsgInput();
               var80 = (Coordinator)var13.readObject(class$org$omg$CosTransactions$Coordinator == null ? (class$org$omg$CosTransactions$Coordinator = class$("org.omg.CosTransactions.Coordinator")) : class$org$omg$CosTransactions$Coordinator);
            } catch (IOException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            } catch (ClassNotFoundException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            }

            boolean var101 = ((CoordinatorOperations)var4).is_ancestor_transaction(var80);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var101);
               break;
            } catch (IOException var39) {
               throw new MarshalException("error marshalling return", var39);
            }
         case 22:
            try {
               MsgInput var15 = var2.getMsgInput();
               var80 = (Coordinator)var15.readObject(class$org$omg$CosTransactions$Coordinator == null ? (class$org$omg$CosTransactions$Coordinator = class$("org.omg.CosTransactions.Coordinator")) : class$org$omg$CosTransactions$Coordinator);
            } catch (IOException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            } catch (ClassNotFoundException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            }

            boolean var103 = ((CoordinatorOperations)var4).is_descendant_transaction(var80);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var103);
               break;
            } catch (IOException var36) {
               throw new MarshalException("error marshalling return", var36);
            }
         case 23:
            try {
               MsgInput var16 = var2.getMsgInput();
               var80 = (Coordinator)var16.readObject(class$org$omg$CosTransactions$Coordinator == null ? (class$org$omg$CosTransactions$Coordinator = class$("org.omg.CosTransactions.Coordinator")) : class$org$omg$CosTransactions$Coordinator);
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            } catch (ClassNotFoundException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            }

            boolean var104 = ((CoordinatorOperations)var4).is_related_transaction(var80);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var104);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 24:
            try {
               MsgInput var17 = var2.getMsgInput();
               var80 = (Coordinator)var17.readObject(class$org$omg$CosTransactions$Coordinator == null ? (class$org$omg$CosTransactions$Coordinator = class$("org.omg.CosTransactions.Coordinator")) : class$org$omg$CosTransactions$Coordinator);
            } catch (IOException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            } catch (ClassNotFoundException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            boolean var105 = ((CoordinatorOperations)var4).is_same_transaction(var80);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var105);
               break;
            } catch (IOException var30) {
               throw new MarshalException("error marshalling return", var30);
            }
         case 25:
            var78 = ((CoordinatorOperations)var4).is_top_level_transaction();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var78);
               break;
            } catch (IOException var29) {
               throw new MarshalException("error marshalling return", var29);
            }
         case 26:
            Resource var77;
            try {
               MsgInput var18 = var2.getMsgInput();
               var77 = (Resource)var18.readObject(class$org$omg$CosTransactions$Resource == null ? (class$org$omg$CosTransactions$Resource = class$("org.omg.CosTransactions.Resource")) : class$org$omg$CosTransactions$Resource);
            } catch (IOException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            } catch (ClassNotFoundException var28) {
               throw new UnmarshalException("error unmarshalling arguments", var28);
            }

            RecoveryCoordinator var106 = ((CoordinatorOperations)var4).register_resource(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var106, class$org$omg$CosTransactions$RecoveryCoordinator == null ? (class$org$omg$CosTransactions$RecoveryCoordinator = class$("org.omg.CosTransactions.RecoveryCoordinator")) : class$org$omg$CosTransactions$RecoveryCoordinator);
               break;
            } catch (IOException var26) {
               throw new MarshalException("error marshalling return", var26);
            }
         case 27:
            SubtransactionAwareResource var76;
            try {
               MsgInput var19 = var2.getMsgInput();
               var76 = (SubtransactionAwareResource)var19.readObject(class$org$omg$CosTransactions$SubtransactionAwareResource == null ? (class$org$omg$CosTransactions$SubtransactionAwareResource = class$("org.omg.CosTransactions.SubtransactionAwareResource")) : class$org$omg$CosTransactions$SubtransactionAwareResource);
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            } catch (ClassNotFoundException var25) {
               throw new UnmarshalException("error unmarshalling arguments", var25);
            }

            ((CoordinatorOperations)var4).register_subtran_aware(var76);
            this.associateResponseData(var2, var3);
            break;
         case 28:
            Synchronization var5;
            try {
               MsgInput var20 = var2.getMsgInput();
               var5 = (Synchronization)var20.readObject(class$org$omg$CosTransactions$Synchronization == null ? (class$org$omg$CosTransactions$Synchronization = class$("org.omg.CosTransactions.Synchronization")) : class$org$omg$CosTransactions$Synchronization);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            ((CoordinatorOperations)var4).register_synchronization(var5);
            this.associateResponseData(var2, var3);
            break;
         case 29:
            ((CoordinatorOperations)var4).rollback_only();
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
            return ((CoordinatorOperations)var3).create_subtransaction();
         case 14:
            return ((CoordinatorOperations)var3).get_parent_status();
         case 15:
            return ((CoordinatorOperations)var3).get_status();
         case 16:
            return ((CoordinatorOperations)var3).get_top_level_status();
         case 17:
            return ((CoordinatorOperations)var3).get_transaction_name();
         case 18:
            return ((CoordinatorOperations)var3).get_txcontext();
         case 19:
            return new Integer(((CoordinatorOperations)var3).hash_top_level_tran());
         case 20:
            return new Integer(((CoordinatorOperations)var3).hash_transaction());
         case 21:
            return new Boolean(((CoordinatorOperations)var3).is_ancestor_transaction((Coordinator)var2[0]));
         case 22:
            return new Boolean(((CoordinatorOperations)var3).is_descendant_transaction((Coordinator)var2[0]));
         case 23:
            return new Boolean(((CoordinatorOperations)var3).is_related_transaction((Coordinator)var2[0]));
         case 24:
            return new Boolean(((CoordinatorOperations)var3).is_same_transaction((Coordinator)var2[0]));
         case 25:
            return new Boolean(((CoordinatorOperations)var3).is_top_level_transaction());
         case 26:
            return ((CoordinatorOperations)var3).register_resource((Resource)var2[0]);
         case 27:
            ((CoordinatorOperations)var3).register_subtran_aware((SubtransactionAwareResource)var2[0]);
            return null;
         case 28:
            ((CoordinatorOperations)var3).register_synchronization((Synchronization)var2[0]);
            return null;
         case 29:
            ((CoordinatorOperations)var3).rollback_only();
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
