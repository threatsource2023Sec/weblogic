package weblogic.ejb.container.cmp11.rdbms.compliance;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import weblogic.ejb.container.cmp11.rdbms.codegen.TypeUtils;
import weblogic.ejb.container.compliance.BaseComplianceChecker;
import weblogic.ejb.container.compliance.EJBComplianceChecker;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.j2ee.validation.ComplianceException;
import weblogic.utils.ErrorCollectionException;

public final class RDBMSComplianceChecker extends BaseComplianceChecker {
   private final Class ejbClass;
   private final String ejbName;
   private final List fieldList;

   public RDBMSComplianceChecker(CMPBeanDescriptor bd, Class ejbClass, List fieldList) {
      this.ejbClass = ejbClass;
      this.fieldList = fieldList;
      this.ejbName = bd.getEJBName();
   }

   public void checkCompliance() throws ErrorCollectionException {
      if (!EJBComplianceChecker.isNeedCheck) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Iterator fieldNames = this.fieldList.iterator();

         while(fieldNames.hasNext()) {
            String fieldName = (String)fieldNames.next();

            try {
               Field field = this.ejbClass.getField(fieldName);
               Class fieldType = field.getType();

               try {
                  TypeUtils.getSQLTypeForClass(fieldType);
               } catch (EJBCException var7) {
                  errors.add(new ComplianceException(this.getFmt().CMP_FIELD_CLASS_NOT_SUPPORTED_IN_CMP11(this.ejbName, field.getName(), fieldType.getName())));
               }
            } catch (NoSuchFieldException var8) {
               errors.add(new ComplianceException(this.getFmt().CMP_FIELDS_MUST_BE_BEAN_FIELDS(this.ejbName, fieldName)));
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }
}
