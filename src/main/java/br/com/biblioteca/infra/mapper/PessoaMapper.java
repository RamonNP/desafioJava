package br.com.biblioteca.infra.mapper;

import br.com.biblioteca.core.model.Pessoa;
import br.com.biblioteca.infra.database.jpa.PessoaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public interface PessoaMapper {
    public static PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    public abstract Pessoa toDomain(PessoaEntity pessoaEntity);
    public abstract PessoaEntity toEntity(Pessoa pessoa);
}
