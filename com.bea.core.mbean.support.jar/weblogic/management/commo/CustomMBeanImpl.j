@start rule: package
package @packageName;

@end rule: package

@start rule: import
import @importName;

@end rule: import

@start rule: mBeanListenerRegistration
      addNotificationListener (new MBeanListener (objName,  // &&& per Listen directive
                                             new MBeanListenerFilter(), null);
@end rule: mBeanListenerRegistration

@start rule: mBeanListenerSupport
   // generic listener (per Listen directive)
   class MBeanListener implements NotificationListener
   {
        ObjectName objName = null;
        MBeanListener (ObjectName objName)
        {
            this. objName = objName;
        }

        public void handleNotification(javax.management.Notification note, Object handback)
        {
            // &&&  ADD YOUR CODE HERE &&&
        }
   }

   // generic notification filter per Listen directive
   class MBeanListenerFilter implements NotificationFilter
   {
        ObjectName objName = null;

        MBeanListener (ObjectName objName)
        {
            this. objName = objName;
        }

        public boolean isNotificationEnabled(Notification notification);
        {
            // &&&  ADD YOUR CODE HERE &&&
            return true;
        }
   }
@end rule: mBeanListenerSupport

@start rule: main
@packageDescriptor
import javax.management.*;
import javax.management.modelmbean.ModelMBean;
import weblogic.management.commo.CommoMBeanInstance;
import java.lang.reflect.*;

@imports
public @checkAbstract class @mBeanImplFileName @extendsSpec
  implements java.io.Serializable
{
   static final long serialVersionUID = @versionID;

   @requiredModelMBeanSpec
   /**
    * @@deprecated Replaced by @mBeanImplFileName (ModelMBean base).
    */
   @@Deprecated
   public @mBeanImplFileName (CommoMBeanInstance base)
        throws MBeanException
   { @constructorBody }


@CheckMBeanListener

   //****************************************************************************************************
   //***************************************** GENERATED METHODS ****************************************
   //****************************************************************************************************
@attributeArrayAccessorMethods
@generatedMethods
   //****************************************************************************************************
   //******************************************* METHODS STUBS ******************************************
   //****************************************************************************************************
//@@constructorMethods
@attributeAccessorMethods
@operationMethods
}

@end rule: main

@start rule: attributeVariableElem
            @attributeVariable,
@end rule: attributeVariableElem

@start rule: attributeDefinition

      //        ***  @objectName ***

      ModelMBeanAttributeInfo @attributeVariable
            = new CommoModelMBeanAttributeInfo (
                    "@objectName",  // name
                    "@attributeType",  // type
                    "@description",  // description
                     @attributeIsReadable, // readable
                     @attributeIsWriteable, // writeable
                     @attributeIsIs );  // boolean with is form

      descr = @attributeVariable.getDescriptor();

      descr.setField ("descriptorType", "attribute"); // required
@attributeDescriptors
      @attributeVariable.setDescriptor (descr);
@end rule: attributeDefinition

@start rule: descriptorOther
      {
        @tagType value = (@tagType)(@tagValue);
        descr.setField("@tagName", value);
      }
@end rule: descriptorOther

@start rule: descriptorString
      descr.setField("@tagName", "@tagValue");
@end rule: descriptorString

@start rule: attributeVariable
mmai_@objectName
@end rule: attributeVariable

@start rule: operationGetterDefinition
      //        ***  generated getter operation for @objectName ***

      Method @attributeGetMethodName@count =
        cl.getMethod ("@attributeGetterName",new Class[]{});
      ModelMBeanOperationInfo mmoi_@attributeGetMethodName@count =
        new CommoModelMBeanOperationInfo ("", @attributeGetMethodName@count);

      descr = mmoi_@attributeGetMethodName@count.getDescriptor();
      descr.removeField ("class");
      mmoi_@attributeGetMethodName@count.setDescriptor (descr);

@end rule: operationGetterDefinition

@start rule: operationSetterDefinition
      //        ***  generated setter operation for @objectName ***

      Method @attributeSetMethodName@count =
        cl.getMethod ("@attributeSetterName",new Class[]{ @attributeTypeType });
      ModelMBeanOperationInfo mmoi_@attributeSetMethodName@count =
        new CommoModelMBeanOperationInfo ("", @attributeSetMethodName@count );

      descr = mmoi_@attributeSetMethodName@count.getDescriptor();
      descr.removeField ("class");
      mmoi_@attributeSetMethodName@count.setDescriptor (descr);

@end rule: operationSetterDefinition

@start rule: operationElementAcessorDefinitions
      //        ***  extended index get method for array @objectName ***

      Method @attributeArrayIndexGetterName@count =
        cl.getMethod ("@attributeArrayIndexGetterName",new Class[]{ Integer.TYPE });
      ModelMBeanOperationInfo mmoi_@attributeArrayIndexGetterName@count =
        new CommoModelMBeanOperationInfo ("", @attributeArrayIndexGetterName@count );

      descr = mmoi_@attributeArrayIndexGetterName@count.getDescriptor();
      descr.removeField ("class");
      mmoi_@attributeArrayIndexGetterName@count.setDescriptor (descr);

      //        ***  extended index set method for array @objectName ***


      Method @attributeArrayIndexSetterName@count =
        cl.getMethod ("@attributeArrayIndexSetterName",new Class[]{ Integer.TYPE, @attributeTypeTypeSimple });
      ModelMBeanOperationInfo mmoi_@attributeArrayIndexSetterName@count =
        new CommoModelMBeanOperationInfo ("", @attributeArrayIndexSetterName@count );

      descr = mmoi_@attributeArrayIndexSetterName@count.getDescriptor();
      descr.removeField ("class");
      mmoi_@attributeArrayIndexSetterName@count.setDescriptor (descr);

@end rule: operationElementAcessorDefinitions

@start rule: operationDefinition

      //        ***  @objectName  ***

      ModelMBeanOperationInfo mmoi_@objectName@count = null;
      {
        Class[] params = new Class[]
        {
            @operationParamTypeList
        };
        Method @objectName =
          cl.getMethod ("@objectName",params);
        mmoi_@objectName@count =
          new CommoModelMBeanOperationInfo ("", @objectName );

        descr = mmoi_@objectName@count.getDescriptor();

        descr.setField ("descriptorType", "operation"); // required
        descr.setField ("role","operation"); // required
@operationDescriptors
        mmoi_@objectName@count.setDescriptor (descr);
      }
@end rule: operationDefinition

@start rule: operationVariable
mmoi_@objectName@count
@end rule: operationVariable

@start rule: operationGetterVariable
mmoi_@attributeGetMethodName@count
@end rule: operationGetterVariable

@start rule: operationSetterVariable
mmoi_@attributeSetMethodName@count
@end rule: operationSetterVariable

@start rule: operationIndexSetterVariable
mmoi_@attributeArrayIndexSetterName@count
@end rule: operationIndexSetterVariable

@start rule: operationIndexGetterVariable
mmoi_@attributeArrayIndexGetterName@count
@end rule: operationIndexGetterVariable

@start rule: constructorVariableElem
            @constructorVariable,
@end rule: constructorVariableElem

@start rule: operationVariableElem
            @operationVariable,
@end rule: operationVariableElem

@start rule: operationGetterVariableElem
            @operationGetterVariable,
@end rule: operationGetterVariableElem

@start rule: operationSetterVariableElem
            @operationSetterVariable,
@end rule: operationSetterVariableElem

@start rule: operationIndexAccessorVariableElems
            @operationIndexGetterVariable,
            @operationIndexSetterVariable,
@end rule: operationIndexAccessorVariableElems

@start rule: constructorDefinition

      //        ***  constructor @count for @mBeanImplFileName  ***

      ModelMBeanConstructorInfo mmci_@mBeanImplFileName@count = null;
      {
        Class[] params = new Class[]
        {
            @operationParamTypeList
        };
        Constructor @mBeanImplFileName =
          cl.getConstructor (params);
        mmci_@mBeanImplFileName@count =
          new CommoModelMBeanConstructorInfo ("", @mBeanImplFileName );

        descr = mmci_@mBeanImplFileName@count.getDescriptor();

        descr.setField ("descriptorType", "operation"); // required
        descr.setField ("role","constructor"); // required
@constructorDescriptors
        mmci_@mBeanImplFileName@count.setDescriptor (descr);
      }
@end rule: constructorDefinition

@start rule: constructorVariable
mmci_@mBeanImplFileName@count
@end rule: constructorVariable

@start rule: notificationDefinition

      //        ***  Notification: [@notificationTypes] ***

      ModelMBeanNotificationInfo @notificationVariable
            = new CommoModelMBeanNotificationInfo (
                    new String[]{"@notificationTypes"},  // type strings
                    "@className", // class
                    "@description" );  // description

      descr = @notificationVariable.getDescriptor();

      descr.setField ("descriptorType", "notification"); // required
@notificationDescriptors
      @notificationVariable.setDescriptor (descr);
@end rule: notificationDefinition

@start rule: notificationClassDefinition
//notificationclass_TBD
@end rule: notificationClassDefinition

@start rule: getterMethodDefinition

        // *****************************************************************************************
        //    Get Method for Attribute @objectName
        // *****************************************************************************************

        /**
         * Get method for attribute @objectName
         */

        public @attributeType @attributeGetterName ()
        {
                @attributeType returnValue = @attributeValue;

                // &&& add your code here

                return returnValue;
        }

@end rule: getterMethodDefinition

@start rule: setterMethodDefinition

        // *****************************************************************************************
        //    Set Method for Attribute @objectName
        // *****************************************************************************************

        /**
         * Set method for attribute @objectName
         *
         * @@param newValue - new value for attribute @objectName
         * @@exception InvalidAttributeValueException
         */

        public void @attributeSetterName (@attributeType newValue)
                throws InvalidAttributeValueException
        {

                // &&& add your code here

        }

@end rule: setterMethodDefinition

@start rule: validatorMethodDefinition

        // *****************************************************************************************
        //    Validator Method for Attribute @objectName
        // *****************************************************************************************

        /**
         * Validator method for attribute @objectName
         *
         * @@param candidateValue - potential new value for attribute @objectName
         * @@exception InvalidAttributeValueException
         */

        public Boolean @attributeValidatorName (@attributeType candidateValue)
                throws InvalidAttributeValueException
        {
                Boolean returnValue = new Boolean(true);

                // &&& add your code here

                return returnValue;
        }
@end rule: validatorMethodDefinition

@start rule: arrayIndexGetterMethodDefinition

        // *****************************************************************************************
        //    Array Index Get Method for Attribute @objectName
        // *****************************************************************************************

        /**
         * Array index getter method for array attribute @objectName
         *
         * @@param index- index of entry being returned
         */

        public @attributeTypeSimple @attributeArrayIndexGetterName (int index)
        {
                try
                {
                        @attributeType array = (@attributeType)getRequiredModelMBean().getAttribute ("@objectName");
                        return array[index];
                }
                catch ( ReflectionException rx ) { rx.printStackTrace(); }
                catch ( MBeanException mx ) { mx.printStackTrace(); }
                catch ( AttributeNotFoundException mx ) { mx.printStackTrace(); }
                return null;
        }

@end rule: arrayIndexGetterMethodDefinition

@start rule: arrayIndexSetterMethodDefinition

        // *****************************************************************************************
        //    Array Index Set Method for Attribute @objectName
        // *****************************************************************************************

        /**
         * Array index setter method for array attribute @objectName
         *
         * @@param index- index of entry being set
         * @@param newValue - new value for attribute @objectName at provided index
         * @@exception InvalidAttributeValueException
         */

        public void @attributeArrayIndexSetterName (int index, @attributeTypeSimple newValue)
                throws InvalidAttributeValueException
        {
                try
                {
                        @attributeType array = (@attributeType)getRequiredModelMBean().getAttribute ("@objectName");
                        array [index] = newValue;
                        Attribute attribute = new Attribute ("@objectName", array);
                        getRequiredModelMBean().setAttribute (attribute);
                }
                catch ( ReflectionException rx ) { rx.printStackTrace(); }
                catch ( MBeanException mx ) { mx.printStackTrace(); }
                catch ( AttributeNotFoundException mx ) { mx.printStackTrace(); }
      }

@end rule: arrayIndexSetterMethodDefinition

@start rule: operationMethodDefinition

        // *****************************************************************************************
        //    Operation: @objectName
        // *****************************************************************************************

        /**
         * @description
         *
@operationParamsDoc
@operationExceptionsDoc
         */

        public @operationReturnType @objectName( @operationParams )
                throws MBeanException @operationExceptions
        {
@operationBody
        }

@end rule: operationMethodDefinition

@start rule: constructorMethodDefinition

        // *****************************************************************************************
        //    Constructor for @mBeanImplFileName
        // *****************************************************************************************

        /**
         * @description
         *
@operationParamsDoc
@operationExceptionsDoc
         */

        public @mBeanImplFileName ( @operationParams )
                throws MBeanException,
                ClassNotFoundException, MBeanException,
                NoSuchMethodException, InvalidTargetObjectTypeException,
                InstanceNotFoundException @operationExceptions
        {
                super();
                init_@mBeanImplFileName ();

                // &&& add your code here

        }

@end rule: constructorMethodDefinition

@start rule: operationParamDoc
         * @@param @paramName - @paramDescription
@end rule: operationParamDoc

@start rule: operationExceptionDoc
         * @@exception @exceptionName
@end rule: operationExceptionDoc
