package com.oracle.wls.shaded.org.apache.xml.dtm.ref.sax2dtm;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMException;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMManager;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMWSFilter;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.ExtendedType;
import com.oracle.wls.shaded.org.apache.xml.res.XMLMessages;
import com.oracle.wls.shaded.org.apache.xml.serializer.SerializationHandler;
import com.oracle.wls.shaded.org.apache.xml.utils.FastStringBuffer;
import com.oracle.wls.shaded.org.apache.xml.utils.SuballocatedIntVector;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLStringDefault;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLStringFactory;
import java.util.Vector;
import javax.xml.transform.Source;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class SAX2DTM2 extends SAX2DTM {
   private int[] m_exptype_map0;
   private int[] m_nextsib_map0;
   private int[] m_firstch_map0;
   private int[] m_parent_map0;
   private int[][] m_exptype_map;
   private int[][] m_nextsib_map;
   private int[][] m_firstch_map;
   private int[][] m_parent_map;
   protected ExtendedType[] m_extendedTypes;
   protected Vector m_values;
   private int m_valueIndex;
   private int m_maxNodeIndex;
   protected int m_SHIFT;
   protected int m_MASK;
   protected int m_blocksize;
   protected static final int TEXT_LENGTH_BITS = 10;
   protected static final int TEXT_OFFSET_BITS = 21;
   protected static final int TEXT_LENGTH_MAX = 1023;
   protected static final int TEXT_OFFSET_MAX = 2097151;
   protected boolean m_buildIdIndex;
   private static final String EMPTY_STR = "";
   private static final XMLString EMPTY_XML_STR = new XMLStringDefault("");

   public SAX2DTM2(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing) {
      this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, 512, true, true, false);
   }

   public SAX2DTM2(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean buildIdIndex, boolean newNameTable) {
      super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, usePrevsib, newNameTable);
      this.m_valueIndex = 0;
      this.m_buildIdIndex = true;

      int shift;
      for(shift = 0; (blocksize >>>= 1) != 0; ++shift) {
      }

      this.m_blocksize = 1 << shift;
      this.m_SHIFT = shift;
      this.m_MASK = this.m_blocksize - 1;
      this.m_buildIdIndex = buildIdIndex;
      this.m_values = new Vector(32, 512);
      this.m_maxNodeIndex = 65536;
      this.m_exptype_map0 = this.m_exptype.getMap0();
      this.m_nextsib_map0 = this.m_nextsib.getMap0();
      this.m_firstch_map0 = this.m_firstch.getMap0();
      this.m_parent_map0 = this.m_parent.getMap0();
   }

   public final int _exptype(int identity) {
      return this.m_exptype.elementAt(identity);
   }

   public final int _exptype2(int identity) {
      return identity < this.m_blocksize ? this.m_exptype_map0[identity] : this.m_exptype_map[identity >>> this.m_SHIFT][identity & this.m_MASK];
   }

   public final int _nextsib2(int identity) {
      return identity < this.m_blocksize ? this.m_nextsib_map0[identity] : this.m_nextsib_map[identity >>> this.m_SHIFT][identity & this.m_MASK];
   }

   public final int _firstch2(int identity) {
      return identity < this.m_blocksize ? this.m_firstch_map0[identity] : this.m_firstch_map[identity >>> this.m_SHIFT][identity & this.m_MASK];
   }

   public final int _parent2(int identity) {
      return identity < this.m_blocksize ? this.m_parent_map0[identity] : this.m_parent_map[identity >>> this.m_SHIFT][identity & this.m_MASK];
   }

   public final int _type2(int identity) {
      int eType;
      if (identity < this.m_blocksize) {
         eType = this.m_exptype_map0[identity];
      } else {
         eType = this.m_exptype_map[identity >>> this.m_SHIFT][identity & this.m_MASK];
      }

      return -1 != eType ? this.m_extendedTypes[eType].getNodeType() : -1;
   }

   public final int getExpandedTypeID2(int nodeHandle) {
      int nodeID = this.makeNodeIdentity(nodeHandle);
      if (nodeID != -1) {
         return nodeID < this.m_blocksize ? this.m_exptype_map0[nodeID] : this.m_exptype_map[nodeID >>> this.m_SHIFT][nodeID & this.m_MASK];
      } else {
         return -1;
      }
   }

   public final int _exptype2Type(int exptype) {
      return -1 != exptype ? this.m_extendedTypes[exptype].getNodeType() : -1;
   }

   public int getIdForNamespace(String uri) {
      int index = this.m_values.indexOf(uri);
      if (index < 0) {
         this.m_values.addElement(uri);
         return this.m_valueIndex++;
      } else {
         return index;
      }
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.charactersFlush();
      int exName = this.m_expandedNameTable.getExpandedTypeID(uri, localName, 1);
      int prefixIndex = qName.length() != localName.length() ? this.m_valuesOrPrefixes.stringToIndex(qName) : 0;
      int elemNode = this.addNode(1, exName, this.m_parents.peek(), this.m_previous, prefixIndex, true);
      if (this.m_indexing) {
         this.indexNode(exName, elemNode);
      }

      this.m_parents.push(elemNode);
      int startDecls = this.m_contextIndexes.peek();
      int nDecls = this.m_prefixMappings.size();
      String prefix;
      int i;
      if (!this.m_pastFirstElement) {
         prefix = "xml";
         String declURL = "http://www.w3.org/XML/1998/namespace";
         exName = this.m_expandedNameTable.getExpandedTypeID((String)null, prefix, 13);
         this.m_values.addElement(declURL);
         i = this.m_valueIndex++;
         this.addNode(13, exName, elemNode, -1, i, false);
         this.m_pastFirstElement = true;
      }

      int n;
      for(n = startDecls; n < nDecls; n += 2) {
         prefix = (String)this.m_prefixMappings.elementAt(n);
         if (prefix != null) {
            String declURL = (String)this.m_prefixMappings.elementAt(n + 1);
            exName = this.m_expandedNameTable.getExpandedTypeID((String)null, prefix, 13);
            this.m_values.addElement(declURL);
            int val = this.m_valueIndex++;
            this.addNode(13, exName, elemNode, -1, val, false);
         }
      }

      n = attributes.getLength();

      for(i = 0; i < n; ++i) {
         String attrUri = attributes.getURI(i);
         String attrQName = attributes.getQName(i);
         String valString = attributes.getValue(i);
         String attrLocalName = attributes.getLocalName(i);
         byte nodeType;
         if (null == attrQName || !attrQName.equals("xmlns") && !attrQName.startsWith("xmlns:")) {
            nodeType = 2;
            if (this.m_buildIdIndex && attributes.getType(i).equalsIgnoreCase("ID")) {
               this.setIDAttribute(valString, elemNode);
            }
         } else {
            prefix = this.getPrefix(attrQName, attrUri);
            if (this.declAlreadyDeclared(prefix)) {
               continue;
            }

            nodeType = 13;
         }

         if (null == valString) {
            valString = "";
         }

         this.m_values.addElement(valString);
         int val = this.m_valueIndex++;
         if (attrLocalName.length() != attrQName.length()) {
            prefixIndex = this.m_valuesOrPrefixes.stringToIndex(attrQName);
            int dataIndex = this.m_data.size();
            this.m_data.addElement(prefixIndex);
            this.m_data.addElement(val);
            val = -dataIndex;
         }

         exName = this.m_expandedNameTable.getExpandedTypeID(attrUri, attrLocalName, nodeType);
         this.addNode(nodeType, exName, elemNode, -1, val, false);
      }

      if (null != this.m_wsfilter) {
         short wsv = this.m_wsfilter.getShouldStripSpace(this.makeNodeHandle(elemNode), this);
         boolean shouldStrip = 3 == wsv ? this.getShouldStripWhitespace() : 2 == wsv;
         this.pushShouldStripWhitespace(shouldStrip);
      }

      this.m_previous = -1;
      this.m_contextIndexes.push(this.m_prefixMappings.size());
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      this.charactersFlush();
      this.m_contextIndexes.quickPop(1);
      int topContextIndex = this.m_contextIndexes.peek();
      if (topContextIndex != this.m_prefixMappings.size()) {
         this.m_prefixMappings.setSize(topContextIndex);
      }

      this.m_previous = this.m_parents.pop();
      this.popShouldStripWhitespace();
   }

   public void comment(char[] ch, int start, int length) throws SAXException {
      if (!this.m_insideDTD) {
         this.charactersFlush();
         this.m_values.addElement(new String(ch, start, length));
         int dataIndex = this.m_valueIndex++;
         this.m_previous = this.addNode(8, 8, this.m_parents.peek(), this.m_previous, dataIndex, false);
      }
   }

   public void startDocument() throws SAXException {
      int doc = this.addNode(9, 9, -1, -1, 0, true);
      this.m_parents.push(doc);
      this.m_previous = -1;
      this.m_contextIndexes.push(this.m_prefixMappings.size());
   }

   public void endDocument() throws SAXException {
      super.endDocument();
      this.m_exptype.addElement(-1);
      this.m_parent.addElement(-1);
      this.m_nextsib.addElement(-1);
      this.m_firstch.addElement(-1);
      this.m_extendedTypes = this.m_expandedNameTable.getExtendedTypes();
      this.m_exptype_map = this.m_exptype.getMap();
      this.m_nextsib_map = this.m_nextsib.getMap();
      this.m_firstch_map = this.m_firstch.getMap();
      this.m_parent_map = this.m_parent.getMap();
   }

   protected final int addNode(int type, int expandedTypeID, int parentIndex, int previousSibling, int dataOrPrefix, boolean canHaveFirstChild) {
      int nodeIndex = this.m_size++;
      if (nodeIndex == this.m_maxNodeIndex) {
         this.addNewDTMID(nodeIndex);
         this.m_maxNodeIndex += 65536;
      }

      this.m_firstch.addElement(-1);
      this.m_nextsib.addElement(-1);
      this.m_parent.addElement(parentIndex);
      this.m_exptype.addElement(expandedTypeID);
      this.m_dataOrQName.addElement(dataOrPrefix);
      if (this.m_prevsib != null) {
         this.m_prevsib.addElement(previousSibling);
      }

      if (this.m_locator != null && this.m_useSourceLocationProperty) {
         this.setSourceLocation();
      }

      switch (type) {
         case 2:
            break;
         case 13:
            this.declareNamespaceInContext(parentIndex, nodeIndex);
            break;
         default:
            if (-1 != previousSibling) {
               this.m_nextsib.setElementAt(nodeIndex, previousSibling);
            } else if (-1 != parentIndex) {
               this.m_firstch.setElementAt(nodeIndex, parentIndex);
            }
      }

      return nodeIndex;
   }

   protected final void charactersFlush() {
      if (this.m_textPendingStart >= 0) {
         int length = this.m_chars.size() - this.m_textPendingStart;
         boolean doStrip = false;
         if (this.getShouldStripWhitespace()) {
            doStrip = this.m_chars.isWhitespace(this.m_textPendingStart, length);
         }

         if (doStrip) {
            this.m_chars.setLength(this.m_textPendingStart);
         } else if (length > 0) {
            if (length <= 1023 && this.m_textPendingStart <= 2097151) {
               this.m_previous = this.addNode(this.m_coalescedTextType, 3, this.m_parents.peek(), this.m_previous, length + (this.m_textPendingStart << 10), false);
            } else {
               int dataIndex = this.m_data.size();
               this.m_previous = this.addNode(this.m_coalescedTextType, 3, this.m_parents.peek(), this.m_previous, -dataIndex, false);
               this.m_data.addElement(this.m_textPendingStart);
               this.m_data.addElement(length);
            }
         }

         this.m_textPendingStart = -1;
         this.m_textType = this.m_coalescedTextType = 3;
      }

   }

   public void processingInstruction(String target, String data) throws SAXException {
      this.charactersFlush();
      int dataIndex = this.m_data.size();
      this.m_previous = this.addNode(7, 7, this.m_parents.peek(), this.m_previous, -dataIndex, false);
      this.m_data.addElement(this.m_valuesOrPrefixes.stringToIndex(target));
      this.m_values.addElement(data);
      this.m_data.addElement(this.m_valueIndex++);
   }

   public final int getFirstAttribute(int nodeHandle) {
      int nodeID = this.makeNodeIdentity(nodeHandle);
      if (nodeID == -1) {
         return -1;
      } else {
         int type = this._type2(nodeID);
         if (1 == type) {
            do {
               ++nodeID;
               type = this._type2(nodeID);
               if (type == 2) {
                  return this.makeNodeHandle(nodeID);
               }
            } while(13 == type);
         }

         return -1;
      }
   }

   protected int getFirstAttributeIdentity(int identity) {
      if (identity == -1) {
         return -1;
      } else {
         int type = this._type2(identity);
         if (1 == type) {
            do {
               ++identity;
               type = this._type2(identity);
               if (type == 2) {
                  return identity;
               }
            } while(13 == type);
         }

         return -1;
      }
   }

   protected int getNextAttributeIdentity(int identity) {
      int type;
      do {
         ++identity;
         type = this._type2(identity);
         if (type == 2) {
            return identity;
         }
      } while(type == 13);

      return -1;
   }

   protected final int getTypedAttribute(int nodeHandle, int attType) {
      int nodeID = this.makeNodeIdentity(nodeHandle);
      if (nodeID == -1) {
         return -1;
      } else {
         int type = this._type2(nodeID);
         if (1 == type) {
            while(true) {
               ++nodeID;
               int expType = this._exptype2(nodeID);
               if (expType == -1) {
                  return -1;
               }

               type = this.m_extendedTypes[expType].getNodeType();
               if (type == 2) {
                  if (expType == attType) {
                     return this.makeNodeHandle(nodeID);
                  }
               } else if (13 != type) {
                  break;
               }
            }
         }

         return -1;
      }
   }

   public String getLocalName(int nodeHandle) {
      int expType = this._exptype(this.makeNodeIdentity(nodeHandle));
      if (expType == 7) {
         int dataIndex = this._dataOrQName(this.makeNodeIdentity(nodeHandle));
         dataIndex = this.m_data.elementAt(-dataIndex);
         return this.m_valuesOrPrefixes.indexToString(dataIndex);
      } else {
         return this.m_expandedNameTable.getLocalName(expType);
      }
   }

   public final String getNodeNameX(int nodeHandle) {
      int nodeID = this.makeNodeIdentity(nodeHandle);
      int eType = this._exptype2(nodeID);
      if (eType == 7) {
         int dataIndex = this._dataOrQName(nodeID);
         dataIndex = this.m_data.elementAt(-dataIndex);
         return this.m_valuesOrPrefixes.indexToString(dataIndex);
      } else {
         ExtendedType extType = this.m_extendedTypes[eType];
         if (extType.getNamespace().length() == 0) {
            return extType.getLocalName();
         } else {
            int qnameIndex = this.m_dataOrQName.elementAt(nodeID);
            if (qnameIndex == 0) {
               return extType.getLocalName();
            } else {
               if (qnameIndex < 0) {
                  qnameIndex = -qnameIndex;
                  qnameIndex = this.m_data.elementAt(qnameIndex);
               }

               return this.m_valuesOrPrefixes.indexToString(qnameIndex);
            }
         }
      }
   }

   public String getNodeName(int nodeHandle) {
      int nodeID = this.makeNodeIdentity(nodeHandle);
      int eType = this._exptype2(nodeID);
      ExtendedType extType = this.m_extendedTypes[eType];
      int qnameIndex;
      if (extType.getNamespace().length() == 0) {
         qnameIndex = extType.getNodeType();
         String localName = extType.getLocalName();
         if (qnameIndex == 13) {
            return localName.length() == 0 ? "xmlns" : "xmlns:" + localName;
         } else if (qnameIndex == 7) {
            int dataIndex = this._dataOrQName(nodeID);
            dataIndex = this.m_data.elementAt(-dataIndex);
            return this.m_valuesOrPrefixes.indexToString(dataIndex);
         } else {
            return localName.length() == 0 ? this.getFixedNames(qnameIndex) : localName;
         }
      } else {
         qnameIndex = this.m_dataOrQName.elementAt(nodeID);
         if (qnameIndex == 0) {
            return extType.getLocalName();
         } else {
            if (qnameIndex < 0) {
               qnameIndex = -qnameIndex;
               qnameIndex = this.m_data.elementAt(qnameIndex);
            }

            return this.m_valuesOrPrefixes.indexToString(qnameIndex);
         }
      }
   }

   public XMLString getStringValue(int nodeHandle) {
      int identity = this.makeNodeIdentity(nodeHandle);
      if (identity == -1) {
         return EMPTY_XML_STR;
      } else {
         int type = this._type2(identity);
         int startNode;
         if (type != 1 && type != 9) {
            if (3 != type && 4 != type) {
               startNode = this.m_dataOrQName.elementAt(identity);
               if (startNode < 0) {
                  startNode = -startNode;
                  startNode = this.m_data.elementAt(startNode + 1);
               }

               return (XMLString)(this.m_xstrf != null ? this.m_xstrf.newstr((String)this.m_values.elementAt(startNode)) : new XMLStringDefault((String)this.m_values.elementAt(startNode)));
            } else {
               startNode = this.m_dataOrQName.elementAt(identity);
               if (startNode >= 0) {
                  return (XMLString)(this.m_xstrf != null ? this.m_xstrf.newstr(this.m_chars, startNode >>> 10, startNode & 1023) : new XMLStringDefault(this.m_chars.getString(startNode >>> 10, startNode & 1023)));
               } else {
                  return (XMLString)(this.m_xstrf != null ? this.m_xstrf.newstr(this.m_chars, this.m_data.elementAt(-startNode), this.m_data.elementAt(-startNode + 1)) : new XMLStringDefault(this.m_chars.getString(this.m_data.elementAt(-startNode), this.m_data.elementAt(-startNode + 1))));
               }
            }
         } else {
            startNode = identity;
            identity = this._firstch2(identity);
            if (-1 == identity) {
               return EMPTY_XML_STR;
            } else {
               int offset = -1;
               int length = 0;

               do {
                  type = this._exptype2(identity);
                  if (type == 3 || type == 4) {
                     int dataIndex = this.m_dataOrQName.elementAt(identity);
                     if (dataIndex >= 0) {
                        if (-1 == offset) {
                           offset = dataIndex >>> 10;
                        }

                        length += dataIndex & 1023;
                     } else {
                        if (-1 == offset) {
                           offset = this.m_data.elementAt(-dataIndex);
                        }

                        length += this.m_data.elementAt(-dataIndex + 1);
                     }
                  }

                  ++identity;
               } while(this._parent2(identity) >= startNode);

               if (length > 0) {
                  if (this.m_xstrf != null) {
                     return this.m_xstrf.newstr(this.m_chars, offset, length);
                  } else {
                     return new XMLStringDefault(this.m_chars.getString(offset, length));
                  }
               } else {
                  return EMPTY_XML_STR;
               }
            }
         }
      }
   }

   public final String getStringValueX(int nodeHandle) {
      int identity = this.makeNodeIdentity(nodeHandle);
      if (identity == -1) {
         return "";
      } else {
         int type = this._type2(identity);
         int startNode;
         if (type != 1 && type != 9) {
            if (3 != type && 4 != type) {
               startNode = this.m_dataOrQName.elementAt(identity);
               if (startNode < 0) {
                  startNode = -startNode;
                  startNode = this.m_data.elementAt(startNode + 1);
               }

               return (String)this.m_values.elementAt(startNode);
            } else {
               startNode = this.m_dataOrQName.elementAt(identity);
               return startNode >= 0 ? this.m_chars.getString(startNode >>> 10, startNode & 1023) : this.m_chars.getString(this.m_data.elementAt(-startNode), this.m_data.elementAt(-startNode + 1));
            }
         } else {
            startNode = identity;
            identity = this._firstch2(identity);
            if (-1 == identity) {
               return "";
            } else {
               int offset = -1;
               int length = 0;

               do {
                  type = this._exptype2(identity);
                  if (type == 3 || type == 4) {
                     int dataIndex = this.m_dataOrQName.elementAt(identity);
                     if (dataIndex >= 0) {
                        if (-1 == offset) {
                           offset = dataIndex >>> 10;
                        }

                        length += dataIndex & 1023;
                     } else {
                        if (-1 == offset) {
                           offset = this.m_data.elementAt(-dataIndex);
                        }

                        length += this.m_data.elementAt(-dataIndex + 1);
                     }
                  }

                  ++identity;
               } while(this._parent2(identity) >= startNode);

               if (length > 0) {
                  return this.m_chars.getString(offset, length);
               } else {
                  return "";
               }
            }
         }
      }
   }

   public String getStringValue() {
      int child = this._firstch2(0);
      if (child == -1) {
         return "";
      } else if (this._exptype2(child) == 3 && this._nextsib2(child) == -1) {
         int dataIndex = this.m_dataOrQName.elementAt(child);
         return dataIndex >= 0 ? this.m_chars.getString(dataIndex >>> 10, dataIndex & 1023) : this.m_chars.getString(this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
      } else {
         return this.getStringValueX(this.getDocument());
      }
   }

   public final void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize) throws SAXException {
      int identity = this.makeNodeIdentity(nodeHandle);
      if (identity != -1) {
         int type = this._type2(identity);
         int startNode;
         if (type != 1 && type != 9) {
            if (3 != type && 4 != type) {
               startNode = this.m_dataOrQName.elementAt(identity);
               if (startNode < 0) {
                  startNode = -startNode;
                  startNode = this.m_data.elementAt(startNode + 1);
               }

               String str = (String)this.m_values.elementAt(startNode);
               if (normalize) {
                  FastStringBuffer.sendNormalizedSAXcharacters(str.toCharArray(), 0, str.length(), ch);
               } else {
                  ch.characters(str.toCharArray(), 0, str.length());
               }
            } else {
               startNode = this.m_dataOrQName.elementAt(identity);
               if (startNode >= 0) {
                  if (normalize) {
                     this.m_chars.sendNormalizedSAXcharacters(ch, startNode >>> 10, startNode & 1023);
                  } else {
                     this.m_chars.sendSAXcharacters(ch, startNode >>> 10, startNode & 1023);
                  }
               } else if (normalize) {
                  this.m_chars.sendNormalizedSAXcharacters(ch, this.m_data.elementAt(-startNode), this.m_data.elementAt(-startNode + 1));
               } else {
                  this.m_chars.sendSAXcharacters(ch, this.m_data.elementAt(-startNode), this.m_data.elementAt(-startNode + 1));
               }
            }
         } else {
            startNode = identity;
            identity = this._firstch2(identity);
            if (-1 != identity) {
               int offset = -1;
               int length = 0;

               do {
                  type = this._exptype2(identity);
                  if (type == 3 || type == 4) {
                     int dataIndex = this.m_dataOrQName.elementAt(identity);
                     if (dataIndex >= 0) {
                        if (-1 == offset) {
                           offset = dataIndex >>> 10;
                        }

                        length += dataIndex & 1023;
                     } else {
                        if (-1 == offset) {
                           offset = this.m_data.elementAt(-dataIndex);
                        }

                        length += this.m_data.elementAt(-dataIndex + 1);
                     }
                  }

                  ++identity;
               } while(this._parent2(identity) >= startNode);

               if (length > 0) {
                  if (normalize) {
                     this.m_chars.sendNormalizedSAXcharacters(ch, offset, length);
                  } else {
                     this.m_chars.sendSAXcharacters(ch, offset, length);
                  }
               }
            }
         }

      }
   }

   public String getNodeValue(int nodeHandle) {
      int identity = this.makeNodeIdentity(nodeHandle);
      int type = this._type2(identity);
      int dataIndex;
      if (type != 3 && type != 4) {
         if (1 != type && 11 != type && 9 != type) {
            dataIndex = this.m_dataOrQName.elementAt(identity);
            if (dataIndex < 0) {
               dataIndex = -dataIndex;
               dataIndex = this.m_data.elementAt(dataIndex + 1);
            }

            return (String)this.m_values.elementAt(dataIndex);
         } else {
            return null;
         }
      } else {
         dataIndex = this._dataOrQName(identity);
         return dataIndex > 0 ? this.m_chars.getString(dataIndex >>> 10, dataIndex & 1023) : this.m_chars.getString(this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
      }
   }

   protected final void copyTextNode(int nodeID, SerializationHandler handler) throws SAXException {
      if (nodeID != -1) {
         int dataIndex = this.m_dataOrQName.elementAt(nodeID);
         if (dataIndex >= 0) {
            this.m_chars.sendSAXcharacters(handler, dataIndex >>> 10, dataIndex & 1023);
         } else {
            this.m_chars.sendSAXcharacters(handler, this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
         }
      }

   }

   protected final String copyElement(int nodeID, int exptype, SerializationHandler handler) throws SAXException {
      ExtendedType extType = this.m_extendedTypes[exptype];
      String uri = extType.getNamespace();
      String name = extType.getLocalName();
      if (uri.length() == 0) {
         handler.startElement(name);
         return name;
      } else {
         int qnameIndex = this.m_dataOrQName.elementAt(nodeID);
         if (qnameIndex == 0) {
            handler.startElement(name);
            handler.namespaceAfterStartElement("", uri);
            return name;
         } else {
            if (qnameIndex < 0) {
               qnameIndex = -qnameIndex;
               qnameIndex = this.m_data.elementAt(qnameIndex);
            }

            String qName = this.m_valuesOrPrefixes.indexToString(qnameIndex);
            handler.startElement(qName);
            int prefixIndex = qName.indexOf(58);
            String prefix;
            if (prefixIndex > 0) {
               prefix = qName.substring(0, prefixIndex);
            } else {
               prefix = null;
            }

            handler.namespaceAfterStartElement(prefix, uri);
            return qName;
         }
      }
   }

   protected final void copyNS(int nodeID, SerializationHandler handler, boolean inScope) throws SAXException {
      if (this.m_namespaceDeclSetElements == null || this.m_namespaceDeclSetElements.size() != 1 || this.m_namespaceDeclSets == null || ((SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(0)).size() != 1) {
         SuballocatedIntVector nsContext = null;
         int nextNSNode;
         if (inScope) {
            nsContext = this.findNamespaceContext(nodeID);
            if (nsContext == null || nsContext.size() < 1) {
               return;
            }

            nextNSNode = this.makeNodeIdentity(nsContext.elementAt(0));
         } else {
            nextNSNode = this.getNextNamespaceNode2(nodeID);
         }

         int nsIndex = 1;

         while(nextNSNode != -1) {
            int eType = this._exptype2(nextNSNode);
            String nodeName = this.m_extendedTypes[eType].getLocalName();
            int dataIndex = this.m_dataOrQName.elementAt(nextNSNode);
            if (dataIndex < 0) {
               dataIndex = -dataIndex;
               dataIndex = this.m_data.elementAt(dataIndex + 1);
            }

            String nodeValue = (String)this.m_values.elementAt(dataIndex);
            handler.namespaceAfterStartElement(nodeName, nodeValue);
            if (inScope) {
               if (nsIndex >= nsContext.size()) {
                  return;
               }

               nextNSNode = this.makeNodeIdentity(nsContext.elementAt(nsIndex));
               ++nsIndex;
            } else {
               nextNSNode = this.getNextNamespaceNode2(nextNSNode);
            }
         }

      }
   }

   protected final int getNextNamespaceNode2(int baseID) {
      int type;
      do {
         ++baseID;
      } while((type = this._type2(baseID)) == 2);

      if (type == 13) {
         return baseID;
      } else {
         return -1;
      }
   }

   protected final void copyAttributes(int nodeID, SerializationHandler handler) throws SAXException {
      for(int current = this.getFirstAttributeIdentity(nodeID); current != -1; current = this.getNextAttributeIdentity(current)) {
         int eType = this._exptype2(current);
         this.copyAttribute(current, eType, handler);
      }

   }

   protected final void copyAttribute(int nodeID, int exptype, SerializationHandler handler) throws SAXException {
      ExtendedType extType = this.m_extendedTypes[exptype];
      String uri = extType.getNamespace();
      String localName = extType.getLocalName();
      String prefix = null;
      String qname = null;
      int dataIndex = this._dataOrQName(nodeID);
      int valueIndex = dataIndex;
      if (dataIndex <= 0) {
         int prefixIndex = this.m_data.elementAt(-dataIndex);
         valueIndex = this.m_data.elementAt(-dataIndex + 1);
         qname = this.m_valuesOrPrefixes.indexToString(prefixIndex);
         int colonIndex = qname.indexOf(58);
         if (colonIndex > 0) {
            prefix = qname.substring(0, colonIndex);
         }
      }

      if (uri.length() != 0) {
         handler.namespaceAfterStartElement(prefix, uri);
      }

      String nodeName = prefix != null ? qname : localName;
      String nodeValue = (String)this.m_values.elementAt(valueIndex);
      handler.addAttribute(nodeName, nodeValue);
   }

   public final class TypedSingletonIterator extends DTMDefaultBaseIterators.SingletonIterator {
      private final int _nodeType;

      public TypedSingletonIterator(int nodeType) {
         super();
         this._nodeType = nodeType;
      }

      public int next() {
         int result = this._currentNode;
         if (result == -1) {
            return -1;
         } else {
            this._currentNode = -1;
            if (this._nodeType >= 14) {
               if (SAX2DTM2.this._exptype2(SAX2DTM2.this.makeNodeIdentity(result)) == this._nodeType) {
                  return this.returnNode(result);
               }
            } else if (SAX2DTM2.this._type2(SAX2DTM2.this.makeNodeIdentity(result)) == this._nodeType) {
               return this.returnNode(result);
            }

            return -1;
         }
      }
   }

   public final class TypedDescendantIterator extends DescendantIterator {
      private final int _nodeType;

      public TypedDescendantIterator(int nodeType) {
         super();
         this._nodeType = nodeType;
      }

      public int next() {
         int startNode = this._startNode;
         if (this._startNode == -1) {
            return -1;
         } else {
            int node = this._currentNode;
            int nodeType = this._nodeType;
            int expType;
            if (nodeType == 1) {
               if (startNode == 0) {
                  do {
                     do {
                        ++node;
                        expType = SAX2DTM2.this._exptype2(node);
                        if (-1 == expType) {
                           this._currentNode = -1;
                           return -1;
                        }
                     } while(expType < 14);
                  } while(SAX2DTM2.this.m_extendedTypes[expType].getNodeType() != 1);
               } else {
                  do {
                     do {
                        ++node;
                        expType = SAX2DTM2.this._exptype2(node);
                        if (-1 == expType || SAX2DTM2.this._parent2(node) < startNode && startNode != node) {
                           this._currentNode = -1;
                           return -1;
                        }
                     } while(expType < 14);
                  } while(SAX2DTM2.this.m_extendedTypes[expType].getNodeType() != 1);
               }
            } else {
               do {
                  ++node;
                  expType = SAX2DTM2.this._exptype2(node);
                  if (-1 == expType || SAX2DTM2.this._parent2(node) < startNode && startNode != node) {
                     this._currentNode = -1;
                     return -1;
                  }
               } while(expType != nodeType);
            }

            this._currentNode = node;
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
         }
      }
   }

   public class DescendantIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      public DescendantIterator() {
         super();
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            node = SAX2DTM2.this.makeNodeIdentity(node);
            this._startNode = node;
            if (this._includeSelf) {
               --node;
            }

            this._currentNode = node;
            return this.resetPosition();
         } else {
            return this;
         }
      }

      protected final boolean isDescendant(int identity) {
         return SAX2DTM2.this._parent2(identity) >= this._startNode || this._startNode == identity;
      }

      public int next() {
         int startNode = this._startNode;
         if (startNode == -1) {
            return -1;
         } else if (this._includeSelf && this._currentNode + 1 == startNode) {
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(++this._currentNode));
         } else {
            int node = this._currentNode;
            int type;
            if (startNode == 0) {
               int eType;
               do {
                  do {
                     do {
                        ++node;
                        eType = SAX2DTM2.this._exptype2(node);
                        if (-1 == eType) {
                           this._currentNode = -1;
                           return -1;
                        }
                     } while(eType == 3);
                  } while((type = SAX2DTM2.this.m_extendedTypes[eType].getNodeType()) == 2);
               } while(type == 13);
            } else {
               do {
                  ++node;
                  type = SAX2DTM2.this._type2(node);
                  if (-1 == type || !this.isDescendant(node)) {
                     this._currentNode = -1;
                     return -1;
                  }
               } while(2 == type || 3 == type || 13 == type);
            }

            this._currentNode = node;
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
         }
      }

      public DTMAxisIterator reset() {
         boolean temp = this._isRestartable;
         this._isRestartable = true;
         this.setStartNode(SAX2DTM2.this.makeNodeHandle(this._startNode));
         this._isRestartable = temp;
         return this;
      }
   }

   public final class TypedAncestorIterator extends AncestorIterator {
      private final int _nodeType;

      public TypedAncestorIterator(int type) {
         super();
         this._nodeType = type;
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         this.m_realStartNode = node;
         if (!this._isRestartable) {
            return this;
         } else {
            int nodeID = SAX2DTM2.this.makeNodeIdentity(node);
            this.m_size = 0;
            if (nodeID == -1) {
               this._currentNode = -1;
               this.m_ancestorsPos = 0;
               return this;
            } else {
               int nodeType = this._nodeType;
               if (!this._includeSelf) {
                  nodeID = SAX2DTM2.this._parent2(nodeID);
                  node = SAX2DTM2.this.makeNodeHandle(nodeID);
               }

               this._startNode = node;
               int eType;
               int[] newAncestors;
               if (nodeType >= 14) {
                  for(; nodeID != -1; nodeID = SAX2DTM2.this._parent2(nodeID)) {
                     eType = SAX2DTM2.this._exptype2(nodeID);
                     if (eType == nodeType) {
                        if (this.m_size >= this.m_ancestors.length) {
                           newAncestors = new int[this.m_size * 2];
                           System.arraycopy(this.m_ancestors, 0, newAncestors, 0, this.m_ancestors.length);
                           this.m_ancestors = newAncestors;
                        }

                        this.m_ancestors[this.m_size++] = SAX2DTM2.this.makeNodeHandle(nodeID);
                     }
                  }
               } else {
                  for(; nodeID != -1; nodeID = SAX2DTM2.this._parent2(nodeID)) {
                     eType = SAX2DTM2.this._exptype2(nodeID);
                     if (eType < 14 && eType == nodeType || eType >= 14 && SAX2DTM2.this.m_extendedTypes[eType].getNodeType() == nodeType) {
                        if (this.m_size >= this.m_ancestors.length) {
                           newAncestors = new int[this.m_size * 2];
                           System.arraycopy(this.m_ancestors, 0, newAncestors, 0, this.m_ancestors.length);
                           this.m_ancestors = newAncestors;
                        }

                        this.m_ancestors[this.m_size++] = SAX2DTM2.this.makeNodeHandle(nodeID);
                     }
                  }
               }

               this.m_ancestorsPos = this.m_size - 1;
               this._currentNode = this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1;
               return this.resetPosition();
            }
         }
      }

      public int getNodeByPosition(int position) {
         return position > 0 && position <= this.m_size ? this.m_ancestors[position - 1] : -1;
      }

      public int getLast() {
         return this.m_size;
      }
   }

   public class AncestorIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      private static final int m_blocksize = 32;
      int[] m_ancestors = new int[32];
      int m_size = 0;
      int m_ancestorsPos;
      int m_markedPos;
      int m_realStartNode;

      public AncestorIterator() {
         super();
      }

      public int getStartNode() {
         return this.m_realStartNode;
      }

      public final boolean isReverse() {
         return true;
      }

      public DTMAxisIterator cloneIterator() {
         this._isRestartable = false;

         try {
            AncestorIterator clone = (AncestorIterator)super.clone();
            clone._startNode = this._startNode;
            return clone;
         } catch (CloneNotSupportedException var2) {
            throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", (Object[])null));
         }
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         this.m_realStartNode = node;
         if (this._isRestartable) {
            int nodeID = SAX2DTM2.this.makeNodeIdentity(node);
            this.m_size = 0;
            if (nodeID == -1) {
               this._currentNode = -1;
               this.m_ancestorsPos = 0;
               return this;
            } else {
               if (!this._includeSelf) {
                  nodeID = SAX2DTM2.this._parent2(nodeID);
                  node = SAX2DTM2.this.makeNodeHandle(nodeID);
               }

               for(this._startNode = node; nodeID != -1; node = SAX2DTM2.this.makeNodeHandle(nodeID)) {
                  if (this.m_size >= this.m_ancestors.length) {
                     int[] newAncestors = new int[this.m_size * 2];
                     System.arraycopy(this.m_ancestors, 0, newAncestors, 0, this.m_ancestors.length);
                     this.m_ancestors = newAncestors;
                  }

                  this.m_ancestors[this.m_size++] = node;
                  nodeID = SAX2DTM2.this._parent2(nodeID);
               }

               this.m_ancestorsPos = this.m_size - 1;
               this._currentNode = this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1;
               return this.resetPosition();
            }
         } else {
            return this;
         }
      }

      public DTMAxisIterator reset() {
         this.m_ancestorsPos = this.m_size - 1;
         this._currentNode = this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1;
         return this.resetPosition();
      }

      public int next() {
         int next = this._currentNode;
         int pos = --this.m_ancestorsPos;
         this._currentNode = pos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1;
         return this.returnNode(next);
      }

      public void setMark() {
         this.m_markedPos = this.m_ancestorsPos;
      }

      public void gotoMark() {
         this.m_ancestorsPos = this.m_markedPos;
         this._currentNode = this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1;
      }
   }

   public final class TypedFollowingIterator extends FollowingIterator {
      private final int _nodeType;

      public TypedFollowingIterator(int type) {
         super();
         this._nodeType = type;
      }

      public int next() {
         int nodeType = this._nodeType;
         int currentNodeID = SAX2DTM2.this.makeNodeIdentity(this._currentNode);
         int node;
         int current;
         int type;
         if (nodeType >= 14) {
            do {
               node = currentNodeID;
               current = currentNodeID;

               do {
                  ++current;
                  type = SAX2DTM2.this._type2(current);
               } while(type != -1 && (2 == type || 13 == type));

               currentNodeID = type != -1 ? current : -1;
            } while(node != -1 && SAX2DTM2.this._exptype2(node) != nodeType);
         } else {
            do {
               node = currentNodeID;
               current = currentNodeID;

               do {
                  ++current;
                  type = SAX2DTM2.this._type2(current);
               } while(type != -1 && (2 == type || 13 == type));

               currentNodeID = type != -1 ? current : -1;
            } while(node != -1 && SAX2DTM2.this._exptype2(node) != nodeType && SAX2DTM2.this._type2(node) != nodeType);
         }

         this._currentNode = SAX2DTM2.this.makeNodeHandle(currentNodeID);
         return node == -1 ? -1 : this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
      }
   }

   public class FollowingIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      public FollowingIterator() {
         super();
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (!this._isRestartable) {
            return this;
         } else {
            this._startNode = node;
            node = SAX2DTM2.this.makeNodeIdentity(node);
            int type = SAX2DTM2.this._type2(node);
            int first;
            if (2 == type || 13 == type) {
               node = SAX2DTM2.this._parent2(node);
               first = SAX2DTM2.this._firstch2(node);
               if (-1 != first) {
                  this._currentNode = SAX2DTM2.this.makeNodeHandle(first);
                  return this.resetPosition();
               }
            }

            do {
               first = SAX2DTM2.this._nextsib2(node);
               if (-1 == first) {
                  node = SAX2DTM2.this._parent2(node);
               }
            } while(-1 == first && -1 != node);

            this._currentNode = SAX2DTM2.this.makeNodeHandle(first);
            return this.resetPosition();
         }
      }

      public int next() {
         int node = this._currentNode;
         int current = SAX2DTM2.this.makeNodeIdentity(node);

         int type;
         do {
            ++current;
            type = SAX2DTM2.this._type2(current);
            if (-1 == type) {
               this._currentNode = -1;
               return this.returnNode(node);
            }
         } while(2 == type || 13 == type);

         this._currentNode = SAX2DTM2.this.makeNodeHandle(current);
         return this.returnNode(node);
      }
   }

   public final class TypedPrecedingIterator extends PrecedingIterator {
      private final int _nodeType;

      public TypedPrecedingIterator(int type) {
         super();
         this._nodeType = type;
      }

      public int next() {
         int node = this._currentNode;
         int nodeType = this._nodeType;
         if (nodeType >= 14) {
            while(true) {
               ++node;
               if (this._sp < 0) {
                  node = -1;
                  break;
               }

               if (node >= this._stack[this._sp]) {
                  if (--this._sp < 0) {
                     node = -1;
                     break;
                  }
               } else if (SAX2DTM2.this._exptype2(node) == nodeType) {
                  break;
               }
            }
         } else {
            while(true) {
               ++node;
               if (this._sp < 0) {
                  node = -1;
                  break;
               }

               if (node >= this._stack[this._sp]) {
                  if (--this._sp < 0) {
                     node = -1;
                     break;
                  }
               } else {
                  int expType = SAX2DTM2.this._exptype2(node);
                  if (expType < 14) {
                     if (expType == nodeType) {
                        break;
                     }
                  } else if (SAX2DTM2.this.m_extendedTypes[expType].getNodeType() == nodeType) {
                     break;
                  }
               }
            }
         }

         this._currentNode = node;
         return node == -1 ? -1 : this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
      }
   }

   public class PrecedingIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      private final int _maxAncestors = 8;
      protected int[] _stack = new int[8];
      protected int _sp;
      protected int _oldsp;
      protected int _markedsp;
      protected int _markedNode;
      protected int _markedDescendant;

      public PrecedingIterator() {
         super();
      }

      public boolean isReverse() {
         return true;
      }

      public DTMAxisIterator cloneIterator() {
         this._isRestartable = false;

         try {
            PrecedingIterator clone = (PrecedingIterator)super.clone();
            int[] stackCopy = new int[this._stack.length];
            System.arraycopy(this._stack, 0, stackCopy, 0, this._stack.length);
            clone._stack = stackCopy;
            return clone;
         } catch (CloneNotSupportedException var3) {
            throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", (Object[])null));
         }
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            node = SAX2DTM2.this.makeNodeIdentity(node);
            if (SAX2DTM2.this._type2(node) == 2) {
               node = SAX2DTM2.this._parent2(node);
            }

            this._startNode = node;
            int index = 0;
            this._stack[0] = node;

            for(int parent = node; (parent = SAX2DTM2.this._parent2(parent)) != -1; this._stack[index] = parent) {
               ++index;
               if (index == this._stack.length) {
                  int[] stack = new int[index * 2];
                  System.arraycopy(this._stack, 0, stack, 0, index);
                  this._stack = stack;
               }
            }

            if (index > 0) {
               --index;
            }

            this._currentNode = this._stack[index];
            this._oldsp = this._sp = index;
            return this.resetPosition();
         } else {
            return this;
         }
      }

      public int next() {
         ++this._currentNode;

         for(; this._sp >= 0; ++this._currentNode) {
            if (this._currentNode < this._stack[this._sp]) {
               int type = SAX2DTM2.this._type2(this._currentNode);
               if (type != 2 && type != 13) {
                  return this.returnNode(SAX2DTM2.this.makeNodeHandle(this._currentNode));
               }
            } else {
               --this._sp;
            }
         }

         return -1;
      }

      public DTMAxisIterator reset() {
         this._sp = this._oldsp;
         return this.resetPosition();
      }

      public void setMark() {
         this._markedsp = this._sp;
         this._markedNode = this._currentNode;
         this._markedDescendant = this._stack[0];
      }

      public void gotoMark() {
         this._sp = this._markedsp;
         this._currentNode = this._markedNode;
      }
   }

   public final class TypedPrecedingSiblingIterator extends PrecedingSiblingIterator {
      private final int _nodeType;

      public TypedPrecedingSiblingIterator(int type) {
         super();
         this._nodeType = type;
      }

      public int next() {
         int node = this._currentNode;
         int nodeType = this._nodeType;
         int startNodeID = this._startNodeID;
         if (nodeType != 1) {
            while(node != -1 && node != startNodeID && SAX2DTM2.this._exptype2(node) != nodeType) {
               node = SAX2DTM2.this._nextsib2(node);
            }
         } else {
            while(node != -1 && node != startNodeID && SAX2DTM2.this._exptype2(node) < 14) {
               node = SAX2DTM2.this._nextsib2(node);
            }
         }

         if (node != -1 && node != startNodeID) {
            this._currentNode = SAX2DTM2.this._nextsib2(node);
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
         } else {
            this._currentNode = -1;
            return -1;
         }
      }

      public int getLast() {
         if (this._last != -1) {
            return this._last;
         } else {
            this.setMark();
            int node = this._currentNode;
            int nodeType = this._nodeType;
            int startNodeID = this._startNodeID;
            int last = 0;
            if (nodeType != 1) {
               for(; node != -1 && node != startNodeID; node = SAX2DTM2.this._nextsib2(node)) {
                  if (SAX2DTM2.this._exptype2(node) == nodeType) {
                     ++last;
                  }
               }
            } else {
               for(; node != -1 && node != startNodeID; node = SAX2DTM2.this._nextsib2(node)) {
                  if (SAX2DTM2.this._exptype2(node) >= 14) {
                     ++last;
                  }
               }
            }

            this.gotoMark();
            return this._last = last;
         }
      }
   }

   public class PrecedingSiblingIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      protected int _startNodeID;

      public PrecedingSiblingIterator() {
         super();
      }

      public boolean isReverse() {
         return true;
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            this._startNode = node;
            node = this._startNodeID = SAX2DTM2.this.makeNodeIdentity(node);
            if (node == -1) {
               this._currentNode = node;
               return this.resetPosition();
            } else {
               int type = SAX2DTM2.this._type2(node);
               if (2 != type && 13 != type) {
                  this._currentNode = SAX2DTM2.this._parent2(node);
                  if (-1 != this._currentNode) {
                     this._currentNode = SAX2DTM2.this._firstch2(this._currentNode);
                  } else {
                     this._currentNode = node;
                  }
               } else {
                  this._currentNode = node;
               }

               return this.resetPosition();
            }
         } else {
            return this;
         }
      }

      public int next() {
         if (this._currentNode != this._startNodeID && this._currentNode != -1) {
            int node = this._currentNode;
            this._currentNode = SAX2DTM2.this._nextsib2(node);
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
         } else {
            return -1;
         }
      }
   }

   public final class TypedAttributeIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      private final int _nodeType;

      public TypedAttributeIterator(int nodeType) {
         super();
         this._nodeType = nodeType;
      }

      public DTMAxisIterator setStartNode(int node) {
         if (this._isRestartable) {
            this._startNode = node;
            this._currentNode = SAX2DTM2.this.getTypedAttribute(node, this._nodeType);
            return this.resetPosition();
         } else {
            return this;
         }
      }

      public int next() {
         int node = this._currentNode;
         this._currentNode = -1;
         return this.returnNode(node);
      }
   }

   public final class AttributeIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      public AttributeIterator() {
         super();
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            this._startNode = node;
            this._currentNode = SAX2DTM2.this.getFirstAttributeIdentity(SAX2DTM2.this.makeNodeIdentity(node));
            return this.resetPosition();
         } else {
            return this;
         }
      }

      public int next() {
         int node = this._currentNode;
         if (node != -1) {
            this._currentNode = SAX2DTM2.this.getNextAttributeIdentity(node);
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
         } else {
            return -1;
         }
      }
   }

   public final class TypedFollowingSiblingIterator extends FollowingSiblingIterator {
      private final int _nodeType;

      public TypedFollowingSiblingIterator(int type) {
         super();
         this._nodeType = type;
      }

      public int next() {
         if (this._currentNode == -1) {
            return -1;
         } else {
            int node = this._currentNode;
            int nodeType = this._nodeType;
            if (nodeType != 1) {
               while((node = SAX2DTM2.this._nextsib2(node)) != -1 && SAX2DTM2.this._exptype2(node) != nodeType) {
               }
            } else {
               while((node = SAX2DTM2.this._nextsib2(node)) != -1 && SAX2DTM2.this._exptype2(node) < 14) {
               }
            }

            this._currentNode = node;
            return node == -1 ? -1 : this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
         }
      }
   }

   public class FollowingSiblingIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      public FollowingSiblingIterator() {
         super();
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            this._startNode = node;
            this._currentNode = SAX2DTM2.this.makeNodeIdentity(node);
            return this.resetPosition();
         } else {
            return this;
         }
      }

      public int next() {
         this._currentNode = this._currentNode == -1 ? -1 : SAX2DTM2.this._nextsib2(this._currentNode);
         return this.returnNode(SAX2DTM2.this.makeNodeHandle(this._currentNode));
      }
   }

   public class TypedRootIterator extends DTMDefaultBaseIterators.RootIterator {
      private final int _nodeType;

      public TypedRootIterator(int nodeType) {
         super();
         this._nodeType = nodeType;
      }

      public int next() {
         if (this._startNode == this._currentNode) {
            return -1;
         } else {
            int node = this._startNode;
            int expType = SAX2DTM2.this._exptype2(SAX2DTM2.this.makeNodeIdentity(node));
            this._currentNode = node;
            if (this._nodeType >= 14) {
               if (this._nodeType == expType) {
                  return this.returnNode(node);
               }
            } else if (expType < 14) {
               if (expType == this._nodeType) {
                  return this.returnNode(node);
               }
            } else if (SAX2DTM2.this.m_extendedTypes[expType].getNodeType() == this._nodeType) {
               return this.returnNode(node);
            }

            return -1;
         }
      }
   }

   public final class TypedChildrenIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      private final int _nodeType;

      public TypedChildrenIterator(int nodeType) {
         super();
         this._nodeType = nodeType;
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            this._startNode = node;
            this._currentNode = node == -1 ? -1 : SAX2DTM2.this._firstch2(SAX2DTM2.this.makeNodeIdentity(this._startNode));
            return this.resetPosition();
         } else {
            return this;
         }
      }

      public int next() {
         int node = this._currentNode;
         if (node == -1) {
            return -1;
         } else {
            int nodeType = this._nodeType;
            if (nodeType != 1) {
               while(node != -1 && SAX2DTM2.this._exptype2(node) != nodeType) {
                  node = SAX2DTM2.this._nextsib2(node);
               }
            } else {
               while(node != -1) {
                  int eType = SAX2DTM2.this._exptype2(node);
                  if (eType >= 14) {
                     break;
                  }

                  node = SAX2DTM2.this._nextsib2(node);
               }
            }

            if (node == -1) {
               this._currentNode = -1;
               return -1;
            } else {
               this._currentNode = SAX2DTM2.this._nextsib2(node);
               return this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
            }
         }
      }

      public int getNodeByPosition(int position) {
         if (position <= 0) {
            return -1;
         } else {
            int node = this._currentNode;
            int pos = 0;
            int nodeType = this._nodeType;
            if (nodeType != 1) {
               for(; node != -1; node = SAX2DTM2.this._nextsib2(node)) {
                  if (SAX2DTM2.this._exptype2(node) == nodeType) {
                     ++pos;
                     if (pos == position) {
                        return SAX2DTM2.this.makeNodeHandle(node);
                     }
                  }
               }

               return -1;
            } else {
               for(; node != -1; node = SAX2DTM2.this._nextsib2(node)) {
                  if (SAX2DTM2.this._exptype2(node) >= 14) {
                     ++pos;
                     if (pos == position) {
                        return SAX2DTM2.this.makeNodeHandle(node);
                     }
                  }
               }

               return -1;
            }
         }
      }
   }

   public final class ParentIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      private int _nodeType = -1;

      public ParentIterator() {
         super();
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            this._startNode = node;
            if (node != -1) {
               this._currentNode = SAX2DTM2.this._parent2(SAX2DTM2.this.makeNodeIdentity(node));
            } else {
               this._currentNode = -1;
            }

            return this.resetPosition();
         } else {
            return this;
         }
      }

      public DTMAxisIterator setNodeType(int type) {
         this._nodeType = type;
         return this;
      }

      public int next() {
         int result = this._currentNode;
         if (result == -1) {
            return -1;
         } else if (this._nodeType == -1) {
            this._currentNode = -1;
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(result));
         } else {
            if (this._nodeType >= 14) {
               if (this._nodeType == SAX2DTM2.this._exptype2(result)) {
                  this._currentNode = -1;
                  return this.returnNode(SAX2DTM2.this.makeNodeHandle(result));
               }
            } else if (this._nodeType == SAX2DTM2.this._type2(result)) {
               this._currentNode = -1;
               return this.returnNode(SAX2DTM2.this.makeNodeHandle(result));
            }

            return -1;
         }
      }
   }

   public final class ChildrenIterator extends DTMDefaultBaseIterators.InternalAxisIteratorBase {
      public ChildrenIterator() {
         super();
      }

      public DTMAxisIterator setStartNode(int node) {
         if (node == 0) {
            node = SAX2DTM2.this.getDocument();
         }

         if (this._isRestartable) {
            this._startNode = node;
            this._currentNode = node == -1 ? -1 : SAX2DTM2.this._firstch2(SAX2DTM2.this.makeNodeIdentity(node));
            return this.resetPosition();
         } else {
            return this;
         }
      }

      public int next() {
         if (this._currentNode != -1) {
            int node = this._currentNode;
            this._currentNode = SAX2DTM2.this._nextsib2(node);
            return this.returnNode(SAX2DTM2.this.makeNodeHandle(node));
         } else {
            return -1;
         }
      }
   }
}
