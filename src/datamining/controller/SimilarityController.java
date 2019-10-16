package datamining.controller;

import datamining.model.*;
import datamining.util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class SimilarityController {

    //referência do dataset de poderes, unico dataset que será usado na similaridade
    

    public static void main(String[] args) throws IOException {
        Instances dataset = FileUtil.getDatasetInCsv("res/dataset/superpoderes.csv"); 
        Instance reference = dataset.get(50);
        
        Jaccard jaca = new Jaccard();
        
        printContigence(calculeContigence(dataset, reference, jaca), reference);
    }

    /**
     * Método que calcula a similaridade, ordena e printa a saída
     *
     * @param listSimilarity
     * @param reference
     */
    public static void printContigence(List<Node> listSimilarity, Instance reference) {
        Collections.sort(listSimilarity);
        for (int i = 0; i < 11; i++) {
            if (!listSimilarity.get(i).equals(reference)) {
                System.out.println((i) + " - " + listSimilarity.get(i).getToCompare().stringValue(0));
            }
        }
    }

    /**
     * Método que gera a tabela de contigência dado um algoritimo passado como
     * parâmetro
     *
     * @param dataset
     * @param reference
     * @param algorithm
     * @return
     */
    private static List<Node> calculeContigence(Instances dataset, Instance reference, Algorithms algorithm) {
        List<Node> relationship = new ArrayList<>();
        dataset.remove(0);

        dataset.forEach((Instance instance) -> {
            ContigenceTable table = calculateAttribute(reference, instance);

            double similarity = algorithm.calculate(table.getA(), table.getB(), table.getC(), table.getD());
            relationship.add(new Node(instance, similarity));
        });

        return relationship;
    }

    /**
     * Método que calcula a similaridade de dois herois considerando TODOS os
     * atributos
     *
     * @param reference
     * @param toCompare
     * @return
     */
    private static ContigenceTable calculateAttribute(Instance reference, Instance toCompare) {
        ContigenceTable table = new ContigenceTable();

        for (int current = 1; current < reference.numAttributes(); current++) {
            String relation = Double.toString(reference.value(current)).substring(0, 1);
            relation += Double.toString(toCompare.value(current)).charAt(0);

            switch (relation) {
                case "00": {
                    table.addD();
                    break;
                }
                case "01": {
                    table.addB();
                    break;
                }
                case "10": {
                    table.addC();
                    break;
                }
                case "11": {
                    table.addA();
                    break;
                }
            }
        }
        return table;
    }

    /**
     * Método que procura um heroi no dataset, pelo nome
     *
     * @param name
     * @param dataset
     * @return
     */
    public Instance searchHero(String name, Instances dataset) {
        Enumeration<Instance> enumerate = dataset.enumerateInstances();
        while (enumerate.hasMoreElements()) {
            Instance next = enumerate.nextElement();
            if (next.stringValue(0).equals(name)) {
                return next;
            }
        }
        return null;
    }

//    public Enumeration<Attribute> listAttribute(Instance reference) {
//        return dataset.enumerateAttributes();
//    }
}
