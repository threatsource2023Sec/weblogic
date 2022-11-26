package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AssociationBeanImpl extends AbstractDescriptorBean implements AssociationBean, Serializable {
   private InterceptionPointBean _InterceptionPoint;
   private ProcessorAssociationBean _Processor;
   private static SchemaHelper2 _schemaHelper;

   public AssociationBeanImpl() {
      this._initializeProperty(-1);
   }

   public AssociationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AssociationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public InterceptionPointBean getInterceptionPoint() {
      return this._InterceptionPoint;
   }

   public String getInterceptionPointAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getInterceptionPoint();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isInterceptionPointInherited() {
      return false;
   }

   public boolean isInterceptionPointSet() {
      return this._isSet(0);
   }

   public void setInterceptionPointAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, InterceptionPointBean.class, new ReferenceManager.Resolver(this, 0) {
            public void resolveReference(Object value) {
               try {
                  AssociationBeanImpl.this.setInterceptionPoint((InterceptionPointBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         InterceptionPointBean _oldVal = this._InterceptionPoint;
         this._initializeProperty(0);
         this._postSet(0, _oldVal, this._InterceptionPoint);
      }

   }

   public void setInterceptionPoint(InterceptionPointBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 0, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return AssociationBeanImpl.this.getInterceptionPoint();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      InterceptionPointBean _oldVal = this._InterceptionPoint;
      this._InterceptionPoint = param0;
      this._postSet(0, _oldVal, param0);
   }

   public ProcessorAssociationBean getProcessor() {
      return this._Processor;
   }

   public boolean isProcessorInherited() {
      return false;
   }

   public boolean isProcessorSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getProcessor());
   }

   public void setProcessor(ProcessorAssociationBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      ProcessorAssociationBean _oldVal = this._Processor;
      this._Processor = param0;
      this._postSet(1, _oldVal, param0);
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
      return super._isAnythingSet() || this.isProcessorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._InterceptionPoint = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Processor = new ProcessorAssociationBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._Processor);
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
            case 9:
               if (s.equals("processor")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("interception-point")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ProcessorAssociationBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "interception-point";
            case 1:
               return "processor";
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
      private AssociationBeanImpl bean;

      protected Helper(AssociationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InterceptionPoint";
            case 1:
               return "Processor";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("InterceptionPoint")) {
            return 0;
         } else {
            return propName.equals("Processor") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getProcessor() != null) {
            iterators.add(new ArrayIterator(new ProcessorAssociationBean[]{this.bean.getProcessor()}));
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
            if (this.bean.isInterceptionPointSet()) {
               buf.append("InterceptionPoint");
               buf.append(String.valueOf(this.bean.getInterceptionPoint()));
            }

            childValue = this.computeChildHashValue(this.bean.getProcessor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            AssociationBeanImpl otherTyped = (AssociationBeanImpl)other;
            this.computeDiff("InterceptionPoint", this.bean.getInterceptionPoint(), otherTyped.getInterceptionPoint(), false);
            this.computeSubDiff("Processor", this.bean.getProcessor(), otherTyped.getProcessor());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AssociationBeanImpl original = (AssociationBeanImpl)event.getSourceBean();
            AssociationBeanImpl proposed = (AssociationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("InterceptionPoint")) {
                  original.setInterceptionPointAsString(proposed.getInterceptionPointAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Processor")) {
                  if (type == 2) {
                     original.setProcessor((ProcessorAssociationBean)this.createCopy((AbstractDescriptorBean)proposed.getProcessor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Processor", (DescriptorBean)original.getProcessor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            AssociationBeanImpl copy = (AssociationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("InterceptionPoint")) && this.bean.isInterceptionPointSet()) {
               copy._unSet(copy, 0);
               copy.setInterceptionPointAsString(this.bean.getInterceptionPointAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Processor")) && this.bean.isProcessorSet() && !copy._isSet(1)) {
               Object o = this.bean.getProcessor();
               copy.setProcessor((ProcessorAssociationBean)null);
               copy.setProcessor(o == null ? null : (ProcessorAssociationBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getInterceptionPoint(), clazz, annotation);
         this.inferSubTree(this.bean.getProcessor(), clazz, annotation);
      }
   }
}
