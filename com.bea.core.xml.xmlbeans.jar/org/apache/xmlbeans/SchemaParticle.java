package org.apache.xmlbeans;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public interface SchemaParticle {
   int ALL = 1;
   int CHOICE = 2;
   int SEQUENCE = 3;
   int ELEMENT = 4;
   int WILDCARD = 5;
   int STRICT = 1;
   int LAX = 2;
   int SKIP = 3;

   int getParticleType();

   BigInteger getMinOccurs();

   BigInteger getMaxOccurs();

   int getIntMinOccurs();

   int getIntMaxOccurs();

   boolean isSingleton();

   SchemaParticle[] getParticleChildren();

   SchemaParticle getParticleChild(int var1);

   int countOfParticleChild();

   boolean canStartWithElement(QName var1);

   QNameSet acceptedStartNames();

   boolean isSkippable();

   QNameSet getWildcardSet();

   int getWildcardProcess();

   QName getName();

   SchemaType getType();

   boolean isNillable();

   String getDefaultText();

   XmlAnySimpleType getDefaultValue();

   boolean isDefault();

   boolean isFixed();
}
