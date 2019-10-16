package datamining.controller;

import datamining.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class IntegrationController {

    // Arquivos de entrada da integração.
    private static final String HEROES_CSV = "res/dataset/herois.csv";
    private static final String POWERS_CSV = "res/dataset/superpoderes.csv";

    // Caminho para o arquivo de saída da integração
    private static final String DATASET_CSV = "res/dataset/dataset.csv";
    public static void main(String[] args) throws IOException {
        if (!new File(DATASET_CSV).exists()) {
            try {
                List<String[]> heroes = FileUtil.readCSVFile(HEROES_CSV);
                List<String[]> powers = FileUtil.readCSVFile(POWERS_CSV);

                // Cria um novo dataset a unindo heroes e powers
                List<String[]> dataset = createDataset(heroes, powers);

                // Escreve o dataset no formato csv no arquivo indicado por DATASET_CSV
                writeDataset(dataset, DATASET_CSV);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }        
        ProcessingController.switchToken(DATASET_CSV);
        FileUtil.saveDatasetInArff(FileUtil.getDatasetInCsv(DATASET_CSV), "res/dataset/dataset.arff");
    }
    //testhjghjghjhkjhkjjk

    /**
     * Este método cria um novo dataset contendo uma união dos dois datasets de
     * entrada
     *
     * @param heroes
     * @param powers
     *
     * @return
     */
    public static List<String[]> createDataset(List<String[]> heroes, List<String[]> powers) {
        List<String[]> dataset = new LinkedList<>();
        // Remove a primeira linha dos dataset, que contém o nome dos campos
        String[] fieldsHero = heroes.remove(0);
        fieldsHero[0] = "id";
        String[] fieldsPower = powers.remove(0);
        String[] fields = joinArray(fieldsHero, 0, fieldsPower, 1);

        for (String[] hero : heroes) {
            dataset.add(Arrays.copyOf(hero, fields.length)); // hero[1] => name
        }
        int id = Integer.parseInt(dataset.get(dataset.size() - 1)[0]); // o último ID do dataset
        String name;
        String[] record;
        boolean exists;
        for (String[] power : powers) {
            name = power[0];

            exists = false;
            for (int i = dataset.size() - 1; i >= 0; i--) {
                record = dataset.get(i);
                if (name.equals(record[1])) {
                    record = dataset.remove(i);
                    record = joinArray(record, 0, fieldsHero.length, power, 1, power.length);
                    dataset.add(i, record);
                    exists = true;
                }
            }

            if (!exists) {
                record = new String[fieldsHero.length];
                id++;
                record[0] = "" + id;
                record[1] = name;
                record = joinArray(record, 0, fieldsHero.length, power, 1, power.length);
                dataset.add(record);
            }
        }

        dataset.sort((r1, r2) -> {
            if (r1[0] != null && !r1[0].isEmpty() && r2[0] != null && !r2[0].isEmpty()) {
                return Integer.compare(Integer.parseInt(r1[0]), Integer.parseInt(r2[0]));
            } else if (r1[0] != null && !r1[0].isEmpty()) {
                return 1;
            } else if (r2[0] != null && !r2[0].isEmpty()) {
                return -1;
            } else {
                return 0;
            }
        });

        dataset.add(0, fields);
        return dataset;
    }

    /**
     * Este método escreve a lista passada como parâmetro no formato csv no
     * arquivo indicado por DATASET_CSV
     *
     * @param dataset
     * @param path
     * @throws IOException
     */
    public static void writeDataset(List<String[]> dataset, String path) throws IOException {
        try (PrintStream out = new PrintStream(new FileOutputStream(path))) {
            String attribute;
            for (String[] record : dataset) {
                for (int x = 0; x < record.length; x++) {
                    attribute = record[x];
                    if (attribute == null || attribute.trim().isEmpty()) {
                        // 5 e 9 são as posições dos atributos numéricos
                        attribute = (x == 6 || x == 10) ? "" : "?";
                    }
                    attribute = attribute.trim();
                    if (x != record.length - 1) {
                        out.print(attribute + ",");
                    } else {
                        out.println(attribute);
                    }
                }
            }
        }
    }

    private static String[] joinArray(String[] a1, int a1Start, String[] a2, int a2Start) {
        return joinArray(a1, a1Start, a1.length, a2, a2Start, a2.length);
    }

    private static String[] joinArray(String[] a1, int a1Start, int a1End, String[] a2, int a2Start, int a2End) {
        int size = (a1End - a1Start) + (a2End - a2Start);
        String[] array = new String[size];

        int i = 0;

        for (int x = a1Start; x < a1End; x++) {
            array[i++] = a1[x];
        }

        for (int x = a2Start; x < a2End; x++) {
            array[i++] = a2[x];
        }

        return array;
    }

}
