package com.bea.xbeanmarshal.buildtime.internal.util;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.JavaTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanAnyType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeansBuiltinBindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlTypeName;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlBeanUtil {
   private static final boolean VERBOSE = Verbose.isVerbose(XmlBeanUtil.class);
   public static final String SOAPFAULTS_CONTAIN_XMLBEANS = "com.bea.SOAPFAULTS_CONTAIN_XMLBEANS";
   private static final String XMLOBJECT_SCHEMATYPE_FIELD = "type";
   static final String APACHE_XMLOBJECT_CLASSNAME = "org.apache.xmlbeans.XmlObject";

   private XmlBeanUtil() {
   }

   public static boolean isXmlBean(Class type) {
      Class xmlObject = null;

      try {
         xmlObject = Class.forName("org.apache.xmlbeans.XmlObject");
         if (xmlObject.isAssignableFrom(type)) {
            return true;
         }
      } catch (Throwable var4) {
         throw new RuntimeException(" could not load class org.apache.xmlbeans.XmlObject");
      }

      try {
         xmlObject = type.getClassLoader().loadClass(XmlObject.class.getName());
      } catch (Throwable var3) {
         throw new RuntimeException(" could not load class com.bea.xml.XmlObject");
      }

      return xmlObject.isAssignableFrom(type);
   }

   public static boolean xmlBeanIsAnonymousType(Class c) {
      if (c != null) {
         if (XmlObject.class.isAssignableFrom(c)) {
            SchemaType st = getBeaSchemaTypeForBeaXmlBean(c);
            if (st == null) {
               return false;
            }

            return st.isAnonymousType();
         }

         Class apacheXmlObjectClass;
         try {
            apacheXmlObjectClass = c.getClassLoader().loadClass("org.apache.xmlbeans.XmlObject");
         } catch (ClassNotFoundException var7) {
            return false;
         }

         if (apacheXmlObjectClass.isAssignableFrom(c)) {
            Object st = getApacheSchemaTypeForApacheXmlBean(c);
            if (st == null) {
               return false;
            }

            boolean isAnonymousType = false;

            try {
               Method isAnonymousTypeMethod = st.getClass().getMethod("isAnonymousType");
               Object isAnonymousTypeObject = isAnonymousTypeMethod.invoke(st);
               if (isAnonymousTypeObject instanceof Boolean) {
                  isAnonymousType = (Boolean)isAnonymousTypeObject;
               }
            } catch (Exception var6) {
            }

            return isAnonymousType;
         }
      }

      return false;
   }

   public static boolean xmlBeanIsDocumentType(Class c) {
      if (c != null) {
         if (XmlObject.class.isAssignableFrom(c)) {
            SchemaType st = getBeaSchemaTypeForBeaXmlBean(c);
            if (st == null) {
               return false;
            }

            return st.isDocumentType();
         }

         ClassLoader xmlBeanClassLoader = c.getClassLoader();
         Class apacheXmlObjectClass = null;

         try {
            apacheXmlObjectClass = xmlBeanClassLoader.loadClass("org.apache.xmlbeans.XmlObject");
         } catch (ClassNotFoundException var7) {
            return false;
         }

         if (apacheXmlObjectClass.isAssignableFrom(c)) {
            Object st = getApacheSchemaTypeForApacheXmlBean(c);
            if (st == null) {
               return false;
            }

            try {
               Method isDocumentTypeMethod = st.getClass().getMethod("isDocumentType");
               Object isDocumentTypeObject = isDocumentTypeMethod.invoke(st);
               if (isDocumentTypeObject instanceof Boolean) {
                  return (Boolean)isDocumentTypeObject;
               }
            } catch (Exception var6) {
               return false;
            }
         }
      }

      return false;
   }

   static Object getApacheSchemaTypeForApacheXmlBean(Class xmlBeanClass) {
      Object st = null;
      if (xmlBeanClass == null) {
         return st;
      } else {
         ClassLoader xmlBeanClassLoader = xmlBeanClass.getClassLoader();
         Class apacheXmlObjectClass = null;

         try {
            apacheXmlObjectClass = xmlBeanClassLoader.loadClass("org.apache.xmlbeans.XmlObject");
         } catch (ClassNotFoundException var9) {
            return null;
         }

         assert apacheXmlObjectClass.isAssignableFrom(xmlBeanClass);

         if (apacheXmlObjectClass.isAssignableFrom(xmlBeanClass)) {
            Field f = null;

            try {
               f = xmlBeanClass.getField("type");
            } catch (NoSuchFieldException var8) {
               var8.printStackTrace();
               return st;
            }

            if (f != null) {
               try {
                  st = f.get((Object)null);
               } catch (ExceptionInInitializerError var6) {
                  return st;
               } catch (IllegalAccessException var7) {
                  var7.printStackTrace();
                  return st;
               }
            }
         }

         return st;
      }
   }

   static SchemaType getBeaSchemaTypeForBeaXmlBean(Class xmlBeanClass) {
      SchemaType st = null;
      if (xmlBeanClass == null) {
         return st;
      } else {
         assert XmlObject.class.isAssignableFrom(xmlBeanClass);

         if (XmlObject.class.isAssignableFrom(xmlBeanClass)) {
            Field f = null;

            try {
               f = xmlBeanClass.getField("type");
            } catch (NoSuchFieldException var6) {
               var6.printStackTrace();
               return st;
            }

            if (f != null) {
               try {
                  st = (SchemaType)f.get((Object)null);
               } catch (ExceptionInInitializerError var4) {
                  return st;
               } catch (IllegalAccessException var5) {
                  var5.printStackTrace();
                  return st;
               }
            }
         }

         return st;
      }
   }

   public static Node createWrappedXBeanTopElement(Node parent, QName elementQName) throws XmlException {
      Node newParent = null;

      try {
         String prefix = elementQName.getPrefix();
         if (prefix == null || prefix.equals("")) {
            prefix = parent.getPrefix();
         }

         Document ownerDocument = parent.getOwnerDocument();
         String qualifiedName = elementQName.getLocalPart();
         if (elementQName.getNamespaceURI() != null && !elementQName.getNamespaceURI().equals("")) {
            if (prefix != null && prefix.length() > 0) {
               qualifiedName = prefix + ":" + qualifiedName;
            }

            newParent = ownerDocument.createElementNS(elementQName.getNamespaceURI(), qualifiedName);
         } else {
            newParent = ownerDocument.createElement(elementQName.getLocalPart());
         }

         parent.appendChild(newParent);
         return newParent;
      } catch (DOMException var6) {
         throw new XmlException(" could not create child element '" + elementQName + "' for Wrapped XMLBean operation on '" + toXMLString(parent) + "'  ", var6);
      }
   }

   public static boolean isXmlBeanDocument(Object o) {
      if (o == null) {
         return false;
      } else if (XmlObject.class.isInstance(o)) {
         SchemaType st = getBeaSchemaType(o);
         return st == null ? false : st.isDocumentType();
      } else {
         ClassLoader xmlBeanClassLoader = o.getClass().getClassLoader();
         Class apacheXmlObjectClass = null;

         try {
            apacheXmlObjectClass = xmlBeanClassLoader.loadClass("org.apache.xmlbeans.XmlObject");
         } catch (ClassNotFoundException var7) {
            return false;
         }

         if (apacheXmlObjectClass.isInstance(o)) {
            Object st = getApacheSchemaType(o);
            if (st == null) {
               return false;
            }

            try {
               Method isDocumentTypeMethod = st.getClass().getMethod("isDocumentType");
               Object isDocumentTypeObject = isDocumentTypeMethod.invoke(st);
               if (isDocumentTypeObject instanceof Boolean) {
                  return (Boolean)isDocumentTypeObject;
               }
            } catch (Exception var6) {
               return false;
            }
         }

         return false;
      }
   }

   private static Object getApacheSchemaType(Object xmlBean) {
      if (xmlBean == null) {
         return null;
      } else {
         Class xmlBeanClass = xmlBean.getClass();
         ClassLoader xmlBeanClassLoader = xmlBeanClass.getClassLoader();
         Class apacheXmlObjectClass = null;

         try {
            apacheXmlObjectClass = xmlBeanClassLoader.loadClass("org.apache.xmlbeans.XmlObject");
         } catch (ClassNotFoundException var7) {
            return null;
         }

         assert apacheXmlObjectClass.isInstance(xmlBean);

         try {
            Method schemaTypeMethod = xmlBeanClass.getMethod("schemaType");
            Object schemaTypeObject = schemaTypeMethod.invoke(xmlBean);
            return schemaTypeObject;
         } catch (Exception var6) {
            return null;
         }
      }
   }

   private static SchemaType getBeaSchemaType(Object xmlBean) {
      if (xmlBean == null) {
         return null;
      } else {
         assert XmlObject.class.isInstance(xmlBean);

         XmlObject instance = (XmlObject)xmlBean;
         return instance.schemaType();
      }
   }

   public static Node createXMLFragmentFromElement(Node node) {
      if (node == null) {
         return null;
      } else {
         Node xmlFragment = node.getOwnerDocument().createElement("xml-fragment");
         copyAttributes(node, (Element)xmlFragment);
         if (node.hasChildNodes()) {
            Element clone = (Element)node.cloneNode(true);
            Node nodeClone = clone.getFirstChild();
            if (nodeClone != null) {
               Node nextSibling = nodeClone.getNextSibling();
               xmlFragment.appendChild(nodeClone);

               while(nextSibling != null) {
                  nodeClone = nextSibling;
                  nextSibling = nextSibling.getNextSibling();
                  xmlFragment.appendChild(nodeClone);
               }
            }
         }

         if (VERBOSE) {
            Verbose.log("created xmlFragment \n" + toXMLString(xmlFragment));
         }

         return xmlFragment;
      }
   }

   public static String toXMLString(Node node) {
      CharArrayWriter bufWriter = new CharArrayWriter();

      try {
         xml2Stream(node, bufWriter);
      } catch (IOException var3) {
         if (VERBOSE) {
            Verbose.log(var3.getMessage());
         }
      }

      return bufWriter.toString();
   }

   public static void xml2Stream(Node node, Writer stream) throws IOException {
      Transformer serializer = null;

      try {
         serializer = TransformerFactory.newInstance().newTransformer();
      } catch (TransformerConfigurationException var4) {
         throw new AssertionError(var4);
      } catch (TransformerFactoryConfigurationError var5) {
         throw new AssertionError(var5);
      }

      try {
         serializer.transform(new DOMSource(node), new StreamResult(stream));
      } catch (TransformerException var6) {
         if (VERBOSE) {
            Verbose.log(var6.getMessage());
         }
      }

      stream.flush();
   }

   private static String getCanonicalName(Class c) {
      if (c.isArray()) {
         String canonicalName = getCanonicalName(c.getComponentType());
         return canonicalName != null ? canonicalName + "[]" : null;
      } else {
         return c.getName();
      }
   }

   public static BindingType getBindingTypeFromJavaClass(Class clazz, BindingLoader loader, boolean check_supertypes, QName argElementQName) {
      BindingType bt = getAnyBindingType(argElementQName, getCanonicalName(clazz));
      if (bt != null) {
         return bt;
      } else {
         JavaTypeName jtn = javaTypeNameFromClass(clazz);
         BindingLoader builtinLoader = XmlBeansBuiltinBindingLoader.getInstance();
         BindingTypeName btn = builtinLoader.lookupTypeFor(jtn);
         if (btn != null) {
            bt = builtinLoader.getBindingType(btn);
            if (bt != null) {
               if (VERBOSE) {
                  Verbose.log(" for XmlBean class '" + clazz.getName() + "',  returning builtin type '" + bt + "'");
               }

               return bt;
            }
         }

         btn = loader.lookupTypeFor(jtn);
         if (VERBOSE) {
            Verbose.log("passed in class: '" + clazz.getName() + "',  constructed JTN: '" + jtn + "', yields BTN: '" + btn + "'");
         }

         if (btn != null) {
            bt = loader.getBindingType(btn);
            if (bt != null) {
               if (VERBOSE) {
                  Verbose.log("  found and returning BindingType: " + bt);
               }

               return bt;
            }
         }

         Class[] ifaces = clazz.getInterfaces();

         for(int i = 0; i < ifaces.length; ++i) {
            if (VERBOSE) {
               Verbose.log(" check class interface '" + ifaces[i] + "'");
            }

            jtn = javaTypeNameFromClass(ifaces[i]);
            btn = builtinLoader.lookupTypeFor(jtn);
            if (btn != null) {
               bt = builtinLoader.getBindingType(btn);
               if (bt != null) {
                  if (VERBOSE) {
                     Verbose.log("  found and returning BuiltinBindingType: " + bt);
                  }

                  return bt;
               }
            } else {
               btn = loader.lookupTypeFor(jtn);
               if (btn != null) {
                  bt = loader.getBindingType(btn);
                  if (bt != null) {
                     if (VERBOSE) {
                        Verbose.log("  found and returning BindingType: " + bt);
                     }

                     return bt;
                  }
               }
            }
         }

         return null;
      }
   }

   public static BindingType getAnyBindingType(QName argElementQName, String className) {
      if (argElementQName != null && argElementQName.equals(XmlTypeName.ANY_ELEMENT_WILDCARD_ELEMENT_NAME)) {
         return XmlObject.class.getName().equals(className) ? XmlBeanAnyType.BEA : XmlBeanAnyType.APACHE;
      } else {
         return null;
      }
   }

   public static boolean hasXsiNil(Node n) {
      if (!n.hasAttributes()) {
         return false;
      } else {
         Node xsiNil = n.getAttributes().getNamedItemNS("http://www.w3.org/2001/XMLSchema-instance", "nil");
         if (xsiNil == null) {
            return false;
         } else if (xsiNil.getNodeType() != 2) {
            return false;
         } else {
            String attributeValue = xsiNil.getNodeValue();
            if (attributeValue == null) {
               return false;
            } else {
               return attributeValue.equalsIgnoreCase("true");
            }
         }
      }
   }

   private static JavaTypeName javaTypeNameFromClass(Class c) {
      return JavaTypeName.forClassName(c.getName());
   }

   public static void copyAttributes(Node sourceNode, Element destElement) {
      if (sourceNode != null && destElement != null) {
         if (sourceNode.hasAttributes()) {
            NamedNodeMap nnm = sourceNode.getAttributes();

            for(int i = 0; i < nnm.getLength(); ++i) {
               Node attr = nnm.item(i);
               destElement.setAttributeNS(attr.getNamespaceURI(), attr.getNodeName(), attr.getNodeValue());
            }

         }
      }
   }
}
