package Javadatabase.JVDTB.entity;

import Javadatabase.JVDTB.Validator.annotation.ValidCategoryId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    @NotEmpty(message = "Title must not be empty")
    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    private String title;

    @Column(name = "author", nullable = false)
    @NotEmpty(message = "Author must not be empty")
    private String author;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price is required")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ValidCategoryId(message = "Invalid Category ID")
    private Category category;
    
}

