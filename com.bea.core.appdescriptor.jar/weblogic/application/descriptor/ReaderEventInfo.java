package weblogic.application.descriptor;

import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;

public class ReaderEventInfo {
   private int eventType;
   private Location location;
   private String elementName;
   private String prefix;
   private char[] characters;
   private Attributes attributes;
   private String charEncodingScheme;
   private String inputEncoding;
   Namespaces namespaces;
   private StringBuffer comments;
   private boolean keyCharacters;
   private boolean keyOverride;
   private char[] keyOverrideCharacters;

   public ReaderEventInfo(int et, Location l) {
      this.eventType = et;
      this.location = new EventLocation(l);
   }

   public ReaderEventInfo(int et, String elementName, Location l) {
      this(et, l);
      this.elementName = elementName;
   }

   public String getElementName() {
      return this.elementName;
   }

   public void setElementName(String e) {
      this.elementName = e;
   }

   public char[] getCharacters() {
      return this.characters;
   }

   public void setCharacters(char[] c) {
      if (c != null) {
         this.characters = new char[c.length];
         System.arraycopy(c, 0, this.characters, 0, c.length);
      }
   }

   void appendCharacters(char[] c) {
      if (c != null && (c.length != 0 || this.characters == null)) {
         if (this.characters != null) {
            char[] newCharacters = new char[this.characters.length + c.length];
            System.arraycopy(this.characters, 0, newCharacters, 0, this.characters.length);
            System.arraycopy(c, 0, newCharacters, this.characters.length, c.length);
            this.characters = newCharacters;
         } else if (c.length == 0) {
            this.characters = new char[0];
         } else {
            this.setCharacters(c);
         }

      }
   }

   public boolean hasComment() {
      return this.comments != null;
   }

   public String getComments() {
      return this.hasComment() ? this.comments.toString() : null;
   }

   public void setComments(String text) {
      if (this.comments == null) {
         this.comments = new StringBuffer();
      }

      this.comments.append(text);
   }

   public String getCharactersAsString() {
      return this.characters != null ? new String(this.getCharacters()) : "";
   }

