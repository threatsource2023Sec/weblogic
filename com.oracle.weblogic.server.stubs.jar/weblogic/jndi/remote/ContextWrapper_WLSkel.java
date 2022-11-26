package weblogic.jndi.remote;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ContextWrapper_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Hashtable;
   // $FF: synthetic field
   private static Class class$javax$naming$Context;
   // $FF: synthetic field
   private static Class class$javax$naming$Name;
   // $FF: synthetic field
   private static Class class$javax$naming$NameParser;
   // $FF: synthetic field
   private static Class class$javax$naming$NamingEnumeration;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      Name var5;
      String var103;
      Object var106;
      Object var122;
      switch (var1) {
         case 0:
            try {
               MsgInput var7 = var2.getMsgInput();
               var103 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var106 = (Object)var7.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var101) {
               throw new UnmarshalException("error unmarshalling arguments", var101);
            } catch (ClassNotFoundException var102) {
               throw new UnmarshalException("error unmarshalling arguments", var102);
            }

            Object var107 = ((Context)var4).addToEnvironment(var103, var106);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var107, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var100) {
               throw new MarshalException("error marshalling return", var100);
            }
         case 1:
            try {
               MsgInput var8 = var2.getMsgInput();
               var103 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var106 = (Object)var8.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var98) {
               throw new UnmarshalException("error unmarshalling arguments", var98);
            } catch (ClassNotFoundException var99) {
               throw new UnmarshalException("error unmarshalling arguments", var99);
            }

            ((Context)var4).bind(var103, var106);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (Name)var9.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var106 = (Object)var9.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var96) {
               throw new UnmarshalException("error unmarshalling arguments", var96);
            } catch (ClassNotFoundException var97) {
               throw new UnmarshalException("error unmarshalling arguments", var97);
            }

            ((Context)var4).bind(var5, var106);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            ((Context)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 4:
            String var105;
            try {
               MsgInput var10 = var2.getMsgInput();
               var103 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var105 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var94) {
               throw new UnmarshalException("error unmarshalling arguments", var94);
            } catch (ClassNotFoundException var95) {
               throw new UnmarshalException("error unmarshalling arguments", var95);
            }

            String var109 = ((Context)var4).composeName(var103, var105);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var109, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var93) {
               throw new MarshalException("error marshalling return", var93);
            }
         case 5:
            Name var104;
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (Name)var11.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var104 = (Name)var11.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var91) {
               throw new UnmarshalException("error unmarshalling arguments", var91);
            } catch (ClassNotFoundException var92) {
               throw new UnmarshalException("error unmarshalling arguments", var92);
            }

            Name var111 = ((Context)var4).composeName(var5, var104);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var111, class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               break;
            } catch (IOException var90) {
               throw new MarshalException("error marshalling return", var90);
            }
         case 6:
            try {
               MsgInput var6 = var2.getMsgInput();
               var103 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var88) {
               throw new UnmarshalException("error unmarshalling arguments", var88);
            } catch (ClassNotFoundException var89) {
               throw new UnmarshalException("error unmarshalling arguments", var89);
            }

            Context var110 = ((Context)var4).createSubcontext(var103);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var110, class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
               break;
            } catch (IOException var87) {
               throw new MarshalException("error marshalling return", var87);
            }
         case 7:
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (Name)var12.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var85) {
               throw new UnmarshalException("error unmarshalling arguments", var85);
            } catch (ClassNotFoundException var86) {
               throw new UnmarshalException("error unmarshalling arguments", var86);
            }

            Context var112 = ((Context)var4).createSubcontext(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var112, class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
               break;
            } catch (IOException var84) {
               throw new MarshalException("error marshalling return", var84);
            }
         case 8:
            try {
               MsgInput var13 = var2.getMsgInput();
               var103 = (String)var13.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var82) {
               throw new UnmarshalException("error unmarshalling arguments", var82);
            } catch (ClassNotFoundException var83) {
               throw new UnmarshalException("error unmarshalling arguments", var83);
            }

            ((Context)var4).destroySubcontext(var103);
            this.associateResponseData(var2, var3);
            break;
         case 9:
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = (Name)var14.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var80) {
               throw new UnmarshalException("error unmarshalling arguments", var80);
            } catch (ClassNotFoundException var81) {
               throw new UnmarshalException("error unmarshalling arguments", var81);
            }

            ((Context)var4).destroySubcontext(var5);
            this.associateResponseData(var2, var3);
            break;
         case 10:
            Hashtable var108 = ((Context)var4).getEnvironment();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var108, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
               break;
            } catch (IOException var79) {
               throw new MarshalException("error marshalling return", var79);
            }
         case 11:
            var103 = ((Context)var4).getNameInNamespace();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var103, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var78) {
               throw new MarshalException("error marshalling return", var78);
            }
         case 12:
            try {
               MsgInput var15 = var2.getMsgInput();
               var103 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var76) {
               throw new UnmarshalException("error unmarshalling arguments", var76);
            } catch (ClassNotFoundException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            }

            NameParser var113 = ((Context)var4).getNameParser(var103);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var113, class$javax$naming$NameParser == null ? (class$javax$naming$NameParser = class$("javax.naming.NameParser")) : class$javax$naming$NameParser);
               break;
            } catch (IOException var75) {
               throw new MarshalException("error marshalling return", var75);
            }
         case 13:
            try {
               MsgInput var16 = var2.getMsgInput();
               var5 = (Name)var16.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var73) {
               throw new UnmarshalException("error unmarshalling arguments", var73);
            } catch (ClassNotFoundException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            }

            NameParser var114 = ((Context)var4).getNameParser(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var114, class$javax$naming$NameParser == null ? (class$javax$naming$NameParser = class$("javax.naming.NameParser")) : class$javax$naming$NameParser);
               break;
            } catch (IOException var72) {
               throw new MarshalException("error marshalling return", var72);
            }
         case 14:
            try {
               MsgInput var17 = var2.getMsgInput();
               var103 = (String)var17.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var70) {
               throw new UnmarshalException("error unmarshalling arguments", var70);
            } catch (ClassNotFoundException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            }

            NamingEnumeration var115 = ((Context)var4).list(var103);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var115, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var69) {
               throw new MarshalException("error marshalling return", var69);
            }
         case 15:
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (Name)var18.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            } catch (ClassNotFoundException var68) {
               throw new UnmarshalException("error unmarshalling arguments", var68);
            }

            NamingEnumeration var116 = ((Context)var4).list(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var116, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var66) {
               throw new MarshalException("error marshalling return", var66);
            }
         case 16:
            try {
               MsgInput var19 = var2.getMsgInput();
               var103 = (String)var19.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var64) {
               throw new UnmarshalException("error unmarshalling arguments", var64);
            } catch (ClassNotFoundException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            }

            NamingEnumeration var117 = ((Context)var4).listBindings(var103);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var117, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var63) {
               throw new MarshalException("error marshalling return", var63);
            }
         case 17:
            try {
               MsgInput var20 = var2.getMsgInput();
               var5 = (Name)var20.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
            } catch (ClassNotFoundException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            }

            NamingEnumeration var118 = ((Context)var4).listBindings(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var118, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var60) {
               throw new MarshalException("error marshalling return", var60);
            }
         case 18:
            try {
               MsgInput var21 = var2.getMsgInput();
               var103 = (String)var21.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var58) {
               throw new UnmarshalException("error unmarshalling arguments", var58);
            } catch (ClassNotFoundException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            }

            Object var119 = ((Context)var4).lookup(var103);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var119, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var57) {
               throw new MarshalException("error marshalling return", var57);
            }
         case 19:
            try {
               MsgInput var22 = var2.getMsgInput();
               var5 = (Name)var22.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            } catch (ClassNotFoundException var56) {
               throw new UnmarshalException("error unmarshalling arguments", var56);
            }

            Object var120 = ((Context)var4).lookup(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var120, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var54) {
               throw new MarshalException("error marshalling return", var54);
            }
         case 20:
            try {
               MsgInput var23 = var2.getMsgInput();
               var103 = (String)var23.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var52) {
               throw new UnmarshalException("error unmarshalling arguments", var52);
            } catch (ClassNotFoundException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            }

            Object var121 = ((Context)var4).lookupLink(var103);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var121, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var51) {
               throw new MarshalException("error marshalling return", var51);
            }
         case 21:
            try {
               MsgInput var24 = var2.getMsgInput();
               var5 = (Name)var24.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            } catch (ClassNotFoundException var50) {
               throw new UnmarshalException("error unmarshalling arguments", var50);
            }

            var122 = ((Context)var4).lookupLink(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var122, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var48) {
               throw new MarshalException("error marshalling return", var48);
            }
         case 22:
            try {
               MsgInput var26 = var2.getMsgInput();
               var103 = (String)var26.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var122 = (Object)var26.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var46) {
               throw new UnmarshalException("error unmarshalling arguments", var46);
            } catch (ClassNotFoundException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            }

            ((Context)var4).rebind(var103, var122);
            this.associateResponseData(var2, var3);
            break;
         case 23:
            try {
               MsgInput var27 = var2.getMsgInput();
               var5 = (Name)var27.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var122 = (Object)var27.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var44) {
               throw new UnmarshalException("error unmarshalling arguments", var44);
            } catch (ClassNotFoundException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            }

            ((Context)var4).rebind(var5, var122);
            this.associateResponseData(var2, var3);
            break;
         case 24:
            try {
               MsgInput var25 = var2.getMsgInput();
               var103 = (String)var25.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var42) {
               throw new UnmarshalException("error unmarshalling arguments", var42);
            } catch (ClassNotFoundException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            }

            Object var125 = ((Context)var4).removeFromEnvironment(var103);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var125, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var41) {
               throw new MarshalException("error marshalling return", var41);
            }
         case 25:
            String var124;
            try {
               MsgInput var29 = var2.getMsgInput();
               var103 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var124 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            } catch (ClassNotFoundException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            }

            ((Context)var4).rename(var103, var124);
            this.associateResponseData(var2, var3);
            break;
         case 26:
            Name var123;
            try {
               MsgInput var30 = var2.getMsgInput();
               var5 = (Name)var30.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var123 = (Name)var30.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            } catch (ClassNotFoundException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            }

            ((Context)var4).rename(var5, var123);
            this.associateResponseData(var2, var3);
            break;
         case 27:
            try {
               MsgInput var28 = var2.getMsgInput();
               var103 = (String)var28.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            } catch (ClassNotFoundException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            }

            ((Context)var4).unbind(var103);
            this.associateResponseData(var2, var3);
            break;
         case 28:
            try {
               MsgInput var31 = var2.getMsgInput();
               var5 = (Name)var31.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var33) {
               throw new UnmarshalException("error unmarshalling arguments", var33);
            } catch (ClassNotFoundException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            }

            ((Context)var4).unbind(var5);
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
            return ((Context)var3).addToEnvironment((String)var2[0], (Object)var2[1]);
         case 1:
            ((Context)var3).bind((String)var2[0], (Object)var2[1]);
            return null;
         case 2:
            ((Context)var3).bind((Name)var2[0], (Object)var2[1]);
            return null;
         case 3:
            ((Context)var3).close();
            return null;
         case 4:
            return ((Context)var3).composeName((String)var2[0], (String)var2[1]);
         case 5:
            return ((Context)var3).composeName((Name)var2[0], (Name)var2[1]);
         case 6:
            return ((Context)var3).createSubcontext((String)var2[0]);
         case 7:
            return ((Context)var3).createSubcontext((Name)var2[0]);
         case 8:
            ((Context)var3).destroySubcontext((String)var2[0]);
            return null;
         case 9:
            ((Context)var3).destroySubcontext((Name)var2[0]);
            return null;
         case 10:
            return ((Context)var3).getEnvironment();
         case 11:
            return ((Context)var3).getNameInNamespace();
         case 12:
            return ((Context)var3).getNameParser((String)var2[0]);
         case 13:
            return ((Context)var3).getNameParser((Name)var2[0]);
         case 14:
            return ((Context)var3).list((String)var2[0]);
         case 15:
            return ((Context)var3).list((Name)var2[0]);
         case 16:
            return ((Context)var3).listBindings((String)var2[0]);
         case 17:
            return ((Context)var3).listBindings((Name)var2[0]);
         case 18:
            return ((Context)var3).lookup((String)var2[0]);
         case 19:
            return ((Context)var3).lookup((Name)var2[0]);
         case 20:
            return ((Context)var3).lookupLink((String)var2[0]);
         case 21:
            return ((Context)var3).lookupLink((Name)var2[0]);
         case 22:
            ((Context)var3).rebind((String)var2[0], (Object)var2[1]);
            return null;
         case 23:
            ((Context)var3).rebind((Name)var2[0], (Object)var2[1]);
            return null;
         case 24:
            return ((Context)var3).removeFromEnvironment((String)var2[0]);
         case 25:
            ((Context)var3).rename((String)var2[0], (String)var2[1]);
            return null;
         case 26:
            ((Context)var3).rename((Name)var2[0], (Name)var2[1]);
            return null;
         case 27:
            ((Context)var3).unbind((String)var2[0]);
            return null;
         case 28:
            ((Context)var3).unbind((Name)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
