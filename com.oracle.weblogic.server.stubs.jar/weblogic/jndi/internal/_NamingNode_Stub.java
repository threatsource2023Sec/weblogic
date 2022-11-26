package weblogic.jndi.internal;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.event.NamingListener;
import javax.rmi.PortableRemoteObject;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class _NamingNode_Stub extends Stub implements NamingNode {
   private static String[] _type_ids = new String[]{"RMI:weblogic.jndi.internal.NamingNode:0000000000000000"};
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
   private static Class class$javax$naming$NamingException;
   // $FF: synthetic field
   private static Class class$javax$naming$event$NamingListener;
   // $FF: synthetic field
   private static Class class$weblogic$jndi$internal$NamingNode;
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$internal$AuthenticatedSubject;

   public String[] _ids() {
      return _type_ids;
   }

   public final void addNamingListener(String var1, int var2, NamingListener var3, Hashtable var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("addNamingListener", true);
            var6.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var6.write_long(var2);
            var6.write_value(var3, class$javax$naming$event$NamingListener == null ? (class$javax$naming$event$NamingListener = class$("javax.naming.event.NamingListener")) : class$javax$naming$event$NamingListener);
            var6.write_value(var4, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var5.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.addNamingListener(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void addOneLevelScopeNamingListener(NamingListener var1) throws RemoteException {
      try {
         InputStream var2 = null;

         try {
            OutputStream var3 = (OutputStream)this._request("addOneLevelScopeNamingListener", true);
            var3.write_value(var1, class$javax$naming$event$NamingListener == null ? (class$javax$naming$event$NamingListener = class$("javax.naming.event.NamingListener")) : class$javax$naming$event$NamingListener);
            this._invoke(var3);
         } catch (ApplicationException var11) {
            var2 = (InputStream)var11.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var12) {
            this.addOneLevelScopeNamingListener(var1);
         } finally {
            this._releaseReply(var2);
         }

      } catch (SystemException var14) {
         throw Util.mapSystemException(var14);
      }
   }

   public final Object authenticatedLookup(String var1, Hashtable var2, AuthenticatedSubject var3) throws RemoteException {
      try {
         InputStream var4 = null;

         Object var8;
         try {
            OutputStream var5 = (OutputStream)this._request("authenticatedLookup", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var5.write_value(var3, class$weblogic$security$acl$internal$AuthenticatedSubject == null ? (class$weblogic$security$acl$internal$AuthenticatedSubject = class$("weblogic.security.acl.internal.AuthenticatedSubject")) : class$weblogic$security$acl$internal$AuthenticatedSubject);
            var4 = (InputStream)this._invoke(var5);
            Object var6 = (Object)Util.readAny(var4);
            return var6;
         } catch (ApplicationException var15) {
            var4 = (InputStream)var15.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var4.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var16) {
            var8 = this.authenticatedLookup(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

         return var8;
      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
      }
   }

   public final void bind(String var1, Object var2, Hashtable var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("bind", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            Util.writeAny(var5, var2);
            var5.write_value(var3, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var4.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.bind(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final Context createSubcontext(String var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         Context var7;
         try {
            OutputStream var4 = (OutputStream)this._request("createSubcontext", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var3 = (InputStream)this._invoke(var4);
            Context var5 = (Context)var3.read_value(class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.createSubcontext(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void destroySubcontext(String var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("destroySubcontext", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.destroySubcontext(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final Context getContext(Hashtable var1) throws RemoteException {
      try {
         InputStream var2 = null;

         Context var6;
         try {
            OutputStream var3 = (OutputStream)this._request("getContext", true);
            var3.write_value(var1, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var2 = (InputStream)this._invoke(var3);
            Context var4 = (Context)var2.read_value(class$javax$naming$Context == null ? (class$javax$naming$Context = class$("javax.naming.Context")) : class$javax$naming$Context);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.getContext(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final String getNameInNamespace() throws RemoteException {
      try {
         InputStream var1 = null;

         String var5;
         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("_get_nameInNamespace__", true);
            var1 = (InputStream)this._invoke(var2);
            String var3 = (String)var1.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getNameInNamespace();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final String getNameInNamespace(String var1) throws RemoteException {
      try {
         InputStream var2 = null;

         String var6;
         try {
            OutputStream var3 = (OutputStream)this._request("getNameInNamespace__CORBA_WStringValue", true);
            var3.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var2 = (InputStream)this._invoke(var3);
            String var4 = (String)var2.read_value(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            return var4;
         } catch (ApplicationException var13) {
            var2 = (InputStream)var13.getInputStream();
            String var5 = var2.read_string();
            if (var5.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var2.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var5);
         } catch (RemarshalException var14) {
            var6 = this.getNameInNamespace(var1);
         } finally {
            this._releaseReply(var2);
         }

         return var6;
      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final NameParser getNameParser(String var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         NameParser var7;
         try {
            OutputStream var4 = (OutputStream)this._request("getNameParser", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var3 = (InputStream)this._invoke(var4);
            NameParser var5 = (NameParser)var3.read_value(class$javax$naming$NameParser == null ? (class$javax$naming$NameParser = class$("javax.naming.NameParser")) : class$javax$naming$NameParser);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.getNameParser(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final List getOneLevelScopeNamingListeners() throws RemoteException {
      try {
         InputStream var1 = null;

         List var5;
         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("_get_oneLevelScopeNamingListeners", true);
            var1 = (InputStream)this._invoke(var2);
            List var3 = (List)var1.read_value(class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getOneLevelScopeNamingListeners();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final NamingNode getParent() throws RemoteException {
      try {
         InputStream var1 = null;

         NamingNode var5;
         try {
            org.omg.CORBA.portable.OutputStream var2 = this._request("_get_parent", true);
            var1 = (InputStream)this._invoke(var2);
            NamingNode var3 = (NamingNode)PortableRemoteObject.narrow((NamingNode)var1.read_Object(), class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode);
            return var3;
         } catch (ApplicationException var12) {
            var1 = (InputStream)var12.getInputStream();
            String var4 = var1.read_string();
            throw new UnexpectedException(var4);
         } catch (RemarshalException var13) {
            var5 = this.getParent();
         } finally {
            this._releaseReply(var1);
         }

         return var5;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final boolean isBindable(String var1, Object var2, Hashtable var3) throws RemoteException {
      try {
         Object var4 = null;

         boolean var8;
         try {
            OutputStream var5 = (OutputStream)this._request("isBindable__CORBA_WStringValue__java_lang_Object__java_util_Hashtable", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            Util.writeAny(var5, var2);
            var5.write_value(var3, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var4 = this._invoke(var5);
            boolean var6 = ((org.omg.CORBA.portable.InputStream)var4).read_boolean();
            return var6;
         } catch (ApplicationException var15) {
            var4 = (InputStream)var15.getInputStream();
            String var7 = ((org.omg.CORBA.portable.InputStream)var4).read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)((InputStream)var4).read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var16) {
            var8 = this.isBindable(var1, var2, var3);
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var4);
         }

         return var8;
      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
      }
   }

   public final boolean isBindable(String var1, boolean var2, Hashtable var3) throws RemoteException {
      try {
         Object var4 = null;

         boolean var8;
         try {
            OutputStream var5 = (OutputStream)this._request("isBindable__CORBA_WStringValue__boolean__java_util_Hashtable", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_boolean(var2);
            var5.write_value(var3, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var4 = this._invoke(var5);
            boolean var6 = ((org.omg.CORBA.portable.InputStream)var4).read_boolean();
            return var6;
         } catch (ApplicationException var15) {
            var4 = (InputStream)var15.getInputStream();
            String var7 = ((org.omg.CORBA.portable.InputStream)var4).read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)((InputStream)var4).read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var16) {
            var8 = this.isBindable(var1, var2, var3);
         } finally {
            this._releaseReply((org.omg.CORBA.portable.InputStream)var4);
         }

         return var8;
      } catch (SystemException var18) {
         throw Util.mapSystemException(var18);
      }
   }

   public final NamingEnumeration list(String var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         NamingEnumeration var7;
         try {
            OutputStream var4 = (OutputStream)this._request("list", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var3 = (InputStream)this._invoke(var4);
            NamingEnumeration var5 = (NamingEnumeration)var3.read_value(class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.list(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final NamingEnumeration listBindings(String var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         NamingEnumeration var7;
         try {
            OutputStream var4 = (OutputStream)this._request("listBindings", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var3 = (InputStream)this._invoke(var4);
            NamingEnumeration var5 = (NamingEnumeration)var3.read_value(class$javax$naming$NamingEnumeration == null ? (class$javax$naming$NamingEnumeration = class$("javax.naming.NamingEnumeration")) : class$javax$naming$NamingEnumeration);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.listBindings(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final Object lookup(String var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         Object var7;
         try {
            OutputStream var4 = (OutputStream)this._request("lookup", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var3 = (InputStream)this._invoke(var4);
            Object var5 = (Object)Util.readAny(var3);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.lookup(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final Object lookupLink(String var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         Object var7;
         try {
            OutputStream var4 = (OutputStream)this._request("lookupLink", true);
            var4.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            var3 = (InputStream)this._invoke(var4);
            Object var5 = (Object)Util.readAny(var3);
            return var5;
         } catch (ApplicationException var14) {
            var3 = (InputStream)var14.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var15) {
            var7 = this.lookupLink(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

         return var7;
      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void rebind(String var1, Object var2, Object var3, Hashtable var4) throws RemoteException {
      try {
         InputStream var5 = null;

         try {
            OutputStream var6 = (OutputStream)this._request("rebind__CORBA_WStringValue__java_lang_Object__java_lang_Object__java_util_Hashtable", true);
            var6.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            Util.writeAny(var6, var2);
            Util.writeAny(var6, var3);
            var6.write_value(var4, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var6);
         } catch (ApplicationException var14) {
            var5 = (InputStream)var14.getInputStream();
            String var8 = var5.read_string();
            if (var8.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var5.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var8);
         } catch (RemarshalException var15) {
            this.rebind(var1, var2, var3, var4);
         } finally {
            this._releaseReply(var5);
         }

      } catch (SystemException var17) {
         throw Util.mapSystemException(var17);
      }
   }

   public final void rebind(String var1, Object var2, Hashtable var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("rebind__CORBA_WStringValue__java_lang_Object__java_util_Hashtable", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            Util.writeAny(var5, var2);
            var5.write_value(var3, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var4.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.rebind(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void rebind(Name var1, Object var2, Hashtable var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("rebind__javax_naming_Name__java_lang_Object__java_util_Hashtable", true);
            var5.write_value(var1, class$javax$naming$Name == null ? (class$javax$naming$Name = class$("javax.naming.Name")) : class$javax$naming$Name);
            Util.writeAny(var5, var2);
            var5.write_value(var3, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var4.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.rebind(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void removeNamingListener(NamingListener var1, Hashtable var2) throws RemoteException {
      try {
         InputStream var3 = null;

         try {
            OutputStream var4 = (OutputStream)this._request("removeNamingListener", true);
            var4.write_value(var1, class$javax$naming$event$NamingListener == null ? (class$javax$naming$event$NamingListener = class$("javax.naming.event.NamingListener")) : class$javax$naming$event$NamingListener);
            var4.write_value(var2, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var4);
         } catch (ApplicationException var12) {
            var3 = (InputStream)var12.getInputStream();
            String var6 = var3.read_string();
            if (var6.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var3.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var6);
         } catch (RemarshalException var13) {
            this.removeNamingListener(var1, var2);
         } finally {
            this._releaseReply(var3);
         }

      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public final void rename(String var1, String var2, Hashtable var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("rename", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            var5.write_value(var3, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var4.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.rename(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }

   public final void unbind(String var1, Object var2, Hashtable var3) throws RemoteException {
      try {
         InputStream var4 = null;

         try {
            OutputStream var5 = (OutputStream)this._request("unbind", true);
            var5.write_value(var1, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            Util.writeAny(var5, var2);
            var5.write_value(var3, class$java$util$Hashtable == null ? (class$java$util$Hashtable = class$("java.util.Hashtable")) : class$java$util$Hashtable);
            this._invoke(var5);
         } catch (ApplicationException var13) {
            var4 = (InputStream)var13.getInputStream();
            String var7 = var4.read_string();
            if (var7.equals("IDL:javax/naming/NamingEx:1.0")) {
               throw (NamingException)((NamingException)var4.read_value(class$javax$naming$NamingException == null ? (class$javax$naming$NamingException = class$("javax.naming.NamingException")) : class$javax$naming$NamingException));
            }

            throw new UnexpectedException(var7);
         } catch (RemarshalException var14) {
            this.unbind(var1, var2, var3);
         } finally {
            this._releaseReply(var4);
         }

      } catch (SystemException var16) {
         throw Util.mapSystemException(var16);
      }
   }
}
