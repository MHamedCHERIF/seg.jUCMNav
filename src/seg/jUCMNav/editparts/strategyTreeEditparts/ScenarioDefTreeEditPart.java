package seg.jUCMNav.editparts.strategyTreeEditparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import seg.jUCMNav.JUCMNavPlugin;
import seg.jUCMNav.Messages;
import seg.jUCMNav.editpolicies.element.EvaluationStrategyComponentEditPolicy;
import ucm.scenario.ScenarioDef;
import ucm.scenario.ScenarioGroup;

/**
 * TreeEditPart for a scenario in the strategies view
 * 
 * @author jkealey
 * 
 */
public class ScenarioDefTreeEditPart extends StrategyUrnModelElementTreeEditPart {


	/**
	 * @param model the scenario
	 */
	public ScenarioDefTreeEditPart(ScenarioDef model) {
		super(model);
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
    	if (!isInherited()) 
    		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EvaluationStrategyComponentEditPolicy());
	}

    /**
     * 
     * @return the scenario
     */
	public ScenarioDef getScenarioDef() {
		return (ScenarioDef) getModel();
	}

    /**
     * Returns all the children of a scenario (folders for start/end poitns, pre/post conditions, initializations and included scenarios). 
     */
	protected List getModelChildren() {
        ArrayList list = new ArrayList();
        list.add(Messages.getString("ScenarioLabelTreeEditPart.IncludedScenarios")); //$NON-NLS-1$
        list.add(Messages.getString("ScenarioLabelTreeEditPart.StartPoints")); //$NON-NLS-1$
        list.add(Messages.getString("ScenarioLabelTreeEditPart.Initializations")); //$NON-NLS-1$
        list.add(Messages.getString("ScenarioLabelTreeEditPart.Preconditions")); //$NON-NLS-1$
        list.add(Messages.getString("ScenarioLabelTreeEditPart.EndPoints")); //$NON-NLS-1$
        list.add(Messages.getString("ScenarioLabelTreeEditPart.Postconditions")); //$NON-NLS-1$
        return list;
        }
	
    /**
	 * Returns the icon for the {@link ScenarioDef}
	 */
	protected Image getImage() {
		if (super.getImage() == null) {
			setImage((ImageDescriptor.createFromFile(JUCMNavPlugin.class, "icons/ucm16.gif")).createImage()); //$NON-NLS-1$
		}
		return super.getImage();
	}

	/**
     * If selected, set the background color. 
	 */
	public void setSelected(boolean selected) {
		

		// bug 411
		if (!checkTreeItem())
			return;
		
		if (selected) {
			((TreeItem) widget).setBackground(GRAY);
		} else {
			((TreeItem) widget).setBackground(WHITE);
		}
		// refreshVisuals();
	}
	

    /**
     * Is this scenario inherited from another scenario? This depends on the edit part and not the model instance; the model instance is not duplicated, the edit part is. 
     *  
     * @return Is this scenario inherited from another scenario?
     */
	private boolean isInherited() {
		if (getParent().getModel() instanceof ScenarioGroup)
			return false;
		else 
			return getParent().getChildren().indexOf(this) < ((ScenarioLabelTreeEditPart)getParent()).getModelChildren().size()-((ScenarioDef) getParent().getParent().getModel()).getIncludedScenarios().size();
	}

    /** 
     * Returns the scenario's name and sets the label as grayed out if it is inherited {@link #isInherited()}
     */
	protected String getText() {
		if (widget!=null && !widget.isDisposed()) {
	    	if (isInherited()) 
	    		((TreeItem) widget).setForeground(DARKGRAY);
	    	else
	    		((TreeItem) widget).setForeground(BLACK);
		}
    	
    	return super.getText();
	}


}
