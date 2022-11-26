package weblogic.servlet.internal.dd.compliance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.utils.ErrorCollectionException;

public class EnvEntryComplianceChecker extends BaseComplianceChecker {
   private static final String[] ENTRY_TYPES = new String[]{"java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.String", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double"};

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      WebAppBean web = info.getWebAppBean();
      if (web != null) {
         EnvEntryBean[] entries = web.getEnvEntries();
         if (entries != null) {
            for(int i = 0; i < entries.length; ++i) {
               this.checkEnvEntry(entries[i]);
            }

            this.checkForExceptions();
         }
      }
   }

   private void checkEnvEntry(EnvEntryBean entry) {
      if (entry != null) {
         String entryType = entry.getEnvEntryType();
         String entryValue = entry.getEnvEntryValue();
         if (entryType != null && isEntryTypeValid(entryType)) {
            this.validateEntryValue(entryType, entryValue);
         } else {
            this.addDescriptorError(this.fmt.INVALID_ENV_ENTRY_TYPE(entryType));
         }
      }
   }

   private void validateEntryValue(String type, String value) {
      if (type.equals("java.lang.Character")) {
         if (value == null || value.length() > 2) {
            this.update(this.fmt.warning() + this.fmt.INVALID_ENV_ENTRY_VALUE(type, value));
         }
      } else {
         try {
            Class clazz = this.getClass().getClassLoader().loadClass(type);
            Constructor ctor = clazz.getConstructor(String.class);
            ctor.newInstance(value);
         } catch (ClassNotFoundException var5) {
         } catch (IllegalAccessException var6) {
         } catch (NoSuchMethodException var7) {
         } catch (InstantiationException var8) {
            this.addDescriptorError(this.fmt.INVALID_ENV_ENTRY_VALUE(type, value));
         } catch (InvocationTargetException var9) {
            this.addDescriptorError(this.fmt.INVALID_ENV_ENTRY_VALUE(type, value));
         }
      }

   }

   private static boolean isEntryTypeValid(String type) {
      for(int i = 0; i < ENTRY_TYPES.length; ++i) {
         if (ENTRY_TYPES[i].equals(type)) {
            return true;
         }
      }

      return false;
   }
}
