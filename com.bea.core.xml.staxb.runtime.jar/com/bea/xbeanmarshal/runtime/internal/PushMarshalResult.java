package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbean.values.TypeStore;
import com.bea.xbean.values.XmlObjectBase;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlTypeName;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.ArrayUtils;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import com.bea.xml.XmlCursor;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract class PushMarshalResult extends MarshalResult implements RuntimeTypeVisitor {
   private static final boolean VERBOSE = Verbose.isVerbose(PushMarshalResult.class);
   private static boolean WRITE_ELEMENT_WILDCARD_ARRAY_WRAPPER = false;
   private Document document;
   private final SOAPElement soapElementTarget;
   private Node currParentNode;
   private QName currElementQName;
   private Node currDomNode;
   private Object currObject;
   private RuntimeBindingProperty currProp;
   private boolean isWrapped;

   PushMarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, SOAPElement soapElement) throws XmlException {
      super(bindingLoader, typeTable);
      this.soapElementTarget = soapElement;
   }

   final void marshalTopType(Object argToSerialize, RuntimeGlobalProperty prop, RuntimeBindingType actual_rtt) throws XmlException {
      assert prop.getName() != null : "null prop name from " + prop;

      QName name = prop.getName();

      assert name != null;

      this.isWrapped = prop.isWrapped();
      this.document = prop.getDocument();
      this.updateState(this.soapElementTarget, name, argToSerialize, prop);
      this.writeContents(actual_rtt);
   }

   private void marshalType(Object obj, RuntimeBindingProperty prop, RuntimeBindingType actual_rtt) throws XmlException {
      assert prop.getName() != null : "null prop name from " + prop;

      this.writeContents(actual_rtt);
   }

   protected final void updateState(Node parentNode, QName elementName, Object obj, RuntimeBindingProperty prop) {
      this.currParentNode = parentNode;
      this.currElementQName = elementName;
      this.currObject = obj;
      this.currProp = prop;
   }

   protected void writeContents(RuntimeBindingType actual_rtt) throws XmlException {
      actual_rtt.accept(this);
   }

   protected final Node getCurrParentNode() {
      return this.currParentNode;
   }

   protected final Object getCurrObject() {
      return this.currObject;
   }

   public void visit(WrappedArrayRuntimeBindingType wrappedArrayRuntimeBindingType) throws XmlException {
      if (this.currObject == null) {
         this.serializeNullXmlObject(this.currParentNode.getOwnerDocument(), this.currParentNode, this.currObject, this.currElementQName);
      } else {
         WrappedArrayRuntimeBindingType.ItemProperty elem_prop = wrappedArrayRuntimeBindingType.getElementProperty();
         QName itemElementQName = elem_prop.getName();
         String arrayElementNamespaceURI = this.currElementQName.getNamespaceURI();
         String arrayElementLocalName = this.currElementQName.getLocalPart();
         String arrayElementPrefix = this.currElementQName.getPrefix();
         String qualifiedName = arrayElementLocalName;
         if (arrayElementPrefix != null && arrayElementPrefix.length() > 0) {
            qualifiedName = arrayElementPrefix + ":" + arrayElementLocalName;
         }

         Document ownerDocument = this.currParentNode.getOwnerDocument();
         Node parentNode = this.currParentNode;
         if (!itemElementQName.equals(XmlTypeName.forElementWildCardElement().getQName()) || itemElementQName.equals(XmlTypeName.forElementWildCardElement().getQName()) && WRITE_ELEMENT_WILDCARD_ARRAY_WRAPPER) {
            Node arrayElementNode = ownerDocument.createElementNS(arrayElementNamespaceURI, qualifiedName);
            ownerDocument.importNode(arrayElementNode, true);
            this.currParentNode.appendChild(arrayElementNode);
            parentNode = arrayElementNode;
            if (VERBOSE) {
               Verbose.log(" WrappedArray before iterating over item name: '" + itemElementQName + "'  appended array element node \n" + XmlBeanUtil.toXMLString(arrayElementNode) + "\ncomplete  parentNode is now \n" + XmlBeanUtil.toXMLString(this.currParentNode) + "\n\n");
            }
         }

         int count = 0;
         Iterator itr = ArrayUtils.getCollectionIterator(this.currObject);

         while(itr.hasNext()) {
            Object item = itr.next();
            this.updateState((Node)parentNode, itemElementQName, item, elem_prop);
            RuntimeBindingType actual_rtt = elem_prop.getActualRuntimeType(item, this);
            this.marshalType(item, elem_prop, actual_rtt);
            if (VERBOSE) {
               Verbose.log(" after marshal type for element " + count++ + ", marshalled array is \n" + XmlBeanUtil.toXMLString(this.currParentNode) + "\n");
            }
         }

      }
   }

   public void visit(XmlBeanAnyRuntimeBindingType xmlBeanAnyRuntimeBindingType) throws XmlException {
      Node parent = this.currParentNode;
      if (this.currObject != null && !XmlBeanUtil.isXmlBeanDocument(this.currObject) && XmlBeanUtil.xmlBeanIsAnonymousType(this.currObject.getClass())) {
         throw new XmlException("anonymous type can't be an xmlbean top element, class: " + this.currObject.getClass());
      } else {
         this.commonMarshalXmlBean(parent);
      }
   }

   public void visit(XmlBeanTypeRuntimeBindingType xmlBeanTypeRuntimeBindingType) throws XmlException {
      this.marshalType();
   }

   private void marshalType() throws XmlException {
      Node parent = this.currParentNode;
      if (this.currObject == null) {
         this.commonMarshalXmlBean(parent);
      } else {
         parent = XmlBeanUtil.createWrappedXBeanTopElement(parent, this.currElementQName);
         this.commonMarshalXmlBean(parent);
      }
   }

   public void visit(XmlBeanDocumentRuntimeBindingType xmlBeanDocumentRuntimeBindingType) throws XmlException {
      Node parent = this.currParentNode;
      this.commonMarshalXmlBean(parent);
   }

   public void visit(XmlBeanBuiltinRuntimeBindingType xmlBeanBuiltinRuntimeBindingType) throws XmlException {
      if (this.currObject != null && XmlBeanUtil.isXmlBeanDocument(this.currObject)) {
         this.commonMarshalXmlBean(this.currParentNode);
      } else {
         this.marshalType();
      }

   }

   private void commonMarshalXmlBean(Node parent) throws XmlException {
      if (this.currObject == null) {
         this.serializeNullXmlObject(this.document, parent, this.currObject, this.currElementQName);
      } else {
         this.getXBeanProxy(this.currObject).appendTo(this.currObject, parent);
      }
   }

   private XBeanProxy getXBeanProxy(Object object) {
      if (PushMarshalResult.ApacheXmlObject.isInstance(object)) {
         return new XBeanProxy.ApacheXBean(new ApacheXmlObject(object));
      } else if (XmlObject.class.isInstance(object)) {
         return new XBeanProxy.BeaXBean((XmlObject)object);
      } else {
         throw new IllegalArgumentException("Unknown XmlBean type " + object.getClass().getName());
      }
   }

   static class ApacheXmlObjectBase extends BaseApacheProxy {
      public ApacheXmlObjectBase(Object target) {
         super(target);
      }

      public static boolean isInstance(Object object) {
         return isInstanceOf("org.apache.xmlbeans.impl.values.XmlObjectBase", object);
      }

      public ApacheTypeStore get_store() {
         return (ApacheTypeStore)this.invokeTarget("get_store", ApacheTypeStore.class);
      }
   }

   static class ApacheXmlObject extends BaseApacheProxy {
      public ApacheXmlObject(Object wrapped) {
         super(wrapped);
      }

      public static boolean isInstance(Object object) {
         return isInstanceOf("org.apache.xmlbeans.XmlObject", object);
      }

      public Node getDomNode() {
         return (Node)this.invokeTarget("getDomNode");
      }

      public Node newDomNode() {
         return (Node)this.invokeTarget("newDomNode");
      }

      public ApacheXmlCursor newCursor() {
         return (ApacheXmlCursor)this.invokeTarget("newCursor", ApacheXmlCursor.class);
      }
   }

   static class ApacheXmlCursor extends BaseApacheProxy {
      public ApacheXmlCursor(Object wrapped) {
         super(wrapped);
      }

      public void toStartDoc() {
         this.invokeTarget("toStartDoc");
      }

      public Boolean toFirstAttribute() {
         return (Boolean)this.invokeTarget("toFirstAttribute");
      }

      public QName getName() {
         return (QName)this.invokeTarget("getName");
      }

      public String getTextValue() {
         return (String)this.invokeTarget("getTextValue");
      }

      public Boolean toNextAttribute() {
         return (Boolean)this.invokeTarget("toNextAttribute");
      }

      public Boolean toParent() {
         return (Boolean)this.invokeTarget("toParent");
      }

      public void dispose() {
         this.invokeTarget("dispose");
      }
   }

   static class ApacheTypeStore extends BaseApacheProxy {
      public ApacheTypeStore(Object wrapped) {
         super(wrapped);
      }

      public ApacheXmlCursor new_cursor() {
         return (ApacheXmlCursor)this.invokeTarget("new_cursor", ApacheXmlCursor.class);
      }
   }

   static class BaseApacheProxy {
      private Object target;
      private Class targetClass;

      protected BaseApacheProxy(Object wrapped) {
         this.setTarget(wrapped);
      }

      private void setTarget(Object wrapped) {
         this.target = wrapped;
         this.targetClass = this.target.getClass();
      }

      protected static boolean isInstanceOf(String className, Object object) {
         boolean result = false;

         try {
            Class objectClass = object.getClass();
            ClassLoader loader = objectClass.getClassLoader();
            Class xmlObjectBaseClass = loader.loadClass(className);
            result = xmlObjectBaseClass.isInstance(object);
            return result;
         } catch (ClassNotFoundException var6) {
            return false;
         }
      }

      protected Object invokeTarget(String methodName) {
         Object result = null;

         try {
            Method targetMethod = this.targetClass.getMethod(methodName);
            boolean wasAccessible = targetMethod.isAccessible();
            if (!wasAccessible) {
               targetMethod.setAccessible(true);
            }

            try {
               Object resultObject = targetMethod.invoke(this.target);
               if (resultObject != null) {
                  result = resultObject;
               }
            } finally {
               if (!wasAccessible) {
                  targetMethod.setAccessible(false);
               }

            }

            return result;
         } catch (InvocationTargetException var12) {
            throw new RuntimeException(var12);
         } catch (NoSuchMethodException var13) {
            throw new RuntimeException(var13);
         } catch (IllegalAccessException var14) {
            throw new RuntimeException(var14);
         }
      }

      protected BaseApacheProxy invokeTarget(String methodName, Class returnType) {
         BaseApacheProxy result = null;
         Object resultObject = this.invokeTarget(methodName);
         if (resultObject != null) {
            try {
               Constructor resultConstructor = returnType.getConstructor(Object.class);
               result = (BaseApacheProxy)resultConstructor.newInstance(resultObject);
            } catch (InstantiationException var6) {
               throw new RuntimeException(var6);
            } catch (IllegalAccessException var7) {
               throw new RuntimeException(var7);
            } catch (InvocationTargetException var8) {
               throw new RuntimeException(var8);
            } catch (NoSuchMethodException var9) {
               throw new RuntimeException(var9);
            }
         }

         return result;
      }
   }

   private abstract static class XBeanProxy {
      private XBeanProxy() {
      }

      abstract Node getNode();

      abstract Node getNoDefaultNamespaceNode();

      abstract boolean isRoot();

      private void appendTo(Object currObject, Node parent) {
         Node n = this.getNode();
         if (((Node)n).getNodeType() == 9) {
            n = ((Document)n).getDocumentElement();
         }

         if (currObject != null && XmlBeanUtil.isXmlBeanDocument(currObject)) {
            Node newNode = this.getNoDefaultNamespaceNode();
            if (((Node)newNode).getNodeType() == 9) {
               newNode = ((Document)newNode).getDocumentElement();
            }

            n = newNode;
         }

         if (this.isRoot()) {
            NodeList nodeList = ((Node)n).getChildNodes();

            for(int i = 0; i < nodeList.getLength(); ++i) {
               Node child = nodeList.item(i);
               parent.appendChild(parent.getOwnerDocument().importNode(child, true));
            }
         } else {
            Node firstChild = ((Node)n).getFirstChild();
            if (firstChild != null && firstChild.getLocalName() != null && firstChild.getLocalName().equalsIgnoreCase("xml-fragment")) {
               if (PushMarshalResult.VERBOSE) {
                  Verbose.log(" remove xml-fragment from " + XmlBeanUtil.toXMLString((Node)n));
               }

               ((Node)n).removeChild(firstChild);
               NodeList nodeList = firstChild.getChildNodes();
               int initialLength = nodeList.getLength();

               for(int i = 0; i < initialLength; ++i) {
                  Node child = nodeList.item(0);
                  ((Node)n).appendChild(child);
               }
            }

            Element pElement;
            QName attrQName;
            if (PushMarshalResult.ApacheXmlObjectBase.isInstance(currObject) && parent instanceof Element) {
               pElement = (Element)parent;
               ApacheTypeStore ts = (new ApacheXmlObjectBase(currObject)).get_store();
               ApacheXmlCursor cursor = ts.new_cursor();
               cursor.toStartDoc();
               if (cursor.toFirstAttribute()) {
                  do {
                     attrQName = cursor.getName();
                     if (!pElement.hasAttributeNS(attrQName.getNamespaceURI(), attrQName.getLocalPart())) {
                        pElement.setAttributeNS(attrQName.getNamespaceURI(), attrQName.getLocalPart(), cursor.getTextValue());
                     }
                  } while(cursor.toNextAttribute());
               }
            } else if (currObject instanceof XmlObjectBase && parent instanceof Element) {
               pElement = (Element)parent;
               TypeStore ts = ((XmlObjectBase)currObject).get_store();
               XmlCursor cursor = ts.new_cursor();
               cursor.toStartDoc();
               if (cursor.toFirstAttribute()) {
                  do {
                     attrQName = cursor.getName();
                     if (!pElement.hasAttributeNS(attrQName.getNamespaceURI(), attrQName.getLocalPart())) {
                        pElement.setAttributeNS(attrQName.getNamespaceURI(), attrQName.getLocalPart(), cursor.getTextValue());
                     }
                  } while(cursor.toNextAttribute());
               }
            }

            parent.appendChild(parent.getOwnerDocument().importNode((Node)n, true));
         }

         if (PushMarshalResult.VERBOSE) {
            Verbose.log("serialized XmlBean topElement is " + XmlBeanUtil.toXMLString(parent) + "\n");
         }

      }

      // $FF: synthetic method
      XBeanProxy(Object x0) {
         this();
      }

      private static class BeaXBean extends XBeanProxy {
         XmlObject xbean;

         private BeaXBean(XmlObject xbean) {
            super(null);
            this.xbean = null;
            this.xbean = xbean;
         }

         Node getNode() {
            return this.xbean.getDomNode();
         }

         Node getNoDefaultNamespaceNode() {
            return this.xbean.newDomNode();
         }

         boolean isRoot() {
            XmlCursor cur = null;

            boolean var2;
            try {
               cur = this.xbean.newCursor();
               if (cur != null) {
                  var2 = cur.toParent();
                  return var2;
               }

               var2 = true;
            } finally {
               if (cur != null) {
                  cur.dispose();
               }

            }

            return var2;
         }

         // $FF: synthetic method
         BeaXBean(XmlObject x0, Object x1) {
            this(x0);
         }
      }

      private static class ApacheXBean extends XBeanProxy {
         ApacheXmlObject xbean;

         private ApacheXBean(ApacheXmlObject xbean) {
            super(null);
            this.xbean = null;
            this.xbean = xbean;
         }

         Node getNode() {
            return this.xbean.getDomNode();
         }

         Node getNoDefaultNamespaceNode() {
            return this.xbean.newDomNode();
         }

         boolean isRoot() {
            ApacheXmlCursor cur = null;

            boolean var2;
            try {
               cur = this.xbean.newCursor();
               if (cur != null) {
                  var2 = cur.toParent();
                  return var2;
               }

               var2 = true;
            } finally {
               if (cur != null) {
                  cur.dispose();
               }

            }

            return var2;
         }

         // $FF: synthetic method
         ApacheXBean(ApacheXmlObject x0, Object x1) {
            this(x0);
         }
      }
   }
}
