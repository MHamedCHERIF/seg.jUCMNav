package seg.jUCMNav.editors.palette.tools;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import seg.jUCMNav.model.ModelCreationFactory;
import ucm.map.EmptyPoint;
import ucm.map.EndPoint;
import ucm.map.NodeConnection;
import ucm.map.PathNode;
import ucm.map.StartPoint;
import urn.URNspec;

/**
 * Created 2005-03-28 <br>
 * <br>
 * 
 * This class represent the main tool used to edit paths in the UcmEditor. The tool has a very specific behavior depending on the selecting of the editpart
 * viewer selection and the target editpart the tool is currently pointing at. <br>
 * <br>
 * 
 * This tool should: <br>- Extend the path when an EndPoint is selected. <br>- Extend the path backward when a StartPoint is selected. <br>- Create an
 * OR-FORK when an EmptyPoint is selected. <br>- Join two paths when an EmptyPoint is selected and that the user click on an empty point of an other path. <br>-
 * Create an EmptyPoint when the user click on a NodeConnection and select the EndPoint of the path. <br>- When a user clicks a PathNode, the tool should
 * select it. <br>
 * <br>
 * 
 * @author Etienne Tremblay
 */
public class PathTool extends CreationTool implements ISelectionChangedListener {

    /**
     * <code>ENDPOINT</code>: State value when an EndPoint is selected.
     */
    private final int ENDPOINT = 0;
    /**
     * <code>STARTPOINT</code>: State value when a StartPoint is selected.
     */
    private final int STARTPOINT = 1;
    /**
     * <code>NOSELECT</code>: State value when the map is selected.
     */
    private final int NOSELECT = 2;
    /**
     * <code>EMPTYPOINT</code>: State value when an empty point is selected.
     */
    private final int EMPTYPOINT = 3;
    /**
     * <code>CONNECTION</code>: State value when the cursor is over a connection.
     */
    private final int CONNECTION = 4;

    /**
     * <code>state</code>: The state value for the tool.
     */
    private int state = NOSELECT;

    /**
     * <code>urn</code>: The tool needs to now the URNspec to provide a creation factory.
     */
    private URNspec urn;

    /**
     * <code>target</code>: The editpart the tool is currently targeting.
     */
    private EditPart target;

    /**
     * <code>targetRequest</code>: The request the tool should return from its state.
     */
    private Request targetRequest;

    /**
     * <code>selected</code>: The selected editpart in the editpart viewer.
     */
    private EditPart selected;

    /**
     * We should not allow the use of the default constructor. We need a URNspec.
     */
    private PathTool() {
        super();
    }

    /**
     * The default constructor with the URNspec.
     * 
     * @param urn
     *            The required URNspec of the tool.
     */
    public PathTool(URNspec urn) {
        super();
        this.urn = urn;
    }

    protected Request createTargetRequest() {
        return super.createTargetRequest();
    }

    protected void setTargetRequest(Request req) {
        targetRequest = req;
        super.setTargetRequest(req);
    }

