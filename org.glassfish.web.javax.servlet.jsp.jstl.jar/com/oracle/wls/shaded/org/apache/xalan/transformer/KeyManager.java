package com.oracle.wls.shaded.org.apache.xalan.transformer;

import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import com.oracle.wls.shaded.org.apache.xml.utils.PrefixResolver;
import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNodeSet;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class KeyManager {
   private transient Vector m_key_tables = null;

   public XNodeSet getNodeSetDTMByKey(XPathContext xctxt, int doc, QName name, XMLString ref, PrefixResolver nscontext) throws TransformerException {
      XNodeSet nl = null;
      ElemTemplateElement template = (ElemTemplateElement)nscontext;
      if (null != template && null != template.getStylesheetRoot().getKeysComposed()) {
         boolean foundDoc = false;
         if (null == this.m_key_tables) {
            this.m_key_tables = new Vector(4);
         } else {
            int nKeyTables = this.m_key_tables.size();

            for(int i = 0; i < nKeyTables; ++i) {
               KeyTable kt = (KeyTable)this.m_key_tables.elementAt(i);
               if (kt.getKeyTableName().equals(name) && doc == kt.getDocKey()) {
                  nl = kt.getNodeSetDTMByKey(name, ref);
                  if (nl != null) {
                     foundDoc = true;
                     break;
                  }
               }
            }
         }

         if (null == nl && !foundDoc) {
            KeyTable kt = new KeyTable(doc, nscontext, name, template.getStylesheetRoot().getKeysComposed(), xctxt);
            this.m_key_tables.addElement(kt);
            if (doc == kt.getDocKey()) {
               foundDoc = true;
               nl = kt.getNodeSetDTMByKey(name, ref);
            }
         }
      }

      return nl;
   }
}
