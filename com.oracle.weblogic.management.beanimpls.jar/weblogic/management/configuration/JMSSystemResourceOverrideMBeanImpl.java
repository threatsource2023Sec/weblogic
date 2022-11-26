package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JMSSystemResourceOverrideMBeanImpl extends ConfigurationMBeanImpl implements JMSSystemResourceOverrideMBean, Serializable {
   private ForeignServerOverrideMBean[] _ForeignServers;
   private static SchemaHelper2 _schemaHelper;

   public JMSSystemResourceOverrideMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMSSystemResourceOverrideMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMSSystemResourceOverrideMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addForeignServer(ForeignServerOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         ForeignServerOverrideMBean[] _new;
         if (this._isSet(10)) {
            _new = (ForeignServerOverrideMBean[])((ForeignServerOverrideMBean[])this._getHelper()._extendArray(this.getForeignServers(), ForeignServerOverrideMBean.class, param0));
         } else {
            _new = new ForeignServerOverrideMBean[]{param0};
         }

         try {
            this.setForeignServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignServerOverrideMBean[] getForeignServers() {
      return this._ForeignServers;
   }

   public boolean isForeignServersInherited() {
      return false;
   }

   public boolean isForeignServersSet() {
      return this._isSet(10);
   }

   public void removeForeignServer(ForeignServerOverrideMBean param0) {
      this.destroyForeignServer(param0);
   }

   public void setForeignServers(ForeignServerOverrideMBean[] param0) throws InvalidAttributeValueException {
      ForeignServerOverrideMBean[] param0 = param0 == null ? new ForeignServerOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignServerOverrideMBean[] _oldVal = this._ForeignServers;
      this._ForeignServers = (ForeignServerOverrideMBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public ForeignServerOverrideMBean createForeignServer(String param0) {
      ForeignServerOverrideMBeanImpl lookup = (ForeignServerOverrideMBeanImpl)this.lookupForeignServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignServerOverrideMBeanImpl _val = new ForeignServerOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignServer(_val);
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

   public void destroyForeignServer(ForeignServerOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         ForeignServerOverrideMBean[] _old = this.getForeignServers();
         ForeignServerOverrideMBean[] _new = (ForeignServerOverrideMBean[])((ForeignServerOverrideMBean[])this._getHelper()._removeElement(_old, ForeignServerOverrideMBean.class, param0));
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
               this.setForeignServers(_new);
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

   public ForeignServerOverrideMBean lookupForeignServer(String param0) {
      Object[] aary = (Object[])this._ForeignServers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignServerOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignServerOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
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
               this._ForeignServers = new ForeignServerOverrideMBean[0];
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
      return "JMSSystemResourceOverride";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ForeignServers")) {
         ForeignServerOverrideMBean[] oldVal = this._ForeignServers;
         this._ForeignServers = (ForeignServerOverrideMBean[])((ForeignServerOverrideMBean[])v);
         this._postSet(10, oldVal, this._ForeignServers);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      return name.equals("ForeignServers") ? this._ForeignServers : super.getValue(name);
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("foreign-server")) {
                  return 10;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new ForeignServerOverrideMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "foreign-server";
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
            case 10:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private JMSSystemResourceOverrideMBeanImpl bean;

      protected Helper(JMSSystemResourceOverrideMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ForeignServers";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("ForeignServers") ? 10 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getForeignServers()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getForeignServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            JMSSystemResourceOverrideMBeanImpl otherTyped = (JMSSystemResourceOverrideMBeanImpl)other;
            this.addRestartElements("ForeignServers", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeChildDiff("ForeignServers", this.bean.getForeignServers(), otherTyped.getForeignServers(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSSystemResourceOverrideMBeanImpl original = (JMSSystemResourceOverrideMBeanImpl)event.getSourceBean();
            JMSSystemResourceOverrideMBeanImpl proposed = (JMSSystemResourceOverrideMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ForeignServers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignServer((ForeignServerOverrideMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignServer((ForeignServerOverrideMBean)update.getRemovedObject());
                  }

                  if (original.getForeignServers() == null || original.getForeignServers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
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
            JMSSystemResourceOverrideMBeanImpl copy = (JMSSystemResourceOverrideMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ForeignServers")) && this.bean.isForeignServersSet() && !copy._isSet(10)) {
               ForeignServerOverrideMBean[] oldForeignServers = this.bean.getForeignServers();
               ForeignServerOverrideMBean[] newForeignServers = new ForeignServerOverrideMBean[oldForeignServers.length];

               for(int i = 0; i < newForeignServers.length; ++i) {
                  newForeignServers[i] = (ForeignServerOverrideMBean)((ForeignServerOverrideMBean)this.createCopy((AbstractDescriptorBean)oldForeignServers[i], includeObsolete));
               }

               copy.setForeignServers(newForeignServers);
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
         this.inferSubTree(this.bean.getForeignServers(), clazz, annotation);
      }
   }
}
