package weblogic.rmi.provider;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.rmi.spi.ServiceContext;
import weblogic.utils.Debug;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextInput;
import weblogic.workarea.WorkContextOutput;
import weblogic.workarea.spi.WorkContextMapInterceptor;

public class WorkServiceContext implements ServiceContext, Externalizable {
   private transient boolean request;

   public WorkServiceContext() {
   }

   public WorkServiceContext(boolean request) {
      this.request = request;
   }

   public int getContextId() {
      return 5;
   }

   public Object getContextData() {
      return null;
   }

   public boolean isUser() {
      return true;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.write(5);
      out.writeBoolean(this.request);
      WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getLocalInterceptor();
      if (interceptor != null) {
         if (this.request) {
            interceptor.sendRequest((WorkContextOutput)out, 4);
         } else {
            interceptor.sendResponse((WorkContextOutput)out, 4);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int id = in.read();
      this.request = in.readBoolean();
      Debug.assertion(id == 5);
      WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getInterceptor();
      if (this.request) {
         interceptor.receiveRequest((WorkContextInput)in);
      } else {
         interceptor.receiveResponse((WorkContextInput)in);
      }

   }

   public String toString() {
      return "WorkServiceContext";
   }
}
