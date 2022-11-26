package com.bea.xbean.schema;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.XBeanDebug;
import com.bea.xml.SchemaAttributeModel;
import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaIdentityConstraint;
import com.bea.xml.SchemaLocalAttribute;
import com.bea.xml.SchemaLocalElement;
import com.bea.xml.SchemaParticle;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlError;
import com.bea.xml.XmlID;
import com.bea.xml.XmlNOTATION;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;

public class StscChecker {
   public static void checkAll() {
      StscState state = StscState.get();
      List allSeenTypes = new ArrayList();
      allSeenTypes.addAll(Arrays.asList(state.documentTypes()));
      allSeenTypes.addAll(Arrays.asList(state.attributeTypes()));
      allSeenTypes.addAll(Arrays.asList(state.redefinedGlobalTypes()));
      allSeenTypes.addAll(Arrays.asList(state.globalTypes()));

      for(int i = 0; i < allSeenTypes.size(); ++i) {
         SchemaType gType = (SchemaType)allSeenTypes.get(i);
         if (!state.noPvr() && !gType.isDocumentType()) {
            checkRestriction((SchemaTypeImpl)gType);
         }

         checkFields((SchemaTypeImpl)gType);
         allSeenTypes.addAll(Arrays.asList(gType.getAnonymousTypes()));
      }

      checkSubstitutionGroups(state.globalElements());
   }

   public static void checkFields(SchemaTypeImpl sType) {
      if (!sType.isSimpleType()) {
         XmlObject location = sType.getParseObject();
         SchemaAttributeModel sAttrModel = sType.getAttributeModel();
         if (sAttrModel != null) {
            SchemaLocalAttribute[] sAttrs = sAttrModel.getAttributes();
            QName idAttr = null;

            for(int i = 0; i < sAttrs.length; ++i) {
               XmlObject attrLocation = ((SchemaLocalAttributeImpl)sAttrs[i])._parseObject;
               if (XmlID.type.isAssignableFrom(sAttrs[i].getType())) {
                  if (idAttr == null) {
                     idAttr = sAttrs[i].getName();
                  } else {
                     StscState.get().error("ag-props-correct.3", new Object[]{QNameHelper.pretty(idAttr), sAttrs[i].getName()}, attrLocation != null ? attrLocation : location);
                  }

                  if (sAttrs[i].getDefaultText() != null) {
                     StscState.get().error("a-props-correct.3", (Object[])null, attrLocation != null ? attrLocation : location);
                  }
               } else if (XmlNOTATION.type.isAssignableFrom(sAttrs[i].getType())) {
                  if (sAttrs[i].getType().getBuiltinTypeCode() == 8) {
                     StscState.get().recover("enumeration-required-notation-attr", new Object[]{QNameHelper.pretty(sAttrs[i].getName())}, attrLocation != null ? attrLocation : location);
                  } else {
                     if (sAttrs[i].getType().getSimpleVariety() == 2) {
                        SchemaType[] members = sAttrs[i].getType().getUnionConstituentTypes();

                        for(int j = 0; j < members.length; ++j) {
                           if (members[j].getBuiltinTypeCode() == 8) {
                              StscState.get().recover("enumeration-required-notation-attr", new Object[]{QNameHelper.pretty(sAttrs[i].getName())}, attrLocation != null ? attrLocation : location);
                           }
                        }
                     }

                     boolean hasNS;
                     if (sType.isAttributeType()) {
                        hasNS = sAttrs[i].getName().getNamespaceURI().length() > 0;
                     } else {
                        Object t;
                        for(t = sType; ((SchemaType)t).getOuterType() != null; t = ((SchemaType)t).getOuterType()) {
                        }

                        if (((SchemaType)t).isDocumentType()) {
                           hasNS = ((SchemaType)t).getDocumentElementName().getNamespaceURI().length() > 0;
                        } else {
                           hasNS = ((SchemaType)t).getName().getNamespaceURI().length() > 0;
                        }
                     }

                     if (hasNS) {
                        StscState.get().warning("notation-targetns-attr", new Object[]{QNameHelper.pretty(sAttrs[i].getName())}, attrLocation != null ? attrLocation : location);
                     }
                  }
               } else {
                  String valueConstraint = sAttrs[i].getDefaultText();
                  if (valueConstraint != null) {
                     try {
                        XmlAnySimpleType val = sAttrs[i].getDefaultValue();
                        if (!val.validate()) {
                           throw new Exception();
                        }

                        SchemaPropertyImpl sProp = (SchemaPropertyImpl)sType.getAttributeProperty(sAttrs[i].getName());
                        if (sProp != null && sProp.getDefaultText() != null) {
                           sProp.setDefaultValue(new XmlValueRef(val));
                        }
                     } catch (Exception var11) {
                        String constraintName = sAttrs[i].isFixed() ? "fixed" : "default";
                        XmlObject constraintLocation = location;
                        if (attrLocation != null) {
                           constraintLocation = attrLocation.selectAttribute("", constraintName);
                           if (constraintLocation == null) {
                              constraintLocation = attrLocation;
                           }
                        }

                        StscState.get().error("a-props-correct.2", new Object[]{QNameHelper.pretty(sAttrs[i].getName()), constraintName, valueConstraint, QNameHelper.pretty(sAttrs[i].getType().getName())}, constraintLocation);
                     }
                  }
               }
            }
         }

         checkElementDefaults(sType.getContentModel(), location, sType);
      }
   }

