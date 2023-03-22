package com.hue.search.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes =
        {@Index(name = "uk_keyword", columnList = "keyword", unique = true)}
)
@NoArgsConstructor
public class SearchKeyword {
    public final static int INIT_COUNT = 1;
    public static int POPULAR_KEYWORD_MAX_COUNT = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)")
    private String keyword;

    @NotNull
    private Integer count;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public SearchKeyword(String searchKeyword) {
        this.keyword = searchKeyword;
        this.count = INIT_COUNT;
    }

    public void hit() {
        this.count++;
    }

}
