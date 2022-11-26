package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.ObjectFactory;
import com.bea.staxb.runtime.StreamReaderFromNode;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.common.PrefixResolver;
import com.bea.xbean.common.XmlStreamUtils;
import com.bea.xbean.richParser.XMLStreamReaderExt;
import com.bea.xbean.richParser.XMLStreamReaderExtImpl;
import com.bea.xml.XmlException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

abstract class SoapUnmarshalResult extends UnmarshalResult implements PrefixResolver {
   private final SoapAttributeHolder soapAttributeHolder = new SoapAttributeHolder();
   private final RefObjectTable refObjectTable;
   private final StreamRefNavigator streamRefNavigator;
   private List fillEvents;
   private IdentityHashMap interToFinalMap;

   SoapUnmarshalResult(BindingLoader loader, RuntimeBindingTypeTable typeTable, RefObjectTable refObjectTable, StreamRefNavigator refNavigator, UnmarshalOptions options) {
      super(loader, typeTable, options);
      this.refObjectTable = refObjectTable;

      assert refNavigator != null;

      this.streamRefNavigator = refNavigator;
   }

   public String getNamespaceForPrefix(String prefix) {
      assert this.baseReader != null;

      return this.getNamespaceContext().getNamespaceURI(prefix);
   }

   protected XMLStreamReader getValidatingStream(XMLStreamReader reader) {
      return reader;
   }

   protected XMLStreamReader getValidatingStream(XmlTypeName schemaType, XMLStreamReader reader) throws XmlException {
      return reader;
   }

   protected Object unmarshalBindingType(RuntimeBindingType actual_rtt) throws XmlException {
      this.updateSoapAttributes();
      String id = this.soapAttributeHolder.ref;
      if (id != null) {
         this.baseReader = this.relocateStreamToRef(id);
         this.updateSoapAttributes();
      }

      id = this.soapAttributeHolder.id;
      this.updateAttributeState();
      ObjectFactory of = this.extractObjectFactory();

      Object retval;
      try {
         TypeUnmarshaller um;
         Object inter;
         if (of == null) {
            if (id == null) {
               if (this.hasXsiNil()) {
                  um = NullUnmarshaller.getInstance();
               } else {
                  um = actual_rtt.getUnmarshaller();
               }

               retval = um.unmarshal(this);
            } else if (!this.hasXsiNil() && actual_rtt.hasElementChildren()) {
               inter = actual_rtt.createIntermediary(this);
               retval = this.umarshalComplexElementWithId(actual_rtt, inter, id);
            } else {
               retval = this.unmarshalSimpleElementWithId(actual_rtt, id);
            }
         } else {
            inter = of.createObject(actual_rtt.getJavaType());
            um = actual_rtt.getUnmarshaller();
            Object inter = actual_rtt.createIntermediary(this, inter);
            if (id == null) {
               um.unmarshalIntoIntermediary(inter, this);
               retval = actual_rtt.getFinalObjectFromIntermediary(inter, this);
            } else {
               retval = this.umarshalComplexElementWithId(actual_rtt, inter, id);
            }
         }
      } catch (InvalidLexicalValueException var8) {
         assert !this.errors.isEmpty();

         retval = null;
      }

      this.fireFillEvents();
      return retval;
   }

   private void fireFillEvents() {
      if (this.fillEvents != null && !this.fillEvents.isEmpty()) {
         int i = 0;

         for(int len = this.fillEvents.size(); i < len; ++i) {
            FillEvent event = (FillEvent)this.fillEvents.get(i);
            Object prop_val = this.refObjectTable.getObjectForRef(event.ref);

            assert prop_val != null;

            event.prop.fill(this.getFinalObjForInter(event.inter), event.index, prop_val);
         }

      }
   }

   private Object getFinalObjForInter(Object inter) {
      assert this.interToFinalMap != null;

      Object final_obj = this.interToFinalMap.get(inter);

      assert final_obj != null;

      return final_obj;
   }

   void extractAndFillElementProp(RuntimeBindingProperty prop, Object inter) throws XmlException {
      assert this.baseReader != null;

      XMLStreamReaderExt curr_reader = this.baseReader;
      if (!this.processRef(prop, inter)) {
         this.basicExtractAndFill(prop, inter);
      }

      if (this.baseReader != curr_reader) {
         this.baseReader = curr_reader;
         this.skipElement();
      }

   }

   private boolean processRef(RuntimeBindingProperty prop, Object inter) throws XmlException {
      this.updateSoapAttributes();
      String ref = this.soapAttributeHolder.ref;
      if (ref == null) {
         return false;
      } else {
         RefObjectTable.RefEntry entry = this.refObjectTable.getEntryForRef(ref);
         Object tmpval = entry == null ? null : entry.final_obj;
         if (tmpval == null) {
            Object tmp_inter = entry == null ? null : entry.inter;
            if (tmp_inter == null) {
               this.baseReader = this.relocateStreamToRef(ref);
               this.updateSoapAttributes();
               this.updateAttributeState();
               return false;
            } else if (!prop.isMutable()) {
               throw new XmlException("unable to process ref " + ref + " since property " + prop + " is not mutable");
            } else {
               this.enqueueFillEvent(inter, ref, prop);
               prop.fillPlaceholder(inter);
               this.skipElement();
               return true;
            }
         } else {
            prop.fill(inter, tmpval);
            this.skipElement();
            return true;
         }
      }
   }

   private void enqueueFillEvent(Object inter, String ref, RuntimeBindingProperty prop) {
      if (this.fillEvents == null) {
         this.fillEvents = new ArrayList();
      }

      this.fillEvents.add(new FillEvent(ref, prop, prop.getSize(inter), inter));
   }

