package weblogic.j2ee.descriptor;

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

public class ModuleBeanImpl extends AbstractDescriptorBean implements ModuleBean, Serializable {
   private String _AltDd;
   private String _Connector;
   private String _Ejb;
   private String _Id;
   private String _Java;
   private WebBean _Web;
   private static SchemaHelper2 _schemaHelper;

   public ModuleBeanImpl() {
      this._initializeProperty(-1);
   }

   public ModuleBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ModuleBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getConnector() {
      return this._Connector;
   }

   public boolean isConnectorInherited() {
      return false;
   }

   public boolean isConnectorSet() {
      return this._isSet(0);
   }

   public void setConnector(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Connector;
      this._Connector = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getEjb() {
      return this._Ejb;
   }

   public boolean isEjbInherited() {
      return false;
   }

   public boolean isEjbSet() {
      return this._isSet(1);
   }

   public void setEjb(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Ejb;
      this._Ejb = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getJava() {
      return this._Java;
   }

   public boolean isJavaInherited() {
      return false;
   }

   public boolean isJavaSet() {
      return this._isSet(2);
   }

   public void setJava(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Java;
      this._Java = param0;
      this._postSet(2, _oldVal, param0);
   }

   public WebBean getWeb() {
      return this._Web;
   }

   public boolean isWebInherited() {
      return false;
   }

   public boolean isWebSet() {
      return this._isSet(3);
   }

   public void setWeb(WebBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWeb() != null && param0 != this.getWeb()) {
         throw new BeanAlreadyExistsException(this.getWeb() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WebBean _oldVal = this._Web;
         this._Web = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public WebBean createWeb() {
      WebBeanImpl _val = new WebBeanImpl(this, -1);

      try {
         this.setWeb(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWeb(WebBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Web;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWeb((WebBean)null);
               this._unSet(3);
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

   public String getAltDd() {
      return this._AltDd;
   }

   public boolean isAltDdInherited() {
      return false;
   }

   public boolean isAltDdSet() {
      return this._isSet(4);
   }

   public void setAltDd(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AltDd;
      this._AltDd = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      Object keyChoice = null;
      if (keyChoice == null) {
         keyChoice = this.getAltDd();
      }

      if (keyChoice == null) {
         keyChoice = this.getConnector();
      }

      if (keyChoice == null) {
         keyChoice = this.getEjb();
      }

      if (keyChoice == null) {
         keyChoice = this.getJava();
      }

      if (keyChoice == null) {
         keyChoice = this._getBeanKey((AbstractDescriptorBean)this.getWeb());
      }

      return keyChoice;
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
         case 3:
            if (s.equals("ejb")) {
               return info.compareXpaths(this._getPropertyXpath("ejb"));
            }
            break;
         case 4:
            if (s.equals("java")) {
               return info.compareXpaths(this._getPropertyXpath("java"));
            }
            break;
         case 5:
         case 7:
         case 8:
         default:
            return super._isPropertyAKey(info);
         case 6:
            if (s.equals("alt-dd")) {
               return info.compareXpaths(this._getPropertyXpath("alt-dd"));
            }
            break;
         case 9:
            if (s.equals("connector")) {
               return info.compareXpaths(this._getPropertyXpath("connector"));
            }
      }

      return super._isPropertyAKey(info);
   }

   public boolean _isChildPropertyAKey(String s) {
      switch (s.length()) {
         case 3:
            if (s.equals("web")) {
               return true;
            }

            return false;
         default:
            return false;
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._AltDd = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Connector = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Ejb = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Java = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Web = null;
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
                  return 5;
               }
               break;
            case 3:
               if (s.equals("ejb")) {
                  return 1;
               }

               if (s.equals("web")) {
                  return 3;
               }
               break;
            case 4:
               if (s.equals("java")) {
                  return 2;
               }
            case 5:
            case 7:
            case 8:
            default:
               break;
            case 6:
               if (s.equals("alt-dd")) {
                  return 4;
               }
               break;
            case 9:
               if (s.equals("connector")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new WebBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "connector";
            case 1:
               return "ejb";
            case 2:
               return "java";
            case 3:
               return "web";
            case 4:
               return "alt-dd";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKeyChoice(int propIndex) {
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
            default:
               return super.isKeyChoice(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ModuleBeanImpl bean;

      protected Helper(ModuleBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Connector";
            case 1:
               return "Ejb";
            case 2:
               return "Java";
            case 3:
               return "Web";
            case 4:
               return "AltDd";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AltDd")) {
            return 4;
         } else if (propName.equals("Connector")) {
            return 0;
         } else if (propName.equals("Ejb")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("Java")) {
            return 2;
         } else {
            return propName.equals("Web") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWeb() != null) {
            iterators.add(new ArrayIterator(new WebBean[]{this.bean.getWeb()}));
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
            if (this.bean.isAltDdSet()) {
               buf.append("AltDd");
               buf.append(String.valueOf(this.bean.getAltDd()));
            }

            if (this.bean.isConnectorSet()) {
               buf.append("Connector");
               buf.append(String.valueOf(this.bean.getConnector()));
            }

            if (this.bean.isEjbSet()) {
               buf.append("Ejb");
               buf.append(String.valueOf(this.bean.getEjb()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isJavaSet()) {
               buf.append("Java");
               buf.append(String.valueOf(this.bean.getJava()));
            }

            childValue = this.computeChildHashValue(this.bean.getWeb());
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
            ModuleBeanImpl otherTyped = (ModuleBeanImpl)other;
            this.computeDiff("AltDd", this.bean.getAltDd(), otherTyped.getAltDd(), false);
            this.computeDiff("Connector", this.bean.getConnector(), otherTyped.getConnector(), false);
            this.computeDiff("Ejb", this.bean.getEjb(), otherTyped.getEjb(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Java", this.bean.getJava(), otherTyped.getJava(), false);
            this.computeChildDiff("Web", this.bean.getWeb(), otherTyped.getWeb(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ModuleBeanImpl original = (ModuleBeanImpl)event.getSourceBean();
            ModuleBeanImpl proposed = (ModuleBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AltDd")) {
                  original.setAltDd(proposed.getAltDd());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Connector")) {
                  original.setConnector(proposed.getConnector());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Ejb")) {
                  original.setEjb(proposed.getEjb());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Java")) {
                  original.setJava(proposed.getJava());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Web")) {
                  if (type == 2) {
                     original.setWeb((WebBean)this.createCopy((AbstractDescriptorBean)proposed.getWeb()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Web", (DescriptorBean)original.getWeb());
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
            ModuleBeanImpl copy = (ModuleBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AltDd")) && this.bean.isAltDdSet()) {
               copy.setAltDd(this.bean.getAltDd());
            }

            if ((excludeProps == null || !excludeProps.contains("Connector")) && this.bean.isConnectorSet()) {
               copy.setConnector(this.bean.getConnector());
            }

            if ((excludeProps == null || !excludeProps.contains("Ejb")) && this.bean.isEjbSet()) {
               copy.setEjb(this.bean.getEjb());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Java")) && this.bean.isJavaSet()) {
               copy.setJava(this.bean.getJava());
            }

            if ((excludeProps == null || !excludeProps.contains("Web")) && this.bean.isWebSet() && !copy._isSet(3)) {
               Object o = this.bean.getWeb();
               copy.setWeb((WebBean)null);
               copy.setWeb(o == null ? null : (WebBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getWeb(), clazz, annotation);
      }
   }
}
