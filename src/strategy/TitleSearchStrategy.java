package strategy;

import model.Video;

import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Video> search(List<Video> videos) {
        return List.of();
    }

    @Override
    public List<Video> search(List<Video> videos, String query) {
        return videos.stream()
                .filter(video -> video.getTitle().equalsIgnoreCase(query))  // Filtra pelo título
                .collect(Collectors.toList());
    }
}
