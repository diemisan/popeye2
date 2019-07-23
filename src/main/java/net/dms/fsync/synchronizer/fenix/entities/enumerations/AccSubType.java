package net.dms.fsync.synchronizer.fenix.entities.enumerations;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 19/04/2017.
 */
public enum AccSubType implements VisualizableType{
    ANALISIS("ANALISIS", AccType.EVOLUTIVO ),
    CONSULTA("CONSULTA", AccType.EVOLUTIVO ),
    CODIFICACION("CODIFICACION",  AccType.EVOLUTIVO, AccType.CORRECCION_INCIDENCIAS),
    REVISION("REVISION",  AccType.EVOLUTIVO),
    CORRECCION("CORRECCION",  AccType.CORRECCION_INCIDENCIAS),
    DAILY_MEETING("DAILY MEETING", AccType.MEETING),
    REFINEMENT("REFINEMENT", AccType.MEETING),
    GESTION("GESTION", AccType.GESTION_PETICION);

    private AccType[] accTypes;
    private String description;

    AccSubType(String description, AccType ... accTypes) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Set<AccSubType> findAccSubtypesByType(AccType accType){
        return  Arrays.stream(AccSubType.values()).filter(s -> s.accTypes == null || Arrays.stream(accTypes).filter(t -> t.equals(accType)).count() > 0).collect(Collectors.toSet());
    }
}
