package datamining.controller;

import static datamining.controller.IntegrationController.writeDataset;
import datamining.util.FileUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

/**
 *
 * @author Neto
 */
public class ProcessingController {

    private static String nonProcessed = "res/dataset/dataset.csv";
    private static String processed = "res/pre-processed/dataset pre-processed.csv";

    public static void main(String[] args) throws IOException, Exception {
        Instances dataset = FileUtil.getDatasetInCsv(nonProcessed);
        switchToken(nonProcessed);
        dataset = processing();
    }

    public static Instances processing() throws IOException, Exception {

        Instances data = FileUtil.getDatasetInCsv("res/dataset/dataset.csv");

        Filter impute = new ReplaceMissingValues();
        impute.setInputFormat(data);
        data = Filter.useFilter(data, impute);

        FileUtil.saveDatasetInCsv(data, processed);
        return data;

    }

    /**
     * Método para tratar os apóstrofos que impediam na manipulação do dataset
     * pelo weka
     *
     * @param path
     * @throws IOException
     */
    public static void switchToken(String path) throws IOException {
        List<String[]> dataset = FileUtil.readCSVFile(path);
        for (String[] strings : dataset) {
            for (int i = 0; i < strings.length; i++) {

                String content = strings[i];
                switch (content) {
                    case "-":
                        content = content.replaceAll("-", "?");
                        break;
                    case "-99":
                        content = content.replaceAll("-99", "");
                        break;
                    default:
                        content = content.replaceAll("'", "");
                        break;
                }
                strings[i] = content;
            }
        }
        writeDataset(dataset, path);
    }

    public List<Instance> getSubDataset(Instances dataset, int attribute, String filterFeature, boolean include) {
        Enumeration<Instance> instances = dataset.enumerateInstances();

        List<Instance> listInclude = new LinkedList();
        List<Instance> listExclude = new LinkedList();

        while (instances.hasMoreElements()) {
            Instance currentInstance = instances.nextElement();
            String currentValue = Double.toString(currentInstance.value(attribute));
            currentValue = currentValue.substring(0, currentValue.indexOf("."));

            if (currentValue.equals(filterFeature)) {
                System.out.println(currentValue);
                listInclude.add(currentInstance);
            } else {
                listExclude.add(currentInstance);
            }
        }

        return include ? listInclude : listExclude;
    }
}