   private static void checkElementDefaults(SchemaParticle model, XmlObject location, SchemaType parentType) {
      if (model != null) {
         switch (model.getParticleType()) {
            case 1:
            case 2:
            case 3:
               SchemaParticle[] children = model.getParticleChildren();

               for(int i = 0; i < children.length; ++i) {
                  checkElementDefaults(children[i], location, parentType);
               }

               return;
            case 4:
               String valueConstraint = model.getDefaultText();
               String warningType;
               if (valueConstraint != null) {
                  if (!model.getType().isSimpleType() && model.getType().getContentType() != 2) {
                     if (model.getType().getContentType() == 4) {
                        if (!model.getType().getContentModel().isSkippable()) {
                           warningType = model.isFixed() ? "fixed" : "default";
                           XmlObject constraintLocation = location.selectAttribute("", warningType);
                           StscState.get().error("cos-valid-default.2.2.2", new Object[]{QNameHelper.pretty(model.getName()), warningType, valueConstraint}, constraintLocation == null ? location : constraintLocation);
                        } else {
                           SchemaPropertyImpl sProp = (SchemaPropertyImpl)parentType.getElementProperty(model.getName());
                           if (sProp != null && sProp.getDefaultText() != null) {
                              sProp.setDefaultValue(new XmlValueRef(XmlString.type.newValue(valueConstraint)));
                           }
                        }
                     } else {
                        XmlObject constraintLocation;
                        if (model.getType().getContentType() == 3) {
                           constraintLocation = location.selectAttribute("", "default");
                           StscState.get().error("cos-valid-default.2.1", new Object[]{QNameHelper.pretty(model.getName()), valueConstraint, "element"}, constraintLocation == null ? location : constraintLocation);
                        } else if (model.getType().getContentType() == 1) {
                           constraintLocation = location.selectAttribute("", "default");
                           StscState.get().error("cos-valid-default.2.1", new Object[]{QNameHelper.pretty(model.getName()), valueConstraint, "empty"}, constraintLocation == null ? location : constraintLocation);
                        }
                     }
                  } else {
                     try {
                        XmlAnySimpleType val = model.getDefaultValue();
                        XmlOptions opt = new XmlOptions();
                        opt.put("VALIDATE_TEXT_ONLY");
                        if (!val.validate(opt)) {
                           throw new Exception();
                        }

                        SchemaPropertyImpl sProp = (SchemaPropertyImpl)parentType.getElementProperty(model.getName());
                        if (sProp != null && sProp.getDefaultText() != null) {
                           sProp.setDefaultValue(new XmlValueRef(val));
                        }
                     } catch (Exception var8) {
                        String constraintName = model.isFixed() ? "fixed" : "default";
                        XmlObject constraintLocation = location.selectAttribute("", constraintName);
                        StscState.get().error("e-props-correct.2", new Object[]{QNameHelper.pretty(model.getName()), constraintName, valueConstraint, QNameHelper.pretty(model.getType().getName())}, constraintLocation == null ? location : constraintLocation);
                     }
                  }
               }

               warningType = null;
               if (BuiltinSchemaTypeSystem.ST_ID.isAssignableFrom(model.getType())) {
                  warningType = BuiltinSchemaTypeSystem.ST_ID.getName().getLocalPart();
               } else if (BuiltinSchemaTypeSystem.ST_IDREF.isAssignableFrom(model.getType())) {
                  warningType = BuiltinSchemaTypeSystem.ST_IDREF.getName().getLocalPart();
               } else if (BuiltinSchemaTypeSystem.ST_IDREFS.isAssignableFrom(model.getType())) {
                  warningType = BuiltinSchemaTypeSystem.ST_IDREFS.getName().getLocalPart();
               } else if (BuiltinSchemaTypeSystem.ST_ENTITY.isAssignableFrom(model.getType())) {
                  warningType = BuiltinSchemaTypeSystem.ST_ENTITY.getName().getLocalPart();
               } else if (BuiltinSchemaTypeSystem.ST_ENTITIES.isAssignableFrom(model.getType())) {
                  warningType = BuiltinSchemaTypeSystem.ST_ENTITIES.getName().getLocalPart();
               } else if (BuiltinSchemaTypeSystem.ST_NOTATION.isAssignableFrom(model.getType())) {
                  if (model.getType().getBuiltinTypeCode() == 8) {
                     StscState.get().recover("enumeration-required-notation-elem", new Object[]{QNameHelper.pretty(model.getName())}, ((SchemaLocalElementImpl)model)._parseObject == null ? location : ((SchemaLocalElementImpl)model)._parseObject.selectAttribute("", "type"));
                  } else {
                     if (model.getType().getSimpleVariety() == 2) {
                        SchemaType[] members = model.getType().getUnionConstituentTypes();

                        for(int i = 0; i < members.length; ++i) {
                           if (members[i].getBuiltinTypeCode() == 8) {
                              StscState.get().recover("enumeration-required-notation-elem", new Object[]{QNameHelper.pretty(model.getName())}, ((SchemaLocalElementImpl)model)._parseObject == null ? location : ((SchemaLocalElementImpl)model)._parseObject.selectAttribute("", "type"));
                           }
                        }
                     }

                     warningType = BuiltinSchemaTypeSystem.ST_NOTATION.getName().getLocalPart();
                  }

                  SchemaType t;
                  for(t = parentType; t.getOuterType() != null; t = t.getOuterType()) {
                  }

                  boolean hasNS;
                  if (t.isDocumentType()) {
                     hasNS = t.getDocumentElementName().getNamespaceURI().length() > 0;
                  } else {
                     hasNS = t.getName().getNamespaceURI().length() > 0;
                  }

                  if (hasNS) {
                     StscState.get().warning("notation-targetns-elem", new Object[]{QNameHelper.pretty(model.getName())}, ((SchemaLocalElementImpl)model)._parseObject == null ? location : ((SchemaLocalElementImpl)model)._parseObject);
                  }
               }

               if (warningType != null) {
                  StscState.get().warning("id-idref-idrefs-entity-entities-notation", new Object[]{QNameHelper.pretty(model.getName()), warningType}, ((SchemaLocalElementImpl)model)._parseObject == null ? location : ((SchemaLocalElementImpl)model)._parseObject.selectAttribute("", "type"));
               }
         }

      }
   }