   public Location getLocation() {
      return this.location;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setPrefix(String p) {
      this.prefix = p;
   }

   public int getEventType() {
      return this.eventType;
   }

   public int getAttributeCount() {
      return this.getAttributes().getAttributeCount();
   }

   Attributes getAttributes() {
      if (this.attributes == null) {
         this.attributes = new Attributes();
      }

      return this.attributes;
   }

   public void removeNillableAttribute() {
      if (this.attributes != null) {
         this.attributes.removeNillable();
      }

   }

   public void setAttributeCount(int c) {
      this.getAttributes().setAttributeCount(c);
   }

   public String getAttributeLocalName(int index) {
      return this.getAttributes().getAttributeLocalName(index);
   }

   public void setAttributeLocalName(String s, int index) {
      this.getAttributes().setAttributeLocalName(s, index);
   }

   public String getAttributeNamespace(int index) {
      return this.getAttributes().getAttributeNamespace(index);
   }

   public void setAttributeNamespace(String s, int index) {
      this.getAttributes().setAttributeNamespace(s, index);
   }

   public String getAttributePrefix(int index) {
      return this.getAttributes().getAttributePrefix(index);
   }

   public void setAttributePrefix(String prefix, int index) {
      this.getAttributes().setAttributePrefix(prefix, index);
   }

   public String getAttributeType(int index) {
      return this.getAttributes().getAttributeType(index);
   }

   public boolean isAttributeSpecified(int index) {
      return this.getAttributes().isAttributeSpecified(index);
   }

   public String getAttributeValue(int index) {
      return this.getAttributes().getAttributeValue(index);
   }

   public void setAttributeValue(String s, int index) {
      this.getAttributes().setAttributeValue(s, index);
   }

   public String getAttributeValue(String namespace, String name) {
      return this.getAttributes().getAttributeValue(namespace, name);
   }

   public void setAttributeValue(String val, String namespace, String name) {
      this.getAttributes().setAttributeValue(val, namespace, name);
   }

   public QName getAttributeName(int index) {
      return this.getAttributes().getAttributeName(index);
   }

   public String getCharacterEncodingScheme() {
      return this.charEncodingScheme;
   }

   public void setCharacterEncodingScheme(String s) {
      this.charEncodingScheme = s;
   }

   public int getNamespaceCount() {
      return this.getNamespaces().getNamespaceCount();
   }

   public String getNamespacePrefix(int index) {
      return this.getNamespaces().getNamespacePrefix(index);
   }

   public String getNamespaceURI(int index) {
      return this.getNamespaces().getNamespaceURI(index);
   }

   public boolean hasNamespaceURI(String uri) {
      return this.getNamespaces().hasNamespaceURI(uri);
   }

   public void setNamespaceCount(int c) {
      this.getNamespaces().setNamespaceCount(c);
   }

   Namespaces getNamespaces() {
      if (this.namespaces == null) {
         this.namespaces = new Namespaces();
      }

      return this.namespaces;
   }

   public void clearNamespaces() {
      this.namespaces = new Namespaces();
   }

   public void setNamespaceURI(String prefix, String uri) {
      if (uri != null) {
         this.getNamespaces().setNamespaceURI(prefix, uri);
      }
   }

   public javax.xml.namespace.NamespaceContext getNamespaceContext() {
      return new NamespaceContext(this.getNamespaces());
   }

   public String getEncoding() {
      return this.inputEncoding;
   }

   public void setEncoding(String s) {
      this.inputEncoding = s;
   }

   public boolean isKeyCharacters() {
      return this.keyCharacters;
   }

   public void setKeyCharacters(boolean isKey) {
      this.keyCharacters = isKey;
   }

   public boolean isKeyOverride() {
      return this.keyOverride;
   }

   public void setKeyOverride(boolean isKeyOverride) {
      this.keyOverride = isKeyOverride;
   }

   public char[] getKeyOverrideCharacters() {
      return this.keyOverrideCharacters;
   }

   public void setKeyOverrideCharacters(char[] c) {
      if (c != null) {
         this.keyOverrideCharacters = new char[c.length];
         System.arraycopy(c, 0, this.keyOverrideCharacters, 0, c.length);
      }
   }

   private class EventLocation implements Location {
      int lineNumber = -1;
      int columnNumber = -1;
      int charOffset = -1;
      String publicId;
      String systemId;

      EventLocation(Location l) {
         if (l != null) {
            this.lineNumber = l.getLineNumber();
            this.columnNumber = l.getColumnNumber();
            this.charOffset = l.getCharacterOffset();
            this.publicId = l.getPublicId();
            this.systemId = l.getSystemId();
         }

      }

      public int getLineNumber() {
         return this.lineNumber;
      }

      public int getColumnNumber() {
         return this.columnNumber;
      }

      public int getCharacterOffset() {
         return this.charOffset;
      }

      public String getPublicId() {
         return this.publicId;
      }

      public String getSystemId() {
         return this.systemId;
      }
   }

   class NamespaceContext implements javax.xml.namespace.NamespaceContext {
      Namespaces namespaces;

      NamespaceContext(Namespaces namespaces) {
         this.namespaces = namespaces;
      }

      public String getNamespaceURI(String prefix) {
         if (prefix == null) {
            throw new IllegalArgumentException("The prefix may not be null");
         } else {
            return this.namespaces.getNamespaceURI(prefix);
         }
      }

      public String getPrefix(String namespaceURI) {
         if (namespaceURI == null) {
            throw new IllegalArgumentException("The uri may not be null.");
         } else {
            for(int i = 0; i < this.namespaces.getNamespaceCount(); ++i) {
               if (namespaceURI.equals(this.namespaces.getNamespaceURI(i))) {
                  return this.namespaces.getNamespacePrefix(i);
               }
            }

            return null;
         }
      }

      public Iterator getPrefixes(String namespaceURI) {
         ArrayList list = new ArrayList();

         for(int i = 0; i < this.namespaces.getNamespaceCount(); ++i) {
            if (namespaceURI.equals(this.namespaces.getNamespaceURI(i))) {
               list.add(this.namespaces.getNamespacePrefix(i));
            }
         }

         return list.iterator();
      }
   }

   class Namespaces {
      private int namespaceCount;
      private String[] namespacePrefixes;
      private String[] namespaceValues;
      private static final String DEFAULT_NS = "";

      public void ensureSpaceAvail(int size) {
         int curSize = this.namespaceValues == null ? 0 : this.namespaceValues.length;
         if (size > curSize) {
            int newSize = size > 10 ? size * 2 : 10;
            if (this.namespaceValues == null) {
               this.namespaceValues = new String[newSize];
               this.namespacePrefixes = new String[newSize];
               return;
            }

            String[] s = new String[newSize];
            System.arraycopy(this.namespaceValues, 0, s, 0, curSize);
            this.namespaceValues = s;
            s = new String[newSize];
            System.arraycopy(this.namespacePrefixes, 0, s, 0, curSize);
            this.namespacePrefixes = s;
         }

      }

      public int getNamespaceCount() {
         return this.namespaceCount;
      }

      public String getNamespacePrefix(int index) {
         return this.namespaceCount == 0 ? null : this.namespacePrefixes[index];
      }

      public String getNamespaceURI(int index) {
         return this.namespaceCount == 0 ? null : this.namespaceValues[index];
      }

      public String getNamespaceURI() {
         return this.getNamespaceURI(0);
      }

      public String getNamespaceURI(String prefix) {
         int i;
         for(i = 0; i < this.namespaceCount; ++i) {
            if (this.namespacePrefixes[i] == prefix && this.namespaceValues[i] != null) {
               return this.namespaceValues[i];
            }

            if (this.namespacePrefixes[i] != null && prefix != null && prefix.equals(this.namespacePrefixes[i])) {
               return this.namespaceValues[i];
            }
         }

         for(i = 0; i < this.namespaceCount; ++i) {
            if ("".equals(prefix) && this.namespacePrefixes[i] == null) {
               return this.namespaceValues[i];
            }
         }

         return null;
      }

      public boolean hasNamespaceURI(String uri) {
         for(int i = 0; i < this.namespaceCount; ++i) {
            if (this.namespaceValues[i] != null && this.namespaceValues[i].equals(uri)) {
               return true;
            }
         }

         return false;
      }

      public void setNamespaceCount(int c) {
         if (c > 0) {
            this.ensureSpaceAvail(c);
         }

         if (this.namespaceCount < c) {
            this.namespaceCount = c;
         }

      }

      public void setNamespaceURI(String prefix, String uri) {
         if (uri != null && this.namespaceCount != 0) {
            int i;
            if (prefix != null) {
               for(i = 0; i < this.namespaceCount; ++i) {
                  if (prefix.equals(this.namespacePrefixes[i]) && uri.equals(this.namespaceValues[i])) {
                     return;
                  }
               }
            } else {
               for(i = 0; i < this.namespaceCount; ++i) {
                  if (uri.equals(this.namespaceValues[i])) {
                     return;
                  }
               }
            }

            for(i = 0; i < this.namespaceCount; ++i) {
               if (this.namespaceValues[i] == null) {
                  this.namespaceValues[i] = uri;
                  this.namespacePrefixes[i] = prefix;
                  return;
               }
            }

            this.setNamespaceCount(this.getNamespaceCount() + 1);
            this.namespaceValues[this.getNamespaceCount() - 1] = uri;
            this.namespacePrefixes[this.getNamespaceCount() - 1] = prefix;
         }
      }

      void copy(Namespaces parentNamespace) {
         if (parentNamespace.namespaceCount > 0) {
            this.setNamespaceCount(parentNamespace.namespaceCount);

            for(int i = 0; i < this.namespaceCount; ++i) {
               this.namespacePrefixes[i] = parentNamespace.namespacePrefixes[i];
               this.namespaceValues[i] = parentNamespace.namespaceValues[i];
            }
         }

      }
   }

   class Attributes {
      private int attributeCount;
      private String[] attributeNames;
      private String[] attributeValues;
      private String[] attributeUris;
      private String[] attributePrefixes;

      public void ensureSpaceAvail(int size) {
         int curSize = this.attributeValues == null ? 0 : this.attributeValues.length;
         if (size > curSize) {
            int newSize = size > 10 ? size * 2 : 10;
            if (this.attributeValues == null) {
               this.attributeNames = new String[newSize];
               this.attributeValues = new String[newSize];
               this.attributeUris = new String[newSize];
               this.attributePrefixes = new String[newSize];
               return;
            }

            String[] s = null;
            s = new String[newSize];
            System.arraycopy(this.attributeNames, 0, s, 0, curSize);
            this.attributeNames = s;
            s = new String[newSize];
            System.arraycopy(this.attributePrefixes, 0, s, 0, curSize);
            this.attributePrefixes = s;
            s = new String[newSize];
            System.arraycopy(this.attributeUris, 0, s, 0, curSize);
            this.attributeUris = s;
            s = new String[newSize];
            System.arraycopy(this.attributeValues, 0, s, 0, curSize);
            this.attributeValues = s;
         }

      }

      public int getAttributeCount() {
         return this.attributeCount;
      }

      public void setAttributeCount(int c) {
         this.attributeCount = c;
         if (c > 0) {
            this.ensureSpaceAvail(c);
         }

      }

      public String getAttributeLocalName(int index) {
         if (index >= 0 && index < this.attributeCount) {
            return this.attributeNames[index];
         } else {
            throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
         }
      }

      public void setAttributeLocalName(String s, int index) {
         this.attributeNames[index] = s;
      }

      public String getAttributeNamespace(int index) {
         if (index >= 0 && index < this.attributeCount) {
            return this.attributeUris[index];
         } else {
            throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
         }
      }

      public void setAttributeNamespace(String s, int index) {
         this.attributeUris[index] = s;
      }

      public String getAttributePrefix(int index) {
         if (index >= 0 && index < this.attributeCount) {
            return this.attributePrefixes[index];
         } else {
            throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
         }
      }

      public void setAttributePrefix(String prefix, int index) {
         this.attributePrefixes[index] = prefix;
      }

      public String getAttributeType(int index) {
         return "CDATA";
      }

      public boolean isAttributeSpecified(int index) {
         return false;
      }

      public String getAttributeValue(int index) {
         if (index >= 0 && index < this.attributeCount) {
            return this.attributeValues[index];
         } else {
            throw new IndexOutOfBoundsException("attribute position must be 0.." + (this.attributeCount - 1) + " and not " + index);
         }
      }

      public void setAttributeValue(String s, int index) {
         this.attributeValues[index] = s;
      }

      public String getAttributeValue(String namespace, String name) {
         int i;
         if (namespace != null) {
            for(i = 0; i < this.attributeCount; ++i) {
               if (namespace.equals(this.attributeUris[i]) && name.equals(this.attributeNames[i])) {
                  return this.attributeValues[i];
               }
            }
         } else {
            for(i = 0; i < this.attributeCount; ++i) {
               if (name.equals(this.attributeNames[i])) {
                  return this.attributeValues[i];
               }
            }
         }

         return null;
      }

      public void setAttributeValue(String val, String namespace, String name) {
         if (val != null && this.attributeCount != 0) {
            int i;
            if (namespace != null) {
               for(i = 0; i < this.attributeCount; ++i) {
                  if (namespace.equals(this.attributeUris[i]) && name.equals(this.attributeNames[i]) && val.equals(this.attributeValues[i])) {
                     return;
                  }
               }
            } else {
               for(i = 0; i < this.attributeCount; ++i) {
                  if (name.equals(this.attributeNames[i]) && val.equals(this.attributeValues[i])) {
                     return;
                  }
               }
            }

            for(i = 0; i < this.attributeCount; ++i) {
               if (this.attributeValues[i] == null) {
                  if (namespace != null) {
                     this.attributeUris[i] = namespace;
                  }

                  this.attributeNames[i] = name;
                  this.attributeValues[i] = val;
                  return;
               }
            }

            this.ensureSpaceAvail(this.attributeCount++);
            if (namespace != null) {
               this.attributeUris[this.attributeCount - 1] = namespace;
            }

            this.attributeNames[this.attributeCount - 1] = name;
            this.attributeValues[this.attributeCount - 1] = val;
         }
      }

      public QName getAttributeName(int index) {
         return new QName(this.getAttributeNamespace(index), this.getAttributeLocalName(index), this.getAttributePrefix(index));
      }

      public void removeNillable() {
         if (this.attributeNames != null && this.attributeValues != null && this.attributeUris != null && this.attributePrefixes != null) {
            for(int i = 0; i < this.attributeNames.length; ++i) {
               String val = this.attributeNames[i];
               if ("nil".equals(val) && "true".equals(this.attributeValues[i]) && "http://www.w3.org/2001/XMLSchema-instance".equals(this.attributeUris[i])) {
                  --this.attributeCount;
                  String[] temp = new String[this.attributeNames.length];
                  System.arraycopy(this.attributeNames, 0, temp, 0, i);
                  System.arraycopy(this.attributeNames, i + 1, temp, i + 1, this.attributeNames.length - (i + 1));
                  this.attributeNames = temp;
                  temp = new String[this.attributeValues.length];
                  System.arraycopy(this.attributeValues, 0, temp, 0, i);
                  System.arraycopy(this.attributeValues, i + 1, temp, i + 1, this.attributeValues.length - (i + 1));
                  this.attributeValues = temp;
                  temp = new String[this.attributeUris.length];
                  System.arraycopy(this.attributeUris, 0, temp, 0, i);
                  System.arraycopy(this.attributeUris, i + 1, temp, i + 1, this.attributeUris.length - (i + 1));
                  this.attributeUris = temp;
                  temp = new String[this.attributePrefixes.length];
                  System.arraycopy(this.attributePrefixes, 0, temp, 0, i);
                  System.arraycopy(this.attributePrefixes, i + 1, temp, i + 1, this.attributePrefixes.length - (i + 1));
                  this.attributePrefixes = temp;
               }
            }

         }
      }
   }
}
