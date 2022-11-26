package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocation;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocationBasedExporter;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocationResult;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

public abstract class RemoteInvocationSerializingExporter extends RemoteInvocationBasedExporter implements InitializingBean {
   public static final String CONTENT_TYPE_SERIALIZED_OBJECT = "application/x-java-serialized-object";
   private String contentType = "application/x-java-serialized-object";
   private boolean acceptProxyClasses = true;
   private Object proxy;

   public void setContentType(String contentType) {
      Assert.notNull(contentType, (String)"'contentType' must not be null");
      this.contentType = contentType;
   }

   public String getContentType() {
      return this.contentType;
   }

   public void setAcceptProxyClasses(boolean acceptProxyClasses) {
      this.acceptProxyClasses = acceptProxyClasses;
   }

   public boolean isAcceptProxyClasses() {
      return this.acceptProxyClasses;
   }

   public void afterPropertiesSet() {
      this.prepare();
   }

   public void prepare() {
      this.proxy = this.getProxyForService();
   }

   protected final Object getProxy() {
      if (this.proxy == null) {
         throw new IllegalStateException(ClassUtils.getShortName(this.getClass()) + " has not been initialized");
      } else {
         return this.proxy;
      }
   }

   protected ObjectInputStream createObjectInputStream(InputStream is) throws IOException {
      return new CodebaseAwareObjectInputStream(is, this.getBeanClassLoader(), this.isAcceptProxyClasses());
   }

   protected RemoteInvocation doReadRemoteInvocation(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      Object obj = ois.readObject();
      if (!(obj instanceof RemoteInvocation)) {
         throw new RemoteException("Deserialized object needs to be assignable to type [" + RemoteInvocation.class.getName() + "]: " + ClassUtils.getDescriptiveType(obj));
      } else {
         return (RemoteInvocation)obj;
      }
   }

   protected ObjectOutputStream createObjectOutputStream(OutputStream os) throws IOException {
      return new ObjectOutputStream(os);
   }

   protected void doWriteRemoteInvocationResult(RemoteInvocationResult result, ObjectOutputStream oos) throws IOException {
      oos.writeObject(result);
   }
}
