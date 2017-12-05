package seg.jUCMNav.model.commands.create;

import java.util.Date;

import org.eclipse.gef.commands.Command;

import grl.Actor;
import grl.ActorRef;
import grl.ElementLink;
import grl.IntentionalElement;
import grl.IntentionalElementRef;
import grl.LinkRef;
import seg.jUCMNav.Messages;
import seg.jUCMNav.editparts.ActorRefEditPart;
import seg.jUCMNav.editparts.ComponentRefEditPart;
import seg.jUCMNav.editparts.IntentionalElementEditPart;
import seg.jUCMNav.editparts.LinkRefEditPart;
import seg.jUCMNav.editparts.PathNodeEditPart;
import seg.jUCMNav.model.ModelCreationFactory;
import seg.jUCMNav.model.commands.JUCMNavCommand;
import ucm.map.ComponentRef;
import ucm.map.StartPoint;
import ucm.map.Timer;
import ucm.map.WaitingPlace;
import urn.URNspec;
import urn.dyncontext.Change;
import urn.dyncontext.DynamicContext;
import urn.dyncontext.EnumChange;
import urncore.Component;
import urncore.URNmodelElement;

/**
 * This command adds an EnumChange to the selected URNModelElement and Dynamic Context
 * 
 * @author aprajita
 * 
 */
public class AddEnumerationChangeCommand extends Command implements JUCMNavCommand {
	
	private Object parent;
	private String selectedChange, affectedProperty;
	private Date startDate;
	private Date endDate;
    boolean aborted = false;
    boolean isInCompoundCommand = false;
    private URNspec urn;
    private EnumChange newChange;
    private DynamicContext dyn;
    private String newValue;

    /**
	 * Constructor
	 */
    public AddEnumerationChangeCommand(Object parent, DynamicContext dyn, String selectedChange, String affectedProperty, Date startDate, Date endDate, String newValue, URNspec urn) {
        this.parent = parent;
        this.urn = urn;
        this.dyn = dyn;
        this.selectedChange = selectedChange;
        this.affectedProperty = affectedProperty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.newValue = newValue;
        setLabel(Messages.getString("AddEnumChangeCommand.AddEnumChange")); //$NON-NLS-1$
    }

    /**
	 * 
	 */
    public AddEnumerationChangeCommand(Object parent, String selectedChange, String affectedProperty, boolean isInCompoundCommand, DynamicContext dyn, Date startDate, Date endDate, String newValue, URNspec urn) {
    	this.parent = parent;
        this.urn = urn;
        this.dyn = dyn;
        this.selectedChange = selectedChange;
        this.affectedProperty = affectedProperty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.newValue = newValue;
        setLabel(Messages.getString("AddEnumChangeCommand.AddEnumChange")); //$NON-NLS-1$
        this.isInCompoundCommand = isInCompoundCommand;
    }

    /**
     * @see org.eclipse.gef.commands.Command#canExecute()
     */
    public boolean canExecute() {
        return parent != null && selectedChange != null && affectedProperty != null && startDate != null && endDate != null && dyn != null;
    }

    /**
     * @see org.eclipse.gef.commands.Command#execute()
     */
    public void execute() {
        redo();
    }

