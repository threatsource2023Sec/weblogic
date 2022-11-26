package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import java.util.List;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class ElemUse extends ElemTemplateElement {
   static final long serialVersionUID = 5830057200289299736L;
   private QName[] m_attributeSetsNames = null;

   public void setUseAttributeSets(Vector v) {
      int n = v.size();
      this.m_attributeSetsNames = new QName[n];

      for(int i = 0; i < n; ++i) {
         this.m_attributeSetsNames[i] = (QName)v.elementAt(i);
      }

   }

   public void setUseAttributeSets(QName[] v) {
      this.m_attributeSetsNames = v;
   }

   public QName[] getUseAttributeSets() {
      return this.m_attributeSetsNames;
   }

   public void applyAttrSets(TransformerImpl transformer, StylesheetRoot stylesheet) throws TransformerException {
      this.applyAttrSets(transformer, stylesheet, this.m_attributeSetsNames);
   }

   private void applyAttrSets(TransformerImpl transformer, StylesheetRoot stylesheet, QName[] attributeSetsNames) throws TransformerException {
      if (null != attributeSetsNames) {
         int nNames = attributeSetsNames.length;

         for(int i = 0; i < nNames; ++i) {
            QName qname = attributeSetsNames[i];
            List attrSets = stylesheet.getAttributeSetComposed(qname);
            if (null == attrSets) {
               throw new TransformerException(XSLMessages.createMessage("ER_NO_ATTRIB_SET", new Object[]{qname}), this);
            }

            int nSets = attrSets.size();

            for(int k = nSets - 1; k >= 0; --k) {
               ElemAttributeSet attrSet = (ElemAttributeSet)attrSets.get(k);
               attrSet.execute(transformer);
            }
         }
      }

   }

   public void execute(TransformerImpl transformer) throws TransformerException {
      if (null != this.m_attributeSetsNames) {
         this.applyAttrSets(transformer, this.getStylesheetRoot(), this.m_attributeSetsNames);
      }

   }
}
