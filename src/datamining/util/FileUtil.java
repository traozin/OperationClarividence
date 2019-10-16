/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

/**
 *
 * @author VíctorCésar
 */
public class FileUtil {
    
    /**
     * Método utilizado para realizar a leitura de um arquivo csv
     *
     * @param fileName Nome do arquivo que será lido
     *
     * @return Retorna uma lista de arrays de string, onde cada array representa
     * uma linha do csv e cada string do array, um campo daquela linha.
     *
     * @throws IOException Exceção que será lançada caso ocorra algum problema
     * durante a leitura do arquivo
     */
    public static List<String[]> readCSVFile(String fileName) throws IOException {
        List<String[]> data = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] record;
            while (reader.ready()) {
                line = reader.readLine();
//                line = line.replaceAll("-99.0","");
                record = line.split(",");
                if (line.endsWith(",")) {
                    record = Arrays.copyOf(record, record.length + 1);
                    record[record.length - 1] = "";
                }
                data.add(record);
            }
        }

        return data;
    }
    
    public static List<String> readCSVFileLine(String fileName) throws IOException {
        List<String> data = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] record;
            while (reader.ready()) {
                line = reader.readLine();
                data.add(line);
            }
        }

        return data;
    }
    
    /**
     * Leitura de um arquivo em Csv com retorno de um dataset manipulável pelo
     * weka
     *
     * @param fileCsv
     * @return
     * @throws IOException
     */
    public static Instances getDatasetInCsv(String fileCsv) throws IOException {
        CSVLoader reader = new CSVLoader();
        reader.setFile(new File(fileCsv));
        return reader.getDataSet();
    }

    /**
     * Escrita de um dataset manipulável pelo weka em um arquivo .csv
     *
     * @param dataset
     * @param path
     * @throws IOException
     */
    public static void saveDatasetInCsv(Instances dataset, String path) throws IOException {
        CSVSaver saver = new CSVSaver();
        saver.setInstances(dataset);
        saver.setFile(new File(path));
        saver.writeBatch();
    }

    /**
     * Leitura de um arquivo em arff com retorno de um dataset manipulável pelo
     * weka
     *
     * @param fileArff
     * @return
     * @throws IOException
     */
    public static Instances getDatasetInArff(String fileArff) throws IOException {
        ArffLoader reader = new ArffLoader();
        reader.setFile(new File(fileArff));
        return reader.getDataSet();
    }

    /**
     * Escrita de um dataset manipulável pelo weka em um arquivo .arff
     *
     * @param dataset
     * @param path
     * @throws IOException
     */
    public static void saveDatasetInArff(Instances dataset, String path) throws IOException {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataset);
        saver.setFile(new File(path));
        saver.writeBatch();
    }

}
