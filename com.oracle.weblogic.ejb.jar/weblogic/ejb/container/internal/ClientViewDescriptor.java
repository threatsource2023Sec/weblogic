package weblogic.ejb.container.internal;

import java.rmi.Remote;
import weblogic.ejb.container.interfaces.BeanInfo;

public final class ClientViewDescriptor {
   private final Class viewClass;
   private final String intfType;
   private final boolean isLocal;
   private final boolean isBusinessView;
   private final BeanInfo beanInfo;

   public ClientViewDescriptor(Class viewClass, String intfType, boolean isLocal, boolean isBusinessView, BeanInfo beanInfo) {
      this.viewClass = viewClass;
      this.intfType = intfType;
      this.isLocal = isLocal;
      this.isBusinessView = isBusinessView;
      this.beanInfo = beanInfo;
   }

   public Class getViewClass() {
      return this.viewClass;
   }

   public String getIntfType() {
      return this.intfType;
   }

   public boolean isLocal() {
      return this.isLocal;
   }

   public boolean isBusinessView() {
      return this.isBusinessView;
   }

   public boolean extendsRemote() {
      return !this.isLocal && Remote.class.isAssignableFrom(this.viewClass);
   }

   public BeanInfo getBeanInfo() {
      return this.beanInfo;
   }
}
