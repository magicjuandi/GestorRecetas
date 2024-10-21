package co.edu.cue.GestorRecetas.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menus", schema = "public")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMenu;
    @ManyToMany
    @JoinTable(name = "menu_recipe",
            joinColumns = @JoinColumn(name = "idMenu"),
            inverseJoinColumns = @JoinColumn(name = "idReciepe"))
    private List<Recipe> recipes;
    private Character status;
}
