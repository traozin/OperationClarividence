package datamining.controller;

import datamining.util.FileUtil;
import static datamining.util.FileUtil.getDatasetInCsv;
import java.text.DecimalFormat;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;


public class PredictionController {

    private static final String DATASET_CSV = "res/dataset/dataset.csv";
    private static final String DATASET_ARFF = "res/dataset/dataset.arff";
    private static final String SUPERPOWERS_CSV = "res/dataset/superpoderes.csv";
    private static final String SUPERPOWERS_ARFF = "res/dataset/superpoderes.arff";
    
    public static void main(String[] args) throws Exception {
        treeGenerator(90);
        
    }

    /**
     * Método que gera a árvore de predição e fala o resultado da sua avaliação
     *
     * @param reference
     * @throws Exception
     */
    public static void treeGenerator(int reference) throws Exception {
        J48 tree = new J48();

        Instances instance = getDatasetInCsv(DATASET_CSV);
        instance.setClassIndex(reference);
        Evaluation eval = new Evaluation(instance);

        eval.crossValidateModel(tree, instance, 10, new Random(1));

//        tree.buildClassifier(instance);
        evaluationResults(eval, instance);
    }

    /**
     * Resultado da avaliação da árvore
     *
     * @param evaluation
     * @param dataset
     */
    public static void evaluationResults(Evaluation evaluation, Instances dataset) {
        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println("\nClasse: " + dataset.get(18).classAttribute().name());
        System.out.println("Quantidade de amostras classificadas corretamente: " + df.format(evaluation.pctCorrect()) + "%");
        System.out.println("Quantidade de amostras classificadas incorretamente: " + df.format(evaluation.pctIncorrect()) + "%");
        System.out.println("Índice Kappa: " + df.format(evaluation.kappa()) + "\n");
    }

}
