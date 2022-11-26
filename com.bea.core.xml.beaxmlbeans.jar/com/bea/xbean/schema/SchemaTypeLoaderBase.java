package com.bea.xbean.schema;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.store.Locale;
import com.bea.xbean.validator.ValidatingXMLInputStream;
import com.bea.xml.SchemaAttributeGroup;
import com.bea.xml.SchemaField;
import com.bea.xml.SchemaGlobalAttribute;
import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaModelGroup;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlFactoryHook;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlSaxHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public abstract class SchemaTypeLoaderBase implements SchemaTypeLoader {
   private static final String USER_AGENT = "XMLBeans/" + XmlBeans.getVersion() + " (" + XmlBeans.getTitle() + ")";
   private static final Method _pathCompiler = getMethod("com.bea.xbean.store.Path", "compilePath", new Class[]{String.class, XmlOptions.class});
   private static final Method _queryCompiler = getMethod("com.bea.xbean.store.Query", "compileQuery", new Class[]{String.class, XmlOptions.class});

   private static Method getMethod(String className, String methodName, Class[] args) {
      try {
         return Class.forName(className).getDeclaredMethod(methodName, args);
      } catch (Exception var4) {
         throw new IllegalStateException("Cannot find " + className + "." + methodName + ".  verify that xmlstore " + "(from xbean.jar) is on classpath");
      }
   }

   private static Object invokeMethod(Method method, Object[] args) {
      try {
         return method.invoke(method, args);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      } catch (Exception var6) {
         IllegalStateException ise = new IllegalStateException(var6.getMessage());
         ise.initCause(var6);
         throw ise;
      }
   }

   private static String doCompilePath(String pathExpr, XmlOptions options) {
      return (String)invokeMethod(_pathCompiler, new Object[]{pathExpr, options});
   }

   private static String doCompileQuery(String queryExpr, XmlOptions options) {
      return (String)invokeMethod(_queryCompiler, new Object[]{queryExpr, options});
   }

   public SchemaType findType(QName name) {
      SchemaType.Ref ref = this.findTypeRef(name);
      if (ref == null) {
         return null;
      } else {
         SchemaType result = ref.get();

         assert result != null;

         return result;
      }
   }

   public SchemaType findDocumentType(QName name) {
      SchemaType.Ref ref = this.findDocumentTypeRef(name);
      if (ref == null) {
         return null;
      } else {
         SchemaType result = ref.get();

         assert result != null;

         return result;
      }
   }

   public SchemaType findAttributeType(QName name) {
      SchemaType.Ref ref = this.findAttributeTypeRef(name);
      if (ref == null) {
         return null;
      } else {
         SchemaType result = ref.get();

         assert result != null;

         return result;
      }
   }

   public SchemaModelGroup findModelGroup(QName name) {
      SchemaModelGroup.Ref ref = this.findModelGroupRef(name);
      if (ref == null) {
         return null;
      } else {
         SchemaModelGroup result = ref.get();

         assert result != null;

         return result;
      }
   }

   public SchemaAttributeGroup findAttributeGroup(QName name) {
      SchemaAttributeGroup.Ref ref = this.findAttributeGroupRef(name);
      if (ref == null) {
         return null;
      } else {
         SchemaAttributeGroup result = ref.get();

         assert result != null;

         return result;
      }
   }

   public SchemaGlobalElement findElement(QName name) {
      SchemaGlobalElement.Ref ref = this.findElementRef(name);
      if (ref == null) {
         return null;
      } else {
         SchemaGlobalElement result = ref.get();

         assert result != null;

         return result;
      }
   }

   public SchemaGlobalAttribute findAttribute(QName name) {
      SchemaGlobalAttribute.Ref ref = this.findAttributeRef(name);
      if (ref == null) {
         return null;
      } else {
         SchemaGlobalAttribute result = ref.get();

         assert result != null;

         return result;
      }
   }

   public XmlObject newInstance(SchemaType type, XmlOptions options) {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      return hook != null ? hook.newInstance(this, type, options) : Locale.newInstance(this, type, options);
   }

   public XmlObject parse(String xmlText, SchemaType type, XmlOptions options) throws XmlException {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      return hook != null ? hook.parse(this, (String)xmlText, type, options) : Locale.parseToXmlObject(this, (String)xmlText, type, options);
   }

   /** @deprecated */
   public XmlObject parse(XMLInputStream xis, SchemaType type, XmlOptions options) throws XmlException, XMLStreamException {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      return hook != null ? hook.parse(this, (XMLInputStream)xis, type, options) : Locale.parseToXmlObject(this, (XMLInputStream)xis, type, options);
   }

   public XmlObject parse(XMLStreamReader xsr, SchemaType type, XmlOptions options) throws XmlException {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      return hook != null ? hook.parse(this, (XMLStreamReader)xsr, type, options) : Locale.parseToXmlObject(this, (XMLStreamReader)xsr, type, options);
   }

   public XmlObject parse(File file, SchemaType type, XmlOptions options) throws XmlException, IOException {
      if (options == null) {
         options = new XmlOptions();
         options.put("DOCUMENT_SOURCE_NAME", file.toURI().normalize().toString());
      } else if (!options.hasOption("DOCUMENT_SOURCE_NAME")) {
         options = new XmlOptions(options);
         options.put("DOCUMENT_SOURCE_NAME", file.toURI().normalize().toString());
      }

      InputStream fis = new FileInputStream(file);

      XmlObject var5;
      try {
         var5 = this.parse((InputStream)fis, type, options);
      } finally {
         fis.close();
      }

      return var5;
   }

   public XmlObject parse(URL url, SchemaType type, XmlOptions options) throws XmlException, IOException {
      if (options == null) {
         options = new XmlOptions();
         options.put("DOCUMENT_SOURCE_NAME", url.toString());
      } else if (!options.hasOption("DOCUMENT_SOURCE_NAME")) {
         options = new XmlOptions(options);
         options.put("DOCUMENT_SOURCE_NAME", url.toString());
      }

      URLConnection conn = null;
      InputStream stream = null;

      XmlObject var14;
      try {
         boolean redirected = false;
         int count = 0;

         do {
            conn = url.openConnection();
            conn.addRequestProperty("User-Agent", USER_AGENT);
            conn.addRequestProperty("Accept", "application/xml, text/xml, */*");
            if (conn instanceof HttpURLConnection) {
               HttpURLConnection httpcon = (HttpURLConnection)conn;
               int code = httpcon.getResponseCode();
               redirected = code == 301 || code == 302;
               if (redirected && count > 5) {
                  redirected = false;
               }

               if (redirected) {
                  String newLocation = httpcon.getHeaderField("Location");
                  if (newLocation == null) {
                     redirected = false;
                  } else {
                     url = new URL(newLocation);
                     ++count;
                  }
               }
            }
         } while(redirected);

         stream = conn.getInputStream();
         var14 = this.parse(stream, type, options);
      } finally {
         if (stream != null) {
            stream.close();
         }

      }

      return var14;
   }

   public XmlObject parse(InputStream jiois, SchemaType type, XmlOptions options) throws XmlException, IOException {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      DigestInputStream digestStream = null;
      if (options != null && options.hasOption("LOAD_MESSAGE_DIGEST")) {
         label23: {
            MessageDigest sha;
            try {
               sha = MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException var8) {
               break label23;
            }

            digestStream = new DigestInputStream((InputStream)jiois, sha);
            jiois = digestStream;
         }
      }

      if (hook != null) {
         return hook.parse(this, (InputStream)jiois, type, options);
      } else {
         XmlObject result = Locale.parseToXmlObject(this, (InputStream)jiois, type, options);
         if (digestStream != null) {
            result.documentProperties().setMessageDigest(digestStream.getMessageDigest().digest());
         }

         return result;
      }
   }

   public XmlObject parse(Reader jior, SchemaType type, XmlOptions options) throws XmlException, IOException {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      return hook != null ? hook.parse(this, (Reader)jior, type, options) : Locale.parseToXmlObject(this, (Reader)jior, type, options);
   }

   public XmlObject parse(Node node, SchemaType type, XmlOptions options) throws XmlException {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      return hook != null ? hook.parse(this, (Node)node, type, options) : Locale.parseToXmlObject(this, (Node)node, type, options);
   }

   public XmlSaxHandler newXmlSaxHandler(SchemaType type, XmlOptions options) {
      XmlFactoryHook hook = XmlFactoryHook.ThreadContext.getHook();
      return hook != null ? hook.newXmlSaxHandler(this, type, options) : Locale.newSaxHandler(this, type, options);
   }

   public DOMImplementation newDomImplementation(XmlOptions options) {
      return Locale.newDomImplementation(this, options);
   }

   /** @deprecated */
   public XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, SchemaType type, XmlOptions options) throws XmlException, XMLStreamException {
      return new ValidatingXMLInputStream(xis, this, type, options);
   }

   public String compilePath(String pathExpr) {
      return this.compilePath(pathExpr, (XmlOptions)null);
   }

   public String compilePath(String pathExpr, XmlOptions options) {
      return doCompilePath(pathExpr, options);
   }

   public String compileQuery(String queryExpr) {
      return this.compileQuery(queryExpr, (XmlOptions)null);
   }

   public String compileQuery(String queryExpr, XmlOptions options) {
      return doCompileQuery(queryExpr, options);
   }

   public SchemaType typeForSignature(String signature) {
      int end = signature.indexOf(64);
      String uri;
      if (end < 0) {
         uri = "";
         end = signature.length();
      } else {
         uri = signature.substring(end + 1);
      }

      List parts = new ArrayList();

      int i;
      int offset;
      for(int index = 0; index < end; index = offset + 1) {
         i = signature.indexOf(58, index);
         int nextd = signature.indexOf(124, index);
         offset = i < 0 ? nextd : (nextd < 0 ? i : (i < nextd ? i : nextd));
         if (offset < 0 || offset > end) {
            offset = end;
         }

         String part = signature.substring(index, offset);
         parts.add(part);
      }

      SchemaType curType = null;

      label176:
      for(i = parts.size() - 1; i >= 0; --i) {
         String part = (String)parts.get(i);
         if (part.length() < 1) {
            throw new IllegalArgumentException();
         }

         offset = part.length() >= 2 && part.charAt(1) == '=' ? 2 : 1;
         int j;
         SchemaField field;
         SchemaType[] subTypes;
         String localName;
         switch (part.charAt(0)) {
            case 'A':
            case 'Q':
               if (curType != null) {
                  if (curType.isSimpleType()) {
                     return null;
                  }

                  subTypes = curType.getAnonymousTypes();
                  localName = part.substring(offset);

                  for(j = 0; j < subTypes.length; ++j) {
                     field = subTypes[j].getContainerField();
                     if (field != null && field.isAttribute() && field.getName().getLocalPart().equals(localName)) {
                        curType = subTypes[j];
                        continue label176;
                     }
                  }

                  return null;
               } else {
                  SchemaGlobalAttribute attr = this.findAttribute(QNameHelper.forLNS(part.substring(offset), uri));
                  if (attr == null) {
                     return null;
                  }

                  curType = attr.getType();
                  break;
               }
            case 'B':
               if (curType == null) {
                  throw new IllegalArgumentException();
               }

               if (curType.getSimpleVariety() != 1) {
                  return null;
               }

               subTypes = curType.getAnonymousTypes();
               if (subTypes.length != 1) {
                  return null;
               }

               curType = subTypes[0];
               break;
            case 'C':
            case 'R':
               if (curType != null) {
                  throw new IllegalArgumentException();
               }

               curType = this.findAttributeType(QNameHelper.forLNS(part.substring(offset), uri));
               if (curType == null) {
                  return null;
               }
               break;
            case 'D':
               if (curType != null) {
                  throw new IllegalArgumentException();
               }

               curType = this.findDocumentType(QNameHelper.forLNS(part.substring(offset), uri));
               if (curType == null) {
                  return null;
               }
               break;
            case 'E':
            case 'U':
               if (curType != null) {
                  if (curType.getContentType() < 3) {
                     return null;
                  }

                  subTypes = curType.getAnonymousTypes();
                  localName = part.substring(offset);

                  for(j = 0; j < subTypes.length; ++j) {
                     field = subTypes[j].getContainerField();
                     if (field != null && !field.isAttribute() && field.getName().getLocalPart().equals(localName)) {
                        curType = subTypes[j];
                        continue label176;
                     }
                  }

                  return null;
               } else {
                  SchemaGlobalElement elt = this.findElement(QNameHelper.forLNS(part.substring(offset), uri));
                  if (elt == null) {
                     return null;
                  }

                  curType = elt.getType();
                  break;
               }
            case 'F':
            case 'G':
            case 'H':
            case 'J':
            case 'K':
            case 'L':
            case 'N':
            case 'O':
            case 'P':
            case 'S':
            default:
               throw new IllegalArgumentException();
            case 'I':
               if (curType == null) {
                  throw new IllegalArgumentException();
               }

               if (curType.getSimpleVariety() != 3) {
                  return null;
               }

               subTypes = curType.getAnonymousTypes();
               if (subTypes.length != 1) {
                  return null;
               }

               curType = subTypes[0];
               break;
            case 'M':
               if (curType == null) {
                  throw new IllegalArgumentException();
               }

               int index;
               try {
                  index = Integer.parseInt(part.substring(offset));
               } catch (Exception var13) {
                  throw new IllegalArgumentException();
               }

               if (curType.getSimpleVariety() != 2) {
                  return null;
               }

               SchemaType[] subTypes = curType.getAnonymousTypes();
               if (subTypes.length <= index) {
                  return null;
               }

               curType = subTypes[index];
               break;
            case 'T':
               if (curType != null) {
                  throw new IllegalArgumentException();
               }

               curType = this.findType(QNameHelper.forLNS(part.substring(offset), uri));
               if (curType == null) {
                  return null;
               }
         }
      }

      return curType;
   }
}
