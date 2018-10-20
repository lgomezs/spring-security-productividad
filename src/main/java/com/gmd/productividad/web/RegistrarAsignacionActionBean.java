package com.gmd.productividad.web;

import com.gmd.productividad.domain.*;
import com.gmd.productividad.service.AsignacionTrabajoService;
import com.gmd.productividad.util.Constantes;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller("registrarAsignacionActionBean")
@Scope("session")
public class RegistrarAsignacionActionBean {
    Logger log = Logger.getLogger(RegistrarAsignacionActionBean.class);
    @Autowired
    private AsignacionTrabajoService asignacionTrabajoService;
    @Autowired
    private ComboModelActionBean comboModelActionBean;

    private AsignacionTrabajo asignacionTrabajo = initAsignacion();
    private List<AsignacionTrabajo> listaAsignacionTrabajo;
    private Long selectedId;
    private Long idLenguaje;
    private Long idColaborador;

    public void cargarAsignaciones(Long idColaborador){
        this.idColaborador = idColaborador;
        listaAsignacionTrabajo = asignacionTrabajoService.findByColaborador(idColaborador);
    }
    public List<AsignacionTrabajo> getAsignaciones(){
        return listaAsignacionTrabajo;
    }

	public void asignar(Long idColaborador){
        if(asignacionTrabajo.getHorasEstimadas()==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Las fechas no son validas.!"));
            return;
        }
        asignacionTrabajo.getColaborador().setId(idColaborador);
        asignacionTrabajoService.save(asignacionTrabajo);
        cargarAsignaciones(idColaborador);
        clean();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se guardo exitosamente!"));
    }
    private AsignacionTrabajo initAsignacion(){
        AsignacionTrabajo asignacionTrabajo = new AsignacionTrabajo();
        asignacionTrabajo.setComponente(new Componente());
        asignacionTrabajo.setHerramienta(new Herramienta());
        asignacionTrabajo.setTecnologia(new Tecnologia());
        asignacionTrabajo.setColaborador(new Colaborador());
        return asignacionTrabajo;
    }
    public void clean(){
        this.asignacionTrabajo = initAsignacion();
        listaAsignacionTrabajo=null;
        selectedId=null;
        idLenguaje=null;
        idColaborador=null;
    }

    public void delete(){
        try{
            asignacionTrabajoService.delete(selectedId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion", "Se elimino la asignacion correctamente!"));
            cargarAsignaciones(idColaborador);
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Hubo un error al intentar eliminar!"));
        }
    }


    public void updateHorasEstimadas(){
        if(asignacionTrabajo.getFechaInicio()!=null && asignacionTrabajo.getFechaFinEstimada()!=null){
            //Para una respuesta más rápida programar el procedimiento en java.
            Integer horas = asignacionTrabajoService.calcularHorasLaborables(asignacionTrabajo.getFechaInicio(), asignacionTrabajo.getFechaFinEstimada());
            if(horas<=0){

                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: ", "El tiempo de trabajo no pueden ser negativo  o igual a cero."));
                horas = null;
            }
            asignacionTrabajo.setHorasEstimadas(horas);
        }
    }

    public int getMinHoraInicio(){
       return Integer.parseInt(comboModelActionBean.getHorarioTrabajo(Constantes.ENTRADA).getDetalle());
    }

    public int getMaxHoraInicio(){
       if(asignacionTrabajo.getFechaFinEstimada()!=null && asignacionTrabajo.getFechaInicio()!=null && DateUtils.isSameDay(asignacionTrabajo.getFechaFinEstimada(), asignacionTrabajo.getFechaInicio())){
            Calendar c = Calendar.getInstance();
            c.setTime(asignacionTrabajo.getFechaFinEstimada());
            return c.get(Calendar.HOUR_OF_DAY);
        }
        return Integer.parseInt(comboModelActionBean.getHorarioTrabajo(Constantes.SALIDA).getDetalle());
    }

    public int getMinHoraFin(){
        if(asignacionTrabajo.getFechaFinEstimada()!=null && asignacionTrabajo.getFechaInicio()!=null && DateUtils.isSameDay(asignacionTrabajo.getFechaFinEstimada(), asignacionTrabajo.getFechaInicio())){
            Calendar c = Calendar.getInstance();
            c.setTime(asignacionTrabajo.getFechaInicio());
            return c.get(Calendar.HOUR_OF_DAY);
        }
        return Integer.parseInt(comboModelActionBean.getHorarioTrabajo(Constantes.ENTRADA).getDetalle());
    }
    public int getMaxHoraFin(){
        return Integer.parseInt(comboModelActionBean.getHorarioTrabajo(Constantes.SALIDA).getDetalle());
    }
    public Date getMinDateInicio(){
        return new Date();
    }
    public Date getMaxDateFin(){
        return new Date(Long.MAX_VALUE);
    }
    public AsignacionTrabajo getAsignacionTrabajo() {
        return asignacionTrabajo;
    }

    public void setAsignacionTrabajo(AsignacionTrabajo asignacionTrabajo) {
        this.asignacionTrabajo = asignacionTrabajo;
    }

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }

    public Long getIdLenguaje() {
        return idLenguaje;
    }

    public void setIdLenguaje(Long idLenguaje) {
        this.idLenguaje = idLenguaje;
    }

    public Long getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Long idColaborador) {
        this.idColaborador = idColaborador;
    }
}

