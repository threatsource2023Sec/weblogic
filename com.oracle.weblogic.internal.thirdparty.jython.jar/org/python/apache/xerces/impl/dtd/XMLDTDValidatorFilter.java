package org.python.apache.xerces.impl.dtd;

import org.python.apache.xerces.xni.parser.XMLDocumentFilter;

public interface XMLDTDValidatorFilter extends XMLDocumentFilter {
   boolean hasGrammar();

   boolean validate();
}
