package weblogic.apache.xerces.impl.xs.models;

import java.util.Vector;
import weblogic.apache.xerces.impl.xs.SubstitutionGroupHandler;
import weblogic.apache.xerces.impl.xs.XMLSchemaException;
import weblogic.apache.xerces.xni.QName;

public interface XSCMValidator {
   short FIRST_ERROR = -1;
   short SUBSEQUENT_ERROR = -2;

   int[] startContentModel();

   Object oneTransition(QName var1, int[] var2, SubstitutionGroupHandler var3);

   boolean endContentModel(int[] var1);

   boolean checkUniqueParticleAttribution(SubstitutionGroupHandler var1) throws XMLSchemaException;

   Vector whatCanGoHere(int[] var1);

   int[] occurenceInfo(int[] var1);

   String getTermName(int var1);

   boolean isCompactedForUPA();
}
