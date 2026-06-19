package com.template.controller;

import com.template.entity.Post;
import com.template.service.FeedService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    /** Server-Sent Events stream for real-time feed */
    @GetMapping(value = "/stream/{userId}", produces = "text/event-stream")
    public Flux<Post> streamFeed(@PathVariable Long userId) {
        return feedService.getFeedStream(userId);
    }

    @GetMapping("/{userId}")
    public Flux<Post> getFeed(@PathVariable Long userId,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size) {
        return feedService.getPaginatedFeed(userId, page, size);
    }
}
