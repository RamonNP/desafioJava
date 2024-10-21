package br.com.biblioteca.config;

import br.com.biblioteca.core.model.Membro;
import br.com.biblioteca.infra.database.jpa.MembroEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Configura para lidar com coleções do Hibernate como PersistentBag
        modelMapper.getConfiguration().setPropertyCondition(context -> !(context.getSource() instanceof org.hibernate.collection.spi.PersistentCollection));

        // Mapeamento personalizado de Membro para MembroEntity
        modelMapper.addMappings(new PropertyMap<Membro, MembroEntity>() {
            @Override
            protected void configure() {
                // Mapeia pessoaId e projetoId a partir de Membro
                map().getId().setPessoaId(source.getPessoa().getId());
                map().getId().setProjetoId(source.getProjeto().getId());
            }
        });

        // Mapeamento inverso de MembroEntity para Membro
        modelMapper.addMappings(new PropertyMap<MembroEntity, Membro>() {
            @Override
            protected void configure() {
                // Mapeia a pessoa e o projeto
                map(source.getPessoa(), destination.getPessoa());
                map(source.getProjeto(), destination.getProjeto());
            }
        });

        return modelMapper;
    }
}
