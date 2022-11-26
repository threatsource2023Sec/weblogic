package weblogic.corba.cos.naming;

import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CosNaming.BindingHelper;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingListHelper;
import org.omg.CosNaming.BindingListHolder;

public abstract class _BindingIteratorImplBase extends ObjectImpl implements BindingIterator, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         switch (__method) {
            case 0:
               BindingHolder b = new BindingHolder();
               boolean $result = false;
               $result = this.next_one(b);
               out = $rh.createReply();
               out.write_boolean($result);
               BindingHelper.write(out, b.value);
               break;
            case 1:
               int how_many = in.read_ulong();
               BindingListHolder bl = new BindingListHolder();
               boolean $result = false;
               $result = this.next_n(how_many, bl);
               out = $rh.createReply();
               out.write_boolean($result);
               BindingListHelper.write(out, bl.value);
               break;
            case 2:
               this.destroy();
               out = $rh.createReply();
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
      _methods.put("next_one", 0);
      _methods.put("next_n", 1);
      _methods.put("destroy", 2);
      __ids = new String[]{"IDL:weblogic/corba/cos/naming/BindingIterator:1.0", "IDL:omg.org/CosNaming/BindingIterator:1.0"};
   }
}
