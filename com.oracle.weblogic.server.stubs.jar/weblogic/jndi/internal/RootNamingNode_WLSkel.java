package weblogic.jndi.internal;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.event.NamingListener;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class RootNamingNode_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Hashtable;
   // $FF: synthetic field
   private static Class class$java$util$List;
   // $FF: synthetic field
   private static Class class$javax$naming$Context;
   // $FF: synthetic field
   private static Class class$javax$naming$Name;
   // $FF: synthetic field
   private static Class class$javax$naming$NameParser;
   // $FF: synthetic field
   private static Class class$javax$naming$NamingEnumeration;
   // $FF: synthetic field
   private static Class class$javax$naming$event$NamingListener;
   // $FF: synthetic field
   private static Class class$weblogic$jndi$internal$NamingNode;
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$internal$AuthenticatedSubject;

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
      Object var14;
      Hashtable var26;
      NamingListener var86;
      Hashtable var89;
      Hashtable var91;
      String var100;
      Hashtable var101;
      Hashtable var102;
      Hashtable var110;
      Object var111;
      switch (var1) {
         case 0:
            int var88;
            NamingListener var92;
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var88 = var9.readInt();
               var92 = (NamingListener)var9.readObject(class$javax$naming$event$NamingListener == null ? (class$javax$naming$event$NamingListener = class$("javax.naming.event.NamingListener")) : class$javax$naming$event$NamingListener);
               var91 = (Hashtable)var9.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var84) {
               throw new UnmarshalException("error unmarshalling arguments", var84);
            } catch (ClassNotFoundException var85) {
               throw new UnmarshalException("error unmarshalling arguments", var85);
            }

            ((NamingNode)var4).addNamingListener(var5, var88, var92, var91);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var86 = (NamingListener)var6.readObject(class$javax$naming$event$NamingListener == null ? (class$javax$naming$event$NamingListener = class$("javax.naming.event.NamingListener")) : class$javax$naming$event$NamingListener);
            } catch (IOException var82) {
               throw new UnmarshalException("error unmarshalling arguments", var82);
            } catch (ClassNotFoundException var83) {
               throw new UnmarshalException("error unmarshalling arguments", var83);
            }

            ((NamingNode)var4).addOneLevelScopeNamingListener(var86);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            AuthenticatedSubject var93;
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var89 = (Hashtable)var10.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
               var93 = (AuthenticatedSubject)var10.readObject(class$weblogic$security$acl$internal$AuthenticatedSubject == null ? (class$weblogic$security$acl$internal$AuthenticatedSubject = class$("weblogic.security.acl.internal.AuthenticatedSubject")) : class$weblogic$security$acl$internal$AuthenticatedSubject);
            } catch (IOException var80) {
               throw new UnmarshalException("error unmarshalling arguments", var80);
            } catch (ClassNotFoundException var81) {
               throw new UnmarshalException("error unmarshalling arguments", var81);
            }

            Object var97 = ((NamingNode)var4).authenticatedLookup(var5, var89, var93);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var97, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var79) {
               throw new MarshalException("error marshalling return", var79);
            }
         case 3:
            Object var90;
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var90 = (Object)var11.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var91 = (Hashtable)var11.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            } catch (ClassNotFoundException var78) {
               throw new UnmarshalException("error unmarshalling arguments", var78);
            }

            ((NamingNode)var4).bind(var5, var90, var91);
            this.associateResponseData(var2, var3);
            break;
         case 4:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var89 = (Hashtable)var8.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            } catch (ClassNotFoundException var76) {
               throw new UnmarshalException("error unmarshalling arguments", var76);
            }

            Context var98 = ((NamingNode)var4).createSubcontext(var5, var89);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var98, class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
               break;
            } catch (IOException var74) {
               throw new MarshalException("error marshalling return", var74);
            }
         case 5:
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var89 = (Hashtable)var12.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            } catch (ClassNotFoundException var73) {
               throw new UnmarshalException("error unmarshalling arguments", var73);
            }

            ((NamingNode)var4).destroySubcontext(var5, var89);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            Hashtable var96;
            try {
               MsgInput var7 = var2.getMsgInput();
               var96 = (Hashtable)var7.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var70) {
               throw new UnmarshalException("error unmarshalling arguments", var70);
            } catch (ClassNotFoundException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            }

            Context var99 = ((NamingNode)var4).getContext(var96);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var99, class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
               break;
            } catch (IOException var69) {
               throw new MarshalException("error marshalling return", var69);
            }
         case 7:
            var5 = ((NamingNode)var4).getNameInNamespace();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var5, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var68) {
               throw new MarshalException("error marshalling return", var68);
            }
         case 8:
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            } catch (ClassNotFoundException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            }

            var100 = ((NamingNode)var4).getNameInNamespace(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var100, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var65) {
               throw new MarshalException("error marshalling return", var65);
            }
         case 9:
            try {
               MsgInput var15 = var2.getMsgInput();
               var5 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var101 = (Hashtable)var15.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            } catch (ClassNotFoundException var64) {
               throw new UnmarshalException("error unmarshalling arguments", var64);
            }

            NameParser var103 = ((NamingNode)var4).getNameParser(var5, var101);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var103, class$javax$naming$NameParser == null ? (class$javax$naming$NameParser = class$("javax.naming.NameParser")) : class$javax$naming$NameParser);
               break;
            } catch (IOException var62) {
               throw new MarshalException("error marshalling return", var62);
            }
         case 10:
            List var95 = ((NamingNode)var4).getOneLevelScopeNamingListeners();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var95, class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List);
               break;
            } catch (IOException var61) {
               throw new MarshalException("error marshalling return", var61);
            }
         case 11:
            NamingNode var94 = ((NamingNode)var4).getParent();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var94, class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode);
               break;
            } catch (IOException var60) {
               throw new MarshalException("error marshalling return", var60);
            }
         case 12:
            try {
               MsgInput var17 = var2.getMsgInput();
               var5 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var14 = (Object)var17.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var102 = (Hashtable)var17.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var58) {
               throw new UnmarshalException("error unmarshalling arguments", var58);
            } catch (ClassNotFoundException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            }

            boolean var105 = ((NamingNode)var4).isBindable(var5, var14, var102);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var105);
               break;
            } catch (IOException var57) {
               throw new MarshalException("error marshalling return", var57);
            }
         case 13:
            boolean var104;
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (String)var18.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var104 = var18.readBoolean();
               var102 = (Hashtable)var18.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            } catch (ClassNotFoundException var56) {
               throw new UnmarshalException("error unmarshalling arguments", var56);
            }

            boolean var107 = ((NamingNode)var4).isBindable(var5, var104, var102);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var107);
               break;
            } catch (IOException var54) {
               throw new MarshalException("error marshalling return", var54);
            }
         case 14:
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = (String)var16.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var101 = (Hashtable)var16.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var52) {
               throw new UnmarshalException("error unmarshalling arguments", var52);
            } catch (ClassNotFoundException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            }

            NamingEnumeration var106 = ((NamingNode)var4).list(var5, var101);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var106, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var51) {
               throw new MarshalException("error marshalling return", var51);
            }
         case 15:
            try {
               MsgInput var19 = var2.getMsgInput();
               var5 = (String)var19.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var101 = (Hashtable)var19.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            } catch (ClassNotFoundException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            }

            NamingEnumeration var108 = ((NamingNode)var4).listBindings(var5, var101);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var108, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var48) {
               throw new MarshalException("error marshalling return", var48);
            }
         case 16:
            try {
               MsgInput var20 = var2.getMsgInput();
               var5 = (String)var20.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var101 = (Hashtable)var20.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var46) {
               throw new UnmarshalException("error unmarshalling arguments", var46);
            } catch (ClassNotFoundException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            }

            Object var109 = ((NamingNode)var4).lookup(var5, var101);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var109, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var45) {
               throw new MarshalException("error marshalling return", var45);
            }
         case 17:
            try {
               MsgInput var21 = var2.getMsgInput();
               var5 = (String)var21.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var101 = (Hashtable)var21.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            } catch (ClassNotFoundException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            }

            var111 = ((NamingNode)var4).lookupLink(var5, var101);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var111, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var42) {
               throw new MarshalException("error marshalling return", var42);
            }
         case 18:
            Hashtable var112;
            try {
               MsgInput var24 = var2.getMsgInput();
               var5 = (String)var24.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var14 = (Object)var24.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var111 = (Object)var24.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var112 = (Hashtable)var24.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            } catch (ClassNotFoundException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            }

            ((NamingNode)var4).rebind(var5, var14, var111, var112);
            this.associateResponseData(var2, var3);
            break;
         case 19:
            try {
               MsgInput var23 = var2.getMsgInput();
               var5 = (String)var23.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var14 = (Object)var23.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var110 = (Hashtable)var23.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            } catch (ClassNotFoundException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            ((NamingNode)var4).rebind(var5, var14, var110);
            this.associateResponseData(var2, var3);
            break;
         case 20:
            Name var87;
            try {
               MsgInput var25 = var2.getMsgInput();
               var87 = (Name)var25.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var14 = (Object)var25.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var110 = (Hashtable)var25.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            } catch (ClassNotFoundException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            ((NamingNode)var4).rebind(var87, var14, var110);
            this.associateResponseData(var2, var3);
            break;
         case 21:
            try {
               MsgInput var22 = var2.getMsgInput();
               var86 = (NamingListener)var22.readObject(class$javax$naming$event$NamingListener == null ? (class$javax$naming$event$NamingListener = class$("javax.naming.event.NamingListener")) : class$javax$naming$event$NamingListener);
               var101 = (Hashtable)var22.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            } catch (ClassNotFoundException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            }

            ((NamingNode)var4).removeNamingListener(var86, var101);
            this.associateResponseData(var2, var3);
            break;
         case 22:
            try {
               MsgInput var27 = var2.getMsgInput();
               var5 = (String)var27.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var100 = (String)var27.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var26 = (Hashtable)var27.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            } catch (ClassNotFoundException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            }

            ((NamingNode)var4).rename(var5, var100, var26);
            this.associateResponseData(var2, var3);
            break;
         case 23:
            try {
               MsgInput var28 = var2.getMsgInput();
               var5 = (String)var28.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var14 = (Object)var28.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var26 = (Hashtable)var28.readObject(class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            } catch (IOException var30) {
               throw new UnmarshalException("error unmarshalling arguments", var30);
            } catch (ClassNotFoundException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            }

            ((NamingNode)var4).unbind(var5, var14, var26);
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
            ((NamingNode)var3).addNamingListener((String)var2[0], (Integer)var2[1], (NamingListener)var2[2], (Hashtable)var2[3]);
            return null;
         case 1:
            ((NamingNode)var3).addOneLevelScopeNamingListener((NamingListener)var2[0]);
            return null;
         case 2:
            return ((NamingNode)var3).authenticatedLookup((String)var2[0], (Hashtable)var2[1], (AuthenticatedSubject)var2[2]);
         case 3:
            ((NamingNode)var3).bind((String)var2[0], (Object)var2[1], (Hashtable)var2[2]);
            return null;
         case 4:
            return ((NamingNode)var3).createSubcontext((String)var2[0], (Hashtable)var2[1]);
         case 5:
            ((NamingNode)var3).destroySubcontext((String)var2[0], (Hashtable)var2[1]);
            return null;
         case 6:
            return ((NamingNode)var3).getContext((Hashtable)var2[0]);
         case 7:
            return ((NamingNode)var3).getNameInNamespace();
         case 8:
            return ((NamingNode)var3).getNameInNamespace((String)var2[0]);
         case 9:
            return ((NamingNode)var3).getNameParser((String)var2[0], (Hashtable)var2[1]);
         case 10:
            return ((NamingNode)var3).getOneLevelScopeNamingListeners();
         case 11:
            return ((NamingNode)var3).getParent();
         case 12:
            return new Boolean(((NamingNode)var3).isBindable((String)var2[0], (Object)var2[1], (Hashtable)var2[2]));
         case 13:
            return new Boolean(((NamingNode)var3).isBindable((String)var2[0], (Boolean)var2[1], (Hashtable)var2[2]));
         case 14:
            return ((NamingNode)var3).list((String)var2[0], (Hashtable)var2[1]);
         case 15:
            return ((NamingNode)var3).listBindings((String)var2[0], (Hashtable)var2[1]);
         case 16:
            return ((NamingNode)var3).lookup((String)var2[0], (Hashtable)var2[1]);
         case 17:
            return ((NamingNode)var3).lookupLink((String)var2[0], (Hashtable)var2[1]);
         case 18:
            ((NamingNode)var3).rebind((String)var2[0], (Object)var2[1], (Object)var2[2], (Hashtable)var2[3]);
            return null;
         case 19:
            ((NamingNode)var3).rebind((String)var2[0], (Object)var2[1], (Hashtable)var2[2]);
            return null;
         case 20:
            ((NamingNode)var3).rebind((Name)var2[0], (Object)var2[1], (Hashtable)var2[2]);
            return null;
         case 21:
            ((NamingNode)var3).removeNamingListener((NamingListener)var2[0], (Hashtable)var2[1]);
            return null;
         case 22:
            ((NamingNode)var3).rename((String)var2[0], (String)var2[1], (Hashtable)var2[2]);
            return null;
         case 23:
            ((NamingNode)var3).unbind((String)var2[0], (Object)var2[1], (Hashtable)var2[2]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
