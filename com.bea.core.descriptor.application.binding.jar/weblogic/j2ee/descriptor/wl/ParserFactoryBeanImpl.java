package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
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
import weblogic.utils.collections.CombinedIterator;

public class ParserFactoryBeanImpl extends AbstractDescriptorBean implements ParserFactoryBean, Serializable {
   private String _DocumentBuilderFactory;
   private String _SaxparserFactory;
   private String _SchemaFactory;
   private String _TransformerFactory;
   private String _XMLEventFactory;
   private String _XMLInputFactory;
   private String _XMLOutputFactory;
   private String _XpathFactory;
   private static SchemaHelper2 _schemaHelper;

   public ParserFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public ParserFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ParserFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSaxparserFactory() {
      return this._SaxparserFactory;
   }

   public boolean isSaxparserFactoryInherited() {
      return false;
   }

   public boolean isSaxparserFactorySet() {
      return this._isSet(0);
   }

   public void setSaxparserFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SaxparserFactory;
      this._SaxparserFactory = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDocumentBuilderFactory() {
      return this._DocumentBuilderFactory;
   }

   public boolean isDocumentBuilderFactoryInherited() {
      return false;
   }

   public boolean isDocumentBuilderFactorySet() {
      return this._isSet(1);
   }

   public void setDocumentBuilderFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DocumentBuilderFactory;
      this._DocumentBuilderFactory = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getTransformerFactory() {
      return this._TransformerFactory;
   }

   public boolean isTransformerFactoryInherited() {
      return false;
   }

   public boolean isTransformerFactorySet() {
      return this._isSet(2);
   }

   public void setTransformerFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TransformerFactory;
      this._TransformerFactory = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getXpathFactory() {
      return this._XpathFactory;
   }

   public boolean isXpathFactoryInherited() {
      return false;
   }

   public boolean isXpathFactorySet() {
      return this._isSet(3);
   }

   public void setXpathFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XpathFactory;
      this._XpathFactory = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getSchemaFactory() {
      return this._SchemaFactory;
   }

   public boolean isSchemaFactoryInherited() {
      return false;
   }

   public boolean isSchemaFactorySet() {
      return this._isSet(4);
   }

   public void setSchemaFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SchemaFactory;
      this._SchemaFactory = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getXMLInputFactory() {
      return this._XMLInputFactory;
   }

   public boolean isXMLInputFactoryInherited() {
      return false;
   }

   public boolean isXMLInputFactorySet() {
      return this._isSet(5);
   }

   public void setXMLInputFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XMLInputFactory;
      this._XMLInputFactory = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getXMLOutputFactory() {
      return this._XMLOutputFactory;
   }

   public boolean isXMLOutputFactoryInherited() {
      return false;
   }

   public boolean isXMLOutputFactorySet() {
      return this._isSet(6);
   }

   public void setXMLOutputFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XMLOutputFactory;
      this._XMLOutputFactory = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getXMLEventFactory() {
      return this._XMLEventFactory;
   }

   public boolean isXMLEventFactoryInherited() {
      return false;
   }

   public boolean isXMLEventFactorySet() {
      return this._isSet(7);
   }

   public void setXMLEventFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XMLEventFactory;
      this._XMLEventFactory = param0;
      this._postSet(7, _oldVal, param0);
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
               this._DocumentBuilderFactory = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._SaxparserFactory = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._SchemaFactory = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._TransformerFactory = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._XMLEventFactory = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._XMLInputFactory = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._XMLOutputFactory = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._XpathFactory = null;
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
            case 13:
               if (s.equals("xpath-factory")) {
                  return 3;
               }
               break;
            case 14:
               if (s.equals("schema-factory")) {
                  return 4;
               }
            case 15:
            case 16:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 17:
               if (s.equals("saxparser-factory")) {
                  return 0;
               }

               if (s.equals("xml-event-factory")) {
                  return 7;
               }

