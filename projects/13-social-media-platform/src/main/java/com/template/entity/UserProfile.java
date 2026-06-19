package com.template.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * PROJECT 13: Social Media Platform
 * Stack: Spring Boot + Hibernate + MySQL + Reactive (WebFlux) + JUnit + Spring Security
 *
 * Features: User profiles, Posts, Comments, Likes, Follow/Unfollow,
 *           News feed, Notifications, Direct messaging, Hashtags.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "display_name")
    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "follows",
               joinColumns        = @JoinColumn(name = "follower_id"),
               inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<UserProfile> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<UserProfile> followers;
}
