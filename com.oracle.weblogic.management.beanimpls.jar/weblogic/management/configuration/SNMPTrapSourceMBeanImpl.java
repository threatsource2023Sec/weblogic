package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class SNMPTrapSourceMBeanImpl extends ConfigurationMBeanImpl implements SNMPTrapSourceMBean, Serializable {
   private ServerMBean[] _EnabledServers;
   private static SchemaHelper2 _schemaHelper;

   public SNMPTrapSourceMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPTrapSourceMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPTrapSourceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public ServerMBean[] getEnabledServers() {
      return this._EnabledServers;
   }

   public String getEnabledServersAsString() {
      return this._getHelper()._serializeKeyList(this.getEnabledServers());
   }

   public boolean isEnabledServersInherited() {
      return false;
   }

   public boolean isEnabledServersSet() {
      return this._isSet(10);
   }

   public void setEnabledServersAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._EnabledServers);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, ServerMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        SNMPTrapSourceMBeanImpl.this.addEnabledServer((ServerMBean)value);
                        SNMPTrapSourceMBeanImpl.this._getHelper().reorderArrayObjects((Object[])SNMPTrapSourceMBeanImpl.this._EnabledServers, this.getHandbackObject());
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
               ServerMBean[] var6 = this._EnabledServers;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  ServerMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeEnabledServer(member);
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
         ServerMBean[] _oldVal = this._EnabledServers;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._EnabledServers);
      }
   }

   public void setEnabledServers(ServerMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      ServerMBean[] param0 = param0 == null ? new ServerMBeanImpl[0] : param0;
      param0 = (ServerMBean[])((ServerMBean[])this._getHelper()._cleanAndValidateArray(param0, ServerMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return SNMPTrapSourceMBeanImpl.this.getEnabledServers();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      ServerMBean[] _oldVal = this._EnabledServers;
      this._EnabledServers = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean addEnabledServer(ServerMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         ServerMBean[] _new;
         if (this._isSet(10)) {
            _new = (ServerMBean[])((ServerMBean[])this._getHelper()._extendArray(this.getEnabledServers(), ServerMBean.class, param0));
         } else {
            _new = new ServerMBean[]{param0};
         }

         try {
            this.setEnabledServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeEnabledServer(ServerMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      ServerMBean[] _old = this.getEnabledServers();
      ServerMBean[] _new = (ServerMBean[])((ServerMBean[])this._getHelper()._removeElement(_old, ServerMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setEnabledServers(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof ConfigurationException) {
               throw (ConfigurationException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._EnabledServers = new ServerMBean[0];
               if (initOne) {
                  break;
               }
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
      return "SNMPTrapSource";
   }

   public void putValue(String name, Object v) {
      if (name.equals("EnabledServers")) {
         ServerMBean[] oldVal = this._EnabledServers;
         this._EnabledServers = (ServerMBean[])((ServerMBean[])v);
         this._postSet(10, oldVal, this._EnabledServers);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      return name.equals("EnabledServers") ? this._EnabledServers : super.getValue(name);
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("enabled-server")) {
                  return 10;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "enabled-server";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private SNMPTrapSourceMBeanImpl bean;

      protected Helper(SNMPTrapSourceMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "EnabledServers";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("EnabledServers") ? 10 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isEnabledServersSet()) {
               buf.append("EnabledServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEnabledServers())));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            SNMPTrapSourceMBeanImpl otherTyped = (SNMPTrapSourceMBeanImpl)other;
            this.computeDiff("EnabledServers", this.bean.getEnabledServers(), otherTyped.getEnabledServers(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPTrapSourceMBeanImpl original = (SNMPTrapSourceMBeanImpl)event.getSourceBean();
            SNMPTrapSourceMBeanImpl proposed = (SNMPTrapSourceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EnabledServers")) {
                  original.setEnabledServersAsString(proposed.getEnabledServersAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else {
                  super.applyPropertyUpdate(event, update);
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
            SNMPTrapSourceMBeanImpl copy = (SNMPTrapSourceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EnabledServers")) && this.bean.isEnabledServersSet()) {
               copy._unSet(copy, 10);
               copy.setEnabledServersAsString(this.bean.getEnabledServersAsString());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getEnabledServers(), clazz, annotation);
      }
   }
}
