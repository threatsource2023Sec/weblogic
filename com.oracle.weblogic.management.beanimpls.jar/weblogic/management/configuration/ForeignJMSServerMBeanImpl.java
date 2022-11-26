package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.ForeignJMSServer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ForeignJMSServerMBeanImpl extends DeploymentMBeanImpl implements ForeignJMSServerMBean, Serializable {
   private ForeignJMSConnectionFactoryMBean[] _ConnectionFactories;
   private String _ConnectionURL;
   private ForeignJMSDestinationMBean[] _Destinations;
   private boolean _DynamicallyCreated;
   private ForeignJMSConnectionFactoryMBean[] _ForeignJMSConnectionFactories;
   private ForeignJMSDestinationMBean[] _ForeignJMSDestinations;
   private String _InitialContextFactory;
   private Properties _JNDIProperties;
   private String _JNDIPropertiesCredential;
   private byte[] _JNDIPropertiesCredentialEncrypted;
   private String _Name;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private transient ForeignJMSServer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ForeignJMSServerMBeanImpl() {
      try {
         this._customizer = new ForeignJMSServer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ForeignJMSServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ForeignJMSServer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ForeignJMSServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ForeignJMSServer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ForeignJMSDestinationMBean[] getDestinations() {
      return this._customizer.getDestinations();
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public TargetMBean[] getTargets() {
      return this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isDestinationsInherited() {
      return false;
   }

   public boolean isDestinationsSet() {
      return this._isSet(12);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        ForeignJMSServerMBeanImpl.this.addTarget((TargetMBean)value);
                        ForeignJMSServerMBeanImpl.this._getHelper().reorderArrayObjects((Object[])ForeignJMSServerMBeanImpl.this._Targets, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Targets);
      }
   }

   public void setDestinations(ForeignJMSDestinationMBean[] param0) {
      ForeignJMSDestinationMBean[] param0 = param0 == null ? new ForeignJMSDestinationMBeanImpl[0] : param0;
      this._Destinations = (ForeignJMSDestinationMBean[])param0;
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return ForeignJMSServerMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this.getTargets();
      this._customizer.setTargets(param0);
      this._postSet(10, _oldVal, param0);
   }

   public boolean addDestination(ForeignJMSDestinationMBean param0) {
      return this._customizer.addDestination(param0);
   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeDestination(ForeignJMSDestinationMBean param0) {
      ForeignJMSDestinationMBean[] _old = this.getDestinations();
      ForeignJMSDestinationMBean[] _new = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])this._getHelper()._removeElement(_old, ForeignJMSDestinationMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDestinations(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public ForeignJMSConnectionFactoryMBean[] getConnectionFactories() {
      return this._ConnectionFactories;
   }

   public boolean isConnectionFactoriesInherited() {
      return false;
   }

   public boolean isConnectionFactoriesSet() {
      return this._isSet(13);
   }

   public void setConnectionFactories(ForeignJMSConnectionFactoryMBean[] param0) {
      ForeignJMSConnectionFactoryMBean[] param0 = param0 == null ? new ForeignJMSConnectionFactoryMBeanImpl[0] : param0;
      this._ConnectionFactories = (ForeignJMSConnectionFactoryMBean[])param0;
   }

   public boolean addConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      return this._customizer.addConnectionFactory(param0);
   }

   public boolean removeConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      ForeignJMSConnectionFactoryMBean[] _old = this.getConnectionFactories();
      ForeignJMSConnectionFactoryMBean[] _new = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])this._getHelper()._removeElement(_old, ForeignJMSConnectionFactoryMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setConnectionFactories(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setInitialContextFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("InitialContextFactory", param0);
      String _oldVal = this.getInitialContextFactory();
      this._customizer.setInitialContextFactory(param0);
      this._postSet(14, _oldVal, param0);
   }

   public String getInitialContextFactory() {
      return this._customizer.getInitialContextFactory();
   }

   public boolean isInitialContextFactoryInherited() {
      return false;
   }

   public boolean isInitialContextFactorySet() {
      return this._isSet(14);
   }

   public void setConnectionURL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getConnectionURL();
      this._customizer.setConnectionURL(param0);
      this._postSet(15, _oldVal, param0);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getConnectionURL() {
      return this._customizer.getConnectionURL();
   }

   public boolean isConnectionURLInherited() {
      return false;
   }

   public boolean isConnectionURLSet() {
      return this._isSet(15);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setJNDIProperties(Properties param0) throws InvalidAttributeValueException {
      Properties _oldVal = this.getJNDIProperties();
      this._customizer.setJNDIProperties(param0);
      this._postSet(16, _oldVal, param0);
   }

   public Properties getJNDIProperties() {
      return this._customizer.getJNDIProperties();
   }

   public String getJNDIPropertiesAsString() {
      return StringHelper.objectToString(this.getJNDIProperties());
   }

   public boolean isJNDIPropertiesInherited() {
      return false;
   }

   public boolean isJNDIPropertiesSet() {
      return this._isSet(16);
   }

   public void setJNDIPropertiesAsString(String param0) {
      try {
         this.setJNDIProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public byte[] getJNDIPropertiesCredentialEncrypted() {
      return this._getHelper()._cloneArray(this._JNDIPropertiesCredentialEncrypted);
   }

   public String getJNDIPropertiesCredentialEncryptedAsString() {
      byte[] obj = this.getJNDIPropertiesCredentialEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isJNDIPropertiesCredentialEncryptedInherited() {
      return false;
   }

   public boolean isJNDIPropertiesCredentialEncryptedSet() {
      return this._isSet(17);
   }

   public void setJNDIPropertiesCredentialEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setJNDIPropertiesCredentialEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getJNDIPropertiesCredential() {
      byte[] bEncrypted = this.getJNDIPropertiesCredentialEncrypted();
      return bEncrypted == null ? null : this._decrypt("JNDIPropertiesCredential", bEncrypted);
   }

   public boolean isJNDIPropertiesCredentialInherited() {
      return false;
   }

   public boolean isJNDIPropertiesCredentialSet() {
      return this.isJNDIPropertiesCredentialEncryptedSet();
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public void setJNDIPropertiesCredential(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setJNDIPropertiesCredentialEncrypted(param0 == null ? null : this._encrypt("JNDIPropertiesCredential", param0));
   }

   public void addForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         ForeignJMSConnectionFactoryMBean[] _new;
         if (this._isSet(19)) {
            _new = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])this._getHelper()._extendArray(this.getForeignJMSConnectionFactories(), ForeignJMSConnectionFactoryMBean.class, param0));
         } else {
            _new = new ForeignJMSConnectionFactoryMBean[]{param0};
         }

         try {
            this.setForeignJMSConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignJMSConnectionFactoryMBean[] getForeignJMSConnectionFactories() {
      return this._ForeignJMSConnectionFactories;
   }

   public boolean isForeignJMSConnectionFactoriesInherited() {
      return false;
   }

   public boolean isForeignJMSConnectionFactoriesSet() {
      return this._isSet(19);
   }

   public void removeForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      this.destroyForeignJMSConnectionFactory(param0);
   }

   public void setForeignJMSConnectionFactories(ForeignJMSConnectionFactoryMBean[] param0) throws InvalidAttributeValueException {
      ForeignJMSConnectionFactoryMBean[] param0 = param0 == null ? new ForeignJMSConnectionFactoryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignJMSConnectionFactoryMBean[] _oldVal = this._ForeignJMSConnectionFactories;
      this._ForeignJMSConnectionFactories = (ForeignJMSConnectionFactoryMBean[])param0;
      this._postSet(19, _oldVal, param0);
   }

   public ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String param0) {
      ForeignJMSConnectionFactoryMBeanImpl lookup = (ForeignJMSConnectionFactoryMBeanImpl)this.lookupForeignJMSConnectionFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJMSConnectionFactoryMBeanImpl _val = new ForeignJMSConnectionFactoryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJMSConnectionFactory(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public ForeignJMSConnectionFactoryMBean lookupForeignJMSConnectionFactory(String param0) {
      Object[] aary = (Object[])this._ForeignJMSConnectionFactories;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJMSConnectionFactoryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJMSConnectionFactoryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 19);
         ForeignJMSConnectionFactoryMBean[] _old = this.getForeignJMSConnectionFactories();
         ForeignJMSConnectionFactoryMBean[] _new = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])this._getHelper()._removeElement(_old, ForeignJMSConnectionFactoryMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setForeignJMSConnectionFactories(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String param0, ForeignJMSConnectionFactoryMBean param1) {
      return this._customizer.createForeignJMSConnectionFactory(param0, param1);
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void addForeignJMSDestination(ForeignJMSDestinationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         ForeignJMSDestinationMBean[] _new;
         if (this._isSet(20)) {
            _new = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])this._getHelper()._extendArray(this.getForeignJMSDestinations(), ForeignJMSDestinationMBean.class, param0));
         } else {
            _new = new ForeignJMSDestinationMBean[]{param0};
         }

         try {
            this.setForeignJMSDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignJMSDestinationMBean[] getForeignJMSDestinations() {
      return this._ForeignJMSDestinations;
   }

   public boolean isForeignJMSDestinationsInherited() {
      return false;
   }

   public boolean isForeignJMSDestinationsSet() {
      return this._isSet(20);
   }

   public void removeForeignJMSDestination(ForeignJMSDestinationMBean param0) {
      this.destroyForeignJMSDestination(param0);
   }

   public void setForeignJMSDestinations(ForeignJMSDestinationMBean[] param0) throws InvalidAttributeValueException {
      ForeignJMSDestinationMBean[] param0 = param0 == null ? new ForeignJMSDestinationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignJMSDestinationMBean[] _oldVal = this._ForeignJMSDestinations;
      this._ForeignJMSDestinations = (ForeignJMSDestinationMBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public ForeignJMSDestinationMBean createForeignJMSDestination(String param0) {
      ForeignJMSDestinationMBeanImpl lookup = (ForeignJMSDestinationMBeanImpl)this.lookupForeignJMSDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJMSDestinationMBeanImpl _val = new ForeignJMSDestinationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJMSDestination(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public ForeignJMSDestinationMBean lookupForeignJMSDestination(String param0) {
      Object[] aary = (Object[])this._ForeignJMSDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJMSDestinationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJMSDestinationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJMSDestinationMBean createForeignJMSDestination(String param0, ForeignJMSDestinationMBean param1) {
      return this._customizer.createForeignJMSDestination(param0, param1);
   }

   public void destroyForeignJMSDestination(ForeignJMSDestinationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 20);
         ForeignJMSDestinationMBean[] _old = this.getForeignJMSDestinations();
         ForeignJMSDestinationMBean[] _new = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])this._getHelper()._removeElement(_old, ForeignJMSDestinationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setForeignJMSDestinations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void useDelegates(ForeignServerBean param0, SubDeploymentMBean param1) {
      this._customizer.useDelegates(param0, param1);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setJNDIPropertiesCredentialEncrypted(byte[] param0) {
      byte[] _oldVal = this._JNDIPropertiesCredentialEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: JNDIPropertiesCredentialEncrypted of ForeignJMSServerMBean");
      } else {
         this._getHelper()._clearArray(this._JNDIPropertiesCredentialEncrypted);
         this._JNDIPropertiesCredentialEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(17, _oldVal, param0);
      }
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 18) {
            this._markSet(17, false);
         }
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._ConnectionFactories = new ForeignJMSConnectionFactoryMBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setConnectionURL((String)null);
               if (initOne) {
                  break;
               }
            case 12:
               this._Destinations = new ForeignJMSDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._ForeignJMSConnectionFactories = new ForeignJMSConnectionFactoryMBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._ForeignJMSDestinations = new ForeignJMSDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._customizer.setInitialContextFactory("weblogic.jndi.WLInitialContextFactory");
               if (initOne) {
                  break;
               }
            case 16:
               this._customizer.setJNDIProperties((Properties)null);
               if (initOne) {
                  break;
               }
            case 18:
               this._JNDIPropertiesCredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._JNDIPropertiesCredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setTargets(new TargetMBean[0]);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "ForeignJMSServer";
   }

   public void putValue(String name, Object v) {
      ForeignJMSConnectionFactoryMBean[] oldVal;
      if (name.equals("ConnectionFactories")) {
         oldVal = this._ConnectionFactories;
         this._ConnectionFactories = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])v);
         this._postSet(13, oldVal, this._ConnectionFactories);
      } else {
         String oldVal;
         if (name.equals("ConnectionURL")) {
            oldVal = this._ConnectionURL;
            this._ConnectionURL = (String)v;
            this._postSet(15, oldVal, this._ConnectionURL);
         } else {
            ForeignJMSDestinationMBean[] oldVal;
            if (name.equals("Destinations")) {
               oldVal = this._Destinations;
               this._Destinations = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])v);
               this._postSet(12, oldVal, this._Destinations);
            } else if (name.equals("DynamicallyCreated")) {
               boolean oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("ForeignJMSConnectionFactories")) {
               oldVal = this._ForeignJMSConnectionFactories;
               this._ForeignJMSConnectionFactories = (ForeignJMSConnectionFactoryMBean[])((ForeignJMSConnectionFactoryMBean[])v);
               this._postSet(19, oldVal, this._ForeignJMSConnectionFactories);
            } else if (name.equals("ForeignJMSDestinations")) {
               oldVal = this._ForeignJMSDestinations;
               this._ForeignJMSDestinations = (ForeignJMSDestinationMBean[])((ForeignJMSDestinationMBean[])v);
               this._postSet(20, oldVal, this._ForeignJMSDestinations);
            } else if (name.equals("InitialContextFactory")) {
               oldVal = this._InitialContextFactory;
               this._InitialContextFactory = (String)v;
               this._postSet(14, oldVal, this._InitialContextFactory);
            } else if (name.equals("JNDIProperties")) {
               Properties oldVal = this._JNDIProperties;
               this._JNDIProperties = (Properties)v;
               this._postSet(16, oldVal, this._JNDIProperties);
            } else if (name.equals("JNDIPropertiesCredential")) {
               oldVal = this._JNDIPropertiesCredential;
               this._JNDIPropertiesCredential = (String)v;
               this._postSet(18, oldVal, this._JNDIPropertiesCredential);
            } else if (name.equals("JNDIPropertiesCredentialEncrypted")) {
               byte[] oldVal = this._JNDIPropertiesCredentialEncrypted;
               this._JNDIPropertiesCredentialEncrypted = (byte[])((byte[])v);
               this._postSet(17, oldVal, this._JNDIPropertiesCredentialEncrypted);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("Targets")) {
               TargetMBean[] oldVal = this._Targets;
               this._Targets = (TargetMBean[])((TargetMBean[])v);
               this._postSet(10, oldVal, this._Targets);
            } else if (name.equals("customizer")) {
               ForeignJMSServer oldVal = this._customizer;
               this._customizer = (ForeignJMSServer)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionFactories")) {
         return this._ConnectionFactories;
      } else if (name.equals("ConnectionURL")) {
         return this._ConnectionURL;
      } else if (name.equals("Destinations")) {
         return this._Destinations;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ForeignJMSConnectionFactories")) {
         return this._ForeignJMSConnectionFactories;
      } else if (name.equals("ForeignJMSDestinations")) {
         return this._ForeignJMSDestinations;
      } else if (name.equals("InitialContextFactory")) {
         return this._InitialContextFactory;
      } else if (name.equals("JNDIProperties")) {
         return this._JNDIProperties;
      } else if (name.equals("JNDIPropertiesCredential")) {
         return this._JNDIPropertiesCredential;
      } else if (name.equals("JNDIPropertiesCredentialEncrypted")) {
         return this._JNDIPropertiesCredentialEncrypted;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("InitialContextFactory", "weblogic.jndi.WLInitialContextFactory");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property InitialContextFactory in ForeignJMSServerMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 16:
            case 17:
            case 20:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("destination")) {
                  return 12;
               }
               break;
            case 14:
               if (s.equals("connection-url")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("jndi-properties")) {
                  return 16;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 23:
               if (s.equals("foreign-jms-destination")) {
                  return 20;
               }

               if (s.equals("initial-context-factory")) {
                  return 14;
               }
               break;
            case 26:
               if (s.equals("jndi-properties-credential")) {
                  return 18;
               }
               break;
            case 30:
               if (s.equals("foreign-jms-connection-factory")) {
                  return 19;
               }
               break;
            case 36:
               if (s.equals("jndi-properties-credential-encrypted")) {
                  return 17;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 19:
               return new ForeignJMSConnectionFactoryMBeanImpl.SchemaHelper2();
            case 20:
               return new ForeignJMSDestinationMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "destination";
            case 13:
               return "connection-factory";
            case 14:
               return "initial-context-factory";
            case 15:
               return "connection-url";
            case 16:
               return "jndi-properties";
            case 17:
               return "jndi-properties-credential-encrypted";
            case 18:
               return "jndi-properties-credential";
            case 19:
               return "foreign-jms-connection-factory";
            case 20:
               return "foreign-jms-destination";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
               return super.isArray(propIndex);
            case 12:
               return true;
            case 13:
               return true;
            case 19:
               return true;
            case 20:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 19:
               return true;
            case 20:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private ForeignJMSServerMBeanImpl bean;

      protected Helper(ForeignJMSServerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "Destinations";
            case 13:
               return "ConnectionFactories";
            case 14:
               return "InitialContextFactory";
            case 15:
               return "ConnectionURL";
            case 16:
               return "JNDIProperties";
            case 17:
               return "JNDIPropertiesCredentialEncrypted";
            case 18:
               return "JNDIPropertiesCredential";
            case 19:
               return "ForeignJMSConnectionFactories";
            case 20:
               return "ForeignJMSDestinations";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactories")) {
            return 13;
         } else if (propName.equals("ConnectionURL")) {
            return 15;
         } else if (propName.equals("Destinations")) {
            return 12;
         } else if (propName.equals("ForeignJMSConnectionFactories")) {
            return 19;
         } else if (propName.equals("ForeignJMSDestinations")) {
            return 20;
         } else if (propName.equals("InitialContextFactory")) {
            return 14;
         } else if (propName.equals("JNDIProperties")) {
            return 16;
         } else if (propName.equals("JNDIPropertiesCredential")) {
            return 18;
         } else if (propName.equals("JNDIPropertiesCredentialEncrypted")) {
            return 17;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getForeignJMSConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getForeignJMSDestinations()));
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isConnectionFactoriesSet()) {
               buf.append("ConnectionFactories");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConnectionFactories())));
            }

            if (this.bean.isConnectionURLSet()) {
               buf.append("ConnectionURL");
               buf.append(String.valueOf(this.bean.getConnectionURL()));
            }

            if (this.bean.isDestinationsSet()) {
               buf.append("Destinations");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDestinations())));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getForeignJMSConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJMSConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignJMSDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJMSDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isInitialContextFactorySet()) {
               buf.append("InitialContextFactory");
               buf.append(String.valueOf(this.bean.getInitialContextFactory()));
            }

            if (this.bean.isJNDIPropertiesSet()) {
               buf.append("JNDIProperties");
               buf.append(String.valueOf(this.bean.getJNDIProperties()));
            }

            if (this.bean.isJNDIPropertiesCredentialSet()) {
               buf.append("JNDIPropertiesCredential");
               buf.append(String.valueOf(this.bean.getJNDIPropertiesCredential()));
            }

            if (this.bean.isJNDIPropertiesCredentialEncryptedSet()) {
               buf.append("JNDIPropertiesCredentialEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJNDIPropertiesCredentialEncrypted())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            ForeignJMSServerMBeanImpl otherTyped = (ForeignJMSServerMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ConnectionURL", this.bean.getConnectionURL(), otherTyped.getConnectionURL(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ForeignJMSConnectionFactories", this.bean.getForeignJMSConnectionFactories(), otherTyped.getForeignJMSConnectionFactories(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ForeignJMSDestinations", this.bean.getForeignJMSDestinations(), otherTyped.getForeignJMSDestinations(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("InitialContextFactory", this.bean.getInitialContextFactory(), otherTyped.getInitialContextFactory(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("JNDIProperties", this.bean.getJNDIProperties(), otherTyped.getJNDIProperties(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("JNDIPropertiesCredentialEncrypted", this.bean.getJNDIPropertiesCredentialEncrypted(), otherTyped.getJNDIPropertiesCredentialEncrypted(), false);
            }

            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ForeignJMSServerMBeanImpl original = (ForeignJMSServerMBeanImpl)event.getSourceBean();
            ForeignJMSServerMBeanImpl proposed = (ForeignJMSServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ConnectionFactories")) {
                  if (prop.equals("ConnectionURL")) {
                     original.setConnectionURL(proposed.getConnectionURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (!prop.equals("Destinations")) {
                     if (prop.equals("ForeignJMSConnectionFactories")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addForeignJMSConnectionFactory((ForeignJMSConnectionFactoryMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeForeignJMSConnectionFactory((ForeignJMSConnectionFactoryMBean)update.getRemovedObject());
                        }

                        if (original.getForeignJMSConnectionFactories() == null || original.getForeignJMSConnectionFactories().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 19);
                        }
                     } else if (prop.equals("ForeignJMSDestinations")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addForeignJMSDestination((ForeignJMSDestinationMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeForeignJMSDestination((ForeignJMSDestinationMBean)update.getRemovedObject());
                        }

                        if (original.getForeignJMSDestinations() == null || original.getForeignJMSDestinations().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 20);
                        }
                     } else if (prop.equals("InitialContextFactory")) {
                        original.setInitialContextFactory(proposed.getInitialContextFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     } else if (prop.equals("JNDIProperties")) {
                        original.setJNDIProperties(proposed.getJNDIProperties() == null ? null : (Properties)proposed.getJNDIProperties().clone());
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     } else if (!prop.equals("JNDIPropertiesCredential")) {
                        if (prop.equals("JNDIPropertiesCredentialEncrypted")) {
                           original.setJNDIPropertiesCredentialEncrypted(proposed.getJNDIPropertiesCredentialEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 17);
                        } else if (prop.equals("Name")) {
                           original.setName(proposed.getName());
                           original._conditionalUnset(update.isUnsetUpdate(), 2);
                        } else if (prop.equals("Tags")) {
                           if (type == 2) {
                              update.resetAddedObject(update.getAddedObject());
                              original.addTag((String)update.getAddedObject());
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeTag((String)update.getRemovedObject());
                           }

                           if (original.getTags() == null || original.getTags().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 9);
                           }
                        } else if (prop.equals("Targets")) {
                           original.setTargetsAsString(proposed.getTargetsAsString());
                           original._conditionalUnset(update.isUnsetUpdate(), 10);
                        } else if (!prop.equals("DynamicallyCreated")) {
                           super.applyPropertyUpdate(event, update);
                        }
                     }
                  }
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            ForeignJMSServerMBeanImpl copy = (ForeignJMSServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ConnectionURL")) && this.bean.isConnectionURLSet()) {
               copy.setConnectionURL(this.bean.getConnectionURL());
            }

            int i;
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ForeignJMSConnectionFactories")) && this.bean.isForeignJMSConnectionFactoriesSet() && !copy._isSet(19)) {
               ForeignJMSConnectionFactoryMBean[] oldForeignJMSConnectionFactories = this.bean.getForeignJMSConnectionFactories();
               ForeignJMSConnectionFactoryMBean[] newForeignJMSConnectionFactories = new ForeignJMSConnectionFactoryMBean[oldForeignJMSConnectionFactories.length];

               for(i = 0; i < newForeignJMSConnectionFactories.length; ++i) {
                  newForeignJMSConnectionFactories[i] = (ForeignJMSConnectionFactoryMBean)((ForeignJMSConnectionFactoryMBean)this.createCopy((AbstractDescriptorBean)oldForeignJMSConnectionFactories[i], includeObsolete));
               }

               copy.setForeignJMSConnectionFactories(newForeignJMSConnectionFactories);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ForeignJMSDestinations")) && this.bean.isForeignJMSDestinationsSet() && !copy._isSet(20)) {
               ForeignJMSDestinationMBean[] oldForeignJMSDestinations = this.bean.getForeignJMSDestinations();
               ForeignJMSDestinationMBean[] newForeignJMSDestinations = new ForeignJMSDestinationMBean[oldForeignJMSDestinations.length];

               for(i = 0; i < newForeignJMSDestinations.length; ++i) {
                  newForeignJMSDestinations[i] = (ForeignJMSDestinationMBean)((ForeignJMSDestinationMBean)this.createCopy((AbstractDescriptorBean)oldForeignJMSDestinations[i], includeObsolete));
               }

               copy.setForeignJMSDestinations(newForeignJMSDestinations);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("InitialContextFactory")) && this.bean.isInitialContextFactorySet()) {
               copy.setInitialContextFactory(this.bean.getInitialContextFactory());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JNDIProperties")) && this.bean.isJNDIPropertiesSet()) {
               copy.setJNDIProperties(this.bean.getJNDIProperties());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JNDIPropertiesCredentialEncrypted")) && this.bean.isJNDIPropertiesCredentialEncryptedSet()) {
               Object o = this.bean.getJNDIPropertiesCredentialEncrypted();
               copy.setJNDIPropertiesCredentialEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJMSConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJMSDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
