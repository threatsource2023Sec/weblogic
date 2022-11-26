package com.oracle.wls.shaded.org.apache.xpath.objects;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMManager;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMNodeIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMNodeList;
import com.oracle.wls.shaded.org.apache.xml.utils.FastStringBuffer;
import com.oracle.wls.shaded.org.apache.xml.utils.WrappedRuntimeException;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xpath.NodeSetDTM;
import com.oracle.wls.shaded.org.apache.xpath.axes.NodeSequence;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class XNodeSet extends NodeSequence {
   static final long serialVersionUID = 1916026368035639667L;
   static final LessThanComparator S_LT = new LessThanComparator();
   static final LessThanOrEqualComparator S_LTE = new LessThanOrEqualComparator();
   static final GreaterThanComparator S_GT = new GreaterThanComparator();
   static final GreaterThanOrEqualComparator S_GTE = new GreaterThanOrEqualComparator();
   static final EqualComparator S_EQ = new EqualComparator();
   static final NotEqualComparator S_NEQ = new NotEqualComparator();

   protected XNodeSet() {
   }

   public XNodeSet(DTMIterator val) {
      if (val instanceof XNodeSet) {
         XNodeSet nodeSet = (XNodeSet)val;
         this.setIter(nodeSet.m_iter);
         this.m_dtmMgr = nodeSet.m_dtmMgr;
         this.m_last = nodeSet.m_last;
         if (!nodeSet.hasCache()) {
            nodeSet.setShouldCacheNodes(true);
         }

         this.setObject(nodeSet.getIteratorCache());
      } else {
         this.setIter(val);
      }

   }

   public XNodeSet(XNodeSet val) {
      this.setIter(val.m_iter);
      this.m_dtmMgr = val.m_dtmMgr;
      this.m_last = val.m_last;
      if (!val.hasCache()) {
         val.setShouldCacheNodes(true);
      }

      this.setObject(val.m_obj);
   }

   public XNodeSet(DTMManager dtmMgr) {
      this(-1, dtmMgr);
   }

   public XNodeSet(int n, DTMManager dtmMgr) {
      super((Object)(new NodeSetDTM(dtmMgr)));
      this.m_dtmMgr = dtmMgr;
      if (-1 != n) {
         ((NodeSetDTM)this.m_obj).addNode(n);
         this.m_last = 1;
      } else {
         this.m_last = 0;
      }

   }

   public int getType() {
      return 4;
   }

   public String getTypeString() {
      return "#NODESET";
   }

   public double getNumberFromNode(int n) {
      XMLString xstr = this.m_dtmMgr.getDTM(n).getStringValue(n);
      return xstr.toDouble();
   }

   public double num() {
      int node = this.item(0);
      return node != -1 ? this.getNumberFromNode(node) : Double.NaN;
   }

   public double numWithSideEffects() {
      int node = this.nextNode();
      return node != -1 ? this.getNumberFromNode(node) : Double.NaN;
   }

   public boolean bool() {
      return this.item(0) != -1;
   }

   public boolean boolWithSideEffects() {
      return this.nextNode() != -1;
   }

   public XMLString getStringFromNode(int n) {
      return (XMLString)(-1 != n ? this.m_dtmMgr.getDTM(n).getStringValue(n) : XString.EMPTYSTRING);
   }

   public void dispatchCharactersEvents(ContentHandler ch) throws SAXException {
      int node = this.item(0);
      if (node != -1) {
         this.m_dtmMgr.getDTM(node).dispatchCharactersEvents(node, ch, false);
      }

   }

   public XMLString xstr() {
      int node = this.item(0);
      return (XMLString)(node != -1 ? this.getStringFromNode(node) : XString.EMPTYSTRING);
   }

   public void appendToFsb(FastStringBuffer fsb) {
      XString xstring = (XString)this.xstr();
      xstring.appendToFsb(fsb);
   }

   public String str() {
      int node = this.item(0);
      return node != -1 ? this.getStringFromNode(node).toString() : "";
   }

   public Object object() {
      return null == this.m_obj ? this : this.m_obj;
   }

   public NodeIterator nodeset() throws TransformerException {
      return new DTMNodeIterator(this.iter());
   }

   public NodeList nodelist() throws TransformerException {
      DTMNodeList nodelist = new DTMNodeList(this);
      XNodeSet clone = (XNodeSet)nodelist.getDTMIterator();
      this.SetVector(clone.getVector());
      return nodelist;
   }

   public DTMIterator iterRaw() {
      return this;
   }

   public void release(DTMIterator iter) {
   }

   public DTMIterator iter() {
      try {
         return (DTMIterator)(this.hasCache() ? this.cloneWithReset() : this);
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException(var2.getMessage());
      }
   }

   public XObject getFresh() {
      try {
         return (XObject)(this.hasCache() ? (XObject)this.cloneWithReset() : this);
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException(var2.getMessage());
      }
   }

   public NodeSetDTM mutableNodeset() {
      NodeSetDTM mnl;
      if (this.m_obj instanceof NodeSetDTM) {
         mnl = (NodeSetDTM)this.m_obj;
      } else {
         mnl = new NodeSetDTM(this.iter());
         this.setObject(mnl);
         this.setCurrentPos(0);
      }

      return mnl;
   }

   public boolean compare(XObject obj2, Comparator comparator) throws TransformerException {
      boolean result = false;
      int type = obj2.getType();
      DTMIterator list1;
      DTMIterator list1;
      int node;
      if (4 == type) {
         list1 = this.iterRaw();
         list1 = ((XNodeSet)obj2).iterRaw();
         Vector node2Strings = null;

         while(true) {
            while(true) {
               while(-1 != (node = list1.nextNode())) {
                  XMLString s1 = this.getStringFromNode(node);
                  int n;
                  XMLString s2;
                  if (null == node2Strings) {
                     for(; -1 != (n = list1.nextNode()); node2Strings.addElement(s2)) {
                        s2 = this.getStringFromNode(n);
                        if (comparator.compareStrings(s1, s2)) {
                           result = true;
                           break;
                        }

                        if (null == node2Strings) {
                           node2Strings = new Vector();
                        }
                     }
                  } else {
                     n = node2Strings.size();

                     for(int i = 0; i < n; ++i) {
                        if (comparator.compareStrings(s1, (XMLString)node2Strings.elementAt(i))) {
                           result = true;
                           break;
                        }
                     }
                  }
               }

               list1.reset();
               list1.reset();
               return result;
            }
         }
      } else if (1 == type) {
         double num1 = this.bool() ? 1.0 : 0.0;
         double num2 = obj2.num();
         result = comparator.compareNumbers(num1, num2);
      } else if (2 == type) {
         list1 = this.iterRaw();
         double num2 = obj2.num();

         int node;
         while(-1 != (node = list1.nextNode())) {
            double num1 = this.getNumberFromNode(node);
            if (comparator.compareNumbers(num1, num2)) {
               result = true;
               break;
            }
         }

         list1.reset();
      } else {
         XMLString s2;
         XMLString s1;
         if (5 == type) {
            s2 = obj2.xstr();
            list1 = this.iterRaw();

            while(-1 != (node = list1.nextNode())) {
               s1 = this.getStringFromNode(node);
               if (comparator.compareStrings(s1, s2)) {
                  result = true;
                  break;
               }
            }

            list1.reset();
         } else if (3 == type) {
            s2 = obj2.xstr();
            list1 = this.iterRaw();

            while(-1 != (node = list1.nextNode())) {
               s1 = this.getStringFromNode(node);
               if (comparator.compareStrings(s1, s2)) {
                  result = true;
                  break;
               }
            }

            list1.reset();
         } else {
            result = comparator.compareNumbers(this.num(), obj2.num());
         }
      }

      return result;
   }

   public boolean lessThan(XObject obj2) throws TransformerException {
      return this.compare(obj2, S_LT);
   }

   public boolean lessThanOrEqual(XObject obj2) throws TransformerException {
      return this.compare(obj2, S_LTE);
   }

   public boolean greaterThan(XObject obj2) throws TransformerException {
      return this.compare(obj2, S_GT);
   }

   public boolean greaterThanOrEqual(XObject obj2) throws TransformerException {
      return this.compare(obj2, S_GTE);
   }

   public boolean equals(XObject obj2) {
      try {
         return this.compare(obj2, S_EQ);
      } catch (TransformerException var3) {
         throw new WrappedRuntimeException(var3);
      }
   }

   public boolean notEquals(XObject obj2) throws TransformerException {
      return this.compare(obj2, S_NEQ);
   }
}
