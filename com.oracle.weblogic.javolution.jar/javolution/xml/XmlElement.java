package javolution.xml;

import java.io.IOException;
import java.util.NoSuchElementException;
import javolution.lang.Text;
import javolution.lang.TextBuilder;
import javolution.lang.TypeFormat;
import javolution.util.FastComparator;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastTable;
import javolution.xml.pull.XmlPullParser;
import javolution.xml.pull.XmlPullParserException;
import javolution.xml.pull.XmlPullParserImpl;
import javolution.xml.sax.Attributes;
import javolution.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public final class XmlElement {
   ContentHandler _formatHandler;
   final XmlPullParserImpl _parser;
   private Object _object;
   private Class _objectClass;
   private boolean _isParserAtNext;
   private boolean _isClosure;
   final FormatAttributes _formatAttributes;
   private final TextBuilder _textBuilder = new TextBuilder();
   private CharSequence _elemName;
   final FastTable _packagePrefixes = new FastTable();
   boolean _areReferencesEnabled;
   boolean _expandReferences = false;
   boolean _isClassIdentifierEnabled;
   private final FastMap _idToObject;
   private final FastMap _objectToId;
   private final FastTable _formatStack;
   private final FastList _formatContent = new FastList() {
      public void addLast(Object var1) {
         XmlElement.this.add(var1);
      }
   };
   private final FastTable _parseContents;
   private int _parseContentIndex;

   XmlElement(XmlPullParserImpl var1) {
      if (var1 != null) {
         this._parser = var1;
         this._parseContents = new FastTable();
         this._formatAttributes = null;
         this._idToObject = new FastMap();
         this._objectToId = null;
         this._formatStack = null;
      } else {
         this._parser = null;
         this._parseContents = null;
         this._formatAttributes = new FormatAttributes();
         this._idToObject = null;
         this._objectToId = (new FastMap()).setKeyComparator(FastComparator.IDENTITY);
         this._formatStack = (FastTable)(new FastTable()).setValueComparator(FastComparator.IDENTITY);
      }

   }

   public Object object() throws XmlException {
      if (this._object == null) {
         if (this._objectClass == null) {
            throw new XmlException("Object type unknown, cannot use reflection");
         }

         try {
            this._object = this._objectClass.newInstance();
         } catch (IllegalAccessException var2) {
            throw new XmlException(this._objectClass + " default constructor inaccessible");
         } catch (InstantiationException var3) {
            throw new XmlException(this._objectClass + " default constructor throws an exception");
         }
      }

      return this._object;
   }

   public Class objectClass() {
      return this._objectClass;
   }

   public ContentHandler formatter() {
      return this._formatHandler;
   }

   public void add(Object var1) {
      try {
         if (var1 == null) {
            var1 = XmlFormat.NULL;
         }

         if (this._elemName != null) {
            this.flushStart();
         }

         if (var1 instanceof CharacterData) {
            CharacterData var9 = (CharacterData)var1;
            this._formatHandler.characters(var9.chars(), var9.offset(), var9.length());
         } else {
            Class var2 = this._objectClass = var1.getClass();
            XmlFormat var3 = XmlFormat.getInstance(var2);
            String var4 = var3.defaultName();
            if (var4 != null) {
               this._objectClass = var1.getClass();
               String var8 = XmlFormat.aliasFor(this._objectClass);
               if (this._isClassIdentifierEnabled) {
                  this._formatAttributes.addAttribute("j:class", var8 != null ? toCharSeq(var8) : toCharSeq(this._objectClass.getName()));
               }

               CharSequence var6 = this._elemName = toCharSeq(var4);
               this.format(var1, var3);
               if (this._elemName != null) {
                  this.flushStart();
               }

               this._formatHandler.endElement(Text.EMPTY, var6, var6);
            } else {
               CharSequence var5 = this._elemName = this.qNameFor(var2);
               this.format(var1, var3);
               if (this._elemName != null) {
                  this.flushStart();
               }

               if (var5 == this._textBuilder) {
                  var5 = this.qNameFor(var2);
               }

               this._formatHandler.endElement(Text.EMPTY, var5, var5);
            }
         }
      } catch (SAXException var7) {
         throw new XmlException(var7);
      }
   }

   public void add(Object var1, String var2) {
      if (var1 != null) {
         try {
            if (this._elemName != null) {
               this.flushStart();
            }

            this._objectClass = var1.getClass();
            String var3 = XmlFormat.aliasFor(this._objectClass);
            if (this._isClassIdentifierEnabled) {
               this._formatAttributes.addAttribute("j:class", var3 != null ? toCharSeq(var3) : toCharSeq(this._objectClass.getName()));
            }

            CharSequence var4 = this._elemName = toCharSeq(var2);
            this.format(var1, XmlFormat.getInstance(this._objectClass));
            if (this._elemName != null) {
               this.flushStart();
            }

            this._formatHandler.endElement(Text.EMPTY, var4, var4);
         } catch (SAXException var5) {
            throw new XmlException(var5);
         }
      }
   }

   public void add(Object var1, String var2, Class var3) {
      if (var1 != null) {
         try {
            if (this._elemName != null) {
               this.flushStart();
            }

            CharSequence var4 = this._elemName = toCharSeq(var2);
            this.format(var1, XmlFormat.getInstance(var3));
            if (this._elemName != null) {
               this.flushStart();
            }

            this._formatHandler.endElement(Text.EMPTY, var4, var4);
         } catch (SAXException var5) {
            throw new XmlException(var5);
         }
      }
   }

   public TextBuilder newAttribute(String var1) {
      if (this._formatAttributes == null || this._elemName == null) {
         this.attributeSettingError();
      }

      return this._formatAttributes.newAttribute(var1);
   }

   public void setAttribute(String var1, CharSequence var2) {
      if (this._formatAttributes == null || this._elemName == null) {
         this.attributeSettingError();
      }

      if (var2 != null) {
         this._formatAttributes.addAttribute(var1, var2);
      }
   }

   public void setAttribute(String var1, String var2) {
      if (this._formatAttributes == null || this._elemName == null) {
         this.attributeSettingError();
      }

      if (var2 != null) {
         CharSequence var3 = toCharSeq(var2);
         this._formatAttributes.addAttribute(var1, var3);
      }
   }

   public void setAttribute(String var1, boolean var2) {
      this.newAttribute(var1).append(var2);
   }

   public void setAttribute(String var1, int var2) {
      this.newAttribute(var1).append(var2);
   }

   public void setAttribute(String var1, long var2) {
      this.newAttribute(var1).append(var2);
   }

   public void setAttribute(String var1, float var2) {
      this.newAttribute(var1).append(var2);
   }

   public void setAttribute(String var1, double var2) {
      this.newAttribute(var1).append(var2);
   }

   public void setAttribute(String var1, Boolean var2) {
      if (var2 != null) {
         this.newAttribute(var1).append(var2);
      }
   }

   public void setAttribute(String var1, Byte var2) {
      if (var2 != null) {
         this.newAttribute(var1).append((int)var2);
      }
   }

   public void setAttribute(String var1, Short var2) {
      if (var2 != null) {
         this.newAttribute(var1).append((int)var2);
      }
   }

   public void setAttribute(String var1, Integer var2) {
      if (var2 != null) {
         this.newAttribute(var1).append(var2);
      }
   }

   public void setAttribute(String var1, Long var2) {
      if (var2 != null) {
         this.newAttribute(var1).append(var2);
      }
   }

   public void setAttribute(String var1, Float var2) {
      if (var2 != null) {
         this.newAttribute(var1).append(var2);
      }
   }

   public void setAttribute(String var1, Double var2) {
      if (var2 != null) {
         this.newAttribute(var1).append(var2);
      }
   }

   public void removeAttribute(String var1) {
      if (this._formatAttributes == null || this._elemName == null) {
         this.attributeSettingError();
      }

      int var2 = this._formatAttributes.getIndex(var1);
      if (var2 >= 0) {
         this._formatAttributes.remove(var2);
      }

   }

   public XmlPullParser parser() {
      return this._parser;
   }

   public boolean hasNext() {
      if (!this._isParserAtNext) {
         this.nextToken();
         this._isParserAtNext = true;
      }

      return !this._isClosure;
   }

   public Object getNext() {
      try {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this._isParserAtNext = false;
            if (this._parser.getEventType() != 2) {
               CharacterData var5 = CharacterData.valueOf(this._parser.getText());
               return var5;
            } else {
               CharSequence var1 = this._parser.getSaxAttributes().getValue("j:class");
               if (var1 == null) {
                  CharSequence var2 = this._parser.getNamespace();
                  CharSequence var3 = this._parser.getName();
                  this._objectClass = var2.length() > 0 && !var2.equals(ObjectWriter.JAVOLUTION_URI) ? XmlFormat.classFor(var2, var3) : XmlFormat.classFor(var3);
               } else {
                  this._objectClass = XmlFormat.classFor(var1);
               }

               XmlFormat var6 = XmlFormat.getInstance(this._objectClass);
               ++this._parseContentIndex;
               Object var7 = this.parse(var6);
               --this._parseContentIndex;
               if (this.hasNext()) {
                  this.incompleteReadError();
               }

               this._isParserAtNext = false;
               return var7;
            }
         }
      } catch (XmlPullParserException var4) {
         throw new XmlException(var4);
      }
   }

   public Object get(String var1) {
      try {
         if (this.hasNext() && this._parser.getEventType() == 2 && this._parser.getQName().equals(var1)) {
            this._isParserAtNext = false;
            CharSequence var2 = this._parser.getSaxAttributes().getValue("j:class");
            if (var2 == null) {
               throw new XmlException("\"j:class\" attribute missing");
            } else {
               this._objectClass = XmlFormat.classFor(var2);
               XmlFormat var3 = XmlFormat.getInstance(this._objectClass);
               ++this._parseContentIndex;
               Object var4 = this.parse(var3);
               --this._parseContentIndex;
               if (this.hasNext()) {
                  this.incompleteReadError();
               }

               this._isParserAtNext = false;
               return var4;
            }
         } else {
            return null;
         }
      } catch (XmlPullParserException var5) {
         throw new XmlException(var5);
      }
   }

   public Object get(String var1, Class var2) {
      try {
         if (this.hasNext() && this._parser.getEventType() == 2 && this._parser.getQName().equals(var1)) {
            this._isParserAtNext = false;
            this._objectClass = var2;
            XmlFormat var3 = XmlFormat.getInstance(var2);
            ++this._parseContentIndex;
            Object var4 = this.parse(var3);
            --this._parseContentIndex;
            if (this.hasNext()) {
               this.incompleteReadError();
            }

            this._isParserAtNext = false;
            return var4;
         } else {
            return null;
         }
      } catch (XmlPullParserException var5) {
         throw new XmlException(var5);
      }
   }

   public Attributes getAttributes() {
      if (this._formatAttributes != null) {
         return this._formatAttributes;
      } else if (this._isParserAtNext) {
         throw new XmlException("Attributes should be read before any content");
      } else {
         return this._parser.getSaxAttributes();
      }
   }

   public CharSequence getAttribute(String var1) {
      return this.getAttributes().getValue(var1);
   }

   public boolean isAttribute(String var1) {
      return this.getAttributes().getIndex(var1) >= 0;
   }

   public CharSequence getAttribute(String var1, CharSequence var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? var3 : var2;
   }

   public String getAttribute(String var1, String var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? var3.toString() : var2;
   }

   public boolean getAttribute(String var1, boolean var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? TypeFormat.parseBoolean(var3) : var2;
   }

   public int getAttribute(String var1, int var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? TypeFormat.parseInt(var3) : var2;
   }

   public long getAttribute(String var1, long var2) {
      CharSequence var4 = this.getAttributes().getValue(var1);
      return var4 != null ? TypeFormat.parseLong(var4) : var2;
   }

   public float getAttribute(String var1, float var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? (float)TypeFormat.parseDouble(var3) : var2;
   }

   public double getAttribute(String var1, double var2) {
      CharSequence var4 = this.getAttributes().getValue(var1);
      return var4 != null ? TypeFormat.parseDouble(var4) : var2;
   }

   public Boolean getAttribute(String var1, Boolean var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? new Boolean(TypeFormat.parseBoolean(var3)) : var2;
   }

   public Byte getAttribute(String var1, Byte var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? new Byte(TypeFormat.parseByte(var3)) : var2;
   }

   public Short getAttribute(String var1, Short var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? new Short(TypeFormat.parseShort(var3)) : var2;
   }

   public Integer getAttribute(String var1, Integer var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? new Integer(TypeFormat.parseInt(var3)) : var2;
   }

   public Long getAttribute(String var1, Long var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? new Long(TypeFormat.parseLong(var3)) : var2;
   }

   public Float getAttribute(String var1, Float var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? new Float(TypeFormat.parseFloat(var3)) : var2;
   }

   public Double getAttribute(String var1, Double var2) {
      CharSequence var3 = this.getAttributes().getValue(var1);
      return var3 != null ? new Double(TypeFormat.parseDouble(var3)) : var2;
   }

   void reset() {
      this._elemName = null;
      this._isClosure = false;
      this._object = null;
      this._objectClass = null;
      this._textBuilder.reset();
      if (this._parser != null) {
         this._parser.reset();

         for(int var1 = 0; var1 < this._parseContents.size(); ++var1) {
            FastList var2 = (FastList)this._parseContents.get(var1);
            var2.reset();
         }

         this._parseContentIndex = 0;
         this._isParserAtNext = false;
         this._idToObject.clear();
      } else {
         this._formatAttributes.reset();
         this._formatContent.reset();
         this._formatHandler = null;
         this._objectToId.clear();
         this._formatStack.clear();
      }

   }

   private void flushStart() throws SAXException {
      this._formatHandler.startElement(Text.EMPTY, this._elemName, this._elemName, this._formatAttributes);
      this._formatAttributes.reset();
      this._elemName = null;
   }

   private int nextToken() {
      try {
         while(true) {
            int var1 = this._parser.nextToken();
            switch (var1) {
               case 1:
               case 3:
                  this._isClosure = true;
                  return var1;
               case 2:
               case 5:
                  this._isClosure = false;
                  return var1;
               case 4:
                  if (!this._parser.isWhitespace()) {
                     this._isClosure = false;
                     return var1;
                  }
            }
         }
      } catch (XmlPullParserException var2) {
         throw new XmlException(var2);
      } catch (IOException var3) {
         throw new XmlException(var3);
      }
   }

   private void attributeSettingError() {
      if (this._formatAttributes == null) {
         throw new XmlException("Attributes cannot be set during parsing");
      } else if (this._elemName == null) {
         throw new XmlException("Attributes should be set before adding nested elements");
      } else {
         throw new XmlException();
      }
   }

   private void incompleteReadError() throws XmlPullParserException {
      if (this._parser.getEventType() == 2) {
         throw new XmlException("Incomplete read error (nested " + this._parser.getQName() + " has not been read)");
      } else {
         throw new XmlException("Incomplete read error (character datahas not been read)");
      }
   }

   private CharSequence qNameFor(Class var1) {
      String var2 = XmlFormat.aliasFor(var1);
      String var3 = var2 != null ? var2 : var1.getName();
      String var4 = null;
      String var5 = "";
      int var6 = 0;

      while(true) {
         String var7;
         String var8;
         do {
            do {
               do {
                  if (var6 >= this._packagePrefixes.size()) {
                     if (var4 != null) {
                        this._textBuilder.reset();
                        if (var4.length() > 0) {
                           this._textBuilder.append(var4).append(':');
                        }

                        if (var5.length() > 0) {
                           this._textBuilder.append(var3, var5.length() + 1, var3.length());
                        } else {
                           this._textBuilder.append(var3);
                        }

                        return this._textBuilder;
                     }

                     return toCharSeq(var3);
                  }

                  var7 = (String)this._packagePrefixes.get(var6++);
                  var8 = (String)this._packagePrefixes.get(var6++);
               } while(!var3.startsWith(var8));
            } while(var8.length() < var5.length());
         } while(var2 != null && !var7.equals("j"));

         var4 = var7;
         var5 = var8;
      }
   }

   private static CharSequence toCharSeq(Object var0) {
      return (CharSequence)(var0 instanceof CharSequence ? (CharSequence)var0 : Text.valueOf((Object)((String)var0)));
   }

   private Object parse(XmlFormat var1) {
      if (this._areReferencesEnabled && var1.identifier(false) != null) {
         CharSequence var5 = this.getAttribute(var1.identifier(true));
         Object var3;
         if (var5 == null) {
            var3 = this._object = var1.allocate(this);
            CharSequence var6 = this.getAttribute(var1.identifier(false));
            if (var6 != null) {
               if (var3 != null) {
                  this._idToObject.put(Text.valueOf((Object)var6), var3);
                  if (var1.parse(this) != var3) {
                     throw new XmlException("Parse should return xml.object() when allocate(xml) != null");
                  } else {
                     return var3;
                  }
               } else {
                  var3 = var1.parse(this);
                  this._idToObject.put(Text.valueOf((Object)var6), var3);
                  return var3;
               }
            } else {
               this._areReferencesEnabled = false;
               var3 = var1.parse(this);
               this._areReferencesEnabled = true;
               return var3;
            }
         } else {
            var3 = this._idToObject.get(var5);
            if (var3 == null) {
               throw new IllegalStateException("Reference " + var5 + " not found.");
            } else {
               int var4 = this._parser.getDepth();

               while(this.nextToken() != 3 && this._parser.getDepth() <= var4) {
               }

               this._isParserAtNext = true;
               return var3;
            }
         }
      } else {
         this._object = null;
         Object var2 = this._object = var1.allocate(this);
         if (var2 != null) {
            if (var1.parse(this) != var2) {
               throw new XmlException("Parse should return xml.object() when allocate(xml) != null");
            } else {
               return var2;
            }
         } else {
            return var1.parse(this);
         }
      }
   }

   private void format(Object var1, XmlFormat var2) {
      if (this._areReferencesEnabled && var2.identifier(false) != null) {
         Text var3 = (Text)this._objectToId.get(var1);
         if (var3 != null) {
            this.setAttribute(var2.identifier(true), (CharSequence)var3);
         } else {
            Text var4 = Text.valueOf(this._objectToId.size());
            this._objectToId.put(var1, var4);
            this.setAttribute(var2.identifier(false), (CharSequence)var4);
         }

         if (var3 == null || this._expandReferences && !this._formatStack.contains(var1)) {
            this._formatStack.addLast(var1);
            var2.format(var1, this);
            this._formatStack.removeLast();
         }

      } else {
         var2.format(var1, this);
      }
   }

   /** @deprecated */
   public FastList getContent() {
      if (this._parser == null) {
         return this._formatContent;
      } else {
         while(this._parseContents.size() <= this._parseContentIndex) {
            this._parseContents.add(new FastList());
         }

         FastList var1 = (FastList)this._parseContents.get(this._parseContentIndex);

         while(this.hasNext()) {
            var1.add(this.getNext());
         }

         return var1;
      }
   }

   /** @deprecated */
   public void add(Object var1, String var2, XmlFormat var3) {
      if (var1 != null) {
         try {
            if (this._elemName != null) {
               this.flushStart();
            }

            CharSequence var4 = this._elemName = toCharSeq(var2);
            this.format(var1, var3);
            if (this._elemName != null) {
               this.flushStart();
            }

            this._formatHandler.endElement(Text.EMPTY, var4, var4);
         } catch (SAXException var5) {
            throw new XmlException(var5);
         }
      }
   }

   /** @deprecated */
   public Object get(String var1, XmlFormat var2) {
      try {
         if (this.hasNext() && this._parser.getEventType() == 2 && this._parser.getQName().equals(var1)) {
            this._isParserAtNext = false;
            ++this._parseContentIndex;
            Object var3 = this.parse(var2);
            --this._parseContentIndex;
            if (this.hasNext()) {
               this.incompleteReadError();
            }

            this._isParserAtNext = false;
            return var3;
         } else {
            return null;
         }
      } catch (XmlPullParserException var4) {
         throw new XmlException(var4);
      }
   }
}
