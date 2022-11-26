package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.XMLRegistry;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class XMLRegistryMBeanImpl extends ConfigurationMBeanImpl implements XMLRegistryMBean, Serializable {
   private String _DocumentBuilderFactory;
   private boolean _DynamicallyCreated;
   private XMLEntitySpecRegistryEntryMBean[] _EntitySpecRegistryEntries;
   private boolean _HandleEntityInvalidation;
   private String _Name;
   private XMLParserSelectRegistryEntryMBean[] _ParserSelectRegistryEntries;
   private XMLRegistryEntryMBean[] _RegistryEntries;
   private String _SAXParserFactory;
   private String _SchemaFactory;
   private String[] _Tags;
   private String _TransformerFactory;
   private String _WhenToCache;
   private XMLEntitySpecRegistryEntryMBean[] _XMLEntitySpecRegistryEntries;
   private String _XMLEventFactory;
   private String _XMLInputFactory;
   private String _XMLOutputFactory;
   private XMLParserSelectRegistryEntryMBean[] _XMLParserSelectRegistryEntries;
   private String _XpathFactory;
   private transient XMLRegistry _customizer;
   private static SchemaHelper2 _schemaHelper;

   public XMLRegistryMBeanImpl() {
      try {
         this._customizer = new XMLRegistry(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public XMLRegistryMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new XMLRegistry(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public XMLRegistryMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new XMLRegistry(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getDocumentBuilderFactory() {
      return this._DocumentBuilderFactory;
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public boolean isDocumentBuilderFactoryInherited() {
      return false;
   }

   public boolean isDocumentBuilderFactorySet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setDocumentBuilderFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DocumentBuilderFactory;
      this._DocumentBuilderFactory = param0;
      this._postSet(10, _oldVal, param0);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public String getSAXParserFactory() {
      return this._SAXParserFactory;
   }

   public boolean isSAXParserFactoryInherited() {
      return false;
   }

   public boolean isSAXParserFactorySet() {
      return this._isSet(11);
   }

   public void setSAXParserFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SAXParserFactory;
      this._SAXParserFactory = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getTransformerFactory() {
      return this._TransformerFactory;
   }

   public boolean isTransformerFactoryInherited() {
      return false;
   }

   public boolean isTransformerFactorySet() {
      return this._isSet(12);
   }

   public void setTransformerFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TransformerFactory;
      this._TransformerFactory = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getXpathFactory() {
      return this._XpathFactory;
   }

   public boolean isXpathFactoryInherited() {
      return false;
   }

   public boolean isXpathFactorySet() {
      return this._isSet(13);
   }

   public void setXpathFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XpathFactory;
      this._XpathFactory = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getSchemaFactory() {
      return this._SchemaFactory;
   }

   public boolean isSchemaFactoryInherited() {
      return false;
   }

   public boolean isSchemaFactorySet() {
      return this._isSet(14);
   }

   public void setSchemaFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SchemaFactory;
      this._SchemaFactory = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getXMLInputFactory() {
      return this._XMLInputFactory;
   }

   public boolean isXMLInputFactoryInherited() {
      return false;
   }

   public boolean isXMLInputFactorySet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setXMLInputFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XMLInputFactory;
      this._XMLInputFactory = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getXMLOutputFactory() {
      return this._XMLOutputFactory;
   }

   public boolean isXMLOutputFactoryInherited() {
      return false;
   }

   public boolean isXMLOutputFactorySet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setXMLOutputFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XMLOutputFactory;
      this._XMLOutputFactory = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getXMLEventFactory() {
      return this._XMLEventFactory;
   }

   public boolean isXMLEventFactoryInherited() {
      return false;
   }

   public boolean isXMLEventFactorySet() {
      return this._isSet(17);
   }

   public void setXMLEventFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XMLEventFactory;
      this._XMLEventFactory = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean addRegistryEntry(XMLRegistryEntryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         XMLRegistryEntryMBean[] _new = (XMLRegistryEntryMBean[])((XMLRegistryEntryMBean[])this._getHelper()._extendArray(this.getRegistryEntries(), XMLRegistryEntryMBean.class, param0));

         try {
            this.setRegistryEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public boolean removeRegistryEntry(XMLRegistryEntryMBean param0) {
      XMLRegistryEntryMBean[] _old = this.getRegistryEntries();
      XMLRegistryEntryMBean[] _new = (XMLRegistryEntryMBean[])((XMLRegistryEntryMBean[])this._getHelper()._removeElement(_old, XMLRegistryEntryMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setRegistryEntries(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public XMLRegistryEntryMBean[] getRegistryEntries() {
      return this._RegistryEntries;
   }

   public boolean isRegistryEntriesInherited() {
      return false;
   }

   public boolean isRegistryEntriesSet() {
      return this._isSet(18);
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setRegistryEntries(XMLRegistryEntryMBean[] param0) throws InvalidAttributeValueException {
      XMLRegistryEntryMBean[] param0 = param0 == null ? new XMLRegistryEntryMBeanImpl[0] : param0;
      this._RegistryEntries = (XMLRegistryEntryMBean[])param0;
   }

   public XMLParserSelectRegistryEntryMBean createXMLParserSelectRegistryEntry(String param0) {
      XMLParserSelectRegistryEntryMBeanImpl _val = new XMLParserSelectRegistryEntryMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addXMLParserSelectRegistryEntry(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyXMLParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 19);
         XMLParserSelectRegistryEntryMBean[] _old = this.getXMLParserSelectRegistryEntries();
         XMLParserSelectRegistryEntryMBean[] _new = (XMLParserSelectRegistryEntryMBean[])((XMLParserSelectRegistryEntryMBean[])this._getHelper()._removeElement(_old, XMLParserSelectRegistryEntryMBean.class, param0));
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
               this.setXMLParserSelectRegistryEntries(_new);
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

   public boolean addParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         XMLParserSelectRegistryEntryMBean[] _new = (XMLParserSelectRegistryEntryMBean[])((XMLParserSelectRegistryEntryMBean[])this._getHelper()._extendArray(this.getParserSelectRegistryEntries(), XMLParserSelectRegistryEntryMBean.class, param0));

         try {
            this.setParserSelectRegistryEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public boolean removeParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean param0) {
      XMLParserSelectRegistryEntryMBean[] _old = this.getParserSelectRegistryEntries();
      XMLParserSelectRegistryEntryMBean[] _new = (XMLParserSelectRegistryEntryMBean[])((XMLParserSelectRegistryEntryMBean[])this._getHelper()._removeElement(_old, XMLParserSelectRegistryEntryMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setParserSelectRegistryEntries(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void addXMLParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         XMLParserSelectRegistryEntryMBean[] _new;
         if (this._isSet(19)) {
            _new = (XMLParserSelectRegistryEntryMBean[])((XMLParserSelectRegistryEntryMBean[])this._getHelper()._extendArray(this.getXMLParserSelectRegistryEntries(), XMLParserSelectRegistryEntryMBean.class, param0));
         } else {
            _new = new XMLParserSelectRegistryEntryMBean[]{param0};
         }

         try {
            this.setXMLParserSelectRegistryEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public XMLParserSelectRegistryEntryMBean[] getXMLParserSelectRegistryEntries() {
      return this._XMLParserSelectRegistryEntries;
   }

   public boolean isXMLParserSelectRegistryEntriesInherited() {
      return false;
   }

   public boolean isXMLParserSelectRegistryEntriesSet() {
      return this._isSet(19);
   }

   public void removeXMLParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean param0) {
      this.destroyXMLParserSelectRegistryEntry(param0);
   }

   public void setXMLParserSelectRegistryEntries(XMLParserSelectRegistryEntryMBean[] param0) throws InvalidAttributeValueException {
      XMLParserSelectRegistryEntryMBean[] param0 = param0 == null ? new XMLParserSelectRegistryEntryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      XMLParserSelectRegistryEntryMBean[] _oldVal = this._XMLParserSelectRegistryEntries;
      this._XMLParserSelectRegistryEntries = (XMLParserSelectRegistryEntryMBean[])param0;
      this._postSet(19, _oldVal, param0);
   }

   public XMLParserSelectRegistryEntryMBean[] getParserSelectRegistryEntries() {
      return this._customizer.getParserSelectRegistryEntries();
   }

   public boolean isParserSelectRegistryEntriesInherited() {
      return false;
   }

   public boolean isParserSelectRegistryEntriesSet() {
      return this._isSet(20);
   }

   public void setParserSelectRegistryEntries(XMLParserSelectRegistryEntryMBean[] param0) throws InvalidAttributeValueException {
      XMLParserSelectRegistryEntryMBean[] param0 = param0 == null ? new XMLParserSelectRegistryEntryMBeanImpl[0] : param0;
      this._ParserSelectRegistryEntries = (XMLParserSelectRegistryEntryMBean[])param0;
   }

   public boolean addEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         XMLEntitySpecRegistryEntryMBean[] _new = (XMLEntitySpecRegistryEntryMBean[])((XMLEntitySpecRegistryEntryMBean[])this._getHelper()._extendArray(this.getEntitySpecRegistryEntries(), XMLEntitySpecRegistryEntryMBean.class, param0));

         try {
            this.setEntitySpecRegistryEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean param0) {
      XMLEntitySpecRegistryEntryMBean[] _old = this.getEntitySpecRegistryEntries();
      XMLEntitySpecRegistryEntryMBean[] _new = (XMLEntitySpecRegistryEntryMBean[])((XMLEntitySpecRegistryEntryMBean[])this._getHelper()._removeElement(_old, XMLEntitySpecRegistryEntryMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setEntitySpecRegistryEntries(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public XMLEntitySpecRegistryEntryMBean[] getEntitySpecRegistryEntries() {
      return this._customizer.getEntitySpecRegistryEntries();
   }

   public boolean isEntitySpecRegistryEntriesInherited() {
      return false;
   }

   public boolean isEntitySpecRegistryEntriesSet() {
      return this._isSet(21);
   }

   public void addXMLEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         XMLEntitySpecRegistryEntryMBean[] _new;
         if (this._isSet(22)) {
            _new = (XMLEntitySpecRegistryEntryMBean[])((XMLEntitySpecRegistryEntryMBean[])this._getHelper()._extendArray(this.getXMLEntitySpecRegistryEntries(), XMLEntitySpecRegistryEntryMBean.class, param0));
         } else {
            _new = new XMLEntitySpecRegistryEntryMBean[]{param0};
         }

         try {
            this.setXMLEntitySpecRegistryEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public XMLEntitySpecRegistryEntryMBean[] getXMLEntitySpecRegistryEntries() {
      return this._XMLEntitySpecRegistryEntries;
   }

   public boolean isXMLEntitySpecRegistryEntriesInherited() {
      return false;
   }

   public boolean isXMLEntitySpecRegistryEntriesSet() {
      return this._isSet(22);
   }

   public void removeXMLEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean param0) {
      this.destroyXMLEntitySpecRegistryEntry(param0);
   }

   public void setXMLEntitySpecRegistryEntries(XMLEntitySpecRegistryEntryMBean[] param0) throws InvalidAttributeValueException {
      XMLEntitySpecRegistryEntryMBean[] param0 = param0 == null ? new XMLEntitySpecRegistryEntryMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      XMLEntitySpecRegistryEntryMBean[] _oldVal = this._XMLEntitySpecRegistryEntries;
      this._XMLEntitySpecRegistryEntries = (XMLEntitySpecRegistryEntryMBean[])param0;
      this._postSet(22, _oldVal, param0);
   }

   public XMLEntitySpecRegistryEntryMBean createXMLEntitySpecRegistryEntry(String param0) {
      XMLEntitySpecRegistryEntryMBeanImpl _val = new XMLEntitySpecRegistryEntryMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addXMLEntitySpecRegistryEntry(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyXMLEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 22);
         XMLEntitySpecRegistryEntryMBean[] _old = this.getXMLEntitySpecRegistryEntries();
         XMLEntitySpecRegistryEntryMBean[] _new = (XMLEntitySpecRegistryEntryMBean[])((XMLEntitySpecRegistryEntryMBean[])this._getHelper()._removeElement(_old, XMLEntitySpecRegistryEntryMBean.class, param0));
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
               this.setXMLEntitySpecRegistryEntries(_new);
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

   public void setEntitySpecRegistryEntries(XMLEntitySpecRegistryEntryMBean[] param0) throws InvalidAttributeValueException {
      XMLEntitySpecRegistryEntryMBean[] param0 = param0 == null ? new XMLEntitySpecRegistryEntryMBeanImpl[0] : param0;
      this._EntitySpecRegistryEntries = (XMLEntitySpecRegistryEntryMBean[])param0;
   }

   public String getWhenToCache() {
      return this._WhenToCache;
   }

   public boolean isWhenToCacheInherited() {
      return false;
   }

   public boolean isWhenToCacheSet() {
      return this._isSet(23);
   }

   public void setWhenToCache(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"cache-on-reference", "cache-at-initialization", "cache-never"};
      param0 = LegalChecks.checkInEnum("WhenToCache", param0, _set);
      String _oldVal = this._WhenToCache;
      this._WhenToCache = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isHandleEntityInvalidation() {
      return this._HandleEntityInvalidation;
   }

   public boolean isHandleEntityInvalidationInherited() {
      return false;
   }

   public boolean isHandleEntityInvalidationSet() {
      return this._isSet(24);
   }

   public void setHandleEntityInvalidation(boolean param0) {
      boolean _oldVal = this._HandleEntityInvalidation;
      this._HandleEntityInvalidation = param0;
      this._postSet(24, _oldVal, param0);
   }

   public XMLParserSelectRegistryEntryMBean findParserSelectMBeanByKey(String param0, String param1, String param2) {
      return this._customizer.findParserSelectMBeanByKey(param0, param1, param2);
   }

   public XMLEntitySpecRegistryEntryMBean findEntitySpecMBeanByKey(String param0, String param1) {
      return this._customizer.findEntitySpecMBeanByKey(param0, param1);
   }

   public Object _getKey() {
      return this.getName();
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
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._DocumentBuilderFactory = "weblogic.xml.jaxp.WebLogicDocumentBuilderFactory";
               if (initOne) {
                  break;
               }
            case 21:
               this._EntitySpecRegistryEntries = new XMLEntitySpecRegistryEntryMBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 20:
               this._ParserSelectRegistryEntries = new XMLParserSelectRegistryEntryMBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._RegistryEntries = new XMLRegistryEntryMBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._SAXParserFactory = "weblogic.xml.jaxp.WebLogicSAXParserFactory";
               if (initOne) {
                  break;
               }
            case 14:
               this._SchemaFactory = "weblogic.xml.jaxp.WebLogicSchemaFactory";
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 12:
               this._TransformerFactory = "weblogic.xml.jaxp.WebLogicTransformerFactory";
               if (initOne) {
                  break;
               }
            case 23:
               this._WhenToCache = "cache-on-reference";
               if (initOne) {
                  break;
               }
            case 22:
               this._XMLEntitySpecRegistryEntries = new XMLEntitySpecRegistryEntryMBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._XMLEventFactory = "weblogic.xml.jaxp.WebLogicXMLEventFactory";
               if (initOne) {
                  break;
               }
            case 15:
               this._XMLInputFactory = "weblogic.xml.jaxp.WebLogicXMLInputFactory";
               if (initOne) {
                  break;
               }
            case 16:
               this._XMLOutputFactory = "weblogic.xml.jaxp.WebLogicXMLOutputFactory";
               if (initOne) {
                  break;
               }
            case 19:
               this._XMLParserSelectRegistryEntries = new XMLParserSelectRegistryEntryMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._XpathFactory = "weblogic.xml.jaxp.WebLogicXPathFactory";
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._HandleEntityInvalidation = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "XMLRegistry";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("DocumentBuilderFactory")) {
         oldVal = this._DocumentBuilderFactory;
         this._DocumentBuilderFactory = (String)v;
         this._postSet(10, oldVal, this._DocumentBuilderFactory);
      } else {
         boolean oldVal;
         if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else {
            XMLEntitySpecRegistryEntryMBean[] oldVal;
            if (name.equals("EntitySpecRegistryEntries")) {
               oldVal = this._EntitySpecRegistryEntries;
               this._EntitySpecRegistryEntries = (XMLEntitySpecRegistryEntryMBean[])((XMLEntitySpecRegistryEntryMBean[])v);
               this._postSet(21, oldVal, this._EntitySpecRegistryEntries);
            } else if (name.equals("HandleEntityInvalidation")) {
               oldVal = this._HandleEntityInvalidation;
               this._HandleEntityInvalidation = (Boolean)v;
               this._postSet(24, oldVal, this._HandleEntityInvalidation);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else {
               XMLParserSelectRegistryEntryMBean[] oldVal;
               if (name.equals("ParserSelectRegistryEntries")) {
                  oldVal = this._ParserSelectRegistryEntries;
                  this._ParserSelectRegistryEntries = (XMLParserSelectRegistryEntryMBean[])((XMLParserSelectRegistryEntryMBean[])v);
                  this._postSet(20, oldVal, this._ParserSelectRegistryEntries);
               } else if (name.equals("RegistryEntries")) {
                  XMLRegistryEntryMBean[] oldVal = this._RegistryEntries;
                  this._RegistryEntries = (XMLRegistryEntryMBean[])((XMLRegistryEntryMBean[])v);
                  this._postSet(18, oldVal, this._RegistryEntries);
               } else if (name.equals("SAXParserFactory")) {
                  oldVal = this._SAXParserFactory;
                  this._SAXParserFactory = (String)v;
                  this._postSet(11, oldVal, this._SAXParserFactory);
               } else if (name.equals("SchemaFactory")) {
                  oldVal = this._SchemaFactory;
                  this._SchemaFactory = (String)v;
                  this._postSet(14, oldVal, this._SchemaFactory);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("TransformerFactory")) {
                  oldVal = this._TransformerFactory;
                  this._TransformerFactory = (String)v;
                  this._postSet(12, oldVal, this._TransformerFactory);
               } else if (name.equals("WhenToCache")) {
                  oldVal = this._WhenToCache;
                  this._WhenToCache = (String)v;
                  this._postSet(23, oldVal, this._WhenToCache);
               } else if (name.equals("XMLEntitySpecRegistryEntries")) {
                  oldVal = this._XMLEntitySpecRegistryEntries;
                  this._XMLEntitySpecRegistryEntries = (XMLEntitySpecRegistryEntryMBean[])((XMLEntitySpecRegistryEntryMBean[])v);
                  this._postSet(22, oldVal, this._XMLEntitySpecRegistryEntries);
               } else if (name.equals("XMLEventFactory")) {
                  oldVal = this._XMLEventFactory;
                  this._XMLEventFactory = (String)v;
                  this._postSet(17, oldVal, this._XMLEventFactory);
               } else if (name.equals("XMLInputFactory")) {
                  oldVal = this._XMLInputFactory;
                  this._XMLInputFactory = (String)v;
                  this._postSet(15, oldVal, this._XMLInputFactory);
               } else if (name.equals("XMLOutputFactory")) {
                  oldVal = this._XMLOutputFactory;
                  this._XMLOutputFactory = (String)v;
                  this._postSet(16, oldVal, this._XMLOutputFactory);
               } else if (name.equals("XMLParserSelectRegistryEntries")) {
                  oldVal = this._XMLParserSelectRegistryEntries;
                  this._XMLParserSelectRegistryEntries = (XMLParserSelectRegistryEntryMBean[])((XMLParserSelectRegistryEntryMBean[])v);
                  this._postSet(19, oldVal, this._XMLParserSelectRegistryEntries);
               } else if (name.equals("XpathFactory")) {
                  oldVal = this._XpathFactory;
                  this._XpathFactory = (String)v;
                  this._postSet(13, oldVal, this._XpathFactory);
               } else if (name.equals("customizer")) {
                  XMLRegistry oldVal = this._customizer;
                  this._customizer = (XMLRegistry)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DocumentBuilderFactory")) {
         return this._DocumentBuilderFactory;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EntitySpecRegistryEntries")) {
         return this._EntitySpecRegistryEntries;
      } else if (name.equals("HandleEntityInvalidation")) {
         return new Boolean(this._HandleEntityInvalidation);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("ParserSelectRegistryEntries")) {
         return this._ParserSelectRegistryEntries;
      } else if (name.equals("RegistryEntries")) {
         return this._RegistryEntries;
      } else if (name.equals("SAXParserFactory")) {
         return this._SAXParserFactory;
      } else if (name.equals("SchemaFactory")) {
         return this._SchemaFactory;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TransformerFactory")) {
         return this._TransformerFactory;
      } else if (name.equals("WhenToCache")) {
         return this._WhenToCache;
      } else if (name.equals("XMLEntitySpecRegistryEntries")) {
         return this._XMLEntitySpecRegistryEntries;
      } else if (name.equals("XMLEventFactory")) {
         return this._XMLEventFactory;
      } else if (name.equals("XMLInputFactory")) {
         return this._XMLInputFactory;
      } else if (name.equals("XMLOutputFactory")) {
         return this._XMLOutputFactory;
      } else if (name.equals("XMLParserSelectRegistryEntries")) {
         return this._XMLParserSelectRegistryEntries;
      } else if (name.equals("XpathFactory")) {
         return this._XpathFactory;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            default:
               break;
            case 13:
               if (s.equals("when-to-cache")) {
                  return 23;
               }

               if (s.equals("xpath-factory")) {
                  return 13;
               }
               break;
            case 14:
               if (s.equals("registry-entry")) {
                  return 18;
               }

               if (s.equals("schema-factory")) {
                  return 14;
               }
               break;
            case 17:
               if (s.equals("xml-event-factory")) {
                  return 17;
               }

               if (s.equals("xml-input-factory")) {
                  return 15;
               }
               break;
            case 18:
               if (s.equals("sax-parser-factory")) {
                  return 11;
               }

               if (s.equals("xml-output-factory")) {
                  return 16;
               }
               break;
            case 19:
               if (s.equals("transformer-factory")) {
                  return 12;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 24:
               if (s.equals("document-builder-factory")) {
                  return 10;
               }
               break;
            case 26:
               if (s.equals("entity-spec-registry-entry")) {
                  return 21;
               }

               if (s.equals("handle-entity-invalidation")) {
                  return 24;
               }
               break;
            case 28:
               if (s.equals("parser-select-registry-entry")) {
                  return 20;
               }
               break;
            case 30:
               if (s.equals("xml-entity-spec-registry-entry")) {
                  return 22;
               }
               break;
            case 32:
               if (s.equals("xml-parser-select-registry-entry")) {
                  return 19;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 19:
               return new XMLParserSelectRegistryEntryMBeanImpl.SchemaHelper2();
            case 22:
               return new XMLEntitySpecRegistryEntryMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "document-builder-factory";
            case 11:
               return "sax-parser-factory";
            case 12:
               return "transformer-factory";
            case 13:
               return "xpath-factory";
            case 14:
               return "schema-factory";
            case 15:
               return "xml-input-factory";
            case 16:
               return "xml-output-factory";
            case 17:
               return "xml-event-factory";
            case 18:
               return "registry-entry";
            case 19:
               return "xml-parser-select-registry-entry";
            case 20:
               return "parser-select-registry-entry";
            case 21:
               return "entity-spec-registry-entry";
            case 22:
               return "xml-entity-spec-registry-entry";
            case 23:
               return "when-to-cache";
            case 24:
               return "handle-entity-invalidation";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
               return super.isArray(propIndex);
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 19:
               return true;
            case 22:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private XMLRegistryMBeanImpl bean;

      protected Helper(XMLRegistryMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "DocumentBuilderFactory";
            case 11:
               return "SAXParserFactory";
            case 12:
               return "TransformerFactory";
            case 13:
               return "XpathFactory";
            case 14:
               return "SchemaFactory";
            case 15:
               return "XMLInputFactory";
            case 16:
               return "XMLOutputFactory";
            case 17:
               return "XMLEventFactory";
            case 18:
               return "RegistryEntries";
            case 19:
               return "XMLParserSelectRegistryEntries";
            case 20:
               return "ParserSelectRegistryEntries";
            case 21:
               return "EntitySpecRegistryEntries";
            case 22:
               return "XMLEntitySpecRegistryEntries";
            case 23:
               return "WhenToCache";
            case 24:
               return "HandleEntityInvalidation";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DocumentBuilderFactory")) {
            return 10;
         } else if (propName.equals("EntitySpecRegistryEntries")) {
            return 21;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("ParserSelectRegistryEntries")) {
            return 20;
         } else if (propName.equals("RegistryEntries")) {
            return 18;
         } else if (propName.equals("SAXParserFactory")) {
            return 11;
         } else if (propName.equals("SchemaFactory")) {
            return 14;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("TransformerFactory")) {
            return 12;
         } else if (propName.equals("WhenToCache")) {
            return 23;
         } else if (propName.equals("XMLEntitySpecRegistryEntries")) {
            return 22;
         } else if (propName.equals("XMLEventFactory")) {
            return 17;
         } else if (propName.equals("XMLInputFactory")) {
            return 15;
         } else if (propName.equals("XMLOutputFactory")) {
            return 16;
         } else if (propName.equals("XMLParserSelectRegistryEntries")) {
            return 19;
         } else if (propName.equals("XpathFactory")) {
            return 13;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("HandleEntityInvalidation") ? 24 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getXMLEntitySpecRegistryEntries()));
         iterators.add(new ArrayIterator(this.bean.getXMLParserSelectRegistryEntries()));
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

            if (this.bean.isEntitySpecRegistryEntriesSet()) {
               buf.append("EntitySpecRegistryEntries");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEntitySpecRegistryEntries())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isParserSelectRegistryEntriesSet()) {
               buf.append("ParserSelectRegistryEntries");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getParserSelectRegistryEntries())));
            }

            if (this.bean.isRegistryEntriesSet()) {
               buf.append("RegistryEntries");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getRegistryEntries())));
            }

            if (this.bean.isSAXParserFactorySet()) {
               buf.append("SAXParserFactory");
               buf.append(String.valueOf(this.bean.getSAXParserFactory()));
            }

            if (this.bean.isSchemaFactorySet()) {
               buf.append("SchemaFactory");
               buf.append(String.valueOf(this.bean.getSchemaFactory()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTransformerFactorySet()) {
               buf.append("TransformerFactory");
               buf.append(String.valueOf(this.bean.getTransformerFactory()));
            }

            if (this.bean.isWhenToCacheSet()) {
               buf.append("WhenToCache");
               buf.append(String.valueOf(this.bean.getWhenToCache()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getXMLEntitySpecRegistryEntries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getXMLEntitySpecRegistryEntries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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

            childValue = 0L;

            for(i = 0; i < this.bean.getXMLParserSelectRegistryEntries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getXMLParserSelectRegistryEntries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isXpathFactorySet()) {
               buf.append("XpathFactory");
               buf.append(String.valueOf(this.bean.getXpathFactory()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isHandleEntityInvalidationSet()) {
               buf.append("HandleEntityInvalidation");
               buf.append(String.valueOf(this.bean.isHandleEntityInvalidation()));
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
            XMLRegistryMBeanImpl otherTyped = (XMLRegistryMBeanImpl)other;
            this.computeDiff("DocumentBuilderFactory", this.bean.getDocumentBuilderFactory(), otherTyped.getDocumentBuilderFactory(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("SAXParserFactory", this.bean.getSAXParserFactory(), otherTyped.getSAXParserFactory(), true);
            this.computeDiff("SchemaFactory", this.bean.getSchemaFactory(), otherTyped.getSchemaFactory(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("TransformerFactory", this.bean.getTransformerFactory(), otherTyped.getTransformerFactory(), true);
            this.computeDiff("WhenToCache", this.bean.getWhenToCache(), otherTyped.getWhenToCache(), true);
            this.computeChildDiff("XMLEntitySpecRegistryEntries", this.bean.getXMLEntitySpecRegistryEntries(), otherTyped.getXMLEntitySpecRegistryEntries(), false);
            this.computeDiff("XMLEventFactory", this.bean.getXMLEventFactory(), otherTyped.getXMLEventFactory(), true);
            this.computeDiff("XMLInputFactory", this.bean.getXMLInputFactory(), otherTyped.getXMLInputFactory(), true);
            this.computeDiff("XMLOutputFactory", this.bean.getXMLOutputFactory(), otherTyped.getXMLOutputFactory(), true);
            this.computeChildDiff("XMLParserSelectRegistryEntries", this.bean.getXMLParserSelectRegistryEntries(), otherTyped.getXMLParserSelectRegistryEntries(), false);
            this.computeDiff("XpathFactory", this.bean.getXpathFactory(), otherTyped.getXpathFactory(), true);
            this.computeDiff("HandleEntityInvalidation", this.bean.isHandleEntityInvalidation(), otherTyped.isHandleEntityInvalidation(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            XMLRegistryMBeanImpl original = (XMLRegistryMBeanImpl)event.getSourceBean();
            XMLRegistryMBeanImpl proposed = (XMLRegistryMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DocumentBuilderFactory")) {
                  original.setDocumentBuilderFactory(proposed.getDocumentBuilderFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (!prop.equals("EntitySpecRegistryEntries")) {
                  if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (!prop.equals("ParserSelectRegistryEntries") && !prop.equals("RegistryEntries")) {
                     if (prop.equals("SAXParserFactory")) {
                        original.setSAXParserFactory(proposed.getSAXParserFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     } else if (prop.equals("SchemaFactory")) {
                        original.setSchemaFactory(proposed.getSchemaFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     } else if (prop.equals("Tags")) {
                        if (type == 2) {
                           update.resetAddedObject(update.getAddedObject());
                           original.addTag((String)update.getAddedObject());
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeTag((String)update.getRemovedObject());
                        }

                        if (original.getTags() == null || original.getTags().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 9);
                        }
                     } else if (prop.equals("TransformerFactory")) {
                        original.setTransformerFactory(proposed.getTransformerFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 12);
                     } else if (prop.equals("WhenToCache")) {
                        original.setWhenToCache(proposed.getWhenToCache());
                        original._conditionalUnset(update.isUnsetUpdate(), 23);
                     } else if (prop.equals("XMLEntitySpecRegistryEntries")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addXMLEntitySpecRegistryEntry((XMLEntitySpecRegistryEntryMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeXMLEntitySpecRegistryEntry((XMLEntitySpecRegistryEntryMBean)update.getRemovedObject());
                        }

                        if (original.getXMLEntitySpecRegistryEntries() == null || original.getXMLEntitySpecRegistryEntries().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 22);
                        }
                     } else if (prop.equals("XMLEventFactory")) {
                        original.setXMLEventFactory(proposed.getXMLEventFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 17);
                     } else if (prop.equals("XMLInputFactory")) {
                        original.setXMLInputFactory(proposed.getXMLInputFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 15);
                     } else if (prop.equals("XMLOutputFactory")) {
                        original.setXMLOutputFactory(proposed.getXMLOutputFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     } else if (prop.equals("XMLParserSelectRegistryEntries")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addXMLParserSelectRegistryEntry((XMLParserSelectRegistryEntryMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeXMLParserSelectRegistryEntry((XMLParserSelectRegistryEntryMBean)update.getRemovedObject());
                        }

                        if (original.getXMLParserSelectRegistryEntries() == null || original.getXMLParserSelectRegistryEntries().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 19);
                        }
                     } else if (prop.equals("XpathFactory")) {
                        original.setXpathFactory(proposed.getXpathFactory());
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
                     } else if (!prop.equals("DynamicallyCreated")) {
                        if (prop.equals("HandleEntityInvalidation")) {
                           original.setHandleEntityInvalidation(proposed.isHandleEntityInvalidation());
                           original._conditionalUnset(update.isUnsetUpdate(), 24);
                        } else {
                           super.applyPropertyUpdate(event, update);
                        }
                     }
                  }
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
            XMLRegistryMBeanImpl copy = (XMLRegistryMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DocumentBuilderFactory")) && this.bean.isDocumentBuilderFactorySet()) {
               copy.setDocumentBuilderFactory(this.bean.getDocumentBuilderFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SAXParserFactory")) && this.bean.isSAXParserFactorySet()) {
               copy.setSAXParserFactory(this.bean.getSAXParserFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("SchemaFactory")) && this.bean.isSchemaFactorySet()) {
               copy.setSchemaFactory(this.bean.getSchemaFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("TransformerFactory")) && this.bean.isTransformerFactorySet()) {
               copy.setTransformerFactory(this.bean.getTransformerFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("WhenToCache")) && this.bean.isWhenToCacheSet()) {
               copy.setWhenToCache(this.bean.getWhenToCache());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("XMLEntitySpecRegistryEntries")) && this.bean.isXMLEntitySpecRegistryEntriesSet() && !copy._isSet(22)) {
               XMLEntitySpecRegistryEntryMBean[] oldXMLEntitySpecRegistryEntries = this.bean.getXMLEntitySpecRegistryEntries();
               XMLEntitySpecRegistryEntryMBean[] newXMLEntitySpecRegistryEntries = new XMLEntitySpecRegistryEntryMBean[oldXMLEntitySpecRegistryEntries.length];

               for(i = 0; i < newXMLEntitySpecRegistryEntries.length; ++i) {
                  newXMLEntitySpecRegistryEntries[i] = (XMLEntitySpecRegistryEntryMBean)((XMLEntitySpecRegistryEntryMBean)this.createCopy((AbstractDescriptorBean)oldXMLEntitySpecRegistryEntries[i], includeObsolete));
               }

               copy.setXMLEntitySpecRegistryEntries(newXMLEntitySpecRegistryEntries);
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

            if ((excludeProps == null || !excludeProps.contains("XMLParserSelectRegistryEntries")) && this.bean.isXMLParserSelectRegistryEntriesSet() && !copy._isSet(19)) {
               XMLParserSelectRegistryEntryMBean[] oldXMLParserSelectRegistryEntries = this.bean.getXMLParserSelectRegistryEntries();
               XMLParserSelectRegistryEntryMBean[] newXMLParserSelectRegistryEntries = new XMLParserSelectRegistryEntryMBean[oldXMLParserSelectRegistryEntries.length];

               for(i = 0; i < newXMLParserSelectRegistryEntries.length; ++i) {
                  newXMLParserSelectRegistryEntries[i] = (XMLParserSelectRegistryEntryMBean)((XMLParserSelectRegistryEntryMBean)this.createCopy((AbstractDescriptorBean)oldXMLParserSelectRegistryEntries[i], includeObsolete));
               }

               copy.setXMLParserSelectRegistryEntries(newXMLParserSelectRegistryEntries);
            }

            if ((excludeProps == null || !excludeProps.contains("XpathFactory")) && this.bean.isXpathFactorySet()) {
               copy.setXpathFactory(this.bean.getXpathFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("HandleEntityInvalidation")) && this.bean.isHandleEntityInvalidationSet()) {
               copy.setHandleEntityInvalidation(this.bean.isHandleEntityInvalidation());
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
         this.inferSubTree(this.bean.getEntitySpecRegistryEntries(), clazz, annotation);
         this.inferSubTree(this.bean.getParserSelectRegistryEntries(), clazz, annotation);
         this.inferSubTree(this.bean.getRegistryEntries(), clazz, annotation);
         this.inferSubTree(this.bean.getXMLEntitySpecRegistryEntries(), clazz, annotation);
         this.inferSubTree(this.bean.getXMLParserSelectRegistryEntries(), clazz, annotation);
      }
   }
}
