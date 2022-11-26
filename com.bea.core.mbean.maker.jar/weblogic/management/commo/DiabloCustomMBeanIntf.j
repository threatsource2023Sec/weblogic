@start rule: package
package @packageName;

@end rule: package

@start rule: import
import @importName;

@end rule: import

@start rule: attributeTypeDoc
        
@end rule: attributeTypeDoc

@start rule: attributeMinDoc
        
@end rule: attributeMinDoc

@start rule: attributeMaxDoc

@end rule: attributeMaxDoc

@start rule: attributeDefaultDoc
        
@end rule: attributeDefaultDoc

@start rule: attributeDerivedDefaultDoc

@end rule: attributeDerivedDefaultDoc

@start rule: attributeSecureValueDoc

@end rule: attributeSecureValueDoc

@start rule: attributeInterfaceDefaultDoc
        
@end rule: attributeInterfaceDefaultDoc

@start rule: attributeLegalValuesDoc

@end rule: attributeLegalValuesDoc

@start rule: attributeValidatorDoc

@end rule: attributeValidatorDoc

@start rule: attributeDeprecatedDoc

@end rule: attributeDeprecatedDoc

@start rule: attributeSinceDoc

@end rule: attributeSinceDoc

@start rule: attributeExcludeDoc

@end rule: attributeExcludeDoc

@start rule: attributeRestNameDoc

@end rule: attributeRestNameDoc

@start rule: attributeExcludeFromRestDoc

@end rule: attributeExcludeFromRestDoc

@start rule: attributeRestInternalDoc

@end rule: attributeRestInternalDoc

@start rule: attributeVisibleToPartitionsDoc

@end rule: attributeVisibleToPartitionsDoc

@start rule: operationParamDoc
         * @@param @paramName - @paramDescription
@end rule: operationParamDoc

@start rule: operationExceptionDoc
         * @@exception @exceptionName
@end rule: operationExceptionDoc

@start rule: operationReturnValueDescriptionDocRule
         * @@return @returnValueDescription
@end rule: operationReturnValueDescriptionDocRule

@start rule: attributeMinJavaDoc
         * @@legalMin @attributeMin
@end rule: attributeMinJavaDoc

@start rule: attributeMaxJavaDoc
         * @@legalMax @attributeMax
@end rule: attributeMaxJavaDoc
         
@start rule: attributePresentationStringJavaDoc
         * @@xmlElementName @attributePresentationString
@end rule: attributePresentationStringJavaDoc

@start rule: attributePreprocessor
         * @@preprocessor @attributePreprocessor
@end rule: attributePreprocessor

@start rule: encryptedJavaDoc
         * @@encrypted
@end rule: encryptedJavaDoc

@start rule: noDocJavaDoc
         * @@exclude
         * @@internal
@end rule: noDocJavaDoc


@start rule: generateImplementationDoc
         * @@operation
@end rule: generateImplementationDoc

@start rule: operationImpactDoc
         * @@impact @operationImpact
@end rule: operationImpactDoc

@start rule: obsoleteJavaDoc
         * @@obsolete @attributeObsolete
@end rule: obsoleteJavaDoc

@start rule: previouslyPersistedJavaDoc
         * @@previouslyPersisted
@end rule: previouslyPersistedJavaDoc

@start rule: persistPolicy
         * @@non-configurable
@end rule: persistPolicy

@start rule: attributeDynamicPolicy
         * @@dynamic @isDynamic
@end rule: attributeDynamicPolicy

@start rule: attributePersistPolicy
         @persistPolicy
@end rule: attributePersistPolicy

@start rule: allowsSubTypesDoc
         * @@allowsSubTypes
@end rule: allowsSubTypesDoc

@start rule: attributeDefaultJavaDoc
         * @@default @attributeDefault
@end rule: attributeDefaultJavaDoc

@start rule: attributeDerivedDefaultJavaDoc
         * @@derivedDefault @attributeDerivedDefault
@end rule: attributeDerivedDefaultJavaDoc

@start rule: attributeSecureValueJavaDoc
         * @@secureValue @attributeSecureValue
@end rule: attributeSecureValueJavaDoc

@start rule: attributeInterfaceDefaultJavaDoc
         * @@default @attributeInterfaceDefault
@end rule: attributeInterfaceDefaultJavaDoc

@start rule: attributeDefaultStringJavaDoc
         * @@default "@attributeDefaultString"
@end rule: attributeDefaultStringJavaDoc

@start rule: attributeLegalValuesJavaDoc
         * @@legalValues @attributeLegalValues
@end rule: attributeLegalValuesJavaDoc

@start rule: attributeNullJavaDoc
         * @@exclude
@end rule: attributeNullJavaDoc

@start rule: attributeNotWriteableJavaDoc
         * @@non-configurable
         * @@validatePropertyDeclaration false
@end rule: attributeNotWriteableJavaDoc

@start rule: attributeValidatorJavaDoc
         * @@deferredValidator @attributeValidator
@end rule: attributeValidatorJavaDoc

