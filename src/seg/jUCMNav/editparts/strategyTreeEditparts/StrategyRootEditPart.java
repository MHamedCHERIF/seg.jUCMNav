package seg.jUCMNav.editparts.strategyTreeEditparts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.TreeEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import ucm.UCMspec;
import ucm.scenario.ScenarioDef;
import ucm.scenario.ScenarioGroup;

/**
 * This class is simply here because the root of our strategy (GRLspec) / scenario (UCMspec)
 * 
 * @author jfroy, jkealey
 *  
 */
public class StrategyRootEditPart extends AbstractTreeEditPart {

    public StrategyRootEditPart(UCMNavMultiPageEditor editor) {
        super(editor);
    }

    /**
     * Return the root URNSpec
     */
    protected List getModelChildren() {
        ArrayList l = new ArrayList();
        l.add(((UCMNavMultiPageEditor) getModel()).getModel().getUcmspec());
        l.add(((UCMNavMultiPageEditor) getModel()).getModel().getGrlspec());
        l.add(((UCMNavMultiPageEditor) getModel()).getModel().getUcmspec().getVariables());
        return l;
    }

    /**
     * @see org.eclipse.gef.EditPart#getRoot()
     */
    public RootEditPart getRoot() {
    	if (getParent()==null)
    		return null;
    	else
    		return getParent().getRoot();
    }
    
    public void refreshScenarioTreeView(UCMspec ucmspec) {
    	for (Iterator iter = ucmspec.getScenarioGroups().iterator(); iter.hasNext();) {
			ScenarioGroup group = (ScenarioGroup) iter.next();
			for (Iterator iterator = group.getScenarios().iterator(); iterator.hasNext();) {
				ScenarioDef scenario = (ScenarioDef) iterator.next();
		    	StrategyUrnModelElementTreeEditPart part  = (StrategyUrnModelElementTreeEditPart) getViewer().getEditPartRegistry().get(scenario);
		    	if (part!=null) {
		    		// all children of a strategy
		    		for (Iterator iter2 = part.getChildren().iterator(); iter2.hasNext();) {
						TreeEditPart element = (TreeEditPart) iter2.next();
						element.refresh();
					}
		    	}
			}
		}
    }
}