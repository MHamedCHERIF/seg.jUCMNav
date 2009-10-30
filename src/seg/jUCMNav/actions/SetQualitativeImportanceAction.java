package seg.jUCMNav.actions;

import grl.IntentionalElementRef;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import seg.jUCMNav.JUCMNavPlugin;
import seg.jUCMNav.model.commands.transformations.ChangeQualitativeImportanceCommand;

/**
*
* @author Andrew Miga
*/

public class SetQualitativeImportanceAction extends URNSelectionAction
{
    public static final String SET_QUALITATIVE_IMPORTANCE = "seg.jUCMNav.SET_QUALITATIVE_IMPORTANCE"; //$NON-NLS-1$
    private IntentionalElementRef selection;
    private int id;
    private String[] values = { "High", "Medium", "Low", "None" };

	public SetQualitativeImportanceAction(IWorkbenchPart part, int id ) {
		super(part);
        setId( SET_QUALITATIVE_IMPORTANCE + id );
        setImageDescriptor(JUCMNavPlugin.getImageDescriptor( "icons/StrategyView16.gif")); //$NON-NLS-1$
        setText( values[id] );
        this.id = id;
	}

    protected Command getCommand() {
        return new ChangeQualitativeImportanceCommand( selection, id );
    }

    /**
     * We need to have an intentional element reference selected.
     */
    protected boolean calculateEnabled() {
        SelectionHelper sel = new SelectionHelper(getSelectedObjects());
        switch (sel.getSelectionType()) {
        case SelectionHelper.INTENTIONALELEMENTREF:
            selection = sel.getIntentionalelementref();
            return true;
        default:
            return false;
        }
    }

	public static String generateId(int id) {
		return SET_QUALITATIVE_IMPORTANCE + id;
	}
	
}
