package org.omg.SendingContext;

import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Repository;
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.RepositoryIdSeqHelper;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.SendingContext.CodeBasePackage.URLSeqHelper;
import org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;

public abstract class _CodeBaseImplBase extends ObjectImpl implements CodeBase, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         String x;
         String[] $result;
         String[] x;
         String $result;
         switch (__method) {
            case 0:
               x = null;
               Repository $result = this.get_ir();
               out = $rh.createReply();
               RepositoryHelper.write(out, $result);
               break;
            case 1:
               x = RepositoryIdHelper.read(in);
               $result = null;
               $result = this.implementation(x);
               out = $rh.createReply();
               out.write_string($result);
               break;
            case 2:
               x = RepositoryIdHelper.read(in);
               $result = null;
               $result = this.implementationx(x);
               out = $rh.createReply();
               out.write_string($result);
               break;
            case 3:
               x = RepositoryIdSeqHelper.read(in);
               $result = null;
               $result = this.implementations(x);
               out = $rh.createReply();
               URLSeqHelper.write(out, $result);
               break;
            case 4:
               x = RepositoryIdHelper.read(in);
               $result = null;
               FullValueDescription $result = this.meta(x);
               out = $rh.createReply();
               FullValueDescriptionHelper.write(out, $result);
               break;
            case 5:
               x = RepositoryIdSeqHelper.read(in);
               $result = null;
               FullValueDescription[] $result = this.metas(x);
               out = $rh.createReply();
               ValueDescSeqHelper.write(out, $result);
               break;
            case 6:
               x = RepositoryIdHelper.read(in);
               $result = null;
               $result = this.bases(x);
               out = $rh.createReply();
               RepositoryIdSeqHelper.write(out, $result);
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
      _methods.put("get_ir", 0);
      _methods.put("implementation", 1);
      _methods.put("implementationx", 2);
      _methods.put("implementations", 3);
      _methods.put("meta", 4);
      _methods.put("metas", 5);
      _methods.put("bases", 6);
      __ids = new String[]{"IDL:omg.org/SendingContext/CodeBase:1.0", "IDL:omg.org/SendingContext/RunTime:1.0"};
   }
}
