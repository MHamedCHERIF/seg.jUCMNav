/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package urncore.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import urn.URNlink;
import urn.UrnPackage;

import urncore.URNmodelElement;
import urncore.UrncorePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UR Nmodel Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link urncore.impl.URNmodelElementImpl#getFromLinks <em>From Links</em>}</li>
 *   <li>{@link urncore.impl.URNmodelElementImpl#getToLinks <em>To Links</em>}</li>
 *   <li>{@link urncore.impl.URNmodelElementImpl#getId <em>Id</em>}</li>
 *   <li>{@link urncore.impl.URNmodelElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link urncore.impl.URNmodelElementImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class URNmodelElementImpl extends EObjectImpl implements URNmodelElement {
    /**
     * The cached value of the '{@link #getFromLinks() <em>From Links</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFromLinks()
     * @generated
     * @ordered
     */
    protected EList fromLinks = null;

    /**
     * The cached value of the '{@link #getToLinks() <em>To Links</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToLinks()
     * @generated
     * @ordered
     */
    protected EList toLinks = null;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected URNmodelElementImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EClass eStaticClass() {
        return UrncorePackage.eINSTANCE.getURNmodelElement();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList getFromLinks() {
        if (fromLinks == null) {
            fromLinks = new EObjectWithInverseResolvingEList.ManyInverse(URNlink.class, this, UrncorePackage.UR_NMODEL_ELEMENT__FROM_LINKS, UrnPackage.UR_NLINK__FROM_ELEMS);
        }
        return fromLinks;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList getToLinks() {
        if (toLinks == null) {
            toLinks = new EObjectWithInverseResolvingEList.ManyInverse(URNlink.class, this, UrncorePackage.UR_NMODEL_ELEMENT__TO_LINKS, UrnPackage.UR_NLINK__TO_ELEMS);
        }
        return toLinks;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, UrncorePackage.UR_NMODEL_ELEMENT__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, UrncorePackage.UR_NMODEL_ELEMENT__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, UrncorePackage.UR_NMODEL_ELEMENT__DESCRIPTION, oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case UrncorePackage.UR_NMODEL_ELEMENT__FROM_LINKS:
                    return ((InternalEList)getFromLinks()).basicAdd(otherEnd, msgs);
                case UrncorePackage.UR_NMODEL_ELEMENT__TO_LINKS:
                    return ((InternalEList)getToLinks()).basicAdd(otherEnd, msgs);
                default:
                    return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
            }
        }
        if (eContainer != null)
            msgs = eBasicRemoveFromContainer(msgs);
        return eBasicSetContainer(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case UrncorePackage.UR_NMODEL_ELEMENT__FROM_LINKS:
                    return ((InternalEList)getFromLinks()).basicRemove(otherEnd, msgs);
                case UrncorePackage.UR_NMODEL_ELEMENT__TO_LINKS:
                    return ((InternalEList)getToLinks()).basicRemove(otherEnd, msgs);
                default:
                    return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
            }
        }
        return eBasicSetContainer(null, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case UrncorePackage.UR_NMODEL_ELEMENT__FROM_LINKS:
                return getFromLinks();
            case UrncorePackage.UR_NMODEL_ELEMENT__TO_LINKS:
                return getToLinks();
            case UrncorePackage.UR_NMODEL_ELEMENT__ID:
                return getId();
            case UrncorePackage.UR_NMODEL_ELEMENT__NAME:
                return getName();
            case UrncorePackage.UR_NMODEL_ELEMENT__DESCRIPTION:
                return getDescription();
        }
        return eDynamicGet(eFeature, resolve);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eSet(EStructuralFeature eFeature, Object newValue) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case UrncorePackage.UR_NMODEL_ELEMENT__FROM_LINKS:
                getFromLinks().clear();
                getFromLinks().addAll((Collection)newValue);
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__TO_LINKS:
                getToLinks().clear();
                getToLinks().addAll((Collection)newValue);
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__ID:
                setId((String)newValue);
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__NAME:
                setName((String)newValue);
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__DESCRIPTION:
                setDescription((String)newValue);
                return;
        }
        eDynamicSet(eFeature, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eUnset(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case UrncorePackage.UR_NMODEL_ELEMENT__FROM_LINKS:
                getFromLinks().clear();
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__TO_LINKS:
                getToLinks().clear();
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__ID:
                setId(ID_EDEFAULT);
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__NAME:
                setName(NAME_EDEFAULT);
                return;
            case UrncorePackage.UR_NMODEL_ELEMENT__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
                return;
        }
        eDynamicUnset(eFeature);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean eIsSet(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case UrncorePackage.UR_NMODEL_ELEMENT__FROM_LINKS:
                return fromLinks != null && !fromLinks.isEmpty();
            case UrncorePackage.UR_NMODEL_ELEMENT__TO_LINKS:
                return toLinks != null && !toLinks.isEmpty();
            case UrncorePackage.UR_NMODEL_ELEMENT__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case UrncorePackage.UR_NMODEL_ELEMENT__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case UrncorePackage.UR_NMODEL_ELEMENT__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
        }
        return eDynamicIsSet(eFeature);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (id: ");
        result.append(id);
        result.append(", name: ");
        result.append(name);
        result.append(", description: ");
        result.append(description);
        result.append(')');
        return result.toString();
    }

} //URNmodelElementImpl