               if (s.equals("xml-input-factory")) {
                  return 5;
               }
               break;
            case 18:
               if (s.equals("xml-output-factory")) {
                  return 6;
               }
               break;
            case 19:
               if (s.equals("transformer-factory")) {
                  return 2;
               }
               break;
            case 24:
               if (s.equals("document-builder-factory")) {
                  return 1;
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
               return "saxparser-factory";
            case 1:
               return "document-builder-factory";
            case 2:
               return "transformer-factory";
            case 3:
               return "xpath-factory";
            case 4:
               return "schema-factory";
            case 5:
               return "xml-input-factory";
            case 6:
               return "xml-output-factory";
            case 7:
               return "xml-event-factory";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ParserFactoryBeanImpl bean;

      protected Helper(ParserFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SaxparserFactory";
            case 1:
               return "DocumentBuilderFactory";
            case 2:
               return "TransformerFactory";
            case 3:
               return "XpathFactory";
            case 4:
               return "SchemaFactory";
            case 5:
               return "XMLInputFactory";
            case 6:
               return "XMLOutputFactory";
            case 7:
               return "XMLEventFactory";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DocumentBuilderFactory")) {
            return 1;
         } else if (propName.equals("SaxparserFactory")) {
            return 0;
         } else if (propName.equals("SchemaFactory")) {
            return 4;
         } else if (propName.equals("TransformerFactory")) {
            return 2;
         } else if (propName.equals("XMLEventFactory")) {
            return 7;
         } else if (propName.equals("XMLInputFactory")) {
            return 5;
         } else if (propName.equals("XMLOutputFactory")) {
            return 6;
         } else {
            return propName.equals("XpathFactory") ? 3 : super.getPropertyIndex(propName);
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

            if (this.bean.isSaxparserFactorySet()) {
               buf.append("SaxparserFactory");
               buf.append(String.valueOf(this.bean.getSaxparserFactory()));
            }

            if (this.bean.isSchemaFactorySet()) {
               buf.append("SchemaFactory");
               buf.append(String.valueOf(this.bean.getSchemaFactory()));
            }

            if (this.bean.isTransformerFactorySet()) {
               buf.append("TransformerFactory");
               buf.append(String.valueOf(this.bean.getTransformerFactory()));
            }

            if (this.bean.isXMLEventFactorySet()) {
               buf.append("XMLEventFactory");
               buf.append(String.valueOf(this.bean.getXMLEventFactory()));
            }

            if (this.bean.isXMLInputFactorySet()) {
               buf.append("XMLInputFactory");
               buf.append(String.valueOf(this.bean.getXMLInputFactory()));
            }

            if (this.bean.isXMLOutputFactorySet()) {
               buf.append("XMLOutputFactory");
               buf.append(String.valueOf(this.bean.getXMLOutputFactory()));
            }

            if (this.bean.isXpathFactorySet()) {
               buf.append("XpathFactory");
               buf.append(String.valueOf(this.bean.getXpathFactory()));
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
            ParserFactoryBeanImpl otherTyped = (ParserFactoryBeanImpl)other;
            this.computeDiff("DocumentBuilderFactory", this.bean.getDocumentBuilderFactory(), otherTyped.getDocumentBuilderFactory(), false);
            this.computeDiff("SaxparserFactory", this.bean.getSaxparserFactory(), otherTyped.getSaxparserFactory(), false);
            this.computeDiff("SchemaFactory", this.bean.getSchemaFactory(), otherTyped.getSchemaFactory(), false);
            this.computeDiff("TransformerFactory", this.bean.getTransformerFactory(), otherTyped.getTransformerFactory(), false);
            this.computeDiff("XMLEventFactory", this.bean.getXMLEventFactory(), otherTyped.getXMLEventFactory(), false);
            this.computeDiff("XMLInputFactory", this.bean.getXMLInputFactory(), otherTyped.getXMLInputFactory(), false);
            this.computeDiff("XMLOutputFactory", this.bean.getXMLOutputFactory(), otherTyped.getXMLOutputFactory(), false);
            this.computeDiff("XpathFactory", this.bean.getXpathFactory(), otherTyped.getXpathFactory(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ParserFactoryBeanImpl original = (ParserFactoryBeanImpl)event.getSourceBean();
            ParserFactoryBeanImpl proposed = (ParserFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DocumentBuilderFactory")) {
                  original.setDocumentBuilderFactory(proposed.getDocumentBuilderFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SaxparserFactory")) {
                  original.setSaxparserFactory(proposed.getSaxparserFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SchemaFactory")) {
                  original.setSchemaFactory(proposed.getSchemaFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TransformerFactory")) {
                  original.setTransformerFactory(proposed.getTransformerFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("XMLEventFactory")) {
                  original.setXMLEventFactory(proposed.getXMLEventFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("XMLInputFactory")) {
                  original.setXMLInputFactory(proposed.getXMLInputFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("XMLOutputFactory")) {
                  original.setXMLOutputFactory(proposed.getXMLOutputFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("XpathFactory")) {
                  original.setXpathFactory(proposed.getXpathFactory());
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
            ParserFactoryBeanImpl copy = (ParserFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DocumentBuilderFactory")) && this.bean.isDocumentBuilderFactorySet()) {
               copy.setDocumentBuilderFactory(this.bean.getDocumentBuilderFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("SaxparserFactory")) && this.bean.isSaxparserFactorySet()) {
               copy.setSaxparserFactory(this.bean.getSaxparserFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("SchemaFactory")) && this.bean.isSchemaFactorySet()) {
               copy.setSchemaFactory(this.bean.getSchemaFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("TransformerFactory")) && this.bean.isTransformerFactorySet()) {
               copy.setTransformerFactory(this.bean.getTransformerFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("XMLEventFactory")) && this.bean.isXMLEventFactorySet()) {
               copy.setXMLEventFactory(this.bean.getXMLEventFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("XMLInputFactory")) && this.bean.isXMLInputFactorySet()) {
               copy.setXMLInputFactory(this.bean.getXMLInputFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("XMLOutputFactory")) && this.bean.isXMLOutputFactorySet()) {
               copy.setXMLOutputFactory(this.bean.getXMLOutputFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("XpathFactory")) && this.bean.isXpathFactorySet()) {
               copy.setXpathFactory(this.bean.getXpathFactory());
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
