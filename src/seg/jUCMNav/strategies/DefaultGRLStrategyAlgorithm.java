/**
 * 
 */
package seg.jUCMNav.strategies;

import grl.Contribution;
import grl.ContributionType;
import grl.Decomposition;
import grl.DecompositionType;
import grl.Dependency;
import grl.ElementLink;
import grl.Evaluation;
import grl.EvaluationStrategy;
import grl.IntentionalElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import seg.jUCMNav.extensionpoints.IGRLStrategyAlgorithm;

/**
 * This class implement the default GRL evaluation algorithm.
 * 
 * @author Jean-Fran�ois Roy
 *
 */
public class DefaultGRLStrategyAlgorithm implements IGRLStrategyAlgorithm {
    
    private class EvaluationCalculation{
        public IntentionalElement element;
        public int linkCalc;
        public int totalLinkDest;
        
        public EvaluationCalculation(IntentionalElement element, int totalLink){
            this.element = element;
            this.totalLinkDest = totalLink;
            linkCalc = 0;
        }
    }
    
    Vector evalReady; 
    HashMap evaluationCalculation;
    HashMap evaluations;

    /* (non-Javadoc)
     * @see seg.jUCMNav.extensionpoints.IGRLStrategiesAlgorithm#init(java.util.Vector)
     */
    public void init(EvaluationStrategy strategy, HashMap evaluations) {
        evalReady = new Vector();
        evaluationCalculation = new HashMap();
        this.evaluations = evaluations;
        
        Iterator it = strategy.getGrlspec().getIntElements().iterator();
        while (it.hasNext()){
            IntentionalElement element = (IntentionalElement)it.next();
            if (element.getLinksDest().size() == 0 || ((Evaluation)evaluations.get(element)).getStrategies() != null){
                evalReady.add(element);
            } else{
                EvaluationCalculation calculation = new EvaluationCalculation(element, element.getLinksDest().size());
                evaluationCalculation.put(element,calculation);
            }
        }
    }

    /* (non-Javadoc)
     * @see seg.jUCMNav.extensionpoints.IGRLStrategiesAlgorithm#hasNextNode()
     */
    public boolean hasNextNode() {
        if (evalReady.size() > 0){
            return true;
        } 
        return false;
    }

    /* (non-Javadoc)
     * @see seg.jUCMNav.extensionpoints.IGRLStrategiesAlgorithm#nextNode()
     */
    public IntentionalElement nextNode() {
        IntentionalElement intElem = (IntentionalElement)evalReady.remove(0);

        for (Iterator j=intElem.getLinksSrc().iterator();j.hasNext();){
            IntentionalElement temp = ((ElementLink)j.next()).getDest();
            if (evaluationCalculation.containsKey(temp)){
                EvaluationCalculation calc = (EvaluationCalculation)evaluationCalculation.get(temp);
                calc.linkCalc += 1;
                if (calc.linkCalc >= calc.totalLinkDest){
                    evaluationCalculation.remove(temp);
                    evalReady.add(calc.element);
                }
            }
        }
        return intElem;
    }

    /* (non-Javadoc)
     * @see seg.jUCMNav.extensionpoints.IGRLStrategiesAlgorithm#getEvaluation(grl.IntentionalElement)
     */
    public int getEvaluation(IntentionalElement element) {
        Evaluation eval = (Evaluation)evaluations.get(element);
        if ((element.getLinksDest().size() == 0) || (eval.getIntElement() != null)){
            return eval.getEvaluation();
        }
        int result = 0;
        int decompositionValue = -10000;
        int dependencyValue = 10000;
        int [] contributionValues = new int[100];
        int contribArrayIt = 0;
        
        Iterator it = element.getLinksDest().iterator(); //Return the list of elementlink
        while (it.hasNext()){
            ElementLink link = (ElementLink)it.next();
            if (link instanceof Decomposition){
                if (decompositionValue < -100){
                    decompositionValue = ((Evaluation)evaluations.get(link.getSrc())).getEvaluation();
                } else if (element.getDecompositionType().getValue() == DecompositionType.AND){
                    if (decompositionValue > ((Evaluation)evaluations.get(link.getSrc())).getEvaluation()){
                        decompositionValue = ((Evaluation)evaluations.get(link.getSrc())).getEvaluation();
                    }
                } else if (element.getDecompositionType().getValue() == DecompositionType.OR){
                    if (decompositionValue < ((Evaluation)evaluations.get(link.getSrc())).getEvaluation()){
                        decompositionValue = ((Evaluation)evaluations.get(link.getSrc())).getEvaluation();
                    }
                } 
            } else if (link instanceof Dependency){
                if (dependencyValue > ((Evaluation)evaluations.get(link.getSrc())).getEvaluation()
                        && ((Evaluation)evaluations.get(link.getSrc())).getEvaluation() != 0){
                    dependencyValue = ((Evaluation)evaluations.get(link.getSrc())).getEvaluation();
                }
            } else if (link instanceof Contribution){
                Contribution contrib = (Contribution)link;
                if (contrib.getContribution().getValue() != ContributionType.UNKNOWN){
                    int srcNode = ((Evaluation)evaluations.get(link.getSrc())).getEvaluation();
                    //The source node value is between -100 and 100. For the contribution calculation, 
                    //denied value correspond to 0. The value should be between 0 and 100 and the source evaluation should not be 0.
                    if (srcNode != 0){
                        srcNode = 50 + srcNode/2;
                        
                        double resultContrib;
                        switch (contrib.getContribution().getValue()){
                            case ContributionType.MAKE:
                                resultContrib = srcNode;
                                break;
                            case ContributionType.HELP:
                                resultContrib = srcNode * 0.5;
                                break;
                            case ContributionType.SOME_POSITIVE:
                                resultContrib = srcNode * 0.25;
                                break;
                            case ContributionType.SOME_NEGATIVE:
                                resultContrib = srcNode * -0.25;
                                break;
                            case ContributionType.HURT:
                                resultContrib = srcNode * -0.5;
                                break;
                            case ContributionType.BREAK:
                                resultContrib = srcNode * -1;
                                break;
                            default:
                                resultContrib = 0;
                                break;
                        }
                        int tempEval = ((Evaluation)evaluations.get(link.getSrc())).getEvaluation();
                        if (Math.abs(resultContrib) > Math.abs(tempEval)){
                            resultContrib = (Math.abs(resultContrib)/resultContrib)*tempEval;
                        }
                        if (resultContrib != 0){
                            contributionValues[contribArrayIt] = 
                                (new Double(Math.round(resultContrib))).intValue();
                            contribArrayIt++;
                        }
                    }
                }
            }
        }
        if (decompositionValue >=-100){
            result = decompositionValue;
        }
        if (contributionValues.length > 0){
            int numDenied = 0;
            int numSatisfied = 0;
            int contribValue = 0;
            
            for (int i=0;i<contribArrayIt;i++){
                if (contributionValues[i] == 100){
                    numSatisfied++;
                } else if(contributionValues[i] == -100){
                    numDenied++;
                }
                contribValue += contributionValues[i];
            }
            
            if (contribValue > 90 && numSatisfied == 0){
                contribValue = 90;
            } else if (contribValue < -90 && numDenied == 0){
                contribValue = -90;
            }
            result = result + contribValue;
            
            if (result > 100 || result<-100){
                result = (result/Math.abs(result))*100;
            }
            
        }
        if ((dependencyValue <= 100) && (result > dependencyValue)){
            result = dependencyValue;
        }
        return result;
    }

}