package weblogic.ejb.container.deployer;

import weblogic.ejb.container.utils.ClassUtils;

public final class NamingConvention {
   private String beanClassName;
   private String beanRootName;
   private String beanPackageName;
   private String ejbName;
   private String generatedBaseName;
   private String interceptorClassName;
   private String interceptorPackageName;

   public NamingConvention(String interceptorClassName) {
      this.interceptorClassName = interceptorClassName;
   }

   public NamingConvention(String beanClassName, String ejbName) {
      this.beanClassName = beanClassName;
      this.ejbName = ejbName;
   }

   private String unique(String identifier) {
      String unique = makeLegalFileName(identifier);
      return "_" + Long.toString((long)(unique.hashCode() & Integer.MAX_VALUE), 36) + "_";
   }

   public String getSimpleBeanClassName() {
      return this.tail(this.beanClassName);
   }

   public String getSimpleInterceptorClassName() {
      return this.tail(this.interceptorClassName);
   }

   public String getBeanClassName() {
      return this.beanClassName;
   }

   public String getGeneratedBaseName() {
      assert this.getEJBName() != null;

      if (this.generatedBaseName != null) {
         return this.generatedBaseName;
      } else {
         String identifier = this.getEJBName() + this.getSimpleBeanClassName();
         if (this.getEJBName().length() <= 26) {
            this.generatedBaseName = makeLegalFileName(this.getEJBName() + this.unique(identifier));
         } else {
            this.generatedBaseName = this.getSimpleBeanClassName() + this.unique(identifier);
         }

         return this.generatedBaseName;
      }
   }

   public String getGeneratedBaseInterceptorName() {
      return this.getSimpleInterceptorClassName() + this.unique(this.getSimpleInterceptorClassName());
   }

   public String getSimpleHomeClassName() {
      return this.getGeneratedBaseName() + "HomeImpl";
   }

   public String getHomeClassName() {
      return this.getBeanPackagePrefix() + this.getSimpleHomeClassName();
   }

   public String getSimpleLocalHomeClassName() {
      return this.getGeneratedBaseName() + "LocalHomeImpl";
   }

   public String getLocalHomeClassName() {
      return this.getBeanPackagePrefix() + this.getSimpleLocalHomeClassName();
   }

   public String getSimpleEJBObjectClassName() {
      return this.getGeneratedBaseName() + "EOImpl";
   }

   public String getEJBObjectClassName() {
      return this.getBeanPackagePrefix() + this.getSimpleEJBObjectClassName();
   }

   public String getSimpleEJBLocalObjectClassName() {
      return this.getGeneratedBaseName() + "ELOImpl";
   }

   public String getEJBLocalObjectClassName() {
      return this.getBeanPackagePrefix() + this.getSimpleEJBLocalObjectClassName();
   }

   public String getSimpleLocalBusinessImplClassName(Class iface) {
      return this.getGeneratedBaseName() + iface.getSimpleName() + "Impl";
   }

   public String getLocalBusinessImplClassName(Class iface) {
      return this.getBeanPackagePrefix() + this.getSimpleLocalBusinessImplClassName(iface);
   }

   public String getNoIntfViewImplClassName() {
      return this.getBeanPackagePrefix() + this.getGeneratedBaseName() + "NoIntfViewImpl";
   }

   public String getSimpleInterceptorImplClassName() {
      return this.getGeneratedBaseInterceptorName() + "Impl";
   }

   public String getInterceptorImplClassName() {
      return this.getInterceptorPackagePrefix() + this.getSimpleInterceptorImplClassName();
   }

   public String getSimpleRemoteBusinessImplClassName(Class iface) {
      return this.getGeneratedBaseName() + iface.getSimpleName() + "Impl";
   }

   public String getRemoteBusinessImplClassName(Class iface) {
      return this.getBeanPackagePrefix() + this.getSimpleLocalBusinessImplClassName(iface);
   }

   public String getSimpleRemoteBusinessIntfClassName(Class iface) {
      return this.getGeneratedBaseName() + iface.getSimpleName() + "RIntf";
   }

   public String getRemoteBusinessIntfClassName(Class iface) {
      String pkgName = this.head(iface.getName());
      if (pkgName.length() > 0) {
         pkgName = pkgName + ".";
      }

      return pkgName + this.getSimpleRemoteBusinessIntfClassName(iface);
   }

   public String getSimpleWsObjectClassName() {
      return this.getGeneratedBaseName() + "WSOImpl";
   }

   public String getWsObjectClassName() {
      return this.getBeanPackagePrefix() + this.getSimpleWsObjectClassName();
   }

   public String getSimpleMdLocalObjectClassName() {
      return this.getGeneratedBaseName() + "MDOImpl";
   }

   public String getMdLocalObjectClassName() {
      return this.getBeanPackagePrefix() + this.getSimpleMdLocalObjectClassName();
   }

   public String getSimpleCmpBeanClassName(String ejbStoreType) {
      return this.getGeneratedBaseName() + "_" + ejbStoreType;
   }

   private static String makeLegalFileName(String s) {
      return ClassUtils.makeLegalName(s);
   }

   public String getCmpBeanClassName(String ejbStoreType) {
      return this.getBeanPackagePrefix() + this.getSimpleCmpBeanClassName(ejbStoreType);
   }

   public String getGeneratedBeanClassName() {
      return this.getBeanPackagePrefix() + this.getSimpleGeneratedBeanClassName();
   }

   public String getSimpleGeneratedBeanClassName() {
      return this.getGeneratedBaseName() + "Impl";
   }

   public String getGeneratedBeanInterfaceName() {
      return this.getBeanPackagePrefix() + this.getSimpleGeneratedBeanInterfaceName();
   }

   public String getSimpleGeneratedBeanInterfaceName() {
      return this.getGeneratedBaseName() + "Intf";
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public String getInterceptorPackageName() {
      if (this.interceptorPackageName == null) {
         this.interceptorPackageName = this.head(this.interceptorClassName);
      }

      return this.interceptorPackageName;
   }

   public String getInterceptorPackagePrefix() {
      String bpn = this.getInterceptorPackageName();
      String prefix = "";
      if (bpn != null && bpn.length() > 0) {
         prefix = bpn + ".";
      }

      return prefix;
   }

   public String getBeanPackageName() {
      if (this.beanPackageName == null) {
         this.beanPackageName = this.head(this.beanClassName);
      }

      return this.beanPackageName;
   }

   public String getBeanPackagePrefix() {
      String bpn = this.getBeanPackageName();
      String prefix = "";
      if (bpn != null && bpn.length() > 0) {
         prefix = bpn + ".";
      }

      return prefix;
   }

   private String head(String name) {
      int i = name.lastIndexOf(".");
      return i == -1 ? "" : name.substring(0, i);
   }

   private String tail(String name) {
      int i = name.lastIndexOf(".");
      return name.substring(i + 1);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("BeanClassName: " + this.beanClassName + "\n");
      sb.append("BeanRootName: " + this.beanRootName + "\n");
      sb.append("BeanPackageName: " + this.beanPackageName + "\n");
      sb.append("EJBName: " + this.ejbName + "\n");
      return sb.toString();
   }

   public static void main(String[] args) {
      NamingConvention nc = new NamingConvention(args[0], (String)null);
      System.out.println("beanClass(" + nc.getBeanClassName() + ")");
      System.out.println("homeImplClass(" + nc.getHomeClassName() + ")");
   }
}
