package br.com.biblioteca.infra.mapper;

import br.com.biblioteca.core.model.Projeto;
import br.com.biblioteca.infra.database.jpa.ProjetoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjetoMapper {
    ProjetoMapper INSTANCE = Mappers.getMapper(ProjetoMapper.class);

    ProjetoEntity toEntity(Projeto projeto);
    Projeto toDomain(ProjetoEntity projetoEntity);
}
