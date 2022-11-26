package weblogic.corba.idl;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.Environment;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnknownUserException;
import org.omg.CORBA.UserException;
import org.omg.CORBA.WrongTransaction;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import weblogic.iiop.IIOPInputStream;
import weblogic.iiop.IIOPOutputStream;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.protocol.CorbaInputStream;

class RequestImpl extends Request {
   private RemoteDelegateImpl delegate;
   private final NVList arguments;
   private final String operationName;
   private final NamedValue returnValue;
   private final ExceptionList exceptions;
   private final Environment env;
   private RequestMessage deferredRequest;
   private final Object target;
   private TypeCode returnType;
   private static final String RESULT = "result";

   RequestImpl(Object target, RemoteDelegateImpl delegate, String operationName, NVList arguments, NamedValue returnValue, ExceptionList exceptions) {
      this.delegate = delegate;
      this.operationName = operationName;
      this.target = target;
      if (arguments == null) {
         this.arguments = new NVListImpl(2);
      } else {
         this.arguments = arguments;
      }

      if (exceptions == null) {
         this.exceptions = new ExceptionListImpl();
      } else {
         this.exceptions = exceptions;
      }

      if (returnValue == null) {
         this.returnValue = new NamedValueImpl("result", new AnyImpl(), 2);
      } else {
         this.returnValue = returnValue;
      }

      this.env = new EnvironmentImpl();
   }

   private RequestMessage marshalArgs(boolean repsonseExpected) throws Bounds {
      IIOPOutputStream out = (IIOPOutputStream)this.delegate.request((Object)null, this.operationName, repsonseExpected);
      int i = 0;

      while(i < this.arguments.count()) {
         NamedValue nv = this.arguments.item(i);
         switch (nv.flags()) {
            case 1:
            case 3:
               out.write_any(nv.value(), nv.value().type());
            default:
               ++i;
         }
      }

      return (RequestMessage)out.getMessage();
   }

   private void unmarshalArgs(IIOPInputStream in) throws Bounds {
      if (this.returnType != null) {
         this.returnValue.value().type(this.returnType);
         in.read_any(this.returnValue.value(), this.returnType);
      }

      int i = 0;

      while(i < this.arguments.count()) {
         NamedValue nv = this.arguments.item(i);
         switch (nv.flags()) {
            case 2:
            case 3:
               in.read_any(nv.value(), nv.value().type());
            default:
               ++i;
         }
      }

      this.env.clear();
   }

   public void invoke() {
      IIOPInputStream in = null;
      if (this.delegate == null) {
         throw new BAD_INV_ORDER("invoke() already called", 1330446346, CompletionStatus.COMPLETED_NO);
      } else {
         try {
            RequestMessage request = this.marshalArgs(true);
            in = (IIOPInputStream)this.delegate.invokeAndRecover(this.target, request);
            this.unmarshalArgs(in);
         } catch (RemarshalException var13) {
            this.invoke();
         } catch (ApplicationException var14) {
            ApplicationException ae = var14;

            try {
               for(int i = 0; i < this.exceptions.count(); ++i) {
                  if (this.exceptions.item(i).id().equals(ae.getId())) {
                     Any any = new AnyImpl();
                     any.read_value(ae.getInputStream(), this.exceptions.item(i));
                     this.env.exception(new UnknownUserException(any));
                  }
               }
            } catch (UserException var12) {
            }
         } catch (Bounds var15) {
            throw new AssertionError(var15.toString() + " thrown");
         } catch (SystemException var16) {
            this.env.exception(var16);
            throw var16;
         } finally {
            this.delegate.releaseReply((Object)null, in);
            this.delegate = null;
         }

      }
   }

   public Object target() {
      return this.target;
   }

   public String operation() {
      return this.operationName;
   }

   public NamedValue result() {
      return this.returnValue;
   }

   public NVList arguments() {
      return this.arguments;
   }

   public Environment env() {
      return this.env;
   }

   public ExceptionList exceptions() {
      return this.exceptions;
   }

   public ContextList contexts() {
      throw new NO_IMPLEMENT();
   }