    /**
     * This method is called each time we need to change the target editpart of the tool. This method updates the state of the tool and recreate the target
     * request if required.
     * 
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.tools.TargetingTool#setTargetEditPart(org.eclipse.gef.EditPart)
     */
    protected void setTargetEditPart(EditPart editpart) {
        if (target != editpart) {
            target = editpart;
            setState();
            if (target != null)
                setTargetRequest(createTargetRequest());
        }
        super.setTargetEditPart(editpart);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.tools.AbstractTool#handleMove()
     */
    protected boolean handleMove() {
        // Change the target under the mouse before updating the target request...
        updateTargetUnderMouse();
        updateTargetRequest();
        setCurrentCommand(getCommand());
        showTargetFeedback();
        return true;
    }

    /**
     * This function is called when the editpart viewer the tool is pointing changes. We need to add the tool as a listener to the selection changes of the
     * viewer.
     * 
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.Tool#setViewer(org.eclipse.gef.EditPartViewer)
     */
    public void setViewer(EditPartViewer viewer) {
        super.setViewer(viewer);

        EditPartViewer oldViewer = getCurrentViewer();
        if (oldViewer != null)
            oldViewer.removeSelectionChangedListener(this);
        if (viewer != null) {
            viewer.addSelectionChangedListener(this);
            setSelectionState((IStructuredSelection) viewer.getSelection());
        }
    }

    /**
     * This function is called to return a factory for our create request. A factory is returned depending on the state of the tool.
     * 
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.tools.CreationTool#getFactory()
     */
    protected CreationFactory getFactory() {
        ModelCreationFactory factory;
        switch (state) {
        case ENDPOINT:
            factory = new ModelCreationFactory(getURNspec(), EndPoint.class);
            break;
        case STARTPOINT:
            factory = new ModelCreationFactory(getURNspec(), StartPoint.class);
            break;
        case NOSELECT:
            factory = new ModelCreationFactory(getURNspec(), StartPoint.class);
            break;
        case CONNECTION:
            factory = new ModelCreationFactory(getURNspec(), EmptyPoint.class);
            break;
        default:
            factory = new ModelCreationFactory(getURNspec(), StartPoint.class);
        }

        return factory;
    }

    /**
     * @return The URNspec used by the tool.
     */
    protected URNspec getURNspec() {
        return urn;
    }

    /**
     * This method is called each time the selection changes in the EditPartViewer
     * 
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selecteds = (IStructuredSelection) event.getSelection();
        setSelectionState(selecteds);
    }

    /**
     * From the given strutured selection, set the state of the tool.
     * 
     * @param selecteds
     *            The returned structured selection from the editpart viewer.
     */
    private void setSelectionState(IStructuredSelection selecteds) {
        List list = selecteds.toList();

        if (list.size() == 1) {
            selected = (EditPart) (list.get(0));

            setState();

            // Regenerate the request depending on the state
            createTargetRequest();
        }
    }

    /**
     * Set the state of the tool depending on the selected editpart and the targeted editpart.
     */
    protected void setState() {
        if (target != null) {
            if (target.getModel() instanceof NodeConnection) {
                state = CONNECTION;
                return;
            }
        }

        if (selected != null) {
            if (selected.getModel() instanceof EndPoint) {
                state = ENDPOINT;
            } else if (selected.getModel() instanceof StartPoint) {
                state = STARTPOINT;
            } else
                state = NOSELECT;
        } else {
            state = NOSELECT;
        }
    }

    /**
     * A vestige from CreationTool. Needed to get copied so that we could call our own selectAddedObject function. (non-Javadoc)
     * 
     * @see org.eclipse.gef.tools.CreationTool#performCreation(int)
     */
    protected void performCreation(int button) {
        executeCurrentCommand();
        selectAddedObject();
    }

    /**
     * Add the newly created object to the viewer's selected objects. Select the right object depending on the state.
     */
    private void selectAddedObject() {
        final Object model = getCreateRequest().getNewObject();
        if (model == null)
            return;
        if (model instanceof StartPoint) {
            StartPoint start = (StartPoint) model;
            if (state == NOSELECT) {
                PathNode node = (PathNode) ((NodeConnection) start.getSucc().get(0)).getTarget();
                PathNode end = (PathNode) ((NodeConnection) node.getSucc().get(0)).getTarget();
                if (end instanceof EndPoint)
                    selectModelElement(end);
            } else
                selectModelElement(start);
        } else if (model instanceof EmptyPoint) {
            PathNode pn = findEndPoint((PathNode) model);
            if (pn != null)
                selectModelElement(pn);
        } else
            selectModelElement(model);
    }

    /**
     * From a PathNode in a path, find the end point of the path.
     * 
     * @param start
     *            The path node in the path where you want to find the end point.
     * @return The endpoint of the path.
     */
    private PathNode findEndPoint(PathNode start) {
        PathNode node = null;
        PathNode end = null;
        end = start;
        if (end instanceof EndPoint)
            return end;

        while (!(end instanceof EndPoint)) {
            if (end.getSucc().size() == 0)
                return null;
            end = (PathNode) ((NodeConnection) end.getSucc().get(0)).getTarget();
        }

        return end;
    }

    /**
     * Select the model element you provide.
     * 
     * @param model
     *            The model element you want to see selected in the viewer.
     */
    protected void selectModelElement(Object model) {
        EditPartViewer viewer = getCurrentViewer();
        Object editpart = viewer.getEditPartRegistry().get(model);
        if (editpart instanceof EditPart) {
            viewer.flush();
            viewer.select((EditPart) editpart);
        }
    }
}