package org.apache.xmlbeans;

import java.util.Set;
import javax.xml.namespace.QName;

public interface QNameSetSpecification {
   boolean contains(QName var1);

   boolean isAll();

   boolean isEmpty();

   boolean containsAll(QNameSetSpecification var1);

   boolean isDisjoint(QNameSetSpecification var1);

   QNameSet intersect(QNameSetSpecification var1);

   QNameSet union(QNameSetSpecification var1);

   QNameSet inverse();

   Set excludedURIs();

   Set includedURIs();

   Set excludedQNamesInIncludedURIs();

   Set includedQNamesInExcludedURIs();
}
