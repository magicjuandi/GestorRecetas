package co.edu.cue.GestorRecetas.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recipes", schema = "public")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRecipe;
    private String name;
    private String description;
    private String minutes;
    private String preparation;
    @ManyToMany
    @JoinTable(name = "recipe_ingredient",
    joinColumns = @JoinColumn(name = "idRecipe"),
    inverseJoinColumns = @JoinColumn(name = "idIngredient"))
    @JsonManagedReference
    private List<Ingredient> ingredients;
    private Boolean favorite;
    private String review;
    private Character status;
    @ManyToMany(mappedBy = "recipes")
    private List<Menu> menus;
}
