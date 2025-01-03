package strategy;

import model.Video;
import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Video> search(List<Video> videos, String criterion) {
        return videos.stream()
                .filter(video -> video.getTitle().equalsIgnoreCase(criterion))  // Filtra por título
                .collect(Collectors.toList());
    }
}
