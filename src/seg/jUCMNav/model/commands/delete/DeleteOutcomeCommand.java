    package seg.jUCMNav.model.commands.delete;

    import org.eclipse.gef.commands.Command;

import asd.ASDiagram;
import asd.ASDmodelElement;
import asd.Outcome;
import grl.GRLGraph;
import seg.jUCMNav.Messages;
import seg.jUCMNav.model.commands.JUCMNavCommand;
import urn.URNspec;
    /**
     * 
     * Deletes a belief to a {@link GRLGraph}
     * 
     * @author Jean-Fran�ois Roy
     * 
     */
    public class DeleteOutcomeCommand extends Command implements JUCMNavCommand {

        private Outcome outcome;

        // Graph where the element has been added.
        private URNspec spec;
        
        private ASDiagram asDiagram;

        /**
         * @param asDiagram 
         * @param graph
         *            graph where to add the belief
         * @param belief
         *            belief to add
         */
        public DeleteOutcomeCommand(URNspec spec, Outcome outcome, ASDiagram asDiagram) {
            super();
            this.spec = spec;
            this.outcome = outcome;
            this.asDiagram = asDiagram;
            setLabel(Messages.getString("DeleteOutcomeCommand.deleteOutcome")); //$NON-NLS-1$
        }

        /**
         * 
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
           
            for(Object o: outcome.getEnabledElements())
            {
            	outcome.getEnabledElements().remove((ASDmodelElement)o);
            }
 
            
            spec.getAsdspec().getOutcomes().remove(outcome);
            outcome.getDiagrams().remove(asDiagram);
         
        }

        /*
         * (non-Javadoc)
         * 
         * @see seg.jUCMNav.model.commands.JUCMNavCommand#testPreConditions()
         */
        public void testPreConditions() {
           /* assert outcome != null : "pre belief"; //$NON-NLS-1$
            assert spec != null : "pre spec"; //$NON-NLS-1$
*/
         //  assert !spec.getAsdspec().getOutcomes().contains(outcome) : "pre outcome in spec"; //$NON-NLS-1$
            
        }

        /*
         * (non-Javadoc)
         * 
         * @see seg.jUCMNav.model.commands.JUCMNavCommand#testPostConditions()
         */
        public void testPostConditions() {
            /*assert outcome != null : "post outcome"; //$NON-NLS-1$
            assert spec != null : "post spec"; //$NON-NLS-1$

            assert spec.getAsdspec().getOutcomes().contains(outcome) : "pre outcome in spec"; //$NON-NLS-1$
*/        }

        /**
         * 
         * @see org.eclipse.gef.commands.Command#undo()
         */
        public void undo() {
        	   
        	for(Object o: spec.getAsdspec().getTools())
        	{
        		if(o instanceof Outcome && ((ASDmodelElement) o).getName().equals(outcome.getName()) && !asDiagram.getElements().contains(o))
        		{
        			outcome.getEnabledElements().add(o);
        		}
        	}
        	for(Object o: spec.getAsdspec().getSubjects())
        	{
        		if(o instanceof Outcome && ((ASDmodelElement) o).getName().equals(outcome.getName()) && !asDiagram.getElements().contains(o))
        		{
        			outcome.getEnabledElements().add(o);
        		}
        	}
        	for(Object o: spec.getAsdspec().getRules())
        	{
        		if(o instanceof Outcome && ((ASDmodelElement) o).getName().equals(outcome.getName()) && !asDiagram.getElements().contains(o))
        		{
        			outcome.getEnabledElements().add(o);
        		}
        	}
        	for(Object o: spec.getAsdspec().getCommunities())
        	{
        		if(o instanceof Outcome && ((ASDmodelElement) o).getName().equals(outcome.getName()) && !asDiagram.getElements().contains(o))
        		{
        			outcome.getEnabledElements().add(o);
        		}
        	}
        	for(Object o: spec.getAsdspec().getDols())
        	{
        		if(o instanceof Outcome && ((ASDmodelElement) o).getName().equals(outcome.getName()) && !asDiagram.getElements().contains(o))
        		{
        			outcome.getEnabledElements().add(o);
        		}
        	}
        	
        	
        	
        	
        	
        	spec.getAsdspec().getOutcomes().add(outcome);
        	  outcome.getDiagrams().add(asDiagram);
        }
    }