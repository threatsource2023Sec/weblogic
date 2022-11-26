package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class MailSessionBeanImpl extends AbstractDescriptorBean implements MailSessionBean, Serializable {
   private String _Description;
   private String _From;
   private String _Host;
   private String _Id;
   private String _Name;
   private String _Password;
   private JavaEEPropertyBean[] _Properties;
   private String _StoreProtocol;
   private String _StoreProtocolClass;
   private String _TransportProtocol;
   private String _TransportProtocolClass;
   private String _User;
   private static SchemaHelper2 _schemaHelper;

   public MailSessionBeanImpl() {
      this._initializeProperty(-1);
   }

   public MailSessionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MailSessionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(1);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getStoreProtocol() {
      return this._StoreProtocol;
   }

   public boolean isStoreProtocolInherited() {
      return false;
   }

   public boolean isStoreProtocolSet() {
      return this._isSet(2);
   }

   public void setStoreProtocol(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StoreProtocol;
      this._StoreProtocol = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getStoreProtocolClass() {
      return this._StoreProtocolClass;
   }

   public boolean isStoreProtocolClassInherited() {
      return false;
   }

   public boolean isStoreProtocolClassSet() {
      return this._isSet(3);
   }

   public void setStoreProtocolClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StoreProtocolClass;
      this._StoreProtocolClass = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getTransportProtocol() {
      return this._TransportProtocol;
   }

   public boolean isTransportProtocolInherited() {
      return false;
   }

   public boolean isTransportProtocolSet() {
      return this._isSet(4);
   }

   public void setTransportProtocol(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TransportProtocol;
      this._TransportProtocol = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getTransportProtocolClass() {
      return this._TransportProtocolClass;
   }

   public boolean isTransportProtocolClassInherited() {
      return false;
   }

   public boolean isTransportProtocolClassSet() {
      return this._isSet(5);
   }

   public void setTransportProtocolClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TransportProtocolClass;
      this._TransportProtocolClass = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getHost() {
      return this._Host;
   }

   public boolean isHostInherited() {
      return false;
   }

   public boolean isHostSet() {
      return this._isSet(6);
   }

   public void setHost(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Host;
      this._Host = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getUser() {
      return this._User;
   }

   public boolean isUserInherited() {
      return false;
   }

   public boolean isUserSet() {
      return this._isSet(7);
   }

   public void setUser(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._User;
      this._User = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getPassword() {
      return this._Password;
   }

   public boolean isPasswordInherited() {
      return false;
   }

   public boolean isPasswordSet() {
      return this._isSet(8);
   }

   public void setPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Password;
      this._Password = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getFrom() {
      return this._From;
   }

   public boolean isFromInherited() {
      return false;
   }

   public boolean isFromSet() {
      return this._isSet(9);
   }

   public void setFrom(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._From;
      this._From = param0;
      this._postSet(9, _oldVal, param0);
   }

   public void addProperty(JavaEEPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         JavaEEPropertyBean[] _new;
         if (this._isSet(10)) {
            _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._extendArray(this.getProperties(), JavaEEPropertyBean.class, param0));
         } else {
            _new = new JavaEEPropertyBean[]{param0};
         }

         try {
            this.setProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JavaEEPropertyBean[] getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(10);
   }

   public void removeProperty(JavaEEPropertyBean param0) {
      this.destroyProperty(param0);
   }

   public void setProperties(JavaEEPropertyBean[] param0) throws InvalidAttributeValueException {
      JavaEEPropertyBean[] param0 = param0 == null ? new JavaEEPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JavaEEPropertyBean[] _oldVal = this._Properties;
      this._Properties = (JavaEEPropertyBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public JavaEEPropertyBean lookupProperty(String param0) {
      Object[] aary = (Object[])this._Properties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JavaEEPropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JavaEEPropertyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JavaEEPropertyBean createProperty() {
      JavaEEPropertyBeanImpl _val = new JavaEEPropertyBeanImpl(this, -1);

      try {
         this.addProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProperty(JavaEEPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         JavaEEPropertyBean[] _old = this.getProperties();
         JavaEEPropertyBean[] _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._removeElement(_old, JavaEEPropertyBean.class, param0));
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
               this.setProperties(_new);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(11);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(11, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Name", this.isNameSet());
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Description = "";
               if (initOne) {
                  break;
               }
            case 9:
               this._From = "";
               if (initOne) {
                  break;
               }
            case 6:
               this._Host = "";
               if (initOne) {
                  break;
               }
            case 11:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._Password = "";
               if (initOne) {
                  break;
               }
            case 10:
               this._Properties = new JavaEEPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._StoreProtocol = "";
               if (initOne) {
                  break;
               }
            case 3:
               this._StoreProtocolClass = "";
               if (initOne) {
                  break;
               }
            case 4:
               this._TransportProtocol = "";
               if (initOne) {
                  break;
               }
            case 5:
               this._TransportProtocolClass = "";
               if (initOne) {
                  break;
               }
            case 7:
               this._User = "";
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 11;
               }
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 4:
               if (s.equals("from")) {
                  return 9;
               }

               if (s.equals("host")) {
                  return 6;
               }

               if (s.equals("name")) {
                  return 1;
               }

               if (s.equals("user")) {
                  return 7;
               }
               break;
            case 8:
               if (s.equals("password")) {
                  return 8;
               }

               if (s.equals("property")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("store-protocol")) {
                  return 2;
               }
               break;
            case 18:
               if (s.equals("transport-protocol")) {
                  return 4;
               }
               break;
            case 20:
               if (s.equals("store-protocol-class")) {
                  return 3;
               }
               break;
            case 24:
               if (s.equals("transport-protocol-class")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new JavaEEPropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "name";
            case 2:
               return "store-protocol";
            case 3:
               return "store-protocol-class";
            case 4:
               return "transport-protocol";
            case 5:
               return "transport-protocol-class";
            case 6:
               return "host";
            case 7:
               return "user";
            case 8:
               return "password";
            case 9:
               return "from";
            case 10:
               return "property";
            case 11:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MailSessionBeanImpl bean;

      protected Helper(MailSessionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "Name";
            case 2:
               return "StoreProtocol";
            case 3:
               return "StoreProtocolClass";
            case 4:
               return "TransportProtocol";
            case 5:
               return "TransportProtocolClass";
            case 6:
               return "Host";
            case 7:
               return "User";
            case 8:
               return "Password";
            case 9:
               return "From";
            case 10:
               return "Properties";
            case 11:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("From")) {
            return 9;
         } else if (propName.equals("Host")) {
            return 6;
         } else if (propName.equals("Id")) {
            return 11;
         } else if (propName.equals("Name")) {
            return 1;
         } else if (propName.equals("Password")) {
            return 8;
         } else if (propName.equals("Properties")) {
            return 10;
         } else if (propName.equals("StoreProtocol")) {
            return 2;
         } else if (propName.equals("StoreProtocolClass")) {
            return 3;
         } else if (propName.equals("TransportProtocol")) {
            return 4;
         } else if (propName.equals("TransportProtocolClass")) {
            return 5;
         } else {
            return propName.equals("User") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getProperties()));
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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isFromSet()) {
               buf.append("From");
               buf.append(String.valueOf(this.bean.getFrom()));
            }

            if (this.bean.isHostSet()) {
               buf.append("Host");
               buf.append(String.valueOf(this.bean.getHost()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isStoreProtocolSet()) {
               buf.append("StoreProtocol");
               buf.append(String.valueOf(this.bean.getStoreProtocol()));
            }

            if (this.bean.isStoreProtocolClassSet()) {
               buf.append("StoreProtocolClass");
               buf.append(String.valueOf(this.bean.getStoreProtocolClass()));
            }

            if (this.bean.isTransportProtocolSet()) {
               buf.append("TransportProtocol");
               buf.append(String.valueOf(this.bean.getTransportProtocol()));
            }

            if (this.bean.isTransportProtocolClassSet()) {
               buf.append("TransportProtocolClass");
               buf.append(String.valueOf(this.bean.getTransportProtocolClass()));
            }

            if (this.bean.isUserSet()) {
               buf.append("User");
               buf.append(String.valueOf(this.bean.getUser()));
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
            MailSessionBeanImpl otherTyped = (MailSessionBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("From", this.bean.getFrom(), otherTyped.getFrom(), false);
            this.computeDiff("Host", this.bean.getHost(), otherTyped.getHost(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Password", this.bean.getPassword(), otherTyped.getPassword(), false);
            this.computeChildDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), false);
            this.computeDiff("StoreProtocol", this.bean.getStoreProtocol(), otherTyped.getStoreProtocol(), false);
            this.computeDiff("StoreProtocolClass", this.bean.getStoreProtocolClass(), otherTyped.getStoreProtocolClass(), false);
            this.computeDiff("TransportProtocol", this.bean.getTransportProtocol(), otherTyped.getTransportProtocol(), false);
            this.computeDiff("TransportProtocolClass", this.bean.getTransportProtocolClass(), otherTyped.getTransportProtocolClass(), false);
            this.computeDiff("User", this.bean.getUser(), otherTyped.getUser(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MailSessionBeanImpl original = (MailSessionBeanImpl)event.getSourceBean();
            MailSessionBeanImpl proposed = (MailSessionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("From")) {
                  original.setFrom(proposed.getFrom());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("Host")) {
                  original.setHost(proposed.getHost());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Password")) {
                  original.setPassword(proposed.getPassword());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Properties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addProperty((JavaEEPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeProperty((JavaEEPropertyBean)update.getRemovedObject());
                  }

                  if (original.getProperties() == null || original.getProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("StoreProtocol")) {
                  original.setStoreProtocol(proposed.getStoreProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("StoreProtocolClass")) {
                  original.setStoreProtocolClass(proposed.getStoreProtocolClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TransportProtocol")) {
                  original.setTransportProtocol(proposed.getTransportProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TransportProtocolClass")) {
                  original.setTransportProtocolClass(proposed.getTransportProtocolClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("User")) {
                  original.setUser(proposed.getUser());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
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
            MailSessionBeanImpl copy = (MailSessionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("From")) && this.bean.isFromSet()) {
               copy.setFrom(this.bean.getFrom());
            }

            if ((excludeProps == null || !excludeProps.contains("Host")) && this.bean.isHostSet()) {
               copy.setHost(this.bean.getHost());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Password")) && this.bean.isPasswordSet()) {
               copy.setPassword(this.bean.getPassword());
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(10)) {
               JavaEEPropertyBean[] oldProperties = this.bean.getProperties();
               JavaEEPropertyBean[] newProperties = new JavaEEPropertyBean[oldProperties.length];

               for(int i = 0; i < newProperties.length; ++i) {
                  newProperties[i] = (JavaEEPropertyBean)((JavaEEPropertyBean)this.createCopy((AbstractDescriptorBean)oldProperties[i], includeObsolete));
               }

               copy.setProperties(newProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("StoreProtocol")) && this.bean.isStoreProtocolSet()) {
               copy.setStoreProtocol(this.bean.getStoreProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreProtocolClass")) && this.bean.isStoreProtocolClassSet()) {
               copy.setStoreProtocolClass(this.bean.getStoreProtocolClass());
            }

            if ((excludeProps == null || !excludeProps.contains("TransportProtocol")) && this.bean.isTransportProtocolSet()) {
               copy.setTransportProtocol(this.bean.getTransportProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("TransportProtocolClass")) && this.bean.isTransportProtocolClassSet()) {
               copy.setTransportProtocolClass(this.bean.getTransportProtocolClass());
            }

            if ((excludeProps == null || !excludeProps.contains("User")) && this.bean.isUserSet()) {
               copy.setUser(this.bean.getUser());
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
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}
