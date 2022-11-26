package weblogic.xml.saaj;

import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPException;
import weblogic.xml.domimpl.DocumentImpl;

class DetailImpl extends SOAPElementImpl implements Detail {
   static final long serialVersionUID = 7465819359788672801L;

   DetailImpl(DocumentImpl ownerDocument) {
      super(ownerDocument, (String)null, "detail", (String)null);
   }

   DetailImpl(DocumentImpl ownerDocument, String namespace) {
      super(ownerDocument, namespace, isSoap11(namespace) ? "detail" : SOAPConstants.FAULT12_DETAIL.getLocalPart());
   }

   public DetailEntry addDetailEntry(Name name) throws SOAPException {
      SOAPElementImpl entry = new SOAPElementImpl(this.ownerDocument, name);
      entry.isSaajTyped(true);
      this.appendChild(entry);
      return entry;
   }

   public Iterator getDetailEntries() {
      return this.getChildElements();
   }

   public DetailEntry addDetailEntry(QName qname) throws SOAPException {
      return this.addDetailEntry((Name)(new NameImpl(qname)));
   }
}