   public static boolean checkRestriction(SchemaTypeImpl sType) {
      if (sType.getDerivationType() == 1 && !sType.isSimpleType()) {
         StscState state = StscState.get();
         XmlObject location = sType.getParseObject();
         SchemaType baseType = sType.getBaseType();
         if (baseType.isSimpleType()) {
            state.error("src-ct.1", new Object[]{QNameHelper.pretty(baseType.getName())}, location);
            return false;
         }

         switch (sType.getContentType()) {
            case 1:
               switch (baseType.getContentType()) {
                  case 1:
                     return true;
                  case 2:
                  default:
                     state.error("derivation-ok-restriction.5.2", (Object[])null, location);
                     return false;
                  case 3:
                  case 4:
                     if (baseType.getContentModel() != null && !baseType.getContentModel().isSkippable()) {
                        state.error("derivation-ok-restriction.5.2.2", (Object[])null, location);
                        return false;
                     }

                     return true;
               }
            case 2:
               switch (baseType.getContentType()) {
                  case 2:
                     SchemaType cType = sType.getContentBasedOnType();
                     if (cType != baseType) {
                        SchemaType bType;
                        for(bType = baseType; bType != null && !bType.isSimpleType(); bType = bType.getContentBasedOnType()) {
                        }

                        if (bType != null && !bType.isAssignableFrom(cType)) {
                           state.error("derivation-ok-restriction.5.2.2.1", (Object[])null, location);
                           return false;
                        }
                     }

                     return true;
                  case 4:
                     if (baseType.getContentModel() != null && !baseType.getContentModel().isSkippable()) {
                        state.error("derivation-ok-restriction.5.1.2", (Object[])null, location);
                        return false;
                     }

                     return true;
                  default:
                     state.error("derivation-ok-restriction.5.1", (Object[])null, location);
                     return false;
               }
            case 4:
               if (baseType.getContentType() != 4) {
                  state.error("derivation-ok-restriction.5.3a", (Object[])null, location);
                  return false;
               }
            case 3:
               if (baseType.getContentType() == 1) {
                  state.error("derivation-ok-restriction.5.3b", (Object[])null, location);
                  return false;
               }

               if (baseType.getContentType() == 2) {
                  state.error("derivation-ok-restriction.5.3c", (Object[])null, location);
                  return false;
               }

               SchemaParticle baseModel = baseType.getContentModel();
               SchemaParticle derivedModel = sType.getContentModel();
               if (derivedModel == null && sType.getDerivationType() == 1) {
                  return true;
               }

               if (baseModel == null || derivedModel == null) {
                  XBeanDebug.logStackTrace("Null models that weren't caught by EMPTY_CONTENT: " + baseType + " (" + baseModel + "), " + sType + " (" + derivedModel + ")");
                  state.error("derivation-ok-restriction.5.3", (Object[])null, location);
                  return false;
               }

               List errors = new ArrayList();
               boolean isValid = isParticleValidRestriction(baseModel, derivedModel, errors, location);
               if (!isValid) {
                  if (errors.size() == 0) {
                     state.error("derivation-ok-restriction.5.3", (Object[])null, location);
                  } else {
                     state.getErrorListener().add(errors.get(errors.size() - 1));
                  }

                  return false;
               }
         }
      }

      return true;
   }