   private void basicExtractAndFill(RuntimeBindingProperty prop, Object inter) throws XmlException {
      try {
         Object this_val = this.unmarshalElementProperty(prop, inter);
         prop.fill(inter, this_val);
      } catch (InvalidLexicalValueException var4) {
      }

   }

   private XMLStreamReaderExt relocateStreamToRef(String ref) throws XmlException {
      assert this.streamRefNavigator != null;

      StreamReaderFromNode streamReaderFromNode = this.options.getStreamReaderFromNode();

      assert streamReaderFromNode != null;

      XMLStreamReader reader = this.streamRefNavigator.lookupRef(ref, streamReaderFromNode);
      if (reader == null) {
         throw new XmlException("failed to deref " + ref);
      } else {
         return new XMLStreamReaderExtImpl(reader);
      }
   }

   private void updateSoapAttributes() throws XmlException {
      QName idname = this.getIdAttributeName();
      QName refname = this.getRefAttributeName();

      assert idname.getNamespaceURI().equals(refname.getNamespaceURI());

      String soap_uri = idname.getNamespaceURI();

      assert soap_uri != null;

      String id_lname = idname.getLocalPart();
      String ref_lname = refname.getLocalPart();
      XMLStreamReaderExt reader = this.baseReader;

      assert reader.isStartElement() : " illegal state: " + XmlStreamUtils.printEvent(reader);

      this.soapAttributeHolder.clear();
      int att_cnt = reader.getAttributeCount();

      for(int att_idx = 0; att_idx < att_cnt; ++att_idx) {
         String uri = reader.getAttributeNamespace(att_idx);
         if (soap_uri.equals(uri == null ? "" : uri)) {
            try {
               String lname = reader.getAttributeLocalName(att_idx);
               String attval;
               if (id_lname.equals(lname)) {
                  attval = reader.getAttributeStringValue(att_idx, 3);
                  this.soapAttributeHolder.id = this.getIdFromAttributeValue(attval);
                  return;
               }

               if (ref_lname.equals(lname)) {
                  attval = reader.getAttributeStringValue(att_idx, 3);
                  this.soapAttributeHolder.ref = this.getReferencedIdFromAttributeValue(attval);
                  return;
               }
            } catch (InvalidLexicalValueException var12) {
               this.addError(var12.getMessage(), var12.getLocation());
            } catch (XMLStreamException var13) {
               throw new XmlException(var13);
            }
         }
      }

   }

   protected abstract String getReferencedIdFromAttributeValue(String var1);

   protected abstract String getIdFromAttributeValue(String var1);

   protected abstract QName getIdAttributeName();

   protected abstract QName getRefAttributeName();

   private Object unmarshalElementProperty(RuntimeBindingProperty prop, Object inter) throws XmlException {
      RuntimeBindingType actual_rtt = prop.getRuntimeBindingType().determineActualRuntimeType(this);
      String id = prop.getLexicalDefault();
      if (id != null) {
         this.setNextElementDefault(id);
      }

      id = this.soapAttributeHolder.id;
      Object this_val;
      if (!this.hasXsiNil() && actual_rtt.hasElementChildren()) {
         Object prop_inter = prop.createIntermediary(inter, actual_rtt, this);
         this_val = this.umarshalComplexElementWithId(actual_rtt, prop_inter, id);
      } else {
         this_val = this.unmarshalSimpleElementWithId(actual_rtt, id);
      }

      return this_val;
   }

   private Object unmarshalSimpleElementWithId(RuntimeBindingType actual_rtt, String id) throws XmlException {
      TypeUnmarshaller um = this.getUnmarshaller(actual_rtt);
      Object this_val = um.unmarshal(this);
      if (id != null) {
         this.refObjectTable.putForRef(id, this_val, this_val);
      }

      return this_val;
   }

   private Object umarshalComplexElementWithId(RuntimeBindingType actual_rtt, Object prop_inter, String id) throws XmlException {
      boolean update_again = this.updateRefTable(actual_rtt, prop_inter, id);
      actual_rtt.getUnmarshaller().unmarshalIntoIntermediary(prop_inter, this);
      Object this_val = actual_rtt.getFinalObjectFromIntermediary(prop_inter, this);
      this.interToFinalMap().put(prop_inter, this_val);
      if (update_again) {
         this.refObjectTable.putObjectForRef(id, this_val);
      }

      return this_val;
   }

   private IdentityHashMap interToFinalMap() {
      if (this.interToFinalMap == null) {
         this.interToFinalMap = new IdentityHashMap();
      }

      return this.interToFinalMap;
   }

   private boolean updateRefTable(RuntimeBindingType actual_rtt, Object prop_inter, String id) {
      if (id == null) {
         return false;
      } else if (actual_rtt.isObjectFromIntermediateIdempotent()) {
         this.refObjectTable.putForRef(id, prop_inter, actual_rtt.getObjectFromIntermediate(prop_inter));
         return false;
      } else {
         this.refObjectTable.putIntermediateForRef(id, prop_inter);
         return true;
      }
   }

   private static final class FillEvent {
      final String ref;
      final RuntimeBindingProperty prop;
      final int index;
      final Object inter;

      public FillEvent(String ref, RuntimeBindingProperty prop, int index, Object inter) {
         this.ref = ref;
         this.prop = prop;
         this.index = index;
         this.inter = inter;
      }
   }

   private static final class SoapAttributeHolder {
      String id;
      String ref;

      private SoapAttributeHolder() {
      }

      void clear() {
         this.id = null;
         this.ref = null;
      }

      // $FF: synthetic method
      SoapAttributeHolder(Object x0) {
         this();
      }
   }
}