    /**
     * 
     * @see org.eclipse.gef.commands.Command#redo()
     */
    public void redo() {
        if (!canExecute()) {
            aborted = true; // another command in same compound command invalidated our preconditions
            return;
        }
        testPreConditions();
        
        //Add an enum change
        if (selectedChange.equals("Enumeration Change")){
        	newChange = (EnumChange) ModelCreationFactory.getNewObject(urn, EnumChange.class);
        	
        	URNmodelElement elt = null;
        	if (parent instanceof LinkRefEditPart)
        		elt = ((LinkRef)((LinkRefEditPart) this.parent).getModel()).getLink();
        	else if (parent instanceof ElementLink)
        		elt = (ElementLink) this.parent;
        	else if (parent instanceof IntentionalElementEditPart)
        		elt = ((IntentionalElementRef)((IntentionalElementEditPart) this.parent).getModel()).getDef();
        	else if (parent instanceof IntentionalElement)
        		elt = (IntentionalElement) this.parent;
        	else if (parent instanceof ActorRefEditPart)
        		elt = (Actor) ((ActorRef)((ActorRefEditPart) this.parent).getModel()).getContDef();
        	else if (parent instanceof ActorRef)
        		elt = (Actor) ((ActorRef) this.parent).getContDef();
        	else if (parent instanceof ComponentRefEditPart)
           		elt = (Component)((ComponentRef)((ComponentRefEditPart) this.parent).getModel()).getContDef();
        	else if (parent instanceof Component)
        		elt = (Component) this.parent;
        	else if (parent instanceof PathNodeEditPart) {
    	    	if ((((PathNodeEditPart) parent).getModel()) instanceof StartPoint)
    	    		elt = (StartPoint)((PathNodeEditPart) parent).getModel();
    	    	else if ((((PathNodeEditPart) parent).getModel()) instanceof Timer)
    	    		elt = (Timer)((PathNodeEditPart) parent).getModel();
    	    	else if ((((PathNodeEditPart) parent).getModel()) instanceof WaitingPlace)
    	    		elt = (WaitingPlace)((PathNodeEditPart) parent).getModel();
    	    }
        	else if (parent instanceof StartPoint)
        		elt = (StartPoint) this.parent;
        	else if (parent instanceof Timer)
        		elt = (Timer) this.parent;
        	else if (parent instanceof WaitingPlace)
        		elt = (WaitingPlace) this.parent;
        	newChange.setElement(elt);
        	newChange.setAffectedProperty(affectedProperty);
        	newChange.setStart(startDate);
        	newChange.setEnd(endDate);
        	if (affectedProperty.equals("Decomposition Type")) {
        		if (newValue.equals("0"))
        			newChange.setNewValue("AND");
        		else if (newValue.equals("1"))
    				newChange.setNewValue("OR");
        		else if (newValue.equals("2"))
    				newChange.setNewValue("XOR");
        		else
        			newChange.setNewValue(newValue);
        	} else if (affectedProperty.equals("Failure Kind")) {
        		if (newValue.equals("0"))
        			newChange.setNewValue("FAILURE");
        		else if (newValue.equals("1"))
    				newChange.setNewValue("ABORT");
        		else if (newValue.equals("2"))
    				newChange.setNewValue("NONE");
        		else
        			newChange.setNewValue(newValue);
        	} else if (affectedProperty.equals("Wait Kind")) {
        		if (newValue.equals("0"))
        			newChange.setNewValue("TRANSIENT");
        		else if (newValue.equals("1"))
    				newChange.setNewValue("PERSISTENT");
        		else
        			newChange.setNewValue(newValue);
        	} else if (affectedProperty.equals("Component Kind")) {
        		if (newValue.equals("0"))
        			newChange.setNewValue("TEAM");
        		else if (newValue.equals("1"))
    				newChange.setNewValue("OBJECT");
        		else if (newValue.equals("2"))
    				newChange.setNewValue("PROCESS");
        		else if (newValue.equals("3"))
    				newChange.setNewValue("AGENT");
        		else if (newValue.equals("4"))
    				newChange.setNewValue("ACTOR");
        		else if (newValue.equals("5"))
    				newChange.setNewValue("OTHER");
        		else
        			newChange.setNewValue(newValue);
        	} else
        		newChange.setNewValue(newValue);
        	newChange.setContext(dyn);
        	
        }
        
        testPostConditions();
    }

    /*
     * (non-Javadoc)
     * 
     * @see seg.jUCMNav.model.commands.JUCMNavCommand#testPostConditions()
     */
    public void testPostConditions() {
        assert parent != null && selectedChange != null && affectedProperty != null && urn != null && dyn != null: "post something null"; //$NON-NLS-1$
        assert dyn.getChanges().contains(newChange) : "post child not added"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see seg.jUCMNav.model.commands.JUCMNavCommand#testPreConditions()
     */
    public void testPreConditions() {
        assert parent != null && selectedChange != null && affectedProperty != null && urn != null && dyn != null : "pre something null"; //$NON-NLS-1$
        
        
    }
    
    /**
     * 
     * @return the added new EnumChange
     */
    public Change getAddedChange(){
    	return newChange;
    }

    /**
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo() {
        if (aborted)
            return;
        testPostConditions();
        dyn.getChanges().remove(newChange);
        testPreConditions();
    }

}
