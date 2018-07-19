package seg.jUCMNav.figures;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

import asd.ASDiagram;
import asd.ASDspec;
import asd.Aim;
import asd.Outcome;
import urncore.URNmodelElement;

/**
 * Figure for UCM Labels; uses an EditableLabel.
 * 
 * @author Jordan, Jean-Fran�ois Roy, jkealey
 */
public class AimFigure extends Label implements LabelElementFigure {
    public String suffixText = ""; //$NON-NLS-1$
    public String prefixText = ""; //$NON-NLS-1$
    public String editableText = ""; //$NON-NLS-1$
  
    ASDspec asdSpec;
 // is the figure in hover state
    protected boolean hover;

    // is the figure in selected state
    protected boolean selected;
    Aim model;
    ASDiagram asdiagram;
    int location;
    // for grl multiline label, space between start of the figure and start of the label
    protected static final int LABEL_PADDING_X = 15;
    protected static final int LABEL_PADDING_Y = 8;
 // default sizes
    protected static final int DEFAULT_HEIGHT = 45;
    protected static final int DEFAULT_WIDTH = 90;
    public AimFigure(ASDspec asdSpec, Aim model,ASDiagram asdiagram) {
    	
    	this.model=model;
    this.asdiagram=asdiagram;
    	this.asdSpec=asdSpec;
    	
    }

   
    private Rectangle getSelectionRectangle() {
        Rectangle bounds = getBounds().getCopy();// textFlow.getBounds().getCopy();
        bounds.expand(new Insets(2, 2, 0, 0));
        translateToParent(bounds);
        bounds.intersect(getBounds());
        return bounds;
    }

    /**
     * Use this method instead of getText for LabelFigure
     */
    public String getEditableText() {
        // return UrnMetadata.removeStereotypes(super.getText());
        return editableText;
    }

    /**
     * paints figure differently depends on the whether the figure has focus or is selected
     */
    protected void paintFigure(Graphics graphics) {

        if (selected) {
            graphics.pushState();
            graphics.setBackgroundColor(ColorConstants.menuBackgroundSelected);
            graphics.fillRectangle(getTextBounds());
            graphics.popState();
            graphics.setForegroundColor(ColorConstants.white);
            model.setName(this.getText());
        }

        super.paintFigure(graphics);
    }

    public boolean setSuffixText(String text) {
        /*String previousText = getEditableText();
        if (suffixText.equals(text)) {
            return;
        }
        suffixText = text;
        setEditableText(previousText);*/
    	ArrayList al = new ArrayList(); 
    	
    	for(Object el1: asdiagram.getElements())
    	{
			if(el1 instanceof Outcome && el1!=null) //always true
    		{
				
				al.add(el1);
			
    		}
			
    	}
    	boolean flag_found=false;
    	for(Object el: asdSpec.getOutcomes())
    	{
    		if(el instanceof Outcome && !al.contains(el)) //always true
    		{
    			if((((URNmodelElement) el).getName()).equals(text))
    			{
    				return true;
    				
    			}
    			
    	      
    		}
    	}
    	return flag_found;
         
    }

    public void setPrefixText(String text) {
        String previousText = getEditableText();
        if (prefixText.equals(text)) {
            return;
        }
        prefixText = text;
        setEditableText(previousText);
    }

    /**
     * Sets the selection state of this SimpleActivityLabel
     * 
     * @param b
     *            true will cause the label to appear selected
     */
    public void setSelected(boolean b) {
        selected = b;
        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see seg.jUCMNav.figures.LabelElementFigure#setText(java.lang.String)
     */
    public void setEditableText(String newText) {
        this.editableText = newText;
        /*if(setSuffixText(newText))
        	newText=newText+"*";*/
        super.setText(prefixText+newText+suffixText);
    }


}
