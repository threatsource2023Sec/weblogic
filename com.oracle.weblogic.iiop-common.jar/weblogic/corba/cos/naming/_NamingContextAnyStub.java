package weblogic.corba.cos.naming;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ObjectHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CosNaming.BindingListHelper;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameHelper;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.omg.CosNaming.NamingContextExtPackage.URLStringHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.InvalidNameHelper;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotEmptyHelper;
import weblogic.corba.cos.naming.NamingContextAnyPackage.AppException;
import weblogic.corba.cos.naming.NamingContextAnyPackage.AppExceptionHelper;
import weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed;
import weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceedHelper;
import weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound;
import weblogic.corba.cos.naming.NamingContextAnyPackage.NotFoundHelper;
import weblogic.corba.cos.naming.NamingContextAnyPackage.TypeNotSupported;
import weblogic.corba.cos.naming.NamingContextAnyPackage.TypeNotSupportedHelper;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameHelper;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WStringNameHelper;

public class _NamingContextAnyStub extends ObjectImpl implements NamingContextAny {
   private static String[] __ids = new String[]{"IDL:weblogic/corba/cos/naming/NamingContextAny:1.0", "IDL:omg.org/CosNaming/NamingContextExt:1.0", "IDL:omg.org/CosNaming/NamingContext:1.0"};

