package weblogic.xml.xmlnode;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.utils.collections.NumericValueHashtable;
import weblogic.xml.stream.ReferenceResolver;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public class ReferenceMap implements ReferenceResolver, Serializable {
   private static final long serialVersionUID = -1575276677954010200L;
   protected Map targetNodes = new HashMap();
   protected transient NumericValueHashtable referenceCount = new NumericValueHashtable();
   protected List nodesToProcess = new ArrayList();

   public List getNodesToProcess() {
      return this.nodesToProcess;
   }

   public XMLNode getTarget(String idref) throws XMLNodeException {
      XMLNode node = (XMLNode)this.targetNodes.get(this.removeHash(idref));
      if (node == null) {
         throw new XMLNodeException("Reference to an unknown id[" + idref + "]");
      } else {
         return node;
      }
   }

   public String getId(String idref) {
      return this.removeHash(idref);
   }

   public void addReference(String idref, XMLNode sourceNode) {
      if (idref == null) {
         throw new NullPointerException("id may not be null");
      } else {
         this.nodesToProcess.add(sourceNode);
         long numReference = this.numReference(this.removeHash(idref));
         ++numReference;
         this.referenceCount.put(this.removeHash(idref), numReference);
      }
   }

   public long numReference(String idref) {
      return idref == null ? 0L : this.referenceCount.get(this.removeHash(idref));
   }

   public void clear() {
      this.targetNodes.clear();
      this.referenceCount.clear();
      this.nodesToProcess.clear();
   }

   public void addTarget(String id, XMLNode node) {
      this.targetNodes.put(id, node);
   }

   public XMLInputStream resolve(String idref) throws XMLStreamException {
      try {
         return this.getTarget(idref).stream();
      } catch (XMLNodeException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public String toString() {
      StringBuffer stringBuffer = new StringBuffer();
      Iterator i = this.targetNodes.entrySet().iterator();
      stringBuffer.append("[KEY][COUNT]-->[XMLNODE]\n");

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         XMLNode node = (XMLNode)entry.getValue();
         StringWriter stringWriter = new StringWriter();
         PrintWriter printWriter = new PrintWriter(stringWriter);
         printWriter.println(node);
         stringBuffer.append("[" + entry.getKey() + "][" + this.numReference((String)entry.getKey()) + "]-->[" + stringWriter.toString() + "]\n");
      }

      stringBuffer.append("---------------\n");
      return stringBuffer.toString();
   }

   protected String removeHash(String string) {
      return string.charAt(0) == '#' ? string.substring(1) : string;
   }
}
