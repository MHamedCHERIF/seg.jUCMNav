/*
 * Created on 2005-01-30
 *  
 */
package seg.jUCMNav.editpolicies;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.viewers.IStructuredSelection;

import seg.jUCMNav.editparts.LabelEditPart;
import seg.jUCMNav.editparts.PathNodeEditPart;
import seg.jUCMNav.model.commands.SetConstraintComponentRefCommand;
import seg.jUCMNav.model.commands.AddComponentRefCommand;
import seg.jUCMNav.model.commands.CreatePathCommand;
import seg.jUCMNav.model.commands.ExtendPathCommand;
import seg.jUCMNav.model.commands.LabelSetConstraintCommand;
import seg.jUCMNav.model.commands.SetConstraintCommand;
import ucm.map.ComponentRef;
import ucm.map.EndPoint;
import ucm.map.Map;
import ucm.map.PathGraph;
import ucm.map.PathNode;
import ucm.map.StartPoint;
import urncore.NodeLabel;

public class MapAndPathGraphXYLayoutEditPolicy extends XYLayoutEditPolicy {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart, java.lang.Object)
     */
    protected Command createAddCommand(EditPart child, Object constraint) {
        return null;
    }

    protected PathGraph getPathGraph() {
        return ((Map) getHost().getModel()).getPathGraph();
    }

    protected Map getMap() {
        return (Map) getHost().getModel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
     */
    protected Command getCreateCommand(CreateRequest request) {
        Object newObjectType = null;
        if (request.getNewObject() != null)
            newObjectType = request.getNewObjectType();

        // converts relative to absolute positions (so that zooms work properly)
        Rectangle constraint = (Rectangle) getConstraintFor(request);
        Command createCommand = null;

        if (newObjectType == StartPoint.class) {
            createCommand = new CreatePathCommand(getPathGraph(), (StartPoint) request.getNewObject(), constraint.x, constraint.y );
        } else if (newObjectType == EndPoint.class) {
            ExtendPathCommand create = new ExtendPathCommand();
            create.setDiagram(getPathGraph());

            // Get the list of selected items
            List selecteds = ((IStructuredSelection) getHost().getViewer().getSelection()).toList();
            if (selecteds.size() == 0) {

            }
            // If there's only one item selected
            else if (selecteds.size() == 1) {
                EditPart selected = (EditPart) (selecteds.get(0));
                if (selected.getModel() instanceof EndPoint) {
                    create.setLocation(constraint.getLocation());
                    create.setLastEnd((EndPoint) selected.getModel());
                    create.setNewEnd((EndPoint) request.getNewObject());
                    create.setLabel("Create a node");
                    createCommand = create;
                } else if (selected.getModel() instanceof PathGraph) {
                    // JK: I'm not sure when this code is invoked.
                    // ET: executed when nothing other than the background is selected; used to work without this code when the top level element was the PathGraph

                    createCommand = new CreatePathCommand(getPathGraph(), constraint.x, constraint.y);
                }
            }
        } else if (newObjectType == ComponentRef.class) {

            ComponentRef compRef = (ComponentRef) request.getNewObject();

            AddComponentRefCommand create = new AddComponentRefCommand(getMap(), compRef);
            SetConstraintComponentRefCommand moveResize = new SetConstraintComponentRefCommand(compRef, constraint.x, constraint.y,
                    constraint.width, constraint.height);

            // after creation, move and resize the component;
            createCommand = create.chain(moveResize);
        }

        return createCommand;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
     */
    protected Command getDeleteDependantCommand(Request request) {
        return null;
    }

    //	/* (non-Javadoc)
    //	 * @see
    // org.eclipse.gef.editpolicies.LayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
    //	 */
    //	protected EditPolicy createChildEditPolicy(EditPart child) {
    //			return new NonResizableEditPolicy();
    //	}

    //	/* (non-Javadoc)
    //	 * @see
    // org.eclipse.gef.editpolicies.XYLayoutEditPolicy#getMinimumSizeFor(org.eclipse.gef.GraphicalEditPart)
    //	 */
    //	protected Dimension getMinimumSizeFor(GraphicalEditPart child) {
    //		return child.getContentPane().getMinimumSize();
    //	}

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
     */
    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {

        if (child.getModel() instanceof PathNode) {
            SetConstraintCommand locationCommand = new SetConstraintCommand();
            locationCommand.setNode((PathNode) child.getModel());
            //		Rectangle constraint = (Rectangle)getConstraintFor(request);
            //		this.getConstraintFor((Rectangle)constraint);
            //		Rectangle rect = (Rectangle)constraint;
            //		((GraphicalEditPart)(child)).getFigure().translateToRelative((Rectangle)constraint);
            //		rect.translate(getLayoutOrigin().getNegated());

            // Adjust the coordinates with the coordinates of the figure too
            // since
            // the x,y coordinates is
            // the center of the figure.
            Dimension dim = ((PathNodeEditPart) child).getNodeFigure().getPreferredSize().getCopy();

            Point location = new Point(((Rectangle) constraint).x + (dim.width / 2), ((Rectangle) constraint).y + (dim.height / 2));
            locationCommand.setNewPosition(location);
            return locationCommand;
        } else if (child.getModel() instanceof ComponentRef) {
            Rectangle rect = (Rectangle) constraint;
            ComponentRef compRef = (ComponentRef) child.getModel();

            SetConstraintComponentRefCommand moveResize = new SetConstraintComponentRefCommand(compRef, rect.getLocation().x, rect.getLocation().y,
                    rect.width, rect.height);
            
            return moveResize;

        } else if (child.getModel() instanceof NodeLabel) {
            LabelSetConstraintCommand locationCommand = new LabelSetConstraintCommand();
            locationCommand.setNode((NodeLabel) child.getModel());
            //		Rectangle constraint = (Rectangle)getConstraintFor(request);
            //		this.getConstraintFor((Rectangle)constraint);
            //		Rectangle rect = (Rectangle)constraint;
            //		((GraphicalEditPart)(child)).getFigure().translateToRelative((Rectangle)constraint);
            //		rect.translate(getLayoutOrigin().getNegated());

            // Adjust the coordinates with the coordinates of the figure too
            // since
            // the x,y coordinates is
            // the center of the figure.
            Dimension dim = ((LabelEditPart) child).getLabelFigure().getPreferredSize().getCopy();

            Point location = new Point(((Rectangle) constraint).x + (dim.width / 2), ((Rectangle) constraint).y + (dim.height / 2));
            locationCommand.setNewPosition(location);
            return locationCommand;
        } else {
            System.out.println("unknown model element upon which to call MapAndPathGraphXYLayoutEditPolicy.createChangeConstraintCommand()");
            return new SetConstraintCommand();
        }

    }
}