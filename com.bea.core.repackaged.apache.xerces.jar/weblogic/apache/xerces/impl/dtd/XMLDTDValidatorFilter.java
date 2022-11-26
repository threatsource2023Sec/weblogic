package weblogic.apache.xerces.impl.dtd;

import weblogic.apache.xerces.xni.parser.XMLDocumentFilter;

public interface XMLDTDValidatorFilter extends XMLDocumentFilter {
   boolean hasGrammar();

   boolean validate();
}
