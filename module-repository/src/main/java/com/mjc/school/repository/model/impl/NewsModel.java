package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@Table(name = "News")
@EntityListeners({AuditingEntityListener.class})
public class NewsModel implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Content")
    private String content;
    @Column(name = "Title")
    private String title;
    @CreatedDate
    @Column(name = "Created_Date")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "Last_Updated_Date")
    private LocalDateTime lastUpdatedDate;
    //@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    AuthorModel author;
    //@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "news_tags",
            joinColumns = {@JoinColumn(name = "news_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    List<TagModel> tags = new LinkedList<>();
    //@OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    List<CommentModel> comments = new LinkedList<>();
}
