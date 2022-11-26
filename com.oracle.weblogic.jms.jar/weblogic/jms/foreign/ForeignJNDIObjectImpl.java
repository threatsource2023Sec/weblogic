package weblogic.jms.foreign;

import java.util.HashMap;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.ModuleException;
import weblogic.deployment.jms.ForeignOpaqueReference;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;
import weblogic.j2ee.descriptor.wl.ForeignJNDIObjectBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSException;
import weblogic.management.ManagementException;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.management.utils.GenericBeanListener;

public class ForeignJNDIObjectImpl implements BeanListenerCustomizer {
   private ForeignServerBean parent;
   private ForeignJNDIObjectBean foreignJNDIObjectBean;
   private boolean bound;
   private String localJNDIName;
   private String remoteJNDIName;
   private String username;
   private String password;
   private byte[] encryptedPassword;
   private boolean isDestination;
   private transient GenericBeanListener foreignJNDIObjectBeanListener;
   private static final HashMap foreignDestinationBeanSignatures = new HashMap();
   private static final HashMap foreignConnectionFactoryBeanSignatures = new HashMap();
   private final Context context;
   private boolean needRebind;

   private ForeignJNDIObjectImpl(Context context, ForeignServerBean paramParent, ForeignJNDIObjectBean fjoBean, boolean paramIsDestination, HashMap signature) throws JMSException {
      this.parent = paramParent;
      this.foreignJNDIObjectBean = fjoBean;
      this.isDestination = paramIsDestination;
      this.context = context;
      this.needRebind = false;
   }

   public ForeignJNDIObjectImpl(Context context, ForeignServerBean paramParent, ForeignDestinationBean fjoBean) throws JMSException {
      this(context, paramParent, fjoBean, true, foreignDestinationBeanSignatures);
   }

   public ForeignJNDIObjectImpl(Context context, ForeignServerBean paramParent, ForeignConnectionFactoryBean fjoBean) throws JMSException {
      this(context, paramParent, fjoBean, false, foreignConnectionFactoryBeanSignatures);
   }

   public void close() {
      this.unregisterBeanUpdateListeners();
   }

   synchronized void bind(boolean rebind) throws JMSException {
      this.unregisterBeanUpdateListeners();
      this.registerBeanUpdateListeners();
      this.doBind(this.foreignJNDIObjectBean.getLocalJNDIName(), rebind);
   }

   void validateJNDI() throws ModuleException {
      String jndiName = this.foreignJNDIObjectBean.getLocalJNDIName();

      Object findMe;
      try {
         findMe = this.context.lookup(jndiName);
      } catch (NamingException var4) {
         return;
      }

      throw new ModuleException("The foreign object of name \"" + this.foreignJNDIObjectBean.getName() + "\" cannot bind to JNDI name \"" + jndiName + "\" because another object is already bound there of type " + (findMe == null ? "null" : findMe.getClass().getName()));
   }

   private void doBind(String name, boolean rebind) throws JMSException {
      if (name != null && name.length() != 0) {
         try {
            Object ref = this.getJNDIReference();
            if (rebind && this.bound) {
               if (JMSDebug.JMSConfig.isDebugEnabled()) {
                  JMSDebug.JMSConfig.debug("Replacing binding of MBean " + this.foreignJNDIObjectBean.getName() + " bound to JNDI name " + name);
               }

               this.context.rebind(name, ref);
            } else {
               if (JMSDebug.JMSConfig.isDebugEnabled()) {
                  JMSDebug.JMSConfig.debug("Binding MBean " + this.foreignJNDIObjectBean.getName() + " to JNDI name " + name);
               }

               this.context.bind(name, ref);
               this.bound = true;
            }

         } catch (NamingException var4) {
            throw new JMSException(JMSExceptionLogger.logErrorInJNDIBindLoggable(name), var4);
         }
      }
   }

   synchronized void unbind() {
      this.unregisterBeanUpdateListeners();
      this.doUnbind(this.foreignJNDIObjectBean.getLocalJNDIName());
   }

   private void doUnbind(String name) {
      if (this.bound && name != null && name.length() != 0) {
         try {
            if (JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("Un-binding remote JMS object from " + name);
            }

            this.context.unbind(name);
            this.bound = false;
         } catch (NamingException var3) {
            JMSLogger.logErrorInJNDIUnbind(name, var3.toString());
         }

      }
   }

   private Object getJNDIReference() {
      return new ForeignOpaqueReference(this.parent, this.foreignJNDIObjectBean);
   }

   public String getLocalJNDIName() {
      return this.localJNDIName;
   }

   public void setLocalJNDIName(String paramLocalJNDIName) {
      this.localJNDIName = paramLocalJNDIName;
      this.needRebind = true;
   }

   public String getRemoteJNDIName() {
      return this.remoteJNDIName;
   }

   public void setRemoteJNDIName(String paramRemoteJNDIName) {
      this.remoteJNDIName = paramRemoteJNDIName;
      this.needRebind = true;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String paramUsername) {
      this.username = paramUsername;
      this.needRebind = true;
   }

   public byte[] getPasswordEncrypted() {
      return this.encryptedPassword;
   }

   public void setPasswordEncrypted(byte[] passwordEncrypted) {
      this.encryptedPassword = passwordEncrypted;
      this.needRebind = true;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String paramPassword) {
      this.password = paramPassword;
      this.needRebind = true;
   }

   public String getName() {
      return this.foreignJNDIObjectBean.getName();
   }

   public void setName(String name) {
      throw new AssertionError("Name setter only here to satisfy interface: " + name);
   }

   public String getNotes() {
      return this.foreignJNDIObjectBean.getNotes();
   }

   public void setNotes(String notes) {
      throw new AssertionError("Notes setter only here to satisfy interface: " + notes);
   }

   private void registerBeanUpdateListeners() throws JMSException {
      DescriptorBean descriptor = (DescriptorBean)this.foreignJNDIObjectBean;
      HashMap signature = this.isDestination ? foreignDestinationBeanSignatures : foreignConnectionFactoryBeanSignatures;
      this.foreignJNDIObjectBeanListener = new GenericBeanListener(descriptor, this, signature);
      this.foreignJNDIObjectBeanListener.setCustomizer(this);

      try {
         this.foreignJNDIObjectBeanListener.initialize();
      } catch (ManagementException var4) {
         throw new JMSException(var4.getMessage(), var4);
      }
   }

   private void unregisterBeanUpdateListeners() {
      if (this.foreignJNDIObjectBeanListener != null) {
         this.foreignJNDIObjectBeanListener.close();
         this.foreignJNDIObjectBeanListener = null;
      }

   }

   public void activateFinished() throws BeanUpdateFailedException {
      if (this.needRebind) {
         try {
            this.bind(true);
         } catch (JMSException var5) {
            throw new BeanUpdateFailedException(var5.getMessage());
         } finally {
            this.needRebind = false;
         }
      }

   }

   static {
      foreignDestinationBeanSignatures.put("LocalJNDIName", String.class);
      foreignDestinationBeanSignatures.put("RemoteJNDIName", String.class);
      foreignConnectionFactoryBeanSignatures.put("LocalJNDIName", String.class);
      foreignConnectionFactoryBeanSignatures.put("RemoteJNDIName", String.class);
      foreignConnectionFactoryBeanSignatures.put("Username", String.class);
      foreignConnectionFactoryBeanSignatures.put("Password", String.class);
      foreignConnectionFactoryBeanSignatures.put("PasswordEncrypted", byte[].class);
   }
}
