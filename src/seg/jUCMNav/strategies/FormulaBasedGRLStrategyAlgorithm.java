package seg.jUCMNav.strategies;

import grl.Contribution;
import grl.ElementLink;
import grl.Evaluation;
import grl.IntentionalElement;
import seg.jUCMNav.Messages;
import seg.jUCMNav.extensionpoints.IGRLStrategyAlgorithm;
import seg.jUCMNav.model.util.MetadataHelper;

import com.primalworld.math.MathEvaluator;

/**
 * This class implements the Formula Based Quantitative GRL evaluation algorithm, with KPI aggregation.
 * 
 * @author alireza pourshahid
 * 
 */
public class FormulaBasedGRLStrategyAlgorithm extends QuantitativeGRLStrategyAlgorithm {

    protected String formula = ""; //$NON-NLS-1$;
    protected MathEvaluator mathEvaluator = null;

    /*
     * (non-Javadoc)
     * 
     * @see seg.jUCMNav.extensionpoints.IGRLStrategiesAlgorithm#getEvaluationType()
     */
    public int getEvaluationType() {
        return IGRLStrategyAlgorithm.EVAL_FORMULA;
    }

    protected void setFormulaDefaults() {
        // default values.
        formula = ""; //$NON-NLS-1$
        mathEvaluator = null;
    }

    protected Integer preGetEvaluation(IntentionalElement element, Evaluation eval) {
        // clean state before starting.
        setFormulaDefaults();

        // look at the metadata to see if we have a formula to apply.
        formula = MetadataHelper.getMetaData(element, Messages.getString("FormulaBasedGRLStrategyAlgorithm_Formula")); //$NON-NLS-1$
        if (formula == null)
            formula = ""; //$NON-NLS-1$ // Keep the same default value.
        else
            mathEvaluator = new MathEvaluator(formula);

        // return immediately if we can
        Integer result = null;
        if ((element.getLinksDest().size() == 0) || (eval.getIntElement() != null && mathEvaluator == null)) {
            result = eval.getEvaluation();
        }
        return result;
    }

    protected int postGetEvaluation(IntentionalElement element, Evaluation eval, int result) {
        if (null != mathEvaluator) {
            double resultContrib;
            Double srcAfterFormula;
            srcAfterFormula = mathEvaluator.getValue();

            if (srcAfterFormula != null) {
                resultContrib = srcAfterFormula.doubleValue();
            } else {
                resultContrib = 0.0;
                System.err.println("Incorrect formula '" + formula + "' in KPI '" + element.getName() + "'."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            }

            eval.getKpiEvalValueSet().setEvaluationValue(resultContrib);
            EvaluationStrategyManager strategyManager = EvaluationStrategyManager.getInstance();
            strategyManager.calculateIndicatorEvalLevel(eval);
            result = eval.getEvaluation();
        }

        // forget state to avoid invalid usage after an evaluation.
        setFormulaDefaults();

        return result;
    }

    protected double computeContributionResult(ElementLink link, Contribution contrib) {
        if (mathEvaluator == null) {
            return super.computeContributionResult(link, contrib);
        } else {
            int quantitativeContrib = EvaluationStrategyManager.getInstance().getActiveQuantitativeContribution(contrib);
            double srcNodeEvaluationValue = ((Evaluation) evaluations.get(link.getSrc())).getKpiEvalValueSet().getEvaluationValue();
            // TODO: it might be better if we change this to use the name of the source node as opposed to link
            // TODO: I have noticed if the name of the link does not match the variables used in the formula it can cause errors.
            // This is partially caught now and displayed on Ststem.err, to prevent crashing (Daniel)
            mathEvaluator.addVariable(contrib.getName(), srcNodeEvaluationValue);
            return 0;
        }
    }
}
