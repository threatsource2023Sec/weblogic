package org.apache.openjpa.meta;

public class MetaDataInheritanceComparator extends InheritanceComparator {
   protected Class toClass(Object elem) {
      return elem == null ? null : ((ClassMetaData)elem).getDescribedType();
   }

   public int compare(Object o1, Object o2) {
      if (o1 == o2) {
         return 0;
      } else if (o1 == null) {
         return -1;
      } else if (o2 == null) {
         return 1;
      } else {
         ClassMetaData m1 = (ClassMetaData)o1;
         ClassMetaData m2 = (ClassMetaData)o2;
         FieldMetaData[] fmds = m1.getDeclaredFields();

         int i;
         for(i = 0; i < fmds.length; ++i) {
            if (fmds[i].isPrimaryKey() && m2.getDescribedType().isAssignableFrom(fmds[i].getDeclaredType())) {
               return 1;
            }
         }

         fmds = m2.getDeclaredFields();

         for(i = 0; i < fmds.length; ++i) {
            if (fmds[i].isPrimaryKey() && m1.getDescribedType().isAssignableFrom(fmds[i].getDeclaredType())) {
               return -1;
            }
         }

         return super.compare(o1, o2);
      }
   }
}
