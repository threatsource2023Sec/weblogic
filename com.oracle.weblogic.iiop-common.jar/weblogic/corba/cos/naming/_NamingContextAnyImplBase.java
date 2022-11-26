package weblogic.corba.cos.naming;

import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Object;
import org.omg.CORBA.ObjectHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
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

public abstract class _NamingContextAnyImplBase extends ObjectImpl implements NamingContextAny, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         BindingListHolder $result;
         org.omg.CosNaming.BindingIteratorHolder $result;
         NameComponent[] n;
         NamingContext $result;
         Object $result;
         String n;
         String sn;
         WNameComponent[] n;
         Any $result;
         switch (__method) {
            case 0:
               try {
                  n = WNameHelper.read(in);
                  $result = in.read_any();
                  this.bind_any(n, $result);
                  out = $rh.createReply();
               } catch (NotFound var54) {
                  out = $rh.createExceptionReply();
                  NotFoundHelper.write(out, var54);
               } catch (CannotProceed var55) {
                  out = $rh.createExceptionReply();
                  CannotProceedHelper.write(out, var55);
               } catch (AppException var56) {
                  out = $rh.createExceptionReply();
                  AppExceptionHelper.write(out, var56);
               } catch (InvalidName var57) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var57);
               } catch (AlreadyBound var58) {
                  out = $rh.createExceptionReply();
                  AlreadyBoundHelper.write(out, var58);
               } catch (TypeNotSupported var59) {
                  out = $rh.createExceptionReply();
                  TypeNotSupportedHelper.write(out, var59);
               }
               break;
            case 1:
               try {
                  n = WNameHelper.read(in);
                  $result = in.read_any();
                  this.rebind_any(n, $result);
                  out = $rh.createReply();
               } catch (NotFound var49) {
                  out = $rh.createExceptionReply();
                  NotFoundHelper.write(out, var49);
               } catch (CannotProceed var50) {
                  out = $rh.createExceptionReply();
                  CannotProceedHelper.write(out, var50);
               } catch (AppException var51) {
                  out = $rh.createExceptionReply();
                  AppExceptionHelper.write(out, var51);
               } catch (InvalidName var52) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var52);
               } catch (TypeNotSupported var53) {
                  out = $rh.createExceptionReply();
                  TypeNotSupportedHelper.write(out, var53);
               }
               break;
            case 2:
               try {
                  n = WNameHelper.read(in);
                  $result = null;
                  $result = this.resolve_any(n);
                  out = $rh.createReply();
                  out.write_any($result);
               } catch (NotFound var45) {
                  out = $rh.createExceptionReply();
                  NotFoundHelper.write(out, var45);
               } catch (CannotProceed var46) {
                  out = $rh.createExceptionReply();
                  CannotProceedHelper.write(out, var46);
               } catch (AppException var47) {
                  out = $rh.createExceptionReply();
                  AppExceptionHelper.write(out, var47);
               } catch (InvalidName var48) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var48);
               }
               break;
            case 3:
               try {
                  n = WStringNameHelper.read(in);
                  $result = null;
                  $result = this.resolve_str_any(n);
                  out = $rh.createReply();
                  out.write_any($result);
               } catch (NotFound var41) {
                  out = $rh.createExceptionReply();
                  NotFoundHelper.write(out, var41);
               } catch (CannotProceed var42) {
                  out = $rh.createExceptionReply();
                  CannotProceedHelper.write(out, var42);
               } catch (AppException var43) {
                  out = $rh.createExceptionReply();
                  AppExceptionHelper.write(out, var43);
               } catch (InvalidName var44) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var44);
               }
               break;
            case 4:
               try {
                  n = NameHelper.read(in);
                  $result = null;
                  sn = this.to_string(n);
                  out = $rh.createReply();
                  out.write_string(sn);
               } catch (InvalidName var40) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var40);
               }
               break;
            case 5:
               try {
                  n = StringNameHelper.read(in);
                  $result = null;
                  NameComponent[] $result = this.to_name(n);
                  out = $rh.createReply();
                  NameHelper.write(out, $result);
               } catch (InvalidName var39) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var39);
               }
               break;
            case 6:
               try {
                  n = AddressHelper.read(in);
                  sn = StringNameHelper.read(in);
                  $result = null;
                  String $result = this.to_url(n, sn);
                  out = $rh.createReply();
                  out.write_string($result);
               } catch (InvalidAddress var37) {
                  out = $rh.createExceptionReply();
                  InvalidAddressHelper.write(out, var37);
               } catch (InvalidName var38) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var38);
               }
               break;
            case 7:
               try {
                  n = StringNameHelper.read(in);
                  $result = null;
                  $result = this.resolve_str(n);
                  out = $rh.createReply();
                  ObjectHelper.write(out, $result);
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var34) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var34);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var35) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var35);
               } catch (InvalidName var36) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var36);
               }
               break;
            case 8:
               try {
                  n = NameHelper.read(in);
                  $result = ObjectHelper.read(in);
                  this.bind(n, $result);
                  out = $rh.createReply();
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var30) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var30);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var31) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var31);
               } catch (InvalidName var32) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var32);
               } catch (AlreadyBound var33) {
                  out = $rh.createExceptionReply();
                  AlreadyBoundHelper.write(out, var33);
               }
               break;
            case 9:
               try {
                  n = NameHelper.read(in);
                  $result = ObjectHelper.read(in);
                  this.rebind(n, $result);
                  out = $rh.createReply();
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var27) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var27);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var28) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var28);
               } catch (InvalidName var29) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var29);
               }
               break;
            case 10:
               try {
                  n = NameHelper.read(in);
                  $result = NamingContextHelper.read(in);
                  this.bind_context(n, $result);
                  out = $rh.createReply();
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var23) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var23);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var24) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var24);
               } catch (InvalidName var25) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var25);
               } catch (AlreadyBound var26) {
                  out = $rh.createExceptionReply();
                  AlreadyBoundHelper.write(out, var26);
               }
               break;
            case 11:
               try {
                  n = NameHelper.read(in);
                  $result = NamingContextHelper.read(in);
                  this.rebind_context(n, $result);
                  out = $rh.createReply();
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var20) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var20);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var21) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var21);
               } catch (InvalidName var22) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var22);
               }
               break;
            case 12:
               try {
                  n = NameHelper.read(in);
                  $result = null;
                  $result = this.resolve(n);
                  out = $rh.createReply();
                  ObjectHelper.write(out, $result);
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var17) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var17);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var18) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var18);
               } catch (InvalidName var19) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var19);
               }
               break;
            case 13:
               try {
                  n = NameHelper.read(in);
                  this.unbind(n);
                  out = $rh.createReply();
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var14) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var14);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var15) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var15);
               } catch (InvalidName var16) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var16);
               }
               break;
            case 14:
               n = null;
               NamingContext $result = this.new_context();
               out = $rh.createReply();
               NamingContextHelper.write(out, $result);
               break;
            case 15:
               try {
                  n = NameHelper.read(in);
                  $result = null;
                  $result = this.bind_new_context(n);
                  out = $rh.createReply();
                  NamingContextHelper.write(out, $result);
               } catch (org.omg.CosNaming.NamingContextPackage.NotFound var10) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.NotFoundHelper.write(out, var10);
               } catch (AlreadyBound var11) {
                  out = $rh.createExceptionReply();
                  AlreadyBoundHelper.write(out, var11);
               } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed var12) {
                  out = $rh.createExceptionReply();
                  org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.write(out, var12);
               } catch (InvalidName var13) {
                  out = $rh.createExceptionReply();
                  InvalidNameHelper.write(out, var13);
               }
               break;
            case 16:
               try {
                  this.destroy();
                  out = $rh.createReply();
               } catch (NotEmpty var9) {
                  out = $rh.createExceptionReply();
                  NotEmptyHelper.write(out, var9);
               }
               break;
            case 17:
               int how_many = in.read_ulong();
               $result = new BindingListHolder();
               $result = new org.omg.CosNaming.BindingIteratorHolder();
               this.list(how_many, $result, $result);
               out = $rh.createReply();
               BindingListHelper.write(out, $result.value);
               org.omg.CosNaming.BindingIteratorHelper.write(out, $result.value);
               break;
            default:
               throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
         }

         return out;
      }
   }

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }

   static {
      _methods.put("bind_any", 0);
      _methods.put("rebind_any", 1);
      _methods.put("resolve_any", 2);
      _methods.put("resolve_str_any", 3);
      _methods.put("to_string", 4);
      _methods.put("to_name", 5);
      _methods.put("to_url", 6);
      _methods.put("resolve_str", 7);
      _methods.put("bind", 8);
      _methods.put("rebind", 9);
      _methods.put("bind_context", 10);
      _methods.put("rebind_context", 11);
      _methods.put("resolve", 12);
      _methods.put("unbind", 13);
      _methods.put("new_context", 14);
      _methods.put("bind_new_context", 15);
      _methods.put("destroy", 16);
      _methods.put("list", 17);
      __ids = new String[]{"IDL:weblogic/corba/cos/naming/NamingContextAny:1.0", "IDL:omg.org/CosNaming/NamingContextExt:1.0", "IDL:omg.org/CosNaming/NamingContext:1.0"};
   }
}
