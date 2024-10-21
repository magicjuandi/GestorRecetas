package co.edu.cue.GestorRecetas.mapping.dtos;

import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import lombok.Builder;

import java.util.List;

@Builder
public record MenuDto(int idMenu,
                      List<Recipe> recipes,
                      Character status) {
}