   public static boolean isParticleValidRestriction(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      boolean restrictionValid = false;
      if (baseModel.equals(derivedModel)) {
         restrictionValid = true;
      } else {
         switch (baseModel.getParticleType()) {
            case 1:
               switch (derivedModel.getParticleType()) {
                  case 1:
                     restrictionValid = recurse(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 2:
                  case 5:
                     errors.add(XmlError.forObject("cos-particle-restrict.2", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
                     restrictionValid = false;
                     return restrictionValid;
                  case 3:
                     restrictionValid = recurseUnordered(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 4:
                     restrictionValid = recurseAsIfGroup(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  default:
                     assert false : XBeanDebug.logStackTrace("Unknown schema type for Derived Type");

                     return restrictionValid;
               }
            case 2:
               switch (derivedModel.getParticleType()) {
                  case 1:
                  case 5:
                     errors.add(XmlError.forObject("cos-particle-restrict.2", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
                     restrictionValid = false;
                     return restrictionValid;
                  case 2:
                     restrictionValid = recurseLax(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 3:
                     restrictionValid = mapAndSum(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 4:
                     restrictionValid = recurseAsIfGroup(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  default:
                     assert false : XBeanDebug.logStackTrace("Unknown schema type for Derived Type");

                     return restrictionValid;
               }
            case 3:
               switch (derivedModel.getParticleType()) {
                  case 1:
                  case 2:
                  case 5:
                     errors.add(XmlError.forObject("cos-particle-restrict.2", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
                     restrictionValid = false;
                     return restrictionValid;
                  case 3:
                     restrictionValid = recurse(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 4:
                     restrictionValid = recurseAsIfGroup(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  default:
                     assert false : XBeanDebug.logStackTrace("Unknown schema type for Derived Type");

                     return restrictionValid;
               }
            case 4:
               switch (derivedModel.getParticleType()) {
                  case 1:
                  case 2:
                  case 3:
                  case 5:
                     errors.add(XmlError.forObject("cos-particle-restrict.2", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
                     restrictionValid = false;
                     return restrictionValid;
                  case 4:
                     restrictionValid = nameAndTypeOK((SchemaLocalElement)baseModel, (SchemaLocalElement)derivedModel, errors, context);
                     return restrictionValid;
                  default:
                     assert false : XBeanDebug.logStackTrace("Unknown schema type for Derived Type");

                     return restrictionValid;
               }
            case 5:
               switch (derivedModel.getParticleType()) {
                  case 1:
                     restrictionValid = nsRecurseCheckCardinality(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 2:
                     restrictionValid = nsRecurseCheckCardinality(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 3:
                     restrictionValid = nsRecurseCheckCardinality(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  case 4:
                     restrictionValid = nsCompat(baseModel, (SchemaLocalElement)derivedModel, errors, context);
                     return restrictionValid;
                  case 5:
                     restrictionValid = nsSubset(baseModel, derivedModel, errors, context);
                     return restrictionValid;
                  default:
                     assert false : XBeanDebug.logStackTrace("Unknown schema type for Derived Type");

                     return restrictionValid;
               }
            default:
               assert false : XBeanDebug.logStackTrace("Unknown schema type for Base Type");
         }
      }

      return restrictionValid;
   }

   private static boolean mapAndSum(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      assert baseModel.getParticleType() == 2;

      assert derivedModel.getParticleType() == 3;

      boolean mapAndSumValid = true;
      SchemaParticle[] derivedParticleArray = derivedModel.getParticleChildren();
      SchemaParticle[] baseParticleArray = baseModel.getParticleChildren();

      SchemaParticle derivedParticle;
      for(int i = 0; i < derivedParticleArray.length; ++i) {
         derivedParticle = derivedParticleArray[i];
         boolean foundMatch = false;

         for(int j = 0; j < baseParticleArray.length; ++j) {
            SchemaParticle baseParticle = baseParticleArray[j];
            if (isParticleValidRestriction(baseParticle, derivedParticle, errors, context)) {
               foundMatch = true;
               break;
            }
         }

         if (!foundMatch) {
            mapAndSumValid = false;
            errors.add(XmlError.forObject("rcase-MapAndSum.1", new Object[]{printParticle(derivedParticle)}, context));
            return false;
         }
      }

      BigInteger derivedRangeMin = derivedModel.getMinOccurs().multiply(BigInteger.valueOf((long)derivedModel.getParticleChildren().length));
      derivedParticle = null;
      BigInteger UNBOUNDED = null;
      BigInteger derivedRangeMax;
      if (derivedModel.getMaxOccurs() == UNBOUNDED) {
         derivedRangeMax = null;
      } else {
         derivedRangeMax = derivedModel.getMaxOccurs().multiply(BigInteger.valueOf((long)derivedModel.getParticleChildren().length));
      }

      if (derivedRangeMin.compareTo(baseModel.getMinOccurs()) < 0) {
         mapAndSumValid = false;
         errors.add(XmlError.forObject("rcase-MapAndSum.2a", new Object[]{derivedRangeMin.toString(), baseModel.getMinOccurs().toString()}, context));
      } else if (baseModel.getMaxOccurs() != UNBOUNDED && (derivedRangeMax == UNBOUNDED || derivedRangeMax.compareTo(baseModel.getMaxOccurs()) > 0)) {
         mapAndSumValid = false;
         errors.add(XmlError.forObject("rcase-MapAndSum.2b", new Object[]{derivedRangeMax == UNBOUNDED ? "unbounded" : derivedRangeMax.toString(), baseModel.getMaxOccurs().toString()}, context));
      }

      return mapAndSumValid;
   }

   private static boolean recurseAsIfGroup(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      assert baseModel.getParticleType() == 1 && derivedModel.getParticleType() == 4 || baseModel.getParticleType() == 2 && derivedModel.getParticleType() == 4 || baseModel.getParticleType() == 3 && derivedModel.getParticleType() == 4;

      SchemaParticleImpl asIfPart = new SchemaParticleImpl();
      asIfPart.setParticleType(baseModel.getParticleType());
      asIfPart.setMinOccurs(BigInteger.ONE);
      asIfPart.setMaxOccurs(BigInteger.ONE);
      asIfPart.setParticleChildren(new SchemaParticle[]{derivedModel});
      return isParticleValidRestriction(baseModel, asIfPart, errors, context);
   }

   private static boolean recurseLax(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      assert baseModel.getParticleType() == 2 && derivedModel.getParticleType() == 2;

      boolean recurseLaxValid = true;
      if (!occurrenceRangeOK(baseModel, derivedModel, errors, context)) {
         return false;
      } else {
         SchemaParticle[] derivedParticleArray = derivedModel.getParticleChildren();
         SchemaParticle[] baseParticleArray = baseModel.getParticleChildren();
         int i = 0;
         int j = 0;

         while(i < derivedParticleArray.length && j < baseParticleArray.length) {
            SchemaParticle derivedParticle = derivedParticleArray[i];
            SchemaParticle baseParticle = baseParticleArray[j];
            if (isParticleValidRestriction(baseParticle, derivedParticle, errors, context)) {
               ++i;
               ++j;
            } else {
               ++j;
            }
         }

         if (i < derivedParticleArray.length) {
            recurseLaxValid = false;
            errors.add(XmlError.forObject("rcase-RecurseLax.2", new Object[]{printParticles(baseParticleArray, i)}, context));
         }

         return recurseLaxValid;
      }
   }

   private static boolean recurseUnordered(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      assert baseModel.getParticleType() == 1 && derivedModel.getParticleType() == 3;

      boolean recurseUnorderedValid = true;
      if (!occurrenceRangeOK(baseModel, derivedModel, errors, context)) {
         return false;
      } else {
         SchemaParticle[] baseParticles = baseModel.getParticleChildren();
         HashMap baseParticleMap = new HashMap(10);
         Object MAPPED = new Object();

         for(int i = 0; i < baseParticles.length; ++i) {
            baseParticleMap.put(baseParticles[i].getName(), baseParticles[i]);
         }

         SchemaParticle[] derivedParticles = derivedModel.getParticleChildren();

         for(int i = 0; i < derivedParticles.length; ++i) {
            Object baseParticle = baseParticleMap.get(derivedParticles[i].getName());
            if (baseParticle == null) {
               recurseUnorderedValid = false;
               errors.add(XmlError.forObject("rcase-RecurseUnordered.2", new Object[]{printParticle(derivedParticles[i])}, context));
               break;
            }

            if (baseParticle == MAPPED) {
               recurseUnorderedValid = false;
               errors.add(XmlError.forObject("rcase-RecurseUnordered.2.1", new Object[]{printParticle(derivedParticles[i])}, context));
               break;
            }

            SchemaParticle matchedBaseParticle = (SchemaParticle)baseParticle;
            if (derivedParticles[i].getMaxOccurs() == null || derivedParticles[i].getMaxOccurs().compareTo(BigInteger.ONE) > 0) {
               recurseUnorderedValid = false;
               errors.add(XmlError.forObject("rcase-RecurseUnordered.2.2a", new Object[]{printParticle(derivedParticles[i]), printMaxOccurs(derivedParticles[i].getMinOccurs())}, context));
               break;
            }

            if (!isParticleValidRestriction(matchedBaseParticle, derivedParticles[i], errors, context)) {
               recurseUnorderedValid = false;
               break;
            }

            baseParticleMap.put(derivedParticles[i].getName(), MAPPED);
         }

         if (recurseUnorderedValid) {
            Set baseParticleCollection = baseParticleMap.keySet();
            Iterator iterator = baseParticleCollection.iterator();

            while(iterator.hasNext()) {
               QName baseParticleQName = (QName)iterator.next();
               if (baseParticleMap.get(baseParticleQName) != MAPPED && !((SchemaParticle)baseParticleMap.get(baseParticleQName)).isSkippable()) {
                  recurseUnorderedValid = false;
                  errors.add(XmlError.forObject("rcase-RecurseUnordered.2.3", new Object[]{printParticle((SchemaParticle)baseParticleMap.get(baseParticleQName))}, context));
               }
            }
         }

         return recurseUnorderedValid;
      }
   }

   private static boolean recurse(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      boolean recurseValid = true;
      if (!occurrenceRangeOK(baseModel, derivedModel, errors, context)) {
         return false;
      } else {
         SchemaParticle[] derivedParticleArray = derivedModel.getParticleChildren();
         SchemaParticle[] baseParticleArray = baseModel.getParticleChildren();
         int i = 0;
         int j = 0;

         while(i < derivedParticleArray.length && j < baseParticleArray.length) {
            SchemaParticle derivedParticle = derivedParticleArray[i];
            SchemaParticle baseParticle = baseParticleArray[j];
            if (isParticleValidRestriction(baseParticle, derivedParticle, errors, context)) {
               ++i;
               ++j;
            } else {
               if (!baseParticle.isSkippable()) {
                  recurseValid = false;
                  errors.add(XmlError.forObject("rcase-Recurse.2.1", new Object[]{printParticle(derivedParticle), printParticle(derivedModel), printParticle(baseParticle), printParticle(baseModel)}, context));
                  break;
               }

               ++j;
            }
         }

         if (i < derivedParticleArray.length) {
            recurseValid = false;
            errors.add(XmlError.forObject("rcase-Recurse.2", new Object[]{printParticle(derivedModel), printParticle(baseModel), printParticles(derivedParticleArray, i)}, context));
         } else if (j < baseParticleArray.length) {
            ArrayList particles = new ArrayList(baseParticleArray.length);

            for(int k = j; k < baseParticleArray.length; ++k) {
               if (!baseParticleArray[k].isSkippable()) {
                  particles.add(baseParticleArray[k]);
               }
            }

            if (particles.size() > 0) {
               recurseValid = false;
               errors.add(XmlError.forObject("rcase-Recurse.2.2", new Object[]{printParticle(baseModel), printParticle(derivedModel), printParticles((List)particles)}, context));
            }
         }

         return recurseValid;
      }
   }

   private static boolean nsRecurseCheckCardinality(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      assert baseModel.getParticleType() == 5;

      assert derivedModel.getParticleType() == 1 || derivedModel.getParticleType() == 2 || derivedModel.getParticleType() == 3;

      boolean nsRecurseCheckCardinality = true;
      SchemaParticleImpl asIfPart = new SchemaParticleImpl();
      asIfPart.setParticleType(baseModel.getParticleType());
      asIfPart.setWildcardProcess(baseModel.getWildcardProcess());
      asIfPart.setWildcardSet(baseModel.getWildcardSet());
      asIfPart.setMinOccurs(BigInteger.ZERO);
      asIfPart.setMaxOccurs((BigInteger)null);
      asIfPart.setTransitionRules(baseModel.getWildcardSet(), true);
      asIfPart.setTransitionNotes(baseModel.getWildcardSet(), true);
      SchemaParticle[] particleChildren = derivedModel.getParticleChildren();

      for(int i = 0; i < particleChildren.length; ++i) {
         SchemaParticle particle = particleChildren[i];
         switch (particle.getParticleType()) {
            case 1:
            case 2:
            case 3:
               nsRecurseCheckCardinality = nsRecurseCheckCardinality(asIfPart, particle, errors, context);
               break;
            case 4:
               nsRecurseCheckCardinality = nsCompat(asIfPart, (SchemaLocalElement)particle, errors, context);
               break;
            case 5:
               nsRecurseCheckCardinality = nsSubset(asIfPart, particle, errors, context);
         }

         if (!nsRecurseCheckCardinality) {
            break;
         }
      }

      if (nsRecurseCheckCardinality) {
         nsRecurseCheckCardinality = checkGroupOccurrenceOK(baseModel, derivedModel, errors, context);
      }

      return nsRecurseCheckCardinality;
   }

   private static boolean checkGroupOccurrenceOK(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      boolean groupOccurrenceOK = true;
      BigInteger minRange = BigInteger.ZERO;
      BigInteger maxRange = BigInteger.ZERO;
      switch (derivedModel.getParticleType()) {
         case 1:
         case 3:
            minRange = getEffectiveMinRangeAllSeq(derivedModel);
            maxRange = getEffectiveMaxRangeAllSeq(derivedModel);
            break;
         case 2:
            minRange = getEffectiveMinRangeChoice(derivedModel);
            maxRange = getEffectiveMaxRangeChoice(derivedModel);
      }

      if (minRange.compareTo(baseModel.getMinOccurs()) < 0) {
         groupOccurrenceOK = false;
         errors.add(XmlError.forObject("range-ok.1", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
      }

      BigInteger UNBOUNDED = null;
      if (baseModel.getMaxOccurs() != UNBOUNDED) {
         if (maxRange == UNBOUNDED) {
            groupOccurrenceOK = false;
            errors.add(XmlError.forObject("range-ok.2", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
         } else if (maxRange.compareTo(baseModel.getMaxOccurs()) > 0) {
            groupOccurrenceOK = false;
            errors.add(XmlError.forObject("range-ok.2", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
         }
      }

      return groupOccurrenceOK;
   }

   private static BigInteger getEffectiveMaxRangeChoice(SchemaParticle derivedModel) {
      BigInteger maxRange = BigInteger.ZERO;
      BigInteger UNBOUNDED = null;
      boolean nonZeroParticleChildFound = false;
      BigInteger maxOccursInWildCardOrElement = BigInteger.ZERO;
      BigInteger maxOccursInGroup = BigInteger.ZERO;
      SchemaParticle[] particleChildren = derivedModel.getParticleChildren();

      for(int i = 0; i < particleChildren.length; ++i) {
         SchemaParticle particle = particleChildren[i];
         switch (particle.getParticleType()) {
            case 1:
            case 3:
               maxRange = getEffectiveMaxRangeAllSeq(particle);
               if (maxRange != UNBOUNDED && maxRange.compareTo(maxOccursInGroup) > 0) {
                  maxOccursInGroup = maxRange;
               }
               break;
            case 2:
               maxRange = getEffectiveMaxRangeChoice(particle);
               if (maxRange != UNBOUNDED && maxRange.compareTo(maxOccursInGroup) > 0) {
                  maxOccursInGroup = maxRange;
               }
               break;
            case 4:
            case 5:
               if (particle.getMaxOccurs() == UNBOUNDED) {
                  maxRange = (BigInteger)UNBOUNDED;
               } else if (particle.getIntMaxOccurs() > 0) {
                  nonZeroParticleChildFound = true;
                  if (particle.getMaxOccurs().compareTo(maxOccursInWildCardOrElement) > 0) {
                     maxOccursInWildCardOrElement = particle.getMaxOccurs();
                  }
               }
         }

         if (maxRange == UNBOUNDED) {
            break;
         }
      }

      if (maxRange != UNBOUNDED) {
         if (nonZeroParticleChildFound && derivedModel.getMaxOccurs() == UNBOUNDED) {
            maxRange = (BigInteger)UNBOUNDED;
         } else {
            maxRange = derivedModel.getMaxOccurs().multiply(maxOccursInWildCardOrElement.add(maxOccursInGroup));
         }
      }

      return maxRange;
   }

   private static BigInteger getEffectiveMaxRangeAllSeq(SchemaParticle derivedModel) {
      BigInteger maxRange = BigInteger.ZERO;
      BigInteger UNBOUNDED = null;
      boolean nonZeroParticleChildFound = false;
      BigInteger maxOccursTotal = BigInteger.ZERO;
      BigInteger maxOccursInGroup = BigInteger.ZERO;
      SchemaParticle[] particleChildren = derivedModel.getParticleChildren();

      for(int i = 0; i < particleChildren.length; ++i) {
         SchemaParticle particle = particleChildren[i];
         switch (particle.getParticleType()) {
            case 1:
            case 3:
               maxRange = getEffectiveMaxRangeAllSeq(particle);
               if (maxRange != UNBOUNDED && maxRange.compareTo(maxOccursInGroup) > 0) {
                  maxOccursInGroup = maxRange;
               }
               break;
            case 2:
               maxRange = getEffectiveMaxRangeChoice(particle);
               if (maxRange != UNBOUNDED && maxRange.compareTo(maxOccursInGroup) > 0) {
                  maxOccursInGroup = maxRange;
               }
               break;
            case 4:
            case 5:
               if (particle.getMaxOccurs() == UNBOUNDED) {
                  maxRange = (BigInteger)UNBOUNDED;
               } else if (particle.getIntMaxOccurs() > 0) {
                  nonZeroParticleChildFound = true;
                  maxOccursTotal = maxOccursTotal.add(particle.getMaxOccurs());
               }
         }

         if (maxRange == UNBOUNDED) {
            break;
         }
      }

      if (maxRange != UNBOUNDED) {
         if (nonZeroParticleChildFound && derivedModel.getMaxOccurs() == UNBOUNDED) {
            maxRange = (BigInteger)UNBOUNDED;
         } else {
            maxRange = derivedModel.getMaxOccurs().multiply(maxOccursTotal.add(maxOccursInGroup));
         }
      }

      return maxRange;
   }

   private static BigInteger getEffectiveMinRangeChoice(SchemaParticle derivedModel) {
      SchemaParticle[] particleChildren = derivedModel.getParticleChildren();
      if (particleChildren.length == 0) {
         return BigInteger.ZERO;
      } else {
         BigInteger minRange = null;

         for(int i = 0; i < particleChildren.length; ++i) {
            SchemaParticle particle = particleChildren[i];
            switch (particle.getParticleType()) {
               case 1:
               case 3:
                  BigInteger mrs = getEffectiveMinRangeAllSeq(particle);
                  if (minRange == null || minRange.compareTo(mrs) > 0) {
                     minRange = mrs;
                  }
                  break;
               case 2:
                  BigInteger mrc = getEffectiveMinRangeChoice(particle);
                  if (minRange == null || minRange.compareTo(mrc) > 0) {
                     minRange = mrc;
                  }
                  break;
               case 4:
               case 5:
                  if (minRange == null || minRange.compareTo(particle.getMinOccurs()) > 0) {
                     minRange = particle.getMinOccurs();
                  }
            }
         }

         if (minRange == null) {
            minRange = BigInteger.ZERO;
         }

         minRange = derivedModel.getMinOccurs().multiply(minRange);
         return minRange;
      }
   }

   private static BigInteger getEffectiveMinRangeAllSeq(SchemaParticle derivedModel) {
      BigInteger minRange = BigInteger.ZERO;
      SchemaParticle[] particleChildren = derivedModel.getParticleChildren();
      BigInteger particleTotalMinOccurs = BigInteger.ZERO;

      for(int i = 0; i < particleChildren.length; ++i) {
         SchemaParticle particle = particleChildren[i];
         switch (particle.getParticleType()) {
            case 1:
            case 3:
               particleTotalMinOccurs = particleTotalMinOccurs.add(getEffectiveMinRangeAllSeq(particle));
               break;
            case 2:
               particleTotalMinOccurs = particleTotalMinOccurs.add(getEffectiveMinRangeChoice(particle));
               break;
            case 4:
            case 5:
               particleTotalMinOccurs = particleTotalMinOccurs.add(particle.getMinOccurs());
         }
      }

      minRange = derivedModel.getMinOccurs().multiply(particleTotalMinOccurs);
      return minRange;
   }

   private static boolean nsSubset(SchemaParticle baseModel, SchemaParticle derivedModel, Collection errors, XmlObject context) {
      assert baseModel.getParticleType() == 5;

      assert derivedModel.getParticleType() == 5;

      boolean nsSubset = false;
      if (occurrenceRangeOK(baseModel, derivedModel, errors, context)) {
         if (baseModel.getWildcardSet().inverse().isDisjoint(derivedModel.getWildcardSet())) {
            nsSubset = true;
         } else {
            nsSubset = false;
            errors.add(XmlError.forObject("rcase-NSSubset.2", new Object[]{printParticle(derivedModel), printParticle(baseModel)}, context));
         }
      } else {
         nsSubset = false;
      }

      return nsSubset;
   }

   private static boolean nsCompat(SchemaParticle baseModel, SchemaLocalElement derivedElement, Collection errors, XmlObject context) {
      assert baseModel.getParticleType() == 5;

      boolean nsCompat = false;
      if (baseModel.getWildcardSet().contains(derivedElement.getName())) {
         if (occurrenceRangeOK(baseModel, (SchemaParticle)derivedElement, errors, context)) {
            nsCompat = true;
         }
      } else {
         nsCompat = false;
         errors.add(XmlError.forObject("rcase-NSCompat.1", new Object[]{printParticle((SchemaParticle)derivedElement), printParticle(baseModel)}, context));
      }

      return nsCompat;
   }

   private static boolean nameAndTypeOK(SchemaLocalElement baseElement, SchemaLocalElement derivedElement, Collection errors, XmlObject context) {
      if (!((SchemaParticle)baseElement).canStartWithElement(derivedElement.getName())) {
         errors.add(XmlError.forObject("rcase-NameAndTypeOK.1", new Object[]{printParticle((SchemaParticle)derivedElement), printParticle((SchemaParticle)baseElement)}, context));
         return false;
      } else if (!baseElement.isNillable() && derivedElement.isNillable()) {
         errors.add(XmlError.forObject("rcase-NameAndTypeOK.2", new Object[]{printParticle((SchemaParticle)derivedElement), printParticle((SchemaParticle)baseElement)}, context));
         return false;
      } else if (!occurrenceRangeOK((SchemaParticle)baseElement, (SchemaParticle)derivedElement, errors, context)) {
         return false;
      } else if (!checkFixed(baseElement, derivedElement, errors, context)) {
         return false;
      } else if (!checkIdentityConstraints(baseElement, derivedElement, errors, context)) {
         return false;
      } else if (!typeDerivationOK(baseElement.getType(), derivedElement.getType(), errors, context)) {
         return false;
      } else {
         return blockSetOK(baseElement, derivedElement, errors, context);
      }
   }

   private static boolean blockSetOK(SchemaLocalElement baseElement, SchemaLocalElement derivedElement, Collection errors, XmlObject context) {
      if (baseElement.blockRestriction() && !derivedElement.blockRestriction()) {
         errors.add(XmlError.forObject("rcase-NameAndTypeOK.6", new Object[]{printParticle((SchemaParticle)derivedElement), "restriction", printParticle((SchemaParticle)baseElement)}, context));
         return false;
      } else if (baseElement.blockExtension() && !derivedElement.blockExtension()) {
         errors.add(XmlError.forObject("rcase-NameAndTypeOK.6", new Object[]{printParticle((SchemaParticle)derivedElement), "extension", printParticle((SchemaParticle)baseElement)}, context));
         return false;
      } else if (baseElement.blockSubstitution() && !derivedElement.blockSubstitution()) {
         errors.add(XmlError.forObject("rcase-NameAndTypeOK.6", new Object[]{printParticle((SchemaParticle)derivedElement), "substitution", printParticle((SchemaParticle)baseElement)}, context));
         return false;
      } else {
         return true;
      }
   }

   private static boolean typeDerivationOK(SchemaType baseType, SchemaType derivedType, Collection errors, XmlObject context) {
      boolean typeDerivationOK = false;
      if (baseType.isAssignableFrom(derivedType)) {
         typeDerivationOK = checkAllDerivationsForRestriction(baseType, derivedType, errors, context);
      } else {
         typeDerivationOK = false;
         errors.add(XmlError.forObject("rcase-NameAndTypeOK.7a", new Object[]{printType(derivedType), printType(baseType)}, context));
      }

      return typeDerivationOK;
   }

   private static boolean checkAllDerivationsForRestriction(SchemaType baseType, SchemaType derivedType, Collection errors, XmlObject context) {
      boolean allDerivationsAreRestrictions = true;
      SchemaType currentType = derivedType;
      Set possibleTypes = null;
      if (baseType.getSimpleVariety() == 2) {
         possibleTypes = new HashSet(Arrays.asList(baseType.getUnionConstituentTypes()));
      }

      while(!baseType.equals(currentType) && possibleTypes != null && !possibleTypes.contains(currentType)) {
         if (currentType.getDerivationType() != 1) {
            allDerivationsAreRestrictions = false;
            errors.add(XmlError.forObject("rcase-NameAndTypeOK.7b", new Object[]{printType(derivedType), printType(baseType), printType(currentType)}, context));
            break;
         }

         currentType = currentType.getBaseType();
      }

      return allDerivationsAreRestrictions;
   }

   private static boolean checkIdentityConstraints(SchemaLocalElement baseElement, SchemaLocalElement derivedElement, Collection errors, XmlObject context) {
      boolean identityConstraintsOK = true;
      SchemaIdentityConstraint[] baseConstraints = baseElement.getIdentityConstraints();
      SchemaIdentityConstraint[] derivedConstraints = derivedElement.getIdentityConstraints();

      for(int i = 0; i < derivedConstraints.length; ++i) {
         SchemaIdentityConstraint derivedConstraint = derivedConstraints[i];
         if (checkForIdentityConstraintExistence(baseConstraints, derivedConstraint)) {
            identityConstraintsOK = false;
            errors.add(XmlError.forObject("rcase-NameAndTypeOK.5", new Object[]{printParticle((SchemaParticle)derivedElement), printParticle((SchemaParticle)baseElement)}, context));
            break;
         }
      }

      return identityConstraintsOK;
   }

   private static boolean checkForIdentityConstraintExistence(SchemaIdentityConstraint[] baseConstraints, SchemaIdentityConstraint derivedConstraint) {
      boolean identityConstraintExists = false;

      for(int i = 0; i < baseConstraints.length; ++i) {
         SchemaIdentityConstraint baseConstraint = baseConstraints[i];
         if (baseConstraint.getName().equals(derivedConstraint.getName())) {
            identityConstraintExists = true;
            break;
         }
      }

      return identityConstraintExists;
   }

   private static boolean checkFixed(SchemaLocalElement baseModel, SchemaLocalElement derivedModel, Collection errors, XmlObject context) {
      boolean checkFixed = false;
      if (baseModel.isFixed()) {
         if (baseModel.getDefaultText().equals(derivedModel.getDefaultText())) {
            checkFixed = true;
         } else {
            errors.add(XmlError.forObject("rcase-NameAndTypeOK.4", new Object[]{printParticle((SchemaParticle)derivedModel), derivedModel.getDefaultText(), printParticle((SchemaParticle)baseModel), baseModel.getDefaultText()}, context));
            checkFixed = false;
         }
      } else {
         checkFixed = true;
      }

      return checkFixed;
   }

   private static boolean occurrenceRangeOK(SchemaParticle baseParticle, SchemaParticle derivedParticle, Collection errors, XmlObject context) {
      boolean occurrenceRangeOK = false;
      if (derivedParticle.getMinOccurs().compareTo(baseParticle.getMinOccurs()) >= 0) {
         if (baseParticle.getMaxOccurs() == null) {
            occurrenceRangeOK = true;
         } else if (derivedParticle.getMaxOccurs() != null && baseParticle.getMaxOccurs() != null && derivedParticle.getMaxOccurs().compareTo(baseParticle.getMaxOccurs()) <= 0) {
            occurrenceRangeOK = true;
         } else {
            occurrenceRangeOK = false;
            errors.add(XmlError.forObject("range-ok.2", new Object[]{printParticle(derivedParticle), printMaxOccurs(derivedParticle.getMaxOccurs()), printParticle(baseParticle), printMaxOccurs(baseParticle.getMaxOccurs())}, context));
         }
      } else {
         occurrenceRangeOK = false;
         errors.add(XmlError.forObject("range-ok.1", new Object[]{printParticle(derivedParticle), derivedParticle.getMinOccurs().toString(), printParticle(baseParticle), baseParticle.getMinOccurs().toString()}, context));
      }

      return occurrenceRangeOK;
   }

   private static String printParticles(List parts) {
      return printParticles((SchemaParticle[])((SchemaParticle[])parts.toArray(new SchemaParticle[parts.size()])));
   }

   private static String printParticles(SchemaParticle[] parts) {
      return printParticles(parts, 0, parts.length);
   }

   private static String printParticles(SchemaParticle[] parts, int start) {
      return printParticles(parts, start, parts.length);
   }

   private static String printParticles(SchemaParticle[] parts, int start, int end) {
      StringBuffer buf = new StringBuffer(parts.length * 30);
      int i = start;

      while(i < end) {
         buf.append(printParticle(parts[i]));
         ++i;
         if (i != end) {
            buf.append(", ");
         }
      }

      return buf.toString();
   }

   private static String printParticle(SchemaParticle part) {
      switch (part.getParticleType()) {
         case 1:
            return "<all>";
         case 2:
            return "<choice>";
         case 3:
            return "<sequence>";
         case 4:
            return "<element name=\"" + QNameHelper.pretty(part.getName()) + "\">";
         case 5:
            return "<any>";
         default:
            return "??";
      }
   }

   private static String printMaxOccurs(BigInteger bi) {
      return bi == null ? "unbounded" : bi.toString();
   }

   private static String printType(SchemaType type) {
      return type.getName() != null ? QNameHelper.pretty(type.getName()) : type.toString();
   }

   private static void checkSubstitutionGroups(SchemaGlobalElement[] elts) {
      StscState state = StscState.get();

      for(int i = 0; i < elts.length; ++i) {
         SchemaGlobalElement elt = elts[i];
         SchemaGlobalElement head = elt.substitutionGroup();
         if (head != null) {
            SchemaType headType = head.getType();
            SchemaType tailType = elt.getType();
            XmlObject parseTree = ((SchemaGlobalElementImpl)elt)._parseObject;
            if (!headType.isAssignableFrom(tailType)) {
               state.error("e-props-correct.4", new Object[]{QNameHelper.pretty(elt.getName()), QNameHelper.pretty(head.getName())}, parseTree);
            } else if (head.finalExtension() && head.finalRestriction()) {
               state.error("e-props-correct.4a", new Object[]{QNameHelper.pretty(elt.getName()), QNameHelper.pretty(head.getName()), "#all"}, parseTree);
            } else if (!headType.equals(tailType)) {
               if (head.finalExtension() && tailType.getDerivationType() == 2) {
                  state.error("e-props-correct.4a", new Object[]{QNameHelper.pretty(elt.getName()), QNameHelper.pretty(head.getName()), "extension"}, parseTree);
               } else if (head.finalRestriction() && tailType.getDerivationType() == 1) {
                  state.error("e-props-correct.4a", new Object[]{QNameHelper.pretty(elt.getName()), QNameHelper.pretty(head.getName()), "restriction"}, parseTree);
               }
            }
         }
      }

   }
}
