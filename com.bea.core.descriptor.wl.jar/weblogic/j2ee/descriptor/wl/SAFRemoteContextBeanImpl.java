package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SAFRemoteContextBeanImpl extends NamedEntityBeanImpl implements SAFRemoteContextBean, Serializable {
   private int _CompressionThreshold;
   private String _ReplyToSAFRemoteContextName;
   private SAFLoginContextBean _SAFLoginContext;
   private static SchemaHelper2 _schemaHelper;

   public SAFRemoteContextBeanImpl() {
      this._initializeProperty(-1);
   }

   public SAFRemoteContextBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SAFRemoteContextBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public SAFLoginContextBean getSAFLoginContext() {
      return this._SAFLoginContext;
   }

   public boolean isSAFLoginContextInherited() {
      return false;
   }

   public boolean isSAFLoginContextSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getSAFLoginContext());
   }

   public void setSAFLoginContext(SAFLoginContextBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      SAFLoginContextBean _oldVal = this._SAFLoginContext;
      this._SAFLoginContext = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getCompressionThreshold() {
      return this._CompressionThreshold;
   }

   public boolean isCompressionThresholdInherited() {
      return false;
   }

   public boolean isCompressionThresholdSet() {
      return this._isSet(4);
   }

   public void setCompressionThreshold(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("CompressionThreshold", (long)param0, 0L, 2147483647L);
      int _oldVal = this._CompressionThreshold;
      this._CompressionThreshold = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getReplyToSAFRemoteContextName() {
      return this._ReplyToSAFRemoteContextName;
   }

   public boolean isReplyToSAFRemoteContextNameInherited() {
      return false;
   }

   public boolean isReplyToSAFRemoteContextNameSet() {
      return this._isSet(5);
   }

   public void setReplyToSAFRemoteContextName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReplyToSAFRemoteContextName;
      this._ReplyToSAFRemoteContextName = param0;
      this._postSet(5, _oldVal, param0);
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
      return super._isAnythingSet() || this.isSAFLoginContextSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._CompressionThreshold = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 5:
               this._ReplyToSAFRemoteContextName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._SAFLoginContext = new SAFLoginContextBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._SAFLoginContext);
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

   public static class SchemaHelper2 extends NamedEntityBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 17:
               if (s.equals("saf-login-context")) {
                  return 3;
               }
               break;
            case 21:
               if (s.equals("compression-threshold")) {
                  return 4;
               }
               break;
            case 32:
               if (s.equals("reply-to-saf-remote-context-name")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new SAFLoginContextBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "saf-login-context";
            case 4:
               return "compression-threshold";
            case 5:
               return "reply-to-saf-remote-context-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
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
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends NamedEntityBeanImpl.Helper {
      private SAFRemoteContextBeanImpl bean;

      protected Helper(SAFRemoteContextBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "SAFLoginContext";
            case 4:
               return "CompressionThreshold";
            case 5:
               return "ReplyToSAFRemoteContextName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CompressionThreshold")) {
            return 4;
         } else if (propName.equals("ReplyToSAFRemoteContextName")) {
            return 5;
         } else {
            return propName.equals("SAFLoginContext") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getSAFLoginContext() != null) {
            iterators.add(new ArrayIterator(new SAFLoginContextBean[]{this.bean.getSAFLoginContext()}));
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
            if (this.bean.isCompressionThresholdSet()) {
               buf.append("CompressionThreshold");
               buf.append(String.valueOf(this.bean.getCompressionThreshold()));
            }

            if (this.bean.isReplyToSAFRemoteContextNameSet()) {
               buf.append("ReplyToSAFRemoteContextName");
               buf.append(String.valueOf(this.bean.getReplyToSAFRemoteContextName()));
            }

            childValue = this.computeChildHashValue(this.bean.getSAFLoginContext());
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
            SAFRemoteContextBeanImpl otherTyped = (SAFRemoteContextBeanImpl)other;
            this.computeDiff("CompressionThreshold", this.bean.getCompressionThreshold(), otherTyped.getCompressionThreshold(), true);
            this.computeDiff("ReplyToSAFRemoteContextName", this.bean.getReplyToSAFRemoteContextName(), otherTyped.getReplyToSAFRemoteContextName(), true);
            this.computeSubDiff("SAFLoginContext", this.bean.getSAFLoginContext(), otherTyped.getSAFLoginContext());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SAFRemoteContextBeanImpl original = (SAFRemoteContextBeanImpl)event.getSourceBean();
            SAFRemoteContextBeanImpl proposed = (SAFRemoteContextBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CompressionThreshold")) {
                  original.setCompressionThreshold(proposed.getCompressionThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ReplyToSAFRemoteContextName")) {
                  original.setReplyToSAFRemoteContextName(proposed.getReplyToSAFRemoteContextName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SAFLoginContext")) {
                  if (type == 2) {
                     original.setSAFLoginContext((SAFLoginContextBean)this.createCopy((AbstractDescriptorBean)proposed.getSAFLoginContext()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SAFLoginContext", (DescriptorBean)original.getSAFLoginContext());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            SAFRemoteContextBeanImpl copy = (SAFRemoteContextBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CompressionThreshold")) && this.bean.isCompressionThresholdSet()) {
               copy.setCompressionThreshold(this.bean.getCompressionThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplyToSAFRemoteContextName")) && this.bean.isReplyToSAFRemoteContextNameSet()) {
               copy.setReplyToSAFRemoteContextName(this.bean.getReplyToSAFRemoteContextName());
            }

            if ((excludeProps == null || !excludeProps.contains("SAFLoginContext")) && this.bean.isSAFLoginContextSet() && !copy._isSet(3)) {
               Object o = this.bean.getSAFLoginContext();
               copy.setSAFLoginContext((SAFLoginContextBean)null);
               copy.setSAFLoginContext(o == null ? null : (SAFLoginContextBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getSAFLoginContext(), clazz, annotation);
      }
   }
}
