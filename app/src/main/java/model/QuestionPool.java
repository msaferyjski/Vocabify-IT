package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionPool {
    private static final String POOL_DIR = "questionpools";
    private String name;
    private List<Question> questions;

    public QuestionPool(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        File dir = new File(POOL_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void removeQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
        }
    }

    public String getName() { return name; }
    public List<Question> getQuestions() { return questions; }

    public void saveToFile() throws IOException {
        ensureDirectoryExists();
        File file = new File(POOL_DIR, name + ".txt");
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (Question q : questions) {
                out.println(q.toTxtFormat());
            }
        }
    }

    public static void createPool(String name) throws IOException {
        if (name == null || name.trim().isEmpty()) return;
        new QuestionPool(name.trim()).saveToFile();
    }

    public static void importPool(File sourceFile) throws IOException {
        File destDir = new File(POOL_DIR);
        if (!destDir.exists()) destDir.mkdirs();

        File destFile = new File(destDir, sourceFile.getName());
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void exportPool(String poolName, File destFile) throws IOException {
        File sourceFile = new File(POOL_DIR, poolName + ".txt");
        if (!sourceFile.exists()) throw new FileNotFoundException("Pool-Datei nicht gefunden.");
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void deletePool(String name) {
        if (name == null) return;
        File file = new File(POOL_DIR, name + ".txt");
        if (file.exists()) file.delete();
    }

    public static QuestionPool loadFromFile(String poolName) throws IOException {
        QuestionPool pool = new QuestionPool(poolName);
        File file = new File(POOL_DIR, poolName + ".txt");
        if (!file.exists()) return pool;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts[0].equals("TEXT")) {
                    pool.addQuestion(new TextQuestion(parts[1], parts[2]));
                } else if (parts[0].equals("MC")) {
                    List<String> options = Arrays.asList(parts[3].split(";"));
                    pool.addQuestion(new MultipleChoiceQuestion(parts[1], options, Integer.parseInt(parts[2])));
                }
            }
        }
        return pool;
    }

    public static List<String> getAvailablePoolNames() {
        File folder = new File(POOL_DIR);
        if (!folder.exists()) return new ArrayList<>();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        List<String> names = new ArrayList<>();
        if (files != null) {
            for (File f : files) names.add(f.getName().replace(".txt", ""));
        }
        return names;
    }
}