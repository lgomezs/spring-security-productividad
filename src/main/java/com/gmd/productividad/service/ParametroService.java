package com.gmd.productividad.service;

import com.gmd.productividad.dao.ParametroDAO;
import com.gmd.productividad.domain.Parametro;
import com.gmd.productividad.util.Constantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ParametroService {

	@Autowired
	private ParametroDAO parametroDAO;

	public List<Parametro> listarTiposDocumento() {
		return parametroDAO.findByTipo(Constantes.TIPO_DOCUMENTO);
	}
    public List<Parametro> listarRolesColaborador(){
        return parametroDAO.findByTipo(Constantes.COLABORADOR_ROL);
    }
    public List<Parametro> listarTiposLenguaje(){
        return parametroDAO.findByTipo(Constantes.TIPO_LENGUAJE);
    }
    public List<Parametro> listarCategoriasComponente(){
        return parametroDAO.findByTipo(Constantes.CATEGORIA_COMPONENTE);
    }
    public List<Parametro> listarTiposActividad(){
        return parametroDAO.findByTipo(Constantes.TIPO_ACTIVIDAD);
    }
    public List<Parametro> listarIndicadoresEstandar(){
        return parametroDAO.findByTipo(Constantes.INDICADOR_ESTANDAR);
    }
    public List<Parametro> listarTiposProceso(){
        return parametroDAO.findByTipo(Constantes.TIPO_PROCESO);
    }
    public List<Parametro> listarIndicadoresComplejidad(){
        return parametroDAO.findByTipo(Constantes.INDICADOR_COMPLEJIDAD);
    }
    public List<Parametro> listarEstadoSegunMovimiento() {
        return parametroDAO.findByTipo(Constantes.ESTADO_ASIGNACION_MOV);
    }

    public List<Parametro> listarTiposTarea() {
        return parametroDAO.findByTipo(Constantes.TIPO_TAREA);
    }

    public List<Parametro> listarHorarioTrabajo() {
        return parametroDAO.findByTipo(Constantes.HORARIO_TRABAJO);
    }

    public Parametro findById(Long id){
        return parametroDAO.read(id);
    }
    public Parametro getTipoDocumento(Long value) {
        return parametroDAO.findByTipoAndValor(Constantes.TIPO_DOCUMENTO, value.toString());
    }
    public Parametro getRolColaborador(Long value){
        return parametroDAO.findByTipoAndValor(Constantes.COLABORADOR_ROL, value.toString());
    }
    public Parametro getTipoLenguaje(Long value){
        return parametroDAO.findByTipoAndValor(Constantes.TIPO_LENGUAJE, value.toString());
    }
    public Parametro getCategoriaComponente(Long value){
        return parametroDAO.findByTipoAndValor(Constantes.CATEGORIA_COMPONENTE, value.toString());
    }
    public Parametro getTipoActividad(Long value){
        return parametroDAO.findByTipoAndValor(Constantes.TIPO_ACTIVIDAD, value.toString());
    }
    public Parametro getIndicadorEstandar(Long value){
        return parametroDAO.findByTipoAndValor(Constantes.INDICADOR_ESTANDAR, value.toString());
    }


    public Parametro getTipoProceso(Long value){
        return parametroDAO.findByTipoAndValor(Constantes.TIPO_PROCESO, value.toString());
    }
    public Parametro getIndicadorComplejidad(Long value){
        return parametroDAO.findByTipoAndValor(Constantes.INDICADOR_COMPLEJIDAD, value.toString());
    }
    public Parametro getTipoTarea(Long value) {
        return parametroDAO.findByTipoAndValor(Constantes.TIPO_TAREA, value.toString());
    }
    public Parametro getEstadoSegunMovimiento(String movimiento) {
        return parametroDAO.findByTipoAndValor(Constantes.ESTADO_ASIGNACION_MOV, movimiento);
    }
    public Parametro getHorarioTrabajo(String horario) {
        return parametroDAO.findByTipoAndValor(Constantes.HORARIO_TRABAJO, horario);
    }

    public Parametro getParametro(Iterator<Parametro> it, String value){
        while(it.hasNext()){
            Parametro parametro = it.next();
            if(parametro.getValor().equals(value))
                return parametro;
        }
        return null;
    }
}
