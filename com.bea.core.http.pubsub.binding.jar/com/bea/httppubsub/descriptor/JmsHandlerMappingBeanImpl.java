package com.bea.httppubsub.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JmsHandlerMappingBeanImpl extends AbstractDescriptorBean implements JmsHandlerMappingBean, Serializable {
   private JmsHandlerBean _JmsHandler;
   private String _JmsHandlerName;
   private static SchemaHelper2 _schemaHelper;

   public JmsHandlerMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public JmsHandlerMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JmsHandlerMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJmsHandlerName() {
      return this._JmsHandlerName;
   }

   public boolean isJmsHandlerNameInherited() {
      return false;
   }

   public boolean isJmsHandlerNameSet() {
      return this._isSet(0);
   }

   public void setJmsHandlerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JmsHandlerName;
      this._JmsHandlerName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public JmsHandlerBean getJmsHandler() {
      return this._JmsHandler;
   }

   public boolean isJmsHandlerInherited() {
      return false;
   }

   public boolean isJmsHandlerSet() {
      return this._isSet(1);
   }

   public void setJmsHandler(JmsHandlerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJmsHandler() != null && param0 != this.getJmsHandler()) {
         throw new BeanAlreadyExistsException(this.getJmsHandler() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JmsHandlerBean _oldVal = this._JmsHandler;
         this._JmsHandler = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public JmsHandlerBean createJmsHandler() {
      JmsHandlerBeanImpl _val = new JmsHandlerBeanImpl(this, -1);

      try {
         this.setJmsHandler(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._JmsHandler = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._JmsHandlerName = null;
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
            case 11:
               if (s.equals("jms-handler")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("jms-handler-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new JmsHandlerBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "jms-handler-name";
            case 1:
               return "jms-handler";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JmsHandlerMappingBeanImpl bean;

      protected Helper(JmsHandlerMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JmsHandlerName";
            case 1:
               return "JmsHandler";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("JmsHandler")) {
            return 1;
         } else {
            return propName.equals("JmsHandlerName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getJmsHandler() != null) {
            iterators.add(new ArrayIterator(new JmsHandlerBean[]{this.bean.getJmsHandler()}));
         }

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
            childValue = this.computeChildHashValue(this.bean.getJmsHandler());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJmsHandlerNameSet()) {
               buf.append("JmsHandlerName");
               buf.append(String.valueOf(this.bean.getJmsHandlerName()));
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
            JmsHandlerMappingBeanImpl otherTyped = (JmsHandlerMappingBeanImpl)other;
            this.computeChildDiff("JmsHandler", this.bean.getJmsHandler(), otherTyped.getJmsHandler(), false);
            this.computeDiff("JmsHandlerName", this.bean.getJmsHandlerName(), otherTyped.getJmsHandlerName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JmsHandlerMappingBeanImpl original = (JmsHandlerMappingBeanImpl)event.getSourceBean();
            JmsHandlerMappingBeanImpl proposed = (JmsHandlerMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("JmsHandler")) {
                  if (type == 2) {
                     original.setJmsHandler((JmsHandlerBean)this.createCopy((AbstractDescriptorBean)proposed.getJmsHandler()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JmsHandler", (DescriptorBean)original.getJmsHandler());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("JmsHandlerName")) {
                  original.setJmsHandlerName(proposed.getJmsHandlerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            JmsHandlerMappingBeanImpl copy = (JmsHandlerMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("JmsHandler")) && this.bean.isJmsHandlerSet() && !copy._isSet(1)) {
               Object o = this.bean.getJmsHandler();
               copy.setJmsHandler((JmsHandlerBean)null);
               copy.setJmsHandler(o == null ? null : (JmsHandlerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JmsHandlerName")) && this.bean.isJmsHandlerNameSet()) {
               copy.setJmsHandlerName(this.bean.getJmsHandlerName());
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
         this.inferSubTree(this.bean.getJmsHandler(), clazz, annotation);
      }
   }
}