   public void ctx(Context context) {
      throw new NO_IMPLEMENT();
   }

   public Context ctx() {
      throw new NO_IMPLEMENT();
   }

   public Any add_in_arg() {
      return this.arguments.add(1).value();
   }

   public Any add_named_in_arg(String string) {
      return this.arguments.add_item(string, 1).value();
   }

   public Any add_inout_arg() {
      return this.arguments.add(3).value();
   }

   public Any add_named_inout_arg(String string) {
      return this.arguments.add_item(string, 3).value();
   }

   public Any add_out_arg() {
      return this.arguments.add(2).value();
   }

   public Any add_named_out_arg(String string) {
      return this.arguments.add_item(string, 2).value();
   }

   public void set_return_type(TypeCode typeCode) {
      this.returnType = typeCode;
   }

   public Any return_value() {
      if (this.returnType == null) {
         throw new BAD_INV_ORDER("Cannot access return value before invoke");
      } else {
         return this.returnValue.value();
      }
   }

   public void send_oneway() {
      if (this.delegate == null) {
         throw new BAD_INV_ORDER("send_oneway() already called", 1330446346, CompletionStatus.COMPLETED_NO);
      } else {
         try {
            RequestMessage request = this.marshalArgs(false);
            this.delegate.sendOneway(request);
            this.delegate = null;
         } catch (Bounds var2) {
            throw new AssertionError(var2.toString() + " thrown");
         } catch (SystemException var3) {
            this.env.exception(var3);
            throw var3;
         }
      }
   }

   public void send_deferred() {
      if (this.deferredRequest == null && this.delegate != null) {
         try {
            this.deferredRequest = this.marshalArgs(true);
            this.delegate.sendDeferred(this.deferredRequest);
         } catch (Bounds var2) {
            throw new AssertionError(var2.toString() + " thrown");
         } catch (SystemException var3) {
            this.env.exception(var3);
            throw var3;
         }
      } else {
         throw new BAD_INV_ORDER("send_deferred() already called", 1330446346, CompletionStatus.COMPLETED_NO);
      }
   }

   public boolean poll_response() {
      if (this.delegate == null) {
         throw new BAD_INV_ORDER("poll_response() after invoke()", 1330446349, CompletionStatus.COMPLETED_NO);
      } else if (this.deferredRequest == null) {
         throw new BAD_INV_ORDER("poll_response() without outstanding request", 1330446347, CompletionStatus.COMPLETED_NO);
      } else {
         return this.deferredRequest.pollResponse();
      }
   }

   public void get_response() throws WrongTransaction {
      if (this.delegate == null) {
         throw new BAD_INV_ORDER("get_response() after invoke()", 1330446349, CompletionStatus.COMPLETED_NO);
      } else if (this.deferredRequest == null) {
         throw new BAD_INV_ORDER("get_response() without outstanding request", 1330446347, CompletionStatus.COMPLETED_NO);
      } else {
         CorbaInputStream in = null;

         try {
            ReplyMessage reply = (ReplyMessage)this.deferredRequest.getReply();
            in = this.delegate.postInvoke(this.target, this.deferredRequest, reply);
            this.unmarshalArgs((IIOPInputStream)in);
         } catch (RemarshalException var13) {
            this.env.exception(var13);
         } catch (ApplicationException var14) {
            ApplicationException ae = var14;

            try {
               for(int i = 0; i < this.exceptions.count(); ++i) {
                  if (this.exceptions.item(i).id().equals(ae.getId())) {
                     Any any = new AnyImpl();
                     any.read_value(ae.getInputStream(), this.exceptions.item(i));
                     this.env.exception(new UnknownUserException(any));
                  }
               }
            } catch (UserException var12) {
            }
         } catch (Bounds var15) {
            throw new AssertionError(var15.toString() + " thrown");
         } catch (SystemException var16) {
            this.env.exception(var16);
            throw var16;
         } finally {
            this.deferredRequest = null;
            this.delegate.releaseReply((Object)null, in);
            this.delegate = null;
         }

      }
   }
}
