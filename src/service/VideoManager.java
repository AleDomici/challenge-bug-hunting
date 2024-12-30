package service;

import model.Video;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;
import java.util.List;
import java.util.Scanner;
import util.InputValidator;

public class VideoManager {
    private final Scanner scanner = new Scanner(System.in);
    private final VideoService videoService;
    private final SearchStrategy searchStrategy;

    public VideoManager() {
        this.videoService = new VideoServiceImpl(new repository.FileVideoRepository("videos.txt"));
        this.searchStrategy = new TitleSearchStrategy();  // Usando a estratégia de busca por título
    }

    public void start() {
        while (true) {
            printMenu();
            int option = readOption();

            switch (option) {
                case 1 -> addVideo();
                case 2 -> listVideos();
                case 3 -> searchVideos();
                case 4 -> exit();
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
        System.out.println("1. Adicionar vídeo");
        System.out.println("2. Listar vídeos");
        System.out.println("3. Pesquisar vídeo por título");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addVideo() {
        // Lê as informações do vídeo
        String title = InputValidator.readNonEmptyInput(scanner, "Digite o título do vídeo: ");
        String description = InputValidator.readNonEmptyInput(scanner, "Digite a descrição do vídeo: ");
        int duration = InputValidator.readPositiveNumber(scanner, "Digite a duração do vídeo (em minutos): ");

        // Validação da categoria com o laço while
        String category = null;
        while (category == null || (!category.equalsIgnoreCase("Filme") && !category.equalsIgnoreCase("Série") && !category.equalsIgnoreCase("Documentário"))) {
            category = InputValidator.readNonEmptyInput(scanner, "Digite a categoria do vídeo (Filme, Série, Documentário): ");
            if (category == null || (!category.equalsIgnoreCase("Filme") && !category.equalsIgnoreCase("Série") && !category.equalsIgnoreCase("Documentário"))) {
                System.out.println("Categoria inválida. Tente novamente.");
            }
        }

        // Lê a data de publicação (garantindo o formato correto)
        String publicationDate = InputValidator.readValidDate(scanner, "Digite a data de publicação (dd/MM/yyyy): ");

        // Cria o vídeo e adiciona ao sistema
        Video video = new Video(title, description, duration, category, publicationDate);
        videoService.addVideo(video);
        System.out.println("Vídeo adicionado com sucesso!");
    }

    private void listVideos() {
        List<Video> videos = videoService.getAllVideos();  // Usando getAllVideos() em vez de listVideos()
        if (videos.isEmpty()) {
            System.out.println("Nenhum vídeo cadastrado.");
        } else {
            videos.forEach(System.out::println);
        }
    }

    private void searchVideos() {
        String query = InputValidator.readNonEmptyInput(scanner, "Digite o título para busca: ");

        // Chama o método search da SearchStrategy passando a lista de vídeos e o título
        List<Video> results = searchStrategy.search(videoService.getAllVideos(), query);

        if (results.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado para o título: " + query);
        } else {
            results.forEach(System.out::println);
        }
    }

    private void exit() {
        System.out.println("Saindo do sistema...");
        scanner.close();
        System.exit(0);
    }
}
