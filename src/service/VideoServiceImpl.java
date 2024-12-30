package service;

import model.Video;
import repository.FileVideoRepository;
import strategy.SearchStrategy;

import java.util.List;

public class VideoServiceImpl implements VideoService {
    private final FileVideoRepository repository;

    public VideoServiceImpl(FileVideoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addVideo(Video video) {
        List<Video> videos = repository.loadVideos();
        videos.add(video);
        repository.saveVideos(videos);
    }

    @Override
    public void removeVideo(String title) {
        repository.deleteVideo(title);
    }

    @Override
    public List<Video> getAllVideos() {
        return repository.loadVideos();
    }

    @Override
    public List<Video> searchVideos(SearchStrategy strategy) {
        return strategy.search(repository.loadVideos());
    }
}
