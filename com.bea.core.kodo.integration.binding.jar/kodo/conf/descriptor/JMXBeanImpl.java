package kodo.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import kodo.conf.customizers.JMXBeanCustomizer;
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

public class JMXBeanImpl extends AbstractDescriptorBean implements JMXBean, Serializable {
   private GUIJMXBean _GUIJMX;
   private JMX2JMXBean _JMX2JMX;
   private LocalJMXBean _LocalJMX;
   private MX4J1JMXBean _MX4J1JMX;
   private NoneJMXBean _NoneJMX;
   private WLS81JMXBean _WLS81JMX;
   private transient JMXBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public JMXBeanImpl() {
      try {
         this._customizer = new JMXBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JMXBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JMXBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JMXBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JMXBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public NoneJMXBean getNoneJMX() {
      return this._NoneJMX;
   }

   public boolean isNoneJMXInherited() {
      return false;
   }

   public boolean isNoneJMXSet() {
      return this._isSet(0);
   }

   public void setNoneJMX(NoneJMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNoneJMX() != null && param0 != this.getNoneJMX()) {
         throw new BeanAlreadyExistsException(this.getNoneJMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NoneJMXBean _oldVal = this._NoneJMX;
         this._NoneJMX = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public NoneJMXBean createNoneJMX() {
      NoneJMXBeanImpl _val = new NoneJMXBeanImpl(this, -1);

      try {
         this.setNoneJMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNoneJMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NoneJMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNoneJMX((NoneJMXBean)null);
               this._unSet(0);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LocalJMXBean getLocalJMX() {
      return this._LocalJMX;
   }

   public boolean isLocalJMXInherited() {
      return false;
   }

   public boolean isLocalJMXSet() {
      return this._isSet(1);
   }

   public void setLocalJMX(LocalJMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLocalJMX() != null && param0 != this.getLocalJMX()) {
         throw new BeanAlreadyExistsException(this.getLocalJMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LocalJMXBean _oldVal = this._LocalJMX;
         this._LocalJMX = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public LocalJMXBean createLocalJMX() {
      LocalJMXBeanImpl _val = new LocalJMXBeanImpl(this, -1);

      try {
         this.setLocalJMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLocalJMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LocalJMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLocalJMX((LocalJMXBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public GUIJMXBean getGUIJMX() {
      return this._GUIJMX;
   }

   public boolean isGUIJMXInherited() {
      return false;
   }

   public boolean isGUIJMXSet() {
      return this._isSet(2);
   }

   public void setGUIJMX(GUIJMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getGUIJMX() != null && param0 != this.getGUIJMX()) {
         throw new BeanAlreadyExistsException(this.getGUIJMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         GUIJMXBean _oldVal = this._GUIJMX;
         this._GUIJMX = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public GUIJMXBean createGUIJMX() {
      GUIJMXBeanImpl _val = new GUIJMXBeanImpl(this, -1);

      try {
         this.setGUIJMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyGUIJMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._GUIJMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setGUIJMX((GUIJMXBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public JMX2JMXBean getJMX2JMX() {
      return this._JMX2JMX;
   }

   public boolean isJMX2JMXInherited() {
      return false;
   }

   public boolean isJMX2JMXSet() {
      return this._isSet(3);
   }

   public void setJMX2JMX(JMX2JMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJMX2JMX() != null && param0 != this.getJMX2JMX()) {
         throw new BeanAlreadyExistsException(this.getJMX2JMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JMX2JMXBean _oldVal = this._JMX2JMX;
         this._JMX2JMX = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public JMX2JMXBean createJMX2JMX() {
      JMX2JMXBeanImpl _val = new JMX2JMXBeanImpl(this, -1);

      try {
         this.setJMX2JMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJMX2JMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JMX2JMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMX2JMX((JMX2JMXBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public MX4J1JMXBean getMX4J1JMX() {
      return this._MX4J1JMX;
   }

   public boolean isMX4J1JMXInherited() {
      return false;
   }

   public boolean isMX4J1JMXSet() {
      return this._isSet(4);
   }

   public void setMX4J1JMX(MX4J1JMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMX4J1JMX() != null && param0 != this.getMX4J1JMX()) {
         throw new BeanAlreadyExistsException(this.getMX4J1JMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MX4J1JMXBean _oldVal = this._MX4J1JMX;
         this._MX4J1JMX = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public MX4J1JMXBean createMX4J1JMX() {
      MX4J1JMXBeanImpl _val = new MX4J1JMXBeanImpl(this, -1);

      try {
         this.setMX4J1JMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMX4J1JMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MX4J1JMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMX4J1JMX((MX4J1JMXBean)null);
               this._unSet(4);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public WLS81JMXBean getWLS81JMX() {
      return this._WLS81JMX;
   }

   public boolean isWLS81JMXInherited() {
      return false;
   }

   public boolean isWLS81JMXSet() {
      return this._isSet(5);
   }

   public void setWLS81JMX(WLS81JMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWLS81JMX() != null && param0 != this.getWLS81JMX()) {
         throw new BeanAlreadyExistsException(this.getWLS81JMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WLS81JMXBean _oldVal = this._WLS81JMX;
         this._WLS81JMX = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public WLS81JMXBean createWLS81JMX() {
      WLS81JMXBeanImpl _val = new WLS81JMXBeanImpl(this, -1);

      try {
         this.setWLS81JMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWLS81JMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WLS81JMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWLS81JMX((WLS81JMXBean)null);
               this._unSet(5);
            }
         }
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._GUIJMX = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._JMX2JMX = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._LocalJMX = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._MX4J1JMX = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._NoneJMX = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._WLS81JMX = null;
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
            case 7:
               if (s.equals("gui-jmx")) {
                  return 2;
               }
               break;
            case 8:
               if (s.equals("jmx2-jmx")) {
                  return 3;
               }

               if (s.equals("none-jmx")) {
                  return 0;
               }
               break;
            case 9:
               if (s.equals("local-jmx")) {
                  return 1;
               }

               if (s.equals("mx4j1-jmx")) {
                  return 4;
               }

               if (s.equals("wls81-jmx")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new NoneJMXBeanImpl.SchemaHelper2();
            case 1:
               return new LocalJMXBeanImpl.SchemaHelper2();
            case 2:
               return new GUIJMXBeanImpl.SchemaHelper2();
            case 3:
               return new JMX2JMXBeanImpl.SchemaHelper2();
            case 4:
               return new MX4J1JMXBeanImpl.SchemaHelper2();
            case 5:
               return new WLS81JMXBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "none-jmx";
            case 1:
               return "local-jmx";
            case 2:
               return "gui-jmx";
            case 3:
               return "jmx2-jmx";
            case 4:
               return "mx4j1-jmx";
            case 5:
               return "wls81-jmx";
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
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JMXBeanImpl bean;

      protected Helper(JMXBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "NoneJMX";
            case 1:
               return "LocalJMX";
            case 2:
               return "GUIJMX";
            case 3:
               return "JMX2JMX";
            case 4:
               return "MX4J1JMX";
            case 5:
               return "WLS81JMX";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("GUIJMX")) {
            return 2;
         } else if (propName.equals("JMX2JMX")) {
            return 3;
         } else if (propName.equals("LocalJMX")) {
            return 1;
         } else if (propName.equals("MX4J1JMX")) {
            return 4;
         } else if (propName.equals("NoneJMX")) {
            return 0;
         } else {
            return propName.equals("WLS81JMX") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getGUIJMX() != null) {
            iterators.add(new ArrayIterator(new GUIJMXBean[]{this.bean.getGUIJMX()}));
         }

         if (this.bean.getJMX2JMX() != null) {
            iterators.add(new ArrayIterator(new JMX2JMXBean[]{this.bean.getJMX2JMX()}));
         }

         if (this.bean.getLocalJMX() != null) {
            iterators.add(new ArrayIterator(new LocalJMXBean[]{this.bean.getLocalJMX()}));
         }

         if (this.bean.getMX4J1JMX() != null) {
            iterators.add(new ArrayIterator(new MX4J1JMXBean[]{this.bean.getMX4J1JMX()}));
         }

         if (this.bean.getNoneJMX() != null) {
            iterators.add(new ArrayIterator(new NoneJMXBean[]{this.bean.getNoneJMX()}));
         }

         if (this.bean.getWLS81JMX() != null) {
            iterators.add(new ArrayIterator(new WLS81JMXBean[]{this.bean.getWLS81JMX()}));
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
            childValue = this.computeChildHashValue(this.bean.getGUIJMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJMX2JMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLocalJMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getMX4J1JMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getNoneJMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWLS81JMX());
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
            JMXBeanImpl otherTyped = (JMXBeanImpl)other;
            this.computeChildDiff("GUIJMX", this.bean.getGUIJMX(), otherTyped.getGUIJMX(), false);
            this.computeChildDiff("JMX2JMX", this.bean.getJMX2JMX(), otherTyped.getJMX2JMX(), false);
            this.computeChildDiff("LocalJMX", this.bean.getLocalJMX(), otherTyped.getLocalJMX(), false);
            this.computeChildDiff("MX4J1JMX", this.bean.getMX4J1JMX(), otherTyped.getMX4J1JMX(), false);
            this.computeChildDiff("NoneJMX", this.bean.getNoneJMX(), otherTyped.getNoneJMX(), false);
            this.computeChildDiff("WLS81JMX", this.bean.getWLS81JMX(), otherTyped.getWLS81JMX(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMXBeanImpl original = (JMXBeanImpl)event.getSourceBean();
            JMXBeanImpl proposed = (JMXBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("GUIJMX")) {
                  if (type == 2) {
                     original.setGUIJMX((GUIJMXBean)this.createCopy((AbstractDescriptorBean)proposed.getGUIJMX()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("GUIJMX", (DescriptorBean)original.getGUIJMX());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("JMX2JMX")) {
                  if (type == 2) {
                     original.setJMX2JMX((JMX2JMXBean)this.createCopy((AbstractDescriptorBean)proposed.getJMX2JMX()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JMX2JMX", (DescriptorBean)original.getJMX2JMX());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("LocalJMX")) {
                  if (type == 2) {
                     original.setLocalJMX((LocalJMXBean)this.createCopy((AbstractDescriptorBean)proposed.getLocalJMX()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("LocalJMX", (DescriptorBean)original.getLocalJMX());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MX4J1JMX")) {
                  if (type == 2) {
                     original.setMX4J1JMX((MX4J1JMXBean)this.createCopy((AbstractDescriptorBean)proposed.getMX4J1JMX()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MX4J1JMX", (DescriptorBean)original.getMX4J1JMX());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("NoneJMX")) {
                  if (type == 2) {
                     original.setNoneJMX((NoneJMXBean)this.createCopy((AbstractDescriptorBean)proposed.getNoneJMX()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("NoneJMX", (DescriptorBean)original.getNoneJMX());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("WLS81JMX")) {
                  if (type == 2) {
                     original.setWLS81JMX((WLS81JMXBean)this.createCopy((AbstractDescriptorBean)proposed.getWLS81JMX()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WLS81JMX", (DescriptorBean)original.getWLS81JMX());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            JMXBeanImpl copy = (JMXBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("GUIJMX")) && this.bean.isGUIJMXSet() && !copy._isSet(2)) {
               Object o = this.bean.getGUIJMX();
               copy.setGUIJMX((GUIJMXBean)null);
               copy.setGUIJMX(o == null ? null : (GUIJMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JMX2JMX")) && this.bean.isJMX2JMXSet() && !copy._isSet(3)) {
               Object o = this.bean.getJMX2JMX();
               copy.setJMX2JMX((JMX2JMXBean)null);
               copy.setJMX2JMX(o == null ? null : (JMX2JMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LocalJMX")) && this.bean.isLocalJMXSet() && !copy._isSet(1)) {
               Object o = this.bean.getLocalJMX();
               copy.setLocalJMX((LocalJMXBean)null);
               copy.setLocalJMX(o == null ? null : (LocalJMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MX4J1JMX")) && this.bean.isMX4J1JMXSet() && !copy._isSet(4)) {
               Object o = this.bean.getMX4J1JMX();
               copy.setMX4J1JMX((MX4J1JMXBean)null);
               copy.setMX4J1JMX(o == null ? null : (MX4J1JMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NoneJMX")) && this.bean.isNoneJMXSet() && !copy._isSet(0)) {
               Object o = this.bean.getNoneJMX();
               copy.setNoneJMX((NoneJMXBean)null);
               copy.setNoneJMX(o == null ? null : (NoneJMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WLS81JMX")) && this.bean.isWLS81JMXSet() && !copy._isSet(5)) {
               Object o = this.bean.getWLS81JMX();
               copy.setWLS81JMX((WLS81JMXBean)null);
               copy.setWLS81JMX(o == null ? null : (WLS81JMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getGUIJMX(), clazz, annotation);
         this.inferSubTree(this.bean.getJMX2JMX(), clazz, annotation);
         this.inferSubTree(this.bean.getLocalJMX(), clazz, annotation);
         this.inferSubTree(this.bean.getMX4J1JMX(), clazz, annotation);
         this.inferSubTree(this.bean.getNoneJMX(), clazz, annotation);
         this.inferSubTree(this.bean.getWLS81JMX(), clazz, annotation);
      }
   }
}