   public void bind_any(WNameComponent[] n, Any obj) throws NotFound, CannotProceed, AppException, InvalidName, AlreadyBound, TypeNotSupported {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("bind_any", true);
         WNameHelper.write($out, n);
         $out.write_any(obj);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/NotFound:1.0")) {
            throw NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/CannotProceed:1.0")) {
            throw CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/AppException:1.0")) {
            throw AppExceptionHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
            throw AlreadyBoundHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/TypeNotSupported:1.0")) {
            throw TypeNotSupportedHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         this.bind_any(n, obj);
      } finally {
         this._releaseReply($in);
      }

   }

   public void rebind_any(WNameComponent[] n, Any obj) throws NotFound, CannotProceed, AppException, InvalidName, TypeNotSupported {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rebind_any", true);
         WNameHelper.write($out, n);
         $out.write_any(obj);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/NotFound:1.0")) {
            throw NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/CannotProceed:1.0")) {
            throw CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/AppException:1.0")) {
            throw AppExceptionHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/TypeNotSupported:1.0")) {
            throw TypeNotSupportedHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         this.rebind_any(n, obj);
      } finally {
         this._releaseReply($in);
      }

   }

   public Any resolve_any(WNameComponent[] n) throws NotFound, CannotProceed, AppException, InvalidName {
      InputStream $in = null;

      Any $result;
      try {
         OutputStream $out = this._request("resolve_any", true);
         WNameHelper.write($out, n);
         $in = this._invoke($out);
         $result = $in.read_any();
         Any var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/NotFound:1.0")) {
            throw NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/CannotProceed:1.0")) {
            throw CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/AppException:1.0")) {
            throw AppExceptionHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.resolve_any(n);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public Any resolve_str_any(String n) throws NotFound, CannotProceed, AppException, InvalidName {
      InputStream $in = null;

      Any $result;
      try {
         OutputStream $out = this._request("resolve_str_any", true);
         WStringNameHelper.write($out, n);
         $in = this._invoke($out);
         $result = $in.read_any();
         Any var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/NotFound:1.0")) {
            throw NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/CannotProceed:1.0")) {
            throw CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:weblogic/corba/cos/naming/NamingContextAny/AppException:1.0")) {
            throw AppExceptionHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.resolve_str_any(n);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public String to_string(NameComponent[] n) throws InvalidName {
      InputStream $in = null;

      String _id;
      try {
         OutputStream $out = this._request("to_string", true);
         NameHelper.write($out, n);
         $in = this._invoke($out);
         _id = StringNameHelper.read($in);
         String var5 = _id;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         _id = this.to_string(n);
      } finally {
         this._releaseReply($in);
      }

      return _id;
   }

   public NameComponent[] to_name(String sn) throws InvalidName {
      InputStream $in = null;

      NameComponent[] $result;
      try {
         OutputStream $out = this._request("to_name", true);
         StringNameHelper.write($out, sn);
         $in = this._invoke($out);
         $result = NameHelper.read($in);
         NameComponent[] var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.to_name(sn);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public String to_url(String addr, String sn) throws InvalidAddress, InvalidName {
      InputStream $in = null;

      String _id;
      try {
         OutputStream $out = this._request("to_url", true);
         AddressHelper.write($out, addr);
         StringNameHelper.write($out, sn);
         $in = this._invoke($out);
         _id = URLStringHelper.read($in);
         String var6 = _id;
         return var6;
      } catch (ApplicationException var11) {
         $in = var11.getInputStream();
         _id = var11.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContextExt/InvalidAddress:1.0")) {
            throw InvalidAddressHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var12) {
         _id = this.to_url(addr, sn);
      } finally {
         this._releaseReply($in);
      }

      return _id;
   }

   public Object resolve_str(String n) throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName {
      InputStream $in = null;

      Object $result;
      try {
         OutputStream $out = this._request("resolve_str", true);
         StringNameHelper.write($out, n);
         $in = this._invoke($out);
         $result = ObjectHelper.read($in);
         Object var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.resolve_str(n);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public void bind(NameComponent[] n, Object obj) throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName, AlreadyBound {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("bind", true);
         NameHelper.write($out, n);
         ObjectHelper.write($out, obj);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
            throw AlreadyBoundHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         this.bind(n, obj);
      } finally {
         this._releaseReply($in);
      }

   }

   public void rebind(NameComponent[] n, Object obj) throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rebind", true);
         NameHelper.write($out, n);
         ObjectHelper.write($out, obj);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         this.rebind(n, obj);
      } finally {
         this._releaseReply($in);
      }

   }

   public void bind_context(NameComponent[] n, NamingContext nc) throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName, AlreadyBound {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("bind_context", true);
         NameHelper.write($out, n);
         NamingContextHelper.write($out, nc);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
            throw AlreadyBoundHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         this.bind_context(n, nc);
      } finally {
         this._releaseReply($in);
      }

   }

   public void rebind_context(NameComponent[] n, NamingContext nc) throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("rebind_context", true);
         NameHelper.write($out, n);
         NamingContextHelper.write($out, nc);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         this.rebind_context(n, nc);
      } finally {
         this._releaseReply($in);
      }

   }

   public Object resolve(NameComponent[] n) throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName {
      InputStream $in = null;

      Object $result;
      try {
         OutputStream $out = this._request("resolve", true);
         NameHelper.write($out, n);
         $in = this._invoke($out);
         $result = ObjectHelper.read($in);
         Object var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.resolve(n);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public void unbind(NameComponent[] n) throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("unbind", true);
         NameHelper.write($out, n);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         this.unbind(n);
      } finally {
         this._releaseReply($in);
      }

   }

   public NamingContext new_context() {
      InputStream $in = null;

      NamingContext $result;
      try {
         OutputStream $out = this._request("new_context", true);
         $in = this._invoke($out);
         $result = NamingContextHelper.read($in);
         NamingContext var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.new_context();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public NamingContext bind_new_context(NameComponent[] n) throws org.omg.CosNaming.NamingContextPackage.NotFound, AlreadyBound, org.omg.CosNaming.NamingContextPackage.CannotProceed, InvalidName {
      InputStream $in = null;

      NamingContext $result;
      try {
         OutputStream $out = this._request("bind_new_context", true);
         NameHelper.write($out, n);
         $in = this._invoke($out);
         $result = NamingContextHelper.read($in);
         NamingContext var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
            throw AlreadyBoundHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0")) {
            throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.read($in);
         }

         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
            throw InvalidNameHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.bind_new_context(n);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public void destroy() throws NotEmpty {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("destroy", true);
         $in = this._invoke($out);
         return;
      } catch (ApplicationException var8) {
         $in = var8.getInputStream();
         String _id = var8.getId();
         if (_id.equals("IDL:omg.org/CosNaming/NamingContext/NotEmpty:1.0")) {
            throw NotEmptyHelper.read($in);
         }

         throw new MARSHAL(_id);
      } catch (RemarshalException var9) {
         this.destroy();
      } finally {
         this._releaseReply($in);
      }

   }

   public void list(int how_many, BindingListHolder bl, org.omg.CosNaming.BindingIteratorHolder bi) {
      InputStream $in = null;

      try {
         OutputStream $out = this._request("list", true);
         $out.write_ulong(how_many);
         $in = this._invoke($out);
         bl.value = BindingListHelper.read($in);
         bi.value = org.omg.CosNaming.BindingIteratorHelper.read($in);
         return;
      } catch (ApplicationException var11) {
         $in = var11.getInputStream();
         String _id = var11.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var12) {
         this.list(how_many, bl, bi);
      } finally {
         this._releaseReply($in);
      }

   }

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }

   private void readObject(ObjectInputStream s) throws IOException {
      String str = s.readUTF();
      String[] args = null;
      Properties props = null;
      ORB orb = ORB.init((String[])args, (Properties)props);

      try {
         Object obj = orb.string_to_object(str);
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         this._set_delegate(delegate);
      } finally {
         orb.destroy();
      }

   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      String[] args = null;
      Properties props = null;
      ORB orb = ORB.init((String[])args, (Properties)props);

      try {
         String str = orb.object_to_string(this);
         s.writeUTF(str);
      } finally {
         orb.destroy();
      }

   }
}
