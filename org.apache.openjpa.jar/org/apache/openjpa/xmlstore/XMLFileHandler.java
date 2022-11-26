package org.apache.openjpa.xmlstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.lib.util.Base16Encoder;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.xml.XMLFactory;
import org.apache.openjpa.lib.xml.XMLWriter;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLFileHandler {
   private final XMLConfiguration _conf;

   public XMLFileHandler(XMLConfiguration conf) {
      this._conf = conf;
   }

   public Collection load(ClassMetaData meta) {
      File f = this.getFile(meta);
      if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(f)) && (Long)AccessController.doPrivileged(J2DoPrivHelper.lengthAction(f)) != 0L) {
         try {
            return this.read(f);
         } catch (OpenJPAException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new StoreException(var5);
         }
      } else {
         return Collections.EMPTY_SET;
      }
   }

   private Collection read(File f) throws Exception {
      SAXParser parser = XMLFactory.getSAXParser(false, false);
      ObjectDataHandler handler = new ObjectDataHandler(this._conf);
      parser.parse(f, handler);
      return handler.getExtent();
   }

   private File getFile(ClassMetaData meta) {
      File baseDir = new File(this._conf.getConnectionURL());
      return new File(baseDir, meta.getDescribedType().getName());
   }

   public void store(ClassMetaData meta, Collection datas) {
      if (meta.getPCSuperclass() != null) {
         throw new InternalException();
      } else {
         File f = this.getFile(meta);
         if (!(Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(f.getParentFile()))) {
            AccessController.doPrivileged(J2DoPrivHelper.mkdirsAction(f.getParentFile()));
         }

         FileWriter fw = null;

         try {
            fw = new FileWriter(f);
            this.write(datas, fw);
         } catch (OpenJPAException var14) {
            throw var14;
         } catch (Exception var15) {
            throw new StoreException(var15);
         } finally {
            if (fw != null) {
               try {
                  fw.close();
               } catch (IOException var13) {
               }
            }

         }

      }
   }

   private void write(Collection datas, FileWriter fw) throws Exception {
      Writer out = new XMLWriter(fw);
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      out.write("<extent>");
      Iterator itr = datas.iterator();

      while(itr.hasNext()) {
         ObjectData obj = (ObjectData)itr.next();
         ClassMetaData meta = obj.getMetaData();
         out.write("<object class=\"");
         out.write(meta.getDescribedType().getName());
         out.write("\" oid=\"");
         out.write(obj.getId().toString());
         out.write("\" version=\"");
         out.write(obj.getVersion().toString());
         out.write("\">");
         FieldMetaData[] fmds = meta.getFields();

         for(int i = 0; i < fmds.length; ++i) {
            int var10000 = fmds[i].getManagement();
            FieldMetaData var10001 = fmds[i];
            if (var10000 == 3) {
               out.write("<field name=\"");
               out.write(fmds[i].getName());
               out.write("\">");
               switch (fmds[i].getTypeCode()) {
                  case 11:
                  case 12:
                     Collection c = (Collection)obj.getField(i);
                     if (c != null) {
                        int elemType = fmds[i].getElement().getTypeCode();
                        Iterator ci = c.iterator();

                        while(ci.hasNext()) {
                           out.write("<element>");
                           this.writeDataValue(out, elemType, ci.next());
                           out.write("</element>");
                        }
                     }
                     break;
                  case 13:
                     Map m = (Map)obj.getField(i);
                     if (m != null) {
                        Collection entries = m.entrySet();
                        int keyType = fmds[i].getKey().getTypeCode();
                        int valueType = fmds[i].getElement().getTypeCode();
                        Iterator ei = entries.iterator();

                        while(ei.hasNext()) {
                           Map.Entry e = (Map.Entry)ei.next();
                           out.write("<key>");
                           this.writeDataValue(out, keyType, e.getKey());
                           out.write("</key>");
                           out.write("<value>");
                           this.writeDataValue(out, valueType, e.getValue());
                           out.write("</value>");
                        }
                     }
                     break;
                  default:
                     this.writeDataValue(out, fmds[i].getTypeCode(), obj.getField(i));
               }

               out.write("</field>");
            }
         }

         out.write("</object>");
      }

      out.write("</extent>");
   }

   public void writeDataValue(Writer out, int type, Object val) throws IOException {
      if (val == null) {
         out.write("null");
      } else {
         switch (type) {
            case 2:
            case 18:
               char c = (Character)val;
               out.write("'");
               if (c == 0) {
                  out.write("0x0");
               } else {
                  out.write(XMLFileHandler.XMLEncoder.encode(val.toString()));
               }

               out.write("'");
               break;
            case 8:
            case 29:
               if (!(val instanceof Serializable)) {
                  throw new UnsupportedException("Cannot store non-serializable, non-persistence-capable value: " + val);
               }

               ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
               ObjectOutputStream oos = new ObjectOutputStream(baos);
               oos.writeObject(val);
               oos.close();
               out.write(Base16Encoder.encode(baos.toByteArray()));
               break;
            case 9:
               out.write("\"");
               out.write(XMLFileHandler.XMLEncoder.encode(val.toString()));
               out.write("\"");
               break;
            case 15:
            case 27:
               out.write(val.getClass().getName());
               out.write(58);
               out.write(XMLFileHandler.XMLEncoder.encode(val.toString()));
               break;
            default:
               out.write(val.toString());
         }

      }
   }

   private static class XMLEncoder {
      public static String encode(String s) {
         StringBuffer buf = null;

         for(int i = 0; i < s.length(); ++i) {
            switch (s.charAt(i)) {
               case '&':
                  buf = initializeBuffer(buf, s, i);
                  buf.append("&amp;");
                  break;
               case '<':
                  buf = initializeBuffer(buf, s, i);
                  buf.append("&lt;");
                  break;
               case '>':
                  buf = initializeBuffer(buf, s, i);
                  buf.append("&gt;");
                  break;
               default:
                  if (buf != null) {
                     buf.append(s.charAt(i));
                  }
            }
         }

         if (buf != null) {
            return buf.toString();
         } else {
            return s;
         }
      }

      public static String decode(String s) {
         StringBuffer buf = null;

         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == 38 && s.length() > i + 3) {
               if ((s.charAt(i + 1) == 'l' || s.charAt(i + 1) == 'g') && s.charAt(i + 2) == 't' && s.charAt(i + 3) == ';') {
                  buf = initializeBuffer(buf, s, i);
                  c = s.charAt(i) == 'l' ? 60 : 62;
                  i += 3;
               } else if (s.length() > i + 4 && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 'm' && s.charAt(i + 3) == 'p' && s.charAt(i + 4) == ';') {
                  buf = initializeBuffer(buf, s, i);
                  c = 38;
                  i += 4;
               }
            }

            if (buf != null) {
               buf.append((char)c);
            }
         }

         if (buf != null) {
            return buf.toString();
         } else {
            return s;
         }
      }

      private static StringBuffer initializeBuffer(StringBuffer buf, String s, int i) {
         if (buf == null) {
            buf = new StringBuffer();
            if (i > 0) {
               buf.append(s.substring(0, i));
            }
         }

         return buf;
      }
   }

   private static class ObjectDataHandler extends DefaultHandler {
      private static final Class[] ARGS = new Class[]{String.class};
      private final XMLConfiguration _conf;
      private final Collection _extent = new ArrayList();
      private ObjectData _object;
      private FieldMetaData _fmd;
      private Object _fieldVal;
      private Object _keyVal;
      private StringBuffer _buf;

      public ObjectDataHandler(XMLConfiguration conf) {
         this._conf = conf;
      }

      public Collection getExtent() {
         return this._extent;
      }

      public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
         try {
            this.startElement(qName, attrs);
         } catch (RuntimeException var6) {
            throw var6;
         } catch (SAXException var7) {
            throw var7;
         } catch (Exception var8) {
            throw new SAXException(var8);
         }
      }

      private void startElement(String qName, Attributes attrs) throws Exception {
         switch (qName.charAt(0)) {
            case 'e':
            case 'k':
            case 'v':
               this._buf = new StringBuffer();
               break;
            case 'f':
               this._fmd = this._object.getMetaData().getField(attrs.getValue("name"));
               switch (this._fmd.getTypeCode()) {
                  case 11:
                  case 12:
                     this._fieldVal = new ArrayList();
                     return;
                  case 13:
                     this._fieldVal = new HashMap();
                     return;
                  default:
                     this._buf = new StringBuffer();
                     return;
               }
            case 'o':
               String type = attrs.getValue("class");
               ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData((Class)this.classForName(type), (ClassLoader)null, true);
               Object oid;
               if (meta.getIdentityType() == 1) {
                  oid = new Id(attrs.getValue("oid"), this._conf, (ClassLoader)null);
               } else {
                  oid = PCRegistry.newObjectId(meta.getDescribedType(), attrs.getValue("oid"));
               }

               this._object = new ObjectData(oid, meta);
               this._object.setVersion(new Long(attrs.getValue("version")));
         }

      }

      public void endElement(String uri, String localName, String qName) throws SAXException {
         try {
            this.endElement(qName);
         } catch (RuntimeException var5) {
            throw var5;
         } catch (SAXException var6) {
            throw var6;
         } catch (Exception var7) {
            throw new SAXException(var7);
         }
      }

      private void endElement(String qName) throws Exception {
         Object val;
         label15:
         switch (qName.charAt(0)) {
            case 'e':
               val = this.fromXMLString(this._fmd.getElement().getTypeCode(), this._fmd.getElement().getTypeMetaData(), this._buf.toString());
               ((Collection)this._fieldVal).add(val);
               break;
            case 'k':
               this._keyVal = this.fromXMLString(this._fmd.getKey().getTypeCode(), this._fmd.getKey().getTypeMetaData(), this._buf.toString());
               break;
            case 'o':
               this._extent.add(this._object);
            case 'f':
               switch (this._fmd.getTypeCode()) {
                  default:
                     this._fieldVal = this.fromXMLString(this._fmd.getTypeCode(), this._fmd.getTypeMetaData(), this._buf.toString());
                  case 11:
                  case 12:
                  case 13:
                     this._object.setField(this._fmd.getIndex(), this._fieldVal);
                     break label15;
               }
            case 'v':
               val = this.fromXMLString(this._fmd.getElement().getTypeCode(), this._fmd.getElement().getTypeMetaData(), this._buf.toString());
               Map map = (Map)this._fieldVal;
               map.put(this._keyVal, val);
         }

         this._buf = null;
      }

      public void characters(char[] ch, int start, int length) {
         if (this._buf != null) {
            this._buf.append(ch, start, length);
         }

      }

      public Object fromXMLString(int type, ClassMetaData rel, String str) throws Exception {
         str = str.trim();
         if (str.equals("null")) {
            return null;
         } else {
            switch (type) {
               case 0:
               case 16:
                  return Boolean.valueOf(str);
               case 1:
               case 17:
                  return new Byte(str);
               case 2:
               case 18:
                  str = str.substring(1, str.length() - 1);
                  if (str.equals("0x0")) {
                     return new Character('\u0000');
                  }

                  return new Character(XMLFileHandler.XMLEncoder.decode(str).charAt(0));
               case 3:
               case 19:
                  return new Double(str);
               case 4:
               case 20:
                  return new Float(str);
               case 5:
               case 21:
                  return new Integer(str);
               case 6:
               case 22:
                  return new Long(str);
               case 7:
               case 23:
                  return new Short(str);
               case 8:
               case 29:
                  byte[] bytes = Base16Encoder.decode(str);
                  ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                  ObjectInputStream ois = new ObjectInputStream(bais);
                  Object data = ois.readObject();
                  ois.close();
                  return data;
               case 9:
                  str = str.substring(1, str.length() - 1);
                  return XMLFileHandler.XMLEncoder.decode(str);
               case 10:
               case 24:
                  return new BigDecimal(str);
               case 11:
               case 12:
               case 13:
               case 28:
               default:
                  throw new InternalException();
               case 14:
                  return new Date(str);
               case 15:
               case 27:
                  int idx = str.indexOf(58);
                  Class idClass = this.classForName(str.substring(0, idx));
                  String idStr = XMLFileHandler.XMLEncoder.decode(str.substring(idx + 1));
                  Constructor cons = idClass.getConstructor(ARGS);
                  return cons.newInstance(idStr);
               case 25:
                  return new BigInteger(str);
               case 26:
                  int under1 = str.indexOf(95);
                  if (under1 == -1) {
                     return new Locale(str, "");
                  } else {
                     int under2 = str.indexOf(95, under1 + 1);
                     if (under2 == -1) {
                        return new Locale(str.substring(0, under1), str.substring(under1 + 1));
                     } else {
                        String lang = str.substring(0, under1);
                        String country = str.substring(under1 + 1, under2);
                        String variant = str.substring(under2 + 1);
                        return new Locale(lang, country, variant);
                     }
                  }
            }
         }
      }

      private Class classForName(String name) throws Exception {
         ClassLoader loader = this._conf.getClassResolverInstance().getClassLoader(this.getClass(), (ClassLoader)null);
         return Class.forName(name, true, loader);
      }
   }
}
