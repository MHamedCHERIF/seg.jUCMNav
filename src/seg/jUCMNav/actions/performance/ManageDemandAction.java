package seg.jUCMNav.actions.performance;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import seg.jUCMNav.JUCMNavPlugin;
import seg.jUCMNav.actions.SelectionHelper;
import seg.jUCMNav.actions.URNSelectionAction;
import seg.jUCMNav.views.wizards.performance.ManageDemand;

/**
 * Opens the resource editor
 * 
 * @author jack
 */
public class ManageDemandAction extends URNSelectionAction {

	public static final String MANAGEDEMANDACTION = "seg.jUCMNav.actions.performance.ManageDemandAction"; //$NON-NLS-1$

	private EObject obj;

	/**
	 * Opens the resource editor.
	 * 
	 * @param part
	 *            the UCMNavMultiPageEditor
	 */
	public ManageDemandAction(IWorkbenchPart part) {
		super(part);
		setId(MANAGEDEMANDACTION);
		setImageDescriptor(ImageDescriptor.createFromFile(JUCMNavPlugin.class, "icons/Resource.gif")); //$NON-NLS-1$
	}

	/**
	 * True if we've selected something with resource.
	 */
	protected boolean calculateEnabled() {
		boolean enable = false;

		SelectionHelper sel = new SelectionHelper(getSelectedObjects());
		obj = sel.getURNmodelElement();
		if (sel.getSelectionType() == SelectionHelper.RESPONSIBILITYREF) {
			enable = true;
		}
		return enable;

	}

	/**
	 * Opens the resource editor.
	 * 
	 * @see seg.jUCMNav.views.wizards.performance.CreateResource
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ManageDemand wizard = new ManageDemand();
		wizard.init(PlatformUI.getWorkbench(), obj);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
	}

}
