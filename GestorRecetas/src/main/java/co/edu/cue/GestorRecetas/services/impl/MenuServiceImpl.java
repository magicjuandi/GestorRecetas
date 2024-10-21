package co.edu.cue.GestorRecetas.services.impl;

import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
import co.edu.cue.GestorRecetas.mapping.mappers.MenuMapper;
import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
import co.edu.cue.GestorRecetas.repositories.MenuRepository;
import co.edu.cue.GestorRecetas.services.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository repository;

    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MenuDto> getAll() {
        return MenuMapper.mapFrom((List<Menu>)repository.findAll());
    }

    @Override
    public MenuDto save(MenuDto menu) {
        return MenuMapper.mapFrom(repository.save(MenuMapper.mapFrom(menu)));
    }

    @Override
    public MenuDto update(MenuDto menu) {
        repository.findById(menu.idMenu()).orElseThrow();
        return MenuMapper.mapFrom(repository.save(MenuMapper.mapFrom(menu)));
    }

    @Override
    public void updateStatus(int id, Character status) {
        Menu menu = repository.findById(id).orElseThrow();
        menu.setStatus(status);
        repository.save(menu);
    }

    @Override
    public MenuDto getById(int id) {
        Menu menu = repository.findById(id).orElseThrow();
        return MenuMapper.mapFrom(menu);
    }
}