@start rule: deprecatedJavaDoc
         * @@deprecated @attributeDeprecated
@end rule: deprecatedJavaDoc

@start rule: deprecatedAnnotation
         @@Deprecated
@end rule: deprecatedAnnotation

@start rule: sinceJavaDoc
         * @@since @attributeSince
@end rule: sinceJavaDoc

@start rule: excludeJavaDoc
         * @@exclude @attributeExclude
@end rule: excludeJavaDoc


@start rule: restNameJavaDoc
         * @@restName @attributeRestName
@end rule: restNameJavaDoc

@start rule: excludeFromRestJavaDoc
         * @@excludeFromRest @attributeExcludeFromRest
@end rule: excludeFromRestJavaDoc

@start rule: restInternalJavaDoc
         * @@restInternal @attributeRestInternal
@end rule: restInternalJavaDoc

@start rule: visibleToPartitionsJavaDoc
         * @@VisibleToPartitions @attributeVisibleToPartitions
@end rule: visibleToPartitionsJavaDoc

@start rule: mBeanDeprecatedJavaDoc
 * @@deprecated @attributeDeprecated
@end rule: mBeanDeprecatedJavaDoc

@start rule: mBeanDeprecatedAnnotation
 * @@Deprecated
@end rule: mBeanDeprecatedAnnotation

@start rule: mBeanSinceJavaDoc
 * @@since @attributeSince
@end rule: mBeanSinceJavaDoc

@start rule: mBeanExcludeJavaDoc
 * @@exclude @attributeExclude
@end rule: mBeanExcludeJavaDoc

@start rule: abstractJavaDoc
 * @@abstract true
@end rule: abstractJavaDoc

@start rule: mbeanDynamicPolicy
 * @@dynamic @isDynamic
@end rule: mbeanDynamicPolicy

@start rule: mBeanNoDocJavaDoc
 * @@exclude
@end rule: mBeanNoDocJavaDoc

@start rule: mbeanPresentationStringJavaDoc
 * @@xmlTypeName @getXmlTypeNameString
@end rule: mbeanPresentationStringJavaDoc


@start rule: attributeGetter

        /**
         * @description
@allowsSubTypes
@attributeJavaDocComments
         * @@preserveWhiteSpace
         */
@attributeDeprecationAnnotation
        public @attributeType @attributeGetterName ();

@end rule: attributeGetter

@start rule: attributeSetter

        /**
         * @description
@allowsSubTypes
@attributeJavaDocComments
         * @@param newValue - new value for attribute @objectName
         * @@exception InvalidAttributeValueException
         * @@preserveWhiteSpace
         */
@attributeDeprecationAnnotation
        public void set@objectName (@attributeType newValue)
                throws InvalidAttributeValueException;

@end rule: attributeSetter

@start rule: attributeArrayIndexGetter

        /**
         * Array index getter method for array attribute @objectName
         * @description
@allowsSubTypes
@attributeJavaDocComments
         * @@param index- index of entry being returned
         * @@preserveWhiteSpace
         */
@attributeDeprecationAnnotation
        public @attributeTypeSimple @attributeArrayIndexGetterName (int index);

@end rule: attributeArrayIndexGetter

@start rule: attributeArrayIndexSetter

        /**
         * Array index setter method for array attribute @objectName         *
         * @description
@allowsSubTypes
@attributeJavaDocComments
         * @@param index- index of entry being set
         * @@param newValue - new value for attribute @objectName at provided index
         * @@exception InvalidAttributeValueException
         * @@preserveWhiteSpace
         */
@attributeDeprecationAnnotation
        public void @attributeArrayIndexSetterName (int index, @attributeTypeSimple newValue)
                throws InvalidAttributeValueException;

@end rule: attributeArrayIndexSetter

@start rule: attribute
@attributeComments
@attributeMethods
@end rule: attribute
@start rule: operation
        /**
         * @description
         *
@operationParamsDoc
@returnValueDescriptionDoc
@operationExceptionsDoc
@operationJavaDocComments
@generateImplementation
         */
        public @operationReturnType @objectName ( @operationParams )
                @operationExceptions;

@end rule: operation

@start rule: displayName
/**
         * @@default @getDefaultDisplayName
         * @@dynamic false
         * @@owner RealmAdministrator
         * @@VisibleToPartitions ALWAYS
         */
        @dnComment public java.lang.String getName();
@end rule: displayName

@start rule: genEncrypted
  @generateTheEncrypted
@end rule: genEncrypted

@start rule: main
@packageDescriptor
import javax.management.*;
import weblogic.management.commo.RequiredModelMBeanWrapper;

@imports

/**
 * @description
 * @root
 * @@customizer @packageName.@customizer(new RequiredModelMBeanWrapper(this))
@mBeanJavaDocComments
 */
@mBeanAnnotations
public interface @mBeanIntfFileName extends weblogic.management.commo.StandardInterface,weblogic.descriptor.DescriptorBean@extendsSpec {
        @attributeList
        @operationsList
        @displayName
        @genEncrypted
}
@end rule: main
