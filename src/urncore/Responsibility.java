/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package urncore;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Responsibility</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link urncore.Responsibility#getDemands <em>Demands</em>}</li>
 *   <li>{@link urncore.Responsibility#getRespRefs <em>Resp Refs</em>}</li>
 * </ul>
 * </p>
 *
 * @see urncore.UrncorePackage#getResponsibility()
 * @model 
 * @generated
 */
public interface Responsibility extends UCMmodelElement {
    /**
     * Returns the value of the '<em><b>Demands</b></em>' containment reference list.
     * The list contents are of type {@link ucm.performance.Demand}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Demands</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Demands</em>' containment reference list.
     * @see urncore.UrncorePackage#getResponsibility_Demands()
     * @model type="ucm.performance.Demand" containment="true"
     * @generated
     */
    EList getDemands();

    /**
     * Returns the value of the '<em><b>Resp Refs</b></em>' reference list.
     * The list contents are of type {@link ucm.map.RespRef}.
     * It is bidirectional and its opposite is '{@link ucm.map.RespRef#getRespDef <em>Resp Def</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resp Refs</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resp Refs</em>' reference list.
     * @see urncore.UrncorePackage#getResponsibility_RespRefs()
     * @see ucm.map.RespRef#getRespDef
     * @model type="ucm.map.RespRef" opposite="respDef" required="true"
     * @generated
     */
    EList getRespRefs();

} // Responsibility
