package weblogic.jndi.remote;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class AttributesWrapper_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$javax$naming$NamingEnumeration;
   // $FF: synthetic field
   private static Class class$javax$naming$directory$Attribute;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var29;
      NamingEnumeration var32;
      Attribute var36;
      switch (var1) {
         case 0:
            Object var34 = ((Attributes)var4).clone();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var34, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var28) {
               throw new MarshalException("error marshalling return", var28);
            }
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var29 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            Attribute var35 = ((Attributes)var4).get(var29);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var35, class$javax$naming$directory$Attribute == null ? (class$javax$naming$directory$Attribute = class$("javax.naming.directory.Attribute")) : class$javax$naming$directory$Attribute);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 2:
            var32 = ((Attributes)var4).getAll();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var32, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var24) {
               throw new MarshalException("error marshalling return", var24);
            }
         case 3:
            var32 = ((Attributes)var4).getIDs();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var32, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var23) {
               throw new MarshalException("error marshalling return", var23);
            }
         case 4:
            boolean var31 = ((Attributes)var4).isCaseIgnored();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var31);
               break;
            } catch (IOException var22) {
               throw new MarshalException("error marshalling return", var22);
            }
         case 5:
            Object var33;
            try {
               MsgInput var8 = var2.getMsgInput();
               var29 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var33 = (Object)var8.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            var36 = ((Attributes)var4).put(var29, var33);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var36, class$javax$naming$directory$Attribute == null ? (class$javax$naming$directory$Attribute = class$("javax.naming.directory.Attribute")) : class$javax$naming$directory$Attribute);
               break;
            } catch (IOException var19) {
               throw new MarshalException("error marshalling return", var19);
            }
         case 6:
            Attribute var30;
            try {
               MsgInput var7 = var2.getMsgInput();
               var30 = (Attribute)var7.readObject(class$javax$naming$directory$Attribute == null ? (class$javax$naming$directory$Attribute = class$("javax.naming.directory.Attribute")) : class$javax$naming$directory$Attribute);
            } catch (IOException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            } catch (ClassNotFoundException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            }

            var36 = ((Attributes)var4).put(var30);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var36, class$javax$naming$directory$Attribute == null ? (class$javax$naming$directory$Attribute = class$("javax.naming.directory.Attribute")) : class$javax$naming$directory$Attribute);
               break;
            } catch (IOException var16) {
               throw new MarshalException("error marshalling return", var16);
            }
         case 7:
            try {
               MsgInput var9 = var2.getMsgInput();
               var29 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            } catch (ClassNotFoundException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            }

            Attribute var10 = ((Attributes)var4).remove(var29);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var10, class$javax$naming$directory$Attribute == null ? (class$javax$naming$directory$Attribute = class$("javax.naming.directory.Attribute")) : class$javax$naming$directory$Attribute);
               break;
            } catch (IOException var13) {
               throw new MarshalException("error marshalling return", var13);
            }
         case 8:
            int var5 = ((Attributes)var4).size();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var5);
               break;
            } catch (IOException var12) {
               throw new MarshalException("error marshalling return", var12);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((Attributes)var3).clone();
         case 1:
            return ((Attributes)var3).get((String)var2[0]);
         case 2:
            return ((Attributes)var3).getAll();
         case 3:
            return ((Attributes)var3).getIDs();
         case 4:
            return new Boolean(((Attributes)var3).isCaseIgnored());
         case 5:
            return ((Attributes)var3).put((String)var2[0], (Object)var2[1]);
         case 6:
            return ((Attributes)var3).put((Attribute)var2[0]);
         case 7:
            return ((Attributes)var3).remove((String)var2[0]);
         case 8:
            return new Integer(((Attributes)var3).size());
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
