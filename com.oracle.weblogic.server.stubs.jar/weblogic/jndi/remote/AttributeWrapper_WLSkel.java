package weblogic.jndi.remote;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class AttributeWrapper_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$javax$naming$NamingEnumeration;
   // $FF: synthetic field
   private static Class class$javax$naming$directory$DirContext;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      int var5;
      Object var42;
      DirContext var46;
      switch (var1) {
         case 0:
            Object var44;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = var7.readInt();
               var44 = (Object)var7.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var40) {
               throw new UnmarshalException("error unmarshalling arguments", var40);
            } catch (ClassNotFoundException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            }

            ((Attribute)var4).add(var5, var44);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var42 = (Object)var6.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var38) {
               throw new UnmarshalException("error unmarshalling arguments", var38);
            } catch (ClassNotFoundException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            boolean var48 = ((Attribute)var4).add(var42);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var48);
               break;
            } catch (IOException var37) {
               throw new MarshalException("error marshalling return", var37);
            }
         case 2:
            ((Attribute)var4).clear();
            this.associateResponseData(var2, var3);
            break;
         case 3:
            var42 = ((Attribute)var4).clone();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var42, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var36) {
               throw new MarshalException("error marshalling return", var36);
            }
         case 4:
            try {
               MsgInput var8 = var2.getMsgInput();
               var42 = (Object)var8.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            } catch (ClassNotFoundException var35) {
               throw new UnmarshalException("error unmarshalling arguments", var35);
            }

            boolean var49 = ((Attribute)var4).contains(var42);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var49);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 5:
            var42 = ((Attribute)var4).get();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var42, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var32) {
               throw new MarshalException("error marshalling return", var32);
            }
         case 6:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = var9.readInt();
            } catch (IOException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            }

            Object var50 = ((Attribute)var4).get(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var50, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var30) {
               throw new MarshalException("error marshalling return", var30);
            }
         case 7:
            NamingEnumeration var47 = ((Attribute)var4).getAll();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var47, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var29) {
               throw new MarshalException("error marshalling return", var29);
            }
         case 8:
            var46 = ((Attribute)var4).getAttributeDefinition();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var46, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 9:
            var46 = ((Attribute)var4).getAttributeSyntaxDefinition();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var46, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var27) {
               throw new MarshalException("error marshalling return", var27);
            }
         case 10:
            String var45 = ((Attribute)var4).getID();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var45, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var26) {
               throw new MarshalException("error marshalling return", var26);
            }
         case 11:
            boolean var43 = ((Attribute)var4).isOrdered();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var43);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 12:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = var10.readInt();
            } catch (IOException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            Object var51 = ((Attribute)var4).remove(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var51, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var23) {
               throw new MarshalException("error marshalling return", var23);
            }
         case 13:
            try {
               MsgInput var11 = var2.getMsgInput();
               var42 = (Object)var11.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            } catch (ClassNotFoundException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            boolean var52 = ((Attribute)var4).remove(var42);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var52);
               break;
            } catch (IOException var20) {
               throw new MarshalException("error marshalling return", var20);
            }
         case 14:
            Object var12;
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = var13.readInt();
               var12 = (Object)var13.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            } catch (ClassNotFoundException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            Object var14 = ((Attribute)var4).set(var5, var12);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var14, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var17) {
               throw new MarshalException("error marshalling return", var17);
            }
         case 15:
            var5 = ((Attribute)var4).size();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var5);
               break;
            } catch (IOException var16) {
               throw new MarshalException("error marshalling return", var16);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((Attribute)var3).add((Integer)var2[0], (Object)var2[1]);
            return null;
         case 1:
            return new Boolean(((Attribute)var3).add((Object)var2[0]));
         case 2:
            ((Attribute)var3).clear();
            return null;
         case 3:
            return ((Attribute)var3).clone();
         case 4:
            return new Boolean(((Attribute)var3).contains((Object)var2[0]));
         case 5:
            return ((Attribute)var3).get();
         case 6:
            return ((Attribute)var3).get((Integer)var2[0]);
         case 7:
            return ((Attribute)var3).getAll();
         case 8:
            return ((Attribute)var3).getAttributeDefinition();
         case 9:
            return ((Attribute)var3).getAttributeSyntaxDefinition();
         case 10:
            return ((Attribute)var3).getID();
         case 11:
            return new Boolean(((Attribute)var3).isOrdered());
         case 12:
            return ((Attribute)var3).remove((Integer)var2[0]);
         case 13:
            return new Boolean(((Attribute)var3).remove((Object)var2[0]));
         case 14:
            return ((Attribute)var3).set((Integer)var2[0], (Object)var2[1]);
         case 15:
            return new Integer(((Attribute)var3).size());
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
