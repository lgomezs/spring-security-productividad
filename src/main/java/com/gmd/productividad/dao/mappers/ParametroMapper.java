package com.gmd.productividad.dao.mappers;

import com.gmd.productividad.domain.Parametro;

import java.util.List;

public interface ParametroMapper extends GenericMapper<Parametro> {
    List<Parametro> findByTipo(String tipo);
    Parametro findByTipoAndValor(String tipo, String valor);
}
