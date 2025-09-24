package br.com.theroguedev.api.publication.entity;

import br.com.theroguedev.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "forum_publications")
public class ForumPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "forum_publication_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(name = "image_url",columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ForumPublication parent;

    @ManyToOne
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany
    @JoinTable(name = "forum_publications_authors",
            joinColumns = @JoinColumn(name = "forum_publication_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> authors;

    @ManyToMany
    @JoinTable(name = "forum_publications_tools",
            joinColumns = @JoinColumn(name = "forum_publication_id"),
            inverseJoinColumns = @JoinColumn(name = "publication_tool_id")
    )
    private List<Tool> tools;

    @ManyToMany
    @JoinTable(name = "forum_publications_topics",
            joinColumns = @JoinColumn(name = "forum_publication_id"),
            inverseJoinColumns = @JoinColumn(name = "publication_topic_id")
    )
    private List<Topic> topics;

}
