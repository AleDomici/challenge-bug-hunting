package repository;

import model.Video;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileVideoRepository {

    private final String filePath;

    public FileVideoRepository(String filePath) {
        this.filePath = filePath;
        createFileIfNotExists();  // Chama o método para garantir que o arquivo exista
    }

    // Método para criar o arquivo caso ele não exista
    private void createFileIfNotExists() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();  // Cria o arquivo se não existir
                System.out.println("Arquivo " + filePath + " criado com sucesso.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    public List<Video> loadVideos() {
        List<Video> videos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 5) {
                    Video video = new Video(data[0], data[1], Double.parseDouble(data[2]), data[3], data[4]);
                    videos.add(video);
                } else {
                    System.err.println("Erro no formato da linha. Esperado 5 campos.");
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar vídeos: " + e.getMessage());
        }
        return videos;
    }

    public void saveVideos(List<Video> videos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Video video : videos) {
                writer.write(video.getTitle() + ";" + video.getDescription() + ";" + video.getDuration() + ";" + video.getCategory() + ";" + new SimpleDateFormat("dd/MM/yyyy").format(video.getPublicationDate()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar vídeos: " + e.getMessage());
        }
    }

    public void deleteVideo(String title) {
        List<Video> videos = loadVideos();
        videos.removeIf(video -> video.getTitle().equalsIgnoreCase(title));
        saveVideos(videos);
    }
}
