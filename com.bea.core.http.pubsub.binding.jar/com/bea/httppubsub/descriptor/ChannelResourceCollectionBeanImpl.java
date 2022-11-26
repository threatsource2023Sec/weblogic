package com.bea.httppubsub.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ChannelResourceCollectionBeanImpl extends AbstractDescriptorBean implements ChannelResourceCollectionBean, Serializable {
   private String[] _ChannelOperations;
   private String[] _ChannelPatterns;
   private String _ChannelResourceName;
   private String[] _Descriptions;
   private static SchemaHelper2 _schemaHelper;

   public ChannelResourceCollectionBeanImpl() {
      this._initializeProperty(-1);
   }

   public ChannelResourceCollectionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ChannelResourceCollectionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getChannelResourceName() {
      return this._ChannelResourceName;
   }

   public boolean isChannelResourceNameInherited() {
      return false;
   }

   public boolean isChannelResourceNameSet() {
      return this._isSet(0);
   }

   public void setChannelResourceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ChannelResourceName;
      this._ChannelResourceName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(1);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getChannelPatterns() {
      return this._ChannelPatterns;
   }

   public boolean isChannelPatternsInherited() {
      return false;
   }

   public boolean isChannelPatternsSet() {
      return this._isSet(2);
   }

   public void addChannelPattern(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(2)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getChannelPatterns(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setChannelPatterns(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeChannelPattern(String param0) {
      String[] _old = this.getChannelPatterns();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setChannelPatterns(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setChannelPatterns(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ChannelPatterns;
      this._ChannelPatterns = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getChannelOperations() {
      return this._ChannelOperations;
   }

   public boolean isChannelOperationsInherited() {
      return false;
   }

   public boolean isChannelOperationsSet() {
      return this._isSet(3);
   }

   public void addChannelOperation(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getChannelOperations(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setChannelOperations(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeChannelOperation(String param0) {
      String[] _old = this.getChannelOperations();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setChannelOperations(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setChannelOperations(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ChannelOperations;
      this._ChannelOperations = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getChannelResourceName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 21:
            if (s.equals("channel-resource-name")) {
               return info.compareXpaths(this._getPropertyXpath("channel-resource-name"));
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._ChannelOperations = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._ChannelPatterns = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ChannelResourceName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Descriptions = new String[0];
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
               if (s.equals("description")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("channel-pattern")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("channel-operation")) {
                  return 3;
               }
               break;
            case 21:
               if (s.equals("channel-resource-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "channel-resource-name";
            case 1:
               return "description";
            case 2:
               return "channel-pattern";
            case 3:
               return "channel-operation";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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
         indices.add("channel-resource-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ChannelResourceCollectionBeanImpl bean;

      protected Helper(ChannelResourceCollectionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ChannelResourceName";
            case 1:
               return "Descriptions";
            case 2:
               return "ChannelPatterns";
            case 3:
               return "ChannelOperations";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ChannelOperations")) {
            return 3;
         } else if (propName.equals("ChannelPatterns")) {
            return 2;
         } else if (propName.equals("ChannelResourceName")) {
            return 0;
         } else {
            return propName.equals("Descriptions") ? 1 : super.getPropertyIndex(propName);
         }
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
            if (this.bean.isChannelOperationsSet()) {
               buf.append("ChannelOperations");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getChannelOperations())));
            }

            if (this.bean.isChannelPatternsSet()) {
               buf.append("ChannelPatterns");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getChannelPatterns())));
            }

            if (this.bean.isChannelResourceNameSet()) {
               buf.append("ChannelResourceName");
               buf.append(String.valueOf(this.bean.getChannelResourceName()));
            }

            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
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
            ChannelResourceCollectionBeanImpl otherTyped = (ChannelResourceCollectionBeanImpl)other;
            this.computeDiff("ChannelOperations", this.bean.getChannelOperations(), otherTyped.getChannelOperations(), false);
            this.computeDiff("ChannelPatterns", this.bean.getChannelPatterns(), otherTyped.getChannelPatterns(), false);
            this.computeDiff("ChannelResourceName", this.bean.getChannelResourceName(), otherTyped.getChannelResourceName(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ChannelResourceCollectionBeanImpl original = (ChannelResourceCollectionBeanImpl)event.getSourceBean();
            ChannelResourceCollectionBeanImpl proposed = (ChannelResourceCollectionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ChannelOperations")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addChannelOperation((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeChannelOperation((String)update.getRemovedObject());
                  }

                  if (original.getChannelOperations() == null || original.getChannelOperations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("ChannelPatterns")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addChannelPattern((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeChannelPattern((String)update.getRemovedObject());
                  }

                  if (original.getChannelPatterns() == null || original.getChannelPatterns().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("ChannelResourceName")) {
                  original.setChannelResourceName(proposed.getChannelResourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            ChannelResourceCollectionBeanImpl copy = (ChannelResourceCollectionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("ChannelOperations")) && this.bean.isChannelOperationsSet()) {
               o = this.bean.getChannelOperations();
               copy.setChannelOperations(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ChannelPatterns")) && this.bean.isChannelPatternsSet()) {
               o = this.bean.getChannelPatterns();
               copy.setChannelPatterns(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ChannelResourceName")) && this.bean.isChannelResourceNameSet()) {
               copy.setChannelResourceName(this.bean.getChannelResourceName());
            }

            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
      }
   }
}
