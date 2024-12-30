package service;

import model.Video;
import strategy.SearchStrategy;

import java.util.List;

public interface VideoService {
    void addVideo(Video video);
    void removeVideo(String title);
    List<Video> getAllVideos();
    List<Video> searchVideos(SearchStrategy strategy);
}
