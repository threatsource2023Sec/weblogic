package weblogic.jndi.remote;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class DirContextWrapper_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Ljava$lang$Object;
   // $FF: synthetic field
   private static Class array$Ljava$lang$String;
   // $FF: synthetic field
   private static Class array$Ljavax$naming$directory$ModificationItem;
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
   // $FF: synthetic field
   private static Class class$javax$naming$directory$Attributes;
   // $FF: synthetic field
   private static Class class$javax$naming$directory$DirContext;
   // $FF: synthetic field
   private static Class class$javax$naming$directory$SearchControls;

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
      String var200;
      Object var203;
      Attributes var219;
      Object var234;
      ModificationItem[] var236;
      int var237;
      Attributes var240;
      String var241;
      SearchControls var247;
      NamingEnumeration var249;
      NamingEnumeration var253;
      switch (var1) {
         case 0:
            try {
               MsgInput var7 = var2.getMsgInput();
               var200 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var203 = (Object)var7.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var198) {
               throw new UnmarshalException("error unmarshalling arguments", var198);
            } catch (ClassNotFoundException var199) {
               throw new UnmarshalException("error unmarshalling arguments", var199);
            }

            Object var204 = ((Context)var4).addToEnvironment(var200, var203);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var204, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var197) {
               throw new MarshalException("error marshalling return", var197);
            }
         case 1:
            try {
               MsgInput var8 = var2.getMsgInput();
               var200 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var203 = (Object)var8.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var195) {
               throw new UnmarshalException("error unmarshalling arguments", var195);
            } catch (ClassNotFoundException var196) {
               throw new UnmarshalException("error unmarshalling arguments", var196);
            }

            ((Context)var4).bind(var200, var203);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            Attributes var205;
            try {
               MsgInput var10 = var2.getMsgInput();
               var200 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var203 = (Object)var10.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var205 = (Attributes)var10.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var193) {
               throw new UnmarshalException("error unmarshalling arguments", var193);
            } catch (ClassNotFoundException var194) {
               throw new UnmarshalException("error unmarshalling arguments", var194);
            }

            ((DirContext)var4).bind(var200, var203, var205);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (Name)var9.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var203 = (Object)var9.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var191) {
               throw new UnmarshalException("error unmarshalling arguments", var191);
            } catch (ClassNotFoundException var192) {
               throw new UnmarshalException("error unmarshalling arguments", var192);
            }

            ((Context)var4).bind(var5, var203);
            this.associateResponseData(var2, var3);
            break;
         case 4:
            Attributes var206;
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (Name)var12.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var203 = (Object)var12.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var206 = (Attributes)var12.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var189) {
               throw new UnmarshalException("error unmarshalling arguments", var189);
            } catch (ClassNotFoundException var190) {
               throw new UnmarshalException("error unmarshalling arguments", var190);
            }

            ((DirContext)var4).bind(var5, var203, var206);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            ((Context)var4).close();
            this.associateResponseData(var2, var3);
            break;
         case 6:
            String var202;
            try {
               MsgInput var11 = var2.getMsgInput();
               var200 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var202 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var187) {
               throw new UnmarshalException("error unmarshalling arguments", var187);
            } catch (ClassNotFoundException var188) {
               throw new UnmarshalException("error unmarshalling arguments", var188);
            }

            String var207 = ((Context)var4).composeName(var200, var202);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var207, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var186) {
               throw new MarshalException("error marshalling return", var186);
            }
         case 7:
            Name var201;
            try {
               MsgInput var13 = var2.getMsgInput();
               var5 = (Name)var13.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var201 = (Name)var13.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var184) {
               throw new UnmarshalException("error unmarshalling arguments", var184);
            } catch (ClassNotFoundException var185) {
               throw new UnmarshalException("error unmarshalling arguments", var185);
            }

            Name var210 = ((Context)var4).composeName(var5, var201);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var210, class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               break;
            } catch (IOException var183) {
               throw new MarshalException("error marshalling return", var183);
            }
         case 8:
            try {
               MsgInput var6 = var2.getMsgInput();
               var200 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var181) {
               throw new UnmarshalException("error unmarshalling arguments", var181);
            } catch (ClassNotFoundException var182) {
               throw new UnmarshalException("error unmarshalling arguments", var182);
            }

            Context var209 = ((Context)var4).createSubcontext(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var209, class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
               break;
            } catch (IOException var180) {
               throw new MarshalException("error marshalling return", var180);
            }
         case 9:
            Attributes var208;
            try {
               MsgInput var15 = var2.getMsgInput();
               var200 = (String)var15.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var208 = (Attributes)var15.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var178) {
               throw new UnmarshalException("error unmarshalling arguments", var178);
            } catch (ClassNotFoundException var179) {
               throw new UnmarshalException("error unmarshalling arguments", var179);
            }

            DirContext var214 = ((DirContext)var4).createSubcontext(var200, var208);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var214, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var177) {
               throw new MarshalException("error marshalling return", var177);
            }
         case 10:
            try {
               MsgInput var14 = var2.getMsgInput();
               var5 = (Name)var14.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var175) {
               throw new UnmarshalException("error unmarshalling arguments", var175);
            } catch (ClassNotFoundException var176) {
               throw new UnmarshalException("error unmarshalling arguments", var176);
            }

            Context var212 = ((Context)var4).createSubcontext(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var212, class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
               break;
            } catch (IOException var174) {
               throw new MarshalException("error marshalling return", var174);
            }
         case 11:
            Attributes var211;
            try {
               MsgInput var17 = var2.getMsgInput();
               var5 = (Name)var17.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var211 = (Attributes)var17.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var172) {
               throw new UnmarshalException("error unmarshalling arguments", var172);
            } catch (ClassNotFoundException var173) {
               throw new UnmarshalException("error unmarshalling arguments", var173);
            }

            DirContext var215 = ((DirContext)var4).createSubcontext(var5, var211);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var215, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var171) {
               throw new MarshalException("error marshalling return", var171);
            }
         case 12:
            try {
               MsgInput var16 = var2.getMsgInput();
               var200 = (String)var16.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var169) {
               throw new UnmarshalException("error unmarshalling arguments", var169);
            } catch (ClassNotFoundException var170) {
               throw new UnmarshalException("error unmarshalling arguments", var170);
            }

            ((Context)var4).destroySubcontext(var200);
            this.associateResponseData(var2, var3);
            break;
         case 13:
            try {
               MsgInput var18 = var2.getMsgInput();
               var5 = (Name)var18.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var167) {
               throw new UnmarshalException("error unmarshalling arguments", var167);
            } catch (ClassNotFoundException var168) {
               throw new UnmarshalException("error unmarshalling arguments", var168);
            }

            ((Context)var4).destroySubcontext(var5);
            this.associateResponseData(var2, var3);
            break;
         case 14:
            try {
               MsgInput var19 = var2.getMsgInput();
               var200 = (String)var19.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var165) {
               throw new UnmarshalException("error unmarshalling arguments", var165);
            } catch (ClassNotFoundException var166) {
               throw new UnmarshalException("error unmarshalling arguments", var166);
            }

            Attributes var217 = ((DirContext)var4).getAttributes(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var217, class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
               break;
            } catch (IOException var164) {
               throw new MarshalException("error marshalling return", var164);
            }
         case 15:
            String[] var216;
            try {
               MsgInput var21 = var2.getMsgInput();
               var200 = (String)var21.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var216 = (String[])var21.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var162) {
               throw new UnmarshalException("error unmarshalling arguments", var162);
            } catch (ClassNotFoundException var163) {
               throw new UnmarshalException("error unmarshalling arguments", var163);
            }

            var219 = ((DirContext)var4).getAttributes(var200, var216);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var219, class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
               break;
            } catch (IOException var161) {
               throw new MarshalException("error marshalling return", var161);
            }
         case 16:
            try {
               MsgInput var20 = var2.getMsgInput();
               var5 = (Name)var20.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var159) {
               throw new UnmarshalException("error unmarshalling arguments", var159);
            } catch (ClassNotFoundException var160) {
               throw new UnmarshalException("error unmarshalling arguments", var160);
            }

            var219 = ((DirContext)var4).getAttributes(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var219, class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
               break;
            } catch (IOException var158) {
               throw new MarshalException("error marshalling return", var158);
            }
         case 17:
            String[] var218;
            try {
               MsgInput var23 = var2.getMsgInput();
               var5 = (Name)var23.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var218 = (String[])var23.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var156) {
               throw new UnmarshalException("error unmarshalling arguments", var156);
            } catch (ClassNotFoundException var157) {
               throw new UnmarshalException("error unmarshalling arguments", var157);
            }

            Attributes var221 = ((DirContext)var4).getAttributes(var5, var218);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var221, class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
               break;
            } catch (IOException var155) {
               throw new MarshalException("error marshalling return", var155);
            }
         case 18:
            Hashtable var213 = ((Context)var4).getEnvironment();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var213, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
               break;
            } catch (IOException var154) {
               throw new MarshalException("error marshalling return", var154);
            }
         case 19:
            var200 = ((Context)var4).getNameInNamespace();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var200, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var153) {
               throw new MarshalException("error marshalling return", var153);
            }
         case 20:
            try {
               MsgInput var22 = var2.getMsgInput();
               var200 = (String)var22.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var151) {
               throw new UnmarshalException("error unmarshalling arguments", var151);
            } catch (ClassNotFoundException var152) {
               throw new UnmarshalException("error unmarshalling arguments", var152);
            }

            NameParser var220 = ((Context)var4).getNameParser(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var220, class$javax$naming$NameParser == null ? (class$javax$naming$NameParser = class$("javax.naming.NameParser")) : class$javax$naming$NameParser);
               break;
            } catch (IOException var150) {
               throw new MarshalException("error marshalling return", var150);
            }
         case 21:
            try {
               MsgInput var24 = var2.getMsgInput();
               var5 = (Name)var24.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var148) {
               throw new UnmarshalException("error unmarshalling arguments", var148);
            } catch (ClassNotFoundException var149) {
               throw new UnmarshalException("error unmarshalling arguments", var149);
            }

            NameParser var222 = ((Context)var4).getNameParser(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var222, class$javax$naming$NameParser == null ? (class$javax$naming$NameParser = class$("javax.naming.NameParser")) : class$javax$naming$NameParser);
               break;
            } catch (IOException var147) {
               throw new MarshalException("error marshalling return", var147);
            }
         case 22:
            try {
               MsgInput var25 = var2.getMsgInput();
               var200 = (String)var25.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var145) {
               throw new UnmarshalException("error unmarshalling arguments", var145);
            } catch (ClassNotFoundException var146) {
               throw new UnmarshalException("error unmarshalling arguments", var146);
            }

            DirContext var223 = ((DirContext)var4).getSchema(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var223, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var144) {
               throw new MarshalException("error marshalling return", var144);
            }
         case 23:
            try {
               MsgInput var26 = var2.getMsgInput();
               var5 = (Name)var26.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var142) {
               throw new UnmarshalException("error unmarshalling arguments", var142);
            } catch (ClassNotFoundException var143) {
               throw new UnmarshalException("error unmarshalling arguments", var143);
            }

            DirContext var224 = ((DirContext)var4).getSchema(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var224, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var141) {
               throw new MarshalException("error marshalling return", var141);
            }
         case 24:
            try {
               MsgInput var27 = var2.getMsgInput();
               var200 = (String)var27.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var139) {
               throw new UnmarshalException("error unmarshalling arguments", var139);
            } catch (ClassNotFoundException var140) {
               throw new UnmarshalException("error unmarshalling arguments", var140);
            }

            DirContext var225 = ((DirContext)var4).getSchemaClassDefinition(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var225, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var138) {
               throw new MarshalException("error marshalling return", var138);
            }
         case 25:
            try {
               MsgInput var28 = var2.getMsgInput();
               var5 = (Name)var28.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var136) {
               throw new UnmarshalException("error unmarshalling arguments", var136);
            } catch (ClassNotFoundException var137) {
               throw new UnmarshalException("error unmarshalling arguments", var137);
            }

            DirContext var226 = ((DirContext)var4).getSchemaClassDefinition(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var226, class$javax$naming$directory$DirContext == null ? (class$javax$naming$directory$DirContext = class$("javax.naming.directory.DirContext")) : class$javax$naming$directory$DirContext);
               break;
            } catch (IOException var135) {
               throw new MarshalException("error marshalling return", var135);
            }
         case 26:
            try {
               MsgInput var29 = var2.getMsgInput();
               var200 = (String)var29.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var133) {
               throw new UnmarshalException("error unmarshalling arguments", var133);
            } catch (ClassNotFoundException var134) {
               throw new UnmarshalException("error unmarshalling arguments", var134);
            }

            NamingEnumeration var227 = ((Context)var4).list(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var227, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var132) {
               throw new MarshalException("error marshalling return", var132);
            }
         case 27:
            try {
               MsgInput var30 = var2.getMsgInput();
               var5 = (Name)var30.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var130) {
               throw new UnmarshalException("error unmarshalling arguments", var130);
            } catch (ClassNotFoundException var131) {
               throw new UnmarshalException("error unmarshalling arguments", var131);
            }

            NamingEnumeration var228 = ((Context)var4).list(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var228, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var129) {
               throw new MarshalException("error marshalling return", var129);
            }
         case 28:
            try {
               MsgInput var31 = var2.getMsgInput();
               var200 = (String)var31.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var127) {
               throw new UnmarshalException("error unmarshalling arguments", var127);
            } catch (ClassNotFoundException var128) {
               throw new UnmarshalException("error unmarshalling arguments", var128);
            }

            NamingEnumeration var229 = ((Context)var4).listBindings(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var229, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var126) {
               throw new MarshalException("error marshalling return", var126);
            }
         case 29:
            try {
               MsgInput var32 = var2.getMsgInput();
               var5 = (Name)var32.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var124) {
               throw new UnmarshalException("error unmarshalling arguments", var124);
            } catch (ClassNotFoundException var125) {
               throw new UnmarshalException("error unmarshalling arguments", var125);
            }

            NamingEnumeration var230 = ((Context)var4).listBindings(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var230, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var123) {
               throw new MarshalException("error marshalling return", var123);
            }
         case 30:
            try {
               MsgInput var33 = var2.getMsgInput();
               var200 = (String)var33.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var121) {
               throw new UnmarshalException("error unmarshalling arguments", var121);
            } catch (ClassNotFoundException var122) {
               throw new UnmarshalException("error unmarshalling arguments", var122);
            }

            Object var231 = ((Context)var4).lookup(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var231, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var120) {
               throw new MarshalException("error marshalling return", var120);
            }
         case 31:
            try {
               MsgInput var34 = var2.getMsgInput();
               var5 = (Name)var34.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var118) {
               throw new UnmarshalException("error unmarshalling arguments", var118);
            } catch (ClassNotFoundException var119) {
               throw new UnmarshalException("error unmarshalling arguments", var119);
            }

            Object var232 = ((Context)var4).lookup(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var232, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var117) {
               throw new MarshalException("error marshalling return", var117);
            }
         case 32:
            try {
               MsgInput var35 = var2.getMsgInput();
               var200 = (String)var35.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var115) {
               throw new UnmarshalException("error unmarshalling arguments", var115);
            } catch (ClassNotFoundException var116) {
               throw new UnmarshalException("error unmarshalling arguments", var116);
            }

            Object var233 = ((Context)var4).lookupLink(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var233, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var114) {
               throw new MarshalException("error marshalling return", var114);
            }
         case 33:
            try {
               MsgInput var36 = var2.getMsgInput();
               var5 = (Name)var36.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var112) {
               throw new UnmarshalException("error unmarshalling arguments", var112);
            } catch (ClassNotFoundException var113) {
               throw new UnmarshalException("error unmarshalling arguments", var113);
            }

            var234 = ((Context)var4).lookupLink(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var234, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var111) {
               throw new MarshalException("error marshalling return", var111);
            }
         case 34:
            Attributes var235;
            try {
               MsgInput var39 = var2.getMsgInput();
               var200 = (String)var39.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var237 = var39.readInt();
               var235 = (Attributes)var39.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var109) {
               throw new UnmarshalException("error unmarshalling arguments", var109);
            } catch (ClassNotFoundException var110) {
               throw new UnmarshalException("error unmarshalling arguments", var110);
            }

            ((DirContext)var4).modifyAttributes(var200, var237, var235);
            this.associateResponseData(var2, var3);
            break;
         case 35:
            try {
               MsgInput var38 = var2.getMsgInput();
               var200 = (String)var38.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var236 = (ModificationItem[])var38.readObject(array$Ljavax$naming$directory$ModificationItem == null ? (array$Ljavax$naming$directory$ModificationItem = class$("[Ljavax.naming.directory.ModificationItem;")) : array$Ljavax$naming$directory$ModificationItem);
            } catch (IOException var107) {
               throw new UnmarshalException("error unmarshalling arguments", var107);
            } catch (ClassNotFoundException var108) {
               throw new UnmarshalException("error unmarshalling arguments", var108);
            }

            ((DirContext)var4).modifyAttributes(var200, var236);
            this.associateResponseData(var2, var3);
            break;
         case 36:
            Attributes var238;
            try {
               MsgInput var41 = var2.getMsgInput();
               var5 = (Name)var41.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var237 = var41.readInt();
               var238 = (Attributes)var41.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var105) {
               throw new UnmarshalException("error unmarshalling arguments", var105);
            } catch (ClassNotFoundException var106) {
               throw new UnmarshalException("error unmarshalling arguments", var106);
            }

            ((DirContext)var4).modifyAttributes(var5, var237, var238);
            this.associateResponseData(var2, var3);
            break;
         case 37:
            try {
               MsgInput var40 = var2.getMsgInput();
               var5 = (Name)var40.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var236 = (ModificationItem[])var40.readObject(array$Ljavax$naming$directory$ModificationItem == null ? (array$Ljavax$naming$directory$ModificationItem = class$("[Ljavax.naming.directory.ModificationItem;")) : array$Ljavax$naming$directory$ModificationItem);
            } catch (IOException var103) {
               throw new UnmarshalException("error unmarshalling arguments", var103);
            } catch (ClassNotFoundException var104) {
               throw new UnmarshalException("error unmarshalling arguments", var104);
            }

            ((DirContext)var4).modifyAttributes(var5, var236);
            this.associateResponseData(var2, var3);
            break;
         case 38:
            try {
               MsgInput var42 = var2.getMsgInput();
               var200 = (String)var42.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var234 = (Object)var42.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var101) {
               throw new UnmarshalException("error unmarshalling arguments", var101);
            } catch (ClassNotFoundException var102) {
               throw new UnmarshalException("error unmarshalling arguments", var102);
            }

            ((Context)var4).rebind(var200, var234);
            this.associateResponseData(var2, var3);
            break;
         case 39:
            Attributes var239;
            try {
               MsgInput var44 = var2.getMsgInput();
               var200 = (String)var44.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var234 = (Object)var44.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var239 = (Attributes)var44.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var99) {
               throw new UnmarshalException("error unmarshalling arguments", var99);
            } catch (ClassNotFoundException var100) {
               throw new UnmarshalException("error unmarshalling arguments", var100);
            }

            ((DirContext)var4).rebind(var200, var234, var239);
            this.associateResponseData(var2, var3);
            break;
         case 40:
            try {
               MsgInput var43 = var2.getMsgInput();
               var5 = (Name)var43.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var234 = (Object)var43.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var97) {
               throw new UnmarshalException("error unmarshalling arguments", var97);
            } catch (ClassNotFoundException var98) {
               throw new UnmarshalException("error unmarshalling arguments", var98);
            }

            ((Context)var4).rebind(var5, var234);
            this.associateResponseData(var2, var3);
            break;
         case 41:
            try {
               MsgInput var46 = var2.getMsgInput();
               var5 = (Name)var46.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var234 = (Object)var46.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               var240 = (Attributes)var46.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var95) {
               throw new UnmarshalException("error unmarshalling arguments", var95);
            } catch (ClassNotFoundException var96) {
               throw new UnmarshalException("error unmarshalling arguments", var96);
            }

            ((DirContext)var4).rebind(var5, var234, var240);
            this.associateResponseData(var2, var3);
            break;
         case 42:
            try {
               MsgInput var37 = var2.getMsgInput();
               var200 = (String)var37.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var93) {
               throw new UnmarshalException("error unmarshalling arguments", var93);
            } catch (ClassNotFoundException var94) {
               throw new UnmarshalException("error unmarshalling arguments", var94);
            }

            Object var243 = ((Context)var4).removeFromEnvironment(var200);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var243, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var92) {
               throw new MarshalException("error marshalling return", var92);
            }
         case 43:
            try {
               MsgInput var47 = var2.getMsgInput();
               var200 = (String)var47.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var241 = (String)var47.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var90) {
               throw new UnmarshalException("error unmarshalling arguments", var90);
            } catch (ClassNotFoundException var91) {
               throw new UnmarshalException("error unmarshalling arguments", var91);
            }

            ((Context)var4).rename(var200, var241);
            this.associateResponseData(var2, var3);
            break;
         case 44:
            Name var242;
            try {
               MsgInput var48 = var2.getMsgInput();
               var5 = (Name)var48.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var242 = (Name)var48.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var88) {
               throw new UnmarshalException("error unmarshalling arguments", var88);
            } catch (ClassNotFoundException var89) {
               throw new UnmarshalException("error unmarshalling arguments", var89);
            }

            ((Context)var4).rename(var5, var242);
            this.associateResponseData(var2, var3);
            break;
         case 45:
            SearchControls var245;
            try {
               MsgInput var50 = var2.getMsgInput();
               var200 = (String)var50.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var241 = (String)var50.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var245 = (SearchControls)var50.readObject(class$javax$naming$directory$SearchControls == null ? (class$javax$naming$directory$SearchControls = class$("javax.naming.directory.SearchControls")) : class$javax$naming$directory$SearchControls);
            } catch (IOException var86) {
               throw new UnmarshalException("error unmarshalling arguments", var86);
            } catch (ClassNotFoundException var87) {
               throw new UnmarshalException("error unmarshalling arguments", var87);
            }

            var249 = ((DirContext)var4).search(var200, var241, var245);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var249, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var85) {
               throw new MarshalException("error marshalling return", var85);
            }
         case 46:
            Object[] var244;
            try {
               MsgInput var52 = var2.getMsgInput();
               var200 = (String)var52.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var241 = (String)var52.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var244 = (Object[])var52.readObject(array$Ljava$lang$Object == null ? (array$Ljava$lang$Object = class$("[Ljava.lang.Object;")) : array$Ljava$lang$Object);
               var247 = (SearchControls)var52.readObject(class$javax$naming$directory$SearchControls == null ? (class$javax$naming$directory$SearchControls = class$("javax.naming.directory.SearchControls")) : class$javax$naming$directory$SearchControls);
            } catch (IOException var83) {
               throw new UnmarshalException("error unmarshalling arguments", var83);
            } catch (ClassNotFoundException var84) {
               throw new UnmarshalException("error unmarshalling arguments", var84);
            }

            NamingEnumeration var250 = ((DirContext)var4).search(var200, var241, var244, var247);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var250, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var82) {
               throw new MarshalException("error marshalling return", var82);
            }
         case 47:
            try {
               MsgInput var49 = var2.getMsgInput();
               var200 = (String)var49.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var240 = (Attributes)var49.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var80) {
               throw new UnmarshalException("error unmarshalling arguments", var80);
            } catch (ClassNotFoundException var81) {
               throw new UnmarshalException("error unmarshalling arguments", var81);
            }

            var249 = ((DirContext)var4).search(var200, var240);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var249, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var79) {
               throw new MarshalException("error marshalling return", var79);
            }
         case 48:
            String[] var248;
            try {
               MsgInput var53 = var2.getMsgInput();
               var200 = (String)var53.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var240 = (Attributes)var53.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
               var248 = (String[])var53.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var77) {
               throw new UnmarshalException("error unmarshalling arguments", var77);
            } catch (ClassNotFoundException var78) {
               throw new UnmarshalException("error unmarshalling arguments", var78);
            }

            NamingEnumeration var251 = ((DirContext)var4).search(var200, var240, var248);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var251, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var76) {
               throw new MarshalException("error marshalling return", var76);
            }
         case 49:
            try {
               MsgInput var54 = var2.getMsgInput();
               var5 = (Name)var54.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var241 = (String)var54.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var247 = (SearchControls)var54.readObject(class$javax$naming$directory$SearchControls == null ? (class$javax$naming$directory$SearchControls = class$("javax.naming.directory.SearchControls")) : class$javax$naming$directory$SearchControls);
            } catch (IOException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            } catch (ClassNotFoundException var75) {
               throw new UnmarshalException("error unmarshalling arguments", var75);
            }

            var253 = ((DirContext)var4).search(var5, var241, var247);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var253, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var73) {
               throw new MarshalException("error marshalling return", var73);
            }
         case 50:
            Object[] var246;
            SearchControls var254;
            try {
               MsgInput var56 = var2.getMsgInput();
               var5 = (Name)var56.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var241 = (String)var56.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var246 = (Object[])var56.readObject(array$Ljava$lang$Object == null ? (array$Ljava$lang$Object = class$("[Ljava.lang.Object;")) : array$Ljava$lang$Object);
               var254 = (SearchControls)var56.readObject(class$javax$naming$directory$SearchControls == null ? (class$javax$naming$directory$SearchControls = class$("javax.naming.directory.SearchControls")) : class$javax$naming$directory$SearchControls);
            } catch (IOException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            } catch (ClassNotFoundException var72) {
               throw new UnmarshalException("error unmarshalling arguments", var72);
            }

            NamingEnumeration var255 = ((DirContext)var4).search(var5, var241, var246, var254);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var255, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var70) {
               throw new MarshalException("error marshalling return", var70);
            }
         case 51:
            try {
               MsgInput var51 = var2.getMsgInput();
               var5 = (Name)var51.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var240 = (Attributes)var51.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
            } catch (IOException var68) {
               throw new UnmarshalException("error unmarshalling arguments", var68);
            } catch (ClassNotFoundException var69) {
               throw new UnmarshalException("error unmarshalling arguments", var69);
            }

            var253 = ((DirContext)var4).search(var5, var240);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var253, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var67) {
               throw new MarshalException("error marshalling return", var67);
            }
         case 52:
            String[] var252;
            try {
               MsgInput var57 = var2.getMsgInput();
               var5 = (Name)var57.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
               var240 = (Attributes)var57.readObject(class$javax$naming$directory$Attributes == null ? (class$javax$naming$directory$Attributes = class$("javax.naming.directory.Attributes")) : class$javax$naming$directory$Attributes);
               var252 = (String[])var57.readObject(array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            } catch (IOException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            } catch (ClassNotFoundException var66) {
               throw new UnmarshalException("error unmarshalling arguments", var66);
            }

            NamingEnumeration var58 = ((DirContext)var4).search(var5, var240, var252);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var58, class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
               break;
            } catch (IOException var64) {
               throw new MarshalException("error marshalling return", var64);
            }
         case 53:
            try {
               MsgInput var45 = var2.getMsgInput();
               var200 = (String)var45.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var62) {
               throw new UnmarshalException("error unmarshalling arguments", var62);
            } catch (ClassNotFoundException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            }

            ((Context)var4).unbind(var200);
            this.associateResponseData(var2, var3);
            break;
         case 54:
            try {
               MsgInput var55 = var2.getMsgInput();
               var5 = (Name)var55.readObject(class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            } catch (IOException var60) {
               throw new UnmarshalException("error unmarshalling arguments", var60);
            } catch (ClassNotFoundException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
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
            ((DirContext)var3).bind((String)var2[0], (Object)var2[1], (Attributes)var2[2]);
            return null;
         case 3:
            ((Context)var3).bind((Name)var2[0], (Object)var2[1]);
            return null;
         case 4:
            ((DirContext)var3).bind((Name)var2[0], (Object)var2[1], (Attributes)var2[2]);
            return null;
         case 5:
            ((Context)var3).close();
            return null;
         case 6:
            return ((Context)var3).composeName((String)var2[0], (String)var2[1]);
         case 7:
            return ((Context)var3).composeName((Name)var2[0], (Name)var2[1]);
         case 8:
            return ((Context)var3).createSubcontext((String)var2[0]);
         case 9:
            return ((DirContext)var3).createSubcontext((String)var2[0], (Attributes)var2[1]);
         case 10:
            return ((Context)var3).createSubcontext((Name)var2[0]);
         case 11:
            return ((DirContext)var3).createSubcontext((Name)var2[0], (Attributes)var2[1]);
         case 12:
            ((Context)var3).destroySubcontext((String)var2[0]);
            return null;
         case 13:
            ((Context)var3).destroySubcontext((Name)var2[0]);
            return null;
         case 14:
            return ((DirContext)var3).getAttributes((String)var2[0]);
         case 15:
            return ((DirContext)var3).getAttributes((String)var2[0], (String[])var2[1]);
         case 16:
            return ((DirContext)var3).getAttributes((Name)var2[0]);
         case 17:
            return ((DirContext)var3).getAttributes((Name)var2[0], (String[])var2[1]);
         case 18:
            return ((Context)var3).getEnvironment();
         case 19:
            return ((Context)var3).getNameInNamespace();
         case 20:
            return ((Context)var3).getNameParser((String)var2[0]);
         case 21:
            return ((Context)var3).getNameParser((Name)var2[0]);
         case 22:
            return ((DirContext)var3).getSchema((String)var2[0]);
         case 23:
            return ((DirContext)var3).getSchema((Name)var2[0]);
         case 24:
            return ((DirContext)var3).getSchemaClassDefinition((String)var2[0]);
         case 25:
            return ((DirContext)var3).getSchemaClassDefinition((Name)var2[0]);
         case 26:
            return ((Context)var3).list((String)var2[0]);
         case 27:
            return ((Context)var3).list((Name)var2[0]);
         case 28:
            return ((Context)var3).listBindings((String)var2[0]);
         case 29:
            return ((Context)var3).listBindings((Name)var2[0]);
         case 30:
            return ((Context)var3).lookup((String)var2[0]);
         case 31:
            return ((Context)var3).lookup((Name)var2[0]);
         case 32:
            return ((Context)var3).lookupLink((String)var2[0]);
         case 33:
            return ((Context)var3).lookupLink((Name)var2[0]);
         case 34:
            ((DirContext)var3).modifyAttributes((String)var2[0], (Integer)var2[1], (Attributes)var2[2]);
            return null;
         case 35:
            ((DirContext)var3).modifyAttributes((String)var2[0], (ModificationItem[])var2[1]);
            return null;
         case 36:
            ((DirContext)var3).modifyAttributes((Name)var2[0], (Integer)var2[1], (Attributes)var2[2]);
            return null;
         case 37:
            ((DirContext)var3).modifyAttributes((Name)var2[0], (ModificationItem[])var2[1]);
            return null;
         case 38:
            ((Context)var3).rebind((String)var2[0], (Object)var2[1]);
            return null;
         case 39:
            ((DirContext)var3).rebind((String)var2[0], (Object)var2[1], (Attributes)var2[2]);
            return null;
         case 40:
            ((Context)var3).rebind((Name)var2[0], (Object)var2[1]);
            return null;
         case 41:
            ((DirContext)var3).rebind((Name)var2[0], (Object)var2[1], (Attributes)var2[2]);
            return null;
         case 42:
            return ((Context)var3).removeFromEnvironment((String)var2[0]);
         case 43:
            ((Context)var3).rename((String)var2[0], (String)var2[1]);
            return null;
         case 44:
            ((Context)var3).rename((Name)var2[0], (Name)var2[1]);
            return null;
         case 45:
            return ((DirContext)var3).search((String)var2[0], (String)var2[1], (SearchControls)var2[2]);
         case 46:
            return ((DirContext)var3).search((String)var2[0], (String)var2[1], (Object[])var2[2], (SearchControls)var2[3]);
         case 47:
            return ((DirContext)var3).search((String)var2[0], (Attributes)var2[1]);
         case 48:
            return ((DirContext)var3).search((String)var2[0], (Attributes)var2[1], (String[])var2[2]);
         case 49:
            return ((DirContext)var3).search((Name)var2[0], (String)var2[1], (SearchControls)var2[2]);
         case 50:
            return ((DirContext)var3).search((Name)var2[0], (String)var2[1], (Object[])var2[2], (SearchControls)var2[3]);
         case 51:
            return ((DirContext)var3).search((Name)var2[0], (Attributes)var2[1]);
         case 52:
            return ((DirContext)var3).search((Name)var2[0], (Attributes)var2[1], (String[])var2[2]);
         case 53:
            ((Context)var3).unbind((String)var2[0]);
            return null;
         case 54:
            ((Context)var3).unbind((Name)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
