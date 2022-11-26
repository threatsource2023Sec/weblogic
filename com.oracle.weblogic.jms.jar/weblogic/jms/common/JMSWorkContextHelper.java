package weblogic.jms.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextInput;
import weblogic.workarea.WorkContextOutput;
import weblogic.workarea.spi.WorkContextMapInterceptor;
import weblogic.workarea.utils.WorkContextInputAdapter;
import weblogic.workarea.utils.WorkContextOutputAdapter;

public class JMSWorkContextHelper {
   private static final boolean DEBUG = false;
   private static WorkContextHelper helper = WorkContextHelper.getWorkContextHelper();

   public static void infectMessage(MessageImpl message) {
      WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getLocalInterceptor();
      if (interceptor != null) {
         message.setWorkContext(interceptor.copyThreadContexts(48));
      }

   }

   public static void infectThread(MessageImpl message) {
      Object o = message.getWorkContext();
      if (o != null) {
         WorkContextHelper.getWorkContextHelper().setLocalInterceptor((WorkContextMapInterceptor)o);
      }

   }

   public static void disinfectThread() {
      WorkContextHelper.getWorkContextHelper().setLocalInterceptor((WorkContextMapInterceptor)null);
   }

   static void writeWorkContext(Object workContext, ObjectOutput out) throws IOException {
      WorkContextMapInterceptor i = (WorkContextMapInterceptor)workContext;
      if (out instanceof WorkContextOutput) {
         i.sendRequest((WorkContextOutput)out, 48);
      } else {
         i.sendRequest(new WorkContextOutputAdapter(out), 48);
      }

   }

   static Object readWorkContext(ObjectInput in) throws IOException {
      WorkContextMapInterceptor i = WorkContextHelper.getWorkContextHelper().createInterceptor();
      if (in instanceof WorkContextInput) {
         i.receiveRequest((WorkContextInput)in);
      } else {
         i.receiveRequest(new WorkContextInputAdapter(in));
      }

      return i;
   }

   private static void p(String msg) {
      System.out.println("<JMSWorkContextHelper>: " + msg);
   }
}
