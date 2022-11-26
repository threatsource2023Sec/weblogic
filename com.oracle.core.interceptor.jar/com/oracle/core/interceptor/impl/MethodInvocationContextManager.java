package com.oracle.core.interceptor.impl;

import com.oracle.core.interceptor.MethodInvocationContext;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class MethodInvocationContextManager {
   ThreadLocal current = new ThreadLocal();
   ThreadLocal previous = new ThreadLocal();

   public void pushMethodInvocationContext(MethodInvocationContext ctx) {
      List list = (List)this.current.get();
      if (list == null) {
         list = new ArrayList();
         this.current.set(list);
      }

      ((List)list).add(ctx);
   }

   public MethodInvocationContext popMethodInvocationContext() {
      List list = (List)this.current.get();
      MethodInvocationContext popped = list != null && !list.isEmpty() ? (MethodInvocationContext)list.remove(list.size() - 1) : null;
      this.previous.set(popped);
      return popped;
   }

   public MethodInvocationContext getCurrent() {
      List list = (List)this.current.get();
      return list != null && !list.isEmpty() ? (MethodInvocationContext)list.get(list.size() - 1) : null;
   }

   public MethodInvocationContext getMostRecentCompletedMethodInvocationContext() {
      return (MethodInvocationContext)this.previous.get();
   }
}
