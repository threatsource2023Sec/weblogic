package weblogic.management.configuration;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class XMLRegistryEntryMBeanImpl extends ConfigurationMBeanImpl implements XMLRegistryEntryMBean, Serializable {
   private String _DocumentBuilderFactory;
   private String _EntityPath;
   private String _ParserClassName;
   private String _PublicId;
   private String _RootElementTag;
   private String _SAXParserFactory;
   private String _SystemId;
   private static SchemaHelper2 _schemaHelper;

   public XMLRegistryEntryMBeanImpl() {
      this._initializeProperty(-1);
   }

   public XMLRegistryEntryMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public XMLRegistryEntryMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getPublicId() {
      return this._PublicId;
   }

   public boolean isPublicIdInherited() {
      return false;
   }

   public boolean isPublicIdSet() {
      return this._isSet(10);
   }

   public void setPublicId(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PublicId;
      this._PublicId = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getSystemId() {
      return this._SystemId;
   }

   public boolean isSystemIdInherited() {
      return false;
   }

   public boolean isSystemIdSet() {
      return this._isSet(11);
   }

   public void setSystemId(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SystemId;
      this._SystemId = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getRootElementTag() {
      return this._RootElementTag;
   }

   public boolean isRootElementTagInherited() {
      return false;
   }

   public boolean isRootElementTagSet() {
      return this._isSet(12);
   }

   public void setRootElementTag(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RootElementTag;
      this._RootElementTag = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getEntityPath() {
      return this._EntityPath;
   }

   public boolean isEntityPathInherited() {
      return false;
   }

   public boolean isEntityPathSet() {
      return this._isSet(13);
   }

   public void setEntityPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EntityPath;
      this._EntityPath = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getParserClassName() {
      return this._ParserClassName;
   }

   public boolean isParserClassNameInherited() {
      return false;
   }

   public boolean isParserClassNameSet() {
      return this._isSet(14);
   }

   public void setParserClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ParserClassName;
      this._ParserClassName = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getDocumentBuilderFactory() {
      return this._DocumentBuilderFactory;
   }

   public boolean isDocumentBuilderFactoryInherited() {
      return false;
   }

   public boolean isDocumentBuilderFactorySet() {
      return this._isSet(15);
   }

   public void setDocumentBuilderFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DocumentBuilderFactory;
      this._DocumentBuilderFactory = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getSAXParserFactory() {
      return this._SAXParserFactory;
   }

   public boolean isSAXParserFactoryInherited() {
      return false;
   }

   public boolean isSAXParserFactorySet() {
      return this._isSet(16);
   }

   public void setSAXParserFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SAXParserFactory;
      this._SAXParserFactory = param0;
      this._postSet(16, _oldVal, param0);
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._DocumentBuilderFactory = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._EntityPath = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._ParserClassName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._PublicId = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._RootElementTag = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._SAXParserFactory = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._SystemId = null;
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
      return "XMLRegistryEntry";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("DocumentBuilderFactory")) {
         oldVal = this._DocumentBuilderFactory;
         this._DocumentBuilderFactory = (String)v;
         this._postSet(15, oldVal, this._DocumentBuilderFactory);
      } else if (name.equals("EntityPath")) {
         oldVal = this._EntityPath;
         this._EntityPath = (String)v;
         this._postSet(13, oldVal, this._EntityPath);
      } else if (name.equals("ParserClassName")) {
         oldVal = this._ParserClassName;
         this._ParserClassName = (String)v;
         this._postSet(14, oldVal, this._ParserClassName);
      } else if (name.equals("PublicId")) {
         oldVal = this._PublicId;
         this._PublicId = (String)v;
         this._postSet(10, oldVal, this._PublicId);
      } else if (name.equals("RootElementTag")) {
         oldVal = this._RootElementTag;
         this._RootElementTag = (String)v;
         this._postSet(12, oldVal, this._RootElementTag);
      } else if (name.equals("SAXParserFactory")) {
         oldVal = this._SAXParserFactory;
         this._SAXParserFactory = (String)v;
         this._postSet(16, oldVal, this._SAXParserFactory);
      } else if (name.equals("SystemId")) {
         oldVal = this._SystemId;
         this._SystemId = (String)v;
         this._postSet(11, oldVal, this._SystemId);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DocumentBuilderFactory")) {
         return this._DocumentBuilderFactory;
      } else if (name.equals("EntityPath")) {
         return this._EntityPath;
      } else if (name.equals("ParserClassName")) {
         return this._ParserClassName;
      } else if (name.equals("PublicId")) {
         return this._PublicId;
      } else if (name.equals("RootElementTag")) {
         return this._RootElementTag;
      } else if (name.equals("SAXParserFactory")) {
         return this._SAXParserFactory;
      } else {
         return name.equals("SystemId") ? this._SystemId : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("public-id")) {
                  return 10;
               }

               if (s.equals("system-id")) {
                  return 11;
               }
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 11:
               if (s.equals("entity-path")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("root-element-tag")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("parser-class-name")) {
                  return 14;
               }
               break;
            case 18:
               if (s.equals("sax-parser-factory")) {
                  return 16;
               }
               break;
            case 24:
               if (s.equals("document-builder-factory")) {
                  return 15;
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
            case 10:
               return "public-id";
            case 11:
               return "system-id";
            case 12:
               return "root-element-tag";
            case 13:
               return "entity-path";
            case 14:
               return "parser-class-name";
            case 15:
               return "document-builder-factory";
            case 16:
               return "sax-parser-factory";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
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
      private XMLRegistryEntryMBeanImpl bean;

      protected Helper(XMLRegistryEntryMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "PublicId";
            case 11:
               return "SystemId";
            case 12:
               return "RootElementTag";
            case 13:
               return "EntityPath";
            case 14:
               return "ParserClassName";
            case 15:
               return "DocumentBuilderFactory";
            case 16:
               return "SAXParserFactory";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DocumentBuilderFactory")) {
            return 15;
         } else if (propName.equals("EntityPath")) {
            return 13;
         } else if (propName.equals("ParserClassName")) {
            return 14;
         } else if (propName.equals("PublicId")) {
            return 10;
         } else if (propName.equals("RootElementTag")) {
            return 12;
         } else if (propName.equals("SAXParserFactory")) {
            return 16;
         } else {
            return propName.equals("SystemId") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isDocumentBuilderFactorySet()) {
               buf.append("DocumentBuilderFactory");
               buf.append(String.valueOf(this.bean.getDocumentBuilderFactory()));
            }

            if (this.bean.isEntityPathSet()) {
               buf.append("EntityPath");
               buf.append(String.valueOf(this.bean.getEntityPath()));
            }

            if (this.bean.isParserClassNameSet()) {
               buf.append("ParserClassName");
               buf.append(String.valueOf(this.bean.getParserClassName()));
            }

            if (this.bean.isPublicIdSet()) {
               buf.append("PublicId");
               buf.append(String.valueOf(this.bean.getPublicId()));
            }

            if (this.bean.isRootElementTagSet()) {
               buf.append("RootElementTag");
               buf.append(String.valueOf(this.bean.getRootElementTag()));
            }

            if (this.bean.isSAXParserFactorySet()) {
               buf.append("SAXParserFactory");
               buf.append(String.valueOf(this.bean.getSAXParserFactory()));
            }

            if (this.bean.isSystemIdSet()) {
               buf.append("SystemId");
               buf.append(String.valueOf(this.bean.getSystemId()));
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
            XMLRegistryEntryMBeanImpl otherTyped = (XMLRegistryEntryMBeanImpl)other;
            this.computeDiff("DocumentBuilderFactory", this.bean.getDocumentBuilderFactory(), otherTyped.getDocumentBuilderFactory(), true);
            this.computeDiff("EntityPath", this.bean.getEntityPath(), otherTyped.getEntityPath(), true);
            this.computeDiff("ParserClassName", this.bean.getParserClassName(), otherTyped.getParserClassName(), true);
            this.computeDiff("PublicId", this.bean.getPublicId(), otherTyped.getPublicId(), true);
            this.computeDiff("RootElementTag", this.bean.getRootElementTag(), otherTyped.getRootElementTag(), true);
            this.computeDiff("SAXParserFactory", this.bean.getSAXParserFactory(), otherTyped.getSAXParserFactory(), true);
            this.computeDiff("SystemId", this.bean.getSystemId(), otherTyped.getSystemId(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            XMLRegistryEntryMBeanImpl original = (XMLRegistryEntryMBeanImpl)event.getSourceBean();
            XMLRegistryEntryMBeanImpl proposed = (XMLRegistryEntryMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DocumentBuilderFactory")) {
                  original.setDocumentBuilderFactory(proposed.getDocumentBuilderFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("EntityPath")) {
                  original.setEntityPath(proposed.getEntityPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ParserClassName")) {
                  original.setParserClassName(proposed.getParserClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("PublicId")) {
                  original.setPublicId(proposed.getPublicId());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RootElementTag")) {
                  original.setRootElementTag(proposed.getRootElementTag());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("SAXParserFactory")) {
                  original.setSAXParserFactory(proposed.getSAXParserFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("SystemId")) {
                  original.setSystemId(proposed.getSystemId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            XMLRegistryEntryMBeanImpl copy = (XMLRegistryEntryMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DocumentBuilderFactory")) && this.bean.isDocumentBuilderFactorySet()) {
               copy.setDocumentBuilderFactory(this.bean.getDocumentBuilderFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("EntityPath")) && this.bean.isEntityPathSet()) {
               copy.setEntityPath(this.bean.getEntityPath());
            }

            if ((excludeProps == null || !excludeProps.contains("ParserClassName")) && this.bean.isParserClassNameSet()) {
               copy.setParserClassName(this.bean.getParserClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("PublicId")) && this.bean.isPublicIdSet()) {
               copy.setPublicId(this.bean.getPublicId());
            }

            if ((excludeProps == null || !excludeProps.contains("RootElementTag")) && this.bean.isRootElementTagSet()) {
               copy.setRootElementTag(this.bean.getRootElementTag());
            }

            if ((excludeProps == null || !excludeProps.contains("SAXParserFactory")) && this.bean.isSAXParserFactorySet()) {
               copy.setSAXParserFactory(this.bean.getSAXParserFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemId")) && this.bean.isSystemIdSet()) {
               copy.setSystemId(this.bean.getSystemId());
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
