package co.edu.cue.GestorRecetas.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ingredients", schema = "public")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idIngredient;
    private String name;
    @ManyToMany(mappedBy = "ingredients")
    private List<Recipe> recipes;
    private int quantity;
    private String unit;
    private Character status;
}
