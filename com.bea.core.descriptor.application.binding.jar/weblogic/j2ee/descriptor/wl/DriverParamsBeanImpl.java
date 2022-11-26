package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DriverParamsBeanImpl extends AbstractDescriptorBean implements DriverParamsBean, Serializable {
   private PreparedStatementBean _PreparedStatement;
   private boolean _RowPrefetchEnabled;
   private int _RowPrefetchSize;
   private StatementBean _Statement;
   private int _StreamChunkSize;
   private static SchemaHelper2 _schemaHelper;

   public DriverParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public DriverParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DriverParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public StatementBean getStatement() {
      return this._Statement;
   }

   public boolean isStatementInherited() {
      return false;
   }

   public boolean isStatementSet() {
      return this._isSet(0);
   }

   public void setStatement(StatementBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getStatement() != null && param0 != this.getStatement()) {
         throw new BeanAlreadyExistsException(this.getStatement() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         StatementBean _oldVal = this._Statement;
         this._Statement = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public StatementBean createStatement() {
      StatementBeanImpl _val = new StatementBeanImpl(this, -1);

      try {
         this.setStatement(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyStatement(StatementBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Statement;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setStatement((StatementBean)null);
               this._unSet(0);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public PreparedStatementBean getPreparedStatement() {
      return this._PreparedStatement;
   }

   public boolean isPreparedStatementInherited() {
      return false;
   }

   public boolean isPreparedStatementSet() {
      return this._isSet(1);
   }

   public void setPreparedStatement(PreparedStatementBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPreparedStatement() != null && param0 != this.getPreparedStatement()) {
         throw new BeanAlreadyExistsException(this.getPreparedStatement() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PreparedStatementBean _oldVal = this._PreparedStatement;
         this._PreparedStatement = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public PreparedStatementBean createPreparedStatement() {
      PreparedStatementBeanImpl _val = new PreparedStatementBeanImpl(this, -1);

      try {
         this.setPreparedStatement(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPreparedStatement(PreparedStatementBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PreparedStatement;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPreparedStatement((PreparedStatementBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean isRowPrefetchEnabled() {
      return this._RowPrefetchEnabled;
   }

   public boolean isRowPrefetchEnabledInherited() {
      return false;
   }

   public boolean isRowPrefetchEnabledSet() {
      return this._isSet(2);
   }

   public void setRowPrefetchEnabled(boolean param0) {
      boolean _oldVal = this._RowPrefetchEnabled;
      this._RowPrefetchEnabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getRowPrefetchSize() {
      return this._RowPrefetchSize;
   }

   public boolean isRowPrefetchSizeInherited() {
      return false;
   }

   public boolean isRowPrefetchSizeSet() {
      return this._isSet(3);
   }

   public void setRowPrefetchSize(int param0) {
      int _oldVal = this._RowPrefetchSize;
      this._RowPrefetchSize = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getStreamChunkSize() {
      return this._StreamChunkSize;
   }

   public boolean isStreamChunkSizeInherited() {
      return false;
   }

   public boolean isStreamChunkSizeSet() {
      return this._isSet(4);
   }

   public void setStreamChunkSize(int param0) {
      int _oldVal = this._StreamChunkSize;
      this._StreamChunkSize = param0;
      this._postSet(4, _oldVal, param0);
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
               this._PreparedStatement = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._RowPrefetchSize = 48;
               if (initOne) {
                  break;
               }
            case 0:
               this._Statement = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._StreamChunkSize = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._RowPrefetchEnabled = false;
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
               if (s.equals("statement")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("row-prefetch-size")) {
                  return 3;
               }

               if (s.equals("stream-chunk-size")) {
                  return 4;
               }
               break;
            case 18:
               if (s.equals("prepared-statement")) {
                  return 1;
               }
               break;
            case 20:
               if (s.equals("row-prefetch-enabled")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new StatementBeanImpl.SchemaHelper2();
            case 1:
               return new PreparedStatementBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "statement";
            case 1:
               return "prepared-statement";
            case 2:
               return "row-prefetch-enabled";
            case 3:
               return "row-prefetch-size";
            case 4:
               return "stream-chunk-size";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private DriverParamsBeanImpl bean;

      protected Helper(DriverParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Statement";
            case 1:
               return "PreparedStatement";
            case 2:
               return "RowPrefetchEnabled";
            case 3:
               return "RowPrefetchSize";
            case 4:
               return "StreamChunkSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("PreparedStatement")) {
            return 1;
         } else if (propName.equals("RowPrefetchSize")) {
            return 3;
         } else if (propName.equals("Statement")) {
            return 0;
         } else if (propName.equals("StreamChunkSize")) {
            return 4;
         } else {
            return propName.equals("RowPrefetchEnabled") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getPreparedStatement() != null) {
            iterators.add(new ArrayIterator(new PreparedStatementBean[]{this.bean.getPreparedStatement()}));
         }

         if (this.bean.getStatement() != null) {
            iterators.add(new ArrayIterator(new StatementBean[]{this.bean.getStatement()}));
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
            childValue = this.computeChildHashValue(this.bean.getPreparedStatement());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRowPrefetchSizeSet()) {
               buf.append("RowPrefetchSize");
               buf.append(String.valueOf(this.bean.getRowPrefetchSize()));
            }

            childValue = this.computeChildHashValue(this.bean.getStatement());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isStreamChunkSizeSet()) {
               buf.append("StreamChunkSize");
               buf.append(String.valueOf(this.bean.getStreamChunkSize()));
            }

            if (this.bean.isRowPrefetchEnabledSet()) {
               buf.append("RowPrefetchEnabled");
               buf.append(String.valueOf(this.bean.isRowPrefetchEnabled()));
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
            DriverParamsBeanImpl otherTyped = (DriverParamsBeanImpl)other;
            this.computeChildDiff("PreparedStatement", this.bean.getPreparedStatement(), otherTyped.getPreparedStatement(), false);
            this.computeDiff("RowPrefetchSize", this.bean.getRowPrefetchSize(), otherTyped.getRowPrefetchSize(), false);
            this.computeChildDiff("Statement", this.bean.getStatement(), otherTyped.getStatement(), false);
            this.computeDiff("StreamChunkSize", this.bean.getStreamChunkSize(), otherTyped.getStreamChunkSize(), false);
            this.computeDiff("RowPrefetchEnabled", this.bean.isRowPrefetchEnabled(), otherTyped.isRowPrefetchEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DriverParamsBeanImpl original = (DriverParamsBeanImpl)event.getSourceBean();
            DriverParamsBeanImpl proposed = (DriverParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PreparedStatement")) {
                  if (type == 2) {
                     original.setPreparedStatement((PreparedStatementBean)this.createCopy((AbstractDescriptorBean)proposed.getPreparedStatement()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PreparedStatement", (DescriptorBean)original.getPreparedStatement());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("RowPrefetchSize")) {
                  original.setRowPrefetchSize(proposed.getRowPrefetchSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Statement")) {
                  if (type == 2) {
                     original.setStatement((StatementBean)this.createCopy((AbstractDescriptorBean)proposed.getStatement()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Statement", (DescriptorBean)original.getStatement());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("StreamChunkSize")) {
                  original.setStreamChunkSize(proposed.getStreamChunkSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("RowPrefetchEnabled")) {
                  original.setRowPrefetchEnabled(proposed.isRowPrefetchEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            DriverParamsBeanImpl copy = (DriverParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PreparedStatement")) && this.bean.isPreparedStatementSet() && !copy._isSet(1)) {
               Object o = this.bean.getPreparedStatement();
               copy.setPreparedStatement((PreparedStatementBean)null);
               copy.setPreparedStatement(o == null ? null : (PreparedStatementBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RowPrefetchSize")) && this.bean.isRowPrefetchSizeSet()) {
               copy.setRowPrefetchSize(this.bean.getRowPrefetchSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Statement")) && this.bean.isStatementSet() && !copy._isSet(0)) {
               Object o = this.bean.getStatement();
               copy.setStatement((StatementBean)null);
               copy.setStatement(o == null ? null : (StatementBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StreamChunkSize")) && this.bean.isStreamChunkSizeSet()) {
               copy.setStreamChunkSize(this.bean.getStreamChunkSize());
            }

            if ((excludeProps == null || !excludeProps.contains("RowPrefetchEnabled")) && this.bean.isRowPrefetchEnabledSet()) {
               copy.setRowPrefetchEnabled(this.bean.isRowPrefetchEnabled());
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
         this.inferSubTree(this.bean.getPreparedStatement(), clazz, annotation);
         this.inferSubTree(this.bean.getStatement(), clazz, annotation);
      }
   }
}